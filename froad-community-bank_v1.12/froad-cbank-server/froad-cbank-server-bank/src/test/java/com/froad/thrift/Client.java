package com.froad.thrift;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.alibaba.druid.util.ServletPathMatcher;
import com.alibaba.fastjson.JSON;
import com.froad.db.redis.impl.RedisManager;
import com.froad.enums.AuditTaskState;
import com.froad.enums.AuditType;
import com.froad.po.BankAudit;
import com.froad.po.BankOperator;
import com.froad.thrift.service.ArtificialPersonService;
import com.froad.thrift.service.AuditProcessService;
import com.froad.thrift.service.AuditTaskService;
import com.froad.thrift.service.BankAuditService;
import com.froad.thrift.service.BankOperateLogService;
import com.froad.thrift.service.BankOperatorService;
import com.froad.thrift.service.BankResourceService;
import com.froad.thrift.service.BankRoleResourceService;
import com.froad.thrift.service.BankRoleService;
import com.froad.thrift.service.ClientMerchantAuditService;
import com.froad.thrift.service.ClientPaymentChannelService;
import com.froad.thrift.service.ClientProductAuditService;
import com.froad.thrift.service.ClientService;
import com.froad.thrift.service.OrgService;
import com.froad.thrift.service.OrgUserRoleService;
import com.froad.thrift.vo.AuditProcessVo;
import com.froad.thrift.vo.AuditTaskFilterVo;
import com.froad.thrift.vo.AuditTaskPageDetailVo;
import com.froad.thrift.vo.AuditTaskPageVoRes;
import com.froad.thrift.vo.AuditTaskVo;
import com.froad.thrift.vo.BankAuditVo;
import com.froad.thrift.vo.BankAuditVoRes;
import com.froad.thrift.vo.BankOperateLogPageVoRes;
import com.froad.thrift.vo.BankOperateLogVo;
import com.froad.thrift.vo.BankOperatorPageVoRes;
import com.froad.thrift.vo.BankOperatorVo;
import com.froad.thrift.vo.BankResourceVo;
import com.froad.thrift.vo.BankRolePageVoRes;
import com.froad.thrift.vo.BankRoleVo;
import com.froad.thrift.vo.BankUserResourceVo;
import com.froad.thrift.vo.ClientMerchantAuditOrgCodeVo;
import com.froad.thrift.vo.ClientPaymentChannelVo;
import com.froad.thrift.vo.ClientProductAuditOrgCodeVo;
import com.froad.thrift.vo.ClientVo;
import com.froad.thrift.vo.LoginBankOperatorVoRes;
import com.froad.thrift.vo.OrgPageVoRes;
import com.froad.thrift.vo.OrgUserRoleVo;
import com.froad.thrift.vo.OrgVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.PlatType;
import com.froad.thrift.vo.ResourcesInfoVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.RedisKeyUtil;

public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			TSocket transport = new TSocket("localhost", 15101);
//			TSocket transport = new TSocket("10.43.2.238",15101); //迭代2开发环境
//			TSocket transport = new TSocket("10.43.1.122",15101); //迭代2测试环境
//			TSocket transport = new TSocket("10.43.2.1",15101); //数据迁移开发环境
//			TSocket transport = new TSocket("10.43.1.9",15101); //主版本测试环境
//			TSocket transport = new TSocket("10.43.2.240",15101); //直销银行/v1.1 开发环境
//			TSocket transport = new TSocket("10.43.1.132",15101);//v1.1测试环境
			
			
			TBinaryProtocol protocol = new TBinaryProtocol(transport);

			TMultiplexedProtocol mp1 = new TMultiplexedProtocol(protocol,"ClientService");
			ClientService.Client service1 = new ClientService.Client(mp1);

			TMultiplexedProtocol mp2 = new TMultiplexedProtocol(protocol,"ClientPaymentChannelService");
			ClientPaymentChannelService.Client service2 = new ClientPaymentChannelService.Client(mp2);

			TMultiplexedProtocol mp3 = new TMultiplexedProtocol(protocol,"BankOperatorService");
			BankOperatorService.Client service3 = new BankOperatorService.Client(mp3);

			TMultiplexedProtocol mp4 = new TMultiplexedProtocol(protocol,"BankAuditService");
			BankAuditService.Client service4 = new BankAuditService.Client(mp4);
			
			TMultiplexedProtocol mp5 = new TMultiplexedProtocol(protocol,"BankOperateLogService");
			BankOperateLogService.Client service5 = new BankOperateLogService.Client(mp5);
			
			TMultiplexedProtocol mp6 = new TMultiplexedProtocol(protocol,"BankResourceService");
			BankResourceService.Client service6 = new BankResourceService.Client(mp6);
			
			TMultiplexedProtocol mp7 = new TMultiplexedProtocol(protocol,"BankRoleService");
			BankRoleService.Client service7 = new BankRoleService.Client(mp7);
			
			TMultiplexedProtocol mp8 = new TMultiplexedProtocol(protocol,"BankRoleResourceService");
			BankRoleResourceService.Client service8 = new BankRoleResourceService.Client(mp8);
			
			TMultiplexedProtocol mp9 = new TMultiplexedProtocol(protocol,"OrgService");
			OrgService.Client service9 = new OrgService.Client(mp9);
			
			TMultiplexedProtocol mp10 = new TMultiplexedProtocol(protocol,"BankAuditService");
			BankAuditService.Client service10 = new BankAuditService.Client(mp10);
			
			TMultiplexedProtocol mp11 = new TMultiplexedProtocol(protocol,"ArtificialPersonService");
			ArtificialPersonService.Client service11 = new ArtificialPersonService.Client(mp11);
			
			TMultiplexedProtocol mp12 = new TMultiplexedProtocol(protocol,"ClientMerchantAuditService");
			ClientMerchantAuditService.Client service12 = new ClientMerchantAuditService.Client(mp12);
			
			TMultiplexedProtocol mp13 = new TMultiplexedProtocol(protocol,"ClientProductAuditService");
			ClientProductAuditService.Client service13 = new ClientProductAuditService.Client(mp13);
			
			TMultiplexedProtocol mp14 = new TMultiplexedProtocol(protocol,"AuditTaskService");
			AuditTaskService.Client service14 = new AuditTaskService.Client(mp14);
			
			TMultiplexedProtocol mp15 = new TMultiplexedProtocol(protocol,"AuditProcessService");
			AuditProcessService.Client service15 = new AuditProcessService.Client(mp15);
			
			TMultiplexedProtocol mp16 = new TMultiplexedProtocol(protocol,"OrgUserRoleService");
			OrgUserRoleService.Client service16 = new OrgUserRoleService.Client(mp16);
			
			transport.open();
			
			
			OriginVo originVo = new OriginVo();
			originVo.setOperatorId(16267);
			originVo.setOperatorIp("127.0.0.1");
			originVo.setPlatType(PlatType.bank);
			originVo.setClientId("chongqing");
			

			//待审核任务订单详情
//			AuditTaskVo vo = service14.getAuditTaskWait("05C2A1E08024");
//			System.err.println("审核流水号："+vo.getAuditId());
//			//System.err.println("所属机构："+vo.get);所属机构前端取新商户模块返回数据
//			System.err.println("审核状态："+vo.getAuditState());
//			System.err.println("创建时间："+vo.getCreateTime());
//			System.err.println("商户名称："+vo.getName());
//			System.err.println("营业执照："+vo.getBusCode());
//			System.err.println("创建人："+vo.getUserName());
//			System.err.println("创建机构："+vo.getOrgName());
//			System.err.println("业务类型："+vo.getType());
//			System.err.println("归档时间："+vo.getAuditTime());
			
			
			//查询审核流水号详情
//			AuditTaskVo vo = service14.getAuditTaskByAuditId("2015082600000000");
//			System.err.println("审核流水号："+vo.getAuditId());
//			System.err.println("审核状态："+vo.getAuditState());
//			System.err.println("创建时间："+vo.getCreateTime());
//			System.err.println("商户名称："+vo.getName());
//			System.err.println("营业执照："+vo.getBusCode());
//			System.err.println("创建人："+vo.getUserName());
//			System.err.println("创建机构："+vo.getOrgName());
//			System.err.println("业务类型："+vo.getType());
//			System.err.println("归档时间："+vo.getAuditTime());
			
			
//			String auditId=vo.getAuditId();//审核流水号
			//任务单列表
//			List<AuditProcessVo> voList = service15.getAuditProcess(auditId);
//			for(AuditProcessVo vo:voList){
//				System.err.println("任务流水号："+vo.getTaskId());
//				System.err.println("创建时间："+vo.getCreateTime());
//				System.err.println("审核时间："+vo.getAuditTime());
//				System.err.println("审核机构："+vo.getOrgName());
//				System.err.println("审核人："+vo.getAuditStaff());
//				System.err.println("状态："+vo.getAuditState());
//				System.err.println("备注："+vo.getAuditComment());
//				System.err.println("---------------------");
//			}
			
			//审核任务订单分页查询
//			PageVo pageVo = new PageVo();
//			pageVo.setPageSize(10);
//			pageVo.setPageNumber(1);
			
//			AuditTaskFilterVo auditTaskFilterVo = new AuditTaskFilterVo();
//			auditTaskFilterVo.setAuditId(auditId);
//			auditTaskFilterVo.setStartCreateTime();
//			auditTaskFilterVo.setEndCreateTime(endCreateTime)
//			auditTaskFilterVo.setStartAuditTime(startAuditTime);
//			auditTaskFilterVo.setEndAuditTime(endAuditTime);
//			auditTaskFilterVo.setType(AuditType.merchant.getType());
//			auditTaskFilterVo.setName("");
//			auditTaskFilterVo.setBusCode("");
//			auditTaskFilterVo.setState(AuditTaskState.onPassage.getType());
//			auditTaskFilterVo.setOrgCode("340000");
//			auditTaskFilterVo.setClientId("anhui");
			
//			int flag = 2;//1-查当前机构 2-查当前机构所有下属机构
//			AuditTaskPageVoRes voRes = service14.getAuditTaskByPage(pageVo, auditTaskFilterVo, flag);
//			List<AuditTaskPageDetailVo> voList = voRes.getAuditTaskVoList();
//			for(AuditTaskPageDetailVo taskVo : voList){
//				System.err.println("审核流水号："+taskVo.getAuditId());
//				System.err.println("创建时间："+taskVo.getCreateTime());
//				System.err.println("业务类型："+taskVo.getType());
//				System.err.println("名称："+taskVo.getName());
//				System.err.println("状态："+taskVo.getAuditState());
//				System.err.println("所属机构："+taskVo.getSuperOrgName()+"-->"+taskVo.getOrgName());
//				System.err.println("归档时间："+taskVo.getAuditTime());
//				System.err.println("---------------------");
//			}
			
			
			//审核
//			BankAuditVo bankAuditVo = new BankAuditVo();
//			bankAuditVo.setAuditId("05C2A2D0801B");
//			bankAuditVo.setClientId("anhui");
//			bankAuditVo.setAuditState("1");
////			bankAuditVo.setAuditStaff("yehefeng333");
////			bankAuditVo.setAuditStaff("AHRCU341465");
//			bankAuditVo.setAuditComment("审核下下");
//			bankAuditVo.setAuditStaff("luoli00");
//			
//			List<BankAuditVo> bankAuditList=new ArrayList<BankAuditVo>();
//			bankAuditList.add(bankAuditVo);
//			BankAuditVoRes voRes = service10.auditBatch(originVo, 2, bankAuditList);
//			System.err.println(voRes.getResult().getResultCode()+"----"+voRes.getResult().getResultDesc());
//			System.err.println(voRes.isFinalAudit);
			
			
			
			
			//查询设置到redis中
//			service1.getClientById("anhui");
//			service1.getClientById("chongqing");
//			service2.getClientPaymentChannelById("anhui", "002e583a8000");
//			service2.getClientPaymentChannelById("anhui", "003dd8e50000");
//			service2.getClientPaymentChannelById("anhui", "003dd8e50001");
//			service2.getClientPaymentChannelById("anhui", "003dd8e50002");
//			service2.getClientPaymentChannelById("anhui", "003dd8e50007");
//			
//			service2.getClientPaymentChannelById("chongqing", "003dd8e50003");
//			service2.getClientPaymentChannelById("chongqing", "003dd8e50004");
//			service2.getClientPaymentChannelById("chongqing", "003dd8e50005");
//			service2.getClientPaymentChannelById("chongqing", "003dd8e50006");
//			service2.getClientPaymentChannelById("chongqing", "01A68B708000");
			
			
			
			//客户端信息
//			ClientVo cv = new ClientVo();
//			cv.setId(Long.parseLong("100000000"));
//			cv.setClientId(Long.parseLong("1000"));
//			service1.updateClient(cv);
			
//			cv.setClientId(1000);
//			cv.setName("安徽农信");
//			cv.setPointPartnerNo("80160000018");//积分平台商户号
//			cv.setOpenapiPartnerNo("80160000018");
//			cv.setOrderDisplay("安徽农信");
//			cv.setAppkey("sdsdf");
//			cv.setAppsecret("adfadfadfa");
//			cv.setBankType("1");
//			cv.setBankName("安徽银行");
//			cv.setQrLogo("sdddf");
//			cv.setReturnUrl("sdfadfaf");
//			cv.setRemark("iwefksjdkdj看");
			
//			service1.updateClient(cv);
			
//			System.out.println(service1.addClient(cv));
//			List<ClientVo> a = service1.getClient(cv);
//			ClientVo cvo = a.get(0);
//			System.err.println(cvo.getName()+"---"+cvo.getAppsecret());


			//客户端支付渠道信息
//			ClientPaymentChannelVo clientPaymentChannelVo = new ClientPaymentChannelVo();
//			clientPaymentChannelVo.setClientId(Long.parseLong("1000"));
//			clientPaymentChannelVo.setName("方付通积分支付");
//			clientPaymentChannelVo.setFullName("方付通积分支付");
//			clientPaymentChannelVo.setType("1");//渠道类型
//			clientPaymentChannelVo.setIcoUrl("");
//			clientPaymentChannelVo.setPaymentOrgNo("100010002");//资金机构代号（支付系统）
//			clientPaymentChannelVo.setIsFroadCheckToken(false);
//			clientPaymentChannelVo.setPointRate("1");//积分兑换比例
//			List<ClientPaymentChannelVo> clientPaymentList = service2.getClientPaymentChannel(clientPaymentChannelVo);
//			for(ClientPaymentChannelVo result:clientPaymentList){
//				System.err.println(result.getClientId()+"--------"+result.getName());
//			}
			
			
			
			
//			service2.addClientPaymentChannel(clientPaymentChannelVo);
//			
//			clientPaymentChannelVo = new ClientPaymentChannelVo();
//			clientPaymentChannelVo.setClientId(Long.parseLong("1000"));
//			clientPaymentChannelVo.setName("安徽银行积分");
//			clientPaymentChannelVo.setFullName("安徽银行积分");
//			clientPaymentChannelVo.setType("2");//渠道类型
//			clientPaymentChannelVo.setIcoUrl("");
//			clientPaymentChannelVo.setPaymentOrgNo("100010068");//资金机构代号（支付系统）
//			clientPaymentChannelVo.setIsFroadCheckToken(false);
//			clientPaymentChannelVo.setPointRate("100");//积分兑换比例
//			service2.addClientPaymentChannel(clientPaymentChannelVo);
//			
//			clientPaymentChannelVo = new ClientPaymentChannelVo();
//			clientPaymentChannelVo.setClientId(Long.parseLong("1000"));
//			clientPaymentChannelVo.setName("安徽快捷支付");
//			clientPaymentChannelVo.setFullName("安徽快捷支付");
//			clientPaymentChannelVo.setType("51");//渠道类型
//			clientPaymentChannelVo.setIcoUrl("");
//			clientPaymentChannelVo.setPaymentOrgNo("643");//资金机构代号（支付系统）
//			clientPaymentChannelVo.setIsFroadCheckToken(false);
//			clientPaymentChannelVo.setPointRate("");//积分兑换比例
//			service2.addClientPaymentChannel(clientPaymentChannelVo);
//			
//			
//			clientPaymentChannelVo = new ClientPaymentChannelVo();
//			clientPaymentChannelVo.setClientId(Long.parseLong("1000"));
//			clientPaymentChannelVo.setName("安徽及时支付");
//			clientPaymentChannelVo.setFullName("安徽及时支付");
//			clientPaymentChannelVo.setType("55");//渠道类型
//			clientPaymentChannelVo.setIcoUrl("");
//			clientPaymentChannelVo.setPaymentOrgNo("210");//资金机构代号（支付系统）
//			clientPaymentChannelVo.setIsFroadCheckToken(false);
//			clientPaymentChannelVo.setPointRate("");//积分兑换比例
//			service2.addClientPaymentChannel(clientPaymentChannelVo);
			
			
			
//			ClientPaymentChannelVo clientPaymentChannelVo = new ClientPaymentChannelVo();
//			clientPaymentChannelVo.setClientId("anhui");
//			clientPaymentChannelVo.setName("安徽贴膜卡支付");
//			clientPaymentChannelVo.setFullName("安徽贴膜卡支付");
//			clientPaymentChannelVo.setType("20");//渠道类型
//			clientPaymentChannelVo.setIcoUrl("");
//			clientPaymentChannelVo.setPaymentOrgNo("243");//资金机构代号（支付系统）
//			clientPaymentChannelVo.setIsFroadCheckToken(false);
//			clientPaymentChannelVo.setPointRate("10");//积分兑换比例
//			service2.addClientPaymentChannel(originVo,clientPaymentChannelVo);
			
			
			
				
//			ClientPaymentChannelVo clientPaymentChannelVo = new ClientPaymentChannelVo();
//			clientPaymentChannelVo.setId(100000010);
//			clientPaymentChannelVo.setClientId("chongqing");
//			clientPaymentChannelVo.setPaymentChannelId("09E4F1F18000");
			
//			ResultVo vo = service2.deleteClientPaymentChannel(originVo,clientPaymentChannelVo);
//			System.err.println(vo.getResultCode()+"------"+vo.getResultDesc());
			
			
			
			//银行用户查询分页
//			BankOperatorVo bankOperatorVo = new BankOperatorVo();
//			bankOperatorVo.setClientId("anhui");
//			PageVo pageVo = new PageVo();
//			pageVo.setPageSize(10);
//			pageVo.setPageNumber(1);
//			
//			BankOperatorPageVoRes res=service3.getBankOperatorByPage(pageVo, bankOperatorVo);
//			System.err.println("当前页码:"+res.getPage().getPageNumber());//当前页码
//			System.err.println("每页记录数:"+res.getPage().getPageSize());//每页记录数
//			System.err.println("总页数:"+res.getPage().getPageCount());//总页数
//			System.err.println("总记录数:"+res.getPage().getTotalCount());//总记录数
//			List<BankOperatorVo> operList=res.getBankOperatorVoList();
//			for(BankOperatorVo bVo:operList){
//				System.err.println(bVo.getClientId()+"--"+bVo.getName());
//			}
			
			//银行用户新增
//			BankOperatorVo bankOperatorVo = new BankOperatorVo();
//			bankOperatorVo.setClientId("anhui");
//			bankOperatorVo.setUsername("anhuiadmin");
//			bankOperatorVo.setPassword(DigestUtils.md5Hex("111111"));
//			bankOperatorVo.setOrgCode("340000");
//			bankOperatorVo.setRoleId(100000000);
//			service3.addBankOperator(originVo, bankOperatorVo);
			
			//银行用户删除
//			BankOperatorVo bankOperatorVo = new BankOperatorVo();
//			bankOperatorVo.setId(100000033l);
//			service3.deleteBankOperator(originVo, bankOperatorVo);
			
			
			//银行用户更新
//			BankOperatorVo bankOperatorVo = new BankOperatorVo();
//			bankOperatorVo.setId(Long.parseLong("100000001"));
//			bankOperatorVo.setClientId(Long.parseLong("100000"));
//			bankOperatorVo.setName("admin222");
//			service3.updateBankOperator(bankOperatorVo);
			
//			BankOperatorVo resultVo=service3.getBankOperatorById(Long.parseLong("100000"), Long.parseLong("100000000"));
//			System.err.println(resultVo.getUsername());
			
//			System.err.println(JSON.toJSON(clientPaymentChannelVo));	
			
			
			//银行用户登录
//			LoginBankOperatorVoRes voRes=service3.loginBankOperator("yehefeng", "e10adc3949ba59abbe56e057f20f883e","","127.0.0.1");
//			System.err.println(voRes.getResult().getResultCode()+"---"+voRes.getResult().getResultDesc());
//			System.err.println("登录次数:"+voRes.getLoginFailureCount());
//			System.err.println("token:"+voRes.getToken());
//			System.err.println("姓名"+voRes.getBankOperator().getName());
			
			
			//添加日志
//			BankOperateLogVo bankOperateLogVo=new BankOperateLogVo();
//			bankOperateLogVo.setUserId(1000l);
//			bankOperateLogVo.setUsername("测试来咯");
//			bankOperateLogVo.setDescription("登录");
//			bankOperateLogVo.setClientId(1000l);
//			bankOperateLogVo.setOrgCode("10002");
//			bankOperateLogVo.setOrgName("海滨支行");
//			bankOperateLogVo.setOperatorIp("192.168.1.10");
//			service5.addBankOperateLog(bankOperateLogVo);
			
			//日志查询
//			BankOperateLogVo bankOperateLogVo= new BankOperateLogVo();
//			bankOperateLogVo.setClientId("anhui");
//			bankOperateLogVo.setPlatType(PlatType.bank.getValue());
//			
//			PageVo pageVo = new PageVo();
//			pageVo.setPageSize(10);
//			pageVo.setPageNumber(1);
//			BankOperateLogPageVoRes res=service5.getBankOperateLogByPage(pageVo, bankOperateLogVo);
//			System.err.println("当前页码:"+res.getPage().getPageNumber());//当前页码
//			System.err.println("每页记录数:"+res.getPage().getPageSize());//每页记录数
//			System.err.println("总页数:"+res.getPage().getPageCount());//总页数
//			System.err.println("总记录数:"+res.getPage().getTotalCount());//总记录数
//			List<BankOperateLogVo> operList=res.getBankOperateLogVoList();
//			for(BankOperateLogVo bVo:operList){
//				System.err.println(bVo.getClientId()+"--"+bVo.getDescription()+"---"+bVo.getRoleName()+"---"+bVo.getOrgName());
//			}
			
			
			
			
			//添加资源
//			BankResourceVo bankResourceVo=new BankResourceVo();
//			bankResourceVo.setClientId(1000l);
//			bankResourceVo.setResourceName("密码设置");
//			bankResourceVo.setResourceType(false);
//			bankResourceVo.setParentResourceId(100000020);
//			bankResourceVo.setStatus(true);
//			bankResourceVo.setIsDelete(false);
//			bankResourceVo.setResourceUrl("http://172.18.5.158:8081/bps-website/task/workRemind.htm");
//			bankResourceVo.setTreePath("100000020 ");
//			service6.addBankResource(bankResourceVo);
			
			//添加角色
//			BankRoleVo bankRoleVo=new BankRoleVo();
//			bankRoleVo.setClientId("anhui");
//			bankRoleVo.setRoleName("系统初始化管理员角色");
//			bankRoleVo.setRemark("系统初始化管理员角色");
//			bankRoleVo.setTag("1");
//			List<Long> resourceIds=new ArrayList<Long>();
//			for(long i=100000000;i<=100000041;i++){
//				resourceIds.add(i);
//			}
//			service7.addBankRole(originVo,bankRoleVo, resourceIds);
			
			//修改角色
//			BankRoleVo bankRoleVo=new BankRoleVo();
//			bankRoleVo.setId(100003080l);
//			List<Long> resourceIds=new ArrayList<Long>();
////			for(long i=100000030;i<=100000041;i++){
////				resourceIds.add(i);
////			}
//			resourceIds.add(100001030l);
//			resourceIds.add(100001031l);
//			resourceIds.add(100001036l);
//			resourceIds.add(100001037l);
//			resourceIds.add(100001038l);
//			resourceIds.add(100001039l);
//			resourceIds.add(100001055l);
//			resourceIds.add(100001056l);
//////			resourceIds.add(100001051l);
//////			resourceIds.add(100001052l);
//////			resourceIds.add(100001053l);
//			ResultVo vo = service7.updateBankRole(originVo,bankRoleVo, resourceIds);
//			System.err.println(vo.getResultCode() + "----" + vo.getResultDesc());
			
			//删除角色
//			BankRoleVo bankRoleVo=new BankRoleVo();
//			bankRoleVo.setId(100007870l);
//			service7.deleteBankRole(bankRoleVo);
			
			
			//查询角色资源关系
//			List<BankResourceVo> resources=service8.getBankRoleResource("anhui", 100007870);
//			for(BankResourceVo b:resources){
//				System.err.println(b.getResourceName());
//			}
			
			
			//查询全部角色资源关系
//			List<BankUserResourceVo> resources=service8.getBankRoleResourceAll();
//			for(BankUserResourceVo b:resources){
//				System.err.println("==="+b.getId());
//				List<ResourcesInfoVo> rList = b.getResources();
//				for(ResourcesInfoVo r:rList){
//					System.err.println(r.getResourceName()+"===="+r.getResourceId());
//				}
//			}
			
			
			//角色列表分页
//			PageVo pageVo = new PageVo();
//			pageVo.setPageSize(10);
//			pageVo.setPageNumber(1);
//			BankRoleVo bankRoleVo = new BankRoleVo();
//			BankRolePageVoRes res = service7.getBankRoleByPage(pageVo, bankRoleVo);
//			System.err.println("当前页码:"+res.getPage().getPageNumber());//当前页码
//			System.err.println("每页记录数:"+res.getPage().getPageSize());//每页记录数
//			System.err.println("总页数:"+res.getPage().getPageCount());//总页数
//			System.err.println("总记录数:"+res.getPage().getTotalCount());//总记录数
//			List<BankRoleVo> roleList=res.getBankRoleVoList();
//			for(BankRoleVo vo:roleList){
//				System.err.println(vo.getClientId()+"--"+vo.getRoleName());
//			}
			
			
			//机构新增
//			OrgVo orgVo =new OrgVo();
//			orgVo.setClientId("anhui");
//			orgVo.setBankType("1");
//			orgVo.setOrgCode("34111104");
//			orgVo.setOrgName("测试签约同步假数据222");
//			orgVo.setMerchantId("01D4A0C28000");
//			orgVo.setOutletId("01D4A5928000");
//			orgVo.setAreaId(100000011l);
//			orgVo.setOrgLevel("3");
//			orgVo.setProvinceAgency("340000");
//			orgVo.setCityAgency("3401010081");
//			orgVo.setNeedReview(false);
//			orgVo.setOrgType(true);
//			
//			service9.addOrg(originVo, orgVo);
			
			
			
			//机构分页查询
//			PageVo pageVo = new PageVo();
//			pageVo.setPageSize(10);
//			pageVo.setPageNumber(1);
//			OrgVo orgVo =new OrgVo();
//			orgVo.setOrgLevel("3");
//			OrgPageVoRes res=service9.getOrgByPage(pageVo, orgVo);
//			System.err.println("当前页码:"+res.getPage().getPageNumber());//当前页码
//			System.err.println("每页记录数:"+res.getPage().getPageSize());//每页记录数
//			System.err.println("总页数:"+res.getPage().getPageCount());//总页数
//			System.err.println("总记录数:"+res.getPage().getTotalCount());//总记录数
//			List<OrgVo> orgList=res.getOrgVoList();
//			for(OrgVo vo:orgList){
//				System.err.println(vo.getClientId()+"--"+vo.getOrgName());
//			}
			
			//查询上级多级别机构对象
//			List<OrgVo> orgs = service9.getSuperOrgList("anhui", "3401010201");
//			for(OrgVo o:orgs){
//				System.err.println(o.getOrgCode()+"---"+o.getOrgName());
//			}
			
//			List<Long> areaIds = new ArrayList<Long>();
//			areaIds.add(1889l);
//			areaIds.add(1890l);
//			areaIds.add(1891l);
//			areaIds.add(1892l);
//			areaIds.add(1893l);
//			areaIds.add(1894l);
//			areaIds.add(1895l);
//			areaIds.add(1896l);
//			
//			//查询上级多级别机构对象
//			List<OrgVo> orgs = service9.getOrgByAreaIdsList("anhui", areaIds);
//			for(OrgVo o:orgs){
//				System.err.println(o.getOrgCode()+"---"+o.getOrgName());
//			}
			
//			0-待审核 1-审核通过 2-审核不通过
//			BankAuditVo bankAuditVo = new BankAuditVo();
//			bankAuditVo.setAuditId("01BB01730000");
//			bankAuditVo.setClientId("anhui");
//			bankAuditVo.setAuditState("1");
//			bankAuditVo.setAuditStaff("ztt000");
//			
//			List<BankAuditVo> bankAuditList=new ArrayList<BankAuditVo>();
//			bankAuditList.add(bankAuditVo);
//			System.err.println(service10.auditBatch(originVo,2, bankAuditList));
			
			
			
//			String key = RedisKeyUtil.cbank_preaudit_merchant_count_client_id_org_code(1000l,"3401030219");
//			new RedisManager().putString(key, "0");
			
			
			
			
			//查询待审核数量
//			preauditDuihuan:1, preauditMingYou:1, preauditMerchant:1, preauditPresell:0, preauditGroup:3
//			System.err.println("3411467825"+service10.getPreAuditNumRes(1000l, "3411467825"));
//			System.err.println("3401030219"+service10.getPreAuditNumRes(1000l, "3401030219"));
//			System.err.println("340210"+service10.getPreAuditNumRes(1000l, "340210"));
			
//			System.err.println("3405177879"+service10.getPreAuditNumRes(1000l, "3405177879"));
			
			//查orgCode下所有子机构
//			List<String> subOrgs=service9.getAllSubOrgCodes("340103", 1000l);
//			for(String sub:subOrgs){
//				System.err.println(sub);
//			}
			
			
			//禁用机构
//			OrgVo orgVo =new OrgVo();
//			orgVo.setId(100000016);
//			orgVo.setIsEnable(true);
//			service9.deleteOrg(orgVo);
			
			//编辑机构信息
//			OrgVo orgVo=new OrgVo();
//			orgVo.setId(27592);
////			orgVo.setId(27509);
////			orgVo.setOrgName("合肥科技银行新站支行");
//			orgVo.setNeedReview(false);
//			service9.updateOrg(originVo,orgVo);
			
			//查询下级机构
//			List<OrgVo> orgs=service9.getSubOrgs("340101", 1000l);
//			for(OrgVo org:orgs){
//				System.err.println(org.getOrgCode()+"-----"+org.getOrgName());
//			}
//			List<String> orgCodes=service9.getAllSubOrgCodes("340103", 1000l);
//			for(int i=0;i<orgCodes.size();i++){
//				System.err.println(orgCodes.get(i));
//			}
			
			//根据机构编号集合查询多个对象list
//			List<String> orgCodes = new ArrayList<String>();
//			orgCodes.add("340105");
//			orgCodes.add("340205");
//			orgCodes.add("340206");
//			orgCodes.add("340207");
//			orgCodes.add("340208");
//			orgCodes.add("340209");
//			orgCodes.add("340210");
//			orgCodes.add("3400008889");
//			orgCodes.add("3400008887");
//			orgCodes.add("3400008851");
//			orgCodes.add("3401010003");
//			
//			List<OrgVo> resultList=service9.getOrgByList(1000l, orgCodes);
//			for(OrgVo orgVo:resultList){
//				System.err.println(orgVo.getOrgCode()+"-------"+orgVo.getOrgName());
//			}
			
			//机构信息查询
//			OrgVo vo = service9.getOrgById("3401010031", 1000l);
//			System.err.println(vo.getOrgName());
			
			
			//法人行社批量重置
//			List<String> orgCodes = new ArrayList<String>();
//			orgCodes.add("340207");
//			orgCodes.add("340208");
//			orgCodes.add("340209");
//			service11.addArtificialPerson(1000l, orgCodes, "666666");
//			service11.updateArtificialPerson(1000l, orgCodes, "777777");
//			service11.deleteArtificialPerson(1000l, orgCodes);
//			List<OrgVo> orgs = service11.getArtificialPerson("anhui", "34322");
//			for(OrgVo o :orgs){
//				System.err.println(o.getOrgName());
//			}
			
			
			//商户审核配置查询
//			ClientMerchantAuditOrgCodeVo vo = service12.getClientMerchantAuditByOrgCode("anhui", "3401010003");
//			System.err.println(vo.getStartAuditOrgCode()+"----"+vo.getEndAuditOrgCode());
			
			//商品审核配置查询
//			ClientProductAuditOrgCodeVo vo = service13.getClientProductAuditByOrgCode("anhui", "3401010003", "2");
//			System.err.println(vo.getStartAuditOrgCode()+"----"+vo.getEndAuditOrgCode());
			
			
			//银行联合登录帐号修改角色
			OrgUserRoleVo find = new OrgUserRoleVo();
			find.setId(8575);
			find.setRoleId(100004143);
			find.setRoleName("总行管理员");
			find.setClientId("chongqing");
//			ResultVo vo = service16.updateOrgUserRole(originVo, find);
			LoginBankOperatorVoRes vo = service16.loginOrgUserRole(originVo, "0005073", "111111");
			System.err.println(vo);
			
//			List<String> orgCodes = service9.getIntersectionOrgCodeList("anhui", "340205", "340000");
//			for(String orgCode : orgCodes){
//				System.err.println(orgCode);
//			}
			
			
			transport.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
