package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.ibatis.session.SqlSession;

import com.froad.common.beans.ResultBean;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mongo.page.MongoPage;
import com.froad.db.mongo.page.OrderBy;
import com.froad.db.mongo.page.Sort;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.ProductMapper;
import com.froad.db.mysql.mapper.VipProductMapper;
import com.froad.db.redis.VipRedis;
import com.froad.db.redis.impl.RedisManager;
import com.froad.enums.ActivitiesStatus;
import com.froad.enums.ModuleID;
import com.froad.enums.ProductStatus;
import com.froad.enums.ProductType;
import com.froad.enums.ResultCode;
import com.froad.enums.VipStatus;
import com.froad.logback.LogCvt;
import com.froad.logic.CommonLogic;
import com.froad.logic.VipProductLogic;
import com.froad.po.ActivitiesInfo;
import com.froad.po.BindVipInfo;
import com.froad.po.Product;
import com.froad.po.Result;
import com.froad.po.VipBindProductInfo;
import com.froad.po.VipProduct;
import com.froad.po.mongo.ProductBuyLimit;
import com.froad.po.mongo.ProductDetail;
import com.froad.thrift.vo.PlatType;
import com.froad.util.Arith;
import com.froad.util.Checker;
import com.froad.util.GoodsConstants;
import com.froad.util.MongoTableName;
import com.froad.util.RedisKeyUtil;
import com.froad.util.SimpleID;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;
import com.alibaba.fastjson.JSON;

public class VipProductLogicImpl implements VipProductLogic {
    
    private static SimpleID simpleID = new SimpleID(ModuleID.vipProduct);
    
    private MongoManager manager = new MongoManager();
    private RedisManager redis = new RedisManager();
    
    /**
     * 新加VIP规则
     */
    @Override
    public ResultBean addVipProduct(VipProduct vipProduct) {
        // TODO Auto-generated method stub
        ResultBean resultBean = null;
        SqlSession sqlSession = null;
        
        try{
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            VipProductMapper vipProductMapper = sqlSession.getMapper(VipProductMapper.class);
            
            vipProduct.setVipId(simpleID.nextId());
            vipProduct.setCreateTime(new Date());
            vipProduct.setStatus(VipStatus.ineffect.getCode());
            vipProduct.setCount(0);
            
            //基础信息保存到mysql
            vipProductMapper.addVipProduct(vipProduct);
            sqlSession.commit(true);
            
            resultBean = new ResultBean(ResultCode.success,"添加VIP规则成功",vipProduct.getVipId());
                
        } catch (Exception e) { 
            if(null != sqlSession){
                sqlSession.rollback(true); 
            }
            resultBean = new ResultBean(ResultCode.failed,"添加VIP规则失败");
            LogCvt.error("添加VIP规则失败，原因:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            } 
        } 
        return resultBean;
    }
    
    /**
     * 删除VIP规则
     */
    @Override
    public Result deleteVipProduct(String vipId) {
        // TODO Auto-generated method stub
        Result result = new Result();
        result.setResultCode(ResultCode.failed.getCode());
        result.setResultDesc("删除VIP规则失败");
        
        SqlSession sqlSession = null;
        try{
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            VipProductMapper vipProductMapper = sqlSession.getMapper(VipProductMapper.class);
            
            //根据vipId查询VIP规则
            VipProduct vip = vipProductMapper.getVipProduct(vipId);
            if(vip!=null){
                if(VipStatus.ineffect.getCode().equals(vip.getStatus())){//只有未生效的才可以删除
                    
                    //解除所有商品绑定该VIP规则的绑定
                    boolean sqlSuccess = this.removeAllProductofVipFromVip(vip);
                    
                    vip.setStatus(VipStatus.deleted.getCode());
                    if(sqlSuccess){
                        vipProductMapper.updateVipStatus(vip);//更新mysql vip的状态为删除状态
                        sqlSession.commit(true);
                        result.setResultCode(ResultCode.success.getCode());
                        result.setResultDesc("删除VIP规则成功");
                    }
                }
            } else {
                result.setResultDesc("该VIP规则已经不存在了");
            }
        } catch (Exception e) { 
            if(null != sqlSession){
                sqlSession.rollback(true); 
            }
            LogCvt.error("删除VIP规则失败，原因:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            } 
        } 
        return result;
    }
    
    /**
     * 修改VIP规则
     */
    @Override
    public Result updateVipProduct(VipProduct vipProduct) {
        // TODO Auto-generated method stub
        Result result = new Result();
        result.setResultCode(ResultCode.failed.getCode());
        result.setResultDesc("修改VIP规则失败");
        
        SqlSession sqlSession = null;
        try{
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            VipProductMapper vipProductMapper = sqlSession.getMapper(VipProductMapper.class);
            
            //根据vipId查询VIP规则
            VipProduct vip = vipProductMapper.getVipProduct(vipProduct.getVipId());
            if(vip!=null){
                if(!VipStatus.canceled.getCode().equals(vip.getStatus())){//已作废不能修改
                    if((vipProduct.getActivitiesName()!=null && !"".equals(vipProduct.getActivitiesName())) 
                            || (vipProduct.getRemark()!=null && !"".equals(vipProduct.getRemark()))){
                        
                        if(vipProduct.getActivitiesName()!=null && !"".equals(vipProduct.getActivitiesName())) {
                            vip.setActivitiesName(vipProduct.getActivitiesName());
                        }
                        if(vipProduct.getRemark()!=null && !"".equals(vipProduct.getRemark())){
                            vip.setRemark(vipProduct.getRemark());
                        }
                        boolean redisFlag = false;
                        if(VipStatus.effect.getCode().equals(vip.getStatus())){//缓存里只有启用的
                            redisFlag = VipRedis.set_cbbank_vip_product_client_id(vip);//更新缓存
                        } else {
                            redisFlag = true;
                        }
                        if(redisFlag){//redis更新成功 再更新mysql
                            vipProductMapper.updateVipProduct(vip);
                            sqlSession.commit(true);
                            
                            result.setResultCode(ResultCode.success.getCode());
                            result.setResultDesc("修改VIP规则成功");
                        }
                    }
                } else {
                    result.setResultDesc("该VIP规则已作废不能修改");
                }
            } else {
                result.setResultDesc("该VIP规则已经不存在了");
            }
        } catch (Exception e) { 
            if(null != sqlSession){
                sqlSession.rollback(true); 
            }
            LogCvt.error("修改VIP规则失败，原因:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            } 
        } 
        return result;
    }
    
    /**
     * 根据客户端id查询单独一条启用的vip规则
     */
    @Override
    public VipProduct getVipProduct(String clientId) {
        // TODO Auto-generated method stub
        CommonLogic com = new CommonLogicImpl();
        Map<String,String> hash = com.getVipProductRedis(clientId);
        if(hash!=null && hash.size()>0){
            VipProduct vip = new VipProduct();
            vip.setVipId(hash.get("vip_id"));//VIP活动id
            if(hash.get("create_time")!=null){
                vip.setCreateTime(new Date(Long.valueOf(hash.get("create_time"))));//创建时间
            }
            vip.setVipPrice(Integer.valueOf(hash.get("vip_price")));//VIP价格
            vip.setStatus(hash.get("status"));//状态 0未生效,1已生效,2已作废
            vip.setActivitiesName(hash.get("activities_name"));//VIP规则名称
            vip.setRemark(hash.get("remark"));//VIP特权介绍
            vip.setCount(Integer.valueOf(hash.get("count")));//设置了vip价的商品总数
            vip.setClientId(clientId);//客户端id
            vip.setClientName(hash.get("client_name"));//客户端简称
            return vip;
        }
        
        return null;
    }
    
    /**
     * 根据VIP规则id查询vip规则详情
     */
    @Override
    public VipProduct getVipProductDetail(String vipId) {
        // TODO Auto-generated method stub
        SqlSession sqlSession = null;
        try{
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            VipProductMapper vipProductMapper = sqlSession.getMapper(VipProductMapper.class);
            
            //根据vipId查询VIP规则
            VipProduct vip = vipProductMapper.getVipProduct(vipId);
            return vip;
        } catch (Exception e) { 
            LogCvt.error("根据VIP规则id查询vip规则详情失败，原因:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            } 
        } 
        return null;
    }
    
    /**
     * vip规则分页查询
     */
    @Override
    public List<VipProduct> getVipProductsByPage(Page<VipProduct> page,
            VipProduct vipProduct) throws Exception {
        // TODO Auto-generated method stub
        List<VipProduct> vips = null;
        SqlSession sqlSession = null;
        try{
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            VipProductMapper vipProductMapper = sqlSession.getMapper(VipProductMapper.class);
            
            vips = vipProductMapper.findVipProductByPage(page, vipProduct);
            
        } catch (Exception e) { 
            LogCvt.error("根据VIP规则id查询vip规则详情失败，原因:" + e.getMessage(),e); 
            throw new Exception();
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            } 
        } 
        return vips;
    }
    
    /**
     * 获取某条VIP已经绑定的商品列表
     */
    @Override
    public List<ProductDetail> getProductsOfVipByPage(Page<VipBindProductInfo> page,
            String vipId) throws Exception{
        // TODO Auto-generated method stub
        MongoPage mPage = new MongoPage(page.getPageNumber(), page.getPageSize());
        SqlSession sqlSession = null;
        try{
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            VipProductMapper vipProductMapper = sqlSession.getMapper(VipProductMapper.class);
            
            //根据vipId查询VIP规则
            VipProduct vip = vipProductMapper.getVipProduct(vipId);
            if(vip!=null){
                if(VipStatus.effect.getCode().equals(vip.getStatus())){//已经生效的
                    DBObject dbObject = new BasicDBObject();
                    dbObject.put("buy_limit.id", vip.getId());
                    mPage.setSort(new Sort("start_time", OrderBy.DESC));
                    mPage = manager.findByPage(mPage, dbObject, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
                    List<ProductDetail> pds = (List<ProductDetail>)mPage.getItems();
                    page.setPageCount(mPage.getPageCount());
                    page.setTotalCount(mPage.getTotalCount());
                    return pds;
                } else if(VipStatus.ineffect.getCode().equals(vip.getStatus())){//未生效的
                    List<VipBindProductInfo> vipBindProductInfos = vipProductMapper.findVipBindProductsByPage(page, vipId);
                    if(vipBindProductInfos!=null && vipBindProductInfos.size()>0){
                        List<String> pids = new ArrayList<String>();
                        Map<String,VipBindProductInfo> vipBindMap = new HashMap<String,VipBindProductInfo>();
                                
                        for(VipBindProductInfo vipBindProductInfo : vipBindProductInfos){
                            pids.add(vipBindProductInfo.getProductId());
                            vipBindMap.put(vipBindProductInfo.getProductId(), vipBindProductInfo);
                        }
                        DBObject whereObj = new BasicDBObject();
                        whereObj.put("_id", new BasicDBObject("$in", pids));
                        List<ProductDetail> productDetails = (List<ProductDetail>)manager.findAll(whereObj, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
                        List<ProductDetail> pds = new ArrayList<ProductDetail>();
                        if(productDetails!=null && productDetails.size()>0){
                            for(String productId : pids){
                                for(ProductDetail productDetail:productDetails){
                                    if(productId.equals(productDetail.getId())){
                                        productDetail.setVipPrice(vipBindMap.get(productId).getVipPrice());
                                        if(productDetail.getBuyLimit()!=null){
                                            productDetail.getBuyLimit().setMaxVip(vipBindMap.get(productId).getVipLimit());
                                        } else {
                                            ProductBuyLimit buyLimit = new ProductBuyLimit();
                                            buyLimit.setMaxVip(vipBindMap.get(productId).getVipLimit());
                                            productDetail.setBuyLimit(buyLimit);
                                        }
                                        pds.add(productDetail);
                                    }
                                }
                            }
                        }
                        return pds;
                    }
                }
            }
        } catch (Exception e) { 
            LogCvt.error("获取某条VIP已经绑定的商品列表失败，原因:" + e.getMessage(),e); 
            throw new Exception();
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            } 
        } 
        return null;
    }
    
    /**
     * VIP规则绑定新商品
     */
    @Override
    public Result addProductsToVipProduct(String vipId,
            List<BindVipInfo> bindInfos) {
        // TODO Auto-generated method stub
        Result result = new Result();
        result.setResultCode(ResultCode.failed.getCode());
        result.setResultDesc("VIP规则绑定新商品失败");
        
        SqlSession sqlSession = null;
        try{
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            VipProductMapper vipProductMapper = sqlSession.getMapper(VipProductMapper.class);
            
            //根据vipId查询VIP规则
            VipProduct vip = vipProductMapper.getVipProduct(vipId);
            if(vip==null){
                result.setResultDesc("该VIP规则已经不存在了");
                return result;
            }
            boolean isValid = false;//只有未启用和启用的VIP规则才能绑定商品,已作废VIP规则不能绑定商品
            if(vip.getStatus()!=null
                    && (VipStatus.ineffect.getCode().equals(vip.getStatus())
                            || VipStatus.effect.getCode().equals(vip.getStatus()))){
                isValid = true;
            }
            if(!isValid){
                result.setResultDesc("只有未启用和启用的VIP规则才能绑定商品");
                return result;
            }
            Map<String,Object> param = validContition();//组装符合绑定VIP规则的条件
            List<String> pIds = new ArrayList<String>();
            Map<String,BindVipInfo> bindInfoMap = new HashMap<String,BindVipInfo>();
            for(BindVipInfo bindInfo : bindInfos){
                pIds.add(bindInfo.getProductId());
                bindInfoMap.put(bindInfo.getProductId(), bindInfo);
            }
            param.put("productIds", pIds);
            param.put("clientId", vip.getClientId());
            
            List<String> productIds = vipProductMapper.findValidProductsForVip(param);//已添加过VIP的商品将不会被展示，即一个商品只允许添加一个VIP商品规则
            if(productIds!=null && productIds.size()>0){
                DBObject whereObj = new BasicDBObject();
                whereObj.put("client_id", vip.getClientId());
                whereObj.put("_id", new BasicDBObject("$in", productIds));
                
                List<ProductDetail> pds = (List<ProductDetail>)manager.findAll(whereObj, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
                if(pds!=null && pds.size()>0){
                    List<VipBindProductInfo> vipBindProductInfos = new ArrayList<VipBindProductInfo>();
                    VipBindProductInfo vipBindProductInfo = null;
                    BindVipInfo bindInfo = null;
                    for(ProductDetail pd : pds){
                        bindInfo = bindInfoMap.get(pd.getId());
                        if(bindInfo.getVipPrice().intValue()>pd.getPrice().intValue()){
                            result.setResultDesc(pd.getName()+"的VIP价格不能大于销售价");
                            return result;
                        }
                        if(pd.getBuyLimit()!=null && pd.getBuyLimit().getMax()>0 
                                && bindInfo.getVipLimit()!=null && bindInfo.getVipLimit()>0
                                && bindInfo.getVipLimit()>pd.getBuyLimit().getMax()){
                            result.setResultDesc(pd.getName()+"的普通限购数量为"+pd.getBuyLimit().getMax()+",VIP限购数量不能大于普通限购数量");
                            return result;
                        }
                        vipBindProductInfo = new VipBindProductInfo();
                        vipBindProductInfo.setCreateTime(pd.getCreateTime());
                        vipBindProductInfo.setProductId(pd.getId());
                        vipBindProductInfo.setVipId(vipId);
                        vipBindProductInfo.setVipPrice(bindInfo.getVipPrice());
                        vipBindProductInfo.setVipLimit(bindInfo.getVipLimit());
                        vipBindProductInfos.add(vipBindProductInfo);
                    }
                    if(vipBindProductInfos.size()>0){
                        //放入中间表
                        vipProductMapper.addVipBindProducts(vipBindProductInfos);
                        
                        //更新Vip已经绑定商品数量
                        VipProduct vipProduct = new VipProduct();
                        vipProduct.setVipId(vipId);
                        vipProduct.setCount(vipBindProductInfos.size());
                        vipProductMapper.updateVipCount(vipProduct);
                        
                        if(VipStatus.effect.getCode().equals(vip.getStatus())){//已经生效的VIP规则需要立刻绑定到商品表中
                            int count = attachProductsToVipProduct(vip, vipBindProductInfos, pds, vipProductMapper);
                            
                            //更新 redis count
                            String key = RedisKeyUtil.cbbank_vip_product_client_id(vip.getClientId());
                            Map<String, String> hash = redis.getMap(key);
                            if(hash!=null && hash.size()>0){
                                hash.put("count", ObjectUtils.toString(vipProduct.getCount()));
                                redis.putMap(key, hash);
                            }
                            
                            LogCvt.error("VIP规则绑定新商品，count:" + count+",中间表数量:"+vipBindProductInfos.size()); 
                        }
                        sqlSession.commit(true);
                    }
                }
                result.setResultCode(ResultCode.success.getCode());
                result.setResultDesc("VIP规则绑定商品成功");
            } else {
                result.setResultCode(ResultCode.failed.getCode());
                result.setResultDesc("没有满足绑定VIP规则条件的商品");
            }
        } catch (Exception e) { 
            if(null != sqlSession){
                sqlSession.rollback(true); 
            }
            LogCvt.error("VIP规则绑定新商品失败，原因:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            } 
        } 
        return result;
    }
    
    /**
     * 组装符合绑定VIP规则的条件
     * @return Map<String,Object>
     */
    private Map<String,Object> validContition(){
        Map<String,Object> param = new HashMap<String,Object>();
        //获取可以绑定VIP规则的指定商品类型
        List<String> types = getProductTypeVip();
        //获取指定平台新加的商品可以绑定VIP规则
        List<String> platTypes = getPlatTypeVip();
        //获取指定商品状态可以绑定VIP规则
        List<String> productStatuss = getProductStatusVip();
        
        if(productStatuss!=null && productStatuss.size()>0){//未上架的才可以绑定活动
            if(productStatuss.size()==1){
                param.put("isMarketable", productStatuss.get(0));
            } else {
                param.put("isMarketables", productStatuss);
            }
        } else {
            param.put("isMarketable", ProductStatus.noShelf.getCode());//未上架的才可以绑定活动
        }
   
        if(types!=null && types.size()>0){//VIP活动只支持精品预售中的商品
            if(types.size()==1){
                param.put("type", types.get(0));
            } else if(types.size()>1){
                param.put("types", types);
            }
        } else {
            param.put("type", ProductType.presell.getCode());
        }
        if(platTypes!=null && platTypes.size()>0){//通过BOSS系统录入或通过银行管理端录入的预售商品均可被展示及绑定
            if(platTypes.size()==1){
                param.put("platType", platTypes.get(0));
                
            } else {
                param.put("platTypes", platTypes);
            }
        } else {
            platTypes = new ArrayList<String>();
            platTypes.add(PlatType.boss.getValue()+"");
            platTypes.add(PlatType.bank.getValue()+"");
            param.put("platTypes", platTypes);
        }
        return param;
    }
    
    /**
     * 直接绑定到商品表上
     * @param vip
     * @param vipBindProductInfos
     * @param pds
     * @param vipProductMapper
     * @return int
     */
    private int attachProductsToVipProduct(VipProduct vip,List<VipBindProductInfo> vipBindProductInfos,List<ProductDetail> pds,VipProductMapper vipProductMapper) {
        int count = 0;
        DBObject whereObj = new BasicDBObject();
        DBObject valueObj = null;
        ProductBuyLimit buyLimit = null;
        Map<String,Object> param = new HashMap<String,Object>();
        
        Map<String,ProductDetail> pdsMap = new HashMap<String,ProductDetail>();
        for(ProductDetail pd : pds){
            pdsMap.put(pd.getId(), pd);
        }
        
        for(VipBindProductInfo vipBindProductInfo : vipBindProductInfos){
            ProductDetail pd = pdsMap.get(vipBindProductInfo.getProductId());
            whereObj.put("_id", vipBindProductInfo.getProductId());
            
            valueObj = new BasicDBObject();
            //vip_price字段设置值
            valueObj.put("vip_price", vipBindProductInfo.getVipPrice());
            //buy_limit字段设置
            if(pd.getBuyLimit()!=null){
                buyLimit = pd.getBuyLimit();
            } else {
                buyLimit = new ProductBuyLimit();
                if(pd.getStartTime()!=null){
                    buyLimit.setStartTime(pd.getStartTime().getTime());
                } else {
                    buyLimit.setStartTime(new Date().getTime());
                }
                if(pd.getEndTime()!=null){
                    buyLimit.setEndTime(pd.getEndTime().getTime());
                } else {
                    buyLimit.setEndTime(GoodsConstants.FUTURE_END_DATE.getTime());
                }
            }
            buyLimit.setId(vip.getId());
            buyLimit.setMaxVip(vipBindProductInfo.getVipLimit());
            valueObj.put("buy_limit", JSON.toJSON(buyLimit));
            
            //is_limit字段设置值
            if(buyLimit.getMax()>0 || buyLimit.getMaxVip()>0 || buyLimit.getMaxCard()>0){
                valueObj.put("is_limit", 1);
            } else {
                valueObj.put("is_limit", 0);
            }
            
            boolean success = manager.update(valueObj, whereObj, MongoTableName.CB_PRODUCT_DETAIL, "$set")>0;//更新mongo vip_price buy_limit和is_limit
            if(success){
                count++;
                //更新mysqlvip_price
                param.put("newVipPrice", vipBindProductInfo.getVipPrice());
                param.put("isLimit", valueObj.get("is_limit"));
                param.put("productId", vipBindProductInfo.getProductId());
                
                vipProductMapper.updateProductVipPrice(param);
            }
        }
        return count;
    }
    
    /**
     * VIP规则解除绑定商品
     */
    @Override
    public Result removeProductsFromVipProduct(String vipId,
            List<String> productIds) {
        // TODO Auto-generated method stub
        Result result = new Result();
        result.setResultCode(ResultCode.failed.getCode());
        result.setResultDesc("VIP规则解除绑定商品失败");
        
        SqlSession sqlSession = null;
        try{
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            VipProductMapper vipProductMapper = sqlSession.getMapper(VipProductMapper.class);
            
            //根据vipId查询VIP规则
            VipProduct vip = vipProductMapper.getVipProduct(vipId);
            if(vip!=null){
                if(productIds!=null && productIds.size()>0){//该VIP规则多条商品解绑
                    DBObject whereObj = new BasicDBObject();
                    ProductDetail pd = null;
                    DBObject valueObj = null;
                    ProductBuyLimit buyLimit = null;
                    DBObject w1 = null;
                    DBObject w2 = null;
                    Map<String,Object> param = new HashMap<String,Object>();
                    for(String productId : productIds){
                        whereObj.put("_id", productId);
                        pd = manager.findOne(whereObj, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
                        if(pd!=null){
                            w1 = new BasicDBObject();
                            w2 = new BasicDBObject();
                            valueObj = new BasicDBObject();
                            if(pd.getBuyLimit()!=null){
                                buyLimit = pd.getBuyLimit();
                                buyLimit.setId(0L);
                                buyLimit.setMaxVip(0);
                                
                                if(buyLimit.getMax()>0 || buyLimit.getMaxVip()>0 || buyLimit.getMaxCard()>0){
                                    w2.put("is_limit", 1);
                                    param.put("isLimit", 1);
                                    w2.put("buy_limit", JSON.toJSON(buyLimit));
                                } else {
                                    w2.put("is_limit", 0);
                                    param.put("isLimit", 0);
                                    //删除buy_limit这个字段值
                                    w1.put("buy_limit", 1);
                                }
                            } else {
                                w2.put("is_limit", 0);
                                param.put("isLimit", 0);
                            }
                            valueObj.put("$set", w2);
                            //删除vip_price这个字段值
                            w1.put("vip_price", 1);
                            valueObj.put("$unset", w1);
                            
                            manager.update(valueObj, whereObj, MongoTableName.CB_PRODUCT_DETAIL, null);//更新mongo vip_price buy_limit和is_limit
                            
                            //更新mysql vip_price is_limit
                            param.put("newVipPrice", 0);
                            param.put("productId", productId);
                            vipProductMapper.updateProductVipPrice(param);
                        }
                    }
                    //更新Vip已经绑定商品数量
                    VipProduct vipProduct = new VipProduct();
                    vipProduct.setVipId(vipId);
                    vipProduct.setCount(-productIds.size());
                    vipProductMapper.updateVipCount(vipProduct);
                    
                    if(VipStatus.effect.getCode().equals(vip.getStatus())){//已经生效的VIP规则需要更新 redis count
                        String key = RedisKeyUtil.cbbank_vip_product_client_id(vip.getClientId());
                        Map<String, String> hash = redis.getMap(key);
                        if(hash!=null && hash.get("count")!=null){
                            hash.put("count", ObjectUtils.toString(Integer.valueOf(hash.get("count"))-productIds.size()));
                            redis.putMap(key, hash);
                        }
                    }
                    
                    //删除绑定关系的中间表
                    param = new HashMap<String,Object>();
                    param.put("productIds", productIds);
                    param.put("vipId", vip.getVipId());
                    vipProductMapper.deleteProductsFromVip(param);
                    
                    sqlSession.commit(true);
                    result.setResultCode(ResultCode.success.getCode());
                    result.setResultDesc("VIP规则解除绑定商品成功");
                } else {//该VIP规则全部商品解绑
                    boolean sqlSuccess = this.removeAllProductofVipFromVip(vip);
                    if(sqlSuccess){
                        //更新Vip已经绑定商品数量
                        VipProduct vipProduct = new VipProduct();
                        vipProduct.setVipId(vipId);
                        vipProduct.setCount(0);
                        vipProductMapper.resetVipCount(vipProduct);
                        sqlSession.commit(true);
                        
                        if(VipStatus.effect.getCode().equals(vip.getStatus())){//已经生效的VIP规则需要更新 redis count
                            String key = RedisKeyUtil.cbbank_vip_product_client_id(vip.getClientId());
                            Map<String, String> hash = redis.getMap(key);
                            if(hash!=null && hash.size()>0){
                                hash.put("count", ObjectUtils.toString(0));
                                redis.putMap(key, hash);
                            }
                        }
                        
                        result.setResultCode(ResultCode.success.getCode());
                        result.setResultDesc("VIP规则解除绑定商品成功");
                    }
                }
            } else {
                result.setResultDesc("该VIP规则已经不存在了");
            }
        } catch (Exception e) { 
            if(null != sqlSession){
                sqlSession.rollback(true); 
            }
            LogCvt.error("VIP规则解除绑定商品失败，原因:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            } 
        } 
        return result;
    }
    
    /**
     * 解除所有商品绑定该VIP规则的绑定关系
     * @param vip
     * @param vipProductMapper
     * @return boolean
     */
    private boolean removeAllProductofVipFromVip(VipProduct vip){
        //解除所有商品绑定该VIP规则绑定
        boolean removeFlag = true;
        
        //is_limit字段值变化了的也要更新mysql
        Map<String,Integer> isLimitMap = new HashMap<String,Integer>();
        
        SqlSession sqlSession = null;
        try{
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            VipProductMapper vipProductMapper = sqlSession.getMapper(VipProductMapper.class);
            if(VipStatus.effect.getCode().equals(vip.getStatus())
                    || VipStatus.canceled.getCode().equals(vip.getStatus())){//解除商品上的绑定关系
                try{
                    DBObject dbObject = new BasicDBObject();
                    dbObject.put("client_id", vip.getClientId());
                    dbObject.put("buy_limit.id", vip.getId());
                    dbObject.put("vip_price", new BasicDBObject(QueryOperators.GT,0));
                    //1000条进行解除mongo商品绑定该VIP规则绑定
                    int pageNumber =1;
                    int pageSize = 1000;
                    int total = 1000;
                    MongoPage mPage = new MongoPage(pageNumber, pageSize);
                    List<ProductDetail> pds = null;
                    ProductBuyLimit buyLimit = null;
                    DBObject valueObj = null;
                    DBObject whereObj = new BasicDBObject();
                    
                    DBObject w1 = null;
                    DBObject w2 = new BasicDBObject();
                    
                    while(total==1000){
                        pds = null;
                        mPage = manager.findByPage(mPage, dbObject, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
                        pds = (List<ProductDetail>)mPage.getItems();
                        if(pds != null) {
                            total = pds.size();
                            
                            for(ProductDetail pd : pds){
                                isLimitMap.put(pd.getId(), 0);//不限购
                                whereObj.put("_id", pd.getId());
                                
                                w1 = new BasicDBObject();
                                w2 = new BasicDBObject();
                                valueObj = new BasicDBObject();
                                if(pd.getBuyLimit()!=null){
                                    
                                    buyLimit = pd.getBuyLimit();
                                    buyLimit.setId(0L);
                                    buyLimit.setMaxVip(0);
                                    
                                    if(buyLimit.getMax()>0 || buyLimit.getMaxVip()>0 || buyLimit.getMaxCard()>0){
                                        w2.put("is_limit", 1);
                                        isLimitMap.put(pd.getId(), 1);//限购
                                        w2.put("buy_limit", JSON.toJSON(buyLimit));
                                    } else {
                                        w2.put("is_limit", 0);
                                        //删除buy_limit这个字段值
                                        w1.put("buy_limit", 1);
                                    }
                                } else {
                                    w2.put("is_limit", 0);
                                }
                                valueObj.put("$set", w2);
                                
                                //删除vip_price这个字段值
                                w1.put("vip_price", 1);
                                valueObj.put("$unset", w1);
                                manager.update(valueObj, whereObj, MongoTableName.CB_PRODUCT_DETAIL, null);//更新mongo vip_price buy_limit和is_limit
                            }
                        } else {
                            total = 0;
                        }
                    }
                    removeFlag = true;
                } catch (Exception e) { 
                    LogCvt.error("删除VIP规则时进行解除所有商品绑定该VIP规则绑定失败，原因:" + e.getMessage(),e); 
                    removeFlag = false;
                }
                if(removeFlag){//解除所有商品绑定该VIP规则绑定成功 再更新mysql
                    //is_limit字段值变化了的也要更新mysql
                    if(isLimitMap.size()>0){
                        Map<String,Object> param1 = null;
                        for(String key : isLimitMap.keySet()){
                            param1 = new HashMap<String,Object>();
                            param1.put("productId", key);
                            param1.put("isLimit", isLimitMap.get(key));
                            param1.put("newVipPrice", 0);
                            vipProductMapper.updateProductVipPrice(param1);
                        }
                    }
                }
            }
            if(removeFlag){//解除所有商品绑定该VIP规则绑定成功 再更新mysql
                //删除绑定关系的中间表
                Map<String,Object> dparam = new HashMap<String,Object>();
                dparam.put("vipId", vip.getVipId());
                vipProductMapper.deleteProductsFromVip(dparam);
            }
            sqlSession.commit(true);
        } catch (Exception e) { 
            if(null != sqlSession){
                sqlSession.rollback(true); 
            }
            LogCvt.error("解除所有商品绑定该VIP规则绑定成功 再更新mysql失败，原因:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            } 
        } 
        return removeFlag;
    }
    
    /**
     * 分页查询可以绑定VIP规则的商品列表
     * -展示可绑定的商品时，需要满足：
     * 1.该商品处于未上架状态；且属于本活动所关联的客户端中的商品；
     * 2.已添加过VIP的商品将不会被展示，即一个商品只允许添加一个VIP商品规则；
     * 特别注意：
     *   每个客户端仅允许添加一条已生效的VIP规则；
     *   VIP活动只支持精品预售中的商品；
     *   通过BOSS系统录入或通过银行管理端录入的预售商品均可被展示及绑定；
     */
    @Override
    public List<Product> findProductsForVipByPage(String vipId,
            String name, double priceStart, double priceEnd,
            Page<Product> page) throws Exception {
        // TODO Auto-generated method stub
        SqlSession sqlSession = null;
        try{
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            VipProductMapper vipProductMapper = sqlSession.getMapper(VipProductMapper.class);
            
            //根据vipId查询VIP规则
            VipProduct vip = vipProductMapper.getVipProduct(vipId);
            if(vip!=null){
                Map<String,Object> param = validContition();//组装符合绑定VIP规则的条件
                param.put("clientId", vip.getClientId());
           
                if(name!=null && !"".equals(name)){
                    //商品名称模糊查询
                    param.put("name", name);
                }
                if(priceStart>0.0 ){
                    param.put("priceStart", Arith.mul(priceStart, GoodsConstants.DOUBLE_INTGER_CHANGE));
                }
                if(priceEnd>0.0){
                    param.put("priceEnd", Arith.mul(priceEnd, GoodsConstants.DOUBLE_INTGER_CHANGE));
                }
                List<Product> products = vipProductMapper.findProductForVipByPage(page, param);//已添加过VIP的商品将不会被展示，即一个商品只允许添加一个VIP商品规则；
                return products;
            }
             
        } catch (Exception e) { 
            LogCvt.error("分页查询可以绑定VIP规则的商品列表失败，原因:" + e.getMessage(),e); 
            throw new Exception();
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            } 
        }
        return null;
    }
    
    /**
     * 获取可以绑定VIP规则的指定商品类型(商品类型逗号分隔,比如1,2)2预售
     * @return List<String>
     */
    private List<String> getProductTypeVip(){
        String productTypeVips = GoodsConstants.GOODS_PRODUCT_TYPE_VIP;
        if(productTypeVips!=null){
            String[] productTypeVipArray = productTypeVips.split(",");
            if(productTypeVipArray.length>0){
                List<String> types = new ArrayList<String>();
                for(String type : productTypeVipArray){
                    if(type!=null && !"".equals(type)){
                        types.add(type);
                    }
                }
                return types;
            }
        }
        return null;
    }
    
    /**
     * 获取指定平台新加的商品可以绑定VIP规则(平台类型逗号分隔,比如1,2) boss 1, 银行端 2
     * @return List<String>
     */
    private List<String> getPlatTypeVip(){
        String platTypeVips = GoodsConstants.GOODS_PLAT_TYPE_VIP;
        if(platTypeVips!=null){
            String[] platTypeVipArray = platTypeVips.split(",");
            if(platTypeVipArray.length>0){
                List<String> types = new ArrayList<String>();
                for(String type : platTypeVipArray){
                    if(type!=null && !"".equals(type)){
                        types.add(type);
                    }
                }
                return types;
            }
        }
        return null;
    }
    
    /**
     * 获取指定商品状态可以绑定VIP规则(商品上下架状态类型逗号分隔,比如0,1,2) 0未上架
     * @return List<String>
     */
    private List<String> getProductStatusVip(){
        String productStatusVips = GoodsConstants.GOODS_ISMARKETABLE_VIP;
        if(productStatusVips!=null){
            String[] productStatusVipArray = productStatusVips.split(",");
            if(productStatusVipArray.length>0){
                List<String> statuss = new ArrayList<String>();
                for(String status : productStatusVipArray){
                    if(status!=null && !"".equals(status)){
                        statuss.add(status);
                    }
                }
                return statuss;
            }
        }
        return null;
    }
    
    /**
     * 启用、作废VIP规则
     */
    @Override
    public Result updateVipStatus(String vipId, String status) {
        // TODO Auto-generated method stub
        Result result = new Result();
        
        result.setResultCode(ResultCode.failed.getCode());
        if(VipStatus.effect.getCode().equals(status)){
            result.setResultDesc("启用VIP规则失败");
        } else if(VipStatus.canceled.getCode().equals(status)) {
            result.setResultDesc("作废VIP规则失败");
        }
        
        SqlSession sqlSession = null;
        try{
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            VipProductMapper vipProductMapper = sqlSession.getMapper(VipProductMapper.class);
            
            //根据vipId查询VIP规则
            VipProduct vip = vipProductMapper.getVipProduct(vipId);
            if(vip!=null){
                boolean valid = false;
                if(VipStatus.effect.getCode().equals(status)){
                    //查询该客户端下是否已经存在一条启用的VIP规则
                    VipProduct validVip = vipProductMapper.isExistVipProduct(vip.getClientId());
                    if(validVip!=null){
                        result.setResultCode(ResultCode.failed.getCode());
                        result.setResultDesc("已经存在一条启用的VIP规则");
                    } else {
                        if(VipStatus.ineffect.getCode().equals(vip.getStatus())){//只有未生效的才可以启用
                            valid = true;
                        } else {
                            result.setResultCode(ResultCode.failed.getCode());
                            result.setResultDesc("只有未生效的VIP规则才可以启用");
                        }
                    }
                } else if(VipStatus.canceled.getCode().equals(status)) {
                    if(VipStatus.effect.getCode().equals(vip.getStatus())){//只有生效的才可以作废
                        valid = true;
                    } else {
                        result.setResultCode(ResultCode.failed.getCode());
                        result.setResultDesc("只有生效的VIP规则才可以作废");
                    }
                } 
                if(valid){//有效操作
                    vip.setStatus(status);
                    boolean redisFlag = false;
                    if(VipStatus.effect.getCode().equals(vip.getStatus())){//缓存里只有启用的
                        redisFlag = VipRedis.set_cbbank_vip_product_client_id(vip);//更新缓存
                    } else if(VipStatus.canceled.getCode().equals(vip.getStatus())){//作废就重置为空
                        redisFlag = VipRedis.reset_cbbank_vip_product_client_id(vip.getClientId());
                    }
                    if(redisFlag){//redis更新成功 再更新mysql vip的状态
                        boolean updateStatus = false;
                        if(VipStatus.canceled.getCode().equals(status)) {//作废VIP规则时解除所有商品绑定该VIP规则的绑定
                            boolean sqlSuccess = this.removeAllProductofVipFromVip(vip);
                            if(sqlSuccess){
                                updateStatus = true;
                            }
                        } else if(VipStatus.effect.getCode().equals(status)) {//启用VIP规则时把所有商品直接绑定到商品表上
                            List<VipBindProductInfo> vipBindProductInfos = vipProductMapper.findVipBindProducts(vipId);
                            if(vipBindProductInfos!=null && vipBindProductInfos.size()>0){
                                List<String> pids = new ArrayList<String>();
                                for(VipBindProductInfo vipBindProductInfo : vipBindProductInfos){
                                    pids.add(vipBindProductInfo.getProductId());
                                }
                                DBObject dbObject = new BasicDBObject();
                                dbObject.put("_id", new BasicDBObject("$in", pids));
                                List<ProductDetail> pds = (List<ProductDetail>)manager.findAll(dbObject, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
                                if(pds!=null && pds.size()>0){
                                    this.attachProductsToVipProduct(vip, vipBindProductInfos, pds, vipProductMapper);
                                }
                            }
                            updateStatus = true;
                        }
                        if(updateStatus){
                            vipProductMapper.updateVipStatus(vip);//再更新mysql vip的状态
                            sqlSession.commit(true);
                            
                            result.setResultCode(ResultCode.success.getCode());
                            if(VipStatus.effect.getCode().equals(status)){
                                result.setResultDesc("启用VIP规则成功");
                            } else if(VipStatus.canceled.getCode().equals(status)) {
                                result.setResultDesc("作废VIP规则成功");
                            }
                        }
                    }
                }
            } else {
                result.setResultDesc("该VIP规则已经不存在了");
            }
        } catch (Exception e) { 
            if(null != sqlSession){
                sqlSession.rollback(true); 
            }
            LogCvt.error("启用、作废VIP规则失败，原因:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            } 
        } 
        return result;
    }
    /**
     * 查询可送积分的商品
     */
	 public List<Product> findProductsForActivtyByPage(Long activtyId,
			 String name,Page<Product> page) {
	        // TODO Auto-generated method stub
		    List<Product> product=new ArrayList<Product>();
	        
	        SqlSession sqlSession = null;
	        String pointRate = null;//银行兑换积分比例
	        try{
	            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
	            VipProductMapper vipProductMapper = sqlSession.getMapper(VipProductMapper.class);
	            ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
	            
	            ActivitiesInfo ac = productMapper.findPoints(activtyId);
	            
	            if(ac!=null && !ActivitiesStatus.repeal.getCode().equals(ac.getStatus())){//只有未启用和启用的活动才能绑定商品
	                pointRate = productMapper.findPointRate(ac.getClientId());
	            }

	            Map<String,Object> param = validContition();//组装符合绑定VIP规则的条件
	            param.put("clientId", ac.getClientId());
	            if(Checker.isNotEmpty(name)){
	            	param.put("name", name);
	            }
	            int price = ac.getPoints();//Arith.mul(Arith.div(ac.getPoints(), GoodsConstants.DOUBLE_INTGER_CHANGE),GoodsConstants.DOUBLE_INTGER_CHANGE);
	            if(Checker.isNotEmpty(pointRate)){
	                price = Arith.mul(Arith.div(Arith.div(ac.getPoints(), GoodsConstants.DOUBLE_INTGER_CHANGE), Integer.valueOf(pointRate)),GoodsConstants.DOUBLE_INTGER_CHANGE);
	            }
	            param.put("points", price);
	            
	            product = vipProductMapper.findProductsForActivtyByPage(page,param);//已添加过VIP的商品将不会被展示，即一个商品只允许添加一个VIP商品规则

	        } catch (Exception e) { 
	            if(null != sqlSession){
	                sqlSession.rollback(true); 
	            }
	            LogCvt.error("查询数据原因:" + e.getMessage(),e); 
	        } finally { 
	            if(null != sqlSession) {
	                sqlSession.close(); 
	            } 
	        } 
	        return product;
	    }
}
