package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.froad.common.beans.AddResultBean;
import com.froad.db.mongo.ProductDetialMongo;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mysql.bean.Page;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.ibatis.session.SqlSession;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.ProductCategoryMapper;
import com.froad.db.mysql.mapper.ProductMapper;
import com.froad.db.mysql.mapper.ProductOperateLogMapper;
import com.froad.db.mysql.mapper.ProductTempMapper;
import com.froad.db.mysql.mapper.VipProductMapper;
import com.froad.db.redis.ProductRedis;
import com.froad.db.redis.impl.RedisManager;
import com.froad.enums.DeliveryType;
import com.froad.enums.ModuleID;
import com.froad.enums.ProductAuditState;
import com.froad.enums.ProductStatus;
import com.froad.enums.ProductType;
import com.froad.enums.QrCodeType;
import com.froad.enums.ResultCode;
import com.froad.enums.SeckillFlagEnum;
import com.froad.enums.VipStatus;
import com.froad.logback.LogCvt;
import com.froad.logic.BossProductLogic;
import com.froad.logic.CommonLogic;
import com.froad.po.Area;
import com.froad.po.BossProductFilter;
import com.froad.po.Org;
import com.froad.po.Product;
import com.froad.po.ProductCategory;
import com.froad.po.ProductGroup;
import com.froad.po.ProductInfo;
import com.froad.po.ProductOutletInfo;
import com.froad.po.ProductPresell;
import com.froad.po.Result;
import com.froad.po.VipBindProductInfo;
import com.froad.po.VipProduct;
import com.froad.thrift.client.ThriftClientProxyFactory;
import com.froad.thrift.service.QrCodeService;
import com.froad.thrift.vo.BossProductInfoVo;
import com.froad.thrift.vo.BossProductVo;
import com.froad.thrift.vo.PlatType;
import com.froad.thrift.vo.ProductImageVo;
import com.froad.thrift.vo.QrCodeRequestVo;
import com.froad.thrift.vo.QrCodeResponseVo;
import com.froad.util.Arith;
import com.froad.util.BossConstants;
import com.froad.util.Checker;
import com.froad.util.GoodsConstants;
import com.froad.util.GoodsUtil;
import com.froad.util.MongoTableName;
import com.froad.util.ProductBeanUtil;
import com.froad.util.RedisKeyUtil;
import com.froad.po.AuditFilter;
import com.froad.po.ProductFilter;
import com.froad.po.ProductOperateLog;
import com.froad.po.ProductTemp;
import com.froad.po.mongo.ProductArea;
import com.froad.po.mongo.ProductBuyLimit;
import com.froad.po.mongo.ProductCategoryInfo;
import com.froad.po.mongo.ProductCityArea;
import com.froad.po.mongo.ProductCityOutlet;
import com.froad.po.mongo.ProductDetail;
import com.froad.po.mongo.ProductImage;
import com.froad.po.mongo.ProductOutlet;
import com.froad.util.SimpleID;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class BossProductLogicImpl implements BossProductLogic {
    
    private static SimpleID simpleID = new SimpleID(ModuleID.bossProduct);
    
    private MongoManager manager = new MongoManager();
    private RedisManager redis = new RedisManager();

    /**
     * boss管理平台查询商品列表
     */
    @Override
    public List<Product> findProductsByPage(Page<Product> page,
            BossProductFilter bossProductFilter) {
        // TODO Auto-generated method stub
        SqlSession sqlSession = null;
        try{
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
            ProductCategoryMapper productCategoryMapper = sqlSession.getMapper(ProductCategoryMapper.class);
            
            //已经删除的不查询出来
            bossProductFilter.setExcludeStatuts(ProductStatus.isDeleted.getCode());
            //未提交审核的不查出来
            bossProductFilter.setExcludeAuditState(ProductAuditState.noCommit.getCode());
            //商品分类查询条件
            if(bossProductFilter.getCategoryId()!=null && bossProductFilter.getCategoryId()>0){
                ProductCategory pc = productCategoryMapper.findProductCategoryById(bossProductFilter.getCategoryId());
                if(Checker.isNotEmpty(pc)){
                    bossProductFilter.setCategoryTreePath(pc.getTreePath());
                }
            }
            long startTime = System.currentTimeMillis();
            //按创建时间降序分页显示
            List<Product> list =  productMapper.findProductListByPage(page, bossProductFilter);
            long endTime = System.currentTimeMillis();
            LogCvt.info("-------boss的ProductMapper中findProductListByPage:耗时"+(endTime - startTime)+"--startTime:"+startTime+"---endTime:"+endTime);
            return list;
        }catch (Exception e) {
            LogCvt.error("boss管理平台查询商品列表失败，原因:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession)  
                sqlSession.close();  
        }
        return null;
    }

    /**
     * 分页查询待审核商品
     */
	@Override
	public List<ProductTemp> findAuditProductsByPage(
			ProductFilter product, Page<ProductTemp> page) {
        List<ProductTemp> list = null;
        SqlSession sqlSession = null;
        try{
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            ProductTempMapper productMapper = sqlSession.getMapper(ProductTempMapper.class);
            long startTime = System.currentTimeMillis();
            list =  productMapper.findProductTempByPage(page, product);
            long endTime = System.currentTimeMillis();
            LogCvt.info("-------mapper中findProductTempByPage:耗时"+(endTime - startTime)+"--startTime:"+startTime+"---endTime:"+endTime);
            
        }catch (Exception e) {
            LogCvt.error("待商品管理分页查询list Product失败，原因:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession)  
                sqlSession.close();  
        }
        return list;
	}
	
	/**
     * 增加商品
     * @param product
     * @return Long    主键ID
     */
    @Override
    public AddResultBean addProduct(ProductInfo productInfo,String roleId) {
        long startTime = System.currentTimeMillis();
        AddResultBean resultBean = null;
        
        SqlSession sqlSession = null;
        try{
            Product product = productInfo.getProduct();
            product.setIsMarketable(ProductStatus.noShelf.getCode());//未上架
            product.setAuditState(ProductAuditState.noAudit.getCode());//待审核
            
            VipProduct vip = null;//有关联VIP规则
            
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            
            VipProductMapper vipProductMapper = null;
            if(Checker.isNotEmpty(productInfo.getVipId()) && product.getVipPrice()!=null && product.getVipPrice()>0){
                vipProductMapper = sqlSession.getMapper(VipProductMapper.class);
                //根据vipId查询VIP规则
                vip = vipProductMapper.getVipProduct(productInfo.getVipId());
                
                if(vip==null){
                    resultBean = new AddResultBean();
                    resultBean.setCode(ResultCode.failed.getCode());
                    resultBean.setMessage("该VIP规则已经不存在了");
                    return resultBean;
                } else if(!GoodsUtil.isValidVipContition(product.getIsMarketable(), product.getType(), product.getPlatType())){
                    resultBean = new AddResultBean();
                    resultBean.setCode(ResultCode.failed.getCode());
                    resultBean.setMessage("该商品不符合绑定VIP规则条件");
                    return resultBean;
                }
                if(!vip.getClientId().equals(product.getClientId())){
                    resultBean = new AddResultBean();
                    resultBean.setCode(ResultCode.failed.getCode());
                    resultBean.setMessage("VIP规则和商品不在同一个客户端下");
                    return resultBean;
                }
                if(vip.getStatus()!=null
                        && (VipStatus.ineffect.getCode().equals(vip.getStatus())
                                || VipStatus.effect.getCode().equals(vip.getStatus()))){
                } else {
                    resultBean = new AddResultBean();
                    resultBean.setCode(ResultCode.failed.getCode());
                    resultBean.setMessage("只有未启用和启用的VIP规则才能绑定商品");
                    return resultBean;
                }
            }
            if(productInfo.getBuyLimit()!=null && productInfo.getBuyLimit().getMax()>0 && productInfo.getBuyLimit().getMaxVip()>0 
                    && productInfo.getBuyLimit().getMaxVip()>productInfo.getBuyLimit().getMax()){
                resultBean = new AddResultBean();
                resultBean.setCode(ResultCode.failed.getCode());
                resultBean.setMessage("VIP限购数量不能大于普通限购数量");
                return resultBean;
            }
            
            //填写字段默认值
            product.setPoint(0);//新加商品设置评价为0
            product.setSellCount(0);//新加商品设置销量为0
            product.setOrderValue(0);//排序权重
            product.setIsSeckill(SeckillFlagEnum.notSeckill.getCode());//非秒杀
            product.setIsPoint(false);//非送积分
            product.setIsBest(false);
            product.setAuditOrgCode("0");
            product.setAuditStartOrgCode("0");
            product.setAuditEndOrgCode("0");
            
            CommonLogic comLogic = new CommonLogicImpl();
            //机构商品
            if(Checker.isEmpty(product.getMerchantId())){
                //取商户信息
                Map<String,String> merchantMap = comLogic.getBankMerchantRedis(product.getClientId(), product.getOrgCode());
                //重新设置商户Id和商户名称
                if(merchantMap!=null && merchantMap.size()>0){
                    product.setMerchantId(merchantMap.get("merchant_id"));
                    product.setMerchantName(merchantMap.get("merchant_name"));
                }
            } else {//商户商品
                Map<String,String> merchantMap = comLogic.getMerchantRedis(productInfo.getProduct().getClientId(), productInfo.getProduct().getMerchantId());
                if(Checker.isEmpty(merchantMap)){
                    resultBean = new AddResultBean();
                    resultBean.setCode(ResultCode.failed.getCode());
                    resultBean.setMessage("商户不存在不能新增商品");
                    return resultBean;
                } else if(Checker.isEmpty(merchantMap.get("is_enable")) || "0".equals(merchantMap.get("is_enable"))){
                    resultBean = new AddResultBean();
                    resultBean.setCode(ResultCode.failed.getCode());
                    resultBean.setMessage(merchantMap.get("merchant_name")+"商户已失效不能新增商品");
                    return resultBean;
                }
                //设置商户名称
                product.setMerchantName(merchantMap.get("merchant_name"));
            }
            product.setCreateTime(new Date());//设置创建时间为当前值
           
            //设置商品Id
            product.setProductId(simpleID.nextId());
            productInfo.setProduct(product);
            
            //设置1-2-3级orgCode
            if(Checker.isNotEmpty(product.getClientId()) && Checker.isNotEmpty(product.getOrgCode())){
                List<Org> orgLevel=comLogic.getSuperOrgList(product.getClientId(),product.getOrgCode());
                for(Org org:orgLevel){
                    if(Checker.isNotEmpty(org)&& "1".equals(org.getOrgLevel())){
                        product.setProOrgCode(org.getOrgCode());
                    }else if(Checker.isNotEmpty(org)&& "2".equals(org.getOrgLevel())){
                        product.setCityOrgCode(org.getOrgCode());
                    }else if(Checker.isNotEmpty(org)&& "3".equals(org.getOrgLevel())){
                        product.setCountryOrgCode(org.getOrgCode());
                    }
                }
            }
            
            //设置category_tree_path商品分类tree_path
            if(productInfo.getProductCategory()!=null && productInfo.getProductCategory().getId()!=null && productInfo.getProductCategory().getId()>0){
                ProductCategory productCategory = comLogic.findCategoryById(product.getClientId(), productInfo.getProductCategory().getId());
                if(Checker.isNotEmpty(productCategory)){
                    product.setCategoryTreePath(productCategory.getTreePath());
                }
            }
            
            //生成mongo保存数据的格式对象
            ProductDetail productDetail = changToProductDetail(productInfo);
            
            /**********************操作MySQL数据库**********************/
            boolean productSqlFlag = false;
            try{
            
                //绑定vip规则
                if(vip!=null){
                    List<VipBindProductInfo> vipBindProductInfos = new ArrayList<VipBindProductInfo>();
                    //放入中间表
                    VipBindProductInfo vipBindProductInfo = new VipBindProductInfo();
                    vipBindProductInfo.setCreateTime(new Date());
                    vipBindProductInfo.setProductId(product.getProductId());
                    vipBindProductInfo.setVipId(vip.getVipId());
                    vipBindProductInfo.setVipPrice(product.getVipPrice());
                    if(productInfo.getBuyLimit()!=null && productInfo.getBuyLimit().getMaxVip()>0){
                        vipBindProductInfo.setVipLimit(productInfo.getBuyLimit().getMaxVip());
                    } else {
                        vipBindProductInfo.setVipLimit(0);
                    }
                    
                    vipBindProductInfos.add(vipBindProductInfo);
                    if(vipBindProductInfos.size()>0){
                        //放入中间表
                        vipProductMapper.addVipBindProducts(vipBindProductInfos);
                        
                        //更新Vip已经绑定商品数量加1
                        vip.setCount(1);
                        vipProductMapper.updateVipCount(vip);
                    }
                    if(VipStatus.ineffect.getCode().equals(vip.getStatus())){//未生效的VIP规则不需要立刻绑定到商品表中
                        product.setVipPrice(0);
                        product.setIsLimit(false);
                        productDetail.setVipPrice(0);
                        productDetail.setIsLimit(0);
                        if(productDetail.getBuyLimit()!=null){
                            if(productDetail.getBuyLimit().getMax()>0){
                                product.setIsLimit(true);
                                productDetail.setIsLimit(1);
                                productDetail.getBuyLimit().setMaxVip(0);
                            } else {
                                productDetail.setBuyLimit(null);
                            }
                        }
                    }
                }
                
                ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
                ProductTempMapper productTempMapper = sqlSession.getMapper(ProductTempMapper.class);
                
                productMapper.addProduct(product);//基础信息保存到mysql
                
                generateQrCode(product.getClientId(), product.getProductId(), QrCodeType.PRODUCT.getCode());//生成二维码
                ProductPresell presellProduct = null;
                if(product.getType().equals(ProductType.presell.getCode())){
                    presellProduct = productInfo.getProductPresell();
                    presellProduct.setProductId(product.getProductId());
                    presellProduct.setCreateTime(new Date());
                    presellProduct.setTrueBuyerNumber(0);
                    presellProduct.setClusterState(false);
                    presellProduct.setClusterType(false);
                    productMapper.addProductPresell(presellProduct);//预售信息保存到mysql
                }
                //临时表插入数据
                ProductTemp pt = createProductTemp(product, presellProduct, roleId);
                productTempMapper.addProductTemp(pt);
                
                sqlSession.commit(true);
                productSqlFlag = true;
            } catch (Exception e) { 
                productSqlFlag = false;
                if(null != sqlSession){
                    sqlSession.rollback(true);  
                }
                LogCvt.error("boss添加Product mysql失败，原因:" + e.getMessage(),e); 
            }
            if(productSqlFlag){
                if(vip!=null){
                    if(VipStatus.effect.getCode().equals(vip.getStatus())){//已经生效的VIP规则需要立刻绑定到商品表中
                        //更新 redis count
                        String key = RedisKeyUtil.cbbank_vip_product_client_id(vip.getClientId());
                        Map<String, String> hash = redis.getMap(key);
                        if(hash!=null && hash.size()>0){
                            hash.put("count", String.valueOf(Integer.valueOf(hash.get("count"))+1));
                            redis.putMap(key, hash);
                        }
                        //将VIP规则id保存到mondo结构对象中
                        if(productDetail.getBuyLimit()==null){
                            productDetail.setBuyLimit(new ProductBuyLimit());
                        }
                        productDetail.getBuyLimit().setId(vip.getId());
                    }
                }
                /**********************操作Redis缓存**********************/
                ProductRedis.addProductRedis(productInfo);
                /**********************操作Mongo，添加商品详情**********************/
                ProductDetialMongo productDetialMongo = new ProductDetialMongo();
                boolean productMongoFlag = productDetialMongo.addProductDetail(productDetail);
                if(productMongoFlag==true){
                    resultBean = new AddResultBean();
                    resultBean.setCode(ResultCode.success.getCode());
                    resultBean.setMessage("添加商品成功");
                    resultBean.setId(product.getProductId());
                } else {
                    resultBean = new AddResultBean();
                    resultBean.setCode(ResultCode.failed.getCode());
                    resultBean.setMessage("添加商品失败");
                }
            } else {
                resultBean = new AddResultBean();
                resultBean.setCode(ResultCode.failed.getCode());
                resultBean.setMessage("添加商品失败");
            }
        } catch (Exception e) { 
            LogCvt.error("boss添加Product失败，原因:" + e.getMessage(),e);
            resultBean = new AddResultBean();
            resultBean.setCode(ResultCode.failed.getCode());
            resultBean.setMessage("添加商品失败");
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            } 
        } 
        long endTime = System.currentTimeMillis();
        LogCvt.info("-------boss增加商品耗时:"+(endTime - startTime)+"--startTime:"+startTime+"---endTime:"+endTime);
        return resultBean;
    }
    
    private ProductTemp createProductTemp(Product p,ProductPresell pp,String roleId){
        ProductTemp pt = new ProductTemp();
        ProductBeanUtil.copyProperties(pt, p);
        if(pp!=null){
            pt.setDeliveryStartTime(pp.getDeliveryStartTime());
            pt.setDeliveryEndTime(pp.getDeliveryEndTime());
            pt.setTrueBuyerNumber(pp.getTrueBuyerNumber());
            pt.setVirtualBuyerNumber(pp.getVirtualBuyerNumber());
            pt.setClusterState(pp.getClusterState());
            pt.setClusterType(pp.getClusterType());
            pt.setExpireStartTime(pp.getExpireStartTime());
            pt.setExpireEndTime(pp.getExpireEndTime());
        }
        pt.setRoleId(roleId);
        return pt;
    }

    /**
     * 生成二维码
     * @param clientId
     * @param productId
     * @param qrCodeType
     * @return String 二维码url
     */
    private String generateQrCode(String clientId,String productId,String qrCodeType){
        try {
            LogCvt.info("开始生成二维码"+new Date().getTime());
            QrCodeService.Iface client = (QrCodeService.Iface) ThriftClientProxyFactory.createIface(QrCodeService.class, BossConstants.THRIFT_QRCODE_HOST, Integer.valueOf(BossConstants.THRIFT_QRCODE_PORT), 60000);
            
            QrCodeRequestVo qrcodeRequestVo = new QrCodeRequestVo(qrCodeType+productId, clientId);
            //面对面商品二维码url
            QrCodeResponseVo qrCodeResponseVo = client.retrieveQrCode(qrcodeRequestVo);
            LogCvt.info("生成二维码结束"+new Date().getTime());
            return qrCodeResponseVo.getUrl();
        }catch (Exception e) {
            LogCvt.error("商品二维码url异常：" +e,e);
            LogCvt.error("qrcodehost" +BossConstants.THRIFT_QRCODE_HOST+"qrcodeport:"+BossConstants.THRIFT_QRCODE_PORT+",productId:"+productId+",clientId:"+clientId+",qrCodeType:"+qrCodeType);
        }
        return null;
    }
    
    /**
     * 将商品信息转换成mongo中存储的信息对象
     * @param productInfo
     * @return ProductDetail
     */
    public ProductDetail changToProductDetail(ProductInfo productInfo) {
        ProductDetail productDetail = new ProductDetail(); 
        try{
            //基础信息设置
            Product p = productInfo.getProduct();
            productDetail.setId(p.getProductId());
            productDetail.setPlatType(p.getPlatType());
            productDetail.setIsSeckill(p.getIsSeckill());
            productDetail.setIsBest(BooleanUtils.toInteger(p.getIsBest(), 1, 0, 0));
            productDetail.setIsLimit(BooleanUtils.toInteger(p.getIsLimit(), 1, 0, 0));
            productDetail.setName(p.getName());
            productDetail.setFullName(p.getFullName());
            productDetail.setBriefIntroduction(p.getBriefIntroduction());
            productDetail.setProductType(p.getType());
            productDetail.setPrice(p.getPrice());
            productDetail.setMarketPrice(p.getMarketPrice());
            productDetail.setClientId(p.getClientId());
            productDetail.setIsMarketable(p.getIsMarketable());
            productDetail.setDeliveryOption(p.getDeliveryOption());
            productDetail.setSellCount(p.getSellCount());
            productDetail.setStartTime(p.getStartTime());
            productDetail.setEndTime(p.getEndTime());
            productDetail.setRackTime(p.getRackTime());
            productDetail.setMerchantId(p.getMerchantId());
            productDetail.setMerchantName(p.getMerchantName());
            productDetail.setCreateTime(new Date());
            
            //图片关联信息设置
            if(productInfo.getProductImages()!=null && productInfo.getProductImages().size()>0){
                List<ProductImage> imageInfo = new  ArrayList<ProductImage>();
                for(ProductImage image : productInfo.getProductImages()){
                    imageInfo.add(image);
                }
                productDetail.setImageInfo(imageInfo);
            }
            
            //商品分类信息设置
            if(productInfo.getProductCategory()!=null){
                List<ProductCategoryInfo> productCategoryInfo = new ArrayList<ProductCategoryInfo>();
                
                //该分类的父分类的id也会保留到mongo里
                String treePath = p.getCategoryTreePath();
                LogCvt.info("所有的产品分类tree" + treePath); 
                ProductCategoryInfo productCategory = null;
                if(treePath != null){
                    String[] pcids = treePath.split(" ");
                    boolean isContain = false;
                    for(String pcid : pcids){
                        LogCvt.info("所有的产品分类id" + pcid); 
                        productCategory = new ProductCategoryInfo();
                        if(!"".equals(pcid) && pcid!=null){
                         productCategory.setProductCategoryId(Long.valueOf(pcid));
                          productCategoryInfo.add(productCategory);
                          if(productInfo.getProductCategory().getId().longValue()==Long.valueOf(pcid).longValue()){
                              isContain=true;
                          }
                        }
                    }
                    if(isContain==false){
                        productCategory = new ProductCategoryInfo();
                        productCategory.setProductCategoryId(productInfo.getProductCategory().getId());
                        productCategoryInfo.add(productCategory);
                    }
                } else {
                    productCategory = new ProductCategoryInfo();
                    productCategory.setProductCategoryId(productInfo.getProductCategory().getId());
                    productCategoryInfo.add(productCategory);
                }
                productDetail.setProductCategoryInfo(productCategoryInfo);
            }
            
            //购买限制信息设置
            if(productInfo.getBuyLimit()!=null){
                productDetail.setBuyLimit(productInfo.getBuyLimit());
            }
            if(productDetail.getBuyLimit()!=null && (productDetail.getBuyLimit().getMax()>0 || productDetail.getBuyLimit().getMaxVip()>0)){
                productDetail.setIsLimit(1);
            } else {
                productDetail.setIsLimit(0);
            }
            
            productDetail.setVipPrice(p.getVipPrice());
            
            //前端页面所选的门店对应的机构代码,供查看详细信息时供前端直接勾选已经选择的网点机构
            productDetail.setOrgCodes(productInfo.getOrgCodes());
            //门店信息设置
            if(productInfo.getProductOutlets()!=null && productInfo.getProductOutlets().size()>0){
                productDetail.setOutletInfo(productInfo.getProductOutlets());
                setAreasOutletByOutletids(productDetail);//获取门店和区域 将门店所在的区冗余到市级-区和市级-门店集合中
                productDetail.setOutletInfo(null);
            }
        } catch (Exception e) { 
            LogCvt.error("boss新加商品保存到mongo之前生成ProductDetail失败，原因:" + e.getMessage(),e); 
            return null;
        }
        return productDetail;
    }
    
    /**
     * 设置商品的市级门店和市级区
     * @param pd
     * @return 
     */
    private void setAreasOutletByOutletids(ProductDetail pd){
        long startTime = System.currentTimeMillis();
        try{
            //根据门店id获取对应的区id
            List<ProductOutlet> productOutlets = pd.getOutletInfo();
            if (productOutlets != null && productOutlets.size() > 0) {
                CommonLogic comLogic = new CommonLogicImpl();
                //该客户端下的所有的虚拟门店集合
                Map<String,ProductOutletInfo> allClientBankOutlets = comLogic.get_cbbank_client_bank_outlet_redis(pd.getClientId(), 0, -1);
                Map<Long,Area> areaMap = new HashMap<Long,Area>();
                
                if(allClientBankOutlets!=null && allClientBankOutlets.size()>0){
                	
                	Map<Long,List<ProductArea>> countys = new HashMap<Long,List<ProductArea>>();//城市下的区
                    Map<Long,List<ProductOutlet>> pcosMap = new HashMap<Long,List<ProductOutlet>>();//城市下的门店
                    
                	ProductOutletInfo bankOutlet = null;
                	Area area = null;
                	Long cityId = null;
                    Long areaId = null;
                    Set<Long> areaIds = new HashSet<Long>();
                    for (ProductOutlet outlet : productOutlets) {
                        cityId = null;
                        areaId = null;
                        bankOutlet = allClientBankOutlets.get(outlet.getOutletId());
                        if (bankOutlet != null && bankOutlet.getAreaId()>0) {
                        	area = areaMap.get(bankOutlet.getAreaId());
                        	if(area==null){
                        		area = comLogic.findAreaById(bankOutlet.getAreaId());
                        		if(area!=null){
                        			areaMap.put(area.getId(),area);
                        		}
                        	}
                            if (Checker.isNotEmpty(area.getTreePath())) {
                                String[] treePtah = area.getTreePath().split(",");
                                if(treePtah.length==3){//门店所属的areaId为区
                                    areaId = Long.valueOf(treePtah[2]);
                                    cityId = Long.valueOf(treePtah[1]);
                                } else if(treePtah.length==2){//门店所属的areaId为市
                                    cityId = Long.valueOf(treePtah[1]);
                                }
                                if(!areaIds.contains(bankOutlet.getAreaId())) {//门店所属的areaId没有计算过的需要计算该区对应的市
                                    areaIds.add(bankOutlet.getAreaId());
                                    if(areaId!=null){//门店所属的areaId为区
                                        if(countys.get(cityId)==null){
                                            countys.put(cityId, new ArrayList<ProductArea>());
                                        }
                                        ProductArea pa = new ProductArea();
                                        pa.setAreaId(bankOutlet.getAreaId());
                                        pa.setAreaName(area.getName());
                                        countys.get(cityId).add(pa);
                                    }
                                }
                                if(cityId!=null){
                                    if(pcosMap.get(cityId)==null){
                                        pcosMap.put(cityId, new ArrayList<ProductOutlet>());
                                    }
                                    ProductOutlet pco = new ProductOutlet();
                                    pco.setAddress(bankOutlet.getAddress());
                                    pco.setOrgCode(bankOutlet.getOrgCode());
                                    pco.setOutletId(bankOutlet.getOutletId());
                                    pco.setOutletName(bankOutlet.getOutletName());
                                    pco.setPhone(bankOutlet.getPhone());
                                    pco.setAreaId(bankOutlet.getAreaId());
                                    pcosMap.get(cityId).add(pco);
                                }
                            }// end if (Checker.isNotEmpty(area.getTreePath())) {
                            
                        }//end if (bankOutlet != null && bankOutlet.getAreaId()>0) {
                        
                    }// end for (ProductOutlet outlet : productOutlets) {
                    
                    //城市下的区
                    List<ProductCityArea> cityAreas = new ArrayList<ProductCityArea>();
                    for(Long pacityId : countys.keySet()){
                        ProductCityArea pca = new ProductCityArea();
                        pca.setCityId(pacityId);
                        area = areaMap.get(Long.valueOf(pacityId));
                        if(area==null){
                        	area = comLogic.findAreaById(Long.valueOf(pacityId));
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
                        area = areaMap.get(Long.valueOf(pcityId));
                        if(area==null){
                        	area = comLogic.findAreaById(Long.valueOf(pcityId));
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
                    
                }// end if(allClientBankOutlets!=null && allClientBankOutlets.size()>0){
            }// end if (productOutlets != null && productOutlets.size() > 0) {
        } catch (Exception e) { 
            LogCvt.error("boss添加商品到mongo时设置商品的市级门店和市级区失败，原因:" + e.getMessage(),e); 
        }
        long endTime = System.currentTimeMillis();
        LogCvt.info("-------boss添加商品到mongo时设置商品的市级门店和市级区:耗时"+(endTime - startTime)+"--startTime:"+startTime+"---endTime:"+endTime);
    }

    /**
     * 修改商品
     */
    @Override
    public Result updateProduct(BossProductInfoVo productInfoVo,String roleId) {
        // TODO Auto-generated method stub
        Result result = new Result();
        
        SqlSession sqlSession = null;
        try{
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
            VipProductMapper vipProductMapper = sqlSession.getMapper(VipProductMapper.class);
            
            Product oldProduct = productMapper.getProductByProductId(productInfoVo.getProduct().getProductId());
            
            if(oldProduct!=null){
                BossProductVo productVo = productInfoVo.getProduct();
                
                //判断销售开始时间是否小于结束时间
                Long startTime = null;
                Long endTime = null;
                if(productVo.getStartTime()>0){
                    startTime = productVo.getStartTime();
                } else {
                    startTime = oldProduct.getStartTime().getTime();
                }
                if(productVo.getEndTime()>0){
                    endTime = productVo.getEndTime();
                } else {
                    endTime = oldProduct.getEndTime().getTime();
                }
                if(startTime.longValue()>=endTime.longValue()){
                    result.setResultCode(ResultCode.failed.getCode());
                    result.setResultDesc("商品销售期开始时间只能小于结束时间");
                    return result;
                }
                
                //判断预售的有没有变化的字段
                boolean isHaveUpdateProperty = false;
                ProductPresell productPresell = null;
                if(oldProduct.getType().equals(ProductType.presell.getCode())){
                    ProductPresell pp = productMapper.getProductPresellByProductId(productInfoVo.getProduct().getProductId());
                    
                    Long deliveryStartTime = null;
                    Long deliveryEndTime = null;
                    String deliveryOption = null;
                    if(productVo.getDeliveryStartTime()>0 && productVo.getDeliveryStartTime() != pp.getDeliveryStartTime().getTime()){
                        deliveryStartTime = productVo.getDeliveryStartTime();
                        isHaveUpdateProperty = true;
                    } else {
                        deliveryStartTime = pp.getDeliveryStartTime().getTime();
                    }
                    if(productVo.getDeliveryEndTime()>0 && productVo.getDeliveryEndTime() != pp.getDeliveryEndTime().getTime()){
                        isHaveUpdateProperty = true;
                        deliveryEndTime = productVo.getDeliveryEndTime();
                    } else {
                        deliveryEndTime = pp.getDeliveryEndTime().getTime();
                    }
                    
                    if(Checker.isNotEmpty(productVo.getDeliveryOption())){
                        deliveryOption = productVo.getDeliveryOption();
                    } else {
                        deliveryOption = oldProduct.getDeliveryOption();
                    }
                           
                    //只要是自提的都限制
                    if(deliveryOption.equals(DeliveryType.take.toString()) || deliveryOption.equals(DeliveryType.home_or_take.toString())){
                        if(deliveryStartTime==null || deliveryStartTime==0){
                            result.setResultCode(ResultCode.failed.getCode());
                            result.setResultDesc("配送方式可以自提的预售类型商品提货开始时间不能为空");
                            return result;
                        }
                        if(deliveryEndTime==null || deliveryEndTime==0){
                            result.setResultCode(ResultCode.failed.getCode());
                            result.setResultDesc("配送方式可以自提的预售类型商品提货结束时间不能为空");
                            return result;
                        }
                    }
                            
                    if(isHaveUpdateProperty){
                        if(deliveryStartTime!=null && deliveryEndTime!=null && deliveryStartTime.longValue()>=deliveryEndTime.longValue()){
                            result.setResultCode(ResultCode.failed.getCode());
                            result.setResultDesc("预售类型商品提货开始时间只能小于提货结束时间");
                            return result;
                        }
                        productPresell = new ProductPresell();
                        productPresell.setProductId(productInfoVo.getProduct().getProductId());
                        productPresell.setDeliveryStartTime(new Date(deliveryStartTime));
                        productPresell.setDeliveryEndTime(new Date(deliveryStartTime));
                        productPresell.setExpireStartTime(new Date(deliveryStartTime));
                        productPresell.setExpireEndTime(new Date(deliveryStartTime));
                    }
                }
                
                if(!(PlatType.boss.getValue()+"").equals(oldProduct.getPlatType())){
                    result.setResultCode(ResultCode.failed.getCode());
                    result.setResultDesc("不是boss平台新加的商品不可以修改");
                    return result;
                }
                
                VipProduct vip = null;//有关联VIP规则
                
                DBObject seach=new BasicDBObject();
                seach.put("_id",oldProduct.getProductId());
                ProductDetail pd = manager.findOne(seach, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
                Long oldVipId = null;
                if(pd!=null && pd.getBuyLimit()!=null){
                	oldVipId = pd.getBuyLimit().getId();
                }
                
                int max = 0;
                int maxVip = 0;
                if(productVo.isSetMax()){
                    max = productVo.getMax();
                } else if(pd!=null && pd.getBuyLimit()!=null){
                    max = pd.getBuyLimit().getMax();
                }
                
                if(productVo.isSetMaxVip()){
                    maxVip = productVo.getMaxVip();
                } else if(pd!=null && pd.getBuyLimit()!=null){
                    maxVip = pd.getBuyLimit().getMaxVip();
                }
                if(max>0 && maxVip>max){
                    result.setResultCode(ResultCode.failed.getCode());
                    result.setResultDesc("VIP限购数量不能大于普通限购数量");
                    return result;
                }
                boolean isNeedAuditVip = false;
                 
                if((pd==null || pd.getBuyLimit()==null || pd.getBuyLimit().getId()<=0)
                        && !productVo.isRemoveVipId && Checker.isNotEmpty(productVo.getVipId())
                        && (productVo.isSetVipPrice() && productVo.getVipPrice()>0.0)){//原来没有有关联vip规则，修改时候新加VIP规则关联需要判断vip规则是否满足
                    isNeedAuditVip = true;
                } else if(pd!=null && pd.getBuyLimit()!=null && pd.getBuyLimit().getId()>0 
                        && !productVo.isRemoveVipId && Checker.isNotEmpty(productVo.getVipId())){//原来有关联vip规则，修改时候不删除vip关联 需要判断vip规则是否满足
                    if((oldProduct.getVipPrice()!=null && oldProduct.getVipPrice()>0) 
                            || (productVo.isSetVipPrice() && productVo.getVipPrice()>0.0)){
                        isNeedAuditVip = true;
                    }
                }
                if(isNeedAuditVip){
                    //根据vipId查询VIP规则
                    vip = vipProductMapper.getVipProduct(productVo.getVipId());
                    
                    if(vip==null){
                        result.setResultCode(ResultCode.failed.getCode());
                        result.setResultDesc("该VIP规则已经不存在了");
                        return result;
                    } else if(!GoodsUtil.isValidVipContition(ProductStatus.offShelf.getCode(), oldProduct.getType(), oldProduct.getPlatType())){
                        result.setResultCode(ResultCode.failed.getCode());
                        result.setResultDesc("该商品不符合绑定VIP规则");
                        return result;
                    }
                    if(!vip.getClientId().equals(oldProduct.getClientId())){
                        result.setResultCode(ResultCode.failed.getCode());
                        result.setResultDesc("VIP规则和商品不在同一个客户端下");
                        return result;
                    }
                    if(vip.getStatus()!=null
                            && (VipStatus.ineffect.getCode().equals(vip.getStatus())
                                    || VipStatus.effect.getCode().equals(vip.getStatus()))){
                    } else {
                        result.setResultCode(ResultCode.failed.getCode());
                        result.setResultDesc("只有未启用和启用的VIP规则才能绑定商品");
                        return result;
                    }
                }
                    
                //是否有字段值被修改了
                ProductInfo productInfo = this.copyFromProductInfoVo(productInfoVo, oldProduct, vip, pd);
                if(productInfo==null && isHaveUpdateProperty==false){
                    result.setResultCode(ResultCode.failed.getCode());
                    result.setResultDesc("没有需要修改的字段");
                } else {
                    productInfo.setProductPresell(productPresell);
                    
                    //是秒杀商品时 该商品的结束时间不能小于秒杀的结束时间
                    if(!SeckillFlagEnum.notSeckill.getCode().equals(oldProduct.getIsSeckill())){
                        Product seckillProducts = productMapper.getSeckillProduct(productInfo.getProduct());
                        if(seckillProducts!=null){
                            if(productInfo.getProduct().getEndTime()==null){
                                productInfo.getProduct().setEndTime(oldProduct.getEndTime());
                            }
                            if(productInfo.getProduct().getEndTime()!=null && seckillProducts.getEndTime()!=null 
                                    && seckillProducts.getEndTime().getTime()>productInfo.getProduct().getEndTime().getTime()){
                                result.setResultCode(ResultCode.failed.getCode());
                                result.setResultDesc("您的此商品正在参加秒杀活动，请先结束秒杀活动后再修改");
                                return result;
                            }
                        }
                    }
                    productInfo.getProduct().setIsMarketable(ProductStatus.offShelf.getCode());//下架
                    productInfo.getProduct().setDownTime(new Date());//下架时间
                    productInfo.getProduct().setAuditState(ProductAuditState.noAudit.toString());//待审核状态
                    
                    //设置category_tree_path商品分类tree_path
                    if(productInfo.getProductCategory()!=null){
                        productInfo.getProduct().setCategoryTreePath(productInfo.getProductCategory().getTreePath());
                    }
                    
                    ProductDetail productDetail = updateToProductDetail(productInfo, pd);
                    
                    Product product = productInfo.getProduct();
                    
                    if(productVo.isRemoveVipId){//删除绑定规则
                        product.setVipPrice(0);
                        product.setIsLimit(false);
                        productDetail.setVipPrice(0);
                        productDetail.setIsLimit(0);
                        if(productDetail.getBuyLimit()!=null){
                            if(productDetail.getBuyLimit().getMax()>0){
                                product.setIsLimit(true);
                                productDetail.setIsLimit(1);
                                productDetail.getBuyLimit().setId(0);
                                productDetail.getBuyLimit().setMaxVip(0);
                            } else {
                                productDetail.setBuyLimit(null);
                            }
                        }
                        //更新Vip已经绑定商品数量减去1
                        VipBindProductInfo vbpi = vipProductMapper.findVipBindProduct(oldProduct.getProductId());
                        if(vbpi!=null){
                            VipProduct oldVipProduct = vipProductMapper.getVipProduct(vbpi.getVipId());
                            if(oldVipProduct!=null){
                                oldVipProduct.setCount(-1);
                                vipProductMapper.updateVipCount(oldVipProduct);
                                
                                if(VipStatus.effect.getCode().equals(oldVipProduct.getStatus())){//已经生效的VIP规则需要更新 redis count
                                    String key = RedisKeyUtil.cbbank_vip_product_client_id(oldVipProduct.getClientId());
                                    Map<String, String> hash = redis.getMap(key);
                                    if(hash!=null && hash.get("count")!=null){
                                        hash.put("count", ObjectUtils.toString(Integer.valueOf(hash.get("count"))-1));
                                        redis.putMap(key, hash);
                                    }
                                }
                                
                                //删除绑定关系的中间表
                                Map<String,Object> param = new HashMap<String,Object>();
                                param.put("productId", oldProduct.getProductId());
                                param.put("vipId", oldVipProduct.getVipId());
                                vipProductMapper.deleteProductFromVip(param);
                            }
                        }
                        
                    } else {
                        //绑定vip规则换成另一个VIP规则 需要解除绑定原来vip规则 绑定新的VIP规则
                        if(vip!=null){
                            
                            if(((oldProduct.getVipPrice()!=null && oldProduct.getVipPrice()>0) 
                                    || (productVo.isSetVipPrice() && productVo.getVipPrice()>0)) 
                                    && (oldVipId==null || oldVipId.longValue()!=vip.getId())){
                                
                                //解除原来的绑定
                                //更新Vip已经绑定商品数量减去1
                                VipBindProductInfo  vbpi = vipProductMapper.findVipBindProduct(oldProduct.getProductId());
                                if(vbpi!=null){
                                    VipProduct oldVipProduct = vipProductMapper.getVipProduct(vbpi.getVipId());
                                    if(oldVipProduct!=null){
                                        oldVipProduct.setCount(-1);
                                        vipProductMapper.updateVipCount(oldVipProduct);
                                        
                                        if(VipStatus.effect.getCode().equals(oldVipProduct.getStatus())){//已经生效的VIP规则需要更新 redis count
                                            String key = RedisKeyUtil.cbbank_vip_product_client_id(oldVipProduct.getClientId());
                                            Map<String, String> hash = redis.getMap(key);
                                            if(hash!=null && hash.get("count")!=null){
                                                hash.put("count", ObjectUtils.toString(Integer.valueOf(hash.get("count"))-1));
                                                redis.putMap(key, hash);
                                            }
                                        }
                                        
                                        //删除绑定关系的中间表
                                        Map<String,Object> param = new HashMap<String,Object>();
                                        param.put("productId", oldProduct.getProductId());
                                        param.put("vipId", oldVipProduct.getVipId());
                                        vipProductMapper.deleteProductFromVip(param);
                                    }
                                }
                                
                                List<VipBindProductInfo> vipBindProductInfos = new ArrayList<VipBindProductInfo>();
                                //放入中间表
                                VipBindProductInfo vipBindProductInfo = new VipBindProductInfo();
                                vipBindProductInfo.setCreateTime(new Date());
                                vipBindProductInfo.setProductId(oldProduct.getProductId());
                                vipBindProductInfo.setVipId(vip.getVipId());
                                if(productVo.isSetVipPrice() && productVo.getVipPrice()>0){
                                    vipBindProductInfo.setVipPrice(productDetail.getVipPrice());
                                } else {
                                    vipBindProductInfo.setVipPrice(oldProduct.getVipPrice());
                                }
                                vipBindProductInfo.setVipLimit(0);
                                if(productVo.isSetMaxVip() && productVo.getMaxVip()>0){
                                    vipBindProductInfo.setVipLimit(productVo.getMaxVip());
                                } else if(pd!=null && pd.getBuyLimit()!=null && pd.getBuyLimit().getMaxVip()>0) {
                                    vipBindProductInfo.setVipLimit(pd.getBuyLimit().getMaxVip());
                                }
                                vipBindProductInfos.add(vipBindProductInfo);
                                if(vipBindProductInfos.size()>0){
                                    //放入中间表
                                    vipProductMapper.addVipBindProducts(vipBindProductInfos);
                                    
                                    //更新Vip已经绑定商品数量加1
                                    vip.setCount(1);
                                    vipProductMapper.updateVipCount(vip);
                                }
                                productDetail.getBuyLimit().setId(vip.getId());
                                
                                
                            }
                            
                            if(VipStatus.ineffect.getCode().equals(vip.getStatus())){//未生效的VIP规则不需要立刻绑定到商品表中
                                product.setVipPrice(0);
                                product.setIsLimit(false);
                                productDetail.setVipPrice(0);
                                productDetail.setIsLimit(0);
                                if(productDetail.getBuyLimit()!=null){
                                    if(productDetail.getBuyLimit().getMax()>0){
                                        product.setIsLimit(true);
                                        productDetail.setIsLimit(1);
                                        productDetail.getBuyLimit().setId(0);
                                        productDetail.getBuyLimit().setMaxVip(0);
                                    } else {
                                        productDetail.setBuyLimit(null);
                                    }
                                }
                            }
                        }
                    }
                    
                    ProductTempMapper productTempMapper = sqlSession.getMapper(ProductTempMapper.class);
                    
                    boolean productSqlFlag = false;
                    try{
                        if(productDetail.getIsLimit()==1){
                            product.setIsLimit(true);
                        } else {
                            product.setIsLimit(false);
                        }
                        productMapper.updateProduct(product); //更新mysql基础信息
                        
                        ProductPresell presellProduct = null;
                        if(oldProduct.getType().equals(ProductType.presell.getCode())){
                            presellProduct = productMapper.getProductPresellByProductId(product.getProductId());
                            if(productVo.getDeliveryStartTime()>0 || productVo.getDeliveryEndTime()>0){
                                if(productVo.getDeliveryStartTime()>0){
                                    presellProduct.setDeliveryStartTime(new Date(productVo.getDeliveryStartTime()));
                                    presellProduct.setExpireStartTime(new Date(productVo.getDeliveryStartTime()));
                                }
                                if(productVo.getDeliveryEndTime()>0){
                                    presellProduct.setDeliveryEndTime(new Date(productVo.getDeliveryEndTime()));
                                    presellProduct.setExpireEndTime(new Date(productVo.getDeliveryEndTime()));
                                }
                                productMapper.updateProductPresell(presellProduct);//更新mysql预售信息
                            }
                        }
                        
                        Product newProduct = createNewProduct(product,oldProduct);
                        //临时表插入数据
                        ProductTemp pt = createProductTemp(newProduct, presellProduct, roleId);
                        productTempMapper.deleteProductTemp(oldProduct.getProductId());
                        productTempMapper.addProductTemp(pt);
                        
                        sqlSession.commit(true);
                        productSqlFlag = true;
                    } catch (Exception e) { 
                        if(null != sqlSession){
                            sqlSession.rollback(true);
                        }
                        LogCvt.error("boss修改Product mysql失败，原因:" + e.getMessage(),e); 
                        productSqlFlag = false;
                    }
                    if(productSqlFlag){
                        //商品redis
                        boolean productRedisFlag = ProductRedis.updateProductRedis(productInfo, oldProduct);
                        
                        //更新商品详情mongo
                        ProductDetialMongo productDetialMongo = new ProductDetialMongo();
                        boolean productMongoFlag = productDetialMongo.updateProductDetail(productDetail, pd==null?false:true);
                        if(productMongoFlag && productRedisFlag){
                            result.setResultCode(ResultCode.success.getCode());
                            result.setResultDesc("商品修改成功");
                            
                            LogCvt.info("商品修改成功"+(productSqlFlag==false?"mysql修改失败":"")+(productRedisFlag==false?"redis修改失败":"")+(productMongoFlag==false?"mongo修改失败":""));
                        } else {
                            result.setResultCode(ResultCode.failed.getCode());
                            result.setResultDesc("商品修改失败");
                        }
                    } else {
                        result.setResultCode(ResultCode.failed.getCode());
                        result.setResultDesc("商品修改失败");
                    }
                    return result;
                }
            } else {
                result.setResultCode(ResultCode.failed.getCode());
                result.setResultDesc("商品信息已经不存在,修改商品失败");
            }
        } catch (Exception e) {
            if (null != sqlSession) {
                sqlSession.rollback(true);
            }
            LogCvt.error("boss修改商品失败，原因:" + e.getMessage(), e);
            result.setResultCode(ResultCode.failed.getCode());
            result.setResultDesc("修改商品失败");
            return result;
        } finally {
            if (null != sqlSession) {
                sqlSession.close();
            }
        }
        return null;
    }
    
    private Product createNewProduct(Product p, Product oldProduct){
        Product product = new Product();
        ProductBeanUtil.copyProperties(product, oldProduct);
        
        //商品分类逻辑
        if(Checker.isNotEmpty(p.getCategoryTreePath())){
            product.setCategoryTreePath(p.getCategoryTreePath());
        }
        if(Checker.isNotEmpty(p.getName())){
            product.setName(p.getName());
        }
        if(Checker.isNotEmpty(p.getFullName())){
            product.setFullName(p.getFullName());
        }
        if(Checker.isNotEmpty(p.getBriefIntroduction())){
            product.setBriefIntroduction(p.getBriefIntroduction());
        }
        if(p.getPrice()!=null && p.getPrice()>0.0){
            product.setPrice(p.getPrice());
        }
        if(p.getMarketPrice()!=null && p.getMarketPrice()>0.0){
            product.setMarketPrice(p.getMarketPrice());
        }
        if(p.getDeliveryMoney()!=null && p.getDeliveryMoney()>0.0){
            product.setDeliveryMoney(p.getDeliveryMoney());
        }
        if(p.getStore()!=null && p.getStore()>0.0){
            product.setStore(p.getStore());
        }
        if(p.getStartTime()!=null){
            product.setStartTime(p.getStartTime());
        }
        if(p.getEndTime()!=null){
            product.setEndTime(p.getEndTime());
        }
        if(Checker.isNotEmpty(p.getWeight())){
            product.setWeight(p.getWeight());
        }
        if(Checker.isNotEmpty(p.getWeightUnit())){
            product.setWeightUnit(p.getWeightUnit());
        }
        if(Checker.isNotEmpty(p.getDeliveryOption())){
            product.setDeliveryOption(p.getDeliveryOption());
        }
        if(Checker.isNotEmpty(p.getBuyKnow())){
            product.setBuyKnow(p.getBuyKnow());
        }
        if(Checker.isNotEmpty(p.getIntroduction())){
            product.setIntroduction(p.getIntroduction());
        }
        if(p.getVipPrice()!=null && p.getVipPrice()>0){
            product.setVipPrice(p.getVipPrice());
        }
        return product;
    }
    
    private ProductInfo copyFromProductInfoVo(BossProductInfoVo productInfoVo,Product oldProduct,VipProduct vip,ProductDetail oldProductDetail){
        ProductInfo productInfo = null;
        boolean isHaveProperty = false;//判断是否有变化的字段
        
        CommonLogic common = new CommonLogicImpl();
        
        BossProductVo productVo = productInfoVo.getProduct();//商品基础信息
        if(productVo!=null && productVo.getType()!=null){
            //把productvo转成productInfo
            productInfo = new ProductInfo();
            Product product = new Product();
            product.setClientId(oldProduct.getClientId());
            product.setProductId(productVo.getProductId());
            product.setMerchantId(oldProduct.getMerchantId());
            //商品分类逻辑
            if(productVo.getCategoryId()>0){
                ProductCategory productCategory = common.findCategoryById(oldProduct.getClientId(), productVo.getCategoryId());
                if(productCategory!=null && !productCategory.getTreePath().equals(oldProduct.getCategoryTreePath())){
                    productInfo.setProductCategory(productCategory);
                    isHaveProperty = true;
                }
            }
            if(Checker.isNotEmpty(productVo.getName()) && !productVo.getName().equals(oldProduct.getName())){
                product.setName(productVo.getName());
                isHaveProperty = true;
            }
            if(Checker.isNotEmpty(productVo.getFullName()) && !productVo.getFullName().equals(oldProduct.getFullName())){
                product.setFullName(productVo.getFullName());
                isHaveProperty = true;
            }
            if(Checker.isNotEmpty(productVo.getBriefIntroduction()) && !productVo.getBriefIntroduction().equals(oldProduct.getBriefIntroduction())){
                product.setBriefIntroduction(productVo.getBriefIntroduction());
                isHaveProperty = true;
            }
            if(productVo.getPrice()>0.0 && productVo.getPrice() != Arith.div(oldProduct.getPrice(), GoodsConstants.DOUBLE_INTGER_CHANGE)){
                product.setPrice(Arith.mul(productVo.getPrice(), GoodsConstants.DOUBLE_INTGER_CHANGE));
                isHaveProperty = true;
            }
            if(productVo.getMarketPrice()>0.0 && productVo.getMarketPrice() != Arith.div(oldProduct.getMarketPrice(), GoodsConstants.DOUBLE_INTGER_CHANGE)){
                product.setMarketPrice(Arith.mul(productVo.getMarketPrice(), GoodsConstants.DOUBLE_INTGER_CHANGE));
                isHaveProperty = true;
            }
            if(productVo.getDeliveryMoney()>0.0 && productVo.getDeliveryMoney() != Arith.div(oldProduct.getDeliveryMoney(), GoodsConstants.DOUBLE_INTGER_CHANGE)){
                product.setDeliveryMoney(Arith.mul(productVo.getDeliveryMoney(), GoodsConstants.DOUBLE_INTGER_CHANGE));
                isHaveProperty = true;
            }
            if(productVo.getStore()>0 && productVo.getStore() != oldProduct.getStore()){
                product.setStore(productVo.getStore());
                isHaveProperty = true;
            }
            if(productVo.getStartTime()>0 && productVo.getStartTime() != oldProduct.getStartTime().getTime()){
                product.setStartTime(new Date(productVo.getStartTime()));
                isHaveProperty = true;
            }
            if(productVo.getEndTime()>0 && productVo.getEndTime() != oldProduct.getEndTime().getTime()){
                product.setEndTime(new Date(productVo.getEndTime()));
                isHaveProperty = true;
            }
            if(Checker.isNotEmpty(productVo.getWeight()) && !productVo.getWeight().equals(oldProduct.getWeight())){
                product.setWeight(productVo.getWeight());
                isHaveProperty = true;
            }
            if(Checker.isNotEmpty(productVo.getWeightUnit()) && !productVo.getWeightUnit().equals(oldProduct.getWeightUnit())){
                product.setWeightUnit(productVo.getWeightUnit());
                isHaveProperty = true;
            }
            if(Checker.isNotEmpty(productVo.getDeliveryOption()) && !productVo.getDeliveryOption().equals(oldProduct.getDeliveryOption())){
                product.setDeliveryOption(productVo.getDeliveryOption());
                isHaveProperty = true;
            }
            if(Checker.isNotEmpty(productVo.getBuyKnow()) && !productVo.getBuyKnow().equals(oldProduct.getBuyKnow())){
                product.setBuyKnow(productVo.getBuyKnow());
                isHaveProperty = true;
            }
            if(Checker.isNotEmpty(productVo.getIntroduction()) && !productVo.getIntroduction().equals(oldProduct.getIntroduction())){
                product.setIntroduction(productVo.getIntroduction());
                isHaveProperty = true;
            }
            
            if(productVo.isSetMax()){
                if(oldProductDetail==null || oldProductDetail.getBuyLimit()==null || oldProductDetail.getBuyLimit().getMax()!=productVo.getMax()){
                    ProductBuyLimit buyLimit = new ProductBuyLimit();
                    buyLimit.setMax(productVo.getMax());
                    productInfo.setBuyLimit(buyLimit);
                    isHaveProperty = true;
                }
            }
            
            if(productVo.isSetVipPrice() 
                    && (oldProduct.getVipPrice()==null
                    || (oldProduct.getVipPrice()!=null && Arith.mul(productVo.getVipPrice(), GoodsConstants.DOUBLE_INTGER_CHANGE) != oldProduct.getVipPrice().intValue())
                )){
                product.setVipPrice(Arith.mul(productVo.getVipPrice(), GoodsConstants.DOUBLE_INTGER_CHANGE));
                isHaveProperty = true;
            }
            
            if(productVo.isSetMaxVip()){
                if(oldProductDetail==null || oldProductDetail.getBuyLimit()==null || oldProductDetail.getBuyLimit().getMaxVip()!=productVo.getMaxVip()){
                    if(productInfo.getBuyLimit()==null){
                        productInfo.setBuyLimit(new ProductBuyLimit());
                    }
                    productInfo.getBuyLimit().setMaxVip(productVo.getMaxVip());
                    isHaveProperty = true;
                }
            }
            
            if(Checker.isNotEmpty(productVo.getVipId()) && vip!=null){
                if(oldProductDetail==null || oldProductDetail.getBuyLimit()==null || oldProductDetail.getBuyLimit().getId()!=vip.getId()){
                    if(productInfo.getBuyLimit()==null){
                        productInfo.setBuyLimit(new ProductBuyLimit());
                    }
                    productInfo.getBuyLimit().setId(vip.getId());
                    isHaveProperty = true;
                }
            }
            
            productInfo.setProduct(product);
            
            //商品图片处理逻辑
            if(productInfoVo.getImage()!=null && productInfoVo.getImage().size()>0){//图片信息
                List<ProductImageVo> images = productInfoVo.getImage();
                List<ProductImage> productImages = new ArrayList<ProductImage>();
                ProductImage productIamge = null;
                for(ProductImageVo image : images){
                    productIamge = new ProductImage();
                    ProductBeanUtil.copyProperties(productIamge, image);
                    productImages.add(productIamge);
                }
                productInfo.setProductImages(productImages);
                isHaveProperty = true;
            }
            //提货网点的门店信息处理逻辑
            if(productInfoVo.isSetOutletIds()){//门店信息
                if(productInfoVo.getOutletIds()!=null && productInfoVo.getOutletIds().size()>0){
                    List<ProductOutlet> productOutlets = new ArrayList<ProductOutlet>();
                    List<String> outletIds = productInfoVo.getOutletIds();
                    ProductOutlet productOutlet = null;
                    for(String outletId : outletIds){
                        productOutlet = new ProductOutlet();
                        productOutlet.setOutletId(outletId);
                        productOutlets.add(productOutlet);
                    }
                    productInfo.setProductOutlets(productOutlets);
                } else {
                    productInfo.setProductOutlets(null);
                }
                isHaveProperty = true;
            }
            //前端提货网点所在的法人行社机构号列表
            if(productInfoVo.isSetOrgCodes()){
                if(productInfoVo.getOrgCodes()!=null && productInfoVo.getOrgCodes().size()>0){
                    productInfo.setOrgCodes(productInfoVo.getOrgCodes());
                } else {
                    productInfo.setOrgCodes(null);
                }
                isHaveProperty = true;
            } else if(oldProductDetail.getOrgCodes()!=null && oldProductDetail.getOrgCodes().size()>0){
                isHaveProperty = true;
            }
        }
        if(isHaveProperty){
            return productInfo;
        } else {
            return null;
        }
    }
    
    /**
     * 将商品信息转换成mongo中存储的信息对象
     * @param productInfo
     * @return ProductDetail
     */
    public ProductDetail updateToProductDetail(ProductInfo productInfo,ProductDetail productDetail) {
        if(productDetail==null){
            productDetail = this.changToProductDetail(productInfo);
        } else {
            try{
                //基础信息设置
                Product p = productInfo.getProduct();
                
                if(Checker.isNotEmpty(p.getName())){
                    productDetail.setName(p.getName());
                }
                if(Checker.isNotEmpty(p.getFullName())){
                    productDetail.setFullName(p.getFullName());
                }
                if(Checker.isNotEmpty(p.getBriefIntroduction())){
                    productDetail.setBriefIntroduction(p.getBriefIntroduction());
                }
                if(p.getPrice()!=null){
                    productDetail.setPrice(p.getPrice());
                }
                if(p.getMarketPrice()!=null){
                    productDetail.setMarketPrice(p.getMarketPrice());
                }
                productDetail.setIsMarketable(p.getIsMarketable());
                if(Checker.isNotEmpty(p.getDeliveryOption())){
                    productDetail.setDeliveryOption(p.getDeliveryOption());
                }
                if(p.getStartTime()!=null){
                    productDetail.setStartTime(p.getStartTime());
                }
                if(p.getEndTime()!=null){
                    productDetail.setEndTime(p.getEndTime());
                }
                //图片关联信息设置
                if(productInfo.getProductImages()!=null && productInfo.getProductImages().size()>0){
                    List<ProductImage> imageInfo = new  ArrayList<ProductImage>();
                    for(ProductImage image : productInfo.getProductImages()){
                        imageInfo.add(image);
                    }
                    productDetail.setImageInfo(imageInfo);
                }
                
                //商品分类信息设置
                if(productInfo.getProductCategory()!=null){
                    List<ProductCategoryInfo> productCategoryInfo = new ArrayList<ProductCategoryInfo>();
                    
                    //该分类的父分类的id也会保留到mongo里
                    String treePath = p.getCategoryTreePath();
                    LogCvt.info("所有的产品分类tree" + treePath); 
                    ProductCategoryInfo productCategory = null;
                    if(treePath != null){
                        String[] pcids = treePath.split(" ");
                        boolean isContain = false;
                        for(String pcid : pcids){
                            LogCvt.info("所有的产品分类id" + pcid); 
                            productCategory = new ProductCategoryInfo();
                            if(!"".equals(pcid) && pcid!=null){
                             productCategory.setProductCategoryId(Long.valueOf(pcid));
                              productCategoryInfo.add(productCategory);
                              if(productInfo.getProductCategory().getId().longValue()==Long.valueOf(pcid).longValue()){
                                  isContain=true;
                              }
                            }
                        }
                        if(isContain==false){
                            productCategory = new ProductCategoryInfo();
                            productCategory.setProductCategoryId(productInfo.getProductCategory().getId());
                            productCategoryInfo.add(productCategory);
                        }
                    } else {
                        productCategory = new ProductCategoryInfo();
                        productCategory.setProductCategoryId(productInfo.getProductCategory().getId());
                        productCategoryInfo.add(productCategory);
                    }
                    productDetail.setProductCategoryInfo(productCategoryInfo);
                }
                
                ProductBuyLimit buyLimit = null;
                if(productDetail.getBuyLimit()!=null){
                    buyLimit = productDetail.getBuyLimit();
                }
                //购买限制信息设置
                if(productInfo.getBuyLimit()!=null){
                    if(buyLimit==null){
                        buyLimit = new ProductBuyLimit();
                    }
                    buyLimit.setMax(productInfo.getBuyLimit().getMax());
                    buyLimit.setMaxVip(productInfo.getBuyLimit().getMaxVip());
                    buyLimit.setId(productInfo.getBuyLimit().getId());
                        
                    productDetail.setBuyLimit(buyLimit);
                }
                if(buyLimit!=null && (buyLimit.getMax()>0 || buyLimit.getMaxVip()>0)){
                    productDetail.setIsLimit(1);
                } else {
                    productDetail.setIsLimit(0);
                }
                if(productDetail.getIsLimit()==0 && productDetail.getBuyLimit()!=null && productDetail.getBuyLimit().getId()<=0){
                    productDetail.setBuyLimit(null);
                }
                productDetail.setVipPrice(p.getVipPrice());
                //前端页面所选的门店对应的机构代码,供查看详细信息时供前端直接勾选已经选择的网点机构
                productDetail.setOrgCodes(productInfo.getOrgCodes());
                //门店信息设置
                if(productInfo.getProductOutlets()!=null && productInfo.getProductOutlets().size()>0){
                    productDetail.setOutletInfo(productInfo.getProductOutlets());
                    setAreasOutletByOutletids(productDetail);//获取门店和区域 将门店所在的区冗余到市级-区和市级-门店集合中
                    productDetail.setOutletInfo(null);
                } else {
                    productDetail.setCityAreas(null);
                    productDetail.setOrgOutlets(null);
                }
            } catch (Exception e) { 
                LogCvt.error("boss新加商品保存到mongo之前生成ProductDetail失败，原因:" + e.getMessage(),e); 
                return null;
            }
        }
        return productDetail;
    }

    /**
     * 查询商品详情
     */
    @Override
    public Map<String,Object> getBossProductDetail(String productId) {
        // TODO Auto-generated method stub
        Map<String,Object> map = null;
        SqlSession sqlSession = null;
        try{
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
            VipProductMapper vipProductMapper = sqlSession.getMapper(VipProductMapper.class);
            
            Product product = productMapper.getProductByProductId(productId);
            if(product!=null){
                map = new HashMap<String,Object>();
                map.put("product", product);
                if(ProductType.group.getCode().equals(product.getType())){//团购商品
                    ProductGroup pg = productMapper.getProductGroupByProductId(productId);
                    map.put("productGroup", pg);
                } else if(ProductType.presell.getCode().equals(product.getType())){//预售商品
                    ProductPresell pr = productMapper.getProductPresellByProductId(productId);
                    map.put("productPresell", pr);
                }
                
                DBObject where = new BasicDBObject();
                where.put("_id", product.getProductId());
                MongoManager manager = new MongoManager();
                ProductDetail pd = manager.findOne(where, MongoTableName.CB_PRODUCT_DETAIL, ProductDetail.class);
                if(pd!=null){
                    VipBindProductInfo vbp = vipProductMapper.findVipBindProduct(product.getProductId());
                    if(vbp!=null){
                      //绑定的VIP价格是未生效的VIP规则时候VIP价格和VIP限购需要从中间表拿到
                        VipProduct vip = vipProductMapper.getVipProduct(vbp.getVipId());
                        if(vip!=null && VipStatus.ineffect.getCode().equals(vip.getStatus())){
                            pd.setVipPrice(vbp.getVipPrice());
                            if(pd.getBuyLimit()==null){
                                pd.setBuyLimit(new ProductBuyLimit());
                            }
                            if(vbp.getVipLimit()!=null){
                                pd.getBuyLimit().setMaxVip(vbp.getVipLimit());
                            }
                            pd.getBuyLimit().setId(vip.getId());
                        }
                        VipProduct vipProduct = vipProductMapper.getVipProductById(vip.getId());
                        if(vipProduct!=null){
                           map.put("vipProduct", vipProduct);
                        }
                    }
                }
                map.put("productDetail", pd);
            }
        } catch (Exception e) {
            LogCvt.error("boss查询商品明细失败，原因:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            }
        }
        return map;
    }

    @Override
    public Result deleteProduct(String productId) {
        // TODO Auto-generated method stub
        Result result = new Result();
        
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
          
            Product product = productMapper.getProductByProductId(productId);
            if(product!=null && !ProductStatus.isDeleted.getCode().equals(product.getIsMarketable())){
                if(!(PlatType.boss.getValue()+"").equals(product.getPlatType())){
                    result.setResultCode(ResultCode.failed.getCode());
                    result.setResultDesc("不是boss平台新加的商品不可以删除");
                    return result;
                }
                //更新mysql的is_marketable为删除状态
                product.setIsMarketable(ProductStatus.isDeleted.getCode());
                productMapper.deleteLogicProduct(product); //mysql逻辑删除
                sqlSession.commit(true);
                
                /* redis缓存 */
                String key = RedisKeyUtil.cbbank_product_client_id_merchant_id_product_id(product.getClientId(), product.getMerchantId(), product.getProductId());
                //更新redis缓存 is_marketable为删除状态
                Map<String, String> hash = redis.getMap(key);
                if(hash!=null){
                    hash.put("is_marketable", product.getIsMarketable()+"");
                    redis.putMap(key, hash);
                }
                //更新mongo is_marketable为删除状态
                DBObject value = new BasicDBObject();
                value.put("is_marketable", product.getIsMarketable());
                DBObject where = new BasicDBObject();
                where.put("_id", product.getProductId());
                manager.update(value, where, MongoTableName.CB_PRODUCT_DETAIL, "$set");
                result.setResultCode(ResultCode.success.getCode());
                result.setResultDesc("删除商品成功");
            } else {
                result.setResultCode(ResultCode.failed.getCode());
                result.setResultDesc("商品信息已经不存在");
            }
        } catch (Exception e) {
            if(null != sqlSession){
                sqlSession.rollback(true);  
            }
            LogCvt.error("boss删除商品失败，原因:" + e.getMessage(),e); 
            result.setResultCode(ResultCode.failed.getCode());
            result.setResultDesc("删除商品失败");
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            }
        }
        return result;
    }

    @Override
    public Result updateProductStatus(String productId, String marketableStatus) {
        // TODO Auto-generated method stub
        Result result = new Result();
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
            
            Product p = productMapper.getProductByProductId(productId);
            if(p!=null && !ProductStatus.isDeleted.getCode().equals(p.getIsMarketable())){
                if(marketableStatus.equals(ProductStatus.offShelf.toString()) || marketableStatus.equals(ProductStatus.onShelf.toString())){
                    if(!(PlatType.boss.getValue()+"").equals(p.getPlatType())){
                        result.setResultCode(ResultCode.failed.getCode());
                        result.setResultDesc("不是boss平台新加的商品不可以上下架操作");
                        return result;
                    }
                    if(marketableStatus.equals(ProductStatus.onShelf.toString()) && !ProductAuditState.passAudit.toString().equals(p.getAuditState())){
                        result.setResultCode(ResultCode.failed.getCode());
                        result.setResultDesc("审核通过才可以 上架操作");
                        return result;
                    }
                    if(marketableStatus.equals(ProductStatus.offShelf.toString()) && !ProductStatus.onShelf.toString().equals(p.getIsMarketable())){
                        result.setResultCode(ResultCode.failed.getCode());
                        result.setResultDesc("上架后的商品才可以下架操作");
                        return result;
                    }
                    Date now = new Date();
                    Product product = new Product();
                    product.setProductId(productId);
                    product.setIsMarketable(marketableStatus);
                    if(marketableStatus.equals(ProductStatus.onShelf.getCode())){
                        product.setRackTime(now);
                    } else if(marketableStatus.equals(ProductStatus.offShelf.getCode())){
                        product.setDownTime(now);
                    }
                    //更新mysql
                    productMapper.updateProductStatus(product); 
                    sqlSession.commit(true);
                    
                    /* 更新redis缓存is_marketable */
                    String key = RedisKeyUtil.cbbank_product_client_id_merchant_id_product_id(p.getClientId(), p.getMerchantId(), p.getProductId());
                    Map<String, String> hash = redis.getMap(key);
                    if(hash!=null){
                        hash.put("is_marketable", product.getIsMarketable()+"");
                        redis.putMap(key, hash);
                    }
                    //更新mongo is_marketable
                    DBObject value = new BasicDBObject();
                    value.put("is_marketable", product.getIsMarketable());
                    if(ProductStatus.onShelf.getCode().equals(product.getIsMarketable())){
                        value.put("rack_time", now.getTime());
                    }
                    DBObject where = new BasicDBObject();
                    where.put("_id", product.getProductId());
                    manager.update(value, where, MongoTableName.CB_PRODUCT_DETAIL, "$set");//更新mongo is_marketable
                    
                    result.setResultCode(ResultCode.success.getCode());
                    if(marketableStatus.equals(ProductStatus.onShelf.toString())){
                        result.setResultDesc("上架操作成功");
                    } else {
                        result.setResultDesc("下架操作成功");
                    }
                    return result;
                } else {
                    result.setResultCode(ResultCode.failed.getCode());
                    result.setResultDesc("只能进行上下架操作");
                    return result;
                }
            } else {
                result.setResultCode(ResultCode.failed.getCode());
                result.setResultDesc("该商品不存在");
                return result;
            }
        } catch (Exception e) {
            if(null != sqlSession){
                sqlSession.rollback(true);
            }
            LogCvt.error("boss上下架 Product失败，原因:" + e.getMessage(),e); 
            result.setResultCode(ResultCode.failed.getCode());
            result.setResultDesc("上下架商品失败");
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            }
        }
        return result; 
    }
    
    /**
     * 审核商品
     */
	public Result auditProduct(AuditFilter auditFilter) {
		Result result = new Result();
        SqlSession sqlSession = null;
        try{
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            //ProductTempMapper producttempMapper = sqlSession.getMapper(ProductTempMapper.class);
            ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
            ProductOperateLogMapper productOperateLogMapper = sqlSession.getMapper(ProductOperateLogMapper.class);
            long startTime = System.currentTimeMillis();
            //Long  audit = producttempMapper.deleteProductTemp(auditFilter.getProductId());
            Product temp= productMapper.getProductByProductId(auditFilter.getProductId());
            Product product=new Product();
            product.setAuditComment(auditFilter.getAuditRemark());
            product.setAuditTime(new Date());
            product.setAuditState(auditFilter.getAuditState());
            product.setReviewStaff(auditFilter.getUserId());
            product.setAuditStaff(auditFilter.getUserId());
            product.setProductId(auditFilter.getProductId());
            product.setMerchantId(temp.getMerchantId());
            product.setClientId(temp.getClientId());
            if(ProductAuditState.passAudit.getCode().equals(auditFilter.getAuditState())){
                product.setIsMarketable(ProductStatus.offShelf.getCode());
            }else{
            	product.setIsMarketable(ProductStatus.noShelf.getCode());
            }
            productMapper.updateAuditProduct(product);
            ProductOperateLog log=new ProductOperateLog();
            log.setCreateTime(new Date());
            log.setProductId(auditFilter.getProductId());
            log.setUserId(auditFilter.getUserId());
            log.setUserName(auditFilter.getUserName());
            log.setType("审核");
            log.setRoleId(auditFilter.getRoleId());
            productOperateLogMapper.addProductOperatorLog(log);
            sqlSession.commit(true);
            /**********************操作Redis缓存**********************/
            ProductRedis.auditProduct(product);
            /**********************操作Mongo，修改上下架状态**********************/
            ProductDetialMongo productDetialMongo = new ProductDetialMongo();         
            ProductDetail detail=new ProductDetail();
            detail.setId(product.getProductId());
            productDetialMongo.auditProduct(detail);
            long endTime = System.currentTimeMillis();
            LogCvt.info("-------mapper中deleteProductTemp:耗时"+(endTime - startTime)+"--startTime:"+startTime+"---endTime:"+endTime);
            result.setResultCode(ResultCode.success.getCode());
            result.setResultDesc("审核商品成功");
        }catch (Exception e) {
            result.setResultCode(ResultCode.failed.getCode());
            result.setResultDesc("审核商品失败");
            LogCvt.error("商品审核失败，原因:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession)  
                sqlSession.close();  
        }
        return result;
	}

	@Override
	public Result addProductLog(ProductOperateLog log) {
		Result result = new Result();
        SqlSession sqlSession = null;
        Long audit=null;
        try{
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            ProductOperateLogMapper logMapper = sqlSession.getMapper(ProductOperateLogMapper.class);
            long startTime = System.currentTimeMillis();
            audit =logMapper.addProductOperatorLog(log);
            long endTime = System.currentTimeMillis();
            LogCvt.info("-------mapper中deleteProductTemp:耗时"+(endTime - startTime)+"--startTime:"+startTime+"---endTime:"+endTime);
            result.setResultCode(ResultCode.success.getCode());
            result.setResultDesc("审核商品成功");
        }catch (Exception e) {
            result.setResultCode(ResultCode.failed.getCode());
            result.setResultDesc("审核商品失败");
            LogCvt.error("商品审核失败，原因:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession)  
                sqlSession.close();  
        }
        return result;
	}

	@Override
	public List<VipProduct> findVipProductByList(VipProduct vipProduct) {
		List<VipProduct> vip=null;
        SqlSession sqlSession = null;
        try{
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            VipProductMapper vipProductMapper = sqlSession.getMapper(VipProductMapper.class);
            long startTime = System.currentTimeMillis();
            vip= vipProductMapper.findVipProductByList(vipProduct);
            long endTime = System.currentTimeMillis();
            LogCvt.info("-------mapper中deleteProductTemp:耗时"+(endTime - startTime)+"--startTime:"+startTime+"---endTime:"+endTime);

        }catch (Exception e) {
            LogCvt.error("商品审核失败，原因:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession)  
                sqlSession.close();  
        }
        return vip;
	}

	@Override
	public ProductCategory findProductCategoryById(Long id) {
		ProductCategory productCategory=null;
        SqlSession sqlSession = null;
        try{
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            ProductCategoryMapper productCategoryMapper = sqlSession.getMapper(ProductCategoryMapper.class);
            long startTime = System.currentTimeMillis();
            productCategory= productCategoryMapper.findProductCategoryById(id);
            long endTime = System.currentTimeMillis();
            LogCvt.info("-------mapper中deleteProductTemp:耗时"+(endTime - startTime)+"--startTime:"+startTime+"---endTime:"+endTime);

        }catch (Exception e) {
            LogCvt.error("商品审核失败，原因:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession)  
                sqlSession.close();  
        }
        return productCategory;
	}

}
