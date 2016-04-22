package com.froad.cbank.coremodule.module.normal.merchant.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.merchant.enums.OutletDisableStatusEnum;
import com.froad.cbank.coremodule.module.normal.merchant.enums.ProductType;
import com.froad.cbank.coremodule.module.normal.merchant.exception.MerchantException;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Add_Face_Group_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Order_Shipping_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.PageRes;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Face_Group_Detail_PC_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Face_Group_Detail_PC_Res;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Face_Group_Detail_Product_PC_Res;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Face_Group_Detail_Product_Res;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Face_Group_Detail_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Face_Group_Detail_Res;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Face_Group_PC_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Face_Group_PC_Res;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Face_Group_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Face_Group_Res;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Merchant_Settlement_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Merchant_Settlement_Res;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Order_Detail_PC_DeliverInfo_Res;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Order_Detail_PC_Product_Res;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Order_Detail_PC_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Order_Detail_PC_Res;
import com.froad.cbank.coremodule.module.normal.merchant.utils.EncryptUtil;
import com.froad.cbank.coremodule.module.normal.merchant.utils.EnumTypes;
import com.froad.cbank.coremodule.module.normal.merchant.utils.PramasUtil;
import com.froad.cbank.coremodule.module.normal.merchant.utils.TargetObjectFormat;
import com.froad.cbank.coremodule.module.normal.merchant.utils.orgNameUtil;
import com.froad.thrift.service.MerchantUserService;
import com.froad.thrift.service.OrderQueryService;
import com.froad.thrift.service.OrderService;
import com.froad.thrift.service.OutletService;
import com.froad.thrift.service.ProductService;
import com.froad.thrift.vo.AddOutletProductVoRes;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.MerchantUserVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.OutletDetailVo;
import com.froad.thrift.vo.OutletProductVo;
import com.froad.thrift.vo.OutletVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.order.GetOrderByQrcodeVoReq;
import com.froad.thrift.vo.order.GetOrderByQrcodeVoRes;
import com.froad.thrift.vo.order.GetOrderDetailByBankManageVoRes;
import com.froad.thrift.vo.order.GetOrderDetailByMerchantManageVoReq;
import com.froad.thrift.vo.order.GetOrderDetailByMerchantManageVoRes;
import com.froad.thrift.vo.order.GetOrderDetailByMerchantPhoneVoReq;
import com.froad.thrift.vo.order.GetOrderDetailByMerchantPhoneVoRes;
import com.froad.thrift.vo.order.ProductInfoVo;
import com.froad.thrift.vo.order.QueryMerchantSettlementReq;
import com.froad.thrift.vo.order.QueryMerchantSettlementRes;
import com.froad.thrift.vo.order.QueryOrderByMerchantManageVo;
import com.froad.thrift.vo.order.QueryOrderByMerchantManageVoReq;
import com.froad.thrift.vo.order.QueryOrderByMerchantManageVoRes;
import com.froad.thrift.vo.order.QueryOrderByMerchantPhoneVo;
import com.froad.thrift.vo.order.QueryOrderByMerchantPhoneVoReq;
import com.froad.thrift.vo.order.QueryOrderByMerchantPhoneVoRes;
import com.froad.thrift.vo.order.ShippingOrderVoReq;
import com.froad.thrift.vo.order.ShippingOrderVoRes;

/**
 * 订单
 * @ClassName OrderH5Service
 * @author zxl
 * @date 2015年3月23日 下午2:03:24
 */
@Service
public class Order_Service {
	@Resource
	Common_Service common_Service;
	@Resource
	OrderService.Iface orderService;
	
	@Resource
	ProductService.Iface productService;
	
	@Resource
	OrderQueryService.Iface orderQueryService;	
	@Resource
	OutletService.Iface outletService;

	@Resource
	User_Service user_Service;
	@Resource
	MerchantUserService.Iface merchantUserService;
	
	
	/**
	 * 面对面创建
	 * @tilte facetrnCreate
	 * @author zxl
	 * @date 2015年4月2日 上午11:29:01
	 * @param merchantId
	 * @param outletId
	 * @param money
	 * @return
	 * @throws MerchantException
	 * @throws TException 
	 */
	public HashMap<String,Object> face_Group_Create(Add_Face_Group_Req req) throws MerchantException, TException{
		HashMap<String,Object> reMap = new HashMap<String, Object>();
		
		OutletProductVo vo = new OutletProductVo();
		vo.setRemark(req.getRemark());
		vo.setCost(StringUtil.isNotBlank(req.getMoney())?Double.valueOf(req.getMoney()):0);
		vo.setClientId(req.getClientId());
		vo.setMerchantId(req.getMerchantUser().getMerchantId());
		String outletId = req.getMerchantUser().getOutletId();
		
		/**2015-11-12 迭代1.7 
		 * 放开商户管理员员收银权限，将商户管理员关联在默认门店上其收银均记录在默认门店中。
		 * */
		//如果是商户没有门店去查默认门店
		if(outletId.equals("0")){
			OutletVo outletVo = new OutletVo();
			outletVo.setClientId(req.getClientId());
			outletVo.setMerchantId(req.getMerchantUser().getMerchantId());
			List<String> disableStatusList = new ArrayList<String>();
			disableStatusList.add(OutletDisableStatusEnum.normal.getCode());
			outletVo.setDisableStatusList(disableStatusList);
			List<OutletVo> listOutletPage= outletService.getOutlet(outletVo);
			if(listOutletPage!=null&&listOutletPage.size()>0){
				for(OutletVo outVo : listOutletPage){
					//如果是默认门店绑定门店
					if(outVo.isDefault){
						vo.setOutletId(outVo.getOutletId());
					}
				}
			}else{
				throw new MerchantException(EnumTypes.fail.getCode(),"此商户无门店，请新建门店后再收银!");
			}
		}else{
			vo.setOutletId(req.getMerchantUser().getOutletId());
		}
		
		vo.setUserId(req.getMerchantUser().getId()+"");
		
		OriginVo ori = new OriginVo();
		ori.setOperatorId(req.getMerchantUser().getId());
		ori.setOperatorIp(req.getIp());
		ori.setPlatType(req.getPlatType());
		if(Double.valueOf(req.getMoney())>50000){
			throw new MerchantException(EnumTypes.fail.getCode(),"您单笔收款最大金额不能超过5万元");
		}
		AddOutletProductVoRes resp= productService.addOutletProduct(ori,vo);
		if(resp.getOutletProductQrCodeVo()!=null){
			//二维码
			reMap.put("qrcode", resp.getOutletProductQrCodeVo().getQrCode());
			//二维码url
			reMap.put("url", resp.getOutletProductQrCodeVo().getUrl());
			//商品ID
			reMap.put("productId", resp.getOutletProductQrCodeVo().getProductId());
		}else{
			throw new MerchantException(resp.getResultVo().getResultCode(),resp.getResultVo().getResultDesc());
		}
		return reMap;
	} 
	
	
    /**
     * 查询列表，团购和面对面订单
     * @return
     * @throws MerchantException
     * @throws TException 
     */
	public HashMap<String,Object> query_face_group(Query_Face_Group_Req req) throws MerchantException, TException{
		HashMap<String,Object> reMap = new HashMap<String, Object>();
		QueryOrderByMerchantPhoneVoReq reqvo=new QueryOrderByMerchantPhoneVoReq();
		TargetObjectFormat.copyProperties(req, reqvo);
		reqvo.setMerchantId(req.getMerchantUser().getMerchantId());
		//商户管理员可查全部，门店查所属门店下的
		LogCvt.info("门店id："+req.getMerchantUser().getOutletId());
		if(req.getMerchantUser().getIsSuperAdmin()){
			if(StringUtils.isNotBlank(req.getOutletId()) &&  !req.getOutletId().equals("0")){
				reqvo.setOutletId(req.getOutletId());
			}
		}else{
			reqvo.setOutletId(req.getMerchantUser().getOutletId());
		}
		reqvo.setClientId(req.getClientId());
		PageVo page=new PageVo();
		page.setPageNumber(req.getPageNumber());
		page.setPageSize(req.getPageSize());
		page.setLastPageNumber(req.getLastPageNumber());
		page.setFirstRecordTime(req.getFirstRecordTime());
		page.setLastRecordTime(req.getLastRecordTime());
		reqvo.setPage(page);
		LogCvt.info("订单列表查询条件："+JSON.toJSONString(reqvo));
		QueryOrderByMerchantPhoneVoRes res=orderQueryService.queryOrderByMerchantPhone(reqvo);
		LogCvt.info("订单数据server端返回： "+JSON.toJSONString(res));
		if(res.getOrderVo()!=null&&res.getOrderVo().size()>0){
			List<Query_Face_Group_Res> listf=new ArrayList<Query_Face_Group_Res>();
			for(QueryOrderByMerchantPhoneVo po:res.getOrderVo()){
				Query_Face_Group_Res face=new Query_Face_Group_Res();
                TargetObjectFormat.copyProperties(po, face);
                //加密手机号
                face.setPhone(EncryptUtil.encryptMobile(po.getPhone()));
                //面对面订单小订单号就是大订单号
                if(req.getType()!=null&&req.getType().equals("0")){
                	face.setOrderId(po.getSubOrderId());
                }
                face.setProductImages(po.getProductImages());
                //团购订单列表中消费人信息如是联合登录用户包含身份证号 显示身份号前三后四位，中间用*显示
//                if(po.getMemberName().length()>=18){
//                	String idNo = EncryptUtil.encryptIdentityNo(po.getMemberName());
//                	face.setMemberName(idNo);
//                }
                face.setMemberName(po.getMemberName());
				listf.add(face);
			}
			reMap.put("orderList", listf);
			reMap.put("totalIncome", res.getTotalIncome()); //收入总金额
			reMap.put("orderCount", res.getOrderCount()); //订单条数
			PageRes pageRes = new PageRes();
			TargetObjectFormat.copyProperties(res.getPage(), pageRes);
			reMap.put("page",pageRes);
			
		}else{
			throw new MerchantException(EnumTypes.noresult.getCode(), EnumTypes.noresult.getMsg());
		}
		return reMap;
	}
	
    /**
     * 查询详细内容，团购和面对面订单详细
     * @param map
     * @return
     * @throws MerchantException
     * @throws TException 
     */
	@SuppressWarnings("unused")
	public HashMap<String,Object> query_order_detail(Query_Face_Group_Detail_Req req) throws MerchantException, TException{
		HashMap<String,Object> reMap = new HashMap<String, Object>();
		GetOrderDetailByMerchantPhoneVoReq reqvo=new GetOrderDetailByMerchantPhoneVoReq();
		TargetObjectFormat.copyProperties(req, reqvo);
        GetOrderDetailByMerchantPhoneVoRes res=orderQueryService.getOrderDetailByMerchantPhone(reqvo);
        LogCvt.info("GetOrderDetailByMerchantPhoneVoRes 数据信息："+JSON.toJSONString(res.toString()));
        if(res!=null){
        	Query_Face_Group_Detail_Res face=new Query_Face_Group_Detail_Res();
        	TargetObjectFormat.copyProperties(res, face);
        	//加密手机号
        	face.setPhone(EncryptUtil.encryptMobile(res.getPhone()));
        	if(StringUtil.isNotEmpty(res.getOutletId())&&!res.getOutletId().equals("0")){
        		OutletDetailVo outletVo=outletService.getOutletDetail(res.getOutletId());
        		face.setOutletName(outletVo!=null?outletVo.getOutletName():"");
        	}else{
        		face.setOutletName("总店");
        	}
        	//获取区域地址
        	if(res.getAreaId()>0){
        		String address=common_Service.queryByAreaId(String.valueOf(res.getAreaId()).trim());
        		face.setAddress(address!=null&&address.length()>0?address+res.getAddress():res.getAddress());
        	}
        	
//            if(res.getMemberName().length()>=18){
//            	String idNo = EncryptUtil.encryptIdentityNo(res.getMemberName());
//            	face.setMemberName(idNo);
//            }
        	face.setMemberName(res.getMemberName());
            /**2015-11-17 迭代1.7需求 ---在原有功能基础上将总额更改为实付现金（总金额-抵扣积分），如使用积分则增加积分抵扣显示，如未使用则不显示*/
            face.setPointDiscount(res.getCutPoint());
            
        	if(res.getProductInfos()!=null&&res.getProductInfos().size()>0){
        		List<Query_Face_Group_Detail_Product_Res> listpo=new ArrayList<Query_Face_Group_Detail_Product_Res>();
        		BigDecimal y=new BigDecimal(String.valueOf(0));
        		int bigNumber = 0;int bigWNmber = 0;
        		for(ProductInfoVo vo:res.getProductInfos()){
        	        BigDecimal x = new BigDecimal(String.valueOf(vo.getDeliveryMoney()));
        	        y=y.add(x);
        			Query_Face_Group_Detail_Product_Res fpo=new Query_Face_Group_Detail_Product_Res();
    				TargetObjectFormat.copyProperties(vo, fpo);
    				
    				  //面对面订单小订单号就是大订单号
                    if(req.getType()!=null&&!req.getType().equals("0")){
                    	int number=StringUtil.isNotBlank(vo.getSettlementNumber())?Integer.valueOf(vo.getSettlementNumber()):0;
                    	bigNumber += number;
                    	bigWNmber += (vo.getQuantity() - number);
                    	  //结算状态件数统计
                    	fpo.setSettlementStatus("已结算"+number+"件");
                    }
    				listpo.add(fpo);
        		}
        		face.setBigSettleState("已结算"+bigNumber+"件"+"/未结算"+bigWNmber+"件");
        		face.setTotalDevriyMoney(y.doubleValue());
        		face.setProductInfos(listpo);
        	}
        	
        	reMap.put("orderDetail", face);
        	return reMap;
        }else{
        	throw new MerchantException(res.getResultVo().getResultCode(),res.getResultVo().getResultDesc());
        }
		
	}
	
	
    /**
     * pc查询列表，团购和面对面订单
     * @return
     * @throws Exception 
     */
	public HashMap<String,Object> query_order_group_PC(Query_Face_Group_PC_Req req) throws Exception{
		//固定查询不能大于三90天
		if(StringUtil.isEmpty(req.getStartTime())){
			//
			req.setStartTime(StringUtil.isEmpty(req.getEndTime())?
					PramasUtil.notEmpyBe(0, true):
					PramasUtil.notEmpyBe(PramasUtil.DateFormat(req.getEndTime()), false)
				   );
		}
		if(StringUtil.isEmpty(req.getEndTime())){
			req.setEndTime(StringUtil.isEmpty(req.getStartTime())?
					PramasUtil.notEmpyEn_E(0, true):
					PramasUtil.notEmpyEn_E(PramasUtil.DateFormat(req.getStartTime()), false)
				   );
		}
		if(PramasUtil.notEmpyEn_E(PramasUtil.DateFormat(req.getStartTime()),PramasUtil.DateFormat(req.getEndTime()))){
			throw new MerchantException("608","查询时间不能超过90天");
		}
		//固定查询不能大于三90天
		if(StringUtil.isEmpty(req.getStartTime())){
			//
			req.setStartTime(StringUtil.isEmpty(req.getEndTime())?
					PramasUtil.notEmpyBe(0, true):
					PramasUtil.notEmpyBe(PramasUtil.DateFormat(req.getEndTime()), false)
				   );
		}
		if(StringUtil.isEmpty(req.getEndTime())){
			req.setEndTime(StringUtil.isEmpty(req.getStartTime())?
					PramasUtil.notEmpyEn_E(0, true):
					PramasUtil.notEmpyEn_E(PramasUtil.DateFormat(req.getStartTime()), false)
				   );
		}
		if(PramasUtil.notEmpyEn_E(PramasUtil.DateFormat(req.getStartTime()),PramasUtil.DateFormat(req.getEndTime()))){
			throw new MerchantException("608","查询时间不能超过90天");
		}
		HashMap<String,Object> reMap = new HashMap<String, Object>();
		QueryOrderByMerchantManageVoReq reqvo=new QueryOrderByMerchantManageVoReq();
		TargetObjectFormat.copyProperties(req, reqvo);
		reqvo.setStartTime(PramasUtil.DateFormat(req.getStartTime()));
		reqvo.setEndTime(PramasUtil.DateFormatEnd(StringUtil.isNotEmpty(req.getEndTime())?req.getEndTime():""));
		reqvo.setMerchantId(req.getMerchantUser().getMerchantId());
		if(req.getMerchantUser().getIsSuperAdmin()){
			if(StringUtils.isNotBlank(req.getOutletId())){
				reqvo.setOutletId(req.getOutletId());
			}
		}else{
			reqvo.setOutletId(req.getMerchantUser().getOutletId());
		}
		reqvo.setClientId(req.getClientId());
		reqvo.setOperType("1");//1查询，2下载

		PageVo page=new PageVo();
		page.setPageNumber(req.getPageNumber());
		page.setPageSize(req.getPageSize());
		page.setLastPageNumber(req.getLastPageNumber());
		page.setFirstRecordTime(req.getFirstRecordTime());
		page.setLastRecordTime(req.getLastRecordTime());
		reqvo.setPage(page);
		
		QueryOrderByMerchantManageVoRes res=orderQueryService.queryOrderByMerchantManage(reqvo);
		LogCvt.info("订单列表数据server端"+JSON.toJSONString(res));
		if(res.getOrderVo()!=null&&res.getOrderVo().size()>0){
			List<Query_Face_Group_PC_Res> listf=new ArrayList<Query_Face_Group_PC_Res>();
			for(QueryOrderByMerchantManageVo po:res.getOrderVo()){
				Query_Face_Group_PC_Res face=new Query_Face_Group_PC_Res();
                TargetObjectFormat.copyProperties(po, face);
            	//获取区域地址
//            	if(po.getAreaId()>0){
//            		String address=outlet_Service.query_areaId_by_cityId(String.valueOf(po.getAreaId()).trim());
//            		face.setAddress(address!=null&&address.length()>0?address+po.getAddress():po.getAddress());
//            	}
                 //获取商品名称*件数
                if(po.getProductInfoVo()!=null&&po.getProductInfoVo().size()>0){
                	StringBuffer sb=new StringBuffer();
                    for(ProductInfoVo pvo:po.getProductInfoVo()){
                    	sb.append(pvo.getProductName()).append(pvo.getQuantity()>1?"x"+pvo.getQuantity():"").append("、");
                    }
                    face.setProductName(sb.substring(0,sb.length()-1));
                }
                //结算状态件数统计
                face.setSettleState(po.getSettlementNumber()>0?
                		po.getQuantity()==po.getSettlementNumber()?
                				"已结算"+po.getSettlementNumber()+"件":
                				   "已结算"+po.getSettlementNumber()+"件"+"/未结算"+(po.getQuantity()-po.getSettlementNumber()+"件")
                				:"未结算"+po.getQuantity()+"件");
                face.setType(po.getType());
                //根据用户id查询操作人
        	    if(po.getMerchantUserId()>0){
        		   face.setUserName(user_Service.query_user_by_userId(po.getMerchantUserId()));  
        	    }else{
        	    	face.setUserName("");
        	    }
                //面对面订单小订单号就是大订单号
                if(req.getType()!=null&&req.getType().equals("0")){
                	face.setOrderId(po.getSubOrderId());
                }
				listf.add(face);
			}
			reMap.put("orderList", listf);
			PageRes pageRes = new PageRes();
			TargetObjectFormat.copyProperties(res.getPageVo(), pageRes);
			reMap.put("page",pageRes);
			
		}else{
			throw new MerchantException(res.getResultVo().getResultCode(),res.getResultVo().getResultDesc());
		}
		return reMap;
	}
	
    /**
     * pc查询列表，团购和面对面订单 下载
     * @return
     * @throws Exception 
     */
	public HashMap<String,Object> query_order_group_down(Query_Face_Group_PC_Req req) throws Exception{
		//固定查询不能大于三90天
		if(StringUtil.isEmpty(req.getStartTime())){
			//
			req.setStartTime(StringUtil.isEmpty(req.getEndTime())?
					PramasUtil.notEmpyBe(0, true):
					PramasUtil.notEmpyBe(PramasUtil.DateFormat(req.getEndTime()), false)
				   );
		}
		if(StringUtil.isEmpty(req.getEndTime())){
			req.setEndTime(StringUtil.isEmpty(req.getStartTime())?
					PramasUtil.notEmpyEn_E(0, true):
					PramasUtil.notEmpyEn_E(PramasUtil.DateFormat(req.getStartTime()), false)
				   );
		}
		if(PramasUtil.notEmpyEn_E(PramasUtil.DateFormat(req.getStartTime()),PramasUtil.DateFormat(req.getEndTime()))){
			throw new MerchantException("608","查询时间不能超过90天");
		}
		HashMap<String,Object> reMap = new HashMap<String, Object>();
		QueryOrderByMerchantManageVoReq reqvo=new QueryOrderByMerchantManageVoReq();
		TargetObjectFormat.copyProperties(req, reqvo);
		reqvo.setStartTime(PramasUtil.DateFormat(req.getStartTime()));
		reqvo.setEndTime(PramasUtil.DateFormatEnd(req.getEndTime()));
		reqvo.setMerchantId(req.getMerchantUser().getMerchantId());
		reqvo.setOutletId(req.getOutletId());
		reqvo.setClientId(req.getClientId());
		reqvo.setOperType("1");//1查询，2下载
		
		PageVo page=new PageVo();
		page.setPageNumber(req.getPageNumber());
		page.setPageSize(req.getPageSize());
		page.setLastPageNumber(req.getLastPageNumber());
		page.setFirstRecordTime(req.getFirstRecordTime());
		page.setLastRecordTime(req.getLastRecordTime());
		reqvo.setPage(page);
		
		QueryOrderByMerchantManageVoRes res=orderQueryService.queryOrderByMerchantManage(reqvo);
//		QueryOrderByMerchantManageVoRes res=searchCruiseRoute(reqvo);
		if(res.getOrderVo()!=null&&res.getOrderVo().size()>0){
			List<Query_Face_Group_PC_Res> listf=new ArrayList<Query_Face_Group_PC_Res>();
			for(QueryOrderByMerchantManageVo po:res.getOrderVo()){
				Query_Face_Group_PC_Res face=new Query_Face_Group_PC_Res();
                TargetObjectFormat.copyProperties(po, face);
            	//获取区域地址
            	if(po.getAreaId()>0){
            		String address=common_Service.queryByAreaId(String.valueOf(po.getAreaId()).trim());
            		face.setAddress(address!=null&&address.length()>0?address+po.getAddress():po.getAddress());
            	}
                //获取商品名称*件数
               if(po.getProductInfoVo()!=null&&po.getProductInfoVo().size()>0){
               	StringBuffer sb=new StringBuffer();
                   for(ProductInfoVo pvo:po.getProductInfoVo()){
                   	sb.append(pvo.getProductName()).append(pvo.getQuantity()>1?"x"+pvo.getQuantity():"").append("、");
                   }
                   face.setProductName(sb.substring(0,sb.length()-1));
               }
               //面对面订单小订单号就是大订单号
               if(req.getType()!=null&&req.getType().equals("0")){
            	    //根据用户id查询操作人
            	    if(po.getMerchantUserId()>0){
            		   face.setUserName(user_Service.query_user_by_userId(po.getMerchantUserId()));  
            	    }
               		face.setOrderId(po.getSubOrderId());
               		Map<String,String> mapset=new HashMap<String,String>();
               		mapset.put("0", "未结算");
               		mapset.put("1", "结算中");
               		mapset.put("2", "已结算");
               		mapset.put("3", "结算失败");
               		mapset.put("4", "无效结算记录");
               		face.setSettleState(StringUtil.isNotEmpty(face.getSettlementStatus())?mapset.get(face.getSettlementStatus()):"--");
               }else{
                   //结算状态件数统计
                   face.setSettleState(po.getSettlementNumber()>0?
                   		po.getQuantity()==po.getSettlementNumber()?
                   				"已结算"+po.getSettlementNumber()+"件":
                   				   "已结算"+po.getSettlementNumber()+"件"+"/未结算"+(po.getQuantity()-po.getSettlementNumber()+"件")
                   				:"未结算"+po.getQuantity()+"件");
               }
				listf.add(face);
			}
			reMap.put("orderList", listf);
			PageRes pageRes = new PageRes();
			TargetObjectFormat.copyProperties(res.getPageVo(), pageRes);
			reMap.put("page",pageRes);
		}else{
//			return reMap;
        	throw new MerchantException(res.getResultVo().getResultCode(),res.getResultVo().getResultDesc());
		}
		return reMap;
	}

    /**
     * pc查询详细内容，团购和面对面订单详细
     * @param map
     * @return
     * @throws MerchantException
     * @throws TException 
     */
	@SuppressWarnings("null")
	public HashMap<String,Object> query_order_group_detail(Query_Face_Group_Detail_PC_Req req) throws MerchantException, TException{
		HashMap<String,Object> reMap = new HashMap<String, Object>();
		GetOrderDetailByMerchantManageVoReq reqvo=new GetOrderDetailByMerchantManageVoReq();
		TargetObjectFormat.copyProperties(req, reqvo);
		reqvo.setClientId(req.getClientId());
		GetOrderDetailByMerchantManageVoRes res=orderQueryService.getOrderDetailByMerchantManage(reqvo);
        if(res!=null){
        	Query_Face_Group_Detail_PC_Res face=new Query_Face_Group_Detail_PC_Res();
            TargetObjectFormat.copyProperties(res, face);
            face.setClientId(req.getClientId());
        	face.setSubOrderId(StringUtil.isNotEmpty(req.getSubOrderId())?req.getSubOrderId():"");
        	face.setOrderId(StringUtil.isNotEmpty(req.getOrderId())?req.getOrderId():"");
        	//获取区域地址
        	if(res.getAreaId()>0){
        		String address=common_Service.queryByAreaId(String.valueOf(res.getAreaId()).trim());
        		face.setAddress(address!=null&&address.length()>0?address+res.getAddress():res.getAddress());
        	}
        	
        	if(StringUtil.isNotEmpty(res.getOutletId())&&!res.getOutletId().equals("0")){
        		OutletDetailVo outletVo=outletService.getOutletDetail(res.getOutletId());
        		face.setOutletName(outletVo!=null?outletVo.getOutletName():"");
        	}else{
        		face.setOutletName("总店");
        	}
        	if(res.getProductInfo()!=null&&res.getProductInfo().size()>0){
        		List<Query_Face_Group_Detail_Product_PC_Res> listpo=new ArrayList<Query_Face_Group_Detail_Product_PC_Res>();
        		BigDecimal y=new BigDecimal(String.valueOf(0));
        		for(ProductInfoVo vo:res.getProductInfo()){
        	        BigDecimal x = new BigDecimal(String.valueOf(vo.getDeliveryMoney()));
        	        y=y.add(x);
        			Query_Face_Group_Detail_Product_PC_Res fpo=new Query_Face_Group_Detail_Product_PC_Res();
        			TargetObjectFormat.copyProperties(vo, fpo);
                    //面对面订单小订单号就是大订单号
                    if(req.getType()!=null&&!req.getType().equals("0")){
                    	int number=StringUtil.isNotBlank(vo.getSettlementNumber())?Integer.valueOf(vo.getSettlementNumber()):0;
                        //结算状态件数统计
                    	fpo.setSettlementStatus(number>0?
                        		vo.getQuantity()==number?
                        				"已结算"+number+"件":
                        				   "已结算"+number+"件"+"/未结算"+(vo.getQuantity()-number)+"件"
                        				:"未结算"+vo.getQuantity()+"件");
                    }
        			listpo.add(fpo);
        		}
        		face.setTotalDeliveryMoney(y.doubleValue());
        		face.setProductInfo(listpo);
        	}
        	reMap.put("orderDetail", face);
        	return reMap;
        }else{
        	throw new MerchantException(res.getResultVo().getResultCode(),res.getResultVo().getResultDesc());
        }
		
	}

	/**
	 * 订单发货
	 * @tilte shippingOrder
	 * @author zxl
	 * @date 2015年4月8日 下午5:13:18
	 * @param po
	 * @return
	 * @throws MerchantException
	 * @throws TException
	 */
	public HashMap<String,Object> shippingOrder(Order_Shipping_Req req) throws MerchantException, TException{
		HashMap<String,Object> reMap = new HashMap<String, Object>();
		ShippingOrderVoReq reqvo = new ShippingOrderVoReq();
		TargetObjectFormat.copyProperties(req, reqvo);
        reqvo.setMerchantUserId(req.getMerchantUser().getId()+"");
		ShippingOrderVoRes resp = orderService.shippingOrder(reqvo);
		if(!EnumTypes.success.getCode().equals(resp.getResultVo().getResultCode())){
			throw new MerchantException(resp.getResultVo().getResultCode(),resp.getResultVo().getResultDesc());
		}
		return reMap;
	}
   
	
	/**
	 * 获取二维码信息
	 * @param clientId
	 * @param qrcode
	 * @return
	 * @throws MerchantException
	 * @throws TException
	 */

	public HashMap<String,Object> query_qrcode_detail(String clientId,String qrcode) throws MerchantException, TException{
		HashMap<String,Object> reMap = new HashMap<String, Object>();
		GetOrderByQrcodeVoReq reqvo = new GetOrderByQrcodeVoReq();
        reqvo.setClientId(clientId);
        reqvo.setQrcode(qrcode);
        GetOrderByQrcodeVoRes resp = orderService.getOrderByQrcode(reqvo);
		if(EnumTypes.success.getCode().equals(resp.getResultVo().getResultCode())){
			
			reMap.put("orderId", resp.getOrderId());
			reMap.put("orderStatus", resp.getOrderStatus());
			reMap.put("paymentTime", resp.getPaymentTime());
			reMap.put("totalPrice", resp.getTotalPrice());
			reMap.put("memberCode", resp.getMemberCode());
			//如是联合登录用户包含身份证号 显示身份号前三后四位，中间用*显示
//            if(resp.getMemberName().length()>=18){
//            	String idNo = EncryptUtil.encryptIdentityNo(resp.getMemberName());
//            	reMap.put("memberName", idNo);
//            }else{
            	reMap.put("memberName", resp.getMemberName());
//            }
            String outletName="总店";
            if(StringUtils.isNotEmpty(resp.getOutletId())){
            	OutletVo outletVo=outletService.getOutletByOutletId(resp.getOutletId());
            	outletName=outletVo!=null?outletVo.getOutletName():outletName;
            }
            
            reMap.put("outletName", StringUtils.isNotEmpty(outletName)?outletName:"总店");
            reMap.put("tlementStatus", resp.getSettlementStatus());
       
		}else{
			throw new MerchantException(resp.getResultVo().getResultCode(),resp.getResultVo().getResultDesc());
		}
		return reMap;
	}
	
	
	/**
	 * 查询商户结算汇总
	 * @param req
	 * @return
	 * @throws MerchantException
	 * @throws TException
	 */
	public HashMap<String,Object> query_order_settlement(Query_Merchant_Settlement_Req req) throws MerchantException, TException{
		HashMap<String,Object> sumMap = new HashMap<String, Object>();
		QueryMerchantSettlementReq qmsr = new QueryMerchantSettlementReq();
		qmsr.setClientId(req.getClientId());
		qmsr.setMerchantId(req.getMerchantUser().getMerchantId());
		
		if(StringUtils.isNotBlank(req.getMerchantUser().getOutletId())){
			qmsr.setOutletId(req.getMerchantUser().getOutletId());
		}
		//如果角色为非超级管理员时
//		if( !req.getRoleId().equals(UserType.admin.getCode()) && StringUtils.isNotBlank(req.getUserId()) ){
//			qmsr.setUserId(req.getUserId());
//		}
		QueryMerchantSettlementRes msr = orderQueryService.queryMerchantSettlement(qmsr);
		LogCvt.info("结算汇总server端返回： "+JSON.toJSONString(msr));
		if(EnumTypes.success.getCode().equals(msr.getResultVo().getResultCode())){
			Query_Merchant_Settlement_Res query_msr = new Query_Merchant_Settlement_Res();
			TargetObjectFormat.copyProperties(msr, query_msr);
			sumMap.put("merchantHzdd", query_msr);
		}else{
			throw new MerchantException(EnumTypes.noresult.getCode(), EnumTypes.noresult.getMsg());
		}
		return sumMap;
	}
	
	/**
	 * 
	 * query_order_down:(订单下载).
	 *
	 * @author wm
	 * 2015年9月9日 下午2:30:10
	 * @param req
	 * @return
	 * @throws Exception
	 *
	 */
	public HashMap<String,Object> query_order_down(Query_Face_Group_PC_Req req) throws Exception{
		//固定查询不能大于三90天
		if(StringUtil.isEmpty(req.getStartTime())){
			//
			req.setStartTime(StringUtil.isEmpty(req.getEndTime())?
					PramasUtil.notEmpyBe(0, true):
					PramasUtil.notEmpyBe(PramasUtil.DateFormat(req.getEndTime()), false)
				   );
		}
		if(StringUtil.isEmpty(req.getEndTime())){
			req.setEndTime(StringUtil.isEmpty(req.getStartTime())?
					PramasUtil.notEmpyEn_E(0, true):
					PramasUtil.notEmpyEn_E(PramasUtil.DateFormat(req.getStartTime()), false)
				   );
		}
		if(PramasUtil.notEmpyEn_E(PramasUtil.DateFormat(req.getStartTime()),PramasUtil.DateFormat(req.getEndTime()))){
			throw new MerchantException("608","查询时间不能超过90天");
		}
		HashMap<String,Object> reMap = new HashMap<String, Object>();
		QueryOrderByMerchantManageVoReq reqvo=new QueryOrderByMerchantManageVoReq();
		TargetObjectFormat.copyProperties(req, reqvo);
		reqvo.setStartTime(PramasUtil.DateFormat(req.getStartTime()));
		reqvo.setEndTime(PramasUtil.DateFormatEnd(req.getEndTime()));
		reqvo.setMerchantId(req.getMerchantUser().getMerchantId());
		reqvo.setOutletId(req.getOutletId());
		reqvo.setClientId(req.getClientId());
		reqvo.setOperType("1");//1查询，2下载
		
		ExportResultRes resV=orderQueryService.exportOrderByMerchantManage(reqvo);
		if(EnumTypes.success.getCode().equals(resV.getResultVo().getResultCode()) && StringUtil.isNotBlank(resV.getUrl())){
			LogCvt.info("订单下载返回参数："+JSON.toJSONString(resV));
			reMap.put("orderUrl", resV.getUrl());
		}else{
			throw new MerchantException(resV.getResultVo().getResultCode(),resV.getResultVo().getResultDesc());
		}
		return reMap;
	}
	
	/**
	 * 
	 * query_order_detail_pc_new:订单详情新需求
	 *
	 * @author wm
	 * 2015年9月11日 下午2:52:04
	 * @param req
	 * @return
	 * @throws MerchantException
	 * @throws TException
	 *
	 */

	public HashMap<String,Object> query_order_detail_pc_new(Query_Order_Detail_PC_Req req) throws MerchantException, TException{
		HashMap<String,Object> reMap = new HashMap<String, Object>();
		GetOrderDetailByMerchantManageVoReq reqvo=new GetOrderDetailByMerchantManageVoReq();
		TargetObjectFormat.copyProperties(req, reqvo);
		reqvo.setClientId(req.getClientId());
		GetOrderDetailByBankManageVoRes res=orderQueryService.getOrderDetailByMerchantManageNew(reqvo);
		LogCvt.info("订单详情优化接收参数:  "+JSON.toJSONString(res));
        if(res.getResultVo().getResultCode().equals(EnumTypes.success.getCode())){
        	Query_Order_Detail_PC_Res orderRes = new Query_Order_Detail_PC_Res();
        	TargetObjectFormat.copyProperties(res, orderRes);
        	if(res.getMerchantUserId()>0&&StringUtils.isEmpty(res.getMerchantUserName())){
        		MerchantUserVo uservo=merchantUserService.getMerchantUserById(res.getMerchantUserId());
        		if(uservo!=null)orderRes.setMerchantUserName(uservo.getUsername());
        	}
        	
        	/**2015-11-20 迭代1.7需求 商户默认门店 */
        	if(StringUtil.isNotEmpty(res.getOutletId())){
        		OutletDetailVo outletVo=outletService.getOutletDetail(res.getOutletId());
        		if(outletVo!=null)orderRes.setOutletName(outletVo.getOutletName());
        	}
        	//提货信息
        	Query_Order_Detail_PC_DeliverInfo_Res deliverInfo = new Query_Order_Detail_PC_DeliverInfo_Res();
        	TargetObjectFormat.copyProperties(res.getDeliverInfoVo(), deliverInfo);
        	//商品信息
        	List<Query_Order_Detail_PC_Product_Res> productList = new ArrayList<Query_Order_Detail_PC_Product_Res>();
        	
        	int quantity = 0;
        	int refundIndex = 0;
    		int refundingIndex = 0;
        	if(res.getProductInfos() != null && res.getProductInfos().size()>0){
        		for(ProductInfoVo product : res.getProductInfos()){
            		Query_Order_Detail_PC_Product_Res productInfos = new Query_Order_Detail_PC_Product_Res();
            		TargetObjectFormat.copyProperties(product, productInfos);
            		
            		quantity += product.getQuantity();// 购买总数
            		// 团购是否全单退款
    				if (ProductType.group.getCode().equals(reqvo.getType())) {
    					// 退款完成
    					if ("3".equals(product.getRefundState())) {
    						++refundIndex;
    					}
    					// 退款中
    					if ("2".equals(product.getRefundState())) {
    						++refundingIndex;
    						//退款中没有退款成功时间
    						productInfos.setRefundSuccessTime(0);
    					}
    				}
    				//卷码加密
    				String tackCode = EncryptUtil.encryptTack(product.getTakeCode());
    				productInfos.setTakeCode(tackCode);
    				MerchantUserVo merchantUserVo = merchantUserService.getMerchantUserById(product.getMerchantUserId());
    				LogCvt.info("操作员对象信息 : " + JSON.toJSONString(merchantUserVo));
    				//操作员
    				if (merchantUserVo != null) {
    					productInfos.setMerchantUserName(merchantUserVo.getUsername());
    				}
            		productList.add(productInfos);
            	}
        		orderRes.setProductInfos(productList);
        	}
        	//机构名称处理
        	orderRes.setOrgNames(orgNameUtil.getOrgNames(res.getOrgNames()));
        	orderRes.setDeliverInfoVo(deliverInfo);
        	//0 不是全单退款  1 是全单退款
        	int productinfos=res.getProductInfos()!=null?res.getProductInfos().size():0;
    		if ((refundIndex + refundingIndex)>0&&(refundIndex + refundingIndex)==productinfos) {
    			orderRes.setIsAllRefund("1");
    			//只要有退款中存在那么退款成功时间就赋值为0
    			orderRes.setRefundSuccessTime(refundingIndex>0?0:orderRes.getRefundSuccessTime());
			} else {
				orderRes.setIsAllRefund("0");
			}
        	orderRes.setBuyTotalPoint(quantity - refundIndex - refundingIndex);// 总购买数量-退款数量
			// 如果是团购
			if (ProductType.group.getCode().equals(reqvo.getType())) {
				orderRes.setBuyTotalPoint(res.getProductInfos().size() - refundIndex - refundingIndex);// 总购买数量-退款数量
			}
        	LogCvt.info("订单详情优化web给前端:  "+JSON.toJSONString(orderRes));
        	reMap.put("orderDetail", orderRes);
        	return reMap;
        }else{
        	throw new MerchantException(res.getResultVo().getResultCode(),res.getResultVo().getResultDesc());
        }
		
	}
}
