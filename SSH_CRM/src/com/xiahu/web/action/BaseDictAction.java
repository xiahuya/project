package com.xiahu.web.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.xiahu.domain.BaseDict;
import com.xiahu.service.BaseDictService;

import net.sf.json.JSONArray;



@Controller("baseDictAction")
@Scope("prototype")
public class BaseDictAction extends ActionSupport {

	@Resource(name="baseDictService")
	private BaseDictService baseDictService;

	private String dict_type_code;

	@Override
	public String execute() throws Exception {
		// 1.调用service查询list
		List<BaseDict> list = baseDictService.getListByTypeCode(dict_type_code);
		// 2.用json将list格式封装成json
		String json = JSONArray.fromObject(list).toString();
		// 3.发送到前端页面
		ServletActionContext.getResponse().setContentType("application/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().write(json);
		return null;// 告诉struts2不需要处理结果
	}

	public void setDict_type_code(String dict_type_code) {
		this.dict_type_code = dict_type_code;
	}

	public BaseDictService getBaseDictService() {
		return baseDictService;
	}

	public void setBaseDictService(BaseDictService baseDictService) {
		this.baseDictService = baseDictService;
	}
}
