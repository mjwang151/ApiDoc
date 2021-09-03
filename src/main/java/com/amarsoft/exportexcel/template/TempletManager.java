package com.amarsoft.exportexcel.template;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TempletManager {
	
	/**
	 * 生成标题cell样式
	 */
	public static XSSFCellStyle getHeaderStyle(XSSFWorkbook wb){
		XSSFCellStyle ztStyle = (XSSFCellStyle) wb.createCellStyle();
		// 创建字体对象
		Font ztFont = wb.createFont();
		ztFont.setItalic(true);                     // 设置字体为斜体字
		ztFont.setColor(IndexedColors.WHITE.getIndex());            // 将字体设置为“红色”
		ztFont.setFontHeightInPoints((short)10);    // 将字体大小设置为18px
		ztFont.setFontName("微软雅黑");
		ztFont.setBold(true);
//		ztFont.setUnderline(Font.U_DOUBLE);         // 添加（Font.U_SINGLE单条下划线/Font.U_DOUBLE双条下划线）
		ztStyle.setFont(ztFont);                    // 将字体应用到样式上面
		ztStyle.setAlignment(HorizontalAlignment.CENTER);
		ztStyle.setFillForegroundColor(IndexedColors.SEA_GREEN.getIndex());
		ztStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		ztStyle.setBorderTop(BorderStyle.THIN);

        return ztStyle;
	}


	/**
	 * 生成标题cell样式
	 */
	public static XSSFCellStyle getCellStyle(XSSFWorkbook wb){
		XSSFCellStyle ztStyle = (XSSFCellStyle) wb.createCellStyle();
		ztStyle.setWrapText(true);//设置自动换行
		ztStyle.setAlignment(HorizontalAlignment.LEFT);
		ztStyle.setVerticalAlignment(VerticalAlignment.TOP);

		return ztStyle;
	}


}
