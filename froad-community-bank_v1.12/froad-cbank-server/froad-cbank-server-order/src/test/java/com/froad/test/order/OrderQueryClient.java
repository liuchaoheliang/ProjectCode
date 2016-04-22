package com.froad.test.order;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import com.froad.enums.ActivitiesType;
import com.froad.enums.OrderStatus;
import com.froad.enums.OrderType;
import com.froad.enums.ProductType;
import com.froad.thrift.service.OrderQueryService;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.order.GetOrderDetailByBankManageVoReq;
import com.froad.thrift.vo.order.GetOrderDetailByBankManageVoRes;
import com.froad.thrift.vo.order.QueryOrderByBankManageVoReq;
import com.froad.thrift.vo.order.QueryOrderByBankManageVoRes;
import com.froad.thrift.vo.order.QueryOrderByMerchantPhoneVoReq;
import com.froad.thrift.vo.order.QueryOrderByMerchantPhoneVoRes;
import com.froad.util.JSonUtil;

/**
 * 类描述：相关业务类
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: f-road.com.cn
 * @time: 2015-8-31下午7:59:58 
 */
public class OrderQueryClient {
	public static final String SERVER_IP = "localhost";
    public static final int SERVER_PORT = 8090;
    public static final int TIMEOUT = 30000;
    public Data data = new Data();
    /**测试数据**/
    public class Data{
    	String clientId = "anhui";
    	
    	String clientId_ah = "1001";
    	
    	//商品
    	int isLimit = 1;
    	int isNotLimit = 0;
    	//商品1-预售自提
    	String productId1 = "01E0BFD08000";
    	/*String productName1 = "蕾丝仙女中裙子";
    	String productType1 = ProductType.presell.getCode();*/
    	String price1 = "59.9";
    	int quantity1 = 1;//买1
    	String store1 = "10";
    	
    	//商品2-预售送货上门
    	String productId2 = "0091716b8000";
    	String productName2 = "卤肉饭";
    	String productType2 = ProductType.presell.getCode();
    	int quantity2 = 3;//买2
    	int price2 = 10;
    	int store2 = 5;
    	
    	//预售商品
    	String productId21 = "0122BEC58000";
    	String productName21 = "Cetaphil洁面乳";
    	String productType21 = ProductType.presell.getCode();
    	int quantity21 = 1;//买2
    	int price21 = 5990;
    	int store21 = 5;
    	
    	//预售商品
    	String productId22 = "007dd1fe8000";
    	String productName22 = "麦克赛尔SDXC";
    	String productType22 = ProductType.presell.getCode();
    	int quantity22 = 1;//买2
    	int price22 = 5990;
    	int store22 = 5;
    	
    	String merchantId22 = "01A240508000";
    	
    	//商品3-面对面商品
    	String productId3 = "00A80E718000";
    	int cost = 10999;
    	String qrcode = "1000A80E718001";
    	
    	//商品4-名优特惠
    	String productId4 = "0093991f0000";
    	String productName4 = "金满堂黑糖四物饮";
    	String productType4 = ProductType.special.getCode();
    	int quantity4 = 3;//买2
    	int price4 = 10;
    	int store4 = 5;
    	
    	//名优特惠
    	//商户
    	String sMerchantId1 = "0164FC090000";
    	//商品
    	String sProductId1 = "01767C390000";
    	String sProductId2 = "0178C8690000";
    	
    	//团购
    	//商品
    	String gProductId1 = "01679CA68000";
    	String gProductId2 = "016852B68000";
    	String gProductId3 = "01781B090000";
    	
    	//商品5-团购商品
    	String productId5 = "011252050000";
    	String productName5 = "商品简称ddddddd";
    	String productType5 = ProductType.group.getCode();
    	int quantity5 = 1;//买2
    	int price5 = 888000;
    	int store5 = 5;
    	
    	/*----------------------------------------------*/
    	//商品5（1）-团购商品
    	String productId51 = "01126E450000";
    	String productName51 = "ddddds";
    	String productType51 = ProductType.group.getCode();
    	int quantity51 = 1;//买2
    	int price51 = 10;
    	int store51 = 5;
    	
    	
    	//商户
    	String merchantId51 = "0039f38a8000";
    	String merchantName51 = "阿里巴巴小微贷";
    	/*-----------------------------------------------*/
    	
    	//商品6-网点礼品
    	String productId6 = "01673C760000";
    	String productName6 = "网店兑换1";
    	String productType6 = ProductType.dotGift.getCode();
    	int quantity6 = 3;//买2
    	int price6 = 10;
    	int store6 = 5;
    	
    	//限购
    	int maxBuy = 3;
    	
    	//门店最大提货数量
    	String presellMax = "15";
    	
    	//活动1
    	Long activityId1 = 10000L;
    	String activityType1 = ActivitiesType.vip.getCode();
    	String vipPrice = "4";
    	//活动2
    	Long activityId2 = 20000L;
    	String activityType2 = ActivitiesType.point.getCode();
    	
    	//门店
    	String outletId1 = "583584220961263616";
    	String outletName1 = "阿牛嫂梅花路店";
    	
    	//门店
    	String outletId2 = "583584220961263610";
    	String outletName2 = "阿牛嫂樱花路店";
    	
    	//银行二级机构商户
    	String merchantId1 = "0026cbd80000";
    	String merchantName1 = "安徽肥东农村合作银行";
    	
    	//银行三级机构商户
    	String merchantId2 = "0027d7380000";
    	String merchantName2 = "肥西农村商业银行肥光支行";
    	
    	//名优特惠商户
    	String merchantId3 = "00932ef20000";
    	String merchantName3 = "纽伊斯特责任无限公司";
    	
    	//团购商户
    	String merchantId4 = "0039f38a8000";
    	String merchantName4 = "阿里巴巴小微贷";
    	
    	//团购商户
    	String merchantId41 = "00932ef20000";
    	String merchantName41 = "纽伊斯特责任无限公司";
    	
    	
    	
    	//网点礼品
    	String merchantId5 = "01CEA1C80000";
//    	String merchantName5 = "合肥科技银行新站支行";
    	
        //提货网点机构
//    	String orgCode = "3401030221";
//    	String orgName = "肥西农村商业银行肥光支行";
//    	String orgCode = "3401010003";
    	
    	String orgName = "芜湖津盛农商银行花桥支行";
//    	String cardNo = "6229538106502396018";//安徽银行卡
    	String cardNo = "6229538106500532358";//安徽银行卡
    	
    	//会员
//    	Long memberCode = 10000012041L;
    	Long memberCode = 50000000225L;
    	String memberName = "T哪有女流氓";
    	String phone = "13024323415";
    	String deliverId = "0156A7630000";//自提
    	String recvId = "01CEFE188000";//收货
    	String merchantUserId = "10001";
    	
    	//订单
    	String orderId = "74493165568";
    	String subOrderId = "74493100032";
    	
    	//配送
    	String deliveryCorpId = "100000";
    	String deliveryCorpName = "顺风";
    	String trackingNo = "1234567";
    	String deliveryOption ="";
    	String merchantName="";
    	String pointOrderType="";
//    	long startTime = 1433088000000l;//2015-06-01 00:00:00
    	long startTime = 1412092800000l;//2014-10-01 00:00:00     
    	long endTime = 1541123199000l;//1441123199000l:2015-09-01 23:59:59
    	//2015-06-16 00:00:00 startTime=1434384000000                  2015-09-14 23:59:59  endTime:1442246399000 
    	String orgCode = "340000";//341465
    	String myOrgCode = "340415";
    }
    /**
     * 订单查询列表
     * page:PageVo(pageNumber:1, pageSize:10, totalCount:0, pageCount:0,  hasNext:false), subOrderId:null, deliveryOption:null, myOrgCode:null, orgCodes:null
     */
    public void testQueryOrderByBankManage(OrderQueryService.Client client) throws TException{
    	QueryOrderByBankManageVoReq req = new QueryOrderByBankManageVoReq();
    	QueryOrderByBankManageVoRes res = new QueryOrderByBankManageVoRes();
    	List<String> orderStatus = new ArrayList<String>();
    	PageVo page= new PageVo();
    	//page.setBegDate(1412092800000l);
    	//page.setEndDate(1441123199000l);
    	page.setFirstRecordTime(1445937719477L);//:1441525066871  :1430379859000
    	//page.setFirstRecordTime(1441525066871l);
    	page.setLastPageNumber(1);
    	//page.setLastPageNumber(1);
    	page.setLastRecordTime(1445846907703L);//:1430379912000   :1430373448000
    	//page.setLastRecordTime(1430379912000l);
  //  	page.setPageCount(50);
    	page.setPageNumber(1);
    	page.setPageSize(10);
    //	page.setTotalCount(totalCount);
    	req.setPage(page);
    	req.setClientId("chongqing");
    	req.setOrgCode("020000,100000");
//    	req.setSubOrderId(data.subOrderId);
    	req.setStartTime(1438704000000l);
    	req.setEndTime(1446566399999l);
//    	req.setMyOrgCode(data.myOrgCode);
//    	req.setDeliveryOption(data.deliveryOption);
//    	req.setMerchantName(data.merchantName);
    	orderStatus.add(OrderStatus.create.getCode());//订单创建
    	orderStatus.add(OrderStatus.closed.getCode());//订单用户关闭
    	orderStatus.add(OrderStatus.sysclosed.getCode());//订单系统关闭
    	orderStatus.add(OrderStatus.paying.getCode());//订单支付中
    	orderStatus.add(OrderStatus.payfailed.getCode());//订单支付失败
    	orderStatus.add(OrderStatus.paysuccess.getCode());//订单支付成功
    	req.setOrderStatus(null);
    	req.setMerchantName(null);
    	
//    	req.setOrderId("0D57D8F30000");
//    	req.setSubOrderId("0D57D8F30001");
    	//团购
    	//req.setType(OrderType.group_merchant.getCode());
    	//预售
    	req.setType(OrderType.face2face.getCode());
    	req.setDeliveryOption("0");//0:配送 1:自提
    	//req.setDeliveryOption("1");//0:配送 1:自提
    	//名优特惠
    	//req.setType(OrderType.special_merchant.getCode());
    	
    	//积分兑换
    	//req.setType(OrderType.offline_points_org.getCode());
    	//req.setType(OrderType.online_points_org.getCode());
    	
    	//面对面
    	//req.setType(OrderType.face2face.getCode());
    	System.out.println("查询订单列表，参数数据报文: " + JSonUtil.toJSonString(req));
    	res = client.queryOrderByBankManage(req);
    	System.out.println("查询订单列表，返回数据报文: " + JSonUtil.toJSonString(res));
    }
    
    /**
     * 订单查询详情
     */
    public void testGetOrderDetailByBankManage(OrderQueryService.Client client) throws TException{
    	GetOrderDetailByBankManageVoReq req =new GetOrderDetailByBankManageVoReq();
    	GetOrderDetailByBankManageVoRes res =new GetOrderDetailByBankManageVoRes();
    	req.setClientId(data.clientId);
    	req.setSubOrderId("0D70C1E30000");//自提：0CED78800000  配送：06D644F08000
    	//req.setOrderId("0D5D29548000");//自提：0CED78800000  配送：06D644F08000
    	//团购
    	//req.setType(OrderType.group_merchant.getCode());
    	//预售
    	req.setType(OrderType.presell_org.getCode());
    	//req.setDeliveryOption("0");//0:配送 1:自提
    	//req.setDeliveryOption("1");//0:配送 1:自提
    	//名优特惠
    	//req.setType(OrderType.special_merchant.getCode());
    	//积分兑换
    	//req.setType(OrderType.offline_points_org.getCode());
    	//req.setType(OrderType.online_points_org.getCode());
    	//面对面
    	//req.setType(OrderType.face2face.getCode());
    	System.out.println("查询订单详情，参数数据报文: " + JSonUtil.toJSonString(req));
    	res = client.getOrderDetailByBankManage(req);
    	System.out.println("查询订单详情，返回数据报文: " + JSonUtil.toJSonString(res));
    }
    
    /**
     * 订单查询详情
     */
    public void testQueryOrderByMerchantPhone(OrderQueryService.Client client) throws TException{
    	QueryOrderByMerchantPhoneVoReq req =new QueryOrderByMerchantPhoneVoReq();
    	QueryOrderByMerchantPhoneVoRes res =new QueryOrderByMerchantPhoneVoRes();
    	req.setClientId("chongqing");
    	req.setMerchantId("0DDBE1738000");//自提：0CED78800000  配送：06D644F08000
    	req.setOutletId("0DDC16F40000");
    	
    	PageVo page= new PageVo();
    	page.setBegDate(0);
    	page.setEndDate(0);
    	page.setFirstRecordTime(0L);//:1441525066871  :1430379859000
    	//page.setFirstRecordTime(1441525066871l);
    	page.setLastPageNumber(1);
    	//page.setLastPageNumber(1);
    	page.setLastRecordTime(0L);//:1430379912000   :1430373448000
    	//page.setLastRecordTime(1430379912000l);
    	page.setPageCount(0);
    	page.setPageNumber(1);
    	page.setPageSize(10);
    	page.setTotalCount(0);
    	req.setPage(page);
    	
    	//req.setOrderId("0D5D29548000");//自提：0CED78800000  配送：06D644F08000
    	//团购
    	//req.setType(OrderType.group_merchant.getCode());
    	//预售
    	req.setType(OrderType.face2face.getCode());
    	//req.setDeliveryOption("0");//0:配送 1:自提
    	//req.setDeliveryOption("1");//0:配送 1:自提
    	//名优特惠
    	//req.setType(OrderType.special_merchant.getCode());
    	//积分兑换
    	//req.setType(OrderType.offline_points_org.getCode());
    	//req.setType(OrderType.online_points_org.getCode());
    	//面对面
    	//req.setType(OrderType.face2face.getCode());
    	System.out.println("查询订单详情，参数数据报文: " + JSonUtil.toJSonString(req));
    	res = client.queryOrderByMerchantPhone(req);
    	System.out.println("查询订单详情，返回数据报文: " + JSonUtil.toJSonString(res));
    }
    
    
    /**
     * @param userName
     */
    public void startClient() {//TODO
        TTransport transport = null;
        try {
            transport = new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT);
            // 协议要和服务端一致
//            TProtocol protocol = new TBinaryProtocol(transport);
            TProtocol protocol = new TCompactProtocol(transport);
            // TProtocol protocol = new TJSONProtocol(transport);
            OrderQueryService.Client client = new OrderQueryService.Client(protocol);
            transport.open();
            
            
            //请求服务端
    		/*TBinaryProtocol protocol = new TBinaryProtocol(transport);
    		TMultiplexedProtocol mp1 = new TMultiplexedProtocol(protocol,"OrderService");
    		OrderService.Client client = new OrderService.Client(mp1);
    		transport.open();*/
            
            //订单列表查询
            testQueryOrderByMerchantPhone(client);
            
            //订单详情查询
//            testGetOrderDetailByBankManage(client); 
            
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        } finally {
            if (null != transport) {
                transport.close();
            }
        }
    }
    
    public static void main(String[] args) throws ParseException {
//		long time1 = new Date().getTime();
//		String framat = "yyyy-MM-dd HH:mm:ss";
//		SimpleDateFormat sf=new SimpleDateFormat(framat);
////		Date timeStart=sf.parse("2015-06-01 00:00:00");//1433088000000
////		Date timeEnd=sf.parse("2015-09-01 23:59:59");//1441123199000
////		Date timeStart=sf.parse("2014-10-01 00:00:00");//1412092800000
////		Date timeEnd=sf.parse("2015-09-01 23:59:59");//1441123199000
//		Date timeStart=sf.parse("2015-06-16 00:00:00");//1412092800000
//		Date timeEnd=sf.parse("2015-09-14 23:59:59");//1441123199000
//		long startTime=timeStart.getTime();
//		long endTime=timeEnd.getTime();
//		System.out.println("startTime="+startTime+" endTime:"+endTime);
		OrderQueryClient client = new OrderQueryClient();
        client.startClient();
	}
}
