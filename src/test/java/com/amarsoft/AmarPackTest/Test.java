package com.amarsoft.AmarPackTest;

import com.amarsoft.bean.AssetApi;
import com.amarsoft.service.impl.DocServiceImpl;
import com.amarsoft.service.impl.EdsDocServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author mjwang
 * @version 1.0
 * @date 2021/4/2 21:49
 */
@SpringBootTest
public class Test {
    @Autowired
    EdsDocServiceImpl docServiceImpl;
    @org.junit.jupiter.api.Test
    public void test(){
        AssetApi rc1134 = docServiceImpl.findApi("R11C53");
        System.out.print(rc1134.getName());
    }
}
