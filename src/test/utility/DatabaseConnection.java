package test.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    static {
        try {
            // 注册SQL Server驱动（JDBC 4.2+驱动可省略，但显式注册更可靠）
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 数据库连接信息
    private static final String DB_URL = "jdbc:sqlserver://192.168.1.13:1433;databaseName=MESPRD_DB"; // 移除末尾分号
    private static final String USER = "sa";
    private static final String PASS = "Ht123456";

    // 获取数据库连接
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}