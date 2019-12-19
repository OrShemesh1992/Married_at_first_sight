package com.example.married_at_first_sight;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/*
This class is for the options of the manager page.
 */
public class managerOptionsPage extends AppCompatActivity
{
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_options);
    }

    /*
    This function is for the statistic button.
     */
    public void statistic(View view)
    {
       intent = new Intent(getApplicationContext(), statisticsPage.class);
       startActivity(intent);
    }

    /*
    This function is for the statistic button.
     */
    public void editQuestionnaire(View view)
    {
        intent = new Intent(getApplicationContext(), managerQuizEditorPage.class);
        startActivity(intent);
    }
}
