package com.example.novigrad.Users;

import android.content.Context;
import android.widget.Adapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.novigrad.UsersView.EmployeeListAdapter;
import com.example.novigrad.UsersView.UserListAdapter;
import com.example.novigrad.UsersView.UsersView;
import com.example.novigrad.Utils.Authenticate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Customer extends User implements Serializable {

        private Map<String, Service> serviceForms;

        private Map<String, Integer> branchRatingHistory;
        private List<User> employeeList;


        public Customer(){
                super();
                serviceForms = new HashMap<>();;
                branchRatingHistory = new HashMap<>();
                employeeList = new ArrayList<>();
        }

        public Customer(String firstName, String lastName, String email, String password){
                super(firstName, lastName,  email, "customer", password);
                serviceForms = new HashMap<>();
                branchRatingHistory = new HashMap<>();
                employeeList = new ArrayList<>();
        }

        public List<User> getEmployeeList(){ return this.employeeList; }
        public Map<String, Service> getServiceForms(){ return this.serviceForms; }
        public void setServiceForms(Map<String, Service> serviceForms){ this.serviceForms = serviceForms; }
        public Map<String, Integer> getBranchRatingHistory(){ return this.branchRatingHistory; }
        public void setBranchRatingHistory(Map<String, Integer> branchRatingHistory){ this.branchRatingHistory = branchRatingHistory; }

        protected void setUserMap(Context someContext){
                super.setUserMap();
                this.userMap.put("serviceForms", serviceForms);
                this.userMap.put("branchRatingHistory", branchRatingHistory);
                this.updateDoc(someContext);
                System.out.println("FINISHED UPDATING CUSTOMER");
        }


        public void addServiceForm(Context someContext, Service serviceForm){
                this.serviceForms.put(serviceForm.getTitle(), serviceForm);
                this.setUserMap(someContext);
        }

        //pre-condition: all fields are validated prior to calling this method
        public void searchBranch(final UserListAdapter someAdapter) throws NullPointerException{ //throws so exception can be handled in a try except where called

                FirebaseFirestore fReference = FirebaseFirestore.getInstance();
                CollectionReference collection = fReference.collection("users");

                Task<QuerySnapshot> querySnapshotTask = collection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                List<DocumentSnapshot> documents = task.getResult().getDocuments();
                                if (documents.isEmpty()) {
                                        return;
                                }

                                for (DocumentSnapshot doc : documents) {
                                        String someROLE = doc.getString("ROLE");
                                        if (!someROLE.equals("employee")) {
                                                continue;
                                        }
                                        Employee someUser = doc.toObject(Employee.class);



                                        if (someUser.getMyBranch() == null) {
                                                continue;
                                        }

                                        if (someUser.toString().equals(this.toString())) {
                                                continue;
                                        }

                                        if (!someUser.hasBranch()) {
                                                continue;
                                        }
                                                someAdapter.addUserToAdapter(someUser);
                                                employeeList.add(someUser);
                                        }

                                }
                });
        }


        //pre-condition: all fields are validated prior to calling this method
        public void searchBranch(final EmployeeListAdapter someAdapter) throws NullPointerException{ //throws so exception can be handled in a try except where called

                FirebaseFirestore fReference = FirebaseFirestore.getInstance();
                CollectionReference collection = fReference.collection("users");

                Task<QuerySnapshot> querySnapshotTask = collection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                List<DocumentSnapshot> documents = task.getResult().getDocuments();
                                if (documents.isEmpty()) {
                                        return;
                                }

                                for (DocumentSnapshot doc : documents) {
                                        String someROLE = doc.getString("ROLE");
                                        if (!someROLE.equals("employee")) {
                                                continue;
                                        }
                                        Employee someUser = doc.toObject(Employee.class);



                                        if (someUser.getMyBranch() == null) {
                                                continue;
                                        }

                                        if (someUser.toString().equals(this.toString())) {
                                                continue;
                                        }

                                        if (!someUser.hasBranch()) {
                                                continue;
                                        }
                                        someAdapter.addUserToAdapter(someUser);
                                        employeeList.add(someUser);
                                }

                        }
                });
        }

        public List<User> filterHours(Map<String, Map<String, String>> searchMap) {
                List<User> filteredList = new ArrayList<>();
                for (User employee : employeeList) {
                        Employee someUser = (Employee) employee;
                        if (searchMap.containsKey("hours") && !searchMap.get("hours").get("time").equals("all")) {
                                String day = searchMap.get("hours").get("day");
                                String between = searchMap.get("hours").get("time");
                                String openingHours = someUser.branchHours().get(day).get("openingHours");
                                String closingHours = someUser.branchHours().get(day).get("closingHours");
                                System.out.println("day: " + day);
                                System.out.println("between: " + between);
                                System.out.println("openingHours: " + openingHours);
                                System.out.println("closingHours: " + closingHours);
                                if (openingHours == null || closingHours == null || openingHours.equals("Closed") || closingHours.equals("Closed") || openingHours.equals("Closing") || closingHours.equals("Closing")) {
                                        continue;
                                }
                                System.out.println("open between: " + Authenticate.timeCompare(openingHours, between));
                                System.out.println("between close: " + Authenticate.timeCompare(between, closingHours));
                                if (Authenticate.timeCompare(openingHours, between, closingHours)) {
                                        filteredList.add(employee);
                                }
                        }
                }
                return filteredList;
        }

public List<User> filterServices(String title){
        List<User> filteredList = new ArrayList<>();
                for (User employee : employeeList){
                        Employee someUser = (Employee) employee;
                        //System.out.println("offered: " + ((Employee) employee).getServicesOffered().toString());
                        //System.out.println("offeredTitle: " + ((Employee) employee).getServicesOffered().get(title));
                        if (someUser.getServicesOffered().containsKey(title)){
                                filteredList.add(someUser);
                        }
                }
                return filteredList;
}

        public List<User> filterAddress(String title){



                String[] input = cleanAddressForSearch(title);
                List<User> filteredList = new ArrayList<>();
                for (User employee : employeeList){
                        Employee someUser = (Employee) employee;
                        //System.out.println("offered: " + ((Employee) employee).getServicesOffered().toString());
                        //System.out.println("offeredTitle: " + ((Employee) employee).getServicesOffered().get(title));
                        if (compareAddressFields(input, someUser.addressToString())){
                                filteredList.add(someUser);
                        }
                }
                return filteredList;
        }

        private String[] cleanAddressForSearch(String title){
                System.out.println(title);
                String clean = title.toLowerCase().replaceAll("[^a-zA-Z0-9]", " ");
                System.out.println(clean);

                String[] fields = clean.split(" ");
                return fields;
        }

        private boolean compareAddressFields(String[] input, Map<String, String> address){
                String unit = address.get("unitNumber");

                String streetNumber = address.get("streetNumber");
                String streetName = address.get("streetName").toLowerCase();
                System.out.println("StreetName: " + streetName);
                String city = address.get("city").toLowerCase();
                String province = address.get("province").toLowerCase();
                String postalCode = address.get("postalCode").toLowerCase();
                String country = address.get("country").toLowerCase();

                for(String field : input){
                        System.out.println("field: " + field );
                        if(field.equals(unit) ||
                        field.equals(streetNumber) ||
                        field.equals(streetName) ||
                        field.equals(city) ||
                        field.equals(province) ||
                        field.equals(postalCode) ||
                        field.equals(country)) {
                                return true; //will adjust later. DOn't plan to use one if statement in the end.
                        }
                }
                return false;
        }

        private boolean compareAddressFields(String[] input, String address){

                String[] addressFields = address.toLowerCase().replaceAll("[^a-zA-Z0-9]", " ").split(" ");

                for(String field : input){
                        for(String adField : addressFields){
                                if(field.equals(adField) && (!field.equals("") || !adField.equals(""))){
                                System.out.println("field: " + field);
                                System.out.println("adField: " + adField);
                                System.out.println(field + (".equals(" + adField + "): " + field.equals(adField)));

                                        return true;
                                }
                        }
                }
                return false;
        }

        public void purchaseService(){

        }

        public void submitServiceRequest(){

        }

        public void rateBranch(Context someContext, Employee employee, int rating, String comment){
                employee.addRating(someContext, getUserID(), rating, comment);
        }

        public String toString(){
                String str = super.toString();
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                //sb.append(serviceForms.toString());

                return sb.toString();
        }

}
