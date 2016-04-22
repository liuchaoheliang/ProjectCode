package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.DictionaryItemMapper;
import com.froad.exceptions.FroadServerException;
import com.froad.logic.DictionaryItemLogic;
import com.froad.po.DictionaryItem;
import com.froad.util.Checker;

/**
 * 
 * <p>@Title: DictionaryItemLogicImpl.java</p>
 * <p>Description: 描述 </p> 字典条目Logic实现类
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年11月26日
 */
public class DictionaryItemLogicImpl implements DictionaryItemLogic {


    /**
     * 增加 DictionaryItem
     * @param dictionaryItem
     * @return Long    主键ID
     */
	@Override
	public Long addDictionaryItem(DictionaryItem dictionaryItem) throws FroadServerException, Exception{

		Long id = 0l; 
		SqlSession sqlSession = null;
		DictionaryItemMapper dictionaryItemMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			dictionaryItemMapper = sqlSession.getMapper(DictionaryItemMapper.class);

			//条目编码不可相同(当前字典条目中唯一)
			DictionaryItem filter = new DictionaryItem();
			filter.setItemCode(dictionaryItem.getItemCode());
			filter.setDicId(dictionaryItem.getDicId());
			List<DictionaryItem> itemList = dictionaryItemMapper.findDictionaryItem(filter);
			if(Checker.isNotEmpty(itemList)){
				throw new FroadServerException("条目编码已存在！");
			}
			
			// 添加成功
			if (dictionaryItemMapper.addDictionaryItem(dictionaryItem) > -1) { 
				id = dictionaryItem.getId(); 
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
     * 删除 DictionaryItem
     * @param DictionaryItem
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteDictionaryItemBatch(List<Long> itemIds) throws FroadServerException, Exception{

		Boolean result = false; 
		SqlSession sqlSession = null;
		DictionaryItemMapper dictionaryItemMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			dictionaryItemMapper = sqlSession.getMapper(DictionaryItemMapper.class);
			
			/**********************操作Redis缓存**********************/
			
			
			/**********************操作MySQL数据库**********************/
			result = dictionaryItemMapper.deleteDictionaryItemBatch(itemIds);
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
     * 修改 DictionaryItem
     * @param dictionaryItem  修改信息
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateDictionaryItem(DictionaryItem dictionaryItem) throws FroadServerException, Exception{

		Boolean result = false; 
		SqlSession sqlSession = null;
		DictionaryItemMapper dictionaryItemMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			dictionaryItemMapper = sqlSession.getMapper(DictionaryItemMapper.class);

			//判断是否修改条目编码
			DictionaryItem oldItem = dictionaryItemMapper.findDictionaryItemById(dictionaryItem.getId());
			if(Checker.isEmpty(oldItem)){
				throw new FroadServerException("条目对象不存在！");
			}
			
			//修改了条目编码(判断编码唯一性)
			if(!oldItem.getItemCode().equals(dictionaryItem.getItemCode())){
				//条目编码不可相同(当前字典条目中唯一)
				DictionaryItem filter = new DictionaryItem();
				filter.setItemCode(dictionaryItem.getItemCode());
				filter.setDicId(dictionaryItem.getDicId());
				List<DictionaryItem> itemList = dictionaryItemMapper.findDictionaryItem(filter);
				if(Checker.isNotEmpty(itemList)){
					throw new FroadServerException("条目编码已存在！");
				}
			}
			
			
			
			result = dictionaryItemMapper.updateDictionaryItem(dictionaryItem); 
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
     * 分页查询 DictionaryItem
     * @param page
     * @param DictionaryItem
     * @return Page<DictionaryItem>    结果集合 
     */
	@Override
	public Page<DictionaryItem> findDictionaryItemByPage(Page<DictionaryItem> page, DictionaryItem dictionaryItem) throws Exception{

		List<DictionaryItem> result = new ArrayList<DictionaryItem>(); 
		SqlSession sqlSession = null;
		DictionaryItemMapper dictionaryItemMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			dictionaryItemMapper = sqlSession.getMapper(DictionaryItemMapper.class);

			result = dictionaryItemMapper.findByPage(page, dictionaryItem); 
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