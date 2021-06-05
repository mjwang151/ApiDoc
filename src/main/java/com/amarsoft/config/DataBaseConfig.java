package com.amarsoft.config;


import com.alibaba.druid.pool.DruidDataSource;
import com.amarsoft.bean.DataBaseBean;
import com.amarsoft.service.impl.DataBaseConfigImpl;
import com.amarsoft.utils.ApplicationContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class DataBaseConfig  implements CommandLineRunner {

    public static volatile Map<String, DruidDataSource> dataSourceMap = new HashMap<String, DruidDataSource>();

    @Autowired
    DataBaseConfigImpl docServiceImpl;

    @Override
    public void run(String... args) throws Exception {
        log.info("----开始加载数据源-----，当前环境为："+ApplicationContextUtils.getProperty("spring.profiles.active"));
        List<DataBaseBean> allconfig = docServiceImpl.getAllconfig(ApplicationContextUtils.getProperty("spring.profiles.active"));
        allconfig.stream().forEach(v->{
            dataSourceMap.put(v.getDbname(),getJdbcTemplate(v));
            log.info("加载数据源："+v.getDbname()+"完成...");
        });
    }
    public static Connection getConn(String dbname) throws SQLException {
        if(dataSourceMap.containsKey(dbname)){
            return dataSourceMap.get(dbname).getConnection();
        }else{
            return null;
        }
    }

    private DruidDataSource getJdbcTemplate(DataBaseBean db) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(db.getUrl());
        dataSource.setUsername(db.getUsername());
        dataSource.setPassword(db.getPassword());
        dataSource.setDriverClassName(db.getDriverClassName());
        dataSource.setInitialSize(db.getInitialSize());
        dataSource.setMinIdle(db.getMinIdle());
        dataSource.setMaxActive(db.getMaxActive());
        dataSource.setMaxWait(db.getMaxWait());
        dataSource.setTimeBetweenEvictionRunsMillis(db.getTimeBetweenEvictionRunsMillis());
        dataSource.setMinEvictableIdleTimeMillis(db.getMinEvictableIdleTimeMillis());
        dataSource.setTestWhileIdle(Boolean.parseBoolean(db.getTestWhileIdle()));
        dataSource.setTestOnBorrow(Boolean.parseBoolean(db.getTestOnBorrow()));
        dataSource.setTestOnReturn(Boolean.parseBoolean(db.getTestOnReturn()));
        return dataSource;
    }



}
