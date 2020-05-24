package com.wf.pc.controller;

import com.wf.pc.service.BaiduZhiShuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
public class BaiDuZhiShuController {

    @Autowired
    private BaiduZhiShuService baiduZhiShuService;

    @RequestMapping("BaiDuZhiShu")
    public void getDefaultAllData(HttpServletResponse response){
        baiduZhiShuService.getDefaultAllData(response);
    }
}
