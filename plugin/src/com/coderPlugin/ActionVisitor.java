package com.coderPlugin;

import ConfigPara.TypeEntity;
import com.db.JdbcUtils;
import com.dvop.csv.DvoCSV;
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
import com.opencsv.CSVWriter;
import com.regular.Classify;
import groovy.json.StringEscapeUtils;
import org.bouncycastle.jcajce.provider.symmetric.IDEA;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ActionVisitor implements AnActionListener {
    private Classify classify;//推荐代码分类器
    private List<String> IDEcodea = new ArrayList<String>();
    private List<String> AiXcode = new ArrayList<String>();
    private List<String> Kitecode = new ArrayList<String>();
    private String input = "";
    private int selectat;
    private long time_of_codelist;//代码列表出现时间
    private long time_of_select;//代码选择完成时间
    private long time_of_input;
    private int len;
    private String selectvalue = "";//选择的代码
    private int selectNum = 1;//选择代码完成键数
    private String selectfrom;
    private JdbcUtils jdbcUtils;
    private JList<LookupElement> list;
    private LookupElementPresentation lookupElementPresentation;
    private String deleteCode = "";
    private List<Integer> IDEACodeIndex = new ArrayList<Integer>();
    private List<Integer> AiXcoderCodeIndex = new ArrayList<Integer>();
    private List<Integer> KiteCodeIndex = new ArrayList<Integer>();
    private String offset;
    private String codeContext;
    private int deleteStartOofset = 0;
    private List<String> actionType = new ArrayList<String>();
    private LookupImpl lookup;

    ActionVisitor() {
        System.out.println("action 构造函数执行！！！");
    }

    @Override
    public void beforeActionPerformed(AnAction action, DataContext dataContext, AnActionEvent event) {
        String actionContext = action.getTemplatePresentation().getText();
//        String a = action.getTemplateText();
        String actionPlace = event.getPlace();
        String actionDescription = action.getTemplatePresentation().getDescription();
        System.out.println("action内容：" + actionContext);
        System.out.println("action作用域：" + actionPlace);
        System.out.println("action描述：" + actionDescription);
        if (actionType.size() < 60) {
            actionType.add(actionContext + "%" + actionPlace);
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
        if (actionContext.equals("Choose Lookup Item")) {
            LookupManager lookupManager = LookupManager.getInstance(event.getProject());
            lookup = (LookupImpl) lookupManager.getActiveLookup();
            if (lookup != null) {
                offset = String.valueOf(lookup.getEditor().getCaretModel().getOffset());
                codeContext = lookup.getEditor().getDocument().getText();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            jdbcUtils = new JdbcUtils();
                            jdbcUtils.getConnection();
                            String sql2 = "UPDATE jicheng SET delete_behavior= ? ORDER BY time DESC LIMIT 1";
                            List<Object> params = new ArrayList<Object>();
                            params.add(deleteCode);
                            jdbcUtils.updateByPreparedStatement(sql2, params);
                            deleteCode = "";
                        } catch (Exception ex) {
                            System.out.println("删除代码入库错误");
                            ex.printStackTrace();
                        }
                        if (time_of_codelist == 0) {
                            time_of_codelist = System.currentTimeMillis();
                        }
                        System.out.println("代码推荐列表出现");
                        try {
                            System.out.println("time_of_codelist" + time_of_codelist);
                            selectat = lookup.getSelectedIndex();//选择推荐代码在推荐列表中的位置
                            selectNum = selectat + 1;
                            list = lookup.getList();
                            len = list.getModel().getSize();

                            lookupElementPresentation = LookupElementPresentation.renderElement(list.getModel().getElementAt(selectat));
                            if (lookupElementPresentation.getTailText() == null) {
                                selectvalue = lookupElementPresentation.getItemText();
                            } else {
                                selectvalue = lookupElementPresentation.getItemText() + lookupElementPresentation.getTailText();
                            }
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                        if ((event.getInputEvent().toString().contains("Enter") || event.getInputEvent().toString().contains("Tab"))) {
                            System.out.println("lookup长度：" + lookup.getList().getModel().getSize());
                            time_of_select = System.currentTimeMillis();
                            System.out.println("time_of_select" + time_of_select);
                            for (int i = 0; i < len; i++) {
                                try {
                                    classify = new Classify(list.getModel().getElementAt(i));
                                    classify.sorting();
                                } catch (Exception e) {
                                }
                                if (classify.getAiXcoder() != null) {
                                    AiXcode.add(classify.getAiXcoder());
                                    if (selectat == i) {
                                        selectfrom = "AiXcoder";
                                    }
                                    AiXcoderCodeIndex.add(i);
                                }
                                if ((classify.getIDEAcode() != null) && !classify.getIDEAcode().equals("             ")) {
                                    IDEcodea.add(classify.getIDEAcode());
                                    if (selectat == i) {
                                        selectfrom = "IDEA";
                                    }
                                    IDEACodeIndex.add(i);
                                }
                                if (classify.getKitecode() != null) {
                                    Kitecode.add(classify.getKitecode());
                                    KiteCodeIndex.add(i);
                                    if (selectat == i) {
                                        if (selectfrom != null) {
                                            selectfrom += "/Kite";
                                        } else {
                                            selectfrom = "Kite";
                                        }
                                    }
                                }
                            }
                        }
                        if ((event.getInputEvent().toString().contains("Enter") || event.getInputEvent().toString().contains("Tab")) && list != null) {
                            //数据入库
                            jdbcUtils = new JdbcUtils();
                            jdbcUtils.getConnection();
                            SimpleDateFormat tf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                            System.out.println("数据开始入库！！！！！！！！！！！！！！！");
                            String sql = "insert into jicheng (time, dataContext,codeContext,caretOffset,coder_input,coder_select,select_num,code_from,IDEAcode," +
                                    "IDEAcode_num,IDEAcode_index,AiXcode,AiXcode_num,AiXcoder_index,KiteCode,Kitecode_num,Kitecode_index," +
                                    "time_input_to_show,time_of_select_code,delete_behavior) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                            List<Object> params = new ArrayList<Object>();
                            params.add(tf.format(new Date()));
                            params.add(dataContext.toString());
                            params.add(codeContext);
                            params.add(offset);
                            params.add(input);
                            params.add(selectvalue);
                            params.add(selectNum);
                            params.add(selectfrom);
                            params.add(IDEcodea.toString());
                            params.add(IDEcodea.size());
                            params.add(IDEACodeIndex.toString());
                            params.add(AiXcode.toString());
                            params.add(AiXcode.size());
                            params.add(AiXcoderCodeIndex.toString());
                            params.add(Kitecode.toString());
                            params.add(Kitecode.size());
                            params.add(KiteCodeIndex.toString());
                            params.add(Math.abs(time_of_codelist - time_of_input));
                            params.add(Math.abs(time_of_select - time_of_input));
                            params.add(deleteCode);
                            String[] line = {tf.format(new Date()), dataContext.toString(), codeContext, offset, input, selectvalue, Integer.toString(selectNum), selectfrom, IDEcodea.toString()
                                    , Integer.toString(IDEcodea.size()), IDEACodeIndex.toString(), AiXcode.toString(), Integer.toString(AiXcode.size()), AiXcoderCodeIndex.toString(), Kitecode.toString(), Integer.toString(Kitecode.size()),
                                    KiteCodeIndex.toString(), Long.toString(Math.abs(time_of_codelist - time_of_input)), Long.toString(Math.abs(time_of_select - time_of_input)), deleteCode};

                            try {
                                CSVWriter writer = new CSVWriter(new FileWriter(TypeEntity.getCsvPath(), true));
                                writer.writeNext(line);
                                writer.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                boolean flag = jdbcUtils.updateByPreparedStatement(sql, params);
                                System.out.println(flag + "写入成功");
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                System.out.println("写入失败，请检查连接");
                                e.printStackTrace();
                                Messages.showMessageDialog("数据采集上传失败，请检查数据库配置或检查网络！", "Faile！", Messages.getInformationIcon());
                            }
                            IDEcodea.clear();
                            AiXcode.clear();
                            Kitecode.clear();
                            input = "";
                            selectfrom = null;
                            time_of_codelist = 0;
                            selectNum = 1;
                            IDEACodeIndex.clear();
                            AiXcoderCodeIndex.clear();
                            KiteCodeIndex.clear();
                            params.clear();
                            String sqlStr = "";
                            try {
                                for (int i = 0; i < actionType.size(); i++) {
                                    sqlStr += "action" + (i + 1) + "='" + actionType.get(i) + "',";
                                }
                                sqlStr = sqlStr.substring(0, sqlStr.length() - 1);
                                jdbcUtils = new JdbcUtils();
                                jdbcUtils.getConnection();
                                sql = "UPDATE jicheng SET " + sqlStr + " ORDER BY time DESC LIMIT 1";
                                boolean flag = jdbcUtils.executeQuery(sql);
                                System.out.println(flag + "action数据写入成功");
                                actionType.clear();
                            } catch (SQLException e) {
                                Messages.showMessageDialog("数据采集上传失败，请检查数据库配置或检查网络！", "Faile！", Messages.getInformationIcon());
                                e.printStackTrace();
                            }
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
