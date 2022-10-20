package com.tjnu.system.controller;


import com.tjnu.frame.dto.ResponseResult;
import com.tjnu.system.entity.BookInfo;
import com.tjnu.system.entity.CollectInfo;
import com.tjnu.system.service.CollectInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bobo
 * @since 2021-05-26
 */
@Controller
@RequestMapping("/system/collectInfo")
public class CollectInfoController {

    @Autowired
    CollectInfoService collectInfoService;

    @RequestMapping("listByUserId")
    @ResponseBody
    public ResponseResult listByUserId(@RequestParam("page")int page,@RequestParam("limit")int limit){
        return collectInfoService.listByUserId(page,limit);
    }

    @RequestMapping("saveBookByUserId")
    @ResponseBody
    public ResponseResult saveBookByUserId(BookInfo bookInfo){
        //System.out.println("点击收藏获得的书籍信息:"+bookInfo);
        return collectInfoService.saveBookByUser(bookInfo);
    }

    @RequestMapping("deleteByCollectId")
    @ResponseBody
    public ResponseResult deleteByBookId(int id){
        collectInfoService.removeById(id);
        return ResponseResult.success("取消收藏");
    }

}
