package com.example.androidproject;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class Popup extends Activity {

    private TextView result;
    private String receivedResult = "";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupwindow);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8),(int)(height*0.8));

        result = (TextView)findViewById(R.id.optimalroute_result);

        Intent intent = getIntent();
        receivedResult = intent.getStringExtra(MainActivity.TAG);
        result.setText(receivedResult);

    }
}
