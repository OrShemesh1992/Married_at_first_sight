package com.example.married_at_first_sight;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class answer extends AppCompatActivity {
// managger Editor quiz
    EditText questionsend;
    EditText answer1;
    EditText answer2;
    EditText answer3;
    EditText answer4;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);


    }


    public void Send(View view)
    {

        questionsend = (EditText) findViewById(R.id.questionsend);
        answer1 = (EditText) findViewById(R.id.answer1);
        answer2 = (EditText) findViewById(R.id.answer2);
        answer3 = (EditText) findViewById(R.id.answer3);
        answer4 = (EditText) findViewById(R.id.answer4);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        question_4answer sendToFire=new question_4answer(questionsend.getText().toString(),
                answer1.getText().toString().trim(), answer2.getText().toString().trim(),
                answer3.getText().toString().trim(), answer4.getText().toString().trim());

        mDatabase.child("Questions").child(questionsend.getText().toString()).setValue(sendToFire);

    }
}