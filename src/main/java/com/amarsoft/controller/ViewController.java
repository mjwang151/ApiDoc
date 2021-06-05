package com.amarsoft.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author mjwang
 * @version 1.0
 * @date 2021/4/4 21:30
 */
@Controller
@RequestMapping
@Slf4j
public class ViewController {

    @RequestMapping("test")
    public String view(Model model){
        model.addAttribute("key","abc");
        return  "test";
    }

    @RequestMapping("main")
    public String main(Model model){
        return "main";
    }

    @RequestMapping("getData")
    public ModelAndView getData(Model model,HttpServletRequest request){
        String reqparam = request.getParameter("reqparam");
        ModelAndView mo = new ModelAndView();
        mo.setViewName("TableView");
        mo.addObject("reqparam",reqparam);
        return mo;
    }

    @RequestMapping("queryView")
    public ModelAndView queryView(Model model){
        ModelAndView mo = new ModelAndView();
        mo.setViewName("query/QueryData");
        return mo;
    }
    @RequestMapping("home")
    public ModelAndView HomePage(Model model){
        ModelAndView mo = new ModelAndView();
        mo.setViewName("query/HomePage");
        return mo;
    }
    @RequestMapping("Tab")
    public ModelAndView Tab(Model model){
        ModelAndView mo = new ModelAndView();
        mo.setViewName("Tab");
        return mo;
    }
}
