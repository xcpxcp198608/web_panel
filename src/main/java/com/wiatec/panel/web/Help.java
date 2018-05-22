package com.wiatec.panel.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author patrick
 */
@Controller
public class Help {

    @RequestMapping(value = "/help")
    public String getHelp(){
        return "help";
    }
}
