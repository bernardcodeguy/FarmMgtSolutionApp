package com.lawrencemupaku.farmmgtsolutionapp.dialogs;

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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.lawrencemupaku.farmmgtsolutionapp.R;

public class OtherActivityDialog extends AppCompatDialogFragment {
    private EditText edtActivityType;
    private OtherActivityDialog.OtherActivityDialogListener listener;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.layout_other_activity_dialog,null);
        edtActivityType = v.findViewById(R.id.edtActivityType);

        builder.setView(v)
                .setTitle("Other")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String activityType = edtActivityType.getText().toString();

                        if(activityType.isEmpty()){
                            Toast.makeText(getActivity(), "Empty field might cause data errors", Toast.LENGTH_SHORT).show();
                        }
                        //Toast.makeText(getActivity(), activityType, Toast.LENGTH_SHORT).show();
                        listener.applyValue6(activityType);

                    }
                });



        return  builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (OtherActivityDialog.OtherActivityDialogListener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface OtherActivityDialogListener {
        void applyValue6(String activityType);
    }
}
