package com.tjnu.system.service;

import com.tjnu.frame.dto.ResponseResult;
import com.tjnu.system.entity.BookInfo;
import com.tjnu.system.entity.CollectInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tjnu.system.entity.UserInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bobo
 * @since 2021-05-26
 */
public interface CollectInfoService extends IService<CollectInfo> {

    ResponseResult listByUserId(int page,int limit);

    ResponseResult saveBookByUser(BookInfo bookInfo);

    Boolean isCollect(UserInfo userInfo,BookInfo entity);

}
