package ConfigPara;

/*
    保存和获取Settings配置的参数信息 2019/03/28
 */
public class TypeEntity {
    private static String user = "root";
    private static String password = "xiaojiangzhang";
    private static String dburl = "jdbc:mysql://47.101.184.222:3306/SampleJavaCode?characterEncoding=UTF-8";
    private static String driver = "com.mysql.jdbc.Driver";
    private static String tool1 = "IDEA";
    private static String tool2 = "Aixcoder";
    private static String tool3 = "Kite";
    private static String tool1key = "jetbrains";
    private static String tool2key = "aiXcoder";
    private static String tool3key = "kite";

    public static String getTool1() {
        return tool1;
    }

    public static void setTool1(String tool1) {
        TypeEntity.tool1 = tool1;
    }

    public static String getTool2() {
        return tool2;
    }

    public static void setTool2(String tool2) {
        TypeEntity.tool2 = tool2;
    }

    public static String getTool3() {
        return tool3;
    }

    public static void setTool3(String tool3) {
        TypeEntity.tool3 = tool3;
    }

    public static String getTool1key() {
        return tool1key;
    }

    public static void setTool1key(String tool1key) {
        TypeEntity.tool1key = tool1key;
    }

    public static String getTool2key() {
        return tool2key;
    }

    public static void setTool2key(String tool2key) {
        TypeEntity.tool2key = tool2key;
    }

    public static String getTool3key() {
        return tool3key;
    }

    public static void setTool3key(String tool3key) {
        TypeEntity.tool3key = tool3key;
    }

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        TypeEntity.user = user;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        TypeEntity.password = password;
    }

    public static String getDburl() {
        return dburl;
    }

    public static void setDburl(String dburl) {
        TypeEntity.dburl = dburl;
    }

    public static String getDriver() {
        return driver;
    }

    public static void setDriver(String driver) {
        TypeEntity.driver = driver;
    }
}
