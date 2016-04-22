package com.froad.cbank.coremodule.module.normal.merchant.service;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.module.normal.merchant.enums.ProductType;
import com.froad.cbank.coremodule.module.normal.merchant.enums.TicketStatus;
import com.froad.cbank.coremodule.module.normal.merchant.exception.MerchantException;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Outlet_Comment_Count_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Outlet_Comment_Count_Res;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Outlet_Comment_Replay_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.PageRes;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Product_Comment_Replay_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Outlet_Comment_Detail_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Outlet_Comment_Detail_Res;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Outlet_Comment_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Outlet_Comment_Res;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Product_Comment_Details_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Product_Comment_Details_Res;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Product_Comment_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Product_Comment_Res;
import com.froad.cbank.coremodule.module.normal.merchant.utils.EncryptUtil;
import com.froad.cbank.coremodule.module.normal.merchant.utils.EnumTypes;
import com.froad.cbank.coremodule.module.normal.merchant.utils.PramasUtil;
import com.froad.cbank.coremodule.module.normal.merchant.utils.TargetObjectFormat;
import com.froad.thrift.service.OrderService;
import com.froad.thrift.service.OutletCommentService;
import com.froad.thrift.service.ProductService;
import com.froad.thrift.service.TicketService;
import com.froad.thrift.vo.CreateTimeFilterVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.OutletCommentLevelAmountVo;
import com.froad.thrift.vo.OutletCommentPageVoRes;
import com.froad.thrift.vo.OutletCommentVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ProductCommentFilterReq;
import com.froad.thrift.vo.ProductCommentPageVo;
import com.froad.thrift.vo.ProductCommentVo;
import com.froad.thrift.vo.RecommentNotEmptyVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.StarLevelFilterVo;
import com.froad.thrift.vo.order.GetSubOrderProductVoReq;
import com.froad.thrift.vo.order.GetSubOrderProductVoRes;
import com.froad.thrift.vo.ticket.TicketDetailVo;

/**
 * 评价
 * @ClassName CommentH5Service
 * @author zxl
 * @date 2015年3月23日 上午11:44:04
 */
@Service
public class Comment_Service {
	
	@Resource
	ProductService.Iface productService;
	
	@Resource
	OutletCommentService.Iface outletCommentService;
	
	@Resource
	OrderService.Iface orderService;
	
	@Resource
	TicketService.Iface ticketService;
	/**
	 * 商品评价列表
	 * @tilte list
	 * @author zxl
	 * @date 2015年3月23日 上午11:46:41
	 * @param map
	 * @return
	 * @throws MerchantException
	 * @throws TException 
	 * @throws ParseException 
	 */
	public HashMap<String,Object> query_product_comment_list(Query_Product_Comment_Req req) throws MerchantException, TException, ParseException{
		HashMap<String,Object> reMap = new HashMap<String, Object>();
		
		PageVo page = new PageVo();
		page.setPageNumber(req.getPageNumber());
		page.setPageSize(req.getPageSize());
		page.setLastPageNumber(req.getLastPageNumber());
		page.setFirstRecordTime(req.getFirstRecordTime());
		page.setLastRecordTime(req.getLastRecordTime());
		
		//过滤条件
		HashMap<String,Object> filter = new HashMap<String, Object>();
		if("0".equals(req.getStarLevel())){
			filter.put("starLevel", "00");
		}else{
			filter.put("starLevel", req.getStarLevel());
		}
		filter.put("productId", req.getProductId());
		filter.put("merchantId", req.getMerchantUser().getMerchantId());
		filter.put("outletId", req.getMerchantUser().getOutletId());
		filter.put("isReply", req.getIsReply());
		filter.put("productName", req.getProductName());
		filter.put("orderId", req.getOrderId());
		filter.put("pointStartTime", StringUtil.isNotEmpty(req.getPointEndTime())?PramasUtil.DateFormat(req.getPointStartTime()):null);
		filter.put("pointEndTime", StringUtil.isNotEmpty(req.getPointStartTime())?PramasUtil.DateFormatEnd(req.getPointEndTime()):null);
		
		ProductCommentFilterReq productCommentFilterReq = new ProductCommentFilterReq();
		productCommentFilterReq.setClientId(req.getClientId());
		productCommentFilterReq.setFilter(JSON.toJSONString(filter));
		ProductCommentPageVo resV = productService.getProductCommentList(productCommentFilterReq, page);
		if(resV.getProductCommentVoList()!=null&&resV.getProductCommentVoList().size()>0){
			List<Query_Product_Comment_Res> listRes=new ArrayList<Query_Product_Comment_Res>();
			for(ProductCommentVo vo:resV.getProductCommentVoList()){
				Query_Product_Comment_Res res=new Query_Product_Comment_Res();
				TargetObjectFormat.copyProperties(vo, res);
				//判断是否有权限回复
				TicketDetailVo tv=new TicketDetailVo();
				tv.setProductId(vo.getProductId());
				tv.setSubOrderId(vo.getOrderId());
				tv.setStatus(TicketStatus.consumed.getCode());  //已消费
				boolean bug=false;//标注是否有权限回复
				if(StringUtils.isBlank(req.getMerchantUser().getOutletId())||req.getMerchantUser().getOutletId().equals("0")){
					bug=true;
				}else{
					List<TicketDetailVo> list=ticketService.getTicketDetail(tv);
					if(list!=null&&list.size()>0){
						for(TicketDetailVo v:list){
							if(v.getOutletId().equals(req.getMerchantUser().getOutletId())){
								bug=true;break;
							}	
						}
					}
				}
	            res.setIsverify(bug);
	            
				if(res.getOrderId()!=null){
					//查询商品评价价格
					GetSubOrderProductVoReq sub=new GetSubOrderProductVoReq();
					sub.setClientId(req.getClientId());
					sub.setProductId(vo.getProductId());
					sub.setSubOrderId(vo.getOrderId());
					sub.setOrderId(vo.getBigOrderId());
					GetSubOrderProductVoRes subres=orderService.getSubOrderProduct(sub);
					if(subres!=null&&subres.getResultVo().getResultCode().equals(EnumTypes.success.getCode())){
						res.setPrice(subres.getTotalMoney());
					}
				}
				//如是联合登录用户包含身份证号 显示身份号前三后四位，中间用*显示
//	            if(vo.getMemberName().length()>=18){
//	            	String idNo = EncryptUtil.encryptIdentityNo(vo.getMemberName());
//	            	res.setMemberName(idNo);
//	            }
				res.setMemberName(vo.getMemberName());
				listRes.add(res);
			}
			reMap.put("productComment", listRes);
			PageRes pageRes = new PageRes();
			TargetObjectFormat.copyProperties(resV.getPage(), pageRes);
			reMap.put("page",pageRes);
		}else{
			throw new MerchantException(EnumTypes.noresult.getCode(),EnumTypes.noresult.getMsg());
		}
		return reMap;
	}
	
	
	
	/**
	 * 查询商品评价详情根据订单号查询
	 * @tilte replay
	 * @author zxl
	 * @date 2015年3月23日 上午11:46:55
	 * @param map
	 * @return
	 * @throws MerchantException
	 * @throws TException 
	 */
	public HashMap<String,Object> query_product_detail(Query_Product_Comment_Details_Req req) throws MerchantException, TException{
		
		HashMap<String,Object> reMap = new HashMap<String, Object>();
		ProductCommentVo productCommentVo = new ProductCommentVo();
		productCommentVo.setMerchantId(req.getMerchantUser().getMerchantId());
		productCommentVo.setCommentId(req.getCommentId());
		productCommentVo.setOrderId(StringUtil.isNotBlank(req.getOrderId())?req.getOrderId():null);
		productCommentVo.setProductId(StringUtil.isNotBlank(req.getProductId())?req.getProductId():null);

		productCommentVo.setClientId(req.getClientId());
		ProductCommentVo resv = productService.getProductCommentDetail(productCommentVo);
		if(resv!=null){
			Query_Product_Comment_Details_Res res=new Query_Product_Comment_Details_Res();
			TargetObjectFormat.copyProperties(resv, res);
			res.setType(ProductType.getType(resv.getType())!=null?ProductType.getType(resv.getType()).getDescribe():"");
			//判断是否可以回复
			TicketDetailVo tv=new TicketDetailVo();
			tv.setProductId(req.getProductId());
			tv.setSubOrderId(req.getOrderId());
			tv.setStatus(TicketStatus.consumed.getCode());  //已消费
			boolean bug=false;//标注是否有权限回复
			if(StringUtils.isBlank(req.getMerchantUser().getOutletId())||req.getMerchantUser().getOutletId().equals("0")){
				bug=true;
			}else{
				List<TicketDetailVo> list=ticketService.getTicketDetail(tv);
				if(list!=null&&list.size()>0){
					for(TicketDetailVo v:list){
						if(v.getOutletId().equals(req.getMerchantUser().getOutletId())){
							bug=true;break;
						}	
					}
				}
			}
            res.setIsverify(bug);
            
            //如是联合登录用户包含身份证号 显示身份号前三后四位，中间用*显示
//            if(resv.getMemberName().length()>=18){
//            	String idNo = EncryptUtil.encryptIdentityNo(resv.getMemberName());
//            	res.setMemberName(idNo);
//            }
            res.setMemberName(resv.getMemberName());
			reMap.put("commentDetail", res);
		}else{
			throw new MerchantException(EnumTypes.fail.getCode(),EnumTypes.fail.getMsg());
		}
		return reMap;
	}
	
	
	/**
	 * 商品评价回复根据订单号回复
	 * @tilte replay
	 * @author zxl
	 * @date 2015年3月23日 上午11:46:55
	 * @param map
	 * @return
	 * @throws MerchantException
	 * @throws TException 
	 */
	public HashMap<String,Object> product_replay(Product_Comment_Replay_Req req) throws MerchantException, TException{
		HashMap<String,Object> reMap = new HashMap<String, Object>();
		ProductCommentVo productCommentVo = new ProductCommentVo();
		productCommentVo.setCommentId(req.getCommentId());
		productCommentVo.setProductId(req.getProductId());
		productCommentVo.setRecomment(req.getRecomment());
		productCommentVo.setClientId(req.getClientId());
		productCommentVo.setMerchantId(req.getMerchantUser().getMerchantId());
		productCommentVo.setMerchantUserName(req.getMerchantUser().getUsername());
		ResultVo b = productService.replayProductComment(productCommentVo);
		if(b!=null&&b.getResultCode().equals(EnumTypes.success.getCode())){
			reMap.put("code", EnumTypes.success.getCode());
			reMap.put("message", EnumTypes.success.getMsg());
		}else{
			throw new MerchantException(b.getResultCode(),b.getResultDesc());
		}
		return reMap;
	}
	
	/**
	 * 商品批量回复
	 * @tilte product_replay_batch
	 * @author zxl
	 * @date 2015年4月21日 下午4:39:36
	 * @param req
	 * @return
	 * @throws MerchantException
	 * @throws TException
	 */
	public HashMap<String,Object> product_replay_batch(Product_Comment_Replay_Req req) throws MerchantException, TException{
		HashMap<String,Object> reMap = new HashMap<String, Object>();
		List<ProductCommentVo> list = new ArrayList<ProductCommentVo>();
		String[] commentIds = req.getCommentId().split(",");
		String[] productId = req.getProductId().split(",");
		for(int i =0 ; i<commentIds.length ; i++){
			ProductCommentVo productCommentVo = new ProductCommentVo();
			productCommentVo.setCommentId(commentIds[i]);
			productCommentVo.setProductId(productId[i]);
			productCommentVo.setRecomment(req.getRecomment());
			productCommentVo.setClientId(req.getClientId());
			productCommentVo.setMerchantId(req.getMerchantUser().getMerchantId());
			productCommentVo.setMerchantUserName(req.getMerchantUser().getUsername());
			list.add(productCommentVo);
		}
		
		ResultVo res = productService.replayProductCommentBatch(list);
		if(!res.getResultCode().equals(EnumTypes.success.getCode())){
			throw new MerchantException(res.getResultCode(), res.getResultDesc());
		}
		return reMap;
	}
	
	
    /**
     * 商户门店评价统计(查所有)query_outlet_comment_count
     * @param pojo
     * @return
     * @throws MerchantException
     * @throws Exception
     */
	public Map<String,Object> query_outlet_comment_count_all(Outlet_Comment_Count_Req req)
	     throws MerchantException, Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		List<OutletCommentLevelAmountVo> resvo=outletCommentService.getMerchantCommentLevelAmount(req.getMerchantUser().getMerchantId());
		if(resvo!=null&&resvo.size()>0){
			List<Outlet_Comment_Count_Res> listRes=new ArrayList<Outlet_Comment_Count_Res>();
			for(OutletCommentLevelAmountVo vo:resvo){
				Outlet_Comment_Count_Res res=new Outlet_Comment_Count_Res();
				
	            TargetObjectFormat.copyProperties(vo, res);
	            if(vo.getLevelAmountOne()+vo.getLevelAmountTwo()+vo.getLevelAmountThree()+vo.getLevelAmountFour()+vo.getLevelAmountFive()==0){
	            	 res.setCenter(0d);
	            }else{
	            	double center = (1 * vo.getLevelAmountOne() 
						+ 2 * vo.getLevelAmountTwo() 
						+ 3 * vo.getLevelAmountThree()
						+ 4 * vo.getLevelAmountFour() 
						+ 5 * vo.getLevelAmountFive())
						/ (vo.getLevelAmountOne()+vo.getLevelAmountTwo()+vo.getLevelAmountThree()+vo.getLevelAmountFour()+vo.getLevelAmountFive());
	            	res.setCenter(center);
	            }
	            listRes.add(res);
			}
        	map.put("outletCommentCount",listRes);
        	return map;
        }else {
        	throw new MerchantException(EnumTypes.noresult.getCode(),EnumTypes.noresult.getMsg());
        }
	}
	

	/**
	 * 商户门店评价查询query_outlet_comment
	 * @param cityId
	 * @return
	 */
	public Map<String,Object> query_outlet_comment(Query_Outlet_Comment_Req req)
	     throws MerchantException, Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		OutletCommentVo outletCommentVo=new OutletCommentVo();
		TargetObjectFormat.copyProperties(req, outletCommentVo);
		outletCommentVo.setMerchantId(req.getMerchantUser().getMerchantId());
		outletCommentVo.setOutletId(req.getMerchantUser().getOutletId().equals("0")?null:req.getMerchantUser().getOutletId());
		outletCommentVo.setClientId(req.getClientId());
		CreateTimeFilterVo votime=new CreateTimeFilterVo();
		if(StringUtil.isNotEmpty(req.getEndTime())||StringUtil.isNotEmpty(req.getBegTime())){
			if(StringUtil.isNotEmpty(req.getBegTime())){
				votime.setBegTime(PramasUtil.DateFormat(req.getBegTime()));
			}
			if(StringUtil.isNotEmpty(req.getEndTime())){
				votime.setEndTime(PramasUtil.DateFormatEnd(req.getEndTime()));
			}
			outletCommentVo.setCreateTimeFilter(votime);
		}
		if(req.getStarLevel()!=0){
			StarLevelFilterVo svo=new StarLevelFilterVo();
			svo.setStarLevel(req.getStarLevel());
			outletCommentVo.setStarLevelFilter(svo);
		}
		if(StringUtils.isNotBlank(req.getIsReply())){
			RecommentNotEmptyVo em = new RecommentNotEmptyVo();
			em.setNotEmpty("0".equals(req.getIsReply())?true:false);
			outletCommentVo.setRecommentNotEmpty(em);
		}
		PageVo page=new PageVo();
		page.setPageNumber(req.getPageNumber());
		page.setPageSize(req.getPageSize());
		page.setLastPageNumber(req.getLastPageNumber());
		page.setFirstRecordTime(req.getFirstRecordTime());
		page.setLastRecordTime(req.getLastRecordTime());
		OutletCommentPageVoRes outletCommentPageVoRes=outletCommentService.getOutletCommentByPage(page, outletCommentVo);
		
		if(outletCommentPageVoRes.getOutletCommentVoList()!=null&&outletCommentPageVoRes.getOutletCommentVoList().size()>0){
			List<Query_Outlet_Comment_Res> listRes=new ArrayList<Query_Outlet_Comment_Res>();
			for(OutletCommentVo vo:outletCommentPageVoRes.getOutletCommentVoList()){
				Query_Outlet_Comment_Res res=new Query_Outlet_Comment_Res();
				TargetObjectFormat.copyProperties(vo, res);
				//如是联合登录用户包含身份证号 显示身份号前三后四位，中间用*显示
//	            if(vo.getMemberName().length()>=18){
//	            	String idNo = EncryptUtil.encryptIdentityNo(vo.getMemberName());
//	            	res.setMemberName(idNo);
//	            }
				res.setMemberName(vo.getMemberName());
				listRes.add(res);
			}
        	map.put("outletCommentList", listRes);
        	PageRes pageRes = new PageRes();
			TargetObjectFormat.copyProperties(outletCommentPageVoRes.getPage(), pageRes);
			map.put("page",pageRes);
        	return map;
        }else {
        	throw new MerchantException(EnumTypes.noresult.getCode(),EnumTypes.noresult.getMsg());
        }

	}

	/**
	 * 查询商户门店评价详情query_outlet_comment_detail
	 * @param cityId
	 * @return
	 */
	public Map<String,Object> query_outlet_comment_detail(Query_Outlet_Comment_Detail_Req req)
	     throws MerchantException, Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		OutletCommentVo outletCommentVo=outletCommentService.getOutletCommentById(req.getId());
		if(outletCommentVo!=null){
			Query_Outlet_Comment_Detail_Res res=new Query_Outlet_Comment_Detail_Res();
			TargetObjectFormat.copyProperties(outletCommentVo, res);
			//如是联合登录用户包含身份证号 显示身份号前6后四位，中间用*显示
//            if(outletCommentVo.getMemberName().length()>=18){
//            	String idNo = EncryptUtil.encryptIdentityNo(outletCommentVo.getMemberName());
//            	res.setMemberName(idNo);
//            }
			res.setMemberName(outletCommentVo.getMemberName());
        	map.put("outletComment", res);
        	return map;
        }else {
        	throw new MerchantException(EnumTypes.noresult.getCode(),EnumTypes.noresult.getMsg());
        }

	}
	
	/**
	 * 商户门店评价回复add_outlet_comment
	 * @param cityId
	 * @return
	 */
	public Map<String,Object> replay_outlet_comment(Outlet_Comment_Replay_Req req)
	     throws MerchantException, Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		OutletCommentVo outletCommentVo=new OutletCommentVo();
		outletCommentVo.setId(req.getId());
		outletCommentVo.setRecomment(req.getRecomment());
		outletCommentVo.setMerchantId(req.getMerchantUser().getMerchantId());
		outletCommentVo.setOutletId(req.getMerchantUser().getOutletId());
		outletCommentVo.setClientId(req.getClientId());
		outletCommentVo.setMerchantUserName(req.getMerchantUser().getUsername());
		outletCommentVo.setRecommentTime(new Date().getTime());
		
		OriginVo ori = new OriginVo();
		ori.setOperatorId(req.getMerchantUser().getId());
		ori.setOperatorIp(req.getIp());
		ori.setPlatType(req.getPlatType());
		
		ResultVo res = outletCommentService.addOutletCommentOfRecomment(ori,outletCommentVo);
		if(!res.getResultCode().equals(EnumTypes.success.getCode())){
			throw new MerchantException(res.getResultCode(), res.getResultDesc());
		}
		return map;
	}
	
	
//	
//	
//    /**
//     * 商户门店评价统计(查单个)query_outlet_comment_count
//     * @param pojo
//     * @return
//     * @throws MerchantException
//     * @throws Exception
//     */
//	public Map<String,Object> query_outlet_comment_count(Outlet_Comment_Count_Req req)
//	     throws MerchantException, Exception{
//		Map<String,Object> map=new HashMap<String,Object>();
//		OutletCommentVo outletCommentVo=new OutletCommentVo();
//		outletCommentVo.setMerchantId(req.getMerchantUser().getMerchantId());
//		outletCommentVo.setOutletId(req.getOutletId());
//		OutletCommentLevelAmountVo res=outletCommentService.getOutletCommentLevelAmount(outletCommentVo);
//		if(res!=null){
//			double center=(res.getLevelAmountOne()+res.getLevelAmountTwo()
//            		+res.getLevelAmountThree()+res.getLevelAmountFour()
//            		+res.getLevelAmountFive())/5;
//            Outlet_Comment_Count_Res cp=new Outlet_Comment_Count_Res();
//            TargetObjectFormat.copyProperties(res, cp);
//            cp.setCenter(center);
//        	map.put("outletCommentCount",cp);
//        	return map;
//        }else {
//        	throw new MerchantException(EnumTypes.noresult.getCode(),EnumTypes.noresult.getMsg());
//        }
//	}
	

}
