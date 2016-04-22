package com.froad.fft.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class BeanUtil {
	public static String apply(Class<?> fBean,String fVarName,Class<?> tBean,String tVarName){
		StringBuilder sb = new StringBuilder();
		StringBuilder sbless = new StringBuilder();
		Field[] tfields = tBean.getDeclaredFields();
		for (Field field : tfields) {
			String fieldName = field.getName();
			if("serialVersionUID".equals(fieldName)||field.isAccessible()){
				continue;
			}
			String getMethodName = methodName(fieldName,"get");
			Method getMethod = null;
			try{
				getMethod = fBean.getMethod(getMethodName);
			}catch(Exception e){
				System.out.println("no method named "+getMethodName);
			}
			if(getMethod!=null){
				String valueStr = "";
				valueStr = fVarName+"."+getMethodName+"()";
				sb.append(tVarName+methodName(fieldName,".set")+"("+valueStr+");\n");
			}else{
				sbless.append(tVarName+methodName(fieldName,".set")+"();\n");
			}
		}
		return sb.toString()+sbless.toString();
	}
	
	public static String methodName(String fieldName,String pre){
		
		return pre+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1,fieldName.length());
	}
	
	public static void extendsStr(Class ... beans){
		if(beans!=null){
			Map<String,Field> fields = new HashMap<String,Field>();
			for (int i = 0; i < beans.length; i++) {
				Field[] tfields = beans[i].getDeclaredFields();
				for (Field field : tfields) {
					String fieldName = field.getName();
					if("serialVersionUID".equals(fieldName)||field.isAccessible()){
						continue;
					}
					if(!fields.containsKey(fieldName+field.getType())){
						fields.put(fieldName+field.getClass(), field);
					}
				}
			}
			for (Entry<String,Field> entry :fields.entrySet()) {
				Field field = entry.getValue();
				System.out.println("private "+field.getType().getSimpleName()+" "+field.getName()+";");
			}
		}
	}
	
	public static void main(String[] args){
//		String str = apply(com.froad.fft.entity.AdPosition.class, "adPosition",com.froad.fft.dto.AdPositionDto.class, "adPositionDto");
//		String str = apply(com.froad.fft.entity.Ad.class, "ad",com.froad.fft.dto.AdDto.class, "adDto");
//		String str = apply(com.froad.fft.dto.AdDto.class, "adDto",com.froad.fft.entity.Ad.class, "ad");
		
//		String str = apply(com.froad.fft.entity.AdPosition.class, "adPosition",com.froad.fft.dto.AdPositionDto.class, "adPositionDto");
//		String str = apply(com.froad.fft.dto.AdPositionDto.class, "adPositionDto",com.froad.fft.entity.AdPosition.class, "adPosition");
		
//		String str = apply(com.froad.fft.entity.Agreement.class, "agreement",com.froad.fft.dto.AgreementDto.class, "agreementDto");
//		String str = apply(com.froad.fft.dto.AgreementDto.class, "agreementDto",com.froad.fft.entity.Agreement.class, "agreement");
		
//		String str = apply(com.froad.fft.entity.Area.class, "area",com.froad.fft.dto.AreaDto.class, "areaDto");
//		String str = apply(com.froad.fft.dto.AreaDto.class, "areaDto",com.froad.fft.entity.Area.class, "area");
		
//		String str = apply(com.froad.fft.entity.ArticleCategory.class, "articleCategory",com.froad.fft.dto.ArticleCategoryDto.class, "articleCategoryDto");
//		String str = apply(com.froad.fft.dto.ArticleCategoryDto.class, "articleCategoryDto",com.froad.fft.entity.ArticleCategory.class, "articleCategory");
		
//		String str = apply(com.froad.fft.entity.Article.class, "article",com.froad.fft.dto.ArticleDto.class, "articleDto");
//		String str = apply(com.froad.fft.dto.ArticleDto.class, "articleDto",com.froad.fft.entity.Article.class, "article");
		
//		String str = apply(com.froad.fft.entity.FriendLink.class, "friendLink",com.froad.fft.dto.FriendLinkDto.class, "friendLinkDto");
//		String str = apply(com.froad.fft.dto.FriendLinkDto.class, "friendLinkDto",com.froad.fft.entity.FriendLink.class, "friendLink");
		
//		String str = apply(com.froad.fft.entity.Log.class, "log",com.froad.fft.dto.LogDto.class, "logDto");
//		String str = apply(com.froad.fft.dto.LogDto.class, "logDto",com.froad.fft.entity.Log.class, "log");
		
//		String str = apply(com.froad.fft.entity.MerchantCategory.class, "merchantCategory",com.froad.fft.dto.MerchantCategoryDto.class, "merchantCategoryDto");
//		String str = apply(com.froad.fft.dto.MerchantCategoryDto.class, "merchantCategoryDto",com.froad.fft.entity.MerchantCategory.class, "merchantCategory");
		
//		String str = apply(com.froad.fft.entity.MerchantComment.class, "merchantComment",com.froad.fft.dto.MerchantCommentDto.class, "merchantCommentDto");
//		String str = apply(com.froad.fft.dto.MerchantCommentDto.class, "merchantCommentDto",com.froad.fft.entity.MerchantComment.class, "merchantComment");
		
//		String str = apply(com.froad.fft.entity.Merchant.class, "merchant",com.froad.fft.dto.MerchantDto.class, "merchantDto");
//		String str = apply(com.froad.fft.dto.MerchantDto.class, "merchantDto",com.froad.fft.entity.Merchant.class, "merchant");
		
		
//		String str = apply(com.froad.fft.entity.MerchantOnlineApply.class, "merchantOnlineApply",com.froad.fft.dto.MerchantOnlineApplyDto.class, "merchantOnlineApplyDto");
//		String str = apply(com.froad.fft.dto.MerchantOnlineApplyDto.class, "merchantOnlineApplyDto",com.froad.fft.entity.MerchantOnlineApply.class, "merchantOnlineApply");
		
//		String str = apply(com.froad.fft.entity.MerchantOutlet.class, "merchantOutlet",com.froad.fft.dto.MerchantOutletDto.class, "merchantOutletDto");
//		String str = apply(com.froad.fft.dto.MerchantOutletDto.class, "merchantOutletDto",com.froad.fft.entity.MerchantOutlet.class, "merchantOutlet");
//		
//		String str = apply(com.froad.fft.entity.MerchantPhoto.class, "merchantPhoto",com.froad.fft.dto.MerchantPhotoDto.class, "merchantPhotoDto");
//		String str = apply(com.froad.fft.dto.MerchantPhotoDto.class, "merchantPhotoDto",com.froad.fft.entity.MerchantPhoto.class, "merchantPhoto");
//		
//		String str = apply(com.froad.fft.entity.MerchantPreferential.class, "merchantPreferential",com.froad.fft.dto.MerchantPreferentialDto.class, "merchantPreferentialDto");
//		String str = apply(com.froad.fft.dto.MerchantPreferentialDto.class, "merchantPreferentialDto",com.froad.fft.entity.MerchantPreferential.class, "merchantPreferential");
//		
//		String str = apply(com.froad.fft.entity.MerchantPresent.class, "merchantPresent",com.froad.fft.dto.MerchantPresentDto.class, "merchantPresentDto");
//		String str = apply(com.froad.fft.dto.MerchantPresentDto.class, "merchantPresentDto",com.froad.fft.entity.MerchantPresent.class, "merchantPresent");
//		
//		String str = apply(com.froad.fft.entity.Navigation.class, "navigation",com.froad.fft.dto.NavigationDto.class, "navigationDto");
//		String str = apply(com.froad.fft.dto.NavigationDto.class, "navigationDto",com.froad.fft.entity.Navigation.class, "navigation");
//		
//		String str = apply(com.froad.fft.entity.ProductAttribute.class, "productAttribute",com.froad.fft.dto.ProductAttributeDto.class, "productAttributeDto");
//		String str = apply(com.froad.fft.dto.ProductAttributeDto.class, "productAttributeDto",com.froad.fft.entity.ProductAttribute.class, "productAttribute");
//		
//		String str = apply(com.froad.fft.entity.ProductCategory.class, "productCategory",com.froad.fft.dto.ProductCategoryDto.class, "productCategoryDto");
//		String str = apply(com.froad.fft.dto.ProductCategoryDto.class, "productCategoryDto",com.froad.fft.entity.ProductCategory.class, "productCategory");
//		
//		String str = apply(com.froad.fft.entity.Product.class, "product",com.froad.fft.dto.ProductDto.class, "productDto");
//		String str = apply(com.froad.fft.dto.ProductDto.class, "productDto",com.froad.fft.entity.Product.class, "product");
//		
//		String str = apply(com.froad.fft.entity.ProductGroup.class, "productGroup",com.froad.fft.dto.ProductGroupDto.class, "productGroupDto");
//		String str = apply(com.froad.fft.dto.ProductGroupDto.class, "productGroupDto",com.froad.fft.entity.ProductGroup.class, "productGroup");
//		
//		String str = apply(com.froad.fft.entity.ProductPresell.class, "productPresell",com.froad.fft.dto.ProductPresellDto.class, "productPresellDto");
//		String str = apply(com.froad.fft.dto.ProductPresellDto.class, "productPresellDto",com.froad.fft.entity.ProductPresell.class, "productPresell");
//		
//		String str = apply(com.froad.fft.entity.PresellDelivery.class, "presellDelivery",com.froad.fft.dto.PresellDeliveryDto.class, "presellDeliveryDto");
//		String str = apply(com.froad.fft.dto.PresellDeliveryDto.class, "presellDeliveryDto",com.froad.fft.entity.PresellDelivery.class, "presellDelivery");
//		
		
//		String str = apply(com.froad.fft.entity.ProductImage.class, "productImage",com.froad.fft.dto.ProductImageDto.class, "productImageDto");
//		String str = apply(com.froad.fft.dto.ProductImageDto.class, "productImageDto",com.froad.fft.entity.ProductImage.class, "productImage");
////		
//		String str = apply(com.froad.fft.entity.ProductType.class, "productType",com.froad.fft.dto.ProductTypeDto.class, "productTypeDto");
//		String str = apply(com.froad.fft.dto.ProductTypeDto.class, "productTypeDto",com.froad.fft.entity.ProductType.class, "productType");
////		
//		String str = apply(com.froad.fft.entity.SellerChannel.class, "sellerChannel",com.froad.fft.dto.SellerChannelDto.class, "sellerChannelDto");
//		String str = apply(com.froad.fft.dto.SellerChannelDto.class, "sellerChannelDto",com.froad.fft.entity.SellerChannel.class, "sellerChannel");
////		
//		String str = apply(com.froad.fft.entity.Seller.class, "seller",com.froad.fft.dto.SellerDto.class, "sellerDto");
//		String str = apply(com.froad.fft.dto.SellerDto.class, "sellerDto",com.froad.fft.entity.Seller.class, "seller");
////		
//		String str = apply(com.froad.fft.entity.SellerRule.class, "sellerRule",com.froad.fft.dto.SellerRuleDto.class, "sellerRuleDto");
//		String str = apply(com.froad.fft.dto.SellerRuleDto.class, "sellerRuleDto",com.froad.fft.entity.SellerRule.class, "sellerRule");
////		
//		String str = apply(com.froad.fft.entity.SmsContent.class, "smsContent",com.froad.fft.dto.SmsContentDto.class, "smsContentDto");
//		String str = apply(com.froad.fft.dto.SmsContentDto.class, "smsContentDto",com.froad.fft.entity.SmsContent.class, "smsContent");
////		
//		String str = apply(com.froad.fft.entity.SmsLog.class, "smsLog",com.froad.fft.dto.SmsLogDto.class, "smsLogDto");
//		String str = apply(com.froad.fft.dto.SmsLogDto.class, "smsLogDto",com.froad.fft.entity.SmsLog.class, "smsLog");
////		
		
//		String str = apply(com.froad.fft.entity.SysResource.class, "sysResource",com.froad.fft.dto.SysResourceDto.class, "sysResourceDto");
//		String str = apply(com.froad.fft.dto.SysResourceDto.class, "sysResourceDto",com.froad.fft.entity.SysResource.class, "sysResource");
////	
//		String str = apply(com.froad.fft.entity.SysRole.class, "sysRole",com.froad.fft.dto.SysRoleDto.class, "sysRoleDto");
//		String str = apply(com.froad.fft.dto.SysRoleDto.class, "sysRoleDto",com.froad.fft.entity.SysRole.class, "sysRole");
////	
//		String str = apply(com.froad.fft.entity.SysUser.class, "sysUser",com.froad.fft.dto.SysUserDto.class, "sysUserDto");
//		String str = apply(com.froad.fft.dto.SysUserDto.class, "sysUserDto",com.froad.fft.entity.SysUser.class, "sysUser");
////	
//		String str = apply(com.froad.fft.bean.SystemConfig.class, "systemConfig",com.froad.fft.dto.SystemConfigDto.class, "systemConfigDto");
//		String str = apply(com.froad.fft.dto.SystemConfigDto.class, "systemConfigDto",com.froad.fft.bean.SystemConfig.class, "systemConfig");
////	
//		String str = apply(com.froad.fft.entity.Tag.class, "tag",com.froad.fft.dto.TagDto.class, "tagDto");
//		String str = apply(com.froad.fft.dto.TagDto.class, "tagDto",com.froad.fft.entity.Tag.class, "tag");
////	
//		String str = apply(com.froad.fft.bean.page.Page.class, "page",com.froad.fft.bean.page.PageDto.class, "pageDto");
//		String str = apply(com.froad.fft.bean.page.PageDto.class, "pageDto",com.froad.fft.bean.page.Page.class, "page");
////	
		
//		System.out.println(str);
	}
}
