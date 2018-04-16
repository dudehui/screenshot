package snapshot.task;

import java.io.File;
import java.net.URL;

import com.jinanlongen.screenshot.utils.ScreenShotUtil;

import snapshot.model.Page;

/**
 * 截图任务处理
 * @author Administrator
 *
 */
public class SnapshotProcessor extends AbstractTaskProcessor<Page> {
    // 调用IECapt.exe的windows指令
    //private String exec;
    // 图片根目录
    private String imageRoot;
    // 图片处理单次等待时间
    private Long time = 10000L;//10s
    // 图片处理超时时间
    private Long timeout = 30000L;//30s

    /**
     * 任务处理逻辑 * * @param page * 传入待处理任务对象
     */
    public void process(Page page) {
        if (page != null) {
            String imagePath = imageRoot + page.getFileName() + ".png";
            String[] params = {page.getUrlName(), imagePath};            
//            params[0]; //url
//            params[1];//path
            		
            //String execAll = exec + " " + params[0] + " " + params[1];//==========调用exe截图
            try {
                //log.debug("Execute cmd \"" + execAll + "\"");
                File image = new File(imagePath);
                URL url = new URL(params[0]);
                
                String dirpath = imageRoot;//图片存放那个路径
                String imageurl = params[0];
                String imagename = page.getFileName();
                //Process proc = null;//============================================调用exe截图
                try {
// 防止抓取到404等页面,事先尝试连接本次处理所对应的URL取得状态值
                    //HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    //int responseCode = conn.getResponseCode();
                    //log.debug("Connect to " + params[0] + " successfully, response code " + responseCode);
// 如果该URL相应状态200则执行IECapt.exe抓取快照
                    //if (responseCode == 200) {
//##########################################截图功能#############################################  
                   //proc = Runtime.getRuntime().exec(execAll);//==========================调用exe截图
                        long allTime = 0;
// 检查文件是否已经创建
// 防止因网络不畅通等原因使得IECapt抓取图片过慢这里不采用waitFor方式
                        
                        //************************ //使用谷歌浏览器截图
                       
                          //2.1 屏幕截图功能
                        boolean isUrlFlag = ScreenShotUtil.urlCheck(imageurl);
                       	log.info("-----------url check result------------:"+isUrlFlag);
                       	if(isUrlFlag) {
                       		boolean flag = ScreenShotUtil.getCdp4jInstance().doScreenShot(dirpath, imageurl, imagename);
                		    if(flag) {
                		    	log.info("时间点："+System.currentTimeMillis()+" #######图片截取:"+"success"+" ######  "+url);
                		    }else {
                		    	log.error("时间点："+System.currentTimeMillis()+" #######图片截取:"+"error"+"  ######"+url);
                		    }                		   
                       	}else {
                       		log.info("-----------url check result is false, -----URL格式不正确-------:"+isUrlFlag);
                       	}
                        //************************ //使用谷歌浏览器截图
                        
                        do {
                            Thread.sleep(time);
                            allTime += time;
                            if (allTime >= this.timeout) {
// 超时
                                log.debug("Create image timeout.");
                                break;
                            }
                        } while (!image.isFile());
// 强制停止该程序
                        //proc.destroy();//==========================调用exe截图
// 等待程序退出返回值
                        //int exitValue = proc.waitFor();//==========================调用exe截图
//                        if (image.isFile()) {
//                            log.debug("Create image \"" + imagePath + "\" by " + allTime + " ms successfully.");
//                        }
                        //log.debug(proc + " destoried, exit code " + exitValue + ".");//==========================调用exe截图
               
//##########################################截图功能#############################################     	
                    //} else {
                    //    log.warn("\"" + params[0] + "\" response code " + responseCode);
                    //}
                } catch (Exception ex) {
                    log.error("", ex);
                }
            } catch (Exception ex) {
                log.error("", ex);
            }
        }
    }

    /* * (non-Javadoc) * @see com.futurebnc.myex.taskmgr.task.AbstractTaskProcessor#onDestory() */
    public void onDestory() {
    }

    /**
     * @param exec * the exec to set
     */
//    public void setExec(String exec) {
//        this.exec = exec;
//    }

    /**
     * @param imageRoot * the imageRoot to set
     */
    public void setImageRoot(String imageRoot) {
        this.imageRoot = imageRoot;
        File dir = new File(this.imageRoot);
        if (!dir.isDirectory()) {
            dir.mkdirs();
        }
    }

    /**
     * @param time * the time to set
     */
    public void setTime(Long time) {
        this.time = time;
    }

    /**
     * @param timeout the timeout to set
     */
    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }
}