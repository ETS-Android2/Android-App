package com.example.novigrad.Service;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.novigrad.R;
import com.example.novigrad.Users.Service;
import com.example.novigrad.UsersView.FieldAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CreateServiceActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private FieldAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FirebaseFirestore fStore;
    private ArrayList<String> fields, docs, inputs;
    private String title;
    private String price;
    private String titleId;

    ImageButton mNameAddBtn, mPriceAddBtn, mInputAddBtn, mDocAddBtn, mDeleteFieldBtn;
    Button mCreateBtn;
    EditText mAddName, mAddPrice, mAddInput, mAddDoc;
    TextView mServiceTitle, mServicePrice;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_service);

        mNameAddBtn = findViewById(R.id.nameAddBtn);
        mPriceAddBtn = findViewById(R.id.priceAddBtn);
        mInputAddBtn = findViewById(R.id.inputAddBtn);
        mDocAddBtn = findViewById(R.id.docAddBtn);
        mCreateBtn = findViewById(R.id.createBtn);
        mDeleteFieldBtn = findViewById(R.id.deleteFieldBtn);
        mAddName = findViewById(R.id.addName);
        mAddPrice = findViewById(R.id.addPrice);
        mAddInput = findViewById(R.id.addInput);
        mAddDoc = findViewById(R.id.addDoc);
        mServiceTitle = findViewById(R.id.serviceTitle);
        mServicePrice = findViewById(R.id.servicePrice);

        fStore = FirebaseFirestore.getInstance();

        mRecyclerView = findViewById(R.id.createServiceRecyclerView);

        fields = new ArrayList<String>();
        docs = new ArrayList<String>();
        inputs = new ArrayList<String>();

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
                }
            }
        });
        mInputAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // adds field to the array list fields
                // check to see if the field is already there
                boolean flag = true;
                String inputField = mAddInput.getText().toString();
                for (int i=0; i<fields.size(); i++){
                    if (fields.get(i).equals(inputField)){
                        flag = false;
                    }
                }
                //
                if (!inputField.equals("") && flag){
                    fields.add(inputField);
                    inputs.add("(field) " + inputField);
                    mAdapter.notifyDataSetChanged();
                    mAddInput.setText("");
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
                for (int i=0; i<fields.size(); i++){
                    if (fields.get(i).equals(inputDoc)){
                        flag = false;
                    }
                }
                //
                if (!mAddDoc.getText().toString().equals("") && flag){
                    docs.add(inputDoc);
                    inputs.add("(doc) " + inputDoc);
                    mAdapter.notifyDataSetChanged();
                    mAddDoc.setText("");
                }
            }
        });
        mPriceAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // adds inputted price if it is not null
                String inputPrice = mAddPrice.getText().toString();
                if (!inputPrice.equals("")){
                    price = String.valueOf(Integer.parseInt(inputPrice));
                    mServicePrice.setText("$" + inputPrice);
                    mAddPrice.setText("");
                } else {
                    Toast.makeText(CreateServiceActivity.this, "Error: Enter a price", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mDeleteFieldBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedItem;
                String selectedItemType;
                if (mAdapter.getSelected() != null){
                    selectedItem = mAdapter.getSelected().split(" ")[1];
                    selectedItemType = mAdapter.getSelected().split(" ")[0];
                    inputs.remove(mAdapter.getSelected());
                    if (selectedItemType.equals("(field)")){
                        fields.remove(selectedItem);
                    } else if (selectedItemType.equals("(doc)")){
                        docs.remove(selectedItem);
                    }
                    mAdapter.setFields(inputs);
                } else {
                    Toast.makeText(CreateServiceActivity.this, "No field selected", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fields.size() == 0) {
                    Toast.makeText(CreateServiceActivity.this, "Error: Service must have fields", Toast.LENGTH_SHORT).show();
                } else if (title == null){
                    Toast.makeText(CreateServiceActivity.this, "Error: Service must have a title", Toast.LENGTH_SHORT).show();
                } else {
                    titleId = title.trim().replace(" ", "_").toLowerCase();
                    DocumentReference docRef = fStore.collection("services").document(titleId);
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()){
                                DocumentSnapshot document = task.getResult();
                                if(document.exists()){
                                    Toast.makeText(CreateServiceActivity.this, "Service with this title already exists", Toast.LENGTH_SHORT).show();
                                } else {
                                    Service service = new Service(title, price, fields, docs);
                                    fStore.collection("services").document(titleId).set(service);
                                    Toast.makeText(CreateServiceActivity.this, "Service Created!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(CreateServiceActivity.this, ServiceListActivity.class);
                                    Bundle extras = new Bundle();
                                    extras.putString("ROLE", "admin");
                                    intent.putExtras(extras);
                                    startActivity(intent);
                                }
                            }
                        }
                    });
                }
            }
        });
    }
}
