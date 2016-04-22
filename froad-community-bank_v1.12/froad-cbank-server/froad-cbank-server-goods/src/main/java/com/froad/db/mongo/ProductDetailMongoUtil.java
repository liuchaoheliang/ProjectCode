package com.froad.db.mongo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.froad.logback.LogCvt;
import com.froad.logic.CommonLogic;
import com.froad.logic.impl.CommonLogicImpl;
import com.froad.po.Area;
import com.froad.po.Outlet;
import com.froad.po.mongo.ProductArea;
import com.froad.po.mongo.ProductCityArea;
import com.froad.po.mongo.ProductCityOutlet;
import com.froad.po.mongo.ProductDetail;
import com.froad.po.mongo.ProductOutlet;
import com.froad.util.Checker;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class ProductDetailMongoUtil {
	
	public static DBObject unsetProductDetail(ProductDetail productDetail) {
		boolean isUnset = false;
        DBObject w1 = new BasicDBObject();
        if(productDetail.getBuyLimit()==null){
            //删除buy_limit这个字段值
            w1.put("buy_limit", 1);
            isUnset = true;
        }
        if(productDetail.getCityAreas()==null || productDetail.getCityAreas().size()==0){//市级-区关系
            //删除city_areas这个字段值
            w1.put("city_areas", 1);
            isUnset = true;
        }
        if(productDetail.getOrgOutlets()==null || productDetail.getOrgOutlets().size()==0){//市级-门店关系
        	//删除org_outlets这个字段值
            w1.put("org_outlets", 1);
            isUnset = true;
        }
        if(productDetail.getOrgCodes()==null || productDetail.getOrgCodes().size()==0){//商品对应网店所属的机构代码列表
        	//删除org_codes这个字段值
            w1.put("org_codes", 1);
            isUnset = true;
        }
        if(productDetail.getActivitiesInfo()==null || productDetail.getActivitiesInfo().size()==0){//商品活动
        	//删除activities_info这个字段值
            w1.put("activities_info", 1);
            isUnset = true;
        }
        if(productDetail.getProductCategoryInfo()==null || productDetail.getProductCategoryInfo().size()==0){// 商品分类
        	//删除product_category_info这个字段值
            w1.put("product_category_info", 1);
            isUnset = true;
        }
        if(isUnset){
        	DBObject valueObj = new BasicDBObject();
        	valueObj.put("$unset", w1);
        	return valueObj;
        }
        return null;
	}
	
	/**
     * 将团购商品所属商户下门店所在地区和商品关联
     * @param pd
     * @param outlets
     * @param areaMap
     */
    public static ProductDetail setAreaOutletToGroupProduct(ProductDetail pd,List<Outlet> outlets,Map<Long,Area> areaMap){
        long startTime = System.currentTimeMillis();
        
        //根据门店id获取对应的区id
        CommonLogic comLogic = new CommonLogicImpl();
        if(areaMap==null){
        	areaMap = new HashMap<Long,Area>();
        }
        
        Map<Long,List<ProductArea>> countys = new HashMap<Long,List<ProductArea>>();//城市下的区
        Map<Long,List<ProductOutlet>> pcosMap = new HashMap<Long,List<ProductOutlet>>();//城市下的门店
        
    	Area area = null;
    	Long cityId = null;
        Long areaId = null;
        Set<Long> areaIds = new HashSet<Long>();
        for (Outlet outlet : outlets) {
        	if(outlet.getAreaId()!=null && outlet.getAreaId()>0){
        		cityId = null;
                areaId = null;
                
                area = areaMap.get(outlet.getAreaId());
                if(area==null){
                	area = comLogic.findAreaById(outlet.getAreaId());
                }
                if(area!=null && areaMap.get(area.getId())==null){
        			areaMap.put(area.getId(),area);
        		}
                if (area!=null && Checker.isNotEmpty(area.getTreePath())) {
                    String[] treePtah = area.getTreePath().split(",");
                    if(treePtah.length==3){//门店所属的areaId为区
                        areaId = Long.valueOf(treePtah[2]);
                        cityId = Long.valueOf(treePtah[1]);
                    } else if(treePtah.length==2){//门店所属的areaId为市
                        cityId = Long.valueOf(treePtah[1]);
                    }
                    if(!areaIds.contains(outlet.getAreaId())) {//门店所属的areaId没有计算过的需要计算该区对应的市
                        areaIds.add(outlet.getAreaId());
                        if(areaId!=null){//门店所属的areaId为区
                            if(countys.get(cityId)==null){
                                countys.put(cityId, new ArrayList<ProductArea>());
                            }
                            ProductArea pa = new ProductArea();
                            pa.setAreaId(outlet.getAreaId());
                            pa.setAreaName(area.getName());
                            countys.get(cityId).add(pa);
                        }
                    }
                    if(cityId!=null){
                        if(pcosMap.get(cityId)==null){
                            pcosMap.put(cityId, new ArrayList<ProductOutlet>());
                        }
                        ProductOutlet pco = new ProductOutlet();
                        pco.setAddress(outlet.getAddress());
                        pco.setOutletId(outlet.getOutletId());
                        pco.setOutletName(outlet.getOutletName());
                        pco.setPhone(outlet.getPhone());
                        pco.setAreaId(outlet.getAreaId());
                        pcosMap.get(cityId).add(pco);
                    }
                }// end if (Checker.isNotEmpty(area.getTreePath())) {
                
        	}// end if(outlet.getAreaId()!=null && outlet.getAreaId()>0){
        }// end for (Outlet outlet : outlets) {
        
        //城市下的区
        List<ProductCityArea> cityAreas = new ArrayList<ProductCityArea>();
        for(Long pacityId : countys.keySet()){
            ProductCityArea pca = new ProductCityArea();
            pca.setCityId(pacityId);
            area = areaMap.get(pacityId);
            if(area==null){
            	area = comLogic.findAreaById(pacityId);
        		if(area!=null){
        			areaMap.put(area.getId(),area);
        		}
        	}
            if(area!=null){
                pca.setCityName(area.getName());
            }
            pca.setCountys(countys.get(pacityId));
            cityAreas.add(pca);
        }
        if(cityAreas.size()>0){
            pd.setCityAreas(cityAreas);
        }
        
        //城市下的门店
        List<ProductCityOutlet> orgOutlets = new ArrayList<ProductCityOutlet>();
        for(Long pcityId : pcosMap.keySet()){
            ProductCityOutlet pco= new ProductCityOutlet();
            pco.setCityId(pcityId);
            area = areaMap.get(pcityId);
            if(area==null){
            	area = comLogic.findAreaById(pcityId);
        		if(area!=null){
        			areaMap.put(area.getId(),area);
        		}
        	}
            if(area!=null){
                pco.setCityName(area.getName());
            }
            pco.setOrgOutlets(pcosMap.get(pcityId));
            orgOutlets.add(pco);
        }
        if(orgOutlets.size()>0){
            pd.setOrgOutlets(orgOutlets);
        }
        long endTime = System.currentTimeMillis();
        LogCvt.debug("-------添加团购商品到mongo时设置商品的市级门店和市级区:耗时"+(endTime - startTime)+"--startTime:"+startTime+"---endTime:"+endTime);
        return pd;
    }

}
