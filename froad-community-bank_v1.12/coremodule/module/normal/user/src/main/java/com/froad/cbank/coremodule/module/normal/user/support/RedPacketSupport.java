package com.froad.cbank.coremodule.module.normal.user.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.ArrayUtil;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.user.pojo.MyPacketsReqPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.PagePojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.ProductOfFindUseReqPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.RedPacketsPojo;
import com.froad.cbank.coremodule.module.normal.user.utils.Constants;
import com.froad.cbank.coremodule.module.normal.user.utils.TargetObjectFormat;
import com.froad.cbank.coremodule.module.normal.user.vo.ProductsReqVo;
import com.froad.thrift.service.RegisteredRunService;
import com.froad.thrift.service.VouchersRunService;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.active.CheckVouchersReqVo;
import com.froad.thrift.vo.active.CheckVouchersResVo;
import com.froad.thrift.vo.active.FindVouchersOfCenterReqVo;
import com.froad.thrift.vo.active.FindVouchersOfSubmitReqVo;
import com.froad.thrift.vo.active.FindVouchersResVo;
import com.froad.thrift.vo.active.ProductOfFindUseVo;
import com.froad.thrift.vo.active.RegisteredHandselVo;
import com.froad.thrift.vo.active.VouchersInfoVo;
import com.froad.thrift.vo.active.VouchersToRedPackReqVo;

@Service
public class RedPacketSupport extends BaseSupport {

	@Resource
	private VouchersRunService.Iface vouchersRunService;
	
	@Resource
	private RegisteredRunService.Iface registeredRunService;
  
	/**
	 * 说明   商品购物可用红包查询
	 * 创建日期  2015年11月26日  上午11:58:52
	 * 作者  artPing
	 * 参数  @param clientId
	 * 参数  @param memberCode
	 * 参数  @param isAvailable
	 * 参数  @param sustainActiveIds
	 * 参数  @param pageNumber
	 * 参数  @param pageSize
	 * 参数  @return
	 * 参数  @throws TException
	 */
	public HashMap<String, Object> canUsePackets(String clientId , Long memberCode ,Boolean isAvailable ,Boolean isEnableQrcode,List<String> sustainActiveIdsList, List<ProductOfFindUseReqPojo> list ,PagePojo pagePojo ){
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<RedPacketsPojo> pojo = new ArrayList<RedPacketsPojo>();
		PagePojo page=new PagePojo();
		FindVouchersOfSubmitReqVo reqVo = new FindVouchersOfSubmitReqVo();
		reqVo.setClientId(clientId);
		reqVo.setMemberCode(memberCode);
		reqVo.setIsAvailable(isAvailable);
		reqVo.setIsFtoF(isEnableQrcode);
		reqVo.setOrderMoney(0);
		
		//后续开发
		if(list!=null && list.size()>0){
			List<ProductOfFindUseVo> listPro=new ArrayList<ProductOfFindUseVo>();
			for(ProductOfFindUseReqPojo pro:list){
				ProductOfFindUseVo use=new ProductOfFindUseVo();
				use.setProductId(pro.getProductId());
//				use.setVipCount(pro.getVipCount());
				use.setVipMoney(pro.getVipMoney());
				use.setProductName(pro.getProductName());
				use.setGeneralMoney(pro.getGeneralMoney());
//				use.setGeneralCount(pro.getGeneralCount());
				listPro.add(use);
			}
//			reqVo.setProductOfFindUseList(listPro);
		}
		reqVo.setSustainActiveIds(sustainActiveIdsList);
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(pagePojo.getPageNumber());
		pageVo.setPageSize(pagePojo.getPageSize());
		reqVo.setPage(pageVo);
		
		FindVouchersResVo resVo;
		try {
			resVo = vouchersRunService.findVouchersOfSubmit(reqVo);

			if(resVo!=null){
				TargetObjectFormat.copyProperties(resVo.getPage(),page);
				if(resVo.getVouchersInfoList()!=null&&resVo.getVouchersInfoList().size()>0){
					for(VouchersInfoVo vo:resVo.getVouchersInfoList()){
						RedPacketsPojo redP=new RedPacketsPojo();
						TargetObjectFormat.copyProperties(vo,redP);
					    pojo.add(redP);
					}
				}
			}
			map.put("redPackets", pojo);
			map.put("page", page);
		} catch (TException e) {
			LogCvt.error("查询可用红包接口出错", e);
			map.put("code", "9999");
			map.put("message", "查询可用红包接口异常");
		}
		return map;

	}
    

	/**
	 * 说明    我的红包查询
	 * 创建日期  2015年11月26日  上午11:36:48
	 * 作者  artPing
	 * 参数  @param clientId
	 * 参数  @param redPacketStatus
	 * 参数  @param memberCode
	 * 参数  @param pageNumber
	 * 参数  @param pageSize
	 * 参数  @return
	 * @throws TException 
	 */
	public HashMap<String, Object> myPackets(MyPacketsReqPojo req) throws TException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<RedPacketsPojo> pojo = new ArrayList<RedPacketsPojo>();
		PagePojo page=new PagePojo();
		FindVouchersOfCenterReqVo reqVo = new FindVouchersOfCenterReqVo();
		reqVo.setReqId(StringUtil.isNotEmpty(req.getReqId())?req.getReqId():"");//后续开发
		reqVo.setClientId(req.getClientId());
		reqVo.setMemberCode(req.getMemberCode());
		reqVo.setStatus(req.getStatus());
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(req.getPageNumber());
		pageVo.setPageSize(req.getPageSize());
		reqVo.setPage(pageVo);
		
		FindVouchersResVo resVo=vouchersRunService.findVouchersOfCenter(reqVo);
		if(resVo!=null){
			TargetObjectFormat.copyProperties(resVo.getPage(),page);
			if(resVo.getVouchersInfoList()!=null&&resVo.getVouchersInfoList().size()>0){
				for(VouchersInfoVo vo:resVo.getVouchersInfoList()){
					RedPacketsPojo redP=new RedPacketsPojo();
					TargetObjectFormat.copyProperties(vo,redP);
				    pojo.add(redP);
				}
			}
		}
		map.put("redPackets", pojo);
		map.put("page", page);
		return map;
	}
	
	/**
	 * 优惠码校验+ 抓换红包操作
	 * @author zhouyuhan
	 * @param clientId 客户端id
	 * @param memberCode 用户编号
	 * @param vouchersId 代金券id
	 * @param list 商品列表
	 * @return
	 * @date 2015年11月26日 下午4:29:05
	 */
	public Map<String, Object> getVouchersMonrys(String clientId,Long memberCode,String vouchersId,boolean isEnableQrcode,List<ProductsReqVo> list,List<String> ilist){
		HashMap<String, Object> resMap = new HashMap<String, Object>();		
		try {
			VouchersToRedPackReqVo req = new VouchersToRedPackReqVo();
			req.setClientId(clientId);
			req.setMemberCode(memberCode);
			req.setVouchersId(vouchersId);
			ResultVo res = vouchersRunService.vouchersToRedPack(req); 
			resMap.put("code", res.getResultCode());
			resMap.put("message", res.getResultDesc());
//			resMap.put("amount", checkVouchersResVo.getVouchersMonry());
//			resMap.put("vouchersId", vouchersId);
		} catch (TException e) {
			LogCvt.error("优惠码激活校验接口出错", e);
			resMap.put("code", "9999");
			resMap.put("message", "优惠码激活校验接口异常");
		}
		return resMap;
	}
	
	
	/**
	 * registerActive:注册送红包或者注册送积分活动
	 *
	 * @author 刘超 liuchao@f-road.com.cn
	 * 2015年12月8日 下午2:13:08
	 * @param clientId
	 * @param memberCode
	 * @param loginId
	 * 
	 */
	public void registerActive(String clientId , Long memberCode ,String loginId){
		RegisteredHandselVo req = new RegisteredHandselVo();
		req.setClientId(clientId);
		req.setMemberCode(memberCode);
		req.setLoginId(loginId);
		try {
			ResultVo res = registeredRunService.registeredHandsel(req);
			LogCvt.info("用户注册参加活动接口调用结果："+JSON.toJSONString(res));
		} catch (TException e) {
			LogCvt.error("用户注册参加活动接口异常", e);
		}
	}

}
