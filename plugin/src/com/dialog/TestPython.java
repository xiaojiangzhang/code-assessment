package com.dialog;

import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

import java.util.jar.JarOutputStream;

public class TestPython {
    public static void main(String[] args) {
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.exec("sys.path.append('C:\\Users\\xiaojiangzhang\\Anaconda3\\Lib')");//jython自己的
        interpreter.exec("sys.path.append('C:\\Users\\Administrator\\AppData\\Local\\Programs\\Python\\Python36\\Lib\\site-packages')");//jython自己的
        interpreter.execfile("C:\\Users\\xiaojiangzhang\\Desktop\\TimeCountFor8Labels\\Setup4IDEA.py");
        // 第一个参数为期望获得的函数（变量）的名字，第二个参数为期望返回的对象类型
        PyFunction pyFunction = interpreter.get("TrainForIDEA2Time", PyFunction.class);
        String path = "data/Record.csv";
        String StartTime = "2020-07-18 10:49:04";
        String EndTime = "2020-07-19 14:48:47";
        //调用函数，如果函数需要参数，在Java中必须先将参数转化为对应的“Python类型”
        PyObject pyobj = pyFunction.__call__(new PyString(StartTime), new PyString(EndTime), new PyString(path));
        System.out.println("the anwser is: " + pyobj);
    }
}
