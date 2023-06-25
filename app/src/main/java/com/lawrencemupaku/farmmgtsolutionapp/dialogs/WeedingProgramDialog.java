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

public class WeedingProgramDialog extends AppCompatDialogFragment {
    private EditText herbicides,manualRemoval;

    private WeedingProgramDialog.WeedingProgramDialogListener listener;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.layout_weeding_dialog,null);

        herbicides = v.findViewById(R.id.herbicides);
        manualRemoval = v.findViewById(R.id.manualRemoval);



        builder.setView(v)
                .setTitle("Weeding Program")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String herb = herbicides.getText().toString();
                        String manual = manualRemoval.getText().toString();
                        listener.applyValue4(herb,manual);
                    }
                });

        return  builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (WeedingProgramDialog.WeedingProgramDialogListener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface WeedingProgramDialogListener{

        void applyValue4(String herb, String manual);
    }
}
