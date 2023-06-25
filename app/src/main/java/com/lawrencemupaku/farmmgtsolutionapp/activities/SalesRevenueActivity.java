package com.lawrencemupaku.farmmgtsolutionapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.lawrencemupaku.farmmgtsolutionapp.dialogs.OtherActivityDialog;

public class SalesRevenueActivity extends AppCompatActivity implements OtherActivityDialog.OtherActivityDialogListener{
    private EditText date,fieldName,fieldType,cropName,qtyHarvested,salesPrice,unitMs,exchangeRate;
    private AutoCompleteTextView currencyType;
    private Button btnSend;
    private ArrayAdapter<String> adapterItems;
    private DatabaseReference myRef;
    private DatabaseReference myRef1;
    private String phNumber;
    private String date1;
    private String cuType="";
    private String xRate="";
    private String qtyH ="";
    private String sP = "";
    private String [] cType = {"USD","RTGS","other (specify)"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_revenue);
        Intent i = getIntent();
        phNumber = i.getStringExtra("number").toString();

        myRef1 = FirebaseDatabase.getInstance().getReferenceFromUrl("https://farm-solutions-faca6-default-rtdb.firebaseio.com/").child("transactions").child(phNumber).child("revenue");
        myRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://farm-solutions-faca6-default-rtdb.firebaseio.com/").child("fields").child(phNumber);

        date = findViewById(R.id.date);
        fieldName = findViewById(R.id.fieldName);
        cropName = findViewById(R.id.cropName);
        fieldType = findViewById(R.id.fieldType);
        qtyHarvested = findViewById(R.id.qtyHarvested);
        salesPrice = findViewById(R.id.salesPrice);
        unitMs = findViewById(R.id.unitMs);
        exchangeRate = findViewById(R.id.exchangeRate);
        currencyType = findViewById(R.id.currencyType);
        btnSend = findViewById(R.id.btnSend);

        Toolbar toolbar = findViewById(R.id.toolbarFields);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapterItems = new ArrayAdapter<String>(this,R.layout.list_item,cType);

        currencyType.setAdapter(adapterItems);

        date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() >= 10){
                    date1 = date.getText().toString();


                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(date1)){
                                if(snapshot.child(date1).hasChild("field name")){
                                    final String field_name = snapshot.child(date1).child("field name").getValue(String.class);
                                    fieldName.setText(field_name);
                                }
                                if(snapshot.child(date1).hasChild("field type")){
                                    final String field_type = snapshot.child(date1).child("field type").getValue(String.class);
                                    fieldType.setText(field_type);
                                }
                                if(snapshot.child(date1).hasChild("crop name")){
                                    final String crop_name = snapshot.child(date1).child("crop name").getValue(String.class);
                                    cropName.setText(crop_name);
                                }
                                if(snapshot.child(date1).hasChild("unit")){
                                    final String unit = snapshot.child(date1).child("unit").getValue(String.class);
                                    unitMs.setText(unit);
                                }

                            }else{
                                fieldName.setText("");
                                cropName.setText("");
                                fieldType.setText("");
                                unitMs.setText("");

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    /*myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(date1)) {
                                if(snapshot.child(date1).hasChild("currency type")){
                                    final String curType = snapshot.child(date1).child("currency type").getValue(String.class);
                                    if(curType.equals("USD")){
                                        currencyType.setListSelection(0);
                                    }else if(curType.equals("RTGS")){
                                        currencyType.setListSelection(1);
                                    }else if(curType.equals("other (specify)")){
                                        currencyType.setListSelection(2);
                                    }

                                }
                                if(snapshot.child(date1).hasChild("quantity harvested")){
                                    final long qH = snapshot.child(date1).child("quantity harvested").getValue(Long.class);
                                    final String qHar = String.valueOf(qH);
                                    qtyHarvested.setText(qHar);
                                }
                                if(snapshot.child(date1).hasChild("sales price")){
                                    final double sP = snapshot.child(date1).child("sales price").getValue(Double.class);
                                    final String sPx = String.valueOf(sP);
                                    salesPrice.setText(sPx);
                                }
                                if(snapshot.child(date1).hasChild("exchange rate")){
                                    final double XRates = snapshot.child(date1).child("exchange rate").getValue(Double.class);
                                    final String xRt = String.valueOf(XRates);
                                    exchangeRate.setText(xRt);
                                }

                            }else{
                                currencyType.setText("");
                                qtyHarvested.setText("");
                                salesPrice.setText("");
                                exchangeRate.setText("");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });*/

                }else{
                    fieldName.setText("");
                    cropName.setText("");
                    fieldType.setText("");
                    unitMs.setText("");
                    currencyType.setText("");
                    qtyHarvested.setText("");
                    salesPrice.setText("");
                    exchangeRate.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        currencyType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                cuType = currencyType.getText().toString();
                if(parent.getPositionForView(view) == 2){
                    openOtherDialog();
                }

            }
        });


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String d = date.getText().toString();
                final String fName = fieldName.getText().toString();
                final String fType = fieldType.getText().toString();
                final String cName = cropName.getText().toString();
                final String u = unitMs.getText().toString();
                qtyH = qtyHarvested.getText().toString();
                sP = salesPrice.getText().toString();
                xRate = exchangeRate.getText().toString();

                /*if(d.isEmpty() || fName.isEmpty() || cName.isEmpty() || fType.isEmpty() || u.isEmpty() || qtyH.isEmpty() || sP.isEmpty() || xRate.isEmpty()
                || cuType.isEmpty()){
                    Toast.makeText(SalesRevenueActivity.this, "Empty Field(s)", Toast.LENGTH_SHORT).show();
                }else{*/

                    if(!d.isEmpty()){
                        myRef1.child(d).child("date").setValue(d);
                    }

                    if(!d.isEmpty() && !qtyH.isEmpty()){
                        long qHarvested = Long.parseLong(qtyH);
                        myRef1.child(d).child("quantity harvested").setValue(qHarvested);
                    }
                    if(!d.isEmpty() && !sP.isEmpty()){
                        double sPrice = Double.parseDouble(sP);
                        myRef1.child(d).child("sales price").setValue(sPrice);
                    }

                    if(!xRate.isEmpty() && !d.isEmpty()){
                        double xchangeRate = Double.parseDouble(xRate);
                        myRef1.child(d).child("exchange rate").setValue(xchangeRate);
                    }

                    if(!fName.isEmpty() && !d.isEmpty()){
                        myRef1.child(d).child("field name").setValue(fName);
                    }

                    if(!fType.isEmpty() && !d.isEmpty()){
                        myRef1.child(d).child("field type").setValue(fType);
                    }
                    if(!cName.isEmpty() && !d.isEmpty()){
                        myRef1.child(d).child("crop name").setValue(cName);
                    }
                    if(!u.isEmpty() && !d.isEmpty()){
                        myRef1.child(d).child("unit").setValue(u);
                    }

                    if(!cuType.isEmpty() && !d.isEmpty()){


                        myRef1.child(d).child("currency type").setValue(cuType);
                    }


                    if(!qtyH.isEmpty() && !sP.isEmpty() && !xRate.isEmpty() && !d.isEmpty()){
                        long qHarvested = Long.parseLong(qtyH);
                        double sPrice = Double.parseDouble(sP);
                        double xchangeRate = Double.parseDouble(xRate);
                        double totalSalesRevenue = sPrice * qHarvested;
                        double convertedSalesRevenue = xchangeRate * totalSalesRevenue;
                        myRef1.child(d).child("exchange rate").setValue(xchangeRate);
                        myRef1.child(d).child("total sales rev").setValue(totalSalesRevenue);
                        myRef1.child(d).child("converted sales rev").setValue(convertedSalesRevenue);
                    }


                    Toast.makeText(SalesRevenueActivity.this, "saved", Toast.LENGTH_SHORT).show();
                }

           /* }*/
        });


    }

    private void openOtherDialog() {
        OtherActivityDialog otherActivityDialog = new OtherActivityDialog();
        otherActivityDialog.show(getSupportFragmentManager(),"other program dialog");
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
        Intent intent = new Intent(SalesRevenueActivity.this,TransactionActivity.class);
        intent.putExtra("number",phNumber);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void applyValue6(String otherVal) {
        cuType = otherVal;
    }
}