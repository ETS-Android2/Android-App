package com.example.novigrad.Welcome;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.novigrad.MainActivity;
import com.example.novigrad.R;
import com.example.novigrad.Users.Admin;
import com.example.novigrad.Users.Customer;
import com.example.novigrad.Users.Employee;
import com.example.novigrad.Users.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

//UI
//Firebase

public class WelcomeActivity extends AppCompatActivity {
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    Button mLogoutBtn;
    TextView firstName;
    TextView role;
    User user;
    String ROLE;


    public User getUser(){
        return this.user;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        //this.user = (Admin) extras.getSerializable(ROLE);

        System.out.println("Extras == null" + (extras == null));
        if (extras != null){
            ROLE = extras.getString("ROLE");
            System.out.println("ROLE IS: " + ROLE);
            System.out.println(".equals" + ROLE.equals("employee"));

            Bundle bundle = new Bundle();
            Fragment welcomeFrag;
            switch(ROLE){
                case("admin"):
                    this.user = (Admin) extras.getSerializable(ROLE);
                    //https://zocada.com/using-intents-extras-pass-data-activities-android-beginners-guide/
                    welcomeFrag = new aWelcomeFragment();
                    bundle.putString("ROLE", ROLE);
                    bundle.putSerializable(ROLE, user);
                    welcomeFrag.setArguments(bundle);
                    setFragment(welcomeFrag);
                    break;
                case("employee"):
                    this.user = (Employee) extras.getSerializable(ROLE);
                    welcomeFrag = new eWelcomeFragment();
                    bundle.putString("ROLE", ROLE);
                    bundle.putSerializable(ROLE, user);
                    welcomeFrag.setArguments(bundle);
                    setFragment(welcomeFrag);
                    break;
                default:
                    this.user = (Customer) extras.getSerializable(ROLE);
                    welcomeFrag = new cWelcomeFragment();
                    bundle.putString("ROLE", ROLE);
                    bundle.putSerializable(ROLE, user);
                    welcomeFrag.setArguments(bundle);
                    setFragment(welcomeFrag);
                    break;
            }
        }

                System.out.println("No fragment made.");
        //firstName = findViewById(R.id.txt_getName);
        //firstName.setText(user.getFirstName());

        //role = findViewById(R.id.txt_getRole);
        //role.setText(user.getROLE());

        mLogoutBtn = findViewById(R.id.logoutBtn);


        //redirects users to log in page if they already have an account
        //Note: Since all users are able to sign out, this is a common action featured for all users in welcomeActivity.
        mLogoutBtn.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                onStop();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

    }

    public void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //In this activity, the fragment is rendered inside a frameLayout widget
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }


}
