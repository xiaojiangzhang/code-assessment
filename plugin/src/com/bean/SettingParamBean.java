package com.bean;

public class SettingParamBean {
    private String user = "root";
    private String password = "xiaojiangzhang";
    private String dburl = "jdbc:mysql://47.101.184.222:3306/SampleJavaCode?characterEncoding=UTF-8";
    private String driver = "com.mysql.jdbc.Driver";
    private String tool1 = "IDEA";
    private String tool2 = "Aixcoder";
    private String tool3 = "Kite";
    private String tool1key = "jetbrains";
    private String tool2key = "aiXcoder";
    private String tool3key = "kite";

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDburl() {
        return dburl;
    }

    public void setDburl(String dburl) {
        this.dburl = dburl;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getTool1() {
        return tool1;
    }

    public void setTool1(String tool1) {
        this.tool1 = tool1;
    }

    public String getTool2() {
        return tool2;
    }

    public void setTool2(String tool2) {
        this.tool2 = tool2;
    }

    public String getTool3() {
        return tool3;
    }

    public void setTool3(String tool3) {
        this.tool3 = tool3;
    }

    public String getTool1key() {
        return tool1key;
    }

    public void setTool1key(String tool1key) {
        this.tool1key = tool1key;
    }

    public String getTool2key() {
        return tool2key;
    }

    public void setTool2key(String tool2key) {
        this.tool2key = tool2key;
    }

    public String getTool3key() {
        return tool3key;
    }

    public void setTool3key(String tool3key) {
        this.tool3key = tool3key;
    }

    @Override
    public String toString() {
        return "SettingParamBean{" +
                "user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", dburl='" + dburl + '\'' +
                ", driver='" + driver + '\'' +
                ", tool1='" + tool1 + '\'' +
                ", tool2='" + tool2 + '\'' +
                ", tool3='" + tool3 + '\'' +
                ", tool1key='" + tool1key + '\'' +
                ", tool2key='" + tool2key + '\'' +
                ", tool3key='" + tool3key + '\'' +
                '}';
    }
}
