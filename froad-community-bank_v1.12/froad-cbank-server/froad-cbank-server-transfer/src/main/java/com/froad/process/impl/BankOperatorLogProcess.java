package com.froad.process.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.froad.config.ProcessServiceConfig;
import com.froad.db.bps.entity.OperatorLog;
import com.froad.db.bps.mappers.OperatorLogMapper;
import com.froad.db.chonggou.entity.BankOperatorCG;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.BankOperateLogMapper;
import com.froad.db.chonggou.mappers.BankOperatorMapperCG;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.enums.TransferTypeEnum;
import com.froad.logback.LogCvt;
import com.froad.po.BankOperateLog;
import com.froad.process.AbstractProcess;
import com.froad.thrift.vo.PlatType;
import com.froad.util.Checker;
import com.froad.util.CollectionsUtil;
import com.froad.util.Constans;

public class BankOperatorLogProcess extends AbstractProcess {
	
	private OperatorLogMapper operatorLogMapper;
	private BankOperateLogMapper bankOperateLogMapper;
	private TransferMapper transferMapper;
	private BankOperatorMapperCG bankOperatorMapperCG;
	
	public BankOperatorLogProcess(String name, ProcessServiceConfig config) {
		super(name, config);
	}

	@Override
	public void process() {
		LogCvt.info("银行操作日志迁移开始");
		operatorLogMapper = bpsSqlSession.getMapper(OperatorLogMapper.class);
		bankOperateLogMapper = sqlSession.getMapper(BankOperateLogMapper.class);
		transferMapper = sqlSession.getMapper(TransferMapper.class);
		bankOperatorMapperCG = sqlSession.getMapper(BankOperatorMapperCG.class);
		
		List<OperatorLog> logs = operatorLogMapper.findAll();
		if(Checker.isEmpty(logs)){
			LogCvt.info("银行操作日志 无数据需迁移");
			return;
		}
		LogCvt.info("银行操作日志共有"+logs.size()+"条数据需迁移");
		
		Map<String, Long> bankOperatorInfo = getTransferInfo();
		Map<Long, BankOperatorCG> operatorInfo = getBankOperatorInfo();
		
		List<List<OperatorLog>> splitLogs = CollectionsUtil.splitList(logs, 5000);
		for(List<OperatorLog> logsList : splitLogs){
			List<BankOperateLog> bankLogs = new ArrayList<BankOperateLog>();
			for(OperatorLog o : logsList){
				if(Checker.isEmpty(bankOperatorInfo.get(o.getOperator()))){
					continue;
				}
				BankOperateLog b = new BankOperateLog();
				Long operatorId = bankOperatorInfo.get(o.getOperator());
				BankOperatorCG cg = operatorInfo.get(operatorId);
				Long roleId = Checker.isNotEmpty(cg)?cg.getRoleId():0l;
				b.setClientId(Constans.clientId);
				b.setCreateTime(o.getCreateTime());
				b.setPlatType(PlatType.bank.getValue());
				b.setUserId(operatorId);
				b.setUsername(o.getLoginAccount());
				b.setRoleId(roleId);
				b.setDescription(o.getRemark()+("1".equals(o.getOperateStatus())?"成功":"失败"));
				b.setOrgCode(Constans.filterOrg(o.getOrgCode()));
				b.setOrgName(o.getOrgName());
				b.setOperatorIp(o.getLoginIp());
				b.setLogType("01001".equals(o.getOperateType()));
				bankLogs.add(b);
			}
			boolean result = bankOperateLogMapper.addBankOperateLogByBatch(bankLogs);
			LogCvt.info("银行操作日志 "+bankLogs.size()+"条数据迁移"+(result?"成功":"失败"));
		}
		LogCvt.info("银行操作日志迁移结束");
	}
	
	
	private Map<String, Long> getTransferInfo(){
		List<Transfer> list = transferMapper.queryGroupList(TransferTypeEnum.bank_operator_id);
		Map<String, Long> info = new HashMap<String, Long>();
		for (Transfer transfer : list) {
			info.put(transfer.getOldId(), Long.valueOf(transfer.getNewId()));
		}
		return info;
	}
	
	private Map<Long, BankOperatorCG> getBankOperatorInfo(){
		List<BankOperatorCG> list = bankOperatorMapperCG.findAll();
		Map<Long, BankOperatorCG> info = new HashMap<Long, BankOperatorCG>();
		for (BankOperatorCG b : list) {
			info.put(b.getId(), b);
		}
		return info;
	}
}
