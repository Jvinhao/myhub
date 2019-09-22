package org.lf.community.community.controller;

import org.lf.community.community.mapper.QuestionMapper;
import org.lf.community.community.mapper.UserMapper;
import org.lf.community.community.model.Question;
import org.lf.community.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;

    @RequestMapping("/publish")
    public String publish() {
        return "/publish";
    }

    @RequestMapping(value = "/publish",method = RequestMethod.POST)
    public String doPublish(@RequestParam("title") String title,
                            @RequestParam("description") String description,
                            @RequestParam("tag") String tag,
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
        question.setCreator(user.getAccountId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());

        questionMapper.create(question);


        return "redirect:/";
    }
}
