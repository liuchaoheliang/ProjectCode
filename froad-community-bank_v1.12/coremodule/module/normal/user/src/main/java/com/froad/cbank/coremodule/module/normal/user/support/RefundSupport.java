package com.froad.cbank.coremodule.module.normal.user.support;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.enums.ResultCode;
import com.froad.cbank.coremodule.framework.common.util.type.ArrayUtil;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.user.pojo.PagePojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.RefundInfoPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.RefundProduct;
import com.froad.cbank.coremodule.module.normal.user.utils.DateUtils;
import com.froad.thrift.service.ClientService;
import com.froad.thrift.service.RefundService;
import com.froad.thrift.vo.ClientVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.refund.RefundDetailRequestVo;
import com.froad.thrift.vo.refund.RefundInfoVo;
import com.froad.thrift.vo.refund.RefundListRequestVo;
import com.froad.thrift.vo.refund.RefundListResponseVo;
import com.froad.thrift.vo.refund.RefundProductVo;
import com.froad.thrift.vo.refund.RefundRequestVo;
import com.froad.thrift.vo.refund.RefundResponseVo;
import com.pay.user.dto.Result;
import com.pay.user.service.VIPSpecService;


	/**
	 * 类描述：退款相关接口支持
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2015 
	 * @author: 刘超 liuchao@f-road.com.cn
	 * @time: 2015年3月25日 下午3:43:56 
	 */
@Service
public class RefundSupport extends BaseSupport {

	@Resource
	private RefundService.Iface refundService;
	@Resource
	private VIPSpecService vipSpecService;
	@Resource
	private ClientService.Iface clientService;
	
	/**
	  * 方法描述：用户退款接口
	  * @param: orgOrderId	原订单号,orgSubOrderId	原子订单号
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年3月25日 下午3:45:29
	  */
	public HashMap<String, Object> doOrderRefund(String clientId,String subOrderId,String option,String reason,List<RefundProductVo> productList){
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		
		RefundRequestVo refundRequestVo = new RefundRequestVo();
		refundRequestVo.setSubOrderId(subOrderId);
		refundRequestVo.setProductList(productList);
		refundRequestVo.setClientId(clientId);
		refundRequestVo.setOption(option);
		refundRequestVo.setReason(reason);
		RefundResponseVo refundResponseVo = null; 
		try {
			LogCvt.info("调用接口传入参数:"+JSON.toJSONString(refundRequestVo)  );
			refundResponseVo=refundService.doOrderRefund(refundRequestVo);
			if("0000".equals(refundResponseVo.getResultVo().getResultCode())){
				resMap.put("refundId", refundResponseVo.getRefundInfo().getRefundId());
				resMap.put("refundAmount", refundResponseVo.getRefundInfo().getRefundAmount());
				resMap.put("refundPoints", refundResponseVo.getRefundInfo().getRefundPoints());
			}else{
				LogCvt.info("退款申请失败："+JSON.toJSONString( refundResponseVo.getResultVo()) );
				resMap.put("code",refundResponseVo.getResultVo().getResultCode() );
				resMap.put("message", refundResponseVo.getResultVo().getResultDesc());
			}
		} catch (TException e) {
			LogCvt.error("用户退款出错", e);
		}
		return resMap;
	}
	
	
	
	
	/**
	  * 方法描述：根据用户获取用户的退款列表
	  * @param:int pageNumber 当前页数， int pageSize 每页容量, member_code	会员编号	Long
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年3月25日 下午3:48:09
	  */
	public HashMap<String, Object> getRefundListByMember(String clientId, String memberCode,String timeType,String status ,PagePojo pagePojo ){
		HashMap<String, Object> resMap = new HashMap<String, Object>();

		
		RefundListRequestVo refundResponseVo = new RefundListRequestVo();
		
		//分页参数
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(pagePojo.getPageNumber());
		pageVo.setPageSize(pagePojo.getPageSize());
		pageVo.setLastPageNumber(pagePojo.getLastPageNumber());
		pageVo.setLastRecordTime(pagePojo.getLastRecordTime());
		pageVo.setFirstRecordTime(pagePojo.getFirstRecordTime());
		
		refundResponseVo.setPageVo(pageVo);
		
		//条件参数
		refundResponseVo.setClientId(clientId);
		refundResponseVo.setSource("1");//默认设置为1，为查询会员退款记录
		refundResponseVo.setMemberCode(memberCode);	
		if( !StringUtil.empty(status) ){
			refundResponseVo.setStatus(status);
		}
		
		String strEndDate = String.valueOf(Calendar.getInstance().getTimeInMillis());
		Date startDate =null;
		String strStartDate ="";
		//是否最近三个月
		if( "3".equals(timeType) ){
			startDate = DateUtils.getDateBeforeOrAfterNMonths(
					-3, Calendar.getInstance().getTime());
			strStartDate = String.valueOf(startDate.getTime());
		}else if ( "6".equals(timeType) ){
			startDate = DateUtils.getDateBeforeOrAfterNMonths(
					-6, Calendar.getInstance().getTime());
			strStartDate = String.valueOf(startDate.getTime());
		}else if ( "12".equals(timeType) ){
			startDate = DateUtils.getDateBeforeOrAfterNMonths(
					-12, Calendar.getInstance().getTime());
			strStartDate = String.valueOf(startDate.getTime());
		}else if ( "0".equals(timeType) ){
			strEndDate = null;
			strStartDate = null;
		}
		
		
		refundResponseVo.setStartDate(strStartDate) ;
		refundResponseVo.setEndDate(strEndDate);
		
		RefundListResponseVo refundListResponseVo = null ;
		PagePojo page=new PagePojo();
		List<RefundInfoPojo> refundInfoList=null ;
		List<RefundProduct> products=null ;
		RefundInfoPojo refundInfoPojo=null;
		RefundProduct refundProduct= null ;
		try {
			LogCvt.info("调用接口传入参数:"+JSON.toJSONString(refundResponseVo)  );
			refundListResponseVo=refundService.getRefundList(refundResponseVo);
			
			//分页数据
			if(refundListResponseVo.isSetPageVo()){
				BeanUtils.copyProperties(page,refundListResponseVo.getPageVo());
			}
			
			
			//遍历结果集合
			if(  !ArrayUtil.empty(refundListResponseVo.getRefundInfoList())  ){
				refundInfoList=new ArrayList<RefundInfoPojo>();
				for(RefundInfoVo temp:refundListResponseVo.getRefundInfoList() ){
					//转化退款列表实体pojo
					refundInfoPojo= new RefundInfoPojo();
					BeanUtils.copyProperties(refundInfoPojo,temp);
					if(  !ArrayUtil.empty(temp.getProductList() )  ){
						//获取退款信息对应的商品列表信息
						products=new ArrayList<RefundProduct>();
						for(RefundProductVo temp1 : temp.getProductList()  ){
							//转化退款商品实体pojo
							refundProduct=new RefundProduct();
							BeanUtils.copyProperties(refundProduct, temp1);
							products.add(refundProduct);
						}
						refundInfoPojo.setProductList(products);
					}
					
					refundInfoList.add(refundInfoPojo);
				}
				
			}
			resMap.put("page", page);
			resMap.put("resResult", refundInfoList);
		} catch (TException e) {
			LogCvt.error("获取用户的退款列表出错", e);
		} 
		return resMap;
	}
	
	
	
	
	/**
	  * 方法描述：获取退款详情
	  * @param: refund_id	退款id
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年3月25日 下午3:49:20
	  */
	public HashMap<String, Object> getRefundDetails(String clientId,String refundId){
		
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		RefundDetailRequestVo detailRequestVo=new RefundDetailRequestVo();
		detailRequestVo.setClientId(clientId);
		detailRequestVo.setRefundId(refundId);
		RefundResponseVo refundResponseVo=null; 
		try {
			refundResponseVo=refundService.getRefundDetail(detailRequestVo);
			RefundInfoPojo detailPojo =new RefundInfoPojo();
			BeanUtils.copyProperties(detailPojo, refundResponseVo.getRefundInfo());
			
			RefundProduct refundProduct= null ;
			List<RefundProduct> products=null;
			
			if( !ArrayUtil.empty(refundResponseVo.getRefundInfo().getProductList() ) ){
				products=new ArrayList<RefundProduct>();
				for(RefundProductVo temp : refundResponseVo.getRefundInfo().getProductList()  ){
					refundProduct=new RefundProduct();
					BeanUtils.copyProperties(refundProduct, temp);
					products.add(refundProduct);
				}
			}
			detailPojo.setProductList(products);
			resMap.put("resResult",detailPojo);
			
		} catch (TException e) {
			LogCvt.error("获取退款详情出错", e);
		} 
		return resMap;
	}
	
	
	/**
	 * getRefundInfo:(获取可退款订单的详情)
	 *
	 * @author 刘超 liuchao@f-road.com.cn
	 * 2015年8月25日 下午5:36:20
	 * @return
	 * 
	 */
	public HashMap<String, Object> getRefundInfo(String clientId, String subOrderId) {
		HashMap<String, Object> resMap = new HashMap<String, Object>();

		RefundRequestVo refundRequestVo = new RefundRequestVo();
		refundRequestVo.setSubOrderId(subOrderId);
		refundRequestVo.setClientId(clientId);
		// 1是查询，2是退款
		refundRequestVo.setOption("1");
		RefundResponseVo refundResponseVo = null;
		RefundInfoPojo refundInfoPojo = new RefundInfoPojo();
		try {
			LogCvt.info("调用获取可退款订单的详情接口传入参数:" + JSON.toJSONString(refundRequestVo));
			refundResponseVo = refundService.doOrderRefund(refundRequestVo);

			// 转化退款列表实体pojo
			if ("0000".equals(refundResponseVo.getResultVo().getResultCode())) {
				BeanUtils.copyProperties(refundInfoPojo, refundResponseVo.getRefundInfo());
				if (!ArrayUtil.empty(refundResponseVo.getRefundInfo().getProductList())) {
					// 获取退款信息对应的商品列表信息
					ArrayList<RefundProduct> products = new ArrayList<RefundProduct>();
					for (RefundProductVo temp1 : refundResponseVo.getRefundInfo().getProductList()) {
						// 转化退款商品实体pojo
						RefundProduct refundProduct = new RefundProduct();
						BeanUtils.copyProperties(refundProduct, temp1);
						products.add(refundProduct);
					}
					refundInfoPojo.setProductList(products);
				}
			}
		} catch (TException e) {
			LogCvt.error("获取可退款订单的详情接口出错", e);
		}
		resMap.put("refundInfo", refundInfoPojo);
		return resMap;
	}
	
	/**
	 * 方法描述：vip退款接口
	 * @author: yfy
	 * @time: 2015年3月25日 下午3:45:29
	 * @param orderId
	 * @return
	 */
	public HashMap<String, Object> doOrderRefundVip(String orderId,String clientId,Long memberCode){
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		
		RefundResponseVo refundResponseVo = null; 
		try {
			LogCvt.info("调用接口传入参数:orderId:"+orderId+",memberCode:"+memberCode );
			ClientVo clientVo = clientService.getClientById(clientId);
			if(StringUtil.isNotBlank(clientVo.getBankOrg())){
				//VIP退款校验
				refundResponseVo = refundService.doOrderRefundOfVIP(orderId, clientId, memberCode,"1");//1-校验
				if(refundResponseVo.getResultVo().getResultCode().equals(ResultCode.success.getCode())){
					//校验VIP是否支持取消
					Result result = vipSpecService.cancelCheck(memberCode, clientVo.getBankOrg());
					if(result.getResult()){
						//退款
						refundResponseVo=refundService.doOrderRefundOfVIP(orderId, clientId, memberCode,"2");//1-校验 2-退款
						if(ResultCode.success.getCode().equals(refundResponseVo.getResultVo().getResultCode())){
							resMap.put("refundId", refundResponseVo.getRefundInfo().getRefundId());
							resMap.put("refundAmount", refundResponseVo.getRefundInfo().getRefundAmount());
							resMap.put("refundPoints", refundResponseVo.getRefundInfo().getRefundPoints());
						}else{
							LogCvt.info("VIP退款失败："+JSON.toJSONString(refundResponseVo.getResultVo()));
							resMap.put("code",refundResponseVo.getResultVo().getResultCode());
							resMap.put("message", refundResponseVo.getResultVo().getResultDesc());
						}
					}else{
						LogCvt.info("校验VIP是否支持退款失败："+JSON.toJSONString(result));
						resMap.put("code",result.getCode());
						resMap.put("message", result.getMessage());
					}
				}else{
					LogCvt.info("VIP退款校验失败："+JSON.toJSONString(refundResponseVo.getResultVo()));
					resMap.put("code",refundResponseVo.getResultVo().getResultCode());
					resMap.put("message", refundResponseVo.getResultVo().getResultDesc());
				}
			}else{
				LogCvt.info("VIP退款查询客户端信息失败："+JSON.toJSONString(clientVo.getBankOrg()));
				resMap.put("code",ResultCode.failed.getCode());
				resMap.put("message", "当前客户端没有银行标识");
			}
		} catch (TException e) {
			LogCvt.error("vip退款出错", e);
		}
		return resMap;
	}
	
	/**
	 * vip退款校验
	 * @author: yfy
	 * @time: 2015年12月10日 下午2:25:29
	 * @param orderId
	 * @param clientId
	 * @param memberCode
	 * @return
	 */
	public Boolean doOrderRefundOfVIP(String orderId,String clientId,Long memberCode){
		Boolean result = false;
		try{
			RefundResponseVo refundResponseVo = refundService.doOrderRefundOfVIP(orderId, clientId, memberCode,"1");//1-校验
			if(refundResponseVo.getResultVo().getResultCode().equals(ResultCode.success.getCode())){
				result = true;
			}
		} catch (TException e) {
			LogCvt.error("vip退款校验接口异常", e);
		}
		return result;
	}
	
}
