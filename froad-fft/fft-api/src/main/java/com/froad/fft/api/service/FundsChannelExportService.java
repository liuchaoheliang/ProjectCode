/**
 * 文件名称:FundsChannelExportService.java
 * 文件描述: 资金渠道对外接口定义
 * 产品标识: fft
 * 单元描述: fft-api
 * 编写人: houguoquan_Aides
 * 编写时间: 14-4-2
 * 历史修改:  
 */
package com.froad.fft.api.service;

import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.dto.FundsChannelDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;

/**
 * 资金渠道对外接口
 *
 * @author houguoquan_Aides
 * @version 1.0
 */
public interface FundsChannelExportService
{
    /**
       * 增加
       *
       * @param clientAccessType
       * @param clientVersion
       * @param fundsChannelDto
       * @return
       * @throws com.froad.fft.bean.FroadException
       */
      public Long addFundsChannel(ClientAccessType clientAccessType, ClientVersion clientVersion, FundsChannelDto fundsChannelDto) throws FroadException;

      /**
       * 方法描述：
       *
       * @param:
       * @return:
       * @version: 1.0
       * @author: 侯国权
       * @time: 2014年4月1日 愚人节快乐
       */
      public Boolean updateFundsChannelById(ClientAccessType clientAccessType, ClientVersion clientVersion,FundsChannelDto fundsChannelDto)throws FroadException;

      /**
       * 方法描述：
       *
       * @param:
       * @return:
       * @version: 1.0
       * @author: 侯国权
       * @time: 2014年4月1日 愚人节快乐
       */
      public FundsChannelDto findFundsChannelById(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id)throws FroadException;

      /**
       * 方法描述：分页查询
       *
       * @param:
       * @return:
       * @version: 1.0
       * @author: 侯国权
       * @time: 2014年4月1日 愚人节快乐
       */
      public PageDto<FundsChannelDto> findFundsChannelByPage(ClientAccessType clientAccessType, ClientVersion clientVersion, PageDto<FundsChannelDto> pageDto)throws FroadException;




}
