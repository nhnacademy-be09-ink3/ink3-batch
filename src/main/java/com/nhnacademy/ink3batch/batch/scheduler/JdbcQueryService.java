package com.nhnacademy.ink3batch.batch.scheduler;

import jakarta.transaction.Transactional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public int markDormantUsers(int daysThreshold) {
        String sql = """
            UPDATE users
            SET status = 'DORMANT'
            WHERE last_login_at < DATE_SUB(CURDATE(), INTERVAL ? DAY)
              AND status = 'ACTIVE'
        """;

        try (Connection conn = mysqlDataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, daysThreshold);
            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int updateMemberRate() {
        String sql = """
                UPDATE users u
                JOIN (
                  SELECT
                    o.user_id,
                    SUM(p.payment_amount) AS total_amount
                  FROM orders o
                  JOIN payments p
                    ON p.order_id = o.id
                  WHERE o.status = 'DELIVERED'
                    AND p.approved_at BETWEEN DATE_SUB(NOW(), INTERVAL 3 MONTH) AND NOW()
                  GROUP BY o.user_id
                ) agg
                  ON u.id = agg.user_id
                JOIN (
                  SELECT
                    r.user_id,
                    (
                      SELECT m.id
                      FROM memberships m
                      WHERE m.condition_amount <= r.total_amount
                      ORDER BY m.condition_amount DESC
                      LIMIT 1
                    ) AS target_membership_id
                  FROM (
                    SELECT
                      o.user_id,
                      SUM(p.payment_amount) AS total_amount
                    FROM orders o
                    JOIN payments p
                      ON p.order_id = o.id
                    WHERE o.status = 'DELIVERED'
                      AND p.approved_at BETWEEN DATE_SUB(NOW(), INTERVAL 3 MONTH) AND NOW()
                    GROUP BY o.user_id
                  ) r
                ) t
                  ON u.id = t.user_id
                SET
                  u.membership_id = COALESCE(t.target_membership_id, 1)
        """;

        try (Connection conn = mysqlDataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            int updatedRows = ps.executeUpdate();
            System.out.println("회원 등급 업데이트 건수: " + updatedRows);
            return updatedRows;

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }


}