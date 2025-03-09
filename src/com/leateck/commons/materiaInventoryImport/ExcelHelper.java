package com.leateck.commons.materiaInventoryImport;

import com.ibm.icu.text.DecimalFormat;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class ExcelHelper {

	public static Vector readExcel(String fileName,int columCount) throws IOException {
		
		Vector vect = new Vector();
		FileInputStream is = new FileInputStream(fileName);
		// 「POIFSFileSystem」类对象可以把Excel文件作为数据流来进行传入传出。
		POIFSFileSystem fs = new POIFSFileSystem(is);
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		/**
		 * 获取第一个Sheet页或根据名字获得sheet页 public HSSFSheet getSheet(java.lang.String
		 * name) Get sheet with the given name
		 */
		HSSFSheet hssfSheet = wb.getSheetAt(0);
		if (hssfSheet == null) {
			return null;
		}
		// 遍历行Row
		for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
			HSSFRow hssfRow = hssfSheet.getRow(rowNum);
			if (hssfRow == null) {
				continue;
			}
			Vector vRow = new Vector();
			// 遍历列Cell
			for (int cellNum = 0; cellNum <= hssfRow.getLastCellNum()&& cellNum<columCount; cellNum++) {
				HSSFCell hssfCell = hssfRow.getCell(cellNum);
//				if (hssfCell == null && cellNum == 0) {
//					break;
//				}
				//else if (hssfCell == null) {
				//	continue;
				//}
				vRow.add( getCellString(hssfCell) );
			}
			if(vRow.size() >0 )
				vect.add(vRow);
		}
		
		return vect;
	}

	private static String getCellString(HSSFCell cell) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// TODO Auto-generated method stub
		String result = null;
		if (cell == null) {
			return "";
		}
		// cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		int cellType = cell.getCellType();
		switch (cellType) {
		case HSSFCell.CELL_TYPE_STRING:
			result = cell.getStringCellValue();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				result = sdf.format(cell.getDateCellValue());
			} else {
				//cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				//result = cell.getStringCellValue();
				result = NumberToString(cell.getNumericCellValue());
			}
			break;
		case HSSFCell.CELL_TYPE_FORMULA:
			result = String.valueOf(cell.getNumericCellValue());
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			result =  String.valueOf(cell.getBooleanCellValue());
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			result = "";
			break;
		case HSSFCell.CELL_TYPE_ERROR:
			result = "";
			break;
		default:
			// System.out.println("枚举了所有类型");
			break;
		}
		return result;
	}
	
	private static String NumberToString(double s)
	{
		DecimalFormat format = new DecimalFormat("#.########");
		String sMoney = format.format(s);
		return sMoney;	
	
	}
}
