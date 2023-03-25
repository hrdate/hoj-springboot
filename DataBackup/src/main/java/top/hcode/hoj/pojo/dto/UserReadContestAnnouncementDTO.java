package top.hcode.hoj.pojo.dto;

import lombok.Data;

import java.util.List;

/**
 * @Description:
 */

@Data
public class UserReadContestAnnouncementDTO {

    private Long cid;

    private List<Long> readAnnouncementList;
}