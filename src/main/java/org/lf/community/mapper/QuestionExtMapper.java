package org.lf.community.mapper;

import org.lf.community.model.Question;

import java.util.List;

public interface QuestionExtMapper {

    int incView(Question question);

    int incComment(Question question);

    List<Question> selectRelated(Question question);
}