package com.froad.logic;

import java.util.List;
import java.util.Map;

import com.froad.po.MerchantCategory;
import com.froad.po.MerchantCategoryInput;
import com.froad.thrift.vo.merchant.BossMerchantCategoryVo;
import com.froad.thrift.vo.merchant.BossParentCategoryListReq;
import com.froad.thrift.vo.merchant.BossParentCategoryListRes;

public interface BossMerchantCategoryLogic {
	/**
	 * 查询商户分类列表
	 * @param clientId
	 * @param iscludeDisable
	 * @param originVo
	 * 
	 * @return List<MerchantCategory>
	 * @author liuyanyun 2015-9-21 下午13:21
	 */
	public List<MerchantCategory> findCategorys(String clientId, boolean iscludeDisable);
	
	/**
	 * 查询商户分类信息
	 * @param id
	 * @param clientId
	 * 
	 * @return MerchantCategory
	 * @author liuyanyun 2015-9-21 下午13:21
	 */
	public BossMerchantCategoryVo getBossMerchantCategoryById(long id, String clientId);
	
	/**
	 * 新增商户分类
	 * @param mo
	 * 
	 * @return int
	 * @author liuyanyun 2015-9-21 下午13:21
	 */
	public int addBossMerchantCategoryVo(MerchantCategory mo);
	
	/**
	 * 修改增商户分类
	 * @param mo
	 * 
	 * @return int
	 * @author liuyanyun 2015-9-21 下午13:21
	 */
	public int updateBossMerchantCategoryVo(MerchantCategory mo);
	
	
	/**
	 * 
	 * merchantCategoryInput:(商户分类的商户导入).
	 *
	 * @author huangyihao
	 * 2015年11月2日 下午1:30:45
	 * @param inputs
	 * @return
	 * @throws Exception
	 *
	 */
	public List<MerchantCategoryInput> merchantCategoryInput(List<MerchantCategoryInput> inputs) throws Exception;
	
	
	/**
	 * 
	 * merchantCategoryDetailExport:(商户分类的商户明细导出).
	 *
	 * @author huangyihao
	 * 2015年11月2日 下午1:30:17
	 * @param clientId
	 * @param categoryId
	 * @return
	 * @throws Exception
	 *
	 */
	public Map<String, Object> merchantCategoryDetailExport(String clientId, Long categoryId) throws Exception;
	
	
	
	public BossParentCategoryListRes getParentCategoryList(BossParentCategoryListReq req);
	
}
