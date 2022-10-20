package com.tjnu.system.mapper;

import com.tjnu.system.entity.BookInfo;
import com.tjnu.system.entity.CommentInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tjnu.system.vo.CommentInfoVo;
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
public interface CommentInfoMapper extends BaseMapper<CommentInfo> {

    int countByBookId(@Param("entity")BookInfo bookInfo);

    List<CommentInfoVo> listByBookId(@Param("entity")BookInfo bookInfo);

}
