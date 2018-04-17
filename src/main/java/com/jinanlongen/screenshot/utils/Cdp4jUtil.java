package com.jinanlongen.screenshot.utils;

import static java.awt.Desktop.getDesktop;
import static java.awt.Desktop.isDesktopSupported;
import static java.nio.file.Files.createTempFile;
import static java.nio.file.Files.write;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import io.webfolder.cdp.Launcher;
import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;

/**
 * cdp4j屏幕截图工具类
 * 
 * @author Administrator
 *
 */
public class Cdp4jUtil {
	
	public static int WAIT_TIMEOUT = 60 * 1000; // 90 seconds
	
	/**
	 * 屏幕截图功能
	 * 
	 * @param dirpath
	 *            图片存放的根目录
	 * @param url
	 *            超链接地址
	 * @return
	 */
	public boolean doScreenShot(String dirpath, String imageurl, String imagename) {
		
		Path path = Paths.get(dirpath);
		Launcher launcher = new Launcher();
		// String ts = System.currentTimeMillis()+"";//图片名成按照：时间戳命名,不会重复
		Path file = null;
		try {
			file = createTempFile(path, imagename, ".png");// 路径， 名称， 格式
			try (SessionFactory factory = launcher.launch(); Session session = factory.create()) {
				session.navigate(imageurl);// 超链接
				session.waitDocumentReady(WAIT_TIMEOUT);
				session.waitDocumentReady();
				// activate the tab/session before capturing the screenshot
				session.activate();
				byte[] data = session.captureScreenshot();
				write(file, data);
			}
		} catch (Exception e) { // io exception
			return false;
		}
		return true;

	}
	
	/**
	 * 屏幕截图功能, 无界面
	 * 
	 * @param dirpath
	 *            图片存放的根目录
	 * @param url
	 *            超链接地址
	 * @return
	 */
	public boolean doScreenShotHeadLess(String dirpath, String imageurl, String imagename) {
		//
		ArrayList<String> command = new ArrayList<String>(); //不显示google 浏览器 
		command.add("--headless"); 
		Path path = Paths.get(dirpath);
		Launcher launcher = new Launcher();
		
		// String ts = System.currentTimeMillis()+"";//图片名成按照：时间戳命名,不会重复
		Path file = null;
		try {
			file = createTempFile(path, imagename, ".png");// 路径， 名称， 格式
			try (SessionFactory factory = launcher.launch(command); Session session = factory.create()) {
				session.navigate(imageurl);// 超链接
				session.waitDocumentReady(WAIT_TIMEOUT);
				session.waitDocumentReady();
				// activate the tab/session before capturing the screenshot
				session.activate();
				byte[] data = session.captureScreenshot();
				write(file, data);
			}
		} catch (Exception e) { // io exception
			return false;
		}
		return true;

	}

	
	/**
	 * 屏幕截图功能, 无界面, 命令位置
	 * 
	 * @param dirpath
	 *            图片存放的根目录
	 * @param url
	 *            超链接地址
	 * @return
	 */
	public boolean doScreenShotHeadLess(String dirpath, String imageurl, String imagename, String googleDir) {
		//
		ArrayList<String> command = new ArrayList<String>(); //不显示google 浏览器 
		command.add("--headless"); 
//		Path exepath = Paths.get("/usr/bin/google-chrome-stable");
//		Path exepath = Paths.get("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
		Path path = Paths.get(dirpath);
		Path exepath = Paths.get(googleDir);
		Launcher launcher = new Launcher();
		
		// String ts = System.currentTimeMillis()+"";//图片名成按照：时间戳命名,不会重复
		Path file = null;
		try {
			file = createTempFile(path, imagename, ".png");// 路径， 名称， 格式
			try (SessionFactory factory = launcher.launch(exepath, command); Session session = factory.create()) {
				session.navigate(imageurl);// 超链接
				session.waitDocumentReady(WAIT_TIMEOUT);
				session.waitDocumentReady();
				// activate the tab/session before capturing the screenshot
				session.activate();
				byte[] data = session.captureScreenshot();
				write(file, data);
			}
			if (isDesktopSupported()) {
	            getDesktop().open(file.toFile());//将截取的图片显示出来
	        }
		} catch (Exception e) { // io exception
			return false;
		}
		return true;

	}
	
	
}
