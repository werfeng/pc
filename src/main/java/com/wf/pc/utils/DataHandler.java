package com.wf.pc.utils;

import com.alibaba.fastjson.JSONArray;
import com.wf.pc.common.UrlConstant;
import com.wf.pc.config.websocket.WebsocketServer;
import fr.opensagres.xdocreport.core.utils.StringUtils;

import java.io.IOException;

public final class DataHandler {
    private String time;
    private int count;
    private JSONArray array = new JSONArray();
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public DataHandler(String time) {
        this.time = time;
    }

    public JSONArray getData(){
        //获取总页数
        String result = HttpUtil.doGet(UrlConstant.BAIDUZHISHU_DEFAULT_URL);
        int size = ParseUtil.getSize(result);
        //获取wesocket对象
        WebsocketServer websocketServer = WebsocketServer.getIndex(time);
        //是否发送消息
        boolean flag = false;
        if(StringUtils.isNotEmpty(time) && websocketServer!=null){
            flag=true;
        }
        for(int i=1;i<=size;i++){
            try {
                //网站限制一秒爬一次
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String url = UrlConstant.BAIDUZHISHU_PREFIX_URL+UrlConstant.BAIDUZHISHU_DEFAULT_WEB;
            url+=UrlConstant.BAIDUZHISHU_SUBCENTER_URL+UrlConstant.BAIDUZHISHU_CENTER_URL+i+UrlConstant.BAIDUZHISHU_SUFFIX_URL;
            String pageInfo = HttpUtil.doGet(url);
            if(pageInfo.contains("您的查询太频繁了,请稍后查询")){
                //请求过于频繁重新再来一次
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("请求频繁，重新请求一次");
                i--;
                continue;
            }
            JSONArray subArray = ParseUtil.parseBaiDuZhiShuHtml(pageInfo);
            //websocket传输数据
            if(flag){
                try {
                    websocketServer.sendMessage("链接地址："+url+"，爬取数量："+subArray.size());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("链接地址："+url+"，爬取数量："+subArray.size());
            if(subArray!=null){
                array.addAll(subArray);
            }
        }
        return array;
    }
}
