package com;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.demo.BarChartDemo1;
import org.jfree.chart.demo.PieChartDemo1;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

public class Access extends JDialog {
    private JPanel contentPane;

    //    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel aaa;
    private JPanel bbb;
    private JTextField textField1;
    private JTextField textField2;
    private JButton 确定Button;
    private JTable accessTable;
    private JPanel f1;
    private JPanel f2;
    private JPanel f3;


    public Access() {
        setContentPane(contentPane);

        contentPane.setBounds(50, 50, 800, 600);
        setModal(true);
//        getRootPane().setDefaultButton(buttonOK);
        setTitle("access");
        setSize(500, 400); // 设置窗口大小 2018/3/29 19:08
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int Swing1x = 750;
        int Swing1y = 500;
        setBounds((screensize.width - Swing1x) / 2, (screensize.height - Swing1y) / 2 - 100, Swing1x, Swing1y);
        f1.setLayout(new GridLayout(1, 1, 5, 5));
        f2.setLayout(new GridLayout(1, 1, 5, 5));
        f3.setLayout(new GridLayout(1, 1, 5, 5));

        f1.add(new BarChart().getChartPanel());
        f2.add(new PieChart().getChartPanel());
        f3.add(new TimeSeriesChart().getChartPanel());


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

    private void onOK() {
        // add your code here
        dispose();
    }
    private void accessTable(){
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


//        JFrame frame=new JFrame("Java数据统计图");
//        frame.setLayout(new GridLayout(2,2,10,10));
//        frame.add(new BarChart().getChartPanel());           //添加柱形图
//        frame.add(new TimeSeriesChart().getChartPanel());
//        frame.setBounds(50, 50, 800, 600);
//        frame.setVisible(true);
    }
}

class BarChart {
    ChartPanel frame1;

    public BarChart() {
        CategoryDataset dataset = getDataSet();
        JFreeChart chart = ChartFactory.createBarChart3D(
                "生成代码占用空间", // 图表标题
                "代码自动生成工具", // 目录轴的显示标签
                "大小", // 数值轴的显示标签
                dataset, // 数据集
                PlotOrientation.VERTICAL, // 图表方向：水平、垂直
                true,           // 是否显示图例(对于简单的柱状图必须是false)
                false,          // 是否生成工具
                false           // 是否生成URL链接
        );

        //从这里开始
        CategoryPlot plot = chart.getCategoryPlot();//获取图表区域对象
        CategoryAxis domainAxis = plot.getDomainAxis();         //水平底部列表
        domainAxis.setLabelFont(new Font("黑体", Font.BOLD, 14));         //水平底部标题
        domainAxis.setTickLabelFont(new Font("宋体", Font.BOLD, 12));  //垂直标题
        ValueAxis rangeAxis = plot.getRangeAxis();//获取柱状
        rangeAxis.setLabelFont(new Font("黑体", Font.BOLD, 15));
        chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 15));
        chart.getTitle().setFont(new Font("宋体", Font.BOLD, 20));//设置标题字体

        //到这里结束，虽然代码有点多，但只为一个目的，解决汉字乱码问题

        frame1 = new ChartPanel(chart, true);        //这里也可以用chartFrame,可以直接生成一个独立的Frame

    }

    private static CategoryDataset getDataSet() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(100, "IDEA", "单行代码生成次数");
        dataset.addValue(100, "aiXcoder", "单行代码生成次数");
        dataset.addValue(200, "IDEA", "多行代码生成次数");
        dataset.addValue(200, "aiXcoder", "多行代码生成次数");
        dataset.addValue(200, "IDEA", "成功推荐代码次数");
        dataset.addValue(300, "aiXcoder", "成功推荐代码次数");
        dataset.addValue(300, "IDEA", "平均推荐代码列表长度");
        dataset.addValue(300, "aiXcoder", "平均推荐代码列表长度");
        return dataset;
    }

    public ChartPanel getChartPanel() {
        return frame1;

    }
}

class PieChart {
    ChartPanel frame1;

    public PieChart() {
        DefaultPieDataset data = getDataSet();
        JFreeChart chart = ChartFactory.createPieChart3D("成功推荐代码数量", data, true, false, false);
        //设置百分比
        PiePlot pieplot = (PiePlot) chart.getPlot();
        DecimalFormat df = new DecimalFormat("0.00%");//获得一个DecimalFormat对象，主要是设置小数问题
        NumberFormat nf = NumberFormat.getNumberInstance();//获得一个NumberFormat对象
        StandardPieSectionLabelGenerator sp1 = new StandardPieSectionLabelGenerator("{0}  {2}", nf, df);//获得StandardPieSectionLabelGenerator对象
        pieplot.setLabelGenerator(sp1);//设置饼图显示百分比

        //没有数据的时候显示的内容
        pieplot.setNoDataMessage("无数据显示");
        pieplot.setCircular(false);
        pieplot.setLabelGap(0.02D);

        pieplot.setIgnoreNullValues(true);//设置不显示空值
        pieplot.setIgnoreZeroValues(true);//设置不显示负值
        frame1 = new ChartPanel(chart, true);
        chart.getTitle().setFont(new Font("宋体", Font.BOLD, 20));//设置标题字体
        PiePlot piePlot = (PiePlot) chart.getPlot();//获取图表区域对象
        piePlot.setLabelFont(new Font("宋体", Font.BOLD, 10));//解决乱码
        chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 10));
    }

    private static DefaultPieDataset getDataSet() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("aiXcoder", 100);
        dataset.setValue("IDEA", 200);
        return dataset;
    }

    public ChartPanel getChartPanel() {
        return frame1;

    }
}

class TimeSeriesChart {
    ChartPanel frame1;

    public TimeSeriesChart() {
        XYDataset xydataset = createDataset();
        JFreeChart jfreechart = ChartFactory.createTimeSeriesChart("选择代码按键次数", "time", "times", xydataset, true, true, true);
        XYPlot xyplot = (XYPlot) jfreechart.getPlot();
        DateAxis dateaxis = (DateAxis) xyplot.getDomainAxis();
        dateaxis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
        frame1 = new ChartPanel(jfreechart, true);
        dateaxis.setLabelFont(new Font("黑体", Font.BOLD, 14));         //水平底部标题
        dateaxis.setTickLabelFont(new Font("宋体", Font.BOLD, 12));  //垂直标题
        ValueAxis rangeAxis = xyplot.getRangeAxis();//获取柱状
        rangeAxis.setLabelFont(new Font("黑体", Font.BOLD, 15));
        jfreechart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 15));
        jfreechart.getTitle().setFont(new Font("宋体", Font.BOLD, 20));//设置标题字体

    }

    private static XYDataset createDataset() {  //这个数据集有点多，但都不难理解
        TimeSeries timeseries = new TimeSeries("IDEA",
                org.jfree.data.time.Month.class);
        timeseries.add(new Month(2, 2001), 1.80000000000001D);
        timeseries.add(new Month(3, 2001), 7.30000000000001D);
        timeseries.add(new Month(4, 2001), 3.80000000000001D);
        timeseries.add(new Month(5, 2001), 7.59999999999999D);
        timeseries.add(new Month(6, 2001), 8.80000000000001D);
        timeseries.add(new Month(7, 2001), 8.30000000000001D);
        timeseries.add(new Month(8, 2001), 3.90000000000001D);
        timeseries.add(new Month(9, 2001), 2.69999999999999D);
        timeseries.add(new Month(10, 2001), 3.2D);
        timeseries.add(new Month(11, 2001), 1.80000000000001D);
        timeseries.add(new Month(12, 2001), 9.59999999999999D);
        timeseries.add(new Month(1, 2002), 2.90000000000001D);
        timeseries.add(new Month(2, 2002), 8.69999999999999D);
        timeseries.add(new Month(3, 2002), 7.30000000000001D);
        timeseries.add(new Month(4, 2002), 3.90000000000001D);
        timeseries.add(new Month(5, 2002), 9.80000000000001D);
        timeseries.add(new Month(6, 2002), 7D);
        timeseries.add(new Month(7, 2002), 2.80000000000001D);
        TimeSeries timeseries1 = new TimeSeries("aiXcoder",
                org.jfree.data.time.Month.class);
        timeseries1.add(new Month(2, 2001), 9.59999999999999D);
        timeseries1.add(new Month(3, 2001), 3.2D);
        timeseries1.add(new Month(4, 2001), 7.2D);
        timeseries1.add(new Month(5, 2001), 4.09999999999999D);
        timeseries1.add(new Month(6, 2001), 2.59999999999999D);
        timeseries1.add(new Month(7, 2001), 9.2D);
        timeseries1.add(new Month(8, 2001), 6.5D);
        timeseries1.add(new Month(9, 2001), 2.7D);
        timeseries1.add(new Month(10, 2001), 1.5D);
        timeseries1.add(new Month(11, 2001), 6.09999999999999D);
        timeseries1.add(new Month(12, 2001), 0.3D);
        timeseries1.add(new Month(1, 2002), 1.7D);
        timeseries1.add(new Month(2, 2002), 1D);
        timeseries1.add(new Month(3, 2002), 9.59999999999999D);
        timeseries1.add(new Month(4, 2002), 3.2D);
        timeseries1.add(new Month(5, 2002), 1.59999999999999D);
        timeseries1.add(new Month(6, 2002), 8.8D);
        timeseries1.add(new Month(7, 2002), 1.59999999999999D);
        TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
        timeseriescollection.addSeries(timeseries);
        timeseriescollection.addSeries(timeseries1);
        return timeseriescollection;
    }

    public ChartPanel getChartPanel() {
        return frame1;

    }
}

