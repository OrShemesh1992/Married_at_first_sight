package com.example.married_at_first_sight;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class managerQuizEditor extends AppCompatActivity
{
    EditText questionET; //Question to add or to edit.
    EditText answer1ET; //Answer 1 to add.
    EditText answer2ET; //Answer 2 to add.
    EditText answer3ET; //Answer 3 to add.
    EditText answer4ET; //Answer 4 to add.
    DatabaseReference database; //Database for the quiz.

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
    }

    public void Send(View view)
    {
        //Connect to buttons.
        questionET = (EditText)findViewById(R.id.questionsend);
        answer1ET = (EditText)findViewById(R.id.answer1);
        answer2ET = (EditText)findViewById(R.id.answer2);
        answer3ET = (EditText)findViewById(R.id.answer3);
        answer4ET = (EditText)findViewById(R.id.answer4);

        //Database connection;
        database = FirebaseDatabase.getInstance().getReference();

        quiz newQuiz = new quiz(questionET.getText().toString(),
                                                           answer1ET.getText().toString().trim(),
                                                           answer2ET.getText().toString().trim(),
                                                           answer3ET.getText().toString().trim(),
                                                           answer4ET.getText().toString().trim());

        //Send the new question and it's answers to firebase.
        database.child("Questions").child(questionET.getText().toString()).setValue(newQuiz);
    }
}