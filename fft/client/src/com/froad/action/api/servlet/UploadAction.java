package com.froad.action.api.servlet;

import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileOutputStream;  
import java.io.IOException;
import java.io.InputStream;  
import java.io.OutputStream;  
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
  
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;  
  
import com.froad.action.api.command.FroadAPICommand;
import com.opensymphony.xwork2.ActionSupport;  
  
public class UploadAction extends ActionSupport {  
	
	private Logger logger = Logger.getLogger(UploadAction.class);
	
    // 手机型号  
    private String phoneModel; 
      
    //其他信息   用户信息  手机信息
    private String info; 
    
    //
    private String type; 
      
    // myFile属性用来封装上传的文件  
    private File myFile;  
      
    // myFileContentType属性用来封装上传文件的类型  
    private String myFileContentType;  
  
    // myFileFileName属性用来封装上传文件的文件名  
    private String myFileFileName;  
  
    public String getPhoneModel() {
		return phoneModel;
	}

	public void setPhoneModel(String phoneModel) {
		this.phoneModel = phoneModel;
	}

	//获得myFile值  
    public File getMyFile() {  
        return myFile;  
    }  
  
    //设置myFile值  
    public void setMyFile(File myFile) {  
        this.myFile = myFile;  
    } 
  
    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	//获得myFileContentType值  
    public String getMyFileContentType() {  
        return myFileContentType;  
    }  
  
    //设置myFileContentType值  
    public void setMyFileContentType(String myFileContentType) {  
        this.myFileContentType = myFileContentType;  
    }  
  
    //获得myFileFileName值  
    public String getMyFileFileName() {
    	//根据时间产生文件名
		SimpleDateFormat df = new SimpleDateFormat("HHmmss");
		return df.format(new Date()) + "_" + type + ".txt";
    }  
  
    //设置myFileFileName值  
    public void setMyFileFileName(String myFileFileName) {
        this.myFileFileName = myFileFileName;
    }  
  
    public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
	public void upLoadexecute() throws IOException{
		info = "客户端文件上传   phoneModel: " + phoneModel + "  type:" + type + "  info:" +info + "\n";
		logger.info(info);
		
		PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
		
		try {
			
			if(myFile.length() < 1){
				printWriter.append("false");
				return;
			}
			
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			myExecute(FroadAPICommand.logPath + "/" + df.format(new Date()));
			
			printWriter.append("true");
		} catch (Exception e) {
			logger.error("上传文件异常", e);
			printWriter.append("false");
		} finally {
			printWriter.close();
		}
		
		
	}

	
	/**
	  * 方法描述：
	  * @param: uploadPath 设置上传文件目录  
	  * @return: 
	  * @version: 1.0
	  * @author: 何庆均 heqingjun@f-road.com.cn
	  * @update:
	  * @time: 2013-5-3 下午1:00:25
	  */
	public void myExecute(String uploadPath) throws Exception {  
		logger.info(" path:" + uploadPath  + " file:" + this.getMyFileFileName());
		File file = new File(uploadPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		
        //基于myFile创建一个文件输入流  
        InputStream is = new FileInputStream(myFile);  
        
          
        // 设置目标文件  
        File toFile = new File(uploadPath, this.getMyFileFileName());  
          
        // 创建一个输出流  
        OutputStream os = new FileOutputStream(toFile);  
  
        //设置缓存  
        byte[] buffer = new byte[1024];  
  
        int length = 0;  
        
        os.write(info.getBytes());
        //读取myFile文件输出到toFile文件中  
        while ((length = is.read(buffer)) > 0) {  
            os.write(buffer, 0, length);  
        }  
        logger.info("上传路径："+toFile.getAbsolutePath());  
        logger.info("上传手机型号："+phoneModel);  
        logger.info("上传其他信息："+info);  
        //关闭输入流  
        is.close();  
        os.flush();
        //关闭输出流  
        os.close();  
          
    }  
  
} 