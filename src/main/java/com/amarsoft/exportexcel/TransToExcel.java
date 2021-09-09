package com.amarsoft.exportexcel;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TransToExcel {

    private String api;
    private String apiName;
    private String apiDescr;
    private String ParamId;
    private String ParentParamId;
    private String ApiId;
    private String ProjectId;
    private String Name;
    private String ChName;
    private String ParamType;
    private String RequestType;
    private String ContentType;
    private String DataType;
    private String IsRequired;
    private int SortNo;
    private int Level;
    private String Descr;
    private String InputUser;
    private String InputTime;
    private String UpdateUser;
    private String UpdateTime;
    private String IsDelete;

    /**
     * 创建一个企业名称对应的key
     * @return
     */
    public static TransToExcel buildAEntName(){
        TransToExcel transToExcel = new TransToExcel();
        transToExcel.setChName("请求主体名称");
        transToExcel.setName("req_host_name");
        return transToExcel;
    }


}
