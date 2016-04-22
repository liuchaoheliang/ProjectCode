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

import com.froad.db.mongo.MerchantDetailMongo;
import com.froad.db.mongo.OutletDetailMongo;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.BossMerchantCategoryMapper;
import com.froad.db.mysql.mapper.BossProductCategoryMapper;
import com.froad.db.mysql.mapper.ClientCommonMapper;
import com.froad.db.mysql.mapper.ClientMapper;
import com.froad.db.mysql.mapper.MerchantCommonMapper;
import com.froad.db.mysql.mapper.MerchantMapper;
import com.froad.db.redis.BossMerchantCategoryRedis;
import com.froad.db.redis.BossProductCategoryRedis;
import com.froad.db.redis.MerchantCategoryRedis;
import com.froad.enums.MerchantDisableStatusEnum;
import com.froad.enums.ProductAuditState;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadBusinessException;
import com.froad.logback.LogCvt;
import com.froad.logic.BossMerchantCategoryLogic;
import com.froad.po.Client;
import com.froad.po.Merchant;
import com.froad.po.MerchantCategory;
import com.froad.po.MerchantCategoryInput;
import com.froad.po.ProductCategory;
import com.froad.po.mongo.CategoryInfo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.merchant.BossMerchantCategoryVo;
import com.froad.thrift.vo.merchant.BossParentCategoryListReq;
import com.froad.thrift.vo.merchant.BossParentCategoryListRes;
import com.froad.thrift.vo.merchant.BossParentCategoryVo;
import com.froad.util.Checker;
import com.mongodb.util.Hash;

public class BossMerchantCategoryLogicImpl implements BossMerchantCategoryLogic{

	MerchantDetailMongo merchantDetailMongo = new MerchantDetailMongo();
	OutletDetailMongo outletDetailMongo = new OutletDetailMongo();
	/**
	 * 查询商户分类列表
	 * @param clientId
	 * @param iscludeDisable
	 * @param originVo
	 * 
	 * @return List<MerchantCategory>
	 * @author liuyanyun 2015-9-21 下午14:23
	 */
	@Override
	public List<MerchantCategory> findCategorys(String clientId, boolean iscludeDisable) {
		List<MerchantCategory> list = null;
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			BossMerchantCategoryMapper merchantCategoryMapper = sqlSession.getMapper(BossMerchantCategoryMapper.class);
			
			list = merchantCategoryMapper.findCategorys(clientId, iscludeDisable);
		} catch (Exception e) {
			LogCvt.error("查询商户分类列表  findCategorys 失败，原因:" + e.getMessage(),e); 
			
			throw new RuntimeException("查询商户分类列表  findCategorys 失败", e);
        } finally { 
            if(null != sqlSession)  
            	sqlSession.close();  
        }
		
		return list;
	}

	/**
	 * 查询商户分类信息
	 * @param id
	 * @param clientId
	 * 
	 * @return MerchantCategory
	 * @author liuyanyun 2015-9-21 下午14:41
	 */
	@Override
	public BossMerchantCategoryVo getBossMerchantCategoryById(long id, String clientId) {
		BossMerchantCategoryVo vo = new BossMerchantCategoryVo();
		MerchantCategory mo = null;
		SqlSession sqlSession = null;
		try {
			//从mysql 查询
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
    		BossMerchantCategoryMapper merchantCategoryMapper = sqlSession.getMapper(BossMerchantCategoryMapper.class);
    		mo = merchantCategoryMapper.getBossMerchantCategoryById(id, clientId);
			vo = merchantCategoryCopy(mo);
		} catch (Exception e) {
			LogCvt.error("查询商户分类信息  getBossMerchantCategoryById 失败，原因:" + e.getMessage(),e); 
			
			throw new RuntimeException("查询商户分类信息  getBossMerchantCategoryById 失败", e);
		} finally { 
            if(null != sqlSession)  
            	sqlSession.close();  
		}
		
		return vo;
	}

	/**
	 * 新增商户分类
	 * @param mo
	 * 
	 * @return int
	 * @author liuyanyun 2015-9-21 下午15:05
	 */
	@Override
	public int addBossMerchantCategoryVo(MerchantCategory mo) {
		int rows = 0;
		SqlSession sqlSession = null;
		
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			BossMerchantCategoryMapper merchantCategoryMapper = sqlSession.getMapper(BossMerchantCategoryMapper.class);
			//判断新增的分类名称是否存在
			int count = merchantCategoryMapper.findByName(mo.getClientId(), mo.getName());
			if (count > 0) {
				return rows = -1;
			}
			mo.setTreePath("");
			rows = merchantCategoryMapper.addBossMerchantCategoryVo(mo);
			
			String treePath = "";
			if (mo.getParentId() > 0) {
				long id = mo.getParentId();
				String clientId = mo.getClientId();
				MerchantCategory mc = merchantCategoryMapper.getBossMerchantCategoryById(id, clientId);
				
				if (Checker.isNotEmpty(mc)) {
					treePath = mc.getTreePath();
				}
			}
			mo.setTreePath((treePath.trim() + " " + mo.getId()).trim());
			rows = merchantCategoryMapper.updateBossMerchantCategoryVoTreePath(mo);
			sqlSession.commit(true);
			
			//update Redis缓存
			if(mo!=null){
				BossMerchantCategoryRedis.merchantCategoryRedis(mo);
			}
		} catch (Exception e) {
			LogCvt.error("新增商户分类  addBossMerchantCategoryVo 失败，原因:" + e.getMessage(),e);
			if(null != sqlSession)  
				sqlSession.rollback();
			
			throw new RuntimeException("新增商户分类  addBossMerchantCategoryVo 失败", e);
        } finally { 
        	if(null != sqlSession)  
        		sqlSession.close();  
        }
		
		return rows;
	}
	
	/**
	 * 修改商户分类
	 * @param mo
	 * 
	 * @return int
	 * @author liuyanyun 2015-9-21 下午15:05
	 */
	@Override
	public int updateBossMerchantCategoryVo(MerchantCategory mo) {
		int rows = 0;
		SqlSession sqlSession = null;
		
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			BossMerchantCategoryMapper merchantCategoryMapper = sqlSession.getMapper(BossMerchantCategoryMapper.class);
			//判断编辑的分类名称是否存在
			MerchantCategory merchantCategory = merchantCategoryMapper.getBossMerchantCategoryByName(mo.getClientId(), mo.getName());
			if (merchantCategory != null && !merchantCategory.getId().equals(mo.getId())) {//如果该分类名称已存在，则修改失败
				return rows = -1;
			}
			rows = merchantCategoryMapper.updateBossMerchantCategoryVo(mo);
			//修改商户分类名称
			if(mo.getParentId() == 0){
				merchantDetailMongo.updateMerchantCategoryNameByCategoryId(mo.getId(), mo.getName());
			}else{
				outletDetailMongo.updateOutletCategoryNameByCategoryId(mo.getId(), mo.getName());
			}
			
			MerchantCategory mc = merchantCategoryMapper.findByCategoryId(mo.getId(), mo.getClientId());
			//update Redis缓存
			if(mo!=null){
				mo.setTreePath(mc.getTreePath());
				mo.setParentId(mc.getParentId());
				BossMerchantCategoryRedis.updateMerchantCategoryRedis(mo);
			}
			sqlSession.commit();
		} catch (Exception e) {
			LogCvt.error("修改商户分类  updateBossMerchantCategoryVo 失败，原因:" + e.getMessage(),e);
			if(null != sqlSession)  
				sqlSession.rollback();
			
			throw new RuntimeException("修改商户分类  updateBossMerchantCategoryVo 失败", e);
        } finally { 
        	if(null != sqlSession)  
        		sqlSession.close();  
        }
		
		return rows;
	}
	
	/**
	 * 商户分类vo转换
	 */
	private BossMerchantCategoryVo merchantCategoryCopy(MerchantCategory mo) {
		BossMerchantCategoryVo vo = new BossMerchantCategoryVo();
		vo.setId(mo.getId());
		vo.setClientId(mo.getClientId());
		vo.setName(mo.getName());
		vo.setParentId(mo.getParentId());
		vo.setTreePath(mo.getTreePath());
		vo.setIsEnable(!mo.getIsDelete());
		vo.setIcoUrl(mo.getIcoUrl());
		vo.setOrderValue(mo.getOrderValue().shortValue());
		return vo;
	}

	@Override
	public List<MerchantCategoryInput> merchantCategoryInput(List<MerchantCategoryInput> inputs) throws Exception {
		SqlSession session = null;
		List<MerchantCategoryInput> result = new ArrayList<MerchantCategoryInput>();
		try {
			session = MyBatisManager.getSqlSessionFactory().openSession();
			ClientCommonMapper clientMapper = session.getMapper(ClientCommonMapper.class);
			BossMerchantCategoryMapper categoryMapper = session.getMapper(BossMerchantCategoryMapper.class);
			MerchantMapper merchantMapper = session.getMapper(MerchantMapper.class);
			MerchantDetailMongo merchantMongo = new MerchantDetailMongo();
			MerchantCategoryRedis categoryRedis = new MerchantCategoryRedis();
//			OutletDetailMongo outletMongo = new OutletDetailMongo();
			
			if(Checker.isEmpty(inputs)){
				return result;
			}
			
			Client client = null;
			MerchantCategoryInput input = null;
			Iterator<MerchantCategoryInput> iterator = inputs.iterator();
			Merchant merchant = null;
			String[] treePathSplit = null;
//			String[] categoryDetailSplit = null;
			List<CategoryInfo> categoryInfoNewList = null;
			List<CategoryInfo> categoryInfoOldList = null;
			while (iterator.hasNext()) {
				input = iterator.next();
				
				if(Checker.isEmpty(input.getClientName()) 
						|| Checker.isEmpty(input.getMerchantCategory()) 
						|| Checker.isEmpty(input.getLicense())){
					continue;
				}
				
				
				client = clientMapper.findClientByName(input.getClientName());
				if(Checker.isEmpty(client)){
					continue;
				}
				
				List<Merchant> licenseMerchants = merchantMapper.queryMerchantByLicense(input.getLicense());
				for(Merchant m : licenseMerchants){
					if(m.getIsEnable() 
							&& MerchantDisableStatusEnum.normal.getCode().equals(m.getDisableStatus()) 
							&& ProductAuditState.passAudit.getCode().equals(m.getAuditState())){
						merchant = m;
						break;
					}
				}
				
				if(Checker.isEmpty(merchant)){
					continue;
				}
				
				if(!merchant.getClientId().equals(client.getClientId())){
					continue;
				}
//				if(Checker.isNotEmpty(input.getMerchantId()) && !input.getMerchantId().equals(merchant.getMerchantId())){
//					continue;
//				}
				if(merchant.getMerchantStatus()){ 
					//银行商户没有分类信息
					continue;
				}
				
				MerchantCategory categoryInfo = null;
				
				categoryInfo = categoryMapper.getBossMerchantCategoryByName(client.getClientId(), input.getMerchantCategory());
				categoryInfoNewList = new ArrayList<CategoryInfo>();
				
				if(Checker.isEmpty(categoryInfo)){
					continue;
				}
//				String merchantCategryDetailStr = input.getMerchantCategryDetail();
//				if(Checker.isNotEmpty(merchantCategryDetailStr)){
//					treePathSplit = categoryInfo.getTreePath().split(" ");
//					if(merchantCategryDetailStr.indexOf("-") != -1){
//						categoryDetailSplit = merchantCategryDetailStr.split("-");
//					}else{
//						categoryDetailSplit = new String[1];
//						categoryDetailSplit[0] = merchantCategryDetailStr;
//					}
//					
//					if(treePathSplit.length != categoryDetailSplit.length){
//						continue;
//					}
//					
//					for(int i = 0; i < treePathSplit.length; i++){
//						Long treeCategoryId = Long.parseLong(treePathSplit[i]);
//						categoryInfo = getMerchantCategory(categoryMapper, client.getClientId(), treeCategoryId);
//						if(Checker.isNotEmpty(categoryInfo) && !categoryInfo.getIsDelete() && categoryInfo.getName().equals(categoryDetailSplit[i])){
//							CategoryInfo info = new CategoryInfo();
//							info.setCategoryId(treeCategoryId);
//							info.setName(categoryInfo.getName());
//							categoryInfoNewList.add(info);
//						}else{
//							categoryInfoNewList.clear();
//							break;
//						}
//					}
//					
//				}else{
//					treePathSplit = categoryInfo.getTreePath().split(" ");
//					for(int i = 0; i < treePathSplit.length; i++){
//						Long treeCategoryId = Long.parseLong(treePathSplit[i]);
//						categoryInfo = getMerchantCategory(categoryMapper, client.getClientId(), treeCategoryId);
//						if(Checker.isNotEmpty(categoryInfo) && !categoryInfo.getIsDelete()){
//							CategoryInfo info = new CategoryInfo();
//							info.setCategoryId(categoryInfo.getId());
//							info.setName(categoryInfo.getName());
//							categoryInfoNewList.add(info);
//						}else{
//							categoryInfoNewList.clear();
//							break;
//						}
//					}
//				}
				
				treePathSplit = categoryInfo.getTreePath().split(" ");
				for(int i = 0; i < treePathSplit.length; i++){
					Long treeCategoryId = Long.parseLong(treePathSplit[i]);
					categoryInfo = getMerchantCategory(categoryMapper, client.getClientId(), treeCategoryId);
					if(Checker.isNotEmpty(categoryInfo) && !categoryInfo.getIsDelete()){
						CategoryInfo info = new CategoryInfo();
						info.setCategoryId(categoryInfo.getId());
						info.setName(categoryInfo.getName());
						categoryInfoNewList.add(info);
					}else{
						categoryInfoNewList.clear();
						break;
					}
				}
				
				if(Checker.isNotEmpty(categoryInfoNewList)){
					categoryInfoOldList = merchantMongo.findMerchantCategoryInfo(merchant.getMerchantId());
					if(Checker.isNotEmpty(categoryInfoOldList)){
						for (CategoryInfo categoryInfoOld : categoryInfoOldList) {
							// 先删除旧分类缓存中的商户Id信息
							categoryRedis.delMerchantCategoryRedis(merchant.getClientId(), categoryInfoOld.getCategoryId(), merchant.getMerchantId());
						}
					}
					
					for(CategoryInfo info : categoryInfoNewList){
						categoryRedis.setMerchantCategoryRedis(merchant.getClientId(), info.getCategoryId(), merchant.getMerchantId());
					}
					// 修改商户详情表的分类信息
					merchantMongo.updateMerchantCategoryById(merchant.getMerchantId(), categoryInfoNewList);
					// 修改门店详情表的分类信息
//					outletMongo.updateOutletDetailCategoryInfo(merchant.getMerchantId(), categoryInfoNewList);
				}else{
					continue;
				}
				
				iterator.remove();
			}
			result.addAll(inputs);
		} catch (Exception e) {
			LogCvt.error("商户分类的商户导入异常", e);
			throw e;
		} finally { 
        	if(null != session){
        		session.close();  
        	}
        }
		return result;
	}

	@Override
	public Map<String, Object> merchantCategoryDetailExport(String clientId, Long categoryId)
			throws FroadBusinessException, Exception {
			if(categoryId == 0) {
				SqlSession session = null;
				Map<String, Object> resultMap = new HashMap<String, Object>();
				List<MerchantCategoryInput> resultList = new ArrayList<MerchantCategoryInput>();
				try {
					session = MyBatisManager.getSqlSessionFactory().openSession();
					BossMerchantCategoryMapper categoryMapper = session.getMapper(BossMerchantCategoryMapper.class);
					MerchantCommonMapper merchantCommonMapper = session.getMapper(MerchantCommonMapper.class);
//					ClientMapper clientMapper = session.getMapper(ClientMapper.class);
//					Client c = new Client();
//					c.setClientId(clientId);
//					List<Client> clientList = clientMapper.findClient(c);
//					Client rc = clientList.get(0);
					//从Redis环境中查
					Client rc = new CommonLogicImpl().getClientById(clientId);
					//获取根节点未禁用的商户分类集合
					List<MerchantCategory>  mcList = categoryMapper.findNoDeleteParentList(clientId);
					if(mcList == null || mcList.size() == 0) {
						resultMap.put("categoryName", rc.getBankName());
						resultMap.put("resultList", resultList);
						return resultMap;
					} else {
						resultMap.put("categoryName", rc.getBankName());
						
						Map<String, Merchant> merMap = new HashMap<String, Merchant>();
						Merchant reqMer = new Merchant();
						reqMer.setClientId(clientId);
						List<Merchant> merList = merchantCommonMapper.findMerchant(reqMer);
						if(merList != null && merList.size() > 0) {
							for(Merchant m : merList) {
								merMap.put(m.getMerchantId(), m);
							}
						}
						for(MerchantCategory m : mcList ) {
							Map<String, Object> map =  getMerchantCategoryInputList(clientId, m.getId(), merMap);
							resultList.addAll((List<MerchantCategoryInput>)map.get("resultList"));
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if(session != null) {
						session.close();
					}
				}
				resultMap.put("resultList",resultList);
				return resultMap;
			} else {
				return getMerchantCategoryInputList(clientId, categoryId, null);
			}
		
	}
	
	
	private Map<String, Object> getMerchantCategoryInputList(String clientId, Long categoryId, Map<String, Merchant> merMap) {
		SqlSession session = null;
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<MerchantCategoryInput> resultList = new ArrayList<MerchantCategoryInput>();
		try {
			session = MyBatisManager.getSqlSessionFactory().openSession();
			ClientCommonMapper clientMapper = session.getMapper(ClientCommonMapper.class);
			BossMerchantCategoryMapper categoryMapper = session.getMapper(BossMerchantCategoryMapper.class);
			MerchantCommonMapper merchantCommonMapper = session.getMapper(MerchantCommonMapper.class);
			MerchantCategoryRedis categoryRedis = new MerchantCategoryRedis();
			//获取当前商户分类下面的子分类集合
			Set<String> merchantIdListAll = new HashSet<String>();
			Map<String, Long> merchantIdCategoryIdMap = new HashMap<String, Long>();
			List<MerchantCategory> mcList = categoryMapper.findChildList(categoryId.toString(), clientId);
			if(mcList == null || mcList.size() == 0) {
				resultMap.put("resultList", resultList);
				return resultMap; 
			} else {
				for(MerchantCategory mc : mcList) {
					Set<String> merchantIdList = categoryRedis.getMerchantIdByClientIdAndCategoryId(clientId, mc.getId());
					if(merchantIdList != null && merchantIdList.size() > 0) {
						Iterator<String> merIt = merchantIdList.iterator();
						while(merIt.hasNext()) {
							merchantIdCategoryIdMap.put(merIt.next(), mc.getId());
						}
					}
					merchantIdListAll.addAll(merchantIdList);
				}
			}
			
			MerchantCategory cates = getMerchantCategory(categoryMapper, clientId, categoryId);
			
			resultMap.put("categoryName", cates.getName());
			if(merchantIdListAll != null && merchantIdListAll.size() > 0) {
				Iterator<String> it = merchantIdListAll.iterator();
				long count = 1;
				//查询客户端信息
				Client client = clientMapper.findClientById(clientId);
				if(client == null) {
					resultMap.put("resultList", resultList);
					return resultMap;
				}
				
				if(merMap == null) {
					merMap = new HashMap<String, Merchant>();
					Merchant reqMer = new Merchant();
					reqMer.setClientId(clientId);
					List<Merchant> merList = merchantCommonMapper.findMerchant(reqMer);
					if(merList != null && merList.size() > 0) {
						for(Merchant m : merList) {
							merMap.put(m.getMerchantId(), m);
						}
					}
				}
			
				
				while(it.hasNext()) {
					String merchantId = it.next();
					MerchantCategory cate = getMerchantCategory(categoryMapper, clientId, merchantIdCategoryIdMap.get(merchantId));
					
					MerchantCategoryInput vo = new MerchantCategoryInput();
					vo.setId(count);
					
				//	Merchant merchant = merchantCommonMapper.findMerchantByMerchantId(merchantId);
					Merchant merchant = merMap.get(merchantId);
					if(merchant == null) {
						continue;
					}
					//所属客户端
					vo.setClientName(client.getName());
					
					String caName = "";
					//商户分类详细
					if(cate != null && StringUtils.isNotEmpty(cate.getTreePath())) {
						String[] parentCateIds = cate.getTreePath().split(" ");
						StringBuilder sb = new StringBuilder("");
						if(parentCateIds != null && parentCateIds.length > 0) {
							for(int n = 0; n < parentCateIds.length; n++) {
								MerchantCategory parentCate = getMerchantCategory(categoryMapper, clientId, Long.valueOf(parentCateIds[n]));
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
						
						vo.setMerchantCategryDetail(sbs);
					}
					
					//商户分类
					vo.setMerchantCategory(caName);
					//商户名称
					vo.setMerchantName(merchant.getMerchantName());
					//商户ID
					vo.setMerchantId(merchant.getMerchantId());
					//营业执照号
					vo.setLicense(merchant.getLicense());
					vo.setCreateTime(merchant.getCreateTime());
					count++;
					resultList.add(vo);
				}
			}
		} catch (Exception e) {
			LogCvt.error("商户分类的商户明细导出异常", e);
			session.rollback(true);
		} finally { 
        	if(null != session){
        		session.close();  
        	}
        }
		resultMap.put("resultList", resultList);
		return resultMap;
	}
	
	
	private MerchantCategory getMerchantCategory(BossMerchantCategoryMapper categoryMapper, String clientId, Long categoryId) {
		Map<String, String> cateMap = BossMerchantCategoryRedis.getMerchantCategoryRedis(clientId, categoryId);
		if(cateMap == null || cateMap.size() == 0) {
			MerchantCategory category = categoryMapper.findByCategoryId(categoryId, clientId);
			BossMerchantCategoryRedis.merchantCategoryRedis(category);
			return category;
		} else {
			MerchantCategory c = new MerchantCategory();
			c.setId(categoryId);
			c.setClientId(clientId);
			c.setName(cateMap.get("name"));
			c.setTreePath(cateMap.get("tree_path"));
			c.setParentId(cateMap.get("parent_id") != null? Long.valueOf(cateMap.get("parent_id")) : null);
			c.setIsDelete("1".equals(cateMap.get("is_delete"))? true:false);
			return c;
		}
	}

	
	
	private ProductCategory getProductCategory(BossProductCategoryMapper categoryMapper, String clientId, Long categoryId) {
		Map<String, String> cateMap = BossProductCategoryRedis.getProductCategoryRedis(clientId, categoryId);
		if(cateMap == null || cateMap.size() == 0) {
			ProductCategory category = categoryMapper.getBossProductCategoryById(categoryId, clientId);
			BossProductCategoryRedis.productCategoryRedis(category);
			return category;
		} else {
			ProductCategory p = new ProductCategory();
			p.setClientId(clientId);
			p.setName(cateMap.get("name"));
			p.setTreePath(cateMap.get("tree_path"));
			p.setParentId(cateMap.get("parent_id") != null? Long.valueOf(cateMap.get("parent_id")) : null);
			p.setIsDelete("1".equals(cateMap.get("is_delete"))? true:false);
			return p;
		}
	}
	
	
	public BossParentCategoryListRes getParentCategoryList(BossParentCategoryListReq req)  {
		BossParentCategoryListRes res = new BossParentCategoryListRes();
		List<BossParentCategoryVo> voList = new ArrayList<BossParentCategoryVo>();
		ResultVo resultVo = new ResultVo();
		SqlSession session = null;
		try {
			session = MyBatisManager.getSqlSessionFactory().openSession();
			if("1".equals(req.getType())) {
				BossMerchantCategoryMapper mcategoryMapper = session.getMapper(BossMerchantCategoryMapper.class);
				MerchantCategory mc = mcategoryMapper.findByCategoryId(Long.valueOf(req.getCategoryId()), req.getClientId());
				if(mc != null) {
					String treeName = mc.getTreePath();
					String[] strArr = treeName.split(" ");
					if(strArr.length > 1) {
						for(int i = 0; i < strArr.length; i++) {
							if(!(strArr[i].equals(req.getCategoryId()))) {
								MerchantCategory paMer = getMerchantCategory(mcategoryMapper, req.getClientId(), Long.valueOf(strArr[i].trim()));
								if(paMer != null) {
									BossParentCategoryVo vo = new BossParentCategoryVo();
									vo.setCategoryId(strArr[i]);
									vo.setName(paMer.getName());
									vo.setIsDelete(paMer.getIsDelete()? "1": "0");
									vo.setTreePaht(paMer.getTreePath());
									voList.add(vo);
								}
							}
						}
						/*String parentId = strArr[0];
						List<MerchantCategory> parentList = mcategoryMapper.findChildList(parentId, req.getClientId());
						if(parentList != null && parentList.size() > 0) {
							for(MerchantCategory m : parentList) {
								if(!(m.getId().toString().equals(req.getCategoryId()))) {
									BossParentCategoryVo vo = new BossParentCategoryVo();
									vo.setCategoryId(m.getId().toString());
									vo.setName(m.getName());
									vo.setIsDelete(m.getIsDelete()? "1": "0");
									vo.setTreePaht(m.getTreePath());
									voList.add(vo);
								} else {
									break;
								}
							}
						}*/
					}
				} 
			} else {
				BossProductCategoryMapper pcategoryMapper = session.getMapper(BossProductCategoryMapper.class);
				ProductCategory pc = pcategoryMapper.findByCategoryId(Long.valueOf(req.getCategoryId()), req.getClientId());
				if(pc != null) {
					String treeName = pc.getTreePath();
					String[] strArr = treeName.split(" ");
					if(strArr.length > 1) {
						for(int i = 0; i < strArr.length; i++) { 
							if(!(strArr[i].equals(req.getCategoryId()))) {
								ProductCategory paMer = getProductCategory(pcategoryMapper, req.getClientId(), Long.valueOf(strArr[i].trim()));
								if(paMer != null) {
									BossParentCategoryVo vo = new BossParentCategoryVo();
									vo.setCategoryId(strArr[i]);
									vo.setName(paMer.getName());
									vo.setIsDelete(paMer.getIsDelete()? "1": "0");
									vo.setTreePaht(paMer.getTreePath());
									voList.add(vo);
								}
							}
						}
					}
				}
			}
			resultVo.setResultCode(ResultCode.success.getCode());
			resultVo.setResultDesc(ResultCode.success.getMsg());
		} catch (Exception e) {
			session.rollback(true);
			LogCvt.error("获取当前分类的父分类失败", e);
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc(ResultCode.failed.getMsg());
		} finally { 
        	if(null != session){
        		session.close();  
        	}
        }
		res.setVoList(voList);
		res.setResultVo(resultVo);
		return res;	
	}
}
