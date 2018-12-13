package com.xiahu.bos.realm;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;

import com.xiahu.bos.dao.IFunctionDao;
import com.xiahu.bos.dao.IUserDao;
import com.xiahu.bos.domain.Function;
import com.xiahu.bos.domain.User;

public class BOSRealm extends AuthorizingRealm {
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IFunctionDao functionDao;

	// 认证方法
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// 获取客户端页数输入的用户名跟密码
		UsernamePasswordToken passwordToken = (UsernamePasswordToken) token;
		String username = passwordToken.getUsername();
		// 根据username去数据库中查找对应的password
		User user = userDao.getUserByUsername(username);
		if (user == null) {
			// 页面输入的用户不存在
			return null;
		}

		// 简单认证信息对象
		// 框架负责比对数据库中的密码和页面输入的密码是否一致
		AuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
		return info;
		// 如果认证通过,UserAction_login方法可以取出该user
	}

	// 授权方法
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// 获取当前登录用户对象
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		// User user2 = (User) principals.getPrimaryPrincipal();
		// 根据当前登录用户查询数据库，获取实际对应的权限
		List<Function> list = null;
		if (user.getUsername().equals("admin")) {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Function.class);
			// 超级管理员内置用户，查询所有权限数据
			list = functionDao.findByCriteria(detachedCriteria);
		} else {
			list = functionDao.findFunctionListByUserId(user.getId());
		}

		for (Function function : list) {
			info.addStringPermission(function.getCode());
		}
		return info;
	}

}
