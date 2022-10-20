package com.tjnu.system.service;

import com.tjnu.frame.dto.ResponseResult;
import com.tjnu.system.entity.BookInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tjnu.system.entity.UserInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bobo
 * @since 2021-05-26
 */
public interface BookInfoService extends IService<BookInfo> {

    ResponseResult listByTable(BookInfo entity);

    ResponseResult saveEntity(BookInfo entity);

    ResponseResult updateEntity(BookInfo entity);

    List<BookInfo> TodayRecommendation();

    List<BookInfo> listByLike(String bookSort);

    List<BookInfo> listByType(BookInfo entity);

    List<BookInfo> listBy2000(BookInfo entity);

    List<BookInfo> listByRatingSum(BookInfo entity);

    List<BookInfo> listByGuess(BookInfo entity);

    BookInfo selectOneById(int id);
}
