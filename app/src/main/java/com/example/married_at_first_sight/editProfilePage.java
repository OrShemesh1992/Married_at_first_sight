package com.example.married_at_first_sight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.facebook.Profile;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/*
This class is for edit the user profile page.
 */
public class editProfilePage extends AppCompatActivity
{
    EditText editAgeED; //For edit age.
    final Profile profile = Profile.getCurrentProfile(); //To get the current profile.
    DatabaseReference database; //Datebase for edit profile.
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
    }

    /*
    This function updates age in firebase.
     */
    public void SendEdit(View view)
    {
        editAgeED = (EditText)findViewById(R.id.age);
        database = FirebaseDatabase.getInstance().getReference();
        database.child("faceData").child(profile.getId()).child("age").setValue(editAgeED.getText().toString().trim());
        intent = new Intent(getApplicationContext(), userProfilePage.class);
        startActivity(intent);
    }
}