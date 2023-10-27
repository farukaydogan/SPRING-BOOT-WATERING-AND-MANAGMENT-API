package com.humidty.arge.controller;

import com.humidty.arge.service.UserService;
import com.humidty.arge.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/management")
@RequiredArgsConstructor
public class AdminController {

    private final  UserService userService;
    @GetMapping()
    public String viewAdminPage(Model model) {
//        model.addAttribute("allemplist", employeeServiceImpl.getAllEmployee());
        List<User> users=userService.findAll();
        model.addAttribute("users", users);  //
        return "admin";
    }


}
