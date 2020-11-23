package com.PersistentState;

import com.bean.SettingParamBean;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.PrintWriter;
import java.io.Writer;

public class PersistentState {
    private static SettingParamBean settingParamBean = new SettingParamBean();
    private final static String filepath = "D://Java_persistent.xml";

    public static String getFilepath() {
        return filepath;
    }

    public static SettingParamBean readSettingParamBean() {
        System.out.println("数据配置参数存储文件地址：" + filepath);
        SAXReader reader = new SAXReader(); // 创建解析器
        Document readdoc = null; // 得到Document
        try {
            readdoc = reader.read(filepath);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element readroot = readdoc.getRootElement(); //得到根元素
        settingParamBean.setUser(readroot.attributeValue("user"));
        settingParamBean.setPassword(readroot.attributeValue("password"));
        settingParamBean.setDburl(readroot.attributeValue("dburl"));
        settingParamBean.setTool1(readroot.attributeValue("tool1"));
        settingParamBean.setTool2(readroot.attributeValue("tool2"));
        settingParamBean.setTool3(readroot.attributeValue("tool3"));
        settingParamBean.setTool1key(readroot.attributeValue("tool1key"));
        settingParamBean.setTool2key(readroot.attributeValue("tool2key"));
        settingParamBean.setTool3key(readroot.attributeValue("tool3key"));
        return settingParamBean;
    }

    public static boolean firstwriteSettingParamBean() {
        try {
            // 1. 创建Docuembnt
            Document doc = DocumentHelper.createDocument();
            // 2. 创建根元素
            Element root = doc.addElement("param");
//            root.addComment("coder-Plugin参数保存");// 添加注释
            root.addAttribute("user", "root");
            root.addAttribute("password", "xiaojiangzhang");
            root.addAttribute("dburl", "jdbc:mysql://www.xiaojiangzhang.top:3306/SampleJavaCode?characterEncoding=UTF-8");
            root.addAttribute("driver", "com.mysql.jdbc.Driver");
            root.addAttribute("tool1", "IDEA");
            root.addAttribute("tool2", "Aixcoder");
            root.addAttribute("tool3", "Kite");
            root.addAttribute("tool1key", "icons.jar");
            root.addAttribute("tool2key", "coder.png");
            root.addAttribute("tool3key", "kite");
            //以下三行为追加数据到 xml 文件时使用

            // 创建输出流
            Writer out = new PrintWriter(filepath, "utf-8");
            // 格式化
            OutputFormat format = new OutputFormat("\t", true);
            format.setTrimText(true);//去掉原来的空白(\t和换行和空格)！
            XMLWriter writer = new XMLWriter(out, format);
            // 把document对象写到out流中。
            writer.write(doc);
            out.close();
            writer.close();
            return true;
        } catch (Exception e) {
            // 把编译异常转换成运行异常！
            throw new RuntimeException(e);
        }
    }

    public static boolean writeSettingParamBean(SettingParamBean settingParamBean) {
        try {
            // 1. 创建Docuembnt
            Document doc = DocumentHelper.createDocument();
            // 2. 创建根元素
            Element root = doc.addElement("param");
//            root.addComment("coder-Plugin参数保存");// 添加注释
            root.addAttribute("user", settingParamBean.getUser());
            root.addAttribute("password", settingParamBean.getPassword());
            root.addAttribute("dburl", settingParamBean.getDburl());
            root.addAttribute("driver", settingParamBean.getDriver());
            root.addAttribute("tool1", settingParamBean.getTool1());
            root.addAttribute("tool2", settingParamBean.getTool2());
            root.addAttribute("tool3", settingParamBean.getTool3());
            root.addAttribute("tool1key", settingParamBean.getTool1key());
            root.addAttribute("tool2key", settingParamBean.getTool2key());
            root.addAttribute("tool3key", settingParamBean.getTool3key());
            //以下三行为追加数据到 xml 文件时使用

            // 创建输出流
            Writer out = new PrintWriter(filepath, "utf-8");
            // 格式化
            OutputFormat format = new OutputFormat("\t", true);
            format.setTrimText(true);//去掉原来的空白(\t和换行和空格)！
            XMLWriter writer = new XMLWriter(out, format);
            // 把document对象写到out流中。
            writer.write(doc);
            out.close();
            writer.close();
            return true;
        } catch (Exception e) {
            // 把编译异常转换成运行异常！
            throw new RuntimeException(e);
        }
    }


}
