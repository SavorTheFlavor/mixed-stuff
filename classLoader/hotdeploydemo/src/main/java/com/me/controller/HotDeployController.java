package com.me.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/12/7.
 */
@RestController
public class HotDeployController {

    @RequestMapping("/hey")
    public String hey(){
        return "Hey!!!!!!HEY!HEY!HEY.....";
    }

}
    