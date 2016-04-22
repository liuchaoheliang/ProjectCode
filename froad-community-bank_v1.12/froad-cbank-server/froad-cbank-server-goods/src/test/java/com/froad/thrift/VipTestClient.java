package com.froad.thrift;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.alibaba.fastjson.JSONObject;
import com.froad.logic.CommonLogic;
import com.froad.logic.impl.CommonLogicImpl;
import com.froad.po.Product;
import com.froad.thrift.service.ProductCategoryService;
import com.froad.thrift.service.ProductService;
import com.froad.thrift.service.VipProductService;
import com.froad.thrift.vo.BindVipInfoVo;
import com.froad.thrift.vo.InvalidProductBatchVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.OutletProductVo;
import com.froad.thrift.vo.OutletProductVoReq;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.PlatType;
import com.froad.thrift.vo.ProductActivitiesVo;
import com.froad.thrift.vo.ProductAreaVo;
import com.froad.thrift.vo.ProductBuyLimitVo;
import com.froad.thrift.vo.ProductCategoryVo;
import com.froad.thrift.vo.ProductCommentFilterReq;
import com.froad.thrift.vo.ProductCommentVo;
import com.froad.thrift.vo.ProductExistVoReq;
import com.froad.thrift.vo.ProductFilterVoReq;
import com.froad.thrift.vo.ProductImageVo;
import com.froad.thrift.vo.ProductInfoVo;
import com.froad.thrift.vo.ProductOperateVoReq;
import com.froad.thrift.vo.ProductOutletVo;
import com.froad.thrift.vo.ProductStatusBatchVoReq;
import com.froad.thrift.vo.ProductStatusVoReq;
import com.froad.thrift.vo.ProductStoreFilterVo;
import com.froad.thrift.vo.ProductVo;
import com.froad.thrift.vo.VipProductVo;
import com.froad.util.BeanUtil;
import com.froad.util.DateUtil;

public class VipTestClient {

	public static void main(String[] args) {
	    
		try {
//		    String s = "12,34,56";
//		    System.out.println(s.indexOf(",56"));
//		    System.out.println(",56".length());
//		    System.out.println(s.length());
			TSocket transport = new TSocket("localhost", 15401);
//		    TSocket transport = new TSocket("10.43.2.3", 15401);
//		    TSocket transport = new TSocket("10.43.1.9", 15401);
//		    TSocket transport = new TSocket("10.43.1.123", 15401);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);

			
			TMultiplexedProtocol mp2 = new TMultiplexedProtocol(protocol,"VipProductService");
            VipProductService.Client service2 = new VipProductService.Client(mp2);

			transport.open();
			
			PlatType platType = PlatType.findByValue(1);
			OriginVo originVo = new OriginVo();
			originVo.setPlatType(platType);
			
//			System.out.println(service2.updateVipStatus(originVo, "093F57F00000", "2"));
			
			VipProductVo vipProductVo = new VipProductVo();
			vipProductVo.setClientId("anhui");
			vipProductVo.setClientName("安徽银行");
			vipProductVo.setActivitiesName("anhuiVIP规则名称");
			vipProductVo.setVipPrice(60.0);
			vipProductVo.setStatus("0");
			vipProductVo.setCount(0);
			vipProductVo.setRemark("anhuiVIP规则名称anhuiVIP规则名称");
//			System.out.println(service2.addVipProduct(originVo, vipProductVo));
			
			List<BindVipInfoVo> bindInfos = new ArrayList<BindVipInfoVo>();
			
			BindVipInfoVo vo = new BindVipInfoVo();
			vo.setProductId("093EE9408000");
			vo.setVipLimit(5);
			vo.setVipPrice(0.5);
			bindInfos.add(vo);
			
			BindVipInfoVo vo1 = new BindVipInfoVo();
            vo1.setProductId("093EED408000");
            vo1.setVipLimit(5);
            vo1.setVipPrice(0.6);
            bindInfos.add(vo1);
            
            BindVipInfoVo vo2 = new BindVipInfoVo();
            vo2.setProductId("093EEF608000");
//            vo2.setVipLimit(5);
            vo2.setVipPrice(0.7);
            bindInfos.add(vo2);
            
            
            BindVipInfoVo vo3 = new BindVipInfoVo();
            vo3.setProductId("093EF1008000");
//            vo3.setVipLimit(5);
            vo3.setVipPrice(0.8);
            bindInfos.add(vo3);
            
			System.out.println(service2.addProductsToVipProduct(originVo, "093F57F00000", bindInfos));
			
			List<String> productIds = new ArrayList<String>();
			productIds.add("093EE9408000");
			productIds.add("093EED408000");
			productIds.add("093EEF608000");
			productIds.add("093EF1008000");
//			System.out.println(service2.removeProductsFromVipProduct(originVo, "093F57F00000", productIds));
			
			
			transport.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
