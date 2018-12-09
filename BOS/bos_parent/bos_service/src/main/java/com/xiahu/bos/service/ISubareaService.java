package com.xiahu.bos.service;

import java.util.List;

import com.xiahu.bos.domain.PageBean;
import com.xiahu.bos.domain.Subarea;

public interface ISubareaService {

	public  void save(Subarea model);

	public void getPageBean(PageBean pageBean);

	public List<Subarea> findAll();

	public List<Subarea> findSubareaNotDecidedzone();

	public List<Subarea> getSubareaListByDecidedzoneId(String decidedzoneId);

	public List<Object> findSubareaGroupByProvince();

	public Subarea findById(String id);

	public void update(Subarea subarea);

	public void deleteBatch(String ids);

	public void saveBatch(List<Subarea> subareaList);

}
