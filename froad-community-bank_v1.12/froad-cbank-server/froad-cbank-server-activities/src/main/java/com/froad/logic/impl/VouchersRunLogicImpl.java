package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.thrift.TException;

import com.froad.db.mysql.bean.Page;
import com.froad.db.redis.VouchersRedis;
import com.froad.enums.ActiveLimitType;
import com.froad.enums.ActiveStatus;
import com.froad.enums.ResultCode;
import com.froad.enums.VouchersStatus;
import com.froad.handler.VouchersInfoHandler;
import com.froad.handler.VouchersRunHandler;
import com.froad.handler.impl.VouchersInfoHandlerImpl;
import com.froad.handler.impl.VouchersRunHandlerImpl;
import com.froad.logback.LogCvt;
import com.froad.logic.VouchersRunLogic;
import com.froad.po.CheckVouchersReq;
import com.froad.po.CheckVouchersRes;
import com.froad.po.FindVouchersOfSubmitReq;
import com.froad.po.PayResultNoticeReq;
import com.froad.po.Result;
import com.froad.po.VouchersInfo;
import com.froad.po.VouchersToRedPackReq;
import com.froad.thrift.client.ThriftClientProxyFactory;
import com.froad.thrift.service.MemberInformationService;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.member.MemberInfoVo;
import com.froad.util.ActiveConstants;
import com.froad.util.DateUtil;
import com.froad.util.Validator;

public class VouchersRunLogicImpl implements VouchersRunLogic {

	/**
	 * @Title: findVouchersOfSubmit
	 * @Description: TODO
	 * @author: Joker 2015年11月26日
	 * @modify: Joker 2015年11月26日
	 * @param findVouchersOfSubmitReq
	 * @return
	 * @see com.froad.logic.VouchersRunLogic#findVouchersOfSubmit(com.froad.po.FindVouchersOfSubmitReq)
	 */

	private VouchersRunHandler vouchersRunHandler = new VouchersRunHandlerImpl();
	private VouchersInfoHandler vouchersInfoHandler = new VouchersInfoHandlerImpl();

	@Override
	public Page<VouchersInfo> findVouchersOfSubmit(Page<VouchersInfo> page,
			FindVouchersOfSubmitReq findVouchersOfSubmitReq) {
		
		//1.获取当前会员在当前客户端下的所有券码
		List<VouchersInfo> allVouchersInfoList = vouchersRunHandler.getAllVouchersInfoByClientIdAndMemberCode(findVouchersOfSubmitReq);
		//判断前端传参是返回有效的还是无效的
		boolean isAvailable = findVouchersOfSubmitReq.getIsAvailable();
		//分类的LIST
		List<VouchersInfo> newVouchersInfoList = new ArrayList<VouchersInfo>();

		if(isAvailable){
			for(VouchersInfo infoDetail : allVouchersInfoList)
			{
				infoDetail = checkVouchersInfoIsAvailable(infoDetail, findVouchersOfSubmitReq, null);
				
				if(Validator.isEmptyStr(infoDetail.getReason())){
					Double vouchersMoney = infoDetail.getVouchersMoney()/1000;
					Double vouchersResMoney = infoDetail.getVouchersResMoney()/1000;
					Double vouchersUseMoney = infoDetail.getVouchersUseMoney();
	
					infoDetail.setVouchersMoney(vouchersMoney);
					infoDetail.setVouchersResMoney(vouchersResMoney);
					infoDetail.setVouchersUseMoney(vouchersUseMoney);
					
					newVouchersInfoList.add(infoDetail);
				}
			}
		} else {
			for(VouchersInfo infoDetail : allVouchersInfoList)
			{
				infoDetail = checkVouchersInfoIsAvailable(infoDetail, findVouchersOfSubmitReq, null);
				
				if(!Validator.isEmptyStr(infoDetail.getReason())){
					Double vouchersMoney = infoDetail.getVouchersMoney()/1000;
					Double vouchersResMoney = infoDetail.getVouchersResMoney()/1000;
					Double vouchersUseMoney = infoDetail.getVouchersUseMoney();
	
					infoDetail.setVouchersMoney(vouchersMoney);
					infoDetail.setVouchersResMoney(vouchersResMoney);
					infoDetail.setVouchersUseMoney(vouchersUseMoney);
					
					newVouchersInfoList.add(infoDetail);
				}
			}
		}
		//返回的分页LIST
		List<VouchersInfo> returnList = new ArrayList<VouchersInfo>();  
		page.setTotalCount(newVouchersInfoList.size());
		//根据传过来的分页信息拿到第几页的信息
        for(int i = (page.getPageNumber() - 1) * page.getPageSize(); i < page.getTotalCount() && i < page.getPageNumber() * page.getPageSize(); i++) {  
        	returnList.add(newVouchersInfoList.get(i));  
        }  
		
		page.setResultsContent(returnList);
		
		return page;
	}

	/**
	 * @Title: checkVoucherVaild
	 * @Description: TODO
	 * @author: Joker 2015年11月27日
	 * @modify: Joker 2015年11月27日
	 * @param checkVouchersReq
	 * @return
	 * @see com.froad.logic.VouchersRunLogic#checkVoucherVaild(com.froad.po.CheckVouchersReq)
	 */

	@Override
	public CheckVouchersRes checkVoucherVaild(CheckVouchersReq checkVouchersReq) {
		
		CheckVouchersRes res = new CheckVouchersRes();
		Result result = new Result(ResultCode.success.getCode(), "验证券码成功");
		res.setResult(result);
		// 1.redis中查找失败的次数:
		Long memberCode = checkVouchersReq.getMemberCode();

		if (VouchersRedis.checkExistsByMemberCode(memberCode)) {
			int count = VouchersRedis.getFailureByMemberCode(memberCode);
			LogCvt.info("用户输入代金券失败次数为["+count+"]");
			if (count >= 3) {
				result = new Result(ResultCode.failed.getCode(), "用户已经输错3次,请3小时后再尝试");
				res.setResult(result);
				return res;
			}
		}
		//开始校验输入的代金券
		VouchersInfo vouchersInfoReq = new VouchersInfo();
		vouchersInfoReq.setClientId(checkVouchersReq.getClientId());
		vouchersInfoReq.setVouchersId(checkVouchersReq.getVouchersId());
		VouchersInfo vouchersInfo = vouchersRunHandler
				.findOneVouchersInfo(vouchersInfoReq);
		//不存在，redis失败次数+1
		if (null == vouchersInfo) {
			VouchersRedis.setFailCountByMemberCode(memberCode);
			result = new Result(ResultCode.failed.getCode(), "红包码无效,请重新输入");
			res.setResult(result);
			return res;
		}
		
		vouchersInfo = checkVouchersInfoIsAvailable(vouchersInfo, null, checkVouchersReq);
		
		if(Validator.isEmptyStr(vouchersInfo.getReason())) {
			res.setVouchersMonry((double)vouchersInfo.getVouchersMoney()/1000);
			VouchersRedis.delFailCountByMemberCode(memberCode);
		} else {
			result = new Result(ResultCode.failed.getCode(), "红包码无效,请重新输入");
			res.setResult(result);
		}


		return res;
	}

	 /**
	  * @Title: payResultNotice
	  * @Description: TODO
	  * @author: Joker 2015年11月27日
	  * @modify: Joker 2015年11月27日
	  * @param payResultNoticeReq
	  * @return
	  * @see com.froad.logic.VouchersRunLogic#payResultNotice(com.froad.po.PayResultNoticeReq)
	  */
	
	
	@Override
	public ResultVo payResultNotice(PayResultNoticeReq payResultNoticeReq) {
		
		return vouchersRunHandler.payResultNotice(payResultNoticeReq);
	}
	
	private VouchersInfo checkVouchersInfoIsAvailable(VouchersInfo vouchersInfo, FindVouchersOfSubmitReq findVouchersOfSubmitReq, CheckVouchersReq checkVouchersReq) {
		
		if(null != checkVouchersReq) {
			VouchersInfo vouchersInfoReq = new VouchersInfo();
			vouchersInfoReq.setClientId(checkVouchersReq.getClientId());
			vouchersInfoReq.setVouchersId(checkVouchersReq.getVouchersId());
			 vouchersInfo = vouchersRunHandler
					.findOneVouchersInfo(vouchersInfoReq);
		}
		
		String clientId = vouchersInfo.getClientId();
		String activeId = vouchersInfo.getActiveId();
		Map<String, String> voucherDetail = VouchersRedis.readVouchersActive(clientId, activeId);
		
		if(!voucherDetail.get("result").equals("0")) {//0-初始化成功 18001-活动失效 18002-初始化活动失败 18003-读取活动失败
			if(voucherDetail.get("result").equals("18001"))
				vouchersInfo.setReason("活动失效");
		 if(voucherDetail.get("result").equals("18002"))
				vouchersInfo.setReason("初始化活动失败");
			else if(voucherDetail.get("result").equals("18003"))
				vouchersInfo.setReason("读取活动失败");
			else if(voucherDetail.get("result").equals("18016"))
				vouchersInfo.setReason("活动还没启动");	
			else 
				vouchersInfo.setReason("活动无效");	
			return vouchersInfo;
		}
		String itemType = voucherDetail.get("itemType");
		
		String orderMinMoneyStr = voucherDetail.get("orderMinMoney");
		Double orderMinMoney = Double.valueOf(0);
		boolean isFtoF = false;
		if(null != orderMinMoneyStr) {
			orderMinMoney = Double.valueOf(orderMinMoneyStr).doubleValue()/1000;
		}
		 
		String expireStartTime = voucherDetail.get("expireStartTime");
		String expireEndTime = voucherDetail.get("expireEndTime");
		Date start=new Date(System.currentTimeMillis()); 
		Date end=DateUtil.parse(DateUtil.DATE_TIME_FORMAT2, expireStartTime); 
		long beforeStartTime = Validator.checkActiveVilidTime(start,end);
		int afterEndTime =(int)((DateUtil.parse(DateUtil.DATE_TIME_FORMAT2, expireEndTime).getTime()-System.currentTimeMillis())/1000);
		List<String> orderSustainActiveIds = null;
		Long memberCode = null;
		String vouchersId = null;
		Double orderMoney = Double.valueOf("0").doubleValue();
		
		if(null != findVouchersOfSubmitReq)
		{
			orderSustainActiveIds = findVouchersOfSubmitReq.getSustainActiveIds();
			orderMoney = findVouchersOfSubmitReq.getOrderMoney();
			memberCode= findVouchersOfSubmitReq.getMemberCode();
			isFtoF = findVouchersOfSubmitReq.getIsFtoF();
		}else {
			orderSustainActiveIds = checkVouchersReq.getSustainActiveIds();
			orderMoney = checkVouchersReq.getOrderMoney();
			memberCode = checkVouchersReq.getMemberCode();
			vouchersId = checkVouchersReq.getVouchersId();
			isFtoF = checkVouchersReq.getIsFtoF();
		}
		 
		String status = voucherDetail.get("status");
		String activeName = voucherDetail.get("activeName");
		String description = voucherDetail.get("description");
		vouchersInfo.setActiveName(activeName);
		vouchersInfo.setDescription(description);
		vouchersInfo.setExpireStartTime(DateUtil.parse(DateUtil.DATE_TIME_FORMAT2, expireStartTime));
		vouchersInfo.setExpireEndTime(DateUtil.parse(DateUtil.DATE_TIME_FORMAT2, expireEndTime));
		
		//是否支持面对面
		if(isFtoF){
			boolean support = voucherDetail.get("isFtof").equals("1") ? true : false;
			if(!support) {
				LogCvt.info("不支持面对面");
				vouchersInfo.setReason("不支持面对面");
				return vouchersInfo;
			}
		}
		
		//根据代金券活动范围判断订单金额是否满足
		if(ActiveLimitType.Notlimited.getCode().equals(itemType)) {
			if(orderMoney < orderMinMoney)
			{
				LogCvt.info("订单金额不足以使用代金券");
				vouchersInfo.setReason("订单金额不足以使用代金券");
				return vouchersInfo;
			}
		} else {
			//to be continue..
		}
		
		if(null != checkVouchersReq) {
			String vouchersMemberCode = vouchersInfo.getMemberCode().toString();
			if(!vouchersMemberCode.equals("0") && !vouchersMemberCode.equals(memberCode.toString())){
				LogCvt.info("券码 "+vouchersId+" 不属于 "+memberCode+" 所有");
				vouchersInfo.setReason("券码 "+vouchersId+" 不属于 "+memberCode+" 所有");
				return vouchersInfo;
			}
		}
		
		//输入的券码是否在使用时间内
		if(beforeStartTime > 0 && afterEndTime <=0)
		{
			LogCvt.info("券码有效期不符合要求");
			vouchersInfo.setReason("券码有效期不符合要求");
			return vouchersInfo;
		}
		
		//券码状态是否初始化状态
		//if(VouchersStatus.init.getCode().equals(vouchersInfo.getStatus()))
		
		if(null != checkVouchersReq)
		{
			if(!VouchersStatus.init.getCode().equals(vouchersInfo.getStatus()))
			{
//				LogCvt.info("券码状态不是初始化状态");
//				vouchersInfo.setReason("券码状态不是初始化状态");
//				return vouchersInfo;
				// TODO 2016-07-01 00:00:00 放开上面的注释 去掉下面的代码
				int number = VouchersRedis.getVouchersUseNumber(checkVouchersReq.getVouchersId());
				if( number == 0 ){
					LogCvt.info("此红包券已经被使用");
					vouchersInfo.setReason("此红包券已经被使用");
					return vouchersInfo;
				}else if( number < 0 ){
					LogCvt.info("此红包券已使用次数读取失败");
					vouchersInfo.setReason("此红包券已使用次数读取失败");
					return vouchersInfo;
				}else if( number != 999999 ){
					Set<String> users = VouchersRedis.getVouchersUsers(checkVouchersReq.getVouchersId());
					if( users != null ){
						if( users.contains(memberCode.toString()) ){
							LogCvt.info("此红包券已被该用户使用");
							vouchersInfo.setReason("此红包券已被该用户使用");
							return vouchersInfo;
						}
					}
				}else if( number == 999999 ){
					LogCvt.info("此红包券 "+vouchersId+" 已经被使用");
					vouchersInfo.setReason("此红包券已经被使用");
					return vouchersInfo;
				}
			}
		}
		

		
		//活动状态是否启用状态
		if(!ActiveStatus.launch.getCode().equals(status))
		{
			LogCvt.info("活动状态不是启用状态");
			vouchersInfo.setReason("活动状态不是启用状态");
			return vouchersInfo;
		}
		
		//代金券所属的红包活动是否支持其他营销活动
		Set<String> supportActvieIds = VouchersRedis.getSustainActiveIds(clientId, activeId);

		if(null == supportActvieIds) {//不支持任何满减
			if(null != orderSustainActiveIds)
			{
				if(orderSustainActiveIds.size() > 0){
					LogCvt.info("券码不支持任何营销活动");
					vouchersInfo.setReason("券码不支持任何营销活动");
					return vouchersInfo;
				}
			}
			
		}
		else {
			if(null != orderSustainActiveIds) {
				if(orderSustainActiveIds.size() > 0) {
					for(String orderActiveId : orderSustainActiveIds) {//订单的营销活动
						boolean exists = supportActvieIds.contains(orderActiveId);
						if(!exists) {
							LogCvt.info("券码不支营销活动");
							vouchersInfo.setReason("券码不支营销活动");
							return vouchersInfo;
						}
					}
				}
			}
			
		}
		
		//扣减资格校验
		boolean checkResult = VouchersRedis.checkPersonLimit(clientId, activeId, memberCode);
		LogCvt.debug("活动 "+activeId+" 个人限制:"+checkResult);
		if( !checkResult ){
			LogCvt.info("代金券码/红包所属活动 "+activeId+" 个人限制已满");
			vouchersInfo.setReason("红包码活动的个人限制已满");
			return vouchersInfo;
		}
		
		//时间段限制
		checkResult = VouchersRedis.checkGlobalLimit(clientId, activeId, "");
		LogCvt.debug("活动 "+activeId+" 时间段限制:"+checkResult);
		if( !checkResult ){
			LogCvt.debug("该券码/红包所属活动 "+activeId+" 当前使用额已满");
			vouchersInfo.setReason("红包码活动的当前使用额已满");
			return vouchersInfo;
		}
		
		//是否仅限用新用户
		String isOnlyNewUsers = voucherDetail.get("isOnlyNewUsers");
		if("1".equals(isOnlyNewUsers)) {
			//调用 
			long userRegisterTime = getUserRegisterDate(memberCode, clientId);
			long startTime = DateUtil.parse(DateUtil.DATE_TIME_FORMAT2, expireStartTime).getTime();
			long endTime = DateUtil.parse(DateUtil.DATE_TIME_FORMAT2, expireEndTime).getTime();
			if( userRegisterTime < startTime || userRegisterTime > endTime ){
				LogCvt.info("券码仅能用于新注册用户");
				vouchersInfo.setReason("券码仅能用于新注册用户");
				return vouchersInfo;
			}
		}
		
		return vouchersInfo;
	}
	
	/**
	 * 获取会员注册时间
	 * */
	public static Long getUserRegisterDate(Long memberCode, String clientId) {
		
		Long result = 0L;
		try {
			MemberInformationService.Iface client = (MemberInformationService.Iface) ThriftClientProxyFactory.createIface(MemberInformationService.class, ActiveConstants.THRIFT_ORDER_HOST, Integer.valueOf(ActiveConstants.THRIFT_ORDER_PORT),60000);
			
			MemberInfoVo memberInfoVo = client.selectUserByMemberCode(memberCode, clientId);
			
			result = memberInfoVo.getCreateTime();
			
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ResultVo vouchersToRedPack(VouchersToRedPackReq vouchersToRedPackReq) {
		
		ResultVo result = new ResultVo(ResultCode.success.getCode(), "券码绑定成功");
		// 1.redis中查找失败的次数:
		Long memberCode = vouchersToRedPackReq.getMemberCode();

		if (VouchersRedis.checkExistsByMemberCode(memberCode)) {
			int count = VouchersRedis.getFailureByMemberCode(memberCode);
			LogCvt.info("用户输入代金券失败次数为["+count+"]");
			if (count >= 3) {
				return new ResultVo(ResultCode.failed.getCode(), "用户已经输错3次,请3小时后再尝试");
			}
		}
		
		//开始校验输入的代金券
		VouchersInfo vouchersInfoReq = new VouchersInfo();
		vouchersInfoReq.setClientId(vouchersToRedPackReq.getClientId());
		vouchersInfoReq.setVouchersId(vouchersToRedPackReq.getVouchersId());
		VouchersInfo vouchersInfo = vouchersRunHandler
				.findOneVouchersInfo(vouchersInfoReq);
		
		//不存在，redis失败次数+1
		if (null == vouchersInfo) {
			VouchersRedis.setFailCountByMemberCode(memberCode);
			return new ResultVo(ResultCode.failed.getCode(), "红包码无效,请重新输入");
		}
		

		//开始券码绑定用户前校验
		String clientId = vouchersInfo.getClientId();
		String activeId = vouchersInfo.getActiveId();
		Long vouchersMemberCode = vouchersInfo.getMemberCode();
		

		Map<String, String> voucherDetail = VouchersRedis.readVouchersActive(clientId, activeId);
		
		String expireEndTime = voucherDetail.get("expireEndTime");
		String status = voucherDetail.get("status");
		
		//已经过期,无需绑定
		int afterEndTime =(int)((DateUtil.parse(DateUtil.DATE_TIME_FORMAT2, expireEndTime).getTime()-System.currentTimeMillis())/1000);
		if(afterEndTime <=0)
		{
			LogCvt.info("券码已经过期");
			return new ResultVo(ResultCode.failed.getCode(), "红包码无效,请重新输入");
		}
		
		//活动状态是否启用状态
		if(!ActiveStatus.launch.getCode().equals(status))
		{
			LogCvt.info("活动状态不是启用状态");
			return new ResultVo(ResultCode.failed.getCode(), "红包码无效,请重新输入");
		}
		
		//券码状态是否初始化状态
		//if(VouchersStatus.init.getCode().equals(vouchersInfo.getStatus()))
		if((!VouchersStatus.init.getCode().equals(vouchersInfo.getStatus()))
				|| (VouchersStatus.init.getCode().equals(vouchersInfo.getStatus())&& !"0".equals(vouchersMemberCode.toString())))
		{
//			LogCvt.info("券码状态不是初始化状态");
//			vouchersInfo.setReason("券码状态不是初始化状态");
//			return vouchersInfo;
			// TODO 2016-07-01 00:00:00 放开上面的注释 去掉下面的代码
			int number = VouchersRedis.getVouchersUseNumber(vouchersToRedPackReq.getVouchersId());
			if( number == 0 ){
				LogCvt.info("此红包券已经被使用");
				return new ResultVo(ResultCode.failed.getCode(), "红包码无效,请重新输入");
			}else if( number < 0 ){
				LogCvt.info("此红包券已使用次数读取失败");
				return new ResultVo(ResultCode.failed.getCode(), "红包码无效,请重新输入");
			}else if( number != 999999 ){
				Set<String> users = VouchersRedis.getVouchersUsers(vouchersToRedPackReq.getVouchersId());
				if( users != null ){
					if( users.contains(memberCode.toString()) ){
						LogCvt.info("此红包券已被该用户使用");
						return new ResultVo(ResultCode.failed.getCode(), "红包码无效,请重新输入");
					} else {
						//绑定用户
						int count = VouchersRedis.getVouchersUseNumber(vouchersToRedPackReq.getVouchersId());
						
						vouchersInfo.setMemberCode(memberCode);//设置memberCode
						vouchersInfo.setVouchersId(vouchersInfo.getVouchersId()+"-"+count);
						
						try {
							vouchersInfoHandler.addVouchersInfo(vouchersInfo);
						} catch (Exception e) {
							LogCvt.error("此红包券 "+vouchersInfo.getVouchersId()+" 绑定用户["+memberCode+"]失败");
						}
						
					}
				}
			}else if( number == 999999 ){
				LogCvt.info("此红包券 "+vouchersToRedPackReq.getVouchersId()+" 已经被使用");
				return new ResultVo(ResultCode.failed.getCode(), "红包码无效,请重新输入");
			}
		}
		
		//判断券码所属会员为当前会员编号或者为空（兼容券码多次使用，所以注释）// TODO 2016-07-01 00:00:00 放开下面的注释 去掉下面的代码
//		if(!"0".equals(vouchersInfo.getMemberCode()) && !vouchersToRedPackReq.getMemberCode().toString().equals(vouchersInfo.getMemberCode().toString()) ) {
//			LogCvt.info("此红包券 "+vouchersToRedPackReq.getVouchersId()+" 已经被绑定");
//			return new ResultVo(ResultCode.failed.getCode(), "红包码无效,请重新输入");
//		}
		
		//绑定用户
		if("0".equals(vouchersMemberCode.toString())) {
				
			try {
				vouchersInfo.setMemberCode(memberCode);
				vouchersInfoHandler.updateVouchersInfoByVouchersId(vouchersInfo);
			} catch (Exception e) {
				LogCvt.error("此红包券 "+vouchersInfo.getVouchersId()+" 绑定用户["+memberCode+"]失败");
				return new ResultVo(ResultCode.failed.getCode(), "红包码无效,请重新输入");
			}
		}
		
		return result;
	}
	
}
