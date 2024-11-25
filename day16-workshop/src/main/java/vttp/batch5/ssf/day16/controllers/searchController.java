package vttp.batch5.ssf.day16.controllers;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import vttp.batch5.ssf.day16.models.Info;
import vttp.batch5.ssf.day16.services.searchService;

@Controller
public class searchController {
    private final Logger logger = Logger.getLogger(searchController.class.getName());
    @Autowired
    private searchService searchSvc;

    @GetMapping("/search")
    public String getGifs(@ModelAttribute("info") Info info, Model model) {
        logger.info(info.toString());
        String query = info.getQuery();
        String limit = info.getLimit();
        String rating = info.getRating();
        List<String> urls = searchSvc.getQuery(query, limit, rating);
        model.addAttribute("urlList",urls);
        model.addAttribute("query", query);
        return "gifs";
    }

}
