package com.example.novigrad.Service;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.novigrad.R;
import com.example.novigrad.Users.Employee;
import com.example.novigrad.Users.Service;
import com.example.novigrad.Welcome.WelcomeActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServiceRequestListActivity extends AppCompatActivity {
    Button approveBtn, rejectBtn;
    TextView formName, customerID;

    private Map<String, Object> docs;
    private Map<String, String> fields;
    private ArrayList<String> fieldInputs,docInputs;
    private Employee employee;
    private Service currentRequest;

    private RecyclerView fieldsRecyclerView, docsRecyclerView;
    private ServiceRequestAdapter fieldsAdapter, docsAdapter;
    private RecyclerView.LayoutManager fieldsLayoutManager, docsLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_request_list);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        employee = (Employee) extras.getSerializable("employee");
        currentRequest = employee.nextServiceRequest();
        fields = currentRequest.getFields();
        docs = currentRequest.getDocs();

        Object[] fieldNames = fields.keySet().toArray();
        Object[] docNames = docs.keySet().toArray();

        fieldInputs = new ArrayList<>();
        docInputs = new ArrayList<>();

        for (int i = 0; i < fieldNames.length; i++){
            fieldInputs.add(fieldNames[i] + "." + fields.get(fieldNames[i]));
        }

        for (int i = 0; i < docNames.length; i++){
            docInputs.add(docNames[i] + "." + docs.get(docNames[i]));
        }

        // Buttons
        approveBtn = findViewById(R.id.approveFormBtn);
        rejectBtn = findViewById(R.id.rejectFormButton);

        //TextViews
        customerID = findViewById(R.id.customerID);
        formName = findViewById(R.id.formName);
        customerID.setText(currentRequest.getPurchaseID());
        formName.setText(currentRequest.getTitle());

        // RecyclerView
        fieldsRecyclerView = findViewById(R.id.fieldsRecyclerView);
        docsRecyclerView = findViewById(R.id.docsRecyclerView);

        // Adapter
        fieldsAdapter = new ServiceRequestAdapter(fieldInputs);
        docsAdapter = new ServiceRequestAdapter(docInputs);
        fieldsRecyclerView.setAdapter(fieldsAdapter);
        docsRecyclerView.setAdapter(docsAdapter);

        // Layout Manager
        fieldsLayoutManager = new LinearLayoutManager(this);
        docsLayoutManager = new LinearLayoutManager(this);
        fieldsRecyclerView.setLayoutManager(fieldsLayoutManager);
        docsRecyclerView.setLayoutManager(docsLayoutManager);

        //On Clicks
        approveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                employee.approveServiceRequest(ServiceRequestListActivity.this, currentRequest);
                if (employee.nextServiceRequest()!=null){
                    Intent intent = new Intent(ServiceRequestListActivity.this, ServiceRequestListActivity.class);
                    Bundle extras = new Bundle();
                    extras.putSerializable("employee", employee);
                    intent.putExtras(extras);
                    startActivity(intent);
                } else {
                    employee.passUser(ServiceRequestListActivity.this, WelcomeActivity.class);
                }
            }
        });
        rejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                employee.rejectServiceRequest(ServiceRequestListActivity.this, currentRequest);
                if (employee.nextServiceRequest()!=null){
                    Intent intent = new Intent(ServiceRequestListActivity.this, ServiceRequestListActivity.class);
                    Bundle extras = new Bundle();
                    extras.putSerializable("employee", employee);
                    intent.putExtras(extras);
                    startActivity(intent);
                } else {
                    employee.passUser(ServiceRequestListActivity.this, WelcomeActivity.class);
                }
            }
        });
    }
}