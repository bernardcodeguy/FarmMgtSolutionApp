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

public class IrrigationProgramDialog extends AppCompatDialogFragment {
   private EditText dripSystem,overheadSprinkler,naturalRain;

    private IrrigationProgramDialog.IrrigationProgramDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.layout_iri_dialog,null);

        dripSystem = v.findViewById(R.id.dripSystem);
        overheadSprinkler = v.findViewById(R.id.overheadSprinkler);
        naturalRain = v.findViewById(R.id.naturalRain);


        builder.setView(v)
                .setTitle("Irrigation Program")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String dripS = dripSystem.getText().toString();
                        String overH = overheadSprinkler.getText().toString();
                        String nRain = naturalRain.getText().toString();



                        listener.applyValue5(dripS,overH,nRain);
                    }
                });
        return  builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (IrrigationProgramDialog.IrrigationProgramDialogListener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public interface IrrigationProgramDialogListener{

        void applyValue5(String dripS, String overH, String nRain);
    }
}
