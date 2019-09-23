package org.lf.community.controller;

import org.lf.community.dto.PaginationDTO;
import org.lf.community.mapper.UserMapper;
import org.lf.community.model.User;
import org.lf.community.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    ProfileService profileService;
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable("action") String action, Model model, HttpServletRequest request, @RequestParam(name = "page",defaultValue = "1") Integer page, @RequestParam(name = "size",defaultValue = "5") Integer size) {
        Object userObject = request.getSession().getAttribute("user");
        User user = (User)userObject;
        if ("questions".equals(action)) {
            model.addAttribute("section","questions");
            model.addAttribute("selectionName","我的提问");
        }else if("replies".equals(action)) {
            model.addAttribute("section","replies");
            model.addAttribute("selectionName","最新回复");
        }

        PaginationDTO paginationDTO = profileService.listByUser(user.getId(),page,size);
        model.addAttribute("pagination",paginationDTO);

        return "profile";
    }
}
