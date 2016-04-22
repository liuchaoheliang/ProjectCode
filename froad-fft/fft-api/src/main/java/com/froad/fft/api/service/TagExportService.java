/**
 * 文件名称:TagExportService.java
 * 文件描述: todo
 * 产品标识: todo
 * 单元描述: todo
 * 编写人: houguoquan_Aides
 * 编写时间: 14-4-15
 * 历史修改:  
 */
package com.froad.fft.api.service;

import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.dto.TagDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;

/**
 * todo:
 *
 * @author houguoquan_Aides
 * @version 1.0
 */
public interface TagExportService
{
    /**
        * 增加
        *
        * @param clientAccessType
        * @param clientVersion
        * @param tagDto
        * @return
        * @throws com.froad.fft.bean.FroadException
        */
       public Long addTag(ClientAccessType clientAccessType, ClientVersion clientVersion, TagDto tagDto) throws FroadException;
   
       /**
        * @param clientAccessType
        * @param clientVersion
        * @param pageDto
        * @return
        * @author larry
        * <p>分页查询</p>
        */
       public PageDto findTagByPage(ClientAccessType clientAccessType, ClientVersion clientVersion, PageDto pageDto) throws FroadException;
   
       /**
        * *******************************************************
        * <p> 描述: *-- <b>通过主键查询地区</b> --* </p>
        * <p> 作者: 侯国权</p>
        * <p> 时间: 2014年4月15日 下午5:27:03 </p>
        * <p> 版本: 1.0.1 </p>
        * ********************************************************
        */
       public TagDto findTagById(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id);
   
       /**
        * 增加
        *
        * @param clientAccessType 应用端类型
        * @param clientVersion    应用端版本号
        * @param dto              保存数据实体
        * @return 数据实体Id
        * @throws com.froad.fft.bean.FroadException
        */
       public Boolean updateTagById(ClientAccessType clientAccessType, ClientVersion clientVersion, TagDto dto) throws FroadException;
    
    
    
}
