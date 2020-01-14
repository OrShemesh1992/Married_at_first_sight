package com.example.married_at_first_sight;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.widget.Toast;

/*
This class is for the manager quiz editor page.
He can add or edit a question to fire base for the user quiz.
 */
public class managerQuizEditorPage extends AppCompatActivity
{
    EditText questionET; //Question to add or to edit.
    EditText answer1ET; //Answer 1 to add.
    EditText answer2ET; //Answer 2 to add.
    EditText answer3ET; //Answer 3 to add.
    DatabaseReference database; //Database for the quiz.

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_quiz_editor);
    }

    /*
    This function adds a new question to database.
    It can also change an existing question.
     */
    public void addQuestionToFB(View view)
    {
        //Connect to buttons.
        questionET = (EditText)findViewById(R.id.question);
        answer1ET = (EditText)findViewById(R.id.answer1);
        answer2ET = (EditText)findViewById(R.id.answer2);
        answer3ET = (EditText)findViewById(R.id.answer3);

        if (questionET.getText().toString().isEmpty())
        {
            Toast.makeText(managerQuizEditorPage.this, "Question is empty", Toast.LENGTH_SHORT).show();
        }

        else if (answer1ET.getText().toString().isEmpty() || answer2ET.getText().toString().isEmpty() || answer3ET.getText().toString().isEmpty())
        {
            Toast.makeText(managerQuizEditorPage.this, "Answers are empty", Toast.LENGTH_SHORT).show();
        }

        else
        {
            //Database connection;
            database = FirebaseDatabase.getInstance().getReference();

            //Send the new question and it's answers to firebase.
            database.child("Questionnaire").child(questionET.getText().toString()).child("ans1").setValue(answer1ET.getText().toString());
            database.child("Questionnaire").child(questionET.getText().toString()).child("ans2").setValue(answer2ET.getText().toString());
            database.child("Questionnaire").child(questionET.getText().toString()).child("ans3").setValue(answer3ET.getText().toString());

            Toast.makeText(managerQuizEditorPage.this, "Add successfully", Toast.LENGTH_SHORT).show();
        }

        //Clears all the buttons.
        questionET.setText("");
        answer1ET.setText("");
        answer2ET.setText("");
        answer3ET.setText("");
    }
}