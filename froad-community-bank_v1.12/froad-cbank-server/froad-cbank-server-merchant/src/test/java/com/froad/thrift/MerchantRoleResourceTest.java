package com.froad.thrift;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.alibaba.fastjson.JSON;
import com.froad.thrift.service.MerchantRoleResourceService;
//import com.froad.thrift.service.MerchantAccountService;
import com.froad.thrift.service.OutletCommentService;
import com.froad.thrift.vo.MerchantRoleResourceVo;
import com.froad.thrift.vo.OutletCommentPageVoRes;
//import com.froad.thrift.service.HelloService;
//import com.froad.thrift.service.UserService;
import com.froad.thrift.vo.OutletCommentVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResourceVo;

public class MerchantRoleResourceTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			String host= "10.43.2.3";
			int port = 0;
			host = "127.0.0.1";
			port = 15201;
			TSocket transport = new TSocket(host, port);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			
			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,"MerchantRoleResourceService");
			MerchantRoleResourceService.Client service = new MerchantRoleResourceService.Client(mp);

			transport.open();


			// 新增
			MerchantRoleResourceVo merchantRoleResourceVo = new MerchantRoleResourceVo(); 
			merchantRoleResourceVo.set_id("anhui_100000000");
			List<ResourceVo> resources = new ArrayList<ResourceVo>();
			
			ResourceVo resource = new ResourceVo();
			resource.setIcon("fa fa-folder-o");
			resource.setParent_id(0);
			resource.setResource_id(100000000);
			resource.setResource_name("商户管理");
			resource.setResource_type("1");
			resource.setResource_url("");
			resource.setTree_path("100000000");
			resources.add(resource);
			
			resource = new ResourceVo();
			resource.setIcon("");
			resource.setParent_id(100000000);
			resource.setResource_id(100000001);
			resource.setResource_name("基本信息");
			resource.setResource_type("1");
			resource.setResource_url("businessInfor.html");
			resource.setApi("/outlet/info");
			resource.setTree_path("100000001");
			resources.add(resource);
			
			resource = new ResourceVo();
			resource.setIcon("");
			resource.setParent_id(100000000);
			resource.setResource_id(100000002);
			resource.setResource_name("门店管理");
			resource.setResource_type("1");
			resource.setResource_url("outletList.html");
			resource.setApi("/outlet/list;/outlet/add;/outlet/del;/outlet/qc;/common/bank;/img/upload;/outlet/ld");
			resource.setTree_path("100000002");
			resources.add(resource);
			
			resource = new ResourceVo();
			resource.setIcon("fa fa-gift");
			resource.setParent_id(100000000);
			resource.setResource_id(200000000);
			resource.setResource_name("商品管理");
			resource.setResource_type("1");
			resource.setResource_url("");
			resource.setTree_path("200000000");
			resources.add(resource);
			
			resource = new ResourceVo();
			resource.setIcon("");
			resource.setParent_id(200000000);
			resource.setResource_id(200000000);
			resource.setResource_name("商品查询");
			resource.setResource_type("1");
			resource.setResource_url("productList.html");
			resource.setApi("/product/list;/product/tth;/product/pd;/product/upd;/product/ld");
			resource.setTree_path("200000001");
			resources.add(resource);
			
			resource = new ResourceVo();
			resource.setIcon("");
			resource.setParent_id(200000000);
			resource.setResource_id(200000002);
			resource.setResource_name("商品发布");
			resource.setResource_type("1");
			resource.setResource_url("addProduct.html");
			resource.setApi("/img/upload;/product/qc;/product/mdy;/product/add");
			resource.setTree_path("200000002");
			resources.add(resource);
			
			resource = new ResourceVo();
			resource.setIcon("");
			resource.setParent_id(200000000);
			resource.setResource_id(200000003);
			resource.setResource_name("查看名优特惠商品");
			resource.setResource_type("1");
			resource.setResource_url("famousProductList.html");
			resource.setApi("/product/list");
			resource.setTree_path("200000003");
			resources.add(resource);
			
			resource = new ResourceVo();
			resource.setIcon("fa fa-file-text");
			resource.setParent_id(0);
			resource.setResource_id(300000000);
			resource.setResource_name("交易管理");
			resource.setResource_type("1");
			resource.setResource_url("");
			resource.setTree_path("300000000");
			resources.add(resource);
			
			resource = new ResourceVo();
			resource.setIcon("");
			resource.setParent_id(300000000);
			resource.setResource_id(300000001);
			resource.setResource_name("团购提货");
			resource.setResource_type("1");
			resource.setResource_url("deliveryVerification.html");
			resource.setApi("/order/lsd;/order/qcl;/order/su");
			resource.setTree_path("300000001");
			resources.add(resource);
			
			resource = new ResourceVo();
			resource.setIcon("");
			resource.setParent_id(300000000);
			resource.setResource_id(300000002);
			resource.setResource_name("订单查询");
			resource.setResource_type("1");
			resource.setResource_url("orderList.html");
			resource.setApi("/common/downQcode;/order/qfg;/order/gogd;/order/ship");
			resource.setTree_path("300000002");
			resources.add(resource);
			
			resource = new ResourceVo();
			resource.setIcon("");
			resource.setParent_id(300000000);
			resource.setResource_id(300000003);
			resource.setResource_name("商户评价管理");
			resource.setResource_type("1");
			resource.setResource_url("merchantReviews.html");
			resource.setApi("/comment/qoc;/comment/roc;/comment/pcd");
			resource.setTree_path("300000003");
			resources.add(resource);
			
			resource = new ResourceVo();
			resource.setIcon("");
			resource.setParent_id(300000000);
			resource.setResource_id(300000004);
			resource.setResource_name("商品评价管理");
			resource.setResource_type("1");
			resource.setResource_url("productReviews.html");
			resource.setApi("/comment/qpcl;/comment/rpy;/comment/qpd;/comment/rpyb");
			resource.setTree_path("300000004");
			resources.add(resource);
			
			resource = new ResourceVo();
			resource.setIcon("fa fa-table");
			resource.setParent_id(0);
			resource.setResource_id(400000000);
			resource.setResource_name("数据分析");
			resource.setResource_type("1");
			resource.setResource_url("");
			resource.setTree_path("400000000");
			resources.add(resource);
			
			resource = new ResourceVo();
			resource.setIcon("");
			resource.setParent_id(400000000);
			resource.setResource_id(400000001);
			resource.setResource_name("商户营业报表");
			resource.setResource_type("1");
			resource.setResource_url("");
			resource.setTree_path("400000001");
			resources.add(resource);
			
			resource = new ResourceVo();
			resource.setIcon("");
			resource.setParent_id(400000000);
			resource.setResource_id(400000002);
			resource.setResource_name("商品销售报表");
			resource.setResource_type("1");
			resource.setResource_url("");
			resource.setTree_path("400000002");
			resources.add(resource);
			
			resource = new ResourceVo();
			resource.setIcon("fa fa-user");
			resource.setParent_id(0);
			resource.setResource_id(500000000);
			resource.setResource_name("用户管理");
			resource.setResource_type("1");
			resource.setResource_url("");
			resource.setTree_path("500000000");
			resources.add(resource);
			
			resource = new ResourceVo();
			resource.setIcon("");
			resource.setParent_id(500000000);
			resource.setResource_id(500000001);
			resource.setResource_name("用户查询");
			resource.setResource_type("1");
			resource.setResource_url("userList.html");
			resource.setApi("/outlet/qall;/outlet/mou;/outlet/moup;/outlet/qall;/outlet/mou;/outlet/moua;/outlet/moup;/outlet/mou");
			resource.setTree_path("500000001");
			resources.add(resource);
			
			resource = new ResourceVo();
			resource.setIcon("");
			resource.setParent_id(500000000);
			resource.setResource_id(500000002);
			resource.setResource_name("角色查询");
			resource.setResource_type("1");
			resource.setResource_url("roleQueries.html");
			resource.setApi("/role/list;/role/list;/outlet/qall;/outlet/mou");
			resource.setTree_path("500000002");
			resources.add(resource);
			
			resource = new ResourceVo();
			resource.setIcon("");
			resource.setParent_id(500000000);
			resource.setResource_id(500000003);
			resource.setResource_name("密码修改");
			resource.setResource_type("1");
			resource.setResource_url("changePassword.html");
			resource.setApi("/update;/reset;/outlet/mou");
			resource.setTree_path("500000003");
			resources.add(resource);
			
			merchantRoleResourceVo.setResources(resources);
			System.out.println(service.addMerchantRoleResource(null, merchantRoleResourceVo));
			
			// 删除
//			System.out.println("删除操作结果 " + service.deleteMerchantRoleResource(null, "aihui_100000000"));
			
			// 修改
//			MerchantRoleResourceVo merchantRoleResourceVo = new MerchantRoleResourceVo(); 
//			merchantRoleResourceVo.set_id("100021"+"_"+"100544");
//			List<ResourceVo> resources = new ArrayList<ResourceVo>();
//			ResourceVo resource = new ResourceVo();
//			resource.setResource_id(901234);
//			resource.setResource_name("zy99999");
//			resource.setResource_type("f");
//			resource.setTree_path("i.j.k");
//			resource.setResource_url("iii/jjj/kkk");
//			resources.add(resource);
//			resource = new ResourceVo();
//			resource.setResource_id(123456);
//			resource.setResource_name("zy11111");
//			resource.setResource_type("h");
//			resource.setTree_path("k.l.m");
//			resource.setResource_url("kkk/lll/mmm");
//			resources.add(resource);
//			resource = new ResourceVo();
//			resource.setResource_id(234567);
//			resource.setResource_name("zy22222");
//			resource.setResource_type("i");
//			resource.setTree_path("l.m.n");
//			resource.setResource_url("lll/mmm/nnn");
//			resources.add(resource);
//			merchantRoleResourceVo.setResources(resources);
//			boolean result = service.updateMerchantRoleResource(merchantRoleResourceVo);
//			System.out.println("修改操作结果 " + result);
			
			// 查一条
//			String _id = ("anhui"+"_"+"100000000");
//			MerchantRoleResourceVo merchantRoleResourceVo = service.getMerchantRoleResource(_id);
//			System.out.println(JSON.toJSONString(merchantRoleResourceVo));
			
//			// 查列表
//			String client_id = "1001";
//			List<MerchantRoleResourceVo> result = service.getMerchantRoleResourceListByClientId(client_id);
//			System.out.println(JSON.toJSONString(result));
			
			transport.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
