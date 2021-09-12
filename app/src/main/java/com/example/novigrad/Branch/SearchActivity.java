package com.example.novigrad.Branch;

import com.example.novigrad.R;
import com.example.novigrad.Users.Customer;
import com.example.novigrad.Users.Employee;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

public class SearchActivity extends AppCompatActivity {
    Button addressSearchBtn, hoursSearchBtn, servicesSearchBtn;
    Customer customer;
    Employee employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String ROLE = extras.getString("ROLE");
        this.customer = (Customer) extras.getSerializable(ROLE);
        String extraROLE = extras.getString("extraROLE");

        addressSearchBtn = findViewById(R.id.addressSchBtn);
        hoursSearchBtn = findViewById(R.id.hoursSchBtn);
        servicesSearchBtn = findViewById(R.id.serviceSchBtn);


        servicesSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customer.passUser(getApplicationContext(), ServiceSearchActivity.class);
            }
        });


        hoursSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customer.passUser(getApplicationContext(), HoursSearchActivity.class);
            }
        });

        addressSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customer.passUser(getApplicationContext(), AddressSearchActivity.class);
            }
        });


    }
}