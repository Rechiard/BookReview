package com.tjnu.system.vo;

import com.tjnu.system.entity.CommentInfo;
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
public class CommentInfoVo extends CommentInfo {

    private String nickName;

}
