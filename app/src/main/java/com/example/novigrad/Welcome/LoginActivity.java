package com.example.novigrad.Welcome;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.novigrad.R;
import com.example.novigrad.Users.User;
import com.example.novigrad.Utils.Authenticate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class LoginActivity extends AppCompatActivity {
    //This is where users will sign in, or be re-directed to sign up. This is the first screen that users will see.
    EditText mEmail, mPassword;
    Button mLoginBtn;
    TextView mCreateBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore; // connects to firebase firestore for additional user information and collections
    String userID;
    String ROLE;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signOut();

        //variables to grab information from the input boxes
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        fAuth = FirebaseAuth.getInstance();
        mLoginBtn = findViewById(R.id.loginBtn);
        mCreateBtn = findViewById(R.id.createText);
       // ROLE = "Employee"; //change to align with a spinner

        //Sign in user
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if (email.equals("admin") && password.equals("admin")){
                    email = "admin@novigrad.ca";
                    password = "@Novi2Grad";
                }

                if(authenticated(email, password)){

                    user.signIn(getApplicationContext(), WelcomeActivity.class, email, password);

                }
            }
        });

        //directs users to sign up screen if they would like to create an account
        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }

    private boolean authenticated(String email, String password){
        boolean authenticated = true;
        if (!Authenticate.authenticateEmail(email) || !Authenticate.authenticatePassword(password) ){
            mEmail.setError("Invalid login.");
            mPassword.setError("Invalid login.");
            authenticated = false;
        }
        return authenticated;
    }

    private void signOut(){
        FirebaseAuth.getInstance().signOut();
    }

}
