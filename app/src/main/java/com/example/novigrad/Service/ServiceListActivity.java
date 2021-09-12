package com.example.novigrad.Service;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.novigrad.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import com.example.novigrad.Users.Admin;
import com.example.novigrad.Users.Customer;
import com.example.novigrad.Users.Employee;
import com.example.novigrad.Users.User;

public class ServiceListActivity extends AppCompatActivity {
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    User user;
    String ROLE;

    public User getUser() {return this.user;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        //userId = fAuth.getCurrentUser().getUid();

        System.out.println("Extras == null" + (extras == null));
        if (extras != null){
            ROLE = extras.getString("ROLE");
            System.out.println("ROLE IS: " + ROLE);
            System.out.println(".equals" + ROLE.equals("employee"));
            System.out.println(".equals" + ROLE.equals("admin"));

            Bundle bundle = new Bundle();
            Fragment listFrag;
            switch(ROLE) {
                case ("admin"):
                    this.user = (Admin) extras.getSerializable(ROLE);
                    listFrag = new AdminServiceListFragment();
                    bundle.putString("ROLE", ROLE);
                    bundle.putSerializable(ROLE, user);
                    listFrag.setArguments(bundle);
                    setFragment(listFrag);
                    break;
                case ("employee"):
                    this.user = (Employee) extras.getSerializable(ROLE);
                    listFrag = new EmployeeServiceListFragment();
                    bundle.putString("ROLE", ROLE);
                    bundle.putSerializable(ROLE, user);
                    listFrag.setArguments(bundle);
                    setFragment(listFrag);


                    break;
            }
        }

        System.out.println("NO FRAGMENT MADE");
        //OLD ROLE GRABBER CODE (NON-USER-OBJECT VERSION)
//        final DocumentReference documentReference = fStore.collection("users").document(userId);
//        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
//                //Dynamic fragment rendering based on which ROLE is currently signed in
//
//                if (documentSnapshot.getString("ROLE").equals("admin")){
//
//                    setFragment(new AdminServiceListFragment());
//                } else if (documentSnapshot.getString("ROLE").equals("employee")){
//                    setFragment(new EmployeeServiceListFragment());
//                }
//            }
//
//        });



    }

    //method to control which fragment is displayed in the activity
    private void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //In this activity, the fragment is rendered inside a frameLayout widget
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();

    }
}