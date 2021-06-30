package com.amarsoft.service;

import com.amarsoft.bean.DataBaseBean;

import java.util.List;

public interface DataBaseConfigService {
    /**
     * 查询所有
     * @return
     */
    List<DataBaseBean> getAllconfig(String environment);

    DataBaseBean getOneconfig(String environment,String dbname);


}
