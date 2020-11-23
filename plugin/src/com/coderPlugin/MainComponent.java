package com.coderPlugin;


import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ex.AnActionListener;
import com.intellij.openapi.components.ProjectComponent;

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
//        Messages.showMessageDialog("编码采集器启动成功,编码愉快！", "Successful！", Messages.getInformationIcon());
        ActionManager actionManager = ActionManager.getInstance();

        actionManager.addAnActionListener(anActionListener);
    }

    @Override
    public void disposeComponent() {
        System.out.println("disposeComponent");

    }
}
