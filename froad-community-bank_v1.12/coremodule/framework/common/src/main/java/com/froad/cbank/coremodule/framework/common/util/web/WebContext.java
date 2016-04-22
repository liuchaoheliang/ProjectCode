package com.froad.cbank.coremodule.framework.common.util.web;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.ProtectionDomain;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.froad.cbank.coremodule.framework.common.util.type.ObjectUtil;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;

public class WebContext {

    /**
     * 带类名的全包名
     */
    public final static String CONTEXT = ObjectUtil.getPackageName(
            WebContext.class.getName()) + "." + "WebContext";
   
    /**
     * 通过请求设置用户登录Session
     * @param reqeust 请求
     * @return WebContext对象
     */
    public static WebContext getWebContext(HttpServletRequest reqeust) {
        if (reqeust == null) {
            return null;
        }
        HttpSession session = reqeust.getSession();
        if (session == null) {
            return null;
        }
        WebContext wc = (WebContext) session.getAttribute(CONTEXT);
        return wc;
    }

    /**
     * 猜测程序的主目录
     * @return 应用程序运行的根目录
     */
    public static String toHomeRootPath() {
        String theProductHome = "";
    	Class<?> c = new WebContext().getClass();
    	URL url = c.getClassLoader().getResource("");
    	File file = new File(url.getPath());
    	theProductHome = file.getParentFile().getParent() + File.separator;
//        String webRootUrl = System.getProperty("server.root");
//        System.out.println(webRootUrl);

//        if(!StringUtil.empty(webRootUrl)) {
//            theProductHome = System.getProperty("user.dir") + File.separator+ webRootUrl+ File.separator;
//        }else {
//            String relativePath = "../../../../../../../../";
//            try {
//                ProtectionDomain pd = WebContext.class.getProtectionDomain();
//                URL homeURL = pd.getCodeSource().getLocation();
//                String homePath = homeURL.getFile();
//                File homedir = new File(homePath, relativePath);
//                homePath = homedir.getCanonicalPath();
//                theProductHome = homePath + File.separator;
//            }catch(IOException e) {
//                theProductHome = null;
//            }
//        }
        
        //D:\server\apache-tomcat-6.0.18\webapps\manage\
        return theProductHome;
    }
    
    /**
     * 猜测程序的WEB-INF目录
     * @return 应用程序运行的WEB-INF目录
     */
    public static String toHomeWEBINFPath() {
        String theProductHome=toHomeRootPath()+"WEB-INF"+File.separator;
        return theProductHome;
    }
}
