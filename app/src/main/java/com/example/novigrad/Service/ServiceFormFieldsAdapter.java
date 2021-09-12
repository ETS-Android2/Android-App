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

import java.util.ArrayList;
import java.util.HashMap;

public class ServiceFormFieldsAdapter extends RecyclerView.Adapter<ServiceFormFieldsAdapter.ServiceFormFieldsViewHolder> {
    private ArrayList<String> fields;

    private HashMap<String, String> fieldInputs = new HashMap<>(); // ArrayList of strings to store field inputs

    //ViewHolder Class
    public class ServiceFormFieldsViewHolder extends RecyclerView.ViewHolder{
        public TextView fieldName;
        public EditText fieldInput;
        public ServiceFormFieldsViewHolder(@NonNull View v){
            super(v);
            fieldName = v.findViewById(R.id.fieldName);
            fieldInput = v.findViewById(R.id.fieldInput);
            fieldInput.addTextChangedListener(new TextWatcher() {
                private int position;
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // no action
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // no action
                }

                @Override
                public void afterTextChanged(Editable s) {
                    fieldInputs.put(fields.get(getAdapterPosition()), fieldInput.getText().toString());
                }
            });
        }
    }

    public ServiceFormFieldsAdapter(ArrayList<String> fields){
        this.fields = fields;
    }


    public ServiceFormFieldsAdapter.ServiceFormFieldsViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_form_field, parent, false);
        ServiceFormFieldsViewHolder fvh = new ServiceFormFieldsViewHolder(v);
        return fvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceFormFieldsViewHolder holder, int position) {
        holder.fieldName.setText(fields.get(position));
    }

    public int getItemCount(){return fields.size();}

    public HashMap getFieldInputs() {
        return fieldInputs;
    }



}