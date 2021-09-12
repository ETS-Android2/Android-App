package com.example.novigrad.Welcome;

//Exceptions

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.novigrad.R;
import com.example.novigrad.Users.Admin;
import com.example.novigrad.Users.Customer;
import com.example.novigrad.Users.Employee;
import com.example.novigrad.Users.User;
import com.example.novigrad.Utils.Authenticate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

//UI
//Firebase

public class RegisterActivity extends AppCompatActivity {

    EditText mFirstName, mLastName, mEmail, mPassword, mConfirmPassword; //user information
    Button mRegisterBtn;
    TextView mLoginBtn;
    Switch roleSwitch;
    FirebaseAuth fAuth; // connects to firebase email/password authentication
    FirebaseFirestore fStore; // connects to firebase firestore for additional user information and collections
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        signOut();

        //DEVELOPER NOTE: ALL ELEMENTS IN THE .XML FILE MUST MATCH THESE IDs
        //user information
        mFirstName = findViewById(R.id.firstName);
        mLastName = findViewById(R.id.lastName);

        mEmail = findViewById(R.id.txtviewemail);
        mPassword = findViewById(R.id.txtviewpassword);
        mConfirmPassword = findViewById(R.id.confirmPassword);
        final String[] ROLE = new String[2];

        //buttons
        mRegisterBtn = findViewById(R.id.registerBtn);
        mLoginBtn = findViewById(R.id.loginText);


        fAuth = FirebaseAuth.getInstance();

        ROLE[0]="Customer";
        roleSwitch = (Switch) findViewById(R.id.roleSwitch);
        roleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ROLE[0] = "customer";
                    ROLE[1] = "employee";
                } else {
                    ROLE[0] = "employee";
                    ROLE[1] = "customer";
                }
            }
        });

        //when users click the register button, their email and password are written into the database as a new user
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String firstName = mFirstName.getText().toString();
                final String lastName = mLastName.getText().toString();
                final String email = mEmail.getText().toString().trim();
                final String password = mPassword.getText().toString().trim();
                final String confirmPassword = mConfirmPassword.getText().toString().trim();

                if (authenticated(firstName, lastName, email, password, confirmPassword)){
                        final User user = createUser(ROLE[0], firstName, lastName, email, password);
                        user.registerUser(fAuth, getApplicationContext(), WelcomeActivity.class);
                }
            }
        });

        //redirects users to log in page if they already have an account
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

    }

    private User createUser(String theRole, String firstName, String lastName, String email, String password){
        switch(theRole){
            case("admin"):
                return new Admin();
            case("employee"):
                return new Employee(firstName, lastName, email, password);
            default:
                return new Customer(firstName, lastName, email, password);
        }
    }

    private boolean authenticated(String firstName, String lastName, String email, String password, String confirmPassword){
        boolean authenticated = true;

        //A few methods to limit user inputs (i.e. special character rules, password length, valid email, domain for employee....)
        if (!Authenticate.authenticateName(firstName)){
            mFirstName.setError("First name can only be made of upper and lowercase letters, and be more than one character.");
            authenticated = false;
        }

        if (!Authenticate.authenticateName(lastName)){
            mLastName.setError("Last name can only be made of upper and lowercase letters, and be more than one character.");
            authenticated = false;
        }

        if (!Authenticate.authenticateEmail(email)){
            mEmail.setError("Invalid email.");
            authenticated = false;
        }

        if (!Authenticate.authenticatePassword(password)) {
            /* password must have at least:
               one lowercase letter
               one digit
               one special character
               one capital letter
               a minimum length of 6 characters and a max of 16 characters
             */
            mPassword.setError("Invalid password.\nMust at least one lowercase and letter, one digit, one special character, and be between 6 and 16 characters.");
            authenticated = false;
        } else if(!password.equals(confirmPassword)){
            mPassword.setError("Passwords do not match.");
            mConfirmPassword.setError("Passwords do not match.");
            authenticated = false;
        }

        if (authenticated == false){
            Toast.makeText(RegisterActivity.this, "Invalid Registration", Toast.LENGTH_SHORT).show();
        }

        return authenticated;
    }

        private void signOut(){
            FirebaseAuth.getInstance().signOut();
        }
    }
