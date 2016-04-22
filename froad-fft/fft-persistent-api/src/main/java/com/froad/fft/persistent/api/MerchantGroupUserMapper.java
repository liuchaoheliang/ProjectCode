/**
 * 文件名称:MerchantGroupUserMapper.java
 * 文件描述: 商户的组用户mappler接口定义
 * 产品标识: fft
 * 单元描述: fft-president-api
 * 编写人: gq.hou_Mimosa
 * 编写时间: 14-3-24
 * 历史修改: 2014-3-24 by gq.hou_Mimosa
 */
package com.froad.fft.persistent.api;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.MerchantGroupUser;

import java.util.List;

public interface MerchantGroupUserMapper
{
    /**
           * *******************************************************
           * <p> 描述: *-- <b>保存GivePointRule</b> --* </p>
           * <p> 作者: 侯国权 </p>
           * <p> 时间: 2014年1月3日 下午4:29:27 </p>
           * <p> 版本: 1.0.1 </p>
           * ********************************************************
           */
          public Long saveMerchantGroupUser( MerchantGroupUser temp);

          /**
           * *******************************************************
           * <p> 描述: *-- <b>通过主键更新</b> --* </p>
           * <p> 作者: 侯国权 </p>
           * <p> 时间: 2014年1月6日 上午9:59:25 </p>
           * <p> 版本: 1.0.1 </p>
           * ********************************************************
           */
          public Boolean updateMerchantGroupUserById( MerchantGroupUser temp);

          /**
           * *******************************************************
           * <p> 描述: *-- <b>通过主键获取对象</b> --* </p>
           * <p> 作者: 侯国权 </p>
           * <p> 时间: 2014年1月6日 上午10:18:23 </p>
           * <p> 版本: 1.0.1 </p>
           * ********************************************************
           */
          public  MerchantGroupUser selectMerchantGroupUserById(Long id);

          /**
           * 分页查询
           *
           * @param page
           * @return
           */
          public List< MerchantGroupUser> selectMerchantGroupUserByPage(Page page);

          /**
          *<p>所有商户用户组</p>
          * @author larry
          * @datetime 2014-4-2下午03:22:04
          * @return List<MerchantGroupUser>
          * @throws 
          * example<br/>
           */
		public List<MerchantGroupUser> findAllMerchantGroupUser();

		/**
		*<p>根据用户ID查询用户商户组</p>
		* @author larry
		* @datetime 2014-4-2下午06:20:14
		* @return MerchantGroupUser
		* @throws 
		* example<br/>
		*
		 */
		public MerchantGroupUser findMerchantGroupUserByUserId(Long userId);
		
		
		/**
		  * 方法描述：条件查询
		  * @param: 
		  * @return: 
		  * @version: 1.0
		  * @author: 刘超 liuchao@f-road.com.cn
		  * @time: 2014年4月8日 下午5:33:22
		  */
		public List<MerchantGroupUser> selectByConditions(MerchantGroupUser merchantGroupUser);
}
