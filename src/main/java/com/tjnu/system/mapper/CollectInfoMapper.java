package com.tjnu.system.mapper;

import com.tjnu.system.entity.BookInfo;
import com.tjnu.system.entity.CollectInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tjnu.system.entity.UserInfo;
import com.tjnu.system.vo.CollectInfoVo;
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
public interface CollectInfoMapper extends BaseMapper<CollectInfo> {

    int countByUserId(@Param("entity") UserInfo entity);

    List<CollectInfoVo> listByUserId(@Param("entity") UserInfo entity);

    List<CollectInfoVo> selectByUserId(@Param("entity")UserInfo entity);
}
