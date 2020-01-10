package com.example.married_at_first_sight;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
This class is for the statistics page.
 */
public class statisticsPage extends AppCompatActivity
{
    HashMap<String, Integer> allAnswer = new HashMap<>(); //An HashMap for the statistic of all the answer.
    DatabaseReference database; //The DataBase.
    String question; //The question.
    String answer; //The answer.
    ArrayList<questionnaire> questArr = new ArrayList<>();//List of questions and options of answers.
    ArrayList<statistic> allStatistics = new ArrayList<statistic>();
    Intent intent;
    static int countPeople = 0;

    private float[] yData = {25.3f, 10.6f, 64.10f};
    private String[] xData = {"Maayan", "Nahama", "Or"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        clear();
        showPieChart();
    }

    /*
    The function makes a list on the fireBase of all the questions and all the answers.
     */
    public void clear()
    {
        database = FirebaseDatabase.getInstance().getReference();
        database.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                questArr.add(new questionnaire("Age", "25 - 30", "30 - 35", "35 - 40"));
                for (DataSnapshot child : dataSnapshot.child("Questionnaire").getChildren())
                {
                    questionnaire quest = new questionnaire
                            (child.getKey(),
                             child.child("ans1").getValue(String.class),
                             child.child("ans2").getValue(String.class),
                             child.child("ans3").getValue(String.class));
                    questArr.add(quest);
                }
                for (int i = 0; i < questArr.size(); i++)
                {
                    database.child("Statistic").child(questArr.get(i).getQuestion()).child(questArr.get(i).getAns1()).setValue("0");
                    database.child("Statistic").child(questArr.get(i).getQuestion()).child(questArr.get(i).getAns2()).setValue("0");
                    database.child("Statistic").child(questArr.get(i).getQuestion()).child(questArr.get(i).getAns3()).setValue("0");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
            }
        });
    }

    /*
    The function counts how many people answers which question ans fills out the list on fireBase.
     */
    public void results(View view)
    {
        database = FirebaseDatabase.getInstance().getReference();
        database.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                int counter = 0;
                String countString;
                for (DataSnapshot child : dataSnapshot.child("Answers").getChildren())
                {
                    countPeople++;
                    for (int i = 0; i < questArr.size(); i++)
                    {
                        question = questArr.get(i).getQuestion();
                        answer = (String) child.child(question).getValue();
                        if(!allAnswer.containsKey(answer))
                        {
                            counter = 0;
                            counter++;
                            allAnswer.put(answer, counter);
                        }
                        else
                        {
                            counter = allAnswer.get(answer);
                            counter++;
                            allAnswer.replace(answer, counter);
                        }
                        database.child("Statistic").child(question).child(answer).setValue(String.valueOf(counter));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
            }
        });

    }

    /*
    This function calculates the statics depending on the data in the fireBase.
     */
    public void show(View view)
    {
        DatabaseReference database1 = FirebaseDatabase.getInstance().getReference();
        database1.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                int i = 0;
                int countAnswer = 0;
                float statistic = 0;

                if (countPeople != 0)
                {
                    for (DataSnapshot child : dataSnapshot.child("Statistic").getChildren())
                    {
                        question = child.getKey();
                        statistic stat = new statistic(question);

                        answer = questArr.get(i).getAns1();
                        countAnswer = Integer.parseInt((String) child.child(answer).getValue());
                        statistic = (float) (countAnswer * 100) / (float) countPeople;
                        stat.answers.put(answer, statistic);

                        answer = questArr.get(i).getAns2();
                        countAnswer = Integer.parseInt((String) child.child(answer).getValue());
                        statistic = (float) (countAnswer * 100) / (float) countPeople;
                        stat.answers.put(answer, statistic);

                        answer = questArr.get(i).getAns3();
                        countAnswer = Integer.parseInt((String) child.child(answer).getValue());
                        statistic = (float) (countAnswer * 100) / (float) countPeople;
                        stat.answers.put(answer, statistic);

                        allStatistics.add(stat);
                        i++;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
            }
        });
    }

    public void back(View view)
    {
        intent = new Intent(getApplicationContext(), managerOptionsPage.class);
        startActivity(intent);
    }

    public void showPieChart()
    {
//        pieChart = (PieChart) findViewById(R.id.idPieChart);
//        pieChart.setDescription("Result of Statistics");

        List<PieEntry> pieEntries = new ArrayList<>();

        for (int i = 0; i < yData.length; i++)
        {
            pieEntries.add(new PieEntry(yData[i], xData[i]));
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, "Result for Statistics");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData data = new PieData(dataSet);

        PieChart chart = (PieChart) findViewById(R.id.chart);
        chart.setData(data);
        chart.setRotationEnabled(true);
        chart.setHoleRadius(25f);
        chart.setTransparentCircleAlpha(0);
        chart.setCenterText("Age");
        chart.setCenterTextSize(20);
        chart.setScrollBarSize(20);


        chart.animateY(1000);
        chart.invalidate();

    }

}
