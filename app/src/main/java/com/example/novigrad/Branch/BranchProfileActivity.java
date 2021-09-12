package com.example.novigrad.Branch;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.novigrad.R;
import com.example.novigrad.Users.Employee;
import com.example.novigrad.Utils.Authenticate;
import com.example.novigrad.Utils.Utils;

import java.util.HashMap;
import java.util.Map;


public class BranchProfileActivity extends AppCompatActivity implements AddressDialog.AddressDialogListener, NumDialog.NumDialogListener {


    //employee to transfer info to
    private Employee employee;

    //spinners for operation hours
    private Spinner oSSpinner;
    private Spinner cSSpinner;
    private Spinner oMSpinner;
    private Spinner cMSpinner;
    private Spinner oTSpinner;
    private Spinner cTSpinner;
    private Spinner oWSpinner;
    private Spinner cWSpinner;
    private Spinner oTHSpinner;
    private Spinner cTHSpinner;
    private Spinner oFSpinner;
    private Spinner cFSpinner;
    private Spinner oSASpinner;
    private Spinner cSASpinner;

    //selected string values of current spinner
    String closeSpin;
    String openSpin;



    //buttons
    private Button addressPromptBtn;
    private Button phoneBtn;
    private Button setHoursBtn;
    private Button saveBtn;

    Map<String, Object> addressMap;
    Map<String, Map<String, String>> hoursMap;
    Map<String, Map<String, String>> originalHoursMap;
    String phoneNumber;
    boolean validHours = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_profile);

        initializeActivity();

        oSSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //validateHours(oSSpinner, cSSpinner, "Sunday");
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
        cSSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //validateHours(oSSpinner, cSSpinner, "Sunday");
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });


        oMSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //validateHours(oMSpinner, cMSpinner, "Monday");
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
        cMSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //validateHours(oMSpinner, cMSpinner, "Monday");
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });


        oTSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //validateHours(oTSpinner, cTSpinner, "Tuesday");
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
        cTSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //validateHours(oTSpinner, cTSpinner, "Tuesday");
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });


        oWSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //validateHours(oWSpinner, cWSpinner, "Wednesday");
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
        cWSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //validateHours(oWSpinner, cWSpinner, "Wednesday");
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });


        oTHSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //validateHours(oTHSpinner, cTHSpinner, "Thursday");
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
        cTHSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //validateHours(oTHSpinner, cTHSpinner, "Thursday");
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });


        oFSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //validateHours(oFSpinner, cFSpinner, "Friday");
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
        cFSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //validateHours(oFSpinner, cFSpinner, "Friday");
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });


        oSASpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //validateHours(oSASpinner, cSASpinner, "Saturday");
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
        cSASpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //validateHours(oSASpinner, cSASpinner, "Saturday");
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });


        //button onClicks
        addressPromptBtn = (Button) findViewById(R.id.addressBtn);
        addressPromptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAdDialog();
            }
        });

        phoneBtn = findViewById(R.id.phoneNumBtn);
        phoneBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                openNumDialog();
            }
        });

        setHoursBtn = findViewById(R.id.hoursBtn);
        setHoursBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            validateHourSelections();
                returnHours();
            }
        });

        saveBtn = (Button) findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                System.out.println("branch == null: " + employee.getMyBranch() == null);
                if (!employee.hasBranch() && (addressMap == null || addressMap.isEmpty() || phoneNumber == null)) {
                    Toast.makeText(BranchProfileActivity.this, "A new branch must have at least an address and phone number.", Toast.LENGTH_SHORT).show();
                    return;
                }

                    Map<String, Object> mapToBranch = new HashMap<>();
                    if (hoursMap != null || !hoursMap.equals(null) || !hoursMap.isEmpty()) {
                        System.out.println("hoursLogic: " + hoursMap != null || !hoursMap.equals(null) || !hoursMap.isEmpty());
                        System.out.println("hoursMap emptyish: " + hoursMap.toString());
                        mapToBranch.put("hours", hoursMap);
                    }

                    if (phoneNumber != null) {
                        System.out.println("phoneLogic: " + phoneNumber != null || phoneNumber.equals(null));
                        mapToBranch.put("phoneNumber", formatPhoneNumber(phoneNumber));
                        phoneNumber = null;
                    }

                    System.out.println("add : " + addressMap);
                    if (addressMap != null) {

                        mapToBranch.put("address", addressMap);
                        addressMap = null;
                        //addressMap = new HashMap<>();
                    }
                    employee.createMyBranch(getApplicationContext(), mapToBranch);

            }

        });
    }

    private String formatPhoneNumber(String somePhoneNumber) {
        if (somePhoneNumber == null || somePhoneNumber.equals(null)) {
            return null;
        }
        String strippedPhoneNumber = somePhoneNumber.replaceAll("[^\\d.]", "");
        StringBuilder formatted = new StringBuilder();
        final char[] chars = strippedPhoneNumber.toCharArray();

        for (int i = 0; i< strippedPhoneNumber.length(); i++){

            if(i == 0){
                formatted.append("(");
            }
            if(i==3){
                formatted.append(") ");
            }
            if(i==6){
                formatted.append("-");
            }
            //System.out.println(String.valueOf(chars[i]));
            formatted.append(String.valueOf(chars[i]));

        }
        return formatted.toString();
    }

    //helper methods
    private Map<String, Map<String, String>> returnHours() {
        return hoursMap;
    }

    private void populateSpinner(Spinner someSpin, String time) {

        ArrayAdapter<String> someAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.Timings));
        //someAdapter.remove(time);
        //ArrayAdapter<String> someAdapter = updateSpinner(someSpin, time);

        someAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        someSpin.setAdapter(someAdapter);
        resetSpinner(someAdapter, someSpin, time);
    }

    private void resetSpinner(ArrayAdapter<String> someAdapter, Spinner someSpin, String time){
        if (time != null || !time.equals(null) || !time.equals(" ")) {
            someSpin.setSelection(someAdapter.getPosition(time));
        }
    }

    public void openAdDialog() {
        AddressDialog addressDialog = new AddressDialog();
        addressDialog.show(getSupportFragmentManager(), "Address");
    }

    public void openNumDialog() {
        NumDialog numDialog = new NumDialog();
        numDialog.show(getSupportFragmentManager(), "Phone Number");
    }


    public Map<String, Object> applyTexts(String streetDisplay, String streetNumDisplay, String unitDisplay, String cityDisplay, String provinceDisplay, String zipDisplay, String countryDisplay) {
        //if for some reason, never instantiated, but would never happen
        if (this.addressMap == null) {
            this.addressMap = new HashMap<>(); //should get to this
        }
        //when re-editing an inputted address;
        if (!this.addressMap.isEmpty()) {
            addressMap.clear();
        }

        addressMap.put("streetNumber", streetNumDisplay);
        addressMap.put("streetName", streetDisplay);
        addressMap.put("unitNumber", unitDisplay);
        addressMap.put("city", cityDisplay);
        addressMap.put("province", provinceDisplay);
        addressMap.put("postalCode", zipDisplay);
        addressMap.put("country", countryDisplay);
        return addressMap;

    }

    public String applyTxt(String telNum) {
        phoneNumber = telNum;
        return phoneNumber;
    }


    private String defineSpinner(Spinner oSpin) {
        String selectedValue = oSpin.getSelectedItem().toString();
        if (selectedValue.equals(" ")) {
            //openSpin = null;
            return null;
        } else {
            //openSpin = selectedValue;
            return oSpin.getSelectedItem().toString();
        }
    }

    private void setHours(String daySelected, String open, String close) {
        hoursMap.put(daySelected, newHoursEntry(open, close));
    }

    private Map<String, String> newHoursEntry(String opening, String closing) {
        Map<String, String> newEntry = new HashMap<>();
        newEntry.put("openingHours", opening);
        newEntry.put("closingHours", closing);
        return newEntry;
    }

    private boolean validHourEntry(String day, String opening, String closing) {

        if (day == null || day.equals(null) || day.equals(" ") || day.isEmpty()) {
            //  System.out.println("day not valid");
            return false;
        }

        if (opening == null || opening.equals(null) || opening.equals(" ") || opening.isEmpty()) {
            //  System.out.println("Opening not valid!");
            return false;
        }

        if (closing == null || closing.equals(null) || closing.equals(" ") || closing.isEmpty()) {
            //  System.out.println("Closing not valid!");
            return false;
        }

        validHours = opening.compareTo(closing) < 0;
        return opening.compareTo(closing) < 0;
    }

    private void initializeActivity() {

        //unpacks the employee object to be called in this activity
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String ROLE = extras.getString("ROLE");
        this.employee = (Employee) extras.getSerializable(ROLE);

        hoursMap = new HashMap<>();
        addressMap = new HashMap<>();
        originalHoursMap = new HashMap<>();

        //initialize hours spinners
        oSSpinner = findViewById(R.id.openSunSpin);
        cSSpinner = findViewById(R.id.closeSunSpin);
        oMSpinner = findViewById(R.id.openMonSpin);
        cMSpinner = findViewById(R.id.closeMonSpin);
        oTSpinner = findViewById(R.id.openTueSpin);
        cTSpinner = findViewById(R.id.closeTueSpin);
        oWSpinner = findViewById(R.id.openWedSpin);
        cWSpinner = findViewById(R.id.closeWedSpin);
        oTHSpinner = findViewById(R.id.openThuSpin);
        cTHSpinner = findViewById(R.id.closeThuSpin);
        oFSpinner = findViewById(R.id.openFriSpin);
        cFSpinner = findViewById(R.id.closeFriSpin);
        oSASpinner = findViewById(R.id.openSatSpin);
        cSASpinner = findViewById(R.id.closeSatSpin);

        if (employee.hasBranch()) {
            hoursMap = employee.branchHours();
            originalHoursMap = employee.branchHours();

        } else {
            originalHoursMap.put("Sunday", newHoursEntry("Closed", "Closed"));
            originalHoursMap.put("Monday", newHoursEntry("Closed", "Closed"));
            originalHoursMap.put("Tuesday", newHoursEntry("Closed", "Closed"));
            originalHoursMap.put("Wednesday", newHoursEntry("Closed", "Closed"));
            originalHoursMap.put("Thursday", newHoursEntry("Closed", "Closed"));
            originalHoursMap.put("Friday", newHoursEntry("Closed", "Closed"));
            originalHoursMap.put("Saturday", newHoursEntry("Closed", "Closed"));

            hoursMap.putAll(originalHoursMap);

        }
        populateSpinner(oSSpinner, originalHoursMap.get("Sunday").get("openingHours"));
        populateSpinner(cSSpinner, originalHoursMap.get("Sunday").get("closingHours"));
        populateSpinner(oMSpinner, originalHoursMap.get("Monday").get("openingHours"));
        populateSpinner(cMSpinner, originalHoursMap.get("Monday").get("closingHours"));
        populateSpinner(oTSpinner, originalHoursMap.get("Tuesday").get("openingHours"));
        populateSpinner(cTSpinner, originalHoursMap.get("Tuesday").get("closingHours"));
        populateSpinner(oWSpinner, originalHoursMap.get("Wednesday").get("openingHours"));
        populateSpinner(cWSpinner, originalHoursMap.get("Wednesday").get("closingHours"));
        populateSpinner(oTHSpinner, originalHoursMap.get("Thursday").get("openingHours"));
        populateSpinner(cTHSpinner, originalHoursMap.get("Thursday").get("closingHours"));
        populateSpinner(oFSpinner, originalHoursMap.get("Friday").get("openingHours"));
        populateSpinner(cFSpinner, originalHoursMap.get("Friday").get("closingHours"));
        populateSpinner(oSASpinner, originalHoursMap.get("Saturday").get("openingHours"));
        populateSpinner(cSASpinner, originalHoursMap.get("Saturday").get("closingHours"));
    }

    private void validateHourSelections(){
        boolean hoursMapValid = true;
        hoursMapValid = validateHours(oSSpinner, cSSpinner, "Sunday");
        hoursMapValid = validateHours(oMSpinner, cMSpinner, "Monday");
        hoursMapValid = validateHours(oTSpinner, cTSpinner, "Tuesday");
        hoursMapValid = validateHours(oWSpinner, cWSpinner, "Wednesday");
        hoursMapValid = validateHours(oTHSpinner, cTHSpinner, "Thursday");
        hoursMapValid = validateHours(oFSpinner, cFSpinner, "Friday");
        hoursMapValid = validateHours(oSASpinner, cSASpinner, "Saturday");
        if(!hoursMapValid){
            Toast.makeText(BranchProfileActivity.this, "Some hour entries are invalid and reset to previous values.", Toast.LENGTH_SHORT).show();
        }

    }


    private boolean validateHours(Spinner openSpinner, Spinner closeSpinner, String day){
        String openTime = defineSpinner(openSpinner);
        String closeTime = defineSpinner(closeSpinner);
        if(openTime.equals("Closed") ^ closeTime.equals("Closed")){ //XOR operator
            Toast.makeText(BranchProfileActivity.this, "Invalid entry: A closing time must be paired with another closing time.", Toast.LENGTH_SHORT).show();
            setHours(day, originalHoursMap.get(day).get("openingHours"), originalHoursMap.get(day).get("closingHours"));
            populateSpinner(openSpinner, originalHoursMap.get(day).get("openingHours"));
            populateSpinner(closeSpinner, originalHoursMap.get(day).get("closingHours"));
            return false;
        }

        if((!openTime.equals("Closed") && !closeTime.equals("Closed")) && Authenticate.timeCompare(openTime, closeTime)) {
            Toast.makeText(BranchProfileActivity.this, "Selected hour less than opening hour. Please re-enter valid time.", Toast.LENGTH_SHORT).show();
            setHours(day, originalHoursMap.get(day).get("openingHours"), originalHoursMap.get(day).get("closingHours"));
            populateSpinner(openSpinner, originalHoursMap.get(day).get("openingHours"));
            populateSpinner(closeSpinner, originalHoursMap.get(day).get("closingHours"));
            return false;
        }
        setHours(day, openTime, closeTime);
        return true;
    }



}
