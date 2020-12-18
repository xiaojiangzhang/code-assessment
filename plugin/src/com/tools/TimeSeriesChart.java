package com.tools;

import com.bean.CodeIfo;
import com.util.ChartUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.util.List;

public class TimeSeriesChart {
    ChartPanel frame1;

    public TimeSeriesChart(List<CodeIfo> codeIfoList) {
        ChartUtils.setChartTheme();

        DefaultCategoryDataset linedataset = new DefaultCategoryDataset();
        String series1 = "aiXcoder";
        String series2 = "IDE";
        //横轴名称(列名称)
        String[] type = new String[codeIfoList.size()];
        for (int i = 0; i < codeIfoList.size(); i++) {
            CodeIfo codeIfo = codeIfoList.get(i);
            linedataset.addValue(Integer.valueOf(codeIfo.getAiXcode_num()), series1, String.valueOf(i));
            linedataset.addValue(Integer.valueOf(codeIfo.getIDEAcode_num()), series2, String.valueOf(i));
        }

        JFreeChart jfreechart = ChartFactory.createLineChart("生成代码个数", "time", "time", linedataset, PlotOrientation.VERTICAL, true, true, true);

//        Font titleFont = new Font("宋体", Font.ITALIC, 13);
//        Font font = new Font("宋体", Font.BOLD, 10);
//        Font legendFont = new Font("宋体", Font.BOLD, 10);
//
//        jfreechart.getTitle().setFont(titleFont);
//        jfreechart.getLegend().setItemFont(legendFont);

        frame1 = new ChartPanel(jfreechart, true);
        CategoryPlot plot = jfreechart.getCategoryPlot();
        ChartUtils.setChartTheme();
        ChartUtils.setLineRender(plot, false);
        ChartUtils.setAntiAlias(jfreechart);

//        plot.setBackgroundPaint(JBColor.black);
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setAutoRangeIncludesZero(true);
        rangeAxis.setUpperMargin(0.20);
        rangeAxis.setLabelAngle(Math.PI / 2.0);
    }


    public ChartPanel getChartPanel() {

        return frame1;

    }
}