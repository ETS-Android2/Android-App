package com.example.novigrad.Service;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.novigrad.R;
import com.example.novigrad.Users.Customer;
import com.example.novigrad.Users.Employee;

import java.util.Map;

public class CustomerViewBranchActivity extends AppCompatActivity {
    Button servicesBtn, rateBtn;
    TextView sundayOpen, sundayClose, mondayOpen, mondayClose, tuesdayOpen, tuesdayClose, wednesdayOpen, wednesdayClose
            , thursdayOpen, thursdayClose, fridayOpen, fridayClose, saturdayOpen, saturdayClose, branchAddress, branchPhone;
    EditText comment;
    RadioButton oneStar, twoStars, threeStars, fourStars, fiveStars;
    RadioGroup ratingGroup;

    private Employee employee;
    private Customer user;
    private int rating;
    private String address, phone;
    private Map<String, Map<String, String>> hours;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view_branch);

        final Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        employee = (Employee) extras.getSerializable("employee");
        user = (Customer) extras.getSerializable("customer");
        address = employee.addressToString();
        phone = employee.branchPhone();
        hours = employee.branchHours();

        servicesBtn = findViewById(R.id.servicesBtn);
        rateBtn = findViewById(R.id.rateBtn);

        comment = findViewById(R.id.comment);

        sundayOpen = findViewById(R.id.sundayOpen);
        mondayOpen = findViewById(R.id.mondayOpen);
        tuesdayOpen = findViewById(R.id.tuesdayOpen);
        wednesdayOpen = findViewById(R.id.wednesdayOpen);
        thursdayOpen = findViewById(R.id.thursdayOpen);
        fridayOpen = findViewById(R.id.fridayOpen);
        saturdayOpen = findViewById(R.id.saturdayOpen);

        sundayClose = findViewById(R.id.sundayClose);
        mondayClose = findViewById(R.id.mondayClose);
        tuesdayClose = findViewById(R.id.tuesdayClose);
        wednesdayClose = findViewById(R.id.wednesdayClose);
        thursdayClose = findViewById(R.id.thursdayClose);
        fridayClose = findViewById(R.id.fridayClose);
        saturdayClose = findViewById(R.id.saturdayClose);

        branchAddress = findViewById(R.id.branchAddress);
        branchPhone = findViewById(R.id.branchPhone);

        oneStar = findViewById(R.id.oneStar);
        twoStars = findViewById(R.id.twoStars);
        threeStars = findViewById(R.id.threeStars);
        fourStars = findViewById(R.id.fourStars);
        fiveStars = findViewById(R.id.fiveStars);

        ratingGroup = findViewById(R.id.ratingGroup);

        // Set Text
        branchPhone.setText(phone);
        branchAddress.setText(address);

        sundayOpen.setText(hours.get("Sunday").get("openingHours"));
        mondayOpen.setText(hours.get("Monday").get("openingHours"));
        tuesdayOpen.setText(hours.get("Tuesday").get("openingHours"));
        wednesdayOpen.setText(hours.get("Wednesday").get("openingHours"));
        thursdayOpen.setText(hours.get("Thursday").get("openingHours"));
        fridayOpen.setText(hours.get("Friday").get("openingHours"));
        saturdayOpen.setText(hours.get("Saturday").get("openingHours"));

        sundayClose.setText(hours.get("Sunday").get("closingHours"));
        mondayClose.setText(hours.get("Monday").get("closingHours"));
        tuesdayClose.setText(hours.get("Tuesday").get("closingHours"));
        wednesdayClose.setText(hours.get("Wednesday").get("closingHours"));
        thursdayClose.setText(hours.get("Thursday").get("closingHours"));
        fridayClose.setText(hours.get("Friday").get("closingHours"));
        saturdayClose.setText(hours.get("Saturday").get("closingHours"));


        // On-Clicks
        // sends rating to branch
        rateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = true;
                if (oneStar.isChecked()){
                    rating = 1;

                } else if (twoStars.isChecked()){
                    rating = 2;

                } else if (threeStars.isChecked()){
                    rating = 3;

                } else if (fourStars.isChecked()){
                    rating = 4;

                } else if (fiveStars.isChecked()){
                    rating = 5;

                } else {
                    // if no rating is selected
                    flag = false;
                    Toast.makeText(CustomerViewBranchActivity.this, "Please choose a rating for this branch", Toast.LENGTH_SHORT).show();
                }
                if (comment.getText().toString().equals("")){
                    flag = false;
                    Toast.makeText(CustomerViewBranchActivity.this, "Error: please enter a comment", Toast.LENGTH_SHORT).show();
                }
                if (flag){
                    user.rateBranch(getApplicationContext(), employee, rating, comment.getText().toString());
                    comment.setText("");
                    oneStar.setChecked(false);
                    twoStars.setChecked(false);
                    threeStars.setChecked(false);
                    fourStars.setChecked(false);
                    fiveStars.setChecked(false);
                    Toast.makeText(CustomerViewBranchActivity.this, "Rating sent to branch", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // goes to CustomerServiceList where customers can submit service form
        servicesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.passUser(CustomerViewBranchActivity.this, CustomerServiceList.class, employee);
            }
        });

    }
}
