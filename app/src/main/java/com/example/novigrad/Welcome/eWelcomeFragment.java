package com.example.novigrad.Welcome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.novigrad.Branch.BranchProfileActivity;
import com.example.novigrad.R;
import com.example.novigrad.Service.ServiceListActivity;
import com.example.novigrad.Service.ServiceRequestListActivity;
import com.example.novigrad.Users.Employee;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

public class eWelcomeFragment extends Fragment {
    Button servicesButton, serviceReqButton, branchProfileButton;
    Employee user;
    TextView firstName;

    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.user = (Employee) ((WelcomeActivity)getActivity()).getUser();

        firstName = view.findViewById(R.id.txt_getName);
        firstName.setText(user.getFirstName());

        servicesButton = view.findViewById(R.id.servicesBtn);
        serviceReqButton = view.findViewById(R.id.serviceReqBtn);
        branchProfileButton = view.findViewById(R.id.branchProBtn);


        servicesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.signIn(getActivity(), ServiceListActivity.class, user.getEmail(), user.getPassword());
            }
        });

        serviceReqButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
System.out.println("SERVICE REQUEST LIST: " + ServiceRequestListActivity.class);
                user.signIn(getActivity(), ServiceRequestListActivity.class, user.getEmail(), user.getPassword());
            }
        });

        branchProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.signIn(getActivity(), BranchProfileActivity.class, user.getEmail(), user.getPassword());
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_e_welcome, container, false);
    }
}