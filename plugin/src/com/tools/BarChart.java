package com.tools;

import com.dialog.Access;
import com.intellij.ui.JBColor;
import com.regular.CodeInfoAna;
import com.util.ChartUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;

import java.awt.*;

public class BarChart {
    ChartPanel frame1;

    public BarChart(DefaultCategoryDataset dataset, String utils) {
        ChartUtils.setChartTheme();

//        CategoryDataset dataset = new DefaultCategoryDataset();
        JFreeChart chart = ChartFactory.createBarChart3D(
                " ", // 图表标题
                "", // 目录轴的显示标签
                utils, // 数值轴的显示标签
                dataset, // 数据集
                PlotOrientation.VERTICAL, // 图表方向：水平、垂直
                true,           // 是否显示图例(对于简单的柱状图必须是false)
                false,          // 是否生成工具
                false           // 是否生成URL链接
        );
        //从这里开始
        CategoryPlot plot = chart.getCategoryPlot();//获取图表区域对象
        ChartUtils.setChartTheme();
        ChartUtils.setAntiAlias(chart);
        ChartUtils.setBarRenderer(plot,true);
        NumberAxis na= (NumberAxis)plot.getRangeAxis();
        na.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        //StackedBarRenderer3D stackedbarrenderer = (StackedBarRenderer3D)plot.getRenderer();
        //stackedbarrenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        //到这里结束，虽然代码有点多，但只为一个目的，解决汉字乱码问题

        //设置柱子上方数值显示
        BarRenderer3D customBarRenderer = (BarRenderer3D) plot.getRenderer();
        customBarRenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());//显示每个柱的数值
        customBarRenderer.setBaseItemLabelsVisible(true);
        //注意：此句很关键，若无此句，那数字的显示会被覆盖，给人数字没有显示出来的问题
        customBarRenderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(
                ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));
        customBarRenderer.setItemLabelAnchorOffset(10D);// 设置柱形图上的文字偏离值
        customBarRenderer.setItemLabelsVisible(true);
        na.setLowerBound(0.0); //数据轴上的显示最小值;
        plot.setRenderer(customBarRenderer);
        frame1 = new ChartPanel(chart, true);        //这里也可以用chartFrame,可以直接生成一个独立的Frame

    }

    public ChartPanel getChartPanel() {
        return frame1;

    }
}