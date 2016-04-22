package com.froad.fft.persistent.api;
/**
 * 文件名称:PresellDeliveryMapper.java
 * 文件描述: 预售提货点mappler接口定义
 * 产品标识: fft
 * 单元描述: fft-president-api
 * 编写人: gq.hou_Mimosa
 * 编写时间: 14-3-24
 * 历史修改:2014-3-24
 */
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.PresellDelivery;

import java.util.List;

public interface PresellDeliveryMapper {
	
    /**
       * *******************************************************
       * <p> 描述: *-- <b>保存MerchantGroupUser</b> --* </p>
       * <p> 作者: 侯国权 </p>
       * <p> 时间: 2014年1月3日 下午4:29:27 </p>
       * <p> 版本: 1.0.1 </p>
       * ********************************************************
       */
      public Long savePresellDelivery(PresellDelivery temp);

      /**
       * *******************************************************
       * <p> 描述: *-- <b>通过主键更新</b> --* </p>
       * <p> 作者: 侯国权 </p>
       * <p> 时间: 2014年1月6日 上午9:59:25 </p>
       * <p> 版本: 1.0.1 </p>
       * ********************************************************
       */
      public Boolean updatePresellDeliveryById(PresellDelivery temp);

      /**
       * *******************************************************
       * <p> 描述: *-- <b>通过主键获取对象</b> --* </p>
       * <p> 作者: 侯国权 </p>
       * <p> 时间: 2014年1月6日 上午10:18:23 </p>
       * <p> 版本: 1.0.1 </p>
       * ********************************************************
       */
      public PresellDelivery selectPresellDeliveryById(Long id);

      /**
       * 分页查询
       *
       * @param page
       * @return
       */
      public List<PresellDelivery> selectPresellDeliveryByPage(Page page);
      
      public Integer selectPresellDeliveryByPageCount(Page page);
}
