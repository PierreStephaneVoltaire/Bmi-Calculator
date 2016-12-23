package com.example.steph.bmicalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    TextView resultTextView,Extrainfo;
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
        Extrainfo=(TextView)findViewById(R.id.Result);

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
            resultTextView.setText("Your BMI is " + Math.round(result * 100) / 100.00);

        }
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
