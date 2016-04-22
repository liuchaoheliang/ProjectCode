package com.froad.cbank.coremodule.module.normal.merchant.utils;

import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.froad.cbank.coremodule.module.normal.merchant.pojo.Image_Info_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Update_Product_Req;
import com.froad.thrift.vo.ProductImageVo;
import com.froad.thrift.vo.ProductInfoVo;

/**
 * @author Administrator
 *
 */
public class HandleProduct {

	/**
	 * 
	 */
	public HandleProduct() {
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
	 * @throws ParseException 
	 */
	public static boolean isEquals(ProductInfoVo source,Update_Product_Req target){
		try {
			boolean bug=true;
			if(StringUtils.isNotEmpty(source.getProduct().getBriefIntroduction())&&StringUtils.isNotEmpty(target.getBriefIntroduction())){
				if(!source.getProduct().getBriefIntroduction().equals(target.getBriefIntroduction()))bug=false;
			}else if(StringUtils.isNotEmpty(source.getProduct().getBriefIntroduction())&&!StringUtils.isNotEmpty(target.getBriefIntroduction()))bug=false;
			else if(!StringUtils.isNotEmpty(source.getProduct().getBriefIntroduction())&&StringUtils.isNotEmpty(target.getBriefIntroduction()))bug=false;
			
			if(!source.getProduct().getName().equals(target.getName()))bug=false;
			if(!source.getProduct().getFullName().equals(target.getFullName()))bug=false;
			if(!source.getProduct().getBuyKnow().equals(target.getBuyKnow()))bug=false;
			if(!source.getProduct().getIntroduction().equals(target.getIntroduction()))bug=false;
			if(source.getProduct().getStore()!=target.getStore())bug=false;
			if(!(PramasUtil.DateFormat(source.getProduct().getStartTime(),"yyyy-MM-dd")+"").equals(target.getStartTime()))bug=false;
			if(!(PramasUtil.DateFormat(source.getProduct().getEndTime(),"yyyy-MM-dd")+"").equals(target.getEndTime()))bug=false;
			if(!(PramasUtil.DateFormat(source.getProduct().getExpireEndTime(),"yyyy-MM-dd")+"").equals(target.getExpireEndTime()))bug=false;
			if(source.getProduct().getMarketPrice()!=target.getMarketPrice())bug=false;
			if(source.getProduct().getPrice()!=target.getPrice())bug=false;
			if(source.getBuyLimit().getMax()!=target.getMax())bug=false;
			
			if(source.getProductCategory().getId()!=Long.valueOf(target.getCategoryId()))bug=false;

			List<ProductImageVo> sourceList=source.getImage();
			List<Image_Info_Req> targetList=target.getImgList();
			int number=0;
			for(ProductImageVo sm:sourceList){
				for(Image_Info_Req rm:targetList){
					if(sm.getSource().equals(rm.getSource())){
						  number++;	
					}
				}
			}
			if((number==sourceList.size())&&(number==targetList.size())){}
			else {bug=false;}

			return bug;
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			return false;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return false;
		}catch(Exception e){
			return false;
		}
	}

}
