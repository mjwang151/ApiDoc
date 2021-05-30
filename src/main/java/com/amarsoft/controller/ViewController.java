package com.amarsoft.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author mjwang
 * @version 1.0
 * @date 2021/4/4 21:30
 */
@Controller
@RequestMapping
public class ViewController {

    @RequestMapping("view")
    public String view(Model model){
        model.addAttribute("key","abc");
        return  "test";
    }

    @RequestMapping("main")
    public String main(Model model){
        return "main";
    }
}
