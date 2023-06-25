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

import com.lawrencemupaku.farmmgtsolutionapp.R;

public class LandPreparationDialog extends AppCompatDialogFragment {
    private EditText ploughCost,discHarrow,ripper;
    private LandPreparationDialog.LandPreparationDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.layout_land_pre,null);

        ploughCost = v.findViewById(R.id.ploughCost);
        discHarrow = v.findViewById(R.id.discHarrow);
        ripper = v.findViewById(R.id.ripper);

        builder.setView(v)
                .setTitle("Land Preparation")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String plough = ploughCost.getText().toString();
                        String discH = discHarrow.getText().toString();
                        String rip = ripper.getText().toString();
                        listener.applyValue(plough,discH,rip);
                    }
                });

        return  builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (LandPreparationDialog.LandPreparationDialogListener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface LandPreparationDialogListener{
        void applyValue(String value);

        void applyValue(String plough, String discH, String rip);
    }


}
