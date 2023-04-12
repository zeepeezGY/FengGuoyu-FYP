package cn.stylefeng.guns.modular.system.service;

import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.core.util.JwtTokenUtil;
import cn.stylefeng.guns.modular.system.entity.SystemAttachment;
import cn.stylefeng.guns.modular.system.mapper.SystemAttachmentMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SystemAttachmentService  extends ServiceImpl<SystemAttachmentMapper, SystemAttachment> {

    public Page<Map<String, Object>> list(String condition) {
        Page page = LayuiPageFactory.defaultPage();
        String userId = JwtTokenUtil.getUsername();
        return this.baseMapper.list(page, condition,userId);
    }

}
