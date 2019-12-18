package com.example.married_at_first_sight;

/*
This class is a facebook person data.
 */
public class facebookData
{
    public String id; //Unique person facebook id.
    public String name; //Person facebook name.
    public String age; //Person age.
    public String email; //Person email.

    /*
    An empty constructor.
     */
    public facebookData()
    {
        id = "";
        name = "";
        age = "";
        email = "";
    }

    /*
    A constructor.
     */
    public facebookData(String _id, String _name, String _age, String _email)
    {
    this.id = _id;
    this.name = _name;
    this.age = _age;
    this.email = _email;
    }
}
