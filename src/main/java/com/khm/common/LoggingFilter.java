package com.khm.common;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter("/*")
public class LoggingFilter extends HttpFilter implements Filter {
    PrintWriter writer;
	
    public LoggingFilter() {
        super();
    }

	public void destroy() {
		writer.close();
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	
		long begin = System.currentTimeMillis();
		
		String path = ((HttpServletRequest)request).getContextPath();
		String uri = ((HttpServletRequest)request).getRequestURI();
		String cmd = uri.substring(uri.lastIndexOf("/")+1);
//		System.out.println("path : " + path);
//		System.out.println("uri : " + uri);
//		System.out.println("cmd : " + cmd);
//		writer.printf("path : %s, uri : %S, cmd : %s, \n", path,uri,cmd);
		
		GregorianCalendar now = new GregorianCalendar();
//		writer.printf("�젒洹쇱떆媛� : %TF, %TT %n", now, now);
		
		String addr = request.getRemoteAddr();
		int port = request.getRemotePort();
//		writer.printf("�젒洹쇳븳二쇱냼 : %s\\n, �젒洹쇳룷�듃: %d\n", addr, port);
		
		chain.doFilter(request, response);
		
		long end = System.currentTimeMillis();
//		writer.printf("�쓳�떟�떆媛� : %d ms \n", (end - begin));

	}

	public void init(FilterConfig fConfig) throws ServletException {
		GregorianCalendar cal = new GregorianCalendar();
		String filename = cal.get(Calendar.YEAR) + "_" + (cal.get(Calendar.MONTH)+1) + "_" + cal.get(Calendar.DATE);
		
		try {
			writer = new PrintWriter(new FileWriter("d:\\KHM\\log\\" + filename + ".log", true), true);
		} catch (IOException e) {
			System.out.println("너는 무슨 에러니?");
		}

	}

}
