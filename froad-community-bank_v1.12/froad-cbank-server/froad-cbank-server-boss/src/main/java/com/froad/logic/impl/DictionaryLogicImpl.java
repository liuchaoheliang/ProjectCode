package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.DictionaryCategoryMapper;
import com.froad.db.mysql.mapper.DictionaryItemMapper;
import com.froad.db.mysql.mapper.DictionaryMapper;
import com.froad.exceptions.FroadServerException;
import com.froad.logic.DictionaryLogic;
import com.froad.po.Dictionary;
import com.froad.po.DictionaryCategory;
import com.froad.util.Checker;

/**
 * 
 * <p>@Title: DictionaryLogicImpl.java</p>
 * <p>Description: 描述 </p> 商户审核配置Logic实现类
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年11月26日
 */
public class DictionaryLogicImpl implements DictionaryLogic {


    /**
     * 增加 Dictionary
     * @param Dictionary
     * @return Long    主键ID
     */
	@Override
	public Long addDictionary(Dictionary dictionary) throws FroadServerException, Exception{

		Long id = 0l; 
		SqlSession sqlSession = null;
		DictionaryMapper dictionaryMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			dictionaryMapper = sqlSession.getMapper(DictionaryMapper.class);

			//字典编码不可相同
			Dictionary filter = new Dictionary();
			filter.setDicCode(dictionary.getDicCode());
			List<Dictionary> dicList = dictionaryMapper.findDictionary(filter);
			if(Checker.isNotEmpty(dicList)){
				throw new FroadServerException("字典编码已存在！");
			}
			
			// 添加成功
			if (dictionaryMapper.addDictionary(dictionary) > -1) { 
				id = dictionary.getId(); 
				sqlSession.commit(true); 
			} else { // 添加失败
				sqlSession.rollback(true); 
			}
			
			
			
		}catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e;
		} catch (Exception e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return id; 

	}

	
	/**
     * 删除 Dictionary
     * @param Dictionary
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteDictionaryBatch(List<Long> dicIds) throws FroadServerException, Exception{

		Boolean result = false; 
		SqlSession sqlSession = null;
		DictionaryMapper dictionaryMapper = null;
		DictionaryItemMapper dictionaryItemMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			dictionaryMapper = sqlSession.getMapper(DictionaryMapper.class);
			dictionaryItemMapper = sqlSession.getMapper(DictionaryItemMapper.class);
			
			/**
			 * 删除字典则将字典下的条目均删除（逻辑删除）
			 */
			//1.删条目
			result = dictionaryItemMapper.deleteDictionaryItemBatchByDicId(dicIds);
			
			//2.删字典
			result = dictionaryMapper.deleteDictionaryBatch(dicIds);
			
			/**********************操作MySQL数据库**********************/
			if (result) { // 删除成功
				sqlSession.commit(true);
			} else { // 删除失败
				sqlSession.rollback(true); 
			}
			
			
		}catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e;
		} catch (Exception e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);   
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}
	
	
	/**
     * 修改 Dictionary
     * @param dictionary  修改信息
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateDictionary(Dictionary dictionary) throws FroadServerException, Exception{

		Boolean result = false; 
		SqlSession sqlSession = null;
		DictionaryMapper dictionaryMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			dictionaryMapper = sqlSession.getMapper(DictionaryMapper.class);

			
			//判断是否修改字典编码
			Dictionary oldDic = dictionaryMapper.findDictionaryById(dictionary.getId());
			if(Checker.isEmpty(oldDic)){
				throw new FroadServerException("字典对象不存在！");
			}
			
			//修改了字典编码(判断编码唯一性)
			if(!oldDic.getDicCode().equals(dictionary.getDicCode())){
				//字典编码不可相同
				Dictionary filter = new Dictionary();
				filter.setDicCode(dictionary.getDicCode());
				List<Dictionary> dicList = dictionaryMapper.findDictionary(filter);
				if(Checker.isNotEmpty(dicList)){
					throw new FroadServerException("字典编码已存在！");
				}
			}
			
			
			
			result = dictionaryMapper.updateDictionary(dictionary);
			if (result) { // 修改成功
				sqlSession.commit(true);
			} else { // 修改失败
				sqlSession.rollback(true); 
			}
			
			/**********************操作Redis缓存**********************/
			
			
			
		}catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e;
		} catch (Exception e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}
	
	
	
	/**
     * 根据主键id查询 Dictionary
     * @param id
     * @return Dictionary 字典对象
     */
	@Override
	public Dictionary findDictionaryById(Long id) throws Exception{

		Dictionary result = null;
		SqlSession sqlSession = null;
		DictionaryMapper dictionaryMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			dictionaryMapper = sqlSession.getMapper(DictionaryMapper.class);

			result = dictionaryMapper.findDictionaryById(id);
		} catch (Exception e) { 
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}
	
	
	

	/**
     * 分页查询 Dictionary
     * @param page
     * @param Dictionary
     * @param type 1-当前类别下字典查询  2-当前类别下所有子类别中字典查询
     * @return Page<Dictionary>    结果集合 
     */
	@Override
	public Page<Dictionary> findDictionaryByPage(Page<Dictionary> page, Dictionary dictionary,int type) throws Exception{

		List<Dictionary> result = new ArrayList<Dictionary>(); 
		SqlSession sqlSession = null;
		DictionaryMapper dictionaryMapper = null;
		DictionaryCategoryMapper dictionaryCategoryMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			dictionaryMapper = sqlSession.getMapper(DictionaryMapper.class);
			dictionaryCategoryMapper = sqlSession.getMapper(DictionaryCategoryMapper.class);
			
			List<Long> childIds = null;
			if(type == 2){
				// 2-当前类别下所有子类别中字典查询
				
				childIds = new ArrayList<Long>();
				
				//查询类别id下的子集类别id
				List<DictionaryCategory> subDicLists = dictionaryCategoryMapper.findChildList(dictionary.getCategoryId());
				if(Checker.isEmpty(subDicLists)){
					throw new FroadServerException(dictionary.getDicName()+"上级关联类别对象异常");
				}
				for(DictionaryCategory dic:subDicLists){
					childIds.add(dic.getId());
				}
				dictionary.setCategoryId(null);
			}
			
			
			result = dictionaryMapper.findByPage(page, dictionary,childIds); 
			page.setResultsContent(result);
			
		} catch (Exception e) { 
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 

	}




	


}