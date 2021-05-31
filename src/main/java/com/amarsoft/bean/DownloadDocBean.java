package com.amarsoft.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Setter
@Getter
@Slf4j
@ToString
public class DownloadDocBean {
    //接口编号列表
    private String transcodes;
    //征信 还是 数据公司
    private String company;
    //文件类型 pdf 或者是 doc
//    private String fileType;
    //template类型模板类型
    private String templateType;
    //是否需要demo
    private boolean isNeedDemo;




}
