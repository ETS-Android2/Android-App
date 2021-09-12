package com.example.novigrad.Service;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.novigrad.R;
import com.example.novigrad.Users.Employee;
import com.example.novigrad.Users.Service;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class EmployeeServiceListFragment extends Fragment{

    Employee user;
    List<Service> myServices = new ArrayList<>();
    List<Service> allServices = new ArrayList<>();
    ServiceListAdapter msAdapter, asAdapter;
    Map<String, Service> myServicesMap;
    //as = All Services; ms = My Services;
    RecyclerView asRecycler, msRecycler;
    FirebaseFirestore fStore;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //firestore
        fStore = FirebaseFirestore.getInstance();

        //UI
        asRecycler = getView().findViewById(R.id.allServicesRecyclerView); //left list
        msRecycler = getView().findViewById(R.id.myServicesRecyclerView); //right list

        this.user = (Employee) ((ServiceListActivity) getActivity()).getUser();
        System.out.println(user.getFirstName());
        msAdapter = new ServiceListAdapter(myServices);
        msRecycler.setAdapter(msAdapter);

        myServicesMap = user.getServicesOffered();
        for (Map.Entry<String, Service> entry : myServicesMap.entrySet()) {
            String serviceName = entry.getValue().getTitle();
            System.out.println(serviceName);
            myServices.add(entry.getValue());
        }

        msAdapter.setOnServiceClickListener(new ServiceListAdapter.OnServiceClickListener() {
            @Override
            public void onServiceClick(View itemView, int position) {
                //WHEN MY SERVICE IS CLICKED, IT IS REMOVED FROM MYSERVICES
                String serviceTitle = myServices.get(position).getTitle();
                allServices.add(myServices.get(position));
                myServices.remove(myServices.get(position));
                user.removeServiceOffered(getContext(), serviceTitle);
                msAdapter.notifyDataSetChanged();
                asAdapter.notifyDataSetChanged();
            }
        });




        msRecycler.setAdapter(msAdapter);
        msRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        fStore.collection("services").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot services: task.getResult()){
                                Service service = services.toObject(Service.class);
                                //if (myServices.contains(services.getData())) {
                                    System.out.println("\n\nSERVICE: " + service);
                                    System.out.println("\nMY SERVICES: " + myServices);
                                System.out.println("DOES CONTAIN SERVICE: " + !myServicesMap.containsKey(service.getTitle()));
                                    if(!myServicesMap.containsKey(service.getTitle())){
                                        allServices.add(service);
                                    }

                                //}
                            }
                            System.out.println("all services appended");
                            asAdapter = new ServiceListAdapter(allServices);
                            System.out.println("asAdapter called");
                            asRecycler.setAdapter(asAdapter);
                            System.out.println("recycler called");
                            asRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                            System.out.println("layoutmanager called");
                            asAdapter.setOnServiceClickListener(new ServiceListAdapter.OnServiceClickListener() {
                                @Override
                                public void onServiceClick(View itemView, int position) {
                                    //WHEN ALL SERVICE IS CLICKED, IT IS ADDED TO MYSERVICES
                                    String serviceTitle = allServices.get(position).getTitle();
                                    myServices.add(allServices.get(position));
                                    allServices.remove(allServices.get(position));
                                    user.addServiceOffered(getContext(), serviceTitle);
                                    msAdapter.notifyDataSetChanged();
                                    asAdapter.notifyDataSetChanged();
                                    /*if (!myServices.contains(allServices.get(position))) {

                                    } else {
                                        Toast.makeText(getContext(), "Your branch already offers " + serviceTitle, Toast.LENGTH_SHORT).show();
                                    }

                                     */
                                }
                            });

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_employee_service_list, container, false);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

}

