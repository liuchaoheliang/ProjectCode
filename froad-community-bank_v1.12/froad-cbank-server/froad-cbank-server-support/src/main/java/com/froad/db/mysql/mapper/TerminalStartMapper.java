package com.froad.db.mysql.mapper;

import com.froad.po.TerminalStart;

/**
 * 
 * <p>@Title: TerminalStartMapper.java</p>
 * <p>Description: 描述 </p> 客户端启动页
 * @author f-road-ll 
 * @version 1.2
 * @created 2015年9月17日
 */
public interface TerminalStartMapper {


    /**
     * 增加 TerminalStart
     * @param TerminalStart
     * @return Long    影响条数
     */
	public Long addTerminalStart(TerminalStart terminalStart);


    /**
     * 修改 TerminalStart
     * @param TerminalStart
     * @return Boolean    是否成功
     */
	public Boolean updateTerminalStart(TerminalStart terminalStart);


    /**
     * 查询一个 TerminalStart 客户端启动页
     * @param TerminalStart
     * @return TerminalStart    返回结果
     */
	public TerminalStart findTerminalStart(TerminalStart terminalStart);




}