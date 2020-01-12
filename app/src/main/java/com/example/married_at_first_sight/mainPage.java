package com.example.married_at_first_sight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationBuilderWithBuilderAccessor;
import androidx.core.app.NotificationCompat;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import java.util.Arrays;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.json.JSONException;
import org.json.JSONObject;

/*
This class is the main application page.
 */
public class mainPage extends AppCompatActivity implements View.OnClickListener
{

    LoginButton facebookLoginButton; //Facebook login button.
    Button managerLoginButton; //Manager login button.
    Button managerConnectionButton; //Manager connection button.
    EditText emailET; //Email edit text for manager connection.
    EditText passwordET; //Password edit text for manager connection.

    Dialog dialog;
    FirebaseAuth firebaseAuth;
    DatabaseReference database;
    CallbackManager callbackManager;

    //Facebook token data:
    String id;
    String name;
    String email;

    AccessToken token; //Check token facebook data.

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        managerLoginButton = (Button)findViewById(R.id.managerLogin);
        managerLoginButton.setOnClickListener(this);

        //Facebook connection.
        callbackManager = CallbackManager.Factory.create();
        facebookLoginButton = (LoginButton)findViewById(R.id.facebookLogin);
        facebookLoginButton.setOnClickListener(this);

        FacebookSdk.sdkInitialize(getApplicationContext());
        facebookLoginButton.setReadPermissions(Arrays.asList("public_profile", "email"));

        database = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = firebaseAuth.getInstance();



    }

    /*
    This function is for "IT'S A MATCH" button.
     */
    public void getMatches(View view)
    {
        token = AccessToken.getCurrentAccessToken();
        Intent intent = new Intent(getApplicationContext(), matchPage.class);
        //If user is not connected to facebook.
        if (token == null)
        {
            Toast.makeText(mainPage.this, "Not Connected", Toast.LENGTH_SHORT).show();
        }
        else
        {
            startActivity(intent);
        }
    }

    /*
    This function is a Listener function.
     */
    @Override
    public void onClick(View v)
    {
        if(v == facebookLoginButton)
        {
            facebookConnection();
        }
        else if (v == managerLoginButton)
        {
            managerLoginDialog();
        }
        else if(v == managerConnectionButton)
        {
            managerConnection();
        }
    }

    /*
    This function creates a dialog for manager connection.
     */
    public void managerLoginDialog()
    {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_manager_login);
        dialog.setTitle("Manager Login");

        managerConnectionButton = (Button)dialog.findViewById(R.id.connect);
        emailET = (EditText)dialog.findViewById(R.id.user);
        passwordET = (EditText)dialog.findViewById(R.id.password);
        managerConnectionButton.setOnClickListener(this);
        dialog.show();
    }

    /*
    This function is for the manager connection.
     */
    public void managerConnection()
    {
        String email = emailET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Email or password incorrect", Toast.LENGTH_SHORT).show();
            emailET.setText("");
            passwordET.setText("");
            return;
        }
        else
        {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(mainPage.this, "Connect", Toast.LENGTH_SHORT).show();
                                Intent launchActivity = new Intent(mainPage.this, managerOptionsPage.class);
                                startActivity(launchActivity);
                            }
                            else
                            {
                                Toast.makeText(mainPage.this, "Try again",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    /*
    This function is for facebook connection.
     */
    public void facebookConnection()
    {
        facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>()
        {
            @Override
            public void onSuccess(LoginResult loginResult)
            {
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback()
                        {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response)
                            {
                                try
                                {
                                    id = object.getString("id");
                                    name = object.getString("name");
                                    email = object.getString("email");
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }

                                Toast.makeText(mainPage.this, name + " Welcome", Toast.LENGTH_SHORT).show();
                                facebookData p = new facebookData(id ,name, " ",email);
                                database.child("faceData").child(id).setValue(p);
                                Intent intent = new Intent(getApplicationContext(), questionnairePage.class);
                                startActivity(intent);
                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday,picture.type(large)");
                request.setParameters(parameters);
                request.executeAsync();
            }
            @Override
            public void onCancel()
            {
            }
            @Override
            public void onError(FacebookException error)
            {
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


}