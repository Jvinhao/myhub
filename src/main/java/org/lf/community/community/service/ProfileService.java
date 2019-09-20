package org.lf.community.community.service;

import org.lf.community.community.dto.PaginationDTO;
import org.lf.community.community.dto.QuestionDTO;
import org.lf.community.community.mapper.QuestionMapper;
import org.lf.community.community.mapper.UserMapper;
import org.lf.community.community.model.Question;
import org.lf.community.community.model.User;
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


    public PaginationDTO listByUser(String accountId, Integer page, Integer size) {
        Integer offset = size * (page - 1);
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer countByAccounId = questionMapper.countByAccounId(accountId);
        User user = userMapper.findByAccountId(accountId);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        List<Question> questionList = questionMapper.findAllByAccount(accountId, offset, size);
        paginationDTO.setPagination(countByAccounId,page,size);
        if(page < 1) {
            page = 1;
        }else if(page >= paginationDTO.getTotalPage()) {
            page = paginationDTO.getTotalPage();
        }
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
