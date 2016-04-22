package com.froad.CB.service.impl;

import java.util.List;
import javax.jws.WebService;
import org.apache.log4j.Logger;
import com.froad.CB.AppException.AppException;
import com.froad.CB.common.Command;
import com.froad.CB.dao.tran.PointsCurrencyFormulaDAO;
import com.froad.CB.po.transaction.Points;
import com.froad.CB.po.transaction.PointsCurrencyFormula;
import com.froad.CB.service.PointService;
import com.froad.CB.thirdparty.PointsFunc;
import com.froad.util.Assert;

@WebService(endpointInterface="com.froad.CB.service.PointService")
public class PointServiceImpl implements PointService{

	private static final Logger logger=Logger.getLogger(PointServiceImpl.class);
	
	private PointsCurrencyFormulaDAO pointsCurrencyFormulaDAO;
	
	
	@Override
	public Points queryPoints(Points points) throws Exception {
		if(points==null){
			logger.error("查询积分的参数为空");
			throw new Exception("查询积分的参数为空");
		}
		return PointsFunc.queryPoints(points);
	}

	@Override
	public PointsCurrencyFormula getPointsCurrencyFormuleByMerchantId(
			String merchantId) {
		if(Assert.empty(merchantId))
			return null;
		PointsCurrencyFormula queryCon=new PointsCurrencyFormula();
		queryCon.setState(Command.STATE_START);
		queryCon.setMerchantId(merchantId);
		List<PointsCurrencyFormula> pointsCurrencyFormulaList=pointsCurrencyFormulaDAO.getPointsCurrencyFormulaList(queryCon);
		if(Assert.empty(pointsCurrencyFormulaList))
			return null;
		else
			return pointsCurrencyFormulaList.get(0);
	}

	@Override
	public PointsCurrencyFormula getPointsCurrencyFormuleBySellerId(
			String sellerId) {
		if(Assert.empty(sellerId))
			return null;
		PointsCurrencyFormula queryCon=new PointsCurrencyFormula();
		queryCon.setState(Command.STATE_START);
		queryCon.setSellerId(sellerId);
		List<PointsCurrencyFormula> pointsCurrencyFormulaList=pointsCurrencyFormulaDAO.getPointsCurrencyFormulaList(queryCon);
		if(Assert.empty(pointsCurrencyFormulaList))
			return null;
		else
			return pointsCurrencyFormulaList.get(0);
	}
	

	public void setPointsCurrencyFormulaDAO(
			PointsCurrencyFormulaDAO pointsCurrencyFormulaDAO) {
		this.pointsCurrencyFormulaDAO = pointsCurrencyFormulaDAO;
	}

	@Override
	public Points presentPoints(Points points) throws AppException {
		if(points==null){
			throw new AppException("增送积分的参数为空");
		}
		return PointsFunc.presentPoints(points);
	}
	
	

	@Override
	public Points fillPoints(Points points) throws AppException {
		if(points==null){
			throw new AppException("增送积分的参数为空");
		}
		return PointsFunc.fillPoints(points);
	}
}
