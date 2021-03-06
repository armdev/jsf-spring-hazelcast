package com.backend.configs;

import com.hazelcast.config.Config;
import com.hazelcast.config.ListConfig;
import com.hazelcast.config.MapConfig;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

@Configuration
@EnableScheduling
@EnableAsync
@ComponentScan(basePackages = {"com"}, excludeFilters = {
    @ComponentScan.Filter(Configuration.class)})
@EnableTransactionManagement
@EnableMBeanExport
@EnableWebMvc
@PropertySource(value = {"classpath:application.properties"})
public class CommonCoreConfig {

    @Autowired
    private Environment environment;

    @Bean
    public DataSource mysqlDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
        dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
        dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
        dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
        return dataSource;
    }

    @Bean
    public HazelcastInstance hazelcastInstance() {
        //adding ttl for list, may be will help:)))))
        Config config = new Config();
        LocalListConfig listConfig = new LocalListConfig();
        listConfig.setMaxSize(50);
        listConfig.setTimeToLiveSeconds(10);
        listConfig.setBackupCount(0);
        listConfig.setName("employeeList");
        config.addListConfig(listConfig);
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(config);
        return instance;
    }

}
