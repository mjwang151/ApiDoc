package com.amarsoft.exportexcel.template;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class PureTemplate  implements ExcelExportTemplate{

    /**
     *
     * @param filepath
     * @param datamap
     * @param heads
     */
    @Override
    public  void writeAddExcel(String filepath, Map<String, ArrayList<ArrayList<String>>> datamap, Map<String, ArrayList<ArrayList<String>>> heads) {
        //第一步，创建一个workbook对应一个excel文件
        @SuppressWarnings("resource")
        XSSFWorkbook workbook = new XSSFWorkbook();
        //第二部，在workbook中创建一个sheet对应excel中的sheet
        for (String key : datamap.keySet()) {
            List<ArrayList<String>> list = datamap.get(key);
            XSSFSheet sheet = workbook.createSheet(key);
            XSSFCellStyle headerStyle = TempletManager.getHeaderStyle(sheet.getWorkbook());
            XSSFCellStyle contentStyle = TempletManager.getCellStyle(sheet.getWorkbook());
            XSSFRow row = sheet.createRow(0);
            //第四步，创建单元格，设置表头
            ArrayList<String> headList = heads.get(key).get(0);
            for (int i = 0; i < headList.size(); i++) {
                XSSFCell cell = row.createCell(i);
                cell.setCellStyle(headerStyle);
                cell.setCellValue(headList.get(i));
            }
            //第五步，写入实体数据，实际应用中这些数据从数据库得到,对象封装数据，集合包对象。对象的属性值对应表的每行的值
            for (int i = 1; i <= list.size(); i++) {
                XSSFRow row1 = sheet.createRow(i);
                //创建单元格设值
                List<String> data = list.get(i - 1);
                for (int j = 0; j < data.size(); j++) {
                    XSSFCell hcc = row1.createCell(j);
                    hcc.setCellStyle(contentStyle);
                    if (data.get(j) == null) {
                        hcc.setCellValue("");
                    } else {
                        if (data.get(j).toString().length() >= 32766) {
                            hcc.setCellValue(data.get(j).toString().substring(0, 32766));
                        } else {
                            hcc.setCellValue(data.get(j));
                        }
                    }
                }
            }
        }
        //将文件保存到指定的位置
        try {
            FileOutputStream fos = new FileOutputStream(filepath);
            workbook.write(fos);
            log.info("{},写入成功",filepath);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
