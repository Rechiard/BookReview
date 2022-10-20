package com.tjnu.system.service;

import com.tjnu.frame.dto.ResponseResult;
import com.tjnu.system.entity.BookInfo;
import com.tjnu.system.entity.CommentInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tjnu.system.vo.CommentInfoVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bobo
 * @since 2021-05-26
 */
public interface CommentInfoService extends IService<CommentInfo> {

    List<CommentInfoVo> listByBookId(BookInfo bookInfo);

    ResponseResult addCommentByBookId(CommentInfo commentInfo);
}
