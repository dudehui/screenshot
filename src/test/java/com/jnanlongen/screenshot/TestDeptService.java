package com.jnanlongen.screenshot;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.jinanlongen.screenshot.ScreenshotApplication;
import com.jinanlongen.screenshot.service.UserService;

@SpringBootTest(classes = ScreenshotApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class TestDeptService {

	@Resource
	private UserService userService;
	
	@Test
	public void test1(){
		System.out.println(this.userService.findAllUser(1, 1));
	}
	
}
