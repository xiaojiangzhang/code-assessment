package com.coderPlugin;

import ConfigPara.TypeEntity;
import com.dvop.csv.DvoCSV;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ex.AnActionListener;
import com.intellij.openapi.components.ProjectComponent;
import java.io.File;
import java.io.IOException;

public class MainComponent implements ProjectComponent {
    private AnActionListener anActionListener = new ActionVisitor();

    @Override
    public void projectOpened() {
        System.out.println("Application Component projectOpened");
    }

    @Override
    public void projectClosed() {
        System.out.println("projectClosed");

    }


    @Override
    public void initComponent() {
//        初始化数据存储
        String property = System.getProperties().getProperty("user.home");
        String dataName = "\\data.csv";
        File dataFile = new File(property + dataName);
        TypeEntity.setCsvPath(dataFile.getPath());
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
                String[] header = {"time", "dataContext", "codeContext", "caretOffset", "coder_input", "coder_select", "select_num", "code_from", "IDEAcode",
                        "IDEAcode_num", "IDEAcode_index", "AiXcode", "AiXcode_num", "AiXcoder_index", "KiteCode", "Kitecode_num", "Kitecode_index",
                        "time_input_to_show", "time_of_select_code", "delete_behavior"};
                DvoCSV.writeCSV(dataFile.getPath(), header);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        初始化action监听
        ActionManager actionManager = ActionManager.getInstance();
        actionManager.addAnActionListener(anActionListener);

    }

    @Override
    public void disposeComponent() {
        System.out.println("disposeComponent");

    }
}
