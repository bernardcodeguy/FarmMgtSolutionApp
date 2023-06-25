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
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.lawrencemupaku.farmmgtsolutionapp.R;

public class DirectExpenseDialog extends AppCompatDialogFragment {
    private EditText fuel,seedling,labour,other,other2,other3;

    private DirectExpenseDialog.DirectExpenseDialogListener listener;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.layout_direct_exp_dialog,null);

        fuel = v.findViewById(R.id.fuel);
        seedling = v.findViewById(R.id.seedling);
        labour = v.findViewById(R.id.labour);
        other = v.findViewById(R.id.other);
        other2 = v.findViewById(R.id.other2);
        other3 = v.findViewById(R.id.other3);


        builder.setView(v)
                .setTitle("Direct Expenses")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String fuels = fuel.getText().toString();
                        String seedlings = seedling.getText().toString();
                        String labours = labour.getText().toString();
                        String ot = other.getText().toString();
                        String ot2 = other2.getText().toString();
                        String ot3 = other3.getText().toString();



                       listener.getDirectExpenseValue(fuels,seedlings,labours,ot,ot2,ot3);
                    }
                });
        return  builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (DirectExpenseDialog.DirectExpenseDialogListener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public interface DirectExpenseDialogListener{

        void getDirectExpenseValue(String fuel,String seedling,String labour,String other, String other2,String other3);
    }
}
