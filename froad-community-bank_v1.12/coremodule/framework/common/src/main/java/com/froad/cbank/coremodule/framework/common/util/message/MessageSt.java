package com.froad.cbank.coremodule.framework.common.util.message;

/**
 *********************************************** 
 * @Copyright (c) by soap All right reserved.
 * @Create Date: 2014-2-26 上午10:25:45
 * @Create Author: hujz
 * @File Name: MessageSt
 * @Function: TODO(用一句话描述该类做什么)
 * @Last version: 1.0
 * @Last Update Date:
 * @Change Log:
 ************************************************* 
 */
public class MessageSt {

    /**
     * "信息"类型常数
     */
    public static final String ERR_INFO = "Info";
    /**
     * "警告"类型常数
     */
    public static final String ERR_WARN = "Warn";
    /**
     * "错误"类型常数
     */
    public static final String ERR_ERROR = "Error";
    /**
     * 错误信息编号
     */
    private String messageId;
    /**
     * 错误信息类型
     */
    protected String messageCategory;
    /**
     * 错误信息内容
     */
    private String messageDT;

    /**
     * Get the value of messageDT
     *
     * @return the value of messageDT
     */
    public String getMessageDT() {
        return messageDT;
    }

    /**
     * Set the value of messageDT
     *
     * @param messageDT new value of messageDT
     */
    public void setMessageDT(String messageDT) {
        this.messageDT = messageDT;
    }

    /**
     * Get the value of messageCategory
     *
     * @return the value of messageCategory
     */
    public String getMessageCategory() {
        return messageCategory;
    }

    /**
     * Set the value of messageCategory
     *
     * @param messageCategory new value of messageCategory
     */
    public void setMessageCategory(String messageCategory) {
        this.messageCategory = messageCategory;
    }

    /**
     * Get the value of messageId
     *
     * @return the value of messageId
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * Set the value of messageId
     *
     * @param messageId new value of messageId
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}
