/**
 * 文件名称:BusinessCircleTest.java
 * 文件描述: 商圈测试
 * 产品标识: 分分通
 * 单元描述: fft-test
 * 编写人: gq.hou_Mimosa
 * 编写时间: 14-3-26
 * 历史修改:  
 */
package com.froad.fft;

import com.caucho.hessian.client.HessianProxyFactory;
import com.froad.fft.api.service.*;
import com.froad.fft.dto.BusinessCircleDto;
import com.froad.fft.enums.*;
import junit.framework.TestCase;

/**
 * @author Aides
 * @version 1.0
 */
public class BusinessCircleTest extends TestCase
{

    /**
     * 测试商圈的新增保存方法
     */
    public void testSave()
    {
        String url_3 = "http://localhost:8080/api/froad-fft/service/businessCircleExportService";
        HessianProxyFactory factory = new HessianProxyFactory();

        BusinessCircleDto dto = new BusinessCircleDto();
        long parentId = 1110;
        long areaId = 1011;
        dto.setName("ce11s11hi");
        dto.setOrderValue(1110);
        dto.setTreePath("11aa/aaq11/aa");
        dto.setParentId(parentId);
        dto.setAreaId(areaId);
        try
        {
            BusinessCircleExportService service = (BusinessCircleExportService) factory.create(BusinessCircleExportService.class, url_3);
            Long id = service.saveBusinessCircle(ClientAccessType.management, ClientVersion.version_1_0, dto);
            System.out.print(id);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void testSelectByIdAndUpdateById()
    {
        String url_3 = "http://localhost:8080/api/froad-fft/service/businessCircleExportService";
        HessianProxyFactory factory = new HessianProxyFactory();

        try
        {
            BusinessCircleExportService service = (BusinessCircleExportService) factory.create(BusinessCircleExportService.class, url_3);

            long _id = 10;
            BusinessCircleDto _dto1 = service.findBusinessCircleById(ClientAccessType.management, ClientVersion.version_1_0, _id);
            System.out.println(_dto1.getName());
            _dto1.setName("wo gai xing le bu");
            Boolean updateFlag = service.updateBusinessCircleById(ClientAccessType.management, ClientVersion.version_1_0, _dto1);
            System.out.println(updateFlag);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
