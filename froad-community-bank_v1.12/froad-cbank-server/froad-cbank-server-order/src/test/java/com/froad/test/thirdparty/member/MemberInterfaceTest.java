
package com.froad.test.thirdparty.member;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;

import com.alibaba.fastjson.JSONObject;
import com.froad.common.beans.ResultBean;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mysql.MyBatisManager;
import com.froad.logic.impl.MemberInformationLogicImpl;
import com.froad.po.mongo.OrderMongo;
import com.froad.support.impl.MemberInformationSupportImpl;
import com.froad.support.impl.PaymentSupportImpl;
import com.froad.thrift.service.BankCardService;
import com.froad.thrift.service.MemberInformationService;
import com.froad.thrift.service.MemberSecurityService;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.member.QueryProtocolVo;
import com.froad.thrift.vo.member.UserEngineQuestionVo;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class MemberInterfaceTest {

    static MemberSecurityService.Iface mss;

    static MemberInformationService.Iface mis;

    static BankCardService.Iface bankService;

    static {
    	System.setProperty("CONFIG_PATH", "./config");
        // 调用Thrift服务
//         TSocket transport = new TSocket("10.43.2.3", 15501);
        TSocket transport = new TSocket("localhost", 15501);
        TBinaryProtocol protocol = new TBinaryProtocol(transport);

        TMultiplexedProtocol proxy1 = new TMultiplexedProtocol(protocol, "MemberSecurityService");
        TMultiplexedProtocol proxy2 = new TMultiplexedProtocol(protocol, "MemberInformationService");
        TMultiplexedProtocol proxy3 = new TMultiplexedProtocol(protocol, BankCardService.class.getSimpleName());
        mss = new MemberSecurityService.Client(proxy1);
        mis = new MemberInformationService.Client(proxy2);
        bankService = new BankCardService.Client(proxy3);

        try {
            transport.open();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws TException, IOException {
        // BufferedImage image = ImageIO.read(new File("e:/1.png"));
        // ImageIO.write(image, "jpg", new File("e:/2.jpeg"));
        // testPointsList();
        // testSendExchangeCode();
        // testVerifyCode();
        // testPointsList();s
        // testSignCard();
//        testPassword();
//            new UserEngineFuncImpl().queryMemberAndCardInfo(null, null)

//        testSignCard();

//    	o = new MemberSecurityLogicImpl().verifyMemberPayPwd(50000000164L, pwd, CreateSource.android);
//    	System.out.println(JSONObject.toJSON(o));
//    	BankCardResponse obj = bankService.selectSignedBankCardByClientId("anhui", 50000000193L);
//    	System.out.println(JSONObject.toJSON(obj));
//    	testSignCard();
//    	UserEnginePointsVo obj = mis.selectMemberPointsInfoByLoginID("anhui", "llllll");
//    	System.out.println(JSONObject.toJSON(obj));
    	
//    	Object result = bankService.selectSignedBankCardByClientId("chongqing", 50000000193L);
//    	System.out.println(JSONObject.toJSON(result));
//    	testPointsList();
    	testMongo();
//    	PaymentSupportImpl ps= new PaymentSupportImpl();
//    	ps.updateMerchantMonthCountForRefund("anhui", "0166E2DC0000", 
//    	OrderType.offline_points_org.getCode(), System.currentTimeMillis()-864000000, -1000);
//    	MemberInformationLogicImpl mil = new MemberInformationLogicImpl();
//    	mil.payPointsByMobile("anhui", "order_id_string", "reason","remark","340618", "ah6229538101405013737", 1);
//    	mis.payPointsByMobileNo("anhui", "order_id_string", "reason","remark","340618", "ah6229538101405013737", 1);
//    	UserEngineFunc userEngineFunc = new UserEngineFuncImpl();
//    	String loginId =  "ah6229538101405013737";
//        String pwd = loginId.substring(loginId.length() - 6);
//        UserSpecDto user = new UserSpecDto();
//        user.setLoginID(loginId);
//        user.setLoginPwd(pwd);
//        user.setRegisterIP("192.168.1.1");
//        user.setCreateChannel(CreateChannel.FFT.getValue());
//        UserResult userResult = userEngineFunc.registerMember(user);
//        if (!userResult.getResult()) {
//        	System.out.println("false");
//        }
    	
    }
    public static void testSafeQuestion() throws TException{
    	List<UserEngineQuestionVo> questions = new ArrayList();
    	UserEngineQuestionVo vo = new UserEngineQuestionVo();
    	questions.add(vo);
    	vo.setQuestionID(33336L);
    	vo.setAnswer("你好1");
		ResultVo r = mss.veryfyMemberQuestion(50000000196L, questions);
		System.out.println(r);
    }
    public static void testQuestion() throws TException{
    	String ciphertextPwd = "41709D87851CB0F1715DECB6D68EECF8DB9E7B70FFB23653A1E37BD47884263169AE5957044E2A76C79225339B85A8E585B23B3E3D674350BC0A35B2AD4AE46766C0753150DFCA01DBC533356BC33C1A42221BDB5700785044F3D77EBAA02022CD63CEF5439CF2BF5339E9B44FD43AE1FCE6A841B098699F572AB5956CD4A17B";
    	long memberCode = 50000000166L;
    	Object o =null;
//        o= mss.setFirstMemberPayPwd(memberCode, ciphertextPwd, ciphertextPwd, "200");
//        System.out.println(JSONObject.toJSON(o));
        o = mss.verifyMemberPayPwd(memberCode, "111211", "100");
        System.out.println(JSONObject.toJSON(o));
    }
    public static void testqueryMemberAndCardInfo(){
        
        ResultBean obj = new MemberInformationSupportImpl().queryMemberAndCardInfo("1000", "6229538101405013737");
        System.out.println(JSONObject.toJSON(obj));
    }

    public static void testSignCard() throws TException {
//        ResultVo r = bankService.sendSignBankCardMobileToken(1000, "13123533532", "6229538101405013737");
//        System.out.println(JSONObject.toJSON(r));
//        ResultVo o = bankService.signBankCardByClientId("anhui", 50000000165L, "6229538101405013737", "cqjf", "353533159876523652", "13123533532", null, null, null, null);
//        System.out.println(JSONObject.toJSON(o));

    }

    public static void testSendExchangeCode() throws TException {
        Object o = mss.sendExchangeCode("1001", "6229538101405013737", 10, false);
        System.out.println(JSONObject.toJSON(o));
    }

    public static void testVerifyCode() throws TException {
        Object o = mss.verifyExchangeCode("1001", "6229538101405013737", "1122", false);
        System.out.println(JSONObject.toJSON(o));
    }

    public static void testPassword() throws TException {
//        Object oi = mis.findBankPointsByMobile(1000, "11111111");
//        System.out.println(oi);

        String pwd = "41709D87851CB0F1715DECB6D68EECF8DB9E7B70FFB23653A1E37BD47884263169AE5957044E2A76C79225339B85A8E585B23B3E3D674350BC0A35B2AD4AE46766C0753150DFCA01DBC533356BC33C1A42221BDB5700785044F3D77EBAA02022CD63CEF5439CF2BF5339E9B44FD43AE1FCE6A841B098699F572AB5956CD4A17B";

        Object o = mss.resetMemberPayPwd(50000000164L, pwd, pwd, "200");
        System.out.println(o);

        o = mss.verifyMemberPayPwd(50000000164L, pwd, "200");
        System.out.println(o);

    }

    public static void testPointsList() throws TException {
        
//        RedisManager redis = new RedisManager();
//        redis.hset(RedisKeyUtil.cbbank_client_client_id("1002"), "point_partner_no", "10000005001");
//        Set<String> set = new HashSet<String>();
//        set.add("003dd8e50008");
//        set.add("003dd8e50009");
//        redis.putSet(RedisKeyUtil.cbbank_client_channels_client_id("1002"), set);
//        redis.hset(RedisKeyUtil.cbbank_client_channel_client_id_payment_channel_id("1002", "003dd8e50008"), "payment_org_no", "100010002");
//        redis.hset(RedisKeyUtil.cbbank_client_channel_client_id_payment_channel_id("1002", "003dd8e50008"), "type", "1");
//        redis.hset(RedisKeyUtil.cbbank_client_channel_client_id_payment_channel_id("1002", "003dd8e50009"), "payment_org_no", "100010043");
//        redis.hset(RedisKeyUtil.cbbank_client_channel_client_id_payment_channel_id("1002", "003dd8e50009"), "type", "2");
//
//        Calendar c = Calendar.getInstance();
//        c.add(Calendar.DATE, -80);
//        String userName = "u130814101";
        QueryProtocolVo o = mis.getPointTransBypage("anhui", "u130814101",1409132800000L, 1430755199000L, null, "bankPoints", 20, 1);
        // // for()
        System.out.println(JSONObject.toJSON(o.getQueryInfo()));
        System.out.println(com.alibaba.fastjson.JSON.toJSON(o.getPointInfos()));
        // QueryInfoVo qi = o.getQueryInfo();
        // int num = Integer.valueOf(qi.getTotalPageNum());
        // for (UserEnginePointsRecordVo vo : o.getPointInfos()) {
        // System.out.println(DateUtil.formatDate2Str(new Date(vo.getTime())));
        // }
        // for (int i = 2; i <= num; i++) {
        // o = mis.getPointTransBypage(1002, userName, c.getTimeInMillis(), 0, null, "froadPoints", 10, i);
        // for (UserEnginePointsRecordVo vo : o.getPointInfos()) {
        // System.out.println(DateUtil.formatDate2Str(new Date(vo.getTime())));
        // }
        // }
        // System.out.println(JSONObject.toJSON(o));
    }
    
    
    public static void testMongo(){
    	MongoManager m = new MongoManager();
    	MyBatisManager mm = new MyBatisManager();
//    	DBCollection order = m.getDBCollection("cb_order");
    	DBObject cond = new BasicDBObject();
    	cond.put("order_status", "6");
    	List<OrderMongo>list = (List<OrderMongo>) m.findAll(cond, "cb_order", OrderMongo.class);

    	PaymentSupportImpl ps= new PaymentSupportImpl();
    	for(OrderMongo order : list){
    		ps.updateMerchantMonthCountForPaySuccess(order);
    	}
    	
    }
	private static int getIntValue(Integer value){
		if(value==null){
			return 0;
		}
		return value;
	}
}
