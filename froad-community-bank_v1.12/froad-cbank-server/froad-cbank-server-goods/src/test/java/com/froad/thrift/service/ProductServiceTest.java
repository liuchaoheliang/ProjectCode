package com.froad.thrift.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.froad.po.mongo.ProductBuyLimit;
import com.froad.thrift.vo.FiledSort;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.OutletVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.PlatType;
import com.froad.thrift.vo.ProductBuyLimitVo;
import com.froad.thrift.vo.ProductFilterVoReq;
import com.froad.thrift.vo.ProductImageVo;
import com.froad.thrift.vo.ProductInfoVo;
import com.froad.thrift.vo.ProductOperateVoReq;
import com.froad.thrift.vo.ProductVo;
import com.froad.thrift.vo.QueryProductFilterVo;
import com.froad.util.DateUtil;

public class ProductServiceTest {

    public static void main(String[] args) {
        TSocket transport = null;
        try {
          transport = new TSocket("localhost", 15401);
//          TSocket transport = new TSocket("10.43.2.3", 15401);
//          TSocket transport = new TSocket("10.43.1.9", 15401);
//            transport = new TSocket("10.43.2.238", 15401);
//          transport = new TSocket("10.43.1.123", 15401);
//          transport = new TSocket("10.43.1.121", 9093);
            TBinaryProtocol protocol = new TBinaryProtocol(transport);
            
            TMultiplexedProtocol mp2 = new TMultiplexedProtocol(protocol,"ProductService");
            ProductService.Client productService = new ProductService.Client(mp2);
            
            transport.open();
            
            PlatType platType = PlatType.findByValue(1);
            OriginVo originVo = new OriginVo();
            originVo.setPlatType(platType);
            
//            OutletVo outletVo = new OutletVo();
//            outletVo.setOutletId("05C2AEB08008e");
//            outletVo.setAddress("宣城市宣州区国购广场八佰伴1楼（进门左侧）");
//            outletVo.setOutletName("罗蒂爸爸");
//            outletVo.setAreaId(1978);
//            outletVo.setMerchantId("05C2A1F08019");
//            outletVo.setPhone("18505638899");
//            System.out.println(productService.updateGroupProductByOutlet("", "2", outletVo));
            
            try {
                PageVo pageVo = new PageVo();
                pageVo.setPageNumber(1);
                pageVo.setPageSize(5);
                QueryProductFilterVo filterVo = new QueryProductFilterVo();
                filterVo.setClientId("anhui");
                filterVo.setAreaId(2385L);//1889L
                filterVo.setLatitude(31.9486342961);//纬度
                filterVo.setLongitude(118.7154288854);//经度
//                filterVo.setClientId("anhui");
//                filterVo.setAreaId(2507L);
//                filterVo.setLatitude(31.230638971054);//纬度
//                filterVo.setLongitude(121.55963600779);//经度
                
//                filterVo.setDistance(1000.0);//距离
//                filterVo.setProductCategoryId(100000053L);
                filterVo.setType("1");
//                filterVo.setProductName("团购");
//                {"areaId":0,"clientId":"anhui","distance":0,"isRecommend":true,"latitude":31.230638971054,"longitude":121.55963600779,"productCategoryId":0,
//                    ,"type":"1"}
              
//                productCategoryId商品分类ID
//                productName商品名
                List<FiledSort> sort = new ArrayList<FiledSort>();
                FiledSort s = new FiledSort();
                s.setSortName("distance");
                s.setSortBy(-1);
                sort.add(s);
                
                System.out.println(productService.searchProductByType(filterVo, sort, pageVo));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
//            
            
//            System.out.println(productService.updateHisGroupGoodsOutlets());
            
//            System.out.println("startTime:"+DateUtil.parse(DateUtil.DATE_TIME_FORMAT2,"2015-07-15 12:00:00").getTime());
//            System.out.println("endTime:"+DateUtil.parse(DateUtil.DATE_TIME_FORMAT2,"2015-08-15 12:00:00").getTime());
            
//            findMerchantProductsByPage(productService);//管理平台查询商品列表
//            queryProductDetail(productService);//H5详情
//            getMerchantProductDetail(productService);//PC详情
//            queryGroupProducts(productService);//特惠商品列表
//          addProduct(productService,originVo);//新加商品 线上积分
//            updateProduct(productService,originVo);//修改商品

        }catch (Exception e){
            e.printStackTrace();
        } finally {
            if(null != transport){
                transport.close();
            }
        }
    }
    
    /**
     * 修改商品
     * @param service2
     * @param originVo
     * @return void
     */
    public static void updateProduct(ProductService.Client service2,OriginVo originVo) {
        try {
            ProductInfoVo productInfoVo = generateProduct2();//预售商品
            productInfoVo.getProduct().setName("ya测试vip预售t");
            productInfoVo.getProduct().setIsLimit(true);
            productInfoVo.getProduct().setProductId("093EE9408000");
            System.out.println(service2.updateProduct(originVo, productInfoVo));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * 新加商品
     * @param service2
     * @param originVo
     * @return void
     */
    public static void addProduct(ProductService.Client service2,OriginVo originVo) {
        try {
            ProductInfoVo productInfoVo = generateProduct2();//预售商品
            System.out.println(service2.addProduct(originVo, productInfoVo));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * 预售商品
     * @return
     * @return ProductInfoVo
     */
    private static ProductInfoVo generateProduct2(){
      //商品基础信息
        ProductVo product = new ProductVo();
        product.setClientId("anhui");
        product.setName("d测试vip预售");
        product.setFullName("d测试vip预售商品绑定");
        //`merchant_id`, `org_code`
        product.setOrgCode("340000");
        product.setType("2");
        product.setDeliveryOption("0");
        product.setPrice(1.0);
        product.setMarketPrice(12.0);
        product.setStore(100);
        product.setIsSeckill("0");
        product.setIsLimit(false);
        product.setIsBest(false);
        product.setBriefIntroduction("测试vip预售商品绑定vip规则dd");
        product.setIntroduction("测试vip预售商品绑定vip规则dd");
        product.setBuyKnow("测试vip预售商品绑定vip规则dd");
        product.setAuditOrgCode("340101");
        product.setAuditStartOrgCode("340101");
        product.setAuditEndOrgCode("340000");
        product.setStartTime(1436932800000L);
        product.setEndTime(1439611200000L);
        product.setExpireStartTime(1436932800000L);
        product.setExpireEndTime(1439611200000L);
        
        product.setDeliveryStartTime(1436932800000L);
        product.setDeliveryEndTime(1439611200000L);
        
        //商品图片列表
        List<ProductImageVo> images = new ArrayList<ProductImageVo>();
        ProductImageVo image1 = new ProductImageVo();
        image1.setLarge("");
        image1.setMedium("");
        image1.setSource("");
        image1.setThumbnail("");
        image1.setTitle("");
        images.add(image1);
                
        ProductInfoVo productInfoVo = new ProductInfoVo();
        ProductBuyLimitVo buyLimit = new ProductBuyLimitVo();
        buyLimit.setMax(10);
        productInfoVo.setBuyLimit(buyLimit);
        productInfoVo.setProduct(product);
        productInfoVo.setImage(images);
        
        return productInfoVo;
    }
    
    /**
     * 线下积分商品
     * @return
     * @return ProductInfoVo
     */
    private static ProductInfoVo generateProduct4(){
      //商品基础信息
        ProductVo product = new ProductVo();
        product.setClientId("anhui");
        product.setName("");
        product.setFullName("");
        //`merchant_id`, `org_code`
        product.setType("4");
        product.setDeliveryOption("0");
        product.setPrice(0.0);
        product.setMarketPrice(0.0);
        product.setStore(0);
        product.setIsSeckill("0");
        product.setIsLimit(false);
        product.setIsBest(false);
        product.setBriefIntroduction("");
        product.setIntroduction("");
        product.setBuyKnow("");
        product.setAuditOrgCode("0");
        product.setAuditStartOrgCode("0");
        product.setAuditEndOrgCode("0");
        product.setStartTime(0L);
        product.setEndTime(0L);
        
        //商品图片列表
        List<ProductImageVo> images = new ArrayList<ProductImageVo>();
        ProductImageVo image1 = new ProductImageVo();
        image1.setLarge("");
        image1.setMedium("");
        image1.setSource("");
        image1.setThumbnail("");
        image1.setTitle("");
        images.add(image1);
                
        
        ProductInfoVo productInfoVo = new ProductInfoVo();
        productInfoVo.setProduct(product);
        productInfoVo.setImage(images);
        
        return productInfoVo;
    }
    
    /**
     * PC查询详情
     * @param productService
     * @return void
     */
    private static void getMerchantProductDetail(ProductService.Client productService){
        try {
            ProductOperateVoReq productVoReq = new ProductOperateVoReq();
            productVoReq.setProductId("0850DE428000");
            System.out.println(productService.getMerchantProductDetail(productVoReq));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * H5查询详情
     * @param productService
     * @return void
     */
    private static void queryProductDetail(ProductService.Client productService){
        try {
            ProductOperateVoReq productVoReq = new ProductOperateVoReq();
            productVoReq.setProductId("0850DE428000");
            System.out.println(productService.queryProductDetail(productVoReq));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * H5特惠列表
     * @param productService
     * @return void
     */
    private static void queryGroupProducts(ProductService.Client productService){
        try {
            PageVo pageVo = new PageVo();
            pageVo.setPageNumber(1);
            pageVo.setPageSize(5);
            QueryProductFilterVo filterVo = new QueryProductFilterVo();
            filterVo.setClientId("anhui");
            filterVo.setAreaId(2507L);
//            filterVo.setLatitude(31.230638971054);//纬度
//            filterVo.setLongitude(121.55963600779);//经度
//            filterVo.setDistance(1000.0);//距离
//            filterVo.setProductCategoryId(100000053L);
            filterVo.setType("1");
//            filterVo.setProductName("团购");
//            {"areaId":0,"clientId":"anhui","distance":0,"isRecommend":true,"latitude":31.230638971054,"longitude":121.55963600779,"productCategoryId":0,
//                ,"type":"1"}
          
//            productCategoryId商品分类ID
//            productName商品名
            System.out.println(productService.queryGroupProducts(filterVo, null, pageVo));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    
    /**
     * H5预售推荐列表
     * @param productService
     * @return void
     */
    private static void queryVipPresellProducts(ProductService.Client productService){
        try {
            ProductFilterVoReq productFilterVoReq = new ProductFilterVoReq();
            PageVo pageVo = new PageVo();
            pageVo.setPageSize(10);
            pageVo.setPageNumber(1);
            System.out.println(productService.queryVipPresellProducts(productFilterVoReq, pageVo));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * 管理平台查询商品列表
     * @param productService
     * @return void
     */
    private static void findMerchantProductsByPage(ProductService.Client productService){
        try {
            PageVo pageVo = new PageVo();
            pageVo.setPageNumber(1);
            pageVo.setPageSize(10);
            ProductFilterVoReq productFilterVoReq = new ProductFilterVoReq(); 
            productFilterVoReq.setClientId("anhui");
            String filter="{\"type\":\"1\",\"categoryId\":100000053}";
            productFilterVoReq.setFilter(filter);
            System.out.println(productService.findMerchantProductsByPage(productFilterVoReq, pageVo));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
