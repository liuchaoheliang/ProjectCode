//package com.froad.thrift;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.Iterator;
//import java.util.List;
//import java.util.TimeZone;
//
//import com.froad.db.mongo.impl.MongoManager;
//import com.froad.po.mongo.CategoryInfo;
//import com.froad.po.mongo.MerchantDetail;
//import com.froad.po.mongo.OutletDetail;
//import com.froad.po.mongo.TypeInfo;
//import com.froad.thrift.po.ExcelVo;
//import com.froad.util.MongoTableName;
//import com.froad.util.PropertiesUtil;
//import com.mongodb.BasicDBObject;
//import com.mongodb.DBObject;
//
//import jxl.Cell;
//import jxl.CellType;
//import jxl.CellView;
//import jxl.DateCell;
//import jxl.Sheet;
//import jxl.Workbook;
//import jxl.format.Border;
//import jxl.format.BorderLineStyle;
//import jxl.format.Colour;
//import jxl.read.biff.BiffException;
//import jxl.write.Label;
//import jxl.write.WritableCellFormat;
//import jxl.write.WritableFont;
//import jxl.write.WritableSheet;
//import jxl.write.WritableWorkbook;
//import jxl.write.WriteException;
//import jxl.write.biff.RowsExceededException;
//
//public class ExcelUtil {
//
//	private final String path = "d:\\查询商户1.xls";
//	private final String filepath = "d:\\查询商户2.xls";
//	private java.text.SimpleDateFormat sf = new SimpleDateFormat(
//			"yyyy-MM-dd HH:mm:ss");
//	private List<String[]> list = new ArrayList<String[]>();
//
//	public void crateExcel() throws FileNotFoundException, IOException,
//			RowsExceededException, WriteException, BiffException {
//		WritableWorkbook wwk = Workbook.createWorkbook(new FileOutputStream(filepath));
//		
//		WritableSheet sheet = wwk.createSheet("商户数据",0);
//		Label label;
//		
//		int n = 0;
//		for (String[] strings : list) {
//			for (int i = 0; i < strings.length; i++) {
//				if(n == 0){
//					label = new Label(i, n, strings[i], getHeader());
//				}else{
//					label = new Label(i, n, strings[i]);
//				}
//				CellView cellView = new CellView();  
//				cellView.setAutosize(true); //设置自动大小  
//				sheet.addCell(label);
//				sheet.setColumnView(i, cellView);
//			}
//			n++;
//		}
//		
//		wwk.write();   
//          // 关闭文件   
//		wwk.close();   
//		System.out.println("====写入完成-----");
//	}
//
//	public WritableCellFormat getHeader() {
//		WritableFont font = new WritableFont(WritableFont.TIMES, 12,
//				WritableFont.BOLD);// 定义字体
//		try {
//			font.setColour(Colour.BLUE);// 蓝色字体
//		} catch (WriteException e1) {
//			e1.printStackTrace();
//		}
//		WritableCellFormat format = new WritableCellFormat(font);
//		try {
//			format.setAlignment(jxl.format.Alignment.CENTRE);// 左右居中
//			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 上下居中
//			format.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);// 黑色边框
//			format.setBackground(Colour.YELLOW);// 黄色背景
//		} catch (WriteException e) {
//			e.printStackTrace();
//		}
//		return format;
//	}
//
//	public void readExc() throws BiffException, IOException {
//
//		Workbook wb = Workbook.getWorkbook(new File(path));
//		Sheet s = wb.getSheet(0);// 第1个sheet
//		Cell c = null;
//		int row = s.getRows();// 总行数
//		int col = s.getColumns();// 总列数
//		Date date = new Date();
//		String[] arr = null;
//		MerchantDetail mDetail = null;
//
//		String category = "";
//		String typeName = "";
//		List<CategoryInfo> listcategory = null;
//		List<TypeInfo> listType = null;
//		DateCell dc = null;
//		for (int i = 0; i < row; i++) {
//			arr = new String[col + 2];
//			for (int j = 0; j < col; j++) {
//				c = s.getCell(j, i);
//				if (c.getType() == CellType.DATE) {
//					dc = (DateCell) c;
//					date = dc.getDate();
//					sf.setTimeZone(TimeZone.getTimeZone("GMT"));  
//					arr[j] = sf.format(date);
//				} else {
//					arr[j] = c.getContents();
//				}
//			}
////			System.out.println(s.getCell(0, i).getContents());
//			mDetail = findOutletDetailByoutletId(s.getCell(0, i).getContents());
//			if (mDetail != null) {
//				listcategory = mDetail.getCategoryInfo();
//				for (CategoryInfo cate : listcategory) {
//					category += cate.getName() + ",";
//				}
//				arr[col] = category;
//				listType = mDetail.getTypeInfo();
//				for (TypeInfo typeInfo : listType) {
//					typeName += typeInfo.getTypeName() + ",";
//				}
//				arr[col + 1] = typeName;
//			}
//			if(i == 0){
//				arr[col] = "所属分类";
//				arr[col + 1] = "商户类型";
//			}
//			list.add(arr);
//			category = "";
//			typeName = "";
//		}
//
////		for (String[] strings : list) {
////			for (int i = 0; i < strings.length; i++) {
////				System.out.print(strings[i] + " ");
////			}
////			System.out.println();
////		}
//	}
//
//	public static MerchantDetail findOutletDetailByoutletId(String merchantId) {
//		MongoManager manager = new MongoManager();
//		DBObject dbObj = new BasicDBObject();
//		dbObj.put("_id", merchantId);
//		return manager.findOne(dbObj, MongoTableName.CB_MERCHANT_DETAIL,
//				MerchantDetail.class);
//	}
//
//	public static void main(String[] args) throws BiffException, IOException, RowsExceededException, WriteException {
//		PropertiesUtil.load();
//		ExcelUtil e = new ExcelUtil();
//		e.readExc();
//		e.crateExcel();
//	}
//
//}
