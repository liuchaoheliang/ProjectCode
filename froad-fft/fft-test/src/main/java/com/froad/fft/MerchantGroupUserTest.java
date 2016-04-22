/**
 * 文件名称:MerchantGroupUserTest.java
 * 文件描述: todo
 * 产品标识: todo
 * 单元描述: todo
 * 编写人: houguoquan_Aides
 * 编写时间: 14-3-27
 * 历史修改:  
 */
package com.froad.fft;

import com.caucho.hessian.client.HessianProxyFactory;
import com.froad.fft.api.service.MerchantGroupUserExportService;
import com.froad.fft.dto.MerchantGroupUserDto;
import com.froad.fft.enums.*;
import junit.framework.TestCase;

import java.util.Date;

/**
 * @author houguoquan_Aides
 * @version 1.0
 */
public class MerchantGroupUserTest extends TestCase
{
    /**
     * 测试商圈的新增保存方法
     */
    public void testSave()
    {
        String url_3 = "http://localhost:8080/api/froad-fft/service/merchantGroupUserExportService";
        HessianProxyFactory factory = new HessianProxyFactory();

        MerchantGroupUserDto dto = new MerchantGroupUserDto();

        long merchantId = 100;
        long sysUserId = 200;
        long merchantOutletId = 300;

        dto.setMerchantId(merchantId);
        dto.setSysUserId(sysUserId);
        dto.setMerchantOutletId(merchantOutletId);

        try
        {
            MerchantGroupUserExportService service = (MerchantGroupUserExportService) factory.create(MerchantGroupUserExportService.class, url_3);
            Long id = service.saveMerchantGroupUser(ClientAccessType.management, ClientVersion.version_1_0, dto);
            System.out.print(id);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void testSelectByIdAndUpdateById()
    {
        String url_3 = "http://localhost:8080/api/froad-fft/service/merchantGroupUserExportService";
        HessianProxyFactory factory = new HessianProxyFactory();

        try
        {
            MerchantGroupUserExportService service = (MerchantGroupUserExportService) factory.create(MerchantGroupUserExportService.class, url_3);
            long _id =1;
            MerchantGroupUserDto _dto1 = service.findMerchantGroupUserById(ClientAccessType.management, ClientVersion.version_1_0, _id);
            System.out.println(_dto1.getMerchantId());
            long updateMerchantOutletId = 500;
            _dto1.setMerchantOutletId(updateMerchantOutletId);
            Boolean updateFlag = service.updateMerchantGroupUserById(ClientAccessType.management, ClientVersion.version_1_0, _dto1);
            System.out.println(updateFlag);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
