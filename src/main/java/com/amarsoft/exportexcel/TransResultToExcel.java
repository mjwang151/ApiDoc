package com.amarsoft.exportexcel;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.amarsoft.exportdoc.util.JSONTools;
import com.amarsoft.exportexcel.template.ExcelExportTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class TransResultToExcel {

    Map<String, ArrayList<ArrayList<String>>> dataListMap = new LinkedHashMap<>();
    Map<String, ArrayList<ArrayList<String>>> headMap = new LinkedHashMap<>();
    List<TransToExcel> CurAllNode = new ArrayList<>();

    static List<TransToExcel> listTransToExcel = new ArrayList<>();
    //存储所有接口的文档

    ExcelExportTemplate excelExportTemplate;
    public TransResultToExcel(ExcelExportTemplate excelExportTemplate){
        this.excelExportTemplate = excelExportTemplate;
    }

    static {
        try {
            List<List<String>> lists = ReadExcelUtils.readExcel("./config/api_param.xlsx", 0);
            lists.forEach(v->{
                try{
                    TransToExcel  transToExcel = new TransToExcel();
                    transToExcel.setApi(v.get(0));
                    transToExcel.setApiName(v.get(1));
                    transToExcel.setApiDescr(v.get(2));
                    transToExcel.setParamId(v.get(3));
                    transToExcel.setParentParamId(v.get(4));
                    transToExcel.setApiId(v.get(5));
                    transToExcel.setProjectId(v.get(6));
                    transToExcel.setName(v.get(7));
                    transToExcel.setChName(v.get(8));
                    transToExcel.setParamType(v.get(9));
                    transToExcel.setRequestType(v.get(10));
                    transToExcel.setContentType(v.get(11));
                    transToExcel.setDataType(v.get(12));
                    transToExcel.setIsRequired(v.get(13));
                    transToExcel.setSortNo(Integer.parseInt(v.get(14).toString()));
                    transToExcel.setLevel(Integer.parseInt(v.get(15).toString()));
                    transToExcel.setDescr(v.get(16));
                    transToExcel.setInputUser(v.get(17));
                    transToExcel.setInputTime(v.get(18));
                    transToExcel.setUpdateUser(v.get(19));
                    transToExcel.setUpdateTime(v.get(20));
                    transToExcel.setIsDelete(v.get(21));
                    listTransToExcel.add(transToExcel);
                }catch (Exception e){
                    System.out.println("can not init this row"+v.toString());
                }
            });
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 单笔结果处理
     * @param path excel路径
     * @param jsonArray 单条数据返回jsonarr
     * @param transcode 接口编号
     */
    public void parseJSONToExcel(String path,JSONArray jsonArray,String transcode){
        Map<String, List<TransToExcel>> collect = listTransToExcel.parallelStream().filter(v -> v.getIsDelete().equals("N")).filter(v -> v.getParamType().equals("RESPONSE")).collect(Collectors.groupingBy(TransToExcel::getApi));
        CurAllNode = collect.get(transcode);
        List<TransToExcel> level0List = CurAllNode.parallelStream().filter(v -> v.getLevel() == 0).sorted(Comparator.comparing(TransToExcel::getSortNo)).collect(Collectors.toList());
        String curKey = CurAllNode.get(0).getApiName();
        combineData("1",curKey,level0List,jsonArray);
        excelExportTemplate.writeAddExcel(path, dataListMap, headMap);
    }

    /**
     * 批量处理格式化数据
     * @param path excel路径
     * @param mapArr 多条数据返回jsonarr
     * @param transcode 接口编号
     */
    public void batchParseJSONToExcel(String path,LinkedHashMap<String,JSONArray> mapArr,String transcode){
        Map<String, List<TransToExcel>> collect = listTransToExcel.parallelStream().filter(v->v.getIsDelete().equals("N")).filter(v->v.getParamType().equals("RESPONSE")).collect(Collectors.groupingBy(TransToExcel::getApi));
        CurAllNode = collect.get(transcode);
        List<TransToExcel> level0List = CurAllNode.parallelStream().filter(v -> v.getLevel() == 0).sorted(Comparator.comparing(TransToExcel::getSortNo)).collect(Collectors.toList());
        for (Map.Entry<String,JSONArray> jsonArray:mapArr.entrySet()){
            String curKey = CurAllNode.get(0).getApiName();
            batchCombineData("1",curKey,level0List,jsonArray.getValue(),jsonArray.getKey());
        }
        excelExportTemplate.writeAddExcel(path, dataListMap, headMap);
    }
    /**
     * is not parentNode
     * @param curNodeId
     * @return
     */
    public  boolean isParentNode(String curNodeId){
        return CurAllNode.parallelStream().anyMatch(v->v.getParentParamId().equals(curNodeId));
    }

    /**
     *
     * @param curNode  child node
     * @param jsonArray  jsonarray
     */
    public  void combineData(String index,String curKey,List<TransToExcel> curNode,JSONArray jsonArray){
        ArrayList<ArrayList<String>> dataList_1 = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> headMapFromList_1 = getHeadMapFromList(curNode);
        setValueToHeadMap(headMap,index+"."+curKey,headMapFromList_1);
        setValueToMap(dataListMap,index+"."+curKey,new ArrayList<ArrayList<String>>());
        if(jsonArray != null){
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject firstLevelJo = jsonArray.getJSONObject(i);
                ArrayList<String> dataList_ch_1 = new  ArrayList<String>();
                for (int j = 0; j < curNode.size(); j++) {
                    String str1 = JSONTools.getString(firstLevelJo, curNode.get(j).getName(), "");
                    dataList_ch_1.add(str1);
                    if(isParentNode(curNode.get(j).getParamId())){
                        int finalJ = j;
                        List<TransToExcel> trans_node_2 = CurAllNode.stream().filter(v -> v.getParentParamId().equals(curNode.get(finalJ).getParamId())).sorted(Comparator.comparing(TransToExcel::getSortNo)).collect(Collectors.toList());
                        JSONArray jsonArray2 = new JSONArray();
                        if(str1.startsWith("{") && str1.endsWith("}")){
                            jsonArray2.add(JSONObject.parseObject(str1));
                        }else{
                            jsonArray2 = JSONArray.parseArray(str1);
                        }
                        combineData(index+"."+curNode.get(finalJ).getSortNo()+"",curNode.get(finalJ).getChName(),trans_node_2,jsonArray2);
                    }
                }
                dataList_1.add(dataList_ch_1);
            }
        }
        setValueToMap(dataListMap,index+"."+curKey,dataList_1);
    }

    /**
     * 批量处理-自动增加主键
     * @param curNode  child node
     * @param jsonArray  jsonarray
     */
    public  void batchCombineData(String index,String curKey,List<TransToExcel> curNode,JSONArray jsonArray,String key){
        curNode.add(0,TransToExcel.buildAEntName());
        ArrayList<ArrayList<String>> dataList_1 = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> headMapFromList_1 = getHeadMapFromList(curNode);
        setValueToHeadMap(headMap,index+"."+curKey,headMapFromList_1);
        setValueToMap(dataListMap,index+"."+curKey,new ArrayList<ArrayList<String>>());
        if(jsonArray != null){
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject firstLevelJo = jsonArray.getJSONObject(i);
                ArrayList<String> dataList_ch_1 = new  ArrayList<String>();
                for (int j = 0; j < curNode.size(); j++) {
                    //业务主体名称
                    if(curNode.get(j).getName().equals("req_host_name")){
                        dataList_ch_1.add(key);
                    }else{
                        String str1 = JSONTools.getString(firstLevelJo, curNode.get(j).getName(), "");
                        dataList_ch_1.add(str1);
                        if(isParentNode(curNode.get(j).getParamId())){
                            int finalJ = j;
                            List<TransToExcel> trans_node_2 = CurAllNode.stream().filter(v -> v.getParentParamId().equals(curNode.get(finalJ).getParamId())).sorted(Comparator.comparing(TransToExcel::getSortNo)).collect(Collectors.toList());
                            JSONArray jsonArray2 = new JSONArray();
                            if(str1.startsWith("{") && str1.endsWith("}")){
                                jsonArray2.add(JSONObject.parseObject(str1));
                            }else{
                                jsonArray2 = JSONArray.parseArray(str1);
                            }
                            batchCombineData(index+"."+curNode.get(finalJ).getSortNo()+"",curNode.get(finalJ).getChName(),trans_node_2,jsonArray2,key);
                        }
                    }
                }
                dataList_1.add(dataList_ch_1);
            }
        }
        setValueToMap(dataListMap,index+"."+curKey,dataList_1);
    }


    /**
     * add data
     * @param map
     * @param key
     * @param value
     */
    private  void setValueToMap(Map<String, ArrayList<ArrayList<String>>> map,String key,ArrayList<ArrayList<String>> value){
        key = formatter_special(key);
        if(map.containsKey(key)){
            ArrayList<ArrayList<String>> arrayLists = map.get(key);
            arrayLists.addAll(value);
        }else{
            map.put(key,value);
        }
    }

    /**
     * add data
     * @param map
     * @param key
     * @param value
     */
    private  void setValueToHeadMap(Map<String, ArrayList<ArrayList<String>>> map,String key,ArrayList<ArrayList<String>> value){
        key = formatter_special(key);
        if(!map.containsKey(key)){
            map.put(key,value);
        }
    }
    public static String formatter_special(String key) {
        key = key.replaceAll("\\[|\\]|\\(|\\)|\\ |\\{|\\}|\\【|\\】|\\（|\\）|\\　|\\t|\\n|\\r|\\n|\\n\\r|\\s|\\-", "_");
        return key;
    }
    /**
     * getHeader
     * @param level0List
     * @return
     */
    public  ArrayList<ArrayList<String>> getHeadMapFromList(List<TransToExcel> level0List){
        ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
        ArrayList<String> list_2 = new ArrayList<String>();
        for (int i = 0; i < level0List.size(); i++) {
            list_2.add(level0List.get(i).getChName());
        }
        list.add(list_2);
        return list;
    }




}