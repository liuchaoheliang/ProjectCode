package com.froad.db.chonggou.mappers;

import java.util.List;

import com.froad.db.chonggou.entity.MerchantAccountCG;

public interface MerchantAccountMapperCG {




    /**
     * 增加 MerchantAccount
     * @param merchantAccount
     * @return Long    主键ID
     */
	public Long addMerchantAccount(MerchantAccountCG merchantAccount);



    /**
     * 批量增加 MerchantAccount
     * @param List<MerchantAccount>
     * @return Boolean    是否成功
     */
	public Boolean addMerchantAccountByBatch(List<MerchantAccountCG> merchantAccountList);



    /**
     * 删除 MerchantAccount
     * @param merchantAccount
     * @return Boolean    是否成功
     */
	public Boolean deleteMerchantAccount(MerchantAccountCG merchantAccount);


    /**
     * 删除 MerchantAccount
     * @param merchantAccount
     * @return Boolean    是否成功
     */
	public Boolean removeMerchantAccount(String clientId);

    /**
     * 修改 MerchantAccount
     * @param merchantAccount
     * @return Boolean    是否成功
     */
	public Boolean updateMerchantAccount(MerchantAccountCG merchantAccount);



    /**
     * 查询一个 MerchantAccount
     * @param id
     * @return MerchantAccount    返回结果
     */
	public MerchantAccountCG findMerchantAccountById(Long id);

	 /**
     * 查询一个 MerchantAccount
     * @param outletId
     * @return MerchantAccount    返回结果
     */
	public MerchantAccountCG findMerchantAccountByOutletId(String outletId);

    /**
     * 查询 MerchantAccount
     * @param merchantAccount
     * @return List<MerchantAccount>    返回结果集
     */
	public List<MerchantAccountCG> findMerchantAccount(MerchantAccountCG merchantAccount);






}

