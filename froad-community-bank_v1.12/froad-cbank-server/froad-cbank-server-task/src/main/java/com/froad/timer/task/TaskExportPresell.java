package com.froad.timer.task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.thrift.TException;

import com.froad.db.mongo.MerchantOutletService;
import com.froad.db.mongo.OrderMongoService;
import com.froad.db.mongo.RefundMongoService;
import com.froad.db.mongo.SubOrderMongoService;
import com.froad.db.mongo.impl.MerchantOutletServiceImpl;
import com.froad.db.mongo.impl.OrderMongoServiceImpl;
import com.froad.db.mongo.impl.RefundsMongoServiceImpl;
import com.froad.db.mongo.impl.SubOrderMongoServiceImpl;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.AreaMapper;
import com.froad.db.mysql.mapper.PresellShipMapper;
import com.froad.db.mysql.mapper.ProductMapper;
import com.froad.enums.DeliveryType;
import com.froad.enums.OrderState;
import com.froad.enums.OrderStatus;
import com.froad.enums.OrderType;
import com.froad.enums.ProductType;
import com.froad.enums.RefundState;
import com.froad.logback.LogCvt;
import com.froad.logic.CommonLogic;
import com.froad.logic.impl.CommonLogicImpl;
import com.froad.po.Area;
import com.froad.po.Org;
import com.froad.po.PresellProduct;
import com.froad.po.PresellShip;
import com.froad.po.mongo.MerchantOutletFavorite;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.mongo.ProductMongo;
import com.froad.po.mongo.RecvInfo;
import com.froad.po.mongo.RefundHistory;
import com.froad.po.mongo.RefundShoppingInfo;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.util.AllkindsTimeConfig;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

/**
 * 
  * @ClassName: TaskExportPresell
  * @Description: TODO
  * @author Yaren Liang 2015年6月15日
  * @modify Yaren Liang 2015年6月15日
 */
public class TaskExportPresell  implements Runnable  {
	private static final String PRODUCT="products";
	private static final String PRODUCTID="product_id";
	private static final String CLIENTID="client_id";
	private static final String ORDERSTATUS="order_status";
	private static final String REFUNDSTATE="refund_state";
	private static final String TYPE="type";
	private static final String ID="_id";
	private static final String MEMBERCODE="member_code";
	private static final String RECVINFO="recv_info.recv_id";
	private static final String ORDERID="order_id";
	private static final String DELIVERYOPTION="delivery_option";
//	private Map<String,Object> param=new HashMap<String,Object>();
	private String startTime;
	private String endTime; 
	private SubOrderMongoService subOrderMongoService=new SubOrderMongoServiceImpl();
	private OrderMongoService orderMongoService=new OrderMongoServiceImpl();
	private MerchantOutletService merchantOutletService=new MerchantOutletServiceImpl();
	private RefundMongoService refundMongoService=new RefundsMongoServiceImpl();
	private ProductMapper productMapper;
	private AreaMapper areaMapper;
	@Override
	public void run() {
		LogCvt.debug("定时任务: 导出预售配送商品------开始------");
		SqlSession sqlSession = null;
		
		try{
			/******************开启数据源********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
		    productMapper = sqlSession.getMapper(ProductMapper.class);
		    areaMapper = sqlSession.getMapper(AreaMapper.class);
		    PresellShipMapper presellShipMapper=sqlSession.getMapper(PresellShipMapper.class);
			/******************开启数据源********************/
			
//			/*****************读取配置**************/
//			AllkindsTimeConfig.load();
//			/*****************读取配置*************/
			
			/********设置时间************/
			setTime();
			/********设置时间************/
			List<PresellProduct> products=findAllPresll();
			if(products==null||products.size()==0){
				 LogCvt.debug("定时任务: 导出预售配送商品     昨日预售配送商品数量为 0");
				 return;
			 }
			LogCvt.debug("定时任务: 导出预售配送商品       昨日预售配送商品数量为:------"+products.size()+"------");
			for(PresellProduct product:products){
			
				/**
				 * cb_suborder_product中取订单号、子订单号、下单时间    
				 */
				 List<SubOrderMongo> subOrderMongos=findSubOrder(product);
				 
				 if(subOrderMongos==null||subOrderMongos.size()==0){
					 LogCvt.debug("定时任务: 导出预售配送商品     "+product.getProductId()+":预售配送商品子订单数量为 0");
					 continue;
				 }
				 LogCvt.debug("定时任务: 导出预售配送商品     "+product.getProductId()+":预售配送商品子订单数量为:------"+subOrderMongos.size()+"------");
				 for(SubOrderMongo subOrderMongo:subOrderMongos){
					 
					    PresellShip presellShip=new PresellShip();
						/****************商品基本信息赋值******************/
						setProductInfo(presellShip,product);
						/****************订单基本信息赋值******************/
						setSubOrderInfo(subOrderMongo,presellShip);
						/****************退款基本信息赋值******************/
						setTotalRefund(subOrderMongo,presellShip);
						
						// 避免重复进行添加
						int count=presellShipMapper.findPresellOfCountBySubOrderId(presellShip.getSubOrderId());
						if( count == 0 ){
							presellShipMapper.addPresellShip(presellShip);
							sqlSession.commit(true);
						}
						
				 }
				
				
				
			}
			
			
			
		}catch(Exception e){
			LogCvt.error("定时任务: 导出预售配送商品------系统异常------"+e.getMessage(), e);
		}finally{
			LogCvt.debug("定时任务: 导出预售配送商品------结束------");
			if(null != sqlSession)  
				sqlSession.close();  
		}
		
		
		
		
		
	}
public void  setProductInfo(PresellShip presellShip,PresellProduct product) throws TException{
	presellShip.setCreateTime(new Date());
	presellShip.setClientId(product.getClientId());// 客户端id
	presellShip.setfOrg(product.getProOrgCode());//一级机构
	presellShip.setsOrg(product.getCityOrgCode());//二级机构
	presellShip.setProductId(product.getProductId());//商品ID
	presellShip.setProductName(product.getName());//商品名称
	presellShip.setPresellStartTime(product.getStartTime());//预售开始时间
	presellShip.setPresellEndTime(product.getEndTime());//预售结束时间
	presellShip.setStartTime(product.getDeliveryStartTime());//发货开始时间
	presellShip.setEndTime(product.getDeliveryEndTime());//发货结束时间
	/*******************调用org接口获取机构名称*******************************/
	CommonLogic logic=new CommonLogicImpl();
	if(presellShip.getfOrg()!=null&&!"".equals(presellShip.getfOrg())){
		Org org=logic.getOrgByOrgCode(presellShip.getfOrg(), product.getClientId());
		presellShip.setfOrgName(org.getOrgName());
	    }else{
	    	presellShip.setfOrgName("");
	    }
	if(presellShip.getsOrg()!=null&&!"".equals(presellShip.getsOrg())){
		Org org=logic.getOrgByOrgCode(presellShip.getsOrg(), product.getClientId());
		presellShip.setsOrgName(org.getOrgName());
	    }else{
	    	presellShip.setsOrgName("");
	    }
	/*******************调用org接口获取机构名称*******************************/
	}
public List<SubOrderMongo> findSubOrder(PresellProduct product){
	DBObject setObject = new BasicDBObject();
	DBObject elemObj =new BasicDBObject();
	elemObj.put(PRODUCTID, product.getProductId());
	elemObj.put(TYPE, ProductType.presell.getCode());//预售
	elemObj.put(DELIVERYOPTION, DeliveryType.home.getCode());//送货上门
	DBObject elemMatchObj  =new BasicDBObject();
	elemMatchObj.put(QueryOperators.ELEM_MATCH, elemObj);
	setObject.put(ORDERSTATUS, OrderStatus.paysuccess.getCode());//支付成功
	setObject.put(TYPE, OrderType.presell_org.getCode());
	setObject.put(PRODUCT, elemMatchObj);;
	List<SubOrderMongo> subOrderMongos=subOrderMongoService.findByCondition(setObject);
	if(subOrderMongos!=null&&subOrderMongos.size()>0){
		return subOrderMongos;
	}
	return new ArrayList<SubOrderMongo>();
	
}
//设置前一天的时间
public void setTime() throws Exception{
	Date date=new Date();
	Date dateBefore=new Date();
	SimpleDateFormat simple=new SimpleDateFormat("yyyy-MM-dd");
	Calendar calendar=Calendar.getInstance();
	calendar.setTime(date);//把当前时间赋给日历
	calendar.add(Calendar.DAY_OF_MONTH, -AllkindsTimeConfig.getExportAnDayBefore());  //设置为前一天
	dateBefore=calendar.getTime();
	StringBuffer sb=new StringBuffer();
	StringBuffer sb1=new StringBuffer();
	sb.append(simple.format(dateBefore)).append(" 00:00:00");
	sb1.append(simple.format(dateBefore)).append(" 23:59:59");
	startTime = sb.toString();
	endTime = sb1.toString();
	LogCvt.debug("定时任务: 设置开始时间为: "+sb.toString()+"设置结束时间为:"+sb1.toString());
}
 public void setSubOrderInfo(SubOrderMongo subOrderMongo,PresellShip presellShip){
	 Date date=new Date(subOrderMongo.getCreateTime());
	// SimpleDateFormat simple=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 int num=0;
	 presellShip.setOrderId(subOrderMongo.getOrderId());
	 presellShip.setSubOrderId(subOrderMongo.getSubOrderId());
	 presellShip.setOrderTime(date);
	 List<ProductMongo> productMongos=subOrderMongo.getProducts();
	 //子订单里面有products里面含多个商品,要和此商品ID相同的以及配送是0的才符合
	 for(ProductMongo productMongo:productMongos ){
		 if(productMongo.getProductId().equals(presellShip.getProductId())){
			 if(productMongo.getDeliveryOption().equals("0")){
				 num+= productMongo.getQuantity()+productMongo.getVipQuantity();
				 presellShip.setBuyNumber(num);
			 }
		 }
	 }
	 /********************查询order*********************************/
	 DBObject setObject = new BasicDBObject();
	 setObject.put(ID, subOrderMongo.getOrderId());
	 setObject.put(MEMBERCODE, subOrderMongo.getMemberCode());
	 OrderMongo orderMongo =orderMongoService.findOrderMongo(setObject);
//	 LogCvt.debug("定时任务: 查找的order_id为: "+subOrderMongo.getOrderId()+" , recv_id为"+orderMongo.getRecvId());
	 /**
	  * 通过查询的recv_id关联cb_merchant_outlet_favorite
	  */
	 DBObject setObject1 = new BasicDBObject();
	 setObject1.put(RECVINFO,orderMongo.getRecvId());
	 MerchantOutletFavorite merchantOutletFavorite = merchantOutletService.findByCondition(setObject1);
	List<RecvInfo>  recvInfos=merchantOutletFavorite.getRecvInfo();
	for(RecvInfo recvInfo:recvInfos){
		if(recvInfo.getRecvId().equals(orderMongo.getRecvId())){
			
			if( recvInfo.getAreaId() != null ){
				Area area = areaMapper.findAreaById(recvInfo.getAreaId());
				if( area != null ){
					String treePathName = area.getTreePathName();
					presellShip.setReceiveAddress(treePathName+recvInfo.getAddress());
				}else{
					presellShip.setReceiveAddress(recvInfo.getAddress());
				}
			}else{
				presellShip.setReceiveAddress(recvInfo.getAddress());
			}
			presellShip.setReceiveName(recvInfo.getConsignee());
			presellShip.setPhone(recvInfo.getPhone());
		}
	}
	 
 }
 public void setTotalRefund (SubOrderMongo subOrderMongo,PresellShip presellShip){
//	 RefundService.Iface refundClient = 
//				(RefundService.Iface)ThriftClientProxyFactory.createIface(RefundService.class.getName(), RefundService.class.getSimpleName(), ThriftConfig.ORDER_SERVICE_HOST, ThriftConfig.ORDER_SERVICE_PORT);
//	 RefundListRequestVo refundListRequestVo=new RefundListRequestVo();
//	 refundListRequestVo.setClientId(CLIENTID);
//	 refundListRequestVo.setOrderId(subOrderMongo.getOrderId());
	 try {
		 int num=0;//退款数量
//		RefundListResponseVo refundResponseVo=refundClient.getRefundList(refundListRequestVo);
//		List<RefundInfoVo> refundInfoVos=refundResponseVo.getRefundInfoList();
		 DBObject setObject = new BasicDBObject();
		 DBObject setObject1=new BasicDBObject();
		 setObject1.put(QueryOperators.NE, RefundState.REFUND_FAILED.getCode());
		 setObject.put(CLIENTID, "anhui");
		 setObject.put(ORDERID,subOrderMongo.getOrderId());
		 setObject.put(REFUNDSTATE, setObject1);
		List<RefundHistory> refundHistorys=refundMongoService.findByCondition(setObject);
		for(RefundHistory refundHistory:refundHistorys){
			List<RefundShoppingInfo> refundShoppingInfos= refundHistory.getShoppingInfo();
			for(RefundShoppingInfo refundShoppingInfo:refundShoppingInfos ){
				if(refundShoppingInfo.getSubOrderId().equals(subOrderMongo.getSubOrderId())){
					num+=1;
				}
			}
			
		}
		presellShip.setRefundNumber(num);
	
	} catch (Exception e) {
		e.printStackTrace();
	}
 }
 public List<PresellProduct> findAllPresll(){
	 //先查询表中是否有数据，如果有就跑前一天的，如果没有数据则跑一次历史数据
	 int count=productMapper.findAllPresellByCount();
	 List<PresellProduct> products=null;
		if(count==0){
			products=productMapper.findAllPresellByHis();
		}else{
			products=productMapper.findAllPresell(startTime, endTime);
		}
	 return products;
 }
}

