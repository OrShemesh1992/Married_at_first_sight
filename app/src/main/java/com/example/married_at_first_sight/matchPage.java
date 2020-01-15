package com.example.married_at_first_sight;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/*
This class is for the match page.
 */
public class matchPage extends AppCompatActivity
{
    Intent intent;
    ProfilePictureView matchImage;
    ArrayList<String> arrUserId = new ArrayList<String>();
    DatabaseReference database;
    final Profile profile = Profile.getCurrentProfile();
    String matchID = "";
    TextView percentTV;
    TextView nameTV;
    TextView cityTV;
    TextView ageTV;

    //For gps coordination:
    private LocationManager locationManager = null;
    private LocationListener locationListener = null;
    private Boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        percentTV = (TextView)findViewById(R.id.Precent);
        nameTV = (TextView)findViewById(R.id.name);
        cityTV = (TextView)findViewById(R.id.city);
        ageTV = (TextView)findViewById(R.id.age);

        getMatch();
        new Timer().scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {
                getStatistic();
                setLocationAndName();
            }
        }, 0, 5000);

        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        getGPS();
    }

    /*
    This function gets match percent from 2 users.
     */
    public void getStatistic()
    {
        database = FirebaseDatabase.getInstance().getReference();
        database.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                ArrayList<String> ans_q_1 = new ArrayList<String>();
                ArrayList<String> ans_q_2 = new ArrayList<String>();
                for (DataSnapshot statis : dataSnapshot.child("Answers").child(profile.getId()).getChildren())
                {
                    String get_ans1 = statis.getKey();
                    String get_q1 = statis.getValue(String.class);
                    ans_q_1.add(get_ans1 + get_q1);
                }
                if (matchID != "")
                {
                    for (DataSnapshot statis : dataSnapshot.child("Answers").child(matchID).getChildren())
                    {
                        String get_ans2 = statis.getKey();
                        String get_q2 = statis.getValue(String.class);
                        ans_q_2.add(get_ans2 + get_q2);
                    }
                }
                int equal = 0;
                for(int i = 0; i < ans_q_2.size() || i < ans_q_2.size(); i++)
                {
                    if (ans_q_2.get(i).equals(ans_q_1.get(i)))
                    {
                        equal++;
                    }
                }
                if (Math.max(ans_q_2.size(), ans_q_2.size()) != 0)
                {
                    percentTV.setText(String.valueOf((equal * 100) / Math.max(ans_q_2.size(), ans_q_2.size())));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
            }
        });
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
    This function gets all the key to ArrayList from the firebase for the picture
    and once click it sets different id.
     */
    public void getMatch()
    {
        matchImage = (ProfilePictureView) findViewById(R.id.matchImage);
        database = FirebaseDatabase.getInstance().getReference();
        database.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for (DataSnapshot face : dataSnapshot.child("faceData").getChildren())
                {
                    String id = face.getKey();
                    arrUserId.add(id);
                }
                matchID = arrUserId.get(0);
                matchImage.setProfileId(matchID);

                //Sets a new id when click the match picture.
                matchImage.setClickable(true);

                matchImage.setOnClickListener(new View.OnClickListener()
                {
                    int start = 0;
                    Iterator<String> it = arrUserId.iterator();

                    @Override
                    public void onClick(View v)
                    {
                        if(it.hasNext())
                        {
                            if(start == 0)
                            {
                                it.next();
                                start = 1;
                            }
                            matchID = it.next();
                            if(matchID != profile.getId())
                            {
                                matchImage.setProfileId(matchID);
                                percentTV.setText("0");
                                cityTV.setText("City");
                                nameTV.setText("Name");
                                ageTV.setText("Age");
                            }
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

    /*
    Moves to other activity with match ID.
     */
    public void message(View view)
    {
        Intent i = new Intent(this, messagePage.class);
        i.putExtra("sendID", matchID);
        startActivity(i);
    }

    /*
    Moves to other activity home page.
     */
    public void Home(View view)
    {
        Intent i = new Intent(this, mainPage.class);
        startActivity(i);
    }

    /*
    Sets City in the match page.
     */
    public void setLocationAndName()
    {
        database = FirebaseDatabase.getInstance().getReference();
        database.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (matchID != "")
                {
                    String cityName = dataSnapshot.child("gps_city").child(matchID).getValue(String.class);
                    String personName = dataSnapshot.child("faceData").child(matchID).child("name").getValue(String.class);
                    String personAge = dataSnapshot.child("faceData").child(matchID).child("age").getValue(String.class);

                    nameTV.setText(personName);
                    cityTV.setText(cityName);
                    ageTV.setText(personAge);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
            }
        });
    }

    /*
    This function gets the name city.
     */
    @SuppressLint("MissingPermission")
    public void getGPS()
    {
        flag = displayGpsStatus();
        if (flag)
        {
            locationListener = new MyLocationListener();
            locationManager.requestLocationUpdates(LocationManager
                           .GPS_PROVIDER, 5000, 10,locationListener);
        }
        else
        {
            Toast.makeText(getBaseContext(), "Gps Status!" + " Your GPS is: OFF", Toast.LENGTH_SHORT).show();
        }
    }

    /*
    This function checks if GPS is enable or disable.
     */
    private Boolean displayGpsStatus()
    {
        ContentResolver contentResolver = getBaseContext().getContentResolver();
        boolean gpsStatus = Settings.Secure.isLocationProviderEnabled(contentResolver,
                                                                      LocationManager.GPS_PROVIDER);
        if(gpsStatus)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /*
    ---------- Listener class to get coordinates ----------
    */
    private class MyLocationListener implements LocationListener
    {
        @Override
        public void onLocationChanged(Location loc)
        {
            //to get City-Name from coordinates.
            String cityName = null;
            Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
            List<Address> addresses;
            try
            {
                addresses = gcd.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
                if(addresses.size() > 0) System.out.println(addresses.get(0).getLocality());
                cityName = addresses.get(0).getLocality();

                database.child("gps_city").child(profile.getId()).setValue(cityName);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        @Override
        public void onProviderDisabled(String provider)
        {
        }

        @Override
        public void onProviderEnabled(String provider)
        {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras)
        {
        }
    }
}
