package org.lf.community.mapper;

import org.lf.community.model.Question;

public interface QuestionExtMapper {

    int incView(Question question);

    int incComment(Question question);
}