package com.amarsoft.dao.als;

import com.amarsoft.bean.DataBaseBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Component
public interface DataBaseConfigMapper {


    /**
     * 查询所有
     * @return
     */
    List<DataBaseBean> getAllconfig(String environment);


    /**
     * 查询所有
     * @return
     */
    DataBaseBean getOneconfig(String environment,String dbname);
}
