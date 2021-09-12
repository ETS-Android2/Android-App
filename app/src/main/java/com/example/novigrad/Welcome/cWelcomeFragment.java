package com.example.novigrad.Welcome;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.novigrad.Branch.SearchActivity;
import com.example.novigrad.MainActivity;
import com.example.novigrad.R;
import com.example.novigrad.Users.Customer;
import com.example.novigrad.Users.User;
import com.google.firebase.auth.FirebaseAuth;

public class cWelcomeFragment extends Fragment {
    Button servicesButton, usersButton, searchBtn;
    TextView firstName;
    User user;

    public User getUser(){
        return this.user;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user = (Customer) ((WelcomeActivity)getActivity()).getUser();

        firstName = view.findViewById(R.id.txt_getName);
        firstName.setText(user.getFirstName());

        servicesButton = view.findViewById(R.id.servicesBtn);
        usersButton = view.findViewById(R.id.usersViewBtn);

        searchBtn = view.findViewById(R.id.searchBtn);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //user.signIn(getContext(), SearchActivity.class, user.getEmail(), user.getPassword());
                user.passUser(getContext(), SearchActivity.class);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_c_welcome, container, false);
    }
}