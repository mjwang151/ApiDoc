package com.amarsoft.exportexcel.template;

import java.util.ArrayList;
import java.util.Map;

public interface ExcelExportTemplate {


     void writeAddExcel(String filepath, Map<String, ArrayList<ArrayList<String>>> datamap, Map<String, ArrayList<ArrayList<String>>> heads);
}
