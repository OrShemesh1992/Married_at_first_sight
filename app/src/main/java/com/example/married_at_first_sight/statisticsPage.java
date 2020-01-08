package com.example.married_at_first_sight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    HashMap<String, Integer> allAnswer = new HashMap<>(); //An HashMap for the statistic of all the answer.
    DatabaseReference database; //The DataBase.
    String question; //The question.
    String answer; //The answer.
    ArrayList<questionnaire> questArr = new ArrayList<>();//List of questions and options of answers.
    ArrayList<statistic> allStatistics = new ArrayList<statistic>();
    Intent intent;
    static int countPeople = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        clear();
        //getStatistic();
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

    /*
    The function make a list on the fireBase of all the questions and all the answers.
     */

    public void clear() {
        database = FirebaseDatabase.getInstance().getReference();
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                questArr.add(new questionnaire("Age", "25 - 30", "30 - 35", "35 - 40"));
                for (DataSnapshot child : dataSnapshot.child("Questionnaire").getChildren()) {
                    questionnaire quest = new questionnaire
                            (child.getKey(),
                                    child.child("ans1").getValue(String.class),
                                    child.child("ans2").getValue(String.class),
                                    child.child("ans3").getValue(String.class));
                    questArr.add(quest);
                }

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

    /*
    The function count how many people answers which question ans fills out the list on fireBase.
     */

    public void results(View view) {
        database = FirebaseDatabase.getInstance().getReference();
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int counter = 0;
                String countString;
                for (DataSnapshot child : dataSnapshot.child("Answers").getChildren()) {

                    countPeople++;

                    for (int i = 0; i < questArr.size(); i++) {
                        question = questArr.get(i).getQuestion();
                        answer = (String) child.child(question).getValue();
                        if (!allAnswer.containsKey(answer)) {
                            counter = 0;
                            counter++;
                            allAnswer.put(answer, counter);
                        } else {
                            counter = allAnswer.get(answer);
                            counter++;
                            allAnswer.replace(answer, counter);
                        }
                        database.child("Statistic").child(question).child(answer).setValue(String.valueOf(counter));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

//        showStat();
    }

    public void show(View view) {
        DatabaseReference database1 = FirebaseDatabase.getInstance().getReference();
        database1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int i = 0;
                int countAnswer = 0;
                double statistic = 0;

                if (countPeople != 0) {
                    for (DataSnapshot child : dataSnapshot.child("Statistic").getChildren()) {

                        question = child.getKey();
                        statistic stat = new statistic(question);

                        answer = questArr.get(i).getAns1();
                        countAnswer = Integer.parseInt((String) child.child(answer).getValue());
                        statistic = (double) (countAnswer * 100) / (double) countPeople;
                        stat.answers.put(answer, statistic);

                        answer = questArr.get(i).getAns2();
                        countAnswer = Integer.parseInt((String) child.child(answer).getValue());
                        statistic = (double) (countAnswer * 100) / (double) countPeople;
                        stat.answers.put(answer, statistic);

                        answer = questArr.get(i).getAns3();
                        countAnswer = Integer.parseInt((String) child.child(answer).getValue());
                        statistic = (double) (countAnswer * 100) / (double) countPeople;
                        stat.answers.put(answer, statistic);

                        allStatistics.add(stat);
                        i++;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void back(View view) {
        intent = new Intent(getApplicationContext(), managerOptionsPage.class);
        startActivity(intent);
    }

//    public void home(View view) {
//        intent = new Intent(getApplicationContext(), mainPage.class);
//        startActivity(intent);
//    }
}
