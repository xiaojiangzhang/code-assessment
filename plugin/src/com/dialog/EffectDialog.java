package com.dialog;

import ConfigPara.TypeEntity;
import com.bean.CodeIfo;
import com.intellij.openapi.ui.Messages;
import com.regular.CodeInfoAna;
import com.tools.*;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class EffectDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonCancel;
    private JTextField startTimeTextFiled;
    private JTextField endTimeTextFiled;
    private JButton okButton;
    private JPanel effectPanel;
    private JPanel bbb;
    private JCheckBox successGenerateCheckBox;
    private JCheckBox AVGofGenerateListCheckBox;
    private JCheckBox mutilLineNumsCheckBox;
    private JCheckBox singleLineNumsCheckBox;
    private JCheckBox successGenerateBeateCheckBox;
    private JCheckBox successKeyNumsCheckBox;
    private JCheckBox generateCodeSizeCheckBox;
    private JTable table2;
    private DefaultTableModel defaultEffectTableModel;
    private JPanel aaa;
    private JCheckBox generateCodeNumsCheckBox;
    private JPanel selectTimePanel;
    private JPanel startTimeJPanel;
    private JPanel endTimeJPanel;
    private JButton clearButton;
    JDialog qualityThreadDialogFrame = null;

    //    codeInfoAna缓存 <time, codeInfoAna>
    private HashMap<String, CodeInfoAna> codeInfoAnaMap = new HashMap<>();
    private HashMap<String, List<CodeIfo>> codeInfoListMap = new HashMap<>();
    //    质量评估结果缓存
    private HashMap<String, String[]> effectValueMap = new HashMap<>();
    private CodeInfoAna codeInfoAna;
    private List<CodeIfo> codeIfoList;
    private String[] effectValue;
    private String data = null;


    public EffectDialog() {
        qualityThreadDialogFrame = this;
//        初始化界面base Panel
        initBasePane();
        initCheckBox();

        //载入时间选择器
        Time1 t1 = new Time1();
        Time2 t2 = new Time2();
        startTimeJPanel.setLayout(new GridLayout(1, 1, 5, 5));
        endTimeJPanel.setLayout(new GridLayout(1, 1, 5, 5));
        startTimeJPanel.add(t1);
        endTimeJPanel.add(t2);
        startTimeJPanel.updateUI();
        endTimeJPanel.updateUI();
        aaa.setLayout(new GridLayout(1, 3, 5, 5));

//        添加action ok 按键监听事件
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                effectPanel.removeAll();
                contentPane.updateUI();
                aaa.removeAll();

                Boolean flag = checkSelectTime(t1.getDate(), t2.getDate());
                //读取数据库中时间段内代码生成记录
                //检查checkbox选中情况
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String startTime = sdf.format(t1.getDate());
                String endTime = sdf.format(t2.getDate());
                //读取数据库中时间段内代码生成记录
                codeIfoList = CodeGenerateRecord.getRecordFromStartEndTime(startTime, endTime);


                if (codeIfoList.size() == 0 && flag) {
                    Messages.showMessageDialog("所选时间段内无代码生成记录，请重新选择时间段！", "提示", Messages.getInformationIcon());
                }
                //若没有选择指标，提示用户
                if (!successGenerateBeateCheckBox.isSelected() &&
                        !successGenerateCheckBox.isSelected() &&
                        !AVGofGenerateListCheckBox.isSelected() &&
                        !generateCodeNumsCheckBox.isSelected() &&
                        !generateCodeSizeCheckBox.isSelected() &&
                        !singleLineNumsCheckBox.isSelected() &&
                        !mutilLineNumsCheckBox.isSelected() &&
                        !successKeyNumsCheckBox.isSelected()) {

                    Messages.showMessageDialog("当前没有选择指标，请选择指标！", "提示", Messages.getInformationIcon());

                } else {
                    if (flag) {
//                    检查checkbox选中情况
//                绘图
//                使用startTime endTime 筛选数据
                        Thread effectThread = new Thread() {
                            @Override
                            public void run() {
                                //发请求
                                if (!effectValueMap.containsKey(startTime + endTime)) {
                                    String url = "http://47.101.184.222/effect?s=" + startTime.replace(" ", "/") +
                                            "&e=" + endTime.replace(" ", "%") + "&t=" + TypeEntity.getTableName();
                                    System.out.println(url);
                                    try {
                                        data = ModelRequestHttp.sendGut("http://192.168.100.223:1024/effect?s=" + startTime.replace(" ", "/") +
                                                "&e=" + endTime.replace(" ", "/") + "&t=" + TypeEntity.getTableName(), null, null);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    if (data.contains("500")) {
                                        data = "[0,0,0,0,0,0,0,0]";
                                    }
//                                    data = "[1,2,3,4,5,6,7,8]";
                                    effectValue = data.substring(1, data.length() - 1).split(",");
                                    effectValueMap.put(startTime + endTime, effectValue);
                                } else {
                                    effectValue = effectValueMap.get(startTime + endTime);
                                }
                                if (!codeInfoAnaMap.containsKey(startTime + endTime)) {
//                        计算各项指标
                                    codeInfoAna = new CodeInfoAna(codeIfoList);
                                    codeInfoAna.initAna();
//                                将当前时间和计算结果进行存储
                                    codeInfoAnaMap.put(startTime + endTime, codeInfoAna);
                                    codeInfoListMap.put(startTime + endTime, codeIfoList);
                                } else {
                                    codeInfoAna = codeInfoAnaMap.get(startTime + endTime);
                                    codeIfoList = codeInfoListMap.get(startTime + endTime);
                                }
//判断check box
                                if (successGenerateBeateCheckBox.isSelected()) {
                                    aaa.add(new PieChart(codeInfoAna).getChartPanel());
                                }
                                if (generateCodeNumsCheckBox.isSelected()) {
                                    aaa.add(new TimeSeriesChart(codeIfoList).getChartPanel());
                                }

                                if (mutilLineNumsCheckBox.isSelected() ||
                                        singleLineNumsCheckBox.isSelected()) {
                                    DefaultCategoryDataset dataset1 = single_double_line_code_chackBoxStatus(codeInfoAna);
                                    aaa.add(new BarChart(dataset1, "次数").getChartPanel());
                                }

                                //生成代码占用空间
                                if (generateCodeSizeCheckBox.isSelected()) {
                                    DefaultCategoryDataset dataset_CodeSize = dataset_CodeSize(codeInfoAna);
                                    aaa.add(new BarChart(dataset_CodeSize, "空间(KB)").getChartPanel());
                                }

                                if (successKeyNumsCheckBox.isSelected() ||
                                        successGenerateCheckBox.isSelected() ||
                                        AVGofGenerateListCheckBox.isSelected()
                                ) {
                                    DefaultCategoryDataset dataset2 = chackBoxStatus(codeInfoAna);
                                    aaa.add(new BarChart(dataset2, "次数/长度").getChartPanel());
                                }

                                //        初始化代码质量属性表格数据
                                _initeffecTableData(defaultEffectTableModel, effectValue);
                                //        初始化代码质量属性表格控件
                                _initEffecTable(defaultEffectTableModel);
                                contentPane.updateUI();
                            }
                        };
                        (new ThreadDiag(qualityThreadDialogFrame, effectThread, "正在执行，请等待......")).start();//启动等待提示框线程
                        effectThread.start();
                        if (data.equals("[0,0,0,0,0,0,0,0]")) {
                            Messages.showMessageDialog("所选时间段内代码自动生成记录解析失败，请重新选择时间段！", "提示", Messages.getInformationIcon());
                        }
                    }
                }
            }
        });
        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        //设置清空按钮监听
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //清空质量表格
                defaultEffectTableModel.getDataVector().clear();
                //清空图表
                aaa.removeAll();
                successGenerateBeateCheckBox.setSelected(false);
                successGenerateCheckBox.setSelected(false);
                AVGofGenerateListCheckBox.setSelected(false);
                generateCodeNumsCheckBox.setSelected(false);
                generateCodeSizeCheckBox.setSelected(false);
                singleLineNumsCheckBox.setSelected(false);
                mutilLineNumsCheckBox.setSelected(false);
                successKeyNumsCheckBox.setSelected(false);
                contentPane.updateUI();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    /**
     * 检查时间段是否正常
     *
     * @return
     */
    private Boolean checkSelectTime(Date d1, Date d2) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTime = fmt.format(d1);
        String endTime = fmt.format(d2);
        //System.out.println("'"+startTime+"'");
        //System.out.println("'"+endTime+"'");

        if (startTime.equals(endTime)) {
            Messages.showMessageDialog("开始时间与结束时间相同，请重新输入！", "提示", Messages.getInformationIcon());
            return false;
        }
        if (startTime.compareTo(endTime) >= 0) {
            Messages.showMessageDialog("开始时间大于结束时间，请重新输入！", "提示", Messages.getInformationIcon());
            return false;
        }
        return true;
    }

    /**
     * 将数据填充进效率属性表格
     *
     * @param defaultTableModel
     */
    private void _initEffecTable(DefaultTableModel defaultTableModel) {
        table2 = new JTable(defaultTableModel);
        table2.setRowHeight(25);
        table2.setEnabled(false);
        setTableColumnCenter(table2);
        effectPanel.setLayout(new GridLayout(1, 1, 5, 5));
        effectPanel.add(table2);
    }

    /**
     * 初始化时间选择控件
     */
    private void initSelectTimeContd() {
        SimpleDateFormat tf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        startTimeTextFiled.setText(tf.format(new Date()));
        endTimeTextFiled.setText(tf.format(new Date()));
    }

    /**
     * 初始化checkbox默认为全选择状态
     */
    private void initCheckBox() {
        successGenerateBeateCheckBox.setSelected(true);
        successGenerateCheckBox.setSelected(false);
        AVGofGenerateListCheckBox.setSelected(true);
        generateCodeNumsCheckBox.setSelected(false);
        generateCodeSizeCheckBox.setSelected(true);
        singleLineNumsCheckBox.setSelected(false);
        successKeyNumsCheckBox.setSelected(true);
        mutilLineNumsCheckBox.setSelected(false);
    }

    private DefaultCategoryDataset dataset_CodeSize(CodeInfoAna codeInfoAna) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//        生成代码占用空间
        if (generateCodeSizeCheckBox.isSelected()) {
            dataset.addValue(codeInfoAna.getAiXcodeSize(), "生成代码占用空间", "aiXcoder");
            dataset.addValue(codeInfoAna.getIDEAcodeSize(), "生成代码占用空间", "IDE");
        }
        return dataset;
    }

    private DefaultCategoryDataset single_double_line_code_chackBoxStatus(CodeInfoAna codeInfoAna) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        if (mutilLineNumsCheckBox.isSelected()) {
            dataset.addValue(codeInfoAna.getIDEAmultiLine_num(), "多行代码生成次数", "IDE");
            dataset.addValue(codeInfoAna.getAiXcoderfullLine_num(), "多行代码生成次数", "aiXcoder");
        }
        if (singleLineNumsCheckBox.isSelected()) {
            dataset.addValue(codeInfoAna.getIDEAfullLine_num(), "单行代码生成次数", "IDE");
            dataset.addValue(codeInfoAna.getAiXcoderfullLine_num(), "单行代码生成次数", "aiXcoder");
        }
        return dataset;
    }

    /**
     * 检查chackBox选中状态 准备 f1图标数据
     */
    private DefaultCategoryDataset chackBoxStatus(CodeInfoAna codeInfoAna) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        /*if (mutilLineNumsCheckBox.isSelected()) {
            dataset.addValue(codeInfoAna.getIDEAmultiLine_num(), "多行代码生成次数", "IDEA");
            dataset.addValue(codeInfoAna.getAiXcoderfullLine_num(), "多行代码生成次数", "aiXcoder");
        }
        if (singleLineNumsCheckBox.isSelected()) {
            dataset.addValue(codeInfoAna.getIDEAfullLine_num(), "单行代码生成次数", "IDEA");
            dataset.addValue(codeInfoAna.getAiXcoderfullLine_num(), "单行代码生成次数", "aiXcoder");
        }*/
//        成功推荐代码平均按键次数
        if (successKeyNumsCheckBox.isSelected()) {
            dataset.addValue(codeInfoAna.getAiXcoder_select_sum(), "成功推荐代码平均按键次数", "aiXcoder");
            dataset.addValue(codeInfoAna.getIDEA_select_sum(), "成功推荐代码平均按键次数", "IDEA");
        }
//        成功推荐代码次数
        if (successGenerateCheckBox.isSelected()) {
            dataset.addValue(codeInfoAna.getIDEA_sum(), "成功推荐代码次数", "IDEA");
            dataset.addValue(codeInfoAna.getAiXcoder_sum(), "成功推荐代码次数", "aiXcoder");
        }
        if (AVGofGenerateListCheckBox.isSelected()) {
            dataset.addValue(codeInfoAna.getIDEA_listsize(), "平均推荐代码列表长度", "IDEA");
            dataset.addValue(codeInfoAna.getAiXcoder_listsize(), "平均推荐代码列表长度", "aiXcoder");
        }

        return dataset;
    }

    /**
     * 初始化面板基础控件
     */
    public void initBasePane() {
        setContentPane(contentPane);
//        contentPane.setBounds(50, 50, 1400, 1300);
        setModal(true);
        setTitle("Efficiency Evaluation");
//        setSize(1500, 1400); // 设置窗口大小 2018/3/29 19:08
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int Swing1x = 1600;
        int Swing1y = 550;
        setBounds((screensize.width - Swing1x) / 2, (screensize.height - Swing1y) / 2 - 100, Swing1x, Swing1y);
        defaultEffectTableModel = new DefaultTableModel();
    }

    /**
     * 表格数据居中
     *
     * @param table
     */
    public void setTableColumnCenter(JTable table) {
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, r);
    }


    private void _initeffecTableData(DefaultTableModel tableModel, String[] values) {
        Object[][] playerInfo = {
                // 创建表格中的数据
                {"高效性", "协助性", "可扩展性"},
                values
        };
        // 创建表格中的横标题
        String[] Names = {"高效性", "协助性", "可扩展性"};
        // 以Names和playerInfo为参数，创建一个表格
        tableModel.setDataVector(playerInfo, Names);

    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        EffectDialog dialog = new EffectDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
