package org.lf.community.controller;

import org.lf.community.dto.QuestionDTO;
import org.lf.community.mapper.UserMapper;
import org.lf.community.model.Question;
import org.lf.community.model.User;
import org.lf.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {



    @Autowired
    UserMapper userMapper;

    @Autowired
    QuestionService questionService;

    @RequestMapping("/publish")
    public String publish() {
        return "/publish";
    }

    @RequestMapping(value = "/publish",method = RequestMethod.POST)
    public String doPublish(@RequestParam("title") String title,
                            @RequestParam("description") String description,
                            @RequestParam("tag") String tag,
                            @RequestParam(value = "id",required = false) Long id,
                            HttpServletRequest request,
                            Model model) {
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if(title == null || title.equals("")) {
            model.addAttribute("error","标题不能为空");
            return "/publish";
        }
        if(description == null || description.equals("")) {
            model.addAttribute("error","问题补充不能为空");
            return "/publish";
        }
        if(tag == null || tag.equals("")) {
            model.addAttribute("error","标签不能为空");
            return "/publish";
        }
        User user = (User) request.getSession().getAttribute("user");
        if(user == null) {
            model.addAttribute("error","用户未登录");
            return "/publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        question.setId(id);

        questionService.createOrUpdate(question);

        return "redirect:/";
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable("id") Long id,Model model) {
        QuestionDTO question = questionService.getById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());
        return "publish";
    }
}
