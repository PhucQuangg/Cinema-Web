package stu.edu.vn.cinema_web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    @GetMapping("/movies")
    public String movies(){
        return "home/movies";
    }
    @GetMapping("/contact")
    public String contact(){
        return "home/contact";
    }



}