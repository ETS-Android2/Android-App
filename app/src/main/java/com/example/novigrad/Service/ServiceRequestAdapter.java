package com.example.novigrad.Service;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.novigrad.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServiceRequestAdapter extends RecyclerView.Adapter<ServiceRequestAdapter.ServiceRequestViewHolder> {
    private ArrayList<String> fieldInputs, fields, fieldNames;

    //ViewHolder Class
    public class ServiceRequestViewHolder extends RecyclerView.ViewHolder{
        public TextView itemName,itemInput;
        public ServiceRequestViewHolder(@NonNull View v){
            super(v);
            itemName = v.findViewById(R.id.itemName);
            itemInput = v.findViewById(R.id.itemInput);
        }
    }

    public ServiceRequestAdapter(ArrayList<String> fields){
        this.fields = fields;
        fieldNames = new ArrayList<>();
        fieldInputs = new ArrayList<>();
        for (int i = 0; i < fields.size(); i++ ){
            String[] currentField = fields.get(i).split("\\.");
            fieldNames.add(currentField[0]);
            fieldInputs.add(currentField[1]);
        }
    }


    public ServiceRequestAdapter.ServiceRequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_request_item, parent, false);
        ServiceRequestAdapter.ServiceRequestViewHolder vh = new ServiceRequestAdapter.ServiceRequestViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceRequestAdapter.ServiceRequestViewHolder holder, int position) {
        holder.itemName.setText(fieldNames.get(position));
        holder.itemInput.setText(fieldInputs.get(position));
    }

    public int getItemCount(){return fieldInputs.size();}

}
