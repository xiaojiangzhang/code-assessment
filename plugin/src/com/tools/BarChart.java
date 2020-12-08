package com.tools;

import com.dialog.Access;
import com.intellij.ui.JBColor;
import com.regular.CodeInfoAna;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;

public class BarChart {
    ChartPanel frame1;

    public BarChart(DefaultCategoryDataset dataset) {
//        CategoryDataset dataset = new DefaultCategoryDataset();

        JFreeChart chart = ChartFactory.createBarChart3D(
                " ", // 图表标题
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
//        plot.setBackgroundPaint(JBColor.white);
        CategoryAxis domainAxis = plot.getDomainAxis();         //水平底部列表
        domainAxis.setLabelFont(new Font("宋体", Font.BOLD, 10));         //水平底部标题
        domainAxis.setTickLabelFont(new Font("宋体", Font.BOLD, 10));  //垂直标题
        ValueAxis rangeAxis = plot.getRangeAxis();//获取柱状
        rangeAxis.setLabelFont(new Font("宋体", Font.BOLD, 10));
        chart.getLegend().setItemFont(new Font("宋体", Font.BOLD, 10));
        chart.getTitle().setFont(new Font("宋体", Font.BOLD, 13));//设置标题字体

        //到这里结束，虽然代码有点多，但只为一个目的，解决汉字乱码问题

        frame1 = new ChartPanel(chart, true);        //这里也可以用chartFrame,可以直接生成一个独立的Frame

    }

    public ChartPanel getChartPanel() {
        return frame1;

    }
}