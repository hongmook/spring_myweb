package com.khm.common;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;

public class EncodingFilter implements Filter {
       
	private String encoding = null;
	
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		
		this.encoding = fConfig.getInitParameter("encoding"); //"encoding"�� web.xml�뿉 �엳�뒗 init-param瑜� �쟻�뼱以�
//		System.out.println(fConfig.getFilterName() + "�븘�꽣媛� �떆�옉�릺�뿀�뒿�땲�떎.");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		if(encoding != null) {
			//�슂泥�怨� �쓳�떟�쓣 紐⑤몢 encoding�쓣 �궗�슜�븯寃좊떎怨� �꽕�젙
			request.setCharacterEncoding(encoding);
			response.setCharacterEncoding(encoding);
		}
		
		chain.doFilter(request, response);
	}

	public void destroy() {
		
	}

}
