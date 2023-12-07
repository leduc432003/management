package com.duc.project.controller;

import com.duc.project.model.User;
import com.duc.project.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;
    @RequestMapping("")
    public String home() {
        return "index";
    }
    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute User user, HttpSession session, Model m, HttpServletRequest request) {

        String url = request.getRequestURL().toString();
        url = url.replace(request.getServletPath(), "");
        User u = userService.saveUser(user, url);
        if (u != null) {
            session.setAttribute("msg", "Register successfully");

        } else {
            session.setAttribute("msg", "Something wrong server");
        }
        return "redirect:/register";
    }
}
