package com.geccocrawler.gecco.demo.sogouwx;

import java.util.List;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.RequestParameter;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.spider.HtmlBean;

@Gecco(matchUrl = "http://weixin.sogou.com/weixin?type=2&query={keyword}", pipelines = {"consolePipeline"})
public class SogouWX implements HtmlBean {

    private static final long serialVersionUID = 7504646787612579665L;

    @RequestParameter
    private String keyword;

    @HtmlField(cssPath = ".results .wx-rb")
    private List<WeiXin> weixins;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<WeiXin> getWeixins() {
        return weixins;
    }

    public void setWeixins(List<WeiXin> weixins) {
        this.weixins = weixins;
    }

    public static void main(String[] args) {

//		Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8
//Accept-Encoding: gzip, deflate
//Accept-Language: zh-CN,zh;q=0.9
//Cache-Control: max-age=0
//Connection: keep-alive
//Cookie: CXID=752145E9C7DFF6D75E97FF123D044DA6;
// SUID=879968DA5B68860A5A501D9200069394;
// SUV=00A44ADADEF01E615AA647F46FD45242;
// ad=elllllllll2z$tSLlllllV$rVDtlllllTslWYyllll9lllll9Vxlw@@@@@@@@@@@;
// ABTEST=0|1521184680|v1;
// SNUID=6E10FED00F0A698C6784C9700FC28CFA;
// IPLOC=CN4301;
// JSESSIONID=aaaGQQ3-ViTnV7Y6cBOiw
//Host: weixin.sogou.com
//Upgrade-Insecure-Requests: 1
//User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.162 Safari/537.36

        HttpGetRequest start = new HttpGetRequest("http://weixin.sogou.com/weixin?type=2&query=%E6%B7%B1%E5%9C%B3");
        start.addCookie("SUID","879968DA5B68860A5A501D9200069394");
        start.addCookie("SUV","00A44ADADEF01E615AA647F46FD45242");
        start.addCookie("ad","elllllllll2z$tSLlllllV$rVDtlllllTslWYyllll9lllll9Vxlw@@@@@@@@@@@");
        start.addCookie("ABTEST","0|1521184680|v1");
        start.addCookie("SNUID","6E10FED00F0A698C6784C9700FC28CFA");
        start.addCookie("IPLOC","CN4301");
        start.addCookie("JSESSIONID","aaaGQQ3-ViTnV7Y6cBOiw");
        start.addCookie("","aaaGQQ3-ViTnV7Y6cBOiw");
        start.addCookie("SNUID", "1D22392EF4F6C4A92076C208F4DE3AAB");
        start.addCookie("SUID", "EED1CDDA6B20900A00000000570E1872");
        start.addCookie("IPLOC", "CN1100");
        //start.addCookie("SUV", "1460541527037365");
        start.addHeader("Host", "weixin.sogou.com");
        start.addHeader("Upgrade-Insecure-Requests", "1");
        start.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        start.addHeader("Accept-Encoding", "gzip, deflate");
        start.addHeader("Cache-Control", "max-age=0");
        GeccoEngine.create()
                .classpath("com.geccocrawler.gecco.demo.sogouwx")
                .start(start)
                //.start("http://mp.weixin.qq.com/s?__biz=MzAwMjIyODIwNA==&mid=2650194319&idx=7&sn=5fbba7eb7f393508461468ea5c412ba5&3rd=MzA3MDU4NTYzMw==&scene=6")
                .interval(5000)
                .run();
    }
}
