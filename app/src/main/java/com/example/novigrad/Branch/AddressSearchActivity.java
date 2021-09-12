package com.example.novigrad.Branch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.novigrad.R;
import com.example.novigrad.Service.CustomerViewBranchActivity;
import com.example.novigrad.Users.Customer;
import com.example.novigrad.Users.User;
import com.example.novigrad.UsersView.EmployeeListAdapter;
import com.example.novigrad.UsersView.ItemClickListener;
import com.example.novigrad.UsersView.UserListAdapter;
import com.example.novigrad.Welcome.WelcomeActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

import java.util.List;

public class AddressSearchActivity extends AppCompatActivity implements ItemClickListener {

    RecyclerView branchDisplay;
    EditText lineOne;
    EditText lineTwo;
    Button btnGetSelected;
    Customer customer;
    EmployeeListAdapter userListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeActivity();

        btnGetSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder input = new StringBuilder();
                input.append(lineOne.getText().toString());
                input.append(" ");
                input.append(lineTwo.getText().toString());

                updateRecycler(customer.filterAddress(input.toString()));
            }
        });

    }

    private void initializeActivity(){
        setContentView(R.layout.activity_address_search);

        btnGetSelected = findViewById(R.id.addressSearchBtn);
        branchDisplay = findViewById(R.id.branchDisplayByAddress);
        lineOne = findViewById(R.id.addressLineOne);
        lineTwo = findViewById(R.id.addressLineTwo);

        //getting admin from previous activity
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String ROLE = extras.getString("ROLE");
        this.customer = (Customer) extras.getSerializable(ROLE);

        this.userListAdapter = new EmployeeListAdapter();
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

}