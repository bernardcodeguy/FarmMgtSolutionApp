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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.lawrencemupaku.farmmgtsolutionapp.R;
import com.lawrencemupaku.farmmgtsolutionapp.dialogs.DirectExpenseDialog;
import com.lawrencemupaku.farmmgtsolutionapp.dialogs.OtherActivityDialog;
import com.lawrencemupaku.farmmgtsolutionapp.dialogs.OtherSeedTypeDialog;

public class ExpensesActivity extends AppCompatActivity implements OtherActivityDialog.OtherActivityDialogListener, OtherSeedTypeDialog.OtherSeedTypeDialogListener, DirectExpenseDialog.DirectExpenseDialogListener {

    private EditText date,fieldName,cropName,exchangeRate,activityCost;
    private Button btnSend;
    private AutoCompleteTextView directExpense,expenseClass,currencyType;
    private String [] cTypes = {"USD","RTGS","other (specify)"};
    private String [] dExpenses = {"Add direct expenses"};
    private String [] exClasses = {"Capex","Opex","other (specify)"};

    private ArrayAdapter<String> adapterItems,adapterItems1,adapterItems2;
    private DatabaseReference myRef;
    private DatabaseReference myRef1,myRef2,myRef3,myRef4;
    private String phNumber;
    private String date1;
    private String cType="";
    private String fuelVal="",seedlingVal="",labourVal="",otherVal="",other2Val="",other3Val="";
    private String xClass="";
    double totalActivityCost = 0;

    double sales_revenue = 0;
    double cost_of_goods_sold = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);

        Intent i = getIntent();
        phNumber = i.getStringExtra("number").toString();

        myRef1 = FirebaseDatabase.getInstance().getReferenceFromUrl("https://farm-solutions-faca6-default-rtdb.firebaseio.com/").child("transactions").child(phNumber).child("expenses");
        myRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://farm-solutions-faca6-default-rtdb.firebaseio.com/").child("activities").child(phNumber);
        myRef3 = FirebaseDatabase.getInstance().getReferenceFromUrl("https://farm-solutions-faca6-default-rtdb.firebaseio.com/").child("transactions").child(phNumber).child("revenue");
        myRef4 = FirebaseDatabase.getInstance().getReferenceFromUrl("https://farm-solutions-faca6-default-rtdb.firebaseio.com/").child("transactions").child(phNumber).child("inventory");

        date = findViewById(R.id.date);
        fieldName = findViewById(R.id.fieldName);
        cropName = findViewById(R.id.cropName);
        activityCost = findViewById(R.id.activityCost);
        exchangeRate = findViewById(R.id.exchangeRate);
        directExpense = findViewById(R.id.directExpense);
        expenseClass = findViewById(R.id.expenseClass);
        currencyType = findViewById(R.id.currencyType);
        btnSend = findViewById(R.id.btnSend);

        Toolbar toolbar = findViewById(R.id.toolbarFields);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        adapterItems = new ArrayAdapter<String>(this,R.layout.list_item,dExpenses);

        directExpense.setAdapter(adapterItems);

        adapterItems1 = new ArrayAdapter<String>(this,R.layout.list_item,cTypes);

        currencyType.setAdapter(adapterItems1);

        adapterItems2 = new ArrayAdapter<String>(this,R.layout.list_item,exClasses);

        activityCost.setVisibility(View.GONE);

        expenseClass.setAdapter(adapterItems2);

        date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() >= 10){
                    date1 = date.getText().toString();

                    //final Query[] q = new Query[1];
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(date1)){
                                //final Query q = myRef.child(date1).child("activity type")

                                myRef2 = FirebaseDatabase.getInstance().getReferenceFromUrl("https://farm-solutions-faca6-default-rtdb.firebaseio.com/").child("activities")
                                        .child(phNumber).child(date1).child("activity type");

                                myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.hasChild("Land Preparation")){
                                            Query query = myRef2.child("Land Preparation");
                                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    if(snapshot.exists()){

                                                        for (DataSnapshot data : snapshot.getChildren()) {
                                                            double value = data.getValue(Double.class);
                                                            //Toast.makeText(ExpensesActivity.this, value+"", Toast.LENGTH_SHORT).show();
                                                            totalActivityCost = totalActivityCost + value;

                                                        }

                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
                                        }

                                        if(snapshot.hasChild("Planting")){
                                            Query query = myRef2.child("Planting");
                                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    if(snapshot.exists()){

                                                        for (DataSnapshot data : snapshot.getChildren()) {
                                                            double value = data.getValue(Double.class);
                                                            totalActivityCost = totalActivityCost + value;

                                                        }

                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
                                        }

                                        if(snapshot.hasChild("Chemical Sprays")){
                                            Query query = myRef2.child("Chemical Sprays");
                                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    if(snapshot.exists()){

                                                        for (DataSnapshot data : snapshot.getChildren()) {
                                                            double value = data.getValue(Double.class);
                                                            totalActivityCost = totalActivityCost + value;

                                                        }

                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
                                        }

                                        if(snapshot.hasChild("Fertiliser Application")){
                                            Query query = myRef2.child("Fertiliser Application");
                                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    if(snapshot.exists()){

                                                        for (DataSnapshot data : snapshot.getChildren()) {
                                                            double value = data.getValue(Double.class);
                                                            totalActivityCost = totalActivityCost + value;

                                                        }

                                                        //Toast.makeText(ExpensesActivity.this, total+"", Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
                                        }

                                        if(snapshot.hasChild("Weeding Program")){
                                            Query query = myRef2.child("Weeding Program");
                                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    if(snapshot.exists()){

                                                        for (DataSnapshot data : snapshot.getChildren()) {
                                                            double value = data.getValue(Double.class);
                                                            totalActivityCost = totalActivityCost + value;

                                                        }

                                                        //Toast.makeText(ExpensesActivity.this, total+"", Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
                                        }

                                        if(snapshot.hasChild("Irrigation Program")){
                                            Query query = myRef2.child("Irrigation Program");
                                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    if(snapshot.exists()){

                                                        for (DataSnapshot data : snapshot.getChildren()) {
                                                            double value = data.getValue(Double.class);
                                                            totalActivityCost = totalActivityCost + value;

                                                        }

                                                        //Toast.makeText(ExpensesActivity.this, total+"", Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
                                        }

                                        if(snapshot.hasChild("Other")){
                                            Query query = myRef2.child("Other");
                                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    if(snapshot.exists()){

                                                        for (DataSnapshot data : snapshot.getChildren()) {
                                                            double value = data.getValue(Double.class);
                                                            totalActivityCost = totalActivityCost + value;

                                                        }

                                                        //Toast.makeText(ExpensesActivity.this, total+"", Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
                                        }




                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });






                                final String field_name = snapshot.child(date1).child("field name").getValue(String.class);
                                /*final long plough = snapshot.child(date1).child("plough").getValue(Long.class);
                                final long disc_harrow = snapshot.child(date1).child("disc harrow").getValue(Long.class);
                                final long ripper = snapshot.child(date1).child("ripper").getValue(Long.class);*/
                                final String crop_name = snapshot.child(date1).child("crop name").getValue(String.class);

                                //final String direct_exp = String.valueOf(plough+disc_harrow+ripper);

                                fieldName.setText(field_name);
                                cropName.setText(crop_name);
                                //Toast.makeText(ExpensesActivity.this, direct_exp+"", Toast.LENGTH_SHORT).show();
                            }else{
                                fieldName.setText("");
                                cropName.setText("");

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                    myRef3.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(date1)){

                                sales_revenue = snapshot.child(date1).child("total sales rev").getValue(Double.class);


                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    myRef4.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(date1)){
                                cost_of_goods_sold = snapshot.child(date1).child("cost of goods sold").getValue(Double.class);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



                }else{
                    fieldName.setText("");
                    cropName.setText("");
                    activityCost.setText("");
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        directExpense.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openDirectExpenseDialog();

            }
        });


        currencyType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cType = currencyType.getText().toString();
                if(parent.getPositionForView(view) == 2){

                    openDialogOtherActivity();
                }
            }
        });

        expenseClass.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                xClass = expenseClass.getText().toString();
                if(parent.getPositionForView(view) == 2){

                    openDialogOther2();
                }
            }
        });


        btnSend.setOnClickListener( e ->{
            activityCost.setText(String.valueOf(totalActivityCost));

            final String d = date.getText().toString();
            final String fName = fieldName.getText().toString();
            final String cName = cropName.getText().toString();
            final String actCost = activityCost.getText().toString();
            final String cTy = currencyType.getText().toString();

            final String exRate = exchangeRate.getText().toString();

               /* if(d.isEmpty() || fName.isEmpty()  || cName.isEmpty() || actCost.isEmpty()  || exRate.isEmpty() || fuelVal.isEmpty()
                        || seedlingVal.isEmpty()  || labourVal.isEmpty() || cTy.isEmpty()){
                    Toast.makeText(this, "Empty Field(s)", Toast.LENGTH_SHORT).show();
                }else{*/
                    if(!d.isEmpty()){
                        myRef1.child(d).child("date").setValue(d);
                    }

                    if(!d.isEmpty() && !fName.isEmpty()){
                        myRef1.child(d).child("field name").setValue(fName);
                    }
                    if(!d.isEmpty() && !cName.isEmpty()){
                        myRef1.child(d).child("crop name").setValue(cName);
                    }

                    if(!d.isEmpty() && !fuelVal.isEmpty()){
                        final double fuelCost = Double.parseDouble(fuelVal);
                        myRef1.child(d).child("direct expenses").child("fuel").setValue(fuelCost);
                    }

                    if(!d.isEmpty() && !seedlingVal.isEmpty()){
                        final double seedlingCost = Double.parseDouble(seedlingVal);
                        myRef1.child(d).child("direct expenses").child("seed seedlings").setValue(seedlingCost);
                    }
                    if(!d.isEmpty() && !labourVal.isEmpty()){
                        final double labourCost = Double.parseDouble(labourVal);
                        myRef1.child(d).child("direct expenses").child("labour").setValue(labourCost);
                    }
                    if(!d.isEmpty() && !otherVal.isEmpty()){
                        final double otherCost = Double.parseDouble(otherVal);
                        myRef1.child(d).child("direct expenses").child("other1").setValue(otherCost);
                    }
                    if(!d.isEmpty() && !other2Val.isEmpty()){
                        final double other2Cost = Double.parseDouble(other2Val);
                        myRef1.child(d).child("direct expenses").child("other2").setValue(other2Cost);
                    }
                    if(!d.isEmpty() && !other3Val.isEmpty()){
                        final double other3Cost = Double.parseDouble(other3Val);
                        myRef1.child(d).child("direct expenses").child("other3").setValue(other3Cost);
                    }

            if(!d.isEmpty() && !other3Val.isEmpty() && !fuelVal.isEmpty() && !seedlingVal.isEmpty() && !labourVal.isEmpty()
            && !otherVal.isEmpty() && !other2Val.isEmpty() && !other3Val.isEmpty() && !exRate.isEmpty()){
                final double XRate = Double.parseDouble(exRate);
                final double fuelCost = Double.parseDouble(fuelVal);
                final double seedlingCost = Double.parseDouble(seedlingVal);
                final double labourCost = Double.parseDouble(labourVal);
                final double otherCost = Double.parseDouble(otherVal);
                final double other2Cost = Double.parseDouble(other2Val);
                final double other3Cost = Double.parseDouble(other3Val);
                final double totalDirectCost = fuelCost+seedlingCost+labourCost+otherCost+other2Cost+other3Cost;
                final double totalOpex = totalActivityCost + totalDirectCost;
                final double convertedExpenses = XRate * totalOpex;
                final double grossProfit = sales_revenue - cost_of_goods_sold;
                final double profitOrLoss = grossProfit - totalOpex;
                final double convertedGrossProfit = XRate * grossProfit;
                final double convertedprofitOrLoss = XRate * profitOrLoss;
                myRef1.child(d).child("exchange rate").setValue(XRate);
                myRef1.child(d).child("converted expenses").setValue(convertedExpenses);
                myRef1.child(d).child("gross profit").setValue(grossProfit);
                myRef1.child(d).child("total opex").setValue(totalOpex);
                myRef1.child(d).child("profit or loss").setValue(profitOrLoss);
                myRef1.child(d).child("converted gross profit").setValue(convertedGrossProfit);
                myRef1.child(d).child("converted profit loss").setValue(convertedprofitOrLoss);
            }










                    if(!activityCost.getText().toString().isEmpty()){
                        myRef1.child(d).child("activity cost").setValue(totalActivityCost);
                    }
                    if(!currencyType.getText().toString().isEmpty()){
                        myRef1.child(d).child("currency type").setValue(cTy);
                    }
                if(!expenseClass.getText().toString().isEmpty()){
                    myRef1.child(d).child("expense class").setValue(xClass);
                }


               /* }*/



            Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();

        });

    }

    private void openDirectExpenseDialog() {
        DirectExpenseDialog directExpenseDialog = new DirectExpenseDialog();
        directExpenseDialog.show(getSupportFragmentManager(),"direct expense dialog");
    }

    private void openDialogOtherActivity(){
        OtherActivityDialog otherActivityDialog = new OtherActivityDialog();
        otherActivityDialog.show(getSupportFragmentManager(),"other program dialog");
    }

    private void openDialogOther2(){
        OtherSeedTypeDialog otherSeedTypeDialog = new OtherSeedTypeDialog();
        otherSeedTypeDialog.show(getSupportFragmentManager(),"other dialog");
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
        Intent intent = new Intent(ExpensesActivity.this,TransactionActivity.class);
        intent.putExtra("number",phNumber);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void applyValue6(String currencyType) {
        cType = currencyType;
    }

    @Override
    public void applyText(String xClas) {
        xClass = xClas;
    }

    @Override
    public void getDirectExpenseValue(String fuel, String seedling, String labour, String other, String other2, String other3) {
        fuelVal = fuel;
        seedlingVal = seedling;
        labourVal = labour;
        otherVal = other;
        other2Val = other2;
        other3Val = other3;
    }
}