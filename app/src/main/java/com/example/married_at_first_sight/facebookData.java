package com.example.married_at_first_sight;

/*
This class is a facebook person data.
 */
public class facebookData
{
    private String id; //Unique person facebook id.
    private String name; //Person facebook name.
    private String age; //Person age.
    private String email; //Person email.

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

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAge()
    {
        return age;
    }

    public void setAge(String age)
    {
        this.age = age;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
}
