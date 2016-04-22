/**
 * 包名：com.soap.sb.common.util.message
 * 类名：JXMessage
 * 兴华伟业研发中心
 */
package com.froad.cbank.coremodule.framework.common.util.message;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.froad.cbank.coremodule.framework.common.util.csv.CSVReader;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.common.util.web.WebContext;

/**
 *********************************************** 
 * @Copyright (c) by soap All right reserved.
 * @Create Date: 2014-2-26 上午10:25:30
 * @Create Author: hujz
 * @File Name: JXMessage
 * @Function: 从csv文件信息中获取内容
 * @Last version: 1.0
 * @Last Update Date:
 * @Change Log:
 ************************************************* 
 */
public class JXMessage {

    /**
     * 信息文件带路径名称
     */
    private static String strCsvFileName = "messageInfo.csv";
    /**
     * 保存的csv文件内容
     */
    public static Map<String, String[]> csvFileContentMap;

    /**
     * 解析csv文件
     */
    public static void parse() {
        if (null == csvFileContentMap) {
            csvFileContentMap = new HashMap<String, String[]>();
        } else {
            csvFileContentMap.clear();
        }
        String csvFilePath = WebContext.toHomeWEBINFPath() + File.separator + strCsvFileName;
        CSVReader csvReader = new CSVReader(csvFilePath);
        Vector<String[]> vector = csvReader.parseMessage();
        for (int i = 1; i < vector.size(); i++) {
            String[] strRowContents = (String[]) vector.elementAt(i);
            if(strRowContents==null||strRowContents.length==0){
                continue;
            }
            String messaggeId = strRowContents[0];
            csvFileContentMap.put(messaggeId, strRowContents);
        }

    }

    /**
     * 根据messageId获取信息JavaBean对象
     * @param messageId 错误信息Id
     * @return 行对象JavaBean
     */
    public static JXMessageInfo searchMessageInfo(String messageId) {
        JXMessageInfo messageInfo = null;
        if (!StringUtil.empty(messageId)) {
            messageInfo = new JXMessageInfo();
            String[] strRowContents = csvFileContentMap.get(messageId);
            if (null == strRowContents) {
                messageInfo.setId(messageId);
                messageInfo.setCategory("Error");
                messageInfo.setMessage("错误信息未定义");
            } else {
                messageInfo.setId(strRowContents[0]);
                messageInfo.setCategory(strRowContents[1]);
                messageInfo.setMessage(strRowContents[2]);
            }
        }

        return messageInfo;
    }
    
    public static void main(String[] args) {
		JXMessage.searchMessageInfo("");
	}
}
