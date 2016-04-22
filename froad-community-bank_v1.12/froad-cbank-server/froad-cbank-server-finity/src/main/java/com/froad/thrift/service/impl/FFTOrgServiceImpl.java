package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.thrift.TException;

import com.froad.db.mysql.bean.Page;
import com.froad.enums.Platform;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.FFTOrgLogic;
import com.froad.logic.impl.FFTOrgLogicImpl;
import com.froad.po.FFTOrg;
import com.froad.po.FFTOrgRe;
import com.froad.po.OrgRole;
import com.froad.po.Result;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.vo.CommonAddVoRes;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.orgRoleManager.FFTOrgDetailVo;
import com.froad.thrift.vo.orgRoleManager.FFTOrgListVoRes;
import com.froad.thrift.vo.orgRoleManager.FFTOrgNameListVoRes;
import com.froad.thrift.vo.orgRoleManager.FFTOrgPageVoRes;
import com.froad.thrift.vo.orgRoleManager.FFTOrgReListVoRes;
import com.froad.thrift.vo.orgRoleManager.FFTOrgReVo;
import com.froad.thrift.vo.orgRoleManager.FFTOrgVo;
import com.froad.thrift.vo.orgRoleManager.OrgRoleIdListVoRes;
import com.froad.thrift.vo.orgRoleManager.OrgRoleListVoRes;
import com.froad.thrift.vo.orgRoleManager.OrgRoleVo;
import com.froad.thrift.vo.orgRoleManager.isNextFFTOrgVo;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;
import com.froad.util.LogUtils;
import com.froad.thrift.service.FFTOrgService;


/**
 * 
 * <p>@Title: FFTOrgServiceImpl.java</p>
 * <p>Description: 描述 </p> 组织Service实现类
 * @author f-road-ll 
 * @version 1.0
 * @created 2016年1月5日
 */
public class FFTOrgServiceImpl extends BizMonitorBaseService implements FFTOrgService.Iface {
	private FFTOrgLogic fftOrgLogic = new FFTOrgLogicImpl();
	public FFTOrgServiceImpl(String name, String version)
	{
		super(name, version);
	}


	/**
     * 新增 组织
     * @param originVo 源对象信息(包含平台,操作ip,操作员id等...)
     * @param fftOrgVo 组织信息对象
     * @param roleIds 角色分配
     * @param roleIds 数据权限
     * @return CommonAddVoRes
     */
	@Override
	public CommonAddVoRes addFFTOrg(OriginVo originVo,FFTOrgVo fftOrgVo,List<Long> roleIds, List<String> reOrgIds) throws TException {
		
		CommonAddVoRes voRes = new CommonAddVoRes();
		Result result = new Result();
		try{
			//添加操作日志记录
			originVo.setDescription("添加组织信息");
			LogUtils.addLog(originVo);
						
			if(Checker.isEmpty(fftOrgVo)){
				throw new FroadServerException("添加FFTOrg失败,原因:添加对象不能为空!");
			}
			if(Checker.isEmpty(fftOrgVo.getParentId())){
				throw new FroadServerException("添加FFTOrg失败,原因:ParentId不能为空!");
			}
			if(Checker.isEmpty(fftOrgVo.getLevel())){
				throw new FroadServerException("添加FFTOrg失败,原因:Level不能为空!");
			}
			if(Checker.isEmpty(fftOrgVo.getPlatform())){
				throw new FroadServerException("添加FFTOrg失败,原因:Platform不能为空!");
			}
			if(Checker.isEmpty(fftOrgVo.getOrgName())){
				throw new FroadServerException("添加FFTOrg失败,原因:OrgName不能为空!");
			}
			if(fftOrgVo.getLevel() == 0){
				throw new FroadServerException("添加FFTOrg失败,原因:不可新增此组织类型组织!");
			}
			
			if(fftOrgVo.getPlatform().equals(Platform.bank.getType())){
				//接入行组织，需要机构表的id
				if(Checker.isEmpty(fftOrgVo.getOrgId())){
					throw new FroadServerException("添加FFTOrg失败,原因:OrgId不能为空!");
				}
				if(Checker.isEmpty(fftOrgVo.getCode())){
					throw new FroadServerException("添加FFTOrg失败,原因:Code不能为空!");
				}
				if(fftOrgVo.getLevel() == 1){
					throw new FroadServerException("添加FFTOrg失败,原因:接入行组织不可新增此组织类型组织!");
				}
			}
			
			//vo 转 po 
			FFTOrg fftOrg = (FFTOrg)BeanUtil.copyProperties(FFTOrg.class, fftOrgVo);

			Long id = fftOrgLogic.addFFTOrg(fftOrg,roleIds,reOrgIds);
			if(id == null || id<=0){
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("添加组织信息异常");
			}
			
			voRes.setId(id);
			
		}catch (FroadServerException e) {
			LogCvt.error("添加组织信息addFFTOrg失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		}catch (Exception e) {
			LogCvt.error("添加组织信息addFFTOrg异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.finity_error.getCode());
			result.setResultDesc(ResultCode.finity_error.getMsg());
		}
		
		voRes.setResult((ResultVo)BeanUtil.copyProperties(ResultVo.class,result));
		return voRes;
		
	}


	/**
     * 删除  组织
     * @param OriginVo 源对象信息(包含平台,操作ip,操作员id等...)
     * @param id 主键Id
     * @return ResultVo
     */
	@Override
	public ResultVo deleteFFTOrg(OriginVo originVo, long id) throws TException {
		
		Result result = new Result();
		try{
			//添加操作日志记录
			originVo.setDescription("删除组织信息");
			LogUtils.addLog(originVo);
			
			if(id<=0){
				throw new FroadServerException("删除deleteFFTOrg失败,原因:条件id数据异常!");
			}
			
			if (!fftOrgLogic.deleteFFTOrg(id)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("删除组织DB操作异常");
			}
		}catch (FroadServerException e) {
			LogCvt.error("删除组织deleteFFTOrg失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("删除组织deleteFFTOrg异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.finity_error.getCode());
			result.setResultDesc(ResultCode.finity_error.getMsg());
		}
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}

	

	/**
     * 修改 组织
     * @param OriginVo 源对象信息(包含平台,操作ip,操作员id等...)
     * @param fftOrgVo 组织信息对象
     * @param roleIds 角色分配
     * @param roleIds 数据权限
     * @return ResultVo 
     */
	@Override
	public ResultVo updateFFTOrg(OriginVo originVo,FFTOrgVo fftOrgVo, List<Long> roleIds, List<String> reOrgIds) throws TException {
		
		Result result = new Result();
		try{
			//添加操作日志记录
			originVo.setDescription("修改组织信息");
			LogUtils.addLog(originVo);
			
			//验证update和delete时id不能为空
			if(Checker.isEmpty(fftOrgVo.getId())){
				result = new Result(ResultCode.no_id_error);
				return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
			}
			
			// vo 转 po 
			FFTOrg fftOrg = (FFTOrg)BeanUtil.copyProperties(FFTOrg.class, fftOrgVo);
						
			if (!fftOrgLogic.updateFFTOrg(fftOrg,roleIds,reOrgIds)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("修改组织DB操作异常");
			}
		}catch (FroadServerException e) {
			LogCvt.error("修改组织updateFFTOrg失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("修改组织updateFFTOrg异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.finity_error.getCode());
			result.setResultDesc(ResultCode.finity_error.getMsg());
		}
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}
	
	
	
	/**
     * 组织查询Page
     * @param page 分页对象
     * @param fftOrgVo  组织对象过滤信息
     * @return FFTOrgPageVoRes
	 */
	@Override
	public FFTOrgPageVoRes getFFTOrgByPage(PageVo pageVo, FFTOrgVo fftOrgVo) throws TException {
		
		FFTOrgPageVoRes voRes = new FFTOrgPageVoRes();
		Result result = new Result();
		List<FFTOrgDetailVo> detailVoList = null;
		try{
			
			Page<FFTOrg> page = (Page<FFTOrg>)BeanUtil.copyProperties(Page.class, pageVo);
			FFTOrg fftOrg = (FFTOrg)BeanUtil.copyProperties(FFTOrg.class, fftOrgVo);

			page = fftOrgLogic.findFFTOrgByPage(page, fftOrg);
			List<FFTOrg> fftOrgList = page.getResultsContent();
			
			//定义变量
			detailVoList = new ArrayList<FFTOrgDetailVo>();
			//组装树路径名称及上级组织名称
			FFTOrgDetailVo detailVo = null;
//			for(FFTOrg org:fftOrgList){
//				detailVo = (FFTOrgDetailVo)BeanUtil.copyProperties(FFTOrgDetailVo.class, org);
//				
//				long parentId = org.getParentId();
//				String parentName = "";
//				String treePathName = "";//组织树分页查询未用到treePathName，故而不进行赋值
//				
//				//顶级组织
//				if(parentId == 0){
//					parentName = org.getOrgName();
//					treePathName = parentName;
//				}else{
//					//非顶级组织
//					String[] ids = org.getTreePath().split(",");
//					for(int i=0;i<ids.length;i++){
//						treePathName = treePathName + fftOrgLogic.findFFTOrgDetail(Long.valueOf(ids[i])).getOrgName();
//						if(i<ids.length-1){
//							treePathName+=",";
//						}
//					}
//				}
//				//父级组织名称
//				detailVo.setParentName(parentName);
//				//树路径名称
//				detailVo.setTreePathName(treePathName);
//				
//				detailVoList.add(detailVo);
//			}
			
			List<FFTOrgDetailVo> voList = new ArrayList<FFTOrgDetailVo>();//父级非顶级的组织
			List<Long> parentIds = new ArrayList<Long>();//父级非顶级的parentId
			for(FFTOrg f : fftOrgList){
				detailVo = (FFTOrgDetailVo)BeanUtil.copyProperties(FFTOrgDetailVo.class, f);
				if(f.getParentId() == 0){
					detailVo.setParentName(f.getOrgName());
					detailVoList.add(detailVo);
				}else{
					parentIds.add(f.getParentId());
					voList.add(detailVo);
				}
			}
			
			//减少上级组织的多次mysql查询，通过程序循环
			if(Checker.isNotEmpty(parentIds)){
				List<FFTOrg> parentFFtOrgs = fftOrgLogic.findFFTOrgByIds(parentIds);
				for(FFTOrg f : parentFFtOrgs){
					for(FFTOrgDetailVo vo : voList){
						if(f.getId() == vo.getParentId()){
							//重新塞上级组织名称
							vo.setParentName(f.getOrgName());
							detailVoList.add(vo);
						}
					}
				}
			}
			
			
			
			//fftOrgVoList = (List<FFTOrgVo>)BeanUtil.copyProperties(FFTOrgVo.class, page.getResultsContent());
			pageVo = (PageVo)BeanUtil.copyProperties(PageVo.class, page);
			
		}catch (FroadServerException e) {
			LogCvt.error("分页查询组织列表getFFTOrgByPage失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("分页查询组织列表getFFTOrgByPage异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.finity_error.getCode());
			result.setResultDesc(ResultCode.finity_error.getMsg());
		}
		
		voRes.setResultVo((ResultVo) BeanUtil.copyProperties(ResultVo.class, result));
		voRes.setPage(pageVo);
		voRes.setVoList(detailVoList==null?new ArrayList<FFTOrgDetailVo>():detailVoList);
		return voRes;
	}


	/**
     * 组织查询List
     * @param fftOrgVo 过滤条件对象
     * @return FFTOrgListVoRes
     * 
     **/
	@Override
	public FFTOrgListVoRes getFFTOrgByList(FFTOrgVo fftOrgVo) throws TException {
		FFTOrgListVoRes voRes = new FFTOrgListVoRes();
		Result result = new Result();
		List<FFTOrgVo> fftOrgVoList = null;
		try{
			FFTOrg fftOrg =(FFTOrg)BeanUtil.copyProperties(FFTOrg.class, fftOrgVo);
			
			List<FFTOrg> fftOrgList = fftOrgLogic.findFFTOrgByList(fftOrg);
			fftOrgVoList =(List<FFTOrgVo>)BeanUtil.copyProperties(FFTOrgVo.class, fftOrgList);
			
			
		}catch (FroadServerException e) {
			LogCvt.error("查询组织列表getFFTOrgByList失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("查询组织列表getFFTOrgByList异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.finity_error.getCode());
			result.setResultDesc(ResultCode.finity_error.getMsg());
		}
		
		voRes.setResultVo((ResultVo) BeanUtil.copyProperties(ResultVo.class, result));
		voRes.setVoList(fftOrgVoList==null?new ArrayList<FFTOrgVo>():fftOrgVoList);
		return voRes;
	}

	/**
     * 组织Id多集合查询
     * @param orgIds 组织Id集合
     * @return FFTOrgListVoRes
     * 
     */
	@Override
	public FFTOrgNameListVoRes getFFTOrgByOrgIds(List<String> orgIds)throws TException {
		FFTOrgNameListVoRes voRes = new FFTOrgNameListVoRes();
		Result result = new Result();
		List<FFTOrgDetailVo> detailVoList = null;
		try{
			if(orgIds.size()<=0){
				throw new FroadServerException("组织Id集合数据有误！");
			}
			
			List<FFTOrg> fftOrgList = fftOrgLogic.findFFTOrgByOrgIds(orgIds);
			//List<FFTOrgVo> fftOrgVoList =(List<FFTOrgVo>)BeanUtil.copyProperties(FFTOrgVo.class, fftOrgList);

			//组装树路径名称及上级组织名称
			FFTOrgDetailVo detailVo = null;
			detailVoList = new ArrayList<FFTOrgDetailVo>();
			for(FFTOrg org:fftOrgList){
				detailVo = (FFTOrgDetailVo)BeanUtil.copyProperties(FFTOrgDetailVo.class, org);
				
				long parentId = org.getParentId();
				String parentName = "";
				String treePathName = "";
				
				//顶级组织
				if(parentId == 0){
					parentName = org.getOrgName();
					treePathName = parentName;
				}else{
					//非顶级组织
					//树路径名称
					String[] ids = org.getTreePath().split(",");
					for(int i=0;i<ids.length;i++){
						treePathName = treePathName + fftOrgLogic.findFFTOrgDetail(Long.valueOf(ids[i])).getOrgName();
						if(i<ids.length-1){
							treePathName+=",";
						}
					}
					//上级组织名称
					String[] treePathNames = treePathName.split(",");
					parentName = treePathNames[treePathNames.length-2];
				}
				//父级组织名称
				detailVo.setParentName(parentName);
				//树路径名称
				detailVo.setTreePathName(treePathName);
				
				detailVoList.add(detailVo);
			}
			
			
		}catch (FroadServerException e) {
			LogCvt.error("组织Id多集合查询getFFTOrgByOrgIds失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("组织Id多集合查询getFFTOrgByOrgIds异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.finity_error.getCode());
			result.setResultDesc(ResultCode.finity_error.getMsg());
		}	
		voRes.setResultVo((ResultVo) BeanUtil.copyProperties(ResultVo.class, result));
		voRes.setVoList(detailVoList==null?new ArrayList<FFTOrgDetailVo>():detailVoList);
		return voRes;
	}

	/**
	 * 主键id多集合查询
	 * @param ids 主键id集合
	 * @return FFTOrgListVoRes
	 */
	@Override
	public FFTOrgListVoRes getFFTOrgByIds(List<Long> ids)throws TException {
		FFTOrgListVoRes voRes = new FFTOrgListVoRes();
		Result result = new Result();
		List<FFTOrgVo> voList = null;
		try{
			if(ids.size()<=0){
				throw new FroadServerException("条件id集合数据有误！");
			}
			
			List<FFTOrg> fftOrgList = fftOrgLogic.findFFTOrgByIds(ids);
			voList =(List<FFTOrgVo>)BeanUtil.copyProperties(FFTOrgVo.class, fftOrgList);
			
		}catch (FroadServerException e) {
			LogCvt.error("主键Id多集合查询getFFTOrgByIds失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("主键Id多集合查询getFFTOrgByIds异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.finity_error.getCode());
			result.setResultDesc(ResultCode.finity_error.getMsg());
		}	
		
		voRes.setResultVo((ResultVo) BeanUtil.copyProperties(ResultVo.class, result));
		voRes.setVoList(voList==null?new ArrayList<FFTOrgVo>():voList);
		return voRes;
	}
	
	
	
	/**
     * 组织列表拉取-查全部
     * @param userId 用户Id
     * @return FFTOrgListVoRes
     * 
     */
	@Override
	public FFTOrgListVoRes getFFTOrgByUserId(long userId) throws TException {
//		FFTOrgNameListVoRes voRes = new FFTOrgNameListVoRes();
		FFTOrgListVoRes voRes = new FFTOrgListVoRes();
		Result result = new Result();
		List<FFTOrgVo> fftOrgVoList = null;
//		List<FFTOrgDetailVo> detailVoList = null;
		try{
			
			List<FFTOrg> fftOrgList = fftOrgLogic.findFFTOrgByUserId(userId);
			fftOrgVoList =(List<FFTOrgVo>)BeanUtil.copyProperties(FFTOrgVo.class, fftOrgList);

//			detailVoList = new ArrayList<FFTOrgDetailVo>();
//			//组装树路径名称及上级组织名称
//			FFTOrgDetailVo detailVo = null;
//			for(FFTOrg org:fftOrgList){
//				detailVo = (FFTOrgDetailVo)BeanUtil.copyProperties(FFTOrgDetailVo.class, org);
//				
//				long parentId = org.getParentId();
//				String parentName = "";
//				String treePathName = "";
//				
//				//顶级组织
//				if(parentId == 0){
//					parentName = org.getOrgName();
//					treePathName = parentName;
//				}else{
//					//非顶级组织
//					//树路径名称
//					String[] ids = org.getTreePath().split(",");
//					for(int i=0;i<ids.length;i++){
//						treePathName = treePathName + fftOrgLogic.findFFTOrgDetail(Long.valueOf(ids[i])).getOrgName();
//						if(i<ids.length-1){
//							treePathName+=",";
//						}
//					}
//					//上级组织名称
//					String[] treePathNames = treePathName.split(",");
//					parentName = treePathNames[treePathNames.length-2];
//				}
//				//父级组织名称
//				detailVo.setParentName(parentName);
//				//树路径名称
//				detailVo.setTreePathName(treePathName);
//				
//				detailVoList.add(detailVo);
//			}
			
			
		}catch (FroadServerException e) {
			LogCvt.error("组织列表拉取getFFTOrgByUserId失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("组织列表拉取getFFTOrgByUserId异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.finity_error.getCode());
			result.setResultDesc(ResultCode.finity_error.getMsg());
		}	
		voRes.setResultVo((ResultVo) BeanUtil.copyProperties(ResultVo.class, result));
//		voRes.setVoList(detailVoList==null?new ArrayList<FFTOrgDetailVo>():detailVoList);
		voRes.setVoList(fftOrgVoList==null?new ArrayList<FFTOrgVo>():fftOrgVoList);
		return voRes;
	}


	/**
     * 组织列表拉取-查顶级2级
     * @param userId 用户Id
     * @return FFTOrgListVoRes
     * 
     */
	@Override
	public FFTOrgListVoRes getFFTOrgByUserIdTwoLevel(long userId) throws TException {
		FFTOrgListVoRes voRes = new FFTOrgListVoRes();
		Result result = new Result();
		List<FFTOrgVo> fftOrgVoList = null;
		try{
			
			List<FFTOrg> fftOrgList = fftOrgLogic.findFFTOrgByUserIdTwoLevel(userId);
			fftOrgVoList =(List<FFTOrgVo>)BeanUtil.copyProperties(FFTOrgVo.class, fftOrgList);

		}catch (FroadServerException e) {
			LogCvt.error("组织列表拉取查顶级2级getFFTOrgByUserIdTwoLevel失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("组织列表拉取查顶级2级getFFTOrgByUserIdTwoLevel异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.finity_error.getCode());
			result.setResultDesc(ResultCode.finity_error.getMsg());
		}	
		voRes.setResultVo((ResultVo) BeanUtil.copyProperties(ResultVo.class, result));
		voRes.setVoList(fftOrgVoList==null?new ArrayList<FFTOrgVo>():fftOrgVoList);
		return voRes;
	}
	
	/**
     * 组织列表拉取-查用户下该组织id下一级的权限组织
     * @param userId 用户Id
     * @param orgId 组织Id
     * @return FFTOrgListVoRes
     * 
     */
	@Override
	public FFTOrgListVoRes getFFTOrgByUserIdOrgId(long userId,String orgId) throws TException {
		FFTOrgListVoRes voRes = new FFTOrgListVoRes();
		Result result = new Result();
		List<FFTOrgVo> fftOrgVoList = null;
		try{
			
			List<FFTOrg> fftOrgList = fftOrgLogic.findFFTOrgByUserIdOrgId(userId,orgId);
			fftOrgVoList =(List<FFTOrgVo>)BeanUtil.copyProperties(FFTOrgVo.class, fftOrgList);

		}catch (FroadServerException e) {
			LogCvt.error("组织列表拉取查用户下组织id下一级的权限组织getFFTOrgByUserIdOrgId失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("组织列表拉取查用户下组织id下一级的权限组织getFFTOrgByUserIdOrgId异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.finity_error.getCode());
			result.setResultDesc(ResultCode.finity_error.getMsg());
		}	
		voRes.setResultVo((ResultVo) BeanUtil.copyProperties(ResultVo.class, result));
		voRes.setVoList(fftOrgVoList==null?new ArrayList<FFTOrgVo>():fftOrgVoList);
		return voRes;
	}
	
	/**
     * 验证组织下是否有下级组织
     * @param userId 用户Id
     * @param id集合，组织表主键id集合
     * @return isNextFFTOrgVo集合
     * 
     */
	@Override
	public List<isNextFFTOrgVo> isNextFFTOrg(long userId,List<Long> ids) throws TException {
		List<isNextFFTOrgVo> isNextFFTOrgVoList = new ArrayList<isNextFFTOrgVo>();
		try{
			if(Checker.isEmpty(ids)){
				throw new FroadServerException("id集合有误");
			}
			HashMap<Long, Boolean> hash = fftOrgLogic.isNextFFTOrg(userId,ids);
			isNextFFTOrgVo isNext = null;
			for(long id:ids){
				isNext = new isNextFFTOrgVo();
				isNext.setId(id);
				if(Checker.isNotEmpty(hash.get(id))){
					isNext.isNextFFTOrg = Boolean.valueOf(hash.get(id).toString());
				}else{
					isNext.isNextFFTOrg = false;
				}
				
				isNextFFTOrgVoList.add(isNext);
			}

		}catch (FroadServerException e) {
			LogCvt.error("验证组织下是否有下级组织isNextFFTOrg失败，原因:" + e.getMessage(), e);
		} catch (Exception e) {
			LogCvt.error("验证组织下是否有下级组织isNextFFTOrg异常，原因:" + e.getMessage(), e);
		}	
		return isNextFFTOrgVoList;
	}
	
	
	/**
     * 组织详情
     * @param id 主键Id
     * @param orgId 组织Id
     * @return FFTOrgDetailVo
     * 
     */
	@Override
	public FFTOrgDetailVo getFFTOrgDetail(long id,String orgId) throws TException {
		FFTOrgDetailVo detailVo = null;
		try{
			FFTOrg fftOrg = null;
			if(Checker.isNotEmpty(orgId)){
				fftOrg = fftOrgLogic.findFFTOrgByOrgId(orgId);
			}else{
				if(id<=0){
					throw new FroadServerException("查询条件数据有误");
				}
				fftOrg = fftOrgLogic.findFFTOrgDetail(id);
			}
			
			// po 转 vo
			detailVo = (FFTOrgDetailVo)BeanUtil.copyProperties(FFTOrgDetailVo.class, fftOrg);
			
			if(Checker.isNotEmpty(detailVo)){
				long parentId = fftOrg.getParentId();
				String parentName = "";
				String treePathName = "";
				//顶级组织
				if(parentId == 0){
					parentName = fftOrg.getOrgName();
					treePathName = parentName;
				}else{
					//非顶级组织
					//树路径名称
					String[] ids = fftOrg.getTreePath().split(",");
					for(int i=0;i<ids.length;i++){
						treePathName = treePathName + fftOrgLogic.findFFTOrgDetail(Long.valueOf(ids[i])).getOrgName();
						if(i<ids.length-1){
							treePathName+=",";
						}
					}
					//上级组织名称
					String[] treePathNames = treePathName.split(",");
					parentName = treePathNames[treePathNames.length-2];
				}
				//父级组织名称
				detailVo.setParentName(parentName);
				//树路径名称
				detailVo.setTreePathName(treePathName);
			}
			
			

		}catch (FroadServerException e) {
			LogCvt.error("组织详情getFFTOrgDetail失败，原因:" + e.getMessage(), e);
		} catch (Exception e) {
			LogCvt.error("组织详情getFFTOrgDetail异常，原因:" + e.getMessage(), e);
		}	
		return detailVo==null?new FFTOrgDetailVo():detailVo;
	}


	/**
     * 组织数据权限查询
     * @param orgId 组织Id
     * @return FFTOrgReListVoRes
     * 
     */
	@Override
	public FFTOrgReListVoRes getFFTOrgReByOrgId(String orgId) throws TException {
		FFTOrgReListVoRes voRes = new FFTOrgReListVoRes();
		Result result = new Result();
		List<FFTOrgReVo> fftOrgReVoList = null;
		try{
			
			List<FFTOrgRe> fftOrgReList = fftOrgLogic.findFFTOrgReByOrgId(orgId);
			List<FFTOrgReVo> resulList =(List<FFTOrgReVo>)BeanUtil.copyProperties(FFTOrgReVo.class, fftOrgReList);

			//查出所有数据权限reOrgId的对象集合
			List<String> reOrgIds = new ArrayList<String>(); 
			for(FFTOrgReVo resultVo:resulList){
				reOrgIds.add(resultVo.getReOrgId());
			}
			if(Checker.isEmpty(reOrgIds)){
				LogCvt.info("组织数据权限集合为空！");
				voRes.setResultVo((ResultVo) BeanUtil.copyProperties(ResultVo.class, result));
				voRes.setVoList(fftOrgReVoList==null?new ArrayList<FFTOrgReVo>():fftOrgReVoList);
				return voRes;
			}
			
			List<FFTOrg> fftOrgs = fftOrgLogic.findFFTOrgByOrgIds(reOrgIds);
			fftOrgReVoList = new ArrayList<FFTOrgReVo>();
			for(FFTOrg fftOrg : fftOrgs){
				for(FFTOrgReVo resultVo:resulList){
					if(fftOrg.getOrgId().equals(resultVo.getReOrgId())){
						long parentId = fftOrg.getParentId();
						String treePathName = "";
						//顶级组织
						if(parentId == 0){
							treePathName = fftOrg.getOrgName();
						}else{
							//非顶级组织
							String[] ids = fftOrg.getTreePath().split(",");
							for(int i=0;i<ids.length;i++){
								treePathName = treePathName + fftOrgLogic.findFFTOrgDetail(Long.valueOf(ids[i])).getOrgName();
								if(i<ids.length-1){
									treePathName+=",";
								}
							}
						}
						//树路径名称
						resultVo.setReOrgIdTreePathName(treePathName);
						//树路径
						resultVo.setReOrgIdtreePath(fftOrg.getTreePath());
						fftOrgReVoList.add(resultVo);
					}
				}
			}
			
			
		}catch (FroadServerException e) {
			LogCvt.error("组织数据权限查询getFFTOrgReByOrgId失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("组织数据权限查询getFFTOrgReByOrgId异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.finity_error.getCode());
			result.setResultDesc(ResultCode.finity_error.getMsg());
		}	
		voRes.setResultVo((ResultVo) BeanUtil.copyProperties(ResultVo.class, result));
		voRes.setVoList(fftOrgReVoList==null?new ArrayList<FFTOrgReVo>():fftOrgReVoList);
		return voRes;
	}


	 /**
     * 组织角色查询
     * @param userId 用户Id
     * @return OrgRoleListVoRes
     * 
     */
	@Override
	public OrgRoleListVoRes getOrgRoleByOrgId(String orgId) throws TException {
		OrgRoleListVoRes voRes = new OrgRoleListVoRes();
		Result result = new Result();
		List<OrgRoleVo> orgRoleVoList = null;
		try{
			
			List<OrgRole> orgRoleList = fftOrgLogic.findOrgRoleByOrgId(orgId);
			orgRoleVoList =(List<OrgRoleVo>)BeanUtil.copyProperties(OrgRoleVo.class, orgRoleList);

		}catch (FroadServerException e) {
			LogCvt.error("组织角色查询getOrgRoleByOrgId失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("组织角色查询getOrgRoleByOrgId异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.finity_error.getCode());
			result.setResultDesc(ResultCode.finity_error.getMsg());
		}	
		voRes.setResultVo((ResultVo) BeanUtil.copyProperties(ResultVo.class, result));
		voRes.setVoList(orgRoleVoList==null?new ArrayList<OrgRoleVo>():orgRoleVoList);
		return voRes;
	}


	/**
	 * 根据组织id集合查询组织角色id-去重
	 * @param orgIds 组织Id集合
	 * @return OrgRoleIdListVoRes
	 */
	@Override
	public OrgRoleIdListVoRes getOrgRoleIds(List<String> orgIds) throws TException {
		OrgRoleIdListVoRes voRes = new OrgRoleIdListVoRes();
		Result result = new Result();
		List<Long> orgRoleIdVoList = null;
		try{
			
			List<OrgRole> orgRoleList = fftOrgLogic.findOrgRoleByOrgIds(orgIds);
			
			Set<Long> set = new HashSet<Long>();
			for(OrgRole o : orgRoleList){
				set.add(o.getRoleId());
			}
			orgRoleIdVoList = new ArrayList<Long>(set);
			
		}catch (FroadServerException e) {
			LogCvt.error("组织角色id查询getOrgRoleIds失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("组织角色id查询getOrgRoleIds异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.finity_error.getCode());
			result.setResultDesc(ResultCode.finity_error.getMsg());
		}	
		
		voRes.setResultVo((ResultVo) BeanUtil.copyProperties(ResultVo.class, result));
		voRes.setRoleIdVoList(orgRoleIdVoList==null?new ArrayList<Long>():orgRoleIdVoList);
		return voRes;
	}
	
	/**
	 * 银行渠道列表(获取银行组织的一级)
	 * @param userId 用户Id
	 * @return FFTOrgListVoRes
	 */
	@Override
	public FFTOrgListVoRes getFFTOrgInOneByBank(long userId) throws TException {
		FFTOrgListVoRes voRes = new FFTOrgListVoRes();
		Result result = new Result();
		List<FFTOrgVo> fftOrgVoList = null;
		try{
			
			List<FFTOrg> fftOrgList = fftOrgLogic.findFFTOrgInOneByBank(userId);
			fftOrgVoList =(List<FFTOrgVo>)BeanUtil.copyProperties(FFTOrgVo.class, fftOrgList);
		}catch (FroadServerException e) {
			LogCvt.error("银行渠道列表获取getFFTOrgInOneByBank失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("银行渠道列表获取getFFTOrgInOneByBank异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.finity_error.getCode());
			result.setResultDesc(ResultCode.finity_error.getMsg());
		}	
		voRes.setResultVo((ResultVo) BeanUtil.copyProperties(ResultVo.class, result));
		voRes.setVoList(fftOrgVoList==null?new ArrayList<FFTOrgVo>():fftOrgVoList);
		return voRes;
	}
	
	
	/**
	 * 所属组织-数据权限下拉框(获取银行组织)
	 * @param userId 用户Id
	 * @param clientId 客户端Id
	 * @return FFTOrgListVoRes
	 */
	@Override
	public FFTOrgListVoRes getFFTOrgByUserIdPlatform(long userId,String clientId) throws TException {
		FFTOrgListVoRes voRes = new FFTOrgListVoRes();
		Result result = new Result();
		List<FFTOrgVo> fftOrgVoList = null;
		try{
			
			List<FFTOrg> fftOrgList = fftOrgLogic.findFFTOrgByUserIdPlatform(userId,clientId);
			fftOrgVoList =(List<FFTOrgVo>)BeanUtil.copyProperties(FFTOrgVo.class, fftOrgList);
		}catch (FroadServerException e) {
			LogCvt.error("所属组织权限列表getFFTOrgByUserIdPlatform失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("所属组织权限列表getFFTOrgByUserIdPlatform异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.finity_error.getCode());
			result.setResultDesc(ResultCode.finity_error.getMsg());
		}	
		voRes.setResultVo((ResultVo) BeanUtil.copyProperties(ResultVo.class, result));
		voRes.setVoList(fftOrgVoList==null?new ArrayList<FFTOrgVo>():fftOrgVoList);
		return voRes;
	}
	
	
}

