package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.froad.common.beans.ResultBean;
import com.froad.db.mongo.page.MongoPage;
import com.froad.db.mysql.bean.Page;
import com.froad.db.redis.RedisCommon;
import com.froad.enums.DeliveryType;
import com.froad.enums.OperType;
import com.froad.enums.OrderStatus;
import com.froad.enums.OrderType;
import com.froad.enums.OrgLevelEnum;
import com.froad.enums.PaymentMethod;
import com.froad.enums.RefundState;
import com.froad.enums.ResultCode;
import com.froad.enums.SettlementStatus;
import com.froad.enums.SettlementType;
import com.froad.enums.ShippingStatus;
import com.froad.enums.SubOrderRefundState;
import com.froad.enums.SubOrderType;
import com.froad.enums.TicketStatus;
import com.froad.exceptions.FroadBusinessException;
import com.froad.logback.LogCvt;
import com.froad.logic.CommonLogic;
import com.froad.logic.OrderQueryLogic;
import com.froad.logic.RefundLogic;
import com.froad.logic.impl.order.OrderDataResult;
import com.froad.logic.impl.order.OrderLogger;
import com.froad.logic.impl.order.OrderQueryLogicHelper;
import com.froad.po.OrderQueryByBankCondition;
import com.froad.po.OrderQueryByBossCondition;
import com.froad.po.OrderQueryByMerchantPhoneCondition;
import com.froad.po.OrderQueryMerchantManageCondition;
import com.froad.po.Org;
import com.froad.po.Payment;
import com.froad.po.PresellShip;
import com.froad.po.QueryBoutiqueOrderByBankDto;
import com.froad.po.RecvInfo;
import com.froad.po.Ticket;
import com.froad.po.base.PageEntity;
import com.froad.po.base.PageEntity.OrderByType;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.mongo.ProductMongo;
import com.froad.po.mongo.ShippingOrderMongo;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.po.order.OrderConstants;
import com.froad.po.order.OrderExportData;
import com.froad.po.order.OrderOrgCode;
import com.froad.po.refund.RefundHistory;
import com.froad.po.settlement.Settlement;
import com.froad.support.OrderSupport;
import com.froad.support.PaymentSupport;
import com.froad.support.RefundSupport;
import com.froad.support.SettlementSupport;
import com.froad.support.TicketSupport;
import com.froad.support.impl.OrderSupportImpl;
import com.froad.support.impl.PaymentSupportImpl;
import com.froad.support.impl.RefundSupportImpl;
import com.froad.support.impl.SettlementSupportImpl;
import com.froad.support.impl.TicketSupportImpl;
import com.froad.thirdparty.util.DateUtil;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.order.DeliverInfoVo;
import com.froad.thrift.vo.order.GetOrderDetailByBankManageVoRes;
import com.froad.thrift.vo.order.GetOrderDetailByMerchantManageVoRes;
import com.froad.thrift.vo.order.GetOrderDetailByMerchantPhoneVoRes;
import com.froad.thrift.vo.order.GivePointsOrderVo;
import com.froad.thrift.vo.order.GivePointsProductVo;
import com.froad.thrift.vo.order.OrderDetailRes;
import com.froad.thrift.vo.order.OrderVo;
import com.froad.thrift.vo.order.ProductInfoVo;
import com.froad.thrift.vo.order.ProductVo;
import com.froad.thrift.vo.order.QueryBoutiqueOrderByBankManageVo;
import com.froad.thrift.vo.order.QueryBoutiqueOrderByBankManageVoReq;
import com.froad.thrift.vo.order.QueryGivePointsProductByBossRes;
import com.froad.thrift.vo.order.QueryOrderByBankManageVo;
import com.froad.thrift.vo.order.QueryOrderByBankManageVoReq;
import com.froad.thrift.vo.order.QueryOrderByMerchantManageVo;
import com.froad.thrift.vo.order.QueryOrderByMerchantManageVoReq;
import com.froad.thrift.vo.order.QueryOrderByMerchantPhoneVo;
import com.froad.thrift.vo.order.QueryRecvInfoForProductByBossRes;
import com.froad.thrift.vo.order.RecvInfoForProductVo;
import com.froad.thrift.vo.order.SubOrderVo;
import com.froad.util.Arith;
import com.froad.util.BeanUtil;
import com.froad.util.EmptyChecker;
import com.froad.util.JSonUtil;
import com.froad.util.order.HelpUtil;

public class OrderQueryLogicImpl implements OrderQueryLogic {

    private OrderSupport      orderSupport      = new OrderSupportImpl();

    private SettlementSupport settlementSupport = new SettlementSupportImpl();

    private CommonLogic       commonLogic       = new CommonLogicImpl();

    private PaymentSupport    payementSupport   = new PaymentSupportImpl();

    private TicketSupport     ticketSupport     = new TicketSupportImpl();

    private RefundLogic       refundLogic       = new RefundLogicImpl();
    
    private RefundSupport     refundSupport   	= new RefundSupportImpl();

    @Override
    public ResultBean queryOrderByFacetface(OrderQueryByMerchantPhoneCondition condition, PageVo page) throws FroadBusinessException {

        PageEntity<OrderQueryByMerchantPhoneCondition> pageEntity = new PageEntity<OrderQueryByMerchantPhoneCondition>();
        ResultBean rb = null;

        try {
            pageEntity.setCondition(condition);
            pageEntity.convert(page);

            pageEntity.setOrderByColumn("create_time"); // 设置排序字段
            pageEntity.setOrderByType(OrderByType.desc); // 排序方式
            

            MongoPage mongoPage = orderSupport.queryOrderByFacetfaceConditionOfPage(pageEntity);

            LogCvt.info("mongo返回数据" + JSonUtil.toJSonString(mongoPage));

            List<?> list = mongoPage.getItems();

            if (EmptyChecker.isEmpty(list)) {
                ResultCode result = ResultCode.member_order_query_face2face_empty;
                throw new FroadBusinessException(result.getCode(), result.getMsg());
            }

            List<QueryOrderByMerchantPhoneVo> resultList = new ArrayList<QueryOrderByMerchantPhoneVo>();
            convertPage(mongoPage, page);

            for (int i = 0; i < list.size(); i++) {
                resultList.add(convertFacetfaceMerchantPhoneVo((OrderMongo) list.get(i)));
            }

            rb = new ResultBean(ResultCode.success, resultList);
        } catch (FroadBusinessException e) {
            throw new FroadBusinessException(e.getCode(), e.getMsg());
        }
        return rb;
    }

    @Override
    public ResultBean queryOrderByGroup(OrderQueryByMerchantPhoneCondition condition, PageVo page) throws FroadBusinessException {

        PageEntity<OrderQueryByMerchantPhoneCondition> pageEntity = new PageEntity<OrderQueryByMerchantPhoneCondition>();
        ResultBean rb = null;

        pageEntity.setCondition(condition);
        pageEntity.convert(page);

        pageEntity.setOrderByColumn("create_time"); // 设置排序字段
        pageEntity.setOrderByType(OrderByType.desc); // 排序方式

        try {
            MongoPage mongo = orderSupport.queryBySubOrderByCondition(pageEntity);

            if (EmptyChecker.isEmpty(mongo.getItems())) {
                ResultCode result = ResultCode.member_suborder_query_empty;
                throw new FroadBusinessException(result.getCode(), result.getMsg());
            }

            List<QueryOrderByMerchantPhoneVo> resultList = new ArrayList<QueryOrderByMerchantPhoneVo>();
            convertPage(mongo, page);

            List<?> list = mongo.getItems();

            for (int i = 0; i < list.size(); i++) {
                resultList.add(convertSubOrder((SubOrderMongo) list.get(i)));
            }
            rb = new ResultBean(ResultCode.success, resultList);
        } catch (FroadBusinessException e) {
            throw new FroadBusinessException(e.getCode(), e.getMsg(), e);
        }

        return rb;
    }

    @Override
    public ResultBean getFacetfaceOrderDetailByOrderId(String clientId, String orderId) throws FroadBusinessException {

        ResultBean rb = new ResultBean(ResultCode.success);

        try {
            OrderMongo mongo = orderSupport.getOrderById(clientId, orderId);
            if (EmptyChecker.isEmpty(mongo)) {
                ResultCode result = ResultCode.member_order_query_face2face_empty;
                throw new FroadBusinessException(result.getCode(), result.getMsg());
            }

            GetOrderDetailByMerchantPhoneVoRes res = convertOrderDetail(mongo);
            rb = new ResultBean(ResultCode.success, res);
        } catch (FroadBusinessException e) {
            throw new FroadBusinessException(e.getCode(), e.getMsg());
        }
        return rb;
    }

    @Override
    public ResultBean getSubOrderDetailByOrderIdAndType(String clientId, String subOrderId) throws FroadBusinessException {

        ResultBean rb = new ResultBean(ResultCode.success);

        try { 
            // 查询子订单
            SubOrderMongo subOrder = orderSupport.getSubOrderBySubOrderId(clientId, subOrderId);
            if (EmptyChecker.isEmpty(subOrder)) {
                ResultCode result = ResultCode.member_suborder_query_empty;
                throw new FroadBusinessException(result.getCode(), result.getMsg());
            }
            // 查询订单
            OrderMongo order = orderSupport.getOrderById(clientId, subOrder.getOrderId());
            if (EmptyChecker.isEmpty(order)) {
                ResultCode result = ResultCode.member_order_is_empty;
                throw new FroadBusinessException(result.getCode(), result.getMsg());
            }

            rb = new ResultBean(ResultCode.success, convertSubOrderDetail(order, subOrder));
        } catch (FroadBusinessException e) {
            throw new FroadBusinessException(e.getCode(), e.getMsg());
        }

        return rb;
    }
    
    @Override
    public ResultBean queryBoutiqueSubOrderByBank(QueryBoutiqueOrderByBankDto reqDto, PageVo pageVo) throws FroadBusinessException {
    	ResultBean rb = new ResultBean(ResultCode.success);
    	PageEntity<QueryBoutiqueOrderByBankDto> reqPage = new PageEntity<QueryBoutiqueOrderByBankDto>();
		reqPage.setCondition(reqDto);
		reqPage.convert(pageVo);
		reqPage.setOrderByColumn("create_time"); // 设置排序字段
		reqPage.setOrderByType(OrderByType.desc); // 排序方式

        MongoPage page = orderSupport.queryBoutiqueSubOrderByBank(reqPage);
        convertPage(page, pageVo);
        //一次查询大订单组合为Map
        List<SubOrderMongo> subOrderMongoList = (List<SubOrderMongo>) page.getItems();
        Map<String,OrderMongo> orderMap = getOrderListMapBySubOrderList(reqDto.getClientId(),subOrderMongoList);

        List<QueryBoutiqueOrderByBankManageVo> voList = new ArrayList<QueryBoutiqueOrderByBankManageVo>();
        if(EmptyChecker.isNotEmpty(page.getItems())){
        	for (int i = 0; i < page.getItems().size(); i++) {
            	SubOrderMongo subOrder = (SubOrderMongo)page.getItems().get(i);
            	QueryBoutiqueOrderByBankManageVo vo = new QueryBoutiqueOrderByBankManageVo();
            	OrderMongo orderMongo = orderMap.get(subOrder.getClientId()+"_"+ subOrder.getOrderId());
                //vo.setSubOrderId(subOrder.getSubOrderId());//TODO
                vo.setSubOrderId(subOrder.getOrderId());//TODO
                vo.setProviderName(subOrder.getMerchantName());
                //商品名
                List<String> proNameList = new ArrayList<String>();
                //子订单总金额
                double subTotalMoney = 0;
                for (ProductMongo product : subOrder.getProducts()) { 
                	proNameList.add(product.getProductName());
                	subTotalMoney = Arith.add(subTotalMoney, getSubTotalMoney(product));
                }
                vo.setProductNames(org.apache.commons.lang.StringUtils.join(proNameList.toArray(), ","));
                vo.setSubTotalMoney(subTotalMoney);
                vo.setPaymentMethod(orderMongo.getPaymentMethod());
                vo.setOrderStatus(subOrder.getOrderStatus());
                vo.setCreateTime(subOrder.getCreateTime());
                voList.add(vo);
            }
        }
        
        rb = new ResultBean(ResultCode.success, voList);
        return rb;
    }
    

    @Override
    public ResultBean querySubOrderByBank(OrderQueryByBankCondition condition, PageVo pageVo) throws FroadBusinessException {
        ResultBean rb = new ResultBean(ResultCode.success);
        try {
            PageEntity<OrderQueryByBankCondition> pageEntity = new PageEntity<OrderQueryByBankCondition>();
            if (EmptyChecker.isNotEmpty(condition) && EmptyChecker.isNotEmpty(condition.getType()) 
            		&& SubOrderType.presell_org.getCode().equals(condition.getType()) 
            		&& EmptyChecker.isEmpty(condition.getDeliveryOption())) {
                LogCvt.error("查询子订单列表参数错误" + JSonUtil.toJSonString(pageEntity));
                ResultCode code = ResultCode.member_order_type_deliveryOption_unknow;
                throw new FroadBusinessException(code.getCode(), code.getMsg());
            }
            // 判断机构和机构等级
            if (EmptyChecker.isNotEmpty(condition.getOrgCode()) && EmptyChecker.isNotEmpty(condition.getClientId())) {
            	//机构号支持多机构查询（2015-10-30 需求1.5.0 ）
            	OrderOrgCode orderOrgCode = stringToCodeObject(condition.getOrgCode(),condition.getMyOrgCode(), condition.getClientId());
            	condition.setOrderOrgCode(orderOrgCode);
            	
                /*long st = System.currentTimeMillis();
                OrgLevelEnum level = commonLogic.getOrgLevelByOrgCode(condition.getOrgCode(), condition.getClientId());
                LogCvt.info("查询结构级别耗时：(" + (System.currentTimeMillis()-st) + ")毫秒");
                Integer onelevel = null;
                Integer twolevel = null;
                condition.setOrgCodeFlag("orgCode");//设置默认的最低级别机构是选择的机构
                if(EmptyChecker.isNotEmpty(condition.getMyOrgCode())){
                	OrgLevelEnum myLevel = commonLogic.getOrgLevelByOrgCode(condition.getMyOrgCode(), condition.getClientId());  
                	if(OrgLevelEnum.orgLevel_one.equals(level)) {
                		onelevel = new Integer(level.orgLevel_one.getLevel());
                    } else if(OrgLevelEnum.orgLevel_two.equals(level)) {
                    	onelevel = new Integer(level.orgLevel_two.getLevel());
                    } else if(OrgLevelEnum.orgLevel_three.equals(level)) {
                    	onelevel = new Integer(level.orgLevel_three.getLevel());
                    } else if(OrgLevelEnum.orgLevel_four.equals(level)) {
                    	onelevel = new Integer(level.orgLevel_four.getLevel());
                    }
                	if(OrgLevelEnum.orgLevel_one.equals(myLevel)) {
                		twolevel = new Integer(myLevel.orgLevel_one.getLevel());
                    } else if(OrgLevelEnum.orgLevel_two.equals(myLevel)) {
                    	twolevel = new Integer(myLevel.orgLevel_two.getLevel());
                    } else if(OrgLevelEnum.orgLevel_three.equals(myLevel)) {
                    	twolevel = new Integer(myLevel.orgLevel_three.getLevel());
                    } else if(OrgLevelEnum.orgLevel_four.equals(myLevel)) {
                    	twolevel = new Integer(myLevel.orgLevel_four.getLevel());
                    }
                	if(onelevel!=null && twolevel!=null){
                		if(onelevel > twolevel){
                			condition.setMinOrgLevel(level);
                		}else if(onelevel == twolevel){
                			condition.setMinOrgLevel(level);
                		}else{
                			condition.setMinOrgLevel(myLevel);
                			condition.setOrgCodeFlag("myOrgCode");
                		}
                	}else if(myLevel==null){
                		condition.setMinOrgLevel(level);
                	}
                }
                condition.setOrgLevel(level);*/
            }

            pageEntity.setCondition(condition);
            pageEntity.convert(pageVo);
            pageEntity.setOrderByColumn("create_time"); // 设置排序字段
            pageEntity.setOrderByType(OrderByType.desc); // 排序方式

            MongoPage page = orderSupport.querySubOrderByBank(pageEntity);

            if (EmptyChecker.isEmpty(page) || EmptyChecker.isEmpty(page.getItems())) {
                LogCvt.error("查询子订单列表失败" + JSonUtil.toJSonString(pageEntity));
                ResultCode code = ResultCode.member_suborder_query_empty;
                throw new FroadBusinessException(code.getCode(), code.getMsg());
            }

            convertPage(page, pageVo);
            
            //一次查询大订单组合为Map
            List<SubOrderMongo> subOrderMongoList = (List<SubOrderMongo>) page.getItems();
            Map<String,OrderMongo> orderMap = getOrderListMapBySubOrderList(condition.getClientId(),subOrderMongoList);

            List<QueryOrderByBankManageVo> list = new ArrayList<QueryOrderByBankManageVo>();

            for (int i = 0; i < page.getItems().size(); i++) {
                QueryOrderByBankManageVo vo = convertQueryOrderByBankManageVo((SubOrderMongo) page.getItems().get(i),orderMap,condition.getDeliveryOption());
                list.add(vo);
            }
            rb = new ResultBean(ResultCode.success, list);
        } catch (FroadBusinessException e) {
            throw new FroadBusinessException(e.getCode(), e.getMsg(), e);
        }
        return rb;
    }
    
    
    public ResultBean queryBoutiqueSubOrderByBankForExport(QueryBoutiqueOrderByBankDto reqDto, PageVo pageVo) throws FroadBusinessException {
    	ResultBean rb = new ResultBean(ResultCode.success);
    	PageEntity<QueryBoutiqueOrderByBankDto> pageEntity = new PageEntity<QueryBoutiqueOrderByBankDto>();
    	 
        pageEntity.setCondition(reqDto);
        pageEntity.convert(pageVo);
        pageEntity.setOrderByColumn("create_time"); // 设置排序字段
        pageEntity.setOrderByType(OrderByType.desc); // 排序方式
        MongoPage page = orderSupport.queryBoutiqueSubOrderByBank(pageEntity);
    	
        List<QueryBoutiqueOrderByBankManageVo> list = new ArrayList<QueryBoutiqueOrderByBankManageVo>();
        if(EmptyChecker.isEmpty(page) || EmptyChecker.isEmpty(page.getItems())) {
            LogCvt.info("精品商城订单导出，查询子订单列表为空");
        } else {
        	 convertPage(page, pageVo);
        	 //一次查询大订单组合为Map
             List<SubOrderMongo> subOrderMongoList = (List<SubOrderMongo>) page.getItems();
             Map<String,OrderMongo> orderMap = getOrderListMapBySubOrderList(reqDto.getClientId(),subOrderMongoList);
             for (int i = 0; i < page.getItems().size(); i++) {
            	 QueryBoutiqueOrderByBankManageVo vo = getBoutiqueSubOrderByBankManageVo((SubOrderMongo) page.getItems().get(i),orderMap);
                 list.add(vo);
             }
        }
    	
        rb = new ResultBean(ResultCode.success, list);
    	return rb;
    }
    
    
   private QueryBoutiqueOrderByBankManageVo getBoutiqueSubOrderByBankManageVo(SubOrderMongo subOrder, Map<String, OrderMongo> orderMap) throws FroadBusinessException {
	   QueryBoutiqueOrderByBankManageVo vo = new QueryBoutiqueOrderByBankManageVo();
	   //vo.setSubOrderId(subOrder.getSubOrderId());
	   vo.setSubOrderId(subOrder.getOrderId());
	   vo.setProviderName(subOrder.getMerchantName());
	   
	   double subTotalMoney = 0;
       int quantity = 0; //子订单商品数
       int settlementNum = 0;//已结算数量
       List<ProductVo> productList = new ArrayList<ProductVo>();
       
       for(ProductMongo product : subOrder.getProducts()) {
    	   ProductVo productVo = new ProductVo();
    	   int count = EmptyChecker.isEmpty(product.getQuantity())? 0 : product.getQuantity();
           int vipCount = EmptyChecker.isEmpty(product.getVipQuantity())? 0 : product.getVipQuantity();
           
           productVo.setProductName(product.getProductName());
           productVo.setQuantity(count);
           productVo.setVipQuantity(vipCount);
           productVo.setVipMoney(product.getVipMoney());
           productVo.setMoney(product.getMoney());
           
           subTotalMoney = Arith.add(subTotalMoney, getSubTotalMoney(product));
           quantity += count + vipCount;
           productList.add(productVo);
       }
	   
       vo.setProductList(productList);
       // 从大订单中获取订单状态和购买会员名
       OrderMongo orderMongo = orderMap.get(subOrder.getClientId()+"_"+ subOrder.getOrderId());
       if (EmptyChecker.isEmpty(orderMongo)) {
           LogCvt.error("查询大订单数据为空， 大订单号为：" + subOrder.getOrderId());
           ResultCode resultCode = ResultCode.member_order_is_empty;
           throw new FroadBusinessException(resultCode.getCode(), resultCode.getMsg());
       }
       //订单状态
       vo.setOrderStatus(subOrder.getOrderStatus());
       // 显示子订单金额
       vo.setSubTotalMoney(subTotalMoney); 
       // 支付方式
       vo.setPaymentMethod(orderMongo.getPaymentMethod()); 
       // 创建时间
       vo.setCreateTime(subOrder.getCreateTime()); 
       return vo;
   }
    
    
    @Override
    public ResultBean querySubOrderByBankForExport(OrderQueryByBankCondition condition, PageVo pageVo) throws FroadBusinessException {
        ResultBean rb = new ResultBean(ResultCode.success);
        try {
            PageEntity<OrderQueryByBankCondition> pageEntity = new PageEntity<OrderQueryByBankCondition>();
            // 判断机构和机构等级
            if (EmptyChecker.isNotEmpty(condition.getOrgCode()) && EmptyChecker.isNotEmpty(condition.getClientId())) {
                OrgLevelEnum level = commonLogic.getOrgLevelByOrgCode(condition.getOrgCode(), condition.getClientId());
                condition.setOrgLevel(level);
            }
            
            // 判断机构和机构等级
            if (EmptyChecker.isNotEmpty(condition.getOrgCode()) && EmptyChecker.isNotEmpty(condition.getClientId())) {
            	//机构号支持多机构查询（2015-10-30 需求1.5.0 ）
            	OrderOrgCode orderOrgCode = stringToCodeObject(condition.getOrgCode(),condition.getMyOrgCode(), condition.getClientId());
            	condition.setOrderOrgCode(orderOrgCode);
            }
            
            pageEntity.setCondition(condition);
            pageEntity.convert(pageVo);
            pageEntity.setOrderByColumn("create_time"); // 设置排序字段
            pageEntity.setOrderByType(OrderByType.desc); // 排序方式
            MongoPage page = orderSupport.querySubOrderByBank(pageEntity);

            List<QueryOrderByBankManageVo> list = new ArrayList<QueryOrderByBankManageVo>();
            
            if (EmptyChecker.isEmpty(page) || EmptyChecker.isEmpty(page.getItems())) {
                LogCvt.info("订单导出，查询子订单列表为空");
                /*ResultCode code = ResultCode.member_suborder_query_empty;
                throw new FroadBusinessException(code.getCode(), code.getMsg());*/
            }else{
            	convertPage(page, pageVo);
                
                //一次查询大订单组合为Map
                List<SubOrderMongo> subOrderMongoList = (List<SubOrderMongo>) page.getItems();
                Map<String,OrderMongo> orderMap = getOrderListMapBySubOrderList(condition.getClientId(),subOrderMongoList);
                
                //查询结算记录      bug3426-结算状态去掉
                /*List<String> subOrderIdList = getSubOrderIdList(subOrderMongoList);
                Map<String,Integer> settlementMap = orderSupport.getSettlementBySubOrderIdList(subOrderIdList);*/

                //名优特惠-收货地址需详细省市区
                boolean isNeedAreaDetail = false;
                if(condition.getType().equals(OrderType.special_merchant.getCode())){//名优特惠
                	isNeedAreaDetail = true;
                }
                //查询收货信息-名优特惠、精品预售
                Map<String,RecvInfo> recvMap = null;
                if(condition.getType().equals(OrderType.special_merchant.getCode()) || condition.getType().equals(OrderType.presell_org.getCode())){
                	recvMap = orderSupport.getRecvInfoMap(subOrderMongoList,isNeedAreaDetail);
                }
                
                //提货信息、机构信息
                Map<String,String> takeStatusMap = null;
                //提货网点信息
                Map<String,String> orgMap = null;
                if((condition.getType().equals(OrderType.presell_org.getCode()) && condition.getDeliveryOption().equals(DeliveryType.take.getCode())) || (condition.getType().equals(OrderType.offline_points_org.getCode()))){//预售自提或线下积分兑换
                	takeStatusMap = getTicketTakeState(subOrderMongoList,condition.getDeliveryOption());
                	//预售取提货网点（二级-三级）
                	orgMap = getPresellOrgMap(subOrderMongoList);
                }else{
                	orgMap = getSubOrderOrgMap(subOrderMongoList);
                }
                
                //配送类型
                String deliveryOption = null;
                if(condition.getType().equals(OrderType.presell_org.getCode())){
                	if(condition.getDeliveryOption().equals(DeliveryType.take.getCode())){//预售自提
                		deliveryOption = DeliveryType.take.getCode();
                	}
                	if(condition.getDeliveryOption().equals(DeliveryType.home.getCode())){//预售配送
                		deliveryOption = DeliveryType.home.getCode();
                	}
                }

                for (int i = 0; i < page.getItems().size(); i++) {
                    QueryOrderByBankManageVo vo = getSubOrderByBankManageVo((SubOrderMongo) page.getItems().get(i),orderMap,null,recvMap,takeStatusMap,orgMap,deliveryOption);
                    list.add(vo);
                }
            }
            
            rb = new ResultBean(ResultCode.success, list);
        } catch (FroadBusinessException e) {
            throw new FroadBusinessException(e.getCode(), e.getMsg(), e);
        }
        return rb;
    }
    
    private Map<String,OrderMongo> getOrderListMapBySubOrderList(String clientId,List<SubOrderMongo> subOrderMongoList){
    	Map<String,OrderMongo> orderMap = new HashMap<String,OrderMongo>();
        if(EmptyChecker.isNotEmpty(subOrderMongoList)){
        	List<String> orderIdList = new ArrayList<String>();
        	for(SubOrderMongo subOrder : subOrderMongoList){
        		orderIdList.add(subOrder.getOrderId());
        	}
        	List<OrderMongo> orderMongoList = orderSupport.getOrderListByOrderIdList(clientId,orderIdList);
        	if(EmptyChecker.isNotEmpty(orderMongoList)){
            	for(OrderMongo order : orderMongoList){
            		orderMap.put(order.getClientId()+"_"+order.getOrderId(), order);
            	}
        	}
        }
        return orderMap;
    }

    @Override
    public ResultBean queryOrderOfFacetfacByBank(OrderQueryByBankCondition condition, PageVo pageVo) throws FroadBusinessException {
        // 面对面
        ResultBean rb = new ResultBean(ResultCode.success);

        try {
            PageEntity<OrderQueryByBankCondition> pageEntity = new PageEntity<OrderQueryByBankCondition>();
            
            // 判断机构和机构等级（新增2015-05-12）
            if (EmptyChecker.isNotEmpty(condition.getOrgCode()) && EmptyChecker.isNotEmpty(condition.getClientId())) {
            	//机构号支持多机构查询（2015-10-30 需求1.5.0 ）
            	OrderOrgCode orderOrgCode = stringToCodeObject(condition.getOrgCode(), condition.getClientId());
            	condition.setOrderOrgCode(orderOrgCode);
            	
                /*long st = System.currentTimeMillis();
                OrgLevelEnum level = commonLogic.getOrgLevelByOrgCode(condition.getOrgCode(), condition.getClientId());
                LogCvt.info("查询机构级别耗时：(" + (System.currentTimeMillis() - st) + ")毫秒");
                condition.setOrgLevel(level);*/
            }

            pageEntity.setCondition(condition);
            pageEntity.convert(pageVo);

            pageEntity.setOrderByColumn("create_time"); // 设置排序字段
            pageEntity.setOrderByType(OrderByType.desc); // 排序方式

            MongoPage page = orderSupport.queryOrderOfFacetfaceByBank(pageEntity);

            if (EmptyChecker.isEmpty(page) || EmptyChecker.isEmpty(page.getItems())) {
                ResultCode code = ResultCode.member_order_is_empty;
                throw new FroadBusinessException(code.getCode(), code.getMsg());
            }

            convertPage(page, pageVo);

            List<QueryOrderByBankManageVo> list = new ArrayList<QueryOrderByBankManageVo>();

            for (int i = 0; i < page.getItems().size(); i++) {
                QueryOrderByBankManageVo vo = convertQueryOrderByBankManageVo((OrderMongo) page.getItems().get(i),null,null,null);
                list.add(vo);
            }

            rb = new ResultBean(ResultCode.success, list);
        } catch (FroadBusinessException e) {
            throw new FroadBusinessException(e.getCode(), e.getMsg());
        }

        return rb;
    }
    
    /**
     * orgCode以逗号分隔
     * @param orgCode
     * @return
     */
    public OrderOrgCode stringToCodeObject(String orgCode,String clientId){
    	if(EmptyChecker.isEmpty(orgCode) || EmptyChecker.isEmpty(clientId)){
    		return null;
    	}
    	OrderOrgCode orderOrgCode = new OrderOrgCode();
    	String[] orgCodes = orgCode.split(",");
    	List<String> forgCodeList = new ArrayList<String>();
    	List<String> sorgCodeList = new ArrayList<String>();
    	List<String> torgCodeList = new ArrayList<String>();
    	List<String> lorgCodeList = new ArrayList<String>();
    	for(int i = 0; i<orgCodes.length;i++){
    		if(EmptyChecker.isNotEmpty(orgCodes[i])){
    			OrgLevelEnum level = commonLogic.getOrgLevelByOrgCode(orgCodes[i], clientId);
    			if (EmptyChecker.isNotEmpty(level)) {
    	            if (OrgLevelEnum.orgLevel_one.equals(level)) {
    	                forgCodeList.add(orgCodes[i]);
    	            } else if (OrgLevelEnum.orgLevel_two.equals(level)) {
    	            	sorgCodeList.add(orgCodes[i]);
    	            } else if (OrgLevelEnum.orgLevel_three.equals(level)) {
    	            	torgCodeList.add(orgCodes[i]);
    	            } else if (OrgLevelEnum.orgLevel_four.equals(level)) {
    	            	lorgCodeList.add(orgCodes[i]);
    	            }
    	        }
    		}
    	}
    	if(EmptyChecker.isNotEmpty(forgCodeList)){
    		orderOrgCode.setForgCodeList(forgCodeList);
    	}
    	if(EmptyChecker.isNotEmpty(sorgCodeList)){
    		orderOrgCode.setSorgCodeList(sorgCodeList);
    	}
    	if(EmptyChecker.isNotEmpty(torgCodeList)){
    		orderOrgCode.setTorgCodeList(torgCodeList);
    	}
    	if(EmptyChecker.isNotEmpty(lorgCodeList)){
    		orderOrgCode.setLorgCodeList(lorgCodeList);
    	}
    	return orderOrgCode;
    }
    
    /**
     * orgCode以逗号分隔
     * @param orgCode 查询条件机构号
     * @param loginOrgCode 当前登录人机构号
     * @return
     */
    public OrderOrgCode stringToCodeObject(String orgCode,String loginOrgCode,String clientId){
    	if(EmptyChecker.isEmpty(orgCode) || EmptyChecker.isEmpty(clientId)){
    		return null;
    	}
    	OrgLevelEnum loginLevel = null;
    	if(EmptyChecker.isNotEmpty(loginOrgCode)){
    		loginLevel = commonLogic.getOrgLevelByOrgCode(loginOrgCode, clientId);
    	}
    	
    	OrderOrgCode orderOrgCode = new OrderOrgCode();
    	String[] orgCodes = orgCode.split(",");
    	List<String> forgCodeList = new ArrayList<String>();
    	List<String> sorgCodeList = new ArrayList<String>();
    	List<String> torgCodeList = new ArrayList<String>();
    	List<String> lorgCodeList = new ArrayList<String>();
    	for(int i = 0; i<orgCodes.length;i++){
    		if(EmptyChecker.isNotEmpty(orgCodes[i])){
    			OrgLevelEnum level = commonLogic.getOrgLevelByOrgCode(orgCodes[i], clientId);
    			if (EmptyChecker.isNotEmpty(level)) {
    				String queryOrgCode = orgCodes[i];
    				if(EmptyChecker.isNotEmpty(loginLevel)){
    					if(Integer.valueOf(loginLevel.getLevel())> Integer.valueOf(level.getLevel())){
    						level = loginLevel;
    						queryOrgCode = loginOrgCode;
    					}
    				}
    	            if (OrgLevelEnum.orgLevel_one.equals(level) && !forgCodeList.contains(queryOrgCode)) {
    	                forgCodeList.add(queryOrgCode);
    	            } else if (OrgLevelEnum.orgLevel_two.equals(level) && !sorgCodeList.contains(queryOrgCode)) {
    	            	sorgCodeList.add(queryOrgCode);
    	            } else if (OrgLevelEnum.orgLevel_three.equals(level) && !torgCodeList.contains(queryOrgCode)) {
    	            	torgCodeList.add(queryOrgCode);
    	            } else if (OrgLevelEnum.orgLevel_four.equals(level) && !lorgCodeList.contains(queryOrgCode)) {
    	            	lorgCodeList.add(queryOrgCode);
    	            }
    	        }
    		}
    	}
    	if(EmptyChecker.isNotEmpty(forgCodeList)){
    		orderOrgCode.setForgCodeList(forgCodeList);
    	}
    	if(EmptyChecker.isNotEmpty(sorgCodeList)){
    		orderOrgCode.setSorgCodeList(sorgCodeList);
    	}
    	if(EmptyChecker.isNotEmpty(torgCodeList)){
    		orderOrgCode.setTorgCodeList(torgCodeList);
    	}
    	if(EmptyChecker.isNotEmpty(lorgCodeList)){
    		orderOrgCode.setLorgCodeList(lorgCodeList);
    	}
    	return orderOrgCode;
    }
    
    @Override
    public ResultBean queryQrOrderByBankForExport(OrderQueryByBankCondition condition, PageVo pageVo) throws FroadBusinessException {
        // 面对面
		ResultBean rb = new ResultBean(ResultCode.success);
		PageEntity<OrderQueryByBankCondition> pageEntity = new PageEntity<OrderQueryByBankCondition>();
		// 判断机构和机构等级（新增2015-05-12）
		if (EmptyChecker.isNotEmpty(condition.getOrgCode()) && EmptyChecker.isNotEmpty(condition.getClientId())) {
			//机构号支持多机构查询（2015-10-30 需求1.5.0 ）
        	OrderOrgCode orderOrgCode = stringToCodeObject(condition.getOrgCode(), condition.getClientId());
        	condition.setOrderOrgCode(orderOrgCode);
        	
			/*OrgLevelEnum level = commonLogic.getOrgLevelByOrgCode(condition.getOrgCode(), condition.getClientId());
			condition.setOrgLevel(level);*/
		}
		
		pageEntity.setCondition(condition);
		pageEntity.convert(pageVo);
		pageEntity.setOrderByColumn("create_time"); // 设置排序字段
		pageEntity.setOrderByType(OrderByType.desc); // 排序方式

		MongoPage page = orderSupport.queryOrderOfFacetfaceByBank(pageEntity);

		List<QueryOrderByBankManageVo> list = new ArrayList<QueryOrderByBankManageVo>();
		if (EmptyChecker.isEmpty(page) || EmptyChecker.isEmpty(page.getItems())) {
			LogCvt.info("面对面订单导出，查询大订单数据为空");
		} else {
			convertPage(page, pageVo);

			List<OrderMongo> orderMongoList = (List<OrderMongo>) page.getItems();

			// 结算信息
			List<String> orderIdList = getOrderIdList(orderMongoList);
			Map<String, String> settlementMap = orderSupport.getSettlementByOrderIdList(orderIdList,true);

			// 机构信息
			Map<String, String> orgMap = getOrgMap(orderMongoList);
			
			//操作人
			Map<Long, String> userMap = getMerchantUserNameForQrorder(orderMongoList);

			for (int i = 0; i < page.getItems().size(); i++) {
				QueryOrderByBankManageVo vo = convertQueryOrderByBankManageVo(orderMongoList.get(i), settlementMap, orgMap,userMap);
				list.add(vo);
			}
		}

		rb = new ResultBean(ResultCode.success, list);

		return rb;
    }
    
    /**
     * 获取机构信息       机构号 ： 机构名
     * @param list
     * @return  map  机构号 ： 机构名
     */
    public Map<String,String> getOrgMap(List<OrderMongo> list){
    	long st = System.currentTimeMillis();
    	
    	List<String> orgCodeList = new ArrayList<String>();
    	String clientId = null;
    	if(EmptyChecker.isNotEmpty(list)){
    		clientId = list.get(0).getClientId();
    		for(OrderMongo order : list){
		        if (EmptyChecker.isNotEmpty(order.getForgCode())) {
		        	if(!orgCodeList.contains(order.getForgCode())){
		        		orgCodeList.add(order.getForgCode());
		        	}
		        }
		        if (EmptyChecker.isNotEmpty(order.getSorgCode())) {
		            if(!orgCodeList.contains(order.getSorgCode())){
		        		orgCodeList.add(order.getSorgCode());
		        	}
		        }
		        if (EmptyChecker.isNotEmpty(order.getTorgCode())) {
		        	if(!orgCodeList.contains(order.getTorgCode())){
		        		orgCodeList.add(order.getTorgCode());
		        	}
		        }
		        if (EmptyChecker.isNotEmpty(order.getLorgCode())) {
		            if(!orgCodeList.contains(order.getLorgCode())){
		        		orgCodeList.add(order.getLorgCode());
		        	}
		        }
    		}
    	}
    	
        List<Org> orgList = commonLogic.getOrgByList(clientId, orgCodeList);
        
        Map<String,String> map = new HashMap<String,String>();
        if (EmptyChecker.isNotEmpty(orgList)) {
            for (Org org : orgList) {
            	map.put(org.getOrgCode(), org.getOrgName());
            }
        }
        LogCvt.info("关联查询"+list.size()+"个订单的"+orgList.size()+"个机构信息，耗时：" + (System.currentTimeMillis() - st));
        return map;
    }
    
    
    /**
     * 获取机构信息       机构号 ： 机构名
     * @param list
     * @return  map  机构号 ： 机构名
     */
    public Map<String,String> getSubOrderOrgMap(List<SubOrderMongo> list){
    	long st = System.currentTimeMillis();
    	
    	List<String> orgCodeList = new ArrayList<String>();
    	String clientId = null;
    	if(EmptyChecker.isNotEmpty(list)){
    		clientId = list.get(0).getClientId();
    		for(SubOrderMongo order : list){
		        if (EmptyChecker.isNotEmpty(order.getForgCode())) {
		        	if(!orgCodeList.contains(order.getForgCode())){
		        		orgCodeList.add(order.getForgCode());
		        	}
		        }
		        if (EmptyChecker.isNotEmpty(order.getSorgCode())) {
		            if(!orgCodeList.contains(order.getSorgCode())){
		        		orgCodeList.add(order.getSorgCode());
		        	}
		        }
		        if (EmptyChecker.isNotEmpty(order.getTorgCode())) {
		        	if(!orgCodeList.contains(order.getTorgCode())){
		        		orgCodeList.add(order.getTorgCode());
		        	}
		        }
		        if (EmptyChecker.isNotEmpty(order.getLorgCode())) {
		            if(!orgCodeList.contains(order.getLorgCode())){
		        		orgCodeList.add(order.getLorgCode());
		        	}
		        }
    		}
    	}
    	
        List<Org> orgList = commonLogic.getOrgByList(clientId, orgCodeList);
        
        Map<String,String> map = new HashMap<String,String>();
        if (EmptyChecker.isNotEmpty(orgList)) {
            for (Org org : orgList) {
            	map.put(org.getOrgCode(), org.getOrgName());
            }
        }
        LogCvt.info("关联查询"+list.size()+"个订单的"+orgList.size()+"个机构信息，耗时：" + (System.currentTimeMillis() - st));
        return map;
    }
    
    /**
     * 获取机构信息       机构号 ： 二级机构名-三级机构名
     * @param list
     * @return  map  机构号 ： 二级机构名-三级机构名
     */
    public Map<String,String> getPresellOrgMap(List<SubOrderMongo> list){
    	long st = System.currentTimeMillis();
    	
    	//取商品下的所有提货网点机构号
    	List<String> orgCodeList = new ArrayList<String>();
    	String clientId = null;
    	if(EmptyChecker.isNotEmpty(list)){
    		clientId = list.get(0).getClientId();
    		for(SubOrderMongo order : list){
		        if (EmptyChecker.isNotEmpty(order.getProducts())) {
		        	for(ProductMongo product : order.getProducts()){
		        		if(StringUtils.equals(order.getType(), SubOrderType.presell_org.getCode())){
		        			if(StringUtils.equals(product.getDeliveryOption(),DeliveryType.take.getCode())){
			        			if(!orgCodeList.contains(product.getOrgCode())){
					        		orgCodeList.add(product.getOrgCode());
					        	}
			        		}
		        		}
		        		if(StringUtils.equals(order.getType(), SubOrderType.offline_points_org.getCode())){
		        			if(!orgCodeList.contains(product.getOrgCode())){
				        		orgCodeList.add(product.getOrgCode());
				        	}
		        		}
		        	}
		        	
		        }
    		}
    	}
    	
    	//根据机构号取提货网点（三级）机构信息
        List<Org> orgList = commonLogic.getOrgByList(clientId, orgCodeList);
        
        //取所有网点的二级机构号
        List<String> superOrgCodeList = new ArrayList<String>();
        if (EmptyChecker.isNotEmpty(orgList)) {
            for (Org org : orgList) {
            	if(!superOrgCodeList.contains(org.getCityAgency())){
            		superOrgCodeList.add(org.getCityAgency());
	        	}
            }
        }
        
        //根据机构号取二级机构信息
        List<Org> superOrgList = commonLogic.getOrgByList(clientId, superOrgCodeList);
        
        Map<String,String> superMap = new HashMap<String,String>();
        if (EmptyChecker.isNotEmpty(superOrgList)) {
            for (Org org : superOrgList) {
            	superMap.put(org.getOrgCode(), org.getOrgName());
            }
        }
        
        Map<String,String> map = new HashMap<String,String>();
        if (EmptyChecker.isNotEmpty(orgList)) {
            for (Org org : orgList) {
            	StringBuffer superOrgName = new StringBuffer();
            	if(EmptyChecker.isNotEmpty(superMap) && EmptyChecker.isNotEmpty(superMap.get(org.getCityAgency()))){
            		superOrgName.append(superMap.get(org.getCityAgency()));
            		superOrgName.append("-");
            	}else{
            		LogCvt.error("预售提货网点没有找到二级机构信息，网点机构号："+org.getOrgCode()+"，二级机构号："+org.getCityAgency());
            	}
            	map.put(org.getOrgCode(), superOrgName.append(org.getOrgName()).toString());
            }
        }
        
        LogCvt.info("关联查询"+list.size()+"个子订单的"+orgList.size()+"个机构信息，耗时：" + (System.currentTimeMillis() - st));
        return map;
    }
    
    public Map<Long,String> getMerchantUserNameForQrorder(List<OrderMongo> list){
    	Map<Long,String> merchantUserNameMap = new HashMap<Long,String>();
		List<Long> merchantUserIdList = new ArrayList<Long>();
    	if(EmptyChecker.isNotEmpty(list)){
    		for(OrderMongo vo : list){
    			if(!merchantUserIdList.contains(vo.getMerchantUserId())){
    				merchantUserIdList.add(vo.getMerchantUserId());
    			}
    		}
    	}
		merchantUserNameMap = getMerchantUserNameMap(merchantUserIdList);
		if(EmptyChecker.isEmpty(merchantUserNameMap)){
    		LogCvt.error("[数据错误]-警告!!!面对面订单导出-商户用户名查询结果为空！");
    	}
		return merchantUserNameMap;
    }
    
	@Override
	public Map<Long,String> getMerchantUserNameMap(List<Long> merchantUserIdList){
		long st = System.currentTimeMillis();
		Map<Long,String> merchantUserNameMap = orderSupport.findMerchantUserNameByIdList(merchantUserIdList);
		LogCvt.info("根据商户用户ID查询商户用户名，耗时："+(System.currentTimeMillis() - st)+"ms");
		return merchantUserNameMap;
    }

    @Override
    public ResultBean getFacetfaceOrderDetailOfBankByOrderId(String clientId, String orderId) throws FroadBusinessException {
        // 银行PC面对面详细
        ResultBean rb = new ResultBean(ResultCode.success);
        try {
            OrderMongo order = orderSupport.getOrderByOrderId(clientId, orderId);
            if (EmptyChecker.isEmpty(order)) {
                // 订单信息查询为空
                ResultCode result = ResultCode.member_order_is_empty;
                throw new FroadBusinessException(result.getCode(), result.getMsg());
            }

            GetOrderDetailByBankManageVoRes res = new GetOrderDetailByBankManageVoRes();

            QueryOrderByBankManageVo vo = convertQueryOrderByBankManageVo(order,null,null,null);

            res.setCreateTime(order.getCreateTime());
            res.setOrderStatus(vo.getOrderStatus());
            res.setPointDiscount(getPoint(order)); // 积分抵扣显示积分
            
            res.setTotalCash(Arith.div(order.getRealPrice(), 1000));
            res.setTotalCutMoney(Arith.div(EmptyChecker.isEmpty(order.getCutMoney())?0:order.getCutMoney(), 1000));
            
          // getSubTotalMoney(product)
            res.setSubTotalMoney(vo.getSubTotalMoney());
            res.setOutletId(order.getOutletId());
//            2015-05-08 新增 显示商户
            res.setMerchantId(order.getMerchantId());
            res.setMerchantName(order.getMerchantName());
//            res.setOutletName(order.getMerchantName());
            res.setMerchantUserId(EmptyChecker.isNotEmpty(order.getMerchantUserId())?order.getMerchantUserId():0L);
            if(EmptyChecker.isNotEmpty(order.getPaymentTime())){
            	res.setPaymentTime(order.getPaymentTime());//支付时间            	
            }
            
          //面对面详情增加结算状态
            long st = System.currentTimeMillis();
            List<Settlement> settlements = settlementSupport.querySettlement(order.getOrderId());
            LogCvt.info("关联查询-结算信息-耗时：" + (System.currentTimeMillis()-st));
            if (EmptyChecker.isEmpty(settlements)) {
                res.setSettleState(SettlementStatus.unsettlemnt.getCode());//结算状态
            } else {
                res.setSettleState(settlements.get(0).getSettleState());//结算状态
                if(EmptyChecker.isNotEmpty(settlements.get(0).getCreateTime()) 
                		&& !SettlementStatus.settlementfailed.getCode().equals(settlements.get(0).getSettleState())
                		&& !SettlementStatus.settlementNoInvalid.getCode().equals(settlements.get(0).getSettleState())){
                	res.setSettleTime(settlements.get(0).getCreateTime());//结算时间，面对面详情要用到
                }
            }
            
            StringBuffer orgNames = new StringBuffer("");
            //所属机构           orgNames
            if(order.getForgCode()!= null && !"".equals(order.getForgCode())){
            	Org orgForg = commonLogic.getOrgByOrgCode(order.getForgCode(), clientId);
            	if(EmptyChecker.isNotEmpty(orgForg) && EmptyChecker.isNotEmpty(orgForg.getOrgName())){
            		orgNames.append(orgForg.getOrgName());               		
            	}
            	if(order.getSorgCode()!= null && !"".equals(order.getSorgCode())){
                	Org orgSorg = commonLogic.getOrgByOrgCode(order.getSorgCode(), clientId);
                	if(EmptyChecker.isNotEmpty(orgSorg) && EmptyChecker.isNotEmpty(orgSorg.getOrgName())){
                		orgNames.append("-").append(orgSorg.getOrgName());                    		
                	}
                	if(order.getTorgCode()!= null && !"".equals(order.getTorgCode())){
                    	Org orgTorg = commonLogic.getOrgByOrgCode(order.getTorgCode(), clientId);
                    	if(EmptyChecker.isNotEmpty(orgTorg) && EmptyChecker.isNotEmpty(orgTorg.getOrgName())){
                    		orgNames.append("-").append(orgTorg.getOrgName());
                    	}
                    	if(order.getLorgCode()!= null && !"".equals(order.getLorgCode())){
                        	Org orgLorg = commonLogic.getOrgByOrgCode(order.getLorgCode(), clientId);
                        	if(EmptyChecker.isNotEmpty(orgLorg) && EmptyChecker.isNotEmpty(orgLorg.getOrgName())){
                        		orgNames.append("-").append(orgLorg.getOrgName());
                        	}
                        }
                    }
                }
            }
            res.setOrgNames(orgNames.toString());
            DeliverInfoVo deliverInfoVo = new DeliverInfoVo();
            if(EmptyChecker.isNotEmpty(order.getPhone())){
            	deliverInfoVo.setPhone(order.getPhone());        	
            }
            if(EmptyChecker.isNotEmpty(order.getMemberName())){
            	deliverInfoVo.setConsignee(order.getMemberName());           	
            }
            if(OrderStatus.closed.getCode().equals(order.getOrderStatus())){
            	res.setCloseReason("用户取消");//用户取消
            }else if(OrderStatus.sysclosed.getCode().equals(order.getOrderStatus())){
            	res.setCloseReason("系统关闭");//系统关闭
            }
            if(EmptyChecker.isNotEmpty(order.getRealPrice()) && order.getRealPrice()!=0){
            	res.setRealTotalMoney(Arith.div(order.getRealPrice(), 1000));            	
            }
            res.setDeliverInfoVo(deliverInfoVo);
            
            //惠付新增（2016.1.4）
            if(EmptyChecker.isNotEmpty(order.getIsPrefPay()) && order.getIsPrefPay() == 1){
            	res.setConsumeMoney(EmptyChecker.isEmpty(order.getConsumeMoney())? 0 : Arith.div(order.getConsumeMoney(), 1000));
            }else{
            	Integer cutMoney = order.getCutMoney() == null ? 0 : order.getCutMoney();
            	res.setConsumeMoney(Arith.div(order.getTotalPrice()+cutMoney, 1000));
            }
            res.setDiscountMoney(EmptyChecker.isEmpty(order.getDiscountMoney())? 0 : Arith.div(order.getDiscountMoney(), 1000));
            
            double pointMoney = 0;
            if(EmptyChecker.isNotEmpty(order.getBankPoints()) && order.getBankPoints()>0 && StringUtils.equals(order.getOrderStatus(), OrderStatus.paysuccess.getCode())){
            	if(EmptyChecker.isNotEmpty(order.getPointRate())){
            		Integer pointRate = Integer.valueOf(order.getPointRate());
            		pointMoney = Arith.divFloor(Arith.div(order.getBankPoints(), 1000), pointRate);
            	}
            }
            if(EmptyChecker.isNotEmpty(order.getFftPoints()) && order.getFftPoints()>0 && StringUtils.equals(order.getOrderStatus(), OrderStatus.paysuccess.getCode())){
            	Integer pointRate = 1;
            	if(EmptyChecker.isNotEmpty(order.getPointRate())){
            		pointRate = Integer.valueOf(order.getPointRate());
            	}
            	pointMoney = Arith.divFloor(Arith.div(order.getFftPoints(), 1000), pointRate);
            }
            res.setPointMoney(pointMoney);
            
            rb = new ResultBean(ResultCode.success, res);
        } catch (FroadBusinessException e) {
            throw new FroadBusinessException(e.getCode(), e.getMsg());
        }
        return rb;
    }
    
    @Override
    public ResultBean getSubOrderOfBankByOrderIdAndType(String clientId, String subOrderId, String type,String deOption) throws FroadBusinessException {
        // 银行PC根据订单类型查找详细
        ResultBean rb = new ResultBean(ResultCode.success);
        try {
            SubOrderMongo subOrder = orderSupport.getSubOrderBySubOrderId(clientId, subOrderId);
            if (EmptyChecker.isEmpty(subOrder)) {
                ResultCode result = ResultCode.member_suborder_query_empty;
                throw new FroadBusinessException(result.getCode(), result.getMsg());
            }
            OrderMongo order = orderSupport.getOrderById(clientId, subOrder.getOrderId());

            if (EmptyChecker.isEmpty(order)) {
                ResultCode result = ResultCode.member_order_is_empty;
                throw new FroadBusinessException(result.getCode(), result.getMsg());
            }
            GetOrderDetailByBankManageVoRes res = new GetOrderDetailByBankManageVoRes();
            if(EmptyChecker.isNotEmpty(subOrder.getType()) 
            		&& SubOrderType.group_merchant.getCode().equals(subOrder.getType())){//团购订单详情查询
            	res = getSubOrderOfBankGroupByOrderIdAndType(clientId, subOrderId, subOrder,order);
            }else if(EmptyChecker.isNotEmpty(subOrder.getType()) 
            		&& SubOrderType.presell_org.getCode().equals(subOrder.getType())){//预售订单查询
            	res = getSubOrderOfBankPresellByOrderIdAndType(clientId, subOrderId, subOrder,order,deOption);
            }else if(EmptyChecker.isNotEmpty(subOrder.getType()) 
            		&& SubOrderType.special_merchant.getCode().equals(subOrder.getType())){//名优特惠订单查询
            	res = getSubOrderOfBankSpMerchantByOrderIdAndType(clientId, subOrderId, subOrder,order);
            }else if(EmptyChecker.isNotEmpty(subOrder.getType()) 
            		&& (SubOrderType.offline_points_org.getCode().equals(subOrder.getType()) 
            		|| SubOrderType.online_points_org.getCode().equals(subOrder.getType()))){//积分兑换订单查询
            	res = getSubOrderOfBankPointByOrderIdAndType(clientId, subOrderId, subOrder,order);
            }else if(EmptyChecker.isNotEmpty(subOrder.getType())
            		&& (SubOrderType.boutique.getCode().equals(subOrder.getType()))){
            	res = getSubOrderOfBankBoutiqueByOrderIdAndType(clientId, subOrderId, subOrder,order);//精品商城订单查询
            }else{
                LogCvt.info("订单查询-详情订单类型有误：type:"+ type +" clientId:"+clientId+" subOrderId:"+subOrderId);
            	ResultCode result = ResultCode.member_order_type_unknow;
                throw new FroadBusinessException(result.getCode(), result.getMsg());
            }            
            rb = new ResultBean(ResultCode.success, res);
        } catch (FroadBusinessException e) {
            throw new FroadBusinessException(e.getCode(), e.getMsg());
        }

        return rb;
    }
    /**
     * 银行管理平台团购订单详情查询
     * @param clientId
     * @param subOrderId
     * @param subOrder
     * @param order
     * @return
     */
    public GetOrderDetailByBankManageVoRes getSubOrderOfBankGroupByOrderIdAndType(String clientId, String subOrderId, SubOrderMongo subOrder,OrderMongo order){
        LogCvt.info("订单查询-团购详情开始：clientId:"+clientId+" subOrderId:"+subOrderId);
        GetOrderDetailByBankManageVoRes res = new GetOrderDetailByBankManageVoRes();
        try {
        	OrderDataResult orderDataResult = OrderQueryLogicHelper.getSubOrderMoney(subOrder);
        	double orderPoint = orderDataResult.getPayPointMoney();
//        	double orderPoint = getSubOrderCutPointMoney(subOrder);
        	res.setOrderId(order.getOrderId());
        	res.setSubOrderId(subOrderId);
            res.setOrderStatus(order.getOrderStatus());
            res.setPointDiscount(orderPoint);
            //设置优惠金额
            double totalCutMoney = getSubOrderCutMoney(subOrder);
            res.setTotalCutMoney(totalCutMoney);
            //设置实付金额
//            double totalCash = getSubOrderTotalCash(subOrder);
            double totalCash = orderDataResult.getPayCashAndCutMoney();
            res.setTotalCash(totalCash);
            
            // res.setSubTotalMoney(getSubTotalMoney(order));
            res.setCreateTime(subOrder.getCreateTime());
            res.setMerchantName(subOrder.getMerchantName()!=null?subOrder.getMerchantName():"");
            // 增加返回订单子状态
            res.setRefundState(SubOrderRefundState.REFUND_INIT.getCode());
            try {
                String refundState = subOrder.getRefundState();
                if (EmptyChecker.isNotEmpty(refundState)) {
                    res.setRefundState(subOrder.getRefundState());
                }
            } catch (Exception e) {
            }

            List<ProductInfoVo> list = new ArrayList<ProductInfoVo>();
            double subTotalMoney = 0;
            int takeNum = 0;
            // 最近消费时间（详情返回对象属性）
            long newLastConsumeTime = 1l;
            Long lastConsumeTimeTmp = 1l;//中间变量：团购的时候使用,获取最近消费时间
            String newLastConsumeProductId = "";
            //消费人|提货人|收货人（详情返回对象属性）
            String deliname = "";
            //消费人|提货人|收货人 手机号码（详情返回对象属性）
            String deliPhone = "";
            long minCreateTime = 1l;
            long maxRefundTime = 1l;
            StringBuffer orgNames = new StringBuffer();
            for (ProductMongo product : subOrder.getProducts()) {               
                //团购详情列表的商品以券为单位展示
            	if(lastConsumeTimeTmp > newLastConsumeTime){
                	newLastConsumeTime = lastConsumeTimeTmp;
                	newLastConsumeProductId = product.getProductId();
                }
            	String startTime = null;
            	String endTime = null;
            	boolean isOutletCommentFlag = false;                  
            	String proID = "";
            	 Map<String, String> productMap = getProduct(subOrder.getClientId(), subOrder.getMerchantId(), product.getProductId());
                 if (EmptyChecker.isNotEmpty(productMap)) {
                         startTime = productMap.get("expire_start_time");
                         endTime = productMap.get("expire_end_time");                 
                 }
                 subTotalMoney = Arith.add(subTotalMoney, getSubTotalMoney(product));
                 // 评价1， 已评价， 否则未评价
                 if("1".equals(product.getCommentState())) {
                	 isOutletCommentFlag = true;
                 } 
                 proID = product.getProductId();
            	 List<Ticket> tickets = ticketSupport.getTickets(subOrder.getOrderId(), subOrder.getSubOrderId(), product.getProductId());
                 if (EmptyChecker.isNotEmpty(tickets)) {
                     long tempTime = 1l;
                     for (Ticket t : tickets) {
                    	 ProductInfoVo groupVo = new ProductInfoVo();
                         if (TicketStatus.consumed.getCode().equals(t.getStatus())) {      
                             groupVo.setConsumeStatus("2");//已消费
                             takeNum+=1;
                         }else{
                        	 groupVo.setConsumeStatus("1");//未消费
                         }
                         if(EmptyChecker.isNotEmpty(t.getConsumeTime())){
                        	 tempTime= t.getConsumeTime();                            	 
                        	 groupVo.setCheckCodeTime(t.getConsumeTime());
                         }
                         if (tempTime > lastConsumeTimeTmp) {
                             // 最大的值设为最近消费时间
                        	 lastConsumeTimeTmp= tempTime; 
                         }
                         if(EmptyChecker.isNotEmpty(t.getMerchantUserId())){
                        	 groupVo.setMerchantUserId(t.getMerchantUserId());                            	 
                         }
                         if(EmptyChecker.isNotEmpty(t.getTicketId())){
                        	 groupVo.setTakeCode(t.getTicketId());                            	 
                         }
                         if(EmptyChecker.isNotEmpty(t.getMemberName())){
                        	 deliname = t.getMemberName();                            	 
                         }
                         if(EmptyChecker.isNotEmpty(t.getMobile())){
                        	 deliPhone = t.getMobile();                	
                         }               	
                         groupVo.setOutletName(t.getOutletName());     
                         if (EmptyChecker.isNotEmpty(startTime)) {
                        	 groupVo.setStartTime(Long.parseLong(startTime));
                         }
                         if (EmptyChecker.isNotEmpty(endTime)) {
                        	 groupVo.setEndTime(Long.parseLong(endTime));
                         }
                         // 详细增加发货状态
                    	 groupVo.setDeliveryOption(product.getDeliveryOption());
                         // 未支付完成没有提货状态
                         if (OrderStatus.paysuccess.getCode().equals(subOrder.getOrderStatus()) || OrderStatus.sysclosed.getCode().equals(subOrder.getOrderStatus())) {
                             if (EmptyChecker.isEmpty(product.getDeliveryState())) {
                            	 groupVo.setDeliverState(ShippingStatus.untake.getCode());
                             } else {
                            	 groupVo.setDeliverState(product.getDeliveryState());
                             }
                         }
                         
                         groupVo.setDeliveryMoney(getDeliveryMoney(product));
                         groupVo.setProductName(product.getProductName());
                         groupVo.setQuantity(getProductQuantity(product));
                         groupVo.setMoney(getMoney(product));
                         groupVo.setSubTotalMoney(getSubTotalMoney(product));
                         groupVo.setIsOutletComment(isOutletCommentFlag);
                         groupVo.setProductId(proID);
                         /**退货商品 剩余个数、退款个数*/
                         refundProduct(groupVo,subOrder.getSubOrderId(), subOrder.getOrderId());//新需求中不需要这个信息
                         //结算状态settlementStatus-订单优化     
                         List<String> ticketIds = new ArrayList<String>();
                         ticketIds.add(t.getTicketId());
                         List<Settlement> ticketSettleList =  settlementSupport.querySettlementStatusList(ticketIds);//查出单个的团购券结算信息
                         if(EmptyChecker.isNotEmpty(ticketSettleList) && ticketSettleList.size()>0){
                        	 for(Settlement set:ticketSettleList){
                        		 // 结算状态 0-未结算 1-结算中 2-已结算 3-结算失败
                        		 if(EmptyChecker.isNotEmpty(set) && EmptyChecker.isNotEmpty(set.getSettleState())){
                        			 groupVo.setSettlementStatus(set.getSettleState());                            			 
                        		 }
                        	 }                               	 
                         }
                         //退款券的相关信息
                         if(t.getRefundId()!=null && !"".equals(t.getRefundId())){
                        	 String reasonRef = "";
                        	 if(TicketStatus.refunding.getCode().equals(t.getStatus())){
                        		 groupVo.setRefundState("2");//退款中                            		 
                        	 }else if(TicketStatus.refunded.getCode().equals(t.getStatus())){
                        		 groupVo.setRefundState("3");//已退款                           		 
                        	 }else if(TicketStatus.expired_refunding.getCode().equals(t.getStatus())){
                        		 reasonRef = "已过期退款";
                        		 groupVo.setRefundState("2");//已过期退款中                           		 
                        	 }else if(TicketStatus.init_refunded.getCode().equals(t.getStatus())){
                        		 reasonRef = "未发码退款 ";
                        		 groupVo.setRefundState("3");//未发码退款                           		 
                        	 }else if(TicketStatus.expired_refunded.getCode().equals(t.getStatus())){
                        		 reasonRef = "已过期退款";
                        		 groupVo.setRefundState("3");//已过期退款                           		 
                        	 }
//                        	 else{
//                        		 groupVo.setRefundState("2");//退款中   
//                        	 }
                        	 RefundHistory refundDetail = refundSupport.getByRefundId(t.getRefundId());
                        	 //退款原因refundReason-订单优化
                        	 if(EmptyChecker.isEmpty(refundDetail.getReason()) 
                        			 && EmptyChecker.isNotEmpty(reasonRef)){
                        		 groupVo.setRefundReason(reasonRef);       //系统发起的退款原因（未发码退款|已过期退款）                 		 
                        	 }else{
                        		 groupVo.setRefundReason(refundDetail.getReason());     //用户填写的退款原因                   		 
                        	 }
                        	 //退款申请时间refundApplyTime-订单优化
                        	 if(EmptyChecker.isNotEmpty(refundDetail.getCreateTime())){
                        		 groupVo.setRefundApplyTime(refundDetail.getCreateTime());                            		 
                        	 }
                        	 if(minCreateTime==1){
                        		 if(EmptyChecker.isNotEmpty(refundDetail.getCreateTime())){
 		               	 			minCreateTime = refundDetail.getCreateTime();                           		 
 		               	 		}
                        	 }else if(EmptyChecker.isNotEmpty(refundDetail.getCreateTime()) && minCreateTime > refundDetail.getCreateTime()){
                        		 minCreateTime = refundDetail.getCreateTime();  
                        	 }
                        	 //退款成功时间refundSuccessTime-订单优化  
                        	 if(EmptyChecker.isNotEmpty(refundDetail.getRefundTime())){
                        		 groupVo.setRefundSuccessTime(refundDetail.getRefundTime());                            		 
                        	 }
                        	 if(maxRefundTime==1){
                        		 if(EmptyChecker.isNotEmpty(refundDetail.getRefundTime())){
  		               	 			maxRefundTime = refundDetail.getRefundTime();                           		 
  		               	 		}
                        	 }else if(EmptyChecker.isNotEmpty(refundDetail.getRefundTime()) && maxRefundTime < refundDetail.getRefundTime()){
                        		 maxRefundTime = refundDetail.getRefundTime();  
                        	 }
                         }
                         list.add(groupVo);
                     }                 
                 }else{//没有券(没有支付成功，商品值返回商品信息,以商品为单位)
                	 Integer proNum = getProductQuantity(product);
                	 if(EmptyChecker.isNotEmpty(proNum) && proNum != 0){
                		 for(int i=0;i<proNum;i++){
                			 ProductInfoVo groupVo = new ProductInfoVo();
                			 groupVo.setConsumeStatus("");                         	 
                            groupVo.setCheckCodeTime(0);                                                                     	
                             groupVo.setOutletName("");     
                             if (EmptyChecker.isNotEmpty(startTime)) {
                            	 groupVo.setStartTime(Long.parseLong(startTime));
                             }
                             if (EmptyChecker.isNotEmpty(endTime)) {
                            	 groupVo.setEndTime(Long.parseLong(endTime));
                             }
                             // 详细增加发货状态
                        	 groupVo.setDeliveryOption(product.getDeliveryOption());
                             // 未支付完成没有提货状态
                             if (OrderStatus.paysuccess.getCode().equals(subOrder.getOrderStatus()) || OrderStatus.sysclosed.getCode().equals(subOrder.getOrderStatus())) {
                                 if (EmptyChecker.isEmpty(product.getDeliveryState())) {
                                	 groupVo.setDeliverState(ShippingStatus.untake.getCode());
                                 } else {
                                	 groupVo.setDeliverState(product.getDeliveryState());
                                 }
                             }
                             
                             groupVo.setDeliveryMoney(getDeliveryMoney(product));
                             groupVo.setProductName(product.getProductName());
                             groupVo.setQuantity(getProductQuantity(product));
                             groupVo.setMoney(getMoney(product));
                             groupVo.setSubTotalMoney(getSubTotalMoney(product));
                             groupVo.setIsOutletComment(isOutletCommentFlag);
                             groupVo.setProductId(proID);
                             /**退货商品 剩余个数、退款个数*/
                             refundProduct(groupVo,subOrder.getSubOrderId(), subOrder.getOrderId());//新需求中不需要这个信息
                             list.add(groupVo);
                		 }               		 
                	 }
                 }
            }
            res.setSubTotalMoney(subTotalMoney);
            //订单优化增加	//支付时间paymentTime
            if(EmptyChecker.isNotEmpty(order.getPaymentTime())){
            	res.setPaymentTime(order.getPaymentTime());            	
            }
            	//验码时间(最新一次验码时间)ticketTime
            if(newLastConsumeTime != 1){
            	res.setTicketTime(newLastConsumeTime);    
            	res.setTakeTime(newLastConsumeTime);
            }else if(lastConsumeTimeTmp!=1){
            	res.setTicketTime(lastConsumeTimeTmp);    
            	res.setTakeTime(lastConsumeTimeTmp);
            }
            	//券有效期expireTime
            if(!"".equals(newLastConsumeProductId)){
            	Map<String, String> productMap = getProduct(subOrder.getClientId(), subOrder.getMerchantId(), newLastConsumeProductId);
            	if (EmptyChecker.isNotEmpty(productMap)) {
            		String endTime = null;
            		endTime = productMap.get("expire_end_time");
            		if (EmptyChecker.isNotEmpty(endTime)) {
            			res.setExpireTime(Long.parseLong(endTime));
            		}
            	}           	
            }
        	//收货人信息|提货人信息|消费人信息 DeliverInfoVo deliverInfoVo
        	DeliverInfoVo deliverInfoVo= new DeliverInfoVo();
        	if(EmptyChecker.isNotEmpty(deliname) || EmptyChecker.isNotEmpty(deliPhone)){
        		deliverInfoVo.setConsignee(deliname);
        		deliverInfoVo.setPhone(deliPhone);        		
        	}else{//消费人、手机
        		String recvId = order.getRecvId();
                if(EmptyChecker.isNotEmpty(recvId)){
                	RecvInfo recvInfo = orderSupport.getRecvInfo(order.getClientId(), order.getMemberCode(), recvId);
                    if (EmptyChecker.isNotEmpty(recvInfo)) {
                    	deliverInfoVo.setConsignee(EmptyChecker.isEmpty(recvInfo.getConsignee())?"":recvInfo.getConsignee());
                		deliverInfoVo.setPhone(EmptyChecker.isEmpty(recvInfo.getPhone())?"":recvInfo.getPhone());
                    }
                }
        	}
        	res.setDeliverInfoVo(deliverInfoVo);           	
            
        	//所属机构           orgNames
            if(subOrder.getForgCode()!= null && !"".equals(subOrder.getForgCode())){
            	Org orgForg = commonLogic.getOrgByOrgCode(subOrder.getForgCode(), clientId);
            	if(EmptyChecker.isNotEmpty(orgForg) && EmptyChecker.isNotEmpty(orgForg.getOrgName())){
            		orgNames.append(orgForg.getOrgName());               		
            	}
            	if(subOrder.getSorgCode()!= null && !"".equals(subOrder.getSorgCode())){
                	Org orgSorg = commonLogic.getOrgByOrgCode(subOrder.getSorgCode(), clientId);
                	if(EmptyChecker.isNotEmpty(orgSorg) && EmptyChecker.isNotEmpty(orgSorg.getOrgName())){
                		orgNames.append("-").append(orgSorg.getOrgName());                    		
                	}
                	if(subOrder.getTorgCode()!= null && !"".equals(subOrder.getTorgCode())){
                    	Org orgTorg = commonLogic.getOrgByOrgCode(subOrder.getTorgCode(), clientId);
                    	if(EmptyChecker.isNotEmpty(orgTorg) && EmptyChecker.isNotEmpty(orgTorg.getOrgName())){
                    		orgNames.append("-").append(orgTorg.getOrgName());
                    	}
                    	if(subOrder.getLorgCode()!= null && !"".equals(subOrder.getLorgCode())){
                        	Org orgLorg = commonLogic.getOrgByOrgCode(subOrder.getLorgCode(), clientId);
                        	if(EmptyChecker.isNotEmpty(orgLorg) && EmptyChecker.isNotEmpty(orgLorg.getOrgName())){
                        		orgNames.append("-").append(orgLorg.getOrgName());
                        	}
                        }
                    }
                }
            }
            res.setOrgNames(orgNames.toString());
            res.setTakeNumber(takeNum);//消费数量
            //退款申请时间refundApplyTime
            if(minCreateTime!=1){
            	res.setRefundApplyTime(minCreateTime);            	
            }
    		//退款成功时间refundSuccessTime
            if(maxRefundTime!=1){
            	res.setRefundSuccessTime(maxRefundTime);            	
            }
            	//订单关闭原因（如果订单被关闭）closeReason
            if(OrderStatus.sysclosed.getCode().equals(subOrder.getOrderStatus())){//子订单全单退款
            	if(SubOrderRefundState.REFUND_SUCCESS.getCode().equals(subOrder.getRefundState())){
            		res.setCloseReason("用户退款");//用户退款 关闭
            	}else{
            		res.setCloseReason("系统关闭");//超过2小时 系统关闭
            	}
            }else if(OrderStatus.closed.getCode().equals(subOrder.getOrderStatus())){
            	res.setCloseReason("用户取消");//用户取消 关闭
            }         	
            res.setProductInfos(list);
        } catch (Exception e) {
            LogCvt.info("订单查询-团购详情出错：clientId:"+clientId+" subOrderId:"+subOrderId,e);
        }
        LogCvt.info("订单查询-团购详情结束：clientId:"+clientId+" subOrderId:"+subOrderId);
        return res;
    }  
    /**
     * 银行管理平台预售订单详情查询
     * @param clientId
     * @param subOrderId
     * @param subOrder
     * @param order
     * @return
     */
    public GetOrderDetailByBankManageVoRes getSubOrderOfBankPresellByOrderIdAndType(String clientId, String subOrderId,SubOrderMongo subOrder,OrderMongo order,String deOption){
        LogCvt.info("订单查询-预售详情结束：clientId:"+clientId+" subOrderId:"+subOrderId);
        GetOrderDetailByBankManageVoRes res = new GetOrderDetailByBankManageVoRes();
        try {
        	OrderDataResult orderDataResult = OrderQueryLogicHelper.getSubOrderMoney(subOrder);
        	double orderPoint = orderDataResult.getPayPointMoney();
        	
        	res.setOrderId(order.getOrderId());
        	res.setSubOrderId(subOrderId);
            res.setOrderStatus(order.getOrderStatus());
//            res.setPointDiscount(getSubOrderCutPointMoney(subOrder));
            res.setPointDiscount(orderPoint);
            //设置优惠金额
            double totalCutMoney = getSubOrderCutMoney(subOrder);
            res.setTotalCutMoney(totalCutMoney);
            //设置实付金额
//            double totalCash = getSubOrderTotalCash(subOrder);
            double totalCash = orderDataResult.getPayCashAndCutMoney();
            res.setTotalCash(totalCash);
            
            // res.setSubTotalMoney(getSubTotalMoney(order));
            res.setCreateTime(subOrder.getCreateTime());
            res.setMerchantName(subOrder.getMerchantName()!=null?subOrder.getMerchantName():"");
            // 增加返回订单子状态
            res.setRefundState(SubOrderRefundState.REFUND_INIT.getCode());
            try {
                String refundState = subOrder.getRefundState();
                if (EmptyChecker.isNotEmpty(refundState)) {
                    res.setRefundState(subOrder.getRefundState());
                }
            } catch (Exception e) {
            }

            List<ProductInfoVo> list = new ArrayList<ProductInfoVo>();
            double subTotalMoney = 0;
            double subTotalMoneyZiti = 0;
            double subTotalMoneyPeisong = 0;
            // 最近消费时间（详情返回对象属性）
            long newLastConsumeTime = 1l;
            String newLastConsumeProductId = "";
            //消费人|提货人|收货人（详情返回对象属性）
            String deliname = "";
            //消费人|提货人|收货人 手机号码（详情返回对象属性）
            String deliPhone = "";
            long minCreateTime = 1l;
            long maxRefundTime = 1l;
            int takeNum = 0;
            StringBuffer takeOrgName = new StringBuffer("");
            String tcode = "";
            String optionDe="";
            Integer deMoney = 0;
            for (ProductMongo product : subOrder.getProducts()) {               
            	//非团购
                	ProductInfoVo vo = new ProductInfoVo();
                	// 预售和团购才有券的基本信息
                	Map<String,Object> resMap = getTicketConsumeState(subOrder.getOrderId(), subOrder.getSubOrderId(), product.getProductId());
                    vo.setConsumeStatus(""+String.valueOf((Integer)resMap.get("consumeStatus")));
                    Integer numTmp = (Integer)resMap.get("takedNum");
                    if(EmptyChecker.isNotEmpty(numTmp) && numTmp != 0){
                    	takeNum+=numTmp;
                    }
                    vo.setTakeNumber((Integer)resMap.get("takedNum"));//已提货数量
                    if(EmptyChecker.isNotEmpty(product.getOrgName()) && takeOrgName.toString().indexOf(product.getOrgName()) == -1){ //去重                   	
                    	takeOrgName.append(product.getOrgName());
                    }else if(EmptyChecker.isNotEmpty(product.getOrgCode())){
                    	//如果名字取不到但有code就通过code查询org
                    	Org org = commonLogic.getOrgByOrgCode(product.getOrgCode(), clientId);
                    	if(EmptyChecker.isNotEmpty(org) && EmptyChecker.isNotEmpty(org.getOrgName()) && takeOrgName.toString().indexOf(org.getOrgName()) == -1){
                    		takeOrgName.append(org.getOrgName());
                    	}
                    }
                    tcode=(String)resMap.get("tcode");//提货码
                    long lastConsumeTime = (Long)resMap.get("lastConsumeTime");
                    
                    //提货时间checkCodeTime-订单优化
                    if(lastConsumeTime != 1){
                    	vo.setTakeTime(lastConsumeTime);                    	
                    }
                    if(lastConsumeTime > newLastConsumeTime){//最近一次的提货时间
                    	newLastConsumeTime = lastConsumeTime;
                    	newLastConsumeProductId = product.getProductId();
                    }
                    if("".equals(deliname) && EmptyChecker.isNotEmpty((String)resMap.get("consignee"))){
                    	deliname = (String)resMap.get("consignee");
                    }
                    if("".equals(deliPhone) && EmptyChecker.isNotEmpty((String)resMap.get("phone"))){
                    	deliPhone = (String)resMap.get("phone");
                    }
                    //消费门店outletName-订单优化
                    if(EmptyChecker.isNotEmpty((String)resMap.get("outletName"))){
                    	vo.setOutletName((String)resMap.get("outletName"));
                    }
                    //操作员 merchantUserId-订单优化
                    if(EmptyChecker.isNotEmpty((Long)resMap.get("merchantUserId"))){
                    	vo.setMerchantUserId((Long)resMap.get("merchantUserId"));
                    }
                    
                    //取机构的虚拟商户号
    				String merchantId = subOrder.getMerchantId();
    				if(!(StringUtils.equals(subOrder.getType(), SubOrderType.special_merchant.getCode()) || StringUtils.equals(subOrder.getType(), SubOrderType.group_merchant.getCode()) || StringUtils.equals(subOrder.getType(), SubOrderType.boutique.getCode()))){
    					Org org = commonLogic.getOrgByOrgCode(merchantId, clientId);
    					if(EmptyChecker.isEmpty(org)){
    						LogCvt.error("通过机构号获取商户号失败[机构号："+merchantId+"]");
    						throw new FroadBusinessException(ResultCode.failed.getCode(),"取消订单失败");
    					}
    					merchantId = org.getMerchantId();
    				}
                    
                    Map<String, String> productMap = getProduct(subOrder.getClientId(), merchantId, product.getProductId());
                    if (EmptyChecker.isNotEmpty(productMap)) {
                        String startTime = null;
                        String endTime = null;
                        
                        startTime = productMap.get("delivery_start_time");
                        endTime = productMap.get("delivery_end_time");

                        if (EmptyChecker.isNotEmpty(startTime)) {
                            vo.setStartTime(Long.parseLong(startTime));
                        }
                        if (EmptyChecker.isNotEmpty(endTime)) {
                            vo.setEndTime(Long.parseLong(endTime));
                        }

                    }
                    //如果有退款的商品
                    Integer refundNum = (Integer)resMap.get("refundNum");
                    if(refundNum!=null && refundNum > 0){
                    	vo.setRefundNumber(refundNum);//退款数量
                    }                    
                    // 详细增加发货状态
                    // 配送方式
                    if(EmptyChecker.isNotEmpty(product.getDeliveryOption())){
                    	optionDe = product.getDeliveryOption();
                    	vo.setDeliveryOption(product.getDeliveryOption());                   	
                    }
                    
                    if(DeliveryType.home.getCode().equals(product.getDeliveryOption()) 
                    		&& EmptyChecker.isNotEmpty(product.getDeliveryMoney())){
                    	
                    	deMoney+=product.getDeliveryMoney();                 	
                    }
                    // 未支付完成没有提货状态
                    if (OrderStatus.paysuccess.getCode().equals(subOrder.getOrderStatus()) || OrderStatus.sysclosed.getCode().equals(subOrder.getOrderStatus())) {
                        if (EmptyChecker.isEmpty(product.getDeliveryState())) {
                            vo.setDeliverState(ShippingStatus.untake.getCode());
                        } else {
                            vo.setDeliverState(product.getDeliveryState());
                        }
                    }

                    vo.setDeliveryMoney(getDeliveryMoney(product));
                    vo.setProductName(product.getProductName());
                    vo.setQuantity(getProductQuantity(product));
                    vo.setMoney(getMoney(product));
                    vo.setSubTotalMoney(getSubTotalMoney(product));

                    subTotalMoney = Arith.add(subTotalMoney, getSubTotalMoney(product));
                    if(EmptyChecker.isNotEmpty(product.getDeliveryOption()) && 
                    		DeliveryType.home.getCode().equals(product.getDeliveryOption())){
                    	subTotalMoneyPeisong = Arith.add(subTotalMoneyPeisong, getSubTotalMoney(product));
                    }
                    if(EmptyChecker.isNotEmpty(product.getDeliveryOption()) && 
                    		DeliveryType.take.getCode().equals(product.getDeliveryOption())){
                    	subTotalMoneyZiti = Arith.add(subTotalMoneyZiti, getSubTotalMoney(product));
                    }
                    // 评价1， 已评价， 否则未评价
                    if("1".equals(product.getCommentState())) {
                        vo.setIsOutletComment(true);
                    } else {
                        vo.setIsOutletComment(false);
                    }
                    vo.setProductId(product.getProductId());

                    /**退货商品*/
                    refundProduct(vo,subOrder.getSubOrderId(), subOrder.getOrderId());//新需求中不需要这个信息  
                    List<RefundHistory> refundHistoryList = refundSupport.findRefundListBySubOrderIdAndProductId(subOrder.getSubOrderId(),product.getProductId());
                    long tmpApplyCreateTime = 1l;
                    long tmpSuccessRefundTime = 1l;
                    int refundingNum = 0;
                    int refundedNum = 0;
                    String refundReasonTmp="";
                    if(EmptyChecker.isNotEmpty(refundHistoryList) && refundHistoryList.size()>0){
                    	for(RefundHistory refundHis:refundHistoryList){
                    		if(RefundState.REFUND_PROCESSING.getCode().equals(refundHis.getRefundState())
                    				|| RefundState.REFUND_SUCCESS.getCode().equals(refundHis.getRefundState())){
                    			if(tmpApplyCreateTime==1){
        	               	 		if(EmptyChecker.isNotEmpty(refundHis.getCreateTime())){
    		               	 			tmpApplyCreateTime = refundHis.getCreateTime();  
    		               	 		}
    	    	               	 	if(EmptyChecker.isNotEmpty(refundHis.getReason())){
    		               	 			refundReasonTmp = refundHis.getReason();
    		               	 		}
    			               	}else if(EmptyChecker.isNotEmpty(refundHis.getCreateTime()) && tmpApplyCreateTime > refundHis.getCreateTime()){
    			               		tmpApplyCreateTime = refundHis.getCreateTime(); 
    			               		if(EmptyChecker.isNotEmpty(refundHis.getReason())){
    		               	 			refundReasonTmp = refundHis.getReason();
    		               	 		}
    			               	}
    		               	 	if(tmpSuccessRefundTime==1){
    		               	 		if(EmptyChecker.isNotEmpty(refundHis.getRefundTime())){
    		               	 			tmpSuccessRefundTime = refundHis.getRefundTime();     		               	 			
    		               	 		}
        		               	 	if(EmptyChecker.isNotEmpty(refundHis.getReason())){
    		               	 			refundReasonTmp = refundHis.getReason();
    		               	 		}
    			               	}else if(EmptyChecker.isNotEmpty(refundHis.getRefundTime()) && tmpSuccessRefundTime < refundHis.getRefundTime()){
    			               		tmpSuccessRefundTime = refundHis.getRefundTime();
    			               		if(EmptyChecker.isNotEmpty(refundHis.getReason())){
    		               	 			refundReasonTmp = refundHis.getReason();
    		               	 		}
    			               	}
    	               	 		if(minCreateTime==1){
        	               	 		if(EmptyChecker.isNotEmpty(refundHis.getCreateTime())){
    		               	 			minCreateTime = refundHis.getCreateTime();                           		 
    		               	 		}
    			               	}else if(EmptyChecker.isNotEmpty(refundHis.getCreateTime()) && minCreateTime > refundHis.getCreateTime()){
    			               		 minCreateTime = refundHis.getCreateTime();  
    			               	}
    		               	 	if(maxRefundTime==1){
        		               	 	if(EmptyChecker.isNotEmpty(refundHis.getRefundTime())){
    		               	 			maxRefundTime = refundHis.getRefundTime();                           		 
    		               	 		}
    			               	}else if(EmptyChecker.isNotEmpty(refundHis.getRefundTime()) && maxRefundTime < refundHis.getRefundTime()){
    			               		maxRefundTime = refundHis.getRefundTime();  
    			               	}
	    		               	if(RefundState.REFUND_PROCESSING.getCode().equals(refundHis.getRefundState())){
	 		               	 		refundingNum+=refundHis.getShoppingInfo().get(0).getProducts().get(0).getQuantity();//退款中个数
	 		               	 	}
	 		               	 	if(RefundState.REFUND_SUCCESS.getCode().equals(refundHis.getRefundState())){
	 		               	 		refundedNum+=refundHis.getShoppingInfo().get(0).getProducts().get(0).getQuantity();//已退款个数
	 		               	 	}
                    		}	               	 		
               	 		}
                    	vo.setRefundingNumber(refundingNum);//退款中数量
                    	vo.setRefundNumber(refundedNum);//退款数量
               	 	 Integer proNum = vo.getQuantity();
               	 	Integer refNum = refundingNum+refundedNum;
                     if(EmptyChecker.isNotEmpty(proNum) && EmptyChecker.isNotEmpty(refNum)){
                    	 if(refNum > 0 && refNum < proNum && refundedNum > 0 && refundedNum == refNum){
                    		 vo.setRefundState("1");//预售、名优特惠详情商品退款状态1:部分退款
                    	 }else if(refNum > 0 && refNum < proNum && refundedNum > 0 && refundedNum < refNum && refundingNum>0){
                    		 vo.setRefundState("2");//预售、名优特惠详情商品退款状态2:部分退款和退款中都有 就显示退款中
                    	 }else if(refNum > 0 && refNum == proNum && refundedNum == refNum){
                    		 vo.setRefundState("3");//预售、名优特惠详情商品退款状态3:已退款
                    	 }else if(refNum > 0 && refNum == proNum && refundingNum>0){
                    		 vo.setRefundState("2");//名优特惠详情商品退款状态2:退款中
                    	 }
//                    	 else{
//                    		 vo.setRefundState("2");//预售、名优特惠详情商品退款状态2:退款中
//                    	 }
                     }
               	 	}
                    if(tmpApplyCreateTime!=1 && tmpApplyCreateTime!=0){
                    	vo.setRefundApplyTime(tmpApplyCreateTime);//退款申请时间
                    }
                    if(tmpSuccessRefundTime!=1 && tmpSuccessRefundTime!=0){
                    	vo.setRefundSuccessTime(tmpSuccessRefundTime);//退款成功时间
                    }
                    if(refundReasonTmp!=null && !"".equals(refundReasonTmp)){
                    	vo.setRefundReason(refundReasonTmp);//退款原因
                    }                                        
                list.add(vo);
            }
        	res.setDeliveryOption(deOption);
        	if(deMoney!=0){
        		res.setDeliveryMoney(Arith.div(Long.valueOf(deMoney), 1000));       		
        	}
            res.setSubTotalMoney(subTotalMoney);
            res.setSubTotalMoneyPeisong(subTotalMoneyPeisong);
            res.setSubTotalMoneyZiti(subTotalMoneyZiti);
            //订单优化增加	//支付时间paymentTime
            if(EmptyChecker.isNotEmpty(order.getPaymentTime())){
            	res.setPaymentTime(order.getPaymentTime());            	
            }
            if(EmptyChecker.isNotEmpty(takeNum) && takeNum!=0){
            	res.setTakeNumber(takeNum);//提货总数量
            }
            	//验码时间(最新一次验码时间)ticketTime
            if(newLastConsumeTime != 1){
            	res.setTicketTime(newLastConsumeTime);  
            	res.setTakeTime(newLastConsumeTime);
            }
            	//券有效期expireTime
            if(!"".equals(newLastConsumeProductId)){
            	Map<String, String> productMap = getProduct(subOrder.getClientId(), subOrder.getMerchantId(), newLastConsumeProductId);
            	if (EmptyChecker.isNotEmpty(productMap)) {
            		String endTime = null;
            		endTime = productMap.get("delivery_end_time");
            		if (EmptyChecker.isNotEmpty(endTime)) {
            			res.setExpireTime(Long.parseLong(endTime));
            		}
            	}           	
            }
            boolean flag = false;
            if(DeliveryType.home.getCode().equals(deOption)){//配送
        		String recvId = order.getRecvId();
                if(EmptyChecker.isNotEmpty(recvId)){
//                	RecvInfo recvInfo = orderSupport.getRecvInfo(order.getClientId(), order.getMemberCode(), recvId);
//                    if (EmptyChecker.isNotEmpty(recvInfo)) {
//                    	DeliverInfoVo deliVo= new DeliverInfoVo();
//                    	deliVo.setAddress(EmptyChecker.isEmpty(recvInfo.getAddress())?"":recvInfo.getAddress());
//                    	deliVo.setConsignee(EmptyChecker.isEmpty(recvInfo.getConsignee())?"":recvInfo.getConsignee());
//                    	deliVo.setPhone(EmptyChecker.isEmpty(recvInfo.getPhone())?"":recvInfo.getPhone());
//                    	deliVo.setRecvId(recvId);
//                    	//配送方式返回配送的收货人信息和地址
//                    	res.setDeliverInfoVo(deliVo);
//                    	flag = true;
//                    }
                	List<SubOrderMongo> subOrderList = new ArrayList<SubOrderMongo>();
                	subOrderList.add(subOrder);
                	Map<String,RecvInfo> recvMap =  orderSupport.getRecvInfoMap(subOrderList,true);//true表示要返回省市区
                	RecvInfo recvInfo = recvMap.get(subOrder.getOrderId());
                    if (EmptyChecker.isNotEmpty(recvInfo)) {
                    	//收货人信息|提货人信息|消费人信息 DeliverInfoVo deliverInfoVo
                    	DeliverInfoVo deliVo= new DeliverInfoVo();
                    	deliVo.setAddress(EmptyChecker.isEmpty(recvInfo.getAddress())?"":recvInfo.getAddress());
                    	deliVo.setConsignee(EmptyChecker.isEmpty(recvInfo.getConsignee())?"":recvInfo.getConsignee());
                    	deliVo.setPhone(EmptyChecker.isEmpty(recvInfo.getPhone())?"":recvInfo.getPhone());
                    	deliVo.setRecvId(recvId);
                    	//配送方式返回配送的收货人信息和地址
                    	res.setDeliverInfoVo(deliVo);
                    	flag = true;
                    }else{
                    	RecvInfo recvInfo2 = orderSupport.getRecvInfo(order.getClientId(), order.getMemberCode(), recvId);
                    	if(EmptyChecker.isNotEmpty(recvInfo2)){
                    		//收货人信息|提货人信息|消费人信息 DeliverInfoVo deliverInfoVo
                    		DeliverInfoVo deliVo2= new DeliverInfoVo();
                    		deliVo2.setAddress(EmptyChecker.isEmpty(recvInfo.getAddress())?"":recvInfo.getAddress());
                    		deliVo2.setConsignee(EmptyChecker.isEmpty(recvInfo.getConsignee())?"":recvInfo.getConsignee());
                    		deliVo2.setPhone(EmptyChecker.isEmpty(recvInfo.getPhone())?"":recvInfo.getPhone());
                    		deliVo2.setRecvId(recvId);
                    		//配送方式返回配送的收货人信息和地址
                    		res.setDeliverInfoVo(deliVo2);  
                    		flag = true;
                    	}
                    }
                }
                if(ShippingStatus.shipped.getCode().equals(subOrder.getDeliveryState()) || 
                		ShippingStatus.receipt.getCode().equals(subOrder.getDeliveryState())){//已发货\已收货  
                	ShippingOrderMongo shippingInfo = orderSupport.getShippingInfo(subOrder.getOrderId()+"_"+subOrder.getSubOrderId());
                	//确认收货时间
                	if(shippingInfo!=null && shippingInfo.getReceiptTime()!=null && shippingInfo.getReceiptTime()!=0){
                		res.setConfirmReceiveTime(shippingInfo.getReceiptTime());               		
                	}
                	//发货时间
                	if(shippingInfo!=null && shippingInfo.getShippingTime()!=null && shippingInfo.getShippingTime()!=0){
                		res.setShippingTime(shippingInfo.getShippingTime());               		
                	}
                }
            }
            //收货人信息|提货人信息|消费人信息 DeliverInfoVo deliverInfoVo
            DeliverInfoVo deliverInfoVo= new DeliverInfoVo();
            if(EmptyChecker.isNotEmpty(deliname) && EmptyChecker.isNotEmpty(deliPhone) && 
            		!DeliveryType.home.getCode().equals(deOption)){
            	//deliverInfoVo.setAddress(address);
            	deliverInfoVo.setConsignee(deliname);
            	deliverInfoVo.setPhone(deliPhone);
            	//deliverInfoVo.setRecvId(recvId);  
            	res.setDeliverInfoVo(deliverInfoVo);
            }
            if(!flag){
            	String recvId = order.getDeliverId();
                if(EmptyChecker.isNotEmpty(recvId)){
                	RecvInfo recvInfo = orderSupport.getRecvInfo(order.getClientId(), order.getMemberCode(), recvId);
                    if (EmptyChecker.isNotEmpty(recvInfo)) {
                    	deliverInfoVo.setAddress(EmptyChecker.isEmpty(recvInfo.getAddress())?"":recvInfo.getAddress());
                    	deliverInfoVo.setConsignee(EmptyChecker.isEmpty(recvInfo.getConsignee())?"":recvInfo.getConsignee());
                    	deliverInfoVo.setPhone(EmptyChecker.isEmpty(recvInfo.getPhone())?"":recvInfo.getPhone());
                    	res.setDeliverInfoVo(deliverInfoVo);
                    }
                }
            }                              	            
        	res.setOrgNames(takeOrgName.toString());     
        	res.setTakeCode(tcode);//提货码
            //退款申请时间refundApplyTime
            if(minCreateTime!=1){
            	res.setRefundApplyTime(minCreateTime);            	
            }
    		//退款成功时间refundSuccessTime
            if(maxRefundTime!=1){
            	res.setRefundSuccessTime(maxRefundTime);            	
            }
            	//订单关闭原因（如果订单被关闭）closeReason
            if(OrderStatus.sysclosed.getCode().equals(subOrder.getOrderStatus())){//子订单全单退款
            	if(SubOrderRefundState.REFUND_SUCCESS.getCode().equals(subOrder.getRefundState())){
            		res.setCloseReason("用户退款");//用户退款 关闭
            	}else{
            		res.setCloseReason("系统关闭");//超过2小时 系统关闭
            	}
            }else if(OrderStatus.closed.getCode().equals(subOrder.getOrderStatus())){
            	res.setCloseReason("用户取消");//用户取消 关闭
            }         	
            res.setProductInfos(list);
        } catch (Exception e) {
            LogCvt.info("订单查询-预售详情出错：clientId:"+clientId+" subOrderId:"+subOrderId,e);
        }
        LogCvt.info("订单查询-预售详情结束：clientId:"+clientId+" subOrderId:"+subOrderId);
        return res;
    }
    /**
     * 银行管理平台名优特惠订单详情查询
     * @param clientId
     * @param subOrderId
     * @param subOrder
     * @param order
     * @return
     */   
    public GetOrderDetailByBankManageVoRes getSubOrderOfBankSpMerchantByOrderIdAndType(String clientId, String subOrderId,SubOrderMongo subOrder,OrderMongo order){
        LogCvt.info("订单查询-名优特惠详情结束：clientId:"+clientId+" subOrderId:"+subOrderId);
        GetOrderDetailByBankManageVoRes res = new GetOrderDetailByBankManageVoRes();
        try {
        	OrderDataResult orderDataResult = OrderQueryLogicHelper.getSubOrderMoney(subOrder);
        	double orderPoint = orderDataResult.getPayPointMoney();
        	
        	res.setOrderId(order.getOrderId());
        	res.setSubOrderId(subOrderId);
            res.setOrderStatus(order.getOrderStatus());
//            res.setPointDiscount(getSubOrderCutPointMoney(subOrder));
            res.setPointDiscount(orderPoint);
            //设置优惠金额
            double totalCutMoney = getSubOrderCutMoney(subOrder);
            res.setTotalCutMoney(totalCutMoney);
            //设置实付金额
//            double totalCash = getSubOrderTotalCash(subOrder);
            double totalCash = orderDataResult.getPayCashAndCutMoney();
            res.setTotalCash(totalCash);
            // res.setSubTotalMoney(getSubTotalMoney(order));
            res.setCreateTime(subOrder.getCreateTime());
            res.setMerchantName(subOrder.getMerchantName()!=null?subOrder.getMerchantName():"");
            // 增加返回订单子状态
            res.setRefundState(SubOrderRefundState.REFUND_INIT.getCode());
            try {
                String refundState = subOrder.getRefundState();
                if (EmptyChecker.isNotEmpty(refundState)) {
                    res.setRefundState(subOrder.getRefundState());
                }
            } catch (Exception e) {
            }

            if (SubOrderType.special_merchant.getCode().equals(subOrder.getType())) {
                // 名优特惠添加物流信息
                ShippingOrderMongo shippingOrderMongo = orderSupport.getShippingInfo(subOrder.getOrderId() + "_" + subOrder.getSubOrderId());
                if (EmptyChecker.isNotEmpty(shippingOrderMongo)) {
                    res.setDeliveryCorpName(shippingOrderMongo.getDeliveryCorpName());
                    res.setTrackingNo(shippingOrderMongo.getTrackingNo());
                    res.setShippingTime(shippingOrderMongo.getShippingTime());
                }
                if(ShippingStatus.shipped.getCode().equals(subOrder.getDeliveryState()) || 
                		ShippingStatus.receipt.getCode().equals(subOrder.getDeliveryState())){//已发货\已收货  
                	ShippingOrderMongo shippingInfo = orderSupport.getShippingInfo(subOrder.getOrderId()+"_"+subOrder.getSubOrderId());
                	//确认收货时间
                	if(shippingInfo!=null && shippingInfo.getReceiptTime()!=null && shippingInfo.getReceiptTime()!=0){
                		res.setConfirmReceiveTime(shippingInfo.getReceiptTime());               		
                	}
                }
            }

            List<ProductInfoVo> list = new ArrayList<ProductInfoVo>();
            double subTotalMoney = 0;
            long minCreateTime = 1l;
            long maxRefundTime = 1l;
            StringBuffer orgNames = new StringBuffer();
            Integer deMoney = 0;
            for (ProductMongo product : subOrder.getProducts()) {               
                	ProductInfoVo vo = new ProductInfoVo();                    
                    // 详细增加发货状态
                    vo.setDeliverState(subOrder.getDeliveryState());                 
                    vo.setDeliveryMoney(getDeliveryMoney(product));
                    vo.setProductName(product.getProductName());
                    vo.setQuantity(getProductQuantity(product));
                    vo.setMoney(getMoney(product));
                    vo.setSubTotalMoney(getSubTotalMoney(product));
                    if(EmptyChecker.isNotEmpty(product.getDeliveryMoney())){
                    	
                    	deMoney+=product.getDeliveryMoney();                 	
                    }
                    subTotalMoney = Arith.add(subTotalMoney, getSubTotalMoney(product));
                    // 评价1， 已评价， 否则未评价
                    if("1".equals(product.getCommentState())) {
                        vo.setIsOutletComment(true);
                    } else {
                        vo.setIsOutletComment(false);
                    }
                    vo.setProductId(product.getProductId());

                    /**退货商品*/
                    refundProduct(vo,subOrder.getSubOrderId(), subOrder.getOrderId());//新需求中不需要这个信息  
                        List<RefundHistory> refundHistoryList = refundSupport.findRefundListBySubOrderIdAndProductId(subOrder.getSubOrderId(),product.getProductId());
                        long tmpApplyCreateTime = 1l;
                        long tmpSuccessRefundTime = 1l;
                        int refundingNum = 0;
                        int refundedNum = 0;
                        String refundReasonTmp="";
                        if(EmptyChecker.isNotEmpty(refundHistoryList) && refundHistoryList.size()>0){
                   	 		for(RefundHistory refundHis:refundHistoryList){
                   	 		if(RefundState.REFUND_PROCESSING.getCode().equals(refundHis.getRefundState())
                    				|| RefundState.REFUND_SUCCESS.getCode().equals(refundHis.getRefundState())){
    	               	 		if(tmpApplyCreateTime==1){
	    	               	 		if(EmptyChecker.isNotEmpty(refundHis.getCreateTime())){
			               	 			tmpApplyCreateTime = refundHis.getCreateTime();  
			               	 		}
		    	               	 	if(EmptyChecker.isNotEmpty(refundHis.getReason())){
			               	 			refundReasonTmp = refundHis.getReason();
			               	 		}
    			               	}else if(EmptyChecker.isNotEmpty(refundHis.getCreateTime()) && tmpApplyCreateTime > refundHis.getCreateTime()){
    			               		tmpApplyCreateTime = refundHis.getCreateTime(); 
    			               		if(EmptyChecker.isNotEmpty(refundHis.getReason())){
			               	 			refundReasonTmp = refundHis.getReason();
			               	 		}
    			               	}
    		               	 	if(tmpSuccessRefundTime==1){
    		               	 		if(EmptyChecker.isNotEmpty(refundHis.getRefundTime())){
    		               	 			tmpSuccessRefundTime = refundHis.getRefundTime();     		               	 			
    		               	 		}
	    		               	 	if(EmptyChecker.isNotEmpty(refundHis.getReason())){
			               	 			refundReasonTmp = refundHis.getReason();
			               	 		}
    			               	}else if(EmptyChecker.isNotEmpty(refundHis.getRefundTime()) && tmpSuccessRefundTime < refundHis.getRefundTime()){
    			               		tmpSuccessRefundTime = refundHis.getRefundTime();
    			               		if(EmptyChecker.isNotEmpty(refundHis.getReason())){
			               	 			refundReasonTmp = refundHis.getReason();
			               	 		}
    			               	}
    	               	 		if(minCreateTime==1){
	    	               	 		if(EmptyChecker.isNotEmpty(refundHis.getCreateTime())){
			               	 			minCreateTime = refundHis.getCreateTime();                           		 
			               	 		}
    			               	}else if(EmptyChecker.isNotEmpty(refundHis.getCreateTime()) && minCreateTime > refundHis.getCreateTime()){
    			               		 minCreateTime = refundHis.getCreateTime();  
    			               	}
    		               	 	if(maxRefundTime==1){
	    		               	 	if(EmptyChecker.isNotEmpty(refundHis.getRefundTime())){
			               	 			maxRefundTime = refundHis.getRefundTime();                           		 
			               	 		}
    			               	}else if(EmptyChecker.isNotEmpty(refundHis.getRefundTime()) && maxRefundTime < refundHis.getRefundTime()){
    			               		maxRefundTime = refundHis.getRefundTime();  
    			               	}
    		               	 	if(RefundState.REFUND_PROCESSING.getCode().equals(refundHis.getRefundState())){
    		               	 		refundingNum+=refundHis.getShoppingInfo().get(0).getProducts().get(0).getQuantity();//退款中个数
    		               	 	}
    		               	 	if(RefundState.REFUND_SUCCESS.getCode().equals(refundHis.getRefundState())){
    		               	 		refundedNum+=refundHis.getShoppingInfo().get(0).getProducts().get(0).getQuantity();//已退款个数
    		               	 	}
                   	 		}
                   	 		}
                   	 	 Integer proNum = vo.getQuantity();
                         Integer refNum = refundingNum+refundedNum;
                         if(EmptyChecker.isNotEmpty(proNum) && EmptyChecker.isNotEmpty(refNum)){
                        	 if(refNum > 0 && refNum < proNum && refundedNum > 0 && refundedNum == refNum){
                        		 vo.setRefundState("1");//预售、名优特惠详情商品退款状态1:部分退款
                        	 }else if(refNum > 0 && refNum < proNum && refundedNum > 0 && refundedNum < refNum && refundingNum>0){
                        		 vo.setRefundState("2");//预售、名优特惠详情商品退款状态2:部分退款和退款中都有 就显示退款中
                        	 }else if(refNum > 0 && refNum == proNum && refundedNum == refNum){
                        		 vo.setRefundState("3");//预售、名优特惠详情商品退款状态3:已退款
                        	 }else if(refNum > 0 && refNum == proNum && refundingNum>0){
                        		 vo.setRefundState("2");//名优特惠详情商品退款状态2:退款中
                        	 }
//                        	 else{
//                        		 vo.setRefundState("2");//预售、名优特惠详情商品退款状态2:退款中
//                        	 }
                         }
                   	 	}
                        if(tmpApplyCreateTime!=1 && tmpApplyCreateTime!=0){
                        	vo.setRefundApplyTime(tmpApplyCreateTime);//退款申请时间
                        }
                        if(tmpSuccessRefundTime!=1 && tmpSuccessRefundTime!=0){
                        	vo.setRefundSuccessTime(tmpSuccessRefundTime);//退款成功时间
                        }
                        if(refundReasonTmp!=null && !"".equals(refundReasonTmp)){
                        	vo.setRefundReason(refundReasonTmp);//退款原因
                        }                                        
                    
                    list.add(vo);
            }
            if(deMoney!=0){
        		res.setDeliveryMoney(Arith.div(Long.valueOf(deMoney), 1000));       		
        	}
            res.setSubTotalMoney(subTotalMoney);
            //订单优化增加	//支付时间paymentTime
            if(EmptyChecker.isNotEmpty(order.getPaymentTime())){
            	res.setPaymentTime(order.getPaymentTime());            	
            }
    		String recvId = order.getRecvId();
            if(EmptyChecker.isNotEmpty(recvId)){
//            	RecvInfo recvInfo = orderSupport.getRecvInfo(order.getClientId(), order.getMemberCode(), recvId);
//                if (EmptyChecker.isNotEmpty(recvInfo)) {
//                	//收货人信息|提货人信息|消费人信息 DeliverInfoVo deliverInfoVo
//                	DeliverInfoVo deliVo= new DeliverInfoVo();
//                	deliVo.setAddress(EmptyChecker.isEmpty(recvInfo.getAddress())?"":recvInfo.getAddress());
//                	deliVo.setConsignee(EmptyChecker.isEmpty(recvInfo.getConsignee())?"":recvInfo.getConsignee());
//                	deliVo.setPhone(EmptyChecker.isEmpty(recvInfo.getPhone())?"":recvInfo.getPhone());
//                	deliVo.setRecvId(recvId);
//                	//配送方式返回配送的收货人信息和地址
//                	res.setDeliverInfoVo(deliVo);
//                }
            	List<SubOrderMongo> subOrderList = new ArrayList<SubOrderMongo>();
            	subOrderList.add(subOrder);
            	Map<String,RecvInfo> recvMap =  orderSupport.getRecvInfoMap(subOrderList,true);//true表示要返回省市区
            	RecvInfo recvInfo = recvMap.get(subOrder.getOrderId());
                if (EmptyChecker.isNotEmpty(recvInfo)) {
                	//收货人信息|提货人信息|消费人信息 DeliverInfoVo deliverInfoVo
                	DeliverInfoVo deliVo= new DeliverInfoVo();
                	deliVo.setAddress(EmptyChecker.isEmpty(recvInfo.getAddress())?"":recvInfo.getAddress());
                	deliVo.setConsignee(EmptyChecker.isEmpty(recvInfo.getConsignee())?"":recvInfo.getConsignee());
                	deliVo.setPhone(EmptyChecker.isEmpty(recvInfo.getPhone())?"":recvInfo.getPhone());
                	deliVo.setRecvId(recvId);
                	//配送方式返回配送的收货人信息和地址
                	res.setDeliverInfoVo(deliVo);
                }else{
                	RecvInfo recvInfo2 = orderSupport.getRecvInfo(order.getClientId(), order.getMemberCode(), recvId);
                	if(EmptyChecker.isNotEmpty(recvInfo2)){
                		//收货人信息|提货人信息|消费人信息 DeliverInfoVo deliverInfoVo
                		DeliverInfoVo deliVo2= new DeliverInfoVo();
                		deliVo2.setAddress(EmptyChecker.isEmpty(recvInfo2.getAddress())?"":recvInfo2.getAddress());
                		deliVo2.setConsignee(EmptyChecker.isEmpty(recvInfo2.getConsignee())?"":recvInfo2.getConsignee());
                		deliVo2.setPhone(EmptyChecker.isEmpty(recvInfo2.getPhone())?"":recvInfo2.getPhone());
                		deliVo2.setRecvId(recvId);
                		//配送方式返回配送的收货人信息和地址
                		res.setDeliverInfoVo(deliVo2);                   		
                	}
                }
            }
                
        	//所属机构           orgNames
            if(subOrder.getForgCode()!= null && !"".equals(subOrder.getForgCode())){
            	Org orgForg = commonLogic.getOrgByOrgCode(subOrder.getForgCode(), clientId);
            	if(EmptyChecker.isNotEmpty(orgForg) && EmptyChecker.isNotEmpty(orgForg.getOrgName())){
            		orgNames.append(orgForg.getOrgName());               		
            	}
            	if(subOrder.getSorgCode()!= null && !"".equals(subOrder.getSorgCode())){
                	Org orgSorg = commonLogic.getOrgByOrgCode(subOrder.getSorgCode(), clientId);
                	if(EmptyChecker.isNotEmpty(orgSorg) && EmptyChecker.isNotEmpty(orgSorg.getOrgName())){
                		orgNames.append("-").append(orgSorg.getOrgName());                    		
                	}
                	if(subOrder.getTorgCode()!= null && !"".equals(subOrder.getTorgCode())){
                    	Org orgTorg = commonLogic.getOrgByOrgCode(subOrder.getTorgCode(), clientId);
                    	if(EmptyChecker.isNotEmpty(orgTorg) && EmptyChecker.isNotEmpty(orgTorg.getOrgName())){
                    		orgNames.append("-").append(orgTorg.getOrgName());
                    	}
                    	if(subOrder.getLorgCode()!= null && !"".equals(subOrder.getLorgCode())){
                        	Org orgLorg = commonLogic.getOrgByOrgCode(subOrder.getLorgCode(), clientId);
                        	if(EmptyChecker.isNotEmpty(orgLorg) && EmptyChecker.isNotEmpty(orgLorg.getOrgName())){
                        		orgNames.append("-").append(orgLorg.getOrgName());
                        	}
                        }
                    }
                }
            }
            res.setOrgNames(orgNames.toString());
            //退款申请时间refundApplyTime
            if(minCreateTime!=1){
            	res.setRefundApplyTime(minCreateTime);            	
            }
    		//退款成功时间refundSuccessTime
            if(maxRefundTime!=1){
            	res.setRefundSuccessTime(maxRefundTime);            	
            }
            	//订单关闭原因（如果订单被关闭）closeReason
            if(OrderStatus.sysclosed.getCode().equals(subOrder.getOrderStatus())){//子订单全单退款
            	if(SubOrderRefundState.REFUND_SUCCESS.getCode().equals(subOrder.getRefundState())){
            		res.setCloseReason("用户退款");//用户退款 关闭
            	}else{
            		res.setCloseReason("系统关闭");//超过2小时 系统关闭
            	}
            }else if(OrderStatus.closed.getCode().equals(subOrder.getOrderStatus())){
            	res.setCloseReason("用户取消");//用户取消 关闭
            }     
            List<Settlement> settlements = settlementSupport.querySettlement(order.getOrderId());
            if (EmptyChecker.isEmpty(settlements)) {
                res.setSettleState(SettlementStatus.unsettlemnt.getCode());//结算状态
            } else {
                res.setSettleState(settlements.get(0).getSettleState());//结算状态
            }
            res.setProductInfos(list);
        } catch (Exception e) {
            LogCvt.info("订单查询-名优特惠详情出错-耗时：clientId:"+clientId+" subOrderId:"+subOrderId,e);
        }
        LogCvt.info("订单查询-名优特惠详情结束：clientId:"+clientId+" subOrderId:"+subOrderId);
        return res;
    }
    /**
     * 银行管理平台积分兑换订单详情查询
     * @param clientId
     * @param subOrderId
     * @param subOrder
     * @param order
     * @return
     */
    
    public GetOrderDetailByBankManageVoRes getSubOrderOfBankPointByOrderIdAndType(String clientId, String subOrderId,SubOrderMongo subOrder,OrderMongo order){
        LogCvt.info("订单查询-积分兑换详情结束：clientId:"+clientId+" subOrderId:"+subOrderId);
        GetOrderDetailByBankManageVoRes res = new GetOrderDetailByBankManageVoRes();
        try {
        	OrderDataResult orderDataResult = OrderQueryLogicHelper.getSubOrderMoney(subOrder);
        	double orderPoint = orderDataResult.getPayPointMoney();
        	
        	res.setOrderId(order.getOrderId());
        	res.setSubOrderId(subOrderId);
            res.setOrderStatus(order.getOrderStatus());
//            res.setPointDiscount(getSubOrderExchangePoint(subOrder));
            res.setPointDiscount(orderPoint);
            //设置优惠金额
            double totalCutMoney = getSubOrderCutMoney(subOrder);
            res.setTotalCutMoney(totalCutMoney);
            //设置实付金额
//          double totalCash = getSubOrderTotalCash(subOrder);
            double totalCash = orderDataResult.getPayCashAndCutMoney();
            res.setTotalCash(totalCash);
            // res.setSubTotalMoney(getSubTotalMoney(order));
            res.setCreateTime(subOrder.getCreateTime());
            res.setMerchantName(subOrder.getMerchantName()!=null?subOrder.getMerchantName():"");
            // 增加返回订单子状态
            res.setRefundState(SubOrderRefundState.REFUND_INIT.getCode());
            try {
                String refundState = subOrder.getRefundState();
                if (EmptyChecker.isNotEmpty(refundState)) {
                    res.setRefundState(subOrder.getRefundState());
                }
            } catch (Exception e) {
            }
            List<ProductInfoVo> list = new ArrayList<ProductInfoVo>();
            double subTotalMoney = 0;
            StringBuffer orgNames = new StringBuffer();
            for (ProductMongo product : subOrder.getProducts()) {               
                	ProductInfoVo vo = new ProductInfoVo();
                    // 详细增加发货状态              
                    // 配送方式
                    vo.setDeliveryOption(product.getDeliveryOption());
                    // 未支付完成没有提货状态
                    if (OrderStatus.paysuccess.getCode().equals(subOrder.getOrderStatus()) || OrderStatus.sysclosed.getCode().equals(subOrder.getOrderStatus())) {
                        if (EmptyChecker.isEmpty(product.getDeliveryState())) {
                            vo.setDeliverState(ShippingStatus.untake.getCode());
                        } else {
                            vo.setDeliverState(product.getDeliveryState());
                        }
                    }

                    vo.setDeliveryMoney(getDeliveryMoney(product));
                    vo.setProductName(product.getProductName());
                    vo.setQuantity(getProductQuantity(product));
                    vo.setMoney(getMoney(product));
                    vo.setSubTotalMoney(getSubTotalMoney(product));

                    subTotalMoney = Arith.add(subTotalMoney, getSubTotalMoney(product));
                    // 评价1， 已评价， 否则未评价
                    if("1".equals(product.getCommentState())) {
                        vo.setIsOutletComment(true);
                    } else {
                        vo.setIsOutletComment(false);
                    }
                    vo.setProductId(product.getProductId());
                    /**退货商品*/
                    refundProduct(vo,subOrder.getSubOrderId(), subOrder.getOrderId());//新需求中不需要这个信息                      
                    list.add(vo);
            }
            res.setSubTotalMoney(subTotalMoney);
            //订单优化增加	//支付时间paymentTime
            if(EmptyChecker.isNotEmpty(order.getPaymentTime())){
            	res.setPaymentTime(order.getPaymentTime());            	
            }            	
            
            if(OrderType.online_points_org.getCode().equals(subOrder.getType())){//线上积分
        		String recvId = order.getRecvId();
                if(EmptyChecker.isNotEmpty(recvId)){
                	//RecvInfo recvInfo = orderSupport.getRecvInfo(order.getClientId(), order.getMemberCode(), recvId);
                	List<SubOrderMongo> subOrderList = new ArrayList<SubOrderMongo>();
                	subOrderList.add(subOrder);
                	Map<String,RecvInfo> recvMap =  orderSupport.getRecvInfoMap(subOrderList,true);//true表示要返回省市区
                	RecvInfo recvInfo = recvMap.get(subOrder.getOrderId());
                    if (EmptyChecker.isNotEmpty(recvInfo)) {
                    	//收货人信息|提货人信息|消费人信息 DeliverInfoVo deliverInfoVo
                    	DeliverInfoVo deliVo= new DeliverInfoVo();
                    	deliVo.setAddress(EmptyChecker.isEmpty(recvInfo.getAddress())?"":recvInfo.getAddress());
                    	deliVo.setConsignee(EmptyChecker.isEmpty(recvInfo.getConsignee())?"":recvInfo.getConsignee());
                    	deliVo.setPhone(EmptyChecker.isEmpty(recvInfo.getPhone())?"":recvInfo.getPhone());
                    	deliVo.setRecvId(recvId);
                    	//配送方式返回配送的收货人信息和地址
                    	res.setDeliverInfoVo(deliVo);
                    }else{
                    	RecvInfo recvInfo2 = orderSupport.getRecvInfo(order.getClientId(), order.getMemberCode(), recvId);
                    	if(EmptyChecker.isNotEmpty(recvInfo2)){
                    		//收货人信息|提货人信息|消费人信息 DeliverInfoVo deliverInfoVo
                    		DeliverInfoVo deliVo2= new DeliverInfoVo();
                    		deliVo2.setAddress(EmptyChecker.isEmpty(recvInfo.getAddress())?"":recvInfo.getAddress());
                    		deliVo2.setConsignee(EmptyChecker.isEmpty(recvInfo.getConsignee())?"":recvInfo.getConsignee());
                    		deliVo2.setPhone(EmptyChecker.isEmpty(recvInfo.getPhone())?"":recvInfo.getPhone());
                    		deliVo2.setRecvId(recvId);
                    		//配送方式返回配送的收货人信息和地址
                    		res.setDeliverInfoVo(deliVo2);                   		
                    	}
                    }
                }
                if(ShippingStatus.shipped.getCode().equals(subOrder.getDeliveryState()) || 
                		ShippingStatus.receipt.getCode().equals(subOrder.getDeliveryState())){//已发货\已收货  
                	ShippingOrderMongo shippingInfo = orderSupport.getShippingInfo(subOrder.getOrderId()+"_"+subOrder.getSubOrderId());
                	//确认收货时间
                	if(shippingInfo!=null && shippingInfo.getReceiptTime()!=null && shippingInfo.getReceiptTime()!=0){
                		res.setConfirmReceiveTime(shippingInfo.getReceiptTime());               		
                	}
                	//发货时间
                	if(shippingInfo!=null && shippingInfo.getShippingTime()!=null && shippingInfo.getShippingTime()!=0){
                		res.setShippingTime(shippingInfo.getShippingTime());               		
                	}
                }
            }
            
        	//所属机构           orgNames
            if(subOrder.getForgCode()!= null && !"".equals(subOrder.getForgCode())){
            	Org orgForg = commonLogic.getOrgByOrgCode(subOrder.getForgCode(), clientId);
            	if(EmptyChecker.isNotEmpty(orgForg) && EmptyChecker.isNotEmpty(orgForg.getOrgName())){
            		orgNames.append(orgForg.getOrgName());               		
            	}
            	if(subOrder.getSorgCode()!= null && !"".equals(subOrder.getSorgCode())){
                	Org orgSorg = commonLogic.getOrgByOrgCode(subOrder.getSorgCode(), clientId);
                	if(EmptyChecker.isNotEmpty(orgSorg) && EmptyChecker.isNotEmpty(orgSorg.getOrgName())){
                		orgNames.append("-").append(orgSorg.getOrgName());                    		
                	}
                	if(subOrder.getTorgCode()!= null && !"".equals(subOrder.getTorgCode())){
                    	Org orgTorg = commonLogic.getOrgByOrgCode(subOrder.getTorgCode(), clientId);
                    	if(EmptyChecker.isNotEmpty(orgTorg) && EmptyChecker.isNotEmpty(orgTorg.getOrgName())){
                    		orgNames.append("-").append(orgTorg.getOrgName());
                    	}
                    	if(subOrder.getLorgCode()!= null && !"".equals(subOrder.getLorgCode())){
                        	Org orgLorg = commonLogic.getOrgByOrgCode(subOrder.getLorgCode(), clientId);
                        	if(EmptyChecker.isNotEmpty(orgLorg) && EmptyChecker.isNotEmpty(orgLorg.getOrgName())){
                        		orgNames.append("-").append(orgLorg.getOrgName());
                        	}
                        }
                    }
                }
            }
            res.setOrgNames(orgNames.toString());
            	//订单关闭原因（如果订单被关闭）closeReason
            if(OrderStatus.sysclosed.getCode().equals(subOrder.getOrderStatus())){//子订单全单退款
            	if(SubOrderRefundState.REFUND_SUCCESS.getCode().equals(subOrder.getRefundState())){
            		res.setCloseReason("用户退款");//用户退款 关闭
            	}else{
            		res.setCloseReason("系统关闭");//超过2小时 系统关闭
            	}
            }else if(OrderStatus.closed.getCode().equals(subOrder.getOrderStatus())){
            	res.setCloseReason("用户取消");//用户取消 关闭
            }    
            //积分兑换业务类型
            if(EmptyChecker.isNotEmpty(subOrder.getType())){
            	res.setBusinessType(subOrder.getType());
            }
            if(EmptyChecker.isNotEmpty(subOrder.getOperatorId())){
            	res.setMerchantUserId(Long.valueOf(subOrder.getOperatorId()));            	
            }
            if(EmptyChecker.isNotEmpty(subOrder.getOperatorName())){
            	res.setMerchantUserName(subOrder.getOperatorName());            	
            }
            res.setProductInfos(list);
        } catch (Exception e) {
            LogCvt.info("订单查询-积分兑换详情出错-耗时：clientId:"+clientId+" subOrderId:"+subOrderId,e);
        }
        LogCvt.info("订单查询-积分兑换详情结束：clientId:"+clientId+" subOrderId:"+subOrderId);
        return res;
    }
    
    /**
     * 精品商城订单详情查询
     * 说明   description of the class
     * 创建日期  2016年2月25日  上午10:36:18
     * 作者  artPing
     * 参数  @param clientId
     * 参数  @param subOrderId
     * 参数  @param subOrder
     * 参数  @param order
     * 参数  @return
     */
    public GetOrderDetailByBankManageVoRes getSubOrderOfBankBoutiqueByOrderIdAndType(String clientId, String subOrderId,SubOrderMongo subOrder,OrderMongo order){
        LogCvt.info("订单查询-精品商城详情开始：clientId:"+clientId+" subOrderId:"+subOrderId);
        GetOrderDetailByBankManageVoRes res = new GetOrderDetailByBankManageVoRes();
        try {
        	OrderDataResult orderDataResult = OrderQueryLogicHelper.getSubOrderMoney(subOrder);
        	double orderPoint = orderDataResult.getPayPointMoney();
        	
        	res.setOrderId(order.getOrderId());//大订单
        	res.setSubOrderId(subOrderId);//子订单
            res.setOrderStatus(order.getOrderStatus());//订单状态
            res.setPointDiscount(orderPoint);//积分抵扣
            res.setCreateTime(order.getCreateTime());//下单时间
            res.setMerchantName(EmptyChecker.isNotEmpty(subOrder.getMerchantName()) ? subOrder.getMerchantName() : "");
            res.setMemberName(order.getMemberName());
            res.setDeliveryState(subOrder.getDeliveryState());
            double totalCutMoney = getSubOrderCutMoney(subOrder);
            res.setTotalCutMoney(totalCutMoney);//优惠金额
            double totalCash = orderDataResult.getPayCashAndCutMoney();
            res.setTotalCash(totalCash);//实付金额
            res.setPhone(order.getPhone());
            
            //付款时间
            if(EmptyChecker.isNotEmpty(order.getPaymentTime())){
            	res.setPaymentTime(order.getPaymentTime());            	
            }
            
            //订单关闭原因（如果订单被关闭）closeReason
            if(OrderStatus.sysclosed.getCode().equals(subOrder.getOrderStatus())){//子订单全单退款
            	if(SubOrderRefundState.REFUND_SUCCESS.getCode().equals(subOrder.getRefundState())){
            		res.setCloseReason("用户退款");//用户退款 关闭
            	}else{
            		res.setCloseReason("系统关闭");//超过2小时 系统关闭
            	}
            }else if(OrderStatus.closed.getCode().equals(subOrder.getOrderStatus())){
            	res.setCloseReason("用户取消");//用户取消 关闭
            }
            // 增加返回订单子状态
            res.setRefundState(SubOrderRefundState.REFUND_INIT.getCode());
            if (EmptyChecker.isNotEmpty(subOrder.getRefundState())) {
                res.setRefundState(subOrder.getRefundState());
            }
            
            if (SubOrderType.boutique.getCode().equals(subOrder.getType())) {
                // 精品商城添加物流信息
                ShippingOrderMongo shippingOrderMongo = orderSupport.getShippingInfo(subOrder.getOrderId() + "_" + subOrder.getSubOrderId());
                if (EmptyChecker.isNotEmpty(shippingOrderMongo)) {
                    res.setDeliveryCorpName(shippingOrderMongo.getDeliveryCorpName());//物流公司
                    res.setTrackingNo(shippingOrderMongo.getTrackingNo());//物流单号
                    res.setShippingTime(shippingOrderMongo.getShippingTime());//发货时间
                }
                if(ShippingStatus.shipped.getCode().equals(subOrder.getDeliveryState()) || 
                		ShippingStatus.receipt.getCode().equals(subOrder.getDeliveryState())){//已发货\已收货  
                	ShippingOrderMongo shippingInfo = orderSupport.getShippingInfo(subOrder.getOrderId()+"_"+subOrder.getSubOrderId());
                	//确认收货时间
                	if(shippingInfo!=null && shippingInfo.getReceiptTime()!=null && shippingInfo.getReceiptTime()!=0){
                		res.setConfirmReceiveTime(shippingInfo.getReceiptTime());               		
                	}
                }
                res.setDeliveryOption(DeliveryType.home.getCode());//配送方式
            }

            List<ProductInfoVo> list = new ArrayList<ProductInfoVo>();
            double subTotalMoney = 0;
            long minCreateTime = 1l;
            long maxRefundTime = 1l;
            StringBuffer orgNames = new StringBuffer();
            Integer deMoney = 0;
            for (ProductMongo product : subOrder.getProducts()) {    
        	    //商品信息
            	ProductInfoVo vo = new ProductInfoVo();                    
                vo.setDeliverState(subOrder.getDeliveryState());//商品发货状态                 
                vo.setDeliveryMoney(getDeliveryMoney(product));//物流费用
                vo.setProductName(product.getProductName());//商品名称
                vo.setQuantity(getProductQuantity(product));//商品数据量
                vo.setMoney(getMoney(product));//商品单价
                vo.setSubTotalMoney(getSubTotalMoney(product));//商品总价
                vo.setVipMoney(product.getVipMoney()>0?getVipMoney(product):0);//会员商品单价价格
                vo.setVipQuantity(product.getVipQuantity());
                
                //发货状态
                if(EmptyChecker.isNotEmpty(product.getDeliveryMoney())){
                	deMoney+=product.getDeliveryMoney();                 	
                }
                subTotalMoney = Arith.add(subTotalMoney, getSubTotalMoney(product));
                vo.setProductId(product.getProductId());
                
                //商品退款信息
                refundProduct(vo,subOrder.getSubOrderId(), subOrder.getOrderId());//新需求中不需要这个信息  
                List<RefundHistory> refundHistoryList = refundSupport.findRefundListBySubOrderIdAndProductId(subOrder.getSubOrderId(),product.getProductId());
                long tmpApplyCreateTime = 1l;
                long tmpSuccessRefundTime = 1l;
                int refundingNum = 0;
                int refundedNum = 0;
                String refundReasonTmp="";
                if(EmptyChecker.isNotEmpty(refundHistoryList) && refundHistoryList.size()>0){
           	 		for(RefundHistory refundHis:refundHistoryList){
           	 		if(RefundState.REFUND_PROCESSING.getCode().equals(refundHis.getRefundState())
            				|| RefundState.REFUND_SUCCESS.getCode().equals(refundHis.getRefundState())){
               	 		if(tmpApplyCreateTime==1){
	               	 		if(EmptyChecker.isNotEmpty(refundHis.getCreateTime())){
	               	 			tmpApplyCreateTime = refundHis.getCreateTime();  
	               	 		}
    	               	 	if(EmptyChecker.isNotEmpty(refundHis.getReason())){
	               	 			refundReasonTmp = refundHis.getReason();
	               	 		}
		               	}else if(EmptyChecker.isNotEmpty(refundHis.getCreateTime()) && tmpApplyCreateTime > refundHis.getCreateTime()){
		               		tmpApplyCreateTime = refundHis.getCreateTime(); 
		               		if(EmptyChecker.isNotEmpty(refundHis.getReason())){
	               	 			refundReasonTmp = refundHis.getReason();
	               	 		}
		               	}
	               	 	if(tmpSuccessRefundTime==1){
	               	 		if(EmptyChecker.isNotEmpty(refundHis.getRefundTime())){
	               	 			tmpSuccessRefundTime = refundHis.getRefundTime();     		               	 			
	               	 		}
		               	 	if(EmptyChecker.isNotEmpty(refundHis.getReason())){
	               	 			refundReasonTmp = refundHis.getReason();
	               	 		}
		               	}else if(EmptyChecker.isNotEmpty(refundHis.getRefundTime()) && tmpSuccessRefundTime < refundHis.getRefundTime()){
		               		tmpSuccessRefundTime = refundHis.getRefundTime();
		               		if(EmptyChecker.isNotEmpty(refundHis.getReason())){
	               	 			refundReasonTmp = refundHis.getReason();
	               	 		}
		               	}
               	 		if(minCreateTime==1){
	               	 		if(EmptyChecker.isNotEmpty(refundHis.getCreateTime())){
	               	 			minCreateTime = refundHis.getCreateTime();                           		 
	               	 		}
		               	}else if(EmptyChecker.isNotEmpty(refundHis.getCreateTime()) && minCreateTime > refundHis.getCreateTime()){
		               		 minCreateTime = refundHis.getCreateTime();  
		               	}
	               	 	if(maxRefundTime==1){
		               	 	if(EmptyChecker.isNotEmpty(refundHis.getRefundTime())){
	               	 			maxRefundTime = refundHis.getRefundTime();                           		 
	               	 		}
		               	}else if(EmptyChecker.isNotEmpty(refundHis.getRefundTime()) && maxRefundTime < refundHis.getRefundTime()){
		               		maxRefundTime = refundHis.getRefundTime();  
		               	}
	               	 	if(RefundState.REFUND_PROCESSING.getCode().equals(refundHis.getRefundState())){
	               	 		refundingNum+=refundHis.getShoppingInfo().get(0).getProducts().get(0).getQuantity();//退款中个数
	               	 	}
	               	 	if(RefundState.REFUND_SUCCESS.getCode().equals(refundHis.getRefundState())){
	               	 		refundedNum+=refundHis.getShoppingInfo().get(0).getProducts().get(0).getQuantity();//已退款个数
	               	 	}
           	 		}
           	 		}
           	 	 Integer proNum = vo.getQuantity();
                 Integer refNum = refundingNum+refundedNum;
                 if(EmptyChecker.isNotEmpty(proNum) && EmptyChecker.isNotEmpty(refNum)){
                	 if(refNum > 0 && refNum < proNum && refundedNum > 0 && refundedNum == refNum){
                		 vo.setRefundState("1");//预售、名优特惠详情商品退款状态1:部分退款
                	 }else if(refNum > 0 && refNum < proNum && refundedNum > 0 && refundedNum < refNum && refundingNum>0){
                		 vo.setRefundState("2");//预售、名优特惠详情商品退款状态2:部分退款和退款中都有 就显示退款中
                	 }else if(refNum > 0 && refNum == proNum && refundedNum == refNum){
                		 vo.setRefundState("3");//预售、名优特惠详情商品退款状态3:已退款
                	 }else if(refNum > 0 && refNum == proNum && refundingNum>0){
                		 vo.setRefundState("2");//名优特惠详情商品退款状态2:退款中
                	 }
                 }
           	 	}
                if(tmpApplyCreateTime!=1 && tmpApplyCreateTime!=0){
                	vo.setRefundApplyTime(tmpApplyCreateTime);//退款申请时间
                }
                if(tmpSuccessRefundTime!=1 && tmpSuccessRefundTime!=0){
                	vo.setRefundSuccessTime(tmpSuccessRefundTime);//退款成功时间
                }
                if(refundReasonTmp!=null && !"".equals(refundReasonTmp)){
                	vo.setRefundReason(refundReasonTmp);//退款原因
                }
                
                vo.setRefundingNumber(refundingNum>0?refundingNum:0);//退款中的数量
                vo.setRefundNumber(refundedNum>0?refundedNum:0);//退款成功数量
                list.add(vo);                
            }
            if(deMoney!=0){
        		res.setDeliveryMoney(Arith.div(Long.valueOf(deMoney), 1000));       		
        	}
            res.setSubTotalMoney(subTotalMoney);//总价（含物流费用）

    		String recvId = order.getRecvId();
            if(EmptyChecker.isNotEmpty(recvId)){
            	List<SubOrderMongo> subOrderList = new ArrayList<SubOrderMongo>();
            	subOrderList.add(subOrder);
            	Map<String,RecvInfo> recvMap =  orderSupport.getRecvInfoMap(subOrderList,true);//true表示要返回省市区
            	RecvInfo recvInfo = recvMap.get(subOrder.getOrderId());
                if (EmptyChecker.isNotEmpty(recvInfo)) {
                	//收货人信息|提货人信息|消费人信息 DeliverInfoVo deliverInfoVo
                	DeliverInfoVo deliVo= new DeliverInfoVo();
                	deliVo.setAddress(EmptyChecker.isEmpty(recvInfo.getAddress())?"":recvInfo.getAddress());
                	deliVo.setConsignee(EmptyChecker.isEmpty(recvInfo.getConsignee())?"":recvInfo.getConsignee());
                	deliVo.setPhone(EmptyChecker.isEmpty(recvInfo.getPhone())?"":recvInfo.getPhone());
                	deliVo.setRecvId(recvId);
                	//配送方式返回配送的收货人信息和地址
                	res.setDeliverInfoVo(deliVo);
                }else{
                	RecvInfo recvInfo2 = orderSupport.getRecvInfo(order.getClientId(), order.getMemberCode(), recvId);
                	if(EmptyChecker.isNotEmpty(recvInfo2)){
                		//收货人信息|提货人信息|消费人信息 DeliverInfoVo deliverInfoVo
                		DeliverInfoVo deliVo2= new DeliverInfoVo();
                		deliVo2.setAddress(EmptyChecker.isEmpty(recvInfo2.getAddress())?"":recvInfo2.getAddress());
                		deliVo2.setConsignee(EmptyChecker.isEmpty(recvInfo2.getConsignee())?"":recvInfo2.getConsignee());
                		deliVo2.setPhone(EmptyChecker.isEmpty(recvInfo2.getPhone())?"":recvInfo2.getPhone());
                		deliVo2.setRecvId(recvId);
                		//配送方式返回配送的收货人信息和地址
                		res.setDeliverInfoVo(deliVo2);                   		
                	}
                }
            }
                
        	//所属机构           orgNames
            if(subOrder.getForgCode()!= null && !"".equals(subOrder.getForgCode())){
            	Org orgForg = commonLogic.getOrgByOrgCode(subOrder.getForgCode(), clientId);
            	if(EmptyChecker.isNotEmpty(orgForg) && EmptyChecker.isNotEmpty(orgForg.getOrgName())){
            		orgNames.append(orgForg.getOrgName());               		
            	}
            	if(subOrder.getSorgCode()!= null && !"".equals(subOrder.getSorgCode())){
                	Org orgSorg = commonLogic.getOrgByOrgCode(subOrder.getSorgCode(), clientId);
                	if(EmptyChecker.isNotEmpty(orgSorg) && EmptyChecker.isNotEmpty(orgSorg.getOrgName())){
                		orgNames.append("-").append(orgSorg.getOrgName());                    		
                	}
                	if(subOrder.getTorgCode()!= null && !"".equals(subOrder.getTorgCode())){
                    	Org orgTorg = commonLogic.getOrgByOrgCode(subOrder.getTorgCode(), clientId);
                    	if(EmptyChecker.isNotEmpty(orgTorg) && EmptyChecker.isNotEmpty(orgTorg.getOrgName())){
                    		orgNames.append("-").append(orgTorg.getOrgName());
                    	}
                    	if(subOrder.getLorgCode()!= null && !"".equals(subOrder.getLorgCode())){
                        	Org orgLorg = commonLogic.getOrgByOrgCode(subOrder.getLorgCode(), clientId);
                        	if(EmptyChecker.isNotEmpty(orgLorg) && EmptyChecker.isNotEmpty(orgLorg.getOrgName())){
                        		orgNames.append("-").append(orgLorg.getOrgName());
                        	}
                        }
                    }
                }
            }
            res.setOrgNames(orgNames.toString());
            //退款申请时间refundApplyTime
            if(minCreateTime!=1){
            	res.setRefundApplyTime(minCreateTime);            	
            }
    		//退款成功时间refundSuccessTime
            if(maxRefundTime!=1){
            	res.setRefundSuccessTime(maxRefundTime);            	
            }
     
            List<Settlement> settlements = settlementSupport.querySettlement(order.getOrderId());
            if (EmptyChecker.isEmpty(settlements)) {
                res.setSettleState(SettlementStatus.unsettlemnt.getCode());//结算状态
            } else {
                res.setSettleState(settlements.get(0).getSettleState());//结算状态
            }
            res.setProductInfos(list);
        } catch (Exception e) {
        	OrderLogger.error("订单模块", "银行管理平台-精品商城订单详情", "查询异常", new Object[]{"clientId",clientId,"subOrderId",subOrderId});
            LogCvt.info("订单查询-精品商城详情出错-耗时：clientId:"+clientId+" subOrderId:"+subOrderId,e);
        }
        LogCvt.info("订单查询-精品商城详情结束：clientId:"+clientId+" subOrderId:"+subOrderId);
        return res;
    }

    
    @SuppressWarnings("unchecked")
    @Override
    public ResultBean  queryFacetfaceOrderByMerchantManage(OrderQueryMerchantManageCondition condition, PageVo pageVo) throws FroadBusinessException {
        // 商户管理查询查询面对面订单
        ResultBean rb = new ResultBean(ResultCode.success);
        try {
            PageEntity<OrderQueryMerchantManageCondition> pageEntity = new PageEntity<OrderQueryMerchantManageCondition>();

            pageEntity.setCondition(condition);

            MongoPage mongoPage = null;

            List<QueryOrderByMerchantManageVo> listVo = new ArrayList<QueryOrderByMerchantManageVo>();

            List<OrderMongo> list = new ArrayList<OrderMongo>();

           
            if (OperType.QUERY.getCode().equals(condition.getOperType())) {
            	long st1 = System.currentTimeMillis();
                // 只有查询有分页数据
                pageEntity.convert(pageVo);
                mongoPage = orderSupport.queryOrderByFacetfaceOfMerchantConditionOfPage(pageEntity);
                if (EmptyChecker.isEmpty(mongoPage)) {
                    ResultCode rc = ResultCode.member_order_is_empty;
                    throw new FroadBusinessException(rc.getCode(), rc.getMsg());
                }
                convertPage(mongoPage, pageVo);

                list = (List<OrderMongo>) mongoPage.getItems();
                LogCvt.info("订单查询-分页列表-耗时：" + (System.currentTimeMillis()-st1));
            } else {
            	long st1 = System.currentTimeMillis();
                list = (List<OrderMongo>) orderSupport.queryOrderByFacetfaceOfMerchantConditionOfAll(condition);
                LogCvt.info("订单查询-全部列表-耗时：" + (System.currentTimeMillis()-st1));
            }

            if (EmptyChecker.isEmpty(list)) {
                LogCvt.error("结果为空, 操作（查询|下载）类型：" + condition.getOperType() + ", 条件：" + JSonUtil.toJSonString(condition));
                ResultCode rc = ResultCode.member_order_is_empty;
                throw new FroadBusinessException(rc.getCode(), rc.getMsg());
            }

            for (OrderMongo order : list) {
                QueryOrderByMerchantManageVo vo = new QueryOrderByMerchantManageVo();
                vo.setSubOrderId(order.getOrderId());
                vo.setCreateTime(order.getCreateTime());
                if(EmptyChecker.isNotEmpty(order.getIsPrefPay()) && order.getIsPrefPay() == 1){
                	vo.setSubTotalMoney(EmptyChecker.isEmpty(order.getConsumeMoney())? 0 : Arith.div(order.getConsumeMoney(), 1000));
                }else{
                	Integer cutMoney = order.getCutMoney() == null ? 0 : order.getCutMoney();
                	vo.setSubTotalMoney(Arith.div(order.getTotalPrice()+cutMoney, 1000));
                }
                
                vo.setOrderStatus(order.getOrderStatus());
                vo.setType(OrderType.face2face.getCode()); // 面对面查询
                vo.setMerchantUserId(order.getMerchantUserId() == null ? 0L : order.getMerchantUserId());
                
                long st2 = System.currentTimeMillis();
                //增加结算状态
                List<Settlement> settlements = settlementSupport.querySettlement(order.getOrderId());
                LogCvt.info("关联查询-结算信息-耗时：" + (System.currentTimeMillis()-st2));
                
                // 面对面结算记录为空
                if (EmptyChecker.isEmpty(settlements)) {
                    vo.setSettlementStatus(SettlementStatus.unsettlemnt.getCode());
                } else {
                    vo.setSettlementStatus(settlements.get(0).getSettleState());
                }

                listVo.add(vo);
            }
            rb = new ResultBean(ResultCode.success, listVo);
        } catch (FroadBusinessException e) {
            throw new FroadBusinessException(e.getCode(), e.getMsg());
        }
        return rb;
    }
    
    @Override
    public ResultBean queryQrOrderByMerchantForExport(OrderQueryMerchantManageCondition condition, PageVo pageVo) throws FroadBusinessException {
        // 商户管理查询查询面对面订单
        ResultBean rb = new ResultBean(ResultCode.success);
        PageEntity<OrderQueryMerchantManageCondition> pageEntity = new PageEntity<OrderQueryMerchantManageCondition>();

        pageEntity.setCondition(condition);

        MongoPage mongoPage = null;

        List<QueryOrderByMerchantManageVo> listVo = new ArrayList<QueryOrderByMerchantManageVo>();

        List<OrderMongo> list = new ArrayList<OrderMongo>();

       
        if (OperType.QUERY.getCode().equals(condition.getOperType())) {
        	long st1 = System.currentTimeMillis();
            // 只有查询有分页数据
            pageEntity.convert(pageVo);
            mongoPage = orderSupport.queryOrderByFacetfaceOfMerchantConditionOfPage(pageEntity);
            convertPage(mongoPage, pageVo);
            if (EmptyChecker.isEmpty(mongoPage)) {
                LogCvt.info("面对面订单查询结果为空");
            }else{
                list = (List<OrderMongo>) mongoPage.getItems();
            }
            LogCvt.info("订单导出-订单查询-耗时：" + (System.currentTimeMillis()-st1));
        } else {
        	long st1 = System.currentTimeMillis();
            list = (List<OrderMongo>) orderSupport.queryOrderByFacetfaceOfMerchantConditionOfAll(condition);
            LogCvt.info("订单导出-全部列表-耗时：" + (System.currentTimeMillis()-st1));
        }

        if (EmptyChecker.isEmpty(list)) {
            LogCvt.info("面对面订单导出-查询结果为空 , 条件：" + JSonUtil.toJSonString(condition));
        }else{
        	
        	// 结算信息
			List<String> orderIdList = getOrderIdList(list);
			Map<String, String> settlementMap = orderSupport.getSettlementByOrderIdList(orderIdList,false);
        	
        	for (OrderMongo order : list) {
                QueryOrderByMerchantManageVo vo = new QueryOrderByMerchantManageVo();
                vo.setOrderId(order.getOrderId());
                vo.setCreateTime(order.getCreateTime());
                if(EmptyChecker.isNotEmpty(order.getIsPrefPay()) && order.getIsPrefPay() == 1){
                	vo.setSubTotalMoney(EmptyChecker.isEmpty(order.getConsumeMoney())? 0 : Arith.div(order.getConsumeMoney(), 1000));
                }else{
                	Integer cutMoney = order.getCutMoney() == null ? 0 : order.getCutMoney();
                	vo.setSubTotalMoney(Arith.div(order.getTotalPrice()+cutMoney, 1000));
                }
                vo.setOrderStatus(order.getOrderStatus());
                vo.setType(OrderType.face2face.getCode()); // 面对面查询
                vo.setMerchantUserId(EmptyChecker.isNotEmpty(order.getMerchantUserId())?order.getMerchantUserId():0L);
                
                /*long st2 = System.currentTimeMillis();
                //增加结算状态
                List<Settlement> settlements = settlementSupport.querySettlement(order.getOrderId());
                LogCvt.info("关联查询-结算信息-耗时：" + (System.currentTimeMillis()-st2));
                // 面对面结算记录为空
                if (EmptyChecker.isEmpty(settlements)) {
                    vo.setSettlementStatus(SettlementStatus.unsettlemnt.getCode());
                } else {
                    vo.setSettlementStatus(settlements.get(0).getSettleState());
                }*/
                if(EmptyChecker.isNotEmpty(settlementMap) && EmptyChecker.isNotEmpty(settlementMap.get(order.getOrderId()))){
                	vo.setSettlementStatus(settlementMap.get(order.getOrderId()));
                }else{
                	vo.setSettlementStatus(SettlementStatus.unsettlemnt.getCode());
                }

                listVo.add(vo);
            }
        }
        
        rb = new ResultBean(ResultCode.success, listVo);
        return rb;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ResultBean querySubOrderByMerchantManage(OrderQueryMerchantManageCondition condition, PageVo pageVo) throws FroadBusinessException {
        // 商户管理查询查询团购等订单
        ResultBean rb = new ResultBean(ResultCode.success);
        try {
            PageEntity<OrderQueryMerchantManageCondition> pageEntity = new PageEntity<OrderQueryMerchantManageCondition>();

            pageEntity.setCondition(condition);

            MongoPage mongoPage = null;

            List<QueryOrderByMerchantManageVo> listVo = new ArrayList<QueryOrderByMerchantManageVo>();

            List<SubOrderMongo> list = new ArrayList<SubOrderMongo>();
            
            String operType = OperType.QUERY.getCode();
            if(EmptyChecker.isNotEmpty(condition.getOperType())) {
                operType = condition.getOperType();
            }
            if (OperType.QUERY.getCode().equals(operType)) {
                pageEntity.convert(pageVo);
                mongoPage = orderSupport.querySubOrderOfMerchantConditonOfPage(pageEntity);
                if (EmptyChecker.isEmpty(mongoPage)) {
                    ResultCode rc = ResultCode.member_suborder_query_empty;
                    throw new FroadBusinessException(rc.getCode(), rc.getMsg());
                }
                convertPage(mongoPage, pageVo);
                list = (List<SubOrderMongo>) mongoPage.getItems();
            } else {
                list = (List<SubOrderMongo>) orderSupport.querySubOrderOfMerchantConditonOfAll(condition);
            }

            if (EmptyChecker.isEmpty(list)) {
                LogCvt.error("结果为空, 操作（查询|下载）类型：" + operType+ ", 条件：" + JSonUtil.toJSonString(condition));
                ResultCode rc = ResultCode.member_suborder_query_empty;
                throw new FroadBusinessException(rc.getCode(), rc.getMsg());
            }
            
            boolean isNeedAreaDetail = false;
            if(condition.getType().equals(OrderType.special_merchant.getCode())){//名优特惠
            	isNeedAreaDetail = true;
            }
            //查询收货信息
            Map<String,RecvInfo> recvMap = orderSupport.getRecvInfoMap(list,false);

            for (SubOrderMongo subOrder : list) {
                // System.out.println(JSonUtil.toJSonString(subMongo));
                QueryOrderByMerchantManageVo vo = new QueryOrderByMerchantManageVo();
                vo.setOrderId(subOrder.getOrderId());
                vo.setSubOrderId(subOrder.getSubOrderId());
                vo.setCreateTime(subOrder.getCreateTime());

                String refundState = SubOrderRefundState.REFUND_INIT.getCode();
                try {
                    if (EmptyChecker.isNotEmpty(subOrder.getRefundState())) {
                        refundState = subOrder.getRefundState();
                    }
                } catch (Exception e) {
                }
                vo.setRefundState(refundState);
                
                // 商品数量
                int number = 0;
                double totalMoney = 0;
                List<ProductInfoVo> productInfoVos = new ArrayList<ProductInfoVo>(); 
                // 结算数量
                int sn = 0;
                /**
                 * 增加结算信息
                 */
                vo.setSettlementStatus(SettlementStatus.unsettlemnt.getCode());
                
                long st3 = System.currentTimeMillis();
                //名优特惠查询列表增加地址信息（2015.07.08）
                if(StringUtils.equals(condition.getType(), SubOrderType.special_merchant.getCode()) || StringUtils.equals(condition.getType(), SubOrderType.group_merchant.getCode())){
                	// 收货地址
                	/*OrderMongo order = orderSupport.getOrderById(subOrder.getClientId(), subOrder.getOrderId());
                    String recvId = order.getRecvId();
                    if(EmptyChecker.isNotEmpty(recvId)){
                    	RecvInfo recvInfo = orderSupport.getRecvInfo(order.getClientId(), order.getMemberCode(), recvId);
                        if (EmptyChecker.isNotEmpty(recvInfo)) {
                        	vo.setConsignee(EmptyChecker.isEmpty(recvInfo.getConsignee())?"":recvInfo.getConsignee());
                        	vo.setAddress(EmptyChecker.isEmpty(recvInfo.getAddress())?"":recvInfo.getAddress());
                        	vo.setPhone(EmptyChecker.isEmpty(recvInfo.getPhone())?"":recvInfo.getPhone());
                        	vo.setAreaId(EmptyChecker.isEmpty(recvInfo.getAreaId())?0:recvInfo.getAreaId());
                        }
                        LogCvt.info("关联查询-收货信息-耗时："+(System.currentTimeMillis() - st3));
                    }*/
                	if (EmptyChecker.isNotEmpty(recvMap)) {
                		RecvInfo recvInfo = recvMap.get(subOrder.getOrderId());
                    	if (EmptyChecker.isNotEmpty(recvInfo)) {
                        	vo.setConsignee(EmptyChecker.isEmpty(recvInfo.getConsignee())?"":recvInfo.getConsignee());
                        	vo.setAddress(EmptyChecker.isEmpty(recvInfo.getAddress())?"":recvInfo.getAddress());
                        	vo.setPhone(EmptyChecker.isEmpty(recvInfo.getPhone())?"":recvInfo.getPhone());
                        	vo.setAreaId(EmptyChecker.isEmpty(recvInfo.getAreaId())?0:recvInfo.getAreaId());
                        }
                	}else{
                		LogCvt.error("收货人信息为空");
                	}
                }
                
                //List<String> productName = new ArrayList<String>();
                for (ProductMongo product : subOrder.getProducts()) {
                    
                    ProductInfoVo productInfoVo = (ProductInfoVo) BeanUtil.copyProperties(ProductInfoVo.class, product);
                    
                    productInfoVo.setDeliveryMoney(getDeliveryMoney(product));
                    productInfoVo.setMoney(getMoney(product));
                    productInfoVo.setSubTotalMoney(getSubTotalMoney(product));
                    
                    long st1 = System.currentTimeMillis();
                    refundProduct(productInfoVo, subOrder.getSubOrderId(), subOrder.getOrderId());
                    LogCvt.info("关联查询-退款信息-耗时：" + (System.currentTimeMillis() - st1));
                    
                    productInfoVos.add(productInfoVo);
                    
                    totalMoney = Arith.add(totalMoney, getSubTotalMoney(product));
                    number += getProductQuantity(product);
                    
                    long st2 = System.currentTimeMillis();
                    // 获取结算信息
                    List<Settlement> settlementList = settlementSupport.querySettlement(subOrder.getSubOrderId(), product.getProductId());
                    LogCvt.info("关联查询-结算信息-耗时：" + (System.currentTimeMillis() - st2));
                    
                    if (EmptyChecker.isNotEmpty(settlementList)) {
                        // 没有结算记录
                        for (Settlement s : settlementList) {
                            if (SettlementStatus.settlementsucc.getCode().equals(s.getSettleState())) {
                                sn += s.getProductCount();
                            }
                        }
                    }
                }
                
                vo.setSettlementNumber(sn);
                // 全部结算或者部分结算情况下为已结算
                if (sn > 0) {
                    vo.setSettlementStatus(SettlementStatus.settlementsucc.getCode());
                }
                
                vo.setSubTotalMoney(totalMoney);
                //vo.setProductName(org.apache.commons.lang.StringUtils.join(productName.toArray(), ","));
                vo.setQuantity(number);
                vo.setProductInfoVo(productInfoVos);

                /**
                 * 只有名优特惠查询有发货相关状态
                 */
                if (OrderType.special_merchant.getCode().equals(subOrder.getType())) {
                    vo.setDeliveryStatus(subOrder.getDeliveryState());
                    vo.setDeliveryType(subOrder.getDeliveryOption());
                }
                vo.setType(subOrder.getType());
                vo.setOrderStatus(subOrder.getOrderStatus());
                listVo.add(vo);
            }

            rb = new ResultBean(ResultCode.success, listVo);
        } catch (FroadBusinessException e) {
            throw new FroadBusinessException(e.getCode(), e.getMsg());
        }
        return rb;
    }
    
    
    @SuppressWarnings("unchecked")
    @Override
    public ResultBean querySubOrderByMerchantForExport(OrderQueryMerchantManageCondition condition, PageVo pageVo) throws FroadBusinessException {
        // 商户管理查询查询团购等订单
        ResultBean rb = new ResultBean(ResultCode.success);
        
        PageEntity<OrderQueryMerchantManageCondition> pageEntity = new PageEntity<OrderQueryMerchantManageCondition>();
        pageEntity.setCondition(condition);
        pageEntity.convert(pageVo);
        
        MongoPage mongoPage = orderSupport.querySubOrderOfMerchantConditonOfPage(pageEntity);
        
        convertPage(mongoPage, pageVo);
        
        List<SubOrderMongo> list = new ArrayList<SubOrderMongo>();
        if (EmptyChecker.isNotEmpty(mongoPage)) {
            list = (List<SubOrderMongo>) mongoPage.getItems();
        }

        List<QueryOrderByMerchantManageVo> listVo = new ArrayList<QueryOrderByMerchantManageVo>();
        if (EmptyChecker.isEmpty(list)) {
            LogCvt.info("名优特惠/团购订单查询结果为空, 条件：" + JSonUtil.toJSonString(condition));
        }else{
        	boolean isNeedAreaDetail = false;
            if(condition.getType().equals(OrderType.special_merchant.getCode())){//名优特惠
            	isNeedAreaDetail = true;
            }
            //查询收货信息
            Map<String,RecvInfo> recvMap = orderSupport.getRecvInfoMap(list,isNeedAreaDetail);
            
            //查询结算记录
            List<String> subOrderIdList = getSubOrderIdList(list);
            Map<String,Integer> settlementMap = orderSupport.getSettlementBySubOrderIdList(subOrderIdList);

            for (SubOrderMongo subOrder : list) {
                QueryOrderByMerchantManageVo vo = new QueryOrderByMerchantManageVo();
                vo.setOrderId(subOrder.getOrderId());
                vo.setSubOrderId(subOrder.getSubOrderId());
                vo.setCreateTime(subOrder.getCreateTime());

                String refundState = null;
				if (EmptyChecker.isNotEmpty(subOrder.getRefundState())) {
					refundState = subOrder.getRefundState();
				} else {
					refundState = SubOrderRefundState.REFUND_INIT.getCode();
				}
                vo.setRefundState(refundState);
                
                // 商品数量
                int number = 0;
                double totalMoney = 0;
                List<ProductInfoVo> productInfoVos = new ArrayList<ProductInfoVo>(); 
                // 结算数量
                int sn = 0;
                /**
                 * 增加结算信息
                 */
                vo.setSettlementStatus(SettlementStatus.unsettlemnt.getCode());
                
                //名优特惠查询列表增加地址信息（2015.07.08）
                if(StringUtils.equals(condition.getType(), SubOrderType.special_merchant.getCode()) || StringUtils.equals(condition.getType(), SubOrderType.group_merchant.getCode())){
                	// 收货地址
                	if (EmptyChecker.isNotEmpty(recvMap)) {
                		RecvInfo recvInfo = recvMap.get(subOrder.getOrderId());
                    	if (EmptyChecker.isNotEmpty(recvInfo)) {
                        	vo.setConsignee(EmptyChecker.isEmpty(recvInfo.getConsignee())?"":recvInfo.getConsignee());
                        	vo.setAddress(EmptyChecker.isEmpty(recvInfo.getAddress())?"":recvInfo.getAddress());
                        	vo.setPhone(EmptyChecker.isEmpty(recvInfo.getPhone())?"":recvInfo.getPhone());
                        	vo.setAreaId(EmptyChecker.isEmpty(recvInfo.getAreaId())?0:recvInfo.getAreaId());
                        }
                	}else{
                		LogCvt.error("收货人信息为空");
                	}
                }
                
                for (ProductMongo product : subOrder.getProducts()) {
                    
                    ProductInfoVo productInfoVo = (ProductInfoVo) BeanUtil.copyProperties(ProductInfoVo.class, product);
                    
                    productInfoVo.setDeliveryMoney(getDeliveryMoney(product));
                    productInfoVo.setMoney(getMoney(product));
                    productInfoVo.setSubTotalMoney(getSubTotalMoney(product));
                    
                    /*long st1 = System.currentTimeMillis();
                    refundProduct(productInfoVo, subOrder.getSubOrderId(), subOrder.getOrderId());
                    LogCvt.info("关联查询-退款信息-耗时：" + (System.currentTimeMillis() - st1));*/
                    
                    productInfoVos.add(productInfoVo);
                    
                    totalMoney = Arith.add(totalMoney, getSubTotalMoney(product));
                    number += getProductQuantity(product);
                    
                    /*long st2 = System.currentTimeMillis();
                    // 获取结算信息
                    List<Settlement> settlementList = settlementSupport.querySettlement(subOrder.getSubOrderId(), product.getProductId());
                    LogCvt.info("关联查询-结算信息-耗时：" + (System.currentTimeMillis() - st2));
                    
                    if (EmptyChecker.isNotEmpty(settlementList)) {
                        // 没有结算记录
                        for (Settlement s : settlementList) {
                            if (SettlementStatus.settlementsucc.getCode().equals(s.getSettleState())) {
                                sn += s.getProductCount();
                            }
                        }
                    }*/
                    if(EmptyChecker.isNotEmpty(settlementMap) && EmptyChecker.isNotEmpty(settlementMap.get(subOrder.getSubOrderId()+"_"+product.getProductId()))){
                    	sn += settlementMap.get(subOrder.getSubOrderId()+"_"+product.getProductId());
                    }
                }
                
                vo.setSettlementNumber(sn);
                // 全部结算或者部分结算情况下为已结算
                if (sn > 0) {
                    vo.setSettlementStatus(SettlementStatus.settlementsucc.getCode());
                }
                
                vo.setSubTotalMoney(totalMoney);
                vo.setQuantity(number);
                vo.setProductInfoVo(productInfoVos);

                /**
                 * 只有名优特惠查询有发货相关状态
                 */
                if (OrderType.special_merchant.getCode().equals(subOrder.getType())) {
                    vo.setDeliveryStatus(subOrder.getDeliveryState());
                    vo.setDeliveryType(subOrder.getDeliveryOption());
                }
                vo.setType(subOrder.getType());
                vo.setOrderStatus(subOrder.getOrderStatus());
                listVo.add(vo);
            }
        }
        rb = new ResultBean(ResultCode.success, listVo);
        return rb;
    }
    
    /**
     * 大订单集合取大订单号
     * @param list
     * @return
     */
    public List<String> getOrderIdList(List<OrderMongo> list){
    	List<String> orderIdList = new ArrayList<String>();
    	if(EmptyChecker.isNotEmpty(list)){
    		for(OrderMongo orderMongo : list){
    			orderIdList.add(orderMongo.getOrderId());
    		}
    	}
        return orderIdList;
    }
    
     /**
      * 子订单集合取子订单号
     * @param list
     * @return
     */
    public List<String> getSubOrderIdList(List<SubOrderMongo> list){
    	List<String> subOrderIdList = new ArrayList<String>();
    	if(EmptyChecker.isNotEmpty(list)){
    		for(SubOrderMongo subOrderMongo : list){
    			subOrderIdList.add(subOrderMongo.getSubOrderId());
    		}
    	}
        return subOrderIdList;
    }
    
    @Override
    public ResultBean getOrderDetailByMerchantManage(String clientId, String orderId) throws FroadBusinessException {

        // 查询订单内容
        GetOrderDetailByMerchantManageVoRes res = getDetail(clientId, orderId);
        // 结算信息
        List<Settlement> list = settlementSupport.querySettlement(orderId);
        // 面对面结算记录为空
        if (EmptyChecker.isEmpty(list)) {
            res.setSettlementStatus(SettlementStatus.unsettlemnt.getCode());
        } else {
            res.setSettlementStatus(list.get(0).getSettleState());
        }
        return new ResultBean(ResultCode.success, res);
    }

    @Override
    public ResultBean getOrderDetailByMerchantManageNew(String clientId, String orderId) throws FroadBusinessException {

        // 查询订单内容
        GetOrderDetailByMerchantManageVoRes res = getDetail(clientId, orderId);
        // 结算信息
        List<Settlement> list = settlementSupport.querySettlement(orderId);
        // 面对面结算记录为空
        if (EmptyChecker.isEmpty(list)) {
            res.setSettlementStatus(SettlementStatus.unsettlemnt.getCode());
        } else {
            res.setSettlementStatus(list.get(0).getSettleState());
        }
        return new ResultBean(ResultCode.success, res);
    }
    
    @Override
    public ResultBean getSubOrderDetailByMerchantManage(String clientId, String subOrderId, String type) throws FroadBusinessException {

        SubOrderMongo subOrder = orderSupport.getSubOrderBySubOrderId(clientId, subOrderId);
        if (EmptyChecker.isEmpty(subOrder)) {
            ResultCode rc = ResultCode.member_suborder_query_empty;
            throw new FroadBusinessException(rc.getCode(), rc.getMsg());
        }

        // 物流信息---获取大订单内容
        OrderMongo order = orderSupport.getOrderById(clientId, subOrder.getOrderId());
        
        GetOrderDetailByMerchantManageVoRes res = new GetOrderDetailByMerchantManageVoRes();
        
        if (EmptyChecker.isEmpty(order)) {
            ResultCode rc = ResultCode.member_order_is_empty;
            throw new FroadBusinessException(rc.getCode(), rc.getMsg());
        }

        res.setCreateTime(order.getCreateTime()); // 创建时间
        res.setOrderStatus(order.getOrderStatus()); // 订单状态
        res.setOutletId(order.getOutletId());  // 门店
        res.setPaymentTime(getPaymentTime(order)); // 支付时间
        
        String refundState = SubOrderRefundState.REFUND_INIT.getCode();
        try {
            if (EmptyChecker.isNotEmpty(subOrder.getRefundState())) {
                refundState = subOrder.getRefundState();
            }
        } catch (Exception e) {
        }
        res.setRefundState(refundState);
        
        
        if (EmptyChecker.isNotEmpty(order) && !BooleanUtils.toBoolean(order.getIsQrcode())) {
            // 收货地址
            String recvId = order.getRecvId();
            RecvInfo recvInfo = orderSupport.getRecvInfo(order.getClientId(), order.getMemberCode(), recvId);
            if (EmptyChecker.isNotEmpty(recvInfo)) {
                res.setConsignee(EmptyChecker.isEmpty(recvInfo.getConsignee())?"":recvInfo.getConsignee());
                res.setAddress(EmptyChecker.isEmpty(recvInfo.getAddress())?"":recvInfo.getAddress());
                res.setPhone(EmptyChecker.isEmpty(recvInfo.getPhone())?"":recvInfo.getPhone());
                res.setAreaId(EmptyChecker.isEmpty(recvInfo.getAreaId())?0:recvInfo.getAreaId());
            }
            // 名优特惠-增加已发货物流信息
            if (OrderType.special_merchant.getCode().equals(subOrder.getType())) {

                // 发货状态
                ShippingOrderMongo shippingOrderMongo = orderSupport.getShippingInfo(subOrder.getOrderId() + "_" + subOrder.getSubOrderId());
                if (EmptyChecker.isNotEmpty(shippingOrderMongo)) {
                    res.setDeliveryCorpId(shippingOrderMongo.getDeliveryCorpId());
                    res.setDeliveryCorpName(shippingOrderMongo.getDeliveryCorpName());
                    res.setTrackingNo(shippingOrderMongo.getTrackingNo());
                    res.setShippingTime(shippingOrderMongo.getShippingTime());
                }
                // 默认为0 未结算
                res.setSettlementStatus("0");
                // 名优特惠订单的结算状态
                List<Settlement> settlements = settlementSupport.queryListBySubOrderId(subOrderId);
                if (EmptyChecker.isNotEmpty(settlements)) {
                    for (Settlement s : settlements) {
                        if (SettlementType.special.getCode().equals(s.getType())) {
                            LogCvt.info("名优特惠结算信息：" + s);
                            res.setSettlementStatus(s.getSettleState());
                        }

                    }
                }
            }
        }
        
        double totalMoney = 0;
        
        // 商品信息
        List<ProductInfoVo> list = new ArrayList<ProductInfoVo>();
        for (ProductMongo product : subOrder.getProducts()) {
            ProductInfoVo vo = new ProductInfoVo();
            vo.setSubTotalMoney(getSubTotalMoney(product));
            vo.setQuantity(getProductQuantity(product));
            vo.setMoney(getMoney(product));
            vo.setProductImage(product.getProductImage());
            vo.setProductName(product.getProductName());
            vo.setProductId(product.getProductId());
            
            // 子订单金额总计
            totalMoney = Arith.add(totalMoney, getSubTotalMoney(product));
            
            /**商品退货*/
            refundProduct(vo, subOrder.getSubOrderId(), subOrder.getOrderId());
            
            if (OrderType.special_merchant.getCode().equals(subOrder.getType())) {
                vo.setDeliverState(subOrder.getDeliveryState());
            } else {
                vo.setDeliverState(product.getDeliveryState());
            }
            if("1".equals(product.getCommentState())) {
                vo.setIsOutletComment(true);
            } else {
                vo.setIsOutletComment(false);
            }

            vo.setDeliveryMoney(getDeliveryMoney(product));
            // 结算信息
            // 默认为未结算
            vo.setSettlementStatus(SettlementStatus.unsettlemnt.getCode());
            vo.setSettlementNumber("0");

            // 获取结算信息
            List<Settlement> settlementList = settlementSupport.querySettlement(subOrderId, product.getProductId());
            if (EmptyChecker.isNotEmpty(settlementList)) {
                // 没有结算记录
                int number = 0;
                for (Settlement s : settlementList) {
                    if (SettlementStatus.settlementsucc.getCode().equals(s.getSettleState())) {
                        number += s.getProductCount();
                    }
                }
                vo.setSettlementNumber(String.valueOf(number));
                // 全部结算或者部分结算情况下为已结算
                if (number > 0) {
                    vo.setSettlementStatus(SettlementStatus.settlementsucc.getCode());
                }
            }

            list.add(vo);
        }
        
        res.setTotalMoney(totalMoney);
        res.setProductInfo(list);
        return new ResultBean(ResultCode.success, res);
    }

    @Override
    public ResultBean getSubOrderDetailByMerchantManageNew(String clientId, String subOrderId, String type) throws FroadBusinessException {

        SubOrderMongo subOrder = orderSupport.getSubOrderBySubOrderId(clientId, subOrderId);
        if (EmptyChecker.isEmpty(subOrder)) {
            ResultCode rc = ResultCode.member_suborder_query_empty;
            throw new FroadBusinessException(rc.getCode(), rc.getMsg());
        }

        // 物流信息---获取大订单内容
        OrderMongo order = orderSupport.getOrderById(clientId, subOrder.getOrderId());
        
        GetOrderDetailByMerchantManageVoRes res = new GetOrderDetailByMerchantManageVoRes();
        
        if (EmptyChecker.isEmpty(order)) {
            ResultCode rc = ResultCode.member_order_is_empty;
            throw new FroadBusinessException(rc.getCode(), rc.getMsg());
        }

        res.setCreateTime(order.getCreateTime()); // 创建时间
        res.setOrderStatus(order.getOrderStatus()); // 订单状态
        res.setOutletId(order.getOutletId());  // 门店
        res.setPaymentTime(getPaymentTime(order)); // 支付时间
        
        String refundState = SubOrderRefundState.REFUND_INIT.getCode();
        try {
            if (EmptyChecker.isNotEmpty(subOrder.getRefundState())) {
                refundState = subOrder.getRefundState();
            }
        } catch (Exception e) {
        }
        res.setRefundState(refundState);
        
        
        if (EmptyChecker.isNotEmpty(order) && !BooleanUtils.toBoolean(order.getIsQrcode())) {
            // 收货地址
            String recvId = order.getRecvId();
            RecvInfo recvInfo = orderSupport.getRecvInfo(order.getClientId(), order.getMemberCode(), recvId);
            if (EmptyChecker.isNotEmpty(recvInfo)) {
                res.setConsignee(EmptyChecker.isEmpty(recvInfo.getConsignee())?"":recvInfo.getConsignee());
                res.setAddress(EmptyChecker.isEmpty(recvInfo.getAddress())?"":recvInfo.getAddress());
                res.setPhone(EmptyChecker.isEmpty(recvInfo.getPhone())?"":recvInfo.getPhone());
                res.setAreaId(EmptyChecker.isEmpty(recvInfo.getAreaId())?0:recvInfo.getAreaId());
            }
            // 名优特惠-增加已发货物流信息
            if (OrderType.special_merchant.getCode().equals(subOrder.getType())) {

                // 发货状态
                ShippingOrderMongo shippingOrderMongo = orderSupport.getShippingInfo(subOrder.getOrderId() + "_" + subOrder.getSubOrderId());
                if (EmptyChecker.isNotEmpty(shippingOrderMongo)) {
                    res.setDeliveryCorpId(shippingOrderMongo.getDeliveryCorpId());
                    res.setDeliveryCorpName(shippingOrderMongo.getDeliveryCorpName());
                    res.setTrackingNo(shippingOrderMongo.getTrackingNo());
                    res.setShippingTime(shippingOrderMongo.getShippingTime());
                }
                // 默认为0 未结算
                res.setSettlementStatus("0");
                // 名优特惠订单的结算状态
                List<Settlement> settlements = settlementSupport.queryListBySubOrderId(subOrderId);
                if (EmptyChecker.isNotEmpty(settlements)) {
                    for (Settlement s : settlements) {
                        if (SettlementType.special.getCode().equals(s.getType())) {
                            LogCvt.info("名优特惠结算信息：" + s);
                            res.setSettlementStatus(s.getSettleState());
                        }

                    }
                }
            }
        }
        
        double totalMoney = 0;
        
        // 商品信息
        List<ProductInfoVo> list = new ArrayList<ProductInfoVo>();
        for (ProductMongo product : subOrder.getProducts()) {
            ProductInfoVo vo = new ProductInfoVo();
            vo.setSubTotalMoney(getSubTotalMoney(product));
            vo.setQuantity(getProductQuantity(product));
            vo.setMoney(getMoney(product));
            vo.setProductImage(product.getProductImage());
            vo.setProductName(product.getProductName());
            vo.setProductId(product.getProductId());
            
            // 子订单金额总计
            totalMoney = Arith.add(totalMoney, getSubTotalMoney(product));
            
            /**商品退货*/
            refundProduct(vo, subOrder.getSubOrderId(), subOrder.getOrderId());
            
            if (OrderType.special_merchant.getCode().equals(subOrder.getType())) {
                vo.setDeliverState(subOrder.getDeliveryState());
            } else {
                vo.setDeliverState(product.getDeliveryState());
            }
            if("1".equals(product.getCommentState())) {
                vo.setIsOutletComment(true);
            } else {
                vo.setIsOutletComment(false);
            }

            vo.setDeliveryMoney(getDeliveryMoney(product));
            // 结算信息
            // 默认为未结算
            vo.setSettlementStatus(SettlementStatus.unsettlemnt.getCode());
            vo.setSettlementNumber("0");

            // 获取结算信息
            List<Settlement> settlementList = settlementSupport.querySettlement(subOrderId, product.getProductId());
            if (EmptyChecker.isNotEmpty(settlementList)) {
                // 没有结算记录
                int number = 0;
                for (Settlement s : settlementList) {
                    if (SettlementStatus.settlementsucc.getCode().equals(s.getSettleState())) {
                        number += s.getProductCount();
                    }
                }
                vo.setSettlementNumber(String.valueOf(number));
                // 全部结算或者部分结算情况下为已结算
                if (number > 0) {
                    vo.setSettlementStatus(SettlementStatus.settlementsucc.getCode());
                }
            }

            list.add(vo);
        }
        
        res.setTotalMoney(totalMoney);
        res.setProductInfo(list);
        return new ResultBean(ResultCode.success, res);
    }
    
    @Override
    public ResultBean queryOrderByBoss(OrderQueryByBossCondition condition, PageVo pageVo) throws FroadBusinessException {

        if (EmptyChecker.isNotEmpty(condition.getPaymentId())) {
            // 查询支付表
            String orderId = null;
            Payment payment = payementSupport.findPaymentByPaymentId(condition.getPaymentId());
            
            if(EmptyChecker.isEmpty(payment)) {
                ResultCode resultCode = ResultCode.member_order_is_empty;
                throw new FroadBusinessException(resultCode.getCode(), "根据支付编号查询记录为空");
            }

            LogCvt.info("根据支付编号获取到订单号替换,原orderId:" + orderId + ", 支付编号查询：" + payment.getOrderId());
            condition.setOrderId(payment.getOrderId());

        }
        if (EmptyChecker.isNotEmpty(condition.getPhone())) {
            List<String> recvIds = new ArrayList<String>();
            // 查询收货ID
            List<RecvInfo> list = orderSupport.queryRecvInfos(condition.getPhone());
            for (RecvInfo info : list) {
                recvIds.add(info.getRecvId());
            }
            if (EmptyChecker.isNotEmpty(recvIds)) {
                condition.setRecvId(recvIds);
            }
        }
        PageEntity<OrderQueryByBossCondition> pageEntity = new PageEntity<OrderQueryByBossCondition>();
        pageEntity.setCondition(condition);

        pageEntity.convert(pageVo);
        // 按时间反序查询
        pageEntity.setOrderByColumn("create_time");
        pageEntity.setOrderByType(OrderByType.desc);

        MongoPage mongoPage = orderSupport.queryOrderOfBossByCondition(pageEntity);
        if (EmptyChecker.isEmpty(mongoPage) || EmptyChecker.isEmpty(mongoPage.getItems())) {
            ResultCode resultCode = ResultCode.member_order_is_empty;
            throw new FroadBusinessException(resultCode.getCode(), "查询订单为空");
        }
        convertPage(mongoPage, pageVo);
        List<OrderMongo> list = (List<OrderMongo>) mongoPage.getItems();
        List<OrderVo> resultList = new ArrayList<OrderVo>();
        for (OrderMongo order : list) {
            OrderVo vo = (OrderVo) BeanUtil.copyProperties(OrderVo.class, order);
            vo.setPoints(getPoint(order));
            vo.setRealPrice(getRealPrice(order));
            vo.setTotalPrice(getSubTotalMoney(order));
            resultList.add(vo);
        }
        return new ResultBean(ResultCode.success, resultList);
    }

    @Override
    public ResultBean getSubOrderDetailOfBossByOrderId(String clientId, String orderId) throws FroadBusinessException {
        List<SubOrderVo> listVo = new ArrayList<SubOrderVo>();

        List<SubOrderMongo> list = orderSupport.getSubOrderListByOrderId(clientId, orderId);

        if (EmptyChecker.isEmpty(list)) {
            throw new FroadBusinessException(ResultCode.member_suborder_query_empty.getCode(), "查找子订单列表为空");
        }

        for (SubOrderMongo subOrder : list) {
            SubOrderVo vo = new SubOrderVo();
            vo.setMerchantName(subOrder.getMerchantName());
            vo.setMerchantId(subOrder.getMerchantId());
            vo.setType(subOrder.getType());
            vo.setSubOrderId(subOrder.getSubOrderId());
            /**
             * 只有名优特惠查询有发货相关状态
             */
            if (OrderType.special_merchant.getCode().equals(subOrder.getType())) {
                vo.setDeliveryState(subOrder.getDeliveryState());
            }

            List<ProductVo> productList = new ArrayList<ProductVo>();
            for (ProductMongo product : subOrder.getProducts()) {
                ProductVo productVo = (ProductVo) BeanUtil.copyProperties(ProductVo.class, product);

                // 金额处理
                productVo.setDeliveryMoney(Arith.div(product.getDeliveryMoney(), 1000));
                productVo.setMoney(getMoney(product));
                productVo.setPoints(product.getPoints() / 1000);
                productVo.setVipMoney(Arith.div(product.getVipMoney(), 1000));
                
                /**退款数量*/
                refundProduct(productVo, subOrder.getSubOrderId(), subOrder.getOrderId());
                
                productList.add(productVo);
            }
            vo.setProducts(productList);
            listVo.add(vo);
        }
        return new ResultBean(ResultCode.success, listVo);
    }

    @Override
    public ResultBean getOrderDetailByOrderId(String clientId, String orderId) throws FroadBusinessException {

        OrderMongo orderMongo = orderSupport.getOrderById(clientId, orderId);

        if (EmptyChecker.isEmpty(orderMongo)) {
            LogCvt.error("查询大订单内容为空， 大订单号：" + orderId);
            throw new FroadBusinessException(ResultCode.member_order_is_empty.getCode(), "大订单内容为空");
        }

        if (BooleanUtils.toBoolean(orderMongo.getIsQrcode())) {
            LogCvt.error("该订单为面对面订单，不支持查询");
            throw new FroadBusinessException(ResultCode.failed.getCode(), "不能查询面对面订单");
        }

        OrderDetailRes res = new OrderDetailRes();

        // 大订单内容
        res.setCreateTime(orderMongo.getCreateTime());
        res.setOrderStatus(orderMongo.getOrderStatus());
        res.setPointDiscount(getPointDiscount(orderMongo));
        res.setTotalMoney(getSubTotalMoney(orderMongo));

        List<SubOrderMongo> subOrderMongos = orderSupport.getSubOrderListByOrderId(clientId, orderId);

        if (EmptyChecker.isEmpty(subOrderMongos)) {
            LogCvt.error("查询子订单列表失败， 大订单号：" + orderId);
            throw new FroadBusinessException(ResultCode.member_suborder_query_empty.getCode(), "子订单列表为空");
        }

        List<SubOrderVo> subOrderVos = new ArrayList<SubOrderVo>();
        for (SubOrderMongo subOrder : subOrderMongos) {
            SubOrderVo subOrderVo = convertSubOrderVo(subOrder);
            subOrderVos.add(subOrderVo);
        }
        res.setSubOrderVo(subOrderVos);
        return new ResultBean(ResultCode.success, res);
    }

    /***
     * 内部转换为外部
     * 
     * @param subOrder
     *            子订单记录
     * @return Vo {@link SubOrderVo}
     * 
     *         <pre>
     * 
     * @Description: 针对不同类型返回值有不同差异。 名优特惠有发货信息， 团购和预售有有效期
     * @version V1.0 修改人：Arron 日期：2015年5月2日 下午3:22:51
     * 
     * </pre>
     */
    private SubOrderVo convertSubOrderVo(SubOrderMongo subOrder) {
        SubOrderVo vo = new SubOrderVo();

        vo.setMerchantId(subOrder.getMerchantId()); // 商户ID
        vo.setMerchantName(subOrder.getMerchantName()); // 商户名（简称）
        vo.setType(subOrder.getType());
        vo.setSubOrderId(subOrder.getSubOrderId());
        try {
            vo.setRefundState(SubOrderRefundState.REFUND_INIT.getCode());
            if (EmptyChecker.isNotEmpty(subOrder.getRefundState())) {
                vo.setRefundState(subOrder.getRefundState());
            }
        } catch (Exception e) {
        }

        if (SubOrderType.special_merchant.getCode().equals(subOrder.getType())) {
            // 名优特惠添加物流信息
            ShippingOrderMongo shippingOrderMongo = orderSupport.getShippingInfo(subOrder.getOrderId() + "_" + subOrder.getSubOrderId());
            if (EmptyChecker.isNotEmpty(shippingOrderMongo)) {
                vo.setDeliveryCorpName(shippingOrderMongo.getDeliveryCorpName());
                vo.setDeliveryCorpId(shippingOrderMongo.getDeliveryCorpId());
                vo.setTrackingNo(shippingOrderMongo.getTrackingNo());
                vo.setShippingTime(shippingOrderMongo.getShippingTime());
            }
            // 名优特惠的配送状态
            vo.setDeliveryState(subOrder.getDeliveryState());
        }

        /**
         * 商品信息
         */
        List<ProductVo> productVos = new ArrayList<ProductVo>();
        double totalPrice = 0;
        for (ProductMongo product : subOrder.getProducts()) {
            ProductVo productVo = (ProductVo) BeanUtil.copyProperties(ProductVo.class, product);

            // 金额处理
            productVo.setDeliveryMoney(Arith.div(product.getDeliveryMoney(), 1000));
            productVo.setMoney(getMoney(product));
            // 赠送积分
            productVo.setPoints(Arith.div(product.getPoints(), 1000));
            productVo.setVipMoney(Arith.div(product.getVipMoney(), 1000));

            productVo.setSubTotalMoney(getSubTotalMoney(product));

            
            // 团购并且订单支付完成提货状态设置为未提货
            if (SubOrderType.group_merchant.getCode().equals(subOrder.getType()) && 
                    (OrderStatus.paysuccess.getCode().equals(subOrder.getOrderStatus()) || OrderStatus.sysclosed.getCode().equals(subOrder.getOrderStatus()))) {
                if (EmptyChecker.isEmpty(product.getDeliveryState())) {
                    productVo.setDeliveryState(ShippingStatus.untake.getCode());
                }
            }
            
            totalPrice = Arith.add(totalPrice, getSubTotalMoney(product));

            /**退款数量*/
            refundProduct(productVo, subOrder.getSubOrderId(), subOrder.getOrderId());
            
            // 预售和团购才有券的基本信息
            if (SubOrderType.group_merchant.getCode().equals(subOrder.getType()) || SubOrderType.presell_org.getCode().equals(subOrder.getType())) {
            	//取机构的虚拟商户号
				String merchantId = subOrder.getMerchantId();
				if(!(StringUtils.equals(subOrder.getType(), SubOrderType.special_merchant.getCode()) || StringUtils.equals(subOrder.getType(), SubOrderType.group_merchant.getCode()))){
					Org org = commonLogic.getOrgByOrgCode(merchantId, subOrder.getClientId());
					if(EmptyChecker.isEmpty(org)){
						LogCvt.error("通过机构号获取商户号失败[机构号："+merchantId+"]");
					}else{
						merchantId = org.getMerchantId();
					}
				}
            	
            	Map<String, String> productMap = getProduct(subOrder.getClientId(), merchantId, product.getProductId());
                if (EmptyChecker.isNotEmpty(productMap)) {
                    String startTime = null;
                    String endTime = null;
                    if (SubOrderType.group_merchant.getCode().equals(subOrder.getType())) {
                        startTime = productMap.get("expire_start_time");
                        endTime = productMap.get("expire_end_time");
                    } else {
                        startTime = productMap.get("delivery_start_time");
                        endTime = productMap.get("delivery_end_time");
                    }
                    if (EmptyChecker.isNotEmpty(startTime)) {
                        productVo.setStartTime(Long.parseLong(startTime));
                    }
                    if (EmptyChecker.isNotEmpty(endTime)) {
                        productVo.setEndTime(Long.parseLong(endTime));
                    }
                }
//              增加团购券的消费状态
                Map<String,Object> resMap = getTicketConsumeState(subOrder.getOrderId(), subOrder.getSubOrderId(), product.getProductId());
                productVo.setConsumeStatus((Integer)resMap.get("consumeStatus"));
            }
            productVos.add(productVo);
        }
        vo.setProducts(productVos);
        vo.setTotalPrice(totalPrice);
        return vo;
    }

    private GetOrderDetailByMerchantManageVoRes getDetail(String clientId, String orderId) throws FroadBusinessException {
        OrderMongo orderMongo = orderSupport.getOrderById(clientId, orderId);

        if (EmptyChecker.isEmpty(orderMongo)) {
            ResultCode rc = ResultCode.member_order_is_empty;
            throw new FroadBusinessException(rc.getCode(), rc.getMsg());
        }

        // 转换
        GetOrderDetailByMerchantManageVoRes res = new GetOrderDetailByMerchantManageVoRes();
        res.setCreateTime(orderMongo.getCreateTime());
        res.setOrderStatus(orderMongo.getOrderStatus());
        res.setTotalMoney(getSubTotalMoney(orderMongo));
        res.setPointDiscount(getPointDiscount(orderMongo));
        res.setOutletId(orderMongo.getOutletId());
        res.setPaymentTime(getPaymentTime(orderMongo));
        res.setMerchantUserId(EmptyChecker.isNotEmpty(orderMongo.getMerchantUserId())?orderMongo.getMerchantUserId():0L);
        return res;
    }

    /**
     * 分页数据转换
     * 
     * @param page
     * @param vo
     *            <pre>
     * 
     * @version V1.0 修改人：Arron 日期：2015年4月8日 下午3:19:28
     * 
     * </pre>
     */
    private void convertPage(MongoPage page, PageVo vo) {
        vo.setPageCount(page.getPageCount());
        vo.setPageNumber(page.getPageNumber());
        vo.setPageSize(page.getPageSize());
        vo.setTotalCount(page.getTotalCount());
        
        vo.setLastPageNumber(page.getPageNumber());
        vo.setHasNext(page.getPageCount() > page.getPageNumber());
    }

    /**
     * 大订单转换-银行管理平台
     * @param order
     * @param settlementMap
     * @param orgMap
     * @param userMap
     * @return
     */
    private QueryOrderByBankManageVo convertQueryOrderByBankManageVo(OrderMongo order,Map<String,String> settlementMap,Map<String,String> orgMap,Map<Long,String> userMap) {
        QueryOrderByBankManageVo vo = new QueryOrderByBankManageVo();

        vo.setOrderId(order.getOrderId());
        vo.setPaymentMethod(order.getPaymentMethod());
        vo.setOrderStatus(order.getOrderStatus());
        vo.setCreateSoure(order.getCreateSource());
        vo.setMerchantName(order.getMerchantName());
        vo.setCreateTime(order.getCreateTime());

        if(EmptyChecker.isNotEmpty(order.getIsPrefPay()) && order.getIsPrefPay() == 1){
        	vo.setSubTotalMoney(EmptyChecker.isEmpty(order.getConsumeMoney())? 0 : Arith.div(order.getConsumeMoney(), 1000));
        }else{
        	Integer cutMoney = order.getCutMoney() == null ? 0 : order.getCutMoney();
        	vo.setSubTotalMoney(Arith.div(order.getTotalPrice()+cutMoney, 1000));
        }
        
        vo.setCash(getRealPrice(order));
        vo.setPoint(getPoint(order));
        
        /**2015-09-06 v1.1.0 变更--开始*/
        //增加手机号
        if(EmptyChecker.isNotEmpty(order.getPhone())){
        	DeliverInfoVo deliverInfoVo = new DeliverInfoVo();
        	deliverInfoVo.setPhone(order.getPhone());
        	vo.setDeliverInfoVo(deliverInfoVo);
        }
        //增加商户用户ID
        vo.setMerchantUserId(EmptyChecker.isNotEmpty(order.getMerchantUserId())?order.getMerchantUserId():0L);
        //增加商户用户名
        String merchantUserName = "";
        if(EmptyChecker.isNotEmpty(order.getMerchantUserName())){
        	merchantUserName = order.getMerchantUserName();
        }else{
        	if(EmptyChecker.isNotEmpty(userMap) && EmptyChecker.isNotEmpty(order.getMerchantUserId()) && EmptyChecker.isNotEmpty(userMap.get(order.getMerchantUserId()))){
        		merchantUserName = userMap.get(order.getMerchantUserId());
        	}
        }
        vo.setMerchantUserName(merchantUserName);
        
        //增加结算状态
        if (EmptyChecker.isNotEmpty(settlementMap)) {
        	if (EmptyChecker.isEmpty(settlementMap.get(order.getOrderId()))) {
        		vo.setSettlementStatus(SettlementStatus.unsettlemnt.getDescribe());
        	}else{
        		vo.setSettlementStatus(settlementMap.get(order.getOrderId()));
        	}
        }
        
        //增加所属机构
        if(EmptyChecker.isNotEmpty(orgMap)){
        	vo.setOrgNames(getOrgNames(order,orgMap));
        }else{
        	long st = System.currentTimeMillis();
        	vo.setOrgNames(getOrgNames(order));
        	LogCvt.info("根据机构号获取机构名称，耗时："+(System.currentTimeMillis()-st));
        }
        vo.setOutletId(String.valueOf(order.getOutletId()));
        //门店名称
        String outletName = "";
		if(EmptyChecker.isEmpty(order.getOutletName())){
			Map<String,String> outletNameMap = RedisCommon.getOutletRedis(order.getClientId(),order.getMerchantId(),order.getOutletId());
			if(EmptyChecker.isNotEmpty(outletNameMap)){
				outletName = outletNameMap.get("outlet_name");
	        }
		}else{
			outletName = order.getOutletName();
		}
		vo.setMerchantName(outletName);
        
        return vo;
    }

    @Deprecated
    private QueryOrderByBankManageVo convertQueryOrderByBankManageVo(SubOrderMongo subOrder) throws FroadBusinessException {
        QueryOrderByBankManageVo vo = new QueryOrderByBankManageVo();

        vo.setOrderId(subOrder.getOrderId());
        vo.setSubOrderId(subOrder.getSubOrderId());
        vo.setMerchantName(subOrder.getMerchantName());

        //只有支付完成才有配送状态
        if (OrderType.special_merchant.getCode().equals(subOrder.getType()) && OrderStatus.paysuccess.getCode().equals(subOrder.getOrderStatus())) {
            vo.setDeliveryState(subOrder.getDeliveryState());
        }

        // 商品名
        List<String> list = new ArrayList<String>();
        double subTotalMoney = 0;
        //子订单商品数
        int quantity = 0; 
        for (ProductMongo product : subOrder.getProducts()) {
            list.add(product.getProductName());
            subTotalMoney = Arith.add(subTotalMoney, getSubTotalMoney(product));
            quantity += product.getQuantity() + product.getVipQuantity();
        }
        vo.setProductName(org.apache.commons.lang.StringUtils.join(list.toArray(), ","));

        long st1 = System.currentTimeMillis();
        // 从大订单中获取订单状态和购买会员名
        OrderMongo orderMongo = orderSupport.getOrderById(subOrder.getClientId(), subOrder.getOrderId());
        LogCvt.info("查询大订单耗时：(" + (System.currentTimeMillis() - st1) + ") 毫秒");
        if (EmptyChecker.isEmpty(orderMongo)) {
            LogCvt.error("查询大订单数据位空， 大订单号为：" + subOrder.getOrderId());
            ResultCode resultCode = ResultCode.member_order_is_empty;
            throw new FroadBusinessException(resultCode.getCode(), resultCode.getMsg());
        }
        // 取从表， 通过从表条件查询数据
        vo.setOrderStatus(subOrder.getOrderStatus());
        
        // 线上线下积分获取大订单积分 
        /*if(SubOrderType.offline_points_org.getCode().equals(subOrder.getType()) || SubOrderType.online_points_org.getCode().equals(subOrder.getType())) {
            vo.setPoint(getPoint(orderMongo));
        }*/
        
        vo.setSubTotalMoney(subTotalMoney); // 显示子订单金额
        vo.setPaymentMethod(orderMongo.getPaymentMethod()); // 支付方式
        vo.setCreateSoure(orderMongo.getCreateSource()); // 创建来源

        vo.setCreateTime(subOrder.getCreateTime()); // 创建时间
        vo.setOrgCode(getOrgCode(subOrder)); // 机构代码

        // 获取订单子状态
        vo.setRefundState(SubOrderRefundState.REFUND_INIT.getCode());
        if (EmptyChecker.isNotEmpty(subOrder.getRefundState())) {
            vo.setRefundState(subOrder.getRefundState());
        }
        
        //2015.07.27 加字段：商品数量
        vo.setQuantity(quantity);

        return vo;
    }
    
    private QueryOrderByBankManageVo convertQueryOrderByBankManageVo(SubOrderMongo subOrder,Map<String,OrderMongo> orderMap,String deliveryOption) throws FroadBusinessException {
        QueryOrderByBankManageVo vo = new QueryOrderByBankManageVo();
        OrderMongo orderMongo = orderMap.get(subOrder.getClientId()+"_"+ subOrder.getOrderId());
        vo.setOrderId(subOrder.getOrderId());
        vo.setSubOrderId(subOrder.getSubOrderId());
        vo.setMerchantName(subOrder.getMerchantName());

        //只有支付完成才有配送状态
        if (OrderType.special_merchant.getCode().equals(subOrder.getType()) && OrderStatus.paysuccess.getCode().equals(subOrder.getOrderStatus())) {
            vo.setDeliveryState(subOrder.getDeliveryState());
        }
        
        // 商品名
        List<String> list = new ArrayList<String>();
        //订单优化增加商品列表返回
        ProductVo proVo = null;
        List<ProductVo> productList = new ArrayList<ProductVo>();
        double subTotalMoney = 0;
        double subTotalMoneyZero = 0;//配送商品金额
        double subTotalMoneyOne = 0;//自提商品金额
        String zitiOrgCode = "";
        StringBuffer orgnameTmp = new StringBuffer("");
        StringBuffer orgnameWd = new StringBuffer("");
        //子订单商品数
        int quantity = 0; 
        //积分兑换用到
        Integer points=0;
        StringBuffer orgNames = new StringBuffer("");
        String deoption="";
        Integer newConsumeStatus=0;
        for (ProductMongo product : subOrder.getProducts()) {
            list.add(product.getProductName());
            subTotalMoney = Arith.add(subTotalMoney, getSubTotalMoney(product));
            
            int count = EmptyChecker.isEmpty(product.getQuantity())? 0 : product.getQuantity();
            int vipCount = EmptyChecker.isEmpty(product.getVipQuantity())? 0 : product.getVipQuantity();
            if(EmptyChecker.isNotEmpty(product.getMoney()) && product.getMoney()!=0 && EmptyChecker.isNotEmpty(product.getQuantity())){
            	points +=product.getMoney()*product.getQuantity();
            }
            quantity += count + vipCount;
            int proQuantity =count + vipCount;
            proVo = new ProductVo();
            if(EmptyChecker.isNotEmpty(product.getProductName())){
            	proVo.setProductName(product.getProductName());           	
            }
            if(points!=0){
            	proVo.setPoints(Long.valueOf(points));//积分兑换用到-一种商品的积分总和
            }
            if(EmptyChecker.isNotEmpty(product.getDeliveryOption())){
            	deoption = product.getDeliveryOption();           	
            }
            if(EmptyChecker.isNotEmpty(deoption) && DeliveryType.take.getCode().equals(deoption)){
            	subTotalMoneyOne = Arith.add(subTotalMoneyOne, getSubTotalMoney(product));
            }
            if(EmptyChecker.isNotEmpty(deoption) && DeliveryType.home.getCode().equals(deoption)){
            	subTotalMoneyZero = Arith.add(subTotalMoneyZero, getSubTotalMoney(product));
            }
            //proVo.setQuantity(count + vipCount);//冗余属性返回        
            
            if(OrderType.presell_org.getCode().equals(subOrder.getType()) && 
            		EmptyChecker.isNotEmpty(deliveryOption) && 
            		DeliveryType.take.getCode().equals(deliveryOption) && deliveryOption.equals(deoption)){//获取提货情况
            	
            	if(EmptyChecker.isEmpty(orgnameWd.toString()) && EmptyChecker.isNotEmpty(product.getOrgName())){
            		//zitiOrgCode = product.getOrgCode();
            		orgnameWd.append(product.getOrgName());            		
            	}else if(EmptyChecker.isNotEmpty(orgnameWd.toString()) 
            			&& !orgnameWd.toString().equals(product.getOrgName())
            			&& EmptyChecker.isNotEmpty(product.getOrgName())){
            		//zitiOrgCode = product.getOrgCode();
            		orgnameWd.append(product.getOrgName());            		            		
            	}            
            	//proVo.setOrgNames(orgNames.toString());
                 if(!"".equals(orgnameTmp.toString())){
                	 if(EmptyChecker.isNotEmpty(orgnameWd.toString()) && orgnameTmp.toString().indexOf(orgnameWd.toString()) == -1){
                		 orgnameTmp.append(",").append(orgnameWd.toString());
                		 orgnameWd.delete(0, orgnameWd.toString().length());
                	 }
                 }else if(EmptyChecker.isNotEmpty(orgnameWd.toString())){
                	 orgnameTmp.append(orgnameWd.toString());
                	 orgnameWd.delete(0, orgnameWd.toString().length());
                 }
//            	if(EmptyChecker.isEmpty(zitiOrgCode)){
//            		zitiOrgCode = product.getOrgCode();
//            		List<Org> orgList = commonLogic.getSuperOrgList(subOrder.getClientId(), product.getOrgCode());
//                    for(Org orgVo:orgList){
//                    	if("1".equals(orgVo.getOrgLevel())){
//                    		orgNames.append(orgVo.getOrgName());
//                    	}
//                    	if("2".equals(orgVo.getOrgLevel())){
//                    		orgNames.append("-").append(orgVo.getOrgName());
//                    	}
//                    	if("3".equals(orgVo.getOrgLevel())){
//                    		orgNames.append("-").append(orgVo.getOrgName());
//                    	}
//                    	if("4".equals(orgVo.getOrgLevel())){
//                    		orgNames.append("-").append(orgVo.getOrgName());
//                    	}
//                    }
//            	}else if(EmptyChecker.isNotEmpty(zitiOrgCode) && !zitiOrgCode.equals(product.getOrgCode())){
//            		zitiOrgCode = product.getOrgCode();
//            		List<Org> orgList = commonLogic.getSuperOrgList(subOrder.getClientId(), product.getOrgCode());
//                    for(Org orgVo:orgList){
//                    	if("1".equals(orgVo.getOrgLevel())){
//                    		orgNames.append(orgVo.getOrgName());
//                    	}
//                    	if("2".equals(orgVo.getOrgLevel())){
//                    		orgNames.append("-").append(orgVo.getOrgName());
//                    	}
//                    	if("3".equals(orgVo.getOrgLevel())){
//                    		orgNames.append("-").append(orgVo.getOrgName());
//                    	}
//                    	if("4".equals(orgVo.getOrgLevel())){
//                    		orgNames.append("-").append(orgVo.getOrgName());
//                    	}
//                    }
//            		
//            	}            
//            	//proVo.setOrgNames(orgNames.toString());
//                 if(!"".equals(orgnameTmp.toString())){
//                	 if(EmptyChecker.isNotEmpty(orgNames.toString()) && orgnameTmp.toString().indexOf(orgNames.toString()) == -1){
//                		 orgnameTmp.append(",").append(orgNames.toString());
//                		 orgNames.delete(0, orgNames.toString().length());
//                	 }
//                 }else if(EmptyChecker.isNotEmpty(orgNames.toString())){
//                	 orgnameTmp.append(orgNames.toString());
//                	 orgNames.delete(0, orgNames.toString().length());
//                 }
            	
            	Map<String,Object> resMap = getTicketConsumeState(subOrder.getOrderId(), subOrder.getSubOrderId(), product.getProductId());
            	Integer num = (Integer)resMap.get("consumeStatus"); 
            	if(newConsumeStatus == 0 && num!=null && num != 0){//1,1
            		newConsumeStatus = num;
            	}else if(newConsumeStatus == 1 && num==2){
            		newConsumeStatus=3;
            	}else if(newConsumeStatus == 1 && num==3){
            		newConsumeStatus=3;
            	}else if(newConsumeStatus == 2 && num==1){
            		newConsumeStatus=3;
            	}else if(newConsumeStatus == 2 && num==2){
            		newConsumeStatus=2;
            	}else if(newConsumeStatus == 2 && num==3){
            		newConsumeStatus=3;
            	}
            }  
            proVo.setQuantity(proQuantity);
            if(OrderType.presell_org.getCode().equals(subOrder.getType()) 
            		&& EmptyChecker.isNotEmpty(product.getDeliveryOption())){//获取提货情况
            	proVo.setDeliveryOption(product.getDeliveryOption()); 
            }
            productList.add(proVo);
        }
        
        if(OrderType.presell_org.getCode().equals(subOrder.getType())){//预售获取所属机构
        	if(EmptyChecker.isNotEmpty(deliveryOption) && DeliveryType.home.getCode().equals(deliveryOption)){//配送 获取 录入商品的商户所属机构
        		//只有支付完成才有配送状态
                if (OrderStatus.paysuccess.getCode().equals(subOrder.getOrderStatus())) {
                    vo.setDeliveryState(subOrder.getDeliveryState());
                }
                String recvId = orderMongo.getRecvId();
//                if(EmptyChecker.isNotEmpty(recvId)){
//                	RecvInfo recvInfo = orderSupport.getRecvInfo(orderMongo.getClientId(), orderMongo.getMemberCode(), recvId);
//                    if (EmptyChecker.isNotEmpty(recvInfo)) {
//                    	DeliverInfoVo deliVo= new DeliverInfoVo();
//                    	deliVo.setAddress(EmptyChecker.isEmpty(recvInfo.getAddress())?"":recvInfo.getAddress());
//                    	deliVo.setConsignee(EmptyChecker.isEmpty(recvInfo.getConsignee())?"":recvInfo.getConsignee());
//                    	deliVo.setPhone(EmptyChecker.isEmpty(recvInfo.getPhone())?"":recvInfo.getPhone());
//                    	deliVo.setRecvId(recvId);
//                    	//配送方式返回配送的收货人信息和地址
//                    	vo.setDeliverInfoVo(deliVo);
//                    }
//                }
                List<SubOrderMongo> subOrderList = new ArrayList<SubOrderMongo>();
            	subOrderList.add(subOrder);
            	Map<String,RecvInfo> recvMap =  orderSupport.getRecvInfoMap(subOrderList,true);//true表示要返回省市区
            	RecvInfo recvInfo = recvMap.get(subOrder.getOrderId());
                if (EmptyChecker.isNotEmpty(recvInfo)) {
                	//收货人信息|提货人信息|消费人信息 DeliverInfoVo deliverInfoVo
                	DeliverInfoVo deliVo= new DeliverInfoVo();
                	deliVo.setAddress(EmptyChecker.isEmpty(recvInfo.getAddress())?"":recvInfo.getAddress());
                	deliVo.setConsignee(EmptyChecker.isEmpty(recvInfo.getConsignee())?"":recvInfo.getConsignee());
                	deliVo.setPhone(EmptyChecker.isEmpty(recvInfo.getPhone())?"":recvInfo.getPhone());
                	//deliVo.setRecvId(recvId);
                	//配送方式返回配送的收货人信息和地址
                	vo.setDeliverInfoVo(deliVo);
                }else{
                	RecvInfo recvInfo2 = orderSupport.getRecvInfo(orderMongo.getClientId(), orderMongo.getMemberCode(), recvId);
                	if(EmptyChecker.isNotEmpty(recvInfo2)){
                		//收货人信息|提货人信息|消费人信息 DeliverInfoVo deliverInfoVo
                		DeliverInfoVo deliVo2= new DeliverInfoVo();
                		deliVo2.setAddress(EmptyChecker.isEmpty(recvInfo2.getAddress())?"":recvInfo2.getAddress());
                		deliVo2.setConsignee(EmptyChecker.isEmpty(recvInfo2.getConsignee())?"":recvInfo2.getConsignee());
                		deliVo2.setPhone(EmptyChecker.isEmpty(recvInfo2.getPhone())?"":recvInfo2.getPhone());
                		deliVo2.setRecvId(recvId);
                		//配送方式返回配送的收货人信息和地址
                		vo.setDeliverInfoVo(deliVo2);                   		
                	}
                }
        		if(subOrder.getForgCode()!= null && !"".equals(subOrder.getForgCode())){
                	Org orgForg = commonLogic.getOrgByOrgCode(subOrder.getForgCode(), subOrder.getClientId());
                	if(EmptyChecker.isNotEmpty(orgForg) && EmptyChecker.isNotEmpty(orgForg.getOrgName())){
                		orgNames.append(orgForg.getOrgName());
                	}
                	if(subOrder.getSorgCode()!= null && !"".equals(subOrder.getSorgCode())){
                    	Org orgSorg = commonLogic.getOrgByOrgCode(subOrder.getSorgCode(), subOrder.getClientId());
                    	if(EmptyChecker.isNotEmpty(orgSorg) && EmptyChecker.isNotEmpty(orgSorg.getOrgName())){
                    		orgNames.append("-").append(orgSorg.getOrgName());
                    	}
                    	if(subOrder.getTorgCode()!= null && !"".equals(subOrder.getTorgCode())){
                        	Org orgTorg = commonLogic.getOrgByOrgCode(subOrder.getTorgCode(), subOrder.getClientId());
                        	if(EmptyChecker.isNotEmpty(orgTorg) && EmptyChecker.isNotEmpty(orgTorg.getOrgName())){
                        		orgNames.append("-").append(orgTorg.getOrgName());
                        	}
                        	if(subOrder.getLorgCode()!= null && !"".equals(subOrder.getLorgCode())){
                            	Org orgLorg = commonLogic.getOrgByOrgCode(subOrder.getLorgCode(), subOrder.getClientId());
                            	if(EmptyChecker.isNotEmpty(orgLorg) && EmptyChecker.isNotEmpty(orgLorg.getOrgName())){
                            		orgNames.append("-").append(orgLorg.getOrgName());
                            	}
                            }
                        }
                    }
                }
        	}
        	if("1".equals(deliveryOption)){
        		vo.setOrgNames(orgnameTmp.toString());       		
        	}else{
        		vo.setOrgNames(orgNames.toString());       		
        	}
        }
        if(OrderType.presell_org.getCode().equals(subOrder.getType())){//预售获取所属机构
        	if(EmptyChecker.isNotEmpty(deliveryOption) && 
        			DeliveryType.take.getCode().equals(deliveryOption)){//配送方式：自提  获取网点的所属机构
        		vo.setSubTotalMoneyPresell(subTotalMoneyOne);//自提
        	}else{
        		vo.setSubTotalMoneyPresell(subTotalMoneyZero);//配送
        	}
        }
        vo.setProductName(org.apache.commons.lang.StringUtils.join(list.toArray(), ","));
        vo.setProductList(productList==null?new ArrayList<ProductVo>():productList);
        long st1 = System.currentTimeMillis();
        // 从大订单中获取订单状态和购买会员名
        /*OrderMongo orderMongo = orderSupport.getOrderById(subOrder.getClientId(), subOrder.getOrderId());*/
        LogCvt.info("查询大订单耗时：(" + (System.currentTimeMillis() - st1) + ") 毫秒");
        if (EmptyChecker.isEmpty(orderMongo)) {
            LogCvt.error("查询大订单数据位空， 大订单号为：" + subOrder.getOrderId());
            ResultCode resultCode = ResultCode.member_order_is_empty;
            throw new FroadBusinessException(resultCode.getCode(), resultCode.getMsg());
        }
        // 取从表， 通过从表条件查询数据
        vo.setOrderStatus(subOrder.getOrderStatus());
        //订单优化:子订单所有商品券的消费状态 1:未提货 2：已提货3：部分提货
        if(newConsumeStatus!=0){
        	vo.setConsumeStatus(String.valueOf(newConsumeStatus));       	
        }
        // 线上线下积分获取大订单积分 
        /*if(SubOrderType.offline_points_org.getCode().equals(subOrder.getType()) || SubOrderType.online_points_org.getCode().equals(subOrder.getType())) {
            vo.setPoint(getPoint(orderMongo));
        }*/
        if(points!=0){
        	vo.setPoint(Long.valueOf(points));//积分兑换用到-子订单的商品的积分总和
        }
        vo.setSubTotalMoney(subTotalMoney); // 显示子订单金额
        vo.setPaymentMethod(orderMongo.getPaymentMethod()); // 支付方式
        vo.setCreateSoure(orderMongo.getCreateSource()); // 创建来源

        vo.setCreateTime(subOrder.getCreateTime()); // 创建时间(购买时间)
        vo.setOrgCode(getOrgCode(subOrder)); // 机构代码

        // 获取订单子状态d
        vo.setRefundState(SubOrderRefundState.REFUND_INIT.getCode());
        if (EmptyChecker.isNotEmpty(subOrder.getRefundState())) {
            vo.setRefundState(subOrder.getRefundState());
        }
        
        //2015.07.27 加字段：商品数量
        vo.setQuantity(quantity);

        return vo;
    }
    
    /**
     * 数据转换-订单导出
     * @param subOrder
     * @param orderMap
     * @return
     * @throws FroadBusinessException
     */
    /**
     * @param subOrder 子订单
     * @param orderMap 大订单
     * @param map  商品结算信息
     * @param recvMap   收货信息
     * @param takeStatusMap  商品提货状态
     * @return
     * @throws FroadBusinessException
     */
    private QueryOrderByBankManageVo getSubOrderByBankManageVo(SubOrderMongo subOrder,Map<String,OrderMongo> orderMap,Map<String,Integer> settlementMap,Map<String,RecvInfo> recvMap,Map<String,String> takeStatusMap,Map<String,String> orgMap,String deliveryOption) throws FroadBusinessException {
        QueryOrderByBankManageVo vo = new QueryOrderByBankManageVo();
        vo.setOrderId(subOrder.getOrderId());
        vo.setSubOrderId(subOrder.getSubOrderId());
        vo.setMerchantName(subOrder.getMerchantName());

        //只有支付完成才有配送状态
        if (OrderType.special_merchant.getCode().equals(subOrder.getType()) && OrderStatus.paysuccess.getCode().equals(subOrder.getOrderStatus())) {
            vo.setDeliveryState(subOrder.getDeliveryState());
        }

        double subTotalMoney = 0;
        int quantity = 0; //子订单商品数
        int settlementNum = 0;//已结算数量
        List<ProductVo> productList = new ArrayList<ProductVo>();
        for (ProductMongo product : subOrder.getProducts()) {
        	//如果配送方式不为空，对商品进行过滤
        	if(EmptyChecker.isNotEmpty(deliveryOption) && !StringUtils.equals(deliveryOption, product.getDeliveryOption())){
        		continue;
        	}
        	
        	int count = EmptyChecker.isEmpty(product.getQuantity())? 0 : product.getQuantity();
            int vipCount = EmptyChecker.isEmpty(product.getVipQuantity())? 0 : product.getVipQuantity();
        	
        	ProductVo productVo = new ProductVo();
        	productVo.setProductName(product.getProductName());
        	productVo.setQuantity(count);
        	productVo.setVipQuantity(vipCount);
        	productVo.setVipMoney(product.getVipMoney());
        	productVo.setMoney(product.getMoney());
        	
        	if(EmptyChecker.isNotEmpty(product.getOrgCode())){
        		if(EmptyChecker.isNotEmpty(orgMap.get(product.getOrgCode()))){
        			productVo.setOrgNames(orgMap.get(product.getOrgCode()));
        		}else{
        			if(EmptyChecker.isNotEmpty(product.getOrgName())){
        				LogCvt.error("[数据问题!!!]子订单商品的网点机构号在mysql中查不到，机构名取冗余信息! {orderId:"+subOrder.getOrderId()+",orgCode="+product.getOrgCode()+",orgName="+product.getOrgName()+"}");
        				productVo.setOrgNames(product.getOrgName());
        			}else{
        				productVo.setOrgNames("--");
        			}
        			
        		}
        	}
        	
            subTotalMoney = Arith.add(subTotalMoney, getSubTotalMoney(product));
            quantity += count + vipCount;
            
            //商品结算数量  bug3426 去掉结算状态
            /*int productSettlementNum = 0;
	        if(EmptyChecker.isNotEmpty(settlementMap) && EmptyChecker.isNotEmpty(settlementMap.get(subOrder.getSubOrderId()+"_"+product.getProductId()))){
	        	productSettlementNum = settlementMap.get(subOrder.getSubOrderId()+"_"+product.getProductId());
	        	settlementNum += productSettlementNum;
	        }
	        //商品结算状态
	        productVo.setSettlementStatus("未结算");
	        // 全部结算或者部分结算情况下为已结算
	        if (settlementNum > 0) {
	        	productVo.setSettlementStatus("已结算"+settlementNum+"件");
	        }*/
	        
	        //提货状态
	        String takeStatus = "未提货";
	    	if (EmptyChecker.isNotEmpty(takeStatusMap) && EmptyChecker.isNotEmpty(takeStatusMap.get(subOrder.getOrderId()+"_"+subOrder.getSubOrderId()+"_"+product.getProductId()))) {
	    		takeStatus = takeStatusMap.get(subOrder.getOrderId()+"_"+subOrder.getSubOrderId()+"_"+product.getProductId());
	    	}
	    	productVo.setDeliveryState(takeStatus);
            productList.add(productVo);
        }
        vo.setProductList(productList);

        // 从大订单中获取订单状态和购买会员名
        OrderMongo orderMongo = orderMap.get(subOrder.getClientId()+"_"+ subOrder.getOrderId());
        if (EmptyChecker.isEmpty(orderMongo)) {
            LogCvt.error("查询大订单数据为空， 大订单号为：" + subOrder.getOrderId());
            ResultCode resultCode = ResultCode.member_order_is_empty;
            throw new FroadBusinessException(resultCode.getCode(), resultCode.getMsg());
        }
        // 取从表， 通过从表条件查询数据
        vo.setOrderStatus(subOrder.getOrderStatus());
        
        // 线上线下积分获取大订单积分 
        /*if(SubOrderType.offline_points_org.getCode().equals(subOrder.getType()) || SubOrderType.online_points_org.getCode().equals(subOrder.getType())) {
            vo.setPoint(getPoint(orderMongo));
        }*/
        
        vo.setSubTotalMoney(subTotalMoney); // 显示子订单金额
        vo.setPaymentMethod(orderMongo.getPaymentMethod()); // 支付方式
        vo.setCreateSoure(orderMongo.getCreateSource()); // 创建来源

        vo.setCreateTime(subOrder.getCreateTime()); // 创建时间
        vo.setOrgCode(getOrgCode(subOrder)); // 机构代码

        // 获取订单子状态
        vo.setRefundState(SubOrderRefundState.REFUND_INIT.getCode());
        if (EmptyChecker.isNotEmpty(subOrder.getRefundState())) {
            vo.setRefundState(subOrder.getRefundState());
        }
        
        //商品数量
        vo.setQuantity(quantity);
        
        //所属机构
        if(EmptyChecker.isNotEmpty(orgMap)){
        	vo.setOrgNames(getOrgNames(subOrder,orgMap));
        }else{
        	vo.setOrgNames(getOrgNames(subOrder));
        }
        
        //收货地址
    	if (EmptyChecker.isNotEmpty(recvMap)) {
    		RecvInfo recvInfo = recvMap.get(subOrder.getOrderId());
    		DeliverInfoVo deliverInfoVo = new DeliverInfoVo();
        	if (EmptyChecker.isNotEmpty(recvInfo)) {
        		deliverInfoVo.setConsignee(EmptyChecker.isEmpty(recvInfo.getConsignee())?"":recvInfo.getConsignee());
        		deliverInfoVo.setAddress(EmptyChecker.isEmpty(recvInfo.getAddress())?"":recvInfo.getAddress());
        		deliverInfoVo.setPhone(EmptyChecker.isEmpty(recvInfo.getPhone())?"":recvInfo.getPhone());
            }
        	vo.setDeliverInfoVo(deliverInfoVo);
    	}
    	//线下积分兑换操作人
    	vo.setMerchantUserName(subOrder.getOperatorName());
    	
        return vo;
    }

    /**
     * 面对面订单转换为Vo对象
     * 
     * @param order
     * @return <pre>
     * 
     * @Description: 对象转换 
     * @version V1.0 修改人：Arron 日期：2015年4月8日 下午4:51:33
     * 
     * </pre>
     */
    /**
     * @param order
     * @return
     */
    private QueryOrderByMerchantPhoneVo convertFacetfaceMerchantPhoneVo(OrderMongo order) {
        QueryOrderByMerchantPhoneVo vo = new QueryOrderByMerchantPhoneVo();

        vo.setCreateTime(order.getCreateTime());
        vo.setMemberName(order.getMemberName());
        if(EmptyChecker.isNotEmpty(order.getIsPrefPay()) && order.getIsPrefPay() == 1){
        	vo.setSubTotalMoney(EmptyChecker.isEmpty(order.getConsumeMoney())? 0 : Arith.div(order.getConsumeMoney(), 1000));
        }else{
        	Integer cutMoney = order.getCutMoney() == null ? 0 : order.getCutMoney();
        	vo.setSubTotalMoney(Arith.div(order.getTotalPrice()+cutMoney, 1000));
        }
        
        vo.setOrderStatus(order.getOrderStatus());
        vo.setSubOrderId(order.getOrderId()); // 团购设置为大订单号
        
        vo.setPhone(order.getPhone());
        vo.setPaymentTime(EmptyChecker.isNotEmpty(order.getPaymentTime())?order.getPaymentTime():0L);
        vo.setTotalPrice(Arith.div(order.getTotalPrice(), 1000));
        vo.setRealPrice(Arith.div(order.getRealPrice(), 1000));
        vo.setMerchantUserId(EmptyChecker.isNotEmpty(order.getMerchantUserId())?order.getMerchantUserId():0L);
        vo.setMerchantUserName(EmptyChecker.isNotEmpty(order.getMerchantUserName())?order.getMerchantUserName():"");
        // 结算信息
        List<Settlement> list = settlementSupport.querySettlement(order.getOrderId());
        // 面对面结算记录为空
        if (EmptyChecker.isEmpty(list)) {
        	vo.setSettlementStatus(SettlementStatus.unsettlemnt.getCode());
        } else {
        	vo.setSettlementStatus(list.get(0).getSettleState());
        }
        vo.setOutletId(EmptyChecker.isNotEmpty(order.getOutletId())?order.getOutletId():"");
        vo.setOutletName(EmptyChecker.isNotEmpty(order.getOutletName())?order.getOutletName():"");

        return vo;
    }

    private GetOrderDetailByMerchantPhoneVoRes convertOrderDetail(OrderMongo order) throws FroadBusinessException {
        GetOrderDetailByMerchantPhoneVoRes res = new GetOrderDetailByMerchantPhoneVoRes();

        //数据迁移中的老订单数据中memberCode可能为空
        if(null != order.getMemberCode()) {
        	res.setMemberCode(order.getMemberCode());
        }
        res.setMemberName(order.getMemberName());
        res.setCreateTime(order.getCreateTime());

        // 结算信息
        List<Settlement> list = settlementSupport.querySettlement(order.getOrderId());

        // 面对面结算记录为空
        if (EmptyChecker.isEmpty(list)) {
            res.setSettleState(SettlementStatus.unsettlemnt.getCode());
        } else {
            res.setSettleState(list.get(0).getSettleState());
        }
        res.setOutletId(order.getOutletId());
        res.setOrderStatus(order.getOrderStatus());
        res.setTotalPrice(getSubTotalMoney(order));
        res.setPaymentTime(getPaymentTime(order));

        /**
         * 2015-05-08 新增面对面显示商户名
         */
        res.setMerchantId(order.getMerchantId());
        res.setMerchantName(order.getMerchantName());
        
        //积分对应金额
        double pointMoney = 0;
        if(EmptyChecker.isNotEmpty(order.getBankPoints()) && order.getBankPoints()>0 && StringUtils.equals(order.getOrderStatus(), OrderStatus.paysuccess.getCode())){
        	if(EmptyChecker.isNotEmpty(order.getPointRate())){
        		Integer pointRate = Integer.valueOf(order.getPointRate());
        		pointMoney = Arith.divFloor(Arith.div(order.getBankPoints(), 1000), pointRate);
        	}
        }
        if(EmptyChecker.isNotEmpty(order.getFftPoints()) && order.getFftPoints()>0 && StringUtils.equals(order.getOrderStatus(), OrderStatus.paysuccess.getCode())){
        	Integer pointRate = 1;
        	if(EmptyChecker.isNotEmpty(order.getPointRate())){
        		pointRate = Integer.valueOf(order.getPointRate());
        	}
        	pointMoney = Arith.divFloor(Arith.div(order.getFftPoints(), 1000), pointRate);
        }
        res.setPointMoney(pointMoney);
        res.setCutMoney(EmptyChecker.isEmpty(order.getCutMoney())? 0 : Arith.div(order.getCutMoney(), 1000));
        if(EmptyChecker.isNotEmpty(order.getIsPrefPay()) && order.getIsPrefPay() == 1){
        	res.setConsumeMoney(EmptyChecker.isEmpty(order.getConsumeMoney())? 0 : Arith.div(order.getConsumeMoney(), 1000));
        }else{
        	Integer cutMoney = order.getCutMoney() == null ? 0 : order.getCutMoney();
        	res.setConsumeMoney(Arith.div(order.getTotalPrice()+cutMoney, 1000));
        }
        res.setDiscountMoney(EmptyChecker.isEmpty(order.getDiscountMoney())? 0 : Arith.div(order.getDiscountMoney(), 1000));
        res.setOutletName(EmptyChecker.isNotEmpty(order.getOutletName())?order.getOutletName():"");
        res.setOutletImg(RedisCommon.getOutletImg(order.getClientId(),order.getMerchantId(),order.getOutletId()));
        res.setMerchantUserId(EmptyChecker.isNotEmpty(order.getMerchantUserId())?order.getMerchantUserId():0L);
        res.setMerchantUserName(EmptyChecker.isNotEmpty(order.getMerchantUserName())?order.getMerchantUserName():"");
        res.setPhone(order.getPhone());
        res.setTotalCash(Arith.div(order.getRealPrice(), 1000));
        
        return res;
    }

    /**
     * 子订单转换为VO对象
     * 
     * @param subOrder
     * @return
     * @throws FroadBusinessException
     *             <pre>
     * 
     * @Description: 对象转换 
     * @version V1.0 修改人：Arron 日期：2015年4月9日 上午11:49:58
     * 
     * </pre>l
     */
    private GetOrderDetailByMerchantPhoneVoRes convertSubOrderDetail(OrderMongo order, SubOrderMongo subOrder) throws FroadBusinessException {
        GetOrderDetailByMerchantPhoneVoRes res = new GetOrderDetailByMerchantPhoneVoRes();

        res.setMemberName(order.getMemberName());
        if(null != order.getMemberCode()) {
        	res.setMemberCode(order.getMemberCode());
        }
        res.setCreateTime(subOrder.getCreateTime());
        res.setOutletId(order.getOutletId());
        res.setOrderStatus(order.getOrderStatus());
        res.setSubOrderId(subOrder.getSubOrderId());
        
        String refundState = SubOrderRefundState.REFUND_INIT.getCode();
        try {
            if (EmptyChecker.isNotEmpty(subOrder.getRefundState())) {
                refundState = subOrder.getRefundState();
            }
        } catch (Exception e) {
        }
        res.setRefundState(refundState);
        

        // 商品数量
        int number = 0;
        // 购买商品金额
        double totalMoney = 0;
        List<ProductInfoVo> listProductVo = new ArrayList<ProductInfoVo>();
        
        //抵扣积分
        int cutPoint = 0;
        int sn = 0;//结算数量（20150902-增加结算数量）
        for (ProductMongo product : subOrder.getProducts()) {

            ProductInfoVo vo = new ProductInfoVo();
            double productMoney = getSubTotalMoney(product);
            int quantity = getProductQuantity(product);

            totalMoney = Arith.add(totalMoney, productMoney);
            number += quantity;

            vo.setDeliveryMoney(getDeliveryMoney(product));
            vo.setMoney(getMoney(product));
            vo.setProductImage(product.getProductImage());
            vo.setProductName(product.getProductName());
            vo.setQuantity(quantity);
            vo.setSubTotalMoney(productMoney);
            vo.setProductId(product.getProductId());
            
            if(EmptyChecker.isNotEmpty(product.getTotalPoint())){
            	cutPoint += product.getTotalPoint();
            }
            
            /**退款数量*/
            refundProduct(vo, subOrder.getSubOrderId(), subOrder.getOrderId());
            
            // 如果是名优特惠，发货状态是在订单上
            if (OrderType.special_merchant.getCode().equals(subOrder.getType())) {
                vo.setDeliverState(subOrder.getDeliveryState());
            } else {
                vo.setDeliverState(product.getDeliveryState());
            }
            if("1".equals(product.getCommentState())) {
                vo.setIsOutletComment(true);
            } else {
                vo.setIsOutletComment(false);
            }
            
            long st2 = System.currentTimeMillis();
            // 获取结算信息
            List<Settlement> settlementList = settlementSupport.querySettlement(subOrder.getSubOrderId(), product.getProductId());
            LogCvt.info("关联查询-结算信息-耗时：" + (System.currentTimeMillis() - st2));
            
            int productSettlementNum = 0;
            if (EmptyChecker.isNotEmpty(settlementList)) {
                // 没有结算记录
                for (Settlement s : settlementList) {
                    if (SettlementStatus.settlementsucc.getCode().equals(s.getSettleState())) {
                        sn += s.getProductCount();
                        productSettlementNum += s.getProductCount();
                    }
                }
            }
            vo.setSettlementNumber(String.valueOf(productSettlementNum));
            vo.setSettlementStatus(SettlementStatus.unsettlemnt.getCode());
            // 全部结算或者部分结算情况下为已结算
            if (productSettlementNum > 0) {
            	vo.setSettlementStatus(SettlementStatus.settlementsucc.getCode());
            }

            listProductVo.add(vo);
        }
        OrderDataResult orderDataResult = OrderQueryLogicHelper.getSubOrderMoney(subOrder);
    	double orderPoint = orderDataResult.getPayPointMoney();
    	
    	res.setTotalCash(orderDataResult.getPayCashAndCutMoney());
        
//        res.setCutPoint(Arith.div(cutPoint, 1000));
    	res.setCutPoint(orderPoint);
        
        // 结算状态（20150902-增加结算状态）
        res.setSettleState(SettlementStatus.unsettlemnt.getCode());
        // 全部结算或者部分结算情况下为已结算
        if (sn > 0) {
        	res.setSettleState(SettlementStatus.settlementsucc.getCode());
        }

        /**
         * 收货人和物流信息（2015-05-05-14：15 update arron）
         */
        if (EmptyChecker.isNotEmpty(order) && !BooleanUtils.toBoolean(order.getIsQrcode())) {
            res.setDeliveryStatus(subOrder.getDeliveryState());
            // 收货地址
            String recvId = order.getRecvId();
            RecvInfo recvInfo = orderSupport.getRecvInfo(order.getClientId(), order.getMemberCode(), recvId);
            if (EmptyChecker.isNotEmpty(recvInfo)) {
                res.setConsignee(EmptyChecker.isEmpty(recvInfo.getConsignee())?"":recvInfo.getConsignee());
                res.setAddress(EmptyChecker.isEmpty(recvInfo.getAddress())?"":recvInfo.getAddress());
                res.setPhone(EmptyChecker.isEmpty(recvInfo.getPhone())?"":recvInfo.getPhone());
                res.setAreaId(EmptyChecker.isEmpty(recvInfo.getAreaId())?0:recvInfo.getAreaId());
            }
            // 名优特惠-增加已发货物流信息
            if (OrderType.special_merchant.getCode().equals(subOrder.getType())) {
                // 发货内容
                ShippingOrderMongo shippingOrderMongo = orderSupport.getShippingInfo(subOrder.getOrderId() + "_" + subOrder.getSubOrderId());
                if (EmptyChecker.isNotEmpty(shippingOrderMongo)) {
                    res.setDeliveryCorpId(shippingOrderMongo.getDeliveryCorpId());
                    res.setDeliveryCorpName(shippingOrderMongo.getDeliveryCorpName());
                    res.setTrackingNo(shippingOrderMongo.getTrackingNo());
                    res.setShippingTime(shippingOrderMongo.getShippingTime());
                }
            }

        }

        res.setProductInfos(listProductVo);
        res.setQuantity(number);
//        res.setTotalPrice(totalMoney);
        res.setTotalPrice(orderDataResult.getPayCashAndCutMoney());

        return res;
    }

    /**
     * 子订单对象转换为Vo
     * 
     * @param subOrder
     * @return <pre>
     * 
     * @Description: 对象转换 
     * @version V1.0 修改人：Arron 日期：2015年4月8日 下午4:51:05
     * 
     * </pre>
     */
    private QueryOrderByMerchantPhoneVo convertSubOrder(SubOrderMongo subOrder) throws FroadBusinessException {
        QueryOrderByMerchantPhoneVo vo = new QueryOrderByMerchantPhoneVo();

        vo.setOrderStatus(subOrder.getOrderStatus());
        vo.setMemberName(subOrder.getMemberName());
        vo.setSubOrderId(subOrder.getSubOrderId());
        vo.setCreateTime(subOrder.getCreateTime());
        // 大订单号
        vo.setOrderId(subOrder.getOrderId());
        String refundState = SubOrderRefundState.REFUND_INIT.getCode();
        try {
            if (EmptyChecker.isNotEmpty(subOrder.getRefundState())) {
                refundState = subOrder.getRefundState();
            }
        } catch (Exception e) {
        }
        vo.setRefundState(refundState);
        if(StringUtils.equals(subOrder.getType(), SubOrderType.special_merchant.getCode()) || StringUtils.equals(subOrder.getType(), SubOrderType.boutique.getCode())){
        	vo.setDeliveryStatus(subOrder.getDeliveryState());
        }
        
        int number = 0;
        double totalMoney = 0;
        List<String> images = new ArrayList<String>();
        for (ProductMongo product : subOrder.getProducts()) {
            totalMoney = Arith.add(totalMoney, getSubTotalMoney(product));
            images.add(product.getProductImage());
            number += getProductQuantity(product);
        }
        vo.setProductImages(images);
        vo.setSubTotalMoney(totalMoney);
        vo.setQuantity(number);
        return vo;
    }

    /**
     * 获取子订单一种商品多个数量商品的总金额
     */
    private double getSubTotalMoney(ProductMongo product) {
    	int money = EmptyChecker.isEmpty(product.getMoney()) ? 0 : product.getMoney();
		int quantity = EmptyChecker.isEmpty(product.getQuantity()) ? 0 : product.getQuantity();
		int vipMoney = EmptyChecker.isEmpty(product.getVipMoney()) ? 0 : product.getVipMoney();
		int vipQuantity = EmptyChecker.isEmpty(product.getVipQuantity()) ? 0 : product.getVipQuantity();
		int deliveryMoney = EmptyChecker.isEmpty(product.getDeliveryMoney()) ? 0 : product.getDeliveryMoney();
		double productTotalPrice = Arith.add(Arith.mul(money, quantity),Arith.mul(vipMoney, vipQuantity));
        return Arith.div(Arith.add(productTotalPrice,deliveryMoney), 1000);
    }
    
    /**
     * 获取子订单商品实付金额
     * @param product
     * @return
     */
    private double getSubOrderTotalCash(SubOrderMongo subOrderMongo) {
    	double totalCash = 0;
    	if(EmptyChecker.isNotEmpty(subOrderMongo)){
    		if(EmptyChecker.isNotEmpty(subOrderMongo.getProducts())){
    			for(ProductMongo product : subOrderMongo.getProducts()){
    				int deliveryMoney = EmptyChecker.isEmpty(product.getDeliveryMoney()) ? 0 : product.getDeliveryMoney();
    				int cash = EmptyChecker.isEmpty(product.getTotalCash()) ? 0 : product.getTotalCash();
    				totalCash = Arith.add(totalCash,Arith.div(cash, 1000));
    			}
    		}
    	}
    	return totalCash;
    }
    
 
    
    public static void main(String[] args) {
        System.setProperty("CONFIG_PATH", "./config/");
        OrderQueryLogicImpl impl = new OrderQueryLogicImpl();
//        System.out.println(impl.getRefund("02887F608000", ""));
        //System.out.println(impl.getRefund("01BA04CA8000", "", ""));
    	Date tt = new Date(1442807304478l);
        System.out.println(tt);
        
        try {
			ResultBean result = impl.getFacetfaceOrderDetailOfBankByOrderId("taizhou","17A26FA40000");
			System.out.println(JSON.toJSONString(result,true));
		} catch (FroadBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private double getSubTotalMoney(OrderMongo order) {
        return Arith.div(order.getTotalPrice(), 1000);
    }

    /**
     * 商品单价
     * 
     * @param product
     * @return <pre>
     * 
     * @Description: TODO(用一句话描述该文件做什么) 
     * @version V1.0 修改人：Arron 日期：2015年5月1日 下午7:27:14
     * 
     * </pre>
     */
    private double getMoney(ProductMongo product) {
        return Arith.div(product.getMoney(), 1000);
    }
    private double getVipMoney(ProductMongo product) {
        return Arith.div(product.getVipMoney(), 1000);
    }

    private double getRealPrice(OrderMongo order) {
        return Arith.div(order.getRealPrice(), 1000);
    }

    private String getOrgCode(SubOrderMongo subOrder) {
        String orgCode = null;
        try {
            if (EmptyChecker.isNotEmpty(subOrder.getForgCode())) {
                orgCode = subOrder.getForgCode();
            }
        } catch (Exception e) {
        }
        try {
            if (EmptyChecker.isNotEmpty(subOrder.getSorgCode())) {
                orgCode = subOrder.getSorgCode();
            }
        } catch (Exception e) {
        }
        try {
            if (EmptyChecker.isNotEmpty(subOrder.getTorgCode())) {
                orgCode = subOrder.getTorgCode();
            }
        } catch (Exception e) {
        }
        try {
            if (EmptyChecker.isNotEmpty(subOrder.getLorgCode())) {
                orgCode = subOrder.getLorgCode();
            }
        } catch (Exception e) {
        }
        return orgCode;
    }
    
    /**
     * 获取子订单所属四级机构
     * @param subOrder
     * @return
     */
    private String getOrgNames(SubOrderMongo subOrder) {
        StringBuffer sb = new StringBuffer("");
        if (EmptyChecker.isNotEmpty(subOrder.getForgCode()) && !StringUtils.equals(subOrder.getForgCode(), "0")) {
            Org org = commonLogic.getOrgByOrgCode(subOrder.getForgCode(), subOrder.getClientId());
            if(EmptyChecker.isNotEmpty(org) && EmptyChecker.isNotEmpty(org.getOrgName())){
            	sb.append(org.getOrgName());
            }
        }
        if (EmptyChecker.isNotEmpty(subOrder.getSorgCode()) && !StringUtils.equals(subOrder.getSorgCode(), "0")) {
            Org org = commonLogic.getOrgByOrgCode(subOrder.getSorgCode(), subOrder.getClientId());
            if(EmptyChecker.isNotEmpty(org) && EmptyChecker.isNotEmpty(org.getOrgName())){
            	sb.append("-"+org.getOrgName());
            }
        }
        if (EmptyChecker.isNotEmpty(subOrder.getTorgCode()) && !StringUtils.equals(subOrder.getTorgCode(), "0")) {
            Org org = commonLogic.getOrgByOrgCode(subOrder.getTorgCode(), subOrder.getClientId());
            if(EmptyChecker.isNotEmpty(org) && EmptyChecker.isNotEmpty(org.getOrgName())){
            	sb.append("-"+org.getOrgName());
            }
        }
        if (EmptyChecker.isNotEmpty(subOrder.getLorgCode()) && !StringUtils.equals(subOrder.getLorgCode(), "0")) {
            Org org = commonLogic.getOrgByOrgCode(subOrder.getLorgCode(), subOrder.getClientId());
            if(EmptyChecker.isNotEmpty(org) && EmptyChecker.isNotEmpty(org.getOrgName())){
            	sb.append("-"+org.getOrgName());
            }
        }
        return splitOrgNames(sb.toString());
    }
    
    /**
     * 获取大订单所属四级机构
     * @param subOrder
     * @return
     */
    private String getOrgNames(OrderMongo order) {
        StringBuffer sb = new StringBuffer("");
        if (EmptyChecker.isNotEmpty(order.getForgCode()) && !StringUtils.equals(order.getForgCode(), "0")) {
            Org org = commonLogic.getOrgByOrgCode(order.getForgCode(), order.getClientId());
            sb.append(org.getOrgName());
        }
        if (EmptyChecker.isNotEmpty(order.getSorgCode()) && !StringUtils.equals(order.getSorgCode(), "0")) {
            Org org = commonLogic.getOrgByOrgCode(order.getSorgCode(), order.getClientId());
            sb.append("-"+org.getOrgName());
        }
        if (EmptyChecker.isNotEmpty(order.getTorgCode()) && !StringUtils.equals(order.getTorgCode(), "0")) {
            Org org = commonLogic.getOrgByOrgCode(order.getTorgCode(), order.getClientId());
            sb.append("-"+org.getOrgName());
        }
        if (EmptyChecker.isNotEmpty(order.getLorgCode()) && !StringUtils.equals(order.getLorgCode(), "0")) {
            Org org = commonLogic.getOrgByOrgCode(order.getLorgCode(), order.getClientId());
            sb.append("-"+org.getOrgName());
        }
        return splitOrgNames(sb.toString());
    }
    
    /**
     * 获取大订单所属四级机构
     * @param subOrder
     * @return
     */
    private String getOrgNames(OrderMongo order,Map<String,String> orgMap) {
    	StringBuffer sb = new StringBuffer("");
    	if(EmptyChecker.isNotEmpty(orgMap)){
            if (EmptyChecker.isNotEmpty(order.getForgCode()) && !StringUtils.equals(order.getForgCode(), "0")) {
            	if(EmptyChecker.isNotEmpty(orgMap.get(order.getForgCode()))){
            		sb.append(orgMap.get(order.getForgCode()));
            	}else{
            		LogCvt.error("机构号："+order.getForgCode()+"，未查到对应机构名称");
            	}
            }
            if (EmptyChecker.isNotEmpty(order.getSorgCode()) && !StringUtils.equals(order.getSorgCode(), "0")) {
            	if(EmptyChecker.isNotEmpty(orgMap.get(order.getSorgCode()))){
            		sb.append("-"+orgMap.get(order.getSorgCode()));
            	}else{
            		LogCvt.error("机构号："+order.getSorgCode()+"，未查到对应机构名称");
            	}
            }
            if (EmptyChecker.isNotEmpty(order.getTorgCode()) && !StringUtils.equals(order.getTorgCode(), "0")) {
            	if(EmptyChecker.isNotEmpty(orgMap.get(order.getTorgCode()))){
            		sb.append("-"+orgMap.get(order.getTorgCode()));
            	}else{
            		LogCvt.error("机构号："+order.getTorgCode()+"，未查到对应机构名称");
            	}
            }
            if (EmptyChecker.isNotEmpty(order.getLorgCode()) && !StringUtils.equals(order.getLorgCode(), "0")) {
            	if(EmptyChecker.isNotEmpty(orgMap.get(order.getLorgCode()))){
            		sb.append("-"+orgMap.get(order.getLorgCode()));
            	}else{
            		LogCvt.error("机构号："+order.getLorgCode()+"，未查到对应机构名称");
            	}
            }
    	}
    	return splitOrgNames(sb.toString());
    }
    
    /**
     * 获取大订单所属四级机构
     * @param subOrder
     * @return
     */
    private String getOrgNames(SubOrderMongo order,Map<String,String> orgMap) {
    	StringBuffer sb = new StringBuffer("");
    	if(EmptyChecker.isNotEmpty(orgMap)){
            if (EmptyChecker.isNotEmpty(order.getForgCode()) && !StringUtils.equals(order.getForgCode(), "0")) {
            	if(EmptyChecker.isNotEmpty(orgMap.get(order.getForgCode()))){
            		sb.append(orgMap.get(order.getForgCode()));
            	}else{
            		LogCvt.error("机构号："+order.getForgCode()+"，未查到对应机构名称");
            	}
            }
            if (EmptyChecker.isNotEmpty(order.getSorgCode()) && !StringUtils.equals(order.getSorgCode(), "0")) {
            	if(EmptyChecker.isNotEmpty(orgMap.get(order.getSorgCode()))){
            		sb.append("-"+orgMap.get(order.getSorgCode()));
            	}else{
            		LogCvt.error("机构号："+order.getSorgCode()+"，未查到对应机构名称");
            	}
            }
            if (EmptyChecker.isNotEmpty(order.getTorgCode()) && !StringUtils.equals(order.getTorgCode(), "0")) {
            	if(EmptyChecker.isNotEmpty(orgMap.get(order.getTorgCode()))){
            		sb.append("-"+orgMap.get(order.getTorgCode()));
            	}else{
            		LogCvt.error("机构号："+order.getTorgCode()+"，未查到对应机构名称");
            	}
            }
            if (EmptyChecker.isNotEmpty(order.getLorgCode()) && !StringUtils.equals(order.getLorgCode(), "0")) {
            	if(EmptyChecker.isNotEmpty(orgMap.get(order.getLorgCode()))){
            		sb.append("-"+orgMap.get(order.getLorgCode()));
            	}else{
            		LogCvt.error("机构号："+order.getLorgCode()+"，未查到对应机构名称");
            	}
            }
    	}
    	return splitOrgNames(sb.toString());
    }
    
    public static String splitOrgNames(String orgNameStr) {
		String[] orgNames = orgNameStr.split("-");
		String name = orgNameStr;
		if(orgNames.length == 3){
			name = orgNames[1]+"-"+orgNames[2];
		}
		if(orgNames.length == 4){
			name = orgNames[3]+"-"+orgNames[4];
		}
		return name;
	}

    /**
     * 获取商品数量
     * 
     * @param product
     * @return <pre>
     * 
     * @version V1.0 修改人：Arron 日期：2015年4月14日 上午11:38:47
     * 
     * </pre>
     */
    private int getProductQuantity(ProductMongo product) {
        return product.getQuantity() + product.getVipQuantity();
    }

    /**
     * 按支付方式获取积分-按规则获取不同的方付通积分和银行积分
     * 
     * @param orderMongo
     * @return <pre>
     * 
     * @version V1.0 修改人：Arron 日期：2015年4月14日 上午11:38:58
     * 
     * </pre>
     */
    private double getPoint(OrderMongo orderMongo) {
        int point = 0;
        // 积分 or 积分+现金
        if (EmptyChecker.isNotEmpty(orderMongo.getPaymentMethod()) && !OrderStatus.create.getCode().equals(orderMongo.getOrderStatus())) {
            if (StringUtils.equals(PaymentMethod.froadPoints.getCode(), orderMongo.getPaymentMethod())
                    || StringUtils.equals(PaymentMethod.froadPointsAndCash.getCode(), orderMongo.getPaymentMethod())) {
                point = orderMongo.getFftPoints();
            } else if (StringUtils.equals(PaymentMethod.bankPoints.getCode(), orderMongo.getPaymentMethod())
                    || StringUtils.equals(PaymentMethod.bankPointsAndCash.getCode(), orderMongo.getPaymentMethod())) {
                point = orderMongo.getBankPoints();
            }
        }
        return Arith.div(point, 1000);
    }
    
    /**
     * 获取子订单抵扣积分
     * @param subOrderMongo
     * @return
     */
    private double getSubOrderExchangePoint(SubOrderMongo subOrderMongo) {
        double money = 0;
        if(EmptyChecker.isNotEmpty(subOrderMongo)){
        	if(EmptyChecker.isNotEmpty(subOrderMongo.getProducts())){
        		for(ProductMongo product : subOrderMongo.getProducts()){
        			int quantity = 0;
        			int vipQuantity = 0;
        			int price = 0;
        			int vipPrice = 0;
        			if(EmptyChecker.isNotEmpty(product.getQuantity())){
        				quantity = product.getQuantity();
        			}
        			if(EmptyChecker.isNotEmpty(product.getVipQuantity())){
        				vipQuantity = product.getVipQuantity();
        			}
        			if(EmptyChecker.isNotEmpty(product.getMoney())){
        				price = product.getMoney();
        			}
        			if(EmptyChecker.isNotEmpty(product.getVipMoney())){
        				vipPrice = product.getVipMoney();
        			}
        			money = Arith.add(money, Arith.add(Arith.mul2(price, quantity), Arith.mul2(vipPrice, vipQuantity)));
        		}
        	}
        	
        }
        return Arith.div(money,1000);
    }
    
    private double getSubOrderCutPointMoney(SubOrderMongo subOrderMongo) {
        int cash = 0;
        int cutMoney = 0;
        double money = 0;
        if(EmptyChecker.isNotEmpty(subOrderMongo)){
        	if(EmptyChecker.isNotEmpty(subOrderMongo.getProducts())){
        		for(ProductMongo product : subOrderMongo.getProducts()){
        			if(EmptyChecker.isNotEmpty(product.getTotalCash())){
        				cash += product.getTotalCash();
        			}
        			if(EmptyChecker.isNotEmpty(product.getTotalCutMoney())){
        				cutMoney += product.getTotalCutMoney();
        			}
        			int quantity = 0;
        			int vipQuantity = 0;
        			int price = 0;
        			int vipPrice = 0;
        			if(EmptyChecker.isNotEmpty(product.getQuantity())){
        				quantity = product.getQuantity();
        			}
        			if(EmptyChecker.isNotEmpty(product.getVipQuantity())){
        				vipQuantity = product.getVipQuantity();
        			}
        			if(EmptyChecker.isNotEmpty(product.getMoney())){
        				price = product.getMoney();
        			}
        			if(EmptyChecker.isNotEmpty(product.getVipMoney())){
        				vipPrice = product.getVipMoney();
        			}
        			money = Arith.add(money, Arith.add(Arith.mul2(price, quantity), Arith.mul2(vipPrice, vipQuantity)));
        		}
        	}
        	
        }
        return Arith.div(Arith.sub(Arith.sub(money, cutMoney),cash), 1000);
    }
    
    /**
     * 获取子订单抵扣积分对应的钱
     * @param subOrderMongo
     * @return
     */
    private double getSubOrderCutPoint(SubOrderMongo subOrderMongo) {
        int point = 0;
        if(EmptyChecker.isNotEmpty(subOrderMongo)){
        	if(EmptyChecker.isNotEmpty(subOrderMongo.getProducts())){
        		for(ProductMongo product : subOrderMongo.getProducts()){
        			if(EmptyChecker.isNotEmpty(product.getTotalPoint())){
        				point += product.getTotalPoint();
        			}
        		}
        	}
        	
        }
        return Arith.div(point, 1000);
    }
    
    /**
     * 获取子订单实付金额
     * @param subOrderMongo
     * @return
     */
    private double getSubOrderCutMoney(SubOrderMongo subOrderMongo) {
        int totalCutMoney = 0;
        if(EmptyChecker.isNotEmpty(subOrderMongo)){
        	if(EmptyChecker.isNotEmpty(subOrderMongo.getProducts())){
        		for(ProductMongo product : subOrderMongo.getProducts()){
        			if(EmptyChecker.isNotEmpty(product.getTotalCutMoney())){
        				totalCutMoney +=(product.getTotalCutMoney());
        			}
        		}
        	}
        	
        }
        return Arith.div(totalCutMoney, 1000);
    }

    /**
     * 积分抵扣
     * 
     * @param order
     * @return <pre>
     * 
     * @Description: 获取订单的支付的积分抵扣部分 
     * @version V1.0 修改人：Arron 日期：2015年4月14日 上午11:44:43
     * 
     * </pre>
     */
    private double getPointDiscount(OrderMongo order) {
        double point = 0;
        // 只有在支付完成之后才进行运算积分抵扣金额
        if(OrderStatus.paysuccess.getCode().equals(order.getOrderStatus())) {
            point = Arith.div(order.getTotalPrice() - order.getRealPrice(), 1000);
        }
        return point;
    }

    /**
     * 支付时间
     * 
     * @param orderMongo
     * @return long
     * 
     *         <pre>
     * 
     * @Description: 订单创建没有支付时间， 只有不是订单创建情况下才获取支付时间 
     * @version V1.0 修改人：Arron 日期：2015年4月28日 上午9:35:05
     * 
     * </pre>
     */
    private long getPaymentTime(OrderMongo orderMongo) {
        long paymentTime = 0;
        /**
         * 只有支付（成功|失败）情况下才有支付时间
         */
        if (OrderStatus.payfailed.getCode().equals(orderMongo.getOrderStatus()) || OrderStatus.paysuccess.getCode().equals(orderMongo.getOrderStatus())) {
            paymentTime = orderMongo.getPaymentTime();
        }
        return paymentTime;
    }

    /**
     * 运费计算
     * 
     * @param product
     */
    private double getDeliveryMoney(ProductMongo product) {
        return Arith.div(product.getDeliveryMoney(), 1000);
    }

    /**
     * 从Redis中获取商品基本信息
     * 
     * @param clientId
     * @param merchantId
     * @param productId
     * @return <pre>
     * 
     * @Description: TODO(用一句话描述该文件做什么) 
     * @version V1.0 修改人：Arron 日期：2015年5月1日 下午12:32:03
     * 
     * </pre>
     */
    private Map<String, String> getProduct(String clientId, String merchantId, String productId) {
        return commonLogic.getProductRedis(clientId, merchantId, productId);
    }
    
    private void refundProduct(ProductInfoVo vo, String subOrderId, String orderId) {
        int refundNumber = getRefund(subOrderId, orderId, vo.getProductId());
        vo.setSurplusNumber(vo.getQuantity()-refundNumber); // 总量减去退款数量
        vo.setRefundNumber(refundNumber); // 退款数量
    }
    
    private void refundProduct(ProductVo vo, String subOrderId, String orderId) {
        int refundNumber = getRefund(subOrderId, orderId, vo.getProductId());
        vo.setSurplusNumber(vo.getQuantity()-refundNumber); // 总量减去退款数量
        vo.setRefundNumber(refundNumber); // 退款数量
    }
    
    /**
     * 获取券的消费情况、最近消费券的消费时间等信息
     * @param orderId  大订单号
     * @param subOrderId 子订单号
     * @param productId 商品 ID
     * @return
     *<pre>
     *
     * @Description: 1. 未消费， 2. 已消费， 3. 部分消费， 只正对预售和团购 
     * @version V1.0 修改人：Arron 日期：2015年5月27日 上午11:30:32 
     *
     *</pre>
     */
    private Map<String,Object> getTicketConsumeState(String orderId, String subOrderId, String productId) {
        Map<String,Object> mapRes = new HashMap<String,Object>();
        // 默认为未消费
        Integer consumeStatus = 1;
        // 最近消费时间
        Long lastConsumeTime = 1l;
        //团购消费人
        String consignee = "";
        //消费人手机号码
        String phone="";
        //消费门店
        String outletName="";
        Integer takedNum = 0;//提货数量
        Integer refundNum = 0;//已退款数量
        String orgNameWangdian = "";
        String tcode="";
        //操作员id
        Long merchantUserId=0l;
        List<Ticket> tickets = ticketSupport.getTickets(orderId, subOrderId, productId);
        if (EmptyChecker.isNotEmpty(tickets)) {
            int i = 0; // 消费的券个数
            int j = 0; // 总券数
            long tempTime = 1l;
            for (Ticket t : tickets) {
                j += t.getQuantity();
                if (TicketStatus.consumed.getCode().equals(t.getStatus())) {
                    // 消费的券个数
                    i += t.getQuantity();
                    takedNum += t.getQuantity();
                }
                if (TicketStatus.refunded.getCode().equals(t.getStatus())) {
                    //计算预售退款的个数
                    refundNum += t.getQuantity();
                }
                if(EmptyChecker.isNotEmpty(t.getConsumeTime())){
                	tempTime= t.getConsumeTime();                	
                }
                if (tempTime > lastConsumeTime) {
                    // 最大的值设为最近消费时间
                	lastConsumeTime= tempTime; 
                }
                if("".equals(consignee)){
                	consignee = t.getMemberName();               	
                }
                if("".equals(phone)){
                	phone = t.getMobile();                	
                }
                if("".equals(outletName)){
                	outletName = t.getOutletName();               	
                }
                if("".equals(merchantUserId)){
                	merchantUserId = t.getMerchantUserId();                	
                }
                if("".equals(orgNameWangdian)){
                	orgNameWangdian=t.getOrgName();
                }
                if("".equals(tcode)){
                	tcode=t.getTicketId();
                }
            }
            if (i == 0) {
                //return consumeStatus;
            	mapRes.put("consumeStatus", consumeStatus);
            }
            if (i == j) {
                // 已消费
                consumeStatus = 2;
            } else if (i != 0 && i < j) {
                // 已消费的全小于总券数 == 部分消费
                consumeStatus = 3;
            }
        }
        mapRes.put("consumeStatus", consumeStatus);
        mapRes.put("takedNum", takedNum);
        mapRes.put("refundNum", refundNum);
        mapRes.put("orgNameWangdian", orgNameWangdian);
        mapRes.put("tcode", tcode);
        mapRes.put("lastConsumeTime", lastConsumeTime);
        mapRes.put("consignee", consignee);
        mapRes.put("phone", phone);
        mapRes.put("outletName", outletName);
        mapRes.put("merchantUserId", merchantUserId);
        return mapRes;
    }
    
    /**
     * 获取券的消费情况、最近消费券的消费时间等信息
     * @param orderId  大订单号
     * @param subOrderId 子订单号
     * @param productId 商品 ID
     * @return
     *<pre>
     *
     * @Description: 1. 未提货， 2. 已提货， 3. 部分提货， 只针对预售自提
     * @version V1.0 修改人：Arron 日期：2015年5月27日 上午11:30:32 
     *
     *</pre>
     */
    private Map<String,String> getTicketTakeState(List<SubOrderMongo> subOrderList,String deliveryOption) {
    	
    	List<String> orderIdList = new ArrayList<String>();
    	List<String> subOrderIdList = new ArrayList<String>();
    	List<String> productIdList = new ArrayList<String>();
    	if(EmptyChecker.isNotEmpty(subOrderList)){
    		for(SubOrderMongo subOrder : subOrderList){
    			orderIdList.add(subOrder.getOrderId());
    			subOrderIdList.add(subOrder.getSubOrderId());
    			if(EmptyChecker.isNotEmpty(subOrder.getProducts())){
    				for(ProductMongo product : subOrder.getProducts()){
    					if(StringUtils.equals(deliveryOption,product.getDeliveryOption())){
    						productIdList.add(product.getProductId());
    					}
    				}
    			}
    		}
    	}
    	
		Map<String, String> mapRes = new HashMap<String, String>();
		Map<String, Map<String,Integer>> takeNumMap = new HashMap<String, Map<String,Integer>>();
		List<Ticket> tickets = orderSupport.getTickets(orderIdList,subOrderIdList, productIdList);
		if (EmptyChecker.isNotEmpty(tickets)) {
			for (Ticket t : tickets) {
				String key = t.getOrderId()+"_"+t.getSubOrderId()+"_"+t.getProductId();
				Map<String,Integer> ticketMap = new HashMap<String, Integer>();
				int consumedNum = 0; // 消费的券个数
				int totalNum = t.getQuantity(); // 总券数
				
				if(EmptyChecker.isNotEmpty(takeNumMap.get(key)) && EmptyChecker.isNotEmpty(takeNumMap.get(key).get("totalNum"))){
					totalNum += takeNumMap.get(key).get("totalNum");
				}
				if (TicketStatus.consumed.getCode().equals(t.getStatus())) {
					consumedNum = t.getQuantity();
					if(EmptyChecker.isNotEmpty(takeNumMap.get(key)) && EmptyChecker.isNotEmpty(takeNumMap.get(key).get("consumedNum"))){
						consumedNum += takeNumMap.get(key).get("consumedNum");
					}
				}
				
				ticketMap.put("totalNum", totalNum);
				ticketMap.put("consumedNum", consumedNum);
				takeNumMap.put(key, ticketMap);
			}
		}
		
		if (EmptyChecker.isNotEmpty(takeNumMap)) {
			Iterator entries = takeNumMap.entrySet().iterator();
			while (entries.hasNext()) {  
			    Map.Entry entry = (Map.Entry) entries.next();  
			    String key = (String)entry.getKey();
			    Map<String,Integer> valueMap = (Map<String,Integer>)entry.getValue();
			    Integer consumedNum = valueMap.get("consumedNum");
			    Integer totalNum = valueMap.get("totalNum");
			    if (consumedNum == 0) {
					mapRes.put(key, "未提货");//1
				} else if (consumedNum == totalNum) {
					// 已消费
					mapRes.put(key, "已提货");//2
				} else if (consumedNum < totalNum) {
					// 已消费的全小于总券数 == 部分消费
					mapRes.put(key, "部分提货");//3
				}
			}  
		}
		
		return mapRes;
    }
    
    /**
     * 退款中和已退款数量
     * @param subOrderId
     * @param productId
     * @return
     */
    private Map<String,Object> getRefundNumOfProduct(String subOrderId,String productId){
    	 List<RefundHistory> refundHistoryList = refundSupport.findRefundListBySubOrderIdAndProductId(subOrderId,productId);
    	 Map<String,Object> numMap = new HashMap<String,Object>(); 
         Integer refundingNum = 0;
         Integer refundedNum = 0;
         if(EmptyChecker.isNotEmpty(refundHistoryList) && refundHistoryList.size()>0){
    	 		for(RefundHistory refundHis:refundHistoryList){
    	 		if(RefundState.REFUND_PROCESSING.getCode().equals(refundHis.getRefundState())
     				|| RefundState.REFUND_SUCCESS.getCode().equals(refundHis.getRefundState())){       	 		
            	 	if(RefundState.REFUND_PROCESSING.getCode().equals(refundHis.getRefundState())){
            	 		refundingNum+=1;//退款中个数
            	 	}
            	 	if(RefundState.REFUND_SUCCESS.getCode().equals(refundHis.getRefundState())){
            	 		refundedNum+=1;//已退款个数
            	 	}
    	 		}
    	 	}	
    	 }
         numMap.put("refundingNum", refundingNum);
         numMap.put("refundedNum", refundedNum);
         return numMap;
    }
    
    /**
     * 获取商品退款数量
     * @param subOrderId 子订单号
     * @param productId  商品编号
     * @return
     *<pre>
     *
     * @version V1.0 修改人：Arron 日期：2015年5月8日 上午11:50:08 
     *
     *</pre>
     */
    private int getRefund(String subOrderId,String orderId, String productId) {
        int number = 0;
        Map<String, Integer> map = refundLogic.getRefundedProduct(orderId);
        if(EmptyChecker.isNotEmpty(map)) {
            Integer i = map.get(subOrderId + "_" + productId);
            if(EmptyChecker.isNotEmpty(i)) {
                number = i;
            }
        }
        return number;
    }

	@Override
	public ResultBean queryGivePointsProductByBoss(OrderQueryByBossCondition condition, PageVo page) {
		ResultBean rb = new ResultBean(ResultCode.success,"查询成功");

		PageEntity<OrderQueryByBossCondition> pageEntity = new PageEntity<OrderQueryByBossCondition>();
        pageEntity.setCondition(condition);
        pageEntity.convert(page);
        pageEntity.setOrderByColumn("create_time"); // 设置排序字段
        pageEntity.setOrderByType(OrderByType.desc); // 排序方式

        MongoPage mongo = orderSupport.queryGivePointsProductByBoss(pageEntity);

        List<GivePointsOrderVo> resultList = new ArrayList<GivePointsOrderVo>();
        convertPage(mongo, page);

        if (EmptyChecker.isNotEmpty(mongo.getItems())) {
        	List<SubOrderMongo> list = (List<SubOrderMongo>) mongo.getItems();
        	for (SubOrderMongo subOrderMongo : list) {
            	GivePointsOrderVo vo = new GivePointsOrderVo();
            	vo.setClientId(subOrderMongo.getClientId());
            	vo.setOrderId(subOrderMongo.getOrderId());
            	vo.setSubOrderId(subOrderMongo.getSubOrderId());
            	vo.setRefundState(subOrderMongo.getRefundState());
            	vo.setCreateTime(DateUtil.formatDate2Str(new Date(subOrderMongo.getCreateTime()), DateUtil.C_TIME_PATTON_DEFAULT));
            	
            	//查询支付时间
            	OrderMongo order = orderSupport.getOrderById(subOrderMongo.getClientId(),subOrderMongo.getOrderId());
            	
            	vo.setPaymentMethod(order.getPaymentMethod());
            	vo.setOrderStatus(order.getOrderStatus());
            	double totalPrice = 0.00;
            	double totalGivePoints = 0.00;
            	List<GivePointsProductVo> productList= new ArrayList<GivePointsProductVo>();

            	for(ProductMongo product : subOrderMongo.getProducts()){
            		if(product.getPoints()>0){
            			GivePointsProductVo productVo = new GivePointsProductVo();
                		productVo.setProductId(product.getProductId());
                		productVo.setGiveState(product.getGivePointState());
                		String paymentTime = "";
                		if(EmptyChecker.isNotEmpty(order.getPaymentTime())){
                			paymentTime = DateUtil.formatDate2Str(new Date(order.getPaymentTime()), DateUtil.C_TIME_PATTON_DEFAULT);
                		}
                		productVo.setPaymentTime(paymentTime);
                		productVo.setProductName(product.getProductName());
                		productVo.setGivePoints(Arith.div(product.getPoints(),1000));
                		totalPrice += Arith.div(product.getMoney()*product.getQuantity()+product.getVipMoney()*product.getVipQuantity()+product.getDeliveryMoney(),1000);
                		totalGivePoints += Arith.div(product.getPoints(),1000);
                		productList.add(productVo);
            		}
            	}
            	vo.setProductList(productList);
            	vo.setTotalPrice(totalPrice);
            	vo.setTotalGivePoints(totalGivePoints);
                resultList.add(vo);
            }
        	
        	if(EmptyChecker.isNotEmpty(list)) {
                long firstRecordTime = list.get(0).getCreateTime();
                long lastRecordTime = list.get(resultList.size()-1).getCreateTime();
                page.setFirstRecordTime(firstRecordTime);
                page.setLastRecordTime(lastRecordTime);
            }
        	
        }else{
        	LogCvt.info("子订单查询结果为空");
        }
        
        QueryGivePointsProductByBossRes res = new QueryGivePointsProductByBossRes();
        res.setGivePointsOrderList(resultList);
        res.setPageVo(page);
        rb = new ResultBean(ResultCode.success, res);
        return rb;
	}

	@Override
	public ResultBean queryRecvInfoForProductByBoss(OrderQueryByBossCondition condition, PageVo pageVo) {
		ResultBean rb = new ResultBean(ResultCode.success,"查询成功");

		Page<PresellShip> page = (Page) BeanUtil.copyProperties(Page.class, pageVo);
        
        PresellShip ship = new PresellShip();
        if(EmptyChecker.isNotEmpty(condition.getClientId())){
        	ship.setClientId(condition.getClientId());
        }
        if(EmptyChecker.isNotEmpty(condition.getSellStartTime()) && condition.getSellStartTime() > 0){
        	ship.setPresellStartTime(new Date(condition.getSellStartTime()));
        	System.out.println("s1:"+DateUtil.formatDate2Str(new Date(condition.getSellStartTime()), DateUtil.C_TIME_PATTON_DEFAULT));
        }
        if(EmptyChecker.isNotEmpty(condition.getSellEndTime()) && condition.getSellEndTime() > 0){
        	ship.setPresellEndTime(new Date(condition.getSellEndTime()));
        	System.out.println("s2:"+DateUtil.formatDate2Str(new Date(condition.getSellEndTime()), DateUtil.C_TIME_PATTON_DEFAULT));
        }
        if(EmptyChecker.isNotEmpty(condition.getStartTime()) && condition.getStartTime() > 0){
        	ship.setStartTime(new Date(condition.getStartTime()));
        }
        if(EmptyChecker.isNotEmpty(condition.getEndTime()) && condition.getEndTime() > 0){
        	ship.setEndTime(new Date(condition.getEndTime()));
        }
        if(EmptyChecker.isNotEmpty(condition.getfOrgCode())){
        	ship.setfOrg(condition.getfOrgCode());
        }
        if(EmptyChecker.isNotEmpty(condition.getsOrgCode())){
        	ship.setsOrg(condition.getsOrgCode());
        }
        
        System.out.println("ship:"+JSON.toJSONString(ship, true));

        try {
        	
        	page = orderSupport.queryPresellShipByPage(page,ship);
        	
        	List<RecvInfoForProductVo> recvList = new ArrayList<RecvInfoForProductVo>();
        	if(EmptyChecker.isNotEmpty(page.getResultsContent())){
        		for(PresellShip temp : page.getResultsContent()){
        			RecvInfoForProductVo bean = new RecvInfoForProductVo();
        			bean.setOrderId(temp.getOrderId());
        			bean.setSubOrderId(temp.getSubOrderId());
        			bean.setFOrgName(temp.getfOrgName());
        			bean.setSOrgName(temp.getsOrgName());
        			bean.setProductName(temp.getProductName());
        			bean.setCreateTime(DateUtil.formatDate2Str(temp.getOrderTime(),DateUtil.C_TIME_PATTON_DEFAULT));
        			bean.setBuyCount(temp.getBuyNumber());
        			bean.setRefundCount(temp.getRefundNumber());
        			bean.setRealCount(temp.getBuyNumber() - temp.getRefundNumber());
        			bean.setReciver(temp.getReceiveName());
        			bean.setAddress(temp.getReceiveAddress());
        			bean.setPhone(temp.getPhone());
        			recvList.add(bean);
        		}
        	}
        	
        	QueryRecvInfoForProductByBossRes res = new QueryRecvInfoForProductByBossRes();
    		
    		pageVo = (PageVo)BeanUtil.copyProperties(PageVo.class, page);
    		res.setPageVo(pageVo);
    		res.setRecvList(recvList);
            
            rb = new ResultBean(ResultCode.success, res);
        } catch (Exception e) {
        	rb = new ResultBean(ResultCode.failed,"查询失败，原因："+e.getMessage());
        }

        return rb;
	}
	
	@Override
	public OrderExportData exportBoutiqueOrderByBankManage(List<QueryBoutiqueOrderByBankManageVo> list,QueryBoutiqueOrderByBankManageVoReq req)  throws FroadBusinessException {
		String url = null;
        OrderExportData orderExportData = new OrderExportData();
        String sheetName = null;
        String excelFileName = null;
        // 标题
        List<String> header = new ArrayList<String>();
        
        header = Arrays.asList(OrderConstants.BANK_PC_BOUTIQUE_ORDER_EXPORT_TITLES);
        sheetName = "精品商城订单";
        excelFileName = "精品商城订单列表查询";
        
        List<List<String>> data = new ArrayList<List<String>>();
        if(EmptyChecker.isNotEmpty(list)) {
        	int count = 0;
            for (QueryBoutiqueOrderByBankManageVo vo : list) {
                List<String> record = new ArrayList<String>();
                //订单编号
                String subOrderId = StringUtils.isEmpty(vo.getSubOrderId())?"--":vo.getSubOrderId();
                //支付方式
                String paymentMethod = StringUtils.isEmpty(vo.getPaymentMethod()) ? "未支付" : PaymentMethod.getByCode(vo.getPaymentMethod()).getDescribe();
                //订单状态
                String orderStatus = StringUtils.isEmpty(vo.getOrderStatus()) ? "--" : OrderConstants.ORDER_STATUS_MAP.get(vo.getOrderStatus());
                //下单时间
                String createTime = DateUtil.formatDate2Str(new Date(vo.getCreateTime()),DateUtil.C_TIME_PATTON_DEFAULT);
                //商户
                String merchantName = vo.getProviderName();
                
                if(EmptyChecker.isNotEmpty(vo.getProductList())) {
                	 //序号
                    String no = ""+(++count);
                    int proIndex = 0;
                    for(ProductVo product : vo.getProductList()){
                    	if(proIndex>0){
                            no = "";
                        }
                        proIndex++;
                        //商品
                        String productName = product.getProductName();
                        //销售数量
                        String sellCount = String.valueOf(product.getQuantity()+product.getVipQuantity());
                        double productMoney = Arith.add(Arith.mul(product.getQuantity(), product.getMoney()), Arith.mul(product.getVipQuantity(), product.getVipMoney()));
                        String totalProductMoney = HelpUtil.getDoubleDecimalString(Arith.div(productMoney,1000));
                        String[] dataValue = new String[]{no,subOrderId,paymentMethod,orderStatus,createTime,totalProductMoney,merchantName,productName,sellCount};
                        record =  Arrays.asList(dataValue);
                        data.add(record);
                    }
                }
            }
        }
        orderExportData.setHeader(header);
        orderExportData.setData(data);
        orderExportData.setSheetName(sheetName);
        orderExportData.setExcelFileName(excelFileName);
        return orderExportData;
	}

	
	
	@Override
	public OrderExportData exportOrderByBankManage(List<QueryOrderByBankManageVo> list,QueryOrderByBankManageVoReq req) throws FroadBusinessException {
		String url = null;
		OrderExportData orderExportData = new OrderExportData();
		String sheetName = null;
		String excelFileName = null;
		// 标题
		List<String> header = new ArrayList<String>();
		if (req.getType().equals(OrderType.group_merchant.getCode())) {// 团购
			header = Arrays.asList(OrderConstants.BANK_PC_GROUP_ORDER_EXPORT_TITLES);
			sheetName = "团购订单";
			excelFileName = "团购订单列表查询";
		} else if (req.getType().equals(OrderType.presell_org.getCode())) {// 预售
			sheetName = "预售订单";
			excelFileName = "预售订单列表查询";
			if (StringUtils.equals(req.getDeliveryOption(),DeliveryType.home.getCode())) {
				header = Arrays.asList(OrderConstants.BANK_PC_PRESELL_SHIP_ORDER_EXPORT_TITLES);
				excelFileName = "预售配送订单列表查询";
				sheetName = "预售配送订单";
			}
			if (StringUtils.equals(req.getDeliveryOption(),DeliveryType.take.getCode())) {
				header = Arrays.asList(OrderConstants.BANK_PC_PRESELL_DELIVERY_ORDER_EXPORT_TITLES);
				excelFileName = "预售自提订单列表查询";
				sheetName = "预售自提订单";
			}
		} else if (req.getType().equals(OrderType.special_merchant.getCode())) {// 名优特惠
			header = Arrays.asList(OrderConstants.BANK_PC_SPECIAL_ORDER_EXPORT_TITLES);
			sheetName = "名优特惠订单";
			excelFileName = "名优特惠订单列表查询";
		} else if (req.getType().equals(OrderType.online_points_org.getCode())) {// 线上
			header = Arrays.asList(OrderConstants.BANK_PC_POINT_ORDER_EXPORT_TITLES);
			sheetName = "线上积分兑换订单";
			excelFileName = "线上积分兑换订单列表查询";
		} else if (req.getType().equals(OrderType.offline_points_org.getCode())) {// 线下积分兑换
			header = Arrays.asList(OrderConstants.BANK_PC_POINT_ORDER_EXPORT_TITLES);
			sheetName = "线下积分兑换订单";
			excelFileName = "线下积分兑换订单列表查询";
		} else if (req.getType().equals(OrderType.face2face.getCode())) {// 面对面
			header = Arrays.asList(OrderConstants.BANK_PC_F2F_ORDER_EXPORT_TITLES);
			sheetName = "面对面订单";
			excelFileName = "面对面订单列表查询";
		}
		//数据
 		List<List<String>> data = new ArrayList<List<String>>();
		if(EmptyChecker.isNotEmpty(list)) {
         	
         	//面对面-取操作员
         	/*Map<Long,String> merchantUserNameMap = new HashMap<Long,String>();
        	if(req.getType().equals(OrderType.face2face.getCode())){//面对面
        		List<Long> merchantUserIdList = new ArrayList<Long>();
            	if(EmptyChecker.isNotEmpty(list)){
            		for(QueryOrderByBankManageVo vo : list){
            			merchantUserIdList.add(vo.getMerchantUserId());
            		}
            	}
        		merchantUserNameMap = getMerchantUserNameMap(merchantUserIdList);
        		if(EmptyChecker.isEmpty(merchantUserNameMap)){
            		LogCvt.error("[数据错误]-警告!!!面对面订单导出-商户用户名查询结果为空！");
            	}
			}*/
     		
     		int count = 0;
     		for (QueryOrderByBankManageVo vo : list) {
     			List<String> record = new ArrayList<String>();
					//订单编号
					String subOrderId = StringUtils.isEmpty(vo.getSubOrderId())?"--":vo.getSubOrderId();
					String orderId = StringUtils.isEmpty(vo.getOrderId())?"--":vo.getOrderId();
	                //支付方式
					String paymentMethod = StringUtils.isEmpty(vo.getPaymentMethod()) ? "未支付" : PaymentMethod.getByCode(vo.getPaymentMethod()).getDescribe();
	                //订单状态
					String orderStatus = StringUtils.isEmpty(vo.getOrderStatus()) ? "--" : OrderConstants.ORDER_STATUS_MAP.get(vo.getOrderStatus());
	                //下单时间
	                String createTime = DateUtil.formatDate2Str(new Date(vo.getCreateTime()),DateUtil.C_TIME_PATTON_DEFAULT);
	                //所属机构
	                String orgNames = vo.getOrgNames();
					//商户
	                String merchantName = vo.getMerchantName();
	         	    //发货|提货状态
					String deliveryState = vo.getDeliveryState() == null ? "--": ShippingStatus.getType(vo.getDeliveryState()).getDescribe();
					//收货人/提货人姓名
					String consignee = (vo.getDeliverInfoVo() == null || vo.getDeliverInfoVo().getConsignee() == null) ? "--" : vo.getDeliverInfoVo().getConsignee();
					//地址
					String address = (vo.getDeliverInfoVo() == null || vo.getDeliverInfoVo().getAddress() == null) ? "--" : vo.getDeliverInfoVo().getAddress();
					//手机
					String phone = (vo.getDeliverInfoVo() == null || vo.getDeliverInfoVo().getPhone() == null) ? "--" : vo.getDeliverInfoVo().getPhone();
					//面对面-操作员
					String merchantUserName = EmptyChecker.isNotEmpty(vo.getMerchantUserName()) ? vo.getMerchantUserName():"--";
				
	     			if(req.getType().equals(OrderType.face2face.getCode())){//面对面
	     				//结算状态
	             	    String settlementStatus = vo.getSettlementStatus();
	             	    //序号
						String no = ""+(++count);
						//订单金额
		                String subTotalMoney = HelpUtil.getDoubleDecimalString(vo.getSubTotalMoney());
		                
						String[] dataValue = new String[]{no,orderId,paymentMethod,createTime,orgNames,merchantName,orderStatus,subTotalMoney,settlementStatus,phone,merchantUserName};
	     				record =  Arrays.asList(dataValue);
	             	    data.add(record);
	     			}else{
	     				if(EmptyChecker.isNotEmpty(vo.getProductList())){
	     					//序号
	     					String no = ""+(++count);
	     					int proIndex = 0;
	         				for(ProductVo product : vo.getProductList()){
	         					if(proIndex>0){
	         						no = "";
	         					}
	         					proIndex++;
	         					//序号
	        					/*String no = ""+(++count);*/
	         					//商品
	         	                String productName = product.getProductName();
	     						//销售数量
	         	                String sellCount = String.valueOf(product.getQuantity()+product.getVipQuantity());
	         	                //结算状态
	    	             	    String settlementStatus = product.getSettlementStatus();
	    	             	    //提货状态
	    	             	    String deliveryStatus = product.getDeliveryState();
	    	             	    double productMoney = Arith.add(Arith.mul(product.getQuantity(), product.getMoney()), Arith.mul(product.getVipQuantity(), product.getVipMoney()));
	    	             	    String totalProductMoney = HelpUtil.getDoubleDecimalString(Arith.div(productMoney,1000));
	    	             	    
	    	             	    String[] dataValue = null;
								if(req.getType().equals(OrderType.group_merchant.getCode())){//团购
									dataValue = new String[]{no,orderId,paymentMethod,orderStatus,createTime,totalProductMoney,orgNames,merchantName,productName,sellCount};
			         			}else if(req.getType().equals(OrderType.presell_org.getCode())){//预售
			         				if(StringUtils.equals(req.getDeliveryOption(),DeliveryType.home.getCode())){
			         					dataValue = new String[]{no,orderId,paymentMethod,orderStatus,createTime,"配送",orgNames,productName,sellCount,totalProductMoney,consignee,phone};
			         				}
			         				if(StringUtils.equals(req.getDeliveryOption(),DeliveryType.take.getCode())){
			         					orgNames = product.getOrgNames();
			         					dataValue = new String[]{no,orderId,paymentMethod,orderStatus,createTime,"自提",orgNames,productName,sellCount,totalProductMoney,consignee,phone,deliveryStatus};
			         				}
			         			}else if(req.getType().equals(OrderType.special_merchant.getCode())){//名优特惠
			         				dataValue = new String[]{no,orderId,paymentMethod,orderStatus,createTime,totalProductMoney,merchantName,productName,sellCount,deliveryState,consignee,address,phone};
			         			}else if(req.getType().equals(OrderType.online_points_org.getCode())){//线上积分兑换
			         				merchantUserName = "--";
			         				dataValue = new String[]{no,orderId,subOrderId,paymentMethod,orderStatus,createTime,productName,sellCount,totalProductMoney,orgNames,merchantUserName};
			         			}else if(req.getType().equals(OrderType.offline_points_org.getCode())){//线下积分兑换
			         				orgNames = product.getOrgNames();
			         				dataValue = new String[]{no,orderId,subOrderId,paymentMethod,orderStatus,createTime,productName,sellCount,totalProductMoney,orgNames,merchantUserName};
			         			}
								
								record =  Arrays.asList(dataValue);
			             	    data.add(record);
	         				}
	         			}
     			}
     		}
         }
		orderExportData.setHeader(header);
 		orderExportData.setData(data);
 		orderExportData.setSheetName(sheetName);
 		orderExportData.setExcelFileName(excelFileName);
		return orderExportData;
	}

	@Override
	public OrderExportData exportOrderByMerchantManage(List<QueryOrderByMerchantManageVo> list, QueryOrderByMerchantManageVoReq req) throws FroadBusinessException {
		String url = null;
		OrderExportData orderExportData = new OrderExportData();
		
		//文件名
    	String sheetName = "";
    	String excelName = "";
    	
    	//标题
    	List<String> header = new ArrayList<String>();
    	if(req.getType().equals(OrderType.group_merchant.getCode())){//团购
    		header = Arrays.asList(OrderConstants.MERCHANT_PC_GROUP_ORDER_EXPORT_TITLES);
    		sheetName = "团购订单";
    		excelName = "团购订单列表查询";
		}else if(req.getType().equals(OrderType.special_merchant.getCode())){//名优特惠
			header = Arrays.asList(OrderConstants.MERCHANT_PC_SPECIAL_ORDER_EXPORT_TITLES);
			sheetName = "名优特惠订单";
    		excelName = "名优特惠订单列表查询";
		}else if(req.getType().equals(OrderType.face2face.getCode())){//面对面
			header = Arrays.asList(OrderConstants.MERCHANT_PC_F2F_ORDER_EXPORT_TITLES);
			sheetName = "面对面订单";
    		excelName = "面对面订单列表查询";
		}
		
    	//数据
		List<List<String>> data = new ArrayList<List<String>>();
		
		if(EmptyChecker.isNotEmpty(list)) {
        	Map<Long,String> merchantUserNameMap = new HashMap<Long,String>();
        	if(req.getType().equals(OrderType.face2face.getCode())){//面对面
        		List<Long> merchantUserIdList = new ArrayList<Long>();
            	if(EmptyChecker.isNotEmpty(list)){
            		for(QueryOrderByMerchantManageVo vo : list){
            			merchantUserIdList.add(vo.getMerchantUserId());
            		}
            	}
        		merchantUserNameMap = getMerchantUserNameMap(merchantUserIdList);
        		if(EmptyChecker.isEmpty(merchantUserNameMap)){
            		LogCvt.error("[数据错误]-警告!!!面对面订单导出-商户用户名查询结果为空！");
            	}
			}
        	
    		int count = 0;
    		for (QueryOrderByMerchantManageVo vo : list) {
    			 //获取区域地址
				String address = "--";
            	if(EmptyChecker.isNotEmpty(vo.getAddress())){
            		address = vo.getAddress();
            	}
            	String person = StringUtils.isEmpty(vo.getConsignee()) ? "--" : vo.getConsignee();
				String phone = StringUtils.isEmpty(vo.getPhone()) ? "--" : vo.getPhone();
				String consignee = person + ":" + phone;
    			
    			//获取商品名称*件数
                String productName = "--";
				if (EmptyChecker.isNotEmpty(vo.getProductInfoVo())) {
					StringBuffer sb = new StringBuffer();
					for (ProductInfoVo pvo : vo.getProductInfoVo()) {
						sb.append(pvo.getProductName()).append(pvo.getQuantity() > 1 ? "x" + pvo.getQuantity() : "").append("、");
					}
					productName = sb.substring(0, sb.length() - 1);
				}
				
			 	//结算状态
				String settlementStatus = "--";
				String userName = "--";
                if(StringUtils.equals(req.getType(), OrderType.face2face.getCode())){
             	    //根据用户id查询操作人
             	    if(vo.getMerchantUserId()>0){
             	    	userName = merchantUserNameMap.get(vo.getMerchantUserId());
             	    }
             	    //结算状态
             	    if(EmptyChecker.isNotEmpty(vo.getSettlementStatus()) && EmptyChecker.isNotEmpty(OrderConstants.SETTLEMENT_STATUS_MAP.get(vo.getSettlementStatus()))){
                		settlementStatus = OrderConstants.SETTLEMENT_STATUS_MAP.get(vo.getSettlementStatus());
                	}
                }else{
                    //结算状态件数统计
                	settlementStatus = vo.getSettlementNumber()>0?
                    		vo.getQuantity()==vo.getSettlementNumber()?
                    				"已结算"+vo.getSettlementNumber()+"件":
                    				   "已结算"+vo.getSettlementNumber()+"件"+"/未结算"+(vo.getQuantity()-vo.getSettlementNumber()+"件")
                    				:"未结算"+vo.getQuantity()+"件";
                }
				
    			List<String> record = new ArrayList<String>();
    			record.add(""+(++count));
    			record.add(StringUtils.isEmpty(vo.getOrderId())?"--":vo.getOrderId());
                record.add(DateUtil.formatDate2Str(new Date(vo.getCreateTime()),DateUtil.C_TIME_PATTON_DEFAULT));
                record.add(String.valueOf(vo.getSubTotalMoney()));
				record.add(productName);
				record.add(StringUtils.isEmpty(vo.getType()) ? "--" : OrderConstants.BUSINESS_TYPE_MAP.get(vo.getType()));
				record.add(StringUtils.isEmpty(vo.getOrderStatus()) ? "--" : OrderConstants.ORDER_STATUS_MAP.get(vo.getOrderStatus()));
				record.add(settlementStatus);
				if(req.getType().equals("1")){//团购
					record.add(consignee);//提货人信息
    			}else if(req.getType().equals("3")){//名优特惠
    				record.add(consignee);//收货人信息
    				record.add(address);//收货地址
    			}else if(req.getType().equals("0")){//面对面
    				record.add(userName);//操作员
    			}
				data.add(record);
    		}
        }
		orderExportData.setHeader(header);
 		orderExportData.setData(data);
 		orderExportData.setSheetName(sheetName);
 		orderExportData.setExcelFileName(excelName);
		return orderExportData;
	}

}
