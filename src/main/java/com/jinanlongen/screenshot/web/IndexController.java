package com.jinanlongen.screenshot.web;

import org.apache.catalina.connector.Request;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {
	
	// 日志
    protected Log log = LogFactory.getLog(this.getClass());
	
    /**
     * http://localhost:9081/
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }
    
    
    
}
