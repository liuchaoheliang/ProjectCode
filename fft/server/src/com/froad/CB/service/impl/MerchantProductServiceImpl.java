package com.froad.CB.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import org.apache.log4j.Logger;
import com.froad.CB.dao.merchant.MerchantDAO;
import com.froad.CB.dao.merchant.MerchantProductDAO;
import com.froad.CB.po.merchant.Merchant;
import com.froad.CB.po.merchant.MerchantProduct;
import com.froad.CB.service.MerchantProductService;
import com.froad.util.Assert;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-7  
 * @version 1.0
 */
@WebService(endpointInterface="com.froad.CB.service.MerchantProductService")
public class MerchantProductServiceImpl implements MerchantProductService {
	private static final Logger logger=Logger.getLogger(MerchantProductServiceImpl.class);
	private MerchantProductDAO merchantProductDao;
	private MerchantDAO merchantDao;
	
	@Override
	public Integer addMerchantProduct(MerchantProduct merchantProduct) {
		if(merchantProduct==null){
			logger.info("增加商户产品不能为空！");
			return null;
		}
		return merchantProductDao.insert(merchantProduct);
	}

	@Override
	public MerchantProduct getMerchantProductById(Integer id) {
		if(Assert.empty(id)){
			logger.info("查询商户产品ID不能为空！");
			return null;
		}
		return merchantProductDao.selectByPrimaryKey(id);
	}

	@Override
	public Integer updateByPrimaryKeySelective(MerchantProduct merchantProduct) {
		if(merchantProduct==null || Assert.empty(merchantProduct.getId())){
			logger.info("更新商户产品参数merchantProduct.id不能为空！");
			return null;
		}
		return merchantProductDao.updateByPrimaryKeySelective(merchantProduct);
	}

	@Override
	public Integer updateByMerchantId(MerchantProduct merchantProduct) {
		if(merchantProduct==null || Assert.empty(merchantProduct.getMerchantId())){
			logger.info("更新商户产品参数merchantProduct.merchantId不能为空！");
			return null;
		}
		return merchantProductDao.updateByMerchantId(merchantProduct);
	}

	@Override
	public Integer deleteProductById(MerchantProduct merchantProduct) {
		if(merchantProduct==null || Assert.empty(merchantProduct.getId())){
			logger.info("删除商户产品merchantProduct.getId()不能为空！");
			return null;
		}
		
		return merchantProductDao.updateByPrimaryKeySelective(merchantProduct);
	}

	@Override
	public List<MerchantProduct> getMerchantProductByMerchantId(
			String merchantId) {
		if(merchantId==null){
			logger.info("查询商户产品merchantId不能为空！");
			return null;
		}
		MerchantProduct merchantProduct = new MerchantProduct();
		merchantProduct.setMerchantId(merchantId);
		return merchantProductDao.selectMerchantProducts(merchantProduct);
	}

	@Override
	public List<MerchantProduct> getMerchantProductByUserId(String userId) {
		if(userId==null||"".equals(userId)){
			return null;
		}
		Merchant queryCon=new Merchant();
		queryCon.setUserId(userId);
		List<Merchant> results=null;
		try {
			results = merchantDao.select(queryCon);
		} catch (SQLException e) {
			logger.info("userId查询商户产品,userId查询商户信息出错！");
			e.printStackTrace();
		}
		Merchant merchant=results!=null?results.get(0):new Merchant();
		MerchantProduct merchantProduct = new MerchantProduct();
		merchantProduct.setMerchantId(merchant.getId()+"");
		return merchantProductDao.selectMerchantProducts(merchantProduct);
	}

	@Override
	public MerchantProduct getMerchantProductByPager(MerchantProduct pager) {
		if(pager==null){
			logger.info("分页查询商户产品pager不能为空！");
			return null;
		}
		return merchantProductDao.getMerchantProductByPager(pager);
	}
	
	@Override
	public MerchantProduct updMerchantProductInfo(MerchantProduct merchantProduct) throws Exception{
		if(merchantProduct==null){
			return null;
		}
		try{
			merchantProductDao.updateByPrimaryKeySelective(merchantProduct);
		}catch(Exception e){
			logger.error("更新商户商品信息失败。", e);
			throw new Exception("系统更新商户商品信息失败!");
		}
		MerchantProduct newMerchantProduct=null;
		try{
			newMerchantProduct=merchantProductDao.selectByPrimaryKey(merchantProduct.getId());
		}catch(Exception e){
			logger.error("查询更新后的商户商品信息失败。", e);
			throw new Exception("系统查询更新后的商户商品信息失败!");
		}
		return newMerchantProduct;
	}
	
	/**
	 * 根据商户ID查询产品
	 * @param merchantId
	 * @return
	 */
	public List<MerchantProduct> getMerchantProductListByMerchantId(MerchantProduct merchantProduct) {
		if(merchantProduct==null||Assert.empty(merchantProduct.getMerchantId()))
			return null;
		List<MerchantProduct> results=merchantProductDao.selectMerchantProducts(merchantProduct);
		return results;
	}
	
	@Override
	public List<MerchantProduct> getMerchantProductInfo(String merchantId,
			String userId) {
		List<MerchantProduct> result=new ArrayList<MerchantProduct>();
		if(merchantId!=null&&!"".equals(merchantId)&&userId!=null&&!"".equals(userId)){
			Merchant merchant=getMerchantBaseInfoByUserId(userId);
			if(merchantId.equals(String.valueOf(merchant.getId()))){
				return getMerchantProductInfoByMerchantId(merchantId);
			}else{
				return result;
			}
		}
		if(merchantId!=null&&!"".equals(merchantId)){
			return getMerchantProductInfoByMerchantId(merchantId);
		} 
		if(userId!=null&&!"".equals(userId)){
			return getMerchantProductInfoByUserId(userId);
		}
		return result;
	}
	
	public Merchant getMerchantBaseInfoByUserId(String userId) {
//		if(userId==null||"".equals(userId))
//			return null;
//		Merchant queryCon=new Merchant();
//		queryCon.setUserId(userId);
//		List<Merchant> results;
//		try {
//			results = merchantDAO.select(queryCon);
//			return (results==null||results.size()==0)?null:results.get(0);
//		} catch (SQLException e) {
//			log.error("商家列表信息查询异常",e);
//			return null;
//		}
		return null;
	}
	
	public List<MerchantProduct> getMerchantProductInfoByMerchantId(String merchantId) {
		if(merchantId==null||"".equals(merchantId))
			return null;
		MerchantProduct queryCon=new MerchantProduct();
		queryCon.setMerchantId(merchantId);
		List<MerchantProduct> results=merchantProductDao.selectMerchantProducts(queryCon);
		return results;
	}
	
	public List<MerchantProduct> getMerchantProductInfoByUserId(String userId) {
		if(userId==null||"".equals(userId)){
			return null;
		}
		Merchant merchant=this.getMerchantBaseInfoByUserId(userId);
		return merchant==null?null:this.getMerchantProductInfoByMerchantId(String.valueOf(merchant.getId()));
	}
	
	public MerchantProductDAO getMerchantProductDao() {
		return merchantProductDao;
	}

	public void setMerchantProductDao(MerchantProductDAO merchantProductDao) {
		this.merchantProductDao = merchantProductDao;
	}

	public MerchantDAO getMerchantDao() {
		return merchantDao;
	}

	public void setMerchantDao(MerchantDAO merchantDao) {
		this.merchantDao = merchantDao;
	}

}
