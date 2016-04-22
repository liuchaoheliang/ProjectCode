package com.froad.thrift;

import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.froad.thrift.service.BossMerchantCategoryService;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PlatType;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.merchant.BossMerchantCategoryInfoRes;
import com.froad.thrift.vo.merchant.BossMerchantCategoryListRes;
import com.froad.thrift.vo.merchant.BossMerchantCategoryVo;

public class BossMerchantCategoryTest {

	public static void main(String[] args) throws TException {
		//本地测试
		TSocket transport = new TSocket("127.0.0.1", 16001);
		//TSocket transport = new TSocket("10.24.248.187", 16001);
		transport.open();
		TBinaryProtocol protocol = new TBinaryProtocol(transport);
		TMultiplexedProtocol multiplexedProtocol = new TMultiplexedProtocol(protocol, BossMerchantCategoryService.class.getSimpleName());
		BossMerchantCategoryService.Iface merchantCategoryService = new BossMerchantCategoryService.Client(multiplexedProtocol);
		//testFindAll(merchantCategoryService);
		//getBossMerchantCategoryById(merchantCategoryService);
		//addBossMerchantCategoryVo(merchantCategoryService);
		updateBossMerchantCategoryVo(merchantCategoryService);

	}
	
	/** 测试查询商户分类列表*/
	public static void testFindAll(BossMerchantCategoryService.Iface iface) throws TException{
		String clientId = "anhui";
		boolean iscludeDiable = true;
		OriginVo originVo = new OriginVo();
		originVo.setClientId(clientId);
		originVo.setDescription("查询商户分类列表");
		originVo.setOperatorId(10001);
		originVo.setOperatorIp("127.0.0.1");
		originVo.setPlatType(PlatType.boss);
		originVo.setRoleId("liuyanyun");
		
		BossMerchantCategoryListRes listRes = iface.findAll(clientId, iscludeDiable, originVo);
		if (listRes != null && !"".equals(listRes)) {
			List<BossMerchantCategoryVo> list = listRes.getVoList();
			int count = 0;
			System.out.println(listRes.getResultVo().getResultDesc());
			for (BossMerchantCategoryVo vo : list) {
				count ++;
				System.out.println("第" + count + "条" + vo);
			}
			
		} else {
			System.out.println("查询商品分类列表失败！");
		}
	}
	
	/** 测试查询商户分类信息*/
	public static void getBossMerchantCategoryById(BossMerchantCategoryService.Iface iface) throws TException{
		long id = 100000128;
		String clientId = "chongqing";
		OriginVo originVo = new OriginVo();
		originVo.setClientId("anhui");
		originVo.setDescription("查询商户分类信息");
		originVo.setOperatorId(10001);
		originVo.setOperatorIp("127.0.0.1");
		originVo.setPlatType(PlatType.boss);
		originVo.setRoleId("liuyanyun");
		
		BossMerchantCategoryInfoRes infoRes = iface.getBossMerchantCategoryById(originVo, id, clientId);
		
		if (infoRes != null && !"".equals(infoRes)) {
			System.out.println(infoRes.getResultVo().getResultDesc());
			System.out.println(infoRes.getVo());
		}else {
			System.out.println("查询商户分类信息失败");
		}
	}
	
	/** 测试新增商户分类 */
	public static void addBossMerchantCategoryVo(BossMerchantCategoryService.Iface iface) throws TException{
		BossMerchantCategoryVo vo = new BossMerchantCategoryVo();
		vo.setClientId("chongqing");
		vo.setIcoUrl("c://picture/images/lover.jpg");
		vo.setIsEnable(true);
		vo.setName("五谷丰登");
		vo.setOrderValue((short)7);
		vo.setParentId(100000127);
		vo.setTreePath("");
		
		OriginVo originVo = new OriginVo();
		originVo.setClientId("anhui");
		originVo.setDescription("新增商户分类");
		originVo.setOperatorId(10001);
		originVo.setOperatorIp("127.0.0.1");
		originVo.setPlatType(PlatType.boss);
		originVo.setRoleId("liuyanyun");
		
		ResultVo resultVo = iface.addBossMerchantCategoryVo(originVo, vo);
		
		if (resultVo != null) {
			System.out.println(resultVo.getResultDesc());
		}else {
			System.out.println("新增商户分类失败");
		}
	}
	
	/** 测试修改商户分类 */
	public static void updateBossMerchantCategoryVo(BossMerchantCategoryService.Iface iface) throws TException {
		BossMerchantCategoryVo vo = new BossMerchantCategoryVo();
		vo.setId(100000023);
		vo.setClientId("chongqing");
		vo.setIcoUrl("c://images/picture/lover.jpg");
		vo.setName("汽车服务");
		vo.setOrderValue((short)8);
		vo.setTreePath("100000082");
		vo.setIsEnable(false);
		
		OriginVo originVo = new OriginVo();
		originVo.setClientId("chongqing");
		originVo.setDescription("修改商户分类");
		originVo.setOperatorId(10001);
		originVo.setOperatorIp("127.0.0.1");
		originVo.setPlatType(PlatType.boss);
		originVo.setRoleId("liuyanyun");
		
		ResultVo resultVo = iface.updateBossMerchantCategoryVo(originVo, vo);
		
		if (resultVo != null) {
			System.out.println(resultVo.getResultDesc());
		}else {
			System.out.println("修改商户分类失败");
		}
	}
}
