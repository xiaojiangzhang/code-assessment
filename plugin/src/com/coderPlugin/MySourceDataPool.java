package com.coderPlugin;

import ConfigPara.TypeEntity;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySourceDataPool {
    private static BasicDataSource dataSource;
    private static boolean statusFlag = false;

    /**
     * 初始化线程池
     */
    static {
        try {
            statusFlag = true;
            dataSource = new BasicDataSource();
            dataSource.setDriverClassName(TypeEntity.getDriver());
            dataSource.setUrl(TypeEntity.getDburl());
            dataSource.setUsername(TypeEntity.getUser());
            dataSource.setPassword(TypeEntity.getPassword());
//            最大连接数量
            dataSource.setMaxTotal(20);
            dataSource.setInitialSize(10);
            dataSource.setMaxIdle(10);
            dataSource.setMinIdle(5);
            dataSource.setMaxWaitMillis(-1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void init() {
        System.out.println("初始化数据库连接池");
    }

    /**
     * 从连接池取出连接
     *
     * @return
     */
    public static Connection getConnection() {
        Connection connection = null;
        if (!statusFlag) {
            MySourceDataPool.init();
        }
        try {
            connection = dataSource.getConnection();
//            System.out.println("取出数据库连接");
            System.out.println("数据库连接池信息----当前活跃数量：" + dataSource.getNumActive() + ", 最大可连接数量：" + dataSource.getMaxTotal());
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
