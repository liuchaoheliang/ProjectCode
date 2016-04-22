
package com.froad.cbank.coremodule.module.normal.user.support;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.user.enums.BoutiqueFiledSort;
import com.froad.cbank.coremodule.module.normal.user.enums.ClientChannelEnum;
import com.froad.cbank.coremodule.module.normal.user.pojo.AreaPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.BoutiqueProductPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.GenerateVipOrderPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.PagePojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.VipPresalePojo;
import com.froad.cbank.coremodule.module.normal.user.utils.Constants;
import com.froad.thrift.service.AreaService;
import com.froad.thrift.service.ClientService;
import com.froad.thrift.service.OrderService;
import com.froad.thrift.service.ProductService;
import com.froad.thrift.service.VipProductService;
import com.froad.thrift.vo.AreaVo;
import com.froad.thrift.vo.BoutiqueProductPageVoRes;
import com.froad.thrift.vo.BoutiqueProductVo;
import com.froad.thrift.vo.ClientVo;
import com.froad.thrift.vo.FiledSort;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ProductBriefPageVoRes;
import com.froad.thrift.vo.ProductBriefVoRes;
import com.froad.thrift.vo.ProductFilterVoReq;
import com.froad.thrift.vo.QueryBoutiqueGoodsFilterVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.VipProductVo;
import com.froad.thrift.vo.order.AddVIPOrderVoReq;
import com.froad.thrift.vo.order.AddVIPOrderVoRes;
import com.froad.thrift.vo.order.GetVipDiscountVoReq;
import com.froad.thrift.vo.order.GetVipDiscountVoRes;
import com.pay.user.dto.Result;
import com.pay.user.dto.VIPDto;
import com.pay.user.dto.VIPSpecDto;
import com.pay.user.helper.VIPStatus;
import com.pay.user.service.VIPSpecService;

/**
 * VIP相关接口支持
 */
@Service
public class VipSupport extends BaseSupport {

	@Resource
	private VipProductService.Iface vipProductService;
	
	@Resource
	private ProductService.Iface productService;
	
	@Resource
	private OrderService.Iface orderService;
	
	@Resource
	private AreaService.Iface areaService;
	
	@Resource
	private ClientService.Iface clientService;
	
	@Resource
	private VIPSpecService vipSpecService;
	
	
	
	/**
	 * 客户端机构组织类型
	 * @param clientId
	 * @return
	 */
	public String queryClientOrgType(String clientId){
		ClientVo  client = null;
		try {
			client = clientService.getClientById(clientId);
		} catch (TException e) {
			LogCvt.error("查询客户端信息异常", e);
			client = new ClientVo();
		}
		
		
		return client.getBankType();
	}
	
	
	
	
	/**
	 * 根据clientId获取市级区域
	 * @param clientId
	 * @return
	 */
	public AreaPojo getCityAreaByClientId(String clientId){
		List<AreaVo> plist = null;
		List<AreaVo> clist = null;
		AreaPojo areaPojo = null;
		try {
			plist = areaService.findProvinceAreaByClientId(clientId);
			LogCvt.info("开通VIP资格省级地区数据:" + JSON.toJSONString(plist));
			clist = areaService.findChildrenInfoById(plist.get(0).getId(), "");
			LogCvt.info("开通VIP资格市级地区数据:" + JSON.toJSONString(clist));
			areaPojo = new AreaPojo();
			BeanUtils.copyProperties(areaPojo, clist.get(0));
		} catch (TException e) {
			LogCvt.error("获取省级区域查询接口出错", e);
		}
		
		return areaPojo;
	}
	
	
	
	
	/**
	 * 查询客户端VIP功能信息
	 * @param clientId
	 * @return
	 */
	public VipProductVo getBankVipInfo(String clientId){
		VipProductVo res = null;
		try {
			res = vipProductService.getVipProduct(clientId);
		} catch (TException e) {
			LogCvt.error("VIP商品接口异常", e);
		}
		return res;
		
	}
	
	
	
	/**
	 * 开通VIP
	 * @param memberCode
	 * @param memberName
	 * @param memberLevel
	 * @param phone
	 * @param clientId
	 * @param clientChannelName
	 * @param orderVo
	 * @return
	 * @throws ParseException 
	 */
	public Map<String,Object> createVIPOrder(Long memberCode, String memberName, String phone, String clientId,VIPDto vipDto, String clientChannelName, GenerateVipOrderPojo orderVo) {
		Map<String,Object> resMap = new HashMap<String,Object>();
		
		
		AddVIPOrderVoReq req = new AddVIPOrderVoReq();
		req.setProductId(orderVo.getVipId());
		req.setAreaId(orderVo.getAreaId());
		req.setAreaName(orderVo.getAreaName());
		req.setBankLabelID(orderVo.getOrgCode());
		req.setClientChannel(clientChannelName);
		req.setClientId(clientId);
		req.setCreateSource(orderVo.getCreateSource());
		req.setMemberCode(memberCode);
		req.setIsVip(false);
		req.setMemberLevel(0);
		if(vipDto != null){
			if(vipDto.getVipStatus().equals(VIPStatus.NORMAL)){//状态正常则续费
				req.setIsVip(true);
				req.setMemberLevel(Integer.parseInt(vipDto.getVipLevel().getValue()));
			}
		}
		req.setMemberName(memberName);
		req.setPhone(phone);
		//req.setRemark();
		
		AddVIPOrderVoRes res = null;
		
		try {
			res = orderService.addVIPOrder(req);
		} catch (TException e) {
			LogCvt.error("创建开通VIP订单异常",e);
			res = new AddVIPOrderVoRes();
			ResultVo resultVo = new ResultVo();
			resultVo.setResultCode("9999");
			resultVo.setResultDesc("创建开通VIP订单失败");
			res.setResultVo(resultVo);
		}
		
		if(Constants.RESULT_CODE_SUCCESS.equals(res.getResultVo().getResultCode())){
			resMap.put("orderId", res.getOrderId());
			resMap.put("totalPrice", res.getTotalPrice());
			return resMap;
		}else{
			resMap.put(Results.code, res.getResultVo().getResultCode());
			resMap.put(Results.msg, res.getResultVo().getResultDesc());
			return resMap;
		}
	}
	
	
	
	
	/**
	 * 获取VIP累计节省金额
	 * @param clientId
	 * @param memberCode
	 * @return
	 */
	public double getVipDiscount (String clientId, Long memberCode){
		GetVipDiscountVoReq vipDiscountVoReq = new GetVipDiscountVoReq();   
		vipDiscountVoReq.setClientId(clientId);
		vipDiscountVoReq.setMemberCode(memberCode);
		GetVipDiscountVoRes res = null;
		try {
			res=orderService.getVipDiscount(vipDiscountVoReq);
		} catch (TException e) {
			LogCvt.error("查询VIP优惠信息异常", e);
			return 0.0;
		}
		if(Constants.RESULT_CODE_SUCCESS.equals(res.getResultVo().getResultCode())){
			return res.getMoney();
		}
		return 0.0;
	}
	
	
	
	
	/**
	 * 获取用户VIP信息
	 * @param memberCode
	 * @return
	 */
	public Map<String,Object> getMemberVipInfomation(Long memberCode, String clientId){
		Map<String,Object> resMap = new HashMap<String,Object>();
		/*
		MemberInfoVo res = null;
		try {
			res = memberInformationService.selectUserByMemberCode(memberCode,clientId);
		} catch (TException e) {
			LogCvt.error("查询VIP信息",e);
			res = new MemberInfoVo();
		}
		
		//未开通过VIP为null
		if(res.getMemberVIPInfoVo() != null){
			MemberVIPInfoVo vipVo = res.getMemberVIPInfoVo();
			resMap.put("isVip",true);
			// VIP_LEVEL_1("0001", "VIP一级"), VIP_LEVEL_2("0002", "VIP二级")...
			resMap.put("vipLevel",vipVo.getVipLevel() != null? vipVo.getVipLevel() : 0);
			// NOT_VIP("0000","非VIP"),NORMAL("0001","正常"),PENDING_AUDIT("0002","待审核"),EXPIRED("0003","已过期");
			resMap.put("vipStatus",vipVo.getVipStatus() != null? vipVo.getVipStatus() : 0);//容错处理 0001
			resMap.put("orgName", vipVo.getBankLabelName() != null? vipVo.getBankLabelName() : "");
			resMap.put("remainDay", vipVo.getExpiratioinDays());
		}else{
			resMap.put("isVip",false);
		}*/
		
		String bankOrgNo = ClientChannelEnum.getClientBankOrg(clientId).getBankOrg();
		Result result = vipSpecService.queryUserVIPInfo(memberCode, bankOrgNo);
		if(result.getResult()){
			List<VIPSpecDto> vips = (List<VIPSpecDto>)result.getData();
			if(vips != null && vips.size() > 0){
				VIPSpecDto vip = vips.get(0);
				//开通过VIP即为true
				resMap.put("isVip",true);
				// VIP_LEVEL_1("0001", "VIP一级"), VIP_LEVEL_2("0002", "VIP二级")...
				resMap.put("vipLevel",vip.getVipLevel() != null? vip.getVipLevel().getValue() : 0);
				// NOT_VIP("0000","非VIP"),NORMAL("0001","正常"),PENDING_AUDIT("0002","待审核"),EXPIRED("0003","已过期");
				resMap.put("vipStatus",vip.getVipStatus() != null? vip.getVipStatus().getValue() : 0);//容错处理 0001
				resMap.put("orgCode", vip.getBankLabelID() != null? vip.getBankLabelID() : "");
				resMap.put("orgName", vip.getBankLabelName() != null? vip.getBankLabelName() : "");
				resMap.put("bankOrgNo", vip.getBankOrgNo() != null? vip.getBankOrgNo() : "");
				resMap.put("expireTime", vip.getVipExpirationTime() != null ? vip.getVipExpirationTime().getTime() : null);
				if(vip.getVipExpirationTime() != null && vip.getVipExpirationTime().after(new Date())){
					resMap.put("remainDay", vip.getAvailableDays());
				}else{
					resMap.put("remainDay", 0);
				}
			}else{
				resMap.put("isVip",false);
			}
		}
		
		
		return resMap;
	}
	
	
	
	
	
	/**
	 * 获取VIP推荐商品列表
	 * @param clientId
	 * @param filter
	 * @param merchantId
	 * @param pagePojo
	 * @return
	 */
	public Map<String,Object> getVIPPresellProduct(String clientId, String areaId){
		Map<String,Object> resMap = new HashMap<String,Object>();
		
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(1);
		pageVo.setPageSize(3);
		
		ProductFilterVoReq req = new ProductFilterVoReq();
		req.setClientId(clientId);
		//req.setMerchantId(merchantId);
		
		Map<String,String> hash = new HashMap<String,String>();
		hash.put("areaId", areaId);
		String filter = JSON.toJSONString(hash);
		req.setFilter(filter.replace("\\", ""));
		
		List<VipPresalePojo> plist = null;
		VipPresalePojo productPojo = null;
		ProductBriefPageVoRes res = null;
		PagePojo page = null;
		
		try {
			res= productService.queryVipPresellProducts(req, pageVo);
			
			if(res.getProductBriefVoListSize() > 0){
				 plist = new ArrayList<VipPresalePojo>();
				 for( ProductBriefVoRes temp : res.getProductBriefVoList() ){
					 productPojo=new VipPresalePojo();
					 BeanUtils.copyProperties(productPojo,temp);
					 plist.add(productPojo);
				 }
			}
			
			if(res.isSetPage()){
				page = new PagePojo();
				BeanUtils.copyProperties(page,res.getPage());
			}
			
		} catch (TException e) {
			LogCvt.error("查询VIP推荐商品失败", e);
		}
		
		resMap.put("productList",plist);
		resMap.put("page", page);
		return resMap;
		
	}
	
	
	/**
	 *  我的VIP推荐精品商城商品列表
	 * @author yfy
	 * 2015年12月2日 上午11:23:13
	 * @param clientId
	 * @return
	 */
	public HashMap<String, Object> getRecommendProductList(String clientId){
		HashMap<String, Object> resMap=new HashMap<String, Object>();
		//分页page对象
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(1);
		pageVo.setPageSize(3);
		
		QueryBoutiqueGoodsFilterVo queryBoutiqueGoodsFilterVo = new QueryBoutiqueGoodsFilterVo();
		queryBoutiqueGoodsFilterVo.setClientId(clientId);
		FiledSort filedSort = new FiledSort();
		filedSort.setSortName(BoutiqueFiledSort.recommend.getMsg());//推荐
		
		BoutiqueProductPageVoRes pageVoRes=null;
		PagePojo page=new PagePojo();
		List<BoutiqueProductPojo> plist = null;
		BoutiqueProductPojo boutiqueProductPojo=null;
		
		try {
			pageVoRes = productService.queryBoutiqueGoods(queryBoutiqueGoodsFilterVo,filedSort, pageVo);
			if(pageVoRes.isSetPage()){
				BeanUtils.copyProperties(page,pageVoRes.getPage());
			}
			if( pageVoRes.getProductVos() != null && pageVoRes.getProductVos().size() != 0){
				 plist = new ArrayList<BoutiqueProductPojo>();
				 for( int i=0 ; i<pageVoRes.getProductVos().size() ; i++  ){
					 BoutiqueProductVo temp = pageVoRes.getProductVos().get(i);
					 boutiqueProductPojo=new BoutiqueProductPojo();
					 if(temp.getImage() != null){
						 boutiqueProductPojo.setImgUrl(temp.getImage().getMedium());
					 }
					 BeanUtils.copyProperties(boutiqueProductPojo,temp);
					 plist.add(boutiqueProductPojo);
				 }
			}
			
		} catch (TException e) {
			LogCvt.error("获取我的VIP推荐精品商城商品列表出错", e);
		}
		resMap.put("page", page);
		resMap.put("productList",plist);
		return resMap;
	}
	
	/**
	 * 查询是否VIP
	 * @param memberCode
	 * @param clientId
	 * @return
	 */
	public  Boolean isOrNotVip(Long memberCode, String clientId){
		boolean flag=false;
		try{
			
			/*MemberInfoVo res = memberInformationService.selectUserByMemberCode(memberCode,clientId);
			
			//未开通过VIP为null
			if(res.getMemberVIPInfoVo() != null){
				MemberVIPInfoVo vipVo = res.getMemberVIPInfoVo();
				//VIP状态为正常
				VIPStatus vs = VIPStatus.getInstanceByValue(vipVo.getVipStatus());
				if(VIPStatus.NORMAL.equals(vs)){
					flag = true;
				}
			}*/
			
			String bankOrgNo = ClientChannelEnum.getClientBankOrg(clientId).getBankOrg();
			Result result = vipSpecService.queryUserVIPInfo(memberCode, bankOrgNo);
			if(result.getResult()){
				List<VIPSpecDto> vips = (List<VIPSpecDto>)result.getData();
				if(vips != null && vips.size() > 0){
					if(VIPStatus.NORMAL.equals(vips.get(0).getVipStatus())){
						flag = true;
					}
				}
			}
			
		}catch(Exception e){
			LogCvt.error("查询用户VIP信息失败>> memberCode:" + memberCode,e);
		}
		return flag;
	}
	
	
	/**
	 * 获取会员VIP等级，不是VIP返回0
	 * @param memberCode
	 * @return
	 */
	public int getVipLevel(Long memberCode, String clientId){
		int vipLevel = 0;
		try{
			/*
			MemberInfoVo res = memberInformationService.selectUserByMemberCode(memberCode,clientId);
			
			//未开通过VIP为null
			if(res.getMemberVIPInfoVo() != null){
				MemberVIPInfoVo vipVo = res.getMemberVIPInfoVo();
				//VIP状态为正常
				VIPStatus vs = VIPStatus.getInstanceByValue(vipVo.getVipStatus());
				if(VIPStatus.NORMAL.equals(vs)){
					VIPLevel vl = VIPLevel.getInstanceByValue(vipVo.getVipLevel());
					vipLevel = vl.ordinal()+1;
				}
			}*/
			
			String bankOrgNo = ClientChannelEnum.getClientBankOrg(clientId).getBankOrg();
			Result result = vipSpecService.queryUserVIPInfo(memberCode, bankOrgNo);
			if(result.getResult()){
				List<VIPSpecDto> vips = (List<VIPSpecDto>)result.getData();
				if(vips != null && vips.size() > 0){
					if(VIPStatus.NORMAL.equals(vips.get(0).getVipStatus())){
						vipLevel = vips.get(0).getVipLevel().ordinal() + 1;
					}
				}
			}
		}catch(Exception e){
			LogCvt.error("查询用户VIP等级失败>> memberCode:" + memberCode,e);
		}
		if(vipLevel > 0){
			LogCvt.info(String.format("会员VIP信息查询成功>>	MemberCode:%s	VipLevel: %s", memberCode, vipLevel));
		}
		return vipLevel;
	}
	
	/**
	 * 校验购买后的过期截止时间是否超过当天的十年，不超过返回Result.result=true；
	 * 否则为false( 另外，对于无购买VIP记录，购买的VIP过期，该接口一律返回true:可购买或续费)
	 * @param memberCode
	 * @return
	 */
	public Boolean renewalsCheck(Long memberCode, String clientId){
		Boolean falg = false;
		try {
			//查询当前客户端的bankOrg
			ClientVo clientVo = clientService.getClientById(clientId);
			if(StringUtil.isNotBlank(clientVo.getBankOrg())){
				Result result = vipSpecService.renewalsCheck(memberCode, clientVo.getBankOrg());
				if(result.getResult()){
					falg = result.getResult();
				}
			}
		} catch (TException e) {
			LogCvt.error("校验购买后的过期截止时间是否超过十年失败>> memberCode:" + memberCode,e);
		}
		
		return falg;
	}
	/**
	 * 查询当前用户是否存在历史VIP记录
	 * 如果存在历史VIP记录表示true,再次购买不能再取消
	 * 如果不存在历史VIP记录表示false
	 * @author yfy
	 * @date 2015年12月8日 下午14:27:13
	 * @param memberCode
	 * @return
	 */
	public Boolean isRefundWarn(Long memberCode){
		Boolean falg = false;
		Result result = vipSpecService.queryHisVipBeforeOrder(memberCode);
		if(result.getResult()){
			falg = result.getResult();
		}
		return falg;
	}
	
	/**
	 * isCanRefund:查询当前用户是否可以VIP退款
	 *
	 * @author 刘超 liuchao@f-road.com.cn
	 * 2016年1月13日 上午10:28:43
	 * @param memberCode
	 * @return
	 * 
	 */
	public Boolean isCanRefund(Long memberCode, String bankOrg){
		Boolean falg = false;
		Result result = vipSpecService.cancelCheck(memberCode, bankOrg);
		if(result.getResult()){
			falg = result.getResult();
		}
		return falg;
	}
	
	/**
	 * getVipOrderNo:获取用户会员VIP订单号
	 * @author 蒋晨jiangchen@f-road.com.cn
	 * @param memberCode
	 * @param clientId
	 * @return
	 */
	public String getVipOrderNo(Long memberCode,String clientId){
		String orderNo = "";
		try {
			orderNo = orderService.getVipOrderId(clientId, memberCode);
		}catch (TException e) {
			LogCvt.error("查询用户会员VIP订单号失败>> memberCode:" + memberCode,e);
		}
		
		return orderNo;
	}
}
