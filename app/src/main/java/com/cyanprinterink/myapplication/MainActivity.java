package com.cyanprinterink.myapplication;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void displayTip(View view)
    {
        NumberFormat fmt = NumberFormat.getNumberInstance();
        fmt.setMinimumFractionDigits(2);
        fmt.setMaximumFractionDigits(2);
        EditText totalText = (EditText) findViewById(R.id.totalCost);
        EditText taxText = (EditText) findViewById(R.id.taxPercent);
        CheckBox roundBox = (CheckBox) findViewById(R.id.roundTip);
        TextView tip = (TextView) findViewById(R.id.tip);
        double total;
        double tax;
        try
        {
            total = Double.parseDouble(totalText.getText().toString());
            tax = Double.parseDouble(taxText.getText().toString());
        }
        catch(Exception e)
        {
            tip.setText("Please fill in a total and a tax value.");
            tip.setVisibility(TextView.VISIBLE);
            return;
        }
        boolean round = roundBox.isChecked();
        double tipTotal;
        double subtotal = total / (tax + 1);
        subtotal = twoDec(subtotal);
        tipTotal = subtotal * .15;
        tipTotal = twoDec(tipTotal);
        if(round)
        {
            double temp = total + tipTotal;
            double additional = Math.ceil(temp) - temp;
            tipTotal += additional;
            tipTotal = twoDec(tipTotal);
        }
        tip.setText("Tip: $" + fmt.format(tipTotal) + "\nTotal: $" + fmt.format(total + tipTotal));
        tip.setVisibility(TextView.VISIBLE);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(taxText.getWindowToken(), 0);
    }

    private double twoDec(double val)
    {
        val *= 100;
        val = Math.round(val);
        val /= 100;
        return val;
    }
}
