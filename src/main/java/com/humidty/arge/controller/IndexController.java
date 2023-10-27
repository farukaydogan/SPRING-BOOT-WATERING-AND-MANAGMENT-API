package com.humidty.arge.controller;

import com.humidty.arge.model.Device;
import com.humidty.arge.service.UserService;
import com.humidty.arge.model.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class IndexController {

    private final  UserService userService;
    @GetMapping()
    public String viewAdminPage(@AuthenticationPrincipal User user, Model model) {
        List<Device> devices=user.getDevices();
        model.addAttribute("devices",devices);
    //        List<User> users=userService.findAll();
    //        model.addAttribute("users", users);  //
        return "index";
    }


}
