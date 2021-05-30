package com.amarsoft.config.mybatis;


import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = EdsConfig.PACKAGE, sqlSessionTemplateRef = "edsSqlSessionTemplate")
public class EdsConfig {

    static final String PACKAGE = "com.amarsoft.dao.eds";


    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.eds")
    public DataSource edsDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public SqlSessionFactory edsSqlSessionFactory(@Qualifier("edsDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/eds/*.xml"));
        return bean.getObject();
    }

    @Bean
    public DataSourceTransactionManager edsTransactionManager(@Qualifier("edsDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public SqlSessionTemplate edsSqlSessionTemplate(@Qualifier("edsSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
