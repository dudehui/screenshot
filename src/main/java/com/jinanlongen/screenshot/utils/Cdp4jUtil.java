package com.jinanlongen.screenshot.utils;

import static java.nio.file.Files.createTempFile;
import static java.nio.file.Files.write;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Timer;

import io.webfolder.cdp.Launcher;
import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;
import snapshot.model.Page;
import snapshot.task.SnapshotProcessorDeamon;
import snapshot.task.TaskQueue;

/**
 * cdp4j屏幕截图工具类
 * 
 * @author Administrator
 *
 */
public class Cdp4jUtil {
	/**
	 * 屏幕截图功能
	 * 
	 * @param path
	 * @param url
	 * @return
	 */
	public static boolean doScreenShot(Path path, String url) {
		Launcher launcher = new Launcher();
		String ts = System.currentTimeMillis() + "";// 图片名成按照：时间戳命名,不会重复
		Path file = null;
		try {
			file = createTempFile(path, "screenshot-" + ts, ".png");
			try (SessionFactory factory = launcher.launch(); Session session = factory.create()) {
				session.navigate(url);
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
	 * 屏幕截图功能
	 * 
	 * @param dirpath
	 *            图片存放的根目录
	 * @param url
	 *            超链接地址
	 * @return
	 */
	public static boolean doScreenShot(String dirpath, String imageurl, String imagename) {
		Path path = Paths.get(dirpath);
		Launcher launcher = new Launcher();
		// String ts = System.currentTimeMillis()+"";//图片名成按照：时间戳命名,不会重复
		Path file = null;
		try {
			file = createTempFile(path, imagename, ".png");// 路径， 名称， 格式
			try (SessionFactory factory = launcher.launch(); Session session = factory.create()) {
				session.navigate(imageurl);// 超链接
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

}
