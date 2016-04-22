package com.froad.cbank.coremodule.normal.boss.support.vip;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.froad.cbank.coremodule.framework.common.util.CalendarUtil;
import com.froad.cbank.coremodule.framework.common.util.scp.ScpUtil;
import com.froad.cbank.coremodule.framework.common.util.type.ArrayUtil;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;
import com.froad.cbank.coremodule.normal.boss.pojo.vip.VipImpListReq;
import com.froad.cbank.coremodule.normal.boss.pojo.vip.VipImpReq;
import com.froad.cbank.coremodule.normal.boss.pojo.vip.VipImpRes;
import com.froad.cbank.coremodule.normal.boss.pojo.vip.VipImpRevFailReq;
import com.froad.cbank.coremodule.normal.boss.pojo.vip.VipImpStatReq;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.DateUtil;
import com.froad.cbank.coremodule.normal.boss.utils.FileUtil;
import com.froad.cbank.coremodule.normal.boss.utils.UnicodeReader;
import com.pay.pe.helper.ErrorCodeType;
import com.pay.user.dto.Result;
import com.pay.user.dto.VIPSpecDto;
import com.pay.user.dto.VipBatchDto;
import com.pay.user.helper.BankOrg;
import com.pay.user.service.VIPSpecService;

/**
 * @author 陆万全 luwanquan@f-road.com.cn
 * @date 创建时间：2015年6月10日 上午10:29:14
 * @version 1.0
 * @desc VIP导入支持类
 */
@Service
public class VipImpSupport {
	
	@Resource
	private VIPSpecService vIPSpecService;

	/**
	 * 获取VIP导入批次列表
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年6月29日 下午2:15:14
	 * @param pojo
	 * @return map
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> getImportList(VipImpListReq pojo) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<VipImpRes> vipBatchList = new ArrayList<VipImpRes>();
		VipImpRes temp = new VipImpRes();
		Page page = new Page();
		//封装请求
		Date beginTime = pojo.getBeginTime() == null ? null : DateUtil.longToDate(pojo.getBeginTime());
		Date endTime = pojo.getEndTime() == null ? null : DateUtil.longToDate(pojo.getEndTime());
		BankOrg bankOrg = StringUtils.isNotBlank(pojo.getBankOrg()) ? BankOrg.getInstanceByValue(pojo.getBankOrg()) : null;
		//调用VIP用户系统
		Result resp = vIPSpecService.queryBatch(beginTime, endTime, pojo.getKeyword(), bankOrg, pojo.getPageSize(), pojo.getPageNumber(), pojo.getPendingSort());
		
		if(resp.getResult() && resp.getData() != null) {
			//转为用户系统的Page实例
			com.pay.user.dto.Page<?> result = (com.pay.user.dto.Page<?>) resp.getData();
//			BeanUtils.copyProperties(page, result);//与会员系统分页字段命名不对应，无法copy属性，只能手工赋值
			page.setPageNumber(result.getPageNo());
			page.setPageSize(result.getPageSize());
			page.setTotalCount(result.getTotalRecord());
			page.setPageCount(result.getTotalPage());
			
			List<VipBatchDto> respList = (List<VipBatchDto>) result.getResult();
			if(!ArrayUtil.empty(respList)) {
				for(VipBatchDto tmp : respList) {
					temp = new VipImpRes();
					BeanUtils.copyProperties(temp, tmp);
					temp.setBankOrg(tmp.getBankOrg().getBankName());
					temp.setCreateType(tmp.getCreateType().getDesc());
					temp.setStatus(tmp.getStatus().getValue() + "");
					temp.setDefaultLevel(tmp.getDefaultLevel().getDesc());
					temp.setCreateTime(tmp.getCreateTime() == null ? null : tmp.getCreateTime().getTime());
					temp.setAuditTime(tmp.getAuditTime() == null ? null : tmp.getAuditTime().getTime());
					temp.setExecTime(tmp.getExecTime() == null ? null : tmp.getExecTime().getTime());
					vipBatchList.add(temp);
				}
			}
		} else {
			throw new BossException(resp.getCode() + "", resp.getMessage());
		}
		map.put("vipBatchList", vipBatchList);
		map.put("page", page);
		return map;
	}
	
	/**
	 * 上传CSV文件
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年7月1日 上午10:52:55
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> csvUpload(MultipartFile file, String operator) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		/*
		 * 新文件名格式：操作员用户名_members.csv
		 */
		String newFileName = CalendarUtil.formatDateByFormat(new Date(), CalendarUtil.format9) + ".csv";
		//文件保存
		FileUtil.saveFile(newFileName, file);
		
		//上传文件
		ScpUtil.uploadFile(Constants.getImgLocalUrl()+"/"+newFileName, newFileName, Constants.getScpFileUploadPath(), Constants.SCPCONFIG);
		
		map.put("fileName", newFileName);
		return map;
	}
	
	/**
	 * 导入批次信息
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年6月29日 下午8:32:43
	 * @param pojo
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> importMembers(VipImpReq pojo, File file) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<String> list = new ArrayList<String>();
		//解析CSV文件
		FileInputStream input = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new UnicodeReader(input, "UTF-8"));
		String tmp = null;
		while((tmp = br.readLine()) != null) {
			if(StringUtils.isNotBlank(tmp)) {
				list.add(tmp.trim());
			}
		}
		br.close();
		input.close();
		//封装请求对象
		BankOrg bankOrg = StringUtils.isNotBlank(pojo.getBankOrg()) ? BankOrg.getInstanceByValue(pojo.getBankOrg()) : null;
		VipBatchDto req = new VipBatchDto();
		req.setMemberNames(list);
		req.setBankOrg(bankOrg);//银行渠道
		//文件命名：20150705145855.csv
		req.setFileName(file.getName());
		req.setDescription(pojo.getDescription());
		req.setOperator(pojo.getOperator());
		req.setLabelId(pojo.getLabelId());//银行机构ID
		//调用VIP用户系统
		Result resp = vIPSpecService.batchImport(req);
		
		if(!resp.getResult()) {
			//导入失败返回Map<String, String>，生成CSV文件
			Map<String, ErrorCodeType> vipErrMap = (Map<String, ErrorCodeType>) resp.getData();
			String newFileName = CalendarUtil.formatDateByFormat(new Date(), CalendarUtil.format9) + "_err.csv";
			String saveFilePath = Constants.getImgLocalUrl();
			//新建csv文件
			File f = new File(saveFilePath + File.separator + newFileName);
			if(!f.exists()) {
				f.createNewFile();
			}
			OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(f), "GBK");
			BufferedWriter bw = new BufferedWriter(osw);
			Iterator<String> keys = vipErrMap.keySet().iterator();
			while(keys.hasNext()) {
				String memberName = keys.next();
				bw.write(memberName + "," + vipErrMap.get(memberName));
				bw.newLine();
			}
			bw.flush();
			bw.close();
			
			//上传文件
			ScpUtil.uploadFile(Constants.getImgLocalUrl()+"/"+newFileName, newFileName, Constants.getScpFileUploadPath(), Constants.SCPCONFIG);
			
			File f1 = new File(saveFilePath + File.separator + newFileName);
			if(f1.exists()){
				f1.delete();
			}
			
			map.put("code", Constants.RESULT_SUCCESS);
			map.put("message", newFileName);
		} else {
			map.put("code", Constants.RESULT_SUCCESS);
			map.put("message", "导入成功");
		}
		return map;
	}
	
	/**
	 * 导入会员提交审核
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年6月29日 下午8:33:11
	 * @param pojo
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> reviewImportSubmit(VipImpStatReq pojo) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		//调用VIP用户系统
		Result resp = vIPSpecService.batchSubmitApproval(pojo.getBatchCode());
		
		if(!resp.getResult()) {
			throw new BossException(resp.getCode() + "", resp.getMessage());
		} else {
			map.put("code", Constants.RESULT_SUCCESS);
			map.put("message", "提交审核成功");
		}
		return map;
	}
	
	/**
	 * 审核导入批次通过
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年6月29日 下午8:33:56
	 * @param pojo
	 * @param checker
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> reviewImportPass(VipImpStatReq pojo, String checker) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		//调用VIP用户系统
		Result resp = vIPSpecService.batchApprovePass(pojo.getBatchCode(), checker);
		
		if(!resp.getResult()) {
			throw new BossException(resp.getCode() + "", resp.getMessage());
		} else {
			map.put("code", Constants.RESULT_SUCCESS);
			map.put("message", "审核通过成功");
		}
		return map;
	}
	
	/**
	 * 审核导入批次不通过
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年6月29日 下午8:35:24
	 * @param pojo
	 * @param checker
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> reviewImportFail(VipImpRevFailReq pojo, String checker) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		//调用VIP用户系统
		Result resp = vIPSpecService.batchNoPass(pojo.getBatchCode(), checker, pojo.getDescriptionFail());
		
		if(!resp.getResult()) {
			throw new BossException(resp.getCode() + "", resp.getMessage());
		} else {
			map.put("code", Constants.RESULT_SUCCESS);
			map.put("message", "审核不通过成功");
		}
		return map;
	}
	
	/**
	 * 审核导入批次废弃
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年6月29日 下午8:35:54
	 * @param pojo
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> reviewImportDiscard(VipImpStatReq pojo) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		//调用VIP用户系统
		Result resp = vIPSpecService.batchDisuse(pojo.getBatchCode());
		
		if(!resp.getResult()) {
			throw new BossException(resp.getCode() + "", resp.getMessage());
		} else {
			map.put("code", Constants.RESULT_SUCCESS);
			map.put("message", "废弃成功");
		}
		return map;
	}
	
	/**
	 * 导入批次执行
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年6月29日 下午8:36:10
	 * @param pojo
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> reviewImportExecute(VipImpStatReq pojo, String operator) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		//调用VIP用户系统
		Result resp = vIPSpecService.batchExecute(pojo.getBatchCode());
		
		if(!resp.getResult()) {
			List<VIPSpecDto> vipErrList = (List<VIPSpecDto>) resp.getData();
			if(ArrayUtil.empty(vipErrList)) {
				throw new BossException(resp.getCode() + "", resp.getMessage());
			}
			//导入失败返回List<VIPSpecDto>，生成CSV文件
			String newFileName = operator + "_batch_err.csv";
			String saveFilePath = Constants.getImgLocalUrl();
			//新建csv文件
			File f = new File(saveFilePath + File.separator + newFileName);
			if(!f.exists()) {
				f.createNewFile();
			}
			BufferedWriter bw = new BufferedWriter(new FileWriter(f, false));
			for(VIPSpecDto tmp : vipErrList) {
				String memberName = tmp.getLoginID();
				bw.write(memberName + ",该用户已是VIP会员");
				bw.newLine();
			}
			bw.flush();
			bw.close();
			map.put("code", Constants.RESULT_SUCCESS);
			map.put("message", newFileName);
		} else {
			map.put("code", Constants.RESULT_SUCCESS);
			map.put("message", "执行成功");
		}
		return map;
	}
	
	/**
	 * 下载批次信息
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年6月29日 下午8:36:27
	 * @param pojo
	 * @return dto
	 * @throws Exception
	 */
	public VipBatchDto download(Long batchCode) throws Exception {
		VipBatchDto dto = null;
		//调用VIP用户系统
		Result resp = vIPSpecService.batchDownloadData(batchCode);
		
		if(!resp.getResult()) {
			throw new BossException(resp.getCode() + "", resp.getMessage());
		} else {
			dto = (VipBatchDto) resp.getData();
		}
		return dto;
	}
}
