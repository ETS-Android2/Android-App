/* This is the activity in which forms will be rendered and displayed dynamically*/
package com.example.novigrad.Service;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.novigrad.R;
import com.example.novigrad.Users.Customer;
import com.example.novigrad.Users.Employee;
import com.example.novigrad.Users.Service;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ServiceFormActivity extends AppCompatActivity {

    private Service service;
    TextView formName;
    Button submitBtn;

    // RecyclerView Stuff
    private RecyclerView fieldsRecyclerView, docsRecyclerView;
    private ServiceFormFieldsAdapter fieldsAdapter, docsAdapter;
    private RecyclerView.LayoutManager fieldsLayoutManager, docsLayoutManager;
    private FirebaseFirestore fStore;
    private ArrayList<String> fieldNames, docNames;
    private String ROLE;
    private Employee employee;
    private String customerID;
    private Customer customer;

    private Map<String,String> fields;
    private Map<String,Object> docs;

    private Service serviceForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_form);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        // get from intent the branch that the customer selects beforehand


        // gets the service and the role from previous page
        // either from admin or from customer
        service = (Service) extras.getSerializable("service");
        formName = findViewById(R.id.formName);
        ROLE = extras.getString("ROLE");
        if (ROLE.equals("customer")){
            // When customer submits a service request, an employee must be passed through in
            // extras bundle as serializable with key "employee"
            employee = (Employee) extras.getSerializable("employee");
            // the customer's id must also be passed through the bundle
            customer = (Customer) extras.getSerializable("customer");
            customerID = customer.getUserID();
        }

        formName.setText(service.getTitle());
        fields = service.getFields();
        docs = service.getDocs();

        final Object[] fieldNamesSet = fields.keySet().toArray();
        Object[] docNamesSet = docs.keySet().toArray();

        fieldNames = new ArrayList<>();
        docNames = new ArrayList<>();

        for (int i=0; i < fieldNamesSet.length; i++){
            fieldNames.add(fieldNamesSet[i].toString());
            fields.put(fieldNamesSet[i].toString(), "");
        }
        for (int i=0; i < docNamesSet.length; i++){
            docNames.add(docNamesSet[i].toString());
            docs.put(docNamesSet[i].toString(),"");
        }

        submitBtn = findViewById(R.id.submitFormBtn);

        for (int i = 0; i < fields.size(); i++){

        }

        // RecyclerViews
        fieldsRecyclerView = findViewById(R.id.fieldsRecyclerView);
        docsRecyclerView = findViewById(R.id.docsRecyclerView);
        fieldsRecyclerView.setHasFixedSize(true);
        docsRecyclerView.setHasFixedSize(true);

        // Adapters
        fieldsAdapter = new ServiceFormFieldsAdapter(fieldNames);
        docsAdapter = new ServiceFormFieldsAdapter(docNames);

        // Layout Managers
        fieldsLayoutManager = new LinearLayoutManager(this);
        docsLayoutManager = new LinearLayoutManager(this);

        // Set adapters and layout manager for fields recycler view
        fieldsRecyclerView.setAdapter(fieldsAdapter);
        fieldsRecyclerView.setLayoutManager(fieldsLayoutManager);

        // Set adapters and layout manager for docs recycler view
        docsRecyclerView.setAdapter(docsAdapter);
        docsRecyclerView.setLayoutManager(docsLayoutManager);

        // On-Click
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO - submit form for customers (add to database), make toast once submitted, change activity once submitted

                boolean flag = true; // true if service form is filled out properly

                if (ROLE.equals("admin")){
                    Toast.makeText(ServiceFormActivity.this, "Admin cannot submit service request", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (fields.size() != fieldsAdapter.getFieldInputs().size()){
                    flag = false;
                    Toast.makeText(ServiceFormActivity.this, "Error: All fields must be filled out", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (docs.size() != docsAdapter.getFieldInputs().size()){
                    flag = false;
                    Toast.makeText(ServiceFormActivity.this, "Error: All documents must be filled out", Toast.LENGTH_SHORT).show();
                    return;
                }

                fields = fieldsAdapter.getFieldInputs();
                docs = docsAdapter.getFieldInputs();
                // maybe validate document by checking if there is .png or .jpg at the end
                for (int i = 0; i < fields.size(); i++){
                    //check field input hash map to see if any values are null
                    if (fields.get(fieldNames.get(i)).equals("")){
                        flag = false;
                        Toast.makeText(ServiceFormActivity.this, "Error: All fields must be filled out", Toast.LENGTH_SHORT).show();
                    }
                }
                for (int i = 0; i < docs.size(); i++){
                    String docString = docs.get(docNames.get(i)).toString();
                    //check doc input hash map to see if any values are null
                    if (docString.equals("")){
                        flag = false;
                        Toast.makeText(ServiceFormActivity.this, "Error: All documents must be filled out", Toast.LENGTH_SHORT).show();
                    }
                    // check to see if docs are correct file types
                    if (docString.length() < 5){
                        flag = false;
                        Toast.makeText(ServiceFormActivity.this, "Error: invalid entry", Toast.LENGTH_SHORT).show();
                    }
                    String[] docStringSplit = docString.split("\\.");
                    if (docStringSplit.length != 2){
                        flag = false;
                        Toast.makeText(ServiceFormActivity.this, "Error: invalid file type", Toast.LENGTH_SHORT).show();
                    } else {
                        if (!(docStringSplit[1].equals("jpg") || docStringSplit[1].equals("png") || docStringSplit[1].equals("jpeg"))){
                            flag = false;
                            Toast.makeText(ServiceFormActivity.this, "Error: invalid file type", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                if (flag && ROLE.equals("customer")){
                    // create new instance of service with filled in map values
                    serviceForm = new Service(service.getTitle(), service.getPrice(), fields, docs);
                    // sets the customer ID to the customer's id which is passed through the intent
                    serviceForm.setPurchaseID(customer.getUserID());
                    // submit form
                    employee.enqueueServiceRequest(getApplicationContext(), serviceForm);
                    // send toast saying form was successfully submitted
                    Toast.makeText(ServiceFormActivity.this, "Service form submitted successfully.", Toast.LENGTH_SHORT).show();
                    // change activity may have to change this for deliverable 4
                    //Intent intent = new Intent(ServiceFormActivity.this, CustomerViewBranchActivity.class);
                    customer.passUser(getApplicationContext(), CustomerViewBranchActivity.class, employee);
                    //startActivity(intent);
                }
            }
        });
    }
}