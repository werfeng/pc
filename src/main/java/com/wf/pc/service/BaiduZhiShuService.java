package com.wf.pc.service;

import com.alibaba.fastjson.JSONArray;
import com.wf.pc.common.UrlConstant;
import com.wf.pc.utils.ExcelUtil;
import com.wf.pc.utils.HttpUtil;
import com.wf.pc.utils.ParseUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
public class BaiduZhiShuService {

    public Object getData(String url,String subSearch,String page){
        url = UrlConstant.BAIDUZHISHU_PREFIX_URL+url;
        if(subSearch!=null){
            url+=UrlConstant.BAIDUZHISHU_SUBCENTER_URL+subSearch;
        }
        url+=UrlConstant.BAIDUZHISHU_CENTER_URL+page+UrlConstant.BAIDUZHISHU_SUFFIX_URL;
        return HttpUtil.doGet(url);
    }

    public Object getAllData(String url,String subSearch,String page){
        url = UrlConstant.BAIDUZHISHU_PREFIX_URL+url;
        if(subSearch!=null){
            url+=UrlConstant.BAIDUZHISHU_SUBCENTER_URL+subSearch;
        }
        url+=UrlConstant.BAIDUZHISHU_CENTER_URL+page+UrlConstant.BAIDUZHISHU_SUFFIX_URL;
        return HttpUtil.doGet(url);
    }

    public void getDefaultAllData(HttpServletResponse response){
        JSONArray array = new JSONArray();
        //获取总页数
        String result = HttpUtil.doGet(UrlConstant.BAIDUZHISHU_DEFAULT_URL);
        int size = ParseUtil.getSize(result);
        for(int i=1;i<=size;i++){
            String url = UrlConstant.BAIDUZHISHU_PREFIX_URL+UrlConstant.BAIDUZHISHU_DEFAULT_WEB;
            url+=UrlConstant.BAIDUZHISHU_CENTER_URL+i+UrlConstant.BAIDUZHISHU_SUFFIX_URL;
            HttpUtil.doGet(url);
            JSONArray subArray = ParseUtil.parseBaiDuZhiShuHtml(result);
            array.addAll(subArray);
        }
        //获取网页
//        String result = HttpUtil.doGet(UrlConstant.BAIDUZHISHU_DEFAULT_URL);
        //整合数据
        ExcelUtil.output(array,response);
    }
}
