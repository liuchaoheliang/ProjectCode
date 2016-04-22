package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import com.froad.db.mysql.bean.Page;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.OrgLevelLogic;
import com.froad.logic.impl.OrgLevelLogicImpl;
import com.froad.po.OrgLevel;
import com.froad.po.Result;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.OrgLevelService;
import com.froad.thrift.vo.CommonAddVoRes;
import com.froad.thrift.vo.OrgLevelPageVoRes;
import com.froad.thrift.vo.OrgLevelVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;
import com.froad.util.LogUtils;


/**
 * 
 * <p>@Title: OrgLevelImpl.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class OrgLevelServiceImpl extends BizMonitorBaseService implements OrgLevelService.Iface {
	private OrgLevelLogic orgLevelLogic = new OrgLevelLogicImpl();
	public OrgLevelServiceImpl(String name, String version) {
		super(name, version);
	}


    /**
     * 增加 OrgLevel
     * @param orgLevel
     * @return long    主键ID
     */
	@Override
	public CommonAddVoRes addOrgLevel(OriginVo originVo,OrgLevelVo orgLevelVo) throws TException {
		
		CommonAddVoRes voRes = new CommonAddVoRes();
		Result result = new Result();
		try{
			//添加操作日志记录
			originVo.setDescription("添加银行联合登录初始化机构级别对应角色信息");
			LogUtils.addLog(originVo);
			
			//vo 转 po 
			OrgLevel orgLevel = (OrgLevel)BeanUtil.copyProperties(OrgLevel.class, orgLevelVo);
			
			if(Checker.isEmpty(orgLevel)){
				throw new FroadServerException("添加OrgLevel失败,原因:添加对象不能为空!");
			}
			if(Checker.isEmpty(orgLevel.getClientId())){
				throw new FroadServerException("添加OrgLevel失败,原因:ClientId不能为空!");
			}
			if(Checker.isEmpty(orgLevel.getRoleId())){
				throw new FroadServerException("添加OrgLevel失败,原因:RoleId不能为空!");
			}
			if(Checker.isEmpty(orgLevel.getOrgLevel())){
				throw new FroadServerException("添加OrgLevel失败,原因:OrgLevel不能为空!");
			}
			
			
			Long id = orgLevelLogic.addOrgLevel(orgLevel);
			if(id != null && id > 0){
				voRes.setId(id);
			}else{
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("添加联合登录机构级别角色失败");
			}
		}catch (FroadServerException e) {
			LogCvt.error("添加联合登录机构级别角色addOrgLevel失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		}catch (Exception e) {
			LogCvt.error("添加联合登录机构级别角色addOrgLevel异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.bank_error.getCode());
			result.setResultDesc(ResultCode.bank_error.getMsg());
		}
		
		voRes.setResult((ResultVo)BeanUtil.copyProperties(ResultVo.class,result));
		return voRes;
		
	}



    /**
     * 删除 OrgLevel
     * @param orgLevel
     * @return boolean    
     */
	@Override
	public ResultVo deleteOrgLevel(OriginVo originVo,OrgLevelVo orgLevelVo) throws TException {
		
		Result result = new Result();
		try{
			//添加操作日志记录
			originVo.setDescription("删除银行联合登录初始化机构级别对应角色信息");
			LogUtils.addLog(originVo);
			
			//vo 转 po 
			OrgLevel orgLevel = (OrgLevel)BeanUtil.copyProperties(OrgLevel.class, orgLevelVo);
			
			//验证update和delete时id不能为空
			if(Checker.isEmpty(orgLevel.getId())){
				result = new Result(ResultCode.no_id_error);
				return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
			}
			
			if (!orgLevelLogic.deleteOrgLevel(orgLevel)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("删除银行联合登录角色级别表DB操作异常");
			}
		}catch (FroadServerException e) {
			LogCvt.error("删除联合登录机构级别角色deleteOrgLevel失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("删除联合登录机构级别角色deleteOrgLevel异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.bank_error.getCode());
			result.setResultDesc(ResultCode.bank_error.getMsg());
		}
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
		
	}



    /**
     * 修改 OrgLevel
     * @param orgLevel
     * @return boolean    
     */
	@Override
	public ResultVo updateOrgLevel(OriginVo originVo,OrgLevelVo orgLevelVo) throws TException {
		
		Result result = new Result();
		try{
			//添加操作日志记录
			originVo.setDescription("修改银行联合登录初始化机构级别对应角色信息");
			LogUtils.addLog(originVo);
			
			// vo 转 po
			OrgLevel orgLevel = (OrgLevel)BeanUtil.copyProperties(OrgLevel.class, orgLevelVo);
			
			//验证update和delete时id不能为空
			if(Checker.isEmpty(orgLevel.getId())){
				result = new Result(ResultCode.no_id_error);
				return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
			}
			
			if (!orgLevelLogic.updateOrgLevel(orgLevel)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("修改银行联合登录角色级别表DB操作异常");
			}
		}catch (FroadServerException e) {
			LogCvt.error("修改联合登录机构级别角色updateOrgLevel失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("修改联合登录机构级别角色updateOrgLevel异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.bank_error.getCode());
			result.setResultDesc(ResultCode.bank_error.getMsg());
		}
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
		
	}



    /**
     * 查询 OrgLevel
     * @param orgLevel
     * @return List<OrgLevelVo>
     */
	@Override
	public List<OrgLevelVo> getOrgLevel(OrgLevelVo orgLevelVo) throws TException {
		OrgLevel orgLevel = (OrgLevel)BeanUtil.copyProperties(OrgLevel.class, orgLevelVo);
			
		List<OrgLevel> orgLevelList = orgLevelLogic.findOrgLevel(orgLevel);
		
		List<OrgLevelVo> orgLevelVoList =(List<OrgLevelVo>)BeanUtil.copyProperties(OrgLevelVo.class, orgLevelList);
		
		return orgLevelVoList==null?new ArrayList<OrgLevelVo>():orgLevelVoList;
	}


	/**
     * 分页查询 OrgLevel
     * @param orgLevel
     * @return OrgLevelPageVoRes
     */
	@Override
	public OrgLevelPageVoRes getOrgLevelByPage(PageVo pageVo, OrgLevelVo orgLevelVo) throws TException {
		Page<OrgLevel> page = null;
		OrgLevel orgLevel = null;
		
		page=(Page<OrgLevel>)BeanUtil.copyProperties(Page.class, pageVo);
		orgLevel=(OrgLevel)BeanUtil.copyProperties(OrgLevel.class, orgLevelVo);

		page = orgLevelLogic.findOrgLevelByPage(page, orgLevel);

		
		List<OrgLevelVo> orgLevelVoList=(List<OrgLevelVo>)BeanUtil.copyProperties(OrgLevelVo.class, page.getResultsContent());
		pageVo=(PageVo)BeanUtil.copyProperties(PageVo.class, page);
		
		OrgLevelPageVoRes orgLevelPageVoRes = new OrgLevelPageVoRes();
		orgLevelPageVoRes.setOrgLevelVoList(orgLevelVoList);
		orgLevelPageVoRes.setPage(pageVo);
		
		return orgLevelPageVoRes;
	}


}
