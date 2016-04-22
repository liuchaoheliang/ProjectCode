package com.froad.thrift;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.alibaba.fastjson.JSON;
import com.froad.thrift.service.MerchantUserService;
import com.froad.thrift.vo.MerchantUserPageVoRes;
import com.froad.thrift.vo.MerchantUserVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.PlatType;

public class MerchantUserTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			TSocket transport = new TSocket("localhost", 15201);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			
			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,"MerchantUserService");
			MerchantUserService.Client service = new MerchantUserService.Client(mp);

			transport.open();


			// 新增
//			MerchantUserVo merchantUserVo = new MerchantUserVo();
//			merchantUserVo.setClientId("1000001");
//			merchantUserVo.setCreateTime(new Date().getTime());
//			merchantUserVo.setMerchantId("00CDB73A8000");
//			merchantUserVo.setOutletId("00CDB9AA8000");
//			merchantUserVo.setMerchantRoleId(100000000l);
//			merchantUserVo.setUsername("cccc11154778aaaaa");
//			merchantUserVo.setPassword("xindongfang");
//			merchantUserVo.setEmail("luofansa@126.com");
//			merchantUserVo.setPhone("13800138001");
//			merchantUserVo.setIsRest(false);
//			merchantUserVo.setIsDelete(false);
//			
//			OriginVo originVo = new OriginVo();
//			originVo.setPlatType(PlatType.bank);
//			originVo.setOperatorId(100000000);
//			originVo.setOperatorIp("192.168.19.105");
//			originVo.setDescription("");
//			System.out.println(service.addMerchantUser(originVo, merchantUserVo));

			
			// 查多条
//			MerchantUserVo merchantUserVo = new MerchantUserVo();
//			merchantUserVo.setClientId(1000001);
//			merchantUserVo.setMerchantId("10002145");
//			merchantUserVo.setOutletId("3002400");
//			merchantUserVo.setMerchantRoleId(60109178);
//			merchantUserVo.setUsername("zh");
//			merchantUserVo.setPassword("666888");
//			merchantUserVo.setEmail("zh");
//			merchantUserVo.setPhone("778899");
//			merchantUserVo.setIsRest(false);
//			merchantUserVo.setIsDelete(false);
//			List<MerchantUserVo> ooList = service.getMerchantUser(merchantUserVo);
//			if( ooList != null && ooList.size() > 0 ){
//				for( MerchantUserVo oo : ooList ){
//					System.out.println(JSON.toJSONString(oo));
//				}
//			}else{
//				System.out.println("null data");
//			}
			
			
			// 查一条
//			MerchantUserVo merchantUserVo = service.get("551364c80d5485bbaac2bb43");
//			System.out.println(JSON.toJSONString(merchantUserVo));
			
			// 删除
//			MerchantUserVo merchantUserVo = new MerchantUserVo();
//			merchantUserVo.setId(100000002);
//			boolean result = service.deleteMerchantUser(merchantUserVo);
//			System.out.println("删除操作结果 " + result);
			
			// 修改
//			MerchantUserVo merchantUserVo = new MerchantUserVo();
//			merchantUserVo.setId(100000003);
//			merchantUserVo.setOutletId("3002400");
//			merchantUserVo.setMerchantRoleId(60109178);
//			merchantUserVo.setUsername("zhucb");
//			merchantUserVo.setPassword("123456");
//			merchantUserVo.setEmail("zhucb@sina.com");
//			merchantUserVo.setPhone("13066778899");
//			boolean result = service.updateMerchantUser(merchantUserVo);
//			System.out.println("修改操作结果 " + result);
			
			// 分页查询
			PageVo page = new PageVo();
			page.setPageNumber(1);
			page.setPageSize(20);
			MerchantUserVo merchantUserVo = new MerchantUserVo();
			merchantUserVo.setClientId("anhui");
			merchantUserVo.setMerchantId("01B863EE0000");
//			merchantUserVo.setOutletId("3002400");
			merchantUserVo.setMerchantRoleId(100000001);
//			merchantUserVo.setUsername("zh");
//			merchantUserVo.setPassword("666888");
//			merchantUserVo.setEmail("zh");
//			merchantUserVo.setPhone("778899");
//			merchantUserVo.setIsRest(false);
//			merchantUserVo.setIsDelete(false);
//			MerchantUserPageVoRes merchantUserPageVoRes = service.getMerchantUserByPage(page, merchantUserVo);
//			List<MerchantUserVo> ooList = merchantUserPageVoRes.getMerchantUserVoList();
//			if( ooList != null && ooList.size() > 0 ){
//				for( MerchantUserVo oo : ooList ){
//					System.out.println(JSON.toJSONString(oo));
//				}
//			}else{
//				System.out.println("null data");
//			}
//			
			// 登录
//			System.out.println(JSON.toJSONString(service.login("zhaoky", "666888")));
			
			// 校验token
//			System.out.println("校验token = "+service.tokenCheck(null, "582805046033584128", 100000005));
			
			// 
//			List<String> merchantIdList = new ArrayList<String>();
//			merchantIdList.add("0156ECE38000");
//			merchantIdList.add("0156F4838000");
//			merchantIdList.add("01570FB38000");
//			List<MerchantUserVo> ooList = service.getMerchantUserByRoleIdAndMerchantIds(100000000, merchantIdList);
//			if( ooList != null && ooList.size() > 0 ){
//				for( MerchantUserVo oo : ooList ){
//					System.out.println(JSON.toJSONString(oo));
//				}
//			}else{
//				System.out.println("null data");
//			}
			System.out.println(service.queryErrorCount("anhui", "05DA85238000", 32886));
			transport.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
