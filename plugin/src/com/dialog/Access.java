package com.dialog;

import ConfigPara.TypeEntity;
import com.bean.CodeIfo;
import com.regular.CodeInfoAna;
import com.tools.*;
import org.apache.xmlbeans.impl.xb.ltgfmt.Code;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Access extends JDialog {


    private JPanel contentPane;
    private JButton buttonCancel;
    private JPanel aaa;
    private JPanel bbb;
    private JTextField startTimeTextFiled;
    private JTextField endTimeTextFiled;
    private JButton okButton;
    private JPanel effectPanel;
    private JPanel qualityPanel;


    private JCheckBox successGenerateCheckBox;
    private JCheckBox AVGofGenerateListCheckBox;
    private JCheckBox mutilLineNumsCheckBox;
    private JCheckBox singleLineNumsCheckBox;
    private JCheckBox successGenerateBeateCheckBox;
    private JCheckBox successKeyNumsCheckBox;
    private JPanel selectTimePanel;
    private JCheckBox generateCodeSizeCheckBox;
    private JTable table1;
    private JTable table2;
    private DefaultTableModel defaultQualityTableModel;
    private DefaultTableModel defaultEffectTableModel;
    private JPanel f1;
    private JPanel f2;
    private JPanel f3;
    private JCheckBox generateCodeNumsCheckBox;

    public Access() {
//        初始化界面base Panel

        initBasePane();

        initCheckBox();
//        添加action ok 按键监听事件
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
//                检查checkbox选中情况
//                绘图
//                SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");
                String startTime = startTimeTextFiled.getText();
//                Date date = sdf.parse(startTime);
                String endTime = endTimeTextFiled.getText();
//                使用startTime endTime 筛选数据
                new Thread() {
                    @Override
                    public void run() {
                        qualityPanel.removeAll();
                        effectPanel.removeAll();
                        contentPane.updateUI();
                        f1.removeAll();
                        f2.removeAll();
                        f3.removeAll();
//                        读取时间段内数据
//                        OpenCSVReadBeansEx openCSVReadBeansEx = new OpenCSVReadBeansEx();
//                        List<CodeIfo> codeIfoList = openCSVReadBeansEx.readBeans(startTime, endTime, TypeEntity.getCsvPath());
                        List<CodeIfo> codeIfoList = CodeGenerateRecord.getRecordFromStartEndTime(startTime, endTime);

//                        计算各项指标
                        CodeInfoAna codeInfoAna = new CodeInfoAna(codeIfoList);
                        codeInfoAna.initAna();
//                        判断check box
                        if (successGenerateBeateCheckBox.isSelected()) {
                            f2.setLayout(new GridLayout(1, 1, 5, 5));
                            f2.add(new PieChart(codeInfoAna).getChartPanel());

                        }
                        if (generateCodeNumsCheckBox.isSelected()) {
                            f3.setLayout(new GridLayout(1, 1, 5, 5));
                            f3.add(new TimeSeriesChart(codeIfoList).getChartPanel());
                        }
                        if (mutilLineNumsCheckBox.isSelected() ||
                                singleLineNumsCheckBox.isSelected() ||
                                generateCodeSizeCheckBox.isSelected() ||
                                successKeyNumsCheckBox.isSelected() ||
                                successGenerateCheckBox.isSelected() ||
                                AVGofGenerateListCheckBox.isSelected()
                        ) {
                            DefaultCategoryDataset defaultCategoryDataset = chackBoxStatus(codeInfoAna);
                            f1.setLayout(new GridLayout(1, 1, 5, 5));
                            f1.add(new BarChart(defaultCategoryDataset).getChartPanel());
                        }


//                        String data = null;
//                        try {
//                            data = ModelRequestHttp.sendGut("http://127.0.0.1/tbcnn_param?s=" + startTime.replace(" ", "%") + "&e=" + endTime.replace(" ", "%"), null, null);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        String[] value = data.substring(1, data.length() - 1).split(",");
                        //        初始化代码质量属性表格数据
                        _initQualityTableData(defaultQualityTableModel, new String[]{"1", "2", "3", "4", "5"});
                        //        初始化代码质量属性表格控件
                        _initQualityTable(defaultQualityTableModel);
                        //        初始化代码效率属性表格数据
                        _initeffecTableData(defaultEffectTableModel, new String[]{"1", "2", "3"});
                        //        初始化代码效率属性表格控件
                        _initEffecTable(defaultEffectTableModel);
                        contentPane.updateUI();
                    }
                }.start();
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

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    /**
     * 将数据填充进质量属性表格
     */
    private void _initQualityTable(DefaultTableModel defaultTableModel) {
        table1 = new JTable(defaultTableModel);
        table1.setRowHeight(25);
        table1.setEnabled(false);
        setTableColumnCenter(table1);
        qualityPanel.setLayout(new GridLayout(1, 1, 5, 5));
        qualityPanel.add(table1);
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
        successGenerateCheckBox.setSelected(true);
        AVGofGenerateListCheckBox.setSelected(true);
        generateCodeNumsCheckBox.setSelected(true);
//        this.mutilLineNumsCheckBox.setSelected(true);
        singleLineNumsCheckBox.setSelected(true);
//        this.successGenerateBeateCheckBox.setSelected(true);
        successKeyNumsCheckBox.setSelected(true);
    }

    /**
     * 检查chackBox选中状态 准备 f1图标数据
     */
    private DefaultCategoryDataset chackBoxStatus(CodeInfoAna codeInfoAna) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        if (mutilLineNumsCheckBox.isSelected()) {
            dataset.addValue(codeInfoAna.getIDEAmultiLine_num(), "多行代码生成次数", "IDEA");
            dataset.addValue(codeInfoAna.getAiXcoderfullLine_num(), "多行代码生成次数", "aiXcoder");
        }
        if (singleLineNumsCheckBox.isSelected()) {
            dataset.addValue(codeInfoAna.getIDEAfullLine_num(), "单行代码生成次数", "IDEA");
            dataset.addValue(codeInfoAna.getAiXcoderfullLine_num(), "单行代码生成次数", "aiXcoder");
        }
//        生成代码占用空间
//        if (generateCodeSizeCheckBox.isSelected()) {
//            dataset.addValue(Integer.valueOf(codeInfoAna.getAiXcodeSize().substring(0, codeInfoAna.getAiXcodeSize().length() - 2)), "生成代码占用空间", "aiXcoder");
//            dataset.addValue(Integer.valueOf(codeInfoAna.getIDEAcodeSize().substring(0, codeInfoAna.getIDEAcodeSize().length() - 2)), "生成代码占用空间", "IDEA");
//        }
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
        contentPane.setBounds(50, 50, 1400, 1300);
        setModal(true);
        setTitle("access");
//        setSize(1500, 1400); // 设置窗口大小 2018/3/29 19:08
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int Swing1x = 1000;
        int Swing1y = 500;
        setBounds((screensize.width - Swing1x) / 2, (screensize.height - Swing1y) / 2 - 100, Swing1x, Swing1y);
        SimpleDateFormat tf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        startTimeTextFiled.setText(tf.format(new Date()));
        endTimeTextFiled.setText(tf.format(new Date()));
        defaultQualityTableModel = new DefaultTableModel();
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

    /**
     * 初始化表格数据  调用python TBCNN模块 传入开始结束时间
     * Runtime.getRuntime()执行python脚本文件
     *
     * @param tableModel
     */
    private void _initQualityTableData(DefaultTableModel tableModel, String[] values) {
        Object[][] playerInfo = {
                // 创建表格中的数据
                {"有效性", "可用性", "可靠性", "规范性", "可维护性"},
                values
        };
        // 创建表格中的横标题
        String[] Names = {"有效性", "可用性", "可靠性", "规范性", "可维护性"};
        // 以Names和playerInfo为参数，创建一个表格
        tableModel.setDataVector(playerInfo, Names);

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

    private void accessTable() {
//        accessTable.
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        Access dialog = new Access();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

}

