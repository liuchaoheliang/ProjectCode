package com.froad.cbank.coremodule.module.normal.user.support;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.user.pojo.PagePojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.PointInfoPojo;
import com.froad.cbank.coremodule.module.normal.user.utils.AmountUtils;
import com.froad.cbank.coremodule.module.normal.user.vo.DisplayPayChannelVo;
import com.froad.cbank.coremodule.module.normal.user.vo.PointAmountResVo;
import com.froad.cbank.coremodule.module.normal.user.vo.PointsTypeVo;
import com.froad.cbank.coremodule.module.normal.user.vo.PointsVo;
import com.froad.thrift.service.MemberInformationService;
import com.froad.thrift.vo.member.QueryProtocolVo;
import com.froad.thrift.vo.member.UserEnginePointsRecordVo;
import com.froad.thrift.vo.member.UserEnginePointsVo;

/**
 * 积分服务类
 * @author Liebert
 *
 */
@Service
public class IntegralSupport extends BaseSupport {
	
	@Resource
	private MemberInformationService.Iface memberInformationService;

	@Resource
	private ClientConfigSupport clientConfigSupport;
	
	
	/**
	 * 查询用户积分 - 联盟积分，银行积分
	 * @param integralVo
	 * @param clientId
	 * @return
	 */
	public Map<String,Object> getMemberPoints(String memberName, String clientId){
		Map<String,Object> resMap = new HashMap<String,Object>();
		
		try {
			if (StringUtil.isNotBlank(memberName) && StringUtil.isNotBlank(clientId)) {
				UserEnginePointsVo userPoints = memberInformationService
						.selectMemberPointsInfoByLoginID(clientId, memberName);
				List<PointsTypeVo> pointsTypeVoList = new ArrayList<PointsTypeVo>();

				// 联盟积分
				PointsTypeVo unionPoints = new PointsTypeVo();
				unionPoints.setType("froadPoints");
				unionPoints.setTypeName("联盟积分");
				if(StringUtil.isNotBlank(userPoints.getFroadPoints())){
					unionPoints.setTotal(userPoints.getFroadPoints());
				}else{
					unionPoints.setTotal("0.00");
				}
				
				pointsTypeVoList.add(unionPoints);

				// 银行积分
				PointsTypeVo bankPoints = new PointsTypeVo();
				bankPoints.setType("bankPoints");
				bankPoints.setTypeName("银行积分");
				if(StringUtil.isNotBlank(userPoints.getBankPoints())){
					bankPoints.setTotal(userPoints.getBankPoints());
				}else{
					bankPoints.setTotal("0.00");
				}
				
				pointsTypeVoList.add(bankPoints);

				resMap.put("total", pointsTypeVoList);
				return resMap;
			} else {
				LogCvt.info(String.format("缺少必要的请求参数:memberName:%s, clientId:%s",memberName,clientId));
				resMap.put(Results.code, "1001");
				resMap.put(Results.msg, "缺少必要的请求参数");
				return resMap;
			}
		} catch (TException e) {
			LogCvt.error("用户积分查询失败", e);
			resMap.put(Results.code, "9999");
			resMap.put(Results.msg, "系统错误");
			return resMap;
		}
	}
	
	
	
	/**
	 * 获取用户积分消费记录
	 * @return
	 */
	public Map<String,Object> getMemberIntegralRecord(String pointsType, String userName, String clientId, long startTime, long endTime, int pageSize, int pageNumber){
		Map<String,Object> resMap = new HashMap<String,Object>();
		
		try {
			
			//查询全部积分记录
			String queryType = "";
			if("bankPoints".equals(pointsType) && "jilin".equals(clientId)){
				queryType="consumPoints";
			}
			QueryProtocolVo protocolVo = memberInformationService.getPointTransBypage(clientId, userName,startTime, endTime,queryType,pointsType,pageSize,pageNumber);

			List<PointInfoPojo> pointList = new ArrayList<PointInfoPojo>();
			if(protocolVo.getPointInfosSize() > 0){
				Iterator<UserEnginePointsRecordVo> pointIter = protocolVo.getPointInfosIterator();
				UserEnginePointsRecordVo recordVo = null;
				PointInfoPojo pointPojo = null;
				while(pointIter.hasNext()){
					recordVo = pointIter.next();
					pointPojo = new PointInfoPojo();
					BeanUtils.copyProperties(pointPojo, recordVo);
					//默认Points值为联盟积分(froadPoints)，查询的是银行积分(bankPoints)设为OrgPoints
					if("bankPoints".equals(pointsType)){
						pointPojo.setPoints(recordVo.getOrgPoints());
					}
					pointList.add(pointPojo);
				}
			}
			
			PagePojo page = new PagePojo();
			if(protocolVo.isSetQueryInfo()){
				page.setPageCount(Integer.parseInt(protocolVo.getQueryInfo().getTotalPageNum()));
				page.setPageNumber(Integer.parseInt(protocolVo.getQueryInfo().getPageNum()));
				page.setPageSize(Integer.parseInt(protocolVo.getQueryInfo().getPageSize()));
				page.setTotalCount(Integer.parseInt(protocolVo.getQueryInfo().getTotalCount()));
				page.setHasNext(page.getPageNumber() < page.getPageCount());
			}
			
			resMap.put("pointsList", pointList);
			resMap.put("page", page);
			return resMap;
		} catch (TException e) {
			LogCvt.error("银行积分查询失败", e);
			resMap.put(Results.code, "9999");
			resMap.put(Results.msg, "系统错误");
			return resMap;
		} catch(NumberFormatException e){
			LogCvt.error("数字类型转换异常", e);
			resMap.put(Results.code, "9999");
			resMap.put(Results.msg, "系统错误");
			return resMap;
		}
	}
	
	/**
	 * getUserPointsAmount:根据用户登录LoginId 查询会员积分值情况
	 *
	 * @author 刘超 liuchao@f-road.com.cn
	 * 2015年11月26日 下午12:59:29
	 * @param clientId
	 * @param loginId
	 * @return
	 * 
	 */
	public  Map<String,Object> getUserPointsAmount(String clientId , String loginId){
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		UserEnginePointsVo userPoints;
		// 支付渠道列表
		Map<String,Object> payChannelMap = clientConfigSupport.getClientPaymentChannel(clientId);
		PointsVo unionPoint = (PointsVo)payChannelMap.get("unionPoint");
		PointsVo bankPoint = (PointsVo)payChannelMap.get("bankPoint");
		DisplayPayChannelVo displayChannel;
		displayChannel = (DisplayPayChannelVo) payChannelMap.get("payChannel");
		try {
			userPoints = memberInformationService.selectMemberPointsInfoByLoginID(clientId, loginId);
			// 获取联盟积分
			PointAmountResVo unionPointAmount = new PointAmountResVo();
			unionPointAmount.setExchageRate(unionPoint.getPointRate());
			if (StringUtil.isNotBlank(userPoints.getFroadPoints()) && StringUtil.isNotBlank(unionPoint.getPointRate())) {
				unionPointAmount.setPoint(userPoints.getFroadPoints());
				unionPointAmount.setAmount(AmountUtils.getPointExchangeAmount(userPoints.getFroadPoints(),unionPoint.getPointRate()).doubleValue());
				result.put("unionPointAmount", unionPointAmount);
			} else {
				unionPointAmount.setPoint(StringUtil.isNotBlank(userPoints.getFroadPoints()) ? userPoints.getFroadPoints(): "0.00");
				result.put("unionPointAmount", unionPointAmount);
			}
			// 获取银行积分
			PointAmountResVo bankPointAmount = new PointAmountResVo();
			bankPointAmount.setExchageRate(bankPoint.getPointRate());
			if (StringUtil.isNotBlank(userPoints.getBankPoints()) && StringUtil.isNotBlank(bankPoint.getPointRate())) {

				//银行积分舍弃小数部分计算
				String bankPointVal = new BigDecimal(userPoints.getBankPoints()).setScale(0, BigDecimal.ROUND_DOWN).toString();
				bankPointAmount.setPoint(userPoints.getBankPoints());
				bankPointAmount.setAmount(AmountUtils.getPointExchangeAmount(bankPointVal,bankPoint.getPointRate()).doubleValue());  
				result.put("bankPointAmount", bankPointAmount);
			} else {
				bankPointAmount.setPoint(StringUtil.isNotBlank(userPoints.getBankPoints()) ? userPoints.getBankPoints(): "0.00");
				result.put("bankPointAmount", bankPointAmount);
			}
			
		} catch (TException e) {
			LogCvt.error("查询会员积分值异常", e);
			displayChannel.setDisplayUnionPoint(false);
			displayChannel.setDisplayBankPoint(false);
		}
		result.put("displayChannel", displayChannel);
		return result ;
	}
	
}
