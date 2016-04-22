package com.froad.handler.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.ActiveBaseRuleMapper;
import com.froad.db.mysql.mapper.ActiveSustainRelationMapper;
import com.froad.db.mysql.mapper.ActiveTagRelationMapper;
import com.froad.db.mysql.mapper.VouchersDetailRuleMapper;
import com.froad.db.mysql.mapper.VouchersInfoMapper;
import com.froad.db.mysql.mapper.VouchersMapper;
import com.froad.db.redis.VouchersRedis;
import com.froad.enums.ActiveIdCode;
import com.froad.enums.ResultCode;
import com.froad.handler.VouchersRuleInfoHandler;
import com.froad.logback.LogCvt;
import com.froad.po.ActiveBaseRule;
import com.froad.po.ActiveSustainRelation;
import com.froad.po.ActiveTagRelation;
import com.froad.po.Vouchers;
import com.froad.po.VouchersDetailRule;
import com.froad.po.VouchersInfo;
import com.froad.po.VouchersRuleInfo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.active.FindVouchersRuleInfoVoResultVo;
import com.froad.util.RedisKeyUtil;

/**
 * @ClassName: VouchersRuleInfoHandlerImpl
 * @Description: 代金券信息数据处理接口实现
 * @author froad-shenshaocheng 2015年11月26日
 * @modify froad-shenshaocheng 2015年11月26日
 */
public class VouchersRuleInfoHandlerImpl implements VouchersRuleInfoHandler {

	@Override
	public Long addVouchersRuleInfo(OriginVo originVo,
			VouchersRuleInfo vouchersRuleInfo) {
		SqlSession sqlSession = MyBatisManager.getSqlSessionFactory()
				.openSession();
		long result = 0;
		long vouchersResule = 0;
		try {
			VouchersMapper vouchersMapper = sqlSession
					.getMapper(VouchersMapper.class);
			VouchersInfoMapper vouchersInfoMapper = sqlSession
					.getMapper(VouchersInfoMapper.class);
			result += vouchersMapper.addVouchersRuleInfo(vouchersRuleInfo);
			result += vouchersMapper.addVouchersDetailInfo(vouchersRuleInfo);
			result += vouchersMapper.addActiveTagRelationInfo(vouchersRuleInfo);
			if(vouchersRuleInfo.getVouchersDetailRule().getTemporaryActiveId() != null 
					&& !"".equals(vouchersRuleInfo.getVouchersDetailRule().getTemporaryActiveId())) {
				vouchersResule = vouchersMapper.copyVouchersInfo(vouchersRuleInfo);
				vouchersResule += vouchersMapper.updateVouchersInfo(vouchersRuleInfo);
				if(vouchersResule > 0) {
					LogCvt.debug("新增红包券码表完成，共添加 " + vouchersResule/2 + "条记录。");
				}
			}
			//result += vouchersMapper.addVouchersInfo(vouchersRuleInfo);

			LogCvt.debug("需要保存的红包支持活动 "
					+ vouchersRuleInfo.getVouchersDetailRule()
							.getActiveSustainRelationList());
			StringBuffer activeNameString = new StringBuffer();
			for (ActiveSustainRelation aSustainRelation : vouchersRuleInfo
					.getVouchersDetailRule().getActiveSustainRelationList()) {
				activeNameString
						.append(aSustainRelation.getSustainActiveName())
						.append(";");
			}

			if (null != vouchersRuleInfo.getVouchersDetailRule()
					.getActiveSustainRelationList()
					&& vouchersRuleInfo.getVouchersDetailRule()
							.getActiveSustainRelationList().size() > 0) {
				LogCvt.debug("红包支持活动数："
						+ vouchersRuleInfo.getVouchersDetailRule()
								.getActiveSustainRelationList().size() + "名称为："
						+ activeNameString.toString());
				List<ActiveSustainRelation> sustainRelationList = new ArrayList<ActiveSustainRelation>();
				for(ActiveSustainRelation sustainRelation :
					vouchersRuleInfo.getVouchersDetailRule().getActiveSustainRelationList()) {
					sustainRelation.setUpdateTime(new Date(System.currentTimeMillis()));
					sustainRelationList.add(sustainRelation);
				}
				
				vouchersRuleInfo.getVouchersDetailRule().setActiveSustainRelationList(sustainRelationList);
				result += vouchersMapper.addActiveSustainInfo(vouchersRuleInfo);
			}

			if (result > 1) {
				sqlSession.commit();
				// 新增成功，删除临时表数据
				if (vouchersRuleInfo.getVouchersDetailRule() != null
						&& vouchersRuleInfo.getVouchersDetailRule()
								.getTemporaryActiveId() != null) {
					long resultcode = vouchersInfoMapper
							.deleteTemporaryVouchersInfoByActiveId(
									vouchersRuleInfo.getVouchersDetailRule()
									.getTemporaryActiveId());
					if (resultcode > 0) {
						sqlSession.commit();
						LogCvt.debug("删除临时劵码成功");
					} else {
						LogCvt.debug("删除临时劵码失败");
					}
				}

				// 初始化redis
				VouchersRedis.initVouchersActive(
						vouchersRuleInfo.getActiveBaseRule(),
						vouchersRuleInfo.getVouchersDetailRule(),
						vouchersRuleInfo.getActiveTagRelation());
				Set<String> sustainActiveSet = new HashSet<String>();
				if (vouchersRuleInfo.getActiveSustainRelationList().size() > 0) {
					for (ActiveSustainRelation activeSustainRelation : vouchersRuleInfo
							.getActiveSustainRelationList()) {
						sustainActiveSet.add(activeSustainRelation
								.getSustainActiveId());
					}
					VouchersRedis.setSustainActive(vouchersRuleInfo
							.getActiveBaseRule().getClientId(),
							vouchersRuleInfo.getActiveBaseRule().getActiveId(),
							sustainActiveSet);
				}
			}
		} catch (Exception e) {
			LogCvt.error("新增红包规则错误 " + e.getMessage(), e);
			sqlSession.rollback(true);
			result = 0;
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}

		return result;
	}

	@Override
	public Long addTemporaryVouchersRuleInfo(OriginVo originVo,
			List<VouchersInfo> vouchersRuleInfo) {
		SqlSession sqlSession = MyBatisManager.getSqlSessionFactory()
				.openSession();
		long result = 0;
		try {
			VouchersMapper vouchersMapper = sqlSession
					.getMapper(VouchersMapper.class);
			result += vouchersMapper.addTemporaryVouchersInfo(vouchersRuleInfo);
			sqlSession.commit();
		} catch (Exception e) {
			LogCvt.error("新增临时红包规则异常 " + e.getMessage(), e);
			sqlSession.rollback(true);
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}

		return result;
	}

	@Override
	public Integer disableVouchersRuleInfo(OriginVo originVo, String clientId,
			String activeId, String operator) {
		SqlSession sqlSession = MyBatisManager.getSqlSessionFactory()
				.openSession();
		ActiveBaseRuleMapper activeBaseRuleMapper = sqlSession
				.getMapper(ActiveBaseRuleMapper.class);
		int result = 0;
		try {
			Date updateTime = new Date(System.currentTimeMillis());
			result = activeBaseRuleMapper.disableActiveBaseRuleByActiveId(
					clientId, activeId, operator, updateTime);
			sqlSession.commit();
			// 禁用红包规则后，删除redis中的对应数据
			VouchersRedis.delRedisDataByKey(RedisKeyUtil
					.cbbank_vouchers_client_id_active_id(clientId, activeId));
			VouchersRedis.delRedisDataByKey(RedisKeyUtil
					.cbbank_vouchers_active_sustain_client_id_active_id(
							clientId, activeId));
		} catch (Exception e) {
			LogCvt.error("禁用红包规则异常 ：" + e.getMessage(), e);
			sqlSession.rollback(true);
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return result;
	}

	@Override
	public List<Vouchers> getActiveRuleInfo(VouchersRuleInfo vouchersRuleInfo) {
		SqlSession sqlSession = MyBatisManager.getSqlSessionFactory()
				.openSession();
		VouchersMapper mapper = sqlSession.getMapper(VouchersMapper.class);
		List<Vouchers> vouchersRuleInfoList = new ArrayList<Vouchers>();
		try {
			vouchersRuleInfoList = mapper.findvoucherList(vouchersRuleInfo);
		} catch (Exception e) {
			LogCvt.error("查询红包规则列表异常 " + e.getMessage(), e);
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}

		return vouchersRuleInfoList;
	}

	@Override
	public FindVouchersRuleInfoVoResultVo getActiveRuleInfoById(
			String clientId, String activeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<VouchersRuleInfo> getActiveRuleInfoByPage(
			Page<VouchersRuleInfo> page, VouchersRuleInfo vouchersRuleInfo) {
		SqlSession sqlSession = MyBatisManager.getSqlSessionFactory()
				.openSession();
		VouchersMapper mapper = sqlSession.getMapper(VouchersMapper.class);
		List<VouchersRuleInfo> vouchersRuleInfoList = new ArrayList<VouchersRuleInfo>();
		List<Vouchers> vouchersList = new ArrayList<Vouchers>();
		try {
			vouchersList = mapper.findvoucherListByPage(page,
					vouchersRuleInfo.getActiveBaseRule());
			vouchersRuleInfoList = this.assembleVouchersInfosList(vouchersList);
			page.setResultsContent(vouchersRuleInfoList);
		} catch (Exception e) {
			LogCvt.error("查询红包规则列表异常 " + e.getMessage(), e);
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}

		return page;
	}

	@Override
	public ResultVo updateVouchersRuleInfo(OriginVo originVo,
			VouchersRuleInfo vouchersRuleInfo) {
		long time = System.currentTimeMillis();
		Date updateTime = new Date(time);
		SqlSession sqlSession = MyBatisManager.getSqlSessionFactory()
				.openSession();
		ResultVo resultVo = new ResultVo();
		try {
			ActiveBaseRuleMapper baseRuleMapper = sqlSession
					.getMapper(ActiveBaseRuleMapper.class);
			ActiveTagRelationMapper tagRelationMapper = sqlSession
					.getMapper(ActiveTagRelationMapper.class);
			VouchersDetailRuleMapper detailRuleMapper = sqlSession
					.getMapper(VouchersDetailRuleMapper.class);
			ActiveSustainRelationMapper sustainRelationMapper = sqlSession
					.getMapper(ActiveSustainRelationMapper.class);
			VouchersMapper vouchersMapper = sqlSession
					.getMapper(VouchersMapper.class);
			vouchersRuleInfo
					.getActiveBaseRule().setUpdateTime(updateTime);
			vouchersRuleInfo.getActiveTagRelation().setUpdateTime(updateTime);
			int result = baseRuleMapper
					.updateActiveBaseRulebyActiveId(vouchersRuleInfo
							.getActiveBaseRule());
			result += detailRuleMapper
					.updateVouchersDetailRule(vouchersRuleInfo
							.getVouchersDetailRule());
			result += tagRelationMapper
					.updateActiveTagRelationByActiveId(vouchersRuleInfo
							.getActiveTagRelation());
			//保存前先删除原有支持活动，然后设置更新时间保存
			if (vouchersRuleInfo.getVouchersDetailRule()
					.getActiveSustainRelationList().size() > 0) {
				List<ActiveSustainRelation> sustainRelationList = new ArrayList<ActiveSustainRelation>();
				for(ActiveSustainRelation sustainRelation :
					vouchersRuleInfo.getVouchersDetailRule().getActiveSustainRelationList()) {
					sustainRelation.setUpdateTime(updateTime);
					sustainRelationList.add(sustainRelation);
				}
				
				vouchersRuleInfo.getVouchersDetailRule().setActiveSustainRelationList(sustainRelationList);
				result += sustainRelationMapper
						.deleteActiveSustainRelationByActiveId(vouchersRuleInfo
								.getActiveBaseRule().getActiveId());
				result += vouchersMapper.addActiveSustainInfo(vouchersRuleInfo);
			} else if(!vouchersRuleInfo.getVouchersDetailRule().getIsOtherActive()) {
				result += sustainRelationMapper
						.deleteActiveSustainRelationByActiveId(vouchersRuleInfo
								.getActiveBaseRule().getActiveId());
			}

			if (result > 1) {
				resultVo.setResultCode(ResultCode.success.getCode());
				resultVo.setResultDesc("更新红包规则成功");
				sqlSession.commit();
				// 更新红包规则后，删除redis中的对应数据再重新载入
				VouchersRedis.delRedisDataByKey(RedisKeyUtil
						.cbbank_vouchers_client_id_active_id(vouchersRuleInfo
								.getActiveBaseRule().getClientId(),
								vouchersRuleInfo.getActiveBaseRule()
										.getActiveId()));
				VouchersRedis.delRedisDataByKey(RedisKeyUtil
						.cbbank_vouchers_active_sustain_client_id_active_id(
								vouchersRuleInfo.getActiveBaseRule()
										.getClientId(), vouchersRuleInfo
										.getActiveBaseRule().getActiveId()));
				Set<String> sustainActiveSet = new HashSet<String>();
				for (ActiveSustainRelation activeSustainRelation : vouchersRuleInfo
						.getActiveSustainRelationList()) {
					sustainActiveSet.add(activeSustainRelation
							.getSustainActiveId());
				}
				// 初始化代金券-支持的其他促销活动
				VouchersRedis.setSustainActive(vouchersRuleInfo
						.getActiveBaseRule().getClientId(), vouchersRuleInfo
						.getActiveBaseRule().getActiveId(), sustainActiveSet);
				VouchersRedis.initVouchersActive(
						vouchersRuleInfo.getActiveBaseRule(),
						vouchersRuleInfo.getVouchersDetailRule(),
						vouchersRuleInfo.getActiveTagRelation());
			} else {
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc("更新红包规则失败");
			}
		} catch (Exception e) {
			LogCvt.error("更新红包规则异常" + e.getMessage(), e);
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("更新红包规则失败");
			sqlSession.rollback(true);
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}

		return resultVo;
	}

	/**
	 * @Title: findMaxActiveId
	 * @Description: 获取红包规则活动ID
	 * @author: shenshaocheng 2015年11月27日
	 * @modify: shenshaocheng 2015年11月27日
	 * @param client
	 * @return
	 * @see com.froad.handler.VouchersRuleInfoHandler#findMaxActiveId(java.lang.String)
	 */
	public String findMaxActiveId(String clientId) {
		SqlSession sqlSession = MyBatisManager.getSqlSessionFactory()
				.openSession();
		String maxActiveId = null;
		try {
			ActiveBaseRuleMapper vouchersMapper = sqlSession
					.getMapper(ActiveBaseRuleMapper.class);
			maxActiveId = vouchersMapper.getMaxActiveByClientId(clientId,
					ActiveIdCode.HB.getCode());
		} catch (Exception e) {
			LogCvt.error("获取红包活动ID失败" + e.getMessage(), e);
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}

		return maxActiveId;
	}

	/**
	 * @Title: assembleVouchersInfosList
	 * @Description: 组装返回Po
	 * @author: shenshaocheng 2015年12月4日
	 * @modify: shenshaocheng 2015年12月4日
	 * @return
	 */
	private List<VouchersRuleInfo> assembleVouchersInfosList(
			List<Vouchers> vouchersRuleInfoList) {
		List<VouchersRuleInfo> vouchersInfosList = new ArrayList<VouchersRuleInfo>();
		for (Vouchers vouchers : vouchersRuleInfoList) {
			ActiveBaseRule baseRule = new ActiveBaseRule();
			ActiveTagRelation tagRelation = new ActiveTagRelation();
			VouchersDetailRule detailRule = new VouchersDetailRule();
			VouchersRuleInfo vouchersRuleInfo = new VouchersRuleInfo();
			baseRule.setActiveId(vouchers.getActiveId());
			baseRule.setActiveName(vouchers.getActiveName());
			baseRule.setActiveLogo(vouchers.getActiveLogo());
			baseRule.setBankRate(vouchers.getBankRate());
			baseRule.setClientId(vouchers.getClientId());
			baseRule.setCreateTime(vouchers.getCreateTime());
			baseRule.setDescription(vouchers.getDescription());
			baseRule.setExpireEndTime(vouchers.getExpireEndTime());
			baseRule.setExpireStartTime(vouchers.getExpireStartTime());
			baseRule.setFftRate(vouchers.getFftRate());
			baseRule.setLimitType(vouchers.getLimitType());
			baseRule.setMerchantRate(vouchers.getMerchantRate());
			baseRule.setOperator(vouchers.getOperator());
			baseRule.setSettleType(vouchers.getSettleType());
			baseRule.setStatus(vouchers.getStatus());
			baseRule.setType(vouchers.getType());
			baseRule.setUpdateTime(vouchers.getUpdateTime());

			tagRelation.setActiveId(vouchers.getActiveId());
			tagRelation.setClientId(vouchers.getClientId());
			tagRelation.setCreateTime(vouchers.getCreateTime());
			tagRelation.setItemId(vouchers.getItemId());
			tagRelation.setItemType(vouchers.getItemType());
			tagRelation.setUpdateTime(vouchers.getUpdateTime());

			detailRule.setActiveId(vouchers.getActiveId());
			detailRule.setClientId(vouchers.getClientId());
			detailRule.setIsFtof(vouchers.getIsFtof());
			detailRule.setIsOtherActive(vouchers.getIsOtherActive());
			detailRule.setIsPerDay(vouchers.getIsPerDay());
			detailRule.setIsPrePay(vouchers.getIsPrePay());
			detailRule.setIsRepeat(vouchers.getIsRepeat());
			detailRule.setIsTotalDay(vouchers.getIsTotalDay());
			detailRule.setMaxMoney(vouchers.getMaxMoney());
			detailRule.setMinMoney(vouchers.getMinMoney());
			detailRule.setOrderMinMoney(vouchers.getOrderMinMoney());
			detailRule.setPayMethod(vouchers.getPayMethod());
			detailRule.setPerCount(vouchers.getPerCount());
			detailRule.setPerDay(vouchers.getPerDay());
			detailRule.setTotalCount(vouchers.getTotalCount());
			detailRule.setTotalDay(vouchers.getTotalDay());
			detailRule.setTotalMoney(vouchers.getTotalMoney());

			vouchersRuleInfo.setActiveBaseRule(baseRule);
			vouchersRuleInfo.setActiveTagRelation(tagRelation);
			vouchersRuleInfo.setVouchersDetailRule(detailRule);

			vouchersInfosList.add(vouchersRuleInfo);
		}

		return vouchersInfosList;
	}
}
