package com.example.married_at_first_sight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Profile extends AppCompatActivity {
    TextView name_;
    TextView email_;
    TextView birthday_;
    String id;
    ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();

        name_ = (TextView) findViewById(R.id.DataFacebook_name);
        email_ = (TextView) findViewById(R.id.DataFacebook_email);
        birthday_ = (TextView) findViewById(R.id.DataFacebook_birthday);
        image = (ImageView) findViewById(R.id.imagefacebook);

        String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");
        String birthday = intent.getStringExtra("birthday");
        id = intent.getStringExtra("id");

        name_.setText(id);
        email_.setText(email);
        birthday_.setText(birthday);

    }


//mPostReference.addValueEventListener(postListener);

        //@Override
        //   public View onCreateView(String name, Context context, AttributeSet attrs) {
//        Bitmap mBitmap = null;
//        try {
//            mBitmap = getFacebookProfilePicture(id);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        image.setImageBitmap(mBitmap);
//        return super.onCreateView(name, context, attrs);
//   }

//    public static Bitmap getFacebookProfilePicture(String userID) throws IOException {
//        URL imageURL = new URL("https://graph.facebook.com/" + userID + "/picture?type=large");
//        Bitmap bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
//
//        return bitmap;
//    }
    }




