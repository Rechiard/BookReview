package com.tjnu.system.service.impl;

import com.tjnu.frame.consts.SessionConst;
import com.tjnu.frame.dto.ResponseResult;
import com.tjnu.frame.shiro.util.ShiroUtils;
import com.tjnu.system.entity.BookInfo;
import com.tjnu.system.entity.CommentInfo;
import com.tjnu.system.entity.UserInfo;
import com.tjnu.system.mapper.CommentInfoMapper;
import com.tjnu.system.service.CommentInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tjnu.system.vo.CommentInfoVo;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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
public class CommentInfoServiceImpl extends ServiceImpl<CommentInfoMapper, CommentInfo> implements CommentInfoService {

    @Override
    public List<CommentInfoVo> listByBookId(BookInfo bookInfo) {
        return baseMapper.listByBookId(bookInfo);
    }

    @Override
    public ResponseResult addCommentByBookId(CommentInfo commentInfo) {
        UserInfo userInfo = (UserInfo) ShiroUtils.getSessionAttribute(SessionConst.USER_INFO_SESSION);
        if (userInfo == null){
            return ResponseResult.error("请先登录");
        }
        commentInfo.setUserId(userInfo.getId());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        commentInfo.setCreateTime(timestamp);
        baseMapper.insert(commentInfo);
        return ResponseResult.success("评论成功");
    }
}
