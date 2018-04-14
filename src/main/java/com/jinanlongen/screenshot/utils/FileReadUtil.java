package com.jinanlongen.screenshot.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class FileReadUtil {
	
	/**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     * key 站点名
     * value urlList
     */
    public static Map<String, List<String>> readFileByLines(String fileName) {
    	Map<String, List<String>> rtMap =  new HashMap<>();
    	ArrayList<String> allurlList=new ArrayList<String>();//所有的url
    	ArrayList<String> zapposList=new ArrayList<String>();
    	ArrayList<String> eastbayList=new ArrayList<String>();
    	ArrayList<String> finishlineList=new ArrayList<String>();
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            //System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                //System.out.println("line " + line + ": " + tempString);
            	//判断是否为合法的 http URL 
            	  Pattern pattern = Pattern  
                          .compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$");  
                boolean flag = pattern.matcher(tempString).matches();
               if(flag) {
            	   allurlList.add(tempString);//将url 放入 list
                   //对 tempString 进行截取字符串，根据site划入不同的 list                	
                   //String [] sub_url_array = tempString.split("[/ : . - _ # %]");     	
                   //sub_url_array = removeArrayEmptyTextBackNewArray(sub_url_array);
//                   System.out.println("#####:"+tempString);
//   	     		String siteName = sub_url_array[2];  //----_获取了站点名
//           	       //System.out.println("siteName " + siteName);
//           	      	    /**
//           	      	     * 站点名称包括：
//           					zappos
//           					eastbay
//           					finishline
//           					..............
//           	      	     */
//           	      	    switch (siteName) {
//           				case "zappos":
//           					zapposList.add(tempString);
//           					break;
//                           case "eastbay":
//                           	eastbayList.add(tempString);
//           					break;
//                           case "finishline":
//                           	finishlineList.add(tempString);
//           					break;
//           				default:        					
//           					break;
//           				}
               }else {
            	   //.error("data error: url is not allowed");
               }
        	                          
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        rtMap.put("allurlList", allurlList);
        rtMap.put("zappos", zapposList);
        rtMap.put("eastbay", eastbayList);
        rtMap.put("finishline", finishlineList);
        return rtMap;
    }
    
    
    private static String[] removeArrayEmptyTextBackNewArray(String[] strArray) {
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
	
    
    
    //@Test
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
}
