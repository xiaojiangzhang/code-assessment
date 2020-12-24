package com.coderPlugin;

import com.dialog.Access;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class QualityEvalAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here

        Access access = new Access();
        access.setVisible(true);


    }
}
