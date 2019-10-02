package org.lf.community.controller;

import org.lf.community.dto.CommentDTO;
import org.lf.community.dto.QuestionDTO;
import org.lf.community.enums.CommentTypeEnum;
import org.lf.community.service.CommentService;
import org.lf.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @RequestMapping("/question/{id}")
    public String question(@PathVariable("id") Long id, Model model) {
        QuestionDTO questionDTO = questionService.getById(id);
        questionService.incView(id);   //累加阅读数

        List<CommentDTO> commentDTO = commentService.list(id, CommentTypeEnum.QUESTION);

        List<QuestionDTO> tagList = questionService.listTagsQuestion(questionDTO);

        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",commentDTO);
        model.addAttribute("tagList",tagList);
        return "question";
    }


}
