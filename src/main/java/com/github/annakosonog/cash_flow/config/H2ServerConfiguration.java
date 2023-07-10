package com.github.annakosonog.cash_flow.config;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
public class H2ServerConfiguration {

    @Bean(initMethod = "start",
            destroyMethod = "shutdown")
    public Server h2Server() throws SQLException {
        return Server.createTcpServer("-tcp");
    }
}
