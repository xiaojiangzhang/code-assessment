package com.coderPlugin;

import ConfigPara.DialogInfomation;
import com.dialog.DataAnalysis;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

import java.text.SimpleDateFormat;
import java.util.Date;


//数据分析action
public class DataAnalysisAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        DialogInfomation.setEndTime(df.format(new Date()));
        DataAnalysis dataAnalysis = new DataAnalysis();
        dataAnalysis.setVisible(true);

    }
}
