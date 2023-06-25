package com.lawrencemupaku.farmmgtsolutionapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lawrencemupaku.farmmgtsolutionapp.R;
import com.lawrencemupaku.farmmgtsolutionapp.activities.MainActivity;

public class RegisterFragment extends Fragment {

    DatabaseReference myRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://farm-solutions-faca6-default-rtdb.firebaseio.com/").child("users");
    private SharedPreferences sharedPref;
    public static final String userPassword = "password";
    public static final String userNumber = "number";
    public static final String mypreferences = "mypref";
    private SharedPreferences.Editor editor;
    private EditText name,email,number,password,confirm,location,country,code;
    private Button btnRegister;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPref = getActivity().getSharedPreferences(
                mypreferences, Context.MODE_PRIVATE);

        editor = sharedPref.edit();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_register, container, false);
        Toolbar toolbar = v.findViewById(R.id.toolbar);

        name = v.findViewById(R.id.name);
        email = v.findViewById(R.id.email);
        number = v.findViewById(R.id.number);
        password = v.findViewById(R.id.password);
        confirm = v.findViewById(R.id.confirm);
        location = v.findViewById(R.id.location);
        country = v.findViewById(R.id.country);
        code = v.findViewById(R.id.code);

        btnRegister = v.findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(e ->{

            final String fullname = name.getText().toString();
            final String mail = email.getText().toString();
            final String phNumber = number.getText().toString();
            final String pass = password.getText().toString();
            final String pass2 = confirm.getText().toString();
            final String loc = location.getText().toString();
            final String countr = country.getText().toString();
            final String cd = code.getText().toString();

            if(fullname.isEmpty() || phNumber.isEmpty() || phNumber.isEmpty() || pass.isEmpty() || pass2.isEmpty() || loc.isEmpty() || countr.isEmpty() || cd.isEmpty()){
                Toast.makeText(getActivity(), "Empty field(s)", Toast.LENGTH_SHORT).show();
            }else if(!pass.equals(pass2)){
                Toast.makeText(getActivity(), "Password Mismatch", Toast.LENGTH_SHORT).show();
            }else{

                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(phNumber)){
                            Toast.makeText(getActivity(), "User already registered", Toast.LENGTH_SHORT).show();
                        }else{

                            myRef.child(phNumber).child("fullname").setValue(fullname);
                            myRef.child(phNumber).child("email").setValue(mail);
                            myRef.child(phNumber).child("number").setValue(phNumber);
                            myRef.child(phNumber).child("password").setValue(pass);
                            myRef.child(phNumber).child("location").setValue(loc);
                            myRef.child(phNumber).child("country").setValue(countr);
                            myRef.child(phNumber).child("code").setValue(cd);
                            editor.putString(userNumber,phNumber);
                            editor.putString(userPassword,pass);
                            editor.commit();
                            Toast.makeText(getActivity(), "User registered successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.putExtra("number",phNumber);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });






            }



        });





        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        return v;
    }

}