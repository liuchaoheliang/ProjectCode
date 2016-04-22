package com.froad.thrift.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.froad.po.mongo.ProductBuyLimit;
import com.froad.thrift.vo.BossProductInfoVo;
import com.froad.thrift.vo.BossProductVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.PlatType;
import com.froad.thrift.vo.ProductImageVo;
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
            
            TMultiplexedProtocol mp2 = new TMultiplexedProtocol(protocol,"BossProductService");
            BossProductService.Client productService = new BossProductService.Client(mp2);
            
            transport.open();
            
            PlatType platType = PlatType.findByValue(1);
            OriginVo originVo = new OriginVo();
            originVo.setPlatType(platType);
            
            addProduct(productService,originVo);//新加商品 线上积分
            
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
    public static void updateProduct(BossProductService.Client service2,OriginVo originVo) {
        try {
            BossProductInfoVo productInfoVo = generateProduct2();//预售商品
            productInfoVo.getProduct().setName("ya测试vip预售t");
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
    public static void addProduct(BossProductService.Client service2,OriginVo originVo) {
        try {
            BossProductInfoVo productInfoVo = generateProduct2();//预售商品
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
    private static BossProductInfoVo generateProduct2(){
      //商品基础信息
        BossProductVo product = new BossProductVo();
        product.setClientId("anhui");
        product.setName("d测试vip预售");
        product.setFullName("d测试vip预售商品绑定");
        product.setOrgCode("340000");
        product.setType("2");
        product.setDeliveryOption("0");
        product.setPrice(1.0);
        product.setMarketPrice(12.0);
        product.setStore(100);
        product.setBriefIntroduction("测试vip预售商品绑定vip规则dd");
        product.setIntroduction("测试vip预售商品绑定vip规则dd");
        product.setBuyKnow("测试vip预售商品绑定vip规则dd");
        product.setStartTime(1436932800000L);
        product.setEndTime(1439611200000L);
        product.setExpireStartTime(1436932800000L);
        product.setExpireEndTime(1439611200000L);
        
        product.setDeliveryStartTime(1436932800000L);
        product.setDeliveryEndTime(1439611200000L);
        
        product.setMax(10);
        
        //商品图片列表
        List<ProductImageVo> images = new ArrayList<ProductImageVo>();
        ProductImageVo image1 = new ProductImageVo();
        image1.setLarge("");
        image1.setMedium("");
        image1.setSource("");
        image1.setThumbnail("");
        image1.setTitle("");
        images.add(image1);
                
        BossProductInfoVo productInfoVo = new BossProductInfoVo();
        
        productInfoVo.setProduct(product);
        productInfoVo.setImage(images);
        
        return productInfoVo;
    }
    
    
}
