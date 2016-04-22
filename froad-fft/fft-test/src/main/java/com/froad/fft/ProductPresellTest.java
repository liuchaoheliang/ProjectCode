/**
 * 文件名称:ProductPresellTest.java
 * 文件描述: todo
 * 产品标识: todo
 * 单元描述: todo
 * 编写人: houguoquan_Aides
 * 编写时间: 14-3-27
 * 历史修改:  
 */
package com.froad.fft;

import com.caucho.hessian.client.HessianProxyFactory;
import com.froad.fft.api.service.ProductPresellExportService;
import com.froad.fft.dto.ProductPresellDto;
import com.froad.fft.enums.*;
import junit.framework.TestCase;

import java.util.Date;

/**
 * @author houguoquan_Aides
 * @version 1.0
 */
public class ProductPresellTest extends TestCase
{

    /**
     * 测试商圈的新增保存方法
     */

    public void testSave()
    {
        String url_3 = "http://localhost:8080/api/froad-fft/service/productPresellExportService";
        HessianProxyFactory factory = new HessianProxyFactory();
        String title = "预售标题";// 预售标题
        String summary = "预售商品简介";// 预售商品简介
        Integer perNumber = 10; // 每人限购
        Integer perminNumber = 1;// 最低购买
        Date startTime = new Date();// 预售-开始
        Date endTime = new Date();// 预售-结束
        Date deliveryStartTime = new Date();// 提货开始时间
        Date deliveryEndTime = new Date();// 提货结束时间
        String price = "10.0";// 销售价
        Integer clusteringNumber = 100; // 成功成团数量（指此次成团的商品最低销售数量）
        Integer trueBuyerNumber = 15; // 实际购买商品数量
        Integer virtualBuyerNumber = 0; // 系统添加的虚拟购买商品数量（仅手动成团会出现）
        Boolean isCluster = Boolean.FALSE; // 是否成功成团
        ProductPresellDto.ClusterType clusterType = ProductPresellDto.ClusterType.auto; // 成团类型
        String buyKnow = "购买需知";// 购买需知
        String image = "推广图片";// 展示图片
        String image2 = "详情图片";// 展示图片
        String description = "描述";// 描述
        Integer orderValue = 10;//排序

        ProductPresellDto dto = new ProductPresellDto();

        dto.setTitle(title);
        dto.setSummary(summary);
        dto.setPerNumber(perNumber);
        dto.setPerminNumber(perminNumber);
        dto.setStartTime(startTime);
        dto.setEndTime(endTime);
        dto.setDeliveryStartTime(deliveryStartTime);
        dto.setDeliveryEndTime(deliveryEndTime);
//        dto.setPrice(price);
        dto.setClusteringNumber(clusteringNumber);
        dto.setTrueBuyerNumber(trueBuyerNumber);
        dto.setVirtualBuyerNumber(virtualBuyerNumber);
        dto.setClusterState(ClusterState.wait);
        dto.setClusterType(clusterType);
        dto.setBuyKnow(buyKnow);
        dto.setGeneralizeImage(image);
        dto.setDetailsImage(image2);
//        dto.setImage(image);
        dto.setDescription(description);
        dto.setOrderValue(orderValue);

        try
        {
            ProductPresellExportService service = (ProductPresellExportService) factory.create(ProductPresellExportService.class, url_3);
            Long id = service.saveProductPresell(ClientAccessType.management, ClientVersion.version_1_0, dto);
            System.out.println(id);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void testSelectByIdAndUpdateById()
    {
        String url_3 = "http://localhost:8080/api/froad-fft/service/productPresellExportService";
        HessianProxyFactory factory = new HessianProxyFactory();

        try
        {
            ProductPresellExportService service = (ProductPresellExportService) factory.create(ProductPresellExportService.class, url_3);

            long _id = 1;
            ProductPresellDto _dto1 = service.findProductPresellById(ClientAccessType.management, ClientVersion.version_1_0, _id);
            System.out.println(_dto1.getTitle());
            _dto1.setTitle("wo gai xing le bu");
            Boolean updateFlag = service.updateProductPresellById(ClientAccessType.management, ClientVersion.version_1_0, _dto1);
            System.out.println(updateFlag);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
