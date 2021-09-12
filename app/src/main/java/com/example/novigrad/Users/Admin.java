package com.example.novigrad.Users;

import androidx.annotation.NonNull;

import com.example.novigrad.UsersView.UserListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

//METHODS NOT IMPLEMENTED FOR THIS DELIVERABLE
// --> not necessary according to deliverable specifications for user account creations
public class Admin extends User {

DocumentReference reference;

public List<User> usersList;
public List<User> customerList;
public List<User> employeeList;


//for logging in
public Admin(){
        super();
        //super("admin", "admin", "admin@novigrad.ca", "admin");
        usersList = new ArrayList<>();
        customerList = new ArrayList<>();
        employeeList = new ArrayList<>();
}

//for registering
public Admin(String firstName, String lastName, String username, String email){
        super("admin", "admin", "admin@novigrad.ca", "admin");
        usersList = new ArrayList<>();
        customerList = new ArrayList<>();
        employeeList = new ArrayList<>();
}

public List<User> getUsersList(){
        return usersList;
}
public List<User> getCustomerList(){
        return customerList;
}
public List<User> getEmployeeList(){
        return employeeList;
}


        public void initializeListOfUsers(final UserListAdapter userListAdapter) throws NullPointerException{ //throws so exception can be handled in a try except where called
                clearUserList();

                FirebaseFirestore fReference = FirebaseFirestore.getInstance();
                CollectionReference collection = fReference.collection("users");

                collection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                List<DocumentSnapshot> documents = task.getResult().getDocuments();
                                if (documents.isEmpty()){
                                        return;
                                }
                                for(DocumentSnapshot doc : documents) {

                                        String userRole = doc.get("ROLE").toString();
                                        if (!userRole.equals("admin")){
                                                boolean isCustomer = UserRole.valueOf(userRole).equals(UserRole.customer);
                                                User user = isCustomer ? doc.toObject(Customer.class) : doc.toObject(Employee.class);
                                                if (isCustomer){
                                                        userListAdapter.addUserToAdapter(user);
                                                }
                                                //decides which list to add to
                                                System.out.println("Adding user: " + user);
                                                putUserToList(user);
                                        }

                                }
                                //updateRecycler();
                                System.out.println("Done");
                        }
                });
        }

public void updateListOfUsers() throws NullPointerException{ //throws so exception can be handled in a try except where called
        clearUserList();

        FirebaseFirestore fReference = FirebaseFirestore.getInstance();
        CollectionReference collection = fReference.collection("users");

        collection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<DocumentSnapshot> documents = task.getResult().getDocuments();
                        if (documents.isEmpty()){
                                return;
                        }
                        for(DocumentSnapshot doc : documents) {

                                String userRole = doc.get("ROLE").toString();
                                if (!userRole.equals("admin")){
                                        boolean isCustomer = UserRole.valueOf(userRole).equals(UserRole.customer);
                                        User user = isCustomer ? doc.toObject(Customer.class) : doc.toObject(Employee.class);
                                        putUserToList(user);
                                }

                        }
                        //updateRecycler();
                        System.out.println("Done");
                }
        });
}

private void putUserToList(User user) {
        //Don't add any instance of admin to a list.
        if (user.getROLE().equals(UserRole.admin)){
                return;
        }

        if(user.getROLE().equals("customer")){
                customerList.add(user);
        } else {
                //is an employee
                employeeList.add(user);
        }
}

public void removeUserFromList(User user){
        if(user.getROLE().equals(UserRole.customer)){
                customerList.remove(user);
        } else {
                employeeList.remove(user);
        }

}

public void clearUserList(){
        usersList.clear();
        customerList.clear();
        employeeList.clear();
}



public void deleteAccount(){ //branch or customer accounts

}

public void createService(Object branch, double price){ //change object to Branch once created

}

}

