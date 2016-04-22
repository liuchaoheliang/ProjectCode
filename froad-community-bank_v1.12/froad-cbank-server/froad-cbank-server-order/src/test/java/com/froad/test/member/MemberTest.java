package com.froad.test.member;

import com.alibaba.fastjson.JSONObject;
import com.froad.thirdparty.bean.PointInfoDto;
import com.froad.thirdparty.dto.request.points.PointsReq;
import com.froad.thirdparty.dto.request.points.PointsReq.QueryOrderType;
import com.froad.thirdparty.dto.response.points.PointsRes;
import com.froad.thirdparty.request.points.PointsApiFunc;
import com.froad.thirdparty.request.points.impl.PointsApiFuncImpl;
import com.froad.thrift.vo.member.UserEnginePointsRecordVo;
import com.froad.util.bean.PropCopyUtils;



public class MemberTest {

    /** 
     * @Title: main 
     * @author vania
     * @version 1.0
     * @see: TODO
     * @param args
     * @return void    返回类型 
     * @throws 
     */
    public static void main(String[] args) {
    	System.setProperty("CONFIG_PATH", "./config");
    	PointsApiFunc fun = new PointsApiFuncImpl();
    	PointsReq request = new PointsReq();
    	request.setObjectNo("100000050012015032010");
    	request.setOrderType(QueryOrderType.consume);
    	request.setPartnerNo("10000005001");
		PointsRes result = fun.queryOrderStatus(request);
		System.out.println(JSONObject.toJSON(result.getOrderStatus()));
//    	UserEngineFunc user = new UserEngineFuncImpl();
//		UserResult obj = user.queryMemberAndCardInfo("6229538106502396018","643");
//		System.out.println(JSONObject.toJSON(obj));
    }
    
    public static void testCopy(){
    	UserEnginePointsRecordVo vo = new UserEnginePointsRecordVo();
    	vo.setAccountNo("accountNo");
    	vo.setAccountNoIsSet(true);
    	vo.setTime(System.currentTimeMillis());
    	vo.setProtocolType("cardDepositPoints");
    	PointInfoDto dto = new PointInfoDto();
    	
    	PropCopyUtils.copyProperties(dto, vo);
    	
    	System.out.println(JSONObject.toJSON(dto));
    	vo = new UserEnginePointsRecordVo();
    	PropCopyUtils.copyProperties(vo, dto);
    	
    	System.out.println(JSONObject.toJSON(vo));
    	
    }
    
    public static void testThread(){
    	StackTraceElement[] trace = Thread.getAllStackTraces().get(Thread.currentThread());
    	for( StackTraceElement t : trace){
    		System.out.println(t.getFileName()+'-'+t.getMethodName()+" Line:"+t.getLineNumber());
    	}
    	
    }
    
}
