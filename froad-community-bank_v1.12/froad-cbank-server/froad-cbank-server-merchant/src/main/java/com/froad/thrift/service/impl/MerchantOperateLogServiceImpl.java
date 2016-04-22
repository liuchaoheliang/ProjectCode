
/**  
 * @Title: MerchantOperateLogServiceImpl.java
 * @Package com.froad.thrift.service.impl
 * @Description: TODO
 * @author vania
 * @date 2015年4月8日
 */

package com.froad.thrift.service.impl;

import org.apache.thrift.TException;

import com.froad.logic.MerchantOperateLogLogic;
import com.froad.logic.impl.MerchantOperateLogLogicImpl;
import com.froad.po.MerchantOperateLog;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.MerchantOperateLogService;
import com.froad.thrift.vo.MerchantOperateLogPageVoRes;
import com.froad.thrift.vo.MerchantOperateLogVo;
import com.froad.thrift.vo.PageVo;
import com.froad.util.BeanUtil;

/**    
 * 
 * <p>Title: MerchantOperateLogServiceImpl.java</p>    
 * <p>Description: 描述 </p>   
 * @author vania      
 * @version 1.0    
 * @created 2015年4月8日 上午11:46:20   
 */

public class MerchantOperateLogServiceImpl extends BizMonitorBaseService implements MerchantOperateLogService.Iface{
	private MerchantOperateLogLogic merchantOperateLogLogic = new MerchantOperateLogLogicImpl();
	/**    
	 * 构造函数      
	 */
	public MerchantOperateLogServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	
	/**     
	 * 添加日志
	 * @param MerchantOperateLogVo
	 * @return
	 * @throws TException    
	 * @see com.froad.thrift.service.MerchantOperateLogService.Iface#addMerchantOperateLog(com.froad.thrift.vo.MerchantOperateLogVo)    
	 */	
	@Override
	public boolean addMerchantOperateLog(MerchantOperateLogVo MerchantOperateLogVo) throws TException {
		// TODO Auto-generated method stub
		MerchantOperateLog merchantOperatorLog = null;
		merchantOperatorLog = (MerchantOperateLog) BeanUtil.copyProperties(MerchantOperateLog.class, MerchantOperateLogVo);
		return merchantOperateLogLogic.addBankOperateLog(merchantOperatorLog);
	}

	
	/**     
	 * @param page
	 * @param MerchantOperateLogVo
	 * @return
	 * @throws TException    
	 * @see com.froad.thrift.service.MerchantOperateLogService.Iface#getMerchantOperateLogByPage(com.froad.thrift.vo.PageVo, com.froad.thrift.vo.MerchantOperateLogVo)    
	 */	
	@Override
	public MerchantOperateLogPageVoRes getMerchantOperateLogByPage(PageVo page, MerchantOperateLogVo MerchantOperateLogVo) throws TException {
		// TODO Auto-generated method stub
		return null;
	}
}