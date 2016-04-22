/**
 * 文件名称:ProductGroupExportService.java
 * 文件描述: 团购对外暴露接口
 * 产品标识: fft
 * 单元描述: fft-api
 * 编写人: houguoquan_Aides
 * 编写时间: 14-4-9
 * 历史修改:  
 */
package com.froad.fft.api.service;

import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.dto.ProductGroupDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;

/**
 * 团购对外暴露接口
 *
 * @author houguoquan_Aides
 * @version 1.0
 */
public interface ProductGroupExportService
{
    /**
        * 增加
        *
        * @param clientAccessType 应用端类型
        * @param clientVersion    应用端版本号
        * @param dto              保存数据实体
        * @return 数据实体Id
        * @throws com.froad.fft.bean.FroadException
        */
       public Long saveProductGroup(ClientAccessType clientAccessType, ClientVersion clientVersion, ProductGroupDto dto) throws FroadException;

       /**
        * 增加
        *
        * @param clientAccessType 应用端类型
        * @param clientVersion    应用端版本号
        * @param dto              保存数据实体
        * @return 数据实体Id
        * @throws com.froad.fft.bean.FroadException
        */
       public Boolean updateProductGroupById(ClientAccessType clientAccessType, ClientVersion clientVersion, ProductGroupDto dto) throws FroadException;

       /**
        * 根据ID 查找广告位
        *
        * @param clientAccessType 应用端类型
        * @param clientVersion    应用端版本号
        * @param id               数据实体Id
        * @return 对饮该数据Id的实体
        */
       public ProductGroupDto findProductGroupById(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id) throws FroadException;

       /**
        * 方法描述：分页查询
        *
        * @param:
        * @return:
        * @version: 1.0
        * @author: 侯国权
        * @time: 2014年3月28日 下午12:06:13
        */
       public PageDto<ProductGroupDto> findProductGroupByPage(ClientAccessType clientAccessType, ClientVersion clientVersion, PageDto<ProductGroupDto> pageDto) throws FroadException;


}
