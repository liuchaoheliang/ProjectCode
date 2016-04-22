package com.froad.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.po.ActiveOrderDetail;
import com.froad.po.ActiveBaseRule;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.active.ActiveBaseRuleVo;

  
/** 
 * JavaBean and map converter. 
 *  
 *  
 */  
public final class ActiveUtils {  
	
	/** 活动类型 */
	public static final String ACT_TYPE      = "active_type";
	/** 活动id */
	public static final String ACT_ID        = "active_id";
	/** 商品id */
	public static final String PRO_ID        = "product_id";
	/** 商品名称 */
	public static final String PRO_NAME      = "product_name";
	/** 普通单价 */
	public static final String GEN_PRICE     = "general_price";
	/** vip单价 */
	public static final String VIP_PRICE     = "vip_price";
	/** 普通优惠金额 */
	public static final String GEN_DIS_MONEY = "general_discount_money";
	/** 普通优惠数量 */
	public static final String GEN_DIS_COUNT = "general_discount_count";
	/** vip优惠金额 */
	public static final String VIP_DIS_MONEY = "vip_discount_money";
	/** vip优惠数量 */
	public static final String VIP_DIS_COUNT = "vip_discount_count";
	/** 红包id */
	public static final String VOU_ID        = "vouchers_id";

	
	public static Map<String, String> PO2Map(Object o) throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		Field[] fields = null;
		fields = o.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			String proName = field.getName();
			String proValue=null;
			if(field.getType().equals(java.lang.Boolean.class)){
				proValue = field.get(o)==null?"":("true".equals(field.get(o).toString())?"1":"0");
			}else if(field.getType().equals(Date.class)){
				proValue = field.get(o)==null?"":DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT2, (Date)field.get(o));
			}else{
				proValue = field.get(o)==null?"":field.get(o).toString();
			}
			if(proValue!=null&&!"".equals(proValue))
			map.put(proName, proValue);
		}
		return map;
	}
	
	/*
	public static Object map2PO(Map<String,String> map,Object o) throws Exception{
		if (!map.isEmpty()) {
			for (String k : map.keySet()) {
				Object v = "";
				if (!k.isEmpty()) {
					v = map.get(k);
				}
				Field[] fields = null;
				fields = o.getClass().getDeclaredFields();
				for (Field field : fields) {
					int mod = field.getModifiers();
					if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){
						continue;
					}
					if (field.getName().toUpperCase().equals(k)) {
						field.setAccessible(true);
						field.set(o, v);
					}

				}
			}
		}
		return o;
	}*/
	
	/**
	  * @Title: getEnumConstant
	  * @Description: 根据枚举对象返回ResultVo
	  * @author: zengfanting 2015年11月8日
	  * @modify: zengfanting 2015年11月8日
	  * @param code
	  * @return
	 */
	public static ResultVo getEnumConstant(String code){
		Class<ResultCode> clz=ResultCode.class;
		for (Object obj : clz.getEnumConstants()) {
			if(((ResultCode)obj).getCode().equals(code)){
				ResultVo vo=new ResultVo();
				vo.setResultDesc(((ResultCode)obj).getMsg());
				vo.setResultCode(((ResultCode)obj).getCode());
				return vo;
			}
		}
		return null;
	}
	
	/*
	if(false){
		满减额/总数量×结算数量=
	}else{
		if(结算数量>1){
			满减额/总数量×(结算数量-1)
			+
			满减额-（满减额度/总数量）×（总数量-1）
		}else{
			满减额-（满减额度/总数量数量）×（总数数量-1）
		}
	}*/
	public static Double settlement(Boolean last, Double totalMoney, Integer totalCount, Integer cutCount){
		double tmp=0;
		if(cutCount>0){
			if(last){//最后一个
				if(cutCount>1){//一次计算2个
					 double t1=Arith.div(totalMoney, totalCount, 2)*(cutCount-1);
					 BigDecimal b = new BigDecimal(t1);
					  t1=b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					  
					  double t3=Arith.div(totalMoney, totalCount, 2)*(totalCount-1);
			          b = new BigDecimal(t3);
					  t3=b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					 
					 double t2=totalMoney-t3;
					 
					 tmp= t1+t2;     
					 
					 b = new BigDecimal(tmp);
					 tmp=b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					 
				}else{//最后一个
					 double t=Arith.div(totalMoney, totalCount, 2)*(totalCount-1);
					 tmp= totalMoney-t;
					 BigDecimal b = new BigDecimal(tmp);
					 tmp=b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				}
			}else{//不是最后一个
				tmp= Arith.div(totalMoney, totalCount, 2)*cutCount;
				BigDecimal b = new BigDecimal(tmp);
				tmp=b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		}else{
			tmp= 0;
		}
      return tmp;
	}
	
	/** 
	 * 计算同活动不同商品的占比满减金额
	 * <br>
	 * 单个商品总额 / 同活动商品总额 * 满减金额
	 * <br>
	 * 百分比保留默认的十位小数
	 * <br>
	 * 小数位只保留到2位，后一位进位处理。
	 * @param singleProductSumMoney 单个商品总额
	 * @param sameActiveProductSumMoney 同活动商品总额
	 * @param cutMoney 满减金额
	 * */
	public static Double calcSameActiveShareCutMoney(Double singleProductSumMoney, Double sameActiveProductSumMoney, Double cutMoney){
		
		return Arith.roundUp(Arith.mul(Arith.div(singleProductSumMoney, sameActiveProductSumMoney), cutMoney), 2);
	}
	
	
	public static String generateActiveId(int count, String clientId, String activeIdCode) {
		// 客户端编号+MJ+自然年份+序列号 ,CQ-MJ-2015-005
		StringBuffer sb = new StringBuffer();
		sb.append(clientId);
		sb.append("-");
		sb.append(activeIdCode);
		sb.append("-");
		sb.append(Calendar.getInstance().get(Calendar.YEAR));
		String strCount = count + "";
		if (strCount.length() == 1) {
			sb.append("-00" + strCount);
		} else if (strCount.length() == 2) {
			sb.append("-0" + strCount);
		} else {
			sb.append("-" + strCount);
		}
		LogCvt.debug("[generateActiveId] 完成生成 : " + sb.toString());
		return sb.toString();
	}

	 /**
	  * @Title: processingNullData
	  * @Description: 处理活动基础数据问题
	  * @author: shenshaocheng 2015年12月6日
	  * @modify: shenshaocheng 2015年12月6日
	  * @param activeBaseRuleVo 前端VO
	  * @param activeBaseRule copy后的po
	  * @return 返回处理后的po
	 */	
	public static ActiveBaseRule processingNullData(ActiveBaseRuleVo activeBaseRuleVo, ActiveBaseRule activeBaseRule) {
		if(activeBaseRuleVo.getId() == 0) {
			activeBaseRule.setId(null);
		}
		
		if(activeBaseRuleVo.getFftRate() == 0) {
			activeBaseRule.setFftRate(null);
		}
		
		if(activeBaseRuleVo.getBankRate() == 0) {
			activeBaseRule.setBankRate(null);
		}
		
		if(activeBaseRuleVo.getMerchantRate() == 0) {
			activeBaseRule.setMerchantRate(null);
		}
		
		if(activeBaseRuleVo.getCreateTime() == 0) {
			activeBaseRule.setCreateTime(null);
		} 
		
		if(activeBaseRuleVo.getUpdateTime() == 0) {
			activeBaseRule.setUpdateTime(null);
		}
		
		if(activeBaseRuleVo.getExpireEndTime() == 0) {
			activeBaseRule.setExpireEndTime(null);
		}
		
		if(activeBaseRuleVo.getExpireStartTime() == 0) {
			activeBaseRule.setExpireStartTime(null);
		}
		return activeBaseRule;
	}
	
	/**
	 * 统计订单详情列表中的子订单id
	 * */
	public static List<String> countSubOrderId(List<ActiveOrderDetail> activeOrderDetailList){
		List<String> subOrderIdList = new ArrayList<String>();
		for( ActiveOrderDetail activeOrderDetail : activeOrderDetailList ){
			String subOrderId = activeOrderDetail.getSubOrderId();
			if( subOrderId != null && !subOrderIdList.contains(subOrderId) ){
				subOrderIdList.add(subOrderId);
			}
		}
		return subOrderIdList;
	}
	
	/**
	 * 统计相同子订单id的订单详情列表
	 * */
	public static List<ActiveOrderDetail> countSameSubOrderId(List<ActiveOrderDetail> activeOrderDetailList, String subOrderId){
		List<ActiveOrderDetail> sameSubOrderIdDetailList = new ArrayList<ActiveOrderDetail>();
		for( ActiveOrderDetail activeOrderDetail : activeOrderDetailList ){
			String id = activeOrderDetail.getSubOrderId();
			if( subOrderId.equals(id) ){
				sameSubOrderIdDetailList.add(activeOrderDetail);
			}
		}
		return sameSubOrderIdDetailList;
	}
	
	public static void main(String[] args) {
		
		String url = "jdbc:mysql://10.43.2.7:3306/froad_cbank_4?useUnicode=true&characterEncoding=utf8";  
	    String name = "com.mysql.jdbc.Driver";  
	    String user = "root";  
	    String password = "root";  
	    java.sql.Connection conn = null; 
	    java.sql.PreparedStatement pst1 = null;
	    java.sql.PreparedStatement pst2 = null;
	    java.sql.ResultSet result = null;
		int index = 0, count = 9000, cf = 0, add = 0;
		String ticket = null;
		long s = System.currentTimeMillis();
		try{
			Class.forName(name);//指定连接类型  
			conn = java.sql.DriverManager.getConnection(url, user, password);//获取连接  
			pst1 = conn.prepareStatement("select * from vvv where id = ?");//准备执行语句  
			pst2 = conn.prepareStatement("insert into vvv(id,money) values(?,?)");//准备执行语句  
			while( index < count ){
//				ticket = generate1();
				ticket = generate2().toUpperCase();
				ticket = assembly(ticket);
				pst1.setString(1, ticket);
            	result = pst1.executeQuery();
            	if( result.next() ){
            		System.out.println("重复了 "+(++cf)+" 个");
            	}else{
            		pst2.setString(1, ticket);
                    pst2.setInt(2, 50);
                    add = pst2.executeUpdate();
                    if( add == 1 ){
                    	index++;
                    	System.out.println("成功了 "+(index)+" 个");
                    }else{
                    	System.out.println("第 "+(index)+" 个失败");
                    }
            	}
            	
			}
		} catch (Exception e) {  
			e.printStackTrace();  
		} finally { 
			if(null != conn)
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}  
		} 
		
		long e = System.currentTimeMillis();
		System.out.println("耗时秒数 "+((e-s)/1000));
		System.out.println("失败个数 "+(cf));
	}
	
	public static String generate2(){
		StringBuffer ticket = new StringBuffer();
		int num = 0;
		for( int i = 0; i < 12; i++ ){
			num = random.nextInt(36);
			if( num < 10 ){
				ticket.append(num);
			}else{
				ticket.append(((char)(num+87)));
			}
		}
		return ticket.toString();
	}
	
	private static java.util.Random random = new java.util.Random();;
	public static String generate1(){
		int first = -1,	second = -1, third = -1;
		String ticket = null, t_first = null, t_second = null, t_third = null;
		first = random.nextInt(100000);
		second = random.nextInt(100000);
		third = random.nextInt(100);
		t_first = fill(first);
		t_second = fill(second);
		t_third = ""+(third<10?("0"+third):(third));
		ticket = t_first+t_second+t_third;
		return ticket;
	}
	
	private static String fill(int num){
		String str = null;
		if( num < 10 ){
			str = "0000"+num;
		}else if( num < 100 ){
			str = "000"+num;
		}else if( num < 1000 ){
			str = "00"+num;
		}else if( num < 10000 ){
			str = "0"+num;
		}else{
			str = ""+num;
		}
		return str;
	}
	
	private static String assembly(String str){
		StringBuffer sb = new StringBuffer(str.substring(0, 4));
		sb.append("-");
		sb.append(str.substring(4, 8));
		sb.append("-");
		sb.append(str.substring(8, 12));
		return sb.toString();
	}
}  