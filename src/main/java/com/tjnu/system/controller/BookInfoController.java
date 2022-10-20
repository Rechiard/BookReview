package com.tjnu.system.controller;


import com.tjnu.frame.dto.ResponseResult;
import com.tjnu.system.entity.BookInfo;
import com.tjnu.system.service.BookInfoService;
import com.tjnu.system.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bobo
 * @since 2021-05-26
 */
@Controller
@RequestMapping("/system/bookInfo")
public class BookInfoController {

    @Autowired
    BookInfoService bookInfoService;

    @RequestMapping("init")
    public String init(){
        return "system/backstage/book_Info_list";
    }

    @RequestMapping("listByTable")
    @ResponseBody
    public ResponseResult listByTable(BookInfo entity){
        return bookInfoService.listByTable(entity);
    }

    @RequestMapping("save")
    @ResponseBody
    public ResponseResult save(BookInfo entity){
        return bookInfoService.saveEntity(entity);
    }

    @RequestMapping("updateById")
    @ResponseBody
    public ResponseResult updateById(BookInfo entity){
        return bookInfoService.updateEntity(entity);
    }


    @RequestMapping("deleteById")
    @ResponseBody
    public ResponseResult deleteById(int id){
        bookInfoService.removeById(id);
        return ResponseResult.success("删除成功");
    }

}
