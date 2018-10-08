package com.xiahu.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 使用配置文件加载
 * 
 * @author Administrator
 *
 */
public class JedisPoolUtil {

	private static JedisPool pool = null;
	// 静态代码块
	static {
		// 加载配置文件
		InputStream in = JedisPoolUtil.class.getClassLoader().getResourceAsStream("redis.properties");
		Properties prop = new Properties();
		try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}

		JedisPoolConfig poolConfig = new JedisPoolConfig();		
		poolConfig.setMaxIdle(Integer.parseInt(prop.getProperty("redis.setMaxIdle")));// 最大闲置数
		poolConfig.setMinIdle(Integer.parseInt(prop.getProperty("redis.setMinIdle")));// 最小闲置数
		poolConfig.setMaxTotal(Integer.parseInt(prop.getProperty("redis.setMaxTotal")));// 最大连接数
		
		
		pool = new JedisPool(poolConfig, prop.getProperty("redis.host"), Integer.parseInt(prop.getProperty("redis.port")));
	}

	public static Jedis getJedis() {
		return pool.getResource();
	}
	
	public static void close(){
		pool.getResource().close();
	}


}
