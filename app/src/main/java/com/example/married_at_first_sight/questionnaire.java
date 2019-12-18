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
    public String ans4; //Answer number 4.

    /*
    A constructor.
     */
    public questionnaire(String _question, String _ans1, String _ans2, String _ans3, String _ans4)
    {
        question = _question;
        ans1 = _ans1;
        ans2 = _ans2;
        ans3 = _ans3;
        ans4 = _ans4;
    }
}
