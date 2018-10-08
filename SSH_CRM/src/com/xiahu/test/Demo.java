package com.xiahu.test;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.DetachedCriteria;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.xiahu.dao.UserDao;
import com.xiahu.domain.User;
import com.xiahu.service.UserService;
import com.xiahu.utils.PageBean;

//测试hibernate框架
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class Demo {
	@Resource(name = "sessionFactory")
	private SessionFactory sf;

	@Test
	public void test() {

		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		// ----------------------------------------
		User u = new User();
		u.setUser_code("Two");
		u.setUser_name("2222");
		u.setUser_password("123456");

		session.save(u);
		// ----------------------------------------
		tx.commit();

	}

	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

	@Resource(name = "userDao")
	private UserDao ud;

	@Test
	public void test2() {
		User user = ud.getUserByCode("One");
		System.out.println(user);

	}

	public void setUd(UserDao ud) {
		this.ud = ud;
	}

	@Resource(name = "userService")
	private UserService us;

	@Test
	// 测试AOP事务整合
	public void test3() {
		User u = new User();
		u.setUser_code("Three");
		u.setUser_name("三");
		u.setUser_password("123456");
		us.save(u);

	}

	@Test
	public void test4() {
		// 1.封装离线查询对象
		DetachedCriteria dc = DetachedCriteria.forClass(User.class);
		PageBean pageBean = us.getPageBean(dc, 1, 10);
		Map map = new HashMap();
		map.put("total", pageBean.getTotalCount());
		map.put("rows", pageBean.getList());
		

		String json = JSON.toJSONString(map).toString();
		System.out.println(json);

	}

}
