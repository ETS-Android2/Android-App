
package com.example.novigrad.Users;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.novigrad.Service.CreateServiceActivity;
import com.example.novigrad.Service.EditServiceActivity;
import com.example.novigrad.Service.ServiceFormActivity;
import com.example.novigrad.Service.ServiceListActivity;
import com.example.novigrad.Service.ServiceRequestListActivity;
import com.example.novigrad.Welcome.WelcomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class User implements Serializable {

    private String firstName;
    private String lastName;
    private String email;
    private String ROLE;
    Map<String, Object> userMap;
    private String password;

    private String userID;
    private boolean signedIn;

    FirebaseFirestore reference;
    CollectionReference collection;

    List<Employee> employeeList;

    public User(){
    }

    public User(String firstName, String lastName, String email, String ROLE){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.ROLE = ROLE;
        setUserMap();
    }

    public User(String firstName, String lastName, String email, String ROLE, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.ROLE = ROLE;
        this.password = password;
        setUserMap();
    }

    public User getUser(){ return this; }
    public String getFirstName(){ //retrieved from database? <-- Rachel
        return firstName;
    }
    public String getLastName(){ //retrieved from database? <-- Rachel
        return lastName;
    }
    public String getEmail(){ //retrieved from database? <-- Rachel
        return email;
    }
    public String getROLE(){ //retrieved from database? encrypted? <-- Rachel
        return ROLE;
    }
    public String getUserID(){ return this.userID; }
    public String getPassword(){ return this.password;}
    public Map<String, Object> getUserMap(){ return this.userMap; }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void ROLE(String ROLE){
        this.ROLE = ROLE;
    }
    public void setUserID(String userID){ this.userID = userID; }
    public void setPassword(String password){ this.password = password; }

    protected void setUserMap(){

        if (userMap == null){
            userMap = new HashMap<>();
        }

        if (!userMap.isEmpty()){
            userMap.clear();
        }

        userMap.put("firstName", this.firstName.substring(0, 1).toUpperCase() + this.firstName.substring(1)); //capitalizing first-letter
        userMap.put("lastName", this.lastName.substring(0, 1).toUpperCase() + this.lastName.substring(1)); //capitalizing first-letter
        userMap.put("email", this.email);
        userMap.put("ROLE", this.ROLE);
        userMap.put("password", this.password);
        userMap.put("userID", this.userID);
    }

    public void passUser(Context fromContext, Class toContext){
        Intent intent = new Intent(fromContext, toContext);
        Bundle extras = new Bundle();
        extras.putString("ROLE", getROLE());
        extras.putSerializable(getROLE(), this);
        intent.putExtras(extras);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        fromContext.startActivity(intent);
        //return intent;
    }

    public void passUser(Context fromContext, Class toContext, User someUser){
        Intent intent = new Intent(fromContext, toContext);
        Bundle extras = new Bundle();
        extras.putString("ROLE", getROLE());
        extras.putSerializable(getROLE(), this);
        extras.putSerializable(someUser.getROLE(), someUser);
        intent.putExtras(extras);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        fromContext.startActivity(intent);
    }



    public void registerUser(final FirebaseAuth fAuth, final Context fromContext, final Class toContext){
        signOut();
        fAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            setUserID(fAuth.getCurrentUser().getUid());
                            setPassword(password);
                            setUserMap();
                            FirebaseFirestore fStore = FirebaseFirestore.getInstance();
                            fStore.collection("users").document(getUserID()).set(getUserMap());
                            passUser(fromContext, toContext);
                            Toast.makeText(fromContext, "Account Created!", Toast.LENGTH_SHORT).show();
                        } else {
                            System.out.println("Could not register user in fAuth.");
                            Toast.makeText(fromContext, "Invalid Registration", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static void signIn(final Context fromContext, final Class toContext, String email, String password){
        signOut();
        final FirebaseAuth fAuth;
        fAuth = FirebaseAuth.getInstance();

        final FirebaseFirestore fStore = FirebaseFirestore.getInstance();
        //authenticate the user, and bring them to welcome page
        fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //if authentication is successful, users are brought to the welcome page
                if(!task.isSuccessful()){
                    Toast.makeText(fromContext, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                } else {

                    final String docUserID = fAuth.getCurrentUser().getUid();
                    DocumentReference docRef = fStore.collection("users").document(docUserID);
                    docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            System.out.println("Document snapshot: " + documentSnapshot.getString("ROLE"));
                            String someROLE = documentSnapshot.getString("ROLE");
                            System.out.println("ROLE IS: " + someROLE);
                            User someUser;
                            if (someROLE.equals("admin")){
                                someROLE = "admin";
                                someUser = (Admin) documentSnapshot.toObject(Admin.class);
                                System.out.println("someUser: " + someUser.toString());
                                someUser.getEmail();
                                verifyLogin(someUser, docUserID, fromContext);
                                someUser.passUser(fromContext, toContext);

                            } else if (someROLE.equals("employee")) {
                                someROLE = "employee";
                                someUser = (Employee) documentSnapshot.toObject(Employee.class);
                                verifyLogin(someUser, docUserID, fromContext);
                                if(activityGuard(fromContext, toContext, (Employee) someUser)){
                                    someUser.passUser(fromContext, toContext);
                                }
                            } else {
                                someROLE = "customer";
                                someUser = (Customer) documentSnapshot.toObject(Customer.class);
                                verifyLogin(someUser, docUserID, fromContext);
                                someUser.passUser(fromContext, toContext);

                            }
                        }
                    });
                }
            }
        });
    }

    private static boolean activityGuard(Context fromContext, Class toContext, Employee someUser){
        System.out.println("toContext getName: " + toContext.getName());
        if(!someUser.hasBranch() &&
                (toContext.getName().equals("com.example.novigrad.Service.ServiceListActivity") ||
                        toContext.getName().equals("com.example.novigrad.Service.ServiceRequestListActivity"))){
            Toast.makeText(fromContext, "Employee must have a branch to access Services.", Toast.LENGTH_SHORT).show();
            return false;
        }

        //precondition: hasBranch
        if(someUser.hasBranch() && someUser.getPendingRequests().isEmpty() && toContext.getName().equals("com.example.novigrad.Service.ServiceRequestListActivity")){
            Toast.makeText(fromContext, "There are no pending service requests to review.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }




    private static boolean verifyLogin(User someUser, String docUserID, Context toContext){
        if (someUser.getUserID().equals(docUserID)){
            System.out.println("You've been successfully logged in!");
            //Toast.makeText(toContext, "Log in success", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(toContext, "Failed login", Toast.LENGTH_SHORT).show();
            System.out.println("this.userID: " + someUser.getUserID());
            System.out.println("docUserID: " + docUserID);
            return false;
        }
    }

    public static void signOut(){
        FirebaseAuth.getInstance().signOut();
    }


    public void updateDoc(final Context someContext){
        final FirebaseAuth fAuth;
        fAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = fAuth.getCurrentUser();
        final FirebaseFirestore fStore = FirebaseFirestore.getInstance();
        fStore.collection("users").document(this.getUserID()).set(getUserMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(someContext, "Updated user successfully.", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(someContext, "Could not update user successfully.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void selfDestruct(final Context someContext){
        deleteAuth(someContext);
        deleteDoc(someContext);
    }

    private void deleteAuth(final Context someContext){
        final FirebaseAuth fAuth;
        fAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = fAuth.getCurrentUser();
        final FirebaseFirestore fStore = FirebaseFirestore.getInstance();
        fAuth.signInWithEmailAndPassword(this.email, this.password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //if authentication is successful, users are brought to the welcome page
                if (!task.isSuccessful()) {
                    Toast.makeText(someContext, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                } else {

                    //should always be this user instance
                    FirebaseUser currentUser = fAuth.getCurrentUser();
                    currentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                System.out.println("User's AUTH successfully deleted");
                                //Toast.makeText(someContext, "User auth successfully deleted", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void deleteDoc(final Context someContext){
        String uID = getUserID();
        reference = FirebaseFirestore.getInstance();
        collection = reference.collection("users");
        collection.document(uID)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(someContext, "User successfully deleted", Toast.LENGTH_SHORT).show();
                        System.out.println("User doc successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(someContext, "Error deleting user.", Toast.LENGTH_SHORT).show();
                        System.out.println("Error deleting User!");
                    }
                });
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(firstName + "\n");
        sb.append(lastName + "\n");
        sb.append(email + "\n");
        sb.append(ROLE + "\n");
        sb.append(userID + "\n");
        return sb.toString();
    }




}