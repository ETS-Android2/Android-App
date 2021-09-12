package com.example.novigrad.Branch;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import android.content.Context;

import java.util.ArrayList;

import java.util.Map;
import com.example.novigrad.R;

public class HoursDialog extends AppCompatDialogFragment {
    ArrayList<String> monHours;
    ArrayList<String> tueHours;
    ArrayList<String> wedHours;
    ArrayList<String> thuHours;
    ArrayList<String> friHours;
    ArrayList<String> satHours;
    ArrayList<String> sunHours;

    private Spinner sunOpen;
    private Spinner sunClose;

    private Spinner monOpen;
    private Spinner monClose;

    private Spinner tueOpen;
    private Spinner tueClose;

    private Spinner wedOpen;
    private Spinner wedClose;

    private Spinner thuOpen;
    private Spinner thuClose;

    private Spinner friOpen;
    private Spinner friClose;

    private Spinner satOpen;
    private Spinner satClose;

    private HoursDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.layout_hoursdialog, null);

        //adapter for opening hours
        ArrayAdapter<String> oAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.Timings));
        oAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //adapter for closing hours
        ArrayAdapter<String> cAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.Timings));
        cAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //setting opening hours
        sunOpen.setAdapter(oAdapter);
        monOpen.setAdapter(oAdapter);
        tueOpen.setAdapter(oAdapter);
        wedOpen.setAdapter(oAdapter);
        thuOpen.setAdapter(oAdapter);
        friOpen.setAdapter(oAdapter);
        satOpen.setAdapter(oAdapter);

        //setting closing hours
        sunClose.setAdapter(cAdapter);
        monClose.setAdapter(cAdapter);
        tueClose.setAdapter(cAdapter);
        wedClose.setAdapter(cAdapter);
        thuClose.setAdapter(cAdapter);
        friClose.setAdapter(cAdapter);
        satClose.setAdapter(cAdapter);


        builder.setView(view)
                .setTitle("Operating Hours")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String mOSpinner = monOpen.getSelectedItem().toString();
                        String tOSpinner = tueOpen.getSelectedItem().toString();
                        String wOSpinner = wedOpen.getSelectedItem().toString();
                        String thOSpinner = thuOpen.getSelectedItem().toString();
                        String fOSpinner = friOpen.getSelectedItem().toString();
                        String sOSpinner = satOpen.getSelectedItem().toString();
                        String suOSpinner = sunOpen.getSelectedItem().toString();

                        String mCSpinner = monClose.getSelectedItem().toString();
                        String tCSpinner = tueClose.getSelectedItem().toString();
                        String wCSpinner = wedClose.getSelectedItem().toString();
                        String thCSpinner = thuClose.getSelectedItem().toString();
                        String fCSpinner = friClose.getSelectedItem().toString();
                        String sCSpinner = satClose.getSelectedItem().toString();
                        String suCSpinner = sunClose.getSelectedItem().toString();
                        if(mOSpinner.length() == 0 || mCSpinner.length()==0 || tOSpinner.length() == 0 || tCSpinner.length()==0 || wOSpinner.length() == 0 || wCSpinner.length()==0 || thOSpinner.length() == 0 || thCSpinner.length()==0 || fOSpinner.length() == 0 || fCSpinner.length()==0 || sOSpinner.length() == 0 || sCSpinner.length()==0 || suOSpinner.length() == 0 || suCSpinner.length()==0){
                            Toast.makeText(getActivity(), "ERROR: Cannot leave any field empty", Toast.LENGTH_SHORT).show();
                        } else{
                            monHours.set(0,mOSpinner);
                            monHours.set(1,mCSpinner);
                            tueHours.set(0,tOSpinner);
                            tueHours.set(1,tCSpinner);
                            wedHours.set(0,wOSpinner);
                            wedHours.set(1,wCSpinner);
                            thuHours.set(0,thOSpinner);
                            thuHours.set(1,thCSpinner);
                            friHours.set(0,fOSpinner);
                            friHours.set(1,fCSpinner);
                            satHours.set(0,sOSpinner);
                            satHours.set(1,sCSpinner);
                            sunHours.set(0,suOSpinner);
                            sunHours.set(1,suCSpinner);

                            listener.applyText(monHours,tueHours,wedHours,thuHours,friHours,satHours,sunHours);
                        }
                    }
                });

        monOpen = view.findViewById(R.id.monOpen);
        monClose = view.findViewById(R.id.monClose);
        tueOpen = view.findViewById(R.id.monOpen);
        tueClose = view.findViewById(R.id.monClose);
        wedOpen = view.findViewById(R.id.monOpen);
        wedClose = view.findViewById(R.id.monClose);
        thuOpen = view.findViewById(R.id.monOpen);
        thuClose = view.findViewById(R.id.monClose);
        friOpen = view.findViewById(R.id.monOpen);
        friClose = view.findViewById(R.id.monClose);
        satOpen = view.findViewById(R.id.monOpen);
        satClose = view.findViewById(R.id.monClose);
        sunOpen = view.findViewById(R.id.monOpen);
        sunClose = view.findViewById(R.id.monClose);

        return builder.create();
        //return null;
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        try{
            listener = (HoursDialog.HoursDialogListener)context;
        } catch(ClassCastException e){
            throw new ClassCastException(context.toString() + "Implement HoursDialogListener");
        }
    }

    public interface HoursDialogListener{
        Map<String,Object> applyText(ArrayList<String> monHours,ArrayList<String> tueHours, ArrayList<String> wedHours,ArrayList<String> thuHours, ArrayList<String> friHours, ArrayList<String> satHours, ArrayList<String> sunHours);
    }
}
