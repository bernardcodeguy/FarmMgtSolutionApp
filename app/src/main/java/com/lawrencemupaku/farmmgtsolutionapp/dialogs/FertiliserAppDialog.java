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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.lawrencemupaku.farmmgtsolutionapp.R;

public class FertiliserAppDialog extends AppCompatDialogFragment {

    private EditText prePlanting,starterApp,foliaApp,topDressing,doloping,other;


    private FertiliserAppDialog.FertiliserAppDialogListener listener;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.layout_fertiliser_dialog,null);


        prePlanting = v.findViewById(R.id.prePlanting);
        starterApp = v.findViewById(R.id.starterApp);
        foliaApp = v.findViewById(R.id.foliaApp);
        topDressing = v.findViewById(R.id.topDressing);
        doloping = v.findViewById(R.id.doloping);
        other = v.findViewById(R.id.other);



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
                        String preP = prePlanting.getText().toString();
                        String starterA = starterApp.getText().toString();
                        String folia = foliaApp.getText().toString();
                        String topD = topDressing.getText().toString();
                        String dolop = doloping.getText().toString();
                        String ot = other.getText().toString();

                        listener.applyValue3(preP,starterA,folia,topD,dolop,ot);
                    }
                });
        return  builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (FertiliserAppDialog.FertiliserAppDialogListener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface FertiliserAppDialogListener{
        //void applyValue3(String fertType,String value);

        void applyValue3(String preP, String starterA, String folia, String topD, String dolop, String ot);
    }
}
