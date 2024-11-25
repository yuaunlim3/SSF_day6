package vttp.batch5.ssf.day16.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vttp.batch5.ssf.day16.models.Info;

@Controller
@RequestMapping({"/", "/index"})
public class indexController {
    @GetMapping
    public String start(Model model){
        model.addAttribute("info", new Info());
        return "index";
    }
    
}
