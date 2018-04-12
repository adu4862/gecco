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

@Gecco(matchUrl = "http://www.pniao.com/Mov/one/{code}.html", pipelines = {"consolePipeline1"})
public class MovieDetail implements HtmlBean {

    private static final long serialVersionUID = -377053120283382723L;

    @RequestParameter
    private String code;

//	@Ajax(url="https://p.3.cn/prices/get?type=1&pdtk=&pdbp=0&skuid=J_{code}")
//	private JDPrice price;

    @Text
    @HtmlField(cssPath = "div.movTitle li.mainTitle")
    private String title;

    @Text
    @HtmlField(cssPath = "")
    private String password;

    @Text
    @HtmlField(cssPath = "img.orginSrc[alt]")
    private String img1;
    //<div class="briefCnt">第29届阿姆斯特丹国际纪录片电影节纪录长片评委会大奖。范俭执导，以残疾女诗人余秀华为主角的纪录片，<br>该片记录了中国艾米丽·迪金森之称的中国农村脑瘫女诗人余秀华的生活，讲述了来自湖北省钟祥市横店村的诗人余秀华成名之后，为挣脱束缚，重获自由，与无爱丈夫离婚的故事。<br>余秀华，一个农村女性，从小患有脑瘫，在2015年成为中国最有名的诗人。她写出《穿越大半个中国去睡你》，强烈的情感与欲望引人注目。20年前，余秀华的母亲将懵懂无知的她嫁给了一个比她大十几岁的陌生男人，这段形同陌路的婚姻成为她一生的疼痛和遗憾。余秀华写诗，试图与自己的命运对话，写残缺的身体，写她对真爱的渴望。随着余秀华的成名和经济上的独立，她想通过离婚来重新掌控自己的命运，结束这段没有爱的婚姻。然而她的老公强烈反对离婚，她的父母和儿子也并不支持她。更糟糕的是她的母亲被检查出患有肺癌，余秀华面对的阻力越来越大，她能实现自己的心愿吗？<br>该条目为纪录长片，有别于纪录短片《一个女诗人的意外走红》，标记前请知悉！-豆瓣电影团队</div>
    @Text
    @HtmlField(cssPath = "div.briefCnt")
    private String descb;

    //<div class="movInfoOuter">
    //		<div class="infoOuter"><div class="info">
    //						<ul>
    //			<li>导演:</li>
    //			<li><a rel="nofollow" href="http://www.pniao.com/Mov/tag/director/范俭">范俭</a></li>
    //			</ul>
    //
    //						<ul>			<li>主演:</li>
    //			<li><a rel="nofollow" href="http://www.pniao.com/Mov/tag/actors/余秀华">余秀华</a></li><li><a rel="nofollow" href="http://www.pniao.com/Mov/tag/actors/周金香">周金香</a></li><li><a rel="nofollow" href="http://www.pniao.com/Mov/tag/actors/余文海">余文海</a></li><li><a rel="nofollow" href="http://www.pniao.com/Mov/tag/actors/尹世平">尹世平</a></li>			</ul>
    //
    //						<ul>
    //			<li>类型:</li>
    //			<li><a rel="nofollow" href="http://www.pniao.com/Mov/tag/sorts/纪录片">纪录片</a></li><li><a rel="nofollow" href="http://www.pniao.com/Mov/tag/sorts/传记">传记</a></li>			</ul>
    //
    //						<ul>
    //			<li>国家/地区:</li>
    //			<li><a rel="nofollow" href="http://www.pniao.com/Mov/tag/country/中国大陆">中国大陆</a></li>			</ul>
    //
    //
    //						<ul>
    //			<li>上映时间:</li><li><a rel="nofollow" href="http://www.pniao.com/Mov/tag/year/2017">
    //			2017年06月26日			</a></li>
    //			</ul>
    //
    //
    //						<ul>
    //			<li>IMDb:</li><li><a rel="nofollow" target="blank" href="https://www.imdb.com/title/tt6254446">tt6254446</a></li>
    //			</ul>
    //									<ul class="dbScore">
    //				<li><a rel="nofollow" target="blank" href="https://movie.douban.com/subject/26324232">
    //				豆瓣&nbsp;<span class="score_1">8</span><span class="score_2">.2</span>分				</a></li>
    //			</ul>
    //									<ul class="awards">
    //						<li><a rel="nofollow" target="blank" href="https://movie.douban.com/subject/26324232/awards">
    //		获奖/提名[1项]
    //			</a></li>
    //						</ul>
    //					</div></div>
    //		<!---right--->
    //		<div class="thumbOuter">
    //			<div class="thumb">
    //				<a href="http://www.pniao.com/Mov/one/26462.html">
    //								<img alt="摇摇晃晃的人间" class="orginSrc" data-url="http://www.pniao.com/p/movsp/14/26462.jpg" src="http://www.pniao.com/p/movsp/14/26462.jpg">
    //								</a>
    //			</div>
    //		</div>
    //		<!---thumbOuter--->
    //		</div>


    @HtmlField(cssPath = "div.movInfoOuter")
    private String movInfoOuter;

    public String getMovInfoOuter() {
        return movInfoOuter;
    }

    public void setMovInfoOuter(String movInfoOuter) {
        this.movInfoOuter = movInfoOuter;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getDescb() {
        return descb;
    }

    public void setDescb(String descb) {
        this.descb = descb;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
        for (int i = 1; i < 47999; i++) {
            String url = "http://www.pniao.com/Mov/one/" + i + ".html";
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
                .thread(60)
                //单个爬虫每次抓取完一个请求后的间隔时间
                .interval(4000)
                .start();
    }

}
