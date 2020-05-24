package com.wf.pc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 页面控制跳转
 *
 */
@Controller
public class PageController {

    /**
     * 打开首页
     */
    @RequestMapping("/")
    public ModelAndView showIndex() {
        return new ModelAndView("index");
    }


    /**
     * 跳转页面 一级目录
     *
     * @param page
     * @param request
     * @return
     */
    @RequestMapping("/{page}")
    public ModelAndView showpage(@PathVariable String page, HttpServletRequest request) {
        return new ModelAndView(page);
    }

}
