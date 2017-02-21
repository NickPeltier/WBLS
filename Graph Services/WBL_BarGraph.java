import android.content.Context;
import android.graphics.Color;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nickp on 2/21/2017.
 */

public class WBL_BarGraph
{
    private BarChart chart;
    private BarData chartData;
    private ArrayList<Integer> xValues;
    private ArrayList<Integer> yValues;
    private int min, max;
    private Context context;
    private String xAxisLabel;
    private BarDataSet dat;

    /**
     * Main constructor
     *
     * @param chart the chart that is set to the correct view find. EX: chart = (BarChart) findViewById(R.id.chart);
     * @param activity the activity the graph will be used in
     * @param graphTitle description of what you are graphing
     * @param xDesc description of the values on the x axis
     * @param xV x axis values
     * @param yV y axis values
     */
    public WBL_BarGraph(BarChart chart, Context activity, String graphTitle, String xDesc,
                            ArrayList xV, ArrayList yV)
    {
        this.chart = chart;
        context = activity;
        xAxisLabel = xDesc;
        xValues = xV;
        yValues = yV;

        //Hide the x-values from being visable at the far right of graph
        YAxis yAxisRight = chart.getAxisRight();
        yAxisRight.setEnabled(false);

        //Set description at bottom of graph
        chart.setDescription(graphTitle);

        //Initilize lists
        ArrayList<BarEntry> xPoints = new ArrayList<BarEntry>();
        List<String> yPoints = new ArrayList<String>();

        //Add values to lists and compute max value
        min = 0;
        max = xValues.get(0);
        for(int x = 0; x < xValues.size(); x++)
        {
            int cur = xValues.get(x);

            xPoints.add(new BarEntry(cur, x));
            yPoints.add("" + (x + 1));

            if(cur > max)
            {
                max = cur;
            }
        }

        //Create and set bargraph data
        dat = new BarDataSet(xPoints, xAxisLabel);

        //Change the color of bars
        dat.setColor(Color.BLUE);

        chartData = new BarData(yPoints, dat);
        chart.setData(chartData);

        //Disable vertical pinch zooming
        setZoom(false);
    }

    /**
     * Method that will allow the user to using pinch-zooming features on the graph
     *
     * @param zoomable true if the graph can pinch zoom
     */
    public void setZoom(boolean zoomable)
    {
        chart.setScaleYEnabled(zoomable);
    }

    public void setBarSpacing(int space)
    {
        //Change the spacing between bars
        dat.setBarSpacePercent(space);
    }
}
