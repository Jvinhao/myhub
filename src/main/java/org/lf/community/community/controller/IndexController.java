package org.lf.community.community.controller;

import org.lf.community.community.dto.PaginationDTO;
import org.lf.community.community.mapper.UserMapper;
import org.lf.community.community.model.User;
import org.lf.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model, @RequestParam(name = "page",defaultValue = "1") Integer page, @RequestParam(name = "size",defaultValue = "5") Integer size) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                String token = cookie.getValue();
                Optional<User> userOptional = userMapper.findByToken(token);
                if (userOptional.isPresent()) {
                    request.getSession().setAttribute("user", userOptional.get());
                }
                break;
            }
        }

        PaginationDTO pagination = questionService.list(page,size);
        model.addAttribute("pagination",pagination);

        return "index";
    }
}
