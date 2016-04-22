/**
 * 文件名称:TestMerchantAccount.java
 * 文件描述: todo
 * 产品标识: todo
 * 单元描述: todo
 * 编写人: houguoquan_Aides
 * 编写时间: 14-4-1
 * 历史修改:  
 */
package com.froad.fft.test.remoteservice;

import com.froad.fft.api.service.MerchantAccountExportService;
import com.froad.fft.bean.page.*;
import com.froad.fft.dto.MerchantAccountDto;
import com.froad.fft.enums.AccountType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author houguoquan_Aides
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:context/servlet/applicationContext-mapper-remote.xml", "classpath:context/servlet/applicationContext-service-remote.xml",
        "classpath:context/applicationContext.xml", "classpath:context/applicationContext-expand.xml"})

public class TestMerchantAccount
{
    @Resource
    MerchantAccountExportService merchantAccountExportService;

    @Test
    public void insert()
    {
        MerchantAccountDto dto = new MerchantAccountDto();
        dto.setAcctName("测试分店");
        dto.setAcctNumber("asccount");
        dto.setIsEnabled(Boolean.TRUE);
        dto.setFundsChannelId(Long.valueOf(1));
        dto.setAcctType(AccountType.deduct);
        dto.setMerchantId(Long.valueOf(1));



        Long Id = merchantAccountExportService.addMerchantAccount(null, null, dto);
        System.out.println(Id);
    }

    @Test
    public void page()
    {
        PageDto page = new PageDto();
        PageFilterDto<MerchantAccountDto> pageFilter = new PageFilterDto<MerchantAccountDto>();

        MerchantAccountDto dto = new MerchantAccountDto();

        pageFilter.setFilterEntity(dto);
        page.setPageSize(5);
        page.setPageFilterDto(pageFilter);

        page = merchantAccountExportService.findMerchantAccountByPage(null, null, page);
        System.out.println(page.getResultsContent().size() + "条数：" + page.getTotalCount());

    }


}
