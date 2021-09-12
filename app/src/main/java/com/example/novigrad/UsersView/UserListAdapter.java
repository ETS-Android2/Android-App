package com.example.novigrad.UsersView;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.novigrad.R;
import com.example.novigrad.Users.User;

import java.util.ArrayList;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserListViewHolder> {

    private  List<User> userList = new ArrayList<>();
    ItemClickListener itemClickListener;
    private int selectedPos = RecyclerView.NO_POSITION;
    List<User> usersToDelete;

    private SparseBooleanArray selectedItems = new SparseBooleanArray();

    public List<User> getUsersToDelete(){
        return usersToDelete;
    }

    public UserListAdapter(){
        usersToDelete = new ArrayList<>();
    }

    public UserListAdapter(List<User> userList){
        this.userList.addAll(userList);
        usersToDelete = new ArrayList<>();

    }

    public void initializeAdapterList(){
        this.userList.clear();
    }

    public void addUserToAdapter(User user){
        userList.add(user);
        notifyDataSetChanged();
    }

    public void removeUserFromAdapter(User user){
        userList.remove(user);
        notifyDataSetChanged();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void updateAdapter(List<User> userList){
        this.userList.clear();
        for (User user : userList){
            this.userList.add(user);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.user_list_item, parent, false);

        return new UserListViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull final UserListViewHolder holder, final int position) {
        final User user = userList.get(position);
        System.out.println("user IN LIST: " + user.toString());
        System.out.println("userList: " + userList);
        System.out.println("pos in userList: " + userList.get(position));
        holder.roleView.setText(user.getROLE());
        holder.firstNameView.setText(user.getFirstName());
        holder.lastNameView.setText(user.getLastName());
        holder.emailView.setText(user.getEmail());
        holder.itemView.setSelected(selectedPos == position);
        //dynamic fields for recycler
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.itemClick(user);
            }
        });


    }


    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static  class UserListViewHolder extends  RecyclerView.ViewHolder{

        CheckBox checkBox;
        TextView roleView;
        TextView firstNameView;
        TextView lastNameView;
        TextView emailView;
        LinearLayout container;

        public UserListViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
            roleView = itemView.findViewById(R.id.roleView);
            firstNameView = itemView.findViewById(R.id.firstNameView);
            lastNameView = itemView.findViewById(R.id.lastNameView);
            emailView = itemView.findViewById(R.id.emailView);
            container = itemView.findViewById(R.id.container);


        }


    }
}
