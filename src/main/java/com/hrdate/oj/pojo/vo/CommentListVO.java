package com.hrdate.oj.pojo.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.HashMap;

/**
 * @Author: huangrendi
 * @Date: 2022/11/23 20:00
 * @Description:
 */
@Data
public class CommentListVO {

    private IPage<CommentVO> commentList;

    private HashMap<Integer, Boolean>  commentLikeMap;
}