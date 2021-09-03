package com.amarsoft.exportexcel.template;

public class TemplateFactory {

    /**
     * 获取模板
     * @param type
     * @return
     */
    public static ExcelExportTemplate getTemplate(String type) {
        ExcelExportTemplate excelExportTemplate;
        switch (type) {
            case "pure":
                //不带目录
                excelExportTemplate = new PureTemplate();
                break;
            default:
                //带目录
                excelExportTemplate = new CatalogTemplate();
                break;
        }
        return excelExportTemplate;
    }
}
