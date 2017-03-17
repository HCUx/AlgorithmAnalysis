package hcu.algorithmanalysis;

import android.graphics.Color;
import android.util.Log;
import android.widget.Spinner;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by stava on 17.03.2017.
 */

public class MyChart {

    private Random mRand;

    public MyChart() {
        mRand = new Random();
    }

    public List<IBarDataSet> getSortAlgorithmsTime(int sizeInput, Spinner currentArraySetting) {
        int[] array = generateArray(sizeInput, currentArraySetting);

        List<IBarDataSet> dataSets = new ArrayList<>();

        ZamanHesaplayici sortTimeUtil = new ZamanHesaplayici();

        List<BarEntry> sortTimeEntries = new ArrayList<>();

        sortTimeEntries.add(new BarEntry(sortTimeUtil.bubbleSortTime(Arrays.copyOf(array, array.length)), 0));
        sortTimeEntries.add(new BarEntry(sortTimeUtil.mergeSortTime(Arrays.copyOf(array, array.length)), 1));
        sortTimeEntries.add(new BarEntry(sortTimeUtil.insertionSortTime(Arrays.copyOf(array, array.length)), 2));
        sortTimeEntries.add(new BarEntry(sortTimeUtil.selectionSortTime(Arrays.copyOf(array, array.length)), 3));
        sortTimeEntries.add(new BarEntry(sortTimeUtil.quickSortTime(Arrays.copyOf(array, array.length)), 4));

        BarDataSet sortTimeDataSet = new BarDataSet(sortTimeEntries, "Algoritmalar");

        sortTimeDataSet.setBarSpacePercent(10f);

        int[][] colorTemplates =  {
                ColorTemplate.JOYFUL_COLORS, ColorTemplate.COLORFUL_COLORS,
                ColorTemplate.VORDIPLOM_COLORS, ColorTemplate.PASTEL_COLORS
        };
        sortTimeDataSet.setColors(colorTemplates[mRand.nextInt(colorTemplates.length)]);

        dataSets.add(sortTimeDataSet);

        return dataSets;
    }

    private int[] generateArray(int n, Spinner currentArraySetting) {
        String arraySetting = currentArraySetting.getSelectedItem().toString();
        int[] array = new int[n];
        if (arraySetting.equalsIgnoreCase("Rastgele")) {
            for (int i = 0; i < n; i++) {
                array[i] = mRand.nextInt(n);
            }
        } else if (arraySetting.equalsIgnoreCase("Düz")) {
            for (int i = 0; i < n; i++) {
                array[i] = i;
            }
        } else if (arraySetting.equalsIgnoreCase("Ters")) {
            for (int i = 0; i < n; i++) {
                array[i] = n - i - 1;
            }
        }
        return array;
    }

    public void display(BarChart chart, List<IBarDataSet> dataSets, String[] labels) {
        BarData data = new BarData(labels, dataSets);
        data.setValueTextSize(15f);

        chart.setData(data);
        setChartLegend(chart);
        chart.notifyDataSetChanged();

        setChartAxis(chart);

        chart.setGridBackgroundColor(Color.TRANSPARENT);
        chart.fitScreen();
        chart.setVisibleXRangeMaximum(10);

        setCharAnimation(chart);

        // disable zooming
        chart.setPinchZoom(false);
        chart.setDoubleTapToZoomEnabled(false);

        chart.invalidate();
    }

    private void setChartAxis(BarChart chart) {
        XAxis xAxis = chart.getXAxis();
        xAxis.setTextSize(10f); // top labels
        xAxis.setLabelsToSkip(0);// number of labels to skip
        xAxis.setSpaceBetweenLabels(0); // space in characters
        chart.setDescription("");
        chart.setDescriptionTextSize(10f);
        chart.setDescriptionPosition(300f, 100f);

        chart.getAxisRight().setDrawLabels(false);
        chart.getAxisLeft().setDrawLabels(false);
        chart.getAxisLeft().setDrawGridLines(false); // vertical line grid
        chart.getXAxis().setDrawGridLines(false);
        chart.getAxisRight().setDrawGridLines(false); // horizontal line grid
    }

    /**
     * Helper method to set the chart's legend.
     * @param chart the bar chart to set legend with.
     */
    private void setChartLegend(BarChart chart) {
        Legend legend = chart.getLegend();
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setFormSize(15f);
        legend.setTextSize(12f);
        legend.setFormToTextSpace(5f);
        legend.setXEntrySpace(20f);
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        legend.setExtra(new int[]{Color.BLACK}, new String[]{"Birim : Mikrosaniye"});
    }

    /**
     * Helper method to set the chart's random animation.
     * @param chart the bar chart to set animation with.
     */
    private void setCharAnimation(BarChart chart) {
        chart.animateY(3000, Easing.EasingOption.EaseInOutBounce);
    }
}
