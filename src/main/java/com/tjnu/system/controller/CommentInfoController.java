package com.tjnu.system.controller;


import com.tjnu.frame.dto.ResponseResult;
import com.tjnu.system.entity.CommentInfo;
import com.tjnu.system.service.CommentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
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
@RequestMapping("/system/commentInfo")
public class CommentInfoController {

    @Autowired
    CommentInfoService commentInfoService;

    @RequestMapping("addCommentByBookId")
    @ResponseBody
    public ResponseResult addCommentByBookId(CommentInfo commentInfo){
        return commentInfoService.addCommentByBookId(commentInfo);
    }

}
