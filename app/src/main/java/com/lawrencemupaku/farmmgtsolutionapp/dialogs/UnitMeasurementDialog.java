package com.lawrencemupaku.farmmgtsolutionapp.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.lawrencemupaku.farmmgtsolutionapp.R;

public class UnitMeasurementDialog extends AppCompatDialogFragment {
    private EditText edtMeasurement;
    private UnitMeasurementDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.layout_dialog_measurement,null);

        edtMeasurement = v.findViewById(R.id.edtMeasurement);

        builder.setView(v)
                .setTitle("Other(specify)")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String measurement = edtMeasurement.getText().toString();
                        listener.applyUnitText(measurement);
                    }
                });

        return  builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (UnitMeasurementDialog.UnitMeasurementDialogListener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface UnitMeasurementDialogListener {
        void applyUnitText(String unitMeasurement);
    }
}
