package com.froad.cbank.coremodule.framework.common.util.message;
 

/**
 *********************************************** 
 * @Copyright (c) by soap All right reserved.
 * @Create Date: 2014-2-26 上午10:26:04
 * @Create Author: hujz
 * @File Name: MessageUtil
 * @Function: TODO(用一句话描述该类做什么)
 * @Last version: 1.0
 * @Last Update Date:
 * @Change Log:
 ************************************************* 
 */
public class MessageUtil {

    /**
     * 信息编号
     */
    private static final String ID = "EM000";
    /**
     * 错误类型
     */
    private static final String CATEGORY = "Error";
    /**
     * 错误内容
     */
    private static final String MESSAGEDT = "从Csv文件中取得信息失败";

    /**
     * 根据messageId获取MessageSt对象
     * @param messageId 信息编号
     * @return 含有错误信息的MessageSt
     */
    public static MessageSt getMessage(String messageId) {
        MessageSt messageSt = new MessageSt();
        JXMessageInfo info = JXMessage.searchMessageInfo(messageId);
        if (null != info) {
            String id = info.getId();
            String category = info.getCategory();
            String messageDt = info.getMessage();
            messageSt.setMessageId(id);
            messageSt.setMessageCategory(category);
            messageSt.setMessageDT(messageDt);
        } else {
            messageSt.setMessageId(ID);
            messageSt.setMessageCategory(CATEGORY);
            messageSt.setMessageDT(MESSAGEDT);
        } 
        return messageSt;
    }
    /**
     * 根据messageId获取MessageSt对象
     * @param messageId 信息编号
     * @param arguments 需要替换的内容值
     * @return 含有错误信息的MessageSt
     */
    public static MessageSt getMessage(String messageId,String[] arguments) {
        MessageSt messageSt = new MessageSt();
        JXMessageInfo info = JXMessage.searchMessageInfo(messageId);
        if (null != info) {
            String id = info.getId();
            String category = info.getCategory();
            String messageDt = info.getMessage(arguments);
            messageSt.setMessageId(id);
            messageSt.setMessageCategory(category);
            messageSt.setMessageDT(messageDt);
        } else {
            messageSt.setMessageId(ID);
            messageSt.setMessageCategory(CATEGORY);
            messageSt.setMessageDT(MESSAGEDT);
        }
        return messageSt;
    }
}
