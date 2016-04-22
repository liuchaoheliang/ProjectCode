package com.froad.cbank.coremodule.normal.boss.support.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.encrypt.MD5Encrypt;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.BossUser;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;
import com.froad.cbank.coremodule.normal.boss.pojo.system.AddUserVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.system.OrgVoRes;
import com.froad.cbank.coremodule.normal.boss.pojo.system.UserListVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.system.UserListVoRes;
import com.froad.cbank.coremodule.normal.boss.pojo.system.UserOrgListRes;
import com.froad.cbank.coremodule.normal.boss.pojo.system.UserRolesRes;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.thrift.service.BossFFTUserService;
import com.froad.thrift.service.FFTOrgService;
import com.froad.thrift.service.FFTUserOrgService;
import com.froad.thrift.service.FFTUserRoleService;
import com.froad.thrift.service.FinityRoleService;
import com.froad.thrift.vo.BossUserVo;
import com.froad.thrift.vo.CommonAddVoRes;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.bossfftuser.BossFFTUserBaseVoRes;
import com.froad.thrift.vo.bossfftuser.BossFFTUserDetailVo;
import com.froad.thrift.vo.bossfftuser.BossFFTUserPageVoReq;
import com.froad.thrift.vo.bossfftuser.BossFFTUserPageVoRes;
import com.froad.thrift.vo.bossfftuser.BossFFTUserVo;
import com.froad.thrift.vo.finity.FinityRoleListVoRes;
import com.froad.thrift.vo.finity.FinityRoleVo;
import com.froad.thrift.vo.orgRoleManager.FFTOrgDetailVo;
import com.froad.thrift.vo.orgRoleManager.FFTOrgNameListVoRes;
import com.froad.thrift.vo.orgRoleManager.FFTUserOrgListVoRes;
import com.froad.thrift.vo.orgRoleManager.FFTUserRoleIdListVoRes;
import com.froad.thrift.vo.orgRoleManager.FFTUserRoleListVoRes;
import com.froad.thrift.vo.orgRoleManager.FFTUserRoleVo;

/**
 * 
 * @ClassName: UserSupport
 * @author chenzhangwei@f-road.com.cn
 * @createTime 2016年1月6日 下午5:26:59 
 * @desc <p>用户support</p>
 */
@Service
public class UserSupport {
	
	@Resource
	BossFFTUserService.Iface bossFFTUserService;
	@Resource
	FFTUserRoleService.Iface fftUserRoleService;
	@Resource
	FinityRoleService.Iface finityRoleService;
	@Resource
	FFTOrgService.Iface fftOrgService;
	@Resource
	FFTUserOrgService.Iface fftUserOrgService;
	
	/**
	 * 
	 * <p>Title:分页查询用户列表 </p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2016年1月6日 下午5:42:03 
	 * @param req
	 * @return
	 * @throws TException 
	 * @throws BossException 
	 */
	public HashMap<String,Object> list(UserListVoReq req) throws TException, BossException{
		HashMap<String,Object> map =new HashMap<String,Object>();
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(req.getPageNumber());
		pageVo.setPageSize(req.getPageSize());
		pageVo.setFirstRecordTime(req.getFirstRecordTime());
		pageVo.setLastPageNumber(req.getLastPageNumber());
		pageVo.setLastRecordTime(req.getLastRecordTime());
		
		//构建数据传输对象
		BossFFTUserPageVoReq temp=new BossFFTUserPageVoReq();
		if(StringUtil.isNotBlank(req.getName())){
			temp.setName(req.getName());
		}
		if(StringUtil.isNotBlank(req.getUsername())){
			temp.setUsername(req.getUsername());
		}
		temp.setOrgId(req.getOrgId());
		if("2".equals(req.getStatus())){
			temp.setStatus("");
		}else{
			temp.setStatus(req.getStatus());
		}
		
		
		//执行查询
		BossFFTUserPageVoRes res=bossFFTUserService.getFFTUserByPage(pageVo, temp, req.getType());
		
		if(Constants.RESULT_SUCCESS.equals(res.getResultVo().getResultCode())){
			List<UserListVoRes> list=new ArrayList<UserListVoRes>();
			for (BossFFTUserDetailVo vo : res.getVoList()) {
				UserListVoRes userListVoRes=new UserListVoRes();
				BeanUtils.copyProperties(userListVoRes, vo);
				list.add(userListVoRes);
			}
			Page page = new Page();
			BeanUtils.copyProperties(page, res.getPageVo());
			map.put("page", page);
			map.put("list", list);
		}else{
			throw new BossException(res.getResultVo().getResultCode(), res.getResultVo().getResultDesc());
		}
		return map;
	}
	
	
	/**
	 * 
	 * <p>Title:用户删除 </p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2016年1月6日 下午7:20:45 
	 * @param id
	 * @param request
	 * @return
	 * @throws TException 
	 * @throws NumberFormatException 
	 * @throws BossException 
	 */
	public HashMap<String,Object> delete(String id,HttpServletRequest request) throws NumberFormatException, TException, BossException{
		HashMap<String,Object> map=new HashMap<String,Object>();
		OriginVo originVo=(OriginVo) request.getAttribute(Constants.ORIGIN);
		ResultVo res=bossFFTUserService.deleteFFTUser(originVo, Long.parseLong(id));
		if(!Constants.RESULT_SUCCESS.equals(res.getResultCode())){
			throw new BossException(res.getResultCode(), res.getResultDesc());
		}else{
				map.put("code", "0000");
				map.put("message", "用户删除成功!");
			}
		return map;
	}
	
	/**
	 * 
	 * <p>Title:用户详情查询 </p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2016年1月11日 上午11:36:10 
	 * @param id
	 * @return
	 * @throws TException 
	 * @throws BossException 
	 */
	public HashMap<String,Object> detail(Long id) throws TException, BossException{
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		//查询用户详情
		BossFFTUserBaseVoRes res=bossFFTUserService.findFFTUserById(id);
		if(Constants.RESULT_SUCCESS.equals(res.getResultVo().getResultCode())){
			UserListVoRes detail =new UserListVoRes();
			BeanUtils.copyProperties(detail, res.getBossFFTUserVo());
			//查询用户关联的角色id
			FFTUserRoleIdListVoRes temp=fftUserRoleService.getFFTUserRoleId(id);
			
			if(Constants.RESULT_SUCCESS.equals(temp.getResultVo().getResultCode())){
				List<Long> roleIds = new ArrayList<Long>();
				roleIds =temp.getRoleIdList();
				//List<FFTUserRoleVo> voList = temp.getVoList();
				/*if(!ArrayUtil.empty(voList)){
					for (FFTUserRoleVo fftUserRoleVo : voList) {
						roleIds.add(fftUserRoleVo.getRoleId());
					}
				}*/
				map.put("roleIds",roleIds);
			}else{
				throw new BossException(temp.getResultVo().getResultCode(), temp.getResultVo().getResultDesc());
			}
			//查询用户关联的组织id
			FFTUserOrgListVoRes orgs=fftUserOrgService.getFFTUserOrg(id);
			if(Constants.RESULT_SUCCESS.equals(orgs.getResultVo().getResultCode())){
			}else{
				throw new BossException(orgs.getResultVo().getResultCode(), orgs.getResultVo().getResultDesc());
			}
			List<String> orgIds = orgs.getOrgIds();
			map.put("detail", detail);
			map.put("orgIds",orgIds);
			//查询用户拥有的组织
			map.put("orgs",findAllOrg(orgIds) );
		}else{
			map.put("code", "9999");
			map.put("message", "用户详情查询失败");
		}
			
		return map;
		
	}
	
	
	/**
	 * 
	 * <p>Title: 根据组织机构id集合查询用户组织信息</p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2016年1月12日 下午2:08:25 
	 * @param orgIds
	 * @return
	 * @throws TException 
	 * @throws BossException 
	 */
	public List<OrgVoRes> findAllOrg(List<String> orgIds) throws TException, BossException{
		List<OrgVoRes> userOrgs= new ArrayList<OrgVoRes>();
		FFTOrgNameListVoRes temp = fftOrgService.getFFTOrgByOrgIds(orgIds);
		if(Constants.RESULT_SUCCESS.equals(temp.getResultVo().getResultCode())){
			for (FFTOrgDetailVo  vo : temp.getVoList()) {
				OrgVoRes org = new OrgVoRes();
				BeanUtils.copyProperties(org, vo);
				userOrgs.add(org);
			}
		}else{
			throw new BossException(temp.getResultVo().getResultCode(), temp.getResultVo().getResultDesc());
		}
		return userOrgs;
	}
	/**
	 * 
	 * <p>Title:新增用户 </p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2016年1月6日 下午7:54:27 
	 * @param request
	 * @param req
	 * @return
	 * @throws TException 
	 * @throws BossException 
	 */
	public HashMap<String,Object> add(HttpServletRequest request,AddUserVoReq req) throws TException, BossException{
		HashMap<String,Object> map = new HashMap<String,Object>();
		OriginVo originVo=(OriginVo) request.getAttribute(Constants.ORIGIN);
		BossFFTUserVo temp = new BossFFTUserVo();
		BeanUtils.copyProperties(temp, req);
		CommonAddVoRes res=bossFFTUserService.addFFTUser(originVo, temp);
		if(!Constants.RESULT_SUCCESS.equals(res.getResult().getResultCode())){
			throw new BossException(res.getResult().getResultCode(), res.getResult().getResultDesc());
		}else{
			Long userId=res.getId();
			CommonAddVoRes commonAddVoRes=fftUserRoleService.addFFTUserRole(originVo, userId, req.getOrgIds(), req.getRoleIds());
			if(!Constants.RESULT_SUCCESS.equals(commonAddVoRes.result.getResultCode())){
				//如果新增失败，按照id删除原来insert进cb_user表里的这条数据
				bossFFTUserService.removeFFTUser(originVo,userId );
				throw new BossException(commonAddVoRes.result.getResultCode(), commonAddVoRes.result.getResultDesc());
			}else{
				map.put("code", "0000");
				map.put("message", "用户新增成功");
			}
		}
		return map;
	}
	
	/**
	 * 
	 * <p>Title:修改用户 </p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2016年1月7日 下午4:25:25 
	 * @param request
	 * @param req
	 * @return
	 * @throws TException
	 * @throws BossException
	 */
	public HashMap<String,Object> update(HttpServletRequest request,AddUserVoReq req) throws TException, BossException{
		HashMap<String,Object> map = new HashMap<String,Object>();
		OriginVo originVo=(OriginVo) request.getAttribute(Constants.ORIGIN);
		BossFFTUserVo temp = new BossFFTUserVo();
		BeanUtils.copyProperties(temp, req);
		temp.setId(Long.parseLong(req.getUserId()));
		ResultVo res=bossFFTUserService.updateFFTUser(originVo, temp);
		if(!Constants.RESULT_SUCCESS.equals(res.getResultCode())){
			throw new BossException(res.getResultCode(), res.getResultDesc());
		}else{
			res=fftUserRoleService.updateFFTUserRole(originVo, Long.parseLong(req.getUserId()), req.getOrgIds(), req.getRoleIds());
			if(!Constants.RESULT_SUCCESS.equals(res.getResultCode())){
				throw new BossException(res.getResultCode(), res.getResultDesc());
			}else{
				map.put("code", "0000");
				map.put("message", "用户修改成功");
			}
		}
		return map;
	}
	
	/**
	 * 
	 * <p>Title: 用户重置密码</p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2016年1月11日 上午11:17:51 
	 * @param userId
	 * @param request
	 * @return
	 * @throws TException 
	 * @throws BossException 
	 */
	public HashMap<String,Object> resetPwd(Long userId,HttpServletRequest request) throws TException, BossException{
		HashMap<String,Object> map = new HashMap<String,Object>();
		OriginVo originVo=(OriginVo) request.getAttribute(Constants.ORIGIN);
		BossFFTUserVo temp = new BossFFTUserVo();
		temp.setId(userId);
		temp.setPassword(MD5Encrypt.MD5("1q2w3e"));
		temp.setIsReset("1");
		ResultVo res=bossFFTUserService.updateFFTUser(originVo, temp);
		if(!Constants.RESULT_SUCCESS.equals(res.getResultCode())){
			throw new BossException("9999","密码重置失败");
		}else{
			map.put("code", "0000");
			map.put("message", "重置密码成功！");
		}
		return map;
	}
	
	/**
	 * 
	 * <p>Title:根据用户查询角色列表 </p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2016年1月7日 上午10:51:29 
	 * @param userId
	 * @return
	 * @throws TException 
	 * @throws BossException 
	 */
	public HashMap<String,Object> findRolesByUser(Long userId) throws TException, BossException{
		HashMap<String,Object> map = new HashMap<String,Object>();
		FinityRoleVo req = new FinityRoleVo();
		req.setId(userId);
		//执行查询
		String platform = "boss";
		 com.froad.thrift.vo.finity.FinityRoleListVoRes  temp = finityRoleService.getFinityRoleByUserId(userId,platform);
		 List<UserRolesRes> res = new ArrayList<UserRolesRes>();
		 if(Constants.RESULT_SUCCESS.equals(temp.getResultVo().getResultCode())){
			 List<FinityRoleVo> list =temp.getVoList();
			 for (FinityRoleVo finityRoleVo : list) {
					UserRolesRes role = new UserRolesRes();
					BeanUtils.copyProperties(role, finityRoleVo);
					res.add(role);
				}
			 map.put("roles", res);
			 map.put("code", temp.getResultVo().getResultCode());
			 map.put("message", temp.getResultVo().getResultDesc());
		 }else{
			 throw new BossException(temp.getResultVo().getResultCode(), temp.getResultVo().getResultDesc());
		 }
		return map;
	}
	
	
	/**
	 * 
	 * <p>Title:根据用户id查询用户角色列表 </p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2016年1月11日 下午1:48:35 
	 * @param roleId
	 * @return
	 * @throws TException 
	 * @throws BossException 
	 */
	public HashMap<String,Object> findRolesById(Long loginUserId,Long userId) throws TException, BossException{
		HashMap<String,Object> map = new HashMap<String,Object>();
		FinityRoleVo req = new FinityRoleVo();
		req.setId(userId);
		//执行查询
		String platform = "boss";
		//查询当前登录人拥有的角色
		 com.froad.thrift.vo.finity.FinityRoleListVoRes  temp = finityRoleService.getFinityRoleByUserId(loginUserId,platform);
		 //查询点击用户的角色
		 FinityRoleListVoRes userRoles = finityRoleService.getFinityRoleByUserIdLogin(userId);
		 //查询用户的组织角色关系
		 FFTUserRoleListVoRes fftUserRoleRes = fftUserRoleService.getFFTUserRole(userId);
		 //得到用户的组织角色关系
		 List<FFTUserRoleVo> fftUserRoleList = fftUserRoleRes.getVoList();
		 List<UserRolesRes> res = new ArrayList<UserRolesRes>();
		 List<UserRolesRes> res1 = new ArrayList<UserRolesRes>();
		 
		 if(Constants.RESULT_SUCCESS.equals(temp.getResultVo().getResultCode())){
			 List<FinityRoleVo> list =temp.getVoList();
			 for (FinityRoleVo finityRoleVo : list) {
					UserRolesRes role = new UserRolesRes();
					BeanUtils.copyProperties(role, finityRoleVo);
					res.add(role);
				}
			 //点击用户时返回的角色列表
			 List<FinityRoleVo> voList =userRoles.getVoList();
			for (FinityRoleVo finityRoleVo : voList) {
				UserRolesRes role = new UserRolesRes();
				BeanUtils.copyProperties(role, finityRoleVo);
				res1.add(role);
			}
			Set<UserRolesRes> set =new  HashSet<UserRolesRes>(res);
			
			for (UserRolesRes roleVo : res1) {
				set.add(roleVo);
			}
			//循环设置用户组织关系
			for (UserRolesRes userRolesRes : set) {
				for (FFTUserRoleVo roleVo : fftUserRoleList) {
					if(roleVo.getRoleId()==userRolesRes.getId()){
						userRolesRes.setSource(roleVo.getSource());
					}
				}
			}
			 map.put("roles", set);
			 map.put("code", temp.getResultVo().getResultCode());
			 map.put("message", temp.getResultVo().getResultDesc());
		 }else{
			 throw new BossException(temp.getResultVo().getResultCode(), temp.getResultVo().getResultDesc());
		 }
		return map;
	}
	
	/**
	 * 
	 * <p>Title:根据组织机构id查询所有组织机构</p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2016年1月7日 下午2:55:18 
	 * @param orgIds
	 * @return
	 * @throws TException 
	 * @throws BossException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public HashMap<String,Object> findOrgsByUser(List<String> orgIds) throws TException, BossException{
		HashMap map = new HashMap<String,Object>();
		FFTOrgNameListVoRes fFTOrgListVoRes = fftOrgService.getFFTOrgByOrgIds(orgIds);
		if(Constants.RESULT_SUCCESS.equals(fFTOrgListVoRes.getResultVo().getResultCode())){
			List<UserOrgListRes> list =new ArrayList<UserOrgListRes>();
			List<FFTOrgDetailVo> res = fFTOrgListVoRes.getVoList();
			for (FFTOrgDetailVo fftOrgVo : res) {
				UserOrgListRes orgs = new UserOrgListRes();
				BeanUtils.copyProperties(orgs, fftOrgVo);
				list.add(orgs);
			}
			map.put("list", list);
		}else{
			throw new BossException(fFTOrgListVoRes.getResultVo().getResultCode(), fFTOrgListVoRes.getResultVo().getResultDesc());
		}
		return map;
	}
	
	/**
	 * 
	 * <p>Title: 修改密码</p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2015年11月25日 下午5:13:42 
	 * @param id
	 * @param pwd
	 * @param request
	 * @return
	 * @throws TException
	 * @throws BossException
	 */
	public HashMap<String, Object> updatePwd(BossUser user,String pwd,HttpServletRequest request,String oldpwd) throws TException, BossException {
		HashMap<String, Object> respMap = new HashMap<String, Object>();
		BossUserVo vo=new BossUserVo();
		vo.setUsername(user.getUsername());
		 //得到当前用户
		 BossFFTUserBaseVoRes res=bossFFTUserService.findFFTUserById(user.getId());
		 String oldPwd="";
		 if(res!=null){
				 oldPwd=res.getBossFFTUserVo().getPassword();
		 }
		LogCvt.info("从系统中拿出的旧密码:  " + JSON.toJSONString(oldPwd));
		if(!oldpwd.equals(oldPwd)){
			throw new BossException("9999", "原密码校验失败");
		}
		OriginVo originVo=(OriginVo) request.getAttribute(Constants.ORIGIN);
		BossFFTUserVo temp = new BossFFTUserVo();
		temp.setId(user.getId());
		temp.setPassword(pwd);
		LogCvt.info("用户修改密码条件: " + JSON.toJSONString(temp));
		ResultVo resp=bossFFTUserService.updateFFTUser(originVo, temp);
		if(StringUtil.equals(resp.getResultCode(), Constants.RESULT_SUCCESS)) {
			respMap.put("code", Constants.RESULT_SUCCESS);
			respMap.put("message", resp.getResultDesc());
		}else{
			throw new BossException(resp.getResultCode(), resp.getResultDesc());
		}
		return respMap;
	}
	
}
