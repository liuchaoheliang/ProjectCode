package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSON;
import com.froad.Constants;
import com.froad.db.mongo.OutletDetailMongo;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.OutletMapper;
import com.froad.db.mysql.mapper.OutletTempMapper;
import com.froad.db.redis.OutletRedis;
import com.froad.db.redis.impl.RedisManager;
import com.froad.enums.AccountTypeEnum;
import com.froad.enums.ProductAuditState;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.MerchantAccountLogic;
import com.froad.logic.MerchantCategoryLogic;
import com.froad.logic.MerchantLogic;
import com.froad.logic.MerchantOutletPhotoLogic;
import com.froad.logic.OutletAuditLogic;
import com.froad.logic.OutletLogic;
import com.froad.po.Merchant;
import com.froad.po.MerchantAccount;
import com.froad.po.MerchantCategory;
import com.froad.po.MerchantOutletPhoto;
import com.froad.po.Outlet;
import com.froad.po.OutletTemp;
import com.froad.po.Result;
import com.froad.po.mongo.CategoryInfo;
import com.froad.po.mongo.OutletDetail;
import com.froad.po.mq.AuditMQ;
import com.froad.support.Support;
import com.froad.thrift.vo.OutletVo;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;
import com.froad.util.ClientUtil;
import com.froad.util.JSonUtil;
import com.froad.util.RedisMQKeys;


public class OutletAuditLogicImpl implements OutletAuditLogic {
	private Support support = new Support();
	private OutletLogic outletLogic = new OutletLogicImpl();
	private MerchantOutletPhotoLogic photoLogic = new MerchantOutletPhotoLogicImpl();
	private MerchantCategoryLogic merchantCategoryLogic = new MerchantCategoryLogicImpl();
	private RedisManager reids = new RedisManager();

	
	/**
	 * 门店录入审核操作
	 * @param auditMq 审核队列对象
	 * @param outlet  门店对象
	 * 
	 * 业务流程
	 * 1.审核通过时
	 * 	 a.进行白名单同步
	 *   b.白名单同步成功时，更新cb_Merchant_account表的同步状态，台州为1=同步成功，1=审核中，其他银行为1=同步成功，2=审核通过。如果审核都通过，还需更新cb_outlet的audit_state状态为1=审核通过
	 *     白名单同步失败时，更新cb_Merchant_account表的同步状态为2同步失败
	 *   c.审核通过时若为首次录入改为默认门店
	 * 2.审核不通过
	 *   a.更新门店表cb_outlet的audit_state状态为2=审核不通过
	 */
	public Result auditOutlet(AuditMQ auditMq, Outlet outlet) throws Exception {
		Result res = new Result();
		Outlet upOut = new Outlet();
		
		upOut.setClientId(outlet.getClientId());
		upOut.setMerchantId(outlet.getMerchantId());
		upOut.setOutletId(outlet.getOutletId());
		
		
		upOut.setAuditStaff(StringUtils.isNotBlank(auditMq.getAuditStaff())?auditMq.getAuditStaff():"");
		upOut.setAuditComment(StringUtils.isNotBlank(auditMq.getAuditComment())?auditMq.getAuditComment():"");
		upOut.setAuditTime(auditMq.getFinishTime()==null?new Date():new Date(auditMq.getFinishTime()));
		
		//判断是否审核通过
		if(auditMq.getAuditState().equals(ProductAuditState.passAudit.getCode())){
			
			//同步白名单
			MerchantAccountLogic mcl = new MerchantAccountLogicImpl();
			MerchantAccount macct = mcl.findMerchantAccountByOutletId(outlet.getOutletId());
			if(null != macct){
				res = mcl.registBankWhiteList(macct, "0", Constants.INPUT_AUDIT_OUTLET_TYPE);
				
				//需落地审核流程
				if(ClientUtil.getClientId(outlet.getClientId())){
					upOut.setAuditState(ProductAuditState.waitSynchAudit.getCode());
				}else{
					if(res.getResultCode().equals(ResultCode.success.getCode())){
						upOut.setIsEnable(true);
						upOut.setAuditState(ProductAuditState.passAudit.getCode());
				       if(isFirstTimeAdd(upOut)){
				        upOut.setIsDefault(true);
				          }
					}else{
						upOut.setAuditState(ProductAuditState.noAudit.getCode());
					}
				}			
			}else{
				//更新门店状态
				upOut.setIsEnable(true);
				upOut.setAuditState(ProductAuditState.passAudit.getCode());
                if(isFirstTimeAdd(upOut)){
                upOut.setIsDefault(true);
                }
			}
			
			//更新门店审核状态（1=通过，2=不通过）
			if(!outletLogic.updateOutlet(upOut)){
				throw new FroadServerException("更新门店审核状态失败！");
			}else{
				if(res.getResultCode().equals(ResultCode.success.getCode()) && 
						!ClientUtil.getClientId(outlet.getClientId()) ){
					//更新mongodb中cb_outlet_detail启用状态为有效
					OutletDetailMongo odm = new OutletDetailMongo();
					OutletDetail outletDetail = new OutletDetail();
					outletDetail.setIsEnable(true);
					outletDetail.setId(outlet.getOutletId());
					if(!odm.updateOutletDetail(outletDetail)){
						throw new FroadServerException("更新mongo门店信息失败，门店Id=" + outlet.getOutletId());
					}
				}
			}
		}else{//审核不通过
			//更新门店audit_state审核状态为2
			upOut.setAuditState(ProductAuditState.failAudit.getCode());
			if(!outletLogic.updateOutlet(upOut)){
				throw new FroadServerException("更新门店审核状态失败！");
			}
		}
		
		//更新Redis门店缓存
		if(!OutletRedis.updateOutletRedis(upOut)){
			throw new FroadServerException("更新Redis门店信息失败，门店Id=" + outlet.getOutletId());
		}
		
		return res;
	}

	/**
	 * 门店编辑审核操作.
	 * @param auditId
	 * @return
	 * @throws Exception
	 */
	@Override
	public Result auditOutletEdit(AuditMQ auditMq,Outlet oldOuelet) throws Exception {
		boolean isSuccess = false;
		SqlSession sqlSession = null;
		OutletTempMapper outletTempMapper = null;
		MerchantOutletPhoto outletPhoto = null;
		Result result =  new Result();
		
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			outletTempMapper = sqlSession.getMapper(OutletTempMapper.class);

			//1：先查询outletTemp
			OutletTemp outletTemp = outletTempMapper.findOutletTempByAuditId(auditMq.getAuditId());
			if(null != outletTemp){
				Outlet outlet = getOutlet(outletTemp,auditMq,oldOuelet);
//				isSuccess = outletLogic.updateOutlet(outlet);
					if(auditMq.getIsFinalAudit() && auditMq.getAuditState().equals(ProductAuditState.passAudit.getCode())){
						OutletVo outletVo = (OutletVo) BeanUtil.copyProperties(OutletVo.class, outlet);
						LogCvt.info("转换前="+outletVo);
						support.updateGroupProductByOutlet(outlet.getClientId(), "2", outletVo,result);	
						//1:获取旧相册数据
						Set<String> beforPhotoSet = new HashSet<String>();
						Set<String> tempPhotoSet = new HashSet<String>();
						Set<String> isDelPhotoSet = new HashSet<String>();
						List<MerchantOutletPhoto> merchantOutletPhotoList = new ArrayList<MerchantOutletPhoto>();
						if(StringUtils.isNotBlank(outletTemp.getPhotoList())){								
							//2:修改相册
							String outletTempPhotoList = outletTemp.getPhotoList();
							LogCvt.info("修改后的相册=" + outletTempPhotoList);
							List<Object> photoList = JSonUtil.toObject(outletTempPhotoList, ArrayList.class);
							for (Object photo : photoList) {
								outletPhoto = JSonUtil.toObject(JSonUtil.toJSonString(photo),MerchantOutletPhoto.class);
								tempPhotoSet.add(outletPhoto.getSource());
								outletPhoto.setOutletId(outletTemp.getOutletId());
								outletPhoto.setMerchantId(outletTemp.getMerchantId());
								merchantOutletPhotoList.add(outletPhoto);
							}						
						}
						isSuccess = processMerchantAccount(isSuccess,outlet,outletTemp,result);
						
						if(isSuccess){						
							//1:第一步、修改相册
							MerchantOutletPhoto merchantOutletPhoto = new MerchantOutletPhoto();
							merchantOutletPhoto.setOutletId(outletTemp.getOutletId());
							merchantOutletPhoto.setMerchantId(outletTemp.getMerchantId());
							List<MerchantOutletPhoto> merchantOutletOldPhotoList = photoLogic.findMerchantOutletPhoto(merchantOutletPhoto);
							LogCvt.info("修改前的相册=" +JSON.toJSONString(merchantOutletOldPhotoList));
							for(MerchantOutletPhoto beforMerchantOutletPhoto:merchantOutletOldPhotoList){
								beforPhotoSet.add(beforMerchantOutletPhoto.getSource());
							}
							if(null != tempPhotoSet && tempPhotoSet.size()>0){
								String currentPhotoSource = "";
								for(Iterator<String> iterator = beforPhotoSet.iterator();iterator.hasNext();){  
								 	currentPhotoSource = iterator.next();
									//3:比较修改前相册和修改后相册差别，把修改后相册不存在的相册key进行删除									
									if(!tempPhotoSet.contains(currentPhotoSource)){
										isDelPhotoSet.add(currentPhotoSource);
									}
						        }  
							}
							//2:第二步、整理相册删除source的key
							if (null != merchantOutletPhotoList	&& merchantOutletPhotoList.size() > 0) {
								isSuccess = photoLogic.saveMerchantOutletPhoto(merchantOutletPhotoList);
								if(isSuccess){
									if(null != isDelPhotoSet && isDelPhotoSet.size()>0){
										reids.putSet(RedisMQKeys.cbbank_undelete_images,isDelPhotoSet);
										LogCvt.info("待删除的图片Source信息="+JSON.toJSONString(isDelPhotoSet));
									}
								}
						   }
					 }		
				 }else{
					 isSuccess = outletLogic.updateOutlet(outlet);
				 }
				if(isSuccess){					
					sqlSession.commit(true);
				}else{
					sqlSession.rollback(true);
					result.setResultCode(ResultCode.failed.getCode());
					result.setResultDesc(ResultCode.failed.getMsg());
				}
			}else{
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc(ResultCode.failed.getMsg());
			}
			
		} catch (Exception e) {
			if(null != sqlSession)  
				sqlSession.rollback(true); 
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
			throw e;
		}finally {
			if(null != sqlSession)
				sqlSession.close();
		}
		return result;
	}
	
	//判断是否是第一次录入的门店
	public boolean isFirstTimeAdd(Outlet outlet) throws Exception{
		SqlSession sqlSession=MyBatisManager.getSqlSessionFactory().openSession();
		OutletMapper outletMapper =sqlSession.getMapper(OutletMapper.class);
		Outlet outlet1=new Outlet();
		outlet1.setMerchantId(outlet.getMerchantId());
		outlet1.setIsDefault(true);
		outlet1.setAuditState(outlet.getAuditState());
		try{
			Integer num=outletMapper.countIsDefault(outlet1);
			if(num!=null&&num>0){
				return false;
			}else{
				return true;
			}
		}catch(Exception e){
			LogCvt.error("判断是否默认门店失败,原因："+e.getMessage(),e);
		}finally{
			if(null!=sqlSession)
				sqlSession.close();
		}
		return false;
	}
	/**
	 * 获取门店信息内部方法.
	 * 
	 * @param outletTemp
	 * @param task
	 * @return
	 */
	private Outlet getOutlet(OutletTemp outletTemp,AuditMQ auditMq,Outlet oldOutlet){
		Outlet outlet = null;
		//先判断审核状态，如果审核通过，判断是否终审状态,如果不是终审，不处理，否则都需要处理,1审核通过,2审核未通过
		if(auditMq.getAuditState().equals(ProductAuditState.passAudit.getCode())){
			if(auditMq.getIsFinalAudit()){
				outlet =  new Outlet();
				outlet.setMerchantId(outletTemp.getMerchantId());
				outlet.setClientId(outletTemp.getClientId());
				outlet.setOutletId(outletTemp.getOutletId());
				if(outletTemp.getAreaId()>0 && outletTemp.getAreaId()!=oldOutlet.getAreaId()){
				   outlet.setAreaId(outletTemp.getAreaId());
				}
				if(StringUtils.isNotBlank(outletTemp.getOutletName())){
					if(!outletTemp.getOutletName().equals(oldOutlet.getOutletName())){
						outlet.setOutletName(outletTemp.getOutletName());
					}
				}
				if(StringUtils.isNotBlank(outletTemp.getOutletFullName())){
					if(!outletTemp.getOutletFullName().equals(oldOutlet.getOutletFullname())){
						outlet.setOutletFullname(outletTemp.getOutletFullName());	
					}									
				}
				if(StringUtils.isNotBlank(outletTemp.getAddress())){
					if(!outletTemp.getAddress().equals(oldOutlet.getAddress())){
						outlet.setAddress(outletTemp.getAddress());
					}
				}
				if(StringUtils.isNotBlank(outletTemp.getBusinessHours())){
					if(!outletTemp.getBusinessHours().equals(oldOutlet.getBusinessHours())){
						outlet.setBusinessHours(outletTemp.getBusinessHours());
					}
				}
				if(StringUtils.isNotBlank(outletTemp.getZip())){
					if(!outletTemp.getZip().equals(oldOutlet.getZip())){
						outlet.setZip(outletTemp.getZip());
					}
				}
				if(StringUtils.isNotBlank(outletTemp.getFax())){
					if(!outletTemp.getFax().equals(oldOutlet.getFax())){
						outlet.setFax(outletTemp.getFax());
					}					
				}
				if(StringUtils.isNotBlank(outletTemp.getPhone())){
					if(!outletTemp.getPhone().equals(oldOutlet.getPhone())){
						outlet.setPhone(outletTemp.getPhone());
					}
				}
				if(StringUtils.isNotBlank(outletTemp.getContactName())){
					if(!outletTemp.getContactName().equals(oldOutlet.getContactName())){
						outlet.setContactName(outletTemp.getContactName());
					}					
				}
				if(StringUtils.isNotBlank(outletTemp.getContactPhone())){
					if(!outletTemp.getContactPhone().equals(oldOutlet.getContactPhone())){
						outlet.setContactPhone(outletTemp.getContactPhone());
					}					
				}
				if(StringUtils.isNotBlank(outletTemp.getContactEmail())){
					if(!outletTemp.getContactEmail().equals(oldOutlet.getContactEmail())){
						outlet.setContactEmail(outletTemp.getContactEmail());
					}					
				}
				if(StringUtils.isNotBlank(outletTemp.getLatitude())){
					if(!outletTemp.getLatitude().equals(oldOutlet.getLatitude())){
						outlet.setLatitude(outletTemp.getLatitude());
					}					
				}
				if(StringUtils.isNotBlank(outletTemp.getLongitude())){
					if(!outletTemp.getLongitude().equals(oldOutlet.getLongitude())){
						outlet.setLongitude(outletTemp.getLongitude());
					}					
				}				
				if(StringUtils.isNotBlank(outletTemp.getDescription())){
					if(!outletTemp.getDescription().equals(oldOutlet.getDescription())){
						outlet.setDescription(outletTemp.getDescription());						
					}
				}				
				if(StringUtils.isNotBlank(outletTemp.getPreferDetails())){
					if(!outletTemp.getPreferDetails().equals(oldOutlet.getPreferDetails())){
						outlet.setPreferDetails(outletTemp.getPreferDetails());						
					}
				}
				if(StringUtils.isNotBlank(outletTemp.getDiscount())){
					if(!outletTemp.getDiscount().equals(oldOutlet.getDiscount())){
						outlet.setDiscount(outletTemp.getDiscount());						
					}
				}
				if(StringUtils.isNotBlank(outletTemp.getDiscountCode())){
					if(!outletTemp.getDiscountCode().equals(oldOutlet.getDiscountCode())){
						outlet.setDiscountCode(outletTemp.getDiscountCode());						
					}
				}
				if(StringUtils.isNotBlank(outletTemp.getDiscountRate())){
					if(!outletTemp.getDiscountRate().equals(oldOutlet.getDiscountRate())){
						outlet.setDiscountRate(outletTemp.getDiscountRate());						
					}
				}
				if(StringUtils.isNotBlank(auditMq.getAuditState())){
					outlet.setEditAuditState(auditMq.getAuditState());	
				}
				outlet.setAuditComment(auditMq.getAuditComment()==null?"":auditMq.getAuditComment());
				if(StringUtils.isNotBlank(auditMq.getAuditStaff())){
					outlet.setAuditStaff(auditMq.getAuditStaff());
				}
				//迭代1.7.0修改
				outlet.setIsDefault(oldOutlet.getIsDefault());
				outlet.setAuditTime(auditMq.getFinishTime()==null?new Date():new Date(auditMq.getFinishTime()));
				//商户分类
				if (Checker.isNotEmpty(outletTemp.getOutletCategoryId())) {
					
					List<MerchantCategory> merchantCategoryList = null;
					List<CategoryInfo> categoryInfoList = new ArrayList<CategoryInfo>();
					
					String[] cid = StringUtils.split(outletTemp.getOutletCategoryId(),",");
					List<Long> categoryIdList = new ArrayList<Long>();
					for (int i = 0; i < cid.length; i++) {
						categoryIdList.add(Long.parseLong(cid[i]));
					}
					LogCvt.debug("根据上送的分类id查询分类信息");
					
					merchantCategoryList = merchantCategoryLogic.findMerchantCategoryByCategoryIdList(outlet.getClientId(), categoryIdList);
					for (MerchantCategory mc : merchantCategoryList) {
						CategoryInfo ci = new CategoryInfo();
						ci.setCategoryId(mc.getId());
						ci.setName(mc.getName());
						categoryInfoList.add(ci);
					}
					
					//门店分类
					outlet.setCategoryInfo(categoryInfoList);
				}
				
			}else{
				LogCvt.info("审核状态="+auditMq.getAuditState()+",审核描述="+auditMq.getAuditComment()+"非终审状态不处理");
			}
		}else if(auditMq.getAuditState().equals(ProductAuditState.failAudit.getCode())){
			outlet = new Outlet();
			if(StringUtils.isNotBlank(outletTemp.getMerchantId())){
				outlet.setMerchantId(outletTemp.getMerchantId());
			}
			if(StringUtils.isNotBlank(auditMq.getClientId())){
				outlet.setClientId(auditMq.getClientId());
			}
			if(StringUtils.isNotBlank(outletTemp.getOutletId())){
				outlet.setOutletId(outletTemp.getOutletId());
			}
			outlet.setAuditTime(auditMq.getFinishTime()==null?new Date():new Date(auditMq.getFinishTime()));
			outlet.setAuditComment(auditMq.getAuditComment()==null?"":auditMq.getAuditComment());
			outlet.setAuditStaff(auditMq.getAuditStaff()==null?"":auditMq.getAuditStaff());
			outlet.setEditAuditState(auditMq.getAuditState());	
		}else{
			LogCvt.info("审核状态="+auditMq.getAuditState()+",审核描述="+auditMq.getAuditComment()+"不处理");
		}
        return outlet;	
	}
	

	/**
	 * 获取门店审核信息内部方法.
	 * 
	 * @param auditId
	 * @return
	 * @throws Exception
	 */
	@Override
	public OutletTemp findAuditOutletByAuditId(String auditId) throws Exception {
		SqlSession sqlSession = null; 
		OutletTempMapper outletTempMapper = null;
		OutletTemp outletTemp = new OutletTemp();
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			outletTempMapper = sqlSession.getMapper(OutletTempMapper.class);
			
			outletTemp = outletTempMapper.findOutletTempByAuditId(auditId);
			
		} catch (Exception e) {
			if(null != sqlSession)  
				sqlSession.rollback(true); 
			throw e;
		}finally {
			if(null != sqlSession)
				sqlSession.close();
		}
		return outletTemp;
	}

	
	/**
	 * 获取门店审核通过门店信息.
	 * 
	 * @param outletTemp
	 * @return
	 * @throws Exception
	 */
	@Override
	public OutletTemp findOutletTemp(OutletTemp outletTemp)throws Exception {
		SqlSession sqlSession = null;
		OutletTempMapper outletTempMapper = null;
		OutletTemp outletTemps = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			outletTempMapper = sqlSession.getMapper(OutletTempMapper.class);
			
			outletTemps = outletTempMapper.findOutletTemp(outletTemp);
		} catch (Exception e) {
			if(null != sqlSession)  
				sqlSession.rollback(true); 
			throw e;
		}finally {
			if(null != sqlSession)
				sqlSession.close();
		}
		return outletTemps;
	}


	/**
	 * 删除门店临时表操作.
	 */
	@Override
	public Boolean removeOutletTemp(OutletTemp outletTemp) throws Exception {
		Boolean result = null; 
		SqlSession sqlSession = null;
		OutletTempMapper outletTempMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			outletTempMapper = sqlSession.getMapper(OutletTempMapper.class);
			result = outletTempMapper.removeOutletTemp(outletTemp);
			if(result){
				sqlSession.commit(true); 
			} else {
				sqlSession.rollback(true); 
			}							
		} catch (FroadServerException e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true); 
			result = null; 
			throw e;
		
		} catch (Exception e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			result = false; 
			throw e;
		} finally {
			if(null != sqlSession)
				sqlSession.close();
		}
		return result; 
	}

	/**
	 * 门店临时表新增操作，返回门店Id.
	 * 
	 */
	@Override
	public String addOutletTemp(OutletTemp outletTemp) throws Exception {
		Boolean result = null; 
		String outletId = "";
		SqlSession sqlSession = null;
		OutletTempMapper outletTempMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			outletTempMapper = sqlSession.getMapper(OutletTempMapper.class);
			result = outletTempMapper.addOutletTemp(outletTemp);
			if(result){
				sqlSession.commit(true); 
				outletId = outletTemp.getOutletId();
			} else {
				sqlSession.rollback(true); 
			}							
		} catch (FroadServerException e) { 
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
		return outletId; 
	}


	/**
	 * 修改门店编辑信息.
	 */
	@Override
	public Boolean uddateOutletTemp(OutletTemp outletTemp) throws Exception {
		Boolean result = null; 
		SqlSession sqlSession = null;
		OutletTempMapper outletTempMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			outletTempMapper = sqlSession.getMapper(OutletTempMapper.class);
			result = outletTempMapper.uddateOutletTemp(outletTemp);
			if(result){
				sqlSession.commit(true); 
			} else {
				sqlSession.rollback(true); 
			}							
		} catch (FroadServerException e) { 
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
	 * 通过门店Id获取门店编辑信息.
	 */
	@Override
	public OutletTemp findOutletTempByOutletId(String outletId,String auditId)	throws Exception {
		SqlSession sqlSession = null;
		OutletTempMapper outletTempMapper = null;
		OutletTemp outletTemps = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			outletTempMapper = sqlSession.getMapper(OutletTempMapper.class);
			
			outletTemps = outletTempMapper.findOutletTempByOutletId(outletId,auditId);
		} catch (Exception e) {
			if(null != sqlSession)  
				sqlSession.rollback(true); 
			throw e;
		}finally {
			if(null != sqlSession)
				sqlSession.close();
		}
		return outletTemps;
	}

	/**
	 * 更改门店审核流水号.
	 */
	@Override
	public Boolean updateOutletTempByAuditId(OutletTemp outletTemp)throws Exception{
	Boolean result = null; 
	SqlSession sqlSession = null;
	OutletTempMapper outletTempMapper = null;
	try { 
		/**********************操作MySQL数据库**********************/
		sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
		outletTempMapper = sqlSession.getMapper(OutletTempMapper.class);
		result = outletTempMapper.updateOutletTempByAuditId(outletTemp);
		if(result){
			sqlSession.commit(true); 
		} else {
			sqlSession.rollback(true); 
		}							
	} catch (FroadServerException e) { 
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
	 * 门店管理：编辑审核内部处理门店账号信息.
	 * @param isSuccess
	 * @param outletTemp
	 * @param result
	 * @return
	 * @throws Exception
	 */
	private boolean processMerchantAccount(boolean isSuccess,Outlet whiteOutlet,OutletTemp outletTemp,Result result) throws Exception{
			MerchantAccountLogic accountLogic = new MerchantAccountLogicImpl(); 
			MerchantLogic merchantLogic = new MerchantLogicImpl();
			MerchantAccount findAccount = new MerchantAccount();
			findAccount.setClientId(outletTemp.getClientId());
			findAccount.setMerchantId(outletTemp.getMerchantId());
			findAccount.setOutletId(outletTemp.getOutletId());
			findAccount.setAcctType(AccountTypeEnum.SQ.getCode());
			findAccount.setIsDelete(false);
			List<MerchantAccount> merchantAccountList = accountLogic.findMerchantAccount(findAccount);
			MerchantAccount  account = new MerchantAccount();;
			MerchantAccount targetAccount = null;
			if(null != merchantAccountList && merchantAccountList.size()>0){
				//1:如果数据库有值，填写为空
				account = merchantAccountList.get(0);
				if(StringUtils.isEmpty(outletTemp.getAcctName()) || StringUtils.isEmpty(outletTemp.getAcctNumber())){
					isSuccess = accountLogic.deleteMerchantAccount(account);
					whiteOutlet.setOutletId(outletTemp.getOutletId());
						//同步成功
					whiteOutlet.setEditAuditState(ProductAuditState.passAudit.getCode());
				}else{
					//2:如果数据库有值，填写有值
					if(StringUtils.isNotBlank(outletTemp.getAcctNumber()) || StringUtils.isNotBlank(outletTemp.getAcctName())){	
						if(outletTemp.getAcctName().equals(account.getAcctName()) && outletTemp.getAcctNumber().equals(account.getAcctNumber())){
							whiteOutlet.setOutletId(outletTemp.getOutletId());
							//同步成功
						    whiteOutlet.setEditAuditState(ProductAuditState.passAudit.getCode());
						}else{
							targetAccount = new MerchantAccount();
							targetAccount.setClientId(account.getClientId());
							targetAccount.setMerchantId(account.getMerchantId());
							targetAccount.setOutletId(account.getOutletId());
							targetAccount.setAcctType(account.getAcctType());
							targetAccount.setOpeningBank(account.getOpeningBank());
							targetAccount.setIsDelete(false);
							if(!outletTemp.getAcctName().equals(account.getAcctName())){
								targetAccount.setAcctName(outletTemp.getAcctName());											
							}
							if(!outletTemp.getAcctNumber().equals(account.getAcctNumber())){
								targetAccount.setAcctNumber(outletTemp.getAcctNumber());										
							}	
							isSuccess = accountLogic.updateMerchantAccount(targetAccount);
							
							//编辑审核同步白名单. 目前考虑安徽编辑审核，审核状态为审核通过，重庆编辑审核暂时不考虑
							if(isSuccess){
								whiteOutlet = syncAuditSuccess(result,targetAccount,outletTemp,whiteOutlet,accountLogic);
							}
					     }
					}
				}				
			}else{
				//1:如果数据库为空，填写有值
				if(StringUtils.isNotBlank(outletTemp.getAcctNumber()) || StringUtils.isNotBlank(outletTemp.getAcctName())){	
					//1:执行新增
					Merchant merchant = merchantLogic.findMerchantByMerchantId(outletTemp.getMerchantId());	
					if(null!= merchant){
						MerchantAccount merchantAccount = new MerchantAccount();
						merchantAccount.setClientId(outletTemp.getClientId());
						merchantAccount.setMerchantId(outletTemp.getMerchantId());
						merchantAccount.setOutletId(outletTemp.getOutletId());
						merchantAccount.setAcctName(outletTemp.getAcctName()==null?"":outletTemp.getAcctName());
						merchantAccount.setAcctNumber(outletTemp.getAcctNumber()==null?"":outletTemp.getAcctNumber());
						merchantAccount.setIsDelete(false);					
						merchantAccount.setAcctType(AccountTypeEnum.SQ.getCode());
						merchantAccount.setOpeningBank(merchant.getOrgCode()==null?"":merchant.getOrgCode());						
						Long addResult = accountLogic.addMerchantAccount(merchantAccount);
						if(null != addResult && addResult >0){
							isSuccess = true;
							//编辑审核同步白名单. 目前考虑安徽编辑审核，审核状态为审核通过，重庆编辑审核暂时不考虑
						    whiteOutlet = syncAuditSuccess(result,merchantAccount,outletTemp,whiteOutlet,accountLogic);
						}						
					}
				}else{
					whiteOutlet.setOutletId(outletTemp.getOutletId());
					whiteOutlet.setEditAuditState(ProductAuditState.passAudit.getCode());
				}
			}
			if(null != whiteOutlet){
				LogCvt.info("编辑审核门店信息,账户信息前为空:"+whiteOutlet);
				isSuccess = outletLogic.updateOutlet(whiteOutlet);	
			}
	    return isSuccess;
	}	
	
	
	/**
	 * 新增、修改门店账户时内部同步白名单操作.
	 * @param isSync
	 * @param result
	 * @param merchantAccount
	 * @param outletTemp
	 * @param whiteOutlet
	 * @param accountLogic
	 * @throws Exception
	 */
	private Outlet syncAuditSuccess(Result result,MerchantAccount merchantAccount,OutletTemp outletTemp,Outlet whiteOutlet,MerchantAccountLogic accountLogic)throws Exception{
		boolean isSync = false;
		//编辑审核同步白名单. 目前考虑安徽编辑审核，审核状态为审核通过，重庆编辑审核暂时不考虑
		result = accountLogic.registBankWhiteList(merchantAccount, "0", Constants.INPUT_AUDIT_OUTLET_TYPE);
		if(result.getResultCode().equals(ResultCode.success.getCode())){
			 isSync = true;
			 result.setResultCode(ResultCode.success.getCode());
			 result.setResultDesc(ResultCode.success.getMsg());						
			LogCvt.info("门店编辑审核同步白名单成功,accountNo="+merchantAccount.getAcctNumber()+",accountName="+merchantAccount.getAcctName());
		}else{
			 isSync = false;
			 result.setResultCode(ResultCode.failed.getCode());
		     result.setResultDesc("门店编辑审核同步失败");
		}	
		whiteOutlet.setOutletId(outletTemp.getOutletId());
		//走行内审核
		if(ClientUtil.getClientId(outletTemp.getClientId())){
			whiteOutlet.setEditAuditState(ProductAuditState.waitSynchAudit.getCode());
		}else{
			//同步成功
			if(isSync){
				whiteOutlet.setEditAuditState(ProductAuditState.passAudit.getCode());
			}else{
				whiteOutlet.setEditAuditState(ProductAuditState.noAudit.getCode());
			}
		}
		return whiteOutlet;
	}
}