package com.froad.logic.impl.order;

import com.alibaba.fastjson.JSON;
import com.froad.logback.LogCvt;
import com.froad.util.EmptyChecker;
import com.froad.util.PropertiesUtil;

public class OrderLogger {
	
	public static void setNullStringEmpty(String...params){
		for(String param : params){
			if(EmptyChecker.isEmpty(param)){
				param = "";
			}
		}
	}
	
	public static void info(String modual,String bussiness,String msg,Object[] fieldAndParams){
		try {
			setNullStringEmpty(modual,bussiness,msg);
			StringBuffer sb = new StringBuffer();
			if(EmptyChecker.isNotEmpty(modual)){
				sb.append("[");
				sb.append(modual);
				sb.append("]-");
			}
			if(EmptyChecker.isNotEmpty(bussiness)){
				sb.append(bussiness);
			}
			if(EmptyChecker.isNotEmpty(msg)){
				sb.append("：");
				sb.append(msg);
			}
			if(EmptyChecker.isNotEmpty(fieldAndParams)){
				sb.append(" {");
				StringBuffer sbs = new StringBuffer();
				for(int i = 0 ; i < fieldAndParams.length; i++){
					if(EmptyChecker.isEmpty(fieldAndParams[i])){
						fieldAndParams[i] = "";
					}
					sbs.append(JSON.toJSONString(fieldAndParams[i]));
					if(i%2==0){
						sbs.append(" : ");
					}
					if(i%2==1 && i!=(fieldAndParams.length-1)){
						sbs.append(" , ");
					}
				}
				sb.append(sbs);
				sb.append("}");
			}
			LogCvt.info(sb.toString());
		} catch (Exception e) {
			LogCvt.info("日志打印异常：",e);
		}
	}
	
	public static void error(String modual,String bussiness,String msg,Object[] fieldAndParams){
		try {
			setNullStringEmpty(modual,bussiness,msg);
			StringBuffer sb = new StringBuffer();
			if(EmptyChecker.isNotEmpty(modual)){
				sb.append("[");
				sb.append(modual);
				sb.append("]-");
			}
			if(EmptyChecker.isNotEmpty(bussiness)){
				sb.append(bussiness);
			}
			if(EmptyChecker.isNotEmpty(msg)){
				sb.append("：");
				sb.append(msg);
			}
			if(EmptyChecker.isNotEmpty(fieldAndParams)){
				sb.append(" {");
				StringBuffer sbs = new StringBuffer();
				for(int i = 0 ; i < fieldAndParams.length; i++){
					if(EmptyChecker.isEmpty(fieldAndParams[i])){
						fieldAndParams[i] = "";
					}
					sbs.append(JSON.toJSONString(fieldAndParams[i]));
					if(i%2==0){
						sbs.append(" : ");
					}
					if(i%2==1 && i!=(fieldAndParams.length-1)){
						sbs.append(" , ");
					}
				}
				sb.append(sbs);
				sb.append("}");
			}
			LogCvt.error(sb.toString());
		} catch (Exception e) {
			LogCvt.error("日志打印异常：",e);
		}
	}

	public static void main(String[] args) {
		PropertiesUtil.load();
		// TODO Auto-generated method stub
		Double[] aaa = new Double[]{1.0,2.0};
		info("订单模块","积分拆分",null,new Object[]{"商品ID","111","商品名",new Object[]{"1",aaa}});
	}

}
