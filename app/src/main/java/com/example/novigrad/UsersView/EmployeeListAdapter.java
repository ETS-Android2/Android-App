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
import com.example.novigrad.Users.Employee;
import com.example.novigrad.Users.User;

import java.util.ArrayList;
import java.util.List;

public class EmployeeListAdapter extends RecyclerView.Adapter<EmployeeListAdapter.EmployeeListViewHolder>{

    private  List<User> userList = new ArrayList<>();
    ItemClickListener itemClickListener;
    private int selectedPos = RecyclerView.NO_POSITION;
    List<User> usersToDelete;

    private SparseBooleanArray selectedItems = new SparseBooleanArray();

    public List<User> getUsersToDelete(){
        return usersToDelete;
    }

    public EmployeeListAdapter(){
        usersToDelete = new ArrayList<>();
    }

    public EmployeeListAdapter(List<User> userList){
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
    public EmployeeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.employee_list_item, parent, false);

        return new EmployeeListViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull final EmployeeListViewHolder holder, final int position) {
        final User user = userList.get(position);
        //System.out.println("user IN LIST: " + user.toString());
        //System.out.println("userList: " + userList);
        //System.out.println("pos in userList: " + userList.get(position));
        //System.out.println("NAME!: " + ((Employee) user).branchAddress().get("streetNumber") + " " + ((Employee) user).branchAddress().get("streetName"));
        holder.branchNameView.setText(((Employee) user).branchAddress().get("streetNumber") + " " + ((Employee) user).branchAddress().get("streetName"));
        holder.phoneView.setText(((Employee) user).branchPhone());
        if (((Employee) user).hasBranch()){
            holder.ratingView.setText("Rating: " + ((Employee) user).branchRating() + "/5");
        } else {
            holder.ratingView.setText("Rating: None");
        }



        holder.itemView.setSelected(selectedPos == position);
        //dynamic fields for recycler
//        List<String> otherItems = user.otherItem;
//        for (String s :  otherItems) {
//            holder.container.addView(new TextView(s));
//        }

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

    public static  class EmployeeListViewHolder extends  RecyclerView.ViewHolder{

        CheckBox checkBox;
        TextView branchNameView;
        TextView phoneView;
        TextView ratingView;
        TextView rating;
        TextView lastNameView;
        TextView emailView;
        LinearLayout container;

        public EmployeeListViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
            branchNameView = itemView.findViewById(R.id.branchNameView);
            phoneView = itemView.findViewById(R.id.phoneView);
            ratingView = itemView.findViewById(R.id.ratingView);
            //address
            //hours
            //S: # - #; M


        }


    }
}
