package com.tjnu.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.tjnu.frame.consts.SessionConst;
import com.tjnu.frame.dto.ResponseResult;
import com.tjnu.frame.shiro.util.ShiroUtils;
import com.tjnu.frame.util.IdentifyCodeUtil;
import com.tjnu.frame.util.SendMailUtil;
import com.tjnu.system.entity.UserInfo;
import com.tjnu.system.mapper.UserInfoMapper;
import com.tjnu.system.service.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bobo
 * @since 2021-05-26
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {


    @Override
    public UserInfo getByName(String userName) {
        UserInfo userInfoVo = new UserInfo();
        UserInfo userInfo = baseMapper.selectOne(
                new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getUsername, userName));
        BeanUtils.copyProperties(userInfo, userInfoVo);
        return userInfoVo;
    }

    @Override
    public ResponseResult listByTable(UserInfo entity) {
        return ResponseResult.table(baseMapper.countByTable(entity),baseMapper.listByTable(entity));
    }

    //注册
    @Override
    public ResponseResult saveEntity(UserInfo entity) {
        //查询user_info表中的所有name 是否和 添加的entity的name 有相同的
        List<UserInfo> existList = baseMapper.selectList(new LambdaQueryWrapper<UserInfo>()
                .eq(UserInfo::getUsername,entity.getUsername()));
        if(!CollectionUtils.isEmpty(existList)){
            return ResponseResult.error("用户名已存在"+entity.getUsername());
        }
        UserInfo userInfo = baseMapper.selectOneByEmail(entity.getEmail());
        if(userInfo != null){
            return ResponseResult.error("邮箱已存在");
        }
        entity.setSalt(ShiroUtils.getRandomSalt());
        //System.out.println("用户的密码为："+entity.getPassword()+" 用户的盐值为:"+entity.getSalt());
        entity.setPassword(ShiroUtils.genPassword(entity.getPassword(),entity.getSalt()));
        entity.setRole(1);
        baseMapper.insert(entity);
        //保存用户信息
        ShiroUtils.setSessionAttribute(SessionConst.USER_INFO_SESSION,entity);
        return ResponseResult.success("注册成功");
    }

    @Override
    public ResponseResult updateEntity(UserInfo entity) {
        //查询user_info表中的所有name 是否和 添加的entity的name 有相同的
        List<UserInfo> existList = baseMapper.selectList(new LambdaQueryWrapper<UserInfo>()
                .eq(UserInfo::getUsername,entity.getUsername()));
        //如果有则返回error
        if(!CollectionUtils.isEmpty(existList) && existList.size()>1){
            return ResponseResult.error("数据库中存在多个用户名："+entity.getUsername());
        }else if(existList.size() == 1 && !existList.get(0).getId().equals(entity.getId())){
            return ResponseResult.error("用户名已存在"+entity.getUsername());
        }else{
            baseMapper.updateById(entity);
            ShiroUtils.removeSessionAttribute(SessionConst.USER_INFO_SESSION);
            ShiroUtils.setSessionAttribute(SessionConst.USER_INFO_SESSION,entity);
        }
        return ResponseResult.success("更新成功");
    }

    @Override
    public ResponseResult updatePwdById(int id,String password,String newPassword) {
        UserInfo userInfo = (UserInfo) ShiroUtils.getSessionAttribute(SessionConst.USER_INFO_SESSION);
        String pwd = ShiroUtils.genPassword(password, userInfo.getSalt());  //获得用户输入的同一盐值密码
        if (StringUtils.isEmpty(password) || StringUtils.isEmpty(newPassword)){
            return ResponseResult.error("旧密码或新密码不能为空");
        }else{
            if (userInfo != null && StringUtils.equals(pwd,userInfo.getPassword())) {
                //根据原来的盐值重新加密新密码
                userInfo.setPassword(ShiroUtils.genPassword(newPassword,userInfo.getSalt()));
                //新密码重新加密
                baseMapper.updateById(userInfo);
                return ResponseResult.success("修改密码成功，请重新登录");
            }else{
                return ResponseResult.error("旧密码错误！");
            }
        }
    }

    @Override
    public ResponseResult resetPwd(String password) {
        if (password.equals("") || password == null){
            return ResponseResult.error("密码不能为空");
        }
        UserInfo userInfo = (UserInfo) ShiroUtils.getSessionAttribute(SessionConst.USER_INFO_SESSION);
        userInfo.setPassword(ShiroUtils.genPassword(password,userInfo.getSalt()));
        baseMapper.updateById(userInfo);
        return ResponseResult.success("修改密码成功");
    }

    @Override
    public ResponseResult selectOneByEmail(String email) {
        if (email == null || email.equals("")){
            return ResponseResult.error("邮箱输入有误");
        }
        UserInfo userInfo = baseMapper.selectOneByEmail(email);
        if (userInfo == null){
            return ResponseResult.error("暂无您的账户信息");
        }
        //System.out.println("通过邮箱查询的个人信息为："+userInfo);
        ShiroUtils.setSessionAttribute(SessionConst.USER_INFO_SESSION,userInfo);
        //发送验证码功能
        SendMailUtil sendMailUtil = new SendMailUtil();
        IdentifyCodeUtil identifyCodeUtil = new IdentifyCodeUtil();
        String identifyCode = identifyCodeUtil.createIdentifyCode();
        //保存验证码信息
        ShiroUtils.setSessionAttribute(SessionConst.IDENTIFYCODE,identifyCode);
        //发送验证码到邮箱
        sendMailUtil.sendingMimeMail(email,identifyCode);
        return ResponseResult.success("成功发送验证码到您的邮箱");
    }
}
