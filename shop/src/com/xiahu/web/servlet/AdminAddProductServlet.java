package com.xiahu.web.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.xiahu.domain.Category;
import com.xiahu.domain.Product;
import com.xiahu.service.AdminService;
import com.xiahu.utils.CommonsUtils;

/**
 * @author Administrator
 *后台管理添加页面
 *
 */
public class AdminAddProductServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 目的：收集表单的数据 封装一个Product实体 将上传图片存到服务器磁盘上

		Product product = new Product();

		// 创建一个收集数据的容器,用于封装数据
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			// 1.创建磁盘文件项工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 2.创建文件上传核心类
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("UTF-8");
			// 3.//解析request获得文件项对象集合
			List<FileItem> parseRequest = upload.parseRequest(request);
			// 4.遍历该集合
			for (FileItem item : parseRequest) {
				// 5.判断是否是普通表单项
				boolean formField = item.isFormField();
				if (formField) {
					// 是普通表单项
					// 获得表单的数据
					String fieldName = item.getFieldName();
					String filedValue = item.getString("UTF-8");
					/*
					 * 在这里进行判断，如果是普通表单项，text,password之类的input,
					 * 直接获取相应的name，value，添加到map集合中
					 */
					// 封装到Product实体中
					map.put(fieldName, filedValue);
				} else {
					// 是文件上传项
					// 获得文件名称 获得文件的内容
					String fieldName = item.getName();
					InputStream in = item.getInputStream();
					String path = this.getServletContext().getRealPath("upload");
					// 将上传的文件拷贝到指定的地点(url)
					OutputStream out = new FileOutputStream(new File(path + "/" + fieldName));
					IOUtils.copy(in, out);
					in.close();
					out.close();
					item.delete();

					/*
					 * 在这里进行判断，如果是文件上传项 将io流中获取到的字节拷贝到相应的文件，并将其添加到map集合
					 */
					map.put("pimage", "upload/" + fieldName);

				}
			}

			// **************************************************************************
			// 截止这里,判断是否是没有封装在实体类中的选项
			BeanUtils.populate(product, map);
			// private String pid;
			product.setPid(CommonsUtils.getUUID());
			// private Date pdate;
			product.setPdate(new Date());
			// private int pflag;
			product.setPflag(0);
			// private Category category;
			Category category = new Category();
			category.setCid(map.get("cid").toString());
			product.setCategory(category);
			// **************************************************************************
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 将封装好的数据传递给service层
		AdminService service = new AdminService();
		service.addProdutInfo(product);

		// 重定向到productlist.jsp
		response.sendRedirect(request.getContextPath()+"/admin?method=showProductList");

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}