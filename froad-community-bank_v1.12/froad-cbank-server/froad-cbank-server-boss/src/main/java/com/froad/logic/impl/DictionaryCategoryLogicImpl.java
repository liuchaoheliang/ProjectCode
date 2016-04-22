package com.froad.logic.impl;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.DictionaryCategoryMapper;
import com.froad.db.mysql.mapper.DictionaryItemMapper;
import com.froad.db.mysql.mapper.DictionaryMapper;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadBusinessException;
import com.froad.exceptions.FroadServerException;
import com.froad.ftp.FtpConstants;
import com.froad.logback.LogCvt;
import com.froad.logic.DictionaryCategoryLogic;
import com.froad.po.Dictionary;
import com.froad.po.DictionaryCategory;
import com.froad.po.DictionaryItem;
import com.froad.scp.ScpUtil;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.Checker;
import com.froad.util.PropertiesUtil;

/**
 * 
 * <p>@Title: DictionaryCategoryLogicImpl.java</p>
 * <p>Description: 描述 </p> 字典类别Logic实现类
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年11月26日
 */
public class DictionaryCategoryLogicImpl implements DictionaryCategoryLogic {


    /**
     * 增加 DictionaryCategory
     * @param dictionaryCategory
     * @return Long    主键ID
     */
	@Override
	public Long addDictionaryCategory(DictionaryCategory dictionaryCategory) throws FroadServerException, Exception{

		Long id = 0l; 
		SqlSession sqlSession = null;
		DictionaryCategoryMapper dictionaryCategoryMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			dictionaryCategoryMapper = sqlSession.getMapper(DictionaryCategoryMapper.class);

			//类别名称不可相同
//			DictionaryCategory filter = new DictionaryCategory();
//			filter.setCategoryName(dictionaryCategory.getCategoryName());
//			List<DictionaryCategory> categoryList = dictionaryCategoryMapper.findDictionaryCategory(filter);
//			if(Checker.isNotEmpty(categoryList)){
//				throw new FroadServerException("类别名称已存在！");
//			}
			
			//添加
			dictionaryCategory.setTreePath("");
			dictionaryCategoryMapper.addDictionaryCategory(dictionaryCategory);
			id = dictionaryCategory.getId();
			
			//treePath组装
			String treePath = "";
	        if(dictionaryCategory.getParentId()>0){
	        	DictionaryCategory filter = new DictionaryCategory();
	        	filter.setId(dictionaryCategory.getParentId());
	        	filter.setIsEnable(true);
	        	DictionaryCategory parentDic = dictionaryCategoryMapper.findOnlyDictionaryCategory(filter);
	            if(Checker.isNotEmpty(parentDic)){
	            	treePath = parentDic.getTreePath();
	            }
	        }
	        
	        DictionaryCategory set = new DictionaryCategory();
	        set.setId(id);
	        set.setTreePath((treePath.trim() + " " + id).trim());
			// 修改treePath成功
			if ( dictionaryCategoryMapper.updateDictionaryCategory(set)) { 
				sqlSession.commit(true); 
			} else { // 修改失败
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
     * 修改 DictionaryCategory
     * @param dictionaryCategory  修改信息
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateDictionaryCategory(DictionaryCategory dictionaryCategory) throws FroadServerException, Exception{

		Boolean result = false; 
		SqlSession sqlSession = null;
		DictionaryCategoryMapper dictionaryCategoryMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			dictionaryCategoryMapper = sqlSession.getMapper(DictionaryCategoryMapper.class);

			result = dictionaryCategoryMapper.updateDictionaryCategory(dictionaryCategory); 
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
     * 删除 DictionaryCategory
     * @param dictionaryCategory
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteDictionaryCategory(Long id) throws FroadServerException, Exception{

		Boolean result = false; 
		SqlSession sqlSession = null;
		DictionaryCategoryMapper dictionaryCategoryMapper = null;
		DictionaryMapper dictionaryMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			dictionaryCategoryMapper = sqlSession.getMapper(DictionaryCategoryMapper.class);
			
			
			/**
			 * 校验是否存在字典或子类型，如存在则提示“此类型下存在子集不允许删除”
			 */
			//1.校验是否存在子类型
			DictionaryCategory filter = new DictionaryCategory();
			filter.setParentId(id);
			filter.setIsEnable(true);
			List<DictionaryCategory> dicCateList = dictionaryCategoryMapper.findDictionaryCategory(filter);
			if(Checker.isNotEmpty(dicCateList)){
				throw new FroadServerException("此类型下存在子集不允许删除");
			}
			//2.校验是否存在字典
			dictionaryMapper = sqlSession.getMapper(DictionaryMapper.class);
			Dictionary dicFilter = new Dictionary();
			dicFilter.setCategoryId(id);
			dicFilter.setIsEnable(true);
			List<Dictionary> dicList = dictionaryMapper.findDictionary(dicFilter);
			if(Checker.isNotEmpty(dicList)){
				throw new FroadServerException("此类型下存在子集不允许删除");
			}
			
			
			/**********************操作MySQL数据库**********************/
			result = dictionaryCategoryMapper.deleteDictionaryCategory(id); 
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
     * 查询 DictionaryCategory
     * @param dictionaryCategory
     * @return List<DictionaryCategory>    结果集合 
     */
	@Override
	public List<DictionaryCategory> findDictionaryCategory(DictionaryCategory dictionaryCategory) throws Exception{

		List<DictionaryCategory> result = new ArrayList<DictionaryCategory>(); 
		SqlSession sqlSession = null;
		DictionaryCategoryMapper dictionaryCategoryMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			dictionaryCategoryMapper = sqlSession.getMapper(DictionaryCategoryMapper.class);

			result = dictionaryCategoryMapper.findDictionaryCategory(dictionaryCategory); 
		} catch (Exception e) { 
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


	/**
     * 查询类别id的子类别信息
     * @param id 类别id
     * @return List<DictionaryCategory>    结果集合 
     */
	@Override
	public List<DictionaryCategory> findChildList(Long id) throws Exception{

		List<DictionaryCategory> result = new ArrayList<DictionaryCategory>(); 
		SqlSession sqlSession = null;
		DictionaryCategoryMapper dictionaryCategoryMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			dictionaryCategoryMapper = sqlSession.getMapper(DictionaryCategoryMapper.class);

			result = dictionaryCategoryMapper.findChildList(id);
		} catch (Exception e) { 
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}
	
	

	/**
	 * 导出脚本(导出该类别下所有的字典、条目信息)
	 * 
	 * findDictionaryCategoryExport:导出脚本
	 * @author froad-ll
	 * 2015年11月30日 下午1:47:37
	 * @param type 1-类别Id 2-字典Id
	 * @param ids 类别id或字典Id集合
	 * @return ExportResultRes
	 */
	@Override
	public ExportResultRes findDictionaryCategoryExport(Integer type,List<Long> ids) throws Exception{
		ResultVo rb =  new ResultVo();
		ExportResultRes resultRes = new ExportResultRes();
		
		String url = null;
		FileWriter writer = null;
		SqlSession sqlSession = null;
		DictionaryMapper dictionaryMapper = null;
		DictionaryItemMapper dictionaryItemMapper = null;
		
		try {
			String dateTime = new SimpleDateFormat("yyyyMMdd").format(new Date());
			String fileName = "DICTIONARY-"+dateTime+".sql";
			
			//取配置文件的本地路径
			Map<String, String> SFTP_PROPERTIES = PropertiesUtil.loadProperties(FtpConstants.FTP_PROPERTIES_NAME);
			String localFileDir = SFTP_PROPERTIES.get(FtpConstants.LOCAL_FILE_DIR);
			String backslash = FtpConstants.SLASH;
			String localPath  =  localFileDir + backslash + FtpConstants.DICTIONARY_SQL;

			File fileDir = new File(localPath);
			if(!fileDir.exists()){
				if(!fileDir.mkdirs()){
					LogCvt.error("创建本地目录"+localPath+"失败！");
					throw new FroadBusinessException(ResultCode.failed.getCode(),"导出失败");
				}
			}
			
			localPath +=  backslash + fileName;
			
			
			/**
			 * 查列表数据
			 */
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			dictionaryItemMapper = sqlSession.getMapper(DictionaryItemMapper.class);
			dictionaryMapper = sqlSession.getMapper(DictionaryMapper.class);
			
			
			Dictionary dicFilter = null;
			List<Dictionary> dicList = null;
			DictionaryItem itemFilter = null;
			List<DictionaryItem> itemList = null;
			String dicCode="";//字典编码
			StringBuffer sql = null;
			String desc = null;
			writer = new FileWriter(new File(localPath));
			writer.write("BEGIN;\r\n");
			
			//类别id导出
			if(type == 1){
				for(Long id :ids){
					//类别id下所有子集类别信息
					List<DictionaryCategory> cateList = this.findChildList(id);
					for(DictionaryCategory cate : cateList){
						//类别id下所有字典信息
						dicFilter = new Dictionary();
						dicFilter.setCategoryId(cate.getId());
						dicList = dictionaryMapper.findDictionary(dicFilter);
						for(Dictionary dic : dicList){
							//字典id下所有条目信息
							itemFilter = new DictionaryItem();
							itemFilter.setDicId(dic.getId());
							itemList = dictionaryItemMapper.findDictionaryItem(itemFilter);
							
							
							dicCode = dic.getDicCode();
							desc = dic.getDepiction()==null?null:("'"+dic.getDepiction().replace("'", "''")+"'");
							sql = new StringBuffer();
							//sql.append("---------------- ").append(dic.getDicName()).append("begin-------------------------\r\n");
							sql.append("delete from cb_dictionary_item where dic_id in (select id from cb_dictionary where dic_code='").append(dicCode.replace("'", "''")).append("');\r\n");
							sql.append("delete from cb_dictionary where dic_code='").append(dicCode.replace("'", "''")).append("';\r\n");
							sql.append("insert into cb_dictionary(dic_code,dic_name,depiction,category_id,order_value,is_enable) values('")
							   .append(dicCode.replace("'", "''")).append("','")
							   .append(dic.getDicName().replace("'", "''")).append("',")
							   .append(desc).append(",")
							   .append(dic.getCategoryId()).append(",")
							   .append(dic.getOrderValue()).append(",")
							   .append(dic.getIsEnable()).append(");\r\n");
							sql.append("SELECT @last_id := MAX(id) FROM cb_dictionary;\r\n");
							
							for(DictionaryItem item : itemList){
								desc = item.getDepiction()==null?null:("'"+item.getDepiction().replace("'", "''")+"'");
								sql.append("insert into cb_dictionary_item(item_code,item_name,item_value,depiction,dic_id,client_id,order_value,is_enable) values('")
								   .append(item.getItemCode().replace("'", "''")).append("','")
								   .append(item.getItemName().replace("'", "''")).append("','")
								   .append(item.getItemValue().replace("'", "''")).append("',")
								   .append(desc).append(",")
								   .append("@last_id").append(",'")
								   .append(item.getClientId()).append("',")
								   .append(item.getOrderValue()).append(",")
								   .append(item.getIsEnable()).append(");\r\n");
							}
							
							//sql.append("---------------- ").append(dic.getDicName()).append("end-------------------------\r\n");
							
							
							//写入文件
							writer.write(sql.toString());
						}
						
					}
				}
			}
			//字典id导出
			else if(type == 2){
				Dictionary dic = null;
				for(Long id :ids){
					dic = dictionaryMapper.findDictionaryById(id);
					if(Checker.isEmpty(dic)){
						continue;
					}
					
					//字典id下所有条目信息
					itemFilter = new DictionaryItem();
					itemFilter.setDicId(id);
					itemList = dictionaryItemMapper.findDictionaryItem(itemFilter);
					
					
					dicCode = dic.getDicCode();
					desc = dic.getDepiction()==null?null:("'"+dic.getDepiction().replace("'", "''")+"'");
					sql = new StringBuffer();
					//sql.append("---------------- ").append(dic.getDicName()).append("begin-------------------------\r\n");
					sql.append("delete from cb_dictionary_item where dic_id in (select id from cb_dictionary where dic_code='").append(dicCode.replace("'", "''")).append("');\r\n");
					sql.append("delete from cb_dictionary where dic_code='").append(dicCode.replace("'", "''")).append("';\r\n");
					sql.append("insert into cb_dictionary(dic_code,dic_name,depiction,category_id,order_value,is_enable) values('")
					   .append(dicCode.replace("'", "''")).append("','")
					   .append(dic.getDicName().replace("'", "''")).append("',")
					   .append(desc).append(",")
					   .append(dic.getCategoryId()).append(",")
					   .append(dic.getOrderValue()).append(",")
					   .append(dic.getIsEnable()).append(");\r\n");
					sql.append("SELECT @last_id := MAX(id) FROM cb_dictionary;\r\n");
					
					for(DictionaryItem item : itemList){
						desc = item.getDepiction()==null?null:("'"+item.getDepiction().replace("'", "''")+"'");
						sql.append("insert into cb_dictionary_item(item_code,item_name,item_value,depiction,dic_id,client_id,order_value,is_enable) values('")
						   .append(item.getItemCode().replace("'", "''")).append("','")
						   .append(item.getItemName().replace("'", "''")).append("','")
						   .append(item.getItemValue().replace("'", "''")).append("',")
						   .append(desc).append(",")
						   .append("@last_id").append(",'")
						   .append(item.getClientId()).append("',")
						   .append(item.getOrderValue()).append(",")
						   .append(item.getIsEnable()).append(");\r\n");
					}
					
					//sql.append("---------------- ").append(dic.getDicName()).append("end-------------------------\r\n");
					
					
					//写入文件
					writer.write(sql.toString());
				}
				
			}
			
			
			if(Checker.isNotEmpty(sql)){
				writer.write("Commit;\r\n");
				writer.write("	END;\r\n");
			}else{
				writer = new FileWriter(new File(localPath));
				writer.write("");//清空内容
			}
			
			if(null != writer){
				writer.flush();
				writer.close();
			}
		     
			try {
				//上传
				url = ScpUtil.uploadFile(localPath, fileName);
			} catch (Exception e) {
				LogCvt.error("上传字典脚本文件失败", e);
				rb.setResultCode(ResultCode.failed.getCode());
				rb.setResultDesc(ResultCode.failed.getMsg());
			}
			
		} catch (Exception e) {
			LogCvt.error("导出字典脚本出错："+e.getMessage());
			rb.setResultCode(ResultCode.failed.getCode());
			rb.setResultDesc(ResultCode.failed.getMsg());
		}finally{
			if(null != sqlSession){
				sqlSession.close();
			}
		}
		
		if(StringUtils.isNotEmpty(url)) {
			rb.setResultCode(ResultCode.success.getCode());
			rb.setResultDesc(ResultCode.success.getMsg());
		} else {
			rb.setResultCode(ResultCode.failed.getCode());
			rb.setResultDesc(ResultCode.failed.getMsg());
		}
		resultRes.setResultVo(rb);
		resultRes.setUrl(url);
		return resultRes;
	}
	
	
	


}