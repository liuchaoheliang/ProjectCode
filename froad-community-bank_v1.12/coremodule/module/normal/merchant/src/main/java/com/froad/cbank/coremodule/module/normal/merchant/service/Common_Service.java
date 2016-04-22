package com.froad.cbank.coremodule.module.normal.merchant.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ch.ethz.ssh2.Connection;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.image.ImgZipUtil;
import com.froad.cbank.coremodule.framework.common.util.scp.ScpConfig;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.merchant.enums.MerchantDisableStatusEnum;
import com.froad.cbank.coremodule.module.normal.merchant.enums.UserType;
import com.froad.cbank.coremodule.module.normal.merchant.exception.MerchantException;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Image_Info_Res;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.MerchantUser;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_BankOrg_Res;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Deliery_Corp_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Deliery_Corp_Res;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Verify_AccountNum_Req;
import com.froad.cbank.coremodule.module.normal.merchant.utils.Constants;
import com.froad.cbank.coremodule.module.normal.merchant.utils.EnumTypes;
import com.froad.cbank.coremodule.module.normal.merchant.utils.ImgUtil;
import com.froad.cbank.coremodule.module.normal.merchant.utils.TargetObjectFormat;
import com.froad.thrift.service.AreaService;
import com.froad.thrift.service.BankCardService;
import com.froad.thrift.service.DeliveryCorpService;
import com.froad.thrift.service.MerchantService;
import com.froad.thrift.service.MerchantUserService;
import com.froad.thrift.service.OrgService;
import com.froad.thrift.vo.AreaVo;
import com.froad.thrift.vo.DeliveryCorpVo;
import com.froad.thrift.vo.MerchantAccountVo;
import com.froad.thrift.vo.MerchantUserCheckVoRes;
import com.froad.thrift.vo.MerchantVo;
import com.froad.thrift.vo.OrgVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PlatType;
import com.froad.thrift.vo.ResultVo;

/**
 * 通用service
 * @author args
 * @date 21/03/2015
 */
@Service
public class Common_Service {
	@Resource
	MerchantService.Iface merchantService;
	@Resource
	BankCardService.Iface bankCardService;
	@Resource
	DeliveryCorpService.Iface deliveryCorpService;
	
	@Resource
	OrgService.Iface orgService;
	
	@Resource
	MerchantUserService.Iface merchantUserService;
	@Resource
	AreaService.Iface areaService;
	/**
	 * 图片上传
	 * @tilte imgUpload
	 * @author zxl
	 * @date 2015年4月8日 上午11:37:06
	 * @return
	 * @throws MerchantException
	 */
	public ArrayList<Image_Info_Res> imgUpload(MultipartFile[] fl) throws MerchantException{
		ArrayList<Image_Info_Res> al = new ArrayList<Image_Info_Res>();
		int count=0;
		for(MultipartFile f : fl){
			count++;
			Image_Info_Res img = ImgUtil.zipAndUpload(f);
			img.setIsDefault(count==1?true:false);
			al.add(img);
		}
		return al;	
	}
	
	/**
	 * 单个图片上传
	 * @tilte imgUploadSimple
	 * @author zxl
	 * @date 2015年4月13日 下午1:58:32
	 * @param fl
	 * @return
	 * @throws MerchantException
	 */
	public ArrayList<Image_Info_Res> imgUploadSimple(MultipartFile fl) throws MerchantException{
		ArrayList<Image_Info_Res> al = new ArrayList<Image_Info_Res>();
		Image_Info_Res img = ImgUtil.zipAndUpload(fl);
		img.setIsDefault(false);
		al.add(img);
		return al;
	}
	
	/**
	 * 物流列表
	 * @tilte getDeliveryCorp
	 * @author zxl
	 * @date 2015年4月8日 下午4:54:47
	 * @return
	 * @throws MerchantException
	 * @throws TException 
	 */
	public Map<String,Object> getDeliveryCorp(Query_Deliery_Corp_Req req) throws MerchantException, TException{
		Map<String,Object> reMap = new HashMap<String, Object>();
		DeliveryCorpVo reqvo=new DeliveryCorpVo();
		//reqvo.setClientId(req.getClientId()); //物流api接口变化
		reqvo.setIsEnable(true);
		List<DeliveryCorpVo> al = deliveryCorpService.getDeliveryCorp(reqvo);
		if(al!=null && al.size()>0){
			List<Query_Deliery_Corp_Res> listRes=new ArrayList<Query_Deliery_Corp_Res>();
			for(DeliveryCorpVo vo:al){
				Query_Deliery_Corp_Res res=new Query_Deliery_Corp_Res();
				TargetObjectFormat.copyProperties(vo, res);
				res.setIsEnable(vo.isIsEnable());
				listRes.add(res);
			}
			reMap.put("list", listRes);
		}else{
			throw new MerchantException(EnumTypes.empty);
		}
		return reMap;	
	}
	
	/**
	 * 银行机构列表
	 * @tilte getBankList
	 * @author zxl
	 * @date 2015年4月22日 下午4:04:24
	 * @param orgCode
	 * @param clientId
	 * @return
	 * @throws MerchantException
	 * @throws TException
	 */
	public Map<String,Object> getBankList(String orgCode,String clientId) throws MerchantException, TException{
		Map<String,Object> reMap = new HashMap<String, Object>();
		
		List<Query_BankOrg_Res> bankOrgList = new ArrayList<Query_BankOrg_Res>();
		List<OrgVo> orgList = new ArrayList<OrgVo>(); 
		if(StringUtils.isBlank(orgCode)){
			OrgVo orgVo = new OrgVo(); 
			orgVo.setClientId(clientId);
			orgVo.setOrgLevel("1");
			orgList = orgService.getOrg(orgVo);
		}else{
			orgList = orgService.getSubOrgs(clientId,orgCode);
		}
		for(OrgVo org : orgList){
			Query_BankOrg_Res bankOrg = new Query_BankOrg_Res();
			bankOrg.setOrgCode(org.getOrgCode());
			bankOrg.setOrgName(org.getOrgName());
			bankOrgList.add(bankOrg);
		}
		reMap.put("bankList", bankOrgList);
		
		return reMap;	
	}
		
	/**
	 * 检查登录用户
	 * @tilte verifyUser
	 * @author zxl
	 * @date 2015年5月11日 下午3:13:59
	 * @param request
	 * @param token
	 * @param id
	 * @return
	 * @throws TException
	 * @throws MerchantException 
	 */
	public MerchantUser verifyUser(HttpServletRequest request,String token,String id) throws TException, MerchantException{
		if(token == null || id == null){
			throw new MerchantException(EnumTypes.timeout);
		}
		long i = Long.parseLong(id);
		OriginVo vo = new OriginVo();
		vo.setOperatorId(Long.valueOf(id));
		vo.setOperatorIp((String)request.getAttribute(Constants.CLIENT_IP));
		vo.setPlatType((PlatType)request.getAttribute(Constants.PLAT_TYPE));
		MerchantUserCheckVoRes resp = merchantUserService.tokenCheck(vo,token, i);
		if(!EnumTypes.success.getCode().equals(resp.getResult().getResultCode())){
			throw new MerchantException(EnumTypes.timeout.getCode(),resp.getResult().getResultDesc());
		}
		
		MerchantUser res = new MerchantUser();
		TargetObjectFormat.copyProperties(resp.getMerchantUser(), res);
		if(UserType.admin.getCode().equals(resp.getMerchantUser().getMerchantRoleId()+"")){
			res.setIsSuperAdmin(true);
		}else{
			res.setIsSuperAdmin(false);
		}
		if("0".equals(resp.getMerchantUser().getOutletId())){
			res.setOutletName("总店");
		}
		if(resp.getMerchantUser().isMerchantIsEnable()){
			res.setMerchantDisableStatus(MerchantDisableStatusEnum.normal.getCode());
		}else{
			res.setMerchantDisableStatus(resp.getMerchantUser().getMerchantDisableStatus());
		}
        return res;
	}
	
	/**
	 * 商户状态检查
	 * @tilte checkMerchantStatus
	 * @author zxl
	 * @date 2015年5月11日 下午3:03:03
	 */
	public void checkMerchantStatus(String status) throws MerchantException{
		if(MerchantDisableStatusEnum.disable.getCode().equals(status)){
			throw new MerchantException(EnumTypes.fail.getCode(),"商户已禁用");
		}
		if(MerchantDisableStatusEnum.unregistered.getCode().equals(status)){
			throw new MerchantException(EnumTypes.fail.getCode(),"商户已解约");
		}
	}
	
	/**
	 * 图片格式检查
	 * @tilte ckeckImage
	 * @author zxl
	 * @date 2015年5月19日 下午2:23:29
	 */
	public void checkImage(MultipartFile[] file) throws MerchantException{
		
		for(MultipartFile f : file){
			checkImage(f);
		}
	}
	
	/**
	 * 图片格式检查
	 * @tilte ckeckImage
	 * @author zxl
	 * @date 2015年5月19日 下午2:26:49
	 * @param file
	 * @throws MerchantException
	 */
	public void checkImage(MultipartFile file) throws MerchantException{
		String fileName = file.getOriginalFilename();
		if(fileName.indexOf(".")<0){
			throw new MerchantException(EnumTypes.img_type_fail);
		}
		String det = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
		if(!Constants.IMAGE.contains(det)){
			throw new MerchantException(EnumTypes.img_type_fail);
		}
	}
	
	/**
	 * 根据区域id查询省市区
	 * 通用方法
	 * @param cityId
	 * @return
	 */
	public String queryByAreaId(String cityId) {
		try {
			AreaVo listvo = areaService.findAreaById(Long.valueOf(cityId));
			if (listvo != null) {
				// 区域id、name
				String[] pathid = listvo.getTreePath().split(",");
				String[] name = listvo.getTreePathName().split(",");
				StringBuffer sb = new StringBuffer();
				if (pathid.length > 0 && name.length > 0) {
					for (int i = 0; i < name.length; i++) {
						sb.append(name[i]);
					}
				}
				return sb.toString().substring(0, sb.toString().length());
			}
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
		}
		return null;
	}
	
	
	/**
	 * 收款帐号验证
	 * @param car
	 * @return
	 * @throws MerchantException
	 * @throws Exception
	 */
	public Map<String,Object> verifyAccountNum(Verify_AccountNum_Req car)throws MerchantException, Exception{
		Map<String, Object> resMap = new HashMap<String, Object>();
		ResultVo res=new ResultVo();
		try {
			if(StringUtils.isEmpty(car.getCertificateNo()))
				 res = bankCardService.bankCardAccountCheck(car.getClientId(), car.getAcctName(), car.getAcctNumber(), null, null);
			else
				res = bankCardService.bankCardAccountCheck(car.getClientId(), car.getAcctName(), car.getAcctNumber(), car.getCertificateType(), car.getCertificateNo());
			LogCvt.info("收款帐号验证返回结果：----》"+JSON.toJSONString(res));
			if("00".equals(res.getResultCode())){
				resMap.put("code", EnumTypes.success.getCode());
				resMap.put("message", res.getResultDesc());
			}else{
				resMap.put("code", "9999");
				resMap.put("message", "银行卡号校验不通过，请检查输入是否正确");

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			resMap.put("code", "9999");
			resMap.put("message", "收款帐号验证超时");
			LogCvt.error("收款帐号验证异常-----》"+e);
		}
		return resMap;

	}
	
	/**
	 * 获取收款帐号的公共方法
	 * 说明   description of the class
	 * 创建日期  2015年11月23日  下午2:12:00
	 * 作者  artPing
	 * 参数  @param clientId
	 * 参数  @param merchantId
	 * 参数  @param outleId
	 * 参数  @param acctNumber
	 * 参数  @param acctName
	 * 参数  @return
	 * 参数  @throws TException
	 */
	public MerchantAccountVo setMerchantAccountVo(String clientId,String merchantId,String outleId,String acctNumber,String acctName) throws TException{
		MerchantVo vo=merchantService.getMerchantByMerchantId(merchantId);
		MerchantAccountVo merchantAccountVo = new MerchantAccountVo();
		merchantAccountVo.setClientId(clientId);
		merchantAccountVo.setMerchantId(merchantId);
		merchantAccountVo.setOutletId(outleId);
		merchantAccountVo.setOpeningBank(vo.getOrgCode());
		if(StringUtils.isNotEmpty(acctName))merchantAccountVo.setAcctName(acctName);
		if(StringUtils.isNotEmpty(acctNumber))merchantAccountVo.setAcctNumber(acctNumber);
		merchantAccountVo.setAcctType("1");
		merchantAccountVo.setIsDelete(false);
		return merchantAccountVo;
	}
	
	/**
	 * 链接scp服务
	 * @param cfg
	 * @return
	 * @throws Exception
	*/
	public Connection connectServer(ScpConfig cfg) throws Exception{
		boolean isAuthed = false;
		Connection scpConnection = null;
		if(cfg.getPort()==0){
			scpConnection = new Connection(cfg.getIp());
		}else{
			scpConnection = new Connection(cfg.getIp(), cfg.getPort());
		}
		scpConnection.connect();
		isAuthed = scpConnection.authenticateWithPassword(cfg.getUserName(), cfg.getPassWord());
		if(!isAuthed){
			throw new Exception("connect auth failed!");
		}
		return scpConnection;
	}

		
	/**
	* 下载二维码图片
	* @param path
	* @param res
	* @throws IOException 
	* @throws NumberFormatException 
	*/
	public void downFile(String path,HttpServletResponse res) throws NumberFormatException, IOException{	
		ImgZipUtil imgCom = new ImgZipUtil(path);
		// 二维码规格放大
		imgCom.resizeFix(Integer.parseInt(Constants.get("merchant.largeW")),Integer.parseInt(Constants.get("merchant.largeH")),path);
		try {
			// path是指欲下载的文件的路径。
			File file = new File(path);
			String filename = file.getName();
			res.setContentType("application/x-download;UTF-8");
			res.addHeader("Content-Disposition","attachment;filename=" + filename);	
				
			FileInputStream fis = new FileInputStream(file);
			OutputStream os = res.getOutputStream();
			res.addHeader("Content-Length", file.length() + "");
			byte[] bytes=new byte[1024*1024];	
			int len;				
			while((len = fis.read( bytes , 0, 1024*1024)) != -1){
				os.write(bytes, 0, len); 
			}		
			os.close();
			fis.close();
		}catch(Exception e) {
			LogCvt.error("图片下载异常： "+e.getMessage(), e);
		}
	}
		
	public boolean deleteDirectory(String fileName) {  
		File file = new File(fileName);  
			if (file.exists()) {  
			 file.delete();  
				LogCvt.info("删除单个文件： "+ fileName + "成功！");
				return true;  
			 } else {  
				LogCvt.info("删除单个文件： "+ fileName + "失败！");
				return false;  
			 }    
	}

}
