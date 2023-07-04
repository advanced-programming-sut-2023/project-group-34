package model;

import java.sql.Connection;
import java.sql.*;

public class Connect {
    public static void main(String[] args) {
        createNewTable();
        Connect connect = new Connect();
        connect.insert("aa" , "aabb");
        connect.insert("bb" , "bb2");
        connect.selectAll();
    }
    private static final String url =
            "jdbc:sqlite:C:/Users/Hosein/Desktop/project-group-34/AP_FinalProject/src/main/resources/database/database.db";//fixme: address!!!.
    
    public static java.sql.Connection connect () {
        java.sql.Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    public Connect() {
        createNewTable();
    }
    
    public static void createNewTable () {
        String sql = "CREATE TABLE IF NOT EXISTS userTokens (\n" + "	id integer PRIMARY KEY,\n" + "	username text " +
                "NOT " + "NULL,\n" + "	token text NOT NULL\n" + ");";
        
        try (java.sql.Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public String getUsernameFromToken (String token) {
        String sql = "SELECT id, username, token FROM userTokens";
        
        try (java.sql.Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs =
                stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                if (rs.getString("token").equals(token)) return rs.getString("username");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public void selectAll () {
        String sql = "SELECT id, username, token FROM userTokens";
        
        try (java.sql.Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs =
                stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t" + rs.getString("username") + "\t" + rs.getString("token"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void insert (String username, String token) {
        if (getUsernameFromToken(token) != null) return;
        String sql = "INSERT INTO userTokens(username,token) VALUES(?,?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, token);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
