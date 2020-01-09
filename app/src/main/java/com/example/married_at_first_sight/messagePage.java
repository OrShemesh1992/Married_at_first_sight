package com.example.married_at_first_sight;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.facebook.Profile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Timer;
import java.util.TimerTask;

public class messagePage extends AppCompatActivity
{
    String Matchid;
    EditText messageET;
    TextView conversationTV;
    DatabaseReference database;
    final Profile profile = Profile.getCurrentProfile();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        //get match id from another activity
        Bundle extras = getIntent().getExtras();
        Matchid = extras.getString("sendID");
        conversationTV = (TextView)findViewById(R.id.conversation);
        conversationTV.setMovementMethod(new ScrollingMovementMethod());
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                conversation();
            }
        }, 0, 1000);
    }

    /*
     get from fire base conversation
    */
    public void conversation()
    {
        database = FirebaseDatabase.getInstance().getReference();
        database.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    String messege = "";
                    for (DataSnapshot mess : dataSnapshot.child("messagePage").child(profile.getId()).child(Matchid).getChildren()) {
                        if (mess.child(profile.getId()) != null) {
                            messege += mess.getValue(String.class) + "\n";
                            conversationTV.setText(messege);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
            }
        });
    }

    /*
     send to fire base messagePage
    */
    public void send(View view)
    {
        messageET = (EditText)findViewById(R.id.message);
        if(!messageET.getText().toString().isEmpty())
        {
            database = FirebaseDatabase.getInstance().getReference().child("messagePage").child(profile.getId()).child(Matchid);
            database.push().setValue("you: " + messageET.getText().toString().trim());
            database = FirebaseDatabase.getInstance().getReference().child("messagePage").child(Matchid).child(profile.getId());
            database.push().setValue("Your match: " + messageET.getText().toString().trim());
            messageET.getText().clear();
            conversation();
        }
    }

    public void clear(View view)
    {

        database = FirebaseDatabase.getInstance().getReference();
        database.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    for (DataSnapshot mess : dataSnapshot.child("messagePage").child(profile.getId()).child(Matchid).getChildren())
                    {
                        if (mess.child(profile.getId()) != null)
                        {
                            mess.getRef().removeValue();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
            }
        });
        conversationTV.setText(null);
    }
}
