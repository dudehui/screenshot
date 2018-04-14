package com.jinanlongen.screenshot.utils;

import java.util.Random;

public class ScreenShotUtil {

	/**生成图片名称的随机数： 时间戳+9伪随机数
	 * @return
	 */
	public static String getImageName() {
		
		String ts = System.currentTimeMillis()+"";//图片名成按照：时间戳命名,不会重复
		 Random rd = new Random();  
	     String[] radmon = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };  
	     // for(int j = 0; j < 100; j++) {  
	     StringBuffer sb = new StringBuffer();  
	       for (int i = 0; i < 9; i++) {  
	            String s = radmon[rd.nextInt(10)];  //0-9的随机数
	            sb.append(s);  
	        }  
		return ts+"-"+sb;
	}
	
}
