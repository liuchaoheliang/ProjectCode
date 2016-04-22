package com.froad.cbank.coremodule.framework.common.util.csv;

import com.froad.cbank.coremodule.framework.common.util.type.EncodingUtil;

/**
 *********************************************** 
 * @Copyright (c) by soap All right reserved.
 * @Create Date: 2014-2-26 上午10:22:44
 * @Create Author: hujz
 * @File Name: CsvFile
 * @Function: csv文件处理超类
 * @Last version: 1.0
 * @Last Update Date:
 * @Change Log:
 ************************************************* 
 */
public class CsvFile {

    /**
     * csv文件解析标志常数
     */
    public static final int PARSETYPE_FILEPARSER = 1;
    /**
     * csv的字符串解析标志常数
     */
    public static final int PARSETYPE_STRINGPARSER = 0;
    /**
     * csv文件内容分割标志
     */
    private char separator = ',';
    /**
     * 内容双引号
     */
    private char enclosedBy = '"';
    /**
     * 受双引号影响的标志位
     */
    private boolean isEnclosedByEffective = true;
    /**
     * 编码
     */
    private String encoding = EncodingUtil.CHARSET_GB2312; // "GB2312";


    ///////////////////////////////////////////////////////////////////
    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public void setSeparator(char ch) {
        if (ch == '"') {
            return;
        }
        this.separator = ch;
    }

    public char getSeparator() {
        return this.separator;
    }

    public char getEnclosedBy() {
        return enclosedBy;
    }

    public void setEnclosedBy(char enclosedBy) {
        this.enclosedBy = enclosedBy;
    }

    public boolean isEnclosedByEffective() {
        return isEnclosedByEffective;
    }

    public void setEnclosedByEffective(boolean enclosedByEffective) {
        isEnclosedByEffective = enclosedByEffective;
    }
}
