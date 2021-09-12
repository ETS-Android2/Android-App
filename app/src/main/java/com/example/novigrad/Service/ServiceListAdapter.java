package com.example.novigrad.Service;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.novigrad.R;
import com.example.novigrad.Users.Service;

import java.util.ArrayList;
import java.util.List;

public class ServiceListAdapter extends RecyclerView.Adapter<ServiceListAdapter.ViewHolder>{
    private OnServiceClickListener listener;

    public interface OnServiceClickListener {
        void onServiceClick(View itemView, int position);
    }

    public void setOnServiceClickListener(OnServiceClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView serviceTitleTextView;
        public TextView servicePrice;

        public ViewHolder(final View itemView){
            super(itemView);
            this.serviceTitleTextView = (TextView) itemView.findViewById(R.id.serviceTitle);
            this.servicePrice = (TextView) itemView.findViewById(R.id.servicePrice);

            serviceTitleTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onServiceClick(itemView, position);
                        }
                    }
                }
            });
        }
    }

    private List<Service> mServices;

    public ServiceListAdapter (List<Service> services) {
        mServices = services;
    }

    @NonNull
    @Override
    public ServiceListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View serviceView = inflater.inflate(R.layout.service, parent, false);

        ViewHolder viewHolder = new ViewHolder(serviceView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Service service = mServices.get(position);

        //set item views based on model desired... (changing info etc.)
        TextView textView = holder.serviceTitleTextView;
        TextView servicePrice = holder.servicePrice;
        textView.setText(service.getTitle());
        servicePrice.setText(" ");
        System.out.println(service.getTitle());
    }


    @Override
    public int getItemCount() {
        return mServices.size();
    }
}
