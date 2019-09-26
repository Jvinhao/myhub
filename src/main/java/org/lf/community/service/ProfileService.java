package org.lf.community.service;

import org.apache.ibatis.session.RowBounds;
import org.lf.community.dto.PaginationDTO;
import org.lf.community.dto.QuestionDTO;
import org.lf.community.mapper.QuestionMapper;
import org.lf.community.mapper.UserMapper;
import org.lf.community.model.Question;
import org.lf.community.model.QuestionExample;
import org.lf.community.model.User;
import org.lf.community.model.UserExample;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileService {

    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;


    public PaginationDTO listByUser(Long id, Integer page, Integer size) {
        Integer offset = size * (page - 1);
        PaginationDTO paginationDTO = new PaginationDTO();
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(id);
        Integer questCount = (int)questionMapper.countByExample(questionExample);

        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdEqualTo(id);
        User user = userMapper.selectByExample(userExample).get(0);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(questionExample,new RowBounds(offset,size));
        paginationDTO.setPagination(page,size,questCount);
        //paginationDTO.setTotalPage(questCount);
        for (Question question : questionList) {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }
}
