package com.xiahu.utils;

import java.util.UUID;

public class CommonsUtils {

	// 封住获取uid
	public static String getUUID() {
		return UUID.randomUUID().toString();
	}

}
