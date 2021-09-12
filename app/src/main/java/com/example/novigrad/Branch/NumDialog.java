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

public class NumDialog extends AppCompatDialogFragment {

    private EditText phoneNum;
    private NumDialog.NumDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_numdialog, null);

        builder.setView(view)
                .setTitle("Phone Number")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //put all the values of the address map
                        //return this address map to the branch profile activity
                        String telNum = phoneNum.getText().toString();
                        System.out.println("Scrubbed phone: " + telNum);
                        if(authenticated(telNum)){
                            System.out.println("Phone is authenticated: " + authenticated(telNum));

                            Toast.makeText(getContext(), "New phone number ready to be saved.", Toast.LENGTH_SHORT).show();
                            listener.applyTxt(scrubPhoneNumber(telNum));
                        } else {
                            listener.applyTxt(null);
                            NumDialog numDialog = new NumDialog();
                            numDialog.show(getParentFragmentManager(), "Phone Number");
                        }

                    }

                });

        phoneNum = view.findViewById(R.id.getPhoneNum);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            listener = (NumDialog.NumDialogListener)context;
        } catch(ClassCastException e){
            throw new ClassCastException(context.toString() + "Implement NumDialogListener");
        }
    }

    public interface NumDialogListener{
        String applyTxt(String telNum);
    }

    private boolean authenticated(String telNum) {
        boolean authenticated = true;

        if (!Authenticate.authenticatePhoneNum(telNum)) {
            Toast.makeText(getContext(), "Invalid phone number entered. Phone number must have only 10 digits.", Toast.LENGTH_SHORT).show();
            phoneNum.setError("Invalid phone number entered. Phone number must have only 10 digits.");
            return false;
        }
        return true;
    }

    private String scrubPhoneNumber(String telNum){
        String cleanNum = telNum.replaceAll("[^0-9.]", "");
        return cleanNum;
    }
}

