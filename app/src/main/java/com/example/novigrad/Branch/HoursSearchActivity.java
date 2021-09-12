package com.example.novigrad.Branch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.novigrad.R;
import com.example.novigrad.Service.CustomerViewBranchActivity;
import com.example.novigrad.Users.Admin;
import com.example.novigrad.Users.Customer;
import com.example.novigrad.Users.Employee;
import com.example.novigrad.Users.User;
import com.example.novigrad.UsersView.EmployeeListAdapter;
import com.example.novigrad.UsersView.ItemClickListener;
import com.example.novigrad.UsersView.UserListAdapter;
import com.example.novigrad.Welcome.WelcomeActivity;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;

import android.os.Bundle;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HoursSearchActivity extends AppCompatActivity implements ItemClickListener {
    private Button btnGetSelected;
    private Spinner daySpinner;
    private Spinner timeSpinner;
    RecyclerView branchDisplay;
    EmployeeListAdapter userListAdapter;
    Customer customer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeActivity();
        btnGetSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("day: " + daySpinner.getSelectedItem().toString());
                updateRecycler(customer.filterHours(generateHourMap(daySpinner.getSelectedItem().toString(), timeSpinner.getSelectedItem().toString())));
            }
        });
    }

    private void initializeActivity(){
        setContentView(R.layout.activity_hours_search);
        btnGetSelected = (Button) findViewById(R.id.hoursSearchBtn);
        daySpinner = findViewById(R.id.daySpinner);
        timeSpinner = findViewById(R.id.timeSpinner);
        branchDisplay = findViewById(R.id.branchDisplayByHours);


        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.Days));
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.SearchTime));

        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(dayAdapter);
        timeSpinner.setAdapter(timeAdapter);


        this.userListAdapter = new EmployeeListAdapter();

        //getting admin from previous activity
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String ROLE = extras.getString("ROLE");
        this.customer = (Customer) extras.getSerializable(ROLE);

        customer.searchBranch(userListAdapter); //use this for all
        branchDisplay.setAdapter(userListAdapter);
        updateRecycler(customer.getEmployeeList());
    }

    @Override
    public void itemClick(User user) {
        customer.passUser(getApplicationContext(), CustomerViewBranchActivity.class, user);
    }
    //must fetchUsers before being called to update properly. Fetch users is asynchronous.
    private void updateRecycler(List<User> filteredList){
        userListAdapter.updateAdapter(filteredList);
        userListAdapter.setItemClickListener(this);
        branchDisplay.setHasFixedSize(true);
        branchDisplay.setLayoutManager(new LinearLayoutManager(this));
        branchDisplay.setAdapter(userListAdapter);
    }

    private Map<String, Map<String, String>> generateHourMap(String day, String time){
        Map<String, Map<String, String>> hoursToSearch = new HashMap<>();
        hoursToSearch.put("hours", new HashMap<String, String>());
        hoursToSearch.get("hours").put("day", day);
        hoursToSearch.get("hours").put("time", time);
        return hoursToSearch;
    }

}
