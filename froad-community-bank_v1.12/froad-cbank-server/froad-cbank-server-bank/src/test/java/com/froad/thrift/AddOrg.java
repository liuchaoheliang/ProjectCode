package com.froad.thrift;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.froad.logback.LogCvt;
import com.froad.thrift.utils.AreaService;
import com.froad.thrift.utils.HttpXmlClient;
import com.froad.thrift.vo.AreaVo;
import com.froad.util.Checker;
import com.froad.util.JSonUtil;
import com.froad.util.PropertiesUtil;

/**
 * 批量导入机构信息
 *
 */
public class AddOrg {

	static Map<String,String> areaMap = new HashMap<String, String>();
	static String clientId = "taizhou"; 
	static String supportIp = "10.24.248.187"; //查询cb_area表数据，调用外围服务器Ip
	static Integer supportPort = 15701;//调用外围服务器Port
	
	
	public static void main(String[] args) throws Exception {
		
		String userId="";
		String token ="";
		String filename="机构信息模板.xlsx";
		
		//1.-----login-----------------------------
		String preUrl = "http://10.24.248.180:11101";//coreModule地址
		String url = preUrl+"/bank/login/ve";//api
		Map<String, String> params = new HashMap<String, String>();  
		params.put("userName", "admin");  
		params.put("password", "bcb15f821479b4d5772bd0ca866c00ad5f926e3580720659cc80d39c9d09802a");  
		    
		
		String xml = HttpXmlClient.post(url,userId,token,clientId, params);
		
		userId=JSonUtil.toObject(xml, Map.class).get("userId").toString();
		token=JSonUtil.toObject(xml, Map.class).get("token").toString();
		
		System.err.println("userId:"+userId+"----"+"token:"+token);
		
		
		//2.---------addOrg-------------------------
		System.out.println("----------------------Start--------------------------");
		url = preUrl+"/bank/bankOrg/ad";//api
		
		initArea();
		PropertiesUtil.load();
		//机构号	机构名	机构等级	上级机构号	区域	经度	维度	联系电话	地址
		String path = "."+File.separatorChar+"files"+File.separatorChar+filename;
		
		InputStream input = new FileInputStream(path);
		Workbook wb = null;
		if (path.endsWith("xls")) {  
            wb = new HSSFWorkbook(input);  
        } else {  
            wb = new XSSFWorkbook(input);  
        }
        Sheet sheet = wb.getSheetAt(0);
        Iterator<Row> rows = sheet.rowIterator(); //获得第一个表单的迭代器  
        String zonghang="";//取数据行中的第一行的orgCode
        
        rows.next();//表单头先读取一行
        
        int index = 0;
        //读取数据行
        while (rows.hasNext()) {
        	Row row = rows.next();
        	 
        	//取第一条数据的总行orgCode
        	index ++;
        	if(index == 1){
        		zonghang=row.getCell(0).getStringCellValue();
        	}
        	
            
            params = new HashMap<String, String>();  
    		params.put("clientId", clientId);  
    		params.put("orgName", row.getCell(1).getStringCellValue());
    		params.put("orgCode",row.getCell(0).getStringCellValue());
    		String orgLevel = row.getCell(2).getStringCellValue();
    		params.put("orgLevel",orgLevel);
    		
    		//上级机构号
    		String paraentOrgCode=row.getCell(3).getStringCellValue();
    		switch (Integer.parseInt(orgLevel)) {
	            case 1:
	            	params.put("proinceAgency","");
	        		params.put("cityAgency","");
	        		params.put("countyAgency","");
	                break;
	            case 2:
	            	params.put("proinceAgency",paraentOrgCode);
	        		params.put("cityAgency","");
	        		params.put("countyAgency","");
	                break;
	            case 3:
	            	//从mysql中查询设置到proinceAgency和cityAgency中
	            	params.put("proinceAgency",zonghang);
	        		params.put("cityAgency",paraentOrgCode);
	        		params.put("countyAgency","");
	                break;    
    		}
    		
    		
    		params.put("orgType","true");
    		
    		// 地区名称
            String areaName = row.getCell(4).getStringCellValue();
            String treePath = areaMap.get(areaName);
            if(Checker.isEmpty(treePath)){
            	LogCvt.info("异常地区无区域ID："+areaName);
            	continue;
            }
            String[] treePaths = treePath.split(",");
            if(treePaths.length==1){
            	params.put("areaCode",treePath.split(",")[0]);
            	params.put("cityCode","");
            	params.put("countyCode","");
            }else if(treePaths.length==2){
            	params.put("areaCode",treePath.split(",")[0]);
            	params.put("cityCode",treePath.split(",")[1]);
            	params.put("countyCode","");
            }else if(treePaths.length==3){
            	params.put("areaCode",treePath.split(",")[0]);
            	params.put("cityCode",treePath.split(",")[1]);
            	params.put("countyCode",treePath.split(",")[2]);
            }else{
            	params.put("areaCode","");
            	params.put("cityCode","");
            	params.put("countyCode","");
            }
    		
    		
    		params.put("address",row.getCell(8).getStringCellValue());
    		params.put("longitude",row.getCell(5)==null?"":row.getCell(5).getStringCellValue());
    		params.put("latitude",row.getCell(6)==null?"":row.getCell(6).getStringCellValue());
    		params.put("tel",row.getCell(7)==null?"":row.getCell(7).getStringCellValue());
    		params.put("isMutualAudit","false");

    		
    		System.out.println("----"+index+"------"+JSonUtil.toJSonString(params));
    		xml = "";
    		xml = HttpXmlClient.post(url, userId,token,clientId,params);
    		
    		System.err.println("返回值："+xml); 
    		
    		
        }
        System.out.println("----------------------End--------------------------");
        
        
		
		
	}
	
	
	/**
	 * 初始化区域信息
	 */
	private static void initArea(){
		
		try {
			TSocket transport = new TSocket(supportIp, supportPort);//v1.2开发环境
			
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
	
			TMultiplexedProtocol mp1 = new TMultiplexedProtocol(protocol,"AreaService");
			AreaService.Client service1 = new AreaService.Client(mp1);
			
			transport.open();
	
			AreaVo find = new AreaVo();
			find.setClientId(clientId);
			List<AreaVo> list = service1.getArea(find);
			for (AreaVo vo : list) {
				areaMap.put(vo.getName(),vo.getTreePath());
			}
			
			transport.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	
}
