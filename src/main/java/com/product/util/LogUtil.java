package com.product.util;

import org.apache.log4j.Logger;

public class LogUtil {
	private static Logger logger = Logger.getLogger(LogUtil.class);  
	private LogUtil(){}
	public static Logger  getLog(){
		return logger;
	}
}
