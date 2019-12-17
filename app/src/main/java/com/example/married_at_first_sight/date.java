package com.example.married_at_first_sight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class date extends AppCompatActivity {
    //matchhh
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
    }

    public void MyProfile(View view) {
        intent = new Intent(getApplicationContext(), ProfileFace.class);
        startActivity(intent);
    }
}
