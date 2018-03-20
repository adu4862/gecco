package com.geccocrawler.gecco.demo.ajax;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.annotation.*;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;

import java.util.ArrayList;
import java.util.List;

@Gecco(matchUrl = "http://www.zaixiantt.com/index.php/video/{code}.html", pipelines = {"consolePipeline", "MoviePipeline"})
public class MovieDetail implements HtmlBean {

    private static final long serialVersionUID = -377053120283382723L;

    @RequestParameter
    private String code;

//	@Ajax(url="https://p.3.cn/prices/get?type=1&pdtk=&pdbp=0&skuid=J_{code}")
//	private JDPrice price;

    @Text
    @HtmlField(cssPath = "section div div div div")
    private String title;

    @Text
    @HtmlField(cssPath = "section div div div div div div")
    private String password;

    //	@Ajax(url="https://cd.jd.com/promotion/v2?skuId={code}&area=1_2805_2855_0&cat=737%2C794%2C798")
//	private JDad jdAd;
//    @Href
    @HtmlField(cssPath = "div.downinfo div.downloadurl ")
    private String detail;

    @Image(download = "d:/gecco/jd/img")
    @HtmlField(cssPath = "#spec-n1 img")
    private String image;

//	public JDPrice getPrice() {
//		return price;
//	}
//
//	public void setPrice(JDPrice price) {
//		this.price = price;
//	}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

//	public JDad getJdAd() {
//		return jdAd;
//	}
//
//	public void setJdAd(JDad jdAd) {
//		this.jdAd = jdAd;
//	}


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public static void main(String[] args) throws Exception {
//		HttpRequest request = new HttpGetRequest("http://www.8vdy.com/view/index30032.html");
//		request.setCharset("GBK");
        List<HttpRequest> sortRequests = new ArrayList<>();
        for (int i = 89998; i < 99999; i++) {
            String url = "http://www.zaixiantt.com/index.php/video/" + i + ".html";
//			String url = "http://www.8vdy.com/view/index" + i + ".html";
            //http://www.zaixiantt.com/index.php/video/82275.html//下次64184-82217
            HttpRequest request = new HttpGetRequest(url);
            request.setCharset("GBK");
            sortRequests.add(request);
        }
        GeccoEngine.create()
                .classpath("com.geccocrawler.gecco.demo.ajax")
                //开始抓取的页面地址
                .start(sortRequests)
                //开启几个爬虫线程
                .thread(30)
                //单个爬虫每次抓取完一个请求后的间隔时间
                .interval(3000)
                .start();
    }
}
