package com.xiahu.bos.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiahu.bos.ICustomerService;
import com.xiahu.bos.dao.IDecidedzoneDao;
import com.xiahu.bos.dao.INoticebillDao;
import com.xiahu.bos.dao.IWorkbillDao;
import com.xiahu.bos.domain.Decidedzone;
import com.xiahu.bos.domain.Noticebill;
import com.xiahu.bos.domain.Staff;
import com.xiahu.bos.domain.User;
import com.xiahu.bos.domain.Workbill;
import com.xiahu.bos.service.INoticebillService;
import com.xiahu.bos.utils.BOSUtils;

@Service
@Transactional
public class NoticebillServiceImpl implements INoticebillService {
	@Autowired
	private INoticebillDao noticebillDao;
	@Autowired
	private ICustomerService customerService;
	@Autowired
	private IDecidedzoneDao decidedzoneDao;
	@Autowired
	private IWorkbillDao workbillDao;

	/**
	 * 保存业务通知单，还有尝试自动分单
	 */
	public void save(Noticebill model) {
		User user = BOSUtils.getLoginUser();
		model.setUser(user);// 设置当前登录用户
		// 保存数据
		noticebillDao.save(model);
		// -----------------------------
		// 获取客户取件地址
		String pickaddress = model.getPickaddress();
		// 根据地址从CRM客户表中获取定区ID
		String decidedzoneId = customerService.findDecidedzoneIdByAddress(pickaddress);
		if (decidedzoneId != null) {
			// 查询定区ID,实现自动分单
			// 1.从定区表中根据定区ID获取该定区的信息
			Decidedzone decidedzone = decidedzoneDao.findById(decidedzoneId);
			// 2.获取该定区对应的职工
			Staff staff = decidedzone.getStaff();
			// 3.业务通知单关联该职工
			model.setStaff(staff);
			// 4.设置分单类型----自动分单
			Workbill workbill = new Workbill();
			workbill.setStaff(staff);
			workbill.setAttachbilltimes(0);// 追单次数
			workbill.setBuildtime(new Timestamp(System.currentTimeMillis()));// 创建时间，当前系统时间
			workbill.setNoticebill(model);// 工单关联页面通知单
			workbill.setPickstate(Workbill.PICKSTATE_NO);// 取件状态
			workbill.setRemark(model.getRemark());// 备注信息
			workbill.setType(Workbill.TYPE_1);// 工单类型
			workbillDao.save(workbill);
			// 调用短信平台，发送短信
			// **
			// **
		} else {
			// 没有查询到定区id，不能完成自动分单
			model.setOrdertype(Noticebill.ORDERTYPE_MAN);

		}
	}
}
