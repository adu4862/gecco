package com.geccocrawler.gecco.pipeline;

import com.alibaba.fastjson.JSON;
import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.spider.SpiderBean;
import org.apache.http.util.TextUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@PipelineName("consolePipeline1")
public class ConsolePipeline1 implements Pipeline<SpiderBean> {

    @Override
    public void process(SpiderBean bean) {
        System.out.println(JSON.toJSONString(bean));
        MovieDetail movieDetail = JSON.parseObject(JSON.toJSONString(bean), MovieDetail.class);
//        String detail = movieDetail.getDetail();
//        String s1 = "onclick=\"javascript:dUrlAct(this,'clickDurl','"+movieDetail.getCode()+"');\"";
//        String s = detail.replace(s1, "");
//        s = s.replace("<a target=\"_blank\" href=\"http://www.pniao.com/About/downHelp\">图文教程?</a>", "");
//        movieDetail.setDetail(s);
        if (!TextUtils.isEmpty(movieDetail.getTitle())) {

            writeToDb(movieDetail);
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
//			sql = "create table student(NO char(20),name varchar(20),primary key(NO))";
//			int result = stmt.executeUpdate(sql);// executeUpdate语句会返回一个受影响的行数，如果返回-1就没有成功
//			if (result != -1) {
//				System.out.println("创建数据表成功");
//				sql = "insert into student(NO,name) values('2012001','陶伟基')";
//				result = stmt.executeUpdate(sql);
            sql = "insert into tb_movie2(code,title) values('"+bean.getCode()+"','"+bean.getTitle()+"')";
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
