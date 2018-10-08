package com.xiahu.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.xiahu.dao.CustomerDao;
import com.xiahu.domain.Customer;

@Repository("customerDao")
public class CustomerDaoImpl extends BaseDaoImpl<Customer> implements CustomerDao {
	@Resource(name = "sessionFactory")
	public void setSF(SessionFactory sf) {
		super.setSessionFactory(sf);
	}

	@Override
	public List<Object[]> getIndustryCount() {
		@SuppressWarnings("unchecked")
		List<Object[]> list = getHibernateTemplate().execute(new HibernateCallback<List>() {
			String sql = "select bd.dict_item_name, count(*) " + "from cst_customer c,base_dict bd "
					+ "where c.cust_industry=bd.dict_id " + "GROUP BY c.cust_industry";

			@Override
			public List doInHibernate(Session session) throws HibernateException {
				SQLQuery query = session.createSQLQuery(sql);
				return query.list();
			}
		});
		return list;
	}

}
