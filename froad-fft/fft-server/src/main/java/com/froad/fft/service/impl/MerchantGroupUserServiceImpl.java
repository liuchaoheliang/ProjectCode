/**
 * 文件名称:MerchantGroupUserServiceImpl.java
 * 文件描述: 商户用户组接口实现
 * 产品标识: fft
 * 单元描述: fft-server
 * 编写人: houguoquan_Aides
 * 编写时间: 14-3-27
 * 历史修改:  
 */
package com.froad.fft.service.impl;

import com.froad.fft.persistent.api.MerchantGroupUserMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.MerchantGroupUser;
import com.froad.fft.service.MerchantGroupUserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author houguoquan_Aides
 * @version 1.0
 */
@Service("merchantGroupUserServiceImpl")
public class MerchantGroupUserServiceImpl implements MerchantGroupUserService
{
    private static Logger logger = Logger.getLogger(GivePointRuleServiceImpl.class);

       @Resource
       private MerchantGroupUserMapper merchantGroupUserMapper;

       public Long saveMerchantGroupUser(MerchantGroupUser temp)
       {
        return merchantGroupUserMapper.saveMerchantGroupUser(temp);
       }

       public Boolean updateMerchantGroupUserById(MerchantGroupUser temp)
       {
           if (temp.getId() == null)
           {
               logger.error("更新对象缺少必要Id字段值");
               return false;
           }
           return merchantGroupUserMapper.updateMerchantGroupUserById(temp);
       }

       public MerchantGroupUser selectMerchantGroupUserById(Long id)
       {
           if (id == null)
           {
               logger.error("查询数据id为空");
               return null;
           }
           return merchantGroupUserMapper.selectMerchantGroupUserById(id);
       }

       public List<MerchantGroupUser> selectMerchantGroupUserByPage(Page page)
       {
           return null;
       }

	@Override
	public List<MerchantGroupUser> findAllMerchantGroupUser() {
		return merchantGroupUserMapper.findAllMerchantGroupUser();
	}

	@Override
	public MerchantGroupUser findMerchantGroupUserByUserId(Long userId) {
		 if (userId == null)
         {
             logger.error("查询数据用户ID为空");
             return null;
         }
		return  merchantGroupUserMapper.findMerchantGroupUserByUserId(userId);
	}


	@Override
	public List<MerchantGroupUser> selectByConditions(
			MerchantGroupUser merchantGroupUser) {
		return merchantGroupUserMapper.selectByConditions(merchantGroupUser);
	}
}
