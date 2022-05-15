package com.gyarleque.springboot.app.zuul.filters;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class PostTimeElapsedFilter extends ZuulFilter {
	
	private static final Logger log = LoggerFactory.getLogger(PostTimeElapsedFilter.class);


	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		
		log.info("Entering to post filter");
		
		Long timeInit = (Long) request.getAttribute("timeInit");
		Long timeEnd = System.currentTimeMillis();
		
		Long timeElapsed = timeEnd - timeInit;
		
		log.info(String.format("Time Elapsed in seconds %s seg.", timeElapsed.doubleValue()/1000.00));
		log.info(String.format("Time Elapsed in milliseconds %s ms.", timeElapsed));
		
		request.setAttribute("timeInit", timeInit);
		return null;
	}

	@Override
	public String filterType() {
		return "post";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

}
