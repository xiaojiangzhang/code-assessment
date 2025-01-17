package com.coderPlugin;

import ConfigPara.TypeEntity;
import com.bean.CodeIfo;
import com.db.JdbcUtils;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementPresentation;
import com.intellij.codeInsight.lookup.LookupManager;
import com.intellij.codeInsight.lookup.impl.LookupImpl;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.ex.AnActionListener;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.Messages;
import com.regular.Classify;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ActionVisitor implements AnActionListener {
    //创建codeinfo对象
    CodeIfo codeIfo = new CodeIfo();
    private Classify classify;//推荐代码分类器
    private List<String> IDEcodea = new ArrayList<String>();
    private List<String> AiXcode = new ArrayList<String>();
    private List<String> Kitecode = new ArrayList<String>();
    private String input = "";
    private long time_of_codelist;//代码列表出现时间
    private long time_of_select;//代码选择完成时间
    private long time_of_input;
    /**
     * check len
     */
    private int len;
    private String selectvalue = "";//选择的代码
    private JList<LookupElement> list;
    /**
     * check
     */
    private String deleteCode = "";
    private List<Integer> IDEACodeIndex = new ArrayList<Integer>();
    private List<Integer> AiXcoderCodeIndex = new ArrayList<Integer>();
    private List<Integer> KiteCodeIndex = new ArrayList<Integer>();
    private String offset;
    private String codeContext;
    private int deleteStartOofset = 0;
    private List<String> actionType = new ArrayList<String>();
    private LookupImpl lookup;
    private JdbcUtils jdbcUtils = new JdbcUtils();


    @Override
    public void beforeActionPerformed(AnAction action, DataContext dataContext, AnActionEvent event) {
        String actionContext = action.getTemplatePresentation().getText();
        String actionPlace = event.getPlace();
        String actionDescription = action.getTemplatePresentation().getDescription();
        System.out.println("action内容：" + actionContext);
        System.out.println("action作用域：" + actionPlace);
        System.out.println("action描述：" + actionDescription);
        if (actionType.size() < 60) {
            actionType.add(actionContext + "  " + actionPlace);
        }
        try {
            Editor editor = event.getData(CommonDataKeys.EDITOR);
            String code = editor.getDocument().getText();
            deleteStartOofset = editor.getCaretModel().getOffset();
            deleteCode += code.substring(deleteStartOofset - 1, deleteStartOofset);
            System.out.println("删除代码" + deleteCode);
            //记录删除内容
        } catch (Exception e) {
            System.out.println(e);
        }
        //选择代码操作
        if (actionContext.equals("Choose Lookup Item") || actionContext.equals("Choose Lookup Item Replace")) {
            LookupManager lookupManager = LookupManager.getInstance(event.getProject());
            lookup = (LookupImpl) lookupManager.getActiveLookup();
            if (lookup != null) {
                offset = String.valueOf(lookup.getEditor().getCaretModel().getOffset());
                codeContext = lookup.getEditor().getDocument().getText();
                System.out.println("!!!!!!!!!!!!!准备处理并存储数据！！！！！！！！！！！！！！！！");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("------------------！！！！！！——————————————————————子线开始程存储数据");

                        if (time_of_codelist == 0) {
                            time_of_codelist = System.currentTimeMillis();
                        }

                        try {
                            codeIfo.setSelect_num(lookup.getSelectedIndex() + 1);//选择推荐代码在推荐列表中的位置
                            list = lookup.getList();
                            len = list.getModel().getSize();

                            LookupElementPresentation lookupElementPresentation = LookupElementPresentation.renderElement(list.getModel().getElementAt(codeIfo.getSelect_num() - 1));
                            if (lookupElementPresentation.getTailText() == null) {

                                selectvalue = lookupElementPresentation.getItemText();
                            } else {
                                selectvalue = lookupElementPresentation.getItemText() + lookupElementPresentation.getTailText();
                            }
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
//                        if ((event.getInputEvent().toString().contains("Enter") || event.getInputEvent().toString().contains("Tab"))) {
                        System.out.println("lookup长度：" + lookup.getList().getModel().getSize());
                        time_of_select = System.currentTimeMillis();
                        System.out.println("开始对推荐列表代码进行分类");

                        classify = new Classify(list, lookup.getSelectedIndex());
                        classify.sorting();

                        codeIfo.setCode_from(classify.getSelectcodeFrom());
                        codeIfo.setAiXcode(classify.getAixcoder().toString());
                        codeIfo.setIDEAcode(classify.getIde().toString());
                        codeIfo.setKiteCode(classify.getKite().toString());

                        codeIfo.setAiXcode_num(String.valueOf(classify.getAixcoder().size()));
                        codeIfo.setIDEAcode_num(String.valueOf(classify.getIde().size()));
                        codeIfo.setKitecode_num(String.valueOf(classify.getKite().size()));

                        System.out.println("sssssssss" + classify.getIDEACodeIndex().toString());
                        codeIfo.setIDEAcode_index(classify.getIDEACodeIndex().toString());
                        codeIfo.setAiXcoder_index(classify.getAiXcoderCodeIndex().toString());
                        codeIfo.setKitecode_index(classify.getKiteCodeIndex().toString());
//                        System.out.println(classify.getAixcoder().toString());
//                        System.out.println(classify.getIde().toString());
//                        System.out.println(classify.getKite().toString());
//                        if ((event.getInputEvent().toString().contains("Enter") || event.getInputEvent().toString().contains("Tab")) && list != null) {
                        //数据存入本地
                        SimpleDateFormat tf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式


                        System.out.println("数据开始入库！！！！！！！！！！！！！！！");
                        String sql = "insert into " + TypeEntity.getTableName() + " (time, dataContext,codeContext,caretOffset,coder_input,coder_select,select_num,code_from,IDEAcode," +
                                "IDEAcode_num,IDEAcode_index,AiXcode,AiXcode_num,AiXcoder_index,KiteCode,Kitecode_num,Kitecode_index," +
                                "time_input_to_show,time_of_select_code,delete_behavior) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                        List<Object> params = new ArrayList<>();
                        params.add(tf.format(new Date()));
                        params.add(dataContext.toString());
                        params.add(codeContext);
                        params.add(offset);
                        params.add(input);
                        params.add(selectvalue);
                        params.add(codeIfo.getSelect_num());
                        params.add(codeIfo.getCode_from());

                        params.add(codeIfo.getIDEAcode());
                        params.add(codeIfo.getIDEAcode_num());
                        params.add(codeIfo.getIDEAcode_index());

                        params.add(codeIfo.getAiXcode());
                        params.add(codeIfo.getAiXcode_num());
                        params.add(codeIfo.getAiXcoder_index());

                        params.add(codeIfo.getKiteCode());
                        params.add(codeIfo.getKitecode_num());
                        params.add(codeIfo.getKitecode_index());

                        params.add(Math.abs(time_of_codelist - time_of_input));
                        params.add(Math.abs(time_of_select - time_of_input));
                        params.add(deleteCode);
                        System.out.println(codeIfo.toString());
                        System.out.println(params);

                        try {
                            jdbcUtils.getConnection();
                            boolean flag = jdbcUtils.updateByPreparedStatement(sql, params);
                            System.out.println(flag + "写入成功");
                            jdbcUtils.connClose();
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            System.out.println("写入失败，请检查连接");
                            e.printStackTrace();
                            Messages.showMessageDialog("数据采集上传失败，请检查数据库配置或检查网络！", "Faile！", Messages.getInformationIcon());
                        }
                        IDEcodea.clear();
                        AiXcode.clear();
                        Kitecode.clear();
                        deleteCode = "";
                        input = "";
                        codeIfo.setCode_from("");
                        time_of_codelist = 0;
                        codeIfo.setSelect_num(1);
                        IDEACodeIndex.clear();
                        AiXcoderCodeIndex.clear();
                        KiteCodeIndex.clear();
                        String sqlStr = "";
                        try {
                            for (int i = 0; i < actionType.size(); i++) {
                                sqlStr += "action" + (i + 1) + "='" + actionType.get(i) + "',";
                            }
                            sqlStr = sqlStr.substring(0, sqlStr.length() - 1);
                            jdbcUtils = new JdbcUtils();
                            jdbcUtils.getConnection();
                            sql = "UPDATE " + TypeEntity.getTableName() + " SET " + sqlStr + " ORDER BY time DESC LIMIT 1";
                            System.out.println("upsql:" + sql);
                            boolean flag = jdbcUtils.executeQuery(sql);
                            jdbcUtils.connClose();
                            System.out.println(flag + "action数据写入成功");
                            actionType.clear();
                        } catch (SQLException e) {
//                            Messages.showMessageDialog("数据采集上传失败，请检查数据库配置或检查网络！", "Faile！", Messages.getInformationIcon());
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        }

        System.out.println("beforeaction 执行");
    }

    @Override
    public void afterActionPerformed(AnAction action, @NotNull DataContext dataContext, AnActionEvent event) {
        System.out.println("afteraction 执行");
    }

    @Override
    public void beforeEditorTyping(char c, @NotNull DataContext dataContext) {
        input += String.valueOf(c);
        time_of_input = System.currentTimeMillis();

    }

}
