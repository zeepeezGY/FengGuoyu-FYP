package cn.stylefeng.guns.modular.api.controller;

import cn.stylefeng.guns.config.properties.GunsProperties;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.constants.Constants;
import cn.stylefeng.guns.core.util.DateUtil;
import cn.stylefeng.guns.core.util.JwtTokenUtil;
import cn.stylefeng.guns.core.util.Sha256;
import cn.stylefeng.guns.modular.system.entity.SystemAttachment;
import cn.stylefeng.guns.modular.system.service.SystemAttachmentService;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

@Controller
@RequestMapping("/api")
@Slf4j
public class UploadController {

    @Autowired
    private GunsProperties gunsProperties;

    @Autowired
    private SystemAttachmentService systemAttachmentService;


    /**
     * 云端接口，保存客户端传过来的文件,返回文件id
     * @param picture
     * @param hash
     * @param contractAddress
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, path = "/upload")
    @ResponseBody
    public ResponseData upload(@RequestPart("file") MultipartFile picture, @RequestPart(value = "fileName")String fileName
            , @RequestPart(value = "hash",required=true)String hash
            , @RequestPart(value = "contractAddress",required = false)String contractAddress) {
        String attType =ToolUtil.getFileSuffix(picture.getOriginalFilename());
        String pictureName = UUID.randomUUID().toString() + "." + attType;
        String fileSavePath="";
        String sattDir="";
        File file  =null;
        try {
            sattDir =DateUtil.nowDate(Constants.DATE_FORMAT_DATE).replace("-", "/") + "/"+pictureName;
            fileSavePath =gunsProperties.getFileUploadPath()+"/"+ sattDir;
            file  =new File(fileSavePath);
            if(!file.getParentFile().exists() ){
                file.getParentFile().mkdirs();
            }
            picture.transferTo(file);
        } catch (Exception e) {
            throw new ServiceException(BizExceptionEnum.UPLOAD_ERROR);
        }
        SystemAttachment systemAttachment =new SystemAttachment();
        systemAttachment.setAttDir(fileSavePath);
        systemAttachment.setAttType(attType) ;
        systemAttachment.setImageType(1);
        systemAttachment.setStatus(0);
        systemAttachment.setAttSize(String.valueOf(picture.getSize()));
        if(StringUtils.isNotEmpty(fileName)){
            systemAttachment.setName(fileName);
        }else{
            systemAttachment.setName(picture.getOriginalFilename());
        }
        systemAttachment.setCreateTime(new Date());
        // 相对虚拟路径
        systemAttachment.setSattDir("/upload/"+sattDir);
        systemAttachment.setHash(hash);
        systemAttachment.setContractAddress(contractAddress);
        String userId = JwtTokenUtil.getUsername();
        systemAttachment.setUserId(userId);
        systemAttachmentService.save(systemAttachment);
        HashMap<String, Object> map = new HashMap<>();
        map.put("file",systemAttachment);
        return ResponseData.success(map);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/recoverCloud")
    @ResponseBody
    public ResponseData recoverCloud(@RequestPart("file") MultipartFile uploadfile, @RequestPart(value = "cid")String cid) throws IOException {
        // 需要覆盖的文件
        SystemAttachment systemAttachment =  systemAttachmentService.getById(cid);
        if(systemAttachment==null){
            return ResponseData.error("记录不存在");
        }
        String userId = JwtTokenUtil.getUsername();
        if(!systemAttachment.getUserId().equals(userId)){
            return ResponseData.error("缺少操作权限");
        }
        String fileSavePath=systemAttachment.getAttDir();
        File  file  =new File(fileSavePath);
        if(!file.getParentFile().exists() ){
            file.getParentFile().mkdirs();
        }
        log.info("恢复文件{}",file.getName());
        uploadfile.transferTo(file);
        return ResponseData.success();
    }




}
