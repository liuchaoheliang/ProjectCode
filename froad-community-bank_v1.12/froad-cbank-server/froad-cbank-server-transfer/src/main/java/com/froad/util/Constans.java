package com.froad.util;

import com.froad.logback.LogCvt;

public class Constans {

	/**
	 * 安徽客户端ID
	 */
	public final static String clientId = "anhui";
	
	/**
	 * 生产配置需要修改
	 */
	public final static String oldImagePath = "/froad-fft/image";
	public final static String imagePath = "/froad-cb/anhui";
	public final static String oldDomainPath = "http://image.o2obill.com/fft_image/froad-fft/image/";
//	public final static String domainPath = "//devimg.sqyh365.cn/froad-cb/anhui/";
	public final static String domainPath = "//testimg.sqyh365.cn/froad-cb/anhui/";
	
	public final static String oldBankPointDomainPath = "http://image.o2obill.com/fft_image/bankpoint/image/";
	
	
	public static String converImage(String image){
		if(image == null || "".equals(image)){
			return null;
		}
		return image.replace(oldImagePath, imagePath);
	}
	
	
	public static String converText(String image){
		if(image == null || "".equals(image)){
			return null;
		}
		return image.replaceAll(oldDomainPath, domainPath).replaceAll(oldBankPointDomainPath, domainPath);
	} 
	
	/** 过滤旧机构号,获取新机构号 **/
	public static String filterOrg(String oldOrgCode){
		return oldOrgCode.substring(oldOrgCode.lastIndexOf("_")+1);
	}
	/************过滤treePath**************/
	public static String splitOrgTreePath(String treePath){
		StringBuffer newtreePaths=new StringBuffer();;
		if(treePath.contains("^^")){
			String[] treePaths=treePath.split("\\^\\^");
			for(int i=0;i<treePaths.length;i++){
				String newtreePath=filterOrg(treePaths[i]);
				newtreePaths.append(newtreePath+",");
			}
			return newtreePaths.toString();
		}else{
			newtreePaths.append(filterOrg(treePath));
			return newtreePaths.toString();
		}
	}
	
	/**
	 *  原图
	  * @Title: getSource
	  * @Description: TODO
	  * @author: share 2015年6月1日
	  * @modify: share 2015年6月1日
	  * @param @param jpg
	  * @param @return    
	  * @return String    
	  * @throws
	 */
	public static String getSource(String jpg){
		return converImage(jpg);
	}
	
	public static String getLarge(String jpg){
		return converImage(jpg);
	}
	
	public static String getMedium(String jpg){
		if(jpg.contains("medium")){
			return converImage(jpg);
		}
		return converImage(rename(jpg, "medium"));
	}
	
	public static String getThumbnail(String jpg){
		if(jpg.contains("small")){
			return converImage(jpg);
		}
		return converImage(rename(jpg, "small"));
	}
	
	private static String rename(String jpg,String sufix){
		try {
			String filename = jpg.substring(0,jpg.lastIndexOf("."));
			String sufixPic = jpg.substring(jpg.lastIndexOf("."));
			return filename+"_"+sufix+sufixPic;
		} catch (Exception e) {
			LogCvt.error(e.getMessage(),e);
		}
		return "";
		
	}
	
	
	public static void main(String[] args) {
//		String aa = "{\"org_name\":\"安徽望江农村商业银行桃岭分理处\",\"org_type\":\"1\",\"org_level\":\"3\",\"phone\":\"\",\"area_id\":\"469\",\"need_review\":\"0\",\"org_code\":\"3408292645\",\"city_agency\":\"340829\",\"is_enable\":\"1\",\"county_agency\":\"\",\"province_agency\":\"340000\",\"client_id\":\"anhui\",\"bank_type\":\"1\",\"merchant_id\":\"03B92601801E\"}";
//		String key=RedisKeyUtil.cbbank_org_client_id_org_code("anhui", null);
//		System.out.println(Integer.MAX_VALUE);
		
		System.out.println(getMedium("/fft/aa/dd/aaaabbbdd.jpg"));
	}
	
}

