package com.example.married_at_first_sight;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/*
This class is for the statistics page.
 */
public class statisticsPage extends AppCompatActivity {
    HashMap<String, Integer> allAnswer = new HashMap<>();
    DatabaseReference database;
    String question;
    String answer;
    ArrayList<questionnaire> questArr = new ArrayList<>();//List of questions and options of answers.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        getStatistic();
    }

    public void getStatistic() {
        database = FirebaseDatabase.getInstance().getReference();
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.child("Questionnaire").getChildren()) {
                    questionnaire quest = new questionnaire
                            (child.getKey(),
                                    child.child("ans1").getValue(String.class),
                                    child.child("ans2").getValue(String.class),
                                    child.child("ans3").getValue(String.class));
                    questArr.add(quest);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void clear(View view) {
        database = FirebaseDatabase.getInstance().getReference();
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (int i = 0; i < questArr.size(); i++) {
                    database.child("Statistic").child(questArr.get(i).getQuestion()).child(questArr.get(i).getAns1()).setValue("0");
                    database.child("Statistic").child(questArr.get(i).getQuestion()).child(questArr.get(i).getAns2()).setValue("0");
                    database.child("Statistic").child(questArr.get(i).getQuestion()).child(questArr.get(i).getAns3()).setValue("0");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void results(View view) {
        database = FirebaseDatabase.getInstance().getReference();
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int counter = 0;
                String countString;
                for (DataSnapshot child : dataSnapshot.child("Answers").getChildren()) {
                    Log.v("checkId", child.getKey());
                    for (int i = 0; i < questArr.size(); i++) {
                        question = questArr.get(i).getQuestion();
                        answer = (String) child.child(question).getValue();
                        if (!allAnswer.containsKey(answer)) {
                            counter = Integer.parseInt(dataSnapshot.child("Statistic").child(question).child(answer).getValue(String.class));
                            counter++;
                            allAnswer.put(answer, counter);
                        } else {
                            counter = allAnswer.get(answer);
                            counter++;
                        }
                        database.child("Statistic").child(question).child(answer).setValue(String.valueOf(counter));
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
}
