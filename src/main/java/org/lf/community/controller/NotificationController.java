package org.lf.community.controller;

import org.lf.community.dto.NotificationDTO;
import org.lf.community.enums.NotificationEnum;
import org.lf.community.model.User;
import org.lf.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;
    @GetMapping("/notification/{id}")
    public String notification(HttpServletRequest request, Model model, @PathVariable("id")Long id) {
        User user = (User) request.getSession().getAttribute("user");
        if(user == null) {
            return "redirect:/";
        }
        NotificationDTO notificationDTO = notificationService.read(id,user);
        if(NotificationEnum.REPLY_COMMENT.getType() == notificationDTO.getType() || NotificationEnum.REPLY_QUESTION.getType() == notificationDTO.getType()) {
            return "redirect:/question/" + notificationDTO.getOuterid();
        }else {
            return "redirect:/";
        }
    }
}
