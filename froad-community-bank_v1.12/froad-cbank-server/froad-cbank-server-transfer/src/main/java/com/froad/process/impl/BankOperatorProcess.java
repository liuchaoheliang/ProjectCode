package com.froad.process.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.ibatis.session.SqlSession;

import com.froad.config.ProcessServiceConfig;
import com.froad.db.bps.entity.BankOperator;
import com.froad.db.bps.entity.BankOperatorRole;
import com.froad.db.bps.mappers.BankOperatorMapper;
import com.froad.db.bps.mappers.BankOperatorRoleMapper;
import com.froad.db.chonggou.entity.BankOperatorCG;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.BankOperatorMapperCG;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.redis.impl.RedisManager;
import com.froad.enums.TransferTypeEnum;
import com.froad.logback.LogCvt;
import com.froad.process.AbstractProcess;
import com.froad.util.Checker;
import com.froad.util.ConsoleLogger;
import com.froad.util.Constans;
import com.froad.util.PropertiesUtil;
import com.froad.util.RedisKeyUtil;

public class BankOperatorProcess extends AbstractProcess {
	
	private BankOperatorMapper bankOperatorMapper;
	private BankOperatorRoleMapper bankOperatorRoleMapper;
	private BankOperatorMapperCG bankOperatorMapperCG;
	private TransferMapper transferMapper;
	
	private String pass = "vLFfghR5tNV3K9DKhmwArV+SbjWAcgZZzIDTnJ0JgCo=";
	
	public BankOperatorProcess(String name, ProcessServiceConfig config) {
		super(name, config);
	}

	@Override
	public void process() {
		LogCvt.info("银行操作员表cb_bank_operator 数据迁移开始");
		
		bankOperatorMapper = bpsSqlSession.getMapper(BankOperatorMapper.class);
		bankOperatorRoleMapper = bpsSqlSession.getMapper(BankOperatorRoleMapper.class);
		bankOperatorMapperCG = sqlSession.getMapper(BankOperatorMapperCG.class);
		transferMapper = sqlSession.getMapper(TransferMapper.class);
		
		List<BankOperator> bankOperators = bankOperatorMapper.selectAllOperators();
		if(Checker.isEmpty(bankOperators)){
			LogCvt.info("原安徽bps表tr_bank_operator 无数据需要迁移");
			return;
		}
		
		Map<Long, Long> info = getBankRoleInfo();
		
		Map<Long, Long> operatorRoleInfo = getOperatorRoleInfo();
		
		LogCvt.info("原安徽bps表tr_bank_operator 共有"+bankOperators.size()+"条数据需要迁移");
		int count = 0;
		for(BankOperator bankOperator : bankOperators){
			
			BankOperatorCG sameBankOperatorCG = bankOperatorMapperCG.findByUsername(bankOperator.getLoginName());
			if(Checker.isNotEmpty(sameBankOperatorCG)){
				LogCvt.error("银行登录名[login_name:"+bankOperator.getLoginName()+"] 已存在, 不能重复添加");
				continue;
			}
			if(Checker.isEmpty(operatorRoleInfo.get(bankOperator.getOperatorId()))) continue;
			Long oldRoleId = operatorRoleInfo.get(bankOperator.getOperatorId());
			
			BankOperatorCG bankOperatorCG = new BankOperatorCG();
			bankOperatorCG.setCreateTime(new Date());
			bankOperatorCG.setClientId(Constans.clientId);
			bankOperatorCG.setUsername(bankOperator.getLoginName());
			bankOperatorCG.setPassword(pass);
//			bankOperatorCG.setPassword(bankOperator.getLoginPasswd());
			bankOperatorCG.setOrgCode(Constans.filterOrg(bankOperator.getOrgCode()));
			bankOperatorCG.setMobile(bankOperator.getMobile());
			bankOperatorCG.setEmail(bankOperator.getEmail());
			bankOperatorCG.setName(bankOperator.getOperatorName());
			bankOperatorCG.setPosition(bankOperator.getPosition());
			bankOperatorCG.setDepartment(bankOperator.getDepartment());
			bankOperatorCG.setStatus(BooleanUtils.toBooleanObject(bankOperator.getStatus(), "1", "0", ""));
			bankOperatorCG.setRemark(bankOperator.getRemark());
			bankOperatorCG.setIsReset(BooleanUtils.toBooleanObject(bankOperator.getIsReset(), "1", "0", ""));
			bankOperatorCG.setRoleId(Checker.isNotEmpty(info.get(oldRoleId))?info.get(oldRoleId):0l);
			bankOperatorCG.setIsDelete(false);
			
			long result = bankOperatorMapperCG.addBankOperator(bankOperatorCG);
			LogCvt.info("银行操作员表cb_bank_operator 数据迁移"+(result > 0?"成功":"失败"));
			if(result > 0){
				boolean redisResult = addBankOperatorRedis(bankOperatorCG);
				redisResult = addBankUserlogin(bankOperatorCG.getId(), bankOperator.getFailures());
				LogCvt.info("银行操作员缓存数据迁移"+(redisResult?"成功":"失败"));
			}
			
			LogCvt.info("银行操作员[operatorId:"+bankOperator.getOperatorId()+"] 数据迁移"+(result > 0?"成功":"失败"));
			
			Transfer transfer = new Transfer();
			transfer.setOldId(String.valueOf(bankOperator.getOperatorId()));
			transfer.setNewId(String.valueOf(bankOperatorCG.getId()));
			transfer.setType(TransferTypeEnum.bank_operator_id);
			transferMapper.insert(transfer);
			
			count++;
			if(count % 1000 == 0){
				ConsoleLogger.info("已迁移银行操作员信息"+count+"条");
			}
		}
		ConsoleLogger.info("银行操作员信息"+count+"条 全部迁移完毕");
		LogCvt.info("银行操作员表cb_bank_operator 数据迁移结束");
	}
	
	private Map<Long, Long> getBankRoleInfo(){
		List<Transfer> transfers = transferMapper.queryGroupList(TransferTypeEnum.bank_role_id);
		Map<Long, Long> info = new HashMap<Long, Long>();
		for(Transfer transfer : transfers){
			info.put(Long.valueOf(transfer.getOldId()), Long.valueOf(transfer.getNewId()));
		}
		return info;
	}
	
	
	private Map<Long, Long> getOperatorRoleInfo(){
		List<BankOperatorRole> list = bankOperatorRoleMapper.findAll();
		Map<Long, Long> info = new HashMap<Long, Long>();
		for(BankOperatorRole b : list){
			info.put(b.getOperatorId(), b.getRoleId());
		}
		return info;
	}
	
	private boolean addBankOperatorRedis(BankOperatorCG bankOperatorCG){
		String key = RedisKeyUtil.cbbank_bank_user_client_id_user_id(Constans.clientId,bankOperatorCG.getId()) ;

		Map<String, String> hash = new HashMap<String, String>();
		hash.put("username", bankOperatorCG.getUsername());//登录名
		hash.put("password", bankOperatorCG.getPassword());//登录密码
		hash.put("org_code", bankOperatorCG.getOrgCode());//所属机构编号
		//redis中对bool类型存0和1
		hash.put("status", BooleanUtils.toString(bankOperatorCG.getStatus(), "1", "0", ""));//0-不可用 1-可用
		hash.put("is_reset", BooleanUtils.toString(bankOperatorCG.getIsReset(), "1", "0", ""));//密码是否重置 1-是 0-否
		hash.put("name", ObjectUtils.toString(bankOperatorCG.getName() , ""));//操作员姓名
		hash.put("role_id", ObjectUtils.toString(bankOperatorCG.getRoleId() , ""));//角色ID
		hash.put("department", ObjectUtils.toString(bankOperatorCG.getDepartment() , ""));//部门
		hash.put("position", ObjectUtils.toString(bankOperatorCG.getPosition() , ""));//职位
		
		String result = redis.putMap(key, hash);
		return "OK".equals(result);
	}
	
	private boolean addBankUserlogin(Long operatorId, Integer failures){
		String key = RedisKeyUtil.cbbank_bank_user_login_client_id_user_id(Constans.clientId, operatorId);
		String result = redis.putString(key, ObjectUtils.toString(failures, "0"));
		return "OK".equals(result);
	}
	
	
	public static void main(String[] args) {
		PropertiesUtil.load();
		RedisManager redis = new RedisManager();
		SqlSession session = MyBatisManager.getSqlSessionFactory().openSession();
		BankOperatorMapperCG cgMapper = session.getMapper(BankOperatorMapperCG.class);
		List<BankOperatorCG> list = cgMapper.findAll();
		for(BankOperatorCG b : list){
			String key = RedisKeyUtil.cbbank_bank_user_client_id_user_id(Constans.clientId,b.getId()) ;
			long result = redis.hset(key, "password", b.getPassword());
			System.out.println(result);
		}
	}
	
}
