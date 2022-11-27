package com.hrdate.oj.pojo.dto;

import com.hrdate.oj.entity.discussion.Reply;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: huangrendi
 * @Date: 2022/11/23 20:00
 * @Description:
 */
@Data
@Accessors(chain = true)
public class ReplyDTO {

    private Reply reply;

    private Integer did;

    private Integer quoteId;

    private String quoteType;
}