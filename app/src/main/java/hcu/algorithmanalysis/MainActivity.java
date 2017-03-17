package hcu.algorithmanalysis;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner mSizeSpinner;
    private Spinner mArraySettingSpinner;
    private RetrieveDataTask mCurrentTask;
    private BarChart mChart;
    private MyChart mChartUtil;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mChart = (BarChart) findViewById(R.id.chart);
        mProgressBar = (ProgressBar) findViewById(R.id.displayProgress);
        mProgressBar.setVisibility(View.INVISIBLE);
        mChartUtil = new MyChart();

        setUpSpinners();
        setRunButton();
    }

    private void setUpSpinners() {
        // Set up input size spinner
        mSizeSpinner = (Spinner) findViewById(R.id.ArraySizeSpinner);
        ArrayAdapter<CharSequence> sizeAdapter = ArrayAdapter.createFromResource(this.getApplication(), R.array.input_size, R.layout.spinner_item);
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSizeSpinner.setAdapter(sizeAdapter);
        mSizeSpinner.setVisibility(View.VISIBLE);

        // set up array setting spinner
        mArraySettingSpinner = (Spinner) findViewById(R.id.ArraySortingTypeSpinner);
        ArrayAdapter<CharSequence> arraySettingAdapter = ArrayAdapter.createFromResource(this.getApplication(), R.array.array_setting, R.layout.spinner_item);
        arraySettingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mArraySettingSpinner.setAdapter(arraySettingAdapter);
        mArraySettingSpinner.setVisibility(View.VISIBLE);
    }

    public void setRunButton() {
        Button runButton = (Button) findViewById(R.id.SortStartButton);

        runButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTestProcess();
            }
        });
    }

    private void startTestProcess() {
        int sizeInput = Integer.parseInt(mSizeSpinner.getSelectedItem().toString());

        if (mCurrentTask != null && mCurrentTask.getStatus() != AsyncTask.Status.FINISHED) {
            Toast.makeText(this.getApplication(), "Test Devam Ediyor Bekleyiniz...", Toast.LENGTH_LONG).show();
        } else {
            mCurrentTask = new RetrieveDataTask();
            mCurrentTask.execute(sizeInput);
        }
    }

    public class RetrieveDataTask extends AsyncTask<Integer, Void, List<IBarDataSet>> {
        @Override
        protected void onPreExecute() {
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<IBarDataSet> doInBackground(Integer... params) {
            int sizeInput = params[0];
            return mChartUtil.getSortAlgorithmsTime(sizeInput, mArraySettingSpinner);
        }

        @Override
        protected void onPostExecute(List<IBarDataSet> dataSets) {
            mProgressBar.setVisibility(View.INVISIBLE);
            if (dataSets != null) {
                mChartUtil.display(mChart, dataSets, chooseLabels());
            }
        }
    }

    private String[] chooseLabels() {
        return new String[] {"Bubble sort", "Merge sort", "Insertion sort","Selection sort", "Quicksort"};
    }
}
