package com.jnanlongen.screenshot;


import static java.awt.Desktop.getDesktop;
import static java.awt.Desktop.isDesktopSupported;
import static java.nio.file.Files.createTempFile;
import static java.nio.file.Files.write;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.Test;

import io.webfolder.cdp.Launcher;
import io.webfolder.cdp.command.Page;
import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;

import static io.webfolder.cdp.type.constant.DownloadBehavior.Allow;

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

        try (SessionFactory factory = launcher.launch();
                            Session session = factory.create()) {
            //session.navigate("https://news.ycombinator.com");//
        	session.navigate("https://blog.csdn.net/chenleixing/article/details/44816629");//
        	session.wait(10000);//10秒等待时间
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

		String dir = "c:/snapshot1/";
		Path path = Paths.get("c:\\snapshot1");//磁盘位置
		String ts = System.currentTimeMillis()+"";//时间戳命名
		Path file = createTempFile(path, "screenshot-"+ts, ".png");
		 
        try (SessionFactory factory = launcher.launch();
                            Session session = factory.create()) {
            session.navigate("https://news.ycombinator.com");
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
	public void DownloadFile () {
        Launcher launcher = new Launcher();

        try (SessionFactory factory = launcher.launch();
                            Session session = factory.create()) {
            session.navigate("https://www.chiark.greenend.org.uk/~sgtatham/putty/latest.html");
            session.waitDocumentReady();
            session.getCommand().getNetwork().enable();
            Page page = session.getCommand().getPage();
            Path downloadPath = Paths.get(".").toAbsolutePath();
            page.setDownloadBehavior(Allow, downloadPath.toString());
            // link must be visible before downloading the file
            session.evaluate("document.querySelector(\"code\").scrollIntoView()");
            // click the download link
            session.click("code");
            session.wait(2000 * 200);
        }
    }
	
	
	
	@Test
	public void test1() {
		ArrayList<String> command = new ArrayList<String>(); //不显示google 浏览器 
		command.add("--headless"); 
		Launcher launcher = new Launcher();
		try (SessionFactory factory = launcher.launch(command); 
				Session session = factory.create()) { session.navigate("https://webfolder.io"); 
				session.waitDocumentReady(); 
				String content = (String) session.getContent(); 
				System.out.println(content); 
				//已经拿到渲染后的html，下面就可以用jsoup去抓取想要的数据了
		}
	}
}
