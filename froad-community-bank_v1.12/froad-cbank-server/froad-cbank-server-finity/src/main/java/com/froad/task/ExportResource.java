package com.froad.task;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.froad.db.mongo.RoleResourceMongo;
import com.froad.db.mongo.bean.BankUserResource;
import com.froad.db.mongo.bean.ResourcesInfo;
import com.froad.db.redis.impl.RedisManager;
import com.froad.logback.LogCvt;
import com.froad.logic.ResourceLogic;
import com.froad.logic.RoleResourceLogic;
import com.froad.logic.impl.ResourceLogicImpl;
import com.froad.logic.impl.RoleResourceLogicImpl;
import com.froad.po.Resource;
import com.froad.util.RedisKeyUtil;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class ExportResource {
 
	public static void main(String[] args) {
//		bankExportResource();
//		merchantExportResource();
	}
	
	/**
	 * 银行资源导入
	 */
	public static void bankExportResource(){
		Workbook readwb = null;
		ResourceLogic resourceLogic = new ResourceLogicImpl();
		try

		{ 
			InputStream instream = new FileInputStream("E:/froad/权限审核/银行端权限资源整理1021.xls");

			readwb = Workbook.getWorkbook(instream);

			// Sheet的下标是从0开始

			// 获取第一张Sheet表

			Sheet readsheet = readwb.getSheet(0);

			// 获取Sheet表中所包含的总列数

			int rsColumns = readsheet.getColumns();
			System.out.println("==" + rsColumns);

			// 获取Sheet表中所包含的总行数

			int rsRows = readsheet.getRows();

			List<Resource> rl = new ArrayList<Resource>();
			Resource r = new Resource();
			int sb = 100000000;
			String s1 = "100000000,";
			String s2 = "";
			String s3 = "";
			String s4 = "";
			String s5 = "";
			long parentId1=100000000;
			long parentId2=0;
			long parentId3=0;
			long parentId4=0;
			long parentId5=0;
			int ov=1;
			// 获取指定单元格的对象引用
			for (int i = 2; i < rsRows; i++) {
				sb = sb+1;
				r = new Resource();
				r.setStatus(true);
				r.setIsDelete(false);
				r.setIsSystem(false);
				r.setOrderValue(ov++);
				r.setPlatform("bank");
				for (int j = 0; j < rsColumns; j++) {
					Cell cell = readsheet.getCell(j, i);
					String temp = cell.getContents();
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
							if ("1".equals(temp.trim())) {
								r.setTreePath(s1 + sb);
								s2 = s1 + sb;
								parentId2=sb;
								r.setParentResourceId(Long.valueOf(parentId1));
							} else if ("2".equals(temp.trim())) {
								r.setTreePath(s2 + "," + sb);
								s3 = s2 + "," + sb;
								r.setParentResourceId(parentId2);
								parentId3=sb;
							} else if ("3".equals(temp.trim())) {
								r.setTreePath(s3 + "," + sb);
								s4 = s3 + "," + sb;
								parentId4=sb;
								r.setParentResourceId(parentId3);
							} else if ("4".equals(temp.trim())) {
								r.setTreePath(s4 + "," + sb);
								s5 = s4 + "," + sb;
								parentId5=sb;
								r.setParentResourceId(parentId4);
							}
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
			System.out.println();
			System.out.println(rl.size());
			for(int x=0;x<rl.size();x++){
				resourceLogic.insertSelective(rl.get(x));
				System.out.println(rl.get(x).getResourceName()+"==="+rl.get(x).getResourceKey()+"==="+rl.get(x).getTreePath());
			}
			 
		} catch (Exception e) { 
			e.printStackTrace(); 
		} finally { 
			readwb.close(); 
		} 
	}
	
	
	/**
	 * 银行资源导入
	 */
	public static void merchantExportResource(){
		Workbook readwb = null;
		ResourceLogic resourceLogic = new ResourceLogicImpl();
		try

		{ 
			InputStream instream = new FileInputStream("E:/froad/权限审核/商户端权限资源整理.xls");

			readwb = Workbook.getWorkbook(instream);

			// Sheet的下标是从0开始

			// 获取第一张Sheet表

			Sheet readsheet = readwb.getSheet(0);

			// 获取Sheet表中所包含的总列数

			int rsColumns = readsheet.getColumns();
			System.out.println("==" + rsColumns);

			// 获取Sheet表中所包含的总行数

			int rsRows = readsheet.getRows();

			List<Resource> rl = new ArrayList<Resource>();
			Resource r = new Resource();
			int sb = 200000000;
			String s1 = "200000000,";
			String s2 = "";
			String s3 = "";
			String s4 = "";
			String s5 = "";
			long parentId1=200000000;
			long parentId2=0;
			long parentId3=0;
			long parentId4=0;
			long parentId5=0;
			int ov=1;
			// 获取指定单元格的对象引用
			for (int i = 2; i < rsRows; i++) {
				sb = sb+1;
				r = new Resource();
				r.setStatus(true);
				r.setIsDelete(false);
				r.setIsSystem(false);
				r.setOrderValue(ov++);
				r.setPlatform("merchant");
				for (int j = 0; j < rsColumns; j++) {
					Cell cell = readsheet.getCell(j, i);
					String temp = cell.getContents();
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
							if ("1".equals(temp.trim())) {
								r.setTreePath(s1 + sb);
								s2 = s1 + sb;
								parentId2=sb;
								r.setParentResourceId(Long.valueOf(parentId1));
							} else if ("2".equals(temp.trim())) {
								r.setTreePath(s2 + "," + sb);
								s3 = s2 + "," + sb;
								r.setParentResourceId(parentId2);
								parentId3=sb;
							} else if ("3".equals(temp.trim())) {
								r.setTreePath(s3 + "," + sb);
								s4 = s3 + "," + sb;
								parentId4=sb;
								r.setParentResourceId(parentId3);
							} else if ("4".equals(temp.trim())) {
								r.setTreePath(s4 + "," + sb);
								s5 = s4 + "," + sb;
								parentId5=sb;
								r.setParentResourceId(parentId4);
							}
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
			System.out.println();
			System.out.println(rl.size());
			for(int x=0;x<rl.size();x++){
				resourceLogic.insertSelective(rl.get(x));
				System.out.println(rl.get(x).getResourceName()+"==="+rl.get(x).getResourceKey()+"==="+rl.get(x).getTreePath());
			}
			 
		} catch (Exception e) { 
			e.printStackTrace(); 
		} finally { 
			readwb.close(); 
		} 
	}

}
