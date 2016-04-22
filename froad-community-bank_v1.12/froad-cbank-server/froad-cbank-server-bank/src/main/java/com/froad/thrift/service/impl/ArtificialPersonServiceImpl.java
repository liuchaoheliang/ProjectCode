package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.ArtificialPersonLogic;
import com.froad.logic.impl.ArtificialPersonLogicImpl;
import com.froad.po.Org;
import com.froad.po.Result;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.vo.OrgVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.service.ArtificialPersonService;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;
import com.froad.util.LogUtils;


/**
 * 
 * <p>@Title: ArtificialPersonServiceImpl.java</p>
 * <p>Description: 描述 </p> 法人行社管理员thrift实现类
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年4月17日
 */
public class ArtificialPersonServiceImpl extends BizMonitorBaseService implements ArtificialPersonService.Iface {
	
	private ArtificialPersonLogic artificialPersonLogic = new ArtificialPersonLogicImpl();
	public ArtificialPersonServiceImpl(String name, String version) {
		super(name, version);
	}
	
	
	
	/**
     * 批量生成法人行社管理员接口(一个法人行社orgCode只能生成一个管理员用户)
     * @param clientId 客户端id
     * @param orgCodes 法人行社orgCode集合
     * @param defaultPassword 初始密码
     * @param prefix 登录名前缀
     * @return 批量生成失败的orgCode及名称(orgCode:orgName;)  生成成功返回OK  审核异常返回ERROR 
     */
	@Override
	public ResultVo addArtificialPerson(OriginVo originVo,String clientId, List<String> orgCodes,String defaultPassword,String prefix)
			throws TException {
		Result result = new Result();
		
		try{
			//添加操作日志记录
			originVo.setDescription("批量生成法人行社管理员");
			LogUtils.addLog(originVo);
			
			
			if(Checker.isEmpty(clientId)){
				throw new FroadServerException("添加ArtificialPerson失败,原因:clientId不能为空!");
			}
			if(Checker.isEmpty(orgCodes)){
				throw new FroadServerException("添加ArtificialPerson失败,原因:orgCodes不能为空!");
			}
			if(Checker.isEmpty(defaultPassword)){
				throw new FroadServerException("添加ArtificialPerson失败,原因:defaultPassword不能为空!");
			}
			
			String message = artificialPersonLogic.addArtificialPerson(clientId, orgCodes,defaultPassword,prefix);
			if (!message.equals("OK")) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc(message);
			}
		}catch (FroadServerException e) {
			LogCvt.error("批量生成法人行社管理员addArtificialPerson失败, 原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("批量生成法人行社管理员addArtificialPerson失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.bank_error.getCode());
			result.setResultDesc(ResultCode.bank_error.getMsg());
		}
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
		
		
	}
	
	
	/**
     * 批量删除法人行社管理员账号
     * @param clientId 客户端id
     * @param orgCodes 法人行社orgCode集合
     * @return Boolean    是否成功
     */
	@Override
	public ResultVo deleteArtificialPerson(OriginVo originVo,String clientId, List<String> orgCodes)
			throws TException {
		Result result = new Result();
		try{
			
			//添加操作日志记录
			originVo.setDescription("批量删除法人行社管理员帐号");
			LogUtils.addLog(originVo);
			
			if(Checker.isEmpty(clientId)){
				throw new FroadServerException("删除ArtificialPerson失败,原因:clientId不能为空!");
			}
			if(Checker.isEmpty(orgCodes)){
				throw new FroadServerException("删除ArtificialPerson失败,原因:orgCodes不能为空!");
			}
			
			//控制当前登录人不能删除自己管理员帐号（列表中只允许省联社查询列表，故而不存在法人行社操作员删除自己的账号问题）
//			if(Checker.isNotEmpty(originVo.getClientId()) && Checker.isNotEmpty(originVo.getOperatorId())){
//				BankOperator bankOperator = new CommonLogicImpl().getBankOperatorById(originVo.getClientId(), originVo.getOperatorId());
//				if(Checker.isNotEmpty(bankOperator)){
//					//判断当前所属机构编号是否存在已删除list中
//					boolean isCurrent = orgCodes.contains(bankOperator.getOrgCode());
//					if(isCurrent){
//						//判断是否法人行社管理员角色
//						BankRole bankRole =new BankRole();
//						bankRole.setClientId(clientId);
//						bankRole.setTag("0");
//						bankRole.setStatus(true);
//						bankRole.setIsDelete(false);
//						List<BankRole> roles = new BankRoleLogicImpl().findBankRole(bankRole);
//						//新增角色中，一个clientId只能有一个默认的管理员角色
//						if(roles!=null && roles.size()==1){
//							bankRole=roles.get(0);
//						}else{
//							throw new FroadServerException(clientId+"客户端下无配置默认管理员角色");
//						}
//						
//						if(bankOperator.getRoleId() == bankRole.getId()){
//							throw new FroadServerException("法人行社管理员不可删除自己账号");
//						}
//					}
//				}
//			}
			
			if (!artificialPersonLogic.deleteArtificialPerson(clientId, orgCodes)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("批量删除法人行社管理员账号DB操作失败");
			}
		} catch (FroadServerException e) {
			LogCvt.error("批量删除法人行社管理员账号deleteArtificialPerson失败，原因:" + e.getMessage(), e); 
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("批量删除法人行社管理员账号deleteArtificialPerson失败，原因:" + e.getMessage(), e); 
			result.setResultCode(ResultCode.bank_error.getCode());
			result.setResultDesc(ResultCode.bank_error.getMsg());
		}
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
		
	}
	
	
	/**
     * 批量重置法人行社管理员密码
     * @param clientId 客户端id
     * @param orgCodes 法人行社orgCode集合
     * @param defaultPassword 初始密码
     * @return Boolean    是否成功
     */
	@Override
	public ResultVo updateArtificialPerson(OriginVo originVo,String clientId, List<String> orgCodes,String defaultPassword)
			throws TException {
		
		Result result = new Result();
		try{
			//添加操作日志记录
			originVo.setDescription("批量重置法人行社管理员密码");
			LogUtils.addLog(originVo);
			
			if(Checker.isEmpty(clientId)){
				throw new FroadServerException("修改ArtificialPerson失败,原因:clientId不能为空!");
			}
			if(Checker.isEmpty(orgCodes)){
				throw new FroadServerException("修改ArtificialPerson失败,原因:orgCodes不能为空!");
			}
			if(Checker.isEmpty(defaultPassword)){
				throw new FroadServerException("修改ArtificialPerson失败,原因:defaultPassword不能为空!");
			}
			
			if (!artificialPersonLogic.updateArtificialPerson(clientId, orgCodes,defaultPassword)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("批量重置法人行社管理员密码DB操作失败");
			}
		} catch (FroadServerException e) {
			LogCvt.error("批量重置法人行社管理员密码updateArtificialPerson失败，原因:" + e.getMessage(), e); 
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("批量重置法人行社管理员密码updateArtificialPerson失败，原因:" + e.getMessage(), e); 
			result.setResultCode(ResultCode.bank_error.getCode());
			result.setResultDesc(ResultCode.bank_error.getMsg());
		}
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}
	
	
	/**
     * 法人行社列表接口
     * @param clientId 客户端id
     * @param orgCode 当前登录人所属的orgCode
     * @return List<Org> 法人行社org对象
     */
	@Override
	public List<OrgVo> getArtificialPerson(String clientId,String orgCode) throws TException {
		if(Checker.isEmpty(orgCode) || Checker.isEmpty(clientId)){
			LogCvt.error(ResultCode.notAllParameters.getMsg());
			return new ArrayList<OrgVo>();
		}
		
		List<Org> orgs=artificialPersonLogic.findArtificialPerson(clientId,orgCode);
		List<OrgVo> result =(List<OrgVo>)BeanUtil.copyProperties(OrgVo.class, orgs);
		
		
		return result==null?new ArrayList<OrgVo>():result;
	}
	
	
	/**
     * 已生成管理员的法人行社列表接口
     * @param clientId 客户端id
     * @param orgCode 当前登录人所属的orgCode
     * @return List<Org> 已生成的法人行社org对象
     */
	@Override
	public List<OrgVo> getArtificialPersonByAdd(String clientId,String orgCode)
			throws TException {
		if(Checker.isEmpty(orgCode) || Checker.isEmpty(clientId)){
			LogCvt.error(ResultCode.notAllParameters.getMsg());
			return new ArrayList<OrgVo>();
		}
		
		List<Org> orgs=artificialPersonLogic.findArtificialPersonByAdd(clientId,orgCode);
		List<OrgVo> result =(List<OrgVo>)BeanUtil.copyProperties(OrgVo.class, orgs);
		
		
		return result==null?new ArrayList<OrgVo>():result;
	}


	/**
     * 未生成管理员的法人行社列表接口
     * @param clientId 客户端id
     * @param orgCode 当前登录人所属的orgCode
     * @return List<Org> 未生成的法人行社org对象
     */
	@Override
	public List<OrgVo> getArtificialPersonByNotAdd(String clientId,String orgCode) throws TException {
		if(Checker.isEmpty(orgCode) || Checker.isEmpty(clientId)){
			LogCvt.error(ResultCode.notAllParameters.getMsg());
			return new ArrayList<OrgVo>();
		}
		
		List<Org> orgs=artificialPersonLogic.findArtificialPersonByNotAdd(clientId,orgCode);
		List<OrgVo> result =(List<OrgVo>)BeanUtil.copyProperties(OrgVo.class, orgs);
		
		
		return result==null?new ArrayList<OrgVo>():result;
	}


   
	
	
}
