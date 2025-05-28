package com.nhnacademy.ink3batch.batch.config;

import com.rabbitmq.client.AMQP.Basic;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class CustomDataSource {
    @Bean
    @Primary
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://s4.java21.net:13306/project_be9_team3_batch_dev?serverTimezone=Asia/Seoul");
        dataSource.setUsername("project_be9_team3");
        dataSource.setPassword("-/jvCmf5WlFZF!U_");
        return dataSource;
    }

    @Bean(name="customMysqlDataSource")
    public DataSource customMysqlDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://s4.java21.net:13306/project_be9_team3_dev?serverTimezone=Asia/Seoul");
        dataSource.setUsername("project_be9_team3");
        dataSource.setPassword("-/jvCmf5WlFZF!U_");
        return dataSource;
    }
}
