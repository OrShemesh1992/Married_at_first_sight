package com.example.married_at_first_sight;

import java.util.HashMap;

public class statistic
{
    public String question;
    public HashMap<String, Double> answers;

    public statistic (String que)
    {
        question = que;
        answers = new HashMap<String, Double>();
    }

}
