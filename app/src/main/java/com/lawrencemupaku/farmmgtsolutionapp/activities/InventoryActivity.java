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
import com.lawrencemupaku.farmmgtsolutionapp.dialogs.OtherSeedTypeDialog;
import com.lawrencemupaku.farmmgtsolutionapp.dialogs.UnitMeasurementDialog;

public class InventoryActivity extends AppCompatActivity implements OtherActivityDialog.OtherActivityDialogListener, OtherSeedTypeDialog.OtherSeedTypeDialogListener, UnitMeasurementDialog.UnitMeasurementDialogListener {
    private EditText dateAcquired,dateSold,itemDescription,amount,openInventory,inventoryPurchase,exchangeRate,salesRevenue;
    private AutoCompleteTextView itemClass,unitMs,condition;
    private Button btnSend;
    private String phNumber;
    private String [] classes = {"Fixed","Consumable","Moveable","other (specify)"};
    private String [] units = {"kgs","units","litres","gramms","other (specify)"};
    private String [] conditions = {"New","Preowned","other (specify)"};
    private String otherVal;
    private String date1;
    private DatabaseReference myRef,myRef1,myRef2;
    private ArrayAdapter<String> adapterItems;
    private ArrayAdapter<String> adapterItems1;
    private ArrayAdapter<String> adapterItems2;
    private String itemClassVal ="";
    private String unitVal="";
    private String conditionVal="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        Intent i = getIntent();
        phNumber = i.getStringExtra("number").toString();
        myRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://farm-solutions-faca6-default-rtdb.firebaseio.com/").child("transactions").child(phNumber).child("revenue");
        myRef1 = FirebaseDatabase.getInstance().getReferenceFromUrl("https://farm-solutions-faca6-default-rtdb.firebaseio.com/").child("transactions").child(phNumber).child("inventory");
        dateAcquired = findViewById(R.id.dateAcquired);
        dateSold = findViewById(R.id.dateSold);
        itemDescription = findViewById(R.id.itemDescription);
        amount = findViewById(R.id.amount);
        openInventory = findViewById(R.id.openInventory);
        inventoryPurchase = findViewById(R.id.inventoryPurchase);
        exchangeRate = findViewById(R.id.exchangeRate);
        salesRevenue = findViewById(R.id.salesRevenue);
        itemClass = findViewById(R.id.itemClass);
        unitMs = findViewById(R.id.unitMs);
        condition = findViewById(R.id.condition);
        btnSend = findViewById(R.id.btnSend);
        Toolbar toolbar = findViewById(R.id.toolbarFields);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapterItems = new ArrayAdapter<String>(this,R.layout.list_item,classes);
        itemClass.setAdapter(adapterItems);

        adapterItems1 = new ArrayAdapter<String>(this,R.layout.list_item,units);
        unitMs.setAdapter(adapterItems1);

        adapterItems2 = new ArrayAdapter<String>(this,R.layout.list_item,conditions);
        condition.setAdapter(adapterItems2);


        dateAcquired.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() >= 10){
                    date1 = dateAcquired.getText().toString();


                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(date1)){
                                if(snapshot.child(date1).hasChild("exchange rate")){
                                    final double exchange_rate = snapshot.child(date1).child("exchange rate").getValue(Double.class);
                                    String xRate = String.valueOf(exchange_rate);
                                    exchangeRate.setText(xRate);
                                }
                                if(snapshot.child(date1).hasChild("total sales rev")){
                                    final long sales_revenue = snapshot.child(date1).child("total sales rev").getValue(Long.class);
                                    String sRev = String.valueOf(sales_revenue);
                                    salesRevenue.setText(sRev);
                                }





                            }else{

                                exchangeRate.setText("");
                                salesRevenue.setText("");

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(date1)) {
                                if(snapshot.child(date1).hasChild("date sold or used")){
                                    String dS = snapshot.child(date1).child("date sold or used").getValue(String.class);
                                    dateSold.setText(dS);
                                }
                                if(snapshot.child(date1).hasChild("item description")){
                                    String desr = snapshot.child(date1).child("item description").getValue(String.class);
                                    itemDescription.setText(desr);
                                }



                                if(snapshot.child(date1).hasChild("amount")){
                                    final long aM = snapshot.child(date1).child("amount").getValue(Long.class);
                                    String am = String.valueOf(aM);
                                    amount.setText(am);
                                }


                                if(snapshot.child(date1).hasChild("inventory purchase")){
                                    final long iP = snapshot.child(date1).child("inventory purchase").getValue(Long.class);
                                    String iPur = String.valueOf(iP);
                                    inventoryPurchase.setText(iPur);
                                }

                                if(snapshot.child(date1).hasChild("opening inventory")){
                                    final double oI = snapshot.child(date1).child("opening inventory").getValue(Double.class);
                                    String oInvenT = String.valueOf(oI);
                                    openInventory.setText(oInvenT);
                                }


                            }else{
                                openInventory.setText("0");
                                myRef1.child(date1).child("opening inventory").setValue(0);
                                dateSold.setText("");
                                itemDescription.setText("");
                                amount.setText("");
                                inventoryPurchase.setText("");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });




                }else{

                    exchangeRate.setText("");
                    salesRevenue.setText("");
                    dateSold.setText("");
                    itemDescription.setText("");
                    itemClass.setText("");
                    unitMs.setText("");
                    amount.setText("");
                    condition.setText("");
                    openInventory.setText("");
                    inventoryPurchase.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        
        itemClass.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                itemClassVal = itemClass.getText().toString();
                if(parent.getPositionForView(view) == 3){
                    openOtherDialog();
                }

            }
        });

        unitMs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                unitVal = unitMs.getText().toString();
                if(parent.getPositionForView(view) == 4){
                    openOtherDialog1();
                }
            }
        });

        condition.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                conditionVal = condition.getText().toString();
                if(parent.getPositionForView(view) == 2){
                    openOtherDialog2();
                }
            }
        });


        btnSend.setOnClickListener(e ->{
            final String dAcquired = dateAcquired.getText().toString();
            final String dSold = dateSold.getText().toString();
            final String itemDesc = itemDescription.getText().toString();
            final String itemC = itemClass.getText().toString();
            final String uMeasure = unitMs.getText().toString();
            final String c = condition.getText().toString();
            final String amt = amount.getText().toString();
            final String oInventory = openInventory.getText().toString();
            final String pInventory = inventoryPurchase.getText().toString();

            /*if(dAcquired.isEmpty() || dSold.isEmpty() || itemDesc.isEmpty() || amt.isEmpty() || oInventory.isEmpty() || pInventory.isEmpty() || itemC.isEmpty()
                    || uMeasure.isEmpty() || c.isEmpty()  || exchangeRate.getText().toString().isEmpty() || salesRevenue.getText().toString().isEmpty()){
                Toast.makeText(this, "Empty field(s)", Toast.LENGTH_SHORT).show();
            }else{*/
                if(!dSold.isEmpty()){
                    myRef1.child(dAcquired).child("date").setValue(dAcquired);
                }
                if(!dSold.isEmpty() && !dAcquired.isEmpty()){
                    myRef1.child(dAcquired).child("date sold or used").setValue(dSold);
                }
                if(!itemDesc.isEmpty() && !dAcquired.isEmpty()){
                    myRef1.child(dAcquired).child("item description").setValue(itemDesc);
                }
                if(!itemC.isEmpty() && !dAcquired.isEmpty()){
                    myRef1.child(dAcquired).child("item class").setValue(itemClassVal);
                }
                if(!uMeasure.isEmpty() && !dAcquired.isEmpty()){
                    myRef1.child(dAcquired).child("unit").setValue(unitVal);
                }
                if(!c.isEmpty() && !dAcquired.isEmpty()){
                    myRef1.child(dAcquired).child("condition").setValue(conditionVal);
                }
                if(!amt.isEmpty() && !dAcquired.isEmpty()){
                    final double amounT = Double.parseDouble(amt);
                    myRef1.child(dAcquired).child("amount").setValue(amounT);
                }
                if(!oInventory.isEmpty() && !dAcquired.isEmpty()){
                    final double openingIn = Double.parseDouble(oInventory);
                    myRef1.child(dAcquired).child("opening inventory").setValue(openingIn);
                }
                if(!pInventory.isEmpty() && !dAcquired.isEmpty()){
                    final double inPurchase = Double.parseDouble(pInventory);
                    myRef1.child(dAcquired).child("inventory purchase").setValue(inPurchase);
                }



                if(!salesRevenue.getText().toString().isEmpty() && !oInventory.isEmpty() && !pInventory.isEmpty() && !exchangeRate.getText().toString().isEmpty()
                        && !dAcquired.isEmpty()){
                    final double openingIn = Double.parseDouble(oInventory);
                    final double inPurchase = Double.parseDouble(pInventory);
                    final double sRevenue = Double.parseDouble(salesRevenue.getText().toString());
                    final double closeInv = openingIn + inPurchase - sRevenue;
                    final double costOfGoodsSold = openingIn + inPurchase - closeInv;
                    final double xR = Double.parseDouble(exchangeRate.getText().toString());
                    final double convertedCOGS = costOfGoodsSold * xR;

                    myRef1.child(dAcquired).child("closing inventory").setValue(closeInv);
                    myRef1.child(dAcquired).child("cost of goods sold").setValue(costOfGoodsSold);
                    myRef1.child(dAcquired).child("converted COGS").setValue(convertedCOGS);
                    myRef1.child(dAcquired).child("opening inventory").setValue(closeInv);
                }

            Toast.makeText(InventoryActivity.this, "saved", Toast.LENGTH_SHORT).show();


           /* }*/

        });

    }

    private void openOtherDialog() {
        OtherActivityDialog otherActivityDialog = new OtherActivityDialog();
        otherActivityDialog.show(getSupportFragmentManager(),"other dialog");
    }

    private void openOtherDialog1() {
        OtherSeedTypeDialog otherSeedTypeDialog = new OtherSeedTypeDialog();
        otherSeedTypeDialog.show(getSupportFragmentManager(),"other dialog");
    }

    private void openOtherDialog2() {
        UnitMeasurementDialog unitMeasurementDialog = new UnitMeasurementDialog();
        unitMeasurementDialog.show(getSupportFragmentManager(),"other dialog");
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
        Intent intent = new Intent(InventoryActivity.this,TransactionActivity.class);
        intent.putExtra("number",phNumber);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void applyValue6(String otherVal) {
        itemClassVal = otherVal;
    }

    @Override
    public void applyText(String otherVal) {
        unitVal = otherVal;
    }

    @Override
    public void applyUnitText(String otherVal) {
        conditionVal = otherVal;

    }
}