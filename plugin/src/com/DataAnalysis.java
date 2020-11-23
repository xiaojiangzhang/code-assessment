package com;



import ConfigPara.DialogInfomation;
import com.regular.DataAnalyzer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;

public class DataAnalysis extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField TF_startTime;
    private JTextField TF_endTime;
    private JTextArea txtDataAnalysis;
    private JLabel LB_startTime;
    private JLabel LB_endTime;
    private boolean isModified = false;

    public DataAnalysis() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle("Analysis Result");
        setSize(500, 400); // 设置窗口大小 2018/3/29 19:08
        LB_startTime.setText("Monitoring start time"); //初始化标签内容 2018/3/29 19:08
        LB_endTime.setText("Monitoring end time");
        TF_startTime.setText(DialogInfomation.getStartTime());  //初始化窗口的监测开始时间  2019/03/31 10:09
        TF_endTime.setText(DialogInfomation.getEndTime());      //初始化窗口的监测结束时间  2019/03/31 10:09
        isModified = false;             //是否是重新打开窗口 2018/3/29 19:08

        // 设置窗口位置 2018/3/29 19:08
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int Swing1x = 750;
        int Swing1y = 500;
        setBounds((screensize.width - Swing1x) / 2, (screensize.height - Swing1y) / 2 - 100, Swing1x, Swing1y);

        //禁止编辑  2018/3/29 19:08
        txtDataAnalysis.setEditable(false);

        // 鼠标事件 2018/3/29 19:08
        txtDataAnalysis.removeMouseListener(mouseAdapter);
        txtDataAnalysis.addMouseListener(mouseAdapter);

        // 输入变化事件 2018/3/29 19:08
        txtDataAnalysis.getCaret().removeChangeListener(changeListener);
        txtDataAnalysis.getCaret().addChangeListener(changeListener);

        //添加文本框监听 2018/3/29 19:08
        TF_startTime.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                isModified = true;
                DialogInfomation.setStartTime(TF_startTime.getText());
                DialogInfomation.setEndTime(TF_endTime.getText());
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                isModified = true;
                DialogInfomation.setStartTime(TF_startTime.getText());
                DialogInfomation.setEndTime(TF_endTime.getText());
            }
            @Override
            public void changedUpdate(DocumentEvent e) { }
        });
        TF_endTime.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                isModified = true;
                DialogInfomation.setStartTime(TF_startTime.getText());
                DialogInfomation.setEndTime(TF_endTime.getText());
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                isModified = true;
                DialogInfomation.setStartTime(TF_startTime.getText());
                DialogInfomation.setEndTime(TF_endTime.getText());
            }
            @Override
            public void changedUpdate(DocumentEvent e) { }
        });

        //添加按钮监听  2018/3/29 19:08
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
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
     * 鼠标进出/入事件 2019/3/28 22:17
     */
    private MouseAdapter mouseAdapter = new MouseAdapter() {
        public void mouseEntered(MouseEvent mouseEvent) {
            txtDataAnalysis.setCursor(new Cursor(Cursor.TEXT_CURSOR));   //鼠标进入Text区后变为文本输入指针
        }

        public void mouseExited(MouseEvent mouseEvent) {
            txtDataAnalysis.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));   //鼠标离开Text区后恢复默认形态
        }
    };
    /**
     * 鼠标改变事件 2019/3/28 22:17
     */
    private ChangeListener changeListener = new ChangeListener() {
        public void stateChanged(ChangeEvent e) {
            txtDataAnalysis.getCaret().setVisible(true);   //使Text区的文本光标显示
        }
    };

    private void onOK() {
        // add your code here
        if(!isModified){
            //调用数据分析程序，传入开始时间和结束时间
            DataAnalyzer dataAnalyzer=new DataAnalyzer();
            dataAnalyzer.dataAnalyze(TF_startTime.getText(),TF_endTime.getText());
            txtDataAnalysis.append("IDEA Average number of keystrokes for correct recommendation items:" + dataAnalyzer.getIDEA_select_sum());
            txtDataAnalysis.append("\nAixcoder Average number of keystrokes for correct recommendation items:" + dataAnalyzer.getAiXcoder_select_sum());
            txtDataAnalysis.append("\nKite Average number of keystrokes for correct recommendation items:" + dataAnalyzer.getKite_select_sum());

            txtDataAnalysis.append("\nIDEA Average recommendation list length:" + dataAnalyzer.getIDEA_listsize());
            txtDataAnalysis.append("\nAixcoder Average recommendation list length:" + dataAnalyzer.getAiXcoder_listsize());
            txtDataAnalysis.append("\nKite Average recommendation list length:" + dataAnalyzer.getKite_listsize());

            txtDataAnalysis.append("\nIDEA Number of correct recommendations:" + dataAnalyzer.getIDEA_sum());
            txtDataAnalysis.append("\nAixcoder Number of correct recommendations:" + dataAnalyzer.getAiXcoder_sum());
            txtDataAnalysis.append("\nKite Number of correct recommendations:" + dataAnalyzer.getKite_sum());

            txtDataAnalysis.append("\nIDEA Total generated code takes up space:" + dataAnalyzer.getIDEAcodeSize());
            txtDataAnalysis.append("\nAixcoder Total generated code takes up space:" + dataAnalyzer.getAiXcodeSize());
            txtDataAnalysis.append("\nKite Total generated code takes up space:" + dataAnalyzer.getKitecodeSize());

            txtDataAnalysis.append("\nIDEA Number of single-line code completion：" + dataAnalyzer.getIDEAfullLine_num());
            txtDataAnalysis.append("\nAixcoder Number of single-line code completion：" + dataAnalyzer.getAiXcoderfullLine_num());
            txtDataAnalysis.append("\nKite The Number of single-line code completion：" + dataAnalyzer.getKitefullLine_num());

            txtDataAnalysis.append("\nIDEA Number of multiple-line code completion：" + dataAnalyzer.getIDEAmultiLine_num());
            txtDataAnalysis.append("\nAixcoder Number of multiple-line code completion：" + dataAnalyzer.getAiXcodermultLine_num());
            txtDataAnalysis.append("\nKite Number of multiple-line code completion：" + dataAnalyzer.getKitemultLine_num());
        }
        //dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        DataAnalysis dialog = new DataAnalysis();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
