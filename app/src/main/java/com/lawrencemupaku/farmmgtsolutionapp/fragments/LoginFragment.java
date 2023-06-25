package com.lawrencemupaku.farmmgtsolutionapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lawrencemupaku.farmmgtsolutionapp.R;
import com.lawrencemupaku.farmmgtsolutionapp.activities.LoginActivity;
import com.lawrencemupaku.farmmgtsolutionapp.activities.MainActivity;


public class LoginFragment extends Fragment {
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://farm-solutions-faca6-default-rtdb.firebaseio.com/").child("users");
    private LoginActivity main;
    private EditText phnumber,password;
    private Button loginButton;
    private TextView registerButton;
    private SharedPreferences sharedPref;
    public static final String userPassword = "password";
    public static final String userNumber = "number";
    public static final String mypreferences = "mypref";
    private SharedPreferences.Editor editor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        sharedPref = getActivity().getSharedPreferences(
                mypreferences, Context.MODE_PRIVATE);

        editor = sharedPref.edit();


        if(sharedPref.contains(userNumber) && sharedPref.contains(userPassword)){

            final String ph = sharedPref.getString(userNumber,"N/A");
            final String pass = sharedPref.getString(userPassword,"N/A");

            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("number",ph);
            startActivity(intent);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        phnumber = view.findViewById(R.id.phNumber);
        password = view.findViewById(R.id.password);
        loginButton = view.findViewById(R.id.loginButton);
        registerButton = view.findViewById(R.id.registerButton);

        main = (LoginActivity)getActivity();




        registerButton.setOnClickListener(e ->{

            main.pushNewFragment(new RegisterFragment());

        });

        loginButton.setOnClickListener(e ->{

            final String phNumber = phnumber.getText().toString();
            final String pass = password.getText().toString();


            editor.putString(userNumber,phNumber);
            editor.putString(userPassword,pass);
            editor.commit();


            if(phNumber.isEmpty() || pass.isEmpty() ){
                Toast.makeText(getActivity(), "Empty field(s)", Toast.LENGTH_SHORT).show();
            }else{
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(phNumber)){
                            final String userPassword = snapshot.child(phNumber).child("password").getValue(String.class);

                            if(userPassword.equals(pass)){
                                Toast.makeText(main, "Login successfull", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.putExtra("number",phNumber);

                                startActivity(intent);
                            }else{
                                Toast.makeText(main, "Wrong Password, Try again", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(main, "User does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        return view;
    }





}