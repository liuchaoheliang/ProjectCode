package com.froad.CB.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.froad.CB.dao.PayDao;
import com.froad.CB.po.Pay;
import com.froad.CB.service.PayService;

@WebService(endpointInterface="com.froad.CB.service.PayService")
public class PayServiceImpl implements PayService{

	private static final Logger logger=Logger.getLogger(PayServiceImpl.class);
	
	private PayDao payDao;
	
	@Override
	public Integer addPay(Pay pay) {
		if(pay==null){
			logger.error("参数为空，添加支付失败");
			return null;
		}
		return payDao.addPay(pay);
	}

	@Override
	public void deleteById(Integer id) {
		if(id==null){
			logger.error("编号为空，删除失败");
			return;
		}
		payDao.deleteById(id);
	}

	@Override
	public Pay getPayById(Integer id) {
		if(id==null){
			logger.error("编号为空，查询失败");
			return null;
		}
		return payDao.getPayById(id);
	}

	@Override
	public void updateById(Pay pay) {
		if(pay==null||pay.getId()==null){
			logger.error("参数为空，更新失败");
			return;
		}
		payDao.updateById(pay);
	}

	@Override
	public void updateStateById(Integer id, String state) {
		if(id==null||state==null||"".equals(state)){
			logger.error("参数为空，更新状态失败");
			return;
		}
		payDao.updateStateById(id, state);
	}

	public void setPayDao(PayDao payDao) {
		this.payDao = payDao;
	}

	@Override
	public void updatePay(Pay pay) {
		if(pay==null||pay.getTransId()==null||pay.getState()==null){
			logger.error("参数不完整，修改失败");
			return;
		}
		payDao.updatePay(pay);
	}

	@Override
	public List<Pay> getPayByTransId(String transId) {
		if(transId==null||"".equals(transId)){
			return null;
		}
		return payDao.getPayByTransId(transId);
	}

    public String aliPayResultCheck(String transId)
    {
        String  result = "";
        if(StringUtils.isNotBlank(transId))
        {
        result = payDao.aliPayResultCheck(transId);
        }
        	return result;
    }


}
