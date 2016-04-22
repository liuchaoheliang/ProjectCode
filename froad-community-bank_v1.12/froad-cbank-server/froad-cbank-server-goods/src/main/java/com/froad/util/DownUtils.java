package com.froad.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.froad.common.enums.ProductTempAuditState;
import com.froad.enums.ProductAuditState;
import com.froad.enums.ProductStatus;
import com.froad.logic.CommonLogic;
import com.froad.logic.impl.CommonLogicImpl;
import com.froad.po.Org;
import com.froad.po.ProductListInfo;

public class DownUtils {
	
	/**
	 *  获取下载名优特惠商品的header
	 */
	public List<String> downSpecialProductHeader(){
		List<String> header = new ArrayList<String>();
		header.add("序号");
		header.add("机构名称");
		header.add("商户名称");
		header.add("商品名称");
		header.add("库存数量");
		header.add("特惠价");
		header.add("销售开始时间");
		header.add("销售结束时间");
		header.add("审核时间");
		header.add("审核状态");
		header.add("上下架状态");
		return header;
	}
	
	/**
	 *  获取下载名优特惠商品数据
	 */
	public List<List<String>> downSpecialProductData(List<ProductListInfo> products){
		List<List<String>> data = new ArrayList<List<String>>();
		if(products==null || products.size()==0){
			List<String> record = new ArrayList<String>();
			record.add("");//序号
			record.add("");//机构名称
			record.add("");//商户名称
			record.add("");//商品名称
			record.add("");
			record.add("");
			record.add("");
			record.add("");
			record.add("");
			record.add("");
			record.add("");
			data.add(record);	
		} else {
			Map<String,String> orgNameMap = new HashMap<String,String>();
			CommonLogic commonLogic = new CommonLogicImpl();
			int count = 0;
			Org org = null;
			StringBuilder sb = null;
			List<String> record = null;
			for (ProductListInfo p : products) {
				sb = new StringBuilder(p.getClientId()).append("_").append(p.getOrgCode());
				record = new ArrayList<String>();
				record.add(String.valueOf((++count)));//序号
				if(orgNameMap.get(sb.toString())==null){
					if(p.getOrgCode()!=null && !"".equals(p.getOrgCode().trim())){
		                org = commonLogic.queryByOrgCode(p.getClientId(), p.getOrgCode());
		                if(org!=null){
		                	orgNameMap.put(sb.toString(),org.getOrgName());
		                }
		            }
				}
				record.add(orgNameMap.get(sb.toString()));//机构名称
				record.add(p.getMerchantName());//商户名称
				record.add(p.getName());//商品名称
				if(p.getStore()!=null){
					record.add(p.getStore().toString());//库存数量
				} else {
					record.add("");
				}
				record.add(String.valueOf(Arith.div(p.getPrice(), GoodsConstants.DOUBLE_INTGER_CHANGE)));//特惠价
				if(p.getStartTime()!=null){
					record.add(DateUtil.formatDateTime(DateUtil.DATE_FORMAT1, p.getStartTime()));//特惠开始时间
				} else {
					record.add("");
				}
				if(p.getEndTime()!=null){
					record.add(DateUtil.formatDateTime(DateUtil.DATE_FORMAT1, p.getEndTime()));//特惠结束时间
				} else {
					record.add("");
				}
				if(p.getAuditTime()!=null){
					record.add(DateUtil.formatDateTime(DateUtil.DATE_FORMAT1, p.getAuditTime()));//审核时间
				} else {
					record.add("");
				}
				if(Checker.isNotEmpty(p.getAuditState())){
					if(ProductAuditState.getType(p.getAuditState())!=null){
						record.add(ProductAuditState.getType(p.getAuditState()).getDescribe());//审核状态
					} else {
	    				record.add("");
	    			}
				} else {
					record.add("");
				}
				if(Checker.isNotEmpty(p.getIsMarketable())){
					if(ProductStatus.disOffShelf.getCode().equals(p.getIsMarketable())){
						record.add("已下架");
					} else if(ProductStatus.getType(p.getIsMarketable())!=null){
						record.add(ProductStatus.getType(p.getIsMarketable()).getDescribe());//状态
					} else {
	    				record.add("");
	    			}
				} else {
					record.add("");
				}
				data.add(record);
			}	
		}
		return data;
	}
	
	/**
	 *  获取下载线下积分礼品header
	 */
	public List<String> downDotGiftProductHeader(){
		List<String> header = new ArrayList<String>();
		header.add("序号");
		header.add("机构名称");
		header.add("礼品编号");
		header.add("商品名称");
		header.add("库存数量");
		header.add("兑换积分");
		header.add("兑换开始时间");
		header.add("兑换结束时间");
		header.add("审核时间");
		header.add("审核状态");
		header.add("上下架状态");
		return header;
	}
	
	/**
	 *  获取下载线下积分礼品data
	 */
	public List<List<String>> downDotGiftProductData(List<ProductListInfo> products){
		List<List<String>> data = new ArrayList<List<String>>();
		if(products==null || products.size()==0){
			List<String> record = new ArrayList<String>();
			record.add("");//序号
			record.add("");//机构名称
			record.add("");//商户名称
			record.add("");//商品名称
			record.add("");
			record.add("");
			record.add("");
			record.add("");
			record.add("");
			record.add("");
			record.add("");
			data.add(record);	
		} else {
			Map<String,String> orgNameMap = new HashMap<String,String>();
			CommonLogic commonLogic = new CommonLogicImpl();
			int count = 0;
			Org org = null;
			StringBuilder sb = null;
			List<String> record = null;
			for (ProductListInfo p : products) {
				sb = new StringBuilder(p.getClientId()).append("_").append(p.getOrgCode());
				record = new ArrayList<String>();
				record.add(String.valueOf((++count)));//序号
				if(orgNameMap.get(sb.toString())==null){
					if(p.getOrgCode()!=null && !"".equals(p.getOrgCode().trim())){
		                org = commonLogic.queryByOrgCode(p.getClientId(), p.getOrgCode());
		                if(org!=null){
		                	orgNameMap.put(sb.toString(),org.getOrgName());
		                }
		            }
				}
				record.add(orgNameMap.get(sb.toString()));//机构名称
				record.add(p.getProductId());//商户名称
				record.add(p.getName());//商品名称
				if(p.getStore()!=null){
					record.add(p.getStore().toString());//库存数量
				} else {
					record.add("");
				}
				record.add(String.valueOf(Arith.div(p.getPrice(), GoodsConstants.DOUBLE_INTGER_CHANGE)));//兑换积分
				if(p.getStartTime()!=null){
					record.add(DateUtil.formatDateTime(DateUtil.DATE_FORMAT1, p.getStartTime()));//兑换开始时间
				} else {
					record.add("");
				}
				if(p.getEndTime()!=null){
					record.add(DateUtil.formatDateTime(DateUtil.DATE_FORMAT1, p.getEndTime()));//兑换结束时间
				} else {
					record.add("");
				}
				if(p.getAuditTime()!=null){
					record.add(DateUtil.formatDateTime(DateUtil.DATE_FORMAT1, p.getAuditTime()));//审核时间
				} else {
					record.add("");
				}
				if(Checker.isNotEmpty(p.getAuditState())){
					if(ProductAuditState.getType(p.getAuditState())!=null){
						record.add(ProductAuditState.getType(p.getAuditState()).getDescribe());//审核状态
					} else {
						record.add("");
					}
				} else {
					record.add("");
				}
				if(Checker.isNotEmpty(p.getIsMarketable())){
					if(ProductStatus.disOffShelf.getCode().equals(p.getIsMarketable())){
						record.add("已下架");
					} else if(ProductStatus.getType(p.getIsMarketable())!=null){
						record.add(ProductStatus.getType(p.getIsMarketable()).getDescribe());//状态
					} else {
						record.add("");
					}
				} else {
					record.add("");
				}
				data.add(record);
			}	
		}
		return data;
	}
	
	/**
	 *  获取下载团购商品header
	 */
	public List<String> downGroupProductHeader(){
		List<String> header = new ArrayList<String>();
		header.add("序号");
		header.add("机构名称");
		header.add("商户名称");
		header.add("商品名称");
		header.add("库存数量");
		header.add("团购价");
		header.add("团购开始时间");
		header.add("团购结束时间");
		header.add("审核时间");
		header.add("审核状态");
		header.add("状态");
		return header;
	}
	
	/**
	 *  获取下载团购商品data
	 */
	public List<List<String>> downGroupProductData(List<ProductListInfo> products){
		List<List<String>> data = new ArrayList<List<String>>();
		if(products==null || products.size()==0){
			List<String> record = new ArrayList<String>();
			record.add("");//序号
			record.add("");//机构名称
			record.add("");//商户名称
			record.add("");//商品名称
			record.add("");
			record.add("");//团购价
			record.add("");
			record.add("");
			record.add("");
			record.add("");
			record.add("");
			data.add(record);	
		} else {
			Map<String,String> orgNameMap = new HashMap<String,String>();
			CommonLogic commonLogic = new CommonLogicImpl();
			
			int count = 0;
			Org org = null;
			StringBuilder sb = null;
			List<String> record = null;
			for (ProductListInfo p : products) {
				sb = new StringBuilder(p.getClientId()).append("_").append(p.getOrgCode());
				record = new ArrayList<String>();
				record.add(String.valueOf((++count)));//序号
				if(orgNameMap.get(sb.toString())==null){
					if(p.getOrgCode()!=null && !"".equals(p.getOrgCode().trim())){
		                org = commonLogic.queryByOrgCode(p.getClientId(), p.getOrgCode());
		                if(org!=null){
		                	orgNameMap.put(sb.toString(),org.getOrgName());
		                }
		            }
				}
				record.add(orgNameMap.get(sb.toString()));//机构名称
				record.add(p.getMerchantName());//商户名称
				record.add(p.getName());//商品名称
				if(p.getStore()!=null){
					record.add(p.getStore().toString());//库存数量
				} else {
					record.add("");
				}
				record.add(String.valueOf(Arith.div(p.getPrice(), GoodsConstants.DOUBLE_INTGER_CHANGE)));//团购价
				if(p.getStartTime()!=null){
					record.add(DateUtil.formatDateTime(DateUtil.DATE_FORMAT1, p.getStartTime()));//团购开始时间
				} else {
					record.add("");
				}
				if(p.getEndTime()!=null){
					record.add(DateUtil.formatDateTime(DateUtil.DATE_FORMAT1, p.getEndTime()));//团购结束时间
				} else {
					record.add("");
				}
				if(p.getAuditTime()!=null){
					record.add(DateUtil.formatDateTime(DateUtil.DATE_FORMAT1, p.getAuditTime()));//审核时间
				} else {
					record.add("");
				}
				if(Checker.isNotEmpty(p.getAuditState())){
					if(ProductAuditState.getType(p.getAuditState())!=null){
//						record.add(ProductAuditState.getType(p.getAuditState()).getDescribe());//审核状态
						record.add(ProductTempAuditState.getType(p.getAuditState()).getDescribe());//审核状态
					} else {
						record.add("");
					}
				} else {
					record.add("");
				}
				if(Checker.isNotEmpty(p.getIsMarketable())){
					if(ProductStatus.disOffShelf.getCode().equals(p.getIsMarketable())){
						record.add("已下架");
					} else if(ProductStatus.getType(p.getIsMarketable())!=null){
						record.add(ProductStatus.getType(p.getIsMarketable()).getDescribe());//状态
					} else {
						record.add("");
					}
				} else {
					record.add("");
				}
				data.add(record);
			}		
		}
		return data;
	}
	
	/**
	 *  获取下载预售商品header
	 */
	public List<String> downPresellProductHeader(){
		List<String> header = new ArrayList<String>();
		header.add("序号");
		header.add("机构名称");
		header.add("商品名称");
		header.add("库存数量");
		header.add("预售价");
		header.add("预售开始时间");
		header.add("预售结束时间");
		header.add("提货开始日期");
		header.add("提货结束日期");
		header.add("审核时间");
		header.add("审核状态");
		header.add("状态");
		return header;
	}
	
	/**
	 *  获取下载预售商品data
	 */
	public List<List<String>> downPresellProductData(List<ProductListInfo> products){
		List<List<String>> data = new ArrayList<List<String>>();
		if(products==null || products.size()==0){
			List<String> record = new ArrayList<String>();
			record.add("");//序号
			record.add("");//机构名称
			record.add("");//商户名称
			record.add("");//商品名称
			record.add("");
			record.add("");
			record.add("");
			record.add("");
			record.add("");
			record.add("");
			record.add("");
			record.add("");
			data.add(record);	
		} else {
			Map<String,String> orgNameMap = new HashMap<String,String>();
			CommonLogic commonLogic = new CommonLogicImpl();
			
			int count = 0;
			Org org = null;
			StringBuilder sb = null;
			List<String> record = null;
			for (ProductListInfo p : products) {
				sb = new StringBuilder(p.getClientId()).append("_").append(p.getOrgCode());
				record = new ArrayList<String>();
				record.add(String.valueOf((++count)));//序号
				if(orgNameMap.get(sb.toString())==null){
					if(p.getOrgCode()!=null && !"".equals(p.getOrgCode().trim())){
		                org = commonLogic.queryByOrgCode(p.getClientId(), p.getOrgCode());
		                if(org!=null){
		                	orgNameMap.put(sb.toString(),org.getOrgName());
		                }
		            }
				}
				record.add(orgNameMap.get(sb.toString()));//机构名称
				record.add(p.getName());//商品名称
				if(p.getStore()!=null){
					record.add(p.getStore().toString());//库存数量
				} else {
					record.add("");
				}
				record.add(String.valueOf(Arith.div(p.getPrice(), GoodsConstants.DOUBLE_INTGER_CHANGE)));//预售价
				if(p.getStartTime()!=null){
					record.add(DateUtil.formatDateTime(DateUtil.DATE_FORMAT1, p.getStartTime()));//预售开始时间
				} else {
					record.add("");
				}
				if(p.getEndTime()!=null){
					record.add(DateUtil.formatDateTime(DateUtil.DATE_FORMAT1, p.getEndTime()));//预售结束时间
				} else {
					record.add("");
				}
				
				Map<String, String> hash = null;
				hash = commonLogic.getProductRedis(p.getClientId(), p.getMerchantId(), p.getProductId());
	            if(hash!=null){
	                if(Checker.isNotEmpty(hash.get("delivery_start_time"))){//提货-开始
	                	record.add(DateUtil.formatDateTime(DateUtil.DATE_FORMAT1, new Date(Long.valueOf(hash.get("delivery_start_time")))));//提货开始日期
	                } else {
	    				record.add("");
	    			}
	                if(Checker.isNotEmpty(hash.get("delivery_end_time"))){//提货-结束
	                    record.add(DateUtil.formatDateTime(DateUtil.DATE_FORMAT1, new Date(Long.valueOf(hash.get("delivery_end_time")))));//提货结束日期
	                } else {
	    				record.add("");
	    			}
	            } else {
					record.add("");
					record.add("");
				}
				if(p.getAuditTime()!=null){
					record.add(DateUtil.formatDateTime(DateUtil.DATE_FORMAT1, p.getAuditTime()));//审核时间
				} else {
					record.add("");
				}
				if(Checker.isNotEmpty(p.getAuditState())){
					if(ProductAuditState.getType(p.getAuditState())!=null){
						record.add(ProductAuditState.getType(p.getAuditState()).getDescribe());//审核状态
					} else {
	    				record.add("");
	    			}
				} else {
					record.add("");
				}
				if(Checker.isNotEmpty(p.getIsMarketable())){
					if(ProductStatus.disOffShelf.getCode().equals(p.getIsMarketable())){
						record.add("已下架");
					} else if(ProductStatus.getType(p.getIsMarketable())!=null){
						record.add(ProductStatus.getType(p.getIsMarketable()).getDescribe());//状态
					} else {
	    				record.add("");
	    			}
				} else {
					record.add("");
				}
				data.add(record);
			}
		}
		return data;
	}
	
}
