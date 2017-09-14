package com.awwsheezy.cpsc411_threepointestimate;

/*
    Author: Michael Romero
    Date: September 14, 2017
    Course: CPSC 411, Mobile Application Development
    Professor: Kenytt Avery
    Description: This application provides the user with a "three-point-estimate" which he/she
    may use to provide a project manager with a somewhat realistic expectation of when a particular
    task will be finished.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    float pessimisticValue, optimisticValue, nominalValue;
    Button calculateButton;
    EditText pessimisticEditText, optimisticEditText, nominalEditText;
    TextView meanTextView, standardDeviationTextView, resultTextView;
    String optimisticString, nominalString, pessimisticString, meanString, standardDeviationString, resultString;
    private static final String TAG = "EstimateActivity";

    // We'll use this to restrict our Mean and Standard Deviation to one decimal.
    java.text.DecimalFormat floatFormat = new java.text.DecimalFormat("##.0");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Pull in our widgets (EditText widget set to 'numberDecimal')
        optimisticEditText = (EditText) findViewById(R.id.optimisticEditText);
        nominalEditText = (EditText) findViewById(R.id.nominalEditText);
        pessimisticEditText = (EditText) findViewById(R.id.pessimisticEditText);
        calculateButton = (Button) findViewById(R.id.calculateButton);
        meanTextView = (TextView) findViewById(R.id.meanTextView);
        standardDeviationTextView = (TextView) findViewById(R.id.standardDeviationTextView);
        resultTextView = (TextView) findViewById(R.id.resultTextView);

        // Activate our Listener
        calculateButton.setOnClickListener(this);

        // Restore values saved previously via onSaveInstanceState() to survive orientation change
        if (savedInstanceState != null) {
            optimisticString = savedInstanceState.getString("optimisticString");
            nominalString = savedInstanceState.getString("nominalString");
            pessimisticString = savedInstanceState.getString("pessimisticString");
            meanString = savedInstanceState.getString("meanString");
            standardDeviationString = savedInstanceState.getString("standardDeviationString");
            resultString = savedInstanceState.getString("resultString");
            Log.d(TAG, "getString() returned optimisticString = " + optimisticString) ;
            Log.d(TAG, "getString() returned nominalString = " + nominalString) ;
            Log.d(TAG, "getString() returned pessimisticString = " + pessimisticString) ;
            Log.d(TAG, "getString() returned meanString = " + meanString) ;
            Log.d(TAG, "getString() returned standardDeviationString = " + standardDeviationString) ;
            Log.d(TAG, "getString() returned resultString = " + resultString) ;
        } else {
            optimisticString = "";
            nominalString = "";
            pessimisticString = "";
            meanString = "";
            standardDeviationString = "";
            resultString = "";
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
        outState.putString("resultString", resultString);
        Log.d(TAG, "Saved instance state optimisticString =  " + optimisticString);
        Log.d(TAG, "Saved instance state nominalString =  " + nominalString);
        Log.d(TAG, "Saved instance state pessimisticString =  " + pessimisticString);
        Log.d(TAG, "Saved instance state meanString =  " + meanString);
        Log.d(TAG, "Saved instance state standardDeviationString =  " + standardDeviationString);
        Log.d(TAG, "Saved instance state resultString =  " + resultString);
    }

    private void updateTextView() {
        nominalEditText.setText(nominalString);
        optimisticEditText.setText(optimisticString);
        pessimisticEditText.setText(pessimisticString);
        meanTextView.setText(meanString);
        standardDeviationTextView.setText(standardDeviationString);
        if (meanString.equals("") && standardDeviationString.equals("")) {
            resultString = "";
            resultTextView.setText(resultString);
        } else {
            resultString = getResources().getString(R.string.resultString);
            resultString += " ";
            resultString += meanString;
            resultString += getResources().getString(R.string.plusOrMinusString);
            resultString += standardDeviationString;
            resultString += " ";
            resultString += getResources().getString(R.string.daysString);
            resultTextView.setText(resultString);
        }
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
                    updateTextView();
                }
                break;
            default:
                break;
        }
    }
}
