package com.xiahu.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xiahu.dao.SaleVisitDao;
import com.xiahu.domain.SaleVisit;
import com.xiahu.service.SaleVisitService;
import com.xiahu.utils.PageBean;
@Service("saleVisitService")
@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
public class SaleVisitServiceImpl implements SaleVisitService {
	@Resource(name="saleVisitDao")
	private SaleVisitDao svd;

	@Override
	public PageBean getPageBean(DetachedCriteria dc, Integer currentPage, Integer pageSize) {
		// 1 调用Dao查询总记录数
		Integer totalCount = svd.getTotalCount(dc);
		// 2 创建PageBean对象
		PageBean pb = new PageBean(currentPage, totalCount, pageSize);
		// 3 调用Dao查询分页列表数据

		List<SaleVisit> list = svd.getPageList(dc, pb.getStart(), pb.getPageSize());
		// 4 列表数据放入pageBean中.并返回
		pb.setList(list);
		return pb;
	}

	@Override
	// 添加
	public void save(SaleVisit saleVisit) {
		svd.saveOrUpdate(saleVisit);
	}

	public void setSvd(SaleVisitDao svd) {
		this.svd = svd;
	}

	@Override
	public SaleVisit getById(String visit_id) {
		
		return svd.getById(visit_id);
	}

}
