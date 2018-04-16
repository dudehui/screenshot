package com.jinanlongen.screenshot.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 
 * 类名称：TimerTask   
 * 类描述：定时器任务
 * 创建人：yunfei.han
 * 创建时间：Aug 29, 2016 10:56:27 AM      
 * @version  V1.0
 *
 */
@Component
public class TimerTask {
  /**
   * 每天22点30启动任务
   */
  @Scheduled(cron = "0 30 22 ? * *")
  public void test1()
  {
      System.out.println("job1 开始执行..."+getTime());
  } 
  //@Scheduled(cron = "0/2 * * * * ?")//每隔2秒隔行一次 
  public void test2()
  {
     System.out.println("job2 开始执行");
  } 
  
  /**
	 * 获取当前时间
	 * @param
	 * @return
	 */		
	public static String getTime(){
		Date date=new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return df.format(date);
	}
}
