package com.froad.thrift;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.alibaba.fastjson.JSON;
import com.froad.thrift.service.MerchantOutletPhotoService;
import com.froad.thrift.vo.MerchantOutletPhotoVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PlatType;

public class MerchantOutletPhotoTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			TSocket transport = new TSocket("localhost", 15201);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			
			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,"MerchantOutletPhotoService");
			MerchantOutletPhotoService.Client service = new MerchantOutletPhotoService.Client(mp);

			transport.open();


			// 新增
//			MerchantOutletPhotoVo merchantOutletPhotoVo = new MerchantOutletPhotoVo(); 
//			merchantOutletPhotoVo.setMerchantId("10000021");
//			merchantOutletPhotoVo.setOutletId("10002154");
//			merchantOutletPhotoVo.setIsDefault(false);
//			merchantOutletPhotoVo.setTitle("nike logo");
//			merchantOutletPhotoVo.setSource("原图片");
//			merchantOutletPhotoVo.setLarge("大图片");
//			merchantOutletPhotoVo.setMedium("中图片");
//			merchantOutletPhotoVo.setThumbnail("缩略图");
//			System.out.println(service.addMerchantOutletPhoto(merchantOutletPhotoVo));
			
			// 批量新增
			List<MerchantOutletPhotoVo> merchantOutletPhotoVoList = new ArrayList<MerchantOutletPhotoVo>();
			MerchantOutletPhotoVo merchantOutletPhotoVo = new MerchantOutletPhotoVo(); 
			merchantOutletPhotoVo.setMerchantId("01B863EE0000");
			merchantOutletPhotoVo.setOutletId("02386AC38000");
			merchantOutletPhotoVo.setIsDefault(true);
			merchantOutletPhotoVo.setTitle("nike logo");
			merchantOutletPhotoVo.setSource("nike原图片");
			merchantOutletPhotoVo.setLarge("nike大图片");
			merchantOutletPhotoVo.setMedium("nike中图片");
			merchantOutletPhotoVo.setThumbnail("nike缩略图");
			merchantOutletPhotoVoList.add(merchantOutletPhotoVo);
			
			MerchantOutletPhotoVo merchantOutletPhotoVo1 = new MerchantOutletPhotoVo(); 
			merchantOutletPhotoVo1.setMerchantId("01B863EE0000");
			merchantOutletPhotoVo1.setOutletId("02386AC38000");
			merchantOutletPhotoVo1.setIsDefault(true);
			merchantOutletPhotoVo1.setTitle("nike logo1");
			merchantOutletPhotoVo1.setSource("nike原图片1");
			merchantOutletPhotoVo1.setLarge("nike大图片1");
			merchantOutletPhotoVo1.setMedium("nike中图片1");
			merchantOutletPhotoVo1.setThumbnail("nike缩略图1");
			merchantOutletPhotoVoList.add(merchantOutletPhotoVo1);
			
			
//			merchantOutletPhotoVo = new MerchantOutletPhotoVo(); 
//			merchantOutletPhotoVo.setMerchantId("10000021");
//			merchantOutletPhotoVo.setOutletId("10002154");
//			merchantOutletPhotoVo.setIsDefault(false);
//			merchantOutletPhotoVo.setTitle("361 logo");
//			merchantOutletPhotoVo.setSource("361原图片");
//			merchantOutletPhotoVo.setLarge("361大图片");
//			merchantOutletPhotoVo.setMedium("361中图片");
//			merchantOutletPhotoVo.setThumbnail("361缩略图");
//			merchantOutletPhotoVoList.add(merchantOutletPhotoVo);
			
//			service.addMerchantOutletPhoto(merchantOutletPhotoVo);
//			System.out.println(JSON.toJSONString(service.addMerchantOutletPhotoByBatch(merchantOutletPhotoVoList)));
			OriginVo originVo = new OriginVo();
			originVo.setPlatType(PlatType.bank);
			originVo.setOperatorId(100000000);
			originVo.setOperatorIp("192.168.19.105");
			originVo.setDescription("");
			
			
//			service.addMerchantOutletPhoto(originVo,merchantOutletPhotoVo);
			System.out.println(service.saveMerchantOutletPhoto(originVo, merchantOutletPhotoVoList));
			;
//			System.out.println(JSON.toJSONString(service.addMerchantOutletPhotoByBatch(originVo,merchantOutletPhotoVoList)));
			
			// 删除
//			boolean result = service.deleteMerchantRoleResource(originVo,"100021100541");
//			System.out.println("删除操作结果 " + result);
			
			// 修改
//			MerchantOutletPhotoVo merchantOutletPhotoVo = new MerchantOutletPhotoVo(); 
//			merchantOutletPhotoVo.setId(100000000);
//			merchantOutletPhotoVo.setMerchantId("10000021");
//			merchantOutletPhotoVo.setOutletId("10002154");
//			merchantOutletPhotoVo.setIsDefault(false);
//			merchantOutletPhotoVo.setTitle("adidas logo");
//			merchantOutletPhotoVo.setSource("adidas原图片");
//			merchantOutletPhotoVo.setLarge("adidas大图片");
//			merchantOutletPhotoVo.setMedium("adidas中图片");
//			merchantOutletPhotoVo.setThumbnail("adidas缩略图");
//			boolean result = service.updateMerchantOutletPhoto(merchantOutletPhotoVo);
//			System.out.println("修改操作结果 " + result);
			
			// 查一条
//			String _id = ("100021"+"_"+"100544");
//			MerchantRoleResourceVo merchantRoleResourceVo = service.getMerchantRoleResource(_id);
//			System.out.println(JSON.toJSONString(merchantRoleResourceVo));
			
			// 修改
//			OutletCommentVo outletCommentVo = new OutletCommentVo();
//			outletCommentVo.setId("55136d760d546e4db022007e");
//			outletCommentVo.setCreateTime(new Date().getTime());
//			outletCommentVo.setOrderValue(1);
//			outletCommentVo.setStarLevel(2);
//			outletCommentVo.setEnvironmentPoint(3);
//			outletCommentVo.setServerPoint(4);
//			outletCommentVo.setCommentDescription("酒水太贵了，领舞的妹子不会带气氛，还不如多花点时间到市区");
//			boolean result = service.updateOutletComment(outletCommentVo);
//			System.out.println("修改操作结果 " + result);
			
			// 分页查询
//			PageVo page = new PageVo();
//			page.setPageNumber(1);
//			page.setPageSize(20);
//			OutletCommentVo outletCommentVo = new OutletCommentVo(); 
//			outletCommentVo.setMerchantId("10021547");
//			outletCommentVo.setOutletId("2010057");
//			outletCommentVo.setMemberCode("200146"); // 200148
//			OutletCommentPageVoRes res = service.getOutletCommentByPage(page, outletCommentVo);
//			System.out.println(JSON.toJSONString(res));
			
			transport.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
