package com.tjnu.system.controller;

import com.tjnu.frame.consts.SessionConst;
import com.tjnu.frame.dto.ResponseResult;
import com.tjnu.frame.shiro.util.ShiroUtils;
import com.tjnu.system.entity.UserInfo;
import com.tjnu.system.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bobo
 * @since 2021-05-26
 */
@Controller
@RequestMapping("/system/userInfo")
public class UserInfoController {

    @Autowired
    UserInfoService userInfoService;

    @RequestMapping("init")
    public String init(){
        return "system/backstage/user_Info_list";
    }

    @RequestMapping("listByTable")
    @ResponseBody
    public ResponseResult listByTable(UserInfo userInfo){
        return userInfoService.listByTable(userInfo);
    }

    @PostMapping("deleteById")
    @ResponseBody
    public ResponseResult deleteById(int id){
        userInfoService.removeById(id);
        return ResponseResult.success("删除成功");
    }

    @RequestMapping("save")
    @ResponseBody
    public ResponseResult save(UserInfo userInfo){

        //System.out.println("注册得到的userInfo："+userInfo);
        return userInfoService.saveEntity(userInfo);
    }

    @RequestMapping("update")
    @ResponseBody
    public ResponseResult update(UserInfo userInfo){
        //System.out.println("修改个人信息得到的userInfo："+userInfo);
        return userInfoService.updateEntity(userInfo);
    }

    @RequestMapping("updatePwd")
    @ResponseBody
    public ResponseResult updatePwd(@RequestParam("id")int id,
                                    @RequestParam("password")String password,
                                    @RequestParam("newPassword1")String newPassword1){
        //System.out.println("修改密码得到的id："+id+"，旧密码："+password+"，新密码："+newPassword1);
        return userInfoService.updatePwdById(id,password,newPassword1);
    }

    @RequestMapping("resetPwd")
    @ResponseBody
    public ResponseResult resetPwd(@RequestParam("password")String password){
        return userInfoService.resetPwd(password);
    }

    @RequestMapping("getEmail")
    @ResponseBody
    public ResponseResult getEmail(@RequestParam("email")String email){
        System.out.println("我接受到的邮箱为："+email);
        //通过邮箱查询数据库，看是否有账号，如果没有返回error，如果有，则向邮箱发送验证码
        return userInfoService.selectOneByEmail(email);
    }

    @RequestMapping("getIdentifyCode")
    @ResponseBody
    public ResponseResult getIdentifyCode(@RequestParam("email")String email,@RequestParam("identifyCode")String identifyCode){
        //System.out.println("我接受到的邮箱为："+email+"，我接受到的验证码为:"+identifyCode);
        String code = (String) ShiroUtils.getSessionAttribute(SessionConst.IDENTIFYCODE);
        if( !code.equals(identifyCode)){
            return ResponseResult.error("验证码输入错误");
        }
        String pass = "toGetPassword";
        return ResponseResult.success("验证成功",pass);
    }


}
