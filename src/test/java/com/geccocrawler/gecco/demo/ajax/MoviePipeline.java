package com.geccocrawler.gecco.demo.ajax;

import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.demo.jd.AllSort;
import com.geccocrawler.gecco.demo.jd.Category;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HrefBean;

import java.util.ArrayList;
import java.util.List;

@PipelineName("MoviePipeline")
public class MoviePipeline implements Pipeline<MovieDetail> {
	
	public static List<HttpRequest> sortRequests = new ArrayList<HttpRequest>();

	@Override
	public void process(MovieDetail allSort) {
//		List<Category> mobiles = allSort.getMobile();
//		process(allSort, mobiles);
//		List<Category> domestics = allSort.getDomestic();
//		process(allSort, domestics);
//		List<Category> bodys = allSort.getBaby();
		process1(allSort);
	}
	
	 void process1(MovieDetail allSort) {
		int index = 30001;
		for (int i = 30001; i < 30020; i++) {
			String url = "http://www.8vdy.com/view/index" + i + ".html";
			HttpRequest request = new HttpGetRequest(url);
			sortRequests.add(request);
		}
//		if(categorys == null) {
//			return;
//		}
//		for(Category category : categorys) {
//			List<HrefBean> hrefs = category.getCategorys();
//			for(HrefBean href : hrefs) {
//				String url = href.getUrl()+"&delivery=1&page=1&JL=4_10_0&go=0";
//				HttpRequest currRequest = allSort.getRequest();
//				//SchedulerContext.into(currRequest.subRequest(url));
//				//将分类的商品列表地址暂存起来
//				sortRequests.add(currRequest.subRequest(url));
//			}
//		}
	}

}