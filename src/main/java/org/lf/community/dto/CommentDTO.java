package org.lf.community.dto;

import lombok.Data;
import org.lf.community.model.User;

@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private String content;
    private Long commentator;
    private long gmtCreate;
    private long gmtModified;
    private Integer likeCount;
    private Integer commentCount;
    private User user;

}
