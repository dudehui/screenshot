package com.jnanlongen.screenshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class URLTest {
	// separate 拆分
	
	 private String[] removeArrayEmptyTextBackNewArray(String[] strArray) {
	        List<String> strList= Arrays.asList(strArray);
	        List<String> strListNew=new ArrayList<>();
	        for (int i = 0; i <strList.size(); i++) {
	            if (strList.get(i)!=null&&!strList.get(i).equals("")){
	                strListNew.add(strList.get(i));
	            }
	        }
	        String[] strNewArray = strListNew.toArray(new String[strListNew.size()]);
	        return   strNewArray;
	    }
	
	@Test
	public void testSeprate() {
//		   https://www.eastbay.com/product/model:200618/sku:Q18127/adidas-originals-roller-crew-socks-mens/black/grey
//			https://www.zappos.com/p/nike-cotton-cushion-crew-with-moisture-management-3-pair-pack-black-white/product/8068305/color/151
//			https://www.finishline.com/store/product/womens-puma-popcat-slide-sandals/prod2772299?styleId=36384702&colorId=002
//			https://support.mozilla.org/zh-CN/kb/Firefox%20%E4%BD%BF%E7%94%A8%E5%85%A5%E9%97%A8
//			https://www.baidu.com
		String url = "https://www.finishline.com/store/product/womens-puma-popcat-slide-sandals/prod2772299?styleId=36384702&colorId=002";   
         
//		 Java 使用split函数分割url成为单词
//		    String url = "https://sports.sina.com.cn/";  
		    String [] sub_url_array = url.split("[/ : . - _ # %]");  
		    int sid;  
		    for(sid=0;sid<sub_url_array.length;sid++)  
		    {  
//		        if(sub_url_array[sid].equals("")) continue;  
//		        System.out.println(sub_url_array[sid]);  
		    }  
		    
		    sub_url_array = removeArrayEmptyTextBackNewArray(sub_url_array);
		    System.out.println(sub_url_array[2]);
	}	
	
	{
		 String url = "https://www.iteye.com/problems/new&dfsdf=aa";	
		 String [] sub_url_array = url.split("[/ : . - _ # %]");     	   
	     		String siteName = sub_url_array[2];
	      	    /**
	      	     * 站点名称包括：
					zappos
					eastbay
					finishline
					..............
	      	     */
	      	    switch (siteName) {
				case "zappos":
					
					break;
                case "eastbay":
					
					break;
                case "finishline":
					
					break;
				default:
					break;
				}
	        }  
	

}
