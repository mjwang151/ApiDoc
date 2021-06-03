package com.amarsoft.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DataBaseBean {
	
	private String dbname;
	private String environment;
	private String driverClassName;
	private String url;
	private String username;
	private String password;
	private int initialSize;
	private int minIdle;
	private int maxActive;
	private int maxWait;
	private long timeBetweenEvictionRunsMillis;
	private long minEvictableIdleTimeMillis;
	private String testWhileIdle;
	private String testOnBorrow;
	private String testOnReturn;
	private String status;
	private String inputtime;
	

	
	
}
