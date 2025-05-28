package com.nhnacademy.ink3batch.batch.service;

import jakarta.transaction.Transactional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
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

    public List<Long> printUsersBornIn(int num) {
        String sql = "SELECT id FROM users WHERE MONTH(birthday) = ? AND status = 'ACTIVE'";
        List<Long> userIds = new java.util.ArrayList<>(List.of());
        try (
                Connection conn = mysqlDataSource.getConnection();
                PreparedStatement psmt = conn.prepareStatement(sql)
        ) {
            psmt.setString(1, String.valueOf(num));
            try (ResultSet rs = psmt.executeQuery()) {
                while (rs.next()) {
                    userIds.add(rs.getLong("id"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return userIds;
    }
}