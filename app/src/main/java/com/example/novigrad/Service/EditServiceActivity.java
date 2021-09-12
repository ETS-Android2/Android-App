package com.example.novigrad.Service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.novigrad.R;
import com.example.novigrad.Users.Service;
import com.example.novigrad.UsersView.FieldAdapter;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

public class EditServiceActivity extends AppCompatActivity {

    private Service service;

    private RecyclerView mRecyclerView;
    private FieldAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FirebaseFirestore fStore;
    private Map<String, String> fields;
    private Map<String, Object> docs;
    private ArrayList<String> fieldList, docList, inputs;
    private String title;
    private String price;
    private String titleId;
    private String oldTitle;

    ImageButton mNameAddBtn, mPriceAddBtn, mInputAddBtn, mDocAddBtn, mDeleteFieldBtn;
    Button mUpdateBtn;
    EditText mAddName, mAddPrice, mAddInput, mAddDoc;
    TextView mServiceTitle, mServicePrice;

    //constructor added for test cases

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_service);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        //Should we pass the service here?

        fStore = FirebaseFirestore.getInstance();

        service = (Service) extras.getSerializable("service");
        System.out.println(service);
        title = service.getTitle();
        oldTitle = service.getTitle();
        price = service.getPrice();


        mNameAddBtn = findViewById(R.id.nameAddBtn);
        mPriceAddBtn = findViewById(R.id.priceAddBtn);
        mInputAddBtn = findViewById(R.id.inputAddBtn);
        mDocAddBtn = findViewById(R.id.docAddBtn);
        mUpdateBtn = findViewById(R.id.updateBtn);
        mDeleteFieldBtn = findViewById(R.id.deleteFieldBtn);
        mAddName = findViewById(R.id.addName);
        mAddPrice = findViewById(R.id.addPrice);
        mAddInput = findViewById(R.id.addInput);
        mAddDoc = findViewById(R.id.addDoc);
        mServiceTitle = findViewById(R.id.serviceTitle);
        mServicePrice = findViewById(R.id.servicePrice);



        mRecyclerView = findViewById(R.id.recyclerViewEditService);


        //inputs (fields and docs)
        fields = service.getFields();
        fieldList = new ArrayList<>();
        Object[] fieldSetArray = fields.keySet().toArray();
        for (int i = 0; i < fieldSetArray.length; i++){
            fieldList.add(fieldSetArray[i].toString());
        }
        docs = service.getDocs();
        Object[] docSetArray = docs.keySet().toArray();
        docList = new ArrayList<>();
        for (int i = 0; i < docSetArray.length; i++){
            docList.add(docSetArray[i].toString());
        }
        inputs = new ArrayList<String>(); //the values here should be associated to the key value pair of each attribute in Service

        Map<String, String> someFields = service.getFields();
        for(Map.Entry<String, String> entry : someFields.entrySet()){
            inputs.add("(field) " + entry.getKey());
        }


        Map<String, Object> someDocs = service.getDocs();
        for(Map.Entry<String, Object> entry : someDocs.entrySet()){
            inputs.add("(doc) " + " " + entry.getKey());
        }

        //selected item
        TextView selectedItem;

        //RecyclerView
        mAdapter = new FieldAdapter(inputs);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        //On Clicks
        mNameAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //changes the name of the service
                //if string is not empty, set service title to given name
                String inputName = mAddName.getText().toString();
                if (!mAddName.getText().equals("")){
                    title = mAddName.getText().toString();
                    mServiceTitle.setText(title);
                    mAddName.setText("");
                    service.setTitle(title); //is this correct - Billy
                }
            }
        });
        mInputAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //adds field to the array list fields
                //check to see if the field is already there
                boolean flag = true;
                String inputField = mAddInput.getText().toString();
                for (int i=0; i<fieldList.size(); i++){
                    if (fieldList.get(i).equals(inputField)){
                        flag = false;
                    }
                }
                if (!inputField.equals("") && flag){
                    service.addNewField(inputField);
                    //fields.put(inputField);
                    inputs.add("(field) " + inputField);
                    fieldList.add(inputField);
                    fields.put(inputField,"");
                    mAdapter.notifyDataSetChanged();
                    mAddInput.setText("");
                } else {
                    Toast.makeText(EditServiceActivity.this, "Error: Enter a field", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mDocAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //adds document to the array list docs
                //check to see if the field is already there
                boolean flag = true;
                String inputDoc = mAddDoc.getText().toString();
                if (inputDoc == null){
                    Toast.makeText(EditServiceActivity.this, "Error: Enter a document name", Toast.LENGTH_SHORT).show();
                    return;
                }
                for (int i=0; i<docList.size(); i++){
                    if (docList.get(i).equals(inputDoc)){
                        flag = false;
                    }
                }
                //
                if (!mAddDoc.getText().toString().equals("") && flag){
                    service.addNewDocField(inputDoc);
                    inputs.add("(doc) " + inputDoc);
                    docList.add(inputDoc);
                    docs.put(inputDoc,"");
                    mAdapter.notifyDataSetChanged();
                    mAddDoc.setText("");
                } else {
                    Toast.makeText(EditServiceActivity.this, "Error: Enter a document name", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mPriceAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputPrice = mAddPrice.getText().toString();
                if (!inputPrice.equals("")){
                    //price = String.valueOf(Integer.parseInt(inputPrice));
                    price = inputPrice;
                    service.setPrice(price);
                    mServicePrice.setText("$" + inputPrice);
                    mAddPrice.setText("");
                } else {
                    Toast.makeText(EditServiceActivity.this, "Error: Enter a price", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mDeleteFieldBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // deletes field from the list of fields by removing it from the field names
                // which are passed into the recyclerview and from the map which is ultimately
                // used in the service object
                String selectedItem;
                String selectedItemType;
                if (mAdapter.getSelected() != null){
                    selectedItem = mAdapter.getSelected().split(" ")[1];
                    selectedItemType = mAdapter.getSelected().split(" ")[0];
                    inputs.remove(mAdapter.getSelected());
                    if (selectedItemType.equals("(field)")){
                        fields.remove(selectedItem);
                        fieldList.remove(selectedItem);
                    } else if (selectedItemType.equals("(doc)")){
                        docs.remove(selectedItem);
                        docList.remove(selectedItem);
                    }
                    mAdapter.setFields(inputs);
                } else {
                    Toast.makeText(EditServiceActivity.this, "Error: No field selected", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fields.size() == 0){
                    Toast.makeText(EditServiceActivity.this, "Error: Service must have fields", Toast.LENGTH_SHORT).show();
                } else if (title == null){
                    Toast.makeText(EditServiceActivity.this, "Error: Service must have a title", Toast.LENGTH_SHORT).show();
                } else {
                    titleId = title.trim().replace(" ", "_").toLowerCase();
                    if (!title.equals(oldTitle)) {
                        //if the title changed it deleted the service with the old title then makes new service with corresponding id
                        String oldTitleId = oldTitle.trim().replace(" ", "_").toLowerCase();
                        fStore.collection("services").document(oldTitleId).delete();
                    }
                    Service service = new Service(title, price, fields, docs);
                    fStore.collection("services").document(titleId).set(service);
                    Toast.makeText(EditServiceActivity.this, "Service Created!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditServiceActivity.this, ServiceListActivity.class);
                    Bundle extras = new Bundle();
                    extras.putString("ROLE", "admin");
                    intent.putExtras(extras);
                    startActivity(intent);
                }
            }
        });
    }
}