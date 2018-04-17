package com.jnanlongen.screenshot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.util.ResourceUtils;

import com.jinanlongen.screenshot.utils.FileReadUtil;

public class TestFileReadUtil {

	/**
	 * 
	 */
	@Test
	public void readFile() {
		
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
            System.out.println(""+it2.next());           
        }
		
	}
	
	
	
	
	/**
	 * @throws FileNotFoundException 
	 * 
	 */
	@Test
	public void readFile2() throws FileNotFoundException {
		//获取 resources 目录下的 dataFile 文件夹下的 /ZH.txt文件
		//File file = ResourceUtils.getFile("classpath:dataFile/ZH.txt");
		
		 // 根据系统的实际情况选择目录分隔符（windows下是，linux下是/）
        String separator = File.separator;
        System.out.println("####:"+separator);
//      String directory = "src"+separator+"main"+separator+"resources" + separator + "data.txt";
//      String directory = "url" + separator + "data.txt";
        
        File datafile = ResourceUtils.getFile("classpath:dataFile"+separator+"data.txt");
        
        Map<String, List<String>> map = FileReadUtil.readFileByLines(datafile);
        
        /**
         * 遍历集合
         */
//        Iterator<Map.Entry<String, List<String>>> it = map.entrySet().iterator();  
//        while (it.hasNext()) {  
//            Map.Entry<String, List<String>> entry = it.next();  
//            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
//        }              
        
        ArrayList<String> zapposList= (ArrayList<String>) map.get("allurlList");
        //System.out.println("zapposList"+zapposList);
        Iterator<String> it2=zapposList.iterator();
        while(it2.hasNext()){
            System.out.println(""+it2.next());           
        }
		
	}
	
}
