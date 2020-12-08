package com.tools;

import com.bean.CodeIfo;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Fanshe {
    public static void main(String[] args) {
        Class codeinfo = CodeIfo.class;
        System.out.println("类的名称：" + codeinfo.getName());
        Field[] fields = codeinfo.getDeclaredFields();
//        Field[] fields = codeinfo.getFields();
        System.out.println();
        for(Field field:fields){
            int modifiers = field.getModifiers();
            System.out.print(Modifier.toString(modifiers) + " ");
            //输出变量的类型及变量名
            System.out.println(field.getType().getName()
                    + " " + field.getName());



        }
    }
}
