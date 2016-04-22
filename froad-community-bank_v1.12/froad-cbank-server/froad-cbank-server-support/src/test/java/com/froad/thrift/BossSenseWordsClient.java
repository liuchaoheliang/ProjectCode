package com.froad.thrift;

import java.util.List;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.alibaba.fastjson.JSONObject;
import com.froad.thrift.service.BossSenseWordsService;
import com.froad.thrift.vo.BossSenseWordsPageVoRes;
import com.froad.thrift.vo.BossSenseWordsVo;
import com.froad.thrift.vo.PageVo;

public class BossSenseWordsClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			TSocket transport = new TSocket("localhost", 8899);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			TMultiplexedProtocol mp2 = new TMultiplexedProtocol(protocol,"BossSenseWordsService");
			BossSenseWordsService.Client service2 = new BossSenseWordsService.Client(mp2);
			//-----------------------分页参数设置----------------
			PageVo page = new PageVo();
			page.setPageNumber(0);
			page.setPageSize(3);
			transport.open();
			
			BossSenseWordsVo bossSenseWordsVo =new BossSenseWordsVo();
			bossSenseWordsVo.setId(100000000l);
//			bossSenseWordsVo.setWord("些");
//			bossSenseWordsVo.setIsEnable(true);
//			System.out.println("add==========="+service2.addBossSenseWords(bossSenseWordsVo));
//			System.out.println("update==========="+service2.updateBossSenseWords(bossSenseWordsVo));
//			System.out.println("delete==========="+service2.deleteBossSenseWords(bossSenseWordsVo));
			
			/**
			 * 条件查询
			 */
			List<BossSenseWordsVo> list=null;
//			list=service2.getBossSenseWords(bossSenseWordsVo);
			/**
			 * 分页查询
			 */
			BossSenseWordsPageVoRes Pagelist =service2.getBossSenseWordsByPage(page, bossSenseWordsVo);
		    list=Pagelist.getBossSenseWordsVoList();
			
			for (BossSenseWordsVo bossSenseWordsVo2 : list) {
				System.out.println("-----------"+JSONObject.toJSONString(bossSenseWordsVo2));
			}
			
			transport.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
