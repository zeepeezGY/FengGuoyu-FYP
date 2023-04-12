package cn.stylefeng.guns.modular.api.controller;

import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.core.shiro.service.UserAuthService;
import cn.stylefeng.guns.core.util.JwtTokenUtil;
import cn.stylefeng.guns.modular.system.entity.User;
import cn.stylefeng.guns.modular.system.service.UserService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class ApiLoginController  extends BaseController {


    @Autowired
    private UserAuthService userService;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseData login(@RequestBody JSONObject jsonObject) {
        String username = jsonObject.getString("username").trim();
        String password = jsonObject.getString("password").trim();
        User user = userService.user(username);
        String md5password = ShiroKit.md5(password, user.getSalt());
        if(!user.getPassword().equalsIgnoreCase(md5password)){
            return ResponseData.error("密码错误");
        }
        String token =JwtTokenUtil.generateToken(String.valueOf(user.getUserId()));
        Map map =new HashMap();
        map.put("token",token);
        map.put("userId",user.getUserId());
        //登录成功
        return ResponseData.success(map);
    }




}
