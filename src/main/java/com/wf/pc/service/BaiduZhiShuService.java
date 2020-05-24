package com.wf.pc.service;

import com.alibaba.fastjson.JSONArray;
import com.wf.pc.common.UrlConstant;
import com.wf.pc.utils.DataHandler;
import com.wf.pc.utils.ExcelUtil;
import com.wf.pc.utils.HttpUtil;
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

    public void getDefaultAllData(String time,HttpServletResponse response){
        //数据处理
        DataHandler dataHandler = new DataHandler(time);
        //获取数据
        JSONArray array = dataHandler.getData();
        //整合数据
        ExcelUtil.output(array,response);
    }
}
