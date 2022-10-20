package com.tjnu.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tjnu.frame.dto.ResponseResult;
import com.tjnu.system.entity.BookInfo;
import com.tjnu.system.mapper.BookInfoMapper;
import com.tjnu.system.service.BookInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Random;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bobo
 * @since 2021-05-26
 */
@Service
public class BookInfoServiceImpl extends ServiceImpl<BookInfoMapper, BookInfo> implements BookInfoService {

    private Random random = new Random();

    @Override
    public ResponseResult listByTable(BookInfo entity) {
        return ResponseResult.table(baseMapper.countByTable(entity),baseMapper.listByTable(entity));
    }

    @Override
    public ResponseResult saveEntity(BookInfo entity) {
        List<BookInfo> existList = baseMapper.selectList(new LambdaQueryWrapper<BookInfo>()
                .eq(BookInfo::getBookName,entity.getBookName()));
        if(!CollectionUtils.isEmpty(existList)){
            return ResponseResult.error("书籍已存在",entity.getBookName());
        }
        baseMapper.insert(entity);
        return ResponseResult.success("新增成功");
    }

    @Override
    public ResponseResult updateEntity(BookInfo entity) {
        List<BookInfo> existList = baseMapper.selectList(new LambdaQueryWrapper<BookInfo>()
                .eq(BookInfo::getBookName,entity.getBookName()));
        if(!CollectionUtils.isEmpty(existList) && existList.size() > 1){
            return ResponseResult.error("数据库存在多个书籍名称",entity.getBookName());
        }else if(existList.size() == 1 && !existList.get(0).getId().equals(entity.getId())){
            return ResponseResult.error("书籍名称已存在",entity.getBookName());
        }else{
            baseMapper.updateById(entity);
        }
        return ResponseResult.success("更新成功");
    }

    @Override
    public List<BookInfo> TodayRecommendation() {
        BookInfo entity = new BookInfo();
        entity.setLimit(6);
        entity.setPage(random.nextInt(5)+1);
        List<BookInfo> bookInfos = baseMapper.listByTableImg3(entity);
        return bookInfos;
    }

    @Override
    public List<BookInfo> listByLike(String bookSort) {
        BookInfo entity = new BookInfo();
        entity.setLimit(6);
        entity.setPage(random.nextInt(5)+1);
        entity.setBookSort(bookSort);
        List<BookInfo> bookInfos = baseMapper.listByLikeImg3(entity);
        return bookInfos;
    }

    @Override
    public List<BookInfo> listByType(BookInfo entity) {
        return baseMapper.listByType(entity);
    }

    @Override
    public List<BookInfo> listBy2000(BookInfo entity) {
        return baseMapper.listBy2000(entity);
    }

    @Override
    public List<BookInfo> listByRatingSum(BookInfo entity) {
        return baseMapper.listByRatingSum(entity);
    }

    @Override
    public List<BookInfo> listByGuess(BookInfo entity) {
        String[] bookSorts = {"文学","流行","生活","科技","经管","电子书籍"};
        entity.setBookSort(bookSorts[random.nextInt(5)]);
        //System.out.println("随机获取到的类型"+entity.getBookSort());
        return baseMapper.listByType(entity);
    }

    @Override
    public BookInfo selectOneById(int id) {
        return baseMapper.selectOne(new LambdaQueryWrapper<BookInfo>().eq(BookInfo::getId,id));
    }
}
