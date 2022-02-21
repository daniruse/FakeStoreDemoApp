package com.example.FakeStoreDemoApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextUsername;
    private EditText editTextPassword;
    private String username;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //this will hide the action bar
        getSupportActionBar().hide();

        //initializing Username and Password Edit Texts
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);

    }

    public void login(View view) throws ExecutionException, InterruptedException {
        //get Username and Password
        username = editTextUsername.getText().toString();
        password = editTextPassword.getText().toString(); //not the best idea to store the password in plain text, but it's sent as plain text to the API anyway

        LoginRequest loginRequest = new LoginRequest();
        String authenticationResponse = loginRequest.execute(username,password).get();

        if(!authenticationResponse.equals("error")){
            try {
                //try to parse JSON response
                JSONObject responseJson = new JSONObject(authenticationResponse);
                if(responseJson.get("token")!=null){
                    Toast.makeText(getApplicationContext(),"Login successful!",Toast.LENGTH_LONG).show();

                    //move to Main Activity

                    Intent intent = new Intent (this, MainActivity.class);
                    intent.putExtra("token",responseJson.get("token").toString()); //pass the authentication token
                    startActivity(intent);
                }
            } catch (JSONException e) {
                //response is not JSON
                e.printStackTrace();
                //try to parse response
                switch (authenticationResponse){
                    case "username or password is incorrect":
                        Toast.makeText(getApplicationContext(),"Invalid credentials!",Toast.LENGTH_LONG).show();
                        break;
                    case "403 Forbidden":
                        Toast.makeText(getApplicationContext(),"An error occurred. Check the User-Agent",Toast.LENGTH_LONG).show();
                    default:
                        Toast.makeText(getApplicationContext(),"An error occurred when trying to authenticate",Toast.LENGTH_LONG).show();
                }
            }
        }
        else
        {
            //an unspecified error happened
            Toast.makeText(getApplicationContext(),"An error occurred when trying to authenticate",Toast.LENGTH_LONG).show();
        }


    }
}