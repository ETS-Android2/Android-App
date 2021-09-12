package com.example.novigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.novigrad.Welcome.LoginActivity;
import com.example.novigrad.Welcome.RegisterActivity;

public class MainActivity extends AppCompatActivity {
//This is the welcome screen where users will see their name and role
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToLogin(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, 0);
    }

    public void goToSignUp(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivityForResult(intent, 0);
    }
}