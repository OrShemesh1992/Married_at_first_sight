package com.example.married_at_first_sight;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/*
This class is for the user profile page.
It gets details about the user from facebook,
and adds them to firebase.
 */
public class userProfilePage extends AppCompatActivity
{
    TextView nameTV; //Person name.
    TextView ageTV; //Person age.
    TextView emailTV; //Person email.

    ProfilePictureView profilePicture; //Person profile picture.
    DatabaseReference database; //Database for facebook data.
    Intent intent;
    final Profile profile = Profile.getCurrentProfile(); //To get the current facebook profile.

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        nameTV = (TextView)findViewById(R.id.facebookName);
        ageTV =(TextView)findViewById(R.id.facebookAge);
        emailTV =(TextView)findViewById(R.id.facebookEmail);
        profilePicture = (ProfilePictureView)findViewById(R.id.facebookImage);
        profilePicture.setProfileId(profile.getId()); //Picture facebook profile id.
        fb_details();
    }

    /*
    This function gets details from firebase about the user.
     */
    public void fb_details()
    {
        //DatabaseReference reference;
        database = FirebaseDatabase.getInstance().getReference();
        database.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                String profileId = profile.getId();
                Log.v(profileId,"check1");
                if(dataSnapshot.getValue() != null)
                {
                    facebookData fd = dataSnapshot.child("faceData").child(profileId).getValue(facebookData.class);
                    nameTV.setText(fd.getName());
                    ageTV.setText(fd.getAge());
                    emailTV.setText(fd.getEmail());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError)
            {
            }
        });
    }

    /*
    This function is for edit button.
     */
    public void editProfile(View view)
    {
        intent = new Intent(getApplicationContext(), editProfilePage.class);
        startActivity(intent);
    }

    /*
    Go to match page
     */
    public void back(View view) {
        intent = new Intent(getApplicationContext(), matchPage.class);
        startActivity(intent);
    }

    /*
    Go to home page
     */
    public void Home(View view) {
        Intent i = new Intent(this, mainPage.class);
        startActivity(i);
    }
}