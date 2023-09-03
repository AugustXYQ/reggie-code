package org.itxyq.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.itxyq.reggie.common.R;
import org.itxyq.reggie.entity.User;
import org.itxyq.reggie.service.UserService;
import org.itxyq.reggie.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author xyq 13127
 * @version 1.0.0
 * @date 2023/9/3
 * @description 用户表现层
 **/
@Slf4j
@RestController
@Api(value = "/user", tags = "测试UserController相关api")
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "发送验证码短信", notes = "发送验证码短信")
    @ApiImplicitParam(name = "user", value = "User", required = true, dataType = "User")
    @PostMapping("/sendMsg")
    public R<String> sendMsg(HttpSession session, @RequestBody User user) {
        //获取手机号
        String phone = user.getPhone();

        if (StringUtils.isNotEmpty(phone)) {
            //生成随机的4位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code={}", code);
            //调用阿里云提供的短信服务api完成发送短信
            //SMSUtils.sendMessage("瑞吉外卖", "", phone, code);
            //需要将生成的验证码保存到session
            session.setAttribute(phone, code);
            return R.success("手机验证码发送成功");
        }
        return R.error("手机号不存在");
    }

    @ApiOperation(value = "移动端用户登录", notes = "移动端用户登录")
    @ApiImplicitParam(name = "map", value = "Map", required = true, dataType = "Map")
    @PostMapping("/login")
    public R<User> login(HttpSession session, @RequestBody Map<String, String> map) {
        //获取手机号
        String phone = map.get("phone");
        //获取验证码
        String code = map.get("code");
        //获取session保存的验证码
        Object codeInSession = session.getAttribute(phone);
        //进行验证码的比对 (页面提交 和 session 中的比对) 比对成功即表示登录成功
        if (codeInSession != null && codeInSession.equals(code)) {
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone, phone);
            User user = userService.getOne(queryWrapper);
            if (user == null) {
                //判断当前手机号对应的用户是否为新用户, 如果是新用户则自动完成注册
                user = new User();
                user.setPhone(phone);
                userService.save(user);
            }
            session.setAttribute("user", user.getId());
            return R.success(user);
        }
        return R.error("用户登录失败");
    }
}
