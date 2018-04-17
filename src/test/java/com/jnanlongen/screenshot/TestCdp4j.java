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
import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;

/**
 * 
 * @description
 * @author dudh
 * @date 2018年3月27日
 */
public class TestCdp4j {

	public static int WAIT_TIMEOUT = 30 * 1000; // 90 seconds
	
	/**
	 * 
	* <p>Title: testshot</p>
	* <p>Description: </p>
	 */
	@Test
	public void testshot() {
		System.out.println("Hello World");
	}
	
	@Test
	public void testCdp4j1() throws IOException, InterruptedException {
        Launcher launcher = new Launcher();

        Path file = createTempFile("screenshot", ".png");
//https://www.eastbay.com/product/model:200618/sku:Q18127/adidas-originals-roller-crew-socks-mens/black/grey/
        try (SessionFactory factory = launcher.launch();
                            Session session = factory.create()) {
            session.navigate("https://news.ycombinator.com");//
//        	session.navigate("https://blog.csdn.net/chenleixing/article/details/44816629");//
//        	session.navigate("https://www.eastbay.com/product/model:200618/sku:Q18127/adidas-originals-roller-crew-socks-mens/black/grey");//
            session.waitDocumentReady(WAIT_TIMEOUT);//等待60s
            // activate the tab/session before capturing the screenshot
            session.activate();
            byte[] data = session.captureScreenshot();
            write(file, data);
        }

        if (isDesktopSupported()) {
            getDesktop().open(file.toFile());//将截取的图片显示出来
        }
   }
	

	/** 不显示google 浏览器  Java爬虫入门篇（三）使用 cdp4j 抓取需要渲染的网页
	 * Print text content with cdp4j
	* <p>Title: https://www.jianshu.com/p/054b50026f9a</p>
	* <p>Description: </p>
	 */
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
	
	
	/**截图，保存到磁盘位置
	* <p>Title: testshot1</p>
	* <p>Description: </p>
	* @throws IOException
	*/
	@Test
	public void testshot1() throws IOException {
		int WAIT_TIMEOUT = 90 * 1000; // 10 seconds
		Launcher launcher = new Launcher();
		Path path = Paths.get("c:\\snapshot1");//磁盘位置
		String ts = System.currentTimeMillis()+"";//时间戳命名
		
		String url = "http://www.baidu.com";
//		String url = "https://www.zappos.com/p/nike-cotton-cushion-crew-with-moisture-management-3-pair-pack-black-white/product/8068305/color/151";
		Path file = createTempFile(path, "screenshot-"+ts, ".png");
		 
        try (SessionFactory factory = launcher.launch();
                            Session session = factory.create()) {
            session.navigate(url);
//            session.navigate("https://news.ycombinator.com");//
//          session.waitDocumentReady();
            session.waitDocumentReady(WAIT_TIMEOUT);
          //session.wait(WAIT_TIMEOUT);//-----------这样不行
            // activate the tab/session before capturing the screenshot
            session.activate();
            byte[] data = session.captureScreenshot();
            write(file, data);
        }

        if (isDesktopSupported()) {
            getDesktop().open(file.toFile());//将截取的图片显示出来
        }
	}
	
	/**
	 * 不显示浏览器，截图
	 */
	/**保存到磁盘位置
	* <p>Title: testshot1</p>
	* <p>Description: </p>
	* @throws IOException
	*/
	@Test
	public void testshotHeadLess() throws IOException {
		int WAIT_TIMEOUT = 90 * 1000; // 10 seconds
		Launcher launcher = new Launcher();
		Path path = Paths.get("c:\\snapshot1");//磁盘位置
		String ts = System.currentTimeMillis()+"";//时间戳命名
		
		//String url = "https://www.eastbay.com/product/model:200618/sku:Q18127/adidas-originals-roller-crew-socks-mens/black/grey/";
		Path file = createTempFile(path, "screenshot-"+ts, ".png");
		 
		//
		ArrayList<String> command = new ArrayList<String>(); //不显示google 浏览器 
		command.add("--headless"); 
		
        try (SessionFactory factory = launcher.launch(command);
                            Session session = factory.create()) {
//            session.navigate(url);
            session.navigate("https://news.ycombinator.com");//
//          session.waitDocumentReady();
            session.waitDocumentReady(WAIT_TIMEOUT);
            // activate the tab/session before capturing the screenshot
            session.activate();
            byte[] data = session.captureScreenshot();
            
            write(file, data);
            //################################获取要抓取的页面html
            //String content = (String) session.getContent(); 
			//System.out.println(content); 
			//###########################已经拿到渲染后的html，下面就可以用jsoup去抓取想要的数据了
        }

        if (isDesktopSupported()) {
            getDesktop().open(file.toFile());//将截取的图片显示出来
        }
	}
	
	
	// 根据谷歌浏览器的位置截图 
	//  Ubuntu 安装路径为   /usr/bin/google-chrome-stable
	@Test
	public void screen() throws IOException {
		
		//
		ArrayList<String> command = new ArrayList<String>(); //不显示google 浏览器 
		command.add("--headless"); 
		
		Launcher launcher = new Launcher();
		int WAIT_TIMEOUT = 30 * 1000; // 10 seconds
//		Path path = Paths.get("/usr/bin/google-chrome-stable");
		Path path = Paths.get("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
		Path path2 = Paths.get("D:\\");

		Path file = createTempFile(path2, "screenshot", ".png");

		try (SessionFactory factory = launcher.launch(path, command); 
				Session session = factory.create()) {
			session.navigate(
					"http://www.baidu.com");
//					"https://detail.tmall.com/item.htm?id=547121043892&spm=a21bo.7925826.192013.5.7c5a4c0dd2W2V4&track_params={%22jpFeedId%22:%222200000200313606615%22}&scm=1007.12846.65991.999999999999999&pvid=0f29fc35-e3fa-4b6a-bbbb-d643559de359");
			session.waitDocumentReady(WAIT_TIMEOUT);		
			session.waitDocumentReady();
			
			// activate the tab/session before capturing the screenshot
			 session.activate();
			byte[] data = session.captureScreenshot();
			write(file, data);
		}

		if (isDesktopSupported()) {
			 getDesktop().open(file.toFile());
		}
	}
	
	
}
