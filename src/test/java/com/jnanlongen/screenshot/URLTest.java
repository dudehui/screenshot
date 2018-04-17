package com.jnanlongen.screenshot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.junit.Test;

import com.jinanlongen.screenshot.utils.FileReadUtil;
import com.jinanlongen.screenshot.utils.ScreenShotUtil;

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
//			http://www.baidu.com
		
//		String url = "https://www.finishline.com/store/product/womens-puma-popcat-slide-sandals/prod2772299?styleId=36384702&colorId=002";   
//      String url = "http://www.baidu.com"; 
        String url = "https://www.finishline.com/store/browse/productDetail.jsp?productId=prod2778048&brand_name=PUMA&styleId=19123101&colorId=001&cmp=ppc-pla-google-Casual-Puma%20Women%27s%20x%20Rihanna%20Fenty%20Trainer%20Mid%20Geo%20Casual%20Shoes%2C%20Black&rmatt=tsid:1044134%7ccid:204828221%7cagid:9884120981%7ctid:aud-128242253526:pla-97089901541%7ccrid:46146158021%7cnw:g%7crnd:420252512830585430%7cdvc:c%7cadp:1o4%7cmt:%7cloc:9032008&gclid=EAIaIQobChMI3o7m5qio2gIViInICh2ifAgoEA0YBCABEgLLhvD_BwE&gclsrc=aw.ds"; 
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
	
	
	/**
	 * 【Java】利用正则表达式判断是否为网址 
	 */
	@Test
	public void urlRegTest (){
		String url1 = "http://www.xx.com";  
        String url2 = "w.xx.com";  
        String url3 = "http://w.xx.com";  
        String url4 = "ssss";  
        String url5 = "w.xx.com"; 
        String url6 = "https://www.nike.com/t/jordan-aj1-lover-xx-womens-shoe-qG35N2"; 
        String url7 = "https://www.finishline.com/store/browse/productDetail.jsp?productId=prod2778048&brand_name=PUMA&styleId=19123101&colorId=001&cmp=ppc-pla-google-Casual-Puma%20Women%27s%20x%20Rihanna%20Fenty%20Trainer%20Mid%20Geo%20Casual%20Shoes%2C%20Black&rmatt=tsid:1044134%7ccid:204828221%7cagid:9884120981%7ctid:aud-128242253526:pla-97089901541%7ccrid:46146158021%7cnw:g%7crnd:420252512830585430%7cdvc:c%7cadp:1o4%7cmt:%7cloc:9032008&gclid=EAIaIQobChMI3o7m5qio2gIViInICh2ifAgoEA0YBCABEgLLhvD_BwE&gclsrc=aw.ds"; 

        Pattern pattern = Pattern  
                .compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$");  
//        System.out.println(pattern.matcher(url1).matches());  
//        System.out.println(pattern.matcher(url2).matches());  
//        System.out.println(pattern.matcher(url3).matches());  
//        System.out.println(pattern.matcher(url4).matches());  
//        System.out.println(pattern.matcher(url5).matches());  
//        System.out.println(pattern.matcher(url6).matches()); 
        if(url7.length()<=100) {
        	 
        }else {
            url7 = url7.substring(0, 100);
         }
        System.out.println(pattern.matcher(url7).matches()); 
	}
	
	@Test
    public void testReadURLByline() {
    	 // 根据系统的实际情况选择目录分隔符（windows下是，linux下是/）
        String separator = File.separator;
        String directory = "url" + separator + "file.txt";
        Map<String, List<String>> map = FileReadUtil.readFileByLines(directory);
        
        /**
         * 遍历集合
         */
        Iterator<Map.Entry<String, List<String>>> it = map.entrySet().iterator();  
        while (it.hasNext()) {  
            Map.Entry<String, List<String>> entry = it.next();  
            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());  
        }
              
        
        /*ArrayList<String> zapposList= (ArrayList<String>) map.get("zappos");
        //System.out.println("zapposList"+zapposList);
        Iterator<String> it2=zapposList.iterator();
        while(it2.hasNext()){
            System.out.println(it2.next());
        }*/
    }
	
	
	/**
	 * 
	 */
	@Test
	public void readFileCheckURL() {
		
		 // 根据系统的实际情况选择目录分隔符（windows下是，linux下是/）
        String separator = File.separator;
        String directory = "url" + separator + "data.txt";
        Map<String, List<String>> map = FileReadUtil.readFileByLines(directory);
        
        /**
         * 遍历集合
         */
        Iterator<Map.Entry<String, List<String>>> it = map.entrySet().iterator();  
//        while (it.hasNext()) {  
//            Map.Entry<String, List<String>> entry = it.next();  
//            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
//        }
              
        
        ArrayList<String> zapposList= (ArrayList<String>) map.get("allurlList");
        //System.out.println("zapposList"+zapposList);
        Iterator<String> it2=zapposList.iterator();
        while(it2.hasNext()){
            //System.out.println(""+it2.next());
            System.out.println("check result:"+ScreenShotUtil.urlCheck(it2.next()));
        }
		
	}
	
	
	@Test
	public void testString(){
		//https://www.thegoodwillout.com/puma-x-shantell-martin-wmns-platform-strap-white-black-  365895-01
		String str = "https://www.thegoodwillout.com/puma-x-shantell-martin-wmns-platform-strap-white-black-  365895-01";
	    
		str = str.replace(" ", ""); //去掉所有空格，包括首尾、中间

		//String str = " hell o ";
		//String str2 = str.replaceAll(" ", "");
		System.out.println(str);		
		
	}
	
	//站点名称分类
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
