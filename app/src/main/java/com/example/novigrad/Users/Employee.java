package com.example.novigrad.Users;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

//METHODS NOT IMPLEMENTED FOR THIS DELIVERABLE
// --> not necessary according to deliverable specifications for user account creations
public class Employee extends User implements Serializable {

        private Branch myBranch;

        //public Employee(){}

        public Employee(){
                super();
                //myBranch = new Branch();
        }

        public Employee(String firstName, String lastName, String email, String password){
                super(firstName, lastName, email, "employee", password);
                //myBranch = new Branch(getUserID());
        }

        public void setUserMap(Context someContext){
                super.setUserMap();
                if (myBranch != null){
                        userMap.put("myBranch", myBranch);
                }
                updateDoc(someContext);
        }

        /* ------------------------------------- SECTION: Branch ------------------------------------- */

        public Branch getMyBranch(){ return this.myBranch; }
        public Map<String, Map<String, String>> branchHours(){ return myBranch.getHours(); }
        public Map<String, String> branchAddress(){ return myBranch.getAddress(); }
        public String branchPhone(){ return myBranch.getPhoneNumber(); }

        public boolean hasBranch(){ return (myBranch != null); }

        //pre-condition: all fields are validated prior to calling this method
        public void createMyBranch(final Context someContext, final Map<String, Object> mapToBranch) throws NullPointerException{ //throws so exception can be handled in a try except where called
                //best case, only changing hours
                if (hasBranch() && ((mapToBranch.get("address") != null) || (mapToBranch.get("phoneNumber") != null))){ //otherwise need to compare all the things
                        if(mapToBranch.containsKey("hours")){
                                myBranch.setHours((Map<String, Map<String, String>>) mapToBranch.get("hours"));
                                setUserMap(someContext);
                                return;
                        }
                }



                //worst case, if mapToBranch has address or phoneNumber, we need to compare with every other user

                FirebaseFirestore fReference = FirebaseFirestore.getInstance();
                CollectionReference collection = fReference.collection("users");

                Task<QuerySnapshot> querySnapshotTask = collection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                List<DocumentSnapshot> documents = task.getResult().getDocuments();
                                if (documents.isEmpty()) {
                                        return;
                                }

                                boolean exists = false;
                                for (DocumentSnapshot doc : documents) {
                                        String someROLE = doc.getString("ROLE");
                                        if (!someROLE.equals("employee")) {
                                                continue;
                                        }
                                        Employee someUser = doc.toObject(Employee.class);
                                        //System.out.println(someUser.toString());



                                        if (someUser.toString().equals(this.toString())) {
                                                continue;
                                        }

                                        if (someUser.getMyBranch() == null) {

                                        }

                                        if (someUser.toString().equals(this.toString())) {
                                                continue;
                                        }

                                        if (!someUser.hasBranch() || someUser.branchAddress() == null || someUser.branchPhone() == null){
                                                continue;
                                        }



                                        if (branchAlreadyExists(mapToBranch, someUser)){
                                                Toast.makeText(someContext, "Branch with that address or phone number already exists.", Toast.LENGTH_SHORT).show();
                                                return;
                                        }


                                        Map<String, String> addressMap = null;
                                        if(mapToBranch.containsKey("address")){
                                                addressMap = (Map<String, String>) mapToBranch.get("address");
                                                System.out.println("addressMap: " + addressMap.toString());

                                        }

                                        String inputPhoneNumber = null;
                                        if(mapToBranch.containsKey("phoneNumber")){
                                                inputPhoneNumber = (String) mapToBranch.get("phoneNumber");
                                                System.out.println("PhoneNumber: " + inputPhoneNumber);
                                        }
                                }
                                if(!hasBranch()) {
                                        System.out.println("final: " + mapToBranch);
                                        mapToBranch.put("branchID", getUserID());
                                        myBranch = new Branch(mapToBranch);
                                        Toast.makeText(someContext, "Branch successfully created.", Toast.LENGTH_SHORT).show();
                                } else {
                                        myBranch.mapToBranch(mapToBranch);
                                        Toast.makeText(someContext, "Branch updated.", Toast.LENGTH_SHORT).show();
                                }
                                setUserMap(someContext);
                                return;
                        }


                });


        }

        private boolean branchAlreadyExists(Map<String, Object> mapToBranch, Employee otherEmployee){
                Map<String, String> addressMap = (Map<String, String>) mapToBranch.get("address");
                String inputPhoneNumber = (String) mapToBranch.get("phoneNumber");

                if (addressMap == null || inputPhoneNumber == null || inputPhoneNumber.equals(null)){
                        return false;
                }

                if (!otherEmployee.hasBranch()){
                        return false;
                } else {
                        if (otherEmployee.branchAddress().equals(addressMap) || otherEmployee.branchPhone().equals(inputPhoneNumber)){
                                return true;
                        }
                }
                return false;
        }




        public Map<String, Service> getServicesOffered(){
                return myBranch.getServicesOffered();
        }

        public List<Service> getPendingRequests(){
                return myBranch.getPendingRequests();
        }

        public void addRating(Context someContext, String customerID, int someStars, String comment){
                myBranch.addRating(customerID, someStars, comment);
                setUserMap(someContext);
        }

        //public double calculateRating(){
//                return myBranch.calculateRating();
        //      }



        public String addressToString(){

                if (myBranch.address == null || myBranch.address.equals(null) || myBranch.address.isEmpty()){
                        return null;
                }
                StringBuilder sb = new StringBuilder();
                sb.append(myBranch.address.get("unitNumber") + " - " + myBranch.address.get("streetNumber") + ", " + myBranch.address.get("streetName") + ",\n");
                sb.append(myBranch.address.get("city") + ", " + myBranch.address.get("province") + ", " + myBranch.address.get("postalCode") + ",\n");
                sb.append(myBranch.address.get("country"));
                return sb.toString();
        }

        private String addressToString(Map<String, Object> addressMap){
                if (addressMap == null || addressMap.equals(null) || addressMap.isEmpty()){
                        return null;
                }
                StringBuilder sb = new StringBuilder();
                sb.append(addressMap.get("unitNumber") + " - " + addressMap.get("streetNumber") + " " + addressMap.get("streetName") + ",\n");
                sb.append(addressMap.get("city") + " " + addressMap.get("province") + " " + addressMap.get("postalCode") + ",\n");
                sb.append(addressMap.get("country"));
                return sb.toString();
        }




        /* ------------------------------------- END SECTION: Branch ------------------------------------- */


        /* ------------------------------------- SECTION: Branch Services  ------------------------------------- */

        public void addServiceOffered(final Context someContext, final String serviceTitle) throws NullPointerException{ //throws so exception can be handled in a try except where called
                FirebaseFirestore fReference = FirebaseFirestore.getInstance();
                CollectionReference collection = fReference.collection("services");

                Task<QuerySnapshot> querySnapshotTask = collection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                List<DocumentSnapshot> documents = task.getResult().getDocuments();
                                if (documents.isEmpty()) {
                                        return;
                                }
                                for (DocumentSnapshot doc : documents) {
                                        Service someService = doc.toObject(Service.class);
                                        System.out.println(someService.toString());
                                        if(someService != null || !someService.equals(null)){
                                                if(someService.getTitle().equals(serviceTitle)){
                                                        myBranch.addServiceOffered(someService);
                                                        setUserMap(someContext);
                                                }
                                        }
                                }

                                System.out.println("Done UPDATING SERVICES");
                        }
                });
        }

        public void addServiceOffered(final Context someContext, final Service newService) throws NullPointerException{ //throws so exception can be handled in a try except where called
                myBranch.addServiceOffered(newService);
                setUserMap(someContext);
        }

        public void removeServiceOffered(final Context someContext, final String serviceTitle) throws NullPointerException{ //throws so exception can be handled in a try except where called
                myBranch.removeServiceOffered(serviceTitle);
                setUserMap(someContext);
        }

        /* ------------------------------------- END SECTION: Branch Services ------------------------------------- */

        /* ------------------------------------- SECTION: Service Request Queue ------------------------------------- */

        //Adds a customer's service request to the branch review queue - FIFO
        public void enqueueServiceRequest(final Context someContext, final Service someServiceRequest) throws NullPointerException{
                myBranch.enqueueServiceRequest(someServiceRequest);
                setUserMap(someContext);
        }

        //Get the next request that needs to be reviewed: queue is FIFO
        public Service nextServiceRequest(){
                return myBranch.nextServiceRequest();
        }

        public void approveServiceRequest(final Context someContext, final Service serviceRequest){
                FirebaseFirestore fReference = FirebaseFirestore.getInstance();
                CollectionReference collection = fReference.collection("users");

                //final Service serviceRequest = myBranch.nextServiceRequest();
                System.out.println("SERVICE REQUEST: " + serviceRequest);
                final String customerID = serviceRequest.getPurchaseID();

                myBranch.approveServiceRequest(serviceRequest);

                Task<QuerySnapshot> querySnapshotTask = collection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                List<DocumentSnapshot> documents = task.getResult().getDocuments();
                                if (documents.isEmpty()) {
                                        return;
                                }

                                //for-loop for testing purposes
                                for (DocumentSnapshot doc : documents) {

                                        System.out.println("\nDocument snapshot: " + doc.getString("ROLE"));
                                        String someROLE = doc.getString("ROLE");
                                        System.out.println("ROLE IS: " + someROLE);

                                        System.out.println(doc.get("ROLE"));
                                        if (!someROLE.equals("customer")){
                                                continue;
                                        }
                                        Customer customer= doc.toObject(Customer.class);
                                        System.out.println(customer.toString());

                                        System.out.println("customerID: " + customer.getUserID());
                                        System.out.println("requestID: " + customerID);
                                        System.out.println(customer.getUserID().equals(customerID));
                                        if (customer.getUserID().equals(customerID)){
                                                customer.addServiceForm(someContext, serviceRequest);
                                                rejectServiceRequest(someContext, serviceRequest);
                                                setUserMap(someContext);
                                                return;
                                        }

                                        //If arrived here, customer document no longer exists for a pending request:

                                }

                        }
                });
        }

        //Rejects service request
        public void rejectServiceRequest(final Context someContext, Service someService) {
                myBranch.rejectServiceRequest(someService);
                setUserMap(someContext);
        }

        public String branchRating(){
                if(this.hasBranch()){
                       if(  ((Map<String, Object>)this.myBranch.rating.get("total")).get("totalRatings") == Long.valueOf(0)){
                               return "None";
                       }
                        return myBranch.branchRating().toString();
                }
                return "None";
        }

        protected static class Branch implements Serializable {

                private Map<String, String> address;
                private String phoneNumber;
                private String branchID;
                private Map<String, Map<String, String>> hours;
                private Map<String, Service> servicesOffered;
                private List<Service> pendingRequests;

                private Map<String, Object> rating;




                public Branch(){
                }

                public Branch(Map<String, Object> branchToMap){
                        mapToBranch(branchToMap);

                }


                public void mapToBranch(Map<String, Object> branchToMap){
                        if (branchToMap == null || branchToMap.equals(null) || branchToMap.isEmpty()){ //throw exception
                                return;
                        }


                        if (branchToMap.get("address") != null) {
                                setAddress((Map<String, String>) branchToMap.get("address")); //need to cast since value type in branchToMap is Object
                        }

                        if (branchToMap.get("phoneNumber") != null){
                                System.out.println("inputted phoneNumber: " + (String) branchToMap.get("phoneNumber"));
                                setPhoneNumber((String) branchToMap.get("phoneNumber"));
                                System.out.println("set phonNumber: " + getPhoneNumber());
                        }

                        if (branchToMap.get("branchID") != null){
                                setBranchID((String) branchToMap.get("branchID"));
                        }

                        if (branchToMap.get("servicesOffered") != null){
                                setServicesOffered((Map<String, Service>) branchToMap.get("servicesOffered"));
                        } else {
                                this.servicesOffered = new HashMap<>();
                        }

                        if (branchToMap.get("pendingRequests") != null){
                                setPendingRequests((List<Service>) branchToMap.get("pendingRequests"));
                        } else {
                                this.pendingRequests = new LinkedList<>();
                        }

                        if (branchToMap.get("hours") != null){
                                setHours((Map<String, Map<String, String>>) branchToMap.get("hours"));
                        }

                        if (branchToMap.get("rating") != null) { //should never be true when creating a branch
                                setRating((Map<String, Object>) branchToMap.get("rating"));
                        } else {
                                //this.rating = new HashMap<>();
                                Map<String,Object> rating = new HashMap<>();

                                Map<String, Integer> total = new HashMap<>();
                                total.put("totalStars", 0);
                                total.put("totalRatings", 0);
                                total.put("totalScore", 0);

                                Map<String, Map<String, Object>> history = new HashMap<>();
                                //history.put("userRatings", new HashMap<String, String>());
                                //history.get("userRatings").put("test", "something");

                                Map<String, Object> all = new HashMap<>();
                                all.put("total", total);
                                all.put("history", history);

                                rating.put("ratings", all);

                                setRating(all);


                        }
                }

                /* ---- GETTERS ---- */
                public Map<String, String> getAddress(){ return this.address; } //change
                public String getPhoneNumber(){ return this.phoneNumber; }
                public String getBranchID(){ return this.branchID; }
                public Map<String, Service> getServicesOffered(){ return this.servicesOffered; }
                public List<Service> getPendingRequests(){ return this.pendingRequests; }
                public Map<String, Map<String, String>> getHours(){ return this.hours; }

                //public int getTotalStars(){ return this.totalStars; }
                //public int getRatingCount(){ return this.ratingCount; }
                public Map<String, Object> getRating(){ return this.rating; }

                /* ---- SETTERS ---- */
                public void setAddress(Map<String, String> address){ this.address = address; }
                public void setPhoneNumber(String phoneNumber){ this.phoneNumber = phoneNumber; }
                public void setBranchID(String branchID){ this.branchID = branchID; }
                public void setServicesOffered(Map<String, Service> services){ this.servicesOffered = services; }
                public void setPendingRequests(List<Service> serviceRequests){ this.pendingRequests = serviceRequests; }
                public void setHours(Map<String, Map<String, String>> hours){ this.hours = hours; }

                //public void setTotalStars(int totalStars){ this.totalStars = totalStars; }
                //public void setRatingCount(int ratingCount){ this.ratingCount = ratingCount; }
                public void setRating(Map<String, Object> rating){ this.rating = rating; }


                //Will also replace any ServiceOffered with the same Title
                public void addServiceOffered (Service someService){
                        if (someService == null || someService.equals(null)){
                                return;
                        }

                        if (servicesOffered == null || servicesOffered.equals(null)){
                                servicesOffered = new HashMap<>();
                        }

                        String title = someService.getTitle();
                        servicesOffered.put(title, someService);
                }

                public void removeServiceOffered (String title){
                        servicesOffered.remove(title);
                }

                /* ------------------------------------- SERVICE REQUESTS ------------------------------------- */

                public void enqueueServiceRequest(Service someServiceRequest){
                        if (someServiceRequest == null || someServiceRequest.equals(null)){
                                return;
                        }

                        if (pendingRequests == null || pendingRequests.equals(null)){
                                pendingRequests = new LinkedList<>();
                        }

                        pendingRequests.add(someServiceRequest);
                }

                public Service nextServiceRequest(){
                        if (pendingRequests == null || pendingRequests.equals(null) || pendingRequests.isEmpty()){
                                return null;
                        }


                        return pendingRequests.get(0);//gets head of queue (first ServiceRequest in line)
                }

                public void nextServiceRequest(Service someServiceRequest){
                        if (pendingRequests == null || pendingRequests.equals(null)){
                                pendingRequests = new LinkedList<>();
                        }

                        pendingRequests.add(someServiceRequest);
                }


                private boolean rejectServiceRequest(Service someServiceRequest){ //removes first element in Service Request FIFO;
                        if (pendingRequests == null || pendingRequests.equals(null) || pendingRequests.isEmpty()){
                                return false;
                        }

                        return pendingRequests.remove(someServiceRequest);
                }


                public String approveServiceRequest(Service someServiceRequest){
                        if (someServiceRequest == null || someServiceRequest.equals(null)){
                                return null;
                        }

                        if (pendingRequests == null || pendingRequests.equals(null) || pendingRequests.isEmpty()){
                                return null;
                        }
                        String pID = (String) pendingRequests.get(0).getPurchaseID();
                        rejectServiceRequest(someServiceRequest);
                        return pID; //passes up to Employee class
                }



                /* ------------------------------------- END OF SERVICE REQUESTS ------------------------------------- */


                //Precondition: newHours is validated to ensure each key value is valid.
                public Map<String, Map<String, String>> initializeHours(){
                        Map<String, Map<String, String>> hours = new HashMap<>();
                        //List<String> defaultHours = new ArrayList<>(2);
                        hours.put("Sunday", initializeDailyHours());
                        hours.put("Monday", initializeDailyHours());
                        hours.put("Tuesday", initializeDailyHours());
                        hours.put("Wednesday", initializeDailyHours());
                        hours.put("Friday", initializeDailyHours());
                        hours.put("Saturday", initializeDailyHours());
                        return hours;
                }

                private Map<String, String> initializeDailyHours(){
                        Map<String, String> dailyHours = new HashMap<>();
                        dailyHours.put("openingHours", "Closed");
                        dailyHours.put("closingHours", "Closed");
                        return dailyHours;
                }

                public Map<String, Object> toMap(){
                        Map<String, Object> branchMap = new HashMap<>();
                        branchMap.put("address", address);
                        branchMap.put("phoneNumber", phoneNumber);
                        branchMap.put("branchID", branchID);
                        branchMap.put("services", servicesOffered);
                        //initializeHours();
                        branchMap.put("hours", hours);

                        //branchMap.put("rating", calculateRating());
                        return branchMap;
                }

                public void addAddress(String unitNumber, String streetNumber, String streetName, String city, String province, String postalCode, String country){
                        if (this.address == null){
                                this.address = new HashMap<>();
                        }
                        this.address.put("unitNumber", unitNumber);
                        this.address.put("streetNumber", streetNumber);
                        this.address.put("streetName", streetName);
                        this.address.put("city", city);
                        this.address.put("province", province);
                        this.address.put("postalCode", postalCode);
                        this.address.put("country", country);
                }



                public Long branchRating(){
                        System.out.println("TOTAL SCORE: " + (((Map<String, Object>) this.rating.get("total")).get("totalScore")));
                        return (Long)((Map<String, Object>) this.rating.get("total")).get("totalScore");

                }
                public void addRating(String customerID, int someStars, String comment){

                        Long starsAdded = Long.valueOf(0);
                        System.out.println("RATING MAP: " + ((Map<String, Long>)rating.get("total")).get("totalStars"));
                        Map<String, Long> total = ((Map<String, Long>) this.rating.get("total"));
                        Long totalStars = ((Map<String, Long>)rating.get("total")).get("totalStars");
                        Long totalRatings = ((Map<String, Long>)rating.get("total")).get("totalRatings");
                        Long totalScore = ((Map<String, Long>)rating.get("total")).get("totalScore");

                        System.out.println("RATING MAP: " + ((Map<String, Long>)rating.get("history")));
                        Map<String, Object> history = (Map<String, Object>)((Map<String, Object>) rating.get("history"));
                        if( history.containsKey(customerID)){ //customer has rated already
                                Long prevRating;
                                try{
                                        prevRating = Long.valueOf(((Map<String, Integer>)history.get(customerID)).get("rating"));
                                } catch (ClassCastException e){
                                        prevRating = Long.valueOf(((Map<String, Long>)history.get(customerID)).get("rating"));
                                }

                                String prevComment = (String) ((Map<String, Object>)history.get(customerID)).get("comment");
                                System.out.println("someStars: " + someStars);
                                starsAdded =  (Long)(someStars - prevRating);//can be negative when the customer lowers their previous rating
                                if(starsAdded == 0){
                                        if(comment != null || !comment.equals("") || !comment.equals(prevComment)){
                                                ((Map<String, Object>)history.get(customerID)).put("comment", comment);
                                        }
                                }
                        } else {
                                totalRatings++;
                                starsAdded = Long.valueOf(someStars);
                        }
                        totalStars += starsAdded;
                        Map<String, Object> submission = new HashMap<>();
                        submission.put("rating", someStars);
                        submission.put("comment", comment);
                        history.put(customerID, submission);
                        System.out.println("totalStars:" + totalStars);
                        System.out.println("totalRatings:" + totalRatings);
                        System.out.println("totalScore:" + totalScore);



                        total.put("totalStars", totalStars);
                        total.put("totalRatings", totalRatings);
                        total.put("totalScore", (long) calculateRating(totalStars, totalRatings));


                }




                private double calculateRating(double totalStars, double totalRatings){
                        if (totalStars <= 0 || totalRatings == 0){
                                return 0;
                        }
                        double totalAvg = ((double)totalStars/((double)totalRatings*5));
                        System.out.println("totalAvg: " + totalAvg);
                        //return a 0 to 5 value;
                        return totalAvg;
                }



        }



}
