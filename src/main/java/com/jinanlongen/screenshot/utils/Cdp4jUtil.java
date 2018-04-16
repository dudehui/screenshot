package com.jinanlongen.screenshot.utils;

import static java.nio.file.Files.createTempFile;
import static java.nio.file.Files.write;

import java.nio.file.Path;
import java.nio.file.Paths;

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
	
	public static int WAIT_TIMEOUT = 90 * 1000; // 90 seconds
	
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

}
