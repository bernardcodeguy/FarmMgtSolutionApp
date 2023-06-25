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

public class OtherSeedTypeDialog extends AppCompatDialogFragment {
    private EditText edtSeedType;
    private OtherSeedTypeDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.layout_dialog,null);

        edtSeedType = v.findViewById(R.id.edtSeedType);

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
                        String seedType = edtSeedType.getText().toString();
                        if(seedType.isEmpty()){
                            Toast.makeText(getActivity(), "Empty field might cause data errors", Toast.LENGTH_SHORT).show();
                        }
                        listener.applyText(seedType);
                    }
                });



        return  builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (OtherSeedTypeDialogListener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface OtherSeedTypeDialogListener {
        void applyText(String seedType);
    }
}
