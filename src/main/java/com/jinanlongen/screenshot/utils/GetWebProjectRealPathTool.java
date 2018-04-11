package com.jinanlongen.screenshot.utils;

import java.io.File;

import org.junit.Test;

public class GetWebProjectRealPathTool  {

	/**
	 * 获取项目在服务其中的真实路径的工具类
	 * 这是在web项目中，获取项目实际路径的最佳方式，在windows和linux系统下均可正常使用
	 */
	
	//获取项目的根路径
	public static String classPath = GetWebProjectRealPathTool.class.getClassLoader().getResource("/").getPath();
	//对项目的根路径进行解析，拿到项目路径
	
	public static String getRootPath() {
		String rootPath = "";
		//windows下
		if("\\".equals(File.separator)){
			System.out.println("windows");
		rootPath = classPath.substring(1,classPath.indexOf("/WEB-INF/classes"));
		rootPath = rootPath.replace("/", "\\");
		}
		//linux下
		if("/".equals(File.separator)){
			System.out.println("linux");
		rootPath = classPath.substring(0,classPath.indexOf("/WEB-INF/classes"));
		rootPath = rootPath.replace("\\", "/");
		}
		return rootPath;
	}
	
	
	@Test
	public void testpath() {
		System.out.println("----"+getRootPath());
	}
		
}
