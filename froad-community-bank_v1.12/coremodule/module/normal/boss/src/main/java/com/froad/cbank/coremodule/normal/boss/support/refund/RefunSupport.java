package com.froad.cbank.coremodule.normal.boss.support.refund;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.ArrayUtil;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;
import com.froad.cbank.coremodule.normal.boss.pojo.refund.RefGood;
import com.froad.cbank.coremodule.normal.boss.pojo.refund.RefRecord;
import com.froad.cbank.coremodule.normal.boss.pojo.refund.RefundDetailReq;
import com.froad.cbank.coremodule.normal.boss.pojo.refund.RefundListReq;
import com.froad.cbank.coremodule.normal.boss.pojo.refund.RefundVo;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.PramasUtil;
import com.froad.thrift.service.BossQueryRefundOrderService;
import com.froad.thrift.service.ClientService;
import com.froad.thrift.vo.ClientVo;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.refund.BossQueryRefundDetailReq;
import com.froad.thrift.vo.refund.BossQueryRefundDetailRes;
import com.froad.thrift.vo.refund.BossQueryRefundInfoVo;
import com.froad.thrift.vo.refund.BossQueryRefundOrderListReq;
import com.froad.thrift.vo.refund.BossQueryRefundOrderListRes;
import com.froad.thrift.vo.refund.BossQueryRefundPaymentVo;
import com.froad.thrift.vo.refund.BossQueryRefundProductVo;

/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: f-road
 * @time:  2015-5-1下午3:01:12
 */
@Service
public class RefunSupport {
	
	@Resource
	private BossQueryRefundOrderService.Iface bossQueryRefundOrderService;
	
	@Resource
	ClientService.Iface clientService;
	
	/**
	  * 方法描述：退款订单
	  * @param: 
	  * @return: 
	  * @version: 1.0
	 * @throws TException 
	  * @time: 2015年4月27日 下午4:43:27
	  */
	public HashMap<String, Object> queryRefundList(RefundListReq pojo) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<RefundVo> refunList = new ArrayList<RefundVo>();
		RefundVo temp = null;
		List<RefGood> goodList = null;
		RefGood temp2 = null;
		List<RefRecord> recordList = null;
		RefRecord temp3 = null;
		Page page = new Page();
		//封装请求对象
		BossQueryRefundOrderListReq req = new BossQueryRefundOrderListReq();
		BeanUtils.copyProperties(req, pojo);
		req.setStartDate(StringUtil.isNotBlank(pojo.getStartDate()) ? PramasUtil.DateFormat(pojo.getStartDate()) + "" : "0");
		req.setEndDate(StringUtil.isNotBlank(pojo.getEndDate()) ? PramasUtil.DateFormatEnd(pojo.getEndDate()) + "" : "0");
		if(Long.valueOf(req.getStartDate()) > Long.valueOf(req.getEndDate())) {
			throw new BossException("起始筛选时间不可大于结束筛选时间");
		}
		//封装分页对象
		PageVo pageVo=new PageVo();
		pageVo.setPageNumber(pojo.getPageNumber());
		pageVo.setPageSize(pojo.getPageSize());
		pageVo.setFirstRecordTime(pojo.getFirstRecordTime());
		pageVo.setLastPageNumber(pojo.getLastPageNumber());
		pageVo.setLastRecordTime(pojo.getLastRecordTime());
		req.setPageVo(pageVo);
		
		//所属组织
		List<String> orgs = new ArrayList<String>();
		for(String org:pojo.getOrgCodes().split(",")){
			orgs.add(org);
		}
		req.setOrgCodes(orgs);
		
		//调用SERVER端接口
		BossQueryRefundOrderListRes resp = bossQueryRefundOrderService.getRefundOrderList(req);
		//封装返回结果
		ResultVo result = resp.getResultVo();
		if(Constants.RESULT_SUCCESS.equals(result.getResultCode())) {
			BeanUtils.copyProperties(page, resp.getPageVo());
			if(!ArrayUtil.empty(resp.getRefundInfoList())) {
				
				List<ClientVo> client = clientService.getClient(new ClientVo());
				
				for(BossQueryRefundInfoVo tmp : resp.getRefundInfoList()) {
					temp = new RefundVo();
					BeanUtils.copyProperties(temp, tmp);
					
					for (ClientVo clientVo : client) {
						if (tmp.getClientId().equals(clientVo.getClientId())) {
							temp.setClientId(clientVo.getBankName());
							break;
						}
					}
					
					//封装返回退款订单对应的商品列表
					if(!ArrayUtil.empty(tmp.getProductList())) {
						goodList = new ArrayList<RefGood>();
						for(BossQueryRefundProductVo tmp2 : tmp.getProductList()) {
							temp2 = new RefGood();
							BeanUtils.copyProperties(temp2, tmp2);
							goodList.add(temp2);
						}
						temp.setRefGoodsList(goodList);
					}
					//封装返回退款订单对应的支付记录列表
					if(!ArrayUtil.empty(tmp.getPayList())) {
						recordList = new ArrayList<RefRecord>();
						for(BossQueryRefundPaymentVo tmp3 : tmp.getPayList()) {
							temp3 = new RefRecord();
							BeanUtils.copyProperties(temp3, tmp3);
							//支付类型（1-现金、2-联盟积分、3-银行积分、6-赠送积分）
							if("1".equals(tmp3.getPaymentType())) {
								temp3.setPayTypeName("现金");
							} else if("2".equals(tmp3.getPaymentType())) {
								temp3.setPayTypeName("联盟积分");
							} else if("3".equals(tmp3.getPaymentType())) {
								temp3.setPayTypeName("银行积分");
							} else if("6".equals(tmp3.getPaymentType())) {
								temp3.setPayTypeName("赠送积分");
							} else {
								temp3.setPayTypeName("未知");
							}
							recordList.add(temp3);
						}
						temp.setRefRecordList(recordList);
					}
					refunList.add(temp);
				}
			}
			map.put("totalCountExport", page.getTotalCount());
			map.put("page", page);
			map.put("refOrderList", refunList);
		} else {
			throw new BossException(result.getResultCode(), result.getResultDesc());
		}
		return  map;
	}
	
	/**
	 * 退款订单列表导出
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年8月28日 下午3:10:06
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> refundListExport(RefundListReq pojo) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		//封装请求对象
		BossQueryRefundOrderListReq req = new BossQueryRefundOrderListReq();
		BeanUtils.copyProperties(req, pojo);
		req.setStartDate(StringUtil.isNotBlank(pojo.getStartDate()) ? PramasUtil.DateFormat(pojo.getStartDate()) + "" : "0");
		req.setEndDate(StringUtil.isNotBlank(pojo.getEndDate()) ? PramasUtil.DateFormatEnd(pojo.getEndDate()) + "" : "0");
		if(Long.valueOf(req.getStartDate()) > Long.valueOf(req.getEndDate())) {
			throw new BossException("起始筛选时间不可大于结束筛选时间");
		}
		
		//所属组织
		List<String> orgs = new ArrayList<String>();
		for(String org:pojo.getOrgCodes().split(",")){
			orgs.add(org);
		}
		req.setOrgCodes(orgs);
		
		//调用SERVER端接口
		ExportResultRes resp = bossQueryRefundOrderService.exportRefundOrderList(req);
		//封装返回结果
		ResultVo result = resp.getResultVo();
		LogCvt.info("退款订单列表导出返回结果：" + JSON.toJSONString(result));
		if(Constants.RESULT_SUCCESS.equals(result.getResultCode())) {
			HashMap<String, String> obj = new HashMap<String, String>();
			obj.put("url", resp.getUrl());
			map.put("result", obj);
		} else {
			throw new BossException(result.getResultCode(), result.getResultDesc());
		}
		return map;
	}
	
	/**
	  * 方法描述：退款订单详情查询
	  * @param: 
	  * @return: 
	  * @version: 1.0
	 * @throws TException 
	  * @time: 2015年4月27日 下午4:48:56
	  */
	public HashMap<String, Object> detail(RefundDetailReq pojo) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		RefundVo refund = null;
		List<RefGood> goodList = null;
		RefGood temp2 = null;
		List<RefRecord> recordList = null;
		RefRecord temp3 = null;
		//封装请求对象
		BossQueryRefundDetailReq req = new BossQueryRefundDetailReq();
		BeanUtils.copyProperties(req, pojo);
		//调用SERVER端接口
		BossQueryRefundDetailRes resp = bossQueryRefundOrderService.getRefundDetail(req);
		//封装返回结果
		ResultVo result = resp.getResultVo();
		if(Constants.RESULT_SUCCESS.equals(result.getResultCode())) {
			if(resp.getRefundInfo().getRefundId() != null) {
				BossQueryRefundInfoVo tmp = resp.getRefundInfo();
				refund = new RefundVo();
				BeanUtils.copyProperties(refund, tmp);
				//封装返回退款订单对应的商品列表
				if(!ArrayUtil.empty(tmp.getProductList())) {
					goodList = new ArrayList<RefGood>();
					for(BossQueryRefundProductVo tmp2 : tmp.getProductList()) {
						temp2 = new RefGood();
						BeanUtils.copyProperties(temp2, tmp2);
						goodList.add(temp2);
					}
					refund.setRefGoodsList(goodList);
				}
				//封装返回退款订单对应的支付记录列表
				if(!ArrayUtil.empty(tmp.getPayList())) {
					recordList = new ArrayList<RefRecord>();
					for(BossQueryRefundPaymentVo tmp3 : tmp.getPayList()) {
						temp3 = new RefRecord();
						BeanUtils.copyProperties(temp3, tmp3);
						//支付类型（1-现金、2-联盟积分、3-银行积分、6-赠送积分）
						if("1".equals(tmp3.getPaymentType())) {
							temp3.setPayTypeName("现金");
						} else if("2".equals(tmp3.getPaymentType())) {
							temp3.setPayTypeName("联盟积分");
						} else if("3".equals(tmp3.getPaymentType())) {
							temp3.setPayTypeName("银行积分");
						} else if("6".equals(tmp3.getPaymentType())) {
							temp3.setPayTypeName("赠送积分");
						} else {
							temp3.setPayTypeName("未知");
						}
						recordList.add(temp3);
					}
					refund.setRefRecordList(recordList);
				}
			}
			map.put("refundResponseVo", refund);
		} else {
			throw new BossException(result.getResultCode(), result.getResultDesc());
		}
		return map;
	}
}
