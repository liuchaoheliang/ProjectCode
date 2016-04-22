package com.froad.cbank.coremodule.framework.common.util.message;

import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;


/**
 *********************************************** 
 * @Copyright (c) by soap All right reserved.
 * @Create Date: 2014-2-26 上午10:24:39
 * @Create Author: hujz
 * @File Name: JXMessageInfo
 * @Function: csv文件行内容对应JavaBean
 * @Last version: 1.0
 * @Last Update Date:
 * @Change Log:
 ************************************************* 
 */
public class JXMessageInfo {

    /**
     * 错误信息编号
     */
    protected String id;
    /**
     * 错误信息类型
     */
    protected String category;
    /**
     * 错误信息内容
     */
    protected String message;

    /**
     * Get the value of message
     *
     * @return the value of message
     */
    public String getMessage() {
        return message;
    }

    /**
     * 对错误信息中的可变换内容进行处理
     * @param arguments 要替换错误信息中的内容
     * @return 已经组合好的错误信息
     */
    public String getMessage(String[] arguments) {
        String strMessage = getMessage();
        if (null != arguments && arguments.length > 0) {
            for (int i = 0; i < arguments.length; i++) {
                String strargument = arguments[i];
                String strReplaceFlag = "{" + i + "}";
                strMessage = StringUtil.replace(strMessage, strReplaceFlag, strargument);
            }
        }
        return strMessage;
    }

    /**
     * Set the value of message
     *
     * @param message new value of message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Get the value of category
     *
     * @return the value of category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Set the value of category
     *
     * @param category new value of category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Get the value of id
     *
     * @return the value of id
     */
    public String getId() {
        return id;
    }

    /**
     * Set the value of id
     *
     * @param id new value of id
     */
    public void setId(String id) {
        this.id = id;
    }
}
