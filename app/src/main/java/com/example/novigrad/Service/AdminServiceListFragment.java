package com.example.novigrad.Service;

import android.content.Intent;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.novigrad.R;
import com.example.novigrad.Users.Service;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;


public class AdminServiceListFragment extends Fragment {

    Button deleteBtn, viewBtn, editBtn, createBtn;
    TextView viewSelected;

    private RecyclerView mRecyclerView;
    private FirestoreRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FirebaseFirestore fStore;
    private ArrayList<Service> services;
    private OnItemClickListener listener;
    private String selectedServiceTitle;
    private Service selectedService;

    public void setServices(ArrayList<Service> services) {
        this.services = new ArrayList<>();
        this.services = services;
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        deleteBtn = view.findViewById(R.id.deleteBtn);
        viewBtn = view.findViewById(R.id.viewBtn);
        editBtn = view.findViewById(R.id.editBtn);
        createBtn = view.findViewById(R.id.createBtn);
        viewSelected = view.findViewById(R.id.selectedService);

        fStore = FirebaseFirestore.getInstance();
        mRecyclerView = getView().findViewById(R.id.recyclerViewServices);

        // Query
        Query query = fStore.collection("services");

        // Recycler Options
        FirestoreRecyclerOptions<Service> options = new FirestoreRecyclerOptions.Builder<Service>().setQuery(query, Service.class).build();

        //OnClicks
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //db action to delete service
                if (selectedServiceTitle != null){
                    String serviceId = selectedServiceTitle.trim().replace(" ", "_").toLowerCase();
                    fStore.collection("services").document(serviceId).delete();
                    mAdapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "service successfully deleted", Toast.LENGTH_SHORT).show();
                    selectedServiceTitle=null;
                    mAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), "Error: no service selected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigate to VIEW SERVICE screen (how a customer will see the form)
                if (selectedService != null){
                    Intent intent = new Intent(getActivity(), ServiceFormActivity.class);
                    Bundle extras = new Bundle();
                    extras.putSerializable("service", selectedService);
                    extras.putString("ROLE", "admin");
                    intent.putExtras(extras);
                    startActivity(intent);
                }
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigate to edit service activity
                if (selectedService == null || selectedService.equals(null)){
                    return;
                }
                if (selectedServiceTitle != null){
                    Intent intent = new Intent(getActivity(), EditServiceActivity.class);
                    Bundle extras = new Bundle();
                    extras.putSerializable("service", selectedService);
                    intent.putExtras(extras);
                    startActivity(intent);
                }
            }
        });

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigate to create service activity
                Intent intent = new Intent(getActivity(), CreateServiceActivity.class);
                startActivity(intent);
            }
        });

        // Adapter
        mAdapter = new FirestoreRecyclerAdapter<Service, AdminServiceListFragment.AdminServiceViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull AdminServiceListFragment.AdminServiceViewHolder holder, int position, @NonNull Service model) {
                holder.serviceTitle.setText(model.getTitle());
                holder.servicePrice.setText("$" + model.getPrice());
            }

            @NonNull
            @Override
            public AdminServiceListFragment.AdminServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service, parent, false);
                return new AdminServiceViewHolder(view);
            }

        };

        mRecyclerView.setHasFixedSize(true); // May have to change this
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mAdapter);

        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(String serviceTitle, int position){
                //Toast.makeText(getActivity(), serviceTitle, Toast.LENGTH_SHORT).show(); //CHANGE THIS SO THAT IT ACCESSES A SERVICE OBJECT
                selectedServiceTitle = serviceTitle;
                viewSelected.setText(selectedServiceTitle);
                String titleId = serviceTitle.trim().replace(" ", "_").toLowerCase();
                DocumentReference docRef = fStore.collection("services").document(titleId);
                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        selectedService = documentSnapshot.toObject(Service.class);
                    }
                });
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_service_list, container, false);
    }

    //View Holder Class
     private class AdminServiceViewHolder extends RecyclerView.ViewHolder{
        private TextView serviceTitle, servicePrice;

        public AdminServiceViewHolder(@NonNull View itemView){
            super(itemView);

            serviceTitle = itemView.findViewById(R.id.serviceTitle);
            servicePrice = itemView.findViewById(R.id.servicePrice);

            serviceTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null){
                        listener.onItemClick(serviceTitle.getText().toString(), position);
                    }
;                }
            });

        }

    }

    public interface OnItemClickListener {
        void onItemClick(String serviceTitle, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAdapter.startListening();
    }
}