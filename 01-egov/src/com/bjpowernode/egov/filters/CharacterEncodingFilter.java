package com.bjpowernode.egov.filters;
/*
 * 解决中文字符乱码问题的filter
 */
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


public class CharacterEncodingFilter implements Filter {

  
    public CharacterEncodingFilter() {
       
    }

	
	public void destroy() {
		
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		//解决请求协议中中文乱码问题
		request.setCharacterEncoding("UTF-8");
		chain.doFilter(request, response);
	}


	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
