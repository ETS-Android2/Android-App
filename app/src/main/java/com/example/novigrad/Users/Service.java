package com.example.novigrad.Users;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//SET UP FOR FUTURE DELIVERABLE
public class Service implements Serializable {
    private String title;
    private String price;
    private Map<String, String> fields;
    private Map<String, Object> docs;
    private String purchaseID; //customer's ID

    public Service(){
        initializeBlankForm();
    }

    public Service(String customerID){ //for testing
        initializeBlankForm();
        setPurchaseID(customerID);
    }

    public Service(String title, String price, Map<String, String> fields, Map<String, Object> docs){
        initializeBlankForm();
        setTitle(title);
        setPrice(price);
        setFields(fields);
        setDocs(docs);
    }

    public Service(String title, String price, ArrayList<String> fields, ArrayList<String> docs){
        initializeBlankForm();
        setTitle(title);
        setPrice(price);

        for (String aField : fields){
            addNewField(aField);
        }

        for (String aDoc : docs) {
            addNewDocField(aDoc);
        }
    }

    public Service(String title, String price, Map<String, String> fields, Map<String, Object> docs, String customerID){
        initializeBlankForm();
        setTitle(title);
        setPrice(price);
        setFields(fields);
        setDocs(docs);
        setPurchaseID(customerID);
    }

    //For Serialization and populating adapters
    public String getTitle(){return this.title;}
    public String getPrice(){return this.price;}
    public Map<String, String> getFields(){return this.fields;}
    public Map<String, Object> getDocs(){return this.docs;}
    public String getPurchaseID(){ return this.purchaseID; }


    public void setTitle(String title){ this.title = title; } //should be pre-authenticated
    public void setPrice(String price ){this.price = price;} //should be pre-authenticated: $ X.XX
    public void setFields(Map<String, String> fields){this.fields = fields;} //for Serialization
    public void setDocs(Map<String, Object> doc){ this.docs = doc; } //for Serialization
    public void setPurchaseID(String purchaseID){ this.purchaseID = purchaseID; }

    //For creating new Service forms (to add to Branches)
    public void addNewField(String newField){ this.fields.put(newField, ""); } //replaces any field with the same name
    public void addNewDocField(String newDocField){ this.docs.put(newDocField, ""); } //replaces any doc with the same name

    //For customers filling our Service forms
    public void addFieldValue(String key, String value){ //for customers filling out form
        if (this.fields.containsKey(key)){
            this.fields.put(key, value);
        }
    }
    public void addDocValue(String key, String value){
        if (this.docs.containsKey(key)){
            this.docs.put(key, value);
        }
    }

    private void initializeBlankForm(){
        title = "";
        price = "";
        fields = new HashMap<>();
        docs = new HashMap<>();
        purchaseID = "";
    }

    public Map<String, Object> toMap(){
        Map<String, Object> serviceMap = new HashMap<>();
        serviceMap.put("title", title);
        serviceMap.put("price", price);
        serviceMap.put("fields", fields);
        serviceMap.put("docs", docs);
        serviceMap.put("purchaseID", purchaseID);
        return serviceMap;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();

        Map<String, Object> serviceToMap = this.toMap();
        for(Map.Entry<String, Object> entry : serviceToMap.entrySet()){
            sb.append(entry.getKey() + ": " + entry.getValue());
        }
        return sb.toString();
    }

    public void saveService(final Context someContext){
        final FirebaseAuth fAuth;
        fAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = fAuth.getCurrentUser();
        final FirebaseFirestore fStore = FirebaseFirestore.getInstance();
        fStore.collection("services").document(this.getTitle()).set(this.toMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    //Utils.travelActivity(fromContext, toContext);
                    Toast.makeText(someContext, "Service updated successfully.", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(someContext, "Could not update service successfully.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void selfDestruct(final Context someContext){
        FirebaseFirestore reference;
        CollectionReference collection;
        reference = FirebaseFirestore.getInstance();
        collection = reference.collection("services");
        collection.document(getTitle())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(someContext, "Service successfully deleted", Toast.LENGTH_SHORT).show();
                        System.out.println("Service successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(someContext, "Error deleting user.", Toast.LENGTH_SHORT).show();
                        System.out.println("Error deleting User!");
                    }
                });
    }


}