package com.xiahu.bos.service;

import java.util.List;

import com.xiahu.bos.domain.PageBean;
import com.xiahu.bos.domain.Workordermanage;

public interface IWorkorderImportService {

	void getPageBean(PageBean pageBean);

	void saveBatch(List<Workordermanage> list);

}
