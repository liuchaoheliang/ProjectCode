/**
 * Project Name:froad-cbank-server-boss
 * File Name:BankAccessLogicImpl.java
 * Package Name:com.froad.logic.impl
 * Date:2015年9月17日下午4:50:07
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;

import com.alibaba.druid.sql.visitor.functions.Function;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.ClientMapper;
import com.froad.db.mysql.mapper.ClientPaymentChannelMapper;
import com.froad.db.mysql.mapper.FunctionModuleMapper;
import com.froad.db.redis.ClientFunctionModuleRedis;
import com.froad.db.redis.ClientPaymentChannelRedis;
import com.froad.enums.ClientFunctionModule;
import com.froad.enums.PaymentChannel;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadBusinessException;
import com.froad.logback.LogCvt;
import com.froad.logic.BankAccessLogic;
import com.froad.po.BankAccessDto;
import com.froad.po.Client;
import com.froad.po.ClientPaymentChannel;
import com.froad.po.FunctionModule;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.bankaccess.BankAccessAddReq;
import com.froad.thrift.vo.bankaccess.BankAccessAddRes;
import com.froad.thrift.vo.bankaccess.BankAccessClientListReq;
import com.froad.thrift.vo.bankaccess.BankAccessClientListRes;
import com.froad.thrift.vo.bankaccess.BankAccessClientVo;
import com.froad.thrift.vo.bankaccess.BankAccessDeleteReq;
import com.froad.thrift.vo.bankaccess.BankAccessDeleteRes;
import com.froad.thrift.vo.bankaccess.BankAccessDetailReq;
import com.froad.thrift.vo.bankaccess.BankAccessDetailRes;
import com.froad.thrift.vo.bankaccess.BankAccessEditReq;
import com.froad.thrift.vo.bankaccess.BankAccessEditRes;
import com.froad.thrift.vo.bankaccess.BankAccessListReq;
import com.froad.thrift.vo.bankaccess.BankAccessListRes;
import com.froad.thrift.vo.bankaccess.BankAccessVo;
import com.froad.thrift.vo.bankaccess.FunctionModuleVo;
import com.froad.thrift.vo.bankaccess.PaymentMethodVo;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;

/**
 * ClassName:BankAccessLogicImpl
 * Reason:	 TODO ADD REASON.
 * Date:     2015年9月17日 下午4:50:07
 * @author   kevin
 * @version  
 * @see 	 
 */
public class BankAccessLogicImpl implements BankAccessLogic {

	public BankAccessListRes getBankAccessList(BankAccessListReq req) {
		List<BankAccessDto> result = null;
		SqlSession sqlSession = null;
		ClientMapper clientMapper = null;
		BankAccessListRes res = new BankAccessListRes();
		ResultVo rv = new ResultVo();
		List<BankAccessVo> voList = new ArrayList<BankAccessVo>();
		try{
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			clientMapper = sqlSession.getMapper(ClientMapper.class);
			Page<BankAccessDto> page = (Page)BeanUtil.copyProperties(Page.class, req.getPageVo());
			int totalCount = clientMapper.findClientListCount(req.getClientId());
			int pageCount = totalCount % page.getPageSize() == 0 ? totalCount / page.getPageSize()
					: totalCount / page.getPageSize() + 1;
			result = clientMapper.findClientList(req.getClientId(), (page.getPageNumber() - 1) * page.getPageSize(), page.getPageSize());
			if(result != null && result.size() > 0) {
				for(BankAccessDto dto : result) {
					BankAccessVo vo = new BankAccessVo();
					vo.setId(dto.getId());
					vo.setClientName(dto.getClientName());
					vo.setClientNo(dto.getClientNo());
					vo.setCreateTime(dto.getCreateTime() != null? dto.getCreateTime().getTime() : 0);
					vo.setUpdateTime(dto.getUpdateTime() != null? dto.getUpdateTime().getTime() : 0);
					if(StringUtils.isNotEmpty(dto.getFunctionDesc())) {
						String[] funcTypeList = dto.getFunctionDesc().split(",");
						if(funcTypeList != null && funcTypeList.length > 0  ) {
							StringBuilder b = new StringBuilder("");
							for(int i = 0; i < funcTypeList.length; i++) {
								if(i == funcTypeList.length - 1) {
									b.append(ClientFunctionModule.getByType(Integer.valueOf(funcTypeList[i])).getMsg());
								} else {
									b.append(ClientFunctionModule.getByType(Integer.valueOf(funcTypeList[i])).getMsg() + ",");
								}
							}
							vo.setFunctionDesc(b.toString());
						}
					}
					if(StringUtils.isNotEmpty(dto.getPaymentMethodDesc())) {
						String[] payMethodList = dto.getPaymentMethodDesc().split(",");
						if(payMethodList != null && payMethodList.length > 0) {
							StringBuilder b = new StringBuilder("");
							for(int i = 0; i < payMethodList.length; i++) {
								int tval = Integer.valueOf(payMethodList[i].trim());
								if(i == payMethodList.length - 1) {
									//1:联盟积分;2:银行积分;20:贴膜卡积分（贴膜卡支付）;41:银联无卡积分（银联无卡支付）;50:网银积分（网银支付）;51:快捷支付（银行卡支付）
									if(PaymentChannel.froad_point.getCode() == tval) {
										b.append("手机联盟积分");
									} else if (PaymentChannel.bank_point.getCode() == tval) {
										b.append("银行积分");
									} else if(PaymentChannel.foil_card.getCode() == tval) {
										b.append("贴膜卡支付");
									} else if(PaymentChannel.union_pay.getCode() == tval) {
										b.append("银联无卡支付");
									} else if(PaymentChannel.cyberbank_pay.getCode() == tval) {
										b.append("网银支付");
									} else if(PaymentChannel.fast_pay.getCode() == tval) {
										b.append("银行卡支付");
									}
								} else {
									if(PaymentChannel.froad_point.getCode() == tval) {
										b.append("手机联盟积分,");
									} else if (PaymentChannel.bank_point.getCode() == tval) {
										b.append("银行积分,");
									} else if(PaymentChannel.foil_card.getCode() == tval) {
										b.append("贴膜卡支付,");
									} else if(PaymentChannel.union_pay.getCode() == tval) {
										b.append("银联无卡支付,");
									} else if(PaymentChannel.cyberbank_pay.getCode() == tval) {
										b.append("网银支付,");
									} else if(PaymentChannel.fast_pay.getCode() == tval) {
										b.append("银行卡支付,");
									}
								}
								vo.setPaymentMethodDesc(b.toString());
							}
						}
					}
					voList.add(vo);
				}
			}
			PageVo pageVo = (PageVo)BeanUtil.copyProperties(PageVo.class, page);
			pageVo.setTotalCount(totalCount);
			pageVo.setPageCount(pageCount);
			rv.setResultCode(ResultCode.success.getCode());
			rv.setResultDesc(ResultCode.success.getMsg());
			res.setPageVo(pageVo);
			res.setBankAccessList(voList);
		} catch (Exception e) { 
			LogCvt.error("查询boss设置客户端配置列表失败，原因:" + e.getMessage(), e); 
			rv.setResultCode(ResultCode.failed.getCode());
			rv.setResultDesc(ResultCode.failed.getMsg());

		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		}
		res.setResultVo(rv);
		return res;
	}

	/**
	 * 获取boss银行配置缓存
	 * getBankAccessDetail:(这里用一句话描述这个方法的作用).
	 * @author zhouzhiwei
	 * 2015年9月21日 上午11:12:13
	 * @param req
	 * @return
	 *
	 */
	public BankAccessDetailRes getBankAccessDetail(BankAccessDetailReq req) {
		SqlSession sqlSession = null;
		FunctionModuleMapper funcMapper = null;
		ClientPaymentChannelMapper payChannelMapper = null;
		BankAccessDetailRes res = new BankAccessDetailRes();
		ResultVo rv = new ResultVo();
		List<FunctionModuleVo> funcVoList = new ArrayList<FunctionModuleVo>();
		List<PaymentMethodVo> payVoList = new ArrayList<PaymentMethodVo>();
		try {
			Client client=new CommonLogicImpl().getClientById(req.getClientId());
			if(client != null) {
				res.setCilentName(client.getBankName());
			}
			
			//若缓存信息不存在
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession(false);
			funcMapper = sqlSession.getMapper(FunctionModuleMapper.class);
			payChannelMapper = sqlSession.getMapper(ClientPaymentChannelMapper.class);
			
			//查询功能模块
			List<FunctionModule> moduleList = funcMapper.findListByCondition(req.getClientId());
			if(moduleList != null && moduleList.size() > 0) {
				for(FunctionModule module : moduleList) {
					FunctionModuleVo vo = new FunctionModuleVo();
					vo.setAliasName(module.getModuleAlias());
					vo.setIconUrl(module.getIconUrl());
					vo.setSortValue(module.getSortValue().toString());
					vo.setType(module.getType().toString());
					funcVoList.add(vo);
				}
			}
			res.setFunctionList(funcVoList);
			
			ClientPaymentChannel query = new ClientPaymentChannel();
			query.setClientId(req.getClientId());
			query.setIsDelete(false);
			List<ClientPaymentChannel> payChannelList = payChannelMapper.findListByCondition(query);
			if(payChannelList != null && payChannelList.size() > 0) {
				for(ClientPaymentChannel payChannel : payChannelList) {
					PaymentMethodVo vo = new PaymentMethodVo();
					vo.setAliasName(payChannel.getName());
					vo.setIconUrl(payChannel.getIcoUrl());
					vo.setType(payChannel.getType());
					payVoList.add(vo);
				}
			}
			res.setPaymentList(payVoList);
			
			rv.setResultCode(ResultCode.success.getCode());
			rv.setResultDesc(ResultCode.success.getMsg());
		} catch (Exception e) {
			LogCvt.error( "新增boss银行配置信息出错,clientId" + req.getClientId() ,e);
			rv.setResultCode(ResultCode.failed.getCode());
			rv.setResultDesc(ResultCode.failed.getMsg());
		} finally {
			if(sqlSession != null) {
				sqlSession.close();
			}
		}
		res.setResultVo(rv);
		return res;
	}

	public BankAccessAddRes addBankAccess(BankAccessAddReq req) {
		SqlSession sqlSession = null;
		FunctionModuleMapper funcMapper = null;
		ClientPaymentChannelMapper payChannelMapper = null;
		BankAccessAddRes res = new BankAccessAddRes();
		ResultVo rv = new ResultVo();
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession(false);
			funcMapper = sqlSession.getMapper(FunctionModuleMapper.class);
			payChannelMapper = sqlSession.getMapper(ClientPaymentChannelMapper.class);
			//校验当前客户端是否支持用户所选的所有支付方式
			ClientPaymentChannel qyChannel = new ClientPaymentChannel();
			qyChannel.setClientId(req.getClientId());
			List<ClientPaymentChannel> channelList = payChannelMapper.findListByCondition(qyChannel);
			Map<String, ClientPaymentChannel> channelMap = new HashMap<String, ClientPaymentChannel>();
			List<PaymentMethodVo> payChannelList = req.getPaymentList();
			if(payChannelList != null) {
				if(channelList == null ) {
					throw new FroadBusinessException(ResultCode.payment_channel_not_exist.getCode(), ResultCode.payment_channel_not_exist.getMsg());
				}
				for(int i = 0 ; i < channelList.size(); i++) {
					channelMap.put(channelList.get(i).getClientId() + channelList.get(i).getType(), channelList.get(i));
				}
				for(PaymentMethodVo vo : payChannelList) {
					ClientPaymentChannel cc = channelMap.get(req.getClientId() + vo.getType());
					if(cc == null) {
						throw new FroadBusinessException(ResultCode.payment_channel_not_exist.getCode(), ResultCode.payment_channel_not_exist.getMsg());
					}
				}
			}
			//新增功能模块
			List<FunctionModuleVo> funList = req.getFunctionList();
			if(funList != null && funList.size() > 0) {
				Date date = Calendar.getInstance().getTime();
				for(FunctionModuleVo vo : funList) {
					FunctionModule module = new FunctionModule();
					module.setClientId(req.getClientId());
					module.setCreateTime(date);
					module.setIconUrl(vo.getIconUrl());
					module.setModuleAlias(vo.getAliasName());
					module.setModuleName(ClientFunctionModule.getByType(Integer.valueOf(vo.getType())).getMsg());
					module.setSortValue(Integer.valueOf(vo.getSortValue()));
					module.setType(Integer.valueOf(vo.getType()));
					funcMapper.insert(module);
				}
				
				//缓存添加信息
				FunctionModule[] modules = new FunctionModule[funList.size()];
				for(int i = 0; i < funList.size() ; i++) {
					FunctionModule fm = new FunctionModule();
					fm.setClientId(req.getClientId());
					fm.setCreateTime(date);
					fm.setModuleAlias(funList.get(i).getAliasName());
					fm.setModuleName(ClientFunctionModule.getByType(Integer.valueOf(funList.get(i).getType())).getMsg());
					fm.setSortValue(Integer.valueOf(funList.get(i).getSortValue()));
					fm.setType(Integer.valueOf(funList.get(i).getType()));
					fm.setIconUrl(funList.get(i).getIconUrl());
					modules[i] = fm;
				}
				ClientFunctionModuleRedis.set_cbbank_function_module_client_id(modules);
				ClientFunctionModuleRedis.set_cbbank_function_module_client_id_type(modules);
			}
			
			
			//新增支付方式模块
			if(payChannelList != null && payChannelList.size() > 0) {
				for(PaymentMethodVo vo : payChannelList) {
					ClientPaymentChannel payChannel = new ClientPaymentChannel();
					payChannel.setClientId(req.getClientId());
					//payChannel.setFullName(vo.getAliasName());
					payChannel.setName(vo.getAliasName());
					payChannel.setIcoUrl(vo.getIconUrl());
					payChannel.setType(vo.getType());
					payChannel.setIsDelete(false);
					payChannelMapper.updateBossInfo(payChannel);
				}
				
				//缓存增加信息
				/*ClientPaymentChannel qyChannel = new ClientPaymentChannel();
				qyChannel.setClientId(req.getClientId());
				List<ClientPaymentChannel> channelList = payChannelMapper.findListByCondition(qyChannel);
				Map<String, ClientPaymentChannel> channelMap = new HashMap<String, ClientPaymentChannel>();*/
				if(channelList != null && channelList.size() > 0) {
					/*for(int i = 0 ; i < channelList.size(); i++) {
						channelMap.put(channelList.get(i).getClientId() + channelList.get(i).getType(), channelList.get(i));
					}*/
					ClientPaymentChannel[] payChannels = new ClientPaymentChannel[payChannelList.size()];
					for(int i = 0; i < payChannelList.size(); i++) {
						ClientPaymentChannel cl = new ClientPaymentChannel();
						ClientPaymentChannel mqc = channelMap.get(req.getClientId() +payChannelList.get(i).getType() );
						cl.setClientId(req.getClientId());
						/*cl.setName(mqc.getName());
						cl.setFullName(payChannelList.get(i).getAliasName());*/
						cl.setName(payChannelList.get(i).getAliasName());
						cl.setFullName(mqc.getFullName());
						cl.setType(payChannelList.get(i).getType());
						cl.setIcoUrl(payChannelList.get(i).getIconUrl());
						cl.setPaymentOrgNo(mqc.getPaymentOrgNo());
						cl.setIsFroadCheckToken(mqc.getIsFroadCheckToken());
						cl.setPointRate(mqc.getPointRate());
						cl.setPaymentChannelId(mqc.getPaymentChannelId());
						payChannels[i] = cl;
					}
					ClientPaymentChannelRedis.set_cbbank_client_channel_client_id_payment_channel_id(payChannels);
					ClientPaymentChannelRedis.set_cbbank_client_channels_client_id(payChannels);
				}
			}
			
			
			sqlSession.commit(true);
			rv.setResultCode(ResultCode.success.getCode());
			rv.setResultDesc(ResultCode.success.getMsg());
		} catch(FroadBusinessException ex) {
			LogCvt.error( "编辑boss银行配置信息出错,clientId" + req.getClientId() ,ex);
			rv.setResultCode(ex.getCode());
			rv.setResultDesc(ex.getMsg());
		} catch (Exception e) {
			LogCvt.error( "新增boss银行配置信息出错,clientId" + req.getClientId() ,e);
			rv.setResultCode(ResultCode.failed.getCode());
			rv.setResultDesc(ResultCode.failed.getMsg());
		} finally {
			if(sqlSession != null) {
				sqlSession.close();
			}
		}
		res.setResultVo(rv);
		return res;
	}

	public BankAccessEditRes editBankAccess(BankAccessEditReq req) {
		SqlSession sqlSession = null;
		FunctionModuleMapper funcMapper = null;
		ClientPaymentChannelMapper payChannelMapper = null;
		BankAccessEditRes res = new BankAccessEditRes();
		ResultVo rv = new ResultVo();
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession(false);
			funcMapper = sqlSession.getMapper(FunctionModuleMapper.class);
			payChannelMapper = sqlSession.getMapper(ClientPaymentChannelMapper.class);
			//校验当前客户端是否支持用户所选的所有支付方式
			ClientPaymentChannel qyChannel = new ClientPaymentChannel();
			qyChannel.setClientId(req.getClientId());
			List<ClientPaymentChannel> channelList = payChannelMapper.findListByCondition(qyChannel);
			Map<String, ClientPaymentChannel> channelMap = new HashMap<String, ClientPaymentChannel>();
			List<PaymentMethodVo> payChannelList = req.getPaymentList();
			if(payChannelList != null) {
				if(channelList == null ) {
					throw new FroadBusinessException(ResultCode.payment_channel_not_exist.getCode(), ResultCode.payment_channel_not_exist.getMsg());
				}
				for(int i = 0 ; i < channelList.size(); i++) {
					channelMap.put(channelList.get(i).getClientId() + channelList.get(i).getType(), channelList.get(i));
				}
				for(PaymentMethodVo vo : payChannelList) {
					ClientPaymentChannel cc = channelMap.get(req.getClientId() + vo.getType());
					if(cc == null) {
						throw new FroadBusinessException(ResultCode.payment_channel_not_exist.getCode(), ResultCode.payment_channel_not_exist.getMsg());
					}
				}
			}
			//更新功能模块列表
			List<FunctionModuleVo> funList = req.getFunctionList();
			Date createTime = null;
			List<FunctionModule> oldModuleList = funcMapper.findListByCondition(req.getClientId());
			if(oldModuleList != null && oldModuleList.size() > 0) {
				createTime = oldModuleList.get(0).getCreateTime();
			} else {
				createTime = Calendar.getInstance().getTime();
			}
			funcMapper.deleteByClientId(req.getClientId());
			if(funList != null && funList.size() > 0) {
				Date date = Calendar.getInstance().getTime();
				for(FunctionModuleVo vo : funList) {
					FunctionModule module = new FunctionModule();
					module.setClientId(req.getClientId());
					module.setCreateTime(createTime);
					module.setIconUrl(vo.getIconUrl());
					module.setModuleAlias(vo.getAliasName());
					module.setType(Integer.valueOf(vo.getType()));
					module.setModuleName(ClientFunctionModule.getByType(Integer.valueOf(vo.getType())).getMsg());
					if(StringUtils.isNotEmpty(vo.getSortValue())) {
						module.setSortValue(Integer.valueOf(vo.getSortValue()));
					}
					module.setUpdateTime(date);
					funcMapper.insert(module);
				}
				
				//更新功能模块缓存
				//1.删除缓存中的当前客户端功能模块信息
				Set<String> moduleTypeSet = ClientFunctionModuleRedis.get_cbbank_function_module_client_id(req.getClientId());
                if(moduleTypeSet != null && moduleTypeSet.size() > 0) {
                    Iterator<String> it = moduleTypeSet.iterator();
                    int count = 0;
                    FunctionModule[] funcModules = new FunctionModule[moduleTypeSet.size()];
                    while(it.hasNext()) {
                            FunctionModule m = new FunctionModule();
                            m.setClientId(req.getClientId());
                            m.setType(Integer.valueOf(it.next()));
                            funcModules[count] = m;
                            count++;
                    }
                    ClientFunctionModuleRedis.del_cbbank_function_module_client_id_type(funcModules);
                    ClientFunctionModuleRedis.srem_cbbank_function_module_client_id_type(funcModules);
                }
                //2.新增当前客户端功能模块到缓存
                FunctionModule[] addModules = new FunctionModule[funList.size()];
                for(int i = 0; i < funList.size(); i++) {
                	FunctionModule module = new FunctionModule();
					module.setClientId(req.getClientId());
					module.setCreateTime(createTime);
					module.setIconUrl(funList.get(i).getIconUrl());
					module.setModuleAlias(funList.get(i).getAliasName());
					module.setType(Integer.valueOf(funList.get(i).getType()));
					module.setModuleName(ClientFunctionModule.getByType(Integer.valueOf(funList.get(i).getType())).getMsg());
					if(StringUtils.isNotEmpty(funList.get(i).getSortValue())) {
						module.setSortValue(Integer.valueOf(funList.get(i).getSortValue()));
					}
					addModules[i] = module;
                }
                ClientFunctionModuleRedis.set_cbbank_function_module_client_id(addModules);
                ClientFunctionModuleRedis.set_cbbank_function_module_client_id_type(addModules);
			}
			
			//更新支付方式列表
		//	List<PaymentMethodVo> payChannelList = req.getPaymentList();
			if(payChannelList != null && payChannelList.size() > 0) {
				//先把当前客户端所有支付渠道信息禁用
				ClientPaymentChannel deletePay = new ClientPaymentChannel();
				deletePay.setClientId(req.getClientId());
				deletePay.setIsDelete(true);
				payChannelMapper.updateBossInfo(deletePay);
				for(PaymentMethodVo vo : payChannelList) {
					ClientPaymentChannel payChannel = new ClientPaymentChannel();
					payChannel.setClientId(req.getClientId());
					//payChannel.setFullName(vo.getAliasName());
					payChannel.setName(vo.getAliasName());
					payChannel.setIsDelete(false);
					payChannel.setIcoUrl(vo.getIconUrl());
					payChannel.setType(vo.getType());
					payChannelMapper.updateBossInfo(payChannel);
				}
				
				//更新支付渠道缓存
				//1.先查询现支付渠道。
				Set<String> payChannelIdSet = ClientPaymentChannelRedis.get_cbbank_client_channels_client_id(req.getClientId());
				if(payChannelIdSet != null && payChannelIdSet.size() > 0) {
					ClientPaymentChannel conditionPay = new ClientPaymentChannel();
					conditionPay.setClientId(req.getClientId());
					conditionPay.setType(PaymentChannel.timely_pay.getCode() + "");
					List<ClientPaymentChannel> timePayChannelList = payChannelMapper.findListByCondition(conditionPay);
					ClientPaymentChannel timePayChannel = null;
					if(timePayChannelList != null && timePayChannelList.size() > 0) {
						timePayChannel = timePayChannelList.get(0);
					}
					
					Iterator<String> it = payChannelIdSet.iterator();
					List<ClientPaymentChannel> tmpPayChannelList = new ArrayList<ClientPaymentChannel>();
					while(it.hasNext()) {
						//针对即时支付信息不删除缓存
						String payChannelId = it.next();
						if(timePayChannel == null || !payChannelId.equals(timePayChannel.getPaymentChannelId())) {
							ClientPaymentChannel p = new ClientPaymentChannel();
							p.setClientId(req.getClientId());
							p.setPaymentChannelId(payChannelId);
							tmpPayChannelList.add(p);
						}
					}
					ClientPaymentChannel[] payChannels = tmpPayChannelList.toArray(new ClientPaymentChannel[tmpPayChannelList.size()]);
					ClientPaymentChannelRedis.del_cbbank_client_channel_client_id_payment_channel_id(payChannels);
					ClientPaymentChannelRedis.srem_cbbank_client_channels_client_id(payChannels);
	                
	                //增加本次选择的支付渠道信息到缓存
					/*Map<String, ClientPaymentChannel> channelMap = new HashMap<String,ClientPaymentChannel>();
	                ClientPaymentChannel[] editPayChannels = new ClientPaymentChannel[payChannelList.size()];
	                ClientPaymentChannel allConditionPay = new ClientPaymentChannel();
	                allConditionPay.setClientId(req.getClientId());
					List<ClientPaymentChannel> allPayChannel = payChannelMapper.findListByCondition(allConditionPay);
					if(allPayChannel != null && allPayChannel.size() > 0) {
						for(ClientPaymentChannel c : allPayChannel) {
							channelMap.put(c.getClientId() + c.getType(), c);
							System.out.println("c.getClientId() + c.getType():" + c.getClientId() + c.getType());
						}
					}*/
					ClientPaymentChannel[] editPayChannels = new ClientPaymentChannel[payChannelList.size()];
	                for(int i = 0; i < payChannelList.size(); i++) {
	                	ClientPaymentChannel c = new ClientPaymentChannel();
	                	ClientPaymentChannel mqc = channelMap.get(req.getClientId() + payChannelList.get(i).getType());
	                	c.setClientId(req.getClientId());
	                	/*c.setName(mqc.getName());
	                	c.setFullName(payChannelList.get(i).getAliasName());*/
	                	c.setName(payChannelList.get(i).getAliasName());
	                	c.setFullName(mqc.getFullName());
	                	c.setType(payChannelList.get(i).getType());
	                	c.setIcoUrl(payChannelList.get(i).getIconUrl());
	                	c.setPaymentOrgNo(mqc.getPaymentOrgNo());
	                	c.setIsFroadCheckToken(mqc.getIsFroadCheckToken());
	                	c.setPointRate(mqc.getPointRate());
	                	c.setPaymentChannelId(mqc.getPaymentChannelId());
	                	editPayChannels[i] = c;
	                }
	                ClientPaymentChannelRedis.set_cbbank_client_channel_client_id_payment_channel_id(editPayChannels);
	                ClientPaymentChannelRedis.set_cbbank_client_channels_client_id(editPayChannels);
				}
			}
			
			sqlSession.commit(true);
			rv.setResultCode(ResultCode.success.getCode());
			rv.setResultDesc(ResultCode.success.getMsg());
		} catch(FroadBusinessException ex) {
			LogCvt.error( "编辑boss银行配置信息出错,clientId" + req.getClientId() ,ex);
			rv.setResultCode(ex.getCode());
			rv.setResultDesc(ex.getMsg());
		} catch (Exception e) {
			LogCvt.error( "编辑boss银行配置信息出错,clientId" + req.getClientId() ,e);
			rv.setResultCode(ResultCode.failed.getCode());
			rv.setResultDesc(ResultCode.failed.getMsg());
		} finally {
			if(sqlSession != null) {
				sqlSession.close();
			}
		}
		res.setResultVo(rv);
		return res;
	}

	/**
	 * 删除boss银行配置信息
	 */
	public BankAccessDeleteRes deleteBankAccess(BankAccessDeleteReq req) {
		SqlSession sqlSession = null;
		FunctionModuleMapper funcMapper = null;
		ClientPaymentChannelMapper payChannelMapper = null;
		BankAccessDeleteRes res = new BankAccessDeleteRes();
		ResultVo rv = new ResultVo();
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession(false);
			funcMapper = sqlSession.getMapper(FunctionModuleMapper.class);
			payChannelMapper = sqlSession.getMapper(ClientPaymentChannelMapper.class);
			
			//删除当前客户端功能模块缓存信息
			Set<String> moduleTypeSet = ClientFunctionModuleRedis.get_cbbank_function_module_client_id(req.getClientId());
			if(moduleTypeSet != null && moduleTypeSet.size() > 0) {
				Iterator<String> it = moduleTypeSet.iterator();
				int count = 0;
				FunctionModule[] funcModules = new FunctionModule[moduleTypeSet.size()];
				while(it.hasNext()) {
					FunctionModule m = new FunctionModule();
					m.setClientId(req.getClientId());
					m.setType(Integer.valueOf(it.next()));
					funcModules[count] = m;
					count++;
				}
				ClientFunctionModuleRedis.del_cbbank_function_module_client_id_type(funcModules);
				ClientFunctionModuleRedis.srem_cbbank_function_module_client_id_type(funcModules);
			}
			
			//删除当前客户端支付渠道缓存信息
			Set<String> payChannelIdSet = ClientPaymentChannelRedis.get_cbbank_client_channels_client_id(req.getClientId());
			if(payChannelIdSet != null && payChannelIdSet.size() > 0) {
				//获取即时支付信息
				ClientPaymentChannel conditionPay = new ClientPaymentChannel();
				conditionPay.setClientId(req.getClientId());
				conditionPay.setType(PaymentChannel.timely_pay.getCode() + "");
				List<ClientPaymentChannel> timePayChannelList = payChannelMapper.findListByCondition(conditionPay);
				ClientPaymentChannel timePayChannel = null;
				if(timePayChannelList != null && timePayChannelList.size() > 0) {
					timePayChannel = timePayChannelList.get(0);
				}
				
				Iterator<String> it = payChannelIdSet.iterator();
				List<ClientPaymentChannel> tmpPayChannelList = new ArrayList<ClientPaymentChannel>();
				while(it.hasNext()) {
					//针对即时支付信息不删除缓存
					String payChannelId = it.next();
					if(timePayChannel == null || !payChannelId.equals(timePayChannel.getPaymentChannelId())) {
						ClientPaymentChannel p = new ClientPaymentChannel();
						p.setClientId(req.getClientId());
						p.setPaymentChannelId(payChannelId);
						tmpPayChannelList.add(p);
					}
				}
				ClientPaymentChannel[] payChannels = tmpPayChannelList.toArray(new ClientPaymentChannel[tmpPayChannelList.size()]);
				ClientPaymentChannelRedis.del_cbbank_client_channel_client_id_payment_channel_id(payChannels);
				ClientPaymentChannelRedis.srem_cbbank_client_channels_client_id(payChannels);
			}
			
			//物理删除cb_client_function_module
			funcMapper.deleteByClientId(req.getClientId());
		
			//逻辑删除cb_client_payment_channel
			ClientPaymentChannel payChannel = new ClientPaymentChannel();
			payChannel.setClientId(req.getClientId());
			payChannel.setIsDelete(true);
			payChannelMapper.updateBossInfo(payChannel);
			
			sqlSession.commit(true);
			rv.setResultCode(ResultCode.success.getCode());
			rv.setResultDesc(ResultCode.success.getMsg());
		} catch (Exception e) {
			LogCvt.error( "删除boss银行配置信息出错,clientId" + req.getClientId() ,e);
			rv.setResultCode(ResultCode.failed.getCode());
			rv.setResultDesc(ResultCode.failed.getMsg());
		} finally {
			if(sqlSession != null) {
				sqlSession.close();
			}
		}
		res.setResultVo(rv);
		return res;
	}

	@Override
	public BankAccessClientListRes getClientList(BankAccessClientListReq req) {
		List<Client> result = null;
		SqlSession sqlSession = null;
		ClientMapper clientMapper = null;
		BankAccessClientListRes res = new BankAccessClientListRes();
		ResultVo rv = new ResultVo();
		List<BankAccessClientVo> voList = new ArrayList<BankAccessClientVo>();
		try{
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			clientMapper = sqlSession.getMapper(ClientMapper.class);
			result = clientMapper.findExistBossClientList(req.getType());
			if(result != null && result.size() > 0) {
				for(Client client : result) {
					BankAccessClientVo vo = new BankAccessClientVo();
					vo.setClientId(client.getClientId());
					vo.setClientName(client.getName());
					vo.setBankName(client.getBankName());
					voList.add(vo);
				}
			}
			rv.setResultCode(ResultCode.success.getCode());
			rv.setResultDesc(ResultCode.success.getMsg());
		} catch (Exception e) { 
			LogCvt.error("查询Client失败，原因:" + e.getMessage(), e); 
			rv.setResultCode(ResultCode.failed.getCode());
			rv.setResultDesc(ResultCode.failed.getMsg());

		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		}
		res.setClientList(voList);
		res.setResultVo(rv);
		return res;
	}
}
