package com.froad.cbank.coremodule.module.normal.merchant.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Image_Info_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Image_Info_Res;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Outlet_Category_Res;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Outlet_Detail_Res;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Update_Outlet_Info_Req;
import com.froad.thrift.vo.AreaVo;

/**
 * @author Administrator
 *
 */
public class HandleOutlet {

	/**
	 * 
	 */
	public HandleOutlet() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 判断两个对象是否有修改
	 * 说明   description of the class
	 * 创建日期  2015年11月13日  下午6:11:33
	 * 作者  artPing
	 * 参数  @param source
	 * 参数  @param target
	 * 参数  @param categoryNames
	 * 参数  @return
	 */
	public static boolean isEquals(Query_Outlet_Detail_Res source,Update_Outlet_Info_Req target,String categoryNames){
		boolean bug=true;
		if(StringUtils.isNotEmpty(source.getAcctName())&&StringUtils.isNotEmpty(target.getAcctName())){
			if(!source.getAcctName().equals(target.getAcctName()))bug=false;
		}else if(StringUtils.isNotEmpty(source.getAcctName())&&!StringUtils.isNotEmpty(target.getAcctName()))bug=false;
		else if(!StringUtils.isNotEmpty(source.getAcctName())&&StringUtils.isNotEmpty(target.getAcctName()))bug=false;
		
		if(StringUtils.isNotEmpty(source.getAcctNumber())&&StringUtils.isNotEmpty(target.getAcctNumber())){
			if(!source.getAcctNumber().equals(target.getAcctNumber()))bug=false;
		}
		else if(StringUtils.isNotEmpty(source.getAcctNumber())&&!StringUtils.isNotEmpty(target.getAcctNumber()))bug=false;
		else if(!StringUtils.isNotEmpty(source.getAcctNumber())&&StringUtils.isNotEmpty(target.getAcctNumber()))bug=false;

		if(StringUtils.isNotEmpty(source.getFax())&&StringUtils.isNotEmpty(target.getFax())){
			if(!source.getFax().equals(target.getFax()))bug=false;
		}else if(StringUtils.isNotEmpty(source.getFax())&&!StringUtils.isNotEmpty(target.getFax()))bug=false;
		else if(!StringUtils.isNotEmpty(source.getFax())&&StringUtils.isNotEmpty(target.getFax()))bug=false;

		if(!String.valueOf(source.getAreaId()).trim().equals(String.valueOf(target.getAreaId()).trim()))bug=false;
		if(!source.getAddress().equals(target.getAddress()))bug=false;
		if(!source.getLatitude().equals(target.getLatitude()))bug=false;
		if(!source.getLongitude().equals(target.getLongitude()))bug=false;
		if(!source.getOutletFullname().equals(target.getOutletFullname()))bug=false;
		if(!source.getOutletName().equals(target.getOutletName()))bug=false;
		if(!source.getBusinessHours().equals(target.getBusinessHours()))bug=false;
		if(!source.getPhone().equals(target.getPhone()))bug=false;
		if(!source.getContactName().equals(target.getContactName()))bug=false;
		if(!source.getContactPhone().equals(target.getContactPhone()))bug=false;
		if(!source.getDescription().equals(target.getDescription()))bug=false;
		if(!source.getPreferDetails().equals(target.getPreferDetails()))bug=false;
		if(!source.getDiscountCode().equals(target.getDiscountCode()))bug=false;
		if(!source.getDiscountRate().equals(target.getDiscountRate()))bug=false;
		String categ="";
		List<Query_Outlet_Category_Res> categoryList=source.getCategoryList();
		if(categoryList!=null&&categoryList.size()>0){
			for(Query_Outlet_Category_Res cate:categoryList){
				categ+=cate.getName()+",";
			}
		}
		if(!categ.equals(categoryNames)){
			bug=false;
			
		}
		List<Image_Info_Res> sourceList=source.getImgList();
		List<Image_Info_Req> targetList=target.getImgList();
		int number=0;
		for(Image_Info_Res sm:sourceList){
			for(Image_Info_Req rm:targetList){
				if(sm.getSource().equals(rm.getSource())){
					  number++;	
				}
			}
		}
		if((number==sourceList.size())&&(number==targetList.size())){}
		else {bug=false;}

		return bug;
	}

	
	/**
	 * 判断两个对象是否相等
	 * 说明   description of the class
	 * 创建日期  2015年11月13日  下午6:11:49
	 * 作者  artPing
	 * 参数  @param source
	 * 参数  @param target
	 * 参数  @param categoryNames
	 * 参数  @return
	 */
	public static Query_Outlet_Detail_Res getIsUpdate(Query_Outlet_Detail_Res source,Query_Outlet_Detail_Res target,String categoryNames){

		if(StringUtils.isNotEmpty(source.getAcctName())&&StringUtils.isNotEmpty(target.getAcctName())){
			if(source.getAcctName().equals(target.getAcctName()))target.setAcctName("0 | 0||| ");
		}else if(StringUtils.isEmpty(source.getAcctName())&&StringUtils.isEmpty(target.getAcctName())){
			target.setAcctName("0 | 0||| ");
		}
		
		if(StringUtils.isNotEmpty(source.getAcctNumber())&&StringUtils.isNotEmpty(target.getAcctNumber())){
			if(source.getAcctNumber().equals(target.getAcctNumber()))target.setAcctNumber("0 | 0||| ");
		}else if(StringUtils.isEmpty(source.getAcctNumber())&&StringUtils.isEmpty(target.getAcctNumber())){
			target.setAcctNumber("0 | 0||| ");
		}
		
		if(StringUtils.isNotEmpty(source.getFax())&&StringUtils.isNotEmpty(target.getFax())){
			if(source.getFax().equals(target.getFax()))target.setFax("0 | 0||| ");
		}else if(StringUtils.isEmpty(source.getFax())&&StringUtils.isEmpty(target.getFax())){
			target.setFax("0 | 0||| ");
		}	
		
        boolean bug=false;
		if(!String.valueOf(source.getAreaId()).trim().equals(String.valueOf(target.getAreaId()).trim())){
			bug=true;
		}
		if(!source.getCityId().equals(target.getCityId())){
			bug=true;
		}
		if(!source.getCountyId().equals(target.getCountyId())){
			bug=true;
		}
		
		if(!bug){
			target.setAreaId("0 | 0||| ");
			target.setCityId("0 | 0||| ");
			target.setCountyId("0 | 0||| ");
			target.setCityName("0 | 0||| ");
			target.setAreaName("0 | 0||| ");		
			target.setCountyName("0 | 0||| ");
		}
		if(source.getAddress().equals(target.getAddress()))target.setAddress("0 | 0||| ");
		if(source.getLatitude().equals(target.getLatitude()))target.setLatitude("0 | 0||| ");
		
		if(source.getLongitude().equals(target.getLongitude()))target.setLongitude("0 | 0||| ");
		if(source.getOutletFullname().equals(target.getOutletFullname()))target.setOutletFullname("0 | 0||| ");

		if(source.getOutletName().equals(target.getOutletName()))target.setOutletName("0 | 0||| ");
		if(source.getBusinessHours().equals(target.getBusinessHours()))target.setBusinessHours("0 | 0||| ");

		if(source.getPhone().equals(target.getPhone()))target.setPhone("0 | 0||| ");

		if(source.getContactName().equals(target.getContactName()))target.setContactName("0 | 0||| ");
		if(source.getContactPhone().equals(target.getContactPhone()))target.setContactPhone("0 | 0||| ");
		if(source.getDescription().equals(target.getDescription()))target.setDescription("0 | 0||| ");
		if(source.getPreferDetails().equals(target.getPreferDetails()))target.setPreferDetails("0 | 0||| ");
		

		if(source.getDiscountCode().equals(target.getDiscountCode()))target.setDiscountCode("0 | 0||| ");
		if(source.getDiscountRate().equals(target.getDiscountRate()))target.setDiscountRate("0 | 0||| ");
		String categ="";
		List<Query_Outlet_Category_Res> categoryList=source.getCategoryList();
		if(categoryList!=null&&categoryList.size()>0){
			for(Query_Outlet_Category_Res cate:categoryList){
				categ+=cate.getName()+",";
			}
		}
		if(categ.equals(categoryNames))target.setCategoryList(null);
		List<Image_Info_Res> sourceList=source.getImgList();
		List<Image_Info_Res> targetList=target.getImgList();
		int number=0;
		for(Image_Info_Res sm:sourceList){
			for(Image_Info_Res rm:targetList){
				if(sm.getSource().equals(rm.getSource())){
					  number++;	
				}
			}
		}
		if((number==sourceList.size())&&(number==targetList.size())){
			target.setImgList(null);
		}
		return target;
	}
	
	
	/**
	 * 赋值老数据给门店对象
	 * 说明   description of the class
	 * 创建日期  2015年11月13日  下午6:13:00
	 * 作者  artPing
	 * 参数  @param source
	 * 参数  @param target
	 * 参数  @param categoryNames
	 * 参数  @return
	 */
	public static Query_Outlet_Detail_Res setOutlet(Map<String,Object> data){
	
		Query_Outlet_Detail_Res res=new Query_Outlet_Detail_Res();
		res.setAcctName(data.get("acctName")!=null?data.get("acctName")+"":"");
		res.setAcctNumber(data.get("acctNumber")!=null?data.get("acctNumber")+"":"");
		res.setAddress(data.get("address")!=null?data.get("address")+"":"");
		res.setAreaId(data.get("areaId")!=null?data.get("areaId")+"":"");
		res.setAuditComment(data.get("auditComment")!=null?data.get("auditComment")+"":"");
		res.setAuditStaff(data.get("auditStaff")!=null?data.get("auditStaff")+"":"");
		res.setAuditState(data.get("auditState")!=null?data.get("auditState")+"":"");
		res.setBusinessHours(data.get("businessHours")!=null?data.get("businessHours")+"":"");
		res.setContactName(data.get("contactName")!=null?data.get("contactName")+"":"");
		res.setContactPhone(data.get("contactPhone")!=null?data.get("contactPhone")+"":"");
		res.setDescription(data.get("description")!=null?data.get("description")+"":"");
		res.setDiscountCode(data.get("discountCode")!=null?data.get("discountCode")+"":"");
		res.setDiscountRate(data.get("discountRate")!=null?data.get("discountRate")+"":"");
		res.setEditAuditState(data.get("editAuditState")!=null?data.get("editAuditState")+"":"");
		res.setFax(data.get("fax")!=null?data.get("fax")+"":"");
		res.setOutletFullname(data.get("outletFullname")!=null?data.get("outletFullname")+"":"");
		res.setPhone(data.get("phone")!=null?data.get("phone")+"":"");
		res.setOutletName(data.get("outletName")!=null?data.get("outletName")+"":"");
		res.setImgList(getOutletImage(data.get("merchantOutletPhotoList")+""));
		if(data.get("outletCategoryName")!=null){
			res.setCategoryList(getOutletCateGroryName(data.get("outletCategoryName")+"",data.get("outletCategoryId")+""));
		}
		return res;
	}
	
	/**
	 * 区域公共方法
	 * 说明   description of the class
	 * 创建日期  2015年11月13日  下午5:50:21
	 * 作者  artPing
	 * 参数  @param listvo
	 * 参数  @return
	 */
	public static Map<String,String> getArea(AreaVo listvo){
        Map<String,String> str=new HashMap<String,String>();
		if(listvo!=null){
			String[] pathid=listvo.getTreePath().split(",");
			String[] name=listvo.getTreePathName().split(",");
			if(pathid.length>0&&name.length>0){
				str.put("countyId",pathid[0]);
				str.put("countyName",name[0]);
				if(pathid.length>1){
					str.put("cityId",pathid[1]);
					str.put("cityName",name[1]);
				}
				if(pathid.length>2){
					str.put("areaId",pathid[2]);
					str.put("areaName",name[2]);
				}
			}
		}
		return str;
	}
	
	/**
	 * 格式化门店分类方法
	 * 说明   description of the class
	 * 创建日期  2015年11月13日  下午5:50:35
	 * 作者  artPing
	 * 参数  @param cateName
	 * 参数  @param cateId
	 * 参数  @return
	 */
	public static List<Query_Outlet_Category_Res> getOutletCateGroryName(String cateName,String cateId){
		List<Query_Outlet_Category_Res> categoryList=new ArrayList<Query_Outlet_Category_Res>();
		if(cateName!=null){
			String[] cateNames=cateName.split(",");
			String[] cateIds=cateId.split(",");
			for(int i=0;i<cateNames.length;i++){
				Query_Outlet_Category_Res ocate=new Query_Outlet_Category_Res();
				ocate.setCategoryId(Long.valueOf(cateIds[i]));
				ocate.setName(cateNames[i]);
				categoryList.add(ocate);
			}
	     }
		return categoryList;
	}
	
	/**
	 * 图片字符串转换list对象
	 * 说明   description of the class
	 * 创建日期  2015年11月13日  下午5:53:35
	 * 作者  artPing
	 * 参数  @param imageJson
	 * 参数  @return
	 */
	public static List<Image_Info_Res>  getOutletImage(String imageJson){
		List<Image_Info_Res> imgaes=new ArrayList<Image_Info_Res>();
        JSONArray jsonArray = JSONArray.parseArray(imageJson);  
        for (int i = 0; i < jsonArray.size(); i++) {
        	Image_Info_Res image=JSON.parseObject(jsonArray.get(i).toString().trim(), Image_Info_Res.class);
        	imgaes.add(image);
        }
        return imgaes;
	}
}
