package com.amarsoft.exportexcel.template;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class CatalogTemplate  implements ExcelExportTemplate{

    @Override
    public  void writeAddExcel(String filepath, Map<String, ArrayList<ArrayList<String>>> datamap, Map<String, ArrayList<ArrayList<String>>> heads) {
        //第一步，创建一个workbook对应一个excel文件
        Map<String, ArrayList<ArrayList<String>>> muluMap = new LinkedHashMap<>();
        ArrayList<ArrayList<String>> mululist = new ArrayList<>();
        int ii = 1;
        for (String key : datamap.keySet()) {
            ArrayList<String> tongji = new ArrayList<>();
            List<ArrayList<String>> list = datamap.get(key);
            tongji.add(ii+"");
            tongji.add(key);
            tongji.add(list.size()+"");
            mululist.add(tongji);
            ii++;
        }
        muluMap.put("主目录",mululist);
        //第一步，创建一个workbook对应一个excel文件
        Map<String, ArrayList<ArrayList<String>>> headMulu = new LinkedHashMap<>();
        ArrayList<ArrayList<String>> headMuluList = new ArrayList<>();
        ArrayList<String> headListMl = new ArrayList<>();
        headListMl.add("序号");
        headListMl.add("模块名");
        headListMl.add("数据行数");
        headListMl.add("超链接");
        headMuluList.add(headListMl);
        headMulu.put("主目录",headMuluList);
        @SuppressWarnings("resource")
        XSSFWorkbook workbook = new XSSFWorkbook();

        for (String key : muluMap.keySet()) {
            List<ArrayList<String>> list = muluMap.get(key);
            XSSFSheet sheet = workbook.createSheet(key);
            XSSFCellStyle headerStyle = TempletManager.getHeaderStyle(sheet.getWorkbook());
            XSSFCellStyle contentStyle = TempletManager.getCellStyle(sheet.getWorkbook());
            XSSFRow row = sheet.createRow(0);
            //第四步，创建单元格，设置表头
            ArrayList<String> headList = headMulu.get(key).get(0);
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
                XSSFCell hcc2 = row1.createCell(data.size());
                CreationHelper creationHelper = workbook.getCreationHelper();
                Hyperlink hyperlink = creationHelper.createHyperlink(HyperlinkType.DOCUMENT);
                hyperlink.setAddress("#"+data.get(1)+"!A1");
                hcc2.setHyperlink(hyperlink);
                hcc2.setCellValue("点击跳转");
                XSSFCellStyle linkStyle = workbook.createCellStyle();
                XSSFFont cellFont= workbook.createFont();
                cellFont.setUnderline((byte) 1);
                linkStyle.setFont(cellFont);
                hcc2.setCellStyle(linkStyle);
            }
        }

        writeExcel(datamap,heads,workbook);
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

    /**
     *
     * @param datamap
     * @param heads
     * @param workbook
     */
    public void writeExcel(Map<String, ArrayList<ArrayList<String>>> datamap, Map<String, ArrayList<ArrayList<String>>> heads,XSSFWorkbook workbook){
        //第二部，在workbook中创建一个sheet对应excel中的sheet
        for (String key : datamap.keySet()) {
            List<ArrayList<String>> list = datamap.get(key);
            XSSFSheet sheet = workbook.createSheet(key);
            XSSFCellStyle headerStyle = TempletManager.getHeaderStyle(sheet.getWorkbook());
            XSSFCellStyle contentStyle = TempletManager.getCellStyle(sheet.getWorkbook());
            // create 主目录 start
            XSSFRow row0 = sheet.createRow(0);
            XSSFCell hcc0 = row0.createCell(0);
            CreationHelper creationHelper = workbook.getCreationHelper();
            Hyperlink hyperlink = creationHelper.createHyperlink(HyperlinkType.DOCUMENT);
            hyperlink.setAddress("#主目录!A1");
            hcc0.setHyperlink(hyperlink);
            hcc0.setCellValue("主目录");
            XSSFCellStyle linkStyle = workbook.createCellStyle();
            XSSFFont cellFont= workbook.createFont();
            cellFont.setUnderline((byte) 1);
            linkStyle.setFont(cellFont);
            hcc0.setCellStyle(linkStyle);
            // create 主目录 end

            XSSFRow row1 = sheet.createRow(1);
            ArrayList<String> headList = heads.get(key).get(0);
            for (int i = 0; i < headList.size(); i++) {
                XSSFCell cell = row1.createCell(i);
                cell.setCellStyle(headerStyle);
                cell.setCellValue(headList.get(i));
            }
            //第五步，写入实体数据，实际应用中这些数据从数据库得到,对象封装数据，集合包对象。对象的属性值对应表的每行的值
            for (int i = 1; i <= list.size(); i++) {
                XSSFRow row_n = sheet.createRow(i+1);
                //创建单元格设值
                List<String> data = list.get(i - 1);
                for (int j = 0; j < data.size(); j++) {
                    XSSFCell hcc = row_n.createCell(j);
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
    }
}
