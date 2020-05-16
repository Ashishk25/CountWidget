package com.example.counterwidgetlib;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.countwidget.CountWidget;

public class MainActivity extends AppCompatActivity {
private CountWidget widget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        widget=(CountWidget) findViewById(R.id.counter);

        widget.setOnClickListener(new CountWidget.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"hello",Toast.LENGTH_SHORT);
            }
        });
    }
}
