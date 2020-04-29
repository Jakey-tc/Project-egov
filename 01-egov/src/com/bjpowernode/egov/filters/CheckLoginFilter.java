package com.bjpowernode.egov.filters;
/*
 * 实现只能从login页面输入正确账号密码才能进入项目其他页面的filter。
 */
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class CheckLoginFilter implements Filter {

  
    public CheckLoginFilter() {
        
    }

	public void destroy() {
		
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request1 = (HttpServletRequest)request; 
		HttpServletResponse response1  = (HttpServletResponse)response;
		
		//获取当前访问路径的Servlet的路径。
		String servletPath = request1.getServletPath();
		
		//获取session对象。
		HttpSession session = request1.getSession(false);
		
		//只有当访问登录页面以及访问验证登录信息的Servlet时，或者当session不为空且session中的user属性不为空时，
		//会继续往下访问（也可以说是验证成功）
		//否则重定向到登录页面
		if("/loginServlet".equals(servletPath) || ("/login.jsp".equals(servletPath) ) || (session != null && session.getAttribute("user") != null)) {
			
			chain.doFilter(request1, response1);
		}else {
			
			response1.sendRedirect("/01-egov/login.jsp");
		}
		
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
