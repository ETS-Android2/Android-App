package com.example.novigrad.UsersView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.novigrad.R;
import com.example.novigrad.Users.Admin;
import com.example.novigrad.Users.User;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class UsersView extends AppCompatActivity implements ItemClickListener {

    public Admin admin;

    private RecyclerView recyclerView;

    List<User> customers = new ArrayList<>();
    List<User> employees = new ArrayList<>();
    List<User> list = new ArrayList<>();
    List<User> usersToDelete = new ArrayList<>();
    UserListAdapter userListAdapter;
    Button mDeleteUsers;
    Switch switchView;
    private String[] listRole = new String[1];

    FirebaseFirestore reference;
    CollectionReference collection;
    private AppCompatButton btnGetSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_view);

        initializeActivity();

        //add a text value to see when the button is on.
        switchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ON) {
                if(ON) {
                    updateRecycler(admin.getEmployeeList());
                    System.out.println("Employees:");
                    for(User user : admin.getEmployeeList()){
                        System.out.println(user);
                    }
                } else {
                    updateRecycler(admin.getCustomerList());
                    System.out.println("Custys:");
                    for(User user : admin.getCustomerList()){
                        System.out.println(user);
                    }
                }
            }
        });
        updateRecycler(admin.getCustomerList());
        //Selecting users that admin wants to delete.
        mDeleteUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUsers();
            }
        });

    }

    private void initializeActivity(){
        //setting buttons
        this.recyclerView = findViewById(R.id.list);
        this.mDeleteUsers = findViewById(R.id.mDeleteUsers);
        this.userListAdapter = new UserListAdapter();
        this.switchView = findViewById(R.id.switch2);

        //getting admin from previous activity
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        this.admin = (Admin) extras.getSerializable("user");
        admin.initializeListOfUsers(userListAdapter);
        System.out.println("Current User: " + admin);

        //for database
        reference = FirebaseFirestore.getInstance();
        collection = reference.collection("users");
        System.out.println("PRINTING USERS");
        updateRecycler(admin.getCustomerList());
    }


    //must fetchUsers before being called to update properly. Fetch users is asynchronous.
    private void updateRecycler(List<User> userRole){
        userListAdapter.updateAdapter(userRole);
        userListAdapter.setItemClickListener(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(userListAdapter);

    }

    @Override
    public void itemClick(User user) {
        if (!usersToDelete.contains(user)){
            usersToDelete.add(user);
        } else {
            usersToDelete.remove(user);
        }
    }

    private void deleteUsers(){
        if (usersToDelete.isEmpty()){return;}
        for (final User user : usersToDelete){

            if (user.getROLE().equals("customer")){
                admin.getCustomerList().remove(user);
                updateRecycler(admin.getCustomerList());
            } else {
                admin.getEmployeeList().remove(user);
                updateRecycler(admin.getEmployeeList());
            }
            user.selfDestruct(getApplicationContext());
        }
        usersToDelete.clear();

    }
}
