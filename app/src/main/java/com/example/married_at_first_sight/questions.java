package com.example.married_at_first_sight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class questions extends AppCompatActivity implements View.OnClickListener{
    Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        //button next
        next = (Button)findViewById(R.id.Next_Button);
        next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == next){
            //movw data to profile activity
            Intent getfrommain = getIntent();

            String name =getfrommain.getStringExtra("name");
            String email =getfrommain.getStringExtra("email");
            String birthday =getfrommain.getStringExtra("birthday");
            String id =getfrommain.getStringExtra("id");

            Intent intentProfile = new Intent(this, Profile.class);

            intentProfile.putExtra("name",name);
            intentProfile.putExtra("email",email);
            intentProfile.putExtra("birthday",birthday);
            intentProfile.putExtra("id",id);

            startActivity(intentProfile);


            }
        }
}
