package com.jinanlongen.screenshot.utils;

import java.util.Random;
import java.util.regex.Pattern;

public class ScreenShotUtil {
	
	
	   private volatile static Cdp4jUtil cdp4jiInstance;
	    private ScreenShotUtil(){
	        System.out.println("ScreenShotUtil has loaded");
	    }
	    public static Cdp4jUtil getCdp4jInstance(){
	        if(cdp4jiInstance==null){
	            synchronized (Cdp4jUtil.class){
	                if(cdp4jiInstance==null){
	                	cdp4jiInstance=new Cdp4jUtil();
	                }
	            }
	        }
	        return cdp4jiInstance;
	    }
	

	          
    /**
     * url 检测判断 //判断是否为合法的 http URL 
     */              
     public static boolean urlCheck(String url) {
    	 url = url.replace(" ", ""); //去掉所有空格，包括首尾、中间
    	 //System.out.println("url:" +url);
    	 //System.out.println("length:" +url.length());
    	 if(url==null||url.length()<=0) {
    		 return false;
    	 }
    	String checkUrl="";
    	if(url.length()>50) {//对前50个字符串进行匹配即可
    		checkUrl = url.substring(0, 50);
        }else {
        	checkUrl = url;
        }
        Pattern pattern = Pattern  
                  .compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$");  
        boolean flag = pattern.matcher(checkUrl).matches();
        //System.out.println("result: "+ flag);
    	return flag; 
     }
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
