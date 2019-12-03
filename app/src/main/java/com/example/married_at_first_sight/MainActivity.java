package com.example.married_at_first_sight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;

import android.os.Bundle;

import android.text.TextUtils;

import android.util.Log;
import android.view.View;

import java.util.Arrays;

import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;

import android.widget.Toast;

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

import org.json.JSONException;
import org.json.JSONObject;





public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    //variable
    Button login;
    LoginButton facebook;
    Button ConnectButton;
    EditText USer;
    EditText Pass;
    Dialog d;
    FirebaseAuth firebaseAuth;
    CallbackManager callbackManager;
//take from facrbook
    String email;
    String birthday;
    String name;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //create fire base
        firebaseAuth = firebaseAuth.getInstance();
        //creat Listener Login to Dialog
        login = (Button)findViewById(R.id.Manager_Login);
        login.setOnClickListener(this);
        //For facebook connect
        callbackManager = CallbackManager.Factory.create();
        facebook = (LoginButton) findViewById(R.id.login_button);
        facebook.setOnClickListener(this);
        //take data
        FacebookSdk.sdkInitialize(getApplicationContext());
        facebook.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday"));
    }

//function Listener
    @Override
    public void onClick(View v) {
        if (v == login){
            creatDialog();
        }else if(v == ConnectButton){

            connect();
        }else if(v == facebook){
            connectfacebook();
        }
    }

    //creatDialog to Manager
    public void creatDialog(){
        d = new Dialog(this);
        d.setContentView(R.layout.activity_login_maanger);
        d.setTitle("Login");
        ConnectButton = (Button)d.findViewById(R.id.ConnectId);
        USer = (EditText) d.findViewById(R.id.User);
        Pass = (EditText) d.findViewById(R.id.Password);
        ConnectButton.setOnClickListener(this);
        d.show();
    }

    //function connect to Manager
    public void connect() {
        String username = USer.getText().toString().trim();
        String Password = Pass.getText().toString().trim();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(Password)) {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            return;
        } else {
            firebaseAuth.signInWithEmailAndPassword(username, Password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Connect", Toast.LENGTH_SHORT).show();
                                Intent launchactivity = new Intent(MainActivity.this, questions.class);
                                startActivity(launchactivity);

                            } else {
                                  Toast.makeText(MainActivity.this,"Try again",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    //function connectfacebook
    public void connectfacebook(){
        facebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {

                                Log.v("LoginActivity", response.toString());

                             // Application code
                                try {
                                    email = object.getString("email").toString();
                                    birthday = object.getString("birthday").toString(); // 01/31/1980 format
                                    name = object.getString("name").toString();
                                    id=object.getString("id").toString();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                //move data to questions
                                Toast.makeText(MainActivity.this, "Welcome "+ name, Toast.LENGTH_SHORT).show();
                                Intent intentquestions = new Intent(MainActivity.this, questions.class);
                                intentquestions.putExtra("name",name);
                                intentquestions.putExtra("email",email);
                                intentquestions.putExtra("birthday",birthday);
                                intentquestions.putExtra("id",id);
                                startActivity(intentquestions);
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday,picture.type(large)");
                request.setParameters(parameters);
                request.executeAsync();
            }
            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}