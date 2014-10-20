package com.swords.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExpansionController {

    @RequestMapping("/expansions")
    public String index() {
        return "expansions";
    }
}
