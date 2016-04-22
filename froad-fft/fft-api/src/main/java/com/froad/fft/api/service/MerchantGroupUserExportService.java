/**
 * 文件名称:MerchantGroupUserExportService.java
 * 文件描述: 商圈用户组接口声明
 * 产品标识: fft
 * 单元描述: fft-api
 * 编写人: houguoquan_Aides
 * 编写时间: 14-3-27
 * 历史修改:  
 */
package com.froad.fft.api.service;

import java.util.List;

import com.froad.fft.bean.FroadException;
import com.froad.fft.dto.MerchantGroupUserDto;
import com.froad.fft.enums.*;

/**
 * 商圈用户组接口声明
 *
 * @author houguoquan_Aides
 * @version 1.0
 */
public interface MerchantGroupUserExportService
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
    public Long saveMerchantGroupUser(ClientAccessType clientAccessType, ClientVersion clientVersion, MerchantGroupUserDto dto) throws FroadException;

    /**
     * 增加
     *
     * @param clientAccessType 应用端类型
     * @param clientVersion    应用端版本号
     * @param dto              保存数据实体
     * @return 数据实体Id
     * @throws com.froad.fft.bean.FroadException
     */
    public Boolean updateMerchantGroupUserById(ClientAccessType clientAccessType, ClientVersion clientVersion, MerchantGroupUserDto dto) throws FroadException;

    /**
     * 根据ID 查找广告位
     *
     * @param clientAccessType 应用端类型
     * @param clientVersion    应用端版本号
     * @param id               数据实体Id
     * @return 对饮该数据Id的实体
     */
    public MerchantGroupUserDto findMerchantGroupUserById(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id) throws FroadException;

    /**
     * 
    *<p></p>
    * @author larry
    * @datetime 2014-4-2下午03:11:47
    * @return List<MerchantGroupUserDto>
    * @throws 
    * example<br/>
    *
     */
	public List<MerchantGroupUserDto> findAllMerchantGroupUser()throws FroadException;

	/**
	*<p>根据用户ID查询商户用户组</p>
	* @author larry
	* @datetime 2014-4-2下午06:13:57
	* @return MerchantGroupUserDto
	* @throws 
	* example<br/>
	*
	 */
	public MerchantGroupUserDto findMerchantGroupUserByUserId(
			ClientAccessType management, ClientVersion version10, Long userId)throws FroadException;

	
	/**
	  * 方法描述：条件查询
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月8日 下午5:40:10
	  */
	public List<MerchantGroupUserDto> selectByConditions(
			ClientAccessType management, ClientVersion version10, MerchantGroupUserDto merchantGroupUserDto)throws FroadException;
}
