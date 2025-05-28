package com.nhnacademy.ink3batch.batch.service;

import jakarta.transaction.Transactional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class JdbcQueryService {

    private final DataSource mysqlDataSource;

    public JdbcQueryService(@Qualifier("customMysqlDataSource") DataSource mysqlDataSource) {
        this.mysqlDataSource = mysqlDataSource;
    }

    public void printUsersBornInMay() {
        String sql = "SELECT id FROM users WHERE status = 'ACTIVE'";
        try (
                Connection conn = mysqlDataSource.getConnection();
                PreparedStatement psmt = conn.prepareStatement(sql)
        ) {
            try (ResultSet rs = psmt.executeQuery()) {
                while (rs.next()) {
                    System.out.printf("ID: %d%n", rs.getLong("id"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}