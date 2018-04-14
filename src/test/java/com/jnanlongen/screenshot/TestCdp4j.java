package com.jnanlongen.screenshot;


import static java.awt.Desktop.getDesktop;
import static java.awt.Desktop.isDesktopSupported;
import static java.nio.file.Files.createTempFile;
import static java.nio.file.Files.write;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import io.webfolder.cdp.Launcher;
import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;

/**
 * 
 * @description
 * @author dudh
 * @date 2018年3月27日
 */
public class TestCdp4j {

	public static void main(String[] args) throws IOException, InterruptedException {
        Launcher launcher = new Launcher();

        Path file = createTempFile("screenshot", ".png");
//https://www.eastbay.com/product/model:200618/sku:Q18127/adidas-originals-roller-crew-socks-mens/black/grey/
        try (SessionFactory factory = launcher.launch();
                            Session session = factory.create()) {
//            session.navigate("https://news.ycombinator.com");//
//        	session.navigate("https://blog.csdn.net/chenleixing/article/details/44816629");//
//        	session.navigate("https://www.eastbay.com/product/model:200618/sku:Q18127/adidas-originals-roller-crew-socks-mens/black/grey");//
        	 session.navigate("https://www.eastbay.com/product/model:200618/sku:Q18127/adidas-originals-roller-crew-socks-mens/black/grey/");//
        	session.wait(30000);
            session.waitDocumentReady();
            // activate the tab/session before capturing the screenshot
            session.activate();
            byte[] data = session.captureScreenshot();
            write(file, data);
        }

        if (isDesktopSupported()) {
            getDesktop().open(file.toFile());//将截取的图片显示出来
        }
   }
	
	
	@Test
	public void testshot() {
		System.out.println("Hello World");
	}

	@Test
	public void testshot1() throws IOException {
		Launcher launcher = new Launcher();
		Path path = Paths.get("c:\\snapshot1");//磁盘位置
		String ts = System.currentTimeMillis()+"";//时间戳命名
		Path file = createTempFile(path, "screenshot-"+ts, ".png");
		 
        try (SessionFactory factory = launcher.launch();
                            Session session = factory.create()) {
            session.navigate("https://www.zappos.com/p/nike-cotton-cushion-crew-with-moisture-management-3-pair-pack-black-white/product/8068305/color/151");
            session.waitDocumentReady();
            // activate the tab/session before capturing the screenshot
            //session.activate();
            byte[] data = session.captureScreenshot();
            write(file, data);
        }

        if (isDesktopSupported()) {
            getDesktop().open(file.toFile());//将截取的图片显示出来
        }
	}
	
//	@Test
//	public void test1() {
//		ArrayList<String> command = new ArrayList<String>(); //不显示google 浏览器 
//		command.add("--headless"); 
//		Launcher launcher = new Launcher();
//		try (SessionFactory factory = launcher.launch(command); 
//				Session session = factory.create()) { session.navigate("https://webfolder.io"); 
//				session.waitDocumentReady(); 
//				String content = (String) session.getContent(); 
//				System.out.println(content); 
//				//已经拿到渲染后的html，下面就可以用jsoup去抓取想要的数据了
//		}
//	}
}
