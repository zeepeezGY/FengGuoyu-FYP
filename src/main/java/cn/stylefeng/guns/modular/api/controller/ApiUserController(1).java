package cn.stylefeng.guns.modular.api.controller;

import cn.stylefeng.guns.core.util.JwtTokenUtil;
import cn.stylefeng.guns.modular.system.entity.User;
import cn.stylefeng.guns.modular.system.service.UserService;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api")
public class ApiUserController {


    @Autowired
    private UserService userService;

    /**
     * 已登录用户获取信息
     * @return
     */
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public Object getUserInfo() {
        String userId =JwtTokenUtil.getUsername();
        User user = this.userService.getById(userId);
        return ResponseData.success(user);
    }

}
