package com.example.married_at_first_sight;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
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
    int counter = 0; //Counts the questions;
    int show = 0;
    Button nextStat;
    Button showStat;
    Button computeStat;

    private float[] yData = {25.3f, 10.6f, 64.10f};
    private String[] xData = {"Maayan", "Nahama", "Or"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        clear();
        nextStat = (Button)findViewById(R.id.next);
        showStat = (Button)findViewById(R.id.show);
        computeStat = (Button)findViewById(R.id.results);
//        showPieChart();
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
        showStat.setVisibility(View.VISIBLE);
        computeStat.setVisibility(View.GONE);

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
        show = 1;
        nextStat.setVisibility(View.VISIBLE);
        showStat.setVisibility(View.GONE);
    }

    public void back(View view)
    {
        intent = new Intent(getApplicationContext(), managerOptionsPage.class);
        startActivity(intent);
    }

    public void home(View view)
    {
        intent = new Intent(getApplicationContext(), mainPage.class);
        startActivity(intent);
    }

    public void nextState(View view)
    {
        if(show == 1)
        {
            statistic stat;
            if (counter < questArr.size())
            {
                stat = allStatistics.get(counter);
                question = stat.getQuestion();

                //Add first answer to the array.
                answer = questArr.get(counter).getAns1();
                yData[0] = stat.answers.get(answer);
                xData[0] = answer;

                //Add second answer to the array.
                answer = questArr.get(counter).getAns2();
                yData[1] = stat.answers.get(answer);
                xData[1] = answer;

                answer = questArr.get(counter).getAns3();
                yData[2] = stat.answers.get(answer);
                xData[2] = answer;

                counter++;
            }
            else
            {
                counter = 0;
                nextState(view);
            }
            showPieChart();
        }
        Drawable d = getResources().getDrawable(R.drawable.next2);
        nextStat.setBackground(d);
    }

    public void showPieChart()
    {
        PieChart pieChart = (PieChart) findViewById(R.id.chart);

        //pieChart data set.
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText(question);
        pieChart.setCenterTextSize(20);
        pieChart.setScrollBarSize(20);

        addDataSet(pieChart);

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener()
        {
            @Override
            public void onValueSelected(Entry e, Highlight h)
            {
                int pos1 = e.toString().indexOf("y: ");
                String stats = e.toString().substring(pos1 + 3);

                for (int i = 0; i < yData.length; i++)
                {
                    if (yData[i] == Float.parseFloat(stats))
                    {
                        pos1 = i;
                        break;
                    }
                }
                String ans = xData[pos1];
                Toast.makeText(statisticsPage.this, "Answer: " + ans + "\n" + "Statistics: " + stats + " %", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected()
            {
            }
        });
    }

    private void addDataSet(PieChart chart)
    {
        ArrayList<PieEntry> yEntry = new ArrayList<>();

        List<PieEntry> pieEntries = new ArrayList<>();

        for (int i = 0; i < yData.length; i++)
        {
            pieEntries.add(new PieEntry(yData[i], xData[i]));
        }

        for (int i = 0; i < yData.length; i++)
        {
            yEntry.add(new PieEntry(yData[i], i));
        }

        //Creates data set.
        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
        pieDataSet.setValueTextSize(12);


        //Adds colors to data set.
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.CYAN);
        pieDataSet.setColors(colors);

        //Creates pie data object.
        PieData pieData = new PieData(pieDataSet);
        chart.setData(pieData);
        chart.animateY(1000);
        chart.invalidate();
    }
}
