package com.froad.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.froad.cbank.coremodule.normal.boss.utils.DateUtil;

public class ImportResource {
 
	public static void main(String[] args) { 
		
		//插入数据
//		insert(bossImportResource());
		//生成sql
//		sql(bossImportResource());
	}
	
	
	public static List<Resource> bossImportResource(){
		Workbook readwb = null;
		InputStream instream = null;
		try

		{ 
			instream = new FileInputStream("/home/ling/Desktop/BOSS权限资源整理.xls");

			readwb = new HSSFWorkbook(instream);

			Sheet readsheet = readwb.getSheetAt(0);

			int rsRows = readsheet.getLastRowNum();

			List<Resource> rl = new ArrayList<Resource>();
			Resource r = new Resource();
			int sb = 300000000;
			String s1 = "300000000,";
			String s2 = "";
			String s3 = "";
//			String s4 = "";
//			String s5 = "";
			long parentId1=300000000;
			long parentId2=0;
			long parentId3=0;
//			long parentId4=0;
//			long parentId5=0;
			int ov=1;
			// 获取指定单元格的对象引用
			for (int i = 2; i < rsRows+1; i++) {
				Row row = readsheet.getRow(i);
				sb = sb+1;
				
				r = new Resource();
				r.setStatus(true);
				r.setIsDelete(false);
				r.setIsSystem(false);
				r.setOrderValue(ov++);
				r.setPlatform("boss");
				r.setId(Long.valueOf(sb+""));
				
				for (int j = 0; j < 11; j++) {
					String temp =getStringCellValue(row.getCell(j));
					if (temp != null && !"".equals(temp)) {
						if ((j == 0 || j == 1 || j == 3)) {
							r.setResourceName(temp.trim());
						} else if (j == 2) {
							r.setResourceUrl(temp.trim());
						} else if (j == 4) {
							if ("模块".equals(temp.trim())) {
								r.setType(2);
							} else if ("元素".equals(temp.trim())) {
								r.setType(3);
							}
						} else if (j == 5) {
							if ("1.0".equals(temp.trim())) {
								r.setTreePath(s1 + sb);
								s2 = s1 + sb;
								parentId2=sb;
								r.setParentResourceId(Long.valueOf(parentId1));
							} else if ("2.0".equals(temp.trim())) {
								r.setTreePath(s2 + "," + sb);
								s3 = s2 + "," + sb;
								r.setParentResourceId(parentId2);
								parentId3=sb;
							} else if ("3.0".equals(temp.trim())) {
								r.setTreePath(s3 + "," + sb);
//								s4 = s3 + "," + sb;
//								parentId4=sb;
								r.setParentResourceId(parentId3);
							} 
//							else if ("4".equals(temp.trim())) {
//								r.setTreePath(s4 + "," + sb);
//								s5 = s4 + "," + sb;
//								parentId5=sb;
//								r.setParentResourceId(parentId4);
//							}
						} else if (j == 6) {
							if ("Y".equalsIgnoreCase(temp.trim())) {
								r.setIsMenu(true);
							} else {
								r.setIsMenu(false);
							}
						} else if (j == 7) {
							r.setResourceKey(temp.toLowerCase().trim());
						} else if (j == 8) {
							r.setApi(temp.trim());
						} else if (j == 9 && "Y".equalsIgnoreCase(temp.trim())) {
							r.setIsSystem(true);
						}else if(j==10 ){
							r.setResourceIcon(temp.trim());
						} 
					} 

				}

				rl.add(r);

			}
			
			return rl;
			 
		} catch (Exception e) { 
			e.printStackTrace(); 
			return null;
		} finally { 
			try {
				if(instream != null)
					instream.close();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		} 
	}
	
	public static void insert(List<Resource> rl){
		
		try {
			//dev1 froad_cbank_1		test1 froad_cbank_2
			//dev2 froad_cbank_4		test2 froad_cbank_3
			String url = "jdbc:mysql://10.43.2.7:3306/froad_cbank_4?user=root&password=root&useUnicode=true&characterEncoding=UTF8";
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url);
			
			Statement stmt = conn.createStatement();
			
			for(Resource r : rl){
				String sql = "INSERT INTO cb_resource"+
					"(`id`,`resource_name`,`type`,`parent_resource_id`,`status`,`resource_url`,`resource_icon`,`tree_path`,"+
					"`is_delete`,`api`,`order_value`,`is_system`,`is_menu`,`platform`,`resource_key`,`update_time`,`is_limit`)"+
					"VALUES"+
					"('"+r.getId()+"','"+
					r.getResourceName()+"','"+
					r.getType()+"','"+
					r.getParentResourceId()+"',"+
					r.getStatus()+",'"+
					r.getResourceUrl()+"','"+
					r.getResourceIcon()+"','"+
					r.getTreePath()+"',"+
					r.getIsDelete()+",'"+
					r.getApi()+"','"+
					r.getOrderValue()+"',"+
					r.getIsSystem()+","+
					r.getIsMenu()+",'"+
					r.getPlatform()+"','"+
					r.getResourceKey()+"','"+DateUtil.longToString(System.currentTimeMillis())+"',false)";
				sql = sql.replaceAll("\'null\'", "null");
				System.err.println(sql);
				stmt.executeUpdate(sql);
			}
			
			stmt.close();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void sql(List<Resource> rl){
		
		try {
			
			FileOutputStream out = new FileOutputStream("/home/ling/tmp/resource.sql", false);
			
			for(Resource r : rl){
				String sql = "INSERT INTO cb_resource"+
					"(`id`,`resource_name`,`type`,`parent_resource_id`,`status`,`resource_url`,`resource_icon`,`tree_path`,"+
					"`is_delete`,`api`,`order_value`,`is_system`,`is_menu`,`platform`,`resource_key`,`update_time`,`is_limit`)"+
					"VALUES"+
					"('"+r.getId()+"','"+
					r.getResourceName()+"','"+
					r.getType()+"','"+
					r.getParentResourceId()+"',"+
					r.getStatus()+",'"+
					r.getResourceUrl()+"','"+
					r.getResourceIcon()+"','"+
					r.getTreePath()+"',"+
					r.getIsDelete()+",'"+
					r.getApi()+"','"+
					r.getOrderValue()+"',"+
					r.getIsSystem()+","+
					r.getIsMenu()+",'"+
					r.getPlatform()+"','"+
					r.getResourceKey()+"','"+DateUtil.longToString(System.currentTimeMillis())+"',false)";
				sql = sql.replaceAll("\'null\'", "null");
				System.err.println(sql);
				sql = sql + "\n";
				out.write(sql.getBytes());
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static String getStringCellValue(Cell cell) {
    	if (cell == null){
        	return "";
        }
        String strCell = "";
        switch (cell.getCellType()) {
        case Cell.CELL_TYPE_STRING:
            strCell = cell.getStringCellValue();
            break;
        case Cell.CELL_TYPE_NUMERIC:
        	BigDecimal bd = new BigDecimal(String.valueOf(cell.getNumericCellValue()));  
        	strCell = bd.toPlainString();
            break;
        case Cell.CELL_TYPE_BOOLEAN:
            strCell = String.valueOf(cell.getBooleanCellValue());
            break;
        case Cell.CELL_TYPE_BLANK:
        	strCell = "";  
            break;
        default:
            strCell = "";
            break;
        }
        if (strCell.equals("") || strCell == null) {
            return "";
        }
        return strCell;
    }
	
	
}
class Resource{
	
 	private Long id;

    private String resourceName;

    private Integer type;

    private Long parentResourceId;

    private Boolean status;

    private String resourceUrl;

    private String resourceIcon;

    private String treePath;

    private Boolean isDelete;

    private String api;

    private Integer orderValue;

    private Boolean isSystem;

    private Boolean isMenu;

    private String platform;

    private String resourceKey;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName == null ? null : resourceName.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getParentResourceId() {
        return parentResourceId;
    }

    public void setParentResourceId(Long parentResourceId) {
        this.parentResourceId = parentResourceId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl == null ? null : resourceUrl.trim();
    }

    public String getResourceIcon() {
        return resourceIcon;
    }

    public void setResourceIcon(String resourceIcon) {
        this.resourceIcon = resourceIcon == null ? null : resourceIcon.trim();
    }

    public String getTreePath() {
        return treePath;
    }

    public void setTreePath(String treePath) {
        this.treePath = treePath == null ? null : treePath.trim();
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api == null ? null : api.trim();
    }

    public Integer getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(Integer orderValue) {
        this.orderValue = orderValue;
    }

    public Boolean getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(Boolean isSystem) {
        this.isSystem = isSystem;
    }

    public Boolean getIsMenu() {
        return isMenu;
    }

    public void setIsMenu(Boolean isMenu) {
        this.isMenu = isMenu;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }

    public String getResourceKey() {
        return resourceKey;
    }

    public void setResourceKey(String resourceKey) {
        this.resourceKey = resourceKey == null ? null : resourceKey.trim();
    }
}
