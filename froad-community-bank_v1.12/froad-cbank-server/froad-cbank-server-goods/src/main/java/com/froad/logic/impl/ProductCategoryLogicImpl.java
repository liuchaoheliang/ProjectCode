/**
 * 
 * @Title: ProductCategoryLogicImpl.java
 * @Package com.froad.logic.impl
 * @see: 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.apache.ibatis.session.SqlSession;

import com.froad.db.mongo.MongoGroup;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.ProductCategoryMapper;
import com.froad.db.redis.ProductCategoryRedis;
import com.froad.db.redis.ProductRedis;
import com.froad.db.redis.impl.RedisManager;
import com.froad.enums.ProductStatus;
import com.froad.enums.ProductType;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.CommonLogic;
import com.froad.logic.ProductCategoryLogic;
import com.froad.po.Area;
import com.froad.po.ProductCategory;
import com.froad.po.Result;
import com.froad.thrift.vo.AddProductCategoryVoRes;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.Checker;
import com.froad.util.GoodsConstants;
import com.froad.util.MongoTableName;
import com.froad.util.RedisKeyUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

/**
 * 
 * <p>@Title: ProductCategoryLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class ProductCategoryLogicImpl implements ProductCategoryLogic {
	
	private MongoManager manager = new MongoManager();
	private RedisManager redis = new RedisManager();

    /**
     * 增加 ProductCategory
     * @param productCategory
     * @return Long    主键ID
     */
	@Override
	public Long addProductCategory(ProductCategory productCategory) {
	    SqlSession sqlSession = null;
        try { 
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            ProductCategoryMapper productCategoryMapper = sqlSession.getMapper(ProductCategoryMapper.class);
	        productCategory.setIsDelete(Boolean.FALSE);
	        
	        productCategory.setTreePath("");
	        productCategoryMapper.addProductCategory(productCategory); 
	        
	        String treePath = "";
	        if(productCategory.getParentId()>0){
	            ProductCategory pc = new ProductCategory();
	            pc.setId(productCategory.getParentId());
	            pc.setClientId(productCategory.getClientId());
	            List<ProductCategory> pcs = productCategoryMapper.getProductCategorys(pc);
	            if(pcs!=null && pcs.size()>0){
	                if(pcs.get(0).getTreePath()!=null){
	                    treePath = pcs.get(0).getTreePath();
	                }
	            }
	        }
	        productCategory.setTreePath((treePath.trim()+" "+productCategory.getId()).trim());
	        productCategoryMapper.updateProductCategory(productCategory);
	        sqlSession.commit(true);
	        
	        /* redis缓存 */
	        ProductCategoryRedis.productCategoryRedis(productCategory);
	        
	    } catch (Exception e) { 
	    	if(null != sqlSession)
	    		sqlSession.rollback(true);  
            LogCvt.error("添加ProductCategory失败，原因:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession)  
                sqlSession.close();  
        } 
		return productCategory.getId();
	}


    /**
     * 逻辑删除 ProductCategory
     * @param productCategory
     * @return Result
     */
	@Override
	public Result deleteProductCategory(ProductCategory productCategory) {
	    Result result = new Result();
	    SqlSession sqlSession = null;
	    try { 
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            ProductCategoryMapper productCategoryMapper = sqlSession.getMapper(ProductCategoryMapper.class);
            
            List<ProductCategory> pcs = productCategoryMapper.getProductCategorys(productCategory);
            if(pcs!=null && pcs.size()>0){
                productCategory.setClientId(pcs.get(0).getClientId());
                productCategory.setIsDelete(Boolean.TRUE);
                productCategoryMapper.deleteLogicCategory(productCategory);
                sqlSession.commit(true);
                /* redis缓存 */
                String key = RedisKeyUtil.cbbank_product_category_client_id_product_category_id(productCategory.getClientId(), productCategory.getId());
                RedisManager redis = new RedisManager();
                Map<String, String> hash = redis.getMap(key);
                if(hash!=null){
                    hash.put("is_delete", BooleanUtils.toString(productCategory.getIsDelete(), "1", "0", "0"));
                    redis.putMap(key, hash);
                }
                result.setResultCode(ResultCode.success.getCode());
                result.setResultDesc("删除商品分类成功");
            } else {
                result.setResultCode(ResultCode.failed.getCode());
                result.setResultDesc("该商品分类已不存在");
            }
        } catch (Exception e) { 
        	if(null != sqlSession)
        		sqlSession.rollback(true);  
            result.setResultCode(ResultCode.failed.getCode());
            result.setResultDesc("删除商品分类失败");
            LogCvt.error("逻辑删除 ProductCategory失败，原因:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession)  
                sqlSession.close();  
        } 
        return result;
	}


    /**
     * 修改 ProductCategory
     * @param productCategory
     * @return Result
     */
	@Override
	public Result updateProductCategory(ProductCategory productCategory) {
		Result result = new Result();
		SqlSession sqlSession = null;
		try { 
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            ProductCategoryMapper productCategoryMapper = sqlSession.getMapper(ProductCategoryMapper.class);
            
            List<ProductCategory> pcPos = productCategoryMapper.getProductCategorys(productCategory);
            if(pcPos!=null && pcPos.size()>0){
                String treePath = "";
                if(productCategory.getParentId()>0){
                    ProductCategory pc = new ProductCategory();
                    pc.setId(productCategory.getParentId());
                    pc.setClientId(productCategory.getClientId());
                    List<ProductCategory> pcs = productCategoryMapper.getProductCategorys(pc);
                    if(pcs!=null && pcs.size()>0){
                        if(pcs.get(0).getTreePath()!=null){
                            treePath = pcs.get(0).getTreePath();
                        }
                    }
                }
                productCategory.setTreePath((treePath.trim()+" "+productCategory.getId()).trim());
                productCategory.setIsDelete(Boolean.FALSE);
                productCategoryMapper.updateProductCategory(productCategory); 
                sqlSession.commit(true);
                /* redis缓存 */
                ProductCategoryRedis.productCategoryRedis(productCategory);
                
                result.setResultCode(ResultCode.success.getCode());
                result.setResultDesc("修改商品分类成功");
            } else {
                result.setResultCode(ResultCode.failed.getCode());
                result.setResultDesc("该商品分类已不存在不能修改");
            }
        } catch (Exception e) { 
        	if(null != sqlSession)
        		sqlSession.rollback(true);  
            result.setResultCode(ResultCode.failed.getCode());
            result.setResultDesc("修改商品分类失败");
            LogCvt.error("修改 ProductCategory失败，原因:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession)  
                sqlSession.close();  
        }
        return result;
	}


    /**
     * 查询 ProductCategory
     * @param productCategory
     * @return List<ProductCategory>    结果集合 
     */
	@Override
	public ProductCategory getProductCategoryById(ProductCategory productCategory) {
	    ProductCategory pc = null;
	    SqlSession sqlSession = null;
	    try{
	        sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
	        ProductCategoryMapper productCategoryMapper = sqlSession.getMapper(ProductCategoryMapper.class);
            
	        List<ProductCategory> pcs = productCategoryMapper.getProductCategorys(productCategory); 
	        if(pcs!=null && pcs.size()>0){
	            pc = pcs.get(0);
	        }
	    } catch (Exception e) {
	        LogCvt.error("查询 ProductCategory失败，原因:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession)  
                sqlSession.close();  
        }
		return pc;
	}


	/**
     * 判断是否已经 ProductCategory
     * @param productCategory
     * @return Boolean    true已经存在
     */
    @Override
    public Boolean isProductCategoryExist(ProductCategory productCategory) {
        Boolean isExist = false;
        SqlSession sqlSession = null;
        try{
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            ProductCategoryMapper productCategoryMapper = sqlSession.getMapper(ProductCategoryMapper.class);
            
            if(productCategory.getParentId()!=0){
                if(productCategory.getTreePath()!=null){
                    productCategory.setTreePath(productCategory.getTreePath().trim()+" "+productCategory.getParentId());
                } else {
                    productCategory.setTreePath(" "+productCategory.getParentId());
                }
            }
            Integer categoryNum = productCategoryMapper.getProductCategoryCount(productCategory);
            if(categoryNum!=null && categoryNum>0) {
                isExist = true;
            } 
        } catch (Exception e) {
            isExist = false;
            LogCvt.error("判断是否已经 ProductCategory失败，原因:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession)  
                sqlSession.close();  
        }
        return isExist;
    }


    @Override
    public List<ProductCategory> findCategorysByPage(
            Page<ProductCategory> page,ProductCategory productCategory) {
        List<ProductCategory> list = null;
        SqlSession sqlSession = null;
        try{
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            ProductCategoryMapper productCategoryMapper = sqlSession.getMapper(ProductCategoryMapper.class);
            
            list =  productCategoryMapper.findCategorysByPage(page, productCategory);
        }catch (Exception e) {
            LogCvt.error("分页查询 ProductCategory失败，原因:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession)  
                sqlSession.close();  
        }
        return list;
    }
    

    @Override
    public List<ProductCategory> findCategorys(String clientId) {
        List<ProductCategory> list = null;
        SqlSession sqlSession = null;
        try{
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            ProductCategoryMapper productCategoryMapper = sqlSession.getMapper(ProductCategoryMapper.class);
            
            ProductCategory productCategory = new ProductCategory();
            productCategory.setClientId(clientId);
            
            list =  productCategoryMapper.getProductCategorys(productCategory);
        }catch (Exception e) {
            LogCvt.error("分页查询 ProductCategory失败，原因:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession)  
                sqlSession.close();  
        }
        return list;
    }


    @Override
    public List<AddProductCategoryVoRes> addProductCategoryBatch(List<ProductCategory> productCategorys) {
        List<AddProductCategoryVoRes> addProductCategoryVoResBatch = new ArrayList<AddProductCategoryVoRes>();
        if(productCategorys==null || productCategorys.size()==0){
            AddProductCategoryVoRes addProductCategoryVoRes = new AddProductCategoryVoRes();
            ResultVo result = new ResultVo();
            result.setResultCode(ResultCode.failed.getCode());
            result.setResultDesc("没有需要批量新加的商品分类信息");
            addProductCategoryVoRes.setResult(result);
            addProductCategoryVoResBatch.add(addProductCategoryVoRes);
            return addProductCategoryVoResBatch;
        }
        SqlSession sqlSession = null;
        try { 
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            ProductCategoryMapper productCategoryMapper = sqlSession.getMapper(ProductCategoryMapper.class);
            
            productCategoryMapper.addProductCategoryBatch(productCategorys);
            sqlSession.commit(true);
            
            Map<String,Object> param = new HashMap<String,Object>();
            List<String> names = new ArrayList<String>();
            
            for(ProductCategory productCategory : productCategorys){
                names.add(productCategory.getName().trim());
            }
            param.put("clientId", productCategorys.get(0).getClientId());
            param.put("names", names);
            List<ProductCategory> pcPo = productCategoryMapper.findProductCategorys(param);
            if(pcPo!=null && pcPo.size()>0){
                
                AddProductCategoryVoRes addProductCategoryVoRes = null;
                ResultVo result = null;
                
                for(ProductCategory pc : pcPo){
                    if(pc.getTreePath()!=null && !"".equals(pc.getTreePath())){
                        if(pc.getTreePath().lastIndexOf(pc.getId().toString())==-1){
                            pc.setTreePath(pc.getTreePath()+" "+pc.getId());
                        }
                    } else{
                        pc.setTreePath(""+pc.getId());
                    }
                    productCategoryMapper.updateProductCategory(pc);
                    sqlSession.commit(true);
                    /* redis缓存 */
                    ProductCategoryRedis.productCategoryRedis(pc);
                    
                    addProductCategoryVoRes = new AddProductCategoryVoRes();
                    result = new ResultVo();
                    result.setResultCode(ResultCode.success.getCode());
                    result.setResultDesc(pc.getName().trim()+"批量新加时新加成功");
                    addProductCategoryVoRes.setResult(result);
                    addProductCategoryVoRes.setId(pc.getId());
                    addProductCategoryVoResBatch.add(addProductCategoryVoRes);
                }
            }
        } catch (Exception e) {
        	if(null != sqlSession)
        		sqlSession.rollback(true);  
            LogCvt.error("批量添加ProductCategory失败，原因:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession)  
                sqlSession.close();  
        } 
        return addProductCategoryVoResBatch;
    }


    /**
	 * 获取商品分类对象
	 * @param clientId
	 * @param categoryId
	 * @return
	 */
    @Override
	public ProductCategory findCategoryById(String clientId,Long categoryId){
    	ProductCategory productCategory=null;
		SqlSession sqlSession = null;
		
		try{
            Map<String,String> categoryMap = new RedisManager().getMap(RedisKeyUtil.cbbank_product_category_client_id_product_category_id(clientId,categoryId));
            if(Checker.isNotEmpty(categoryMap)){
            	productCategory=new ProductCategory();
            	productCategory.setClientId(clientId);
            	productCategory.setId(categoryId);
            	productCategory.setName(categoryMap.get("name"));
            	productCategory.setParentId(Long.valueOf(categoryMap.get("parent_id")));
            	productCategory.setTreePath(categoryMap.get("tree_path"));
            	productCategory.setIsDelete(BooleanUtils.toBooleanObject(categoryMap.get("is_delete"), "1", "0", ""));
            }else{
            	sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
 	            ProductCategoryMapper productCategoryMapper = sqlSession.getMapper(ProductCategoryMapper.class);
 	            
            	//从mysql中查询
 	            productCategory = productCategoryMapper.findProductCategoryById(categoryId);
                
                //重新设置到redis中
 	            if(productCategory!=null){
 	               ProductCategoryRedis.productCategoryRedis(productCategory);
 	            }
            }
		}catch(Exception e){
			 LogCvt.error("查询商品分类tree_path失败，原因:" + e.getMessage(),e); 
		}finally{
			if(null != sqlSession) {
				sqlSession.close(); 
            } 
		}
		return productCategory;
	}

    
	@Override
	public Map<String,Object> getManageProductCategorys(Map<String, Object> param) {
		Map<String,Object> categorys = new HashMap<String,Object>();
		SqlSession sqlSession = null;
		try{
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			ProductCategoryMapper productCategoryMapper = sqlSession.getMapper(ProductCategoryMapper.class);
			List<ProductCategory> pcs = productCategoryMapper.findCategorys(param);
			if(pcs!=null && pcs.size()>0){
				List<Long> parentIds = new ArrayList<Long>();
				for(ProductCategory pc : pcs){
					parentIds.add(pc.getId());
				}
				Map<String, Object> param1 = new HashMap<String,Object>();
				param1.put("clientId", param.get("clientId"));
				param1.put("parentIds", parentIds);
				param1.put("isMall", false);
				Map<Long, Long> num = null;
				List<Map<String, Long>>  nums = productCategoryMapper.findCategoryChildNum(param1);
				if(nums!=null && nums.size()>0){
					num = new HashMap<Long, Long>();
					for(Map<String, Long> ch : nums){
						num.put(ch.get("id"), ch.get("childNum"));
					}
				}
				categorys.put("childNum", num);
				categorys.put("categorys", pcs);
			}
		}catch(Exception e){
			 LogCvt.error("查询商品分类失败，原因:" + e.getMessage(),e); 
		}finally{
			if(null != sqlSession) {
				sqlSession.close(); 
            } 
		}
		return categorys;
	}
	

	@Override
	public Map<String,Object> queryProductCategorys(String clientId, Long parentId, Long areaId) {
		Map<String,Object> categorys = new HashMap<String,Object>();
		SqlSession sqlSession = null;
		try{
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			ProductCategoryMapper productCategoryMapper = sqlSession.getMapper(ProductCategoryMapper.class);
			
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("clientId", clientId);
			param.put("parentId", parentId);
			param.put("isMall", false);//不是精品商城的分类
			
			List<ProductCategory> pcs = productCategoryMapper.findCategorys(param);
			List<ProductCategory> productCategorys = new ArrayList<ProductCategory>();
			if(pcs!=null && pcs.size()>0){
				//根据查询条件传进来的areaid判断是市级地区id还是区级地区id
		        Long cityId = null;//市级地区id
		        if(areaId>0){
		            CommonLogic comLogic = new CommonLogicImpl();
		            Area area = comLogic.findAreaById(areaId);
		            if(area!=null){
		                String areaTreePath = area.getTreePath();
		                if(Checker.isNotEmpty(areaTreePath)){
		                    String[] treePtah = areaTreePath.split(",");
		                    if(treePtah.length==2){//areaId为市
		                    	cityId = areaId;
		                    } 
		                }
		            }
		        }
		        
		        String type = ProductType.group.getCode();
				int time = Integer.valueOf(GoodsConstants.PRODUCT_CATEGORY_GOODS_REDIS_LOCK_TIME);//商品分类有对应的商品缓存有效时间,2代表2分钟
				
				List<Long> parentIds = new ArrayList<Long>();
				List<ProductCategory> tempProductCategorys = new ArrayList<ProductCategory>();
				for(ProductCategory pc : pcs){
					parentIds.add(pc.getId());
					if(pc.getParentId()==null || pc.getParentId()==0){
						productCategorys.add(pc);
					} else {//非一级分类有商品数据的商品分类
						tempProductCategorys.add(pc);
					}
				}
				//非一级分类有商品数据的商品分类才会被加入
				if(tempProductCategorys.size()>0){
					List<Long> productCategoryIds = new ArrayList<Long>();
					String key = null;
					for(ProductCategory temp : tempProductCategorys){
						if(!isHaveRedis(clientId, type, time, cityId, areaId, temp.getId())){//没有缓存信息需要去查mongo重新设置缓存
							productCategoryIds.add(temp.getId());
						} else {//有缓存信息直接去取
							key = RedisKeyUtil.cbbank_product_category_product_client_id_product_category_id_type_area_id(clientId, temp.getId(), type, areaId);
							String result = redis.getString(key);
							if("1".equals(result)){
								productCategorys.add(temp);
							} else if(!"0".equals(result)){
								productCategoryIds.add(temp.getId());
							}
						}
					}
					if(productCategoryIds.size()>0){
						Map<Long,Integer> productOfCategoryNums = findProductNumOfCategory(clientId, cityId, areaId, productCategoryIds, type, time);
						if(productOfCategoryNums!=null && productOfCategoryNums.size()>0){
							for(ProductCategory temppc : tempProductCategorys){
								if(productOfCategoryNums.get(temppc.getId())!=null && productOfCategoryNums.get(temppc.getId())>0){
									productCategorys.add(temppc);
								}
							}
						}
					}
				}
				//判断分类是否有子分类以便前端是否显示可以点开的箭头
				Map<Long, Long> num = null;
				
				Map<String, Object> param1 = new HashMap<String,Object>();
				param1.put("clientId", clientId);
				param1.put("parentIds", parentIds);
				param1.put("isMall", false);//不是精品商城的分类
				List<ProductCategory> childs = productCategoryMapper.findCategorys(param1);
				if(childs!=null && childs.size()>0){
					//有子分类的数量
					Map<String, Object> param2 = new HashMap<String,Object>();
					param2.put("clientId", clientId);
					List<Long> parentId1s = new ArrayList<Long>();
					Map<Long, Long> pnum = new HashMap<Long, Long>();//key:子分类没有商品数据的分类id,value:子分类数量
					for(ProductCategory child : childs){
						if(!parentId1s.contains(child.getParentId())){
							parentId1s.add(child.getParentId());
						}
						if(pnum.get(child.getParentId())==null){
							pnum.put(child.getParentId(), 0L);
						}
					}
					param2.put("parentIds", parentId1s);
					param2.put("isMall", false);//不是精品商城的分类
					List<Map<String, Long>>  nums = productCategoryMapper.findCategoryChildNum(param2);
					
					//没有有商品数据的商品分类
					List<Long> childCategoryIds = new ArrayList<Long>();
					String key = null;
					for(ProductCategory child : childs){
						if(!isHaveRedis(clientId, type, time, cityId, areaId, child.getId())){//没有缓存信息需要去查mongo重新设置缓存
							childCategoryIds.add(child.getId());
						} else {//有缓存信息直接去取
							key = RedisKeyUtil.cbbank_product_category_product_client_id_product_category_id_type_area_id(clientId, child.getId(), type, areaId);
							String result = redis.getString(key);
							if("0".equals(result)){
								pnum.put(child.getParentId(), pnum.get(child.getParentId())+1);
							} else if(!"1".equals(result)){
								childCategoryIds.add(child.getId());
							}
						}
					}
					if(childCategoryIds.size()>0){
						Map<Long,Integer> productOfCategoryNums = findProductNumOfCategory(clientId, cityId, areaId, childCategoryIds, type, time);
						for(ProductCategory child : childs){
							if(productOfCategoryNums==null 
									|| productOfCategoryNums.get(child.getId())==null 
									|| productOfCategoryNums.get(child.getId())<=0){
								pnum.put(child.getParentId(), pnum.get(child.getParentId())+1);
							}
						}
					}
					if(nums!=null && nums.size()>0){
						num = new HashMap<Long, Long>();
						for(Map<String, Long> ch : nums){
							//有子分类的数量-没有有商品数据的商品分类就是有商品数据的子分类数量
							num.put(ch.get("id"), ch.get("childNum")-(pnum.get(ch.get("id"))==null?0L:pnum.get(ch.get("id"))));
						}
					}
				}
				categorys.put("childNum", num);
				categorys.put("categorys", productCategorys);
			}
		}catch(Exception e){
			 LogCvt.error("查询商品分类失败，原因:" + e.getMessage(),e); 
		}finally{
			if(null != sqlSession) {
				sqlSession.close(); 
            } 
		}
		return categorys;
	}
	
	/**
	 * 查询该商品分类是否有商品数据
	 * @param clientId 客户端ID
	 * @param cityId 城市id
	 * @param areaId 地区ID
	 * @param productCategoryIds 商品分类ID
	 * @param type 团购商品类型
	 * @param time 缓存时间
	 * @return
	 * @throws Exception
	 */
	private Map<Long,Integer> findProductNumOfCategory(String clientId, Long cityId, Long areaId, List<Long> productCategoryIds, String type, int time) throws Exception {
		long startTime = System.currentTimeMillis();
		LogCvt.debug("查询商品条件下该商品分类是否有商品数据[开始...]时间戳=" + startTime);
		
		// 管道集
		List<DBObject> pipeline = new ArrayList<DBObject>();
		
        
		/*设置查询条件*/
		DBObject where = new BasicDBObject();
		where.put("client_id", clientId);
		where.put("product_type", type);
		where.put("is_marketable", ProductStatus.onShelf.getCode());//已经上架的查询出来
		
		
        if(cityId!=null && cityId>0){//市级地区id
        	where.put("city_areas.city_id", cityId);
        } else if(areaId>0){//区级地区id
        	where.put("city_areas.countys.area_id", areaId);     
        }
        //商品分类查询条件
    	where.put("product_category_info.product_category_id", new BasicDBObject("$in", productCategoryIds));
        where.put("end_time", new BasicDBObject(QueryOperators.GTE, new Date().getTime())); // 结束时间大于等于当前时间,过了团购期商品不显示
        
        /**
         *  联合查询条件
         */
        pipeline.add(new BasicDBObject("$match", where));
        
        //需要查询的字段
        BasicDBObject pro = new BasicDBObject();
        pro.put("_id", "$_id");//商品id
        pro.put("product_category_info", "$product_category_info");//商品id
     			
        pipeline.add(new BasicDBObject("$project", pro));
        
        //分割数组
        pipeline.add(new BasicDBObject("$unwind", "$product_category_info"));
        
        DBObject match = new BasicDBObject();
        match.put("product_category_info.product_category_id", new BasicDBObject("$in", productCategoryIds));
        pipeline.add(new BasicDBObject("$match", match));
        
        DBObject group = new BasicDBObject();
        group.put("_id", "$product_category_info.product_category_id");
        group.put("count", new BasicDBObject("$sum", 1));
        pipeline.add(new BasicDBObject("$group", group));
     			
        List<MongoGroup> mgs = manager.findByPipeline(pipeline, MongoTableName.CB_PRODUCT_DETAIL, MongoGroup.class);
        Map<Long,Integer> m = new HashMap<Long,Integer>();
        if(mgs!=null && mgs.size()>0){
        	String key = null;
        	for(MongoGroup mp : mgs){
        		m.put(Long.valueOf(mp.getId()), mp.getCount());
        	}
        	//设置缓存
        	for(Long id : productCategoryIds){
        		key = RedisKeyUtil.cbbank_product_category_product_client_id_product_category_id_type_area_id(clientId, id, type, areaId);
        		LogCvt.debug("查询商品条件下该商品分类是否有商品数据key[" +key + "]");
        		if(m.get(id)!=null && m.get(id)>0){
    				redis.putString(key, "1");
        			ProductRedis.expire(key, time);
    			} else {
    				redis.putString(key, "0");
        			ProductRedis.expire(key, time);
    			}
    		}
        }
        
		long endTime = System.currentTimeMillis();
		LogCvt.debug("查询商品条件下该商品分类是否有商品数据[结束...]时间戳=" + endTime + ", 耗时:" + (endTime - startTime) + "毫秒!");
		return m;
	}
	

	private boolean isHaveRedis(String clientId, String type, int time, Long cityId, Long areaId, Long categoryId) throws Exception{
		/* 查询商品条件下该商品分类是否有商品数据 */
		String lockKey = RedisKeyUtil.cbbank_product_category_product_lock_client_id_product_category_id_type_area_id(clientId, categoryId, type, areaId);
		long lockResult = ProductRedis.setLock(lockKey, time);
		if (lockResult == 1) {
			LogCvt.debug("查询商品条件下该商品分类是否有商品数据lockKey[" + lockKey + "]是第一次查询mongo");
			return false;
		} else {
			return true;
		}
	}


	@Override
	public List<ProductCategory> getGoodsCategorys(Map<String, Object> param) {
		SqlSession sqlSession = null;
		try{
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			ProductCategoryMapper productCategoryMapper = sqlSession.getMapper(ProductCategoryMapper.class);
			
			List<ProductCategory> categorys = productCategoryMapper.findCategorys(param);
			return categorys;
		} catch(Exception e) {
			 LogCvt.error("商品分类查询mysql失败，原因:" + e.getMessage(),e); 
		} finally{
			if(null != sqlSession) {
				sqlSession.close(); 
            } 
		}
		return null;
	}

	@Override
	public List<ProductCategory> getProductCategoryByIds(String clientId,
			List<String> categoryIds) {
		List<ProductCategory> categoryList = new ArrayList<ProductCategory>();
		CommonLogic commonLogic = new CommonLogicImpl();
		if (Checker.isNotEmpty(categoryIds) && Checker.isNotEmpty(clientId)) {
			for (String categoryId : categoryIds) {
				ProductCategory category = commonLogic
						.findCategoryById(clientId, Long.parseLong(categoryId));
				categoryList.add(category);
			}
		}
		return categoryList;
	}
	
}