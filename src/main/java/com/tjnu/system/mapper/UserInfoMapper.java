package com.tjnu.system.mapper;

import com.tjnu.system.entity.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author bobo
 * @since 2021-05-26
 */
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    int countByTable(@Param("entity") UserInfo entity);

    List<UserInfo> listByTable(@Param("entity") UserInfo entity);

    UserInfo selectOneByEmail(@Param("email")String email);


}
