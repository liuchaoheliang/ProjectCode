package com.froad.action.api.servlet;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
 

public class UploadServlet extends HttpServlet {
	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;
	private String filePath;    // 文件存放目录  
    private String tempPath;    // 临时文件目录  
    private final int flushSize = 1024 * 512; // 缓冲区大小
	private final long fileSize = 100 * 1024 * 1024; // 文件最大大小
	
    // 初始化  
    public void init(ServletConfig config) throws ServletException  
    {  
        super.init(config);  
        // 从配置文件中获得初始化参数  
        filePath = config.getInitParameter("filepath");  
        tempPath = config.getInitParameter("temppath");  
 
        ServletContext context = getServletContext();  
 
        filePath = context.getRealPath(filePath);  
        tempPath = context.getRealPath(tempPath);  
        File file = new File(filePath);
		if (!file.exists()) {
			file.mkdir();
		}
		file = new File(tempPath);
		if (!file.exists()) {
			file.mkdir();
		}
        System.out.println("文件存放目录、临时文件目录准备完毕 ..."+filePath);  
    }  
      
    // doPost  
    public void doPost(HttpServletRequest req, HttpServletResponse res)  
        throws IOException, ServletException  
    {
    	res.setContentType("text/plain;charset=gbk");  
        PrintWriter pw = res.getWriter();  
    	// 判断from表单的enctype属性值是否设置为multipart/form-data
		boolean isMultipart = FileUpload.isMultipartContent(req );
		if (!isMultipart) {
			return;
		}
		String username =req.getParameter("username");
		
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 设置缓冲区大小
			factory.setSizeThreshold(flushSize);
			factory.setRepository(new File(tempPath));
			// 创建FileUpload对象
			FileUpload upload = new FileUpload(factory);
			// 设置文件最大大小
			upload.setSizeMax(fileSize);
			List items = upload.parseRequest(req );
			Iterator iter = items.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				// 判断是否是file表单元素
				if (!item.isFormField()) {
					String name = item.getName();//获得文件路径及名称
					name = name.substring(name.lastIndexOf(File.separator) + 1,
							name.length());//获得文件名
					String fileType = name.substring(name.indexOf("."));//获得文件后缀名
					String filename = getFileName();//随机产生一个文件名防止文件名重复
					long size = item.getSize();
					//如果文件名不存在 则不处理
					if ((name == null || name.equals("")) && size == 0) {
						continue;
					}
					item.write(new File(filePath +"\\" + filename + fileType));
				} else {
					//获得文件描述
					username = item.getString("UTF-8");
					System.out.println(username);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		 System.out.println("从hauli完毕");
		if(!"FFT".equals(username)){
			pw.println(false); 
		}else{
	        pw.println(true); 
		}
    } 
    
    
    private String getFileName() {
		//根据时间产生文件名
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmssms");
		return df.format(new Date());
	}
 
    // 处理表单内容  
    private void processFormField(FileItem item, PrintWriter pw)  
        throws Exception  
    {  
        String name = item.getFieldName();  
        String value = item.getString();          
        pw.println(name + " : " + value + "\r\n");  
    }  
      
    // 处理上传的文件  
    private void processUploadFile(FileItem item, PrintWriter pw)  
        throws Exception  
    {  
        // 此时的文件名包含了完整的路径，得注意加工一下  
        String filename = item.getName();         
        System.out.println("完整的文件名：" +filePath + "/" + filename);  
        int index = filename.lastIndexOf("\\");  
        filename = filename.substring(index + 1, filename.length());  
 
        long fileSize = item.getSize();  
 
        if("".equals(filename) && fileSize == 0)  
        {             
            System.out.println("文件名为空 ...");  
            return;  
        }  
 
        File uploadFile = new File(filePath + "/" + filename);  
        item.write(uploadFile);  
        pw.println(filename + " 文件保存完毕 ...");  
        pw.println("文件大小为 ：" + fileSize + "\r\n");  
    }  
      
    // doGet  
    public void doGet(HttpServletRequest req, HttpServletResponse res)  
        throws IOException, ServletException  
    {  
        doPost(req, res);  
    }  
}
