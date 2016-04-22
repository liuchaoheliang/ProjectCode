/**
 * 文件名称:ProductPresellExportService.java
 * 文件描述: 商品预售export接口定义
 * 产品标识: fft
 * 单元描述: fft-api
 * 编写人: houguoquan_Aides
 * 编写时间: 14-3-27
 * 历史修改:  
 */
package com.froad.fft.api.service;

import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.dto.*;
import com.froad.fft.enums.*;

import java.util.List;
import java.util.Map;

/**
 * 商品预售export接口定义
 *
 * @author houguoquan_Aides
 * @version 1.0
 */
public interface ProductPresellExportService
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
    public Long saveProductPresell(ClientAccessType clientAccessType, ClientVersion clientVersion, ProductPresellDto dto) throws FroadException;

    /**
     * 增加
     *
     * @param clientAccessType 应用端类型
     * @param clientVersion    应用端版本号
     * @param dto              保存数据实体
     * @return 数据实体Id
     * @throws com.froad.fft.bean.FroadException
     */
    public Boolean updateProductPresellById(ClientAccessType clientAccessType, ClientVersion clientVersion, ProductPresellDto dto) throws FroadException;

    /**
     * 根据ID 查找广告位
     *
     * @param clientAccessType 应用端类型
     * @param clientVersion    应用端版本号
     * @param id               数据实体Id
     * @return 对饮该数据Id的实体
     */
    public ProductPresellDto findProductPresellById(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id) throws FroadException;

    /**
     * 方法描述：分页查询
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 侯国权
     * @time: 2014年3月28日 下午12:06:13
     */
    public PageDto<ProductPresellDto> findProductPresellByPage(ClientAccessType clientAccessType, ClientVersion clientVersion, PageDto<ProductPresellDto> pageDto) throws FroadException;

    /**
     * 根据商品Id查询预售交易列表
     *
     * @param productId 商品Id 而非预售Id
     * @return
     */
    public List<Map> findPresellTransByProductId(ClientAccessType clientAccessType, ClientVersion clientVersion, Long productId);

    /**
     * 根据商品Id查询预售交易列表统计
     *
     * @param productId 商品Id 而非预售Id
     * @return
     */
    public List<Map> findPresellTransStatisticByProductId(ClientAccessType clientAccessType, ClientVersion clientVersion, Long productId);

    /**
     * 获取所有未绑定预售商品
     *
     * @return
     */
    public List<ProductPresellDto> findAllUnBindProductPresell(ClientAccessType clientAccessType, ClientVersion clientVersion);

    /**
     * 根据商品Id获取预售实体
     *
     * @param clientAccessType
     * @param clientVersion
     * @param id
     * @return
     * @throws FroadException
     */
    public ProductPresellDto findByProductId(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id) throws FroadException;

}
