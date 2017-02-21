package com.example.nickp.angraph;

import android.content.Context;
import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nickp on 2/21/2017.
 */

public class WBL_LineGraph
{
    protected LineChart chart;
    private double duration;
    private LineDataSet chartData;
    private DecimalFormat df = new DecimalFormat("#");
    private Context context;
    YAxis y;
    XAxis x;
    String xAxisTitle = "Heart Rate";
    ArrayList<Integer> xValues;
    ArrayList<Integer> yValues;

    /**
     * Main Constructor
     *
     * @param activity the activity the graph will be used in
     * @param xV x axis values
     * @param yV y axis values
     */
    public WBL_LineGraph(Context activity, ArrayList xV, ArrayList yV)
    {
        context = activity;
        xValues = xV;
        yValues = yV;

        ArrayList<Entry> xPoints = new ArrayList<Entry>();
        List<String> yPoints = new ArrayList<String>();

        //Loop for creating x and y plot points and finding max/min vals
        int dur = 0;
        int counter = 0;
        int max = xValues.get(0);
        int min = xValues.get(0);
        for(int i = 0; i < xValues.size(); i++)
        {
            if(counter == 25)
            {
                counter = 0;
                dur++;
            }

            xPoints.add(new Entry(xValues.get(i), i));
            yPoints.add("" + dur);

            //Check the max and min
            if(xValues.get(i) > max){max = xValues.get(i);}
            if(xValues.get(i) < min){min = xValues.get(i);}
            counter++;
        }

        //Calculate the duration
        duration = xValues.size() / 25;

        //Plot points created
        chartData = new LineDataSet(xPoints, xAxisTitle);

        //Disable plot point circles, set data color, and set hor. spacing
        chartData.setDrawCircles(false);
        chartData.setColor(Color.BLUE);
        chartData.setHighlightEnabled(false);
        chartData.setDrawHighlightIndicators(false);

        //Disable values for plot points being displayed
        LineData points = new LineData(yPoints, chartData);
        points.setDrawValues(false);
        points.setHighlightEnabled(false);
        chart.setData(points);
        chart.setDescription("");

        //Displays x-axis information at the bottom of the graph, and configure displayed labels
        //Also used for the zoom scale
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        int numLabels = 1;
        if(duration >= 10)
        {
            numLabels = (int) (duration / 10);
        }

        //X-axis configuration
        chart.getXAxis().setLabelsToSkip((int)xValues.size() / numLabels);
        chart.getXAxis().setSpaceBetweenLabels(0);
        x = chart.getXAxis();
        x.setAvoidFirstLastClipping(false);
        x.setDrawLimitLinesBehindData(true);

        //Enable all zooming features, enable a fixed zoom
//        chart.setScaleEnabled(false);
        int test = (xValues.size() / 150);
        chart.zoom((float) (numLabels - 0.1), 0, numLabels, 0);
        chart.setHorizontalScrollBarEnabled(true);
        chart.setPinchZoom(true);

        //Y-axis configuration
        y = chart.getAxisLeft();
//        y.setAxisMaxValue(max + INCREMENT_GRAPH);
//        y.setAxisMinValue(min - INCREMENT_GRAPH);
        setYMaxAndMin(min, max, 5);
        y.setLabelCount(6, true);
        y.setDrawTopYLabelEntry(true);

        //Disable the extra y-axis on the right
        YAxis right = chart.getAxisRight();
        right.setEnabled(false);


        //Create Limit Line for the max x axis label
        LimitLine x_limit = new LimitLine((float) (xValues.size() - 1.1), "" + df.format(duration));
        x_limit.setLineColor(Color.BLACK);
        x_limit.setLineWidth(1f);
        x_limit.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_BOTTOM);
        x_limit.setTextSize(10f);
    }

    /**
     * Method that will allow the user to using pinch-zooming features on the graph
     *
     * @param zoomable true if the graph can pinch zoom
     */
    public void enableZoom(boolean zoomable)
    {
        chart.setPinchZoom(zoomable);
    }

    /**
     * Method that will set the max and min y axis values
     *
     * @param min
     * @param max
     * @param threshold
     */
    public void setYMaxAndMin(int min, int max, int threshold)
    {
        y.setAxisMaxValue(max + threshold);
        y.setAxisMinValue(min - threshold);
    }

    /**
     * Method that will set the max and min x axis values
     *
     * @param min
     * @param max
     * @param threshold
     */
    public void setXMaxAndMin(int min, int max, int threshold)
    {
        x.setAxisMaxValue(max + threshold);
        x.setAxisMinValue(min - threshold);
    }
}
