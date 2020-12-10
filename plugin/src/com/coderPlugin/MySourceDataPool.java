package com.coderPlugin;

import ConfigPara.TypeEntity;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class MySourceDataPool {
    private static BasicDataSource dataSource;

    /**
     * 初始化线程池
     */
    static {
        try {
            dataSource = new BasicDataSource();
            dataSource.setDriverClassName(TypeEntity.getDriver());
            dataSource.setUrl(TypeEntity.getDburl());
            dataSource.setUsername(TypeEntity.getUser());
            dataSource.setPassword(TypeEntity.getPassword());
//            initialSize=5
//            maxActive=3
//            maxIdle=5
//            minIdle=3
//            maxWait=-1
            dataSource.setInitialSize(10);
//            dataSource.max
            dataSource.setMaxIdle(15);
            dataSource.setMinIdle(10);
            dataSource.setMaxWaitMillis(-1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void init() {
    }

    /**
     * 从连接池取出连接
     *
     * @return
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void close() {
        try {
            dataSource.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

class Test {
    public static void main(String[] args) throws SQLException {
        Connection connection = MySourceDataPool.getConnection();
        String sql = "select * from data where id  = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, 1700);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            System.out.println(resultSet.getString("id"));
            System.out.println(resultSet.getString("time"));
        }
        preparedStatement.close();
    }
}
