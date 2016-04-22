package com.froad.db.chonggou.mappers;

import java.util.List;

import com.froad.db.chonggou.entity.MerchantAccount;

public interface MerchantAccountMapperCG {




    /**
     * 增加 MerchantAccount
     * @param merchantAccount
     * @return Long    主键ID
     */
	public Long addMerchantAccount(MerchantAccount merchantAccount);



    /**
     * 批量增加 MerchantAccount
     * @param List<MerchantAccount>
     * @return Boolean    是否成功
     */
	public Boolean addMerchantAccountByBatch(List<MerchantAccount> merchantAccountList);



    /**
     * 删除 MerchantAccount
     * @param merchantAccount
     * @return Boolean    是否成功
     */
	public Boolean deleteMerchantAccount(MerchantAccount merchantAccount);



    /**
     * 修改 MerchantAccount
     * @param merchantAccount
     * @return Boolean    是否成功
     */
	public Boolean updateMerchantAccount(MerchantAccount merchantAccount);



    /**
     * 查询一个 MerchantAccount
     * @param id
     * @return MerchantAccount    返回结果
     */
	public MerchantAccount findMerchantAccountById(Long id);

	 /**
     * 查询一个 MerchantAccount
     * @param outletId
     * @return MerchantAccount    返回结果
     */
	public MerchantAccount findMerchantAccountByOutletId(String outletId);

    /**
     * 查询 MerchantAccount
     * @param merchantAccount
     * @return List<MerchantAccount>    返回结果集
     */
	public List<MerchantAccount> findMerchantAccount(MerchantAccount merchantAccount);






}

