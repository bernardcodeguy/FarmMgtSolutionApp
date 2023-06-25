package com.lawrencemupaku.farmmgtsolutionapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lawrencemupaku.farmmgtsolutionapp.R;

public class MainActivity extends AppCompatActivity {
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://farm-solutions-faca6-default-rtdb.firebaseio.com/").child("users");
    private ConstraintLayout constraintField,constraintTransaction,constarintActivities,constraintReports;
    public static final String userPassword = "password";
    public static final String userNumber = "number";
    public static final String mypreferences = "mypref";
    private SharedPreferences.Editor editor;
    private Context context;
    private SharedPreferences sharedPref;
    //Menu menu;

    String fullname="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this.getApplicationContext();
        sharedPref = context.getSharedPreferences(
                mypreferences, Context.MODE_PRIVATE);

        editor = sharedPref.edit();

        Intent i = getIntent();
        String phNumber = i.getStringExtra("number").toString();

        Toolbar toolbar = findViewById(R.id.toolbar1);

        setSupportActionBar(toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.ic_dot));

        constraintField = findViewById(R.id.constraintField);
        constraintTransaction = findViewById(R.id.constraintTransaction);
        constarintActivities = findViewById(R.id.constarintActivities);
        constraintReports = findViewById(R.id.constraintReports);


        constraintField.setOnClickListener(e ->{
            Intent intent = new Intent(MainActivity.this,FieldActivity.class);
            intent.putExtra("number",phNumber);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        constraintTransaction.setOnClickListener(e ->{
            Intent intent = new Intent(MainActivity.this,TransactionActivity.class);
            intent.putExtra("number",phNumber);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        constarintActivities.setOnClickListener(e ->{
            Intent intent = new Intent(MainActivity.this,ActActivity.class);
            intent.putExtra("number",phNumber);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        constraintReports.setOnClickListener(e ->{
            Intent intent = new Intent(MainActivity.this,ReportActivity.class);
            intent.putExtra("number",phNumber);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });



        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(phNumber)){
                    fullname = snapshot.child(phNumber).child("fullname").getValue(String.class);
                    String [] names = fullname.split(" ");
                    toolbar.setTitle("Welcome "+names[0]);
                }else{
                    editor.remove(userPassword);
                    editor.remove(userNumber);
                    editor.apply();
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.logout:
                editor.remove(userPassword);
                editor.remove(userNumber);
                editor.apply();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}