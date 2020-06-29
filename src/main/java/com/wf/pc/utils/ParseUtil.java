package com.wf.pc.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wf.pc.common.UrlConstant;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParseUtil {
    public static JSONArray parseBaiDuZhiShuHtml(String str){
        Document document = Jsoup.parse(str);

        Elements parentTag = document.getElementsByClass("baidurank-list");
        //为null说明没有数据
        if(parentTag == null || parentTag.size() ==0){
            return null;
        }
        Elements elements = parentTag.get(0).getElementsByTag("tbody");
        if(elements == null || elements.size() ==0){
            return null;
        }

        Element element = elements.get(0);
        Elements img = element.getElementsByTag("tr");
        JSONArray array = new JSONArray();
        if(img!=null && img.size()>0){
            for(int i =0;i<img.size();i++){
                Elements tds = img.get(i).getElementsByTag("td");
                if(tds==null && tds.size()==0){
                    return array;
                }
                JSONObject object = new JSONObject();
                for(int j=0;j<tds.size();j++){
                    //第一行有个div class=path不需要
                    int k = j;
                    if(i==0) {
                        if (j == 0) {
                            continue;
                        } else {
                            k=j-1;
                        }
                    }
                    //如果存在a标签，取a标签值和href值，不存在就取span的值
                    //总共5行，只有第二行是span标签，其余都是a标签
                    Element td = tds.get(j);
                    Elements aTag = td.getElementsByTag("a");
                    if(aTag!=null && aTag.size()>0){
                        String text = aTag.html();
                        String href = aTag.attr("href");
                        object.put(UrlConstant.VK+k,text);
                        try {
//                            object.put(UrlConstant.VKH+k, URLEncoder.encode(href,"utf-8"));
                            //对参数值进行url编码处理，网站ip和路径不处理，？之后的参数=的之后的参数值进行处理
                            /*int paramIndex = href.indexOf("?");
                            if(paramIndex!=-1){
                                String queryString = href.substring(paramIndex + 1);
                                String[] paramStr = queryString.split("&");
                                href = href.substring(0,paramIndex+1);
                                for (String strs:paramStr) {
                                    //=号后面的值进行url编码处理
                                    int i1 = strs.indexOf("=");
                                    if(i1!=-1){
                                        href+=strs.substring(0,i1+1)+URLEncoder.encode(strs.substring(i1+1),"utf-8")+"&";
                                    }else{
                                        href+=strs+"&";
                                    }
                                }
                                //去除最后一个&
                                if(href.lastIndexOf("&")==href.length()-1){
                                    href = href.substring(0,href.length()-1);
                                }
                            }*/
                            //对参数中存在空格进行替换 %20
                            int paramIndex = href.indexOf("?");
                            if(paramIndex!=-1){
                                String queryString = href.substring(paramIndex + 1);
                                if(queryString.indexOf(" ")!=-1){
                                    href = href.substring(0,paramIndex+1)+queryString.replace(" ","%20");
                                }
                            }
                            //url 中 % 转义 %25  否则解析报错
                            href = href.replaceAll("(?i)%(?![\\da-f]{2})", "%25");
                            object.put(UrlConstant.VKH+k, href);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else{
                        Elements spanTag = td.getElementsByTag("span");
                        if(spanTag!=null && spanTag.size()>0){
                            String text = spanTag.get(0).text();
                            object.put(UrlConstant.VK+k,text);
                        }else{
                            object.put(UrlConstant.VK+k,"");
                        }
                    }
                }
                array.add(object);
            }
        }
        return array;
    }

    //获取总页数
    public static int getSize(String str){
        Document document = Jsoup.parse(str);
        Elements elements = document.getElementsByClass("baidurank-pager");
        if(elements==null || elements.size()==0){
            return 0;
        }
        Elements ul = elements.get(0).getElementsByTag("a");
        if(ul==null){
            return 0;
        }
        return ul.size();
    }

    public static void main(String[] args) {
        String doGet = HttpUtil.doGet("https://baidurank.aizhan.com/baidu/www.zhihu.com/-1/0/1/position/1/");
//        System.out.println(doGet);
//        JSONArray array = parseBaiDuZhiShuHtml(doGet);
//        array.forEach(System.out::println);
        int size = getSize(doGet);
        System.out.println(size);

    }
}
