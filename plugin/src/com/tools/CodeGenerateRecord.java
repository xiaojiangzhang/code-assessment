package com.tools;

import ConfigPara.TypeEntity;
//import android.content.pm.UserInfo;
import com.bean.CodeIfo;
import com.db.JdbcUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.xmlbeans.impl.xb.ltgfmt.Code;

import java.lang.reflect.Field;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CodeGenerateRecord {
    /**
     * 获取时间段内代码生成记录
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return codeInfo list
     */
    public static List<CodeIfo> getRecordFromStartEndTime(String startTime, String endTime) {
        JdbcUtils jdbcUtils = new JdbcUtils();
        jdbcUtils.getConnection();
        String sql2 = "select * from "+TypeEntity.getTableName()+" where time >= '" + startTime + "' and time <= '" + endTime + "'";
        List<Map<String, Object>> list = null;
        try {
            list = jdbcUtils.findModeResult(sql2, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ArrayList<CodeIfo> codeIfoArrayList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            CodeIfo codeIfo = new CodeIfo();
            codeIfo.setId(list.get(i).get("id").toString());
            codeIfo.setTime(list.get(i).get("time").toString());
            codeIfo.setDataContext(list.get(i).get("dataContext").toString());
            codeIfo.setCodeContext(list.get(i).get("codeContext").toString());
            codeIfo.setCaretOffset(list.get(i).get("caretOffset").toString());
            codeIfo.setCoder_input(list.get(i).get("coder_input").toString());
            codeIfo.setCoder_select(list.get(i).get("coder_select").toString());
            codeIfo.setSelect_num(Integer.parseInt(list.get(i).get("select_num").toString()));
            codeIfo.setCode_from(list.get(i).get("code_from").toString());
            codeIfo.setIDEAcode(list.get(i).get("IDEAcode").toString());
            codeIfo.setIDEAcode_num(list.get(i).get("IDEAcode_num").toString());
            codeIfo.setIDEAcode_index(list.get(i).get("IDEAcode_index").toString());
            codeIfo.setAiXcode(list.get(i).get("AiXcode").toString());
            codeIfo.setAiXcode_num(list.get(i).get("AiXcode_num").toString());
            codeIfo.setAiXcoder_index(list.get(i).get("AiXcoder_index").toString());
            codeIfo.setKiteCode(list.get(i).get("KiteCode").toString());
            codeIfo.setKitecode_num(list.get(i).get("Kitecode_num").toString());
            codeIfo.setKitecode_index(list.get(i).get("Kitecode_index").toString());
            codeIfo.setTime_input_to_show(list.get(i).get("time_input_to_show").toString());
            codeIfo.setTime_input_to_show(list.get(i).get("time_of_select_code").toString());
            codeIfo.setDelete_behavior(list.get(i).get("delete_behavior").toString());
            codeIfo.setTime_of_select_code(list.get(i).get("time_of_select_code").toString());
            codeIfo.setTime_of_select_code(list.get(i).get("time_of_select_code").toString());
            codeIfo.setTime_of_select_code(list.get(i).get("time_of_select_code").toString());
            codeIfo.setTime_of_select_code(list.get(i).get("time_of_select_code").toString());
            codeIfoArrayList.add(codeIfo);
            jdbcUtils.connClose();
        }
        return codeIfoArrayList;
    }

    public static void main(String[] args) {
        getRecordFromStartEndTime("2020-11-29 17:12:17", "2020-12-07 19:42:25");

    }

    /**
     * map反射为对象
     *
     * @param map   键值对
     * @param clazz 反射对象类
     * @param <T>
     * @return
     */
    public static <T> T map2Object(Map<String, Object> map, Class<T> clazz) {
        Object obj;
        try {
            obj = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        ConvertUtils.register(new Converter() {
            public Object convert(Class type, Object value) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                if (value == null) {
                    return "";
                } else {
                    try {
                        return simpleDateFormat.parse(value.toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                return "";
            }
        }, Date.class);
        for (Object o : map.keySet()) {
            //获得参数名
            String name = String.valueOf(o);
            System.out.println(name);
            //获得参数值
            Object value = map.get(name);
            //然后把参数拷贝到javaBean对象中
            try {
                if (value != null) {//空值不处理，否则会报空指针异常
                    BeanUtils.setProperty(obj, name, value);
                } else {
                    BeanUtils.setProperty(obj, name, "");
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return (T) obj;
    }
}
