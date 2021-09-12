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

import com.example.novigrad.R;
import com.example.novigrad.Service.ServiceListActivity;
import com.example.novigrad.Users.Admin;
import com.example.novigrad.Users.User;
import com.example.novigrad.UsersView.UsersView;

public class aWelcomeFragment extends Fragment {
    Button servicesButton, usersButton;
    TextView firstName;
    User user;

    public User getUser(){
        return this.user;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.user = (Admin) ((WelcomeActivity)getActivity()).getUser();

        firstName = view.findViewById(R.id.txt_getName);
        firstName.setText(user.getFirstName());

        servicesButton = view.findViewById(R.id.servicesBtn);
        usersButton = view.findViewById(R.id.usersViewBtn);

        servicesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.passUser(getActivity(), ServiceListActivity.class);
            }
        });

        usersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = (Admin) ((WelcomeActivity)getActivity()).getUser(); //getting an object from the activity the fragment is in
                //https://zocada.com/using-intents-extras-pass-data-activities-android-beginners-guide/
                Intent intent = new Intent(view.getContext(), UsersView.class);
                Bundle extras = new Bundle();
                extras.putSerializable("user", user);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_a_welcome, container, false);
    }
}
