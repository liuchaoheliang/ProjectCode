/**
 * Project Name:coremodule-merchant
 * File Name:QrcodeDownUtil.java
 * Package Name:com.froad.cbank.coremodule.module.normal.merchant.utils
 * Date:2016-1-6下午02:02:26
 * Copyright (c) 2016, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.bank.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;

import com.froad.cbank.coremodule.framework.common.util.scp.ScpConfig;
import com.froad.cbank.coremodule.framework.common.util.scp.ScpUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.thrift.vo.OutletVo;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;

/**
 * ClassName:QrcodeDownUtil
 * Reason:	 TODO ADD REASON.
 * Date:     2016-1-6 下午02:02:26
 * @author   wufei
 * @version  
 * @see 	 
 */
public class QrcodeDownUtil {
	
	/**
	 * 链接scp服务
	 * @param cfg
	 * @return
	 * @throws Exception
	*/
	private static Connection connectServer(ScpConfig cfg) throws Exception{
		boolean isAuthed = false;
		Connection scpConnection = null;
		if(cfg.getPort()==0){
			scpConnection = new Connection(cfg.getIp());
		}else{
			scpConnection = new Connection(cfg.getIp(), cfg.getPort());
		}
		scpConnection.connect();
		isAuthed = scpConnection.authenticateWithPassword(cfg.getUserName(), cfg.getPassWord());
		if(!isAuthed){
			throw new Exception("connect auth failed!");
		}
		return scpConnection;
	}
	
	/**
	 * 
	 * qrcodeDown:(下载二维码图片).
	 *
	 * @author wufei
	 * 2016-1-6 下午02:16:00
	 * @param qrcodeUrl
	 * @throws InterruptedException
	 *
	 */
	public static void qrcodeDown(String qrcodeUrl,HttpServletResponse response,ModelMap model,OutletVo outletVo,String type) throws InterruptedException{
		SCPClient scpClient = null;
		Connection scpConnection = null;
		String dirImage = "";
		try {
			scpConnection = connectServer(Constants.SCPCONFIG);
			scpClient = scpConnection.createSCPClient();
			
			String qrCodeLocal = Constants.getQrocdImgPath(); 
			String qURL = URLDecoder.decode(qrcodeUrl, "UTF-8");  
			
			LogCvt.info("qURL :"+qrCodeLocal + qURL);
			scpClient.get(qrCodeLocal + qURL,Constants.getImgLocalUrl());
			dirImage = qURL.substring(qURL.lastIndexOf("/") + 1);
			downFile(Constants.getImgLocalUrl()+"/"+dirImage, response,outletVo.getOutletName(),type);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error("Scp 异常： "+e.getMessage(), e);
		}finally{
			if(scpConnection!=null){
				scpConnection.close();  //关闭scp链接
				Thread.sleep(6000);
				deleteDirectory(Constants.getImgLocalUrl()+"/"+dirImage);
			}
		}
		
		
	}
	
	public static String qrcodeDownTemp(String qrcodeUrl,HttpServletResponse response,ModelMap model) throws InterruptedException{
		SCPClient scpClient = null;
		Connection scpConnection = null;
		String dirImage = "";
		try {
			scpConnection = connectServer(Constants.SCPCONFIG);
			scpClient = scpConnection.createSCPClient();
			
			String qrCodeLocal = Constants.getQrocdImgPath(); 
			String qURL = URLDecoder.decode(qrcodeUrl, "UTF-8");  
			
			LogCvt.info("qURL :"+qrCodeLocal + qURL);
			scpClient.get(qrCodeLocal + qURL,Constants.getImgLocalUrl());
			dirImage = qURL.substring(qURL.lastIndexOf("/") + 1);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error("Scp 异常： "+e.getMessage(), e);
		}
		return dirImage;
		
		
	}
	
	/**
	* 二维码图片文件流输出
	* @param path
	* @param res
	* @throws IOException 
	* @throws NumberFormatException 
	*/
	public static void downFile(String path,HttpServletResponse res,String outletName,String type) throws NumberFormatException, IOException{	
		//ImgZipUtil imgCom = new ImgZipUtil(path);
		// 二维码规格放大
		//imgCom.resizeFix(Integer.parseInt(Constants.get("merchant.largeW")),Integer.parseInt(Constants.get("merchant.largeH")),path);
		try {
			// path是指欲下载的文件的路径。
			File file = new File(path);
			String filename = file.getName();
			if(type.equals("0")){  //保存二维码，以"该门店名称+二维码"文字命名
				filename = outletName+"二维码.png";
			}else if(type.equals("1")){ ////保存完整图片，以"门店名称"命名
				filename = outletName+filename.substring(filename.indexOf("."));
			}
			LogCvt.info("outletName:"+outletName+"filename:"+filename);
			res.setContentType("application/x-download;UTF-8");
			res.setHeader("Content-Disposition", "attachment;filename="+new String(filename.getBytes("GBK"),"ISO-8859-1"));
				
			FileInputStream fis = new FileInputStream(file);
			OutputStream os = res.getOutputStream();
			res.addHeader("Content-Length", file.length() + "");
			byte[] bytes=new byte[1024*1024];	
			int len;				
			while((len = fis.read( bytes , 0, 1024*1024)) != -1){
				os.write(bytes, 0, len); 
			}	
			LogCvt.info("下载成功！");
			os.close();
			fis.close();
		}catch(Exception e) {
			LogCvt.error("图片下载异常： "+e.getMessage(), e);
		}
	}
	
	private static boolean deleteDirectory(String fileName) {  
		File file = new File(fileName);  
			if (file.exists()) {  
			 file.delete();  
				LogCvt.info("删除单个文件： "+ fileName + "成功！");
				return true;  
			 } else {  
				LogCvt.info("删除单个文件： "+ fileName + "失败！");
				return false;  
			 }    
	}
	
	//合成整张图片
	public static  String qrcodeMixed(OutletVo outletVo,HttpServletResponse response,ModelMap model,String clientId){
		String qrcodeUrlScp = "";
		String baseImageLocal =  Constants.getBaseImgPath()+clientId+".png";
		LogCvt.info("底图路径："+baseImageLocal);
		BufferedImage baseImage = ImageUtil.loadImageLocal(baseImageLocal); //底图
		LogCvt.info("baseImage："+baseImage);
		String localFilePath = "";
		String qrcodeName = "" ;
		String tmpdir = "";
		try {
			tmpdir = Constants.getImgLocalUrl();
			
			qrcodeName = UUID.randomUUID().toString() + ".png"; //合成图片的名字
			localFilePath = tmpdir+"/"+qrcodeName ;
			
			String dirImage = qrcodeDownTemp(outletVo.getQrcodeUrl(), response, model);
			LogCvt.info("二维码路径："+tmpdir+"/"+dirImage);
			BufferedImage qrcodeImage = ImageUtil.loadImageLocal(tmpdir+"/"+dirImage); //二维码图片
			LogCvt.info("qrcodeImage："+qrcodeImage);
			
			LogCvt.info("门店名称："+outletVo.getOutletFullname());
			ImageUtil.writeImageLocal(localFilePath, ImageUtil.modifyImage(baseImage, qrcodeImage, outletVo.getOutletName()));
			LogCvt.info("合成图片路径："+localFilePath);
			
	
			ScpUtil.uploadFile(localFilePath,new String(qrcodeName.getBytes("UTF-8"),"UTF-8"),Constants.getScpQrcodeUploadPath(), Constants.SCPCONFIG) ;
			downFile(localFilePath, response,outletVo.getOutletName(),"1");
			
			qrcodeUrlScp = Constants.getScpQrcodeImgPath()+qrcodeName;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
				deleteDirectory(localFilePath);
			}
		return qrcodeUrlScp;
		}
	
}
