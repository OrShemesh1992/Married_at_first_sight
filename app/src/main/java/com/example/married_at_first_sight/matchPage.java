package com.example.married_at_first_sight;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/*
This class is for the match page.
 */
public class matchPage extends AppCompatActivity
{
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
    }

    public void MyProfile(View view)
    {
        intent = new Intent(getApplicationContext(), userProfilePage.class);
        startActivity(intent);
    }
}
