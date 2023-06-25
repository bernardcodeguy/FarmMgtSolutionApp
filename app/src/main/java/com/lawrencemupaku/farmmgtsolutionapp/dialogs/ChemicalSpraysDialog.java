package com.lawrencemupaku.farmmgtsolutionapp.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.textfield.TextInputLayout;
import com.lawrencemupaku.farmmgtsolutionapp.R;

public class ChemicalSpraysDialog extends AppCompatDialogFragment {
    private EditText fungicides,herbicides,insecticides;

    private ChemicalSpraysDialog.ChemicalSpraysDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.layout_chemical,null);

        fungicides = v.findViewById(R.id.fungicides);
        herbicides = v.findViewById(R.id.herbicides);
        insecticides = v.findViewById(R.id.insecticides);


        

        builder.setView(v)
                .setTitle("Chemical Sprays")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String fCides = fungicides.getText().toString();
                        String hCides=herbicides.getText().toString();
                        String iCides=insecticides.getText().toString();

                        listener.applyValue2(fCides,hCides,iCides);
                    }
                });
        return  builder.create();


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (ChemicalSpraysDialog.ChemicalSpraysDialogListener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface ChemicalSpraysDialogListener{
        void applyValue2(String fungicides,String herbicides,String insecticides);
    }
}
