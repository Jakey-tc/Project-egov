package com.bjpowernode.egov.system.servlets;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.bjpowernode.egov.beans.User;
import com.bjpowernode.egov.system.service.AuthServiceImpl;
import com.bjpowernode.egov.system.service.IAuthService;


public class SaveAuthServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//����Service����
		IAuthService authService = new AuthServiceImpl();
		
		//����һ���������ڴ洢orgcode���ں����ȡʱ��ֵ����
		String orgcode = null;
		
		//����һ��boolean���ͱ���������ʾ�����׼���Ƿ�ɹ���Ĭ��true���ɹ�
		boolean saveAuthSuccess = true;
		
		//����һ��Map���������洢Ҫ�������ݿ��е����ݡ���auth������ݣ�
		Map<String,String> authMap = new HashMap<String,String>();
		
		//����һ��path��ʾ��׼���ı���·������Ϊ��ɾ��ʱҲҪ�õ����path�����Զ��������档
		String path = null;
		
		//�ѵ�ǰ����Ա��usercode���浽Map�����У�auth������Ҫ����������ݣ���auth������������ֶ���manager
		authMap.put("manager", ((User)request.getSession(false).getAttribute("user")).getUsercode());
		
		//����Ӳ���ļ���Ŀ��������
		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		//����һ�����ķ���������������
		factory.setSizeThreshold(4 * 1024);
		
		//������ʱĿ¼
		String tempPath = this.getServletContext().getRealPath("/tempFile");
		factory.setRepository(new File(tempPath));
		
		//�����ļ��ϴ�����
		ServletFileUpload fileUpload = new ServletFileUpload(factory);
		
		//���÷��������ܵ����������
		fileUpload.setSizeMax(1024 * 1024 * 1024);
		
		try {
			//����request������һ����ű������б�ǩ��List����
			List<FileItem> fileItems = fileUpload.parseRequest(request);
			//��������
			for(FileItem fileItem:fileItems) {
				
				//�����һ��ı�ǩ����ȡ�������ֺ�ֵ����name��value
				if(fileItem.isFormField()) {
					
					String name = fileItem.getFieldName();
					String value = fileItem.getString("UTF-8");
					//�����������orgcode��ǩ��������ֵ�����������ں�߱����׼����������Ҫ�õ�����
					if("orgcode".equals(name)) {
						orgcode = value;
					}
					//�����б�ǩ��name��value����Map�����У�����Service�С�
					authMap.put(name, value);
				}else {
					
					String oldfileName = fileItem.getName();
					//���ں�׼��������Ҫ��Ψһ��������Ҫ��һ���µ����֣����������orgcode�й�ϵ
					String newfileName = orgcode + oldfileName.substring(oldfileName.lastIndexOf("."));
					//�Ѻ�׼�������ֱ��浽Map�����У�����Ҫ���浽���ݿ���ȥ
					authMap.put("capitalDoc", newfileName);
					//�����׼�������·����
				    path = this.getServletContext().getRealPath("/authFile") + "/" + newfileName;
				    //�����׼����д����������Ӳ����
					fileItem.write(new File(path));
				}
			}
		} catch (Exception e) {
			//�����쳣����ʾ��׼�����治�ɹ����������ʾ��׼�������־�ı���Ϊfalse��
			saveAuthSuccess = false;
			e.printStackTrace();
			
		}
		
		//�����׼������ɹ�
		if(saveAuthSuccess) {
			//ִ�б����׼��������ݵķ���������һ��count������ʾ�Ƿ�Insert���ݳɹ�
			int count = authService.saveAuth(authMap);
			//���insert���ݵ����ݿ��гɹ����ض��򵽿�����׼����inputOrg.jspҳ��
			if(count == 1) {
				response.sendRedirect("auth/inputOrg.jsp");
			//���insertִ��ʧ�ܣ�ɾ��ǰ�汣��ĺ�׼��
			}else {
				
				File file = new File(path);
				file.delete();
			}
			
		//�����׼��û�б���ɹ������ﲻ��Ҫִ�в�����
		}else {
			
			
		}
		
		
		
	}

}
