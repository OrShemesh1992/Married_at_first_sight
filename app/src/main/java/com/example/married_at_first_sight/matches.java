package com.example.married_at_first_sight;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/*
This class is for the matches page.
 */
public class matches extends AppCompatActivity
{
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);
    }

    public void MyProfile(View view)
    {
        intent = new Intent(getApplicationContext(), userProfile.class);
        startActivity(intent);
    }
}
