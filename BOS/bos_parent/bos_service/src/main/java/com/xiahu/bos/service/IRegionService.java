package com.xiahu.bos.service;

import java.util.List;

import com.xiahu.bos.domain.PageBean;
import com.xiahu.bos.domain.Region;

public interface IRegionService {

	void saveBatch(List<Region> regionList);

	void getPageBean(PageBean pageBean);

	List<Region> findAll();

	List<Region> findAllByQ(String q);

	void deleteBatch(String ids);

	void save(Region model);

	void edit(Region model);

	Region findById(String id);

}
