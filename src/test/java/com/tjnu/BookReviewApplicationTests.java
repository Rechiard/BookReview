package com.tjnu;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tjnu.frame.dto.ResponseResult;
import com.tjnu.frame.util.IdentifyCodeUtil;
import com.tjnu.frame.util.SendMailUtil;
import com.tjnu.system.entity.BookInfo;
import com.tjnu.system.entity.CollectInfo;
import com.tjnu.system.entity.UserInfo;
import com.tjnu.system.mapper.BookInfoMapper;
import com.tjnu.system.mapper.CollectInfoMapper;
import com.tjnu.system.mapper.UserInfoMapper;
import com.tjnu.system.service.CollectInfoService;
import com.tjnu.system.service.UserInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BookReviewApplicationTests {

    @Autowired
    UserInfoService userInfoService;
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    BookInfoMapper bookInfoMapper;
    @Autowired
    CollectInfoMapper collectInfoMapper;

    @Test
    void contextLoads() {
    }

}
