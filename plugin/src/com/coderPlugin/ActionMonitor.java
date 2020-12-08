package com.coderPlugin;

import com.bean.CodeIfo;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.ex.AnActionListener;
import com.intellij.openapi.editor.Editor;
import org.jetbrains.annotations.NotNull;

public class ActionMonitor implements AnActionListener {
    private CodeIfo codeIfo = new CodeIfo();
    private String time;
    private String dataContext;
    private String codeContext;
    private String caretOffset;
    private String coder_input;
    private String coder_select;
    private String select_num;
    private String code_from;
    private String IDEAcode;
    private String IDEAcode_num;
    private String IDEAcode_index;
    private String AiXcode;
    private String AiXcode_num;
    private String AiXcoder_index;
    private String KiteCode;
    private String Kitecode_num;
    private String Kitecode_index;
    private String time_input_to_show;
    private String time_of_select_code;
    private String delete_behavior;

    //    middle
    private long time_of_input;

    @Override
    public void beforeActionPerformed(@NotNull AnAction action, @NotNull DataContext dataContext, @NotNull AnActionEvent event) {
        String actionContext = action.getTemplatePresentation().getText();
        String actionPlace = event.getPlace();
        String actionDescription = action.getTemplatePresentation().getDescription();
        System.out.println("action内容：" + actionContext);
        System.out.println("action作用域：" + actionPlace);
        System.out.println("action描述：" + actionDescription);
        //记录删除代码内容
//        当前action为删除时,记录删除代码内容，将删除代码内容添加到上一次代码生成记录中
        if (actionContext.equals("Backspace")) {

        } else if (actionContext.equals("Choose Lookup Item")) {

        }


//        Editor editor = event.getData(CommonDataKeys.EDITOR);
//        String code = editor.getDocument().getText();
//        deleteStartOofset = editor.getCaretModel().getOffset();
//        deleteCode += code.substring(deleteStartOofset - 1, deleteStartOofset);
//        System.out.println("删除代码" + deleteCode);

    }

    @Override
    public void afterActionPerformed(@NotNull AnAction action, @NotNull DataContext dataContext, @NotNull AnActionEvent event) {

    }

    @Override
    public void beforeEditorTyping(char c, @NotNull DataContext dataContext) {
        coder_input += String.valueOf(c);
        time_of_input = System.currentTimeMillis();
    }
}
