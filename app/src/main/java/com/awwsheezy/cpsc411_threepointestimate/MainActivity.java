package com.awwsheezy.cpsc411_threepointestimate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements TextView.OnEditorActionListener, View.OnClickListener {
    float pessimisticValue, optimisticValue, nominalValue;
    Button calculateButton;
    EditText pessimisticEditText, optimisticEditText, nominalEditText;
    TextView meanTextView, standardDeviationTextView;
    String optimisticString, nominalString, pessimisticString, meanString, standardDeviationString;
    private static final String TAG = "EstimateActivity";

    // We'll use this to restrict our Mean and Standard Deviation to one decimal.
    java.text.DecimalFormat floatFormat = new java.text.DecimalFormat("##.0");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calculateButton = (Button) findViewById(R.id.calculateButton);
        meanTextView = (TextView) findViewById(R.id.meanTextView);
        standardDeviationTextView = (TextView) findViewById(R.id.standardDeviationTextView);

        // Input Restricted by setting "inputType="numberDecimal" in layout
        optimisticEditText = (EditText) findViewById(R.id.optimisticEditText);
        nominalEditText = (EditText) findViewById(R.id.nominalEditText);
        pessimisticEditText = (EditText) findViewById(R.id.pessimisticEditText);

        // Activate our Listener
        calculateButton.setOnClickListener(this);

        // Restore values saved previously via onSaveInstanceState() to survive orientation change
        if (savedInstanceState != null) {
            optimisticString = savedInstanceState.getString("optimisticString");
            nominalString = savedInstanceState.getString("nominalString");
            pessimisticString = savedInstanceState.getString("pessimisticString");
            meanString = savedInstanceState.getString("meanString");
            standardDeviationString = savedInstanceState.getString("standardDeviationString");
            Log.d(TAG, "getString() returned optimisticString = " + optimisticString) ;
            Log.d(TAG, "getString() returned nominalString = " + nominalString) ;
            Log.d(TAG, "getString() returned pessimisticString = " + pessimisticString) ;
        } else {
            optimisticString = "";
            nominalString = "";
            pessimisticString = "";
            Log.d(TAG, "savedInstanceState was null");
        }

        updateTextView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) { // implemented to save values
        super.onSaveInstanceState(outState);
        outState.putString("optimisticString", optimisticString);
        outState.putString("nominalString", nominalString);
        outState.putString("pessimisticString", pessimisticString);
        outState.putString("meanString", meanString);
        outState.putString("standardDeviationString", standardDeviationString);
        Log.d(TAG, "Saved instance state optimisticString =  " + optimisticString);
        Log.d(TAG, "Saved instance state nominalString =  " + nominalString);
        Log.d(TAG, "Saved instance state pessimisticString =  " + pessimisticString);
        Log.d(TAG, "Saved instance state meanString =  " + meanString);
        Log.d(TAG, "Saved instance state standardDeviationString =  " + standardDeviationString);
    }

    private void updateTextView() {
        nominalEditText.setText(nominalString);
        optimisticEditText.setText(optimisticString);
        pessimisticEditText.setText(pessimisticString);
        meanTextView.setText(meanString);
        standardDeviationTextView.setText(standardDeviationString);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) { // Unused thus far..
        return false;
    }

    float calculateMean(float o, float n, float p) {
        return (o + 4*n + p) / 6;
    }

    float calculateStandardDeviation(float o, float n, float p) {
        return (p - o) / 6;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.calculateButton:
                optimisticString = optimisticEditText.getText().toString();
                pessimisticString = pessimisticEditText.getText().toString();
                nominalString = nominalEditText.getText().toString();
                // Need to check for null inputs, as they crash app
                if (optimisticString.equals("") || pessimisticString.equals("") || nominalString.equals("")) {
                    Toast.makeText(this, R.string.requiredInputString, Toast.LENGTH_SHORT).show();
                } else {
                    optimisticValue = Float.parseFloat(optimisticString);
                    pessimisticValue = Float.parseFloat(pessimisticString);
                    nominalValue = Float.parseFloat(nominalString);
                    meanString = String.valueOf(floatFormat.format(calculateMean(optimisticValue, nominalValue, pessimisticValue)));
                    standardDeviationString = String.valueOf(floatFormat.format(calculateStandardDeviation(optimisticValue, nominalValue, pessimisticValue)));
                    meanTextView.setText(meanString);
                    standardDeviationTextView.setText(standardDeviationString);
                }
                break;
            default:
                break;
        }
    }
}
