/**
 * 文件名称:TestArticleCategory.java
 * 文件描述: todo
 * 产品标识: todo
 * 单元描述: todo
 * 编写人: houguoquan_Aides
 * 编写时间: 14-4-15
 * 历史修改:  
 */
package com.froad.fft.test.remoteservice;

import com.froad.fft.api.service.ArticleCategoryExportService;
import com.froad.fft.dto.ArticleCategoryDto;
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
public class TestArticleCategory
{
    @Resource
    ArticleCategoryExportService exportService;

    @Test
    public void inset()
    {
        ArticleCategoryDto dto = new ArticleCategoryDto();
        dto.setName("测试");
        dto.setSeoTitle("seotime");
        dto.setSeoKeywords("keyword");
        dto.setSeoDescription("wenzi");
        dto.setOrderValue(1);
        dto.setTreePath("1");


        exportService.addArticleCategory(null, null, dto);

    }


}
