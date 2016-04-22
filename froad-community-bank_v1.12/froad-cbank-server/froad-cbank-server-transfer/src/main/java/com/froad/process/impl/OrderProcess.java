package com.froad.process.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

import com.froad.common.mongo.CommonMongo;
import com.froad.common.qrcode.QrCodeGenerateService;
import com.froad.common.qrcode.QrCodeRequestVo;
import com.froad.config.ProcessServiceConfig;
import com.froad.db.ahui.entity.TransDto;
import com.froad.db.ahui.mappers.MerchantExMapper;
import com.froad.db.ahui.mappers.MerchantGroupUserMapper;
import com.froad.db.ahui.mappers.MerchantOutletMapper;
import com.froad.db.ahui.mappers.OutletBankMapper;
import com.froad.db.ahui.mappers.PayMapper;
import com.froad.db.ahui.mappers.ProductCommentMapper;
import com.froad.db.ahui.mappers.TicketMapper;
import com.froad.db.ahui.mappers.TransMapper;
import com.froad.db.chonggou.entity.AreaCG;
import com.froad.db.chonggou.entity.OutletProduct;
import com.froad.db.chonggou.entity.ProductDetail;
import com.froad.db.chonggou.entity.RecvInfo;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.AreaMapperCG;
import com.froad.db.chonggou.mappers.MerchantMapperCG;
import com.froad.db.chonggou.mappers.OrgMapper;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.db.redis.impl.RedisManager;
import com.froad.enums.DeliveryType;
import com.froad.enums.ModuleID;
import com.froad.enums.OrderState;
import com.froad.enums.OrderStatus;
import com.froad.enums.OrgLevelEnum;
import com.froad.enums.PaymentMethod;
import com.froad.enums.QrCodeType;
import com.froad.enums.ShippingStatus;
import com.froad.enums.SubOrderType;
import com.froad.enums.TransferTypeEnum;
import com.froad.fft.persistent.api.PresellDeliveryMapper;
import com.froad.fft.persistent.entity.Merchant;
import com.froad.fft.persistent.entity.MerchantGroupUser;
import com.froad.fft.persistent.entity.MerchantOutlet;
import com.froad.fft.persistent.entity.OutleBank;
import com.froad.fft.persistent.entity.Pay;
import com.froad.fft.persistent.entity.PresellDelivery;
import com.froad.fft.persistent.entity.ProductComment;
import com.froad.fft.persistent.entity.Receiver;
import com.froad.fft.persistent.entity.TransSecurityTicket;
import com.froad.fft.persistent.entity.TransTicketInfo;
import com.froad.logback.LogCvt;
import com.froad.po.Org;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.mongo.ProductMongo;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.process.AbstractProcess;
import com.froad.util.Arith;
import com.froad.util.CollectionsUtil;
import com.froad.util.Constans;
import com.froad.util.MemberUtil;
import com.froad.util.RedisKeyUtil;
import com.froad.util.SimpleID;

public class OrderProcess extends AbstractProcess {
	
	private SimpleID odSimpleID = new SimpleID(ModuleID.order);
	private SimpleID subSimpleID = new SimpleID(ModuleID.suborder);
	private SimpleID prodSimpleID = new SimpleID(ModuleID.product);
    private static SimpleID recvSimpleID = new SimpleID(ModuleID.recvInfo);
    private static BigDecimal maxInt = new BigDecimal(Integer.MAX_VALUE);
    private static Integer constansVal = new Integer(900);

	
	final Map<Long, Date> payMap = new HashMap<Long, Date>();//支付时间
	final Map<String, ProductComment> commentMap = new HashMap<String, ProductComment>();//商品评论
	final Map<String, PresellDelivery> preMap = new HashMap<String, PresellDelivery>();//
	final Map<String, String> merMap = new HashMap<String, String>();
	final Map<String, String> proMap = new HashMap<String, String>();
	final Map<String, String> merUserMap = new HashMap<String, String>();
	final Map<String, String> merGroupUserMap = new HashMap<String, String>();
	final Map<String, String> outletBankMap = new HashMap<String, String>();
	final Map<String, Merchant> merNameMap = new HashMap<String, Merchant>();
	final Map<String, String> outletMap = new HashMap<String, String>();
	//private Map<String, String> outletCgMap = new HashMap<String, String>();
	final Map<String, String> merOutletMap = new HashMap<String, String>();
	final Map<String, String> orgBankMap = new HashMap<String, String>();
	final Map<String, String> area2IdNameMap = new HashMap<String, String>();
	final Map<String, String> area3IdNameMap = new HashMap<String, String>();
	final Map<String, Receiver> receiverMap = new HashMap<String, Receiver>();
	final Map<String, String> receIdMap = new HashMap<String, String>();
	final Map<String, String> areaMap = new HashMap<String, String>();
	final Map<String, TransSecurityTicket> ticketMap = new HashMap<String, TransSecurityTicket>();
	
	private  TransferMapper tsfMapper = null;

	public OrderProcess(String name, ProcessServiceConfig config) {
		super(name, config);
	}
	
	public void loadData() {
		tsfMapper =  sqlSession.getMapper(TransferMapper.class);
		ProductCommentMapper proComMapper = ahSqlSession.getMapper(ProductCommentMapper.class);
		MerchantExMapper merMapper = ahSqlSession.getMapper(MerchantExMapper.class);
		PayMapper payMapper = ahSqlSession.getMapper(PayMapper.class);
		PresellDeliveryMapper preMapper = ahSqlSession.getMapper(PresellDeliveryMapper.class);
		MerchantGroupUserMapper merGroupUserMapper = ahSqlSession.getMapper(MerchantGroupUserMapper.class);
		OutletBankMapper outletMapper = ahSqlSession.getMapper(OutletBankMapper.class);
		MerchantOutletMapper merOutMapper = ahSqlSession.getMapper(MerchantOutletMapper.class);
		TicketMapper ticketMapper = ahSqlSession.getMapper(TicketMapper.class);
		
		OrgMapper orgBankMapper = sqlSession.getMapper(OrgMapper.class);
		AreaMapperCG areaMapper = sqlSession.getMapper(AreaMapperCG.class);
		//OutletMapperCG outletCgMapper = sqlSession.getMapper(OutletMapperCG.class); 
		
		List<Pay> payList = payMapper.findAllGroupFirstDate();
		if(payList != null && payList.size() > 0) {
			for(Pay p : payList) {
				payMap.put(p.getTransId(), p.getCreateTime());
			}
		}
		
		ProductComment qyProcom = new ProductComment();
		List<ProductComment> proComList = proComMapper.selectByCondition(qyProcom);
		if(proComList != null && proComList.size() >0) {
			for(ProductComment procom : proComList) {
				commentMap.put(procom.getTransSn(), procom);
			}
		}
		
		PresellDelivery pre = new PresellDelivery();
		List<PresellDelivery> preList = preMapper.selectByConditions(pre); 
		if(preList != null && preList.size() >0) {
			for(PresellDelivery p : preList) {
				preMap.put(p.getId().toString(), p);
			}
		}
		
		
		List<Transfer> merList = tsfMapper.queryGroupList(TransferTypeEnum.merchant_id);
		if(merList != null && merList.size() >0) {
			for(Transfer tf : merList) {
				merMap.put(tf.getOldId()  , tf.getNewId());
			}
		}
		
		List<Transfer> proList = tsfMapper.queryGroupList(TransferTypeEnum.product_id);
		if(proList != null && proList.size() >0) {
			for(Transfer tf : proList) {
				proMap.put(tf.getOldId() , tf.getNewId());
			}
		}
		
		List<Transfer> merUserList = tsfMapper.queryGroupList(TransferTypeEnum.merchant_user_id);
		if(merUserList != null && merUserList.size() >0) {
			for(Transfer tf : merUserList) {
				merUserMap.put(tf.getOldId()  , tf.getNewId());
			}
		}
		
		
		List<Transfer> outletList = tsfMapper.queryGroupList(TransferTypeEnum.outlet_id);
		if(outletList != null && outletList.size() >0) {
			for(Transfer tf : outletList) {
				outletMap.put(tf.getOldId() , tf.getNewId());
			}
		}
		
		
		List<Transfer> areaIdList = tsfMapper.queryGroupList(TransferTypeEnum.area_id);
		if(areaIdList != null && areaIdList.size() > 0) {
			for(Transfer t : areaIdList) {
				areaMap.put(t.getOldId(), t.getNewId());
			}
		}
		
		List<TransSecurityTicket> ticketList = ticketMapper.queryAll();
		if(ticketList != null && ticketList.size() > 0) {
			for(TransSecurityTicket ti : ticketList) {
				ticketMap.put(ti.getTransId().toString(), ti);
			}
		}
		
		List<MerchantGroupUser> merGroupUserList = merGroupUserMapper.findAllMerchantGroupUser();
		if(merGroupUserList != null && merGroupUserList.size() >0) {
			for(MerchantGroupUser tf : merGroupUserList) {
				if(tf.getSysUserId() != null ) {
					if(tf.getMerchantOutletId() != null) {
						merGroupUserMap.put(tf.getSysUserId().toString() , "#" + tf.getMerchantOutletId().toString());
					} else {
						merGroupUserMap.put(tf.getSysUserId().toString() ,"@" + tf.getMerchantId().toString());
					}
				}
			}
		}
		
		List<OutleBank> outletBankList = outletMapper.queryAllList();
		if(outletBankList != null && outletBankList.size() >0) {
			for(OutleBank tf : outletBankList) {
				if(tf.getMerchatId() != null && StringUtils.isNotEmpty(tf.getBankOrg())) {
					outletBankMap.put(tf.getMerchatId().toString() ,tf.getBankOrg().split("1000_")[1]);
				}
			}
		}
		
		Merchant m = new Merchant();
		m.setOrderValue(null);
		List<Merchant> rsmList = merMapper.selectByConditions(m);
		if(rsmList != null && rsmList.size() > 0) {
			for(Merchant t : rsmList)  {
				merNameMap.put(t.getId().toString(), t);
			}
		}
		
		
		List<MerchantOutlet> merOutList = merOutMapper.selectByProductComment();
		if(merOutList != null && merOutList.size() > 0) {
			for(MerchantOutlet merOut : merOutList) {
				merOutletMap.put(merOut.getMerchantId().toString(), merOut.getId().toString());
			}
		}
		
		/*List<OutletCG> outletCgList = outletCgMapper.findGroupList();
		if(outletCgList != null && outletCgList.size() > 0) {
			for(OutletCG ocg : outletCgList) {
				outletCgMap.put(ocg.getMerchantId(), ocg.getOutletId());
			}
		}*/
		Org org = new Org();
		List<Org> orgBankList = orgBankMapper.findOrg(org);
		if(orgBankList != null && orgBankList.size() > 0){
			for(Org o : orgBankList) {
				orgBankMap.put(o.getOrgCode(), o.getOrgName());
			}
		} 
		
		AreaCG area = new AreaCG();
		List<AreaCG> areaList = areaMapper.findArea(area);
		if(areaList != null && areaList.size() > 0) {
			for(AreaCG a : areaList) {
				String treeName = a.getTreePathName();
				if(StringUtils.isNotEmpty(treeName)) {
					int leng = treeName.split(",").length;
					if(leng == 2) {
						area2IdNameMap.put(treeName.trim().replace(",", ""), a.getId().toString());
					} else if(leng == 3) {
						area3IdNameMap.put(treeName.trim().replace(",", ""), a.getId().toString());
					}
				}
			}
		}
	}

	@Override
	public void process() {
		LogCvt.info("【开始订单、子订单模块数据迁移】");
		TransMapper trMapper = ahSqlSession.getMapper(TransMapper.class);
		//获取所有订单记录信息
		List<TransDto> transList = trMapper.queryAllOrderList();
		List<Transfer> idInsertList = new ArrayList<Transfer>(70000);
		List<Transfer> snInsertList = new ArrayList<Transfer>(70000);
		
		if(null != transList && transList.size() > 0) {
			loadData();//加载相关数据到内存
			BigDecimal change = new BigDecimal("1000");
			for(TransDto t : transList) {
				//根据老商户id获取新生成的商户id
				String newMerchantId = null;
				if(t.getMerchantId() != null) {
					newMerchantId = merMap.get(t.getMerchantId().toString());
				}
				String newProductId = null;
				
				if(t.getProductId() != null) {
					newProductId = proMap.get(t.getProductId().toString());
				}
				String newMerchantUserId = null;
				if(t.getSysUserId() != null) {
					newMerchantUserId = merUserMap.get(t.getSysUserId().toString());
				}
				
				
				//String newOutletId = outletMap.get(t.getOutletId());
				
				String newOutletId = null;
				if(t.getSysUserId() != null) {
					String tempId = merGroupUserMap.get(t.getSysUserId().toString());
					if(StringUtils.isNotEmpty(tempId)) {
						if(tempId.contains("#")) {
							newOutletId = outletMap.get(tempId.substring(1));
						} else {
							String oldOutId  = merOutletMap.get(tempId.substring(1));
							newOutletId = outletMap.get(oldOutId);
						}
					} 
				}
				
				
				//订单相关信息组装
				String orderId = odSimpleID.nextId();//大订单号
				OrderMongo order = new OrderMongo();
				order.setOrderId(orderId);
				order.setCreateTime(t.getCreateTime().getTime());
				order.setCreateSource(t.getCreateSource().getCode());
				if(null == t.getPayChannel()) {
					order.setPaymentMethod("");
				} else {
					if("00".equals(t.getPayChannel().getCode())) {
						order.setPaymentMethod(PaymentMethod.bankPoints.getCode());//银行积分
					} else if(
								(("20".equals(t.getPayChannel().getCode()) || "51".equals(t.getPayChannel().getCode())) && StringUtils.isNotEmpty(t.getBankPoints())) 
							 ) {
						order.setPaymentMethod(PaymentMethod.bankPointsAndCash.getCode());//银行积分+现金
					} else if("20".equals(t.getPayChannel().getCode()) || "51".equals(t.getPayChannel().getCode())) {
						order.setPaymentMethod(PaymentMethod.cash.getCode());//现金
					} 
				}
				
				order.setState(OrderState.NORMAL.getCode());
				order.setMemberCode(t.getMemberCode());
				order.setMemberName(MemberUtil.getMemberName(t.getMemberCode()));
				order.setClientId(Constans.clientId);
				Date payTime = payMap.get(t.getId());
				order.setPaymentTime(payTime != null?payTime.getTime():null);
				if(StringUtils.isNotEmpty(t.getFftPoints())) {
					BigDecimal bFftPoint = new BigDecimal(t.getFftPoints());
					if(bFftPoint.compareTo(maxInt) != -1 ) {
						order.setFftPoints(constansVal.intValue());
					} else {
						order.setFftPoints(bFftPoint.multiply(change).toBigInteger().intValue());
					}
					
				} else{
					order.setFftPoints(0);
				}
				if(StringUtils.isNotEmpty(t.getBankPoints())) {
					BigDecimal bBankPoint = new BigDecimal(t.getBankPoints());
					if(bBankPoint.compareTo(maxInt) != -1 ) {
						order.setBankPoints(constansVal.intValue());
					} else {
						order.setBankPoints(bBankPoint.multiply(change).toBigInteger().intValue());
					}
					
				} else {
					order.setBankPoints(0);
				}
				
				order.setVipDiscount(null);
				order.setFee("0");
				order.setPhone(t.getPhone());
				order.setRemark(t.getRemark());
				order.setPointRate(t.getExchangeRate());
				order.setGivePoints(StringUtils.isNotEmpty(t.getGivePoints())?new Integer(t.getGivePoints()):null);
				order.setMerchantUserId(newMerchantUserId != null?Long.valueOf(newMerchantUserId):null);
				
				
				//更新商户门店收藏表收获地址信息
				
				order.setRecvId(getRecvId(t));
				
				order.setOutletId(newOutletId);
				String orderStatus = null;
				String oldState = t.getState().getCode() ;
				String oldPayState = t.getPayState().getCode();
				if(( "20".equals(oldState) || "40".equals(oldState)) && "10".equals(oldPayState) ) {
					orderStatus = OrderStatus.create.getCode();
				} else if( "50".equals(oldState) &&  "10".equals(oldPayState) && StringUtils.isEmpty(t.getRemark())) {
					orderStatus = OrderStatus.closed.getCode();
				} else if("50".equals(oldState) && ( "10".equals(oldPayState) || "20".equals(oldPayState) )&& StringUtils.isNotEmpty(t.getRemark())) {
					orderStatus = OrderStatus.sysclosed.getCode();
				} else if("20".equals(oldState) && "20".equals(oldPayState)) {
					orderStatus = OrderStatus.paying.getCode();
				} else if("40".equals(oldState) && "40".equals(oldPayState)) {
					orderStatus = OrderStatus.payfailed.getCode();
				} else if(
						  ("30".equals(oldState) && ( "30".equals(oldPayState) || "40".equals(oldPayState) || "60".equals(oldPayState) ))
							||
						  ("40".equals(oldState) && "50".equals(oldPayState))
						    ||
						  ("20".equals(oldState) && ( "30".equals(oldPayState) || "40".equals(oldPayState) ))
						    ||
						  ("50".equals(oldState) && ( "40".equals(oldPayState) || "50".equals(oldPayState) )) 
						    ||
						  ("60".equals(oldState) && ( "30".equals(oldPayState) || "40".equals(oldPayState) ))
						 ) {
					orderStatus = OrderStatus.paysuccess.getCode();
				} 
				
				order.setOrderStatus(orderStatus);
				SubOrderMongo subOrder = null;
				ProductMongo product = null;
				String subOrderId = null;
				if(!"05".equals(t.getType().getCode())) {
					if(StringUtils.isNotEmpty(t.getTotalPrice())) {
						BigDecimal bTotal = new BigDecimal(t.getTotalPrice());
						if(bTotal.compareTo(maxInt) != -1) {
							order.setTotalPrice(constansVal.intValue());
						} else {
							order.setTotalPrice(bTotal.multiply(change).toBigInteger().intValue());	
						}
						
					} else {
						order.setTotalPrice(0);
					}
					if(StringUtils.isNotEmpty(t.getRealPrice())) {
						BigDecimal bReal = new BigDecimal(t.getRealPrice());
						if(bReal.compareTo(maxInt) != -1) {
							order.setRealPrice(constansVal.intValue());
						} else {
							order.setRealPrice(bReal.multiply(change).toBigInteger().intValue());
						}
					
					} else {
						order.setRealPrice(0);
					}
					order.setIsQrcode(BooleanUtils.toInteger(false));
					/**
					 * 待确定
					 *//*
					//order.setDeliverId(t.getDeliveryId());
					
					*//***************子订单相关*********************/
					subOrderId = subSimpleID.nextId();
					subOrder = new SubOrderMongo();
					subOrder.setCreateTime(t.getCreateTime().getTime());
					subOrder.setClientId(Constans.clientId);
					subOrder.setOrderId(orderId);
					subOrder.setSubOrderId(subOrderId);
					subOrder.setMemberCode(t.getMemberCode());
					subOrder.setMemberName(MemberUtil.getMemberName(t.getMemberCode()));
					subOrder.setOrderStatus(orderStatus);
					String orderType = null;
					if("01".equals(t.getType().getCode())) {
						orderType = SubOrderType.online_points_org.getCode();//线上积分兑换
						order.setIsPoint(1);
					} else if("11".equals(t.getType().getCode())) {
						orderType = SubOrderType.offline_points_org.getCode();//线下积分兑换
						order.setIsPoint(1);
					}else if("02".equals(t.getType().getCode())) {
						orderType = SubOrderType.group_merchant.getCode();//团购
						order.setIsPoint(0);
					} else if("08".equals(t.getType().getCode())) {
						orderType = SubOrderType.presell_org.getCode();//预售
						order.setIsPoint(0);
					} else if("12".equals(t.getType().getCode())) {
						orderType = SubOrderType.special_merchant.getCode();//名优特惠
						order.setIsPoint(0);
					}
					subOrder.setType(orderType);
					
					if("08".equals(t.getType().getCode()) || "01".equals(t.getType().getCode()) ||  "11".equals(t.getType().getCode())) {//如果是预售则获取机构编号
						String str = outletBankMap.get(t.getMerchantId().toString());
						if(StringUtils.isEmpty(str)) {
							LogCvt.error("【查询机构code为null:"+ t.getMerchantId().toString() +"】");
						}
						subOrder.setMerchantId(outletBankMap.get(t.getMerchantId().toString()));
					} else {
						subOrder.setMerchantId(newMerchantId);
					}
					if(t.getMerchantId() != null) {
						Merchant mt = merNameMap.get(t.getMerchantId().toString());
						subOrder.setMerchantName(mt!= null?mt.getName():null);
					}
					
					Map<OrgLevelEnum, String> orgCodeMap = getSuperOrgByMerchantId(newMerchantId);
					if(orgCodeMap != null) {
						subOrder.setForgCode(orgCodeMap.get(OrgLevelEnum.orgLevel_one));
						subOrder.setSorgCode(orgCodeMap.get(OrgLevelEnum.orgLevel_two));
						subOrder.setTorgCode(orgCodeMap.get(OrgLevelEnum.orgLevel_three));
						subOrder.setLorgCode(orgCodeMap.get(OrgLevelEnum.orgLevel_four));
					}
					
					/**************************商品相关****************************************/
					product = new ProductMongo();
					product.setProductId(newProductId);
					product.setProductName(t.getProductName());
					if(StringUtils.isNotEmpty(newProductId)) {
						ProductDetail proDetail = mongo.findOneById(newProductId,"cb_product_details", ProductDetail.class);
						if(null != proDetail && null != proDetail.getImageInfo() ) {
							product.setProductImage(proDetail.getImageInfo().get(0).getThumbnail());
						} 
					}else {
						product.setProductImage("");
					}
					
					product.setType(orderType);
					product.setQuantity(t.getQuantity());
					if(StringUtils.isNotEmpty(t.getSingle())) {
						BigDecimal bsingle = new BigDecimal(t.getSingle());
						if(bsingle.compareTo(maxInt) != -1) {
							product.setMoney(constansVal.intValue());
						} else {
							// 如果是积分兑换,商品金额存的积分值
							if("01".equals(t.getType().getCode())) {
								int jfValue = Arith.mul(Double.parseDouble(t.getSingle()), Integer.parseInt(t.getExchangeRate()));
								product.setMoney(Arith.mul(jfValue, 1000));
							} else if("11".equals(t.getType().getCode())) {
								int jfValue = Arith.mul(Double.parseDouble(t.getSingle()), Integer.parseInt(t.getExchangeRate()));
								product.setMoney(Arith.mul(jfValue, 1000));
							}else{
								product.setMoney(bsingle.multiply(change).toBigInteger().intValue());
							}
							
						}
						
					} else {
						product.setMoney(0);
					}
					
					
					product.setVipMoney(0);
					product.setVipQuantity(0);
					if(StringUtils.isNotEmpty(t.getGivePoints()) && t.getQuantity() != null) {
						BigDecimal tempGivePoint = new BigDecimal(t.getGivePoints());
						if(tempGivePoint.compareTo(maxInt) != -1) {
							product.setPoints(constansVal.intValue());
						} else {
							BigInteger givePoint = new BigInteger(t.getGivePoints());
							BigInteger quantity = BigInteger.valueOf(t.getQuantity());
							BigInteger point = givePoint.divide(quantity);
							product.setPoints(point.intValue());
						}
					} else {
						product.setPoints(0);
					}
					product.setDeliverMoney(0);
					if("08".equals(t.getType().getCode()) || "11".equals(t.getType().getCode()))  {//预售网点自提
						PresellDelivery pd = preMap.get(t.getDeliveryId().toString());
						if(null != pd && StringUtils.isNotEmpty(pd.getBankOrgNo())) {
							product.setOrgCode(pd.getBankOrgNo().split("1000_")[1]);
							product.setOrgName(orgBankMap.get(pd.getBankOrgNo().split("1000_")[1]));
						}
					}
					ProductComment procom = commentMap.get(t.getSn());
					if(null != procom) {
						product.setCommentState("1");
		 			} else {
						product.setCommentState("0");
					}
					String deType = null;
					
					if("1".equals(t.getDeliveryType()) ) {//网点自提
						deType = DeliveryType.take.getCode();
					} else if("3".equals(t.getDeliveryType())) {//送货上门
						deType = DeliveryType.home.getCode();
					}
					if("12".equals(t.getType().getCode())) {//如果是名优特惠
						String deState = t.getShipState();
						String ss = null;
						if("0".equals(deState)) {
							ss = ShippingStatus.unshipped.getCode();
						} else if("1".equals(deState)) {
							ss = ShippingStatus.shipped.getCode();
						} else if("2".equals(deState)) {
							ss = ShippingStatus.receipt.getCode();
						}
						subOrder.setDeliveryOption(deType);
						subOrder.setDeliveryState(ss);
					} else if("08".equals(t.getType().getCode())) {//如果是预售
						product.setDeliveryOption(deType);
						TransSecurityTicket ticket = ticketMap.get(t.getId().toString());
						if(ticket == null) {
							product.setDeliveryState(ShippingStatus.untake.getCode());
						} else {
							boolean isConsume = ticket.getIsConsume();
							// 消费时间
							Long consumeTime = ticket.getConsumeTime()==null?null:ticket.getConsumeTime().getTime();
							if(isConsume && consumeTime != null) {
								product.setDeliveryState(ShippingStatus.token.getCode());
							} else {
								product.setDeliveryState(ShippingStatus.untake.getCode());
							}
						}
					} 
					
					//插入子订单表
					List<ProductMongo> products = new ArrayList<ProductMongo>();
					products.add(product);
					subOrder.setProducts(products);
					mongo.add(subOrder, "cb_suborder_product");
				} else {
					//面对面在支付
					order.setIsQrcode(BooleanUtils.toInteger(true));
					order.setMerchantId(newMerchantId);
					Merchant mt = merNameMap.get(t.getMerchantId().toString());
					if(mt != null) {
						order.setMerchantName(mt.getName());
						order.setForgCode(StringUtils.isNotEmpty(mt.getInterbankAgency())?mt.getInterbankAgency().split("1000_")[1]:null );//1
						order.setSorgCode(StringUtils.isNotEmpty(mt.getTravelAgency())?mt.getTravelAgency().split("1000_")[1]:null);//2
						order.setTorgCode(StringUtils.isNotEmpty(mt.getLatticPoint())?mt.getLatticPoint().split("1000_")[1]:null);//3
						order.setLorgCode(null);
					}
					
					if(StringUtils.isNotEmpty(t.getGatheringValue())) {
						BigDecimal vTotal = new BigDecimal(t.getGatheringValue());
						order.setTotalPrice(vTotal.multiply(change).toBigInteger().intValue());
						order.setRealPrice(vTotal.multiply(change).toBigInteger().intValue());
					}
					OutletProduct outpro = null;
					if(StringUtils.isNotEmpty(newOutletId) && StringUtils.isNotEmpty(t.getGatheringValue()) && StringUtils.isNotEmpty(newMerchantId)) {
						BigDecimal bprice = new BigDecimal(t.getGatheringValue());
						Integer tpGather = null;
						if(bprice.compareTo(maxInt) != -1) {
							tpGather = constansVal;
						}  else {
							tpGather = bprice.multiply(change).toBigInteger().intValue();
						}
						
						String proId = null;
						outpro = CommonMongo.queryOutletProduct(newMerchantId, newOutletId, tpGather);
						if(outpro == null) {
							OutletProduct svOutPro = new OutletProduct();
							proId = prodSimpleID.nextId();
							svOutPro.setProductId(proId);
							svOutPro.setCost(tpGather);
							svOutPro.setMerchantId(newMerchantId);
							svOutPro.setClientId(Constans.clientId);
							svOutPro.setOutletId(newOutletId);
							CommonMongo.addOutletProduct(svOutPro);
						} else {
							proId = outpro.getProductId();
						}
						order.setProductId(proId);
						
						if(StringUtils.isNotEmpty(newMerchantUserId)) {
							order.setQrcode(qcode(proId, newMerchantUserId));
						}
					}
				}
				//插入订单
				mongo.add(order, "cb_order");
				
				/****************************将订单关联数据放入内存****************************************/
				Transfer idTransfer = new Transfer();
				idTransfer.setOldId(t.getId().toString());
				idTransfer.setType(TransferTypeEnum.order_id);
				Transfer snTransfer = new Transfer();
				snTransfer.setOldId(t.getSn());
				snTransfer.setType(TransferTypeEnum.sn_order_id);
				
				if(null != subOrder) {
					idTransfer.setNewId(orderId+ "," + subOrderId);
					snTransfer.setNewId(orderId+ "," + subOrderId);
				} else {
					idTransfer.setNewId(orderId+ ",");
					snTransfer.setNewId(orderId+ ",");
				}
				
				idInsertList.add(idTransfer);
				snInsertList.add(snTransfer);
			}
		}
		
		//批量插入订单关联数据到mysql
		List<List<Transfer>> idAll = CollectionsUtil.splitList(idInsertList, 10000);
		if(idAll != null && idAll.size() > 0) {
			for(List<Transfer> t : idAll) {
				tsfMapper.addTransferBatch(t);
			}
		}
		List<List<Transfer>> snAll = CollectionsUtil.splitList(snInsertList, 10000);
		if(snAll != null && snAll.size() > 0) {
			for(List<Transfer> t : snAll) {
				tsfMapper.addTransferBatch(t);
			}
		}
 }
	

	/**
	 * 根据merchant_id查到1、2、3、4级机构号
	 * @Title: getSuperOrgByMerchantId 
	 * @Description: 根据商户ID获取本级机构号与上级机构号
	 * @param merchantId
	 * @param clientId
	 * @return	
	 * @throws
	 */
	private Map<OrgLevelEnum, String> getSuperOrgByMerchantId(String merchantId) {
		if(merchantId == null){
			return null;
		}
		
		Map<OrgLevelEnum, String> map = null;
		try { 
			OrgMapper orgMapper = sqlSession.getMapper(OrgMapper.class);
		
			//过滤条件
			Org orgFileter = new Org();
			orgFileter.setMerchantId(merchantId);
			Org resultOrg=orgMapper.findOrgById(orgFileter);
			
			//如果merchantid属于3级机构发展，那么4就返回0，1、2、3返回有效值。
			//如果merchantid属于4级机构，那么0、1、2、3都返回有效值。
			
			//机构商户
			if(resultOrg!=null){
				map=setOrgMap(resultOrg);
			}else{//普通商户
				MerchantMapperCG merchantMapper=sqlSession.getMapper(MerchantMapperCG.class);
				com.froad.po.Merchant merchant=merchantMapper.findMerchantByMerchantId(merchantId);
				if(merchant!=null){
					String orgCode=merchant.getOrgCode();//发展机构
					String clientId=merchant.getClientId();
					//设置过滤条件，进行查机构对象
					orgFileter=new Org();
					orgFileter.setOrgCode(orgCode);
					orgFileter.setClientId(clientId);
					
					//结果机构对象
					resultOrg=orgMapper.findOrgById(orgFileter);
					if(resultOrg!=null){
						map=this.setOrgMap(resultOrg);
					} else {
						LogCvt.error("【商户ID："+ merchantId +"找不到机构】");
					}
				} else {
					LogCvt.error("【商户ID："+ merchantId +"找不到商户】");
				} 
			}
		}catch (Exception e) { 
			LogCvt.error("根据Id查询Org失败，原因:" + e.getMessage()); 
		} 
		return map;
	}
	
	
	/**
	 * 设置map值
	 * @param resultOrg
	 * @return
	 */
	private Map<OrgLevelEnum, String> setOrgMap(Org resultOrg){
		Map<OrgLevelEnum, String> map = new HashMap<OrgLevelEnum, String>();
		//如果merchantid属于3级机构发展，那么4就返回0，1、2、3返回有效值。
		//如果merchantid属于4级机构，那么0、1、2、3都返回有效值。
		String orgLevel=resultOrg.getOrgLevel();
		if(OrgLevelEnum.orgLevel_three.getLevel().equals(orgLevel)){
			map.put(OrgLevelEnum.orgLevel_one, resultOrg.getProvinceAgency());
			map.put(OrgLevelEnum.orgLevel_two, resultOrg.getCityAgency());
			map.put(OrgLevelEnum.orgLevel_three, resultOrg.getOrgCode());
			map.put(OrgLevelEnum.orgLevel_four, "0");
		}else if(OrgLevelEnum.orgLevel_four.getLevel().equals(orgLevel)){
			map.put(OrgLevelEnum.orgLevel_one, resultOrg.getProvinceAgency());
			map.put(OrgLevelEnum.orgLevel_two, resultOrg.getCityAgency());
			map.put(OrgLevelEnum.orgLevel_three, resultOrg.getCountyAgency());
			map.put(OrgLevelEnum.orgLevel_four, resultOrg.getOrgCode());
		}else if(OrgLevelEnum.orgLevel_two.getLevel().equals(orgLevel)){
			map.put(OrgLevelEnum.orgLevel_one, resultOrg.getProvinceAgency());
			map.put(OrgLevelEnum.orgLevel_two, resultOrg.getOrgCode());
		}else{
			map.put(OrgLevelEnum.orgLevel_one, resultOrg.getOrgCode());
		}
		
		return map;
	}
	
	
	public String qcode(String productId, String merchantUserId ) {
		 generateQrCode(Constans.clientId,productId,QrCodeType.FACE2FACE.getCode());
		 String qcode = QrCodeType.FACE2FACE.getCode() + productId;
		 String key = RedisKeyUtil.cbbank_outlet_product_clientId_qrcode(Constans.clientId, qcode);
         
         RedisManager redis = new RedisManager();
         redis.hset(key, "state", "1");
         redis.hset(key, "id", productId+"_"+merchantUserId);
         return qcode;
	}
	
	
	   /**
     * 生成二维码
     * @param clientId
     * @param productId
     * @param qrCodeType
     * @return String 二维码url
     */
    private void generateQrCode(String clientId,String productId,String qrCodeType){
        try {
        	
        	QrCodeGenerateService qrCodeService = new QrCodeGenerateService();
            QrCodeRequestVo qrcodeRequestVo = new QrCodeRequestVo(qrCodeType+productId, clientId);
            //面对面商品二维码url
            qrCodeService.generateQrCode(qrcodeRequestVo);
            LogCvt.debug("生成二维码结束"+new Date().getTime());
        }catch (Exception e) {
            LogCvt.error("商品二维码url异常：" +e);
            LogCvt.error("productId:"+productId+",clientId:"+clientId+",qrCodeType:"+qrCodeType);
        }
    }
    
    private String getRecvId(TransDto t) {
    	if(null == t.getMemberCode()) {
    		return null;
    	}
    	String areaId = null;
    	if("01".equals(t.getType().getCode()) || "12".equals(t.getType().getCode())) {
    		if(StringUtils.isNotEmpty(t.getAddress())) {
        		String reId = receIdMap.get(t.getMemberCode().toString() + "s" + t.getAddress().replace(" ", ""));
            	if(StringUtils.isNotEmpty(reId)) {
            		return reId;
            	}
            	String aress = t.getAddress().replace(" ", "").trim();
            	int shindex = aress.indexOf("上海");
            	if(shindex != -1) {
            		areaId = area3IdNameMap.get("上海上海市浦东新区");
            	} else {
            		Set<String> keySet3 =  area3IdNameMap.keySet();
            		Iterator<String> it3 = keySet3.iterator();
            		while(it3.hasNext()) {
            			String key = (String)it3.next();
            			if(aress.indexOf(key) != -1) {
            				areaId = area3IdNameMap.get(key);
            				break;
            			}
            		}
            		
            		if(StringUtils.isEmpty(areaId)) {
            			Set<String> keySet2 =  area2IdNameMap.keySet();
            			Iterator<String> it2 = keySet2.iterator();
                		while(it2.hasNext()) {
                			String key = (String)it2.next();
                			if(aress.indexOf(key) != -1) {
                				areaId = area2IdNameMap.get(key);
                				break;
                			}
                		}
            		}
            	}
            	
            	//追加门店收获地址收藏信息
            	RecvInfo recvInfo = new RecvInfo();
            	recvInfo.setAddress(t.getAddress());
            	if(StringUtils.isNotEmpty(areaId)) {
            		recvInfo.setAreaId(Long.valueOf(areaId));
            	}
            	
            	recvInfo.setConsignee(t.getDeliveryName());
            	recvInfo.setPhone(t.getPhone());
            	recvInfo.setRecvId(recvSimpleID.nextId());
            	recvInfo.setIsEnable("0");
            	CommonMongo.appedRecvInfo(Constans.clientId, Long.valueOf(t.getMemberCode()), recvInfo);
            	receIdMap.put(t.getMemberCode().toString() + "s" + t.getAddress().replace(" ", "") , recvInfo.getRecvId());
            	return recvInfo.getRecvId();
        	} 
		}else {
			if(null != t.getDeliveryId() && t.getDeliveryId() != -1) {
				PresellDelivery pd = preMap.get(t.getDeliveryId().toString());
				if(pd != null) {
					String reId = receIdMap.get(t.getMemberCode().toString() + "t" + pd.getAddress().replace(" ", ""));
		        	if(StringUtils.isNotEmpty(reId)) {
		        		return reId;
		        	}
		        	
		        	//追加门店收获地址收藏信息
	            	RecvInfo recvInfo = new RecvInfo();
	            	recvInfo.setAddress(pd.getAddress());
	            	recvInfo.setConsignee(t.getDeliveryName());
	            	recvInfo.setPhone(t.getPhone());
	            	recvInfo.setRecvId(recvSimpleID.nextId());
	            	recvInfo.setIsEnable("0");
	            	areaId = areaMap.get(pd.getAreaId().toString());
	            	if(StringUtils.isNotEmpty(areaId)) {
	            		recvInfo.setAreaId(Long.valueOf(areaId));
	            	}
	            	CommonMongo.appedRecvInfo(Constans.clientId, Long.valueOf(t.getMemberCode()), recvInfo);
	            	receIdMap.put(t.getMemberCode().toString() + "t" + pd.getAddress().replace(" ", ""), recvInfo.getRecvId() );
	            	return recvInfo.getRecvId();
				}
	    	}
		}
    	return null;
    }
    
   /* private String getRecvId(TransDto t) {
    	if( t.getMemberCode() == null) {
    		return null;
    	}
    	String areaId = null;
    	if(StringUtils.isEmpty(t.getAddress()) ) {
    		String reId = receIdMap.get(t.getMemberCode().toString());
        	if(StringUtils.isNotEmpty(reId)) {
        		return reId;
        	}
    		//追加门店收获地址收藏信息
        	RecvInfo recvInfo = new RecvInfo();
        	recvInfo.setAddress("	");
        	
        	if(StringUtils.isNotEmpty(t.getDeliveryName())) {
        		recvInfo.setConsignee(t.getDeliveryName());
            	recvInfo.setPhone(t.getPhone());
        	} else {
        		Receiver r = receiverMap.get(t.getMemberCode().toString());
        		if(r != null) {
            		recvInfo.setConsignee(r.getConsignee());
                	recvInfo.setPhone(r.getPhone());
            	}
        	}
        
        	recvInfo.setRecvId(recvSimpleID.nextId());
        	CommonMongo.appedRecvInfo(Constans.clientId, Long.valueOf(t.getMemberCode()), recvInfo);
        	receIdMap.put(t.getMemberCode().toString(), recvInfo.getRecvId());
        	return recvInfo.getRecvId();
    	} else {
    		String reId = receIdMap.get(t.getMemberCode().toString());
        	if(StringUtils.isNotEmpty(reId)) {
        		return reId;
        	}
        	String aress = t.getAddress().replace(" ", "").trim();
        	int shindex = aress.indexOf("上海");
        	if(shindex != -1) {
        		areaId = area3IdNameMap.get("上海上海市浦东新区");
        	} else {
        		Set<String> keySet3 =  area3IdNameMap.keySet();
        		Iterator<String> it3 = keySet3.iterator();
        		while(it3.hasNext()) {
        			String key = (String)it3.next();
        			if(aress.indexOf(key) != -1) {
        				areaId = area3IdNameMap.get(key);
        				break;
        			}
        		}
        		
        		if(StringUtils.isEmpty(areaId)) {
        			Set<String> keySet2 =  area2IdNameMap.keySet();
        			Iterator<String> it2 = keySet2.iterator();
            		while(it2.hasNext()) {
            			String key = (String)it2.next();
            			if(aress.indexOf(key) != -1) {
            				areaId = area2IdNameMap.get(key);
            				break;
            			}
            		}
        		}
        	}
        	
        	//追加门店收获地址收藏信息
        	RecvInfo recvInfo = new RecvInfo();
        	recvInfo.setAddress(t.getAddress());
        	if(StringUtils.isNotEmpty(areaId)) {
        		recvInfo.setAreaId(Long.valueOf(areaId));
        	}
        	
        	if(StringUtils.isNotEmpty(t.getDeliveryName())) {
        		recvInfo.setConsignee(t.getDeliveryName());
            	recvInfo.setPhone(t.getPhone());
        	} else {
        		Receiver r = receiverMap.get(t.getMemberCode().toString());
            	if(r != null) {
            		recvInfo.setConsignee(r.getConsignee());
                	recvInfo.setPhone(r.getPhone());
            	}
        	}
        	recvInfo.setRecvId(recvSimpleID.nextId());
        	CommonMongo.appedRecvInfo(Constans.clientId, Long.valueOf(t.getMemberCode()), recvInfo);
        	receIdMap.put(t.getMemberCode().toString(), recvInfo.getRecvId());
        	return recvInfo.getRecvId();
    	}
    }*/
   
}
