package com.geccocrawler.gecco.demo.ajax;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.annotation.*;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;
import com.geccocrawler.gecco.utils.ResourceCheckUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Gecco(matchUrl = "http://www.pniao.com/Mdown/ajax_downUrls/{code}", pipelines = {"consolePipeline", "MoviePipeline"})
public class MovieAjaxDetail implements HtmlBean {

    private static final long serialVersionUID = -377053120283382723L;

    @RequestParameter
    private String code;

//	@Ajax(url="https://p.3.cn/prices/get?type=1&pdtk=&pdbp=0&skuid=J_{code}")
//	private JDPrice price;

    @Text
    @HtmlField(cssPath = "div.downUrlList.dUrlFlag a[title] ")
    private String title;

    @Text
    @HtmlField(cssPath = "")
    private String password;
//
//    @Ajax(url = "http://www.pniao.com/Mdown/ajax_downUrls/{code}")
//    private String detail;
//	private JDad jdAd;		oh6qmmpn7mh4v1vup1qq4eoje6
    //16270cad97d169
//    @Href
    //class=  loadDownUrl  "eachUserDown sharerFlag2"class="mainBox downUrlDiv"
    @HtmlField(cssPath = "div.eachUserDown.sharerFlag2")
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

//        getMovieFromTTvip();
//        遍历数据库删除无效地址
//        deleteInvalidAddr();
        getMovieFromPniao();


    }

    /**
     * 爬取胖鸟
     */
    private static void getMovieFromPniao() {
        List<HttpRequest> sortRequests = new ArrayList<>();
        for (int i = 319; i < 49999; i++) {
            String url = "http://www.pniao.com/Mdown/ajax_downUrls/" + i + "";
//			String url = "http://www.8vdy.com/view/index" + i + ".html";
            //http://www.zaixiantt.com/index.php/video/82275.html//下次64184-82217
            //UM_distinctid=16270cad97d169-09cec1ea575459-b34356b-1fa400-16270cad97ec69;
            // CNZZDATA1273184827=580527801-1522305984-null%7C1522305984;
            // PHPSESSID=oh6qmmpn7mh4v1vup1qq4eoje6;
            // Hm_lvt_a8bf1ff34b8beab2a6e10ae8a073e5ac=1522310765,1522632073,1522633080;
            // USER_VIEWMODE=mobile;
            // Hm_lpvt_a8bf1ff34b8beab2a6e10ae8a073e5ac=1522634584
            HttpRequest request = new HttpGetRequest(url);
            request.setCharset("GBK");
            request.addCookie("UM_distinctid","16270cad97d169-09cec1ea575459-b34356b-1fa400-16270cad97ec69");
            request.addCookie("PHPSESSID","oh6qmmpn7mh4v1vup1qq4eoje6");
            request.addCookie("Hm_lvt_a8bf1ff34b8beab2a6e10ae8a073e5ac","1522310765,1522632073,1522633080");
            request.addCookie("USER_VIEWMODE","mobile");
            request.addCookie("Hm_lpvt_a8bf1ff34b8beab2a6e10ae8a073e5ac","1522634584");
            request.addHeader("Referer","http://www.pniao.com/Mov/one/1.html");
            request.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
            request.addHeader("X-Requested-With","XMLHttpRequest");
            sortRequests.add(request);
        }
        GeccoEngine.create()
                .classpath("com.geccocrawler.gecco.demo.ajax")
                //开始抓取的页面地址
                .start(sortRequests)
                //开启几个爬虫线程
                .thread(40)
                //单个爬虫每次抓取完一个请求后的间隔时间
                .interval(4000)
                .start();
    }

    /**
     * 爬取天天在线
     */
    private static void getMovieFromTTvip() {
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

    /**
     * 遍历数据库删除无效地址
     */
    private static void deleteInvalidAddr() {
        Connection conn = null;
        String sql;
        // MySQL的JDBC URL编写方式：jdbc:mysql://主机名称：连接端口/数据库的名称?参数=值
        // 避免中文乱码要指定useUnicode和characterEncoding
        // 执行数据库操作之前要在数据库管理系统上创建一个数据库，名字自己定，
        // 下面语句之前就要先创建javademo数据库
        String url = "jdbc:mysql://localhost:3306/Movie_db?"
                + "user=root&password=root&useUnicode=true";

        try {
            // 之所以要使用下面这条语句，是因为要使用MySQL的驱动，所以我们要把它驱动起来，
            // 可以通过Class.forName把它加载进去，也可以通过初始化来驱动起来，下面三种形式都可以
            Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
            // or:
            // com.mysql.jdbc.Driver driver = new com.mysql.jdbc.Driver();
            // or：
            // new com.mysql.jdbc.Driver();

            System.out.println("成功加载MySQL驱动程序");
            // 一个Connection代表一个数据库连接
            conn = DriverManager.getConnection(url);
            // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
            Statement stmt = conn.createStatement();
            sql = "SELECT *FROM tb_movie";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                String string = rs.getString(2);
                int i1 = string.indexOf("密码");
                if (i1 != -1) {

                    string = string.substring(0, i1);
                }
                if (string.contains("http://pan.baidu.com")) {
                    System.out.println(string);
                    int i = ResourceCheckUtil.isExistResource(string,
                            "baidu");
                    if (i == 0) {
                        System.out
                                .println(rs.getString(1) + "\t" + string + "\t" + rs.getString(3));// 入如果返回的是int类型可以用getInt()

                        sql = "DELETE FROM tb_movie WHERE code=" + rs.getString(1);
                        stmt.executeUpdate(sql);
                    }
                }
            }
//			}
        } catch (SQLException e) {
            System.out.println("MySQL操作错误");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
