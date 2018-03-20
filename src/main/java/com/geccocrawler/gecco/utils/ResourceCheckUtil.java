package com.geccocrawler.gecco.utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * 资源校验工具
 *
 * @author hui
 *
 */
public class ResourceCheckUtil {
    private static Map<String, String[]> rules;
    static {
        loadRule();
    }

    /**
     * 加载规则库
     */
    public static void loadRule() {
        try {
            InputStream in = ResourceCheckUtil.class.getClassLoader()
                    .getResourceAsStream("rule.properties");
            Properties p = new Properties();
            p.load(in);
            Set<Object> keys = p.keySet();
            Iterator<Object> iterator = keys.iterator();
            String key = null;
            String value = null;
            String[] rule = null;
            rules = new HashMap<String, String[]>();
            while (iterator.hasNext()) {
                key = (String) iterator.next();
                value = (String) p.get(key);
                rule = value.split("\\|");
                rules.put(key, rule);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String httpRequest(String url) {
        try {
            URL u = new URL(url);
            Random random = new Random();
            HttpURLConnection connection = (HttpURLConnection) u
                    .openConnection();
            connection.setConnectTimeout(3000);//3秒超时
            connection.setReadTimeout(3000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("GET");

            String[] user_agents = {
                    "Mozilla/5.0 (Windows; U; Windows NT 5.1; it; rv:1.8.1.11) Gecko/20071127 Firefox/2.0.0.11",
                    "Opera/9.25 (Windows NT 5.1; U; en)",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 1.1.4322; .NET CLR 2.0.50727)",
                    "Mozilla/5.0 (compatible; Konqueror/3.5; Linux) KHTML/3.5.5 (like Gecko) (Kubuntu)",
                    "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.8.0.12) Gecko/20070731 Ubuntu/dapper-security Firefox/1.5.0.12",
                    "Lynx/2.8.5rel.1 libwww-FM/2.14 SSL-MM/1.4.1 GNUTLS/1.2.9",
                    "Mozilla/5.0 (X11; Linux i686) AppleWebKit/535.7 (KHTML, like Gecko) Ubuntu/11.04 Chromium/16.0.912.77 Chrome/16.0.912.77 Safari/535.7",
                    "Mozilla/5.0 (X11; Ubuntu; Linux i686; rv:10.0) Gecko/20100101 Firefox/10.0 "
            };
            int index=random.nextInt(7);
            /*connection.setRequestProperty("Content-Type",
                    "text/html;charset=UTF-8");*/
            connection.setRequestProperty("User-Agent",user_agents[index]);
            /*connection.setRequestProperty("Accept-Encoding","gzip, deflate, sdch");
            connection.setRequestProperty("Accept-Language","zh-CN,zh;q=0.8");
            connection.setRequestProperty("Connection","keep-alive");
            connection.setRequestProperty("Host","pan.baidu.com");
            connection.setRequestProperty("Cookie","");
            connection.setRequestProperty("Upgrade-Insecure-Requests","1");*/
            InputStream in = connection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(in,
                    "utf-8"));
            StringBuffer sb = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

//    @Test
//    public void test7() throws Exception {
//        System.out.println(isExistResource("http://pan.baidu.com/s/1jGjBmyq",
//                "baidu"));
//        System.out.println(isExistResource("http://pan.baidu.com/s/1jGjBmyqa",
//                "baidu"));
//
//        System.out.println(isExistResource("http://yunpan.cn/cQx6e6xv38jTd","360"));
//        System.out.println(isExistResource("http://yunpan.cn/cQx6e6xv38jTdd",
//                "360"));
//
//        System.out.println(isExistResource("http://share.weiyun.com/ec4f41f0da292adb89a745200b8e8b57","weiyun"));
//        System.out.println(isExistResource("http://share.weiyun.com/ec4f41f0da292adb89a745200b8e8b57dd",
//                "360"));
//
//        System.out.println(isExistResource("http://cloud.letv.com/s/eiGLzuSes","leshi"));
//        System.out.println(isExistResource("http://cloud.letv.com/s/eiGLzuSesdd",
//                "leshi"));
//    }

    /**
     * 获取指定页面上标签的内容
     *
     * @param url
     * @param tagName
     *            标签名称
     * @return
     */
    private static String getHtmlContent(String url, String tagName) {
        String html = httpRequest(url);
        if(html==null){
            return "";
        }
        Document doc = Jsoup.parse(html);
        //System.out.println("doc======"+doc);
        Elements tag=null;
        if(tagName.equals("<h3>")){ //针对微云
            tag=doc.select("h3");
        }
        else if(tagName.equals("class")){ //针对360
            tag=doc.select("div[class=tip]");
        }
        else{
            tag= doc.getElementsByTag(tagName);
        }
        //System.out.println("tag======"+tag);
        String content="";
        if(tag!=null&&!tag.isEmpty()){
            content = tag.get(0).text();
        }
        return content;
    }

    public static int isExistResource(String url, String ruleName) {
        try {
            String[] rule = rules.get(ruleName);
            String tagName = rule[0];
            String opt = rule[1];
            String flag = rule[2];
            /*System.out.println("ruleName"+ruleName);
            System.out.println("tagName"+tagName);
            System.out.println("opt"+opt);
            System.out.println("flag"+flag);
            System.out.println("url"+url);*/
            String content = getHtmlContent(url, tagName);
            //System.out.println("content="+content);
            if(ruleName.equals("baidu")){
                if(content.contains("百度云升级")){ //升级作为不存在处理
                    return 1;
                }
            }
            String regex = null;
            if ("eq".equals(opt)) {
                regex = "^" + flag + "$";
            } else if ("bg".equals(opt)) {
                regex = "^" + flag + ".*$";
            } else if ("ed".equals(opt)) {
                regex = "^.*" + flag + "$";
            } else if ("like".equals(opt)) {
                regex = "^.*" + flag + ".*$";
            }else if("contain".equals(opt)){
                if(content.contains(flag)){
                    return 0;
                }
                else{
                    return 1;
                }
            }
            if(content.matches(regex)){
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // public static void main(String[] args)throws Exception {
    // final Path p = Paths.get("C:/Users/hui/Desktop/6-14/");
    // final WatchService watchService =
    // FileSystems.getDefault().newWatchService();
    // p.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
    // new Thread(new Runnable() {
    //
    // public void run() {
    // while(true){
    // System.out.println("检测中。。。。");
    // try {
    // WatchKey watchKey = watchService.take();
    // List<WatchEvent<?>> watchEvents = watchKey.pollEvents();
    //
    // for(WatchEvent<?> event : watchEvents){
    // //TODO 根据事件类型采取不同的操作。。。。。。。
    // System.out.println("["+p.getFileName()+"/"+event.context()+"]文件发生了["+event.kind()+"]事件");
    // }
    // watchKey.reset();
    //
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }
    // }
    // }).start();
    // }

//    @Test
//    public void testName() throws Exception {
//        System.out.println(new String("\u8BF7\u8F93\u5165\u63D0\u53D6\u7801".getBytes("utf-8"), "utf-8"));
//    }

}