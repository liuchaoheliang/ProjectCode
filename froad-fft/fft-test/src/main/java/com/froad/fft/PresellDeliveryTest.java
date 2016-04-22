/**
 * 文件名称:PresellDeliveryTest.java
 * 文件描述: 自提点测试
 * 产品标识: fft
 * 单元描述: fft-server
 * 编写人: houguoquan_Aides
 * 编写时间: 14-3-27
 * 历史修改:  
 */
package com.froad.fft;

import com.caucho.hessian.client.HessianProxyFactory;
import com.froad.fft.api.service.PresellDeliveryExportService;
import com.froad.fft.dto.PresellDeliveryDto;
import com.froad.fft.enums.*;
import junit.framework.TestCase;

/**
 * @author houguoquan_Aides
 * @version 1.0
 */
public class PresellDeliveryTest extends TestCase
{
    /**
     * 测试商圈的新增保存方法
     */
    public void testSave()
    {
        String url_3 = "http://localhost:8080/api/froad-fft/service/presellDeliveryExportService";
        HessianProxyFactory factory = new HessianProxyFactory();

        PresellDeliveryDto dto = new PresellDeliveryDto();


        long clientId = 10;
        long businessCircleId = 100;
        dto.setName("test Nam1e");
        dto.setAddress("des1ert Land");
        dto.setTelephone("112321190393");
        dto.setBusinessTime("1109000-090100");
        dto.setCoordinate("2,2");
        dto.setOrderValue(20);
        dto.setClientId(clientId);
        dto.setBusinessCircleId(businessCircleId);
//        dto.setDataState(DataState.enable);

        try
        {
            PresellDeliveryExportService service = (PresellDeliveryExportService) factory.create(PresellDeliveryExportService.class, url_3);
            Long id = service.savePresellDelivery(ClientAccessType.management, ClientVersion.version_1_0, dto);
            System.out.print(id);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void testSelectByIdAndUpdateById()
    {
        String url_3 = "http://localhost:8080/api/froad-fft/service/presellDeliveryExportService";
        HessianProxyFactory factory = new HessianProxyFactory();

        try
        {
            PresellDeliveryExportService service = (PresellDeliveryExportService) factory.create(PresellDeliveryExportService.class, url_3);
            long _id = 1;
            PresellDeliveryDto _dto1 = service.findPresellDeliveryById(ClientAccessType.management, ClientVersion.version_1_0, _id);
            System.out.println(_dto1.getName());

            _dto1.setAddress("This is a new Address");
            Boolean updateFlag = service.updatePresellDeliveryById(ClientAccessType.management, ClientVersion.version_1_0, _dto1);
            System.out.println(updateFlag);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
