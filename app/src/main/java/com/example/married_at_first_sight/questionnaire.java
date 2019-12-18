package com.example.married_at_first_sight;

/*
This class is a questionnaire.
 */
public class questionnaire
{
    public String question; //The question.
    public String ans1; //Answer number 1.
    public String ans2; //Answer number 2.
    public String ans3; //Answer number 3.

    /*
    A constructor.
     */
    public questionnaire(String _question, String _ans1, String _ans2, String _ans3)
    {
        question = _question;
        ans1 = _ans1;
        ans2 = _ans2;
        ans3 = _ans3;
    }

    //Getters and setters:


    public String getQuestion()
    {
        return question;
    }

    public void setQuestion(String question)
    {
        this.question = question;
    }

    public String getAns1()
    {
        return ans1;
    }

    public void setAns1(String ans1)
    {
        this.ans1 = ans1;
    }

    public String getAns2()
    {
        return ans2;
    }

    public void setAns2(String ans2)
    {
        this.ans2 = ans2;
    }

    public String getAns3()
    {
        return ans3;
    }

    public void setAns3(String ans3)
    {
        this.ans3 = ans3;
    }

}
