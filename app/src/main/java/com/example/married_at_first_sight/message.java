package com.example.married_at_first_sight;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.facebook.Profile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class message extends AppCompatActivity {
    String Matchid;
    EditText Message;
    DatabaseReference database;
    TextView Showconversation;
    final Profile profile = Profile.getCurrentProfile();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        //get match id from another activity
        Bundle extras = getIntent().getExtras();
        Matchid = extras.getString("sendID");
        Showconversation = (TextView) findViewById(R.id.get_nessage);
        conversation();
    }

    /*
     get from fire base conversation
    */
    public void conversation() {
        database = FirebaseDatabase.getInstance().getReference();
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String messege = "";
                for (DataSnapshot mess : dataSnapshot.child("message").child(profile.getId()).child(Matchid).getChildren()) {
                    if (mess.child(profile.getId()) != null) {
                        messege += "you: " + mess.getValue(String.class) + "\n";
                        Showconversation.setText(messege);
                    }
                }
                for (DataSnapshot mess : dataSnapshot.child("message").child(Matchid).child(profile.getId()).getChildren()) {
                    messege += "Your match: " + mess.getValue(String.class) + "\n";
                    Showconversation.setText(messege);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
    /*
     send to fire base message
    */
    public void Send(View view) {
        Message = (EditText) findViewById(R.id.message_send);
        database = FirebaseDatabase.getInstance().getReference().child("message").child(profile.getId()).child(Matchid);
        database.push().setValue(Message.getText().toString().trim());
        Message.getText().clear();
        conversation();
    }
}
