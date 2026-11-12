
package service;

import database.DBConnection;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginService {

    public User login(String username, String password) {

        String sql = """
            SELECT user_id, username, password, role, name
            FROM users
            WHERE username = ?
            AND password = ?
        """;

        // Step 1: Get database connection
        Connection con = DBConnection.getConnection();

        // Step 2: Check whether connection exists
        if (con == null) {
            System.out.println("Login failed: Database unavailable!");
            return null;
        }

        // Step 3: Execute login query
        try (
            con;
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return new User(
                        rs.getLong("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getString("name")
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println("Login database error!");
            e.printStackTrace();
        }

        return null;
    }
}
