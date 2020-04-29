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
		//创建Service对象
		IAuthService authService = new AuthServiceImpl();
		
		//定义一个变量用于存储orgcode。在后面获取时赋值给它
		String orgcode = null;
		
		//定义一个boolean类型变量用来表示保存核准件是否成功，默认true即成功
		boolean saveAuthSuccess = true;
		
		//创建一个Map集合用来存储要插入数据库中的数据。（auth表的数据）
		Map<String,String> authMap = new HashMap<String,String>();
		
		//定义一个path表示核准件的保存路径。因为在删除时也要用到这个path，所以定义在外面。
		String path = null;
		
		//把当前管理员的usercode保存到Map集合中，auth表中需要插入这个数据，在auth表中这个数据字段是manager
		authMap.put("manager", ((User)request.getSession(false).getAttribute("user")).getUsercode());
		
		//创建硬盘文件条目工厂对象
		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		//设置一次最大的服务器接受数据量
		factory.setSizeThreshold(4 * 1024);
		
		//设置临时目录
		String tempPath = this.getServletContext().getRealPath("/tempFile");
		factory.setRepository(new File(tempPath));
		
		//创建文件上传对象。
		ServletFileUpload fileUpload = new ServletFileUpload(factory);
		
		//设置服务器接受的最大数据量
		fileUpload.setSizeMax(1024 * 1024 * 1024);
		
		try {
			//解析request请求获得一个存放表单中所有标签的List集合
			List<FileItem> fileItems = fileUpload.parseRequest(request);
			//遍历集合
			for(FileItem fileItem:fileItems) {
				
				//如果是一般的标签，获取属性名字和值，即name和value
				if(fileItem.isFormField()) {
					
					String name = fileItem.getFieldName();
					String value = fileItem.getString("UTF-8");
					//如果遍历到了orgcode标签，将它的值保存下来，在后边保存核准件的命名需要用到它。
					if("orgcode".equals(name)) {
						orgcode = value;
					}
					//把所有标签的name和value放入Map集合中，传入Service中。
					authMap.put(name, value);
				}else {
					
					String oldfileName = fileItem.getName();
					//由于核准件的命名要求唯一，所以需要用一个新的名字，这个名字与orgcode有关系
					String newfileName = orgcode + oldfileName.substring(oldfileName.lastIndexOf("."));
					//把核准件的名字保存到Map集合中，后面要保存到数据库中去
					authMap.put("capitalDoc", newfileName);
					//定义核准件保存的路径名
				    path = this.getServletContext().getRealPath("/authFile") + "/" + newfileName;
				    //保存核准件，写到服务器的硬盘中
					fileItem.write(new File(path));
				}
			}
		} catch (Exception e) {
			//出现异常，表示核准件保存不成功，让这个表示核准件保存标志的变量为false。
			saveAuthSuccess = false;
			e.printStackTrace();
			
		}
		
		//如果核准件保存成功
		if(saveAuthSuccess) {
			//执行保存核准件表的数据的方法。返回一个count用来表示是否Insert数据成功
			int count = authService.saveAuth(authMap);
			//如果insert数据到数据库中成功，重定向到开发核准件的inputOrg.jsp页面
			if(count == 1) {
				response.sendRedirect("auth/inputOrg.jsp");
			//如果insert执行失败，删除前面保存的核准件
			}else {
				
				File file = new File(path);
				file.delete();
			}
			
		//如果核准件没有保存成功，这里不需要执行操作。
		}else {
			
			
		}
		
		
		
	}

}
