package com.example.novigrad.Branch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.novigrad.R;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.novigrad.Service.CustomerViewBranchActivity;
import com.example.novigrad.Users.Customer;
import com.example.novigrad.Users.Employee;
import com.example.novigrad.Users.Service;
import com.example.novigrad.Users.User;
import com.example.novigrad.UsersView.EmployeeListAdapter;
import com.example.novigrad.UsersView.ItemClickListener;
import com.example.novigrad.UsersView.UserListAdapter;
import com.example.novigrad.Welcome.WelcomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import android.os.Bundle;
import android.widget.TextView;

public class ServiceSearchActivity extends AppCompatActivity implements ItemClickListener {
    private Spinner serviceSpinner;
    FirebaseFirestore fStore;
    List<String> services = new ArrayList<>();
    RecyclerView branchDisplay;
    Customer customer;
    EmployeeListAdapter userListAdapter;
    private Button btnGetSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();


        btnGetSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateRecycler(customer.filterServices(serviceSpinner.getSelectedItem().toString()));
            }
        });

    }
    private void initialize(){
        setContentView(R.layout.activity_service_search);
        btnGetSelected = (Button) findViewById(R.id.serviceSearchBtn);
        serviceSpinner = findViewById(R.id.serviceSpinner);
        branchDisplay = findViewById(R.id.branchDisplayByService);

        populateSpinnerArray();


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

    private void populateSpinnerArray(){
        FirebaseFirestore fReference = FirebaseFirestore.getInstance();
        CollectionReference collection = fReference.collection("services");

        Task<QuerySnapshot> querySnapshotTask = collection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<DocumentSnapshot> documents = task.getResult().getDocuments();

                for (DocumentSnapshot doc : documents) {
                    Service someService = doc.toObject(Service.class);

                    services.add(doc.getString("title"));

                }
                ArrayAdapter<String> serviceAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, services);
                serviceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                serviceSpinner.setAdapter(serviceAdapter);
            }

        });

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