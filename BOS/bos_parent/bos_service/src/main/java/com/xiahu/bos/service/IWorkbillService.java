package com.xiahu.bos.service;

import com.xiahu.bos.domain.PageBean;

public interface IWorkbillService {
	// 分页
	public void getPageBean(PageBean pageBean);

	public void zhuidan(String ids);

	public void xiaodan(String ids);

}
