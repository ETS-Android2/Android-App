package com.example.novigrad.Branch;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.novigrad.R;
import com.example.novigrad.Utils.Authenticate;
import com.example.novigrad.Utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class AddressDialog extends AppCompatDialogFragment {
    private EditText street;
    private EditText streetNum;
    private EditText unitNum;
    private EditText city;
    private EditText province;
    private EditText zip;
    private EditText country;
    private AddressDialogListener listener;

    private Map<String, Object> attributesToAdd;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_addressdialog, null);

        attributesToAdd = new HashMap<>();

        builder.setView(view)
                .setTitle("Address")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //put all the values of the address map
                        //return this address map to the branch profile activity
                        String streetDisplay = street.getText().toString();
                        String streetNumDisplay = streetNum.getText().toString();
                        String unitDisplay = unitNum.getText().toString();
                        String cityDisplay = city.getText().toString();
                        String provinceDisplay = province.getText().toString();
                        String zipDisplay = zip.getText().toString();
                        String countryDisplay = country.getText().toString();


                        if(authenticated(streetDisplay, streetNumDisplay, unitDisplay, cityDisplay, provinceDisplay, zipDisplay, countryDisplay)){
                            listener.applyTexts(Utils.capitalizeFirstLetters(streetDisplay),
                            oneSetOfNumbers(streetNumDisplay),
                            oneSetOfNumbers(unitDisplay),
                            Utils.capitalizeFirstLetters(cityDisplay),
                            Utils.capitalizeFirstLetters((provinceDisplay)),
                            formatPostalCode(zipDisplay),
                            Utils.capitalizeFirstLetters((countryDisplay)));
                            Toast.makeText(getContext(), "New address ready to be saved.", Toast.LENGTH_SHORT).show();
                        } else {
                            AddressDialog addressDialog = new AddressDialog();
                            addressDialog.show(getParentFragmentManager(), "Address");
                        }
                    }
                });
        street = view.findViewById(R.id.getStreetTxt);
        streetNum = view.findViewById(R.id.getStreetNum);
        unitNum = view.findViewById(R.id.getUnitNum);
        city = view.findViewById(R.id.getCityTxt);
        province = view.findViewById(R.id.getProvinceTxt);
        zip = view.findViewById(R.id.getPostalNum);
        country = view.findViewById(R.id.getCountryTxt);

        return builder.create();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            listener = (AddressDialogListener)context;
        } catch(ClassCastException e){
            throw new ClassCastException(context.toString() + "Implement AddressDialogListener");
        }
    }

    public interface AddressDialogListener{
        Map<String,Object> applyTexts(String streetDisplay, String streetNumDisplay, String unitDisplay, String cityDisplay, String provinceDisplay, String zipDisplay, String countryDisplay);
    }

    private boolean authenticated(String streetDisplay, String streetNumDisplay, String unitDisplay, String cityDisplay, String provinceDisplay, String zipDisplay, String countryDisplay){

        if(streetDisplay.length() == 0 ||
        streetNumDisplay.length() == 0 ||
        cityDisplay.length() == 0 ||
        provinceDisplay.length() == 0 ||
        zipDisplay.length() == 0 ||
                countryDisplay.length() == 0){
            Toast.makeText(getContext(), "There cannot be empty fields.", Toast.LENGTH_SHORT).show();
            return false;
        }


        if(!Authenticate.authenticateStreetName(streetDisplay)){
            street.setError("The street must be composed of only letters.");
            Toast.makeText(getContext(), "The street must be composed of only letters.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!Authenticate.authenticateStreetNum(streetNumDisplay)){
            streetNum.setError("The street number must bell numbers and must be between 0-9999");
            Toast.makeText(getContext(), "The street number must bell numbers and must be between 0-9999.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!Authenticate.authenticateCountry(countryDisplay)){
            country.setError("The country must be composed of only letters");
            Toast.makeText(getContext(), "Novigrad services are only offered in the country Canada", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!Authenticate.authenticateUnit(unitDisplay)){
            unitNum.setError("The unit number must be all numbers and must be between 0-9999");
            Toast.makeText(getContext(), "The unit number must be all numbers and must be between 0-9999.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!Authenticate.authenticateZip(zipDisplay)){
            zip.setError("The postal code must follow the format A1A 1A1 where A is any letter except DFIOQU, and 1 is any digit between 0 and 9.");
            Toast.makeText(getContext(), "The postal code must follow the format A1A 1A1 where A is any letter except DFIOQU, and 1 is any digit between 0 and 9.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!Authenticate.authenticateCity(cityDisplay)){
            city.setError("The city must be composed of only letters");
            Toast.makeText(getContext(), "The city must be composed of only letters.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!Authenticate.authenticateProvince(provinceDisplay)){
            province.setError("The province must be composed of only letters");
            Toast.makeText(getContext(), "Novigrad services are only offered in the province Novigrad.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private String capitalizeFirstLetters(String someString){
        StringBuilder formatted = new StringBuilder();

        someString.toLowerCase();
        String[] someWords = someString.split(" ");
        for(int i = 0; i < someWords.length; i++){
            System.out.println("capitalizing: " + someWords);
            formatted.append(someWords[i].substring(0, 1).toUpperCase() + someWords[i].substring(1));

            if (i != someWords.length - 1){
                formatted.append(" ");
            }
        }
        return formatted.toString();
    }

    private String formatPostalCode(String someString){
        StringBuilder formatted = new StringBuilder();
        char[] chars = removeWhiteSpace(someString).toUpperCase().toCharArray();

        for(int i = 0; i < 6; i++){
            formatted.append(String.valueOf(chars[i]));
            if (i == (2)){
                formatted.append(" ");
            }
        }
        return formatted.toString();
    }

    private String removeWhiteSpace(String someString){
        return someString.replaceAll("\\s+", "");
    }

    private String oneSetOfNumbers(String someString){
        String[] someWords = someString.split(" ");
        return someWords[0];
    }

}
