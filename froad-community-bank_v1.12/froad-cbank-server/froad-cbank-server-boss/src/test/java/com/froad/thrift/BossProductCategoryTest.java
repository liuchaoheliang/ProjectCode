package com.froad.thrift;

import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.froad.thrift.service.BossProductCategoryService;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PlatType;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.product.BossProductCategoryInfoRes;
import com.froad.thrift.vo.product.BossProductCategoryListRes;
import com.froad.thrift.vo.product.BossProductCategoryVo;

public class BossProductCategoryTest {
	public static void main(String[] args) throws TException {
		//本地测试
		TSocket transport = new TSocket("127.0.0.1", 16001);
		//TSocket transport = new TSocket("10.24.248.187", 16001);
		transport.open();
		TBinaryProtocol protocol = new TBinaryProtocol(transport);
		TMultiplexedProtocol multiplexedProtocol = new TMultiplexedProtocol(protocol, BossProductCategoryService.class.getSimpleName());
		BossProductCategoryService.Iface productCategoryService = new BossProductCategoryService.Client(multiplexedProtocol);
		//testFindAll(productCategoryService);
		//getBossProductCategoryById(productCategoryService);
		//addBossProductCategoryVo(productCategoryService);
		//updateBossProductCategoryVo(productCategoryService);
		export(productCategoryService);
	}
	
	//导出
	public static ExportResultRes export(BossProductCategoryService.Iface iface) throws TException{
		OriginVo originVo = new OriginVo();
		originVo.setClientId(null);
		originVo.setDescription("导出");
		originVo.setOperatorId(100000071l);
		originVo.setOperatorIp("127.0.0.1");
		originVo.setPlatType(PlatType.boss);
		originVo.setRoleId("liuyanyun");
		
		String ExportType = "2";
		String clientId="chongqing";
		long categoryId = 100000045;
		
		ExportResultRes res = iface.productCategoryDetailExport(originVo, ExportType, clientId, categoryId);
		
		System.err.println(res.getUrl());
		System.err.println(res.getResultVo());
		
		
		return res;
	}
	
	/** 测试查询商品分类列表*/
	public static void testFindAll(BossProductCategoryService.Iface iface) throws TException{
		String clientId = "anhui";
		boolean iscludeDiable = true;
		boolean isMall = false;
		OriginVo originVo = new OriginVo();
		originVo.setClientId(clientId);
		originVo.setDescription("查询商品分类列表");
		originVo.setOperatorId(10001);
		originVo.setOperatorIp("127.0.0.1");
		originVo.setPlatType(PlatType.boss);
		originVo.setRoleId("liuyanyun");
		
		BossProductCategoryListRes listRes = iface.findAll(originVo, clientId, iscludeDiable, isMall);
		if (listRes != null && !"".equals(listRes)) {
			List<BossProductCategoryVo> list = listRes.getVoList();
			int count = 0;
			System.out.println(listRes.getResultVo().getResultCode());
			for (BossProductCategoryVo vo : list) {
				count ++;
				System.out.println("第" + count + "条" + vo);
			}
			
		} else {
			System.out.println("查询商品分类列表失败！");
		}
	}
	
	/** 测试查询商品分类信息*/
	public static void getBossProductCategoryById(BossProductCategoryService.Iface iface) throws TException{
		long id = 100000112;
		String clientId = "anhui";
		OriginVo originVo = new OriginVo();
		originVo.setClientId("anhui");
		originVo.setDescription("查询商品分类信息");
		originVo.setOperatorId(10001);
		originVo.setOperatorIp("127.0.0.1");
		originVo.setPlatType(PlatType.boss);
		originVo.setRoleId("liuyanyun");
		
		BossProductCategoryInfoRes infoRes = iface.getBossProductCategoryById(originVo, id, clientId);
		
		if (infoRes != null && !"".equals(infoRes)) {
			System.out.println(infoRes.getResultVo().getResultCode());
			System.out.println(infoRes.getVo());
		}else {
			System.out.println("查询商品分类信息失败");
		}
	}
	
	/** 测试新增商品分类 */
	public static void addBossProductCategoryVo(BossProductCategoryService.Iface iface) throws TException{
		BossProductCategoryVo vo = new BossProductCategoryVo();
		vo.setClientId("anhui");
		vo.setIcoUrl("c://picture/images/lover.jpg");
		vo.setIsEnable(true);
		vo.setName("交通旅游");
		vo.setOrderValue((short)7);
		vo.setParentId(100000111);
		vo.setTreePath("");
		
		OriginVo originVo = new OriginVo();
		originVo.setClientId("anhui");
		originVo.setDescription("新增商品分类");
		originVo.setOperatorId(10001);
		originVo.setOperatorIp("127.0.0.1");
		originVo.setPlatType(PlatType.boss);
		originVo.setRoleId("liuyanyun");
		
		ResultVo resultVo = iface.addBossProductCategoryVo(originVo, vo);
		
		if (resultVo != null) {
			System.out.println(resultVo.getResultDesc());
		}else {
			System.out.println("新增商品分类失败");
		}
	}
	
	/** 测试修改商品分类 */
	public static void updateBossProductCategoryVo(BossProductCategoryService.Iface iface) throws TException {
		BossProductCategoryVo vo = new BossProductCategoryVo();
		vo.setId(100000112);
		vo.setClientId("anhui");
		vo.setIcoUrl("c://images/picture/lover.jpg");
		vo.setName("天天本草");
		vo.setOrderValue((short)9);
		vo.setTreePath("100000111");
		vo.setIsEnable(false);
		
		OriginVo originVo = new OriginVo();
		originVo.setClientId("anhui");
		originVo.setDescription("修改商品分类");
		originVo.setOperatorId(10001);
		originVo.setOperatorIp("127.0.0.1");
		originVo.setPlatType(PlatType.boss);
		originVo.setRoleId("liuyanyun");
		
		ResultVo resultVo = iface.updateBossProductCategoryVo(originVo, vo);
		
		if (resultVo != null) {
			System.out.println(resultVo.getResultDesc());
		}else {
			System.out.println("修改商品分类失败");
		}
		
	}
}
