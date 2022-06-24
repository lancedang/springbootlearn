package com.lance.pdfdemo.util;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class LineChartUtil {

    public LineChartUtil() {

        //initUI();
    }

    public void initUI(String url) throws IOException {

        XYDataset dataset = createXYDataset();
        JFreeChart chart = createXYLineChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);

        //JFreeChartUtil.setLineRender((CategoryPlot) chart.getPlot(), true, true);

        org.jfree.chart.ChartUtils.saveChartAsPNG(new File(url), chart, 1000, 600);
    }

    //创建时间戳横坐标图表
    private XYDataset createTimeSerialDataset() {
        final TimeSeriesCollection dataset = new TimeSeriesCollection();
        //dataset.setDomainIsPointsInTime(true);

        final TimeSeries s1 = new TimeSeries("time1");
        s1.add(new Minute(0, 0, 7, 12, 2003), 1.2);
        s1.add(new Minute(30, 12, 7, 12, 2003), 3.0);
        s1.add(new Minute(15, 14, 7, 12, 2003), 8.0);

        final TimeSeries s2 = new TimeSeries("time2");
        s2.add(new Minute(0, 3, 7, 12, 2003), 0.0);
        s2.add(new Minute(30, 9, 7, 12, 2003), 0.0);
        s2.add(new Minute(15, 10, 7, 12, 2003), 0.0);

        dataset.addSeries(s1);
        dataset.addSeries(s2);

        return dataset;
    }

    private XYDataset createXYDataset() {

        XYSeries series1 = new XYSeries("2014好吧");


        series1.add(18, 530);
        series1.add(20, 580);
        series1.add(25, 740);
        series1.add(30, 901);
        series1.add(40, 1300);
        series1.add(50, 2219);

        XYSeries series2 = new XYSeries("2016阿斯蒂芬");
        series2.add(18, 567);
        series2.add(20, 612);
        series2.add(25, 800);
        series2.add(30, 980);
        series2.add(40, 1210);
        series2.add(50, 2350);

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);

        return dataset;
    }

    private JFreeChart createXYLineChart(final XYDataset dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Average salary per age",
                "Age",
                "Salary (€)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        renderer.setSeriesPaint(1, Color.BLUE);
        renderer.setSeriesStroke(1, new BasicStroke(2.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);
        plot.setRangeGridlinesVisible(false);
        plot.setDomainGridlinesVisible(false);

        LegendTitle legend = chart.getLegend();
        legend.setFrame(BlockBorder.NONE);


        //支持中文
        Font LegendFont = new Font("楷体", Font.PLAIN, 18);

        legend.setItemFont(LegendFont);

        ValueAxis rangeAxis = plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());


        chart.setTitle(new TextTitle("Average Salary per Age",
                        new Font("Serif", Font.BOLD, 18)
                )
        );

        NumberAxis numberAxis =  (NumberAxis) rangeAxis;
        //numberAxis.setAutoTickUnitSelection(false);
        numberAxis.setTickUnit(new NumberTickUnit(1000));

        return chart;
    }

    private JFreeChart createTimeSerialsChart(final XYDataset dataset) {

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Sample Chart",
                "Date",
                "Value",
                dataset,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        renderer.setSeriesPaint(1, Color.BLUE);
        renderer.setSeriesStroke(1, new BasicStroke(2.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);
        plot.setRangeGridlinesVisible(false);
        plot.setDomainGridlinesVisible(false);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle("Average Salary per Age",
                        new Font("Serif", Font.BOLD, 18)
                )
        );

        DateAxis domainAxis = (DateAxis) plot.getDomainAxis();
        domainAxis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM"));


        NumberAxis rangeAxis =  (NumberAxis) plot.getRangeAxis();
        rangeAxis.setAutoTickUnitSelection(false);
        rangeAxis.setTickUnit(new NumberTickUnit(1000));


        return chart;
    }

    public static void main(String[] args) throws IOException {
        File file = new File("D:\\pdf\\222.jpg");
        if (file.exists()) {
            System.out.println("delete");
            file.delete();
        }
        new LineChartUtil().initUI("D:\\pdf\\222.jpg");

    }
}