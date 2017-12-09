package com.example.Demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by hz on 12/7/17.
 */
@Controller
public class HtmlController {

    @RequestMapping(value = "/index")
    public ModelAndView index(){
        return new ModelAndView("index");
    }

    @RequestMapping(value = "/start")
    public String start(Model model){
        model.addAttribute("name", "Dear");
        return "start";
    }
}
