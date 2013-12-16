package com.hskj.util;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
@SuppressWarnings({"unused","deprecation","unchecked"})
public final class ExcelUtils {
	public ExcelUtils() {
	}

	/**
	 * 导出excel对外接口
	 * 
	 * @param title
	 *            标题 如：同业对标分段统计报表
	 * @param tableData
	 *            数据表数据 如：new String[][]{{"表头1","表头2"},{"data1","data2"}}
	 * @param exportFileName
	 *            导出后的文件名 如：data.xls
	 * @param request
	 * @param response
	 */
	public static void exportExcelData(String title, Object[][] tableData,
			String exportFileName, HttpServletRequest request,
			HttpServletResponse response) {
		response.reset();
		String fileName;
		try {
			fileName = "attachment;filename=\"" + java.net.URLEncoder.encode(exportFileName, "UTF-8") + "\";";
			response.setHeader("Content-disposition", fileName);
			response.setContentType("application/vnd.ms-excel");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		try {
			exportExcel(title, tableData, response.getOutputStream(),
					createWorkbook());
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error(e.getMessage(), e);
		}
	}
	
	/*
	 * 
	 * 
	 * 测试
	 * 
	 * 
	 * */
	public static void exportExcelData1(Map<Integer, String[][]> map,
			String exportFileName, HttpServletRequest request,
			HttpServletResponse response) {
		response.reset();
		String fileName = "attachment;filename=\"" + exportFileName + "\";";
		response.setHeader("Content-disposition", fileName);
		response.setContentType("application/vnd.ms-excel");
		try {
			exportExcel1(map, response.getOutputStream(),
					createWorkbook());
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error(e.getMessage(), e);
		}
	}
	
	
	
	/*
	 * 
	 * 
	 * 测试
	 * 
	 * 
	 * */
	public static void exportExcelData2(Map<Integer, String[][]> map,
			String exportFileName, HttpServletRequest request,
			HttpServletResponse response) {
		response.reset();
		String fileName = "attachment;filename=\"" + exportFileName + "\";";
		response.setHeader("Content-disposition", fileName);
		response.setContentType("application/vnd.ms-excel");
		try {
			exportExcel2(map, response.getOutputStream(),
					createWorkbook());
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error(e.getMessage(), e);
		}
	}
	
	/*
	 * 
	 * 
	 * 测试
	 * 
	 * 
	 * */
	public static void exportExcelData3(Map<Integer, Object[][]> map,
			String exportFileName, HttpServletRequest request,
			HttpServletResponse response) {
		response.reset();
		String fileName = "attachment;filename=\"" + exportFileName + "\";";
		response.setHeader("Content-disposition", fileName);
		response.setContentType("application/vnd.ms-excel");
		try {
			exportExcel3(map, response.getOutputStream(),
					createWorkbook());
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error(e.getMessage(), e);
		}
	}
	
//==========================================================================================================

	protected static void exportExcel(String title, Object[][] tableData,
			OutputStream output, HSSFWorkbook workbook) throws Exception {
		HSSFSheet sheet = workbook.getSheetAt(0);
		for (int i=1;i<30;i++) {
			sheet.setColumnWidth(i, 4677);
		}
		HSSFCellStyle titleStyle = workbook.createCellStyle();
		
		titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单无格的边框为粗体 
		titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN); 
		titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN); 
		titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN); 
		
		
		
		titleStyle.setFillBackgroundColor((short) 55);
		HSSFFont hsFont = workbook.createFont();
		hsFont.setBoldweight((short) 700);
		Object titles[] = tableData[0];
		titleStyle.setFont(hsFont);
		titleStyle.setAlignment((short) 2);
		HSSFRow row = sheet.createRow(1);
		row.setHeight((short) 300);
		
		HSSFCell cell = createCell(row, (short) 3, title, null);
		
//		HSSFWorkbook wb = new HSSFWorkbook(); 
//		HSSFCellStyle cellStyle = workbook.createCellStyle(); 
//		cellStyle.setBorderBottom(HSSFCellStyle.SQUARES); // 设置单无格的边框为粗体 
//		cellStyle.setBorderLeft(HSSFCellStyle.SQUARES); 
//		cellStyle.setBorderRight(HSSFCellStyle.SQUARES); 
//		cellStyle.setBorderTop(HSSFCellStyle.SQUARES); 
		
		row = sheet.createRow(3);
		row.setHeight((short) 250);
		short i = 0;
		for (int forI = 0; forI < titles.length; forI++) {
			createCell(row, i, titles[forI], titleStyle);
			i++;
		}
		int rowCount = 4;
		for (int rowPos = 1; rowPos < tableData.length; rowPos++) {
			row = sheet.createRow(rowCount++);
			i = 0;
			for (int colPos = 0; colPos < tableData[rowPos].length; colPos++) {
				createCell(row, i, tableData[rowPos][colPos], titleStyle);
				i++;
			}
		}
		
		
		
		
	
		
		/*row = sheet.createRow(rowCount++);
		i = 0;
		for (int forI = 0; forI < titles.length; forI++) {
			createCell(row, i, titles[forI], titleStyle);
			i++;
		}
		rowCount = tableData.length+5;
		for (int rowPos = 1; rowPos < tableData.length; rowPos++) {
			row = sheet.createRow(rowCount++);
			i = 0;
			for (int colPos = 0; colPos < tableData[rowPos].length; colPos++) {
				createCell(row, i, tableData[rowPos][colPos], null);
				i++;
			}
		}*/
		
		
		try {
			workbook.write(output);
		} catch (Exception e) {
			// throw new Exception(e);
		}
	}

	protected static HSSFCell createCell(HSSFRow row, short cellCount,
			Object value, HSSFCellStyle titleStyle) {
		HSSFCell cell = row.createCell(cellCount);
//		cell.setEncoding((short) 1);
		if (titleStyle != null)
			cell.setCellStyle(titleStyle);
		if(value==null){
			
		}else if (value instanceof String) {
			 cell.setCellValue(value.toString()); 
		}else {
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC); 
			 cell.setCellValue(Float.parseFloat(value.toString())); 
		}
		return cell;
	}

	public static HSSFWorkbook createWorkbook() throws Exception {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet s = wb.createSheet();
		return wb;
	}

	public static HSSFWorkbook createWorkbook(int sheetCount) throws Exception {
		HSSFWorkbook wb = new HSSFWorkbook();
		for (int i = 0; i < sheetCount; i++) {
			HSSFSheet si = wb.createSheet();
		}
		return wb;
	}

	public static HSSFWorkbook createWorkbook(int sheetCount, List sblxList)
			throws Exception {
		HSSFWorkbook wb = new HSSFWorkbook();
		for (int i = 0; i < sheetCount; i++) {
			HSSFSheet si = wb.createSheet((String) sblxList.get(i));
//			wb.setSheetName(i, (String) sblxList.get(i), (short) 1);
			wb.setSheetName(i, (String) sblxList.get(i));
		}
		return wb;
	}
	
	
	protected static void exportExcel1(Map<Integer, String[][]> map,
			OutputStream output, HSSFWorkbook workbook) throws Exception {
		HSSFSheet sheet = workbook.getSheetAt(0);
		for (int i=1;i<30;i++) {
			sheet.setColumnWidth(i, 4677);
		}
		HSSFCellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setFillBackgroundColor((short) 55);
		HSSFFont hsFont = workbook.createFont();
		hsFont.setBoldweight((short) 700);
		int rowCount=0;
		HSSFCell cell=null;
		Set<Map.Entry<Integer, String[][]>> set = map.entrySet();
		for (Iterator<Map.Entry<Integer, String[][]>> it = set.iterator(); it.hasNext();) {
	        Map.Entry<Integer, String[][]> entry = (Map.Entry<Integer, String[][]>) it.next();
	      
				int title =entry.getKey();
				String[][] tableData=entry.getValue();
				String titles[] = tableData[0];
				titleStyle.setFont(hsFont);
				titleStyle.setAlignment((short) 2);
				HSSFRow row = sheet.createRow(++rowCount);
				row.setHeight((short) 300);
				if(title==0){
				cell = createCell(row, (short) 0, "银行汇款", titleStyle);
				}
				if(title==1){
					cell = createCell(row, (short) 0, "现金付款", titleStyle);
				}
				if(title==2){
					cell = createCell(row, (short) 0, "支票付款", titleStyle);
				}
				row = sheet.createRow(++rowCount);
				row.setHeight((short) 250);
				short i = 0;
				for (int forI = 0; forI < titles.length; forI++) {
					createCell(row, i, titles[forI], titleStyle);
					i++;
				}
				rowCount++;
				for (int rowPos = 1; rowPos < tableData.length; rowPos++) {
					row = sheet.createRow(rowCount);
					i = 0;
					for (int colPos = 0; colPos < tableData[rowPos].length; colPos++) {
						createCell(row, i, tableData[rowPos][colPos], null);
						i++;
					}
					rowCount++;
				}	
	    }
		try {
			workbook.write(output);
		} catch (Exception e) {
			// throw new Exception(e);
		}
	}
	
	
	
	protected static void exportExcel2(Map<Integer, String[][]> map,
			OutputStream output, HSSFWorkbook workbook) throws Exception {
		HSSFSheet sheet = workbook.getSheetAt(0);
		for (int i=1;i<30;i++) {
			sheet.setColumnWidth(i, 4677);
		}
		HSSFCellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setFillBackgroundColor((short) 55);
		HSSFFont hsFont = workbook.createFont();
		hsFont.setBoldweight((short) 700);
		int rowCount=0;
		HSSFCell cell=null;
		Set<Map.Entry<Integer, String[][]>> set = map.entrySet();
		for (Iterator<Map.Entry<Integer, String[][]>> it = set.iterator(); it.hasNext();) {
	        Map.Entry<Integer, String[][]> entry = (Map.Entry<Integer, String[][]>) it.next();
	      
				int title =entry.getKey();
				String[][] tableData=entry.getValue();
				String titles[] = tableData[0];
				titleStyle.setFont(hsFont);
				titleStyle.setAlignment((short) 2);
				HSSFRow row = sheet.createRow(++rowCount);
				row.setHeight((short) 300);
				if(title==0){
				cell = createCell(row, (short) 0, "到款详细", titleStyle);
				}
				if(title==1){
					cell = createCell(row, (short) 0, "账单详细", titleStyle);
				}
				row = sheet.createRow(++rowCount);
				row.setHeight((short) 250);
				short i = 0;
				for (int forI = 0; forI < titles.length; forI++) {
					createCell(row, i, titles[forI], titleStyle);
					i++;
				}
				rowCount++;
				for (int rowPos = 1; rowPos < tableData.length; rowPos++) {
					row = sheet.createRow(rowCount);
					i = 0;
					for (int colPos = 0; colPos < tableData[rowPos].length; colPos++) {
						createCell(row, i, tableData[rowPos][colPos], null);
						i++;
					}
					rowCount++;
				}	
	    }
		try {
			workbook.write(output);
		} catch (Exception e) {
			// throw new Exception(e);
		}
	}
	
	
	protected static void exportExcel3(Map<Integer, Object[][]> map,
			OutputStream output, HSSFWorkbook workbook) throws Exception {
		HSSFSheet sheet = workbook.getSheetAt(0);
		for (int i=1;i<30;i++) {
			sheet.setColumnWidth(i, 4677);
		}
		HSSFCellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setFillBackgroundColor((short) 55);
		HSSFFont hsFont = workbook.createFont();
		hsFont.setBoldweight((short) 700);
		int rowCount=0;
		HSSFCell cell=null;
		Set<Map.Entry<Integer, Object[][]>> set = map.entrySet();
		for (Iterator<Map.Entry<Integer, Object[][]>> it = set.iterator(); it.hasNext();) {
	        Map.Entry<Integer, Object[][]> entry = (Map.Entry<Integer, Object[][]>) it.next();
	      
				int title =entry.getKey();
				Object[][] tableData=entry.getValue();
				Object titles[] = tableData[0];
				titleStyle.setFont(hsFont);
				titleStyle.setAlignment((short) 2);
				HSSFRow row = sheet.createRow(++rowCount);
				row.setHeight((short) 300);
				if(title==0){
				cell = createCell(row, (short) 0, "账单发送数据列表", titleStyle);
				}
				if(title==1){
					cell = createCell(row, (short) 0, "发送数据汇总", titleStyle);
				}
				row = sheet.createRow(++rowCount);
				row.setHeight((short) 250);
				short i = 0;
				for (int forI = 0; forI < titles.length; forI++) {
					createCell(row, i, titles[forI], titleStyle);
					i++;
				}
				rowCount++;
				for (int rowPos = 1; rowPos < tableData.length; rowPos++) {
					row = sheet.createRow(rowCount);
					i = 0;
					for (int colPos = 0; colPos < tableData[rowPos].length; colPos++) {
						createCell(row, i, tableData[rowPos][colPos], null);
						i++;
					}
					rowCount++;
				}	
	    }
		try {
			workbook.write(output);
		} catch (Exception e) {
			// throw new Exception(e);
		}
	}
	
	
	
	public static void exportExcelDataOne(String title, Object[][] tableData,
			String exportFileName, HttpServletRequest request,
			HttpServletResponse response) {
		response.reset();
		String fileName;
		try {
			fileName = "attachment;filename=\"" + java.net.URLEncoder.encode(exportFileName, "UTF-8") + "\";";
			response.setHeader("Content-disposition", fileName);
			response.setContentType("application/vnd.ms-excel");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		try {
			exportExcelOne(title, tableData, response.getOutputStream(),
					createWorkbook());
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error(e.getMessage(), e);
		}
	}
	
	
	
	
	
	
	
	
	
	/**账单核对页面的“按接入号导出”  涉及到单元格合并
	 * Description:  
	 * @param title
	 * @param tableData
	 * @param output
	 * @param workbook
	 * @throws Exception
	 * @author baisong
	 * Create at:   Jun 27, 2013 3:33:15 PM 
	 */
	protected static void exportExcelOne(String title, Object[][] tableData,
			OutputStream output, HSSFWorkbook workbook) throws Exception {
		HSSFSheet sheet = workbook.getSheetAt(0);
		sheet.addMergedRegion(new Region(3,(short)3,3,(short)7)); 
		for(int i=0;i<11;i++){
//			if(i!=3 && i!=4 && i!=5 && i!=6 && i!=7){
			if(i<3 || i>7){
				sheet.addMergedRegion(new Region(3,(short)i,4,(short)i)); 	
			}
		}
		
//		sheet.addMergedRegion(new Region(3,(short)0,4,(short)0)); 
//		sheet.addMergedRegion(new Region(3,(short)1,4,(short)1)); 
//		sheet.addMergedRegion(new Region(3,(short)2,4,(short)2));
//		sheet.addMergedRegion(new Region(3,(short)8,4,(short)8)); 
//		sheet.addMergedRegion(new Region(3,(short)9,4,(short)9)); 
//		sheet.addMergedRegion(new Region(3,(short)10,4,(short)10)); 
		for (int i=1;i<30;i++) {
			sheet.setColumnWidth(i, 4677);
		}
		HSSFCellStyle titleStyle = workbook.createCellStyle();
		
		titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单无格的边框为粗体 
		titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN); 
		titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN); 
		titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN); 
		
		titleStyle.setFillBackgroundColor((short) 55);
		HSSFFont hsFont = workbook.createFont();
		hsFont.setBoldweight((short) 700);
		Object titles[] = tableData[0];
		titleStyle.setFont(hsFont);
		titleStyle.setAlignment((short) 2);
		HSSFRow row = sheet.createRow(1);
		row.setHeight((short) 300);
		
		HSSFCell cell = createCell(row, (short) 3, title, null);
		
		
		row = sheet.createRow(3);
		row.setHeight((short) 250);
		short i = 0;
		for (int forI = 0; forI < titles.length; forI++) {
			createCell(row, i, titles[forI], titleStyle);
			i++;
		}
		int rowCount = 4;
		for (int rowPos = 1; rowPos < tableData.length; rowPos++) {
			row = sheet.createRow(rowCount++);
			i = 0;
			for (int colPos = 0; colPos < tableData[rowPos].length; colPos++) {
				createCell(row, i, tableData[rowPos][colPos], titleStyle);
				i++;
			}
		}
		try {
			workbook.write(output);
		} catch (Exception e) {
		}
	}
	
	
	
	
	
	
	
	
}