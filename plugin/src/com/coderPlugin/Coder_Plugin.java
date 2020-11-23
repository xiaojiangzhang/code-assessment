//package com.coderPlugin;
//
//import java.util.*;
//
//import ConfigPara.DialogInfomation;
//import com.PersistentState.PersistentState;
//import com.bean.SettingParamBean;
//import com.intellij.codeInsight.lookup.*;
//import com.regular.Classify;
//import com.regular.CoderInputCorrect;
//import com.intellij.codeInsight.lookup.impl.LookupImpl;
//import com.intellij.openapi.actionSystem.*;
//import com.intellij.openapi.actionSystem.ex.AnActionListener;
//import com.intellij.openapi.editor.actionSystem.EditorActionManager;
//import com.intellij.openapi.editor.actionSystem.TypedAction;
//import com.intellij.openapi.editor.actionSystem.TypedActionHandler;
//import com.intellij.openapi.project.Project;
//import com.intellij.openapi.ui.Messages;
//import com.intellij.openapi.wm.ToolWindow;
//import com.intellij.openapi.wm.ToolWindowManager;
//import com.db.JdbcUtils;
//
//import javax.swing.*;
//import java.sql.SQLException;
//import java.text.SimpleDateFormat;
//import java.lang.Object;
//
//public class Coder_Plugin extends AnAction {
//    private Classify classify;//推荐代码分类器
//    private List<String> IDEcodea = new ArrayList<String>();
//    private List<String> AiXcode = new ArrayList<String>();
//    private List<String> Kitecode = new ArrayList<String>();
//    private String input;
//    private int selectat;
//    private long time_of_codelist;//代码列表出现时间
//    private long time_of_select;//代码选择完成时间
//    private int len;
//    private String selectvalue = "";//选择的代码
//    private int selectNum = 1;//选择代码完成键数
//    private String selectfrom;
//    MyTypedActionHandler myTypedActionHandler;
//    JdbcUtils jdbcUtils;
//    Project project;
//    java.awt.event.KeyEvent keyEvent;
//    JTextArea jTextArea;
//    SimpleDateFormat df;
//    JList<LookupElement> list;
//    LookupElementPresentation lookupElementPresentation;
//    String deleteCode = "";
//    int could_delete = -1;
//    SettingParamBean settingParamBean = PersistentState.readSettingParamBean();
//    private List<Integer> IDEACodeIndex = new ArrayList<Integer>();
//    private List<Integer> AiXcoderCodeIndex = new ArrayList<Integer>();
//    private List<Integer> KiteCodeIndex = new ArrayList<Integer>();
//
//
//    public Coder_Plugin() {
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//        DialogInfomation.setStartTime(df.format(new Date()));
//        final EditorActionManager actionManager = EditorActionManager.getInstance();
//        final TypedAction typedAction = actionManager.getTypedAction();
//        myTypedActionHandler = new MyTypedActionHandler();
//        //将自定义的TypedActionHandler设置进去后，
//        //返回旧的TypedActionHandler，即IDEA自身的TypedActionHandler
//        TypedActionHandler oldHandler = typedAction.setupHandler(myTypedActionHandler);
//        myTypedActionHandler.setOldHandler(oldHandler);
//        System.out.println("构造函数执行");
////        Messages.showMessageDialog("请开启编码信息采集器！", "Attention！", Messages.getInformationIcon());
//    }
//
//
//    @Override
//    public void actionPerformed(AnActionEvent e) {
//        Messages.showMessageDialog("编码采集器启动成功！", "Successful！", Messages.getInformationIcon());
//        project = e.getProject();
//
//        AnActionListener anActionListener = new AnActionListener() {
//            //添加事件监听
//            @Override
//            public void beforeActionPerformed(AnAction action, DataContext dataContext, AnActionEvent event) {
//                System.out.println("*******");
////                keyEvent = (KeyEvent) event.getInputEvent();
//                if (event.getInputEvent().toString().contains("Backspace") && could_delete == 1) {
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            deleteCode += myTypedActionHandler.document.getText().substring(myTypedActionHandler.caretOffset - 1, myTypedActionHandler.caretOffset);
//                            myTypedActionHandler.caretOffset -= 1;
////                  更新删除信息
//                            try {
//                                System.out.println("删除内容：" + deleteCode);
//                                jdbcUtils = new JdbcUtils();
//                                jdbcUtils.getConnection();
//                                String sql2 = "UPDATE data SET delete_behavior= ? ORDER BY time DESC LIMIT 1";
//                                List<Object> params = new ArrayList<Object>();
//                                params.add(deleteCode);
//                                jdbcUtils.updateByPreparedStatement(sql2, params);
//                            } catch (SQLException ex) {
//                                ex.printStackTrace();
//                            }
//                        }
//                    }).start();
//
//                }
//                LookupManager lookupManager = LookupManager.getInstance(project);
//                LookupImpl lookup = (LookupImpl) lookupManager.getActiveLookup();
//                if (lookup != null) {
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            System.out.println("代码推荐列表出现");
//                            try {
//                                time_of_codelist = System.currentTimeMillis();
//                                System.out.println("time_of_codelist" + time_of_codelist);
//                                selectat = lookup.getSelectedIndex();//选择推荐代码在推荐列表中的位置
//                                selectNum = selectat + 1;
////                                System.out.println("选择的位置：" + selectat);
//                                list = lookup.getList();
//                                len = list.getModel().getSize();
//                            } catch (Exception ex) {
//                            }
//                            try {
//                                lookupElementPresentation = LookupElementPresentation.renderElement(list.getModel().getElementAt(selectat));
//                                if (lookupElementPresentation.getTailText() == null) {
//                                    selectvalue = lookupElementPresentation.getItemText();
//                                } else {
//                                    selectvalue = lookupElementPresentation.getItemText() + lookupElementPresentation.getTailText();
//                                }
//                            } catch (Exception e) {
//                            }
//                            if ((event.getInputEvent().toString().contains("Enter") || event.getInputEvent().toString().contains("Tab"))) {
//                                System.out.println("lookup长度：" + lookup.getList().getModel().getSize());
//                                time_of_select = System.currentTimeMillis();
//                                System.out.println("time_of_select" + time_of_select);
//                                for (int i = 0; i < len; i++) {
////                                    System.out.println("list第i个内容：" + lookup.getList().getModel().getElementAt(i));
//                                    LookupElementPresentation lookupElement;
//                                    lookupElement = LookupElementPresentation.renderElement(list.getModel().getElementAt(i));
////                                    try {
////                                        setConsoleValue("lookupElement.getItemText():" + lookupElement.getItemText() +
////                                                "\nlookupElement.getTailText():" + lookupElement.getTailText() +
////                                                "\nicon:" + lookupElement.getIcon().toString() +
////                                                "\nTextFragment:" + lookupElement.toString() +
////                                                "\nlist getModel getElement at :" + list.getModel().getElementAt(i) +
////                                                "\nlist size:" + list.size() +
////                                                "\n***********\n");
////                                    } catch (Exception e) {
////
////                                    }
//                                    try {
////                                        setConsoleValue("lookupElement.getItemText():" + lookupElement.getItemText() +
////                                                "\nlookupElement.getTailText():" + lookupElement.getTailText() +
////                                                "\nicon:" + lookupElement.getIcon().toString() +
////                                                "\n");
////                                        System.out.println("传入分类器代码" + list.getModel().getElementAt(i));
//                                        classify = new Classify(list.getModel().getElementAt(i));
//                                        classify.sorting();
//                                    } catch (Exception e) {
//
//                                    }
////
//
//                                    if (classify.getAiXcoder() != null) {
//                                        StringTokenizer st = new StringTokenizer(classify.getAiXcoder(), " ");
//                                        AiXcode.add(st.nextToken());
//                                        if (selectat == i) {
//                                            selectfrom = "AiXcoder";
//                                        }
//                                        AiXcoderCodeIndex.add(i);
//                                    }
//                                    if ((classify.getIDEAcode() != null) && !classify.getIDEAcode().equals("             ")) {
//                                        IDEcodea.add(classify.getIDEAcode());
//                                        if (selectat == i) {
//                                            selectfrom = "IDEA";
//                                        }
//                                        IDEACodeIndex.add(i);
//
//                                    }
//                                    if (classify.getKitecode() != null) {
//                                        Kitecode.add(classify.getKitecode());
//                                        KiteCodeIndex.add(i);
//                                        if (selectat == i) {
//                                            if (selectfrom != null) {
//                                                selectfrom += "/Kite";
//                                            } else {
//                                                selectfrom = "Kite";
//                                            }
//                                        }
//
//                                    }
//
//                                }
//                                setConsoleValue("----------------------------------------------------" +
//                                        "Input：" + myTypedActionHandler.input +
//                                        "\nSelect code：" + selectvalue +
//                                        "\nCode from：" + selectfrom +
//                                        "\nIDEA code：" + IDEcodea.toString().substring(1, IDEcodea.toString().length() - 1) +
//                                        "\nIDEA code list size：" + IDEcodea.size() +
//                                        "\nAixcoder code：" + AiXcode.toString().substring(1, AiXcode.toString().length() - 1) +
//                                        "\nAiXcoder code list size：" + AiXcode.size() +
//                                        "\nKite code：" + Kitecode.toString().substring(1, Kitecode.toString().length() - 1) +
//                                        "\nKite code list size：" + Kitecode.size() +
//                                        "\nselect num：" + selectNum + "\n");
//                            }
//
//                            System.out.println("IDE预测代码：" + IDEcodea.toString() + "\naixcoder预测代码：" + AiXcode);
//                            System.out.println("按键输入：" + event.getInputEvent());
//                            System.out.println("输入：" + myTypedActionHandler.input +
//                                    "\n选中：" + selectvalue +
//                                    "\n来源：" + selectfrom +
//                                    "\nIDEA推荐代码：" + IDEcodea.toString().substring(1, IDEcodea.toString().length() - 1) +
//                                    "\nIDEA推荐代码列表长度：" + IDEcodea.size() +
//                                    "\nAixcoder推荐代码：" + AiXcode.toString().substring(1, AiXcode.toString().length() - 1) +
//                                    "\nAiXcoder推荐代码列表长度：" + AiXcode.size() +
//                                    "\nKite推荐代码：" + Kitecode.toString() +
//                                    "\nKite推荐代码列表长度：" + Kitecode.size() +
//                                    "\n完成键数：" + selectNum);
//
//                            if ((event.getInputEvent().toString().contains("Enter") || event.getInputEvent().toString().contains("Tab")) && list != null) {
//                                could_delete = 1;
//                                deleteCode = "";
//                                myTypedActionHandler.caretOffset += (selectvalue.length() - 1);
//                                input = new CoderInputCorrect(myTypedActionHandler.input, selectvalue).correct();//纠正预测输入
//                                //数据入库
//                                jdbcUtils = new JdbcUtils();
//                                jdbcUtils.getConnection();
//                                SimpleDateFormat tf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//                                System.out.println("数据开始入库！！！！！！！！！！！！！！！");
//                                String sql = "insert into data (time, dataContext,codeContext,caretOffset,coder_input,coder_select,select_num,code_from,IDEAcode," +
//                                        "IDEAcode_num,IDEAcode_index,AiXcode,AiXcode_num,AiXcoder_index,KiteCode,Kitecode_num,Kitecode_index," +
//                                        "time_input_to_show,time_of_select_code) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";
//                                List<Object> params = new ArrayList<Object>();
//                                params.add(tf.format(new Date()));
//                                params.add(dataContext.toString());
//                                params.add(myTypedActionHandler.document.getText());
//                                params.add(myTypedActionHandler.caretOffset);
//                                params.add(input);
//                                params.add(selectvalue);
//                                params.add(selectNum);
//                                params.add(selectfrom);
//                                params.add(IDEcodea.toString());
//                                params.add(IDEcodea.size());
//                                params.add(IDEACodeIndex.toString());
//                                params.add(AiXcode.toString());
//                                params.add(AiXcode.size());
//                                params.add(AiXcoderCodeIndex.toString());
//                                params.add(Kitecode.toString());
//                                params.add(Kitecode.size());
//                                params.add(KiteCodeIndex.toString());
//                                params.add(time_of_codelist - myTypedActionHandler.time_of_input);
//                                params.add(time_of_select - time_of_codelist);
//                                try {
//                                    boolean flag = jdbcUtils.updateByPreparedStatement(sql, params);
//                                    System.out.println(flag + "写入成功");
//                                } catch (SQLException e) {
//                                    // TODO Auto-generated catch block
//                                    System.out.println("写入失败，请检查连接");
//                                    e.printStackTrace();
//                                }
//                                IDEcodea.clear();
//                                AiXcode.clear();
//                                Kitecode.clear();
//                                selectfrom = null;
//                                selectNum = 1;
//                                IDEACodeIndex.clear();
//                                AiXcoderCodeIndex.clear();
//                                KiteCodeIndex.clear();
//                                myTypedActionHandler.input = null;
//                                jdbcUtils = null;
//                            }
//                        }
//                    }).start();
//                }
//            }
//        };
//        e.getActionManager().addAnActionListener(anActionListener);
//    }
//
//    @Override
//    public void update(AnActionEvent e) {
//    }
//
//    public void setConsoleValue(String s) {
//        //测试toolwindow 2019/03/28 23：14
//        if (project != null) {
//            ToolWindow toolWindow = ToolWindowManager.getInstance(project).getToolWindow("Automatic code generation monitoring");
////             if(toolWindow!=null){
////                 // 无论当前状态为关闭/打开，进行强制打开ToolWindow  2019/03/28 23：14
////                 toolWindow.show(new Runnable() {
////                     @Override
////                     public void run() {
////                     }
////                 });
//            //// ToolWindow未初始化时，可能为空 2019/03/28 23：14
//            try {
//                jTextArea = (JTextArea) ((JScrollPane) toolWindow.getContentManager().getContent(0)
//                        .getComponent().getComponent(0)).getViewport().getComponent(0);
//                if (jTextArea != null) {
//                    jTextArea.append(s);
//                    jTextArea.selectAll();
//                }
//            } catch (Exception el) {
//                el.printStackTrace();
//            }
//        }
//    }
//
//}
