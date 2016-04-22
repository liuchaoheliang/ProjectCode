package com.froad.process.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;

import com.froad.config.ProcessServiceConfig;
import com.froad.db.bps.entity.BankOperator;
import com.froad.db.bps.entity.BankOperatorRole;
import com.froad.db.bps.entity.BankOrganization;
import com.froad.db.bps.entity.Role;
import com.froad.db.bps.mappers.BankOperatorMapper;
import com.froad.db.bps.mappers.BankOperatorRoleMapper;
import com.froad.db.bps.mappers.BankOrganizationMapper;
import com.froad.db.bps.mappers.RoleMapper;
import com.froad.db.chonggou.entity.BankOperatorCG;
import com.froad.db.chonggou.entity.OrgUserRole;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.BankOperatorMapperCG;
import com.froad.db.chonggou.mappers.OrgUserRoleMapper;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.enums.TransferTypeEnum;
import com.froad.logback.LogCvt;
import com.froad.process.AbstractProcess;
import com.froad.util.Checker;
import com.froad.util.Constans;
import com.froad.util.RedisKeyUtil;

public class NewBankOperatorProcess extends AbstractProcess{
     
	private BankOperatorMapper bankOperatorMapper;
	private BankOperatorRoleMapper bankOperatorRoleMapper;
	private TransferMapper transferMapper;
	private OrgUserRoleMapper orgUserRoleMapper;
	private RoleMapper roleMapper;
	private Map<String,Object> tranMapper;
	private BankOrganizationMapper bankOrganizationMapper;
	private BankOperatorMapperCG bankOperatorMapperCG;
	private Map<Long, Long> info;
	private Map<Long, Long> operatorRoleInfo;
	//===========
	private String[] loginNames={"admin","ffttest1","ffttest2","ffttest3"};
	private Set<String> loginNameSet;
	private String pass = "vLFfghR5tNV3K9DKhmwArV+SbjWAcgZZzIDTnJ0JgCo=";
	private boolean  isReset = false;
	private final String STRING="string";
	private final String DATE="date";
	private final String INTEGER="int";
	private final String DOUBLE="double";
	private final String LONG="long";
	public NewBankOperatorProcess(String name, ProcessServiceConfig config) {
		super(name, config);
	}

	@Override
	public void before() {
		orgUserRoleMapper=sqlSession.getMapper(OrgUserRoleMapper.class);
		bankOperatorMapperCG = sqlSession.getMapper(BankOperatorMapperCG.class);
		tranMapper=new HashMap<String,Object>();
		LogCvt.info("删除cb_bank_opertator开始");
		bankOperatorMapperCG.deleteByClientId(Constans.clientId);
		LogCvt.info("删除cb_org_user_role开始");
		orgUserRoleMapper.deleteOrgUserRole(Constans.clientId);
		roleMapper=bpsSqlSession.getMapper(RoleMapper.class);
		List<Role> roles=roleMapper.selectAllRoles();
		for(Role role:roles ){
			tranMapper.put(String.valueOf(role.getRoleId()), role);
		}
	}

	@Override
	public void process() {
		//=============初始化数据源==============
		bankOperatorMapper = bpsSqlSession.getMapper(BankOperatorMapper.class);
		bankOperatorRoleMapper = bpsSqlSession.getMapper(BankOperatorRoleMapper.class);
		transferMapper = sqlSession.getMapper(TransferMapper.class);
		bankOrganizationMapper=bpsSqlSession.getMapper(BankOrganizationMapper.class);
        LogCvt.info("银行操作员表cb_bank_operator 数据迁移开始");
		List<BankOperator> bankOperators = bankOperatorMapper.selectAllOperators();
		if(Checker.isEmpty(bankOperators)){
			LogCvt.info("原重庆bps表tr_bank_operator 无数据需要迁移");
			return;
		}
		//加载到内存中
		LoadRoleInfo();
		for(BankOperator bankOperator:bankOperators){
			 LogCvt.info("银行操作员表cb_bank_operator的userId:"+bankOperator.getOperatorId());
			 //添加操作员用户
			 BankOperatorCG sameBankOperatorCG = bankOperatorMapperCG.findByUsername(bankOperator.getLoginName(), Constans.clientId);
				
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
//				bankOperatorCG.setPassword(bankOperator.getLoginPasswd());
				bankOperatorCG.setOrgCode(Constans.filterOrg(bankOperator.getOrgCode()));
				bankOperatorCG.setMobile(bankOperator.getMobile());
				bankOperatorCG.setEmail(bankOperator.getEmail());
				bankOperatorCG.setName(bankOperator.getOperatorName());
				bankOperatorCG.setPosition(bankOperator.getPosition());
				bankOperatorCG.setDepartment(bankOperator.getDepartment());
				bankOperatorCG.setStatus(BooleanUtils.toBooleanObject(bankOperator.getStatus(), "1", "0", ""));
				bankOperatorCG.setRemark(bankOperator.getRemark());
				if (null == bankOperator.getIsReset() || bankOperator.getIsReset().equals("") || bankOperator.getIsReset().equals("1")){
					isReset = true;
				}
				bankOperatorCG.setIsReset(isReset);
				bankOperatorCG.setRoleId(Checker.isNotEmpty(info.get(oldRoleId))?info.get(oldRoleId):0l);
				bankOperatorCG.setIsDelete(false);
				if(checkIfAdd(bankOperator.getLoginName())){
					bankOperatorCG.setType("2");
				}else{
					bankOperatorCG.setType("1");
				}
				long result = bankOperatorMapperCG.addBankOperator(bankOperatorCG);
				LogCvt.info("银行操作员表cb_bank_operator 数据迁移"+(result > 0?"成功":"失败"));
				if(result > 0){
					boolean redisResult = addBankOperatorRedis(bankOperatorCG);
					redisResult = addBankUserlogin(bankOperatorCG.getId(), bankOperator.getFailures());
					 Transfer transfer = new Transfer();
					 transfer.setOldId(String.valueOf(bankOperator.getOperatorId()));
					 transfer.setNewId(String.valueOf(bankOperatorCG.getId()));
					 transfer.setType(TransferTypeEnum.bank_operator_id);
					 transferMapper.insert(transfer);
					LogCvt.info("银行操作员缓存数据迁移"+(redisResult?"成功":"失败"));
					LogCvt.info("银行操作员[operatorId:"+bankOperator.getOperatorId()+"] 数据迁移"+(result > 0?"成功":"失败"));
				}
			 //添加联和登录用户
				if(checkIfAdd(bankOperator.getLoginName())){
					addOrgUserRole(bankOperator,bankOperatorCG.getId());
				}else{
					LogCvt.error("操作员ID"+bankOperator.getOperatorId()+",的用户"+bankOperator.getLoginName()+"不进行联和登录迁移");
				}
		}
		
		
		
		
	}
//	public boolean addBankOperator(BankOperator bankOperator){
//		BankOperatorCG sameBankOperatorCG = bankOperatorMapperCG.findByUsername(bankOperator.getLoginName(), Constans.clientId);
//		
//		if(Checker.isNotEmpty(sameBankOperatorCG)){
//			LogCvt.error("银行登录名[login_name:"+bankOperator.getLoginName()+"] 已存在, 不能重复添加");
//			continue;
//		}
//		if(Checker.isEmpty(operatorRoleInfo.get(bankOperator.getOperatorId()))) continue;
//		Long oldRoleId = operatorRoleInfo.get(bankOperator.getOperatorId());
//		
//		BankOperatorCG bankOperatorCG = new BankOperatorCG();
//		bankOperatorCG.setCreateTime(new Date());
//		bankOperatorCG.setClientId(Constans.clientId);
//		bankOperatorCG.setUsername(bankOperator.getLoginName());
//		bankOperatorCG.setPassword(pass);
////		bankOperatorCG.setPassword(bankOperator.getLoginPasswd());
//		bankOperatorCG.setOrgCode(Constans.filterOrg(bankOperator.getOrgCode()));
//		bankOperatorCG.setMobile(bankOperator.getMobile());
//		bankOperatorCG.setEmail(bankOperator.getEmail());
//		bankOperatorCG.setName(bankOperator.getOperatorName());
//		bankOperatorCG.setPosition(bankOperator.getPosition());
//		bankOperatorCG.setDepartment(bankOperator.getDepartment());
//		bankOperatorCG.setStatus(BooleanUtils.toBooleanObject(bankOperator.getStatus(), "1", "0", ""));
//		bankOperatorCG.setRemark(bankOperator.getRemark());
//		if (null == bankOperator.getIsReset() || bankOperator.getIsReset().equals("") || bankOperator.getIsReset().equals("1")){
//			isReset = true;
//		}
//		bankOperatorCG.setIsReset(isReset);
//		bankOperatorCG.setRoleId(Checker.isNotEmpty(info.get(oldRoleId))?info.get(oldRoleId):0l);
//		bankOperatorCG.setIsDelete(false);
//		
//		long result = bankOperatorMapperCG.addBankOperator(bankOperatorCG);
//		LogCvt.info("银行操作员表cb_bank_operator 数据迁移"+(result > 0?"成功":"失败"));
//		if(result > 0){
//			boolean redisResult = addBankOperatorRedis(bankOperatorCG);
//			redisResult = addBankUserlogin(bankOperatorCG.getId(), bankOperator.getFailures());
//			LogCvt.info("银行操作员缓存数据迁移"+(redisResult?"成功":"失败"));
//			LogCvt.info("银行操作员[operatorId:"+bankOperator.getOperatorId()+"] 数据迁移"+(result > 0?"成功":"失败"));
//			return true;
//		}
//		return false;
//		
//	}
	public boolean checkIfAdd(String name){
		if(loginNameSet.contains(name)){
			return false;
		}
		
		return true;
	}
	public void addOrgUserRole(BankOperator bankOperator,Long newId){
		LogCvt.info("联和登录用户迁移开始..........");
		Long oldRoleId = operatorRoleInfo.get(bankOperator.getOperatorId());
		OrgUserRole orgUserRole=new OrgUserRole();
		orgUserRole.setClientId(Constans.clientId);
		orgUserRole.setUserId(newId);
		orgUserRole.setUserName((String)checkIsEmpty(bankOperator.getLoginName(),STRING));
		orgUserRole.setOrgCode(Constans.filterOrg(bankOperator.getOrgCode()));
		orgUserRole.setRoleId(Checker.isNotEmpty(info.get(oldRoleId))?info.get(oldRoleId):0l);
		Role role=(Role)tranMapper.get(String.valueOf(oldRoleId));
		orgUserRole.setRoleName(role==null?"":role.getRoleName());
		orgUserRole.setOrgLevel(getLevel(bankOperator.getOrgCode(),bankOperator.getOperatorId()));
		long result=orgUserRoleMapper.addOrgUserRole(orgUserRole);
		LogCvt.info("联和登录用户cb_bank_operator 数据迁移"+(result > 0?"成功":"失败"));
	}
	
	
	public int getLevel(String orgCode,Long userId){
		BankOrganization bankOrganization=	bankOrganizationMapper.findByOrgCode(orgCode);
		if(bankOrganization!=null){
			 return bankOrganization.getLevel();
		}else{
			LogCvt.error("userID为"+userId+"的操作员无对应的机构");
		}
		return 0;
		
	}
	private void LoadRoleInfo(){
	   loginNameSet=new HashSet<String>();
		List<Transfer> transfers = transferMapper.queryGroupList(TransferTypeEnum.bank_role_id);
	    info = new HashMap<Long, Long>();
		for(Transfer transfer : transfers){
			info.put(Long.valueOf(transfer.getOldId()), Long.valueOf(transfer.getNewId()));
		}
		List<BankOperatorRole> list = bankOperatorRoleMapper.findAll();
		operatorRoleInfo = new HashMap<Long, Long>();
		for(BankOperatorRole b : list){
			operatorRoleInfo.put(b.getOperatorId(), b.getRoleId());
		}
		for(String name:loginNames){
			loginNameSet.add(name);
		}
	}
//	private Map<Long, Long> getOperatorRoleInfo(){
//		List<BankOperatorRole> list = bankOperatorRoleMapper.findAll();
//		Map<Long, Long> info = new HashMap<Long, Long>();
//		for(BankOperatorRole b : list){
//			info.put(b.getOperatorId(), b.getRoleId());
//		}
//		return info;
//	}
//	private Map<Long, Long> getBankRoleInfo(){
//		List<Transfer> transfers = transferMapper.queryGroupList(TransferTypeEnum.bank_role_id);
//	    info = new HashMap<Long, Long>();
//		for(Transfer transfer : transfers){
//			info.put(Long.valueOf(transfer.getOldId()), Long.valueOf(transfer.getNewId()));
//		}
//		return info;
//	}
	  public Object checkIsEmpty(Object obj,String jobj) {
	    	if(obj instanceof String){
	    		if(obj!=null){
	    			return obj;
	    		}
	    	}
	    	if(obj instanceof Integer || obj instanceof Double){
	    		if(obj!=null){
	    			return obj;
	    		}
	    	}
	    	if(obj instanceof Date){
	    		if(obj!=null){
	    			return obj;
	    		}
	    	}
	    	if(obj ==null){
	    		if(jobj.equals(STRING)){
	    			return "";
	    		}
	    		if(jobj.equals(INTEGER)){
	    			return 0;
	    		}
	    		if(jobj.equals(DOUBLE)){
	    			return 0.0;
	    		}
	    		if(jobj.equals(LONG)){
	    			return 0L;
	    		}
	    	}
	    	return null;
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
}

