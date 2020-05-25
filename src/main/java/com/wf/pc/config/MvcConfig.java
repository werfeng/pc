//package com.wf.pc.config;
//
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
///**
// * springboot静态资源访问
// */
//public class MvcConfig extends WebMvcConfigurerAdapter {
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        ResourceHandlerRegistration resourceHandlerRegistration = registry.addResourceHandler("/static/**");
//        resourceHandlerRegistration.addResourceLocations("classpath:/static/");
//    }
//}
