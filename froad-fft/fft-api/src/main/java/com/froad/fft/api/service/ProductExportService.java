package com.froad.fft.api.service;

import java.util.List;

import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.dto.ProductDto;
import com.froad.fft.dto.ProductRestrictRuleDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;

/**
 * 商品
 *
 * @author FQ
 */
public interface ProductExportService
{

    /**
     * * 增加商品
     *
     * @param clientAccessType
     * @param clientVersion
     * @param productDto
     * @return 主键
     * @throws FroadException
     */
    public Long addProduct(ClientAccessType clientAccessType, ClientVersion clientVersion, ProductDto productDto) throws FroadException;

    /**
     * @param management
     * @param version10
     * @param pageDto    <p>分页查询商品<p>
     * @return
     * @author larry
     */
    public PageDto<ProductDto> findProductByPage(ClientAccessType management, ClientVersion version10, PageDto<ProductDto> pageDto) throws FroadException;

    /**
     * @param id <p>根据ID获得商品</p>
     * @return
     * @author larry
     */
    public ProductDto getProductById(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id) throws FroadException;

    /**
     * @param productDto <p>更新商品信息</p>
     * @return
     * @author larry
     */
    public Boolean updateProduct(ClientAccessType clientAccessType, ClientVersion clientVersion, ProductDto productDto) throws FroadException;

    /**
     * *******************************************************
     * <p> 描述: *-- <b>通过将productDto 作为条件查询product （* productDto 里面的所有字段属性将成为并列条件用于数据库查询）</b> --* </p>
     * <p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
     * <p> 时间: 2014年4月4日 上午9:46:44 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public List<ProductDto> findProductByCondition(ClientAccessType clientAccessType, ClientVersion clientVersion, ProductDto productDto) throws FroadException;

    /**
     * 查询所有未绑定限定规则的商品
     *
     * @param clientAccessType
     * @param clientVersion
     * @param dto              规则类型
     * @return
     * @throws FroadException
     */
    public List<ProductDto> findAllUnRestrictProduct(ClientAccessType clientAccessType, ClientVersion clientVersion, ProductRestrictRuleDto dto) throws FroadException;

    /**
       * 查询所有已绑定限定规则的商品
       *
       * @param clientAccessType
       * @param clientVersion
       * @param dto              规则类型
       * @return
       * @throws FroadException
       */
      public List<ProductDto> findAllRestrictProduct(ClientAccessType clientAccessType, ClientVersion clientVersion, ProductRestrictRuleDto dto) throws FroadException;

    /**
     * 查询所有已有库存的商品
     * @param clientAccessType
     * @param clientVersion
     * @return
     * @throws FroadException
     */
    public List<ProductDto> findAllProductHaveStockPile(ClientAccessType clientAccessType, ClientVersion clientVersion) throws FroadException;
}
