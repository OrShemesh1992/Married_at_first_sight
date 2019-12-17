package com.example.married_at_first_sight;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.facebook.Profile;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfile extends AppCompatActivity {
    EditText EditAge;
    DatabaseReference mDatabase;
    final Profile profile = Profile.getCurrentProfile();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
    }
//Send Age to firebase
    public void SendEdit(View view) {
        EditAge = (EditText) findViewById(R.id.age);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("faceData").child(profile.getId()).child("birthday").setValue( EditAge.getText().toString().trim());
    }
}