package com.lawrencemupaku.farmmgtsolutionapp.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.lawrencemupaku.farmmgtsolutionapp.R;

public class PlantingDialog extends AppCompatDialogFragment {
    private EditText manualPlanting,mechanicalPlanting,transPlanting;
    private PlantingDialog.PlantingDialogListener listener;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.layout_dialog_plants,null);

        manualPlanting = v.findViewById(R.id.manualPlanting);
        mechanicalPlanting = v.findViewById(R.id.mechanicalPlanting);
        transPlanting = v.findViewById(R.id.transPlanting);

        builder.setView(v)
                .setTitle("Planting")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String mecPlanting = mechanicalPlanting.getText().toString();
                        String manPlanting = manualPlanting.getText().toString();
                        String tPlanting = transPlanting.getText().toString();

                        listener.applyValue1(mecPlanting,manPlanting,tPlanting);
                    }
                });
        return  builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (PlantingDialog.PlantingDialogListener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface PlantingDialogListener{
        void applyValue1(String mecPlanting, String manPlanting, String tPlanting);
    }
}
