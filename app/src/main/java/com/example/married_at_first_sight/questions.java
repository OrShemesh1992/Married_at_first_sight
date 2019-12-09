package com.example.married_at_first_sight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class questions extends AppCompatActivity implements View.OnClickListener
{
//https://firebase.google.com/docs/database/android/read-and-write
    int i = 0; //index.
    Button next;
    Button nextAns1;
    Button nextAns2;
    Button nextAns3;
    ArrayList<String> answer1 = new ArrayList<>(); //List of answers.
    ArrayList<String> answer2 = new ArrayList<>(); //List of answers.
    ArrayList<String> answer3 = new ArrayList<>(); //List of answers.
    ArrayList<String> questions = new ArrayList<>(); //List of questions.
//data refrence
DatabaseReference mDatabase;
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
        setContentView(R.layout.activity_questions);
        next = (Button)findViewById(R.id.Finish);
        next.setOnClickListener(this);
        nextAns1 = (Button)findViewById(R.id.ans1);
        nextAns1.setOnClickListener(this);
        nextAns2 = (Button)findViewById(R.id.ans2);
        nextAns2.setOnClickListener(this);
        nextAns3 = (Button)findViewById(R.id.ans3);
        nextAns3.setOnClickListener(this);

        //fire base
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("Questions").child("1").child("name").setValue("nahama");

    }

    @Override
    public void onClick(View v)
    {
        //Moves to profile activity.
        if(v == next && i > 3)
        {
            Intent getFromMain = getIntent();

            String name = getFromMain.getStringExtra("name");
            String email = getFromMain.getStringExtra("email");
            String birthday = getFromMain.getStringExtra("birthday");
            String id = getFromMain.getStringExtra("id");

            Intent intentProfile = new Intent(this, Profile.class);

            intentProfile.putExtra("name", name);
            intentProfile.putExtra("email", email);
            intentProfile.putExtra("birthday", birthday);
            intentProfile.putExtra("id", id);

            startActivity(intentProfile);
        }

        if((v == nextAns1 || v == nextAns2 || v == nextAns3))
        {
            if(i < 3)
            {
                readFromFirebase();
                TextView quiz = (TextView) findViewById(R.id.questions);
                quiz.setText(questions.get(i));
                nextAns1.setText(answer1.get(i));
                nextAns2.setText(answer2.get(i));
                nextAns3.setText(answer3.get(i));
            }
            i++;
            if(i == 4)
            {
                next.setBackgroundColor(Color.parseColor("#F82727"));
            }
        }
    }
}
