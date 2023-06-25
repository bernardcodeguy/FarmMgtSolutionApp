package com.lawrencemupaku.farmmgtsolutionapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lawrencemupaku.farmmgtsolutionapp.R;
import com.lawrencemupaku.farmmgtsolutionapp.dialogs.OtherSeedTypeDialog;
import com.lawrencemupaku.farmmgtsolutionapp.dialogs.UnitMeasurementDialog;

public class FieldActivity extends AppCompatActivity implements OtherSeedTypeDialog.OtherSeedTypeDialogListener, UnitMeasurementDialog.UnitMeasurementDialogListener {

    private String phNumber;
    DatabaseReference myRef;

    String [] types = {"Greenhouse","Shednet","Open field"};
    String [] soilTypes = {"Red Soil","Black Soil","Sandy Soil"};
    String [] testDone = {"Yes","No","Partial"};
    String [] status = {"Available","Not Available","Partial"};
    String [] seedTypes = {"Seedlings","Seed","Other(specify)"};
    String [] units = {"kgs","grams","heads","Other(specify)"};

    private AutoCompleteTextView fieldType;
    private AutoCompleteTextView soilType;
    private AutoCompleteTextView test;
    private AutoCompleteTextView stat;
    private AutoCompleteTextView seedType;
    private AutoCompleteTextView unitMs;
    private Button btnSend;
    private String seType;
    private String uOfMeasure;

    private EditText fieldName,cropName,numOfPlants,daysToMat,dateOfPlanting,targetYield,actualYield;



    ArrayAdapter<String> adapterItems,adapterItems2,adapterItems3,adapterItems4,adapterItems5,adapterItems6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field);


        Intent i = getIntent();
        phNumber = i.getStringExtra("number").toString();

        myRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://farm-solutions-faca6-default-rtdb.firebaseio.com/").child("fields").child(phNumber);

        Toolbar toolbar = findViewById(R.id.toolbarFields);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnSend = findViewById(R.id.btnSend);
        fieldType = findViewById(R.id.fieldType);
        soilType = findViewById(R.id.soilType);
        test = findViewById(R.id.testDone);
        stat = findViewById(R.id.status);
        seedType = findViewById(R.id.seedType);
        unitMs = findViewById(R.id.unitMs);

        fieldName = findViewById(R.id.fieldName);
        cropName = findViewById(R.id.cropName);
        numOfPlants = findViewById(R.id.numOfPlants);
        daysToMat = findViewById(R.id.daysToMat);
        dateOfPlanting = findViewById(R.id.dateOfPlanting);
        targetYield = findViewById(R.id.targetYield);
        actualYield = findViewById(R.id.actualYield);

        adapterItems = new ArrayAdapter<String>(this,R.layout.list_item,types);
        adapterItems2 = new ArrayAdapter<String>(this,R.layout.list_item2,soilTypes);
        adapterItems3 = new ArrayAdapter<String>(this,R.layout.list_item3,testDone);
        adapterItems4 = new ArrayAdapter<String>(this,R.layout.list_item4,status);
        adapterItems5 = new ArrayAdapter<String>(this,R.layout.list_item5,seedTypes);
        adapterItems6 = new ArrayAdapter<String>(this,R.layout.list_item6,units);


        fieldType.setAdapter(adapterItems);
        soilType.setAdapter(adapterItems2);
        test.setAdapter(adapterItems3);
        stat.setAdapter(adapterItems4);
        seedType.setAdapter(adapterItems5);
        unitMs.setAdapter(adapterItems6);

       /* fieldType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();

                Toast.makeText(FieldActivity.this, "Item: "+item, Toast.LENGTH_SHORT).show();
            }
        });

        soilType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();

                Toast.makeText(FieldActivity.this, "Item: "+item, Toast.LENGTH_SHORT).show();
            }
        });

        test.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();

                Toast.makeText(FieldActivity.this, "Item: "+item, Toast.LENGTH_SHORT).show();
            }
        });

        stat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();

                Toast.makeText(FieldActivity.this, "Item: "+item, Toast.LENGTH_SHORT).show();
            }
        });

        seedType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();

                Toast.makeText(FieldActivity.this, "Item: "+item, Toast.LENGTH_SHORT).show();
            }
        });

        unitMs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();

                Toast.makeText(FieldActivity.this, "Item: "+item, Toast.LENGTH_SHORT).show();
            }
        });*/


        seedType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                seType = seedType.getText().toString();
                String item = parent.getItemAtPosition(position).toString();
                if(parent.getPositionForView(view) == 2){
                    openDialogSeedType();
                }

            }
        });

        unitMs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                uOfMeasure = unitMs.getText().toString();
                String item = parent.getItemAtPosition(position).toString();
                if(parent.getPositionForView(view) == 3){
                    openDialogMeasurement();
                }

            }
        });


        btnSend.setOnClickListener(e ->{
            final String fName = fieldName.getText().toString();
            final String fType = fieldType.getText().toString();
            final String sType = soilType.getText().toString();
            final String tDone = test.getText().toString();
            final String stats = stat.getText().toString();
            final String cName = cropName.getText().toString();
            final String nOP = numOfPlants.getText().toString();
            final String dOM = daysToMat.getText().toString();

            final String dtOfPlanting = dateOfPlanting.getText().toString();

            final String tY = targetYield.getText().toString();
            final String aY = actualYield.getText().toString();

            if(!dtOfPlanting.isEmpty()){
                myRef.child(dtOfPlanting).child("date").setValue(dtOfPlanting);
                if(!fName.isEmpty()){
                    myRef.child(dtOfPlanting).child("field name").setValue(fName);
                }

                if(!fType.isEmpty()){
                    myRef.child(dtOfPlanting).child("field type").setValue(fType);
                }
                if(!sType.isEmpty()){
                    myRef.child(dtOfPlanting).child("soil type").setValue(sType);
                }
                if(!tDone.isEmpty()){
                    myRef.child(dtOfPlanting).child("test done").setValue(tDone);
                }

                if(!stats.isEmpty()){
                    myRef.child(dtOfPlanting).child("status").setValue(stats);
                }

                if(!cName.isEmpty()){
                    myRef.child(dtOfPlanting).child("crop name").setValue(cName);
                }

                if(!seType.isEmpty()){
                    myRef.child(dtOfPlanting).child("seed type").setValue(seType);
                }

                if(!uOfMeasure.isEmpty()){
                    myRef.child(dtOfPlanting).child("unit").setValue(uOfMeasure);
                }





                int nOfPlants = 0;
                int dOfMaturity = 0;
                long tYield = 0;
                long aYield = 0;
                long yieldVariance = 0;

                if(!nOP.isEmpty()){
                    nOfPlants = Integer.parseInt(nOP);
                    myRef.child(dtOfPlanting).child("number of plants").setValue(nOfPlants);
                }

                if(!dOM.isEmpty()){
                    dOfMaturity = Integer.parseInt(dOM);
                    myRef.child(dtOfPlanting).child("days of maturity").setValue(dOfMaturity);
                }

                if(!tY.isEmpty()){
                    tYield = Integer.parseInt(tY);
                    myRef.child(dtOfPlanting).child("target yield").setValue(tYield);
                }

                if(!aY.isEmpty()){
                    aYield = Integer.parseInt(aY);
                    myRef.child(dtOfPlanting).child("actual yield").setValue(aYield);
                }

                if(!aY.isEmpty()){
                    aYield = Integer.parseInt(aY);
                    myRef.child(dtOfPlanting).child("actual yield").setValue(aYield);
                }

                if(!(aY.isEmpty() && tY.isEmpty())){
                    yieldVariance = tYield - aYield;
                    myRef.child(dtOfPlanting).child("yield variance").setValue(yieldVariance);
                }


                Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
            }







        });

    }

    private void openDialogSeedType(){
        OtherSeedTypeDialog otherSeedTypeDialog = new OtherSeedTypeDialog();
        otherSeedTypeDialog.show(getSupportFragmentManager(),"other seed dialog");
    }

    private void openDialogMeasurement(){
        UnitMeasurementDialog unitMeasurementDialog = new UnitMeasurementDialog();
        unitMeasurementDialog.show(getSupportFragmentManager(),"measurement dialog");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //Title bar back press triggers onBackPressed()
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Both navigation bar back press and title bar back press will trigger this method
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(FieldActivity.this,MainActivity.class);
        intent.putExtra("number",phNumber);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void applyText(String seedType) {
        seType = seedType;
    }

    @Override
    public void applyUnitText(String unitMeasurement) {
        uOfMeasure = unitMeasurement;
    }
}