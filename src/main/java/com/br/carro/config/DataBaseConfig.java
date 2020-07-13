package com.br.carro.config;

import javax.sql.DataSource;

import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataBaseConfig {
	
	@Value("${spring.datasource.url}")
	private String url;
		
	
	@Bean
    public DataSource dataSource()  {
       HikariConfig hikariConfig = new HikariConfig();
       hikariConfig.setJdbcUrl(getData(url));
       hikariConfig.setConnectionTestQuery("select 1 from dual");
       hikariConfig.setPoolName("POOL-MYSQL");       
       
       return new HikariDataSource(hikariConfig);
    }

	private String getData(String key) {
		BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
		textEncryptor.setPassword("heroku");
		return  textEncryptor.decrypt(key);
	}
	
}
