package com.froad.db.chonggou.mappers;

import java.util.List;


import com.froad.db.chonggou.entity.MerchantUserCG;

public interface MerchantUserMapperCG {

	
	
    /**
     * 增加 MerchantUser
     * @param merchantUser
     * @return Long    主键ID
     */
	public Long addMerchantUser(MerchantUserCG merchantUser);





    /**
     * 删除 MerchantUser
     * @param merchantUser
     * @return Boolean    是否成功
     */
	public Boolean deleteMerchantUser();



    /**
     * 修改 MerchantUser
     * @param merchantUser
     * @return Boolean    是否成功
     */
	public Boolean updateMerchantUser(MerchantUserCG merchantUser);



    /**
     * 查询一个 MerchantUser
     * @param merchantUser
     * @return MerchantUser    返回结果
     */
	public MerchantUserCG findMerchantUserById(Long id);



    /**
     * 查询 MerchantUser
     * @param merchantUser
     * @return List<MerchantUser>    返回结果集
     */
	public List<MerchantUserCG> findMerchantUser(MerchantUserCG merchantUser);





	 /**
     * 查询 MerchantUser - 根据 username
     * @param username 
     * @return MerchantUser
     */
	public MerchantUserCG findMerchantUserByUsername(String username);
	
	/**
     * 查询 MerchantUser - 根据 phone
     * @param phone 
     * @return MerchantUser
     */
	public MerchantUserCG findMerchantUserByPhone(String phone);
	
}

