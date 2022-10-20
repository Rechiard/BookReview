package com.tjnu.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.tjnu.frame.dto.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author bobo
 * @since 2021-05-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("book_info")
@ApiModel(value="BookInfo对象", description="")
public class BookInfo extends BaseEntity {

    @ApiModelProperty(value = "书的id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "书名")
    @TableField("book_name")
    private String bookName;

    @ApiModelProperty(value = "作者")
    @TableField("author")
    private String author;

    @ApiModelProperty(value = "出版社")
    @TableField("publishing_house")
    private String publishingHouse;

    @ApiModelProperty(value = "译者")
    @TableField("translator")
    private String translator;

    @ApiModelProperty(value = "出版时间")
    @TableField("publish_date")
    private String publishDate;

    @ApiModelProperty(value = "页数")
    @TableField("pages")
    private String pages;

    @ApiModelProperty(value = "ISBN号码")
    @TableField("ISBN")
    private String isbn;

    @ApiModelProperty(value = "价格")
    @TableField("price")
    private String price;

    @ApiModelProperty(value = "内容简介")
    @TableField("brief_introduction")
    private String briefIntroduction;

    @ApiModelProperty(value = "作者简介")
    @TableField("author_introduction")
    private String authorIntroduction;

    @ApiModelProperty(value = "评分")
    @TableField("book_score")
    private Double bookScore;

    @ApiModelProperty(value = "书籍分类")
    @TableField("book_sort")
    private String bookSort;

    @ApiModelProperty(value = "封面地址")
    @TableField("img_url")
    private String imgUrl;

    @ApiModelProperty(value = "评分人数")
    @TableField("rating_sum")
    private Integer ratingSum;

    @ApiModelProperty(value = "1-正常 2-删除")
    @TableField(value = "status", fill = FieldFill.INSERT)
    private Integer status;


}
