/**
 * Project Name:froad-cbank-server-boss
 * File Name:BusinessZoneTagLogicImpl.java
 * Package Name:com.froad.logic.impl
 * Date:2015年10月23日上午10:46:01
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.logic.impl;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.AreaMapper;
import com.froad.db.mysql.mapper.BusinessZoneTagMapper;
import com.froad.db.mysql.mapper.RecommendActivityCounterMapper;
import com.froad.db.mysql.mapper.RecommendActivityTagMapper;
import com.froad.db.redis.impl.RedisManager;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadBusinessException;
import com.froad.ftp.ExcelWriter;
import com.froad.logback.LogCvt;
import com.froad.logic.BusinessZoneTagLogic;
import com.froad.po.Area;
import com.froad.po.BusinessZoneTag;
import com.froad.po.BusinessZoneTagDto;
import com.froad.po.BusinessZoneTagOutlet;
import com.froad.po.RecommendActivityCounter;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.businesszonetag.BusinessZoneTagAddReq;
import com.froad.thrift.vo.businesszonetag.BusinessZoneTagAddRes;
import com.froad.thrift.vo.businesszonetag.BusinessZoneTagDeleteReq;
import com.froad.thrift.vo.businesszonetag.BusinessZoneTagDeleteRes;
import com.froad.thrift.vo.businesszonetag.BusinessZoneTagDetailReq;
import com.froad.thrift.vo.businesszonetag.BusinessZoneTagDetailRes;
import com.froad.thrift.vo.businesszonetag.BusinessZoneTagExportOutletReq;
import com.froad.thrift.vo.businesszonetag.BusinessZoneTagExportOutletRes;
import com.froad.thrift.vo.businesszonetag.BusinessZoneTagListReq;
import com.froad.thrift.vo.businesszonetag.BusinessZoneTagListRes;
import com.froad.thrift.vo.businesszonetag.BusinessZoneTagVo;
import com.froad.thrift.vo.businesszonetag.GenerateActivityNoReq;
import com.froad.thrift.vo.businesszonetag.GenerateActivityNoRes;
import com.froad.util.BeanUtil;
import com.froad.util.BusinessZoneTagRedis;
import com.froad.util.RedisKeyUtil;

/**
 * ClassName:BusinessZoneTagLogicImpl
 * Reason:	 TODO ADD REASON.
 * Date:     2015年10月23日 上午10:46:01
 * @author   kevin
 * @version  
 * @see 	 
 */
public class BusinessZoneTagLogicImpl implements BusinessZoneTagLogic {

	@Override
	public BusinessZoneTagListRes getBusinessZoneTagList(BusinessZoneTagListReq req) {
		SqlSession session = null;
		BusinessZoneTagMapper tagMapper = null;
		BusinessZoneTagListRes res = new BusinessZoneTagListRes();
		ResultVo rv = new ResultVo();
		List<BusinessZoneTagVo> resTagList = new ArrayList<BusinessZoneTagVo>();
		try {
			session = MyBatisManager.getSqlSessionFactory().openSession();
			tagMapper = session.getMapper(BusinessZoneTagMapper.class);
			Page<BusinessZoneTag> page = (Page)BeanUtil.copyProperties(Page.class, req.getPageVo());
			BusinessZoneTag reqTag = new BusinessZoneTag();
			reqTag.setClientId(req.getClientId());
			if(!"0".equals(req.getStatus())) {
				reqTag.setStatus(req.getStatus());
			}
			
			reqTag.setZoneName(req.getTagName());
			List<BusinessZoneTagDto> tagList = tagMapper.findByPage(page, reqTag);
			if(tagList != null && tagList.size() > 0) {
				for(BusinessZoneTagDto t : tagList) {
					BusinessZoneTagVo vo = new BusinessZoneTagVo();
					vo.setId(t.getId().toString());
					vo.setClientName(t.getBankName());
					vo.setTagName(t.getZoneName());
					vo.setDesc(t.getDesc());
					vo.setStatus(t.getStatus());
					//地区信息
					Long areaId = null;
					if(t.getFareaId() != null) {
						areaId = t.getFareaId();
					}
					if(t.getSareaId() != null) {
						areaId = t.getSareaId();
					}
					if(t.getTareaId() != null) {
						areaId = t.getTareaId();
					}
					if(areaId != null) {
						AreaMapper areaMapper = session.getMapper(AreaMapper.class);
						Area area = areaMapper.findById(areaId);
						if(area != null) {
							if("chongqing".equals(t.getClientId()) || "shanghai".equals(t.getClientId()) || "beijing".equals(t.getClientId()) || "tianjin".equals(t.getClientId())) {
								String areaName = area.getTreePathName();
								vo.setAreaName(areaName.substring(areaName.indexOf(",") + 1, areaName.length()));
							} else {
								vo.setAreaName(area.getTreePathName());
							}
						}
					}
					resTagList.add(vo);
				}
			}
			PageVo pageVo = (PageVo)BeanUtil.copyProperties(PageVo.class, page);
			rv.setResultCode(ResultCode.success.getCode());
			rv.setResultDesc(ResultCode.success.getMsg());
			res.setPageVo(pageVo);
			res.setBusinessZoneTagList(resTagList);
		} catch (Exception e) { 
			LogCvt.error("查询商圈标签列表失败，原因:" + e.getMessage(), e); 
			rv.setResultCode(ResultCode.failed.getCode());
			rv.setResultDesc(ResultCode.failed.getMsg());
		} finally { 
			if(null != session)  
				session.close();  
		}
		res.setResultVo(rv);
		return res;
	}
	

	@Override
	public BusinessZoneTagAddRes editBusinessZoneTag(BusinessZoneTagAddReq req) {
		SqlSession sqlSession = null;
		BusinessZoneTagMapper tagMapper = null;
		BusinessZoneTagAddRes res = new BusinessZoneTagAddRes();
		ResultVo rv = new ResultVo();
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession(false);
			tagMapper = sqlSession.getMapper(BusinessZoneTagMapper.class);
			//校验
			BusinessZoneTag rsTag = tagMapper.findById(Long.valueOf(req.getId()));
			//如果用户编辑中没有更改商圈名称、一级区域、二级区域、三级区域（四级区域暂时用不到）字段则不进行校验
			Boolean isChange = false;
			//商圈名称是否更改校验
			if(!req.getTagName().equals(rsTag.getZoneName())) {
				isChange = true;
			}
			//一级区域（必填项）是否更改校验
			if(!req.getFareadId().equals(rsTag.getFareaId().toString())) {
				isChange = true;
			}
			//二级区域是否更改校验
			if(StringUtils.isNotEmpty(req.getSareadId())) {
				if(rsTag.getSareaId() == null || !req.getSareadId().equals(rsTag.getSareaId().toString())) {
					isChange = true;
				}
			} else {
				if(rsTag.getSareaId() != null) {
					isChange = true;
				}
			}
			//三级区域是否更改校验
			if(StringUtils.isNotEmpty(req.getTareadId())) {
				if(rsTag.getTareaId() == null || !req.getTareadId().equals(rsTag.getTareaId().toString())) {
					isChange = true;
				}
			} else {
				if(rsTag.getTareaId() != null) {
					isChange = true;
				}
			}
			
			//如果有更改则校验
			if(isChange) {
				BusinessZoneTag reqTag = new BusinessZoneTag();
				reqTag.setClientId(req.getClientId());
				if(StringUtils.isNotEmpty(req.getFareadId()) ) {
					reqTag.setFareaId(Long.valueOf(req.getFareadId()));
				}
				if(StringUtils.isNotEmpty(req.getSareadId())) {
					reqTag.setSareaId(Long.valueOf(req.getSareadId()));
				}
				if(StringUtils.isNotEmpty(req.getTareadId())) {
					reqTag.setTareaId(Long.valueOf(req.getTareadId()));
				}
				reqTag.setZoneName(req.getTagName());
				int checkCount = tagMapper.findByConditionCount(reqTag);
				if(checkCount > 0) {
					throw new FroadBusinessException(ResultCode.business_zone_name_not_only.getCode(), ResultCode.business_zone_name_not_only.getMsg());
				}
			}
			
			Date date = Calendar.getInstance().getTime();
			BusinessZoneTag t = new BusinessZoneTag();
			t.setId(Long.valueOf(req.getId()));
			t.setClientId(req.getClientId());
			t.setCreateTime(date);
			t.setDesc(req.getDesc());
			if(StringUtils.isNotEmpty(req.getFareadId())) {
				t.setFareaId(Long.valueOf(req.getFareadId()));
			}
			if(StringUtils.isNotEmpty(req.getSareadId())) {
				t.setSareaId(Long.valueOf(req.getSareadId()));
			} else {
				t.setSareaId(null);
			}
			if(StringUtils.isNotEmpty(req.getTareadId())) {
				t.setTareaId(Long.valueOf(req.getTareadId()));
			} else {
				t.setTareaId(null);
			}
			t.setSerialNumber(Long.valueOf(req.getSortValue()));
			t.setStatus(req.getStatus());
			t.setZoneName(req.getTagName());
			t.setUpdateTime(date);
			int count = tagMapper.update(t);
			if(count == 0) {
				throw new FroadBusinessException(ResultCode.update_zone_name_not_exists.getCode(), ResultCode.update_zone_name_not_exists.getMsg());
			}
			
			BusinessZoneTagRedis.srem_cbbank_business_zone_tag_client_id_id(t);
			BusinessZoneTagRedis.set_cbbank_business_zone_tag_client_id(t.getClientId(), t.getId().toString());
			//添加信息到缓存
			BusinessZoneTagRedis.del_cbbank_business_zone_tag_client_id(t);
			BusinessZoneTagRedis.set_cbbank_business_zone_tag_client_id_id(t);
			sqlSession.commit(true);
			rv.setResultCode(ResultCode.success.getCode());
			rv.setResultDesc(ResultCode.success.getMsg());
		} catch (FroadBusinessException e) {
			LogCvt.error("更新商圈标签列表失败，原因:" + e.getMsg(), e); 
			rv.setResultCode(e.getCode());
			rv.setResultDesc(e.getMsg());
		} catch (Exception e) {
			LogCvt.error("更新商圈标签信息失败，原因:" + e.getMessage(), e); 
			rv.setResultCode(ResultCode.failed.getCode());
			rv.setResultDesc(ResultCode.failed.getMsg());
		}
		res.setResultVo(rv);
		return res;
	}

	@Override
	public BusinessZoneTagAddRes addBusinessZoneTag(BusinessZoneTagAddReq req) {
		SqlSession sqlSession = null;
		BusinessZoneTagMapper tagMapper = null;
		BusinessZoneTagAddRes res = new BusinessZoneTagAddRes();
		ResultVo rv = new ResultVo();
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession(false);
			tagMapper = sqlSession.getMapper(BusinessZoneTagMapper.class);
			//进行商圈名称校验，同级下商圈名唯一
			BusinessZoneTag reqTag = new BusinessZoneTag();
			reqTag.setClientId(req.getClientId());
			if(StringUtils.isNotEmpty(req.getFareadId()) ) {
				reqTag.setFareaId(Long.valueOf(req.getFareadId()));
			}
			if(StringUtils.isNotEmpty(req.getSareadId())) {
				reqTag.setSareaId(Long.valueOf(req.getSareadId()));
			}
			if(StringUtils.isNotEmpty(req.getTareadId())) {
				reqTag.setTareaId(Long.valueOf(req.getTareadId()));
			}
			
			reqTag.setZoneName(req.getTagName());
			int count = tagMapper.findByConditionCount(reqTag);
			if(count > 0) {
				throw new FroadBusinessException(ResultCode.business_zone_name_not_only.getCode(), ResultCode.business_zone_name_not_only.getMsg());
			}
			
			Date date = Calendar.getInstance().getTime();
			BusinessZoneTag t = new BusinessZoneTag();
			t.setClientId(req.getClientId());
			t.setCreateTime(date);
			t.setDesc(req.getDesc());
			if(StringUtils.isNotEmpty(req.getFareadId() )) {
				t.setFareaId(Long.valueOf(req.getFareadId()));
			}
			if(StringUtils.isNotEmpty(req.getSareadId() )) {
				t.setSareaId(Long.valueOf(req.getSareadId()));
			}
			if(StringUtils.isNotEmpty(req.getTareadId() )) {
				t.setTareaId(Long.valueOf(req.getTareadId()));
			}
			
			t.setSerialNumber(Long.valueOf(req.getSortValue()));
			t.setStatus(req.getStatus());
			t.setZoneName(req.getTagName());
			tagMapper.insert(t);
			//添加信息到缓存
			BusinessZoneTagRedis.set_cbbank_business_zone_tag_client_id_id(t);
			BusinessZoneTagRedis.set_cbbank_business_zone_tag_client_id(t.getClientId(), t.getId().toString());
			sqlSession.commit(true);
			rv.setResultCode(ResultCode.success.getCode());
			rv.setResultDesc(ResultCode.success.getMsg());
		} catch (FroadBusinessException e) {
			LogCvt.error("新增商圈标签列表失败，原因:" + e.getMsg(), e); 
			rv.setResultCode(e.getCode());
			rv.setResultDesc(e.getMsg());
		} catch (Exception e) {
			LogCvt.error("新增商圈标签信息失败，原因:" + e.getMessage(), e); 
			rv.setResultCode(ResultCode.failed.getCode());
			rv.setResultDesc(ResultCode.failed.getMsg());
		}
		res.setResultVo(rv);
		return res;
	}

	@Override
	public BusinessZoneTagExportOutletRes exportBusinessZoneTag(BusinessZoneTagExportOutletReq req) {
		ResultVo rb =  new ResultVo();
		BusinessZoneTagExportOutletRes resultRes = new BusinessZoneTagExportOutletRes();
		List<String> header = new ArrayList<String>();
		header.add("序号");
		header.add("所属客户端");
		header.add("商圈");
		header.add("门店名称");
		header.add("门店ID");
		header.add("门店地址");
		header.add("所属商户");
		header.add("所属商户ID");
		List<List<String>> allData = new ArrayList<List<String>>();
		List<BusinessZoneTagOutlet> outletList = null;
		String url = null;
		SqlSession sqlSession = null;
		BusinessZoneTagMapper tagMapper = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession(false);
			tagMapper = sqlSession.getMapper(BusinessZoneTagMapper.class);
			//暂时屏蔽掉商圈门店导出功能
			//outletList = tagMapper.findTagOutletList(Long.valueOf(req.getId()));
			outletList = new ArrayList<BusinessZoneTagOutlet>();
			if (outletList != null) {
				allData = convertExportOutlet(outletList);
			}
			
			ExcelWriter excelWriter = new ExcelWriter(allData.size() == 0 ? 1 : allData.size());
			try {
				excelWriter.write(header, allData, "商圈商户门店列表", "商圈商户门店列表");
				url = excelWriter.getUrl();
			} catch (Exception e) {
				LogCvt.error("商圈商户门店列表失败", e);
				rb.setResultCode(ResultCode.failed.getCode());
				rb.setResultDesc(ResultCode.failed.getMsg());
			}
			
		} catch (Exception e) {
			LogCvt.error("导出出错", e);
			rb.setResultCode(ResultCode.failed.getCode());
			rb.setResultDesc(ResultCode.failed.getMsg());
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

	private List<List<String>>  convertExportOutlet(List<BusinessZoneTagOutlet> list) {
		List<List<String>> perList = new ArrayList<List<String>>();
		if(list == null || list.size() == 0) {
			return perList;
		}
		for(int i = 0; i < list.size(); i++) {
			List<String> rowList = new ArrayList<String>();
			BusinessZoneTagOutlet vo = list.get(i);
			rowList.add(i + 1 + "");
			rowList.add(vo.getBankName());
			rowList.add(vo.getZoneName());
			rowList.add(vo.getOutletName());
			rowList.add(vo.getOutletId());
			rowList.add(vo.getAddress());
			rowList.add(vo.getMerchantName());
			rowList.add(vo.getMerchantId());
			perList.add(rowList);
		}
		return perList;
	}

	@Override
	public BusinessZoneTagDetailRes getBusinessZoneTag(BusinessZoneTagDetailReq req) {
		SqlSession sqlSession = null;
		BusinessZoneTagMapper tagMapper = null;
		BusinessZoneTagDetailRes res = new BusinessZoneTagDetailRes();
		ResultVo rv = new ResultVo();
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			tagMapper = sqlSession.getMapper(BusinessZoneTagMapper.class);
			BusinessZoneTag tag = tagMapper.findById(Long.valueOf(req.getId()));
			if(tag != null) {
				res.setClientId(tag.getClientId());
				res.setDesc(tag.getDesc());
				if(tag.getFareaId() != null) {
					res.setFareadId(tag.getFareaId().toString());
				}
				res.setId(tag.getId().toString());
				if(tag.getOareaId() != null) {
					res.setOareadId(tag.getOareaId().toString());
				}
				if(tag.getSareaId() != null) {
					res.setSareadId(tag.getSareaId().toString());
				}
				if(tag.getSerialNumber() != null) {
					res.setSortValue(tag.getSerialNumber().toString());
				}
				res.setStatus(tag.getStatus());
				res.setTagName(tag.getZoneName());
				if(tag.getTareaId() != null) {
					res.setTareadId(tag.getTareaId().toString());
				}
				rv.setResultCode(ResultCode.success.getCode());
				rv.setResultDesc(ResultCode.success.getMsg());
			} else {
				rv.setResultCode(ResultCode.failed.getCode());
				rv.setResultDesc(ResultCode.failed.getMsg());
			}
		}  catch (Exception e) {
			LogCvt.error("查询商圈标签信息失败，原因:" + e.getMessage(), e); 
			rv.setResultCode(ResultCode.failed.getCode());
			rv.setResultDesc(ResultCode.failed.getMsg());
		}
		res.setResultVo(rv);
		return res;
	}



	@Override
	public BusinessZoneTagDeleteRes deleteBusinessZoneTag(BusinessZoneTagDeleteReq req) {
		// TODO Auto-generated method stub
		SqlSession sqlSession = null;
		BusinessZoneTagMapper tagMapper = null;
		BusinessZoneTagDeleteRes res = new BusinessZoneTagDeleteRes();
		ResultVo rv = new ResultVo();
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession(false);
			tagMapper = sqlSession.getMapper(BusinessZoneTagMapper.class);
			BusinessZoneTag tag = tagMapper.findById(Long.valueOf(req.getId()));
			if(tag == null) {
				throw new FroadBusinessException(ResultCode.update_zone_name_not_exists.getCode(), ResultCode.update_zone_name_not_exists.getMsg());
			}
			Date date = Calendar.getInstance().getTime();
			BusinessZoneTag upTag = new BusinessZoneTag();
			upTag.setId(Long.valueOf(req.getId()));
			upTag.setStatus("2");
			upTag.setUpdateTime(date);
			int count = tagMapper.update(upTag);
			if(count == 0) {
				throw new FroadBusinessException(ResultCode.update_zone_name_not_exists.getCode(), ResultCode.update_zone_name_not_exists.getMsg());
			}
			
			//查询缓存是否存在
			BusinessZoneTagRedis.del_cbbank_business_zone_tag_client_id(tag);
			BusinessZoneTagRedis.srem_cbbank_business_zone_tag_client_id_id(tag);
			
			tag.setStatus("2");
			BusinessZoneTagRedis.set_cbbank_business_zone_tag_client_id_id(tag);
			BusinessZoneTagRedis.set_cbbank_business_zone_tag_client_id(tag.getClientId(), tag.getId().toString());
			sqlSession.commit(true);
		} catch (FroadBusinessException e) {
			LogCvt.error("禁用商圈标签失败，原因:" + e.getMessage(), e); 
			rv.setResultCode(e.getCode());
			rv.setResultDesc(e.getMsg());
		}  catch (Exception e) {
			LogCvt.error("禁用商圈标签失败，原因:" + e.getMessage(), e); 
			rv.setResultCode(ResultCode.failed.getCode());
			rv.setResultDesc(ResultCode.failed.getMsg());
		}
		res.setResultVo(rv);
		return res;
	}

	@Override
	public GenerateActivityNoRes generateActivityNo(GenerateActivityNoReq req) {
		// TODO Auto-generated method stub
		ResultVo rv = new ResultVo(); 
		GenerateActivityNoRes res = new GenerateActivityNoRes();
		SqlSession sqlSession = null;
		RecommendActivityCounterMapper tagMapper = null;
		String key = RedisKeyUtil.cbbank_activity_no_lock_client_id_type(req.getClientId(), req.getActivityType());
		RedisManager redisManager = new RedisManager();
		try {
			Long flag = redisManager.setnx(key, req.getActivityType());
			if(flag == 0) {
				throw new FroadBusinessException();
			}
			//查询活动个数
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession(false);
			tagMapper = sqlSession.getMapper(RecommendActivityCounterMapper.class);
			RecommendActivityCounter reqCounter = new RecommendActivityCounter();
			reqCounter.setClientId(req.getClientId());
			reqCounter.setActivityType(req.getActivityType());
			List<RecommendActivityCounter> counterList = tagMapper.findByCondition(reqCounter);
			long count = 0;
			Date date = Calendar.getInstance().getTime();
			if(counterList == null || counterList.size() == 0) {
				reqCounter.setCounter(count);
				reqCounter.setCreateTime(date);
				tagMapper.add(reqCounter);
			} else {
				RecommendActivityCounter rc = counterList.get(0);
				count = rc.getCounter() + 1;
				RecommendActivityCounter c = new RecommendActivityCounter();
				c.setId(rc.getId());
				c.setCounter(count);
				c.setUpdateTime(date);
				tagMapper.update(c);
			}
			
			StringBuffer sb = new StringBuffer();
			sb.append(req.getClientId() + "-");
			sb.append(Calendar.getInstance().get(Calendar.YEAR) + "-");
			//生成活动编号加锁
			sb.append(formatNumberToString(count));
			redisManager.del(key);
			sqlSession.commit(true);
			res.setActiviyNo(sb.toString());
			rv.setResultCode(ResultCode.success.getCode());
			rv.setResultDesc(ResultCode.success.getMsg());
		} catch (FroadBusinessException e) {
			LogCvt.info("生成活动编号加锁失败：clientId:" + req.getClientId() + ";activityType:" + req.getActivityType());
			rv.setResultCode(ResultCode.failed.getCode());
			rv.setResultDesc(ResultCode.failed.getMsg());
		}  catch (Exception e) {
			redisManager.del(key);
			rv.setResultCode(ResultCode.failed.getCode());
			rv.setResultDesc(ResultCode.failed.getMsg());
		}
		res.setResultVo(rv);
		return res;
	}
	
	
	private String formatNumberToString(long i) {
		 //得到一个NumberFormat的实例
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        nf.setMaximumIntegerDigits(3);
        nf.setMinimumIntegerDigits(3);
        return nf.format(i);
	}
	
}
