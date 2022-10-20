package com.tjnu.system.mapper;

import com.tjnu.system.entity.BookInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tjnu.system.entity.UserInfo;
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
public interface BookInfoMapper extends BaseMapper<BookInfo> {

    int countByTable(@Param("entity") BookInfo entity);

    List<BookInfo> listByTable(@Param("entity") BookInfo entity);

    List<BookInfo> listByTableImg3(@Param("entity") BookInfo entity);

    List<BookInfo> listByType(@Param("entity")BookInfo entity);

    List<BookInfo> listByLikeImg3(@Param("entity")BookInfo entity);

    List<BookInfo> listBy2000(@Param("entity")BookInfo entity);

    List<BookInfo> listByRatingSum(@Param("entity")BookInfo entity);

}
