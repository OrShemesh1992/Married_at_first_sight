package com.example.married_at_first_sight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class statisticsOrEditQ extends AppCompatActivity {
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_or_edit_q);
    }

    public void statistic(View view) {
       intent = new Intent(getApplicationContext(), statistics.class);
        startActivity(intent);
    }

    public void Edit_Q(View view) {
        intent = new Intent(getApplicationContext(), managerQuizEditor.class);
        startActivity(intent);
    }
}
