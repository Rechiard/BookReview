package com.tjnu.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.tjnu.frame.consts.SessionConst;
import com.tjnu.frame.dto.ResponseResult;
import com.tjnu.frame.shiro.util.ShiroUtils;
import com.tjnu.system.consts.CollectInfoConst;
import com.tjnu.system.entity.BookInfo;
import com.tjnu.system.entity.CollectInfo;
import com.tjnu.system.entity.UserInfo;
import com.tjnu.system.mapper.BookInfoMapper;
import com.tjnu.system.mapper.CollectInfoMapper;
import com.tjnu.system.mapper.UserInfoMapper;
import com.tjnu.system.service.BookInfoService;
import com.tjnu.system.service.CollectInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tjnu.system.vo.CollectInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
public class CollectInfoServiceImpl extends ServiceImpl<CollectInfoMapper, CollectInfo> implements CollectInfoService {

    @Override
    public ResponseResult listByUserId(int page,int limit) {
        UserInfo entity = (UserInfo) ShiroUtils.getSessionAttribute(SessionConst.USER_INFO_SESSION);
        if(entity == null){
            return ResponseResult.error("请先登录");
        }
        entity.setPage(page);
        entity.setLimit(limit);
        return ResponseResult.table(baseMapper.countByUserId(entity),baseMapper.listByUserId(entity));
    }

    @Override
    public ResponseResult saveBookByUser(BookInfo bookInfo) {
        UserInfo entity = (UserInfo) ShiroUtils.getSessionAttribute(SessionConst.USER_INFO_SESSION);
        if(entity == null){
            return ResponseResult.error("请先登录");
        }
        List<CollectInfo> existList = baseMapper.selectList(new LambdaQueryWrapper<CollectInfo>()
                .eq(CollectInfo::getBookId, bookInfo.getId()));
        for (CollectInfo collectInfo : existList) {
            if(collectInfo.getUserId() == entity.getId()){
                return ResponseResult.error("此书已收藏");
            }
        }
        CollectInfo collectInfo = new CollectInfo();
        collectInfo.setUserId(entity.getId()).setBookId(bookInfo.getId());
        int insert = baseMapper.insert(collectInfo);
        if(insert <= 0){
            return ResponseResult.error("收藏失败");
        }
        return ResponseResult.success("收藏成功");
    }

    @Override
    public Boolean isCollect(UserInfo userInfo,BookInfo bookInfo) {
        List<CollectInfoVo> collectInfoVos = baseMapper.selectByUserId(userInfo);
        //System.out.println("判断收藏此时的书id："+bookInfo.getId());
        for (CollectInfoVo collectInfoVo : collectInfoVos) {
            //System.out.println("collectInfoVo的id为："+collectInfoVo.getBookId());
            if(bookInfo.getId().equals(collectInfoVo.getBookId())){
                //System.out.println("此时判断成功！！！");
                return true;
            }
        }
        return false;
    }

}
