package com.froad.cbank.coremodule.normal.boss.controller.vip;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.common.valid.ValidException;
import com.froad.cbank.coremodule.normal.boss.annotation.Auth;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.BossUser;
import com.froad.cbank.coremodule.normal.boss.pojo.vip.VipImpListReq;
import com.froad.cbank.coremodule.normal.boss.pojo.vip.VipImpReq;
import com.froad.cbank.coremodule.normal.boss.pojo.vip.VipImpRevFailReq;
import com.froad.cbank.coremodule.normal.boss.pojo.vip.VipImpStatReq;
import com.froad.cbank.coremodule.normal.boss.support.LoginBossSupport;
import com.froad.cbank.coremodule.normal.boss.support.vip.VipImpSupport;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.ErrorEnums;
import com.froad.cbank.coremodule.normal.boss.utils.FileUtil;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;

/**
 * vip导入
 * @ClassName VipImpController
 * @author zxl
 * @date 2015年6月9日 下午3:22:11
 */
@Controller
@RequestMapping(value = "/vipimp")
public class VipImpController {

	@Resource
	LoginBossSupport loginBossSupport;
	
	@Resource
	private VipImpSupport vipImpSupport;
	
	/**
	 * @desc 获取导入批次列表
	 * @path /vipimp/list
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年6月10日 下午1:53:55
	 * @param 
	 * @return 
	 */
	@Auth(keys={"boss_vipimp_menu","boss_vipimp_audit_menu"})
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void getImportList(ModelMap model, VipImpListReq pojo) {
		try {
			if(LogCvt.isDebugEnabled()){
				LogCvt.debug("VIP导入批次列表请求参数：" + JSON.toJSONString(pojo));
			}
			model.clear();
			if(pojo.getBeginTime() != null && pojo.getEndTime() != null && pojo.getBeginTime() > pojo.getEndTime()) {
				throw new BossException("开始时间不可大于结束时间");
			}
			model.putAll(vipImpSupport.getImportList(pojo));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}

	/**
	 * 上传CSV文件
	 * @path /vipimp/upload
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年7月1日 上午9:58:51
	 * @param model
	 * @param file
	 * @throws IOException 
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public void csvUpload(HttpServletRequest req, HttpServletResponse res, @RequestParam("file") MultipartFile file) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String json = "";
		try {
			loginBossSupport.tokenCheck(req, req.getParameter("token"), Long.parseLong(req.getParameter("userId")));
			//获取操作用户
			BossUser boss = (BossUser) req.getAttribute(Constants.BOSS_USER);
			res.setCharacterEncoding("UTF-8");
			res.setContentType("text/html");
			if(file.getOriginalFilename().indexOf(".csv") == -1) {
				throw new BossException("文件格式有误，请传入csv文件"); 
			}
			InputStream is = file.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			int count = 0;
			String tmp = null;
			while((tmp = br.readLine()) != null) {
				if(StringUtil.isNotBlank(tmp)) {
					count++;
				}
			}
			if(count == 0) {
				throw new BossException("上传会员数为空");
			}
			if(count > 10000) {
				throw new BossException("单次批量导入会员数限10000条以内");
			}
			map = vipImpSupport.csvUpload(file, boss.getUsername());
			json = JSONArray.toJSONString(map);
			res.getWriter().write(json);
			LogCvt.info("csvUpload-RESPONE：" + json);
		} catch (BossException e) {
			map.put("code", e.getCode());
			map.put("message", e.getMsg());
			res.setStatus(608);
			json = JSON.toJSONString(map);
			res.getWriter().write(json);
			LogCvt.info("csvUpload-RESPONE：" + json);
			LogCvt.error(e.getMessage(), e);
		} catch (Exception e) {
			map.put("code", ErrorEnums.thrift_err.getCode());
			map.put("message", ErrorEnums.thrift_err.getMsg());
			res.setStatus(608);
			json = JSON.toJSONString(map);
			res.getWriter().write(json);
			LogCvt.info("csvUpload-RESPONE：" + json);
			LogCvt.error(e.getMessage(), e);
		}
	}
	
	/**
	 * @desc 导入批次信息
	 * @path /vipimp/imp
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年6月10日 下午1:52:40
	 * @param 
	 * @return 
	 */
	@Auth(keys={"boss_vipimp_import"})
	@RequestMapping(value = "/imp", method = RequestMethod.POST)
	public void importMembers(ModelMap model, HttpServletRequest req, @RequestBody VipImpReq pojo) {
		try {
			//获取操作用户
			BossUser boss = (BossUser) req.getAttribute(Constants.BOSS_USER);
			pojo.setOperator(boss.getUsername());
			
			if(LogCvt.isDebugEnabled()){
				LogCvt.debug("VIP批量导入请求参数：" + JSON.toJSONString(pojo));
			}
			model.clear();
			
			//下载文件
			FileUtil.downloadFile(Constants.getScpFileUploadPath(), pojo.getFileName(), Constants.getImgLocalUrl(), Constants.SCPCONFIG);
			
			String filePath = Constants.getImgLocalUrl() + File.separator + pojo.getFileName();
			File file = new File(filePath);
			if(!file.exists()) {
				throw new BossException("文件不存在，请先上传文件！");
			}
			model.putAll(vipImpSupport.importMembers(pojo, file));
			
			File file1 = new File(filePath); 
			if(file1.exists()) {
				file1.delete();
			}
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * @desc 导入批次提交审核
	 * @path /vipimp/sub
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年6月10日 下午1:54:29
	 * @param 
	 * @return
	 */
	@Auth(keys={"boss_vipimp_audit"})
	@RequestMapping(value = "/sub", method = RequestMethod.PUT)
	public void reviewImportSubmit(ModelMap model, @RequestBody VipImpStatReq pojo) {
		try {
			if(LogCvt.isDebugEnabled()){
				LogCvt.debug("VIP导入批次提交审核请求参数：" + JSON.toJSONString(pojo));
			}
			model.clear();
			model.putAll(vipImpSupport.reviewImportSubmit(pojo));
		} catch (ValidException e) {
			new RespError(model, e.getMessage());
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 审核导入批次通过
	 * @path /vipimp/pass
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年6月29日 下午7:53:57
	 * @param model
	 */
	@Auth(keys={"boss_vipimp_audit_audit"})
	@RequestMapping(value = "/pass", method = RequestMethod.PUT)
	public void reviewImportPass(ModelMap model, HttpServletRequest req, @RequestBody VipImpStatReq pojo) {
		try {
			//获取操作用户
			BossUser boss = (BossUser) req.getAttribute(Constants.BOSS_USER);
			if(LogCvt.isDebugEnabled()){
				LogCvt.debug("审核VIP导入批次通过请求参数：" + JSON.toJSONString(pojo));
			}
			model.clear();
			model.putAll(vipImpSupport.reviewImportPass(pojo, boss.getUsername()));
		} catch (ValidException e) {
			new RespError(model, e.getMessage());
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 审核导入批次不通过
	 * @path /vipimp/fail
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年6月29日 下午7:56:08
	 */
	@Auth(keys={"boss_vipimp_audit_audit"})
	@RequestMapping(value = "/fail", method = RequestMethod.PUT)
	public void reviewImportFail(ModelMap model, HttpServletRequest req, @RequestBody VipImpRevFailReq pojo) {
		try {
			//获取操作用户
			BossUser boss = (BossUser) req.getAttribute(Constants.BOSS_USER);
			if(LogCvt.isDebugEnabled()){
				LogCvt.debug("审核VIP导入批次不通过请求参数：" + JSON.toJSONString(pojo));
			}
			model.clear();
			model.putAll(vipImpSupport.reviewImportFail(pojo, boss.getUsername()));
		} catch (ValidException e) {
			new RespError(model, e.getMessage());
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 导入批次废弃
	 * @path /vipimp/dis
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年6月29日 下午7:59:36
	 */
	@Auth(keys={"boss_vipimp_disable"})
	@RequestMapping(value = "/dis", method = RequestMethod.PUT)
	public void reviewImportDiscard(ModelMap model, @RequestBody VipImpStatReq pojo) {
		try {
			if(LogCvt.isDebugEnabled()){
				LogCvt.debug("VIP导入批次废弃请求参数：" + JSON.toJSONString(pojo));
			}
			model.clear();
			model.putAll(vipImpSupport.reviewImportDiscard(pojo));
		} catch (ValidException e) {
			new RespError(model, e.getMessage());
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 导入批次执行
	 * @path /vipimp/exec
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年6月29日 下午8:05:48
	 */
	@Auth(keys={"boss_vipimp_do"})
	@RequestMapping(value = "/exec", method = RequestMethod.PUT)
	public void reviewImportExecute(ModelMap model, HttpServletRequest req, @RequestBody VipImpStatReq pojo) {
		try {
			//获取操作用户
			BossUser boss = (BossUser) req.getAttribute(Constants.BOSS_USER);
			if(LogCvt.isDebugEnabled()){
				LogCvt.debug("VIP导入批次执行请求参数：" + JSON.toJSONString(pojo));
			}
			model.clear();
			model.putAll(vipImpSupport.reviewImportExecute(pojo, boss.getUsername()));
		} catch (ValidException e) {
			new RespError(model, e.getMessage());
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 删除导入失败结果文件
	 * @path /vipimp/delfail
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年7月2日 下午9:37:53
	 */
	@RequestMapping(value = "/delfail", method = RequestMethod.DELETE)
	public void deleteFailFile(String fileName) {
		try {
			LogCvt.info("删除导入失败结果文件");
			if(StringUtils.isNotBlank(fileName)) {
				String filePath = Constants.getImgLocalUrl() + File.separator + fileName;
				File file = new File(filePath);
				if(file.exists()) {
					file.delete();
					LogCvt.info("文件删除成功");
				} else {
					LogCvt.info("文件‘" + filePath + "’不存在");
				}
			}
		} catch (Exception e) {
			LogCvt.info("文件删除失败");
		}
	}
}
