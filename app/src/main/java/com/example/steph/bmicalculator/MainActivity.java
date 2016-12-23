package com.example.steph.bmicalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    TextView resultTextView, Extrainfo;
    EditText Weight, Height;
    double WeightMultiplier = 0.453592;
    double HeightMultiplier = 0.01;
    ToggleButton CmOrFeet, LbsOrKg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CmOrFeet = (ToggleButton) findViewById(R.id.heightToggle);
        LbsOrKg = (ToggleButton) findViewById(R.id.weightToggle);

        CmOrFeet.setOnCheckedChangeListener(this);
        LbsOrKg.setOnCheckedChangeListener(this);
        Weight = (EditText) findViewById(R.id.Weight);
        Height = (EditText) findViewById(R.id.Height);
        resultTextView = (TextView) findViewById(R.id.Result);
        Extrainfo = (TextView) findViewById(R.id.ExtraInfo);

    }

    public void Clear(View view) {
        Weight.setText("");
        Height.setText("");
        resultTextView.setText("");
        Extrainfo.setText("");
        CmOrFeet.setChecked(false);
        LbsOrKg.setChecked(false);
    }

    public void Calculate(View view) {
        String WeightValue = Weight.getText().toString();
        String HeightValue = Height.getText().toString();
        boolean AllGood = false;
        double height = 0, weight = 0;
        if (WeightValue.trim().length() == 0 || Double.parseDouble(WeightValue) < 0) {
            Weight.setError("please enter a valid number");
            AllGood = false;
        } else {
            weight = Double.parseDouble(WeightValue);
            AllGood = true;
        }
        if (HeightValue.trim().length() == 0 || Double.parseDouble(HeightValue) < 0) {
            Height.setError("please enter a valid number");
            AllGood = false;
        } else {
            height = Double.parseDouble(HeightValue);
            AllGood = true;
        }
        if (AllGood == true) {

            if (CmOrFeet.isChecked()) {
                double ft = Math.floor(height) * 0.3048;
                double inch = (height - Math.floor(height)) * 0.254;
                height = (inch) + (ft);

            }
            double result = (weight * WeightMultiplier) / Math.pow((height * HeightMultiplier), 2);
            displayResult(result);
        }
    }

    public void displayResult(double result) {
        resultTextView.setText("Your BMI is " + Math.round(result * 100) / 100.00);
        /*Underweight = <18.5
Normal weight = 18.5–24.9
Overweight = 25–29.9
Obesity = BMI of 30 or greater*/
        StringBuilder builder = new StringBuilder();
        builder.append("Your BMI is ");
        if (result <= 18.5) {
            builder.append("lower or equal to 18.5 .").append("This is considered being Underweight");
        } else if (result < 25) {
            builder.append("somewhere between 18.5 and 24.9 .").append("This is considered being Normal");
        } else if (result < 30) {
            builder.append("somewhere between 25 and 29.9 .").append("This is considered being Overweight");
        } else {
            builder.append("higher or equal to 30 .").append("This is considered being Obese");
        }
        Extrainfo.setText(builder.toString());


    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView == CmOrFeet) {
            if (isChecked) {
                HeightMultiplier = 1;
            } else {
                HeightMultiplier = 0.01;
            }


        } else if (buttonView == LbsOrKg) {
            if (isChecked) {
                WeightMultiplier = 1;
            } else {
                WeightMultiplier = 0.453592;
            }


        }
    }
}
