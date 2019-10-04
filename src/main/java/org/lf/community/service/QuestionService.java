package org.lf.community.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.lf.community.dto.PaginationDTO;
import org.lf.community.dto.QuestionDTO;
import org.lf.community.exception.CustomizeCode;
import org.lf.community.exception.CustomizeException;
import org.lf.community.mapper.QuestionExtMapper;
import org.lf.community.mapper.QuestionMapper;
import org.lf.community.mapper.UserMapper;
import org.lf.community.model.Question;
import org.lf.community.model.QuestionExample;
import org.lf.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    QuestionExtMapper questionExtMapper;

    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<>();
        QuestionExample questionExample = new QuestionExample();
        Integer totalCount = (int) questionMapper.countByExample(questionExample);
        /*Integer totalPage;
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }*/
        paginationDTO.setPagination(page, size,totalCount);
        Integer offset = size * (page - 1);
        QuestionExample example = new QuestionExample();
        example.setOrderByClause("gmt_create desc");
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));

        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            //userMapper.selectByPrimaryKey(question.getId());
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);


        return paginationDTO;
    }

    public QuestionDTO getById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if(question == null) {
            throw new CustomizeException(CustomizeCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;

    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            questionMapper.insertSelective(question);
        } else {
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria().andIdEqualTo(question.getId());
            questionMapper.updateByExampleSelective(updateQuestion, questionExample);
        }
    }

    public void incView(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }

    public List<QuestionDTO> listTagsQuestion(QuestionDTO queryDTO) {
        if(StringUtils.isBlank(queryDTO.getTag())) {
            return new ArrayList<>();
        }
        String[] tags = queryDTO.getTag().split(",");
        String tagString = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(queryDTO.getId());
        question.setTag(tagString);

        List<Question> questions = questionExtMapper.selectRelated(question);
        List<QuestionDTO> questionDTOList = questions.stream().map(q -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q, questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());
        return questionDTOList;
        //return null;

    }
}
