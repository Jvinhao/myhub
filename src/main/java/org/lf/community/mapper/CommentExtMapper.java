package org.lf.community.mapper;

import org.lf.community.model.Comment;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}