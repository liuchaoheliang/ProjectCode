package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import com.froad.db.mongo.ProductDetialMongo;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.BossProductCategoryMapper;
import com.froad.db.mysql.mapper.ClientCommonMapper;
import com.froad.db.mysql.mapper.ProductCommonMapper;
import com.froad.db.mysql.mapper.ProductMapper;
import com.froad.db.redis.BossProductCategoryRedis;
import com.froad.db.redis.ProductRedis;
import com.froad.logback.LogCvt;
import com.froad.logic.BossProductCategoryLogic;
import com.froad.po.Client;
import com.froad.po.Product;
import com.froad.po.ProductCategory;
import com.froad.po.ProductCategoryInput;
import com.froad.po.mongo.ProductCategoryInfo;
import com.froad.thrift.vo.product.BossProductCategoryVo;
import com.froad.util.Checker;

public class BossProductCategoryLogicImpl implements BossProductCategoryLogic {
	
	
	/**
	 * 查询商品分类列表
	 * @param clientId
	 * @param iscludeDisable
	 * @param isMall 是否精品商城商品分类
	 * @param originVo
	 * 
	 * @return List<ProductCategory>
	 * @author liuyanyun 2015-9-18 上午10:23
	 */
	@Override
	public List<ProductCategory> findCategorys(String clientId,boolean iscludeDisable,boolean isMall) {
		List<ProductCategory> list = null;
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			BossProductCategoryMapper productCategoryMapper = sqlSession.getMapper(BossProductCategoryMapper.class);
			
			list = productCategoryMapper.findCategorys(clientId, iscludeDisable,isMall);
		} catch (Exception e) {
			LogCvt.error("查询商品分类列表  findCategorys 失败，原因:" + e.getMessage(),e); 
			
			throw new RuntimeException("查询商品分类列表  findCategorys 失败", e);
        } finally { 
            if(null != sqlSession)  
            	sqlSession.close();  
        }
		return list;
	}

	/**
	 * 查询商品分类信息
	 * @param id
	 * 
	 * @return ProductCategory
	 * @author liuyanyun 2015-9-18 下午14:23
	 */
	@Override
	public BossProductCategoryVo getBossProductCategoryById(long id, String clientId) {
		BossProductCategoryVo vo = new BossProductCategoryVo();
		ProductCategory po = new ProductCategory();
		SqlSession sqlSession = null;
		try {
			//从mysql 查询
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
    		BossProductCategoryMapper productCategoryMapper = sqlSession.getMapper(BossProductCategoryMapper.class);
    		po = productCategoryMapper.getBossProductCategoryById(id, clientId);
    		if(Checker.isNotEmpty(po)){
    			vo = productCategoryCopy(po);
    		}
		} catch (Exception e) {
			LogCvt.error("查询商品分类信息  getBossProductCategoryById 失败，原因:" + e.getMessage(),e); 
			
			throw new RuntimeException("查询商品分类信息  getBossProductCategoryById 失败", e);
		} finally { 
            if(null != sqlSession)  
            	sqlSession.close();  
		}
		
		return vo;
	}

	/**
	 * 新增商品分类
	 * @param po
	 * 
	 * @return int
	 * @author liuyanyun 2015-9-18 下午15:51
	 */
	@Override
	public int addBossProductCategoryVo(ProductCategory po) {
		int rows = 0;
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			BossProductCategoryMapper productCategoryMapper = sqlSession.getMapper(BossProductCategoryMapper.class);
			
			//判断新增的分类名称是否存在
			int count = productCategoryMapper.findByName(po.getClientId(), po.getName(),po.getIsMall());
			if (count > 0) {
				return rows = -1;
			}
			
			po.setTreePath("");
			rows = productCategoryMapper.addBossProductCategoryVo(po);
			String treePath = "";
	        if(po.getParentId()>0){
	        	long id = po.getParentId();
	        	String clientId = po.getClientId();
	        	ProductCategory pc = productCategoryMapper.getBossProductCategoryById(id,clientId);
	            if(Checker.isNotEmpty(pc)){
	            	treePath = pc.getTreePath();
	            }
	        }
	        
	        po.setTreePath((treePath.trim() + " " + po.getId()).trim());
	        rows = productCategoryMapper.updateBossProductCategoryVoTreePath(po);
	        sqlSession.commit(true);
			
			//update Redis缓存
			BossProductCategoryRedis.productCategoryRedis(po);
    		
		} catch (Exception e) {
			LogCvt.error("新增商品分类  addBossProductCategoryVo 失败，原因:" + e.getMessage(),e);
			if(null != sqlSession)  
				sqlSession.rollback();
			
			throw new RuntimeException("新增商品分类  addBossProductCategoryVo 失败", e);
        } finally { 
        	if(null != sqlSession)  
        		sqlSession.close();  
        }
		return rows;
	}

	/**
	 * 修改商品分类
	 * @param po
	 * 
	 * @return int
	 * @author liuyanyun 2015-9-18 下午16:51
	 */
	@Override
	public int updateBossProductCategoryVo(ProductCategory po) {
		int rows = 0;
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			BossProductCategoryMapper productCategoryMapper = sqlSession.getMapper(BossProductCategoryMapper.class);
			
			ProductCategory oldProductCategory = productCategoryMapper.getBossProductCategoryById(po.getId(), po.getClientId());
			//修改了分类名称
			if(!oldProductCategory.getName().equals(po.getName())){
				//校验编辑的分类名称是否存在
				int count = productCategoryMapper.findByName(po.getClientId(), po.getName(),po.getIsMall());
				if (count > 0) {
					return rows = -1;
				}
			}
			
			rows = productCategoryMapper.updateBossProductCategoryVo(po);
			
			ProductCategory pc = productCategoryMapper.findByCategoryId(po.getId(), po.getClientId());
			//update Redis缓存
			if(po!=null){
				po.setTreePath(pc.getTreePath());
				po.setParentId(pc.getParentId());
				BossProductCategoryRedis.updateProductCategoryRedis(po);
			}
			sqlSession.commit();
		} catch (Exception e) {
			LogCvt.error("修改商品分类  updateBossProductCategoryVo 失败，原因:" + e.getMessage(),e);
			if(null != sqlSession)  
				sqlSession.rollback();
			
			throw new RuntimeException("修改商品分类  updateBossProductCategoryVo 失败", e);
        } finally { 
        	if(null != sqlSession)  
        		sqlSession.close();  
        }
		return rows;
	}
	
	/**
	 * 商品分类vo转换
	 */
	private BossProductCategoryVo productCategoryCopy(ProductCategory po){
		BossProductCategoryVo vo = new BossProductCategoryVo();
		vo.setId(po.getId());
		vo.setClientId(po.getClientId());
		vo.setName(po.getName());
		vo.setParentId(po.getParentId());
		vo.setTreePath(po.getTreePath());
		if (po.getIsDelete()) {
			po.setIsDelete(false);
		} else {
			po.setIsDelete(true);
		}
		vo.setIsEnable(po.getIsDelete());
		vo.setIcoUrl(po.getIcoUrl());
		vo.setOrderValue(po.getOrderValue().shortValue());
		vo.setIsMall(po.getIsMall());
		vo.setIsMarket(po.getIsMarket());
		return vo;
	}

	/**
	 * 商品分类的商品导入
	 */
	@Override
	public List<ProductCategoryInput> productCategoryInput(List<ProductCategoryInput> inputs,boolean isMall) throws Exception {
		SqlSession session = null;
		List<ProductCategoryInput> result = new ArrayList<ProductCategoryInput>();
		try {
			session = MyBatisManager.getSqlSessionFactory().openSession();
			ClientCommonMapper clientMapper = session.getMapper(ClientCommonMapper.class);
			BossProductCategoryMapper categoryMapper = session.getMapper(BossProductCategoryMapper.class);
			ProductCommonMapper productCommonMapper = session.getMapper(ProductCommonMapper.class);
			ProductDetialMongo productMongo = new ProductDetialMongo();
//			ProductCategoryRedis categoryRedis = new ProductCategoryRedis();
			ProductMapper productMapper = session.getMapper(ProductMapper.class);
			
			if(Checker.isEmpty(inputs)){
				return result;
			}
			
			Client client = null;
			ProductCategoryInput input = null;
			Iterator<ProductCategoryInput> iterator = inputs.iterator();
			Product product = null;
			String[] treePathSplit = null;
//			String[] categoryDetailSpilt = null;
			List<ProductCategoryInfo> categoryInfoNewList = null;
//			List<ProductCategoryInfo> categoryInfoOldList = null;
			while (iterator.hasNext()) {
				input = iterator.next();
				
				if(Checker.isEmpty(input.getClientName())
						|| Checker.isEmpty(input.getProductCategory())
						|| Checker.isEmpty(input.getProductId())){
					continue;
				}
				
				client = clientMapper.findClientByName(input.getClientName());
				
				if(Checker.isEmpty(client)){
					continue;
				}
				
				product = productCommonMapper.findProductByProductId(input.getProductId());
				if(Checker.isEmpty(product)){
					continue;
				}
				
				if(!product.getClientId().equals(client.getClientId())){
					continue;
				}
//				if(Checker.isNotEmpty(input.getMerchantId()) && !input.getMerchantId().equals(product.getMerchantId())){
//					continue;
//				}
				
				ProductCategory categoryInfo = null;
				categoryInfo = categoryMapper.getBossProductCategoryByName(client.getClientId(), input.getProductCategory(),isMall);
				categoryInfoNewList = new ArrayList<ProductCategoryInfo>();
				
				if(Checker.isEmpty(categoryInfo)){
					continue;
				}
//				String productCategryDetailStr = input.getProductCategryDetail();
//				if(Checker.isNotEmpty(productCategryDetailStr)){
//					treePathSplit = categoryInfo.getTreePath().split(" ");
//					if(productCategryDetailStr.indexOf("-") != -1){
//						categoryDetailSpilt = productCategryDetailStr.split("-");
//					}else{
//						categoryDetailSpilt = new String[1];
//						categoryDetailSpilt[0] = productCategryDetailStr;
//					}
//					
//					if(treePathSplit.length != categoryDetailSpilt.length){
//						continue;
//					}
//					
//					for(int i = 0; i < treePathSplit.length; i++){
//						Long treeCategoryId = Long.parseLong(treePathSplit[i]);
//						categoryInfo = getProductCategory(categoryMapper, client.getClientId(), treeCategoryId);
//						if(Checker.isNotEmpty(categoryInfo) && !categoryInfo.getIsDelete() && categoryInfo.getName().equals(categoryDetailSpilt[i])){
//							ProductCategoryInfo info = new ProductCategoryInfo();
//							info.setProductCategoryId(categoryInfo.getId());
//							categoryInfoNewList.add(info);
//						}else{
//							categoryInfoNewList.clear();
//							break;
//						}
//					}
//				}else{
//					ProductCategoryInfo info = new ProductCategoryInfo();
//					info.setProductCategoryId(categoryInfo.getId());
//					categoryInfoNewList.add(info);
//				}
				
				treePathSplit = categoryInfo.getTreePath().split(" ");
				for(int i = 0; i < treePathSplit.length; i++){
					Long treeCategoryId = Long.parseLong(treePathSplit[i]);
					categoryInfo = getProductCategory(categoryMapper, client.getClientId(), treeCategoryId);
					if(Checker.isNotEmpty(categoryInfo) && !categoryInfo.getIsDelete()){
						ProductCategoryInfo info = new ProductCategoryInfo();
						info.setProductCategoryId(treeCategoryId);
						categoryInfoNewList.add(info);
					}else{
						categoryInfoNewList.clear();
						break;
					}
				}
				
				if(Checker.isNotEmpty(categoryInfoNewList)){
//					categoryInfoOldList = productMongo.findProductCategoryInfo(product.getProductId());
//					if(Checker.isNotEmpty(categoryInfoOldList)){
//						for (ProductCategoryInfo categoryInfoOld : categoryInfoOldList) {
//							// 先删除旧分类缓存中的商品Id信息
//							categoryRedis.delProductCategoryRedis(product.getClientId(), categoryInfoOld.getProductCategoryId(), product.getProductId());
//						}
//					}
//					
//					for(ProductCategoryInfo info : categoryInfoNewList){
//						// 重新塞缓存商户ID信息
//						categoryRedis.setProductCategoryRedis(product.getClientId(), info.getProductCategoryId(), product.getProductId());
//					}
					
					// 修改商品详情表的分类信息
					productMongo.updateProductCategoryById(product.getProductId(), categoryInfoNewList);
					
					String productCategoryTreePath = "";
					for (int i = 0; i < categoryInfoNewList.size(); i++) {
						if(i == categoryInfoNewList.size() - 1) {
							productCategoryTreePath += categoryInfoNewList.get(i).getProductCategoryId();
						} else {
							productCategoryTreePath += categoryInfoNewList.get(i).getProductCategoryId() + " ";
						}
					}
					//修改mysql商品表的分类信息
					productMapper.updateProductCategroy(product.getProductId(), String.valueOf(productCategoryTreePath));
					//修改redis中分类信息
					ProductRedis.auditProductCategory(product,productCategoryTreePath);
					session.commit();
				}else{
					continue;
				}
				iterator.remove();
			}
			
			result.addAll(inputs);
		}catch (Exception e) {
			LogCvt.error("商品分类的商品导入异常", e);
			throw e;
		} finally { 
        	if(null != session){
        		session.close();  
        	}
        }
		return result;
	}

	/**
	 * 导出分类明细
	 */
	@Override
	public Map<String, Object> productCategoryDetailExport(String clientId, Long categoryId, Boolean isMall) throws Exception {
		if(categoryId == 0) {
			SqlSession session = null;
			Map<String, Object> resultMap = new HashMap<String, Object>();
			List<ProductCategoryInput> resultList = new ArrayList<ProductCategoryInput>();
			try {
				session = MyBatisManager.getSqlSessionFactory().openSession();
				BossProductCategoryMapper categoryMapper = session.getMapper(BossProductCategoryMapper.class);
				ProductCommonMapper productCommonMapper = session.getMapper(ProductCommonMapper.class);
//				ClientMapper clientMapper = session.getMapper(ClientMapper.class);
//				Client c = new Client();
//				c.setClientId(clientId);
//				List<Client> clientList = clientMapper.findClient(c);
//				Client rc = clientList.get(0);
				//从Redis环境中查
				Client rc = new CommonLogicImpl().getClientById(clientId);
				//获取根节点未禁用的商户分类集合
				List<ProductCategory>  mcList = categoryMapper.findNoDeleteParentList(clientId, isMall);
				if(mcList == null || mcList.size() == 0) {
					resultMap.put("categoryName", rc.getBankName());
					resultMap.put("resultList", resultList);
					return resultMap;
				} else {
					resultMap.put("categoryName", rc.getBankName());
					
					Map<String, Product> proMap = new HashMap<String, Product>();
					//获取当前客户端所有商品信息
					List<Product> proList = productCommonMapper.findProductByClientId(clientId);
					if(proList != null && proList.size() > 0) {
						for(Product p : proList) {
							proMap.put(p.getProductId(), p);
						}
					}
					for(ProductCategory m : mcList ) {
						Map<String, Object> map =  getProductategoryInputList( clientId, m.getId(), isMall, proMap);
						resultList.addAll((List<ProductCategoryInput>)map.get("resultList"));
					}
				}
			} catch (Exception e) {
				LogCvt.error(e.getMessage(),e);
			} finally {
				if(session != null) {
					session.close();
				}
			}
			resultMap.put("resultList",resultList);
			return resultMap;
		} else {
			return getProductategoryInputList(clientId, categoryId,isMall, null);
		}
	}

	private Map<String, Object> getProductategoryInputList(String clientId, Long categoryId,Boolean isMall, Map<String, Product> proMap) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<ProductCategoryInput> resultList = new ArrayList<ProductCategoryInput>();
		SqlSession session = null;
		try {
			session = MyBatisManager.getSqlSessionFactory().openSession();
			ClientCommonMapper clientMapper = session.getMapper(ClientCommonMapper.class);
			BossProductCategoryMapper categoryMapper = session.getMapper(BossProductCategoryMapper.class);
			ProductCommonMapper productCommonMapper = session.getMapper(ProductCommonMapper.class);
			ProductMapper productMapper = session.getMapper(ProductMapper.class);
			//ProductCategoryRedis categoryRedis = new ProductCategoryRedis();
			//获取当前商品分类下面的子分类集合
			Set<String> productIdListAll = new HashSet<String>();
			Map<String, Long> productIdCategoryIdMap = new HashMap<String, Long>();
			List<ProductCategory> pcList = categoryMapper.findChildList(categoryId.toString(), clientId);//pcList是父类以及子类的分类信息
			if(pcList == null || pcList.size() == 0) {
				resultMap.put("resultList", resultList);
				return resultMap; 
			} else {
				for(ProductCategory pc : pcList) {
					/*Set<String> productIdList = categoryRedis.getProductIdByClientIdAndCategoryId(clientId, pc.getId());
					if(productIdList != null && productIdList.size() > 0) {
						Iterator<String> perIt = productIdList.iterator();
						while(perIt.hasNext()) {
							productIdCategoryIdMap.put(perIt.next(), pc.getId());
						}
					}
					productIdListAll.addAll(productIdList);*/
					if (!pc.getIsDelete()) {
						//Set<String> productIdList = categoryRedis.getProductIdByClientIdAndCategoryId(clientId, pc.getId());
						//获取当前分类下面所有的商品id集合
						List<Product> productsList = productMapper.findProductListByCategoryId(clientId, pc.getId().toString());
						List<String> productIdList  =new ArrayList<String>();
						if(productsList != null && productsList.size() > 0) {
							for(Product p : productsList) {
								productIdCategoryIdMap.put(p.getProductId(), pc.getId());
								productIdList.add(p.getProductId());
							}
						}
						productIdListAll.addAll(productIdList);
					}
				}
			}
			
			ProductCategory cates = getProductCategory(categoryMapper, clientId, categoryId);
			
			resultMap.put("categoryName", cates.getName());
			if(productIdListAll != null && productIdListAll.size() > 0) {
				Iterator<String> it = productIdListAll.iterator();
				long count = 1;
				//查询客户端信息
				Client client = clientMapper.findClientById(clientId);
				if(client == null) {
					resultMap.put("resultList", resultList);
					return resultMap;
				}
				
				if(proMap == null) {
					proMap = new HashMap<String, Product>();
					//获取当前客户端所有商品信息
					List<Product> proList = productCommonMapper.findProductByClientId(clientId);
					if(proList != null && proList.size() > 0) {
						for(Product p : proList) {
							proMap.put(p.getProductId(), p);
						}
					}
				}
				
				while(it.hasNext()) {
					String productId = it.next();
					ProductCategory cate = getProductCategory(categoryMapper, clientId, productIdCategoryIdMap.get(productId));
					
					ProductCategoryInput vo = new ProductCategoryInput();
					vo.setId(count);
					
					Product product = proMap.get(productId);
					if(product == null) {
						continue;
					}
					//所属客户端
					vo.setClientName(client.getName());
					//商品分类
					
					String caName = "";
					//商品分类详细
					if(cate != null && StringUtils.isNotEmpty(cate.getTreePath())) {
						String[] parentCateIds = cate.getTreePath().split(" ");
						StringBuilder sb = new StringBuilder("");
						if(parentCateIds != null && parentCateIds.length > 0) {
							for(int n = 0; n < parentCateIds.length; n++) {
								ProductCategory parentCate = getProductCategory(categoryMapper, clientId, Long.valueOf(parentCateIds[n]));
								if(parentCate != null) {
									if(parentCate.getIsDelete()) {
										break;
									} else {
										caName = parentCate.getName();
										sb.append(parentCate.getName() + "-");
									}
								}
							}
						}
						String sbs = sb.toString();
						if(!"".equals(sbs) && sbs.endsWith("-")) {
							sbs = sbs.substring(0, sbs.length() - 1);
						} 
						
						vo.setProductCategryDetail(sbs);
					}
					
					vo.setProductCategory(caName);
					//商品名称
					if(isMall){
						//全称
						vo.setProductName(product.getFullName());
					}else{
						//简称
						vo.setProductName(product.getName());
					}
					
					//商品ID
					vo.setProductId(product.getProductId());
					//商品ID
					vo.setMerchantId(product.getMerchantId());
					//所属商户
					vo.setMerchantName(product.getMerchantName());
					vo.setCreateTime(product.getCreateTime());
					count++;
					resultList.add(vo);
				}
			}
		} catch (Exception e) {
			LogCvt.error("商品分类的商品明细导出异常", e);
			session.rollback(true);
		} finally { 
        	if(null != session){
        		session.close();  
        	}
        }
		resultMap.put("resultList", resultList);
		return resultMap;
		
		
		
	}
	
	
	private ProductCategory getProductCategory(BossProductCategoryMapper categoryMapper, String clientId, Long categoryId) {
		Map<String, String> cateMap = BossProductCategoryRedis.getProductCategoryRedis(clientId, categoryId);
		if(cateMap == null || cateMap.size() == 0) {
			ProductCategory category = categoryMapper.getBossProductCategoryById(categoryId, clientId);
			BossProductCategoryRedis.productCategoryRedis(category);
			return category;
		} else {
			ProductCategory p = new ProductCategory();
			p.setId(categoryId);
			p.setClientId(clientId);
			p.setName(cateMap.get("name"));
			p.setTreePath(cateMap.get("tree_path"));
			p.setParentId(cateMap.get("parent_id") != null? Long.valueOf(cateMap.get("parent_id")) : null);
			p.setIsDelete("1".equals(cateMap.get("is_delete"))? true:false);
			return p;
		}
	}
	
}
