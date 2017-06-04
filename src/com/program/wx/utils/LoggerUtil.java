package com.program.wx.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

import com.jfinal.kit.Prop;

public class LoggerUtil {

	public static Logger getLogger(Class<?> clazz) {
		Logger logger = Logger.getLogger(clazz); // 生成新的Logger
		return logger;
	}

}
