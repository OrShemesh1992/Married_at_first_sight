package com.example.married_at_first_sight;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/*
This class is for the questionnaire page.
 */
public class questionnairePage extends AppCompatActivity implements View.OnClickListener
{
    int i = 0; //Number of question in the questionnaire list.
    Button nextButton;
    Button nextAns1Button;
    Button nextAns2Button;
    Button nextAns3Button;
    Button nextAns4Button;
    ArrayList<questionnaire> questArr = new ArrayList<>();//List of questions and options of answers.
    DatabaseReference database; //Database for facebook data.

    /*
    This function reads the Questionnaire from fire base,
    and make a list of questionnaire.
     */
    public void readQuestionnaireFromFirebase()
    {
        //DatabaseReference reference;
        database = FirebaseDatabase.getInstance().getReference();
        database.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for (DataSnapshot child : dataSnapshot.child("Questionnaire").getChildren())
                {
                    questionnaire quest = new questionnaire
                            (child.getKey(),
                            child.child("1").getValue(String.class),
                            child.child("2").getValue(String.class),
                            child.child("3").getValue(String.class),
                            child.child("4").getValue(String.class));
                    questArr.add(quest);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError)
            {
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);
        nextButton = (Button)findViewById(R.id.finish);
        nextButton.setOnClickListener(this);
        nextAns1Button = (Button)findViewById(R.id.ans1);
        nextAns1Button.setOnClickListener(this);
        nextAns2Button = (Button)findViewById(R.id.ans2);
        nextAns2Button.setOnClickListener(this);
        nextAns3Button = (Button)findViewById(R.id.ans3);
        nextAns3Button.setOnClickListener(this);
        nextAns4Button = (Button)findViewById(R.id.ans4);
        nextAns4Button.setOnClickListener(this);
        readQuestionnaireFromFirebase(); //Reads the questionnaire from firebase.
    }

    @Override
    public void onClick(View v)
    {
        //Moves to profile activity.
        if(v == nextButton && i > questArr.size())
        {
            Intent getFromMain = getIntent();
            //intent to Profile
            Intent intentProfile = new Intent(this, matchPage.class);
            startActivity(intentProfile);
        }

        //Goes to the next question in the list.
        if((v == nextAns1Button || v == nextAns2Button || v == nextAns3Button || v == nextAns4Button))
        {
            if(i < questArr.size())
            {
                TextView question = (TextView)findViewById(R.id.questions);
                question.setText(questArr.get(i).getQuestion());
                nextAns1Button.setText(questArr.get(i).getAns1());
                nextAns2Button.setText(questArr.get(i).getAns2());
                nextAns3Button.setText(questArr.get(i).getAns3());
                nextAns4Button.setText(questArr.get(i).getAns4());
            }
            i++;
            //If the end of questions.
            if(i == questArr.size() + 1)
            {
                nextButton.setBackgroundColor(Color.parseColor("#F82727"));
            }
        }
    }
}
