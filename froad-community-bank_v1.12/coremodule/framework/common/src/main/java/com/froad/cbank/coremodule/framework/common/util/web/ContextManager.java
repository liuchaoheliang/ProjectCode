package com.froad.cbank.coremodule.framework.common.util.web;
 
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.froad.cbank.coremodule.framework.common.util.message.JXMessage;

/**
 *********************************************** 
 * @Copyright (c) by soap All right reserved.
 * @Create Date: 2014-2-26 上午10:28:42
 * @Create Author: hujz
 * @File Name: ContextManager
 * @Function: 应用程序启动时,首先执行该类,用于初始化一些数据或加载一些应用数据到缓存中
 * @Last version: 1.0
 * @Last Update Date:
 * @Change Log:
 ************************************************* 
 */
public class ContextManager implements ServletContextListener {

    /**
     * 应用程序启动时,信息加载
     * 
     * @param sce 用于通知web应用程序中servlet上下文的改变
     */
    public void contextInitialized(ServletContextEvent sce) {
        
        // 1:把错误信息文件的内容装载到内存
        System.out.println("☆  START:装载日志信息文件messageInfo.csv开始");
        initCSVMessageInfo();
        System.out.println("☆  END:装载日志信息文件messageInfo.csv文件结束");
    }



    /**
     * 把错误信息文件的内容装载到内存
     */
    private void initCSVMessageInfo() {
        JXMessage.parse();
    }

    /**
     * 停止应用程序时系统自动调用该方法
     * 
     * @param sce 用于通知web应用程序中servlet上下文的改变
     */
    public void contextDestroyed(ServletContextEvent sce) {
       
    }

}
