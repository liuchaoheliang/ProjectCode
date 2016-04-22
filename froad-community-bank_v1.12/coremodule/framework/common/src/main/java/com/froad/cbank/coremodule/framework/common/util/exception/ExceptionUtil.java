package com.froad.cbank.coremodule.framework.common.util.exception;

import com.froad.cbank.coremodule.framework.common.util.message.MessageSt;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;

/**
 *********************************************** 
 * @Copyright (c) by soap All right reserved.
 * @Create Date: 2014-2-26 上午10:22:57
 * @Create Author: hujz
 * @File Name: ExceptionUtil
 * @Function: 对错误类进行封装
 * @Last version: 1.0
 * @Last Update Date:
 * @Change Log:
 ************************************************* 
 */
public class ExceptionUtil extends Throwable {

    /**
     * 序列化时为了保持版本的兼容性，即在版本升级时反序列化仍保持对象的唯一性
     */
    private static final long serialVersionUID = -3155176651169390164L;
    /**
     * 错误信息JavaBean
     */
    private MessageSt messageSt;

    /**
     * Get the value of messageSt
     *
     * @return the value of messageSt
     */
    public MessageSt getMessageSt() {
        return messageSt;
    }

    /**
     * Set the value of messageSt
     *
     * @param messageSt new value of messageSt
     */
    public void setMessageSt(MessageSt messageSt) {
        this.messageSt = messageSt;
    }

    /**
     * 构造函数
     * @param cause 错误对象
     */
    public ExceptionUtil(Throwable cause) {
        initCause(cause);
    }

    /**
     * 构造函数
     * @param messageSt 错误信息JavaBean
     */
    public ExceptionUtil(MessageSt messageSt) {
        super(messageSt.getMessageDT());
        this.messageSt = messageSt;
    }

    /**
     * 构造函数
     * @param cause 错误对象
     * @param messageSt 错误信息JavaBean
     */
    public ExceptionUtil(Throwable cause, MessageSt messageSt) {
        super(messageSt.getMessageDT());
        initCause(cause);
        this.messageSt = messageSt;
    }

    /**
     * 获取错误信息的Id
     * @return 错误信息的Id
     */
    public String getMessageId() {
        String strId = messageSt.getMessageId();
        if (StringUtil.empty(strId)) {
            strId = "";
        }
        return strId;
    }

    /**
     * 错误信息重新组合
     * @return 错误信息
     */
    @Override
    public String toString() {
        String className = getClass().getName();
        String strContent = "";
        if (null != messageSt) {
            strContent = messageSt.getMessageDT();
        }
        StringBuilder builder = new StringBuilder(className);
        builder.append(":");
        builder.append(strContent);
        return builder.toString();
    }
}
