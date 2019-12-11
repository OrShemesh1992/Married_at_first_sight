package com.example.married_at_first_sight;

public class FaceData {
    public String id;
    public String name;
    public String email;
    public String birthday;
    public FaceData(){
        id="";
        name="";
        email="";
        birthday="";
    }
    public FaceData(String _id, String _name,String _email ,String _birthday){
    this.birthday=_birthday;
    this.id=_id;
    this.email=_email;
    this.name=_name;
    }

}
