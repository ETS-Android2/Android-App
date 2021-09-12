package com.example.novigrad.Service;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.novigrad.R;
import com.example.novigrad.Users.Customer;
import com.example.novigrad.Users.Employee;
import com.example.novigrad.Users.Service;
import com.example.novigrad.Welcome.WelcomeActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Map;

public class CustomerServiceList extends AppCompatActivity {
    Button requestBtn;
    TextView selectedService;
    RecyclerView recyclerView;

    private CustomerServiceAdapter adapter;
    private Employee employee;
    private Customer user;
    private ArrayList<Service> services;
    private Map<String, Service> serviceMap;
    private Object[] serviceArray;
    private Service service;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_service_list);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        employee = (Employee) extras.getSerializable("employee");
        user = (Customer) extras.getSerializable("customer");
        serviceMap = employee.getServicesOffered();
        serviceArray = serviceMap.keySet().toArray();
        services = new ArrayList<>();

        for (int i = 0; i < serviceArray.length; i++){
            services.add(serviceMap.get(serviceArray[i]));
        }

        recyclerView = findViewById(R.id.cRecyclerView);
        selectedService = findViewById(R.id.selectedService);
        requestBtn = findViewById(R.id.requestBtn);

        adapter = new CustomerServiceAdapter(services);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //On Clicks
        requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service = adapter.getSelected();
                if (service == null){
                    Toast.makeText(CustomerServiceList.this, "Please select a service", Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(CustomerServiceList.this, ServiceFormActivity.class);
                    Bundle e = new Bundle();
                    e.putSerializable("customer", user);
                    e.putSerializable("employee", employee);
                    e.putString("ROLE", "customer");
                    e.putSerializable("service", service);
                    i.putExtras(e);
                    startActivity(i);
                }
            }
        });

    }

    public class CustomerServiceAdapter extends RecyclerView.Adapter<CustomerServiceAdapter.CustomerServiceViewHolder> {
        private int checkedPosition = -1;
        private ArrayList<Service> services;

        public CustomerServiceAdapter(ArrayList<Service> services){
            this.services = services;
        }

        public CustomerServiceAdapter.CustomerServiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.service, parent, false);
            CustomerServiceAdapter.CustomerServiceViewHolder fvh = new CustomerServiceAdapter.CustomerServiceViewHolder(v);
            return fvh;
        }

        @Override
        public void onBindViewHolder(@NonNull CustomerServiceAdapter.CustomerServiceViewHolder holder, int position) {
            holder.bind(services.get(position));
        }

        public int getItemCount(){return services.size();}

        public Service getSelected() {
            if (checkedPosition != -1){
                return services.get(checkedPosition);
            }
            return null;
        }

        public class CustomerServiceViewHolder extends RecyclerView.ViewHolder{
            private TextView serviceTitle, servicePrice;
            private LinearLayout serviceContainer;

            public CustomerServiceViewHolder(@NonNull View itemView){
                super(itemView);
                serviceTitle = itemView.findViewById(R.id.serviceTitle);
                servicePrice = itemView.findViewById(R.id.servicePrice);
                serviceContainer = itemView.findViewById(R.id.serviceContainer);
            }
            public void bind(final Service service){
                if (checkedPosition == -1){
                    serviceContainer.setBackgroundColor(Color.WHITE);
                } else {
                    if (checkedPosition == getAdapterPosition()){
                        serviceContainer.setBackgroundColor(Color.LTGRAY);
                    } else {
                        serviceContainer.setBackgroundColor(Color.WHITE);
                    }
                }
                serviceTitle.setText(service.getTitle());
                servicePrice.setText(service.getPrice());
                serviceTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        serviceContainer.setBackgroundColor(Color.LTGRAY);
                        if (checkedPosition != getAdapterPosition()){
                            notifyItemChanged(checkedPosition);
                            checkedPosition = getAdapterPosition();
                            selectedService.setText(service.getTitle());
                        }
                    }
                });
            }
        }
    }
}
