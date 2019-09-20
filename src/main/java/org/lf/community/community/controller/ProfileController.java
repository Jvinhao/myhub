package org.lf.community.community.controller;

import org.lf.community.community.dto.PaginationDTO;
import org.lf.community.community.mapper.UserMapper;
import org.lf.community.community.model.User;
import org.lf.community.community.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class ProfileController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    ProfileService profileService;
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable("action") String action, Model model, HttpServletRequest request, @RequestParam(name = "page",defaultValue = "1") Integer page, @RequestParam(name = "size",defaultValue = "5") Integer size) {
        Cookie[] cookies = request.getCookies();
        Optional<User> userOptional = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                String token = cookie.getValue();
                 userOptional = userMapper.findByToken(token);
                if (userOptional.isPresent()) {
                    request.getSession().setAttribute("user", userOptional.get());
                }
                break;
            }
        }

        if ("questions".equals(action)) {
            model.addAttribute("section","questions");
            model.addAttribute("selectionName","我的提问");
        }else if("replies".equals(action)) {
            model.addAttribute("section","replies");
            model.addAttribute("selectionName","最新回复");
        }

        PaginationDTO paginationDTO = profileService.listByUser(userOptional.get().getAccountId(),page,size);
        model.addAttribute("pagination",paginationDTO);

        return "profile";
    }
}
