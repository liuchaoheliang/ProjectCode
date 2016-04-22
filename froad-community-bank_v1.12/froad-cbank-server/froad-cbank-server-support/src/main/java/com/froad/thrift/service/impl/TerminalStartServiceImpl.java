package com.froad.thrift.service.impl;


import org.apache.thrift.TException;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.TerminalStartLogic;
import com.froad.logic.impl.TerminalStartLogicImpl;
import com.froad.po.TerminalStart;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.vo.TerminalStartVo;
import com.froad.util.Checker;
import com.froad.thrift.service.TerminalStartService;


/**
 * 
 * <p>@Title: TerminalStartServiceImpl.java</p>
 * <p>Description: 描述 </p>  客户端启动页服务接口Service类
 * @author f-road-ll 
 * @version 1.2
 * @created 2015年9月16日
 */
public class TerminalStartServiceImpl extends BizMonitorBaseService implements TerminalStartService.Iface {
	private TerminalStartLogic terminalStartLogic = new TerminalStartLogicImpl();
	public TerminalStartServiceImpl(String name, String version){
		super(name, version);
	}
	
	 /**
     * 客户端启动页查询
     * @param clientId 客户端id
     * @param appType 客户端类型  1-个人 2-商户
     * @param terminalType 终端类型 1-pc 2-andriod 3-ios
     * @return 启动页详情 TerminalStartVo
     */
	@Override
	public TerminalStartVo getTerminalStart(String clientId, String appType,String terminalType) throws TException {
		if(Checker.isEmpty(clientId) || Checker.isEmpty(appType) || Checker.isEmpty(terminalType)){
			LogCvt.error(ResultCode.notAllParameters.getMsg());
			return new TerminalStartVo();
		}
		
		TerminalStartVo terminalStartVo = null;
		try {
			TerminalStart  terminalStart = terminalStartLogic.getTerminalStart(clientId, appType, terminalType);
			if(Checker.isNotEmpty(terminalStart)){
				terminalStartVo = new TerminalStartVo();
				terminalStartVo.setClientId(clientId);
				terminalStartVo.setAppType(appType);
				terminalStartVo.setTerminalType(terminalType);
				terminalStartVo.setImageId(terminalStart.getImageId());
				terminalStartVo.setImagePath(terminalStart.getImagePath());
			}else{
				terminalStartVo = new TerminalStartVo();
				terminalStartVo.setImageId("0");
			}
			
			
		} catch (Exception e) {
			LogCvt.error("客户端启动页查询异常"+e.getMessage(),e);
		}
		
		
		return terminalStartVo==null?new TerminalStartVo():terminalStartVo;
	}
	
	
}
