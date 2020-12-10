package com.coderPlugin;

import com.dialog.Access;
import com.dialog.EffectDialog;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class EfficiencyEvaAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        EffectDialog effectDialog = new EffectDialog();
        effectDialog.setVisible(true);
    }
}
