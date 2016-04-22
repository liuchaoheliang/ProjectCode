package com.froad.db.mongo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.froad.Constants;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.ProductMapper;
import com.froad.logback.LogCvt;
import com.froad.po.BankAudit;
import com.froad.po.ProductOutletInfo;
import com.froad.po.mongo.ProductDetail;
import com.froad.po.mongo.ProductOutlet;
import com.froad.util.Checker;
import com.froad.util.MongoTableName;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class AuditMongo {
	
	private static MongoManager manager = new MongoManager();
	
	
	/**
	 * 审核商品/商户mongo操作
	 * @Title: audit 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年4月27日
	 * @modify: froad-huangyihao 2015年4月27日
	 * @param auditFlag	审核标志 1-商品 2-商户
	 * @param audit	审核对象
	 * @return
	 * @throws
	 */
	public static Boolean audit(int auditFlag, BankAudit audit){
		boolean result = false;
		switch (auditFlag) {
		case 1://商品
			if(Checker.isNotEmpty(audit.getIsMarketable()) 
					&& Checker.isNotEmpty(audit.getRackTime())){
				result = auditProduct(audit);
//				attachAreaProduct(audit.getAuditId());
			}else{
				result=true;
			}
			break;
		case 2://商户
			if(Checker.isNotEmpty(audit.getIsEnable())){
				result = auditMerchant(audit);
			}else{
				result=true;
			}
			break;
		case 3://秒杀商品
			if(Checker.isNotEmpty(audit.getIsMarketable())){
				result = auditProductIsSeckill(audit);
			}else{
				result=true;
			}
			break;
		default:
			return false;
		}
		return result;
	}
	
	/**
	 * 更新商户详情表信息
	 * @Title: auditMerchant 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年4月27日
	 * @modify: froad-huangyihao 2015年4月27日
	 * @param audit
	 * @return
	 * @throws
	 */
	private static Boolean auditMerchant(BankAudit audit){
		DBObject where = new BasicDBObject();
		where.put("_id", audit.getAuditId());
		DBObject value = new BasicDBObject();
		value.put("is_enable", audit.getIsEnable());
		int result = manager.update(value, where, MongoTableName.CB_MERCHANT_DETAIL, "$set");
		return result != -1;
	}
	
	/**
	 * 更新商品详情表信息
	 * @Title: auditProduct 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年4月27日
	 * @modify: froad-huangyihao 2015年4月27日
	 * @param audit
	 * @return
	 * @throws
	 */
	private static Boolean auditProduct(BankAudit audit){
		DBObject where = new BasicDBObject();
		where.put("_id", audit.getAuditId());
		DBObject value = new BasicDBObject();
		value.put("is_marketable", audit.getIsMarketable());
		value.put("rack_time", audit.getRackTime().getTime()); //上架时间存long类型  ll add  20151019 
		int result = manager.update(value, where, MongoTableName.CB_PRODUCT_DETAIL, "$set");
		return result != -1;
	}
	
	/**
	 * 更新商品详情表信息
	 * @Title: auditProduct 
	 * @Description: TODO
	 * @author: wangzhangxu 2015年6月1日
	 * @param audit
	 * @return
	 * @throws
	 */
	private static Boolean auditProductIsSeckill(BankAudit audit){
		DBObject where = new BasicDBObject();
		where.put("_id", audit.getAuditId());
		DBObject value = new BasicDBObject();
		value.put("is_seckill", audit.getIsSeckill());
		int result = manager.update(value, where, MongoTableName.CB_PRODUCT_DETAIL, "$set");
		return result != -1;
	}
	
	
	/**
	 * 将商品和区域关联关系关联上（商品上架是需要）
	 * @param productId 商品Id
	 */
//    public static void attachAreaProduct(String productId) {
//        if(Checker.isEmpty(productId)){
//            return;
//        }
//        SqlSession sqlSession = null;
//        ProductMapper mapper = null;
//        try { 
//            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
//            mapper = sqlSession.getMapper(ProductMapper.class);
//            
//            DBObject where = new BasicDBObject();
//            where.put("_id", productId);
//            ProductDetail pd = manager.findOne(where, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
//            //维护商品和区域的关联关系
//            //指定商品类型可以根据区域查询逗号分隔比如1,2,3,4,5
//            if(pd!=null && isExist(pd.getProductType(),Constants.GOODS_AREA_PRODUCT_PRODUCT_TYPE)){
//                Map<Long,Long> areaIds = getAreasByOutletids(pd,mapper);
//                if(areaIds!=null && areaIds.size()>0){
//                    addDeleteProductAreas(areaIds,pd.getClientId(),pd.getId());//将商品和区域关系关联上
//                }
//            }
//        } catch (Exception e) { 
//            LogCvt.error("将商品和区域关联关系关联上（商品上架是需要）异常，原因:" + e.getMessage(),e); 
//        } finally { 
//            if(null != sqlSession)  
//                sqlSession.close();  
//        } 
//    }
    
    /**
     * value在constantsStr中是否存在.
     * @param value
     * @param constantsStr
     * @return
     * @return Boolean
     */
    private static Boolean isExist(String value,String constantsStr){
        if(constantsStr!=null && !"".equals(constantsStr) && value!=null && !"".equals(value)){
            String[] strs = constantsStr.split(",");
            if(strs!=null && strs.length>0){
                for(String str : strs){
                    if(value.equals(str)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    /**
     * 将商品和区域关联上
     * @param areaIds 区域id Map<Long,Long> Map<areaId,area_parent_id>
     * @param clientId 客户端
     * @param productId 商品id
     * @return void
     */
//    private static void addDeleteProductAreas(Map<Long,Long> areaIds,String clientId,String productId){
//        if(areaIds!=null && areaIds.size()>0){
//            DBObject pushObj = new BasicDBObject();
//            pushObj.put("product_ids", productId);
//            
//            DBObject whereObj = new BasicDBObject();
//            whereObj.put("client_id", clientId);
//            
//            //加区域关联
//            AreaProducts areaProducts = null;
//            for(Long areaId : areaIds.keySet()){
//                whereObj.put("area_id", areaId);
//                
//                areaProducts = manager.findOne(whereObj, MongoTableName.CB_AREA_PRODUCTS, AreaProducts.class);//商品和area没有关联关系就新加
//                if(areaProducts==null){//area_id没有的
//                    areaProducts = new AreaProducts();
//                    areaProducts.setAreaId(areaId);
//                    areaProducts.setAreaParentId(areaIds.get(areaId));
//                    areaProducts.setClientId(clientId);
//                    List<String> productIds = new ArrayList<String>();
//                    productIds.add(productId);
//                    areaProducts.setProductIds(productIds);
//                    manager.add(areaProducts, MongoTableName.CB_AREA_PRODUCTS);//加区域关联
//                } else {
//                    //确保area里productIds放唯一productId
//                	manager.update(pushObj, whereObj, MongoTableName.CB_AREA_PRODUCTS, "$pull", true, false);//商品和area有关联关系就pull
//                	manager.update(pushObj, whereObj, MongoTableName.CB_AREA_PRODUCTS, "$push", true, false);//商品和area有关联关系就push进去
//                    
//                    if(areaProducts.getAreaParentId()==null && areaIds.get(areaId)!=null){
//                        DBObject value = new BasicDBObject();
//                        value.put("area_parent_id", areaIds.get(areaId));
//                        manager.update(value, whereObj, MongoTableName.CB_AREA_PRODUCTS, "$set");//更新cityId
//                    }
//                    
//                }
//            }
//                
//        }
//    }
    
    /**
     * 根据门店获取对应的区域
     * @param pd
     * @param productMapper
     * @return Map<Long,Long>
     */
    private static Map<Long,Long> getAreasByOutletids(ProductDetail pd,ProductMapper productMapper){
        
        Map<Long,Long> areaIds = new HashMap<Long,Long>();
        
        //根据门店id获取对应的区id
        List<ProductOutlet> productOutlets = pd.getOutletInfo();
        if(productOutlets!=null && productOutlets.size()>0){
            int num = 0;
            
            int pageNum = productOutlets.size() / 20;
            if (productOutlets.size() % 20 > 0) {
                pageNum += 1;
            }
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("clientId", pd.getClientId());
            
            List<ProductOutletInfo> productOutletIfos = null;
            List<String> outletIds = null;
            for (int i = 1; i <= pageNum; i++) {
                outletIds = new ArrayList<String>();
                if (i == pageNum && productOutlets.size() % 20 > 0) {
                    num = 20 * (i - 1) + productOutlets.size()% 20;
                } else {
                    num = 20 * i;
                }
                for (int j = (i - 1) * 20; j < num; j++) {
                    outletIds.add(productOutlets.get(j).getOutletId());
                }
                if(outletIds.size()>0){
                    param.put("outletIds", outletIds);
                    productOutletIfos = productMapper.findOutletAreas(param);
                    
                    if(productOutletIfos!=null && productOutletIfos.size()>0){
                        for(ProductOutletInfo poi : productOutletIfos){
                            if(poi.getAreaId()>0){
                                areaIds.put(poi.getAreaId(), poi.getAreaParentId());
                            }
                        }
                    } 
                }
            }
        }
        return areaIds;
    }
    
    
    
}
