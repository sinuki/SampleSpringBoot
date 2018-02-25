package com.parksw.app.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Primary MySQL 설정
 * 
 * @author parksw
 * @Create 2018.02.12
 * @version 1.0
 */
@Configuration
@EnableTransactionManagement
@MapperScan("com.parksw.app.primary.dao")
public class PrimaryMySQLConfig {

	@Value("${mybatis.config-location}")
	private String mybatisConfigLocation;
	@Value("${mybatis.mapper-locations}")
	private String mybatisMapperLocations;
	
	@Bean
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource.dbcp2")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean
	@Primary
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource, ApplicationContext context) throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(dataSource);
		factoryBean.setConfigLocation(context.getResource(mybatisConfigLocation));
		factoryBean.setMapperLocations(context.getResources(mybatisMapperLocations));
		return factoryBean.getObject();
	}
	
	@Bean
	@Primary
	public SqlSessionTemplate sessionTemplate(SqlSessionFactory factory) {
		return new SqlSessionTemplate(factory);
	}
}
