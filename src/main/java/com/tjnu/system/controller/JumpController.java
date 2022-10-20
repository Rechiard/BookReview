package com.tjnu.system.controller;

import com.tjnu.frame.consts.SessionConst;
import com.tjnu.frame.shiro.util.ShiroUtils;
import com.tjnu.system.consts.BookInfoConst;
import com.tjnu.system.consts.CollectInfoConst;
import com.tjnu.system.consts.CommentInfoConst;
import com.tjnu.system.consts.UserInfoConst;
import com.tjnu.system.entity.BookInfo;
import com.tjnu.system.entity.UserInfo;
import com.tjnu.system.service.BookInfoService;
import com.tjnu.system.service.CollectInfoService;
import com.tjnu.system.service.CommentInfoService;
import com.tjnu.system.service.UserInfoService;
import com.tjnu.system.vo.CommentInfoVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class JumpController {

    @Autowired
    BookInfoService bookInfoService;
    @Autowired
    CollectInfoService collectInfoService;
    @Autowired
    CommentInfoService commentInfoService;
    @Autowired
    UserInfoService userInfoService;

    public void getSession(Model model){
        UserInfo userInfo = (UserInfo) ShiroUtils.getSessionAttribute(SessionConst.USER_INFO_SESSION);
        //System.out.println("切换了页面后，我保存的userInfo信息为："+userInfo);
        if(userInfo != null){
            model.addAttribute(UserInfoConst.NICK_NAME, userInfo.getNickName());
            model.addAttribute(UserInfoConst.USER_INFO,userInfo);
        }
    }

    public void combine(List<BookInfo> bookInfos){
        for(BookInfo bookInfo : bookInfos){
            String briefIntroduction = bookInfo.getBriefIntroduction();
            if(briefIntroduction.length() > 215){
                String substring = briefIntroduction.substring(0, 215);
                substring += "...";
                bookInfo.setBriefIntroduction(substring);
            }
        }
    }

    @RequestMapping("toLogin")
    public String toLogin(){
        return "system/login";
    }

    @RequestMapping("logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "system/login";
    }

    @RequestMapping({"toIndex","/"})
    public String toIndex(Model model){
        //保存用户信息
        getSession(model);
        List<BookInfo> bookInfos = bookInfoService.TodayRecommendation();
        model.addAttribute(BookInfoConst.BOOK_INFOS,bookInfos);
        return "system/index";
    }

    @RequestMapping("toContact")
    public String toContact(Model model){
        getSession(model);
        return "system/contact";
    }

    @RequestMapping("toForgetPassword")
    public String toForgetPassword(){
        return "system/forget-password";
    }

    @RequestMapping("toMyAccount")
    public String toMyAccount(Model model){
        getSession(model);
        return "system/my-account";
    }

    @RequestMapping("toRegister")
    public String toRegister(){
        return "system/register";
    }

    @RequestMapping("toGetPassword")
    public String toGetPassword(){
        return "system/get-password";
    }

    @RequestMapping("toProductDetails")
    public String toProductDetails(@RequestParam("id")int id, Model model){

        UserInfo userInfo = (UserInfo) ShiroUtils.getSessionAttribute(SessionConst.USER_INFO_SESSION);
        BookInfo bookInfo = bookInfoService.selectOneById(id);
        if(userInfo != null){
            model.addAttribute(UserInfoConst.NICK_NAME, userInfo.getNickName());
            model.addAttribute(UserInfoConst.USER_INFO,userInfo);
        }

        //是否收藏信息
        Boolean collect = collectInfoService.isCollect(userInfo, bookInfo);
        //System.out.println("点开页面后我接受到此用户对此书的收藏信息为："+collect);
        if(collect){
            model.addAttribute(CollectInfoConst.IS_COLLECT,true);
        }

        //评论信息
        List<CommentInfoVo> commentInfoVos = commentInfoService.listByBookId(bookInfo);
        model.addAttribute(CommentInfoConst.COMMENT_INFOS,commentInfoVos);

        //书籍信息
        List<BookInfo> bookInfos = bookInfoService.listByLike(bookInfo.getBookSort());
        model.addAttribute(BookInfoConst.BOOK_INFO,bookInfo);
        model.addAttribute(BookInfoConst.BOOK_INFOS,bookInfos);
        return "system/product-details";
    }


    @RequestMapping("toBookList")
    public String toBookList(@RequestParam("bookSort")String bookSort,@RequestParam("page")int page, Model model){
        getSession(model);
        BookInfo bookInfo = new BookInfo();
        bookInfo.setLimit(30);
        bookInfo.setPage(page);
        if(bookSort.equals("2000年度榜单")){
            List<BookInfo> bookInfos = bookInfoService.listBy2000(bookInfo);
            combine(bookInfos);
            model.addAttribute(BookInfoConst.BOOK_INFOS,bookInfos);
            model.addAttribute(BookInfoConst.BOOK_TYPE,bookSort);
            return "system/book-list";
        }else if(bookSort.equals("畅销图书")){
            List<BookInfo> bookInfos = bookInfoService.listByRatingSum(bookInfo);
            combine(bookInfos);
            model.addAttribute(BookInfoConst.BOOK_INFOS,bookInfos);
            model.addAttribute(BookInfoConst.BOOK_TYPE,bookSort);
            return "system/book-list";
        }else if(bookSort.equals("猜您喜欢")){
            List<BookInfo> bookInfos = bookInfoService.listByGuess(bookInfo);
            combine(bookInfos);
            model.addAttribute(BookInfoConst.BOOK_INFOS,bookInfos);
            model.addAttribute(BookInfoConst.BOOK_TYPE,bookSort);
            return "system/book-list";
        }
        bookInfo.setBookSort(bookSort);
        List<BookInfo> bookInfos = bookInfoService.listByType(bookInfo);
        combine(bookInfos);
        model.addAttribute(BookInfoConst.BOOK_INFOS,bookInfos);
        model.addAttribute(BookInfoConst.BOOK_TYPE,bookSort);
        return "system/book-list";
    }

    /*--------------------------------------------------------------------------*/

    @RequestMapping("toWelcome")
    public String toWelcome(Model model){
        System.out.println(userInfoService.count());
        System.out.println(bookInfoService.count());
        model.addAttribute(UserInfoConst.TOTAL_USER,userInfoService.count());
        model.addAttribute(BookInfoConst.TOTAL_BOOK,bookInfoService.count());
        return "system/backstage/welcome";
    }

    @RequestMapping("toBackIndex")
    public String toBackIndex(Model model){
        getSession(model);
        return "system/backstage/index";
    }

    @RequestMapping("logoutBackIndex")
    public String logoutBackIndex(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "system/backstage/login";
    }

    @RequestMapping("toBackLogin")
    public String toBackLogin(){
        return "system/backstage/login";
    }

    @RequestMapping("dataAnalyse1")
    public String dataAnalyse1(){
        return "system/backstage/analyse/echarts_Ajax";
    }

    @RequestMapping("dataAnalyse2")
    public String dataAnalyse2(){
        return "system/backstage/analyse/Etest";
    }

    @RequestMapping("dataAnalyse3")
    public String dataAnalyse3(){
        return "system/backstage/analyse/pillar";
    }

    @RequestMapping("data4")
    public String data4(){
        return "system/backstage/analyse/data4";
    }

    @RequestMapping("data5")
    public String data5(){
        return "system/backstage/analyse/data5";
    }

}
