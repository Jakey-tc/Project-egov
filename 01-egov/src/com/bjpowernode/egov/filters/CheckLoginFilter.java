package com.bjpowernode.egov.filters;
/*
 * ʵ��ֻ�ܴ�loginҳ��������ȷ�˺�������ܽ�����Ŀ����ҳ���filter��
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
		
		//��ȡ��ǰ����·����Servlet��·����
		String servletPath = request1.getServletPath();
		
		//��ȡsession����
		HttpSession session = request1.getSession(false);
		
		//ֻ�е����ʵ�¼ҳ���Լ�������֤��¼��Ϣ��Servletʱ�����ߵ�session��Ϊ����session�е�user���Բ�Ϊ��ʱ��
		//��������·��ʣ�Ҳ����˵����֤�ɹ���
		//�����ض��򵽵�¼ҳ��
		if("/loginServlet".equals(servletPath) || ("/login.jsp".equals(servletPath) ) || (session != null && session.getAttribute("user") != null)) {
			
			chain.doFilter(request1, response1);
		}else {
			
			response1.sendRedirect("/01-egov/login.jsp");
		}
		
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
