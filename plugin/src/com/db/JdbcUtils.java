package com.db;

import ConfigPara.TypeEntity;
import com.coderPlugin.MySourceDataPool;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcUtils {
    private Connection connection;
    private PreparedStatement pstmt;
    private ResultSet resultSet;
    private Statement stat;

    public JdbcUtils() {
//        try {
//            Class.forName(DRIVER);
//
//        } catch (Exception e) {
//
//        }
    }

    public Connection getConnection() {
//        try {
//            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        从数据库连接池取出连接
        connection = MySourceDataPool.getConnection();
        return connection;
    }

    public boolean executeQuery(String sql) throws SQLException {
        return connection.createStatement().execute(sql);
    }

    //判断macData表中是否有存在mac地址
    public String ifMacAddrExist(String macAddr) throws SQLException {
        String sql = "select tableName from macData where macAddr='"+macAddr+"'";
        boolean flag = false;
        pstmt = connection.prepareStatement(sql);
        resultSet = pstmt.executeQuery();
        String tableName = "";
        while (resultSet.next()){
            tableName = resultSet.getString("tableName");
        }

        return tableName;
    }

    //向macData表中插入数据
    public void insertMacData(String macAddr, String tableName) throws SQLException {
        String sql = "insert into macData(macAddr,tableName) values(?,?)";
        pstmt = connection.prepareStatement(sql);
        pstmt.setString(1,macAddr);
        pstmt.setString(2,tableName);
        pstmt.executeUpdate();
        pstmt.close();
    }

    //根据mac地址创建表
    public void createTable(String tableName) throws SQLException {
        String sql = "CREATE TABLE `"+tableName+"`  (\n" +
                "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `time` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,\n" +
                "  `dataContext` longtext CHARACTER SET utf8 COLLATE utf8_general_ci,\n" +
                "  `codeContext` longtext CHARACTER SET utf8 COLLATE utf8_general_ci,\n" +
                "  `caretOffset` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `coder_input` longtext CHARACTER SET utf8 COLLATE utf8_general_ci,\n" +
                "  `coder_select` longtext CHARACTER SET utf8 COLLATE utf8_general_ci,\n" +
                "  `select_num` int(11) DEFAULT NULL,\n" +
                "  `code_from` longtext CHARACTER SET utf8 COLLATE utf8_general_ci,\n" +
                "  `IDEAcode` longtext CHARACTER SET utf8 COLLATE utf8_general_ci,\n" +
                "  `IDEAcode_num` int(11) DEFAULT NULL,\n" +
                "  `IDEAcode_index` longtext CHARACTER SET utf8 COLLATE utf8_general_ci,\n" +
                "  `AiXcode` longtext CHARACTER SET utf8 COLLATE utf8_general_ci,\n" +
                "  `AiXcode_num` int(11) DEFAULT NULL,\n" +
                "  `AiXcoder_index` longtext CHARACTER SET utf8 COLLATE utf8_general_ci,\n" +
                "  `KiteCode` longtext CHARACTER SET utf8 COLLATE utf8_general_ci,\n" +
                "  `Kitecode_num` int(11) DEFAULT NULL,\n" +
                "  `Kitecode_index` longtext CHARACTER SET utf8 COLLATE utf8_general_ci,\n" +
                "  `time_input_to_show` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `time_of_select_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `delete_behavior` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action3` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action4` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action5` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action6` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action7` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action8` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action9` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action10` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action11` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action12` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action13` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action14` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action15` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action16` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action17` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action18` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action19` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action20` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action21` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action22` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action23` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action24` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action25` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action26` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action27` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action28` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action29` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action30` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action31` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action32` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action33` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action34` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action35` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action36` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action37` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action38` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action39` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action40` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action41` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action42` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action43` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action44` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action45` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action46` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action47` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action48` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action49` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action50` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action51` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action52` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action53` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action54` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action55` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action56` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action57` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action58` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action59` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  `action60` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
                "  PRIMARY KEY (`id`) USING BTREE\n" +
                ") ENGINE = MyISAM AUTO_INCREMENT = 179 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;";
        stat = connection.createStatement();
        stat.executeUpdate(sql);
        stat.close();
    }

    public boolean updateByPreparedStatement(String sql, List<Object> params) {
        boolean flag = false;
        int result = -1;
        try {
            pstmt = connection.prepareStatement(sql);
            int index = 1;
            if (params != null && !params.isEmpty()) {
                for (int i = 0; i < params.size(); i++) {
                    pstmt.setObject(index++, params.get(i));
                }
            }
            result = pstmt.executeUpdate();
            pstmt.close();
            flag = result > 0 ? true : false;
            return flag;
        } catch (Exception e) {
            return flag;
        }

    }

    public Map<String, Object> findSimpleResult(String sql, List<Object> params) throws SQLException {
        Map<String, Object> map = new HashMap<String, Object>();
        int index = 1;
        pstmt = connection.prepareStatement(sql);
        if (params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(index++, params.get(i));
            }
        }
        resultSet = pstmt.executeQuery();//返回查询结果
        ResultSetMetaData metaData = resultSet.getMetaData();
        int col_len = metaData.getColumnCount();
        while (resultSet.next()) {
            for (int i = 0; i < col_len; i++) {
                String cols_name = metaData.getColumnName(i + 1);
                Object cols_value = resultSet.getObject(cols_name);
                if (cols_value == null) {
                    cols_value = "";
                }
                map.put(cols_name, cols_value);
            }
        }
        return map;
    }

    public List<Map<String, Object>> findModeResult(String sql, List<Object> params) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        int index = 1;
        pstmt = connection.prepareStatement(sql);
        if (params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(index++, params.get(i));
            }
        }
        resultSet = pstmt.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int cols_len = metaData.getColumnCount();
        while (resultSet.next()) {
            Map<String, Object> map = new HashMap<String, Object>();
            for (int i = 0; i < cols_len; i++) {
                String cols_name = metaData.getColumnName(i + 1);
                Object cols_value = resultSet.getObject(cols_name);
                if (cols_value == null) {
                    cols_value = "";
                }
                map.put(cols_name, cols_value);
            }
            list.add(map);
        }

        return list;
    }

    public <T> T findSimpleRefResult(String sql, List<Object> params,
                                     Class<T> cls) throws Exception {
        T resultObject = null;
        int index = 1;
        pstmt = connection.prepareStatement(sql);
        if (params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(index++, params.get(i));
            }
        }
        resultSet = pstmt.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int cols_len = metaData.getColumnCount();
        while (resultSet.next()) {
            //通过反射机制创建一个实例
            resultObject = cls.newInstance();
            for (int i = 0; i < cols_len; i++) {
                String cols_name = metaData.getColumnName(i + 1);
                Object cols_value = resultSet.getObject(cols_name);
                if (cols_value == null) {
                    cols_value = "";
                }
                Field field = cls.getDeclaredField(cols_name);
                field.setAccessible(true); //打开javabean的访问权限
                field.set(resultObject, cols_value);
            }
        }
        return resultObject;

    }

    public <T> List<T> findMoreRefResult(String sql, List<Object> params, Class<T> cls) throws Exception {
        List<T> list = new ArrayList<T>();
        int index = 1;
        pstmt = connection.prepareStatement(sql);
        if (params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(index++, params.get(i));
            }
        }
        resultSet = pstmt.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int cols_len = metaData.getColumnCount();
        while (resultSet.next()) {
            //通过反射机制创建一个实例
            T resultObject = cls.newInstance();
            for (int i = 0; i < cols_len; i++) {
                String cols_name = metaData.getColumnName(i + 1);
                Object cols_value = resultSet.getObject(cols_name);
                if (cols_value == null) {
                    cols_value = "";
                }
                Field field = cls.getDeclaredField(cols_name);
                field.setAccessible(true); //打开javabean的访问权限
                field.set(resultObject, cols_value);
            }
            list.add(resultObject);
        }
        return list;
    }

    public void releaseConn() {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws SQLException {
        // TODO Auto-generated method stub
        JdbcUtils jdbcUtils = new JdbcUtils();
        jdbcUtils.getConnection();
        String sql2 = "show global status ";
        //List<Map<String, Object>> list = jdbcUtils.findModeResult(sql2, null);
        /*for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i) + "\n");

        }*/

        //System.out.println(jdbcUtils.ifMacAddrExist("00 23 24 b3 c8 0a"));
        //jdbcUtils.insertMacData("a","b");
        jdbcUtils.createTable("tableeee");
        /*******************增*********************/
		/*		String sql = "insert into userinfo (username, pswd) values (?, ?), (?, ?), (?, ?)";
		List<Object> params = new ArrayList<Object>();
		params.add("小明");
		params.add("123xiaoming");
		params.add("张三");
		params.add("zhangsan");
		params.add("李四");
		params.add("lisi000");
		try {
			boolean flag = jdbcUtils.updateByPreparedStatement(sql, params);
			System.out.println(flag);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/


        /*******************删*********************/
        //删除名字为张三的记录
		/*		String sql = "delete from userinfo where username = ?";
		List<Object> params = new ArrayList<Object>();
		params.add("小明");
		boolean flag = jdbcUtils.updateByPreparedStatement(sql, params);*/

        /*******************改*********************/
        //将名字为李四的密码改了
		/*		String sql = "update userinfo set pswd = ? where username = ? ";
		List<Object> params = new ArrayList<Object>();
		params.add("lisi88888");
		params.add("李四");
		boolean flag = jdbcUtils.updateByPreparedStatement(sql, params);
		System.out.println(flag);*/

        /*******************查*********************/
        //不利用反射查询多个记录
		/*		String sql2 = "select * from userinfo ";
		List<Map<String, Object>> list = jdbcUtils.findModeResult(sql2, null);
		System.out.println(list);*/

        //利用反射查询 单条记录
		/*		String sql = "select * from userinfo where username = ? ";
		List<Object> params = new ArrayList<Object>();
		params.add("李四");
		UserInfo userInfo;
		try {
			userInfo = jdbcUtils.findSimpleRefResult(sql, params, UserInfo.class);
			System.out.print(userInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
//		try {
//			Object obj = clcs.newInstance();
//			Method f = clcs.getDeclaredMethod("setUsername", String.class);
//			f.invoke(obj, "yan123");
//			Method f2 = clcs.getDeclaredMethod("getUsername", null);
//			Object name = f2.invoke(obj, null);
//			System.out.println("反射得到的名字 = "  +  name);
//
//		} catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (NoSuchMethodException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SecurityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalArgumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}


    }

}
