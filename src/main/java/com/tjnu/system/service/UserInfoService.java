package com.tjnu.system.service;

import com.tjnu.frame.dto.ResponseResult;
import com.tjnu.system.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bobo
 * @since 2021-05-26
 */
public interface UserInfoService extends IService<UserInfo> {

    UserInfo getByName(String userName);

    ResponseResult listByTable(UserInfo entity);

    ResponseResult saveEntity(UserInfo entity);

    ResponseResult updateEntity(UserInfo entity);

    ResponseResult updatePwdById(int id,String password,String newPassword);

    ResponseResult resetPwd(String password);

    ResponseResult selectOneByEmail(String email);
}
