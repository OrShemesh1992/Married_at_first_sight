package com.example.married_at_first_sight;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

/*
This class is for the questionnaire page.
 */
public class questionnairePage extends AppCompatActivity implements View.OnClickListener
{
    int i = 0; //index.
    Button nextButton;
    Button nextAns1Button;
    Button nextAns2Button;
    Button nextAns3Button;
    ArrayList<String> answer1 = new ArrayList<>(); //List of answers.
    ArrayList<String> answer2 = new ArrayList<>(); //List of answers.
    ArrayList<String> answer3 = new ArrayList<>(); //List of answers.
    ArrayList<String> questions = new ArrayList<>(); //List of questions.

    /*
    Needs to be read from fireBase!
    */
    public boolean readFromFirebase()
    {
        questions.add("Hobbies");
        questions.add("Religion");
        questions.add("Area");

        answer1.add("Dancing");
        answer2.add("Singing");
        answer3.add("Surfing");

        answer1.add("Religious");
        answer2.add("Secular");
        answer3.add("Traditional");

        answer1.add("Center");
        answer2.add("North");
        answer3.add("South");

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);
        nextButton = (Button)findViewById(R.id.Finish);
        nextButton.setOnClickListener(this);
        nextAns1Button = (Button)findViewById(R.id.ans1);
        nextAns1Button.setOnClickListener(this);
        nextAns2Button = (Button)findViewById(R.id.ans2);
        nextAns2Button.setOnClickListener(this);
        nextAns3Button = (Button)findViewById(R.id.ans3);
        nextAns3Button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        //Moves to profile activity.
        if(v == nextButton && i > 3)
        {
            Intent getFromMain = getIntent();
            //intent to Profile
            Intent intentProfile = new Intent(this, matchPage.class);
            startActivity(intentProfile);
        }

        if((v == nextAns1Button || v == nextAns2Button || v == nextAns3Button))
        {
            if(i < 3)
            {
                readFromFirebase();
                TextView quiz = (TextView) findViewById(R.id.questions);
                quiz.setText(questions.get(i));
                nextAns1Button.setText(answer1.get(i));
                nextAns2Button.setText(answer2.get(i));
                nextAns3Button.setText(answer3.get(i));
            }
            i++;
            if(i == 4)
            {
                nextButton.setBackgroundColor(Color.parseColor("#F82727"));
            }
        }
    }
}
