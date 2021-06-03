package com.amarsoft.AmarPackTest;

import com.alibaba.fastjson.JSONObject;
import com.amarsoft.bean.AssetApi;
import com.amarsoft.bean.DataBaseBean;
import com.amarsoft.service.impl.DataBaseConfigImpl;
import com.amarsoft.service.impl.DocServiceImpl;
import com.amarsoft.service.impl.EdsDocServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author mjwang
 * @version 1.0
 * @date 2021/4/2 21:49
 */
@SpringBootTest
public class Test {
    @Autowired
    DataBaseConfigImpl docServiceImpl;
    @org.junit.jupiter.api.Test
    public void test(){
        List<DataBaseBean> allconfig = docServiceImpl.getAllconfig("dev");
        allconfig.stream().forEach(v->{
            System.out.println(v.toString());
        });



    }
}
