package cn.stylefeng.guns.modular.api.controller;

import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.modular.system.service.SystemAttachmentService;
import cn.stylefeng.guns.modular.system.warpper.NoticeWrapper;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/file")
public class FileController {

    @Autowired
    private SystemAttachmentService systemAttachmentService;

    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Page<Map<String, Object>> list = this.systemAttachmentService.list(condition);
        Page<Map<String, Object>> wrap = new NoticeWrapper(list).wrap();
        return LayuiPageFactory.createPageInfo(wrap);
    }

    @RequestMapping(value = "/findOne")
    @ResponseBody
    public ResponseData findOne(String cid) {
        return ResponseData.success(systemAttachmentService.getById(cid));
    }


}
