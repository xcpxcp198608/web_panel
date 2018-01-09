package com.wiatec.panel.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author patrick
 */
@Controller
@RequestMapping("/ws")
public class PanelSocketWeb {
    private Logger logger = LoggerFactory.getLogger(PanelSocketWeb.class);

    @RequestMapping("/")
    public String start(){
        return "ws/device";
    }

    @RequestMapping("/send")
    @ResponseBody
    public String send(){
        return "ok";
    }
}
