package com.example.madnewelearning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Calculation extends AppCompatActivity {

    EditText e1,e2,e3,e4;

    Button b1,b2;
    String subjects,outofsubjects,numofmod,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation);

        e1=findViewById(R.id.Rmark);
        e2=findViewById(R.id.Routofmark);
        e3=findViewById(R.id.Rname);
        e4=findViewById(R.id.Rnumofmodules);

        b1=findViewById(R.id.Rcalculate);
        b2=findViewById(R.id.Rcalculateback);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                subjects = e1.getText().toString();
                outofsubjects =e2.getText().toString();
                name =e3.getText().toString();
                numofmod =e4.getText().toString();


            }
        });
    }
    public static float calcResult(float castedoutofmark,float castedmark)
    {
        return ((castedmark / castedoutofmark) * 100);
    }
    public static float calcAvgmark(float castedmark,float numofmodule)
    {
        return (castedmark / numofmodule);
    }

    private void clearControls()
    {
        e1.setText("");
        e2.setText("");
        e3.setText("");
        e4.setText("");

    }
}