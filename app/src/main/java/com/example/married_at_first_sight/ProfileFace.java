package com.example.married_at_first_sight;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFace extends AppCompatActivity {
    TextView name_;
    TextView birthday_;
    ProfilePictureView profilePictureView;
    DatabaseReference mDatabase;
    final Profile profile = Profile.getCurrentProfile();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_face);
        name_ = (TextView) findViewById(R.id.DataFacebook_name);
        birthday_ = (TextView) findViewById(R.id.DataFacebook_birthday);
        profilePictureView = (ProfilePictureView) findViewById(R.id.imagefacebook);
        profilePictureView.setProfileId(profile.getId());
        fb_details();
    }

    //get details from fire base about the user
    public void fb_details(){
        //    DatabaseReference reference;
        mDatabase= FirebaseDatabase.getInstance().getReference();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s=profile.getId();
                Log.v(s,"check1");
                if (dataSnapshot.getValue() != null)
                {
                    FaceData face = dataSnapshot.child("faceData").child(s).getValue(FaceData.class);
                    name_.setText(face.name);
                    birthday_.setText(face.birthday);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
