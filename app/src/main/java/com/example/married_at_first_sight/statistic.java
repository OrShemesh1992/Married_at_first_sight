package com.example.married_at_first_sight;
import java.util.HashMap;

/*
This class is for the statistic of the users.
For the manager only.
 */
public class statistic
{
    public String question;
    public HashMap<String, Float> answers;

    /*
    A constructor.
     */
    public statistic (String que)
    {
        question = que;
        answers = new HashMap<String, Float>();
    }

    public String getQuestion()
    {
        return question;
    }

}
