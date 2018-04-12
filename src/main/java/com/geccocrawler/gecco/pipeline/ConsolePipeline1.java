package com.geccocrawler.gecco.pipeline;

import com.alibaba.fastjson.JSON;
import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.spider.SpiderBean;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FetchRet;
import com.qiniu.util.Auth;
import org.apache.http.util.TextUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@PipelineName("consolePipeline1")
public class ConsolePipeline1 implements Pipeline<SpiderBean> {

    public static final String ACCESS_KEY = "YrFo8wwXHr1G2T150slBn5pHd-adC7o91UZHlgYU"; // 你的access_key
    public static final String SECRET_KEY = "fiUUq52QQRMBwTJkZfUb1KYcF6d6FFTrHOn78_Pr"; // 你的secret_key
    public static final String BUCKET_NAME = "solo"; // 你的secret_key

    @Override
    public void process(SpiderBean bean) {
        System.out.println(JSON.toJSONString(bean));
        MovieDetail movieDetail = JSON.parseObject(JSON.toJSONString(bean), MovieDetail.class);
//        String detail = movieDetail.getDetail();
//        String s1 = "onclick=\"javascript:dUrlAct(this,'clickDurl','"+movieDetail.getCode()+"');\"";
//        String s = detail.replace(s1, "");
//        s = s.replace("<a target=\"_blank\" href=\"http://www.pniao.com/About/downHelp\">图文教程?</a>", "");
//        movieDetail.setDetail(s);

        String movInfoOuter = movieDetail.getMovInfoOuter();
        String descb = movieDetail.getDescb();
        String title = movieDetail.getTitle();
        title = getChinese(title);
        descb = getChinese(descb);
        descb.replace("'", "");
        movieDetail.setDescb(descb);
        movieDetail.setTitle(title.replace("'", ""));

        movInfoOuter = movInfoOuter.replace("http://www.pniao.com/Mov/tag/director/", "");
        movInfoOuter = movInfoOuter.replace("http://www.pniao.com/Mov/tag/actors/", "");
        movInfoOuter = movInfoOuter.replace("http://www.pniao.com/Mov/tag/sorts/", "");
        movInfoOuter = movInfoOuter.replace("http://www.pniao.com/Mov/tag/country/", "");
        movInfoOuter = movInfoOuter.replace("http://www.pniao.com/Mov/tag/year/", "");
        movInfoOuter = movInfoOuter.replace("'", "");
//        http://www.pniao.com/Mov/one/26462.html
        movInfoOuter = movInfoOuter.replace( "http://www.pniao.com/Mov/one/" + movieDetail.getCode() + ".html", "http://www.changs1992.cn/wechat/web?code="+ movieDetail.getCode());
        movieDetail.setMovInfoOuter(movInfoOuter);

        if (!TextUtils.isEmpty(title)) {

            writeToDb(movieDetail);
//            uploadImg(movieDetail);
        }

    }

    public static String getChinese(String paramValue) {
        String regex = "([\u4e00-\u9fa5]+)";
        String str = "";
        Matcher matcher = Pattern.compile(regex).matcher(paramValue);
        while (matcher.find()) {
            str+= matcher.group(0);
        }
        return str;
    }



    private void uploadImg(MovieDetail movieDetail) {
        String from = movieDetail.getImg1();

        //获取到 Access Key 和 Secret Key 之后，您可以按照如下方式进行密钥配置
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

//获取空间管理器
        Configuration cfg = new Configuration(Zone.zone1());
//        configuration.
        BucketManager bucketManager = new BucketManager(auth,cfg);
        try {

            // 要求url可公网正常访问BucketManager.fetch(url, bucketName, key);
            // @param url 网络上一个资源文件的URL
            // @param bucketName 空间名称
            // @param key 空间内文件的key[唯一的]
            FetchRet putret = bucketManager.fetch(from, BUCKET_NAME, movieDetail.getCode());

            System.out.println(putret.key);
            System.out.println("succeed upload image");
        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }

    private void writeToDb(MovieDetail bean) {
        Connection conn = null;
        String sql;
        // MySQL的JDBC URL编写方式：jdbc:mysql://主机名称：连接端口/数据库的名称?参数=值
        // 避免中文乱码要指定useUnicode和characterEncoding
        // 执行数据库操作之前要在数据库管理系统上创建一个数据库，名字自己定，
        // 下面语句之前就要先创建javademo数据库
        String url = "jdbc:mysql://localhost:3306/Movie_db?"
                + "user=root&password=root&useUnicode=true&characterEncoding=UTF-8";

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
//			sql = "create table student(NO char(20),name varchar(20),primary key(NO))";
//			int result = stmt.executeUpdate(sql);// executeUpdate语句会返回一个受影响的行数，如果返回-1就没有成功
//			if (result != -1) {
//				System.out.println("创建数据表成功");
//				sql = "insert into student(NO,name) values('2012001','陶伟基')";
//				result = stmt.executeUpdate(sql);
            sql = "insert into tb_movie2_copy(code,title, descb) values('"+bean.getCode()+"','"+bean.getTitle()+"','"+bean.getDescb()+"')";
            int result1 = stmt.executeUpdate(sql);
            sql = "insert into tb_movie_movinfoouter(code,movInfoOuter) values('"+bean.getCode()+"','"+bean.getMovInfoOuter()+"')";
            int result = stmt.executeUpdate(sql);

//            sql = "select * from tb_movie";
//            ResultSet rs = stmt.executeQuery(sql);// executeQuery会返回结果的集合，否则返回空值
//            System.out.println("code\t地址\t标题");
//            while (rs.next()) {
//                System.out
//                        .println(rs.getString(1) + "\t" + rs.getString(2)+ "\t" + rs.getString(3));// 入如果返回的是int类型可以用getInt()
//            }
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
