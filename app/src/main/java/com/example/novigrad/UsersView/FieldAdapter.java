package com.example.novigrad.UsersView;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.novigrad.R;

import java.util.ArrayList;

public class FieldAdapter extends RecyclerView.Adapter<FieldAdapter.FieldViewHolder> {
    private ArrayList<String> fields;

    private static int checkedPosition = -1;

    //ViewHolder Class
    public class FieldViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public FieldViewHolder(@NonNull View v){
            super(v);
            textView = v.findViewById(R.id.field);
        }
        public void bind(final String field){
            if (checkedPosition == -1){
                textView.setBackgroundColor(Color.WHITE);
            } else {
                if (checkedPosition == getAdapterPosition()){
                    textView.setBackgroundColor(Color.LTGRAY);
                } else {
                    textView.setBackgroundColor(Color.WHITE);
                }
            }
            textView.setText(field);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    textView.setBackgroundColor(Color.LTGRAY);
                    if (checkedPosition != getAdapterPosition()){
                        notifyItemChanged(checkedPosition);
                        checkedPosition = getAdapterPosition();
                    }
                }
            });
        }
    }

    public FieldAdapter(ArrayList<String> fields){
        this.fields = fields;
    }

    public void setFields(ArrayList<String> fields){
        this.fields = new ArrayList<>();
        this.fields = fields;
        notifyDataSetChanged();
    }

    public FieldAdapter.FieldViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.field_item, parent, false);
        FieldViewHolder fvh = new FieldViewHolder(v);
        return fvh;
    }

    @Override
    public void onBindViewHolder(@NonNull FieldViewHolder holder, int position) {
        holder.bind(fields.get(position));
    }

    public int getItemCount(){return fields.size();}

    public String getSelected() {
        if (checkedPosition != -1){
            return fields.get(checkedPosition);
        }
        return null;
    }


}
