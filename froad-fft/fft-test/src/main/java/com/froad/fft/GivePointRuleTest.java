/**
 * 文件名称:GivePointRuleTest.java
 * 文件描述: 送积分规则测试
 * 产品标识: fft
 * 单元描述: fft-test
 * 编写人: houguoquan_Aides
 * 编写时间: 14-3-27
 * 历史修改:  
 */
package com.froad.fft;

import com.caucho.hessian.client.HessianProxyFactory;
import com.froad.fft.api.service.GivePointRuleExportService;
import com.froad.fft.dto.GivePointRuleDto;
import com.froad.fft.enums.*;
import junit.framework.TestCase;

import java.util.Date;

/**
 * @author houguoquan_Aides
 * @version 1.0
 */
public class GivePointRuleTest extends TestCase
{
    /**
      * 测试商圈的新增保存方法
      */
     public void testSave()
     {
         String url_3 = "http://localhost:8080/api/froad-fft/service/givePointRuleExportService";
         HessianProxyFactory factory = new HessianProxyFactory();

         GivePointRuleDto dto = new GivePointRuleDto();
         com.froad.fft.dto.GivePointRuleDto.Type type =  GivePointRuleDto.Type.fixed;

         dto.setName("ce11s11hi22");
         dto.setExpireTime(new Date());
         dto.setActiveTime(new Date());
         dto.setPointValue("2");
         dto.setRemark("remark22");
         dto.setType(type);
         try
         {
             GivePointRuleExportService service = (GivePointRuleExportService) factory.create(GivePointRuleExportService.class, url_3);
             Long id = service.saveGivePointRule(ClientAccessType.management, ClientVersion.version_1_0, dto);
             System.out.print(id);
         }
         catch (Exception e)
         {
             e.printStackTrace();
         }
     }

     public void testSelectByIdAndUpdateById()
     {
         String url_3 = "http://localhost:8080/api/froad-fft/service/givePointRuleExportService";
         HessianProxyFactory factory = new HessianProxyFactory();

         try
         {
             GivePointRuleExportService service = (GivePointRuleExportService) factory.create(GivePointRuleExportService.class, url_3);
             long _id = 2;
             GivePointRuleDto _dto1 = service.findGivePointRuleById(ClientAccessType.management, ClientVersion.version_1_0, _id);
             System.out.println(_dto1.getName());
             _dto1.setName("wo gai xing le bu");
             Boolean updateFlag = service.updateGivePointRuleById(ClientAccessType.management, ClientVersion.version_1_0, _dto1);
             System.out.println(updateFlag);
         }
         catch (Exception e)
         {
             e.printStackTrace();
         }
     }



}
