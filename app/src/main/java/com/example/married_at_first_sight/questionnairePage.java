package com.example.married_at_first_sight;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import com.facebook.Profile;
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
    ArrayList<String> answers = new ArrayList<>();
    Button nextButton;
    Button nextAns1Button;
    Button nextAns2Button;
    Button nextAns3Button;
    TextView question;
    ArrayList<questionnaire> questArr = new ArrayList<>();//List of questions and options of answers.
    DatabaseReference database; //Database for facebook data.
    final Profile profile = Profile.getCurrentProfile();
    /*
    This function reads the Questionnaire from firebase,
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
                            child.child("ans1").getValue(String.class),
                            child.child("ans2").getValue(String.class),
                            child.child("ans3").getValue(String.class));
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
        readQuestionnaireFromFirebase(); //Reads the questionnaire from firebase.
    }

    @Override
    public void onClick(View v)
    {
        question = (TextView) findViewById(R.id.questions);
        database = FirebaseDatabase.getInstance().getReference();

        if (v == nextAns1Button)
        {
            database = FirebaseDatabase.getInstance().getReference().child("Answers").child(profile.getId()).child((String) question.getText());
            database.setValue((String) nextAns1Button.getText());
            answers.add(nextAns1Button.getText().toString());
            Log.v("check1", (String) question.getText());
            Log.v("check1", (String) nextAns1Button.getText());
        }
        else if (v == nextAns2Button)
        {
            database = FirebaseDatabase.getInstance().getReference().child("Answers").child(profile.getId()).child((String) question.getText());
            database.setValue((String) nextAns2Button.getText());
            answers.add(nextAns2Button.getText().toString());

            Log.v("check2", (String) question.getText());
            Log.v("check2", (String) nextAns2Button.getText());
        }
        else if (v == nextAns3Button)
        {
            database = FirebaseDatabase.getInstance().getReference().child("Answers").child(profile.getId()).child((String) question.getText());
            database.setValue((String) nextAns3Button.getText());
            answers.add(nextAns3Button.getText().toString());

            Log.v("check3", (String) question.getText());
            Log.v("check3", (String) nextAns3Button.getText());
        }

        //Moves to profile activity.
        if(v == nextButton && i > questArr.size())
        {
            //intent to Profile
            Intent intentProfile = new Intent(this, matchPage.class);
            startActivity(intentProfile);
        }

        //Goes to the next question in the list.
        if((v == nextAns1Button || v == nextAns2Button || v == nextAns3Button))
        {
            if (i < questArr.size())
            {
                question.setText(questArr.get(i).getQuestion());
                nextAns1Button.setText(questArr.get(i).getAns1());
                nextAns2Button.setText(questArr.get(i).getAns2());
                nextAns3Button.setText(questArr.get(i).getAns3());
            }
            i++;
            //If the end of questions.
            if (i == questArr.size() + 1)
            {
                nextButton.setBackgroundColor(Color.parseColor("#F82727"));
            }
        }
    }
}
