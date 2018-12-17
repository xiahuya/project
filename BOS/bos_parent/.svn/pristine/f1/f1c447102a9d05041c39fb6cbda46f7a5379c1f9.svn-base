package com.xiahu.bos.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiahu.bos.dao.IRegionDao;
import com.xiahu.bos.domain.PageBean;
import com.xiahu.bos.domain.Region;
import com.xiahu.bos.service.IRegionService;

/*
 * 区域管理
 */
@Service
@Transactional
public class RegionServiceImpl implements IRegionService {
	@Autowired
	private IRegionDao regiondao;

	/*
	 * 批量保存区域数据
	 */
	public void saveBatch(List<Region> regionList) {
		for (Region region : regionList) {
			regiondao.saveOrUpdate(region);
		}
	}

	/*
	 * 分页查询
	 */
	public void getPageBean(PageBean pageBean) {
		regiondao.getPageBean(pageBean);
	}

	public List<Region> findAll() {
		return regiondao.findAll();
	}

	@Override
	public List<Region> findAllByQ(String q) {
		return regiondao.findAllByQ(q);
	}

	// 批量删除区域
	public void deleteBatch(String ids) {
		if (StringUtils.isNotBlank(ids)) {
			String[] string = ids.split(",");
			for (String id : string) {
                regiondao.executeUpdate("region.delete", id);
			}
		}
	}

	
	/*
	 * 添加区域
	 */
	public void save(Region model) {
		regiondao.save(model);
	}

	/*
	 * 修改区域信息
	 */
	public void edit(Region model) {
		regiondao.update(model);
	}

	
	//根据区域ID查找区域信息
	public Region findById(String id) {
		return regiondao.findById(id);
	}

}
