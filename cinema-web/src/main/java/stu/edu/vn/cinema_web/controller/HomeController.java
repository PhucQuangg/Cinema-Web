package stu.edu.vn.cinema_web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {


    @GetMapping("/home")
    public String home(){
        return "home/index";
    }
    @GetMapping("/about")
    public String about(){
        return "home/about";
    }

    @GetMapping("/contact")
    public String contact(){
        return "home/contact";
    }




}