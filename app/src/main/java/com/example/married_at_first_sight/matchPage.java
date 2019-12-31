package com.example.married_at_first_sight;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.facebook.login.widget.ProfilePictureView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/*
This class is for the match page.
 */
public class matchPage extends AppCompatActivity
{
    Intent intent;
    ProfilePictureView matchImage;
    DatabaseReference database; //Database for facebook data.
    ArrayList<String> ArrUserId=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        getMatch();
    }

    /*
      This function is for the profile button.
     */

    public void myProfile(View view)
    {
        intent = new Intent(getApplicationContext(), userProfilePage.class);
        startActivity(intent);
    }

    /*
    This function get all the key to ArryList from the firebase for the picture
    and once click set diffrent id
     */

    public void getMatch(){
        database = FirebaseDatabase.getInstance().getReference();
        database.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for (DataSnapshot face: dataSnapshot.child("faceData").getChildren()){
                    String id=face.getKey();
                    Log.v("s",id+"Stam");
                    ArrUserId.add(id);
                }
                // set new id on click to picture
                matchImage = (ProfilePictureView) findViewById(R.id.matchImage);
                matchImage.setClickable(true);
                matchImage.setOnClickListener(new View.OnClickListener() {
                    Iterator<String> It=ArrUserId.iterator();
                    @Override
                    public void onClick(View v) {
                        if(It.hasNext()) {
                            matchImage.setProfileId(It.next());
                        }
                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError)
            {
            }
        });
    }
}
