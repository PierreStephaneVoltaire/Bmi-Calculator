package com.example.steph.bmicalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    TextView resultTextView, ExtraInfo;
    EditText Weight, Height;
    double WeightMultiplier = 0.453592;
    double HeightMultiplier = 0.01;
    ToggleButton CmOrFeet, LbsOrKg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initialize widgets
        setContentView(R.layout.activity_main);
        CmOrFeet = (ToggleButton) findViewById(R.id.heightToggle);
        LbsOrKg = (ToggleButton) findViewById(R.id.weightToggle);
        Weight = (EditText) findViewById(R.id.Weight);
        Height = (EditText) findViewById(R.id.Height);
        resultTextView = (TextView) findViewById(R.id.Result);
        ExtraInfo = (TextView) findViewById(R.id.ExtraInfo);
        //set on checked listener
        CmOrFeet.setOnCheckedChangeListener(this);
        LbsOrKg.setOnCheckedChangeListener(this);
    }

    // clears all views
    public void Clear(View view) {
        Weight.setText("");
        Height.setText("");
        resultTextView.setText("");
        ExtraInfo.setText("");
        CmOrFeet.setChecked(false);
        LbsOrKg.setChecked(false);
    }

    public void Calculate(View view) {
        //get the values set in the edit texts
        String WeightValue = Weight.getText().toString();
        String HeightValue = Height.getText().toString();
        //check if everything is correct
        boolean AllGood = false;
        double height = 0, weight = 0;
        //if it's empty or set to 0 or lower display an error message
        if (WeightValue.trim().length() == 0 || Double.parseDouble(WeightValue) < 0) {
            Weight.setError(getString(R.string.errorMessage));
            //else  get the weight value
        } else {
            weight = Double.parseDouble(WeightValue);
            AllGood = true;
        }
        //if it's empty or set to 0 or lower display an error message
        if (HeightValue.trim().length() == 0 || Double.parseDouble(HeightValue) < 0) {
            Height.setError(getString(R.string.errorMessage));
            AllGood = false;
            // else get the height value
        } else {
            height = Double.parseDouble(HeightValue);
            AllGood = true;
        }
        //if everything is fine
        if (AllGood) {
            //check if the height is set to ft"inch" or cm. if it's Ft" inch' , convert it to meters
            if (CmOrFeet.isChecked()) {
                double ft = Math.floor(height) * 0.3048;
                double inch = (height - Math.floor(height)) * 0.254;
                height = (inch) + (ft);

            }
            //get the BMI
            double result = (weight * WeightMultiplier) / Math.pow((height * HeightMultiplier), 2);
            //display the result
            displayResult(result);
        }
    }

    public void displayResult(double result) {
        //display BMI
        resultTextView.setText("Your BMI is " + Math.round(result * 100) / 100.00);
        /*Underweight = <18.5
        Normal weight = 18.5–24.9
        Overweight = 25–29.9
        Obesity = BMI of 30 or greater*/
        //Display extra info
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
        ExtraInfo.setText(builder.toString());


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
