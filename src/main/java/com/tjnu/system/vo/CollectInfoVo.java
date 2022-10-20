package com.tjnu.system.vo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.tjnu.system.entity.CollectInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author bobo
 * @date 2021/05/30
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class CollectInfoVo extends CollectInfo {

    private String bookName;

    private String author;

    private String publishingHouse;

    private String translator;

    private String publishDate;

    private String pages;

    private String isbn;

    private String price;

    private Double bookScore;

    private String bookSort;

    private String imgUrl;

    private Integer ratingSum;
}
