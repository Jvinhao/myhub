package org.lf.community.controller;

import org.lf.community.dto.AccessTokenDTO;
import org.lf.community.dto.GithubUser;
import org.lf.community.mapper.UserMapper;
import org.lf.community.model.User;
import org.lf.community.provider.GithubProvider;
import org.lf.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

    @Autowired
    GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code,
                           @RequestParam("state") String state, HttpServletRequest request, HttpServletResponse response) {
        //调用GitHub 的API
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getHubUser(accessToken);
        //System.out.println(hubUser.getName());  验证
        if (githubUser != null) {
            User user = new User();
            user.setName(githubUser.getName());
            user.setToken(UUID.randomUUID().toString());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            //user.setAvatarUrl(githubUser.getAvatarUrl());
            user.setBio(githubUser.getBio());
            userService.createOrUpdate(user);
            //userMapper.insert(user);   //代替session的存储
            //设置cookie
            response.addCookie(new Cookie("token", user.getToken()));

            //request.getSession().setAttribute("user", githubUser);
            return "redirect:/";
        } else {
            return "redirect:/";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response) {
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/";
    }
}
