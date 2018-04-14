package com.shangyao.screenshot;

import static java.awt.Desktop.isDesktopSupported;
import static java.nio.file.Files.createTempFile;
import static java.nio.file.Files.write;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.Test;

import io.webfolder.cdp.Launcher;
import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年3月29日
 */
public class Cdp4jTest {
	public static void main(String[] args) throws IOException {
//		 Launcher launcher = new Launcher();
//		 Path path = Paths.get("C:\\Users\\Administrator\\AppData\\Local\\Google\\Chrome\\Application\\chrome.exe");
//		 try (SessionFactory factory = launcher.launch(path); Session session =
//		 factory.create()) {
//		 session.navigate("https://webfolder.io");
//		 session.waitDocumentReady();
//		 String content = (String) session.getProperty("//body", "outerText");
//		 System.out.println(content);
//		 }
		screen();
	}
	
	public static  void screen() throws IOException {
		Launcher launcher = new Launcher();
		Path path = Paths.get("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
		Path path2 = Paths.get("D:\\");

		Path file = createTempFile(path2, "screenshot", ".png");

		try (SessionFactory factory = launcher.launch(path); 
				Session session = factory.create()) {
			session.navigate(
					"https://www.eastbay.com/product/model:200618/sku:Q18127/adidas-originals-roller-crew-socks-mens/black/grey/");
//		"http://www.baidu.com/");
			
			session.wait(30000);
			session.waitDocumentReady();
			// activate the tab/session before capturing the screenshot
			session.activate();
			byte[] data = session.captureScreenshot();
			write(file, data);
		}

		if (isDesktopSupported()) {
			// getDesktop().open(file.toFile());
		}
	}
		
}
