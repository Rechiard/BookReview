package com.tjnu.system.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.tjnu.frame.consts.SessionConst;
import com.tjnu.frame.dto.ResponseResult;
import com.tjnu.frame.shiro.util.ShiroUtils;
import com.tjnu.system.entity.UserInfo;
import com.tjnu.system.service.UserInfoService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @Autowired
    UserInfoService userInfoService;

    @RequestMapping("login")
    @ResponseBody
    public ResponseResult login(@RequestParam("username")String username,
                                @RequestParam("password")String password){
        //System.out.println("进入方法1");
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        Subject subject = SecurityUtils.getSubject();
        try{
            subject.login(token);
            UserInfo userInfo = userInfoService.getByName(username);
            //System.out.println("userInfo==="+userInfo);
            ShiroUtils.setSessionAttribute(SessionConst.USER_INFO_SESSION,userInfo);
            String loginSuccessUrl = "toIndex";
            return ResponseResult.success("登录成功",loginSuccessUrl);
        }catch(UnknownAccountException e){
            return ResponseResult.error("用户不存在");
        }catch (IncorrectCredentialsException e){
            return ResponseResult.error("密码错误！");
        }
    }

    @RequestMapping("loginBackStage")
    @ResponseBody
    public ResponseResult loginBackStage(@RequestParam("username")String username,
                                         @RequestParam("password")String password){
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        Subject subject = SecurityUtils.getSubject();
        try{
            subject.login(token);
            UserInfo userInfo = userInfoService.getByName(username);
            //System.out.println("userInfo==="+userInfo);
            if(userInfo.getRole() != 2){
                return ResponseResult.error("您不是管理员，无权登录");
            }
            ShiroUtils.setSessionAttribute(SessionConst.USER_INFO_SESSION,userInfo);
            String loginSuccessUrl = "toBackIndex";
            return ResponseResult.success("登录成功",loginSuccessUrl);
        }catch(UnknownAccountException e){
            return ResponseResult.error("用户不存在");
        }catch (IncorrectCredentialsException e){
            return ResponseResult.error("密码错误！");
        }

    }

}
