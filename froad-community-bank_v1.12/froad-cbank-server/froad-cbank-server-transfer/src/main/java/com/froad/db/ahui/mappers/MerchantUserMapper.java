package com.froad.db.ahui.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.chonggou.entity.MerchantUser;
import com.froad.db.mysql.bean.Page;
import com.froad.fft.persistent.entity.MerchantOutlet;

public interface MerchantUserMapper {

	public List<MerchantOutlet> selectByProductComment();
	
	
    /**
     * 增加 MerchantUser
     * @param merchantUser
     * @return Long    主键ID
     */
	public Long addMerchantUser(MerchantUser merchantUser);



    /**
     * 批量增加 MerchantUser
     * @param List<MerchantUser>
     * @return Boolean    是否成功
     */
	public Boolean addMerchantUserByBatch(List<MerchantUser> merchantUserList);



    /**
     * 删除 MerchantUser
     * @param merchantUser
     * @return Boolean    是否成功
     */
	public Boolean deleteMerchantUser(MerchantUser merchantUser);



    /**
     * 修改 MerchantUser
     * @param merchantUser
     * @return Boolean    是否成功
     */
	public Boolean updateMerchantUser(MerchantUser merchantUser);

	public Boolean updateMerchantUser2(MerchantUser merchantUser);

    /**
     * 查询一个 MerchantUser
     * @param merchantUser
     * @return MerchantUser    返回结果
     */
	public MerchantUser findMerchantUserById(Long id);



    /**
     * 查询 MerchantUser
     * @param merchantUser
     * @return List<MerchantUser>    返回结果集
     */
	public List<MerchantUser> findMerchantUser(MerchantUser merchantUser);



    /**
     * 分页查询 MerchantUser
     * @param page 
     * @param merchantUser
     * @return List<MerchantUser>    返回分页查询结果集
     */
	public List<MerchantUser> findByPage(Page page, @Param("merchantUser")MerchantUser merchantUser);


	 /**
     * 查询 MerchantUser - 根据 username
     * @param username 
     * @return MerchantUser
     */
	public MerchantUser findMerchantUserByUsername(String username);
	
	/**
     * 查询 MerchantUser - 根据 phone
     * @param phone 
     * @return MerchantUser
     */
	public MerchantUser findMerchantUserByPhone(String phone);
}

