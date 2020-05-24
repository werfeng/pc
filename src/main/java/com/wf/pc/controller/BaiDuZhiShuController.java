package com.wf.pc.controller;

import com.wf.pc.service.BaiduZhiShuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
public class BaiDuZhiShuController {

    @Autowired
    private BaiduZhiShuService baiduZhiShuService;

    @RequestMapping("BaiDuZhiShu/{time}")
    public void getDefaultAllData(@PathVariable("time") String time, HttpServletResponse response){
        baiduZhiShuService.getDefaultAllData(time,response);
    }
}
