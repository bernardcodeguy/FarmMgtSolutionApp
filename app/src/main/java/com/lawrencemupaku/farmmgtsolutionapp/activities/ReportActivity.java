package com.lawrencemupaku.farmmgtsolutionapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.lawrencemupaku.farmmgtsolutionapp.models.ChemicalSpray;
import com.lawrencemupaku.farmmgtsolutionapp.models.ConvertedValues;
import com.lawrencemupaku.farmmgtsolutionapp.models.FertiliserApplication;
import com.lawrencemupaku.farmmgtsolutionapp.models.FieldModel;
import com.lawrencemupaku.farmmgtsolutionapp.models.Inventory;
import com.lawrencemupaku.farmmgtsolutionapp.models.IrrigationProgram;
import com.lawrencemupaku.farmmgtsolutionapp.models.LandPreparation;
import com.lawrencemupaku.farmmgtsolutionapp.models.OtherActivity;
import com.lawrencemupaku.farmmgtsolutionapp.models.Planting;
import com.lawrencemupaku.farmmgtsolutionapp.models.Transaction;
import com.lawrencemupaku.farmmgtsolutionapp.models.WeedingProgram;

import java.util.HashMap;
import java.util.Map;

public class ReportActivity extends AppCompatActivity {
    private DatabaseReference myRef;
    private String phNumber;
    private EditText date1;
    private Button btnGenerate;
    private AutoCompleteTextView option;
    private String [] options = {"Field Report","Activities Report","Inventory Report","Transactions Report","Converted Values"};
    private ArrayAdapter<String> adapterItems;
    private String op = "";
    private FieldModel fields;
    private LandPreparation landPreparation;
    private Planting planting;
    private ChemicalSpray chemicalSpray;
    private FertiliserApplication fertiliserApplication;
    private WeedingProgram weedingProgram;
    private IrrigationProgram irrigationProgram;
    private OtherActivity otherActivity;
    private Inventory inventory;
    private static Transaction transaction;
    private static ConvertedValues convertedValues;
    private Map<String,String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Intent i = getIntent();
        if(i.getStringExtra("number").toString() != null){
            phNumber = i.getStringExtra("number").toString();
        }


        myRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://farm-solutions-faca6-default-rtdb.firebaseio.com/");
        Toolbar toolbar = findViewById(R.id.toolbarFields);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        date1 = findViewById(R.id.date1);
        option = findViewById(R.id.options);
        btnGenerate = findViewById(R.id.btnGenerate);
        adapterItems = new ArrayAdapter<String>(this,R.layout.list_item,options);
        option.setAdapter(adapterItems);

        data = new HashMap<>();

        fields = new FieldModel();
        landPreparation = new LandPreparation();
        planting = new Planting();
        chemicalSpray = new ChemicalSpray();
        fertiliserApplication = new FertiliserApplication();
        weedingProgram = new WeedingProgram();
        irrigationProgram = new IrrigationProgram();
        otherActivity = new OtherActivity();
        inventory = new Inventory();
        transaction = new Transaction();
        convertedValues = new ConvertedValues();

        option.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                String item = parent.getItemAtPosition(position).toString();
                if(parent.getPositionForView(view) == 0){
                    op = "fields";
                }else if(parent.getPositionForView(view) == 1){
                    op = "activities";
                }else if(parent.getPositionForView(view) == 2){
                    op = "inventory";
                }else if(parent.getPositionForView(view) == 3){
                    op = "transaction";
                    final String startDate = date1.getText().toString();
                    DatabaseReference optionRef = myRef.child("transactions").child(phNumber);
                    ReportActivity.transaction.setDate(startDate);
                    optionRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild("revenue")){
                                Query query = optionRef.child("revenue").child(startDate);


                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot data : snapshot.getChildren()) {
                                            if(data.getKey().equals("total sales rev")){
                                                ReportActivity.transaction.setTotalSalesRevenue(String.valueOf(data.getValue()));

                                            }

                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });


                            }
                            if(snapshot.hasChild("inventory")){

                                Query query1 = optionRef.child("inventory").child(startDate);


                                query1.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot data : snapshot.getChildren()) {
                                            if(data.getKey().equals("cost of goods sold")){
                                                ReportActivity.transaction.setCostsOfGoodsSold(String.valueOf(data.getValue()));

                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }
                            if(snapshot.hasChild("expenses")){

                                Query query2 = optionRef.child("expenses").child(startDate);

                                query2.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot data : snapshot.getChildren()) {
                                            if(data.getKey().equals("total opex")){
                                                ReportActivity.transaction.setOperatingExpenses(String.valueOf(data.getValue()));


                                            }
                                            if(data.getKey().equals("gross profit")){
                                                ReportActivity.transaction.setGrossProfit(String.valueOf(data.getValue()));

                                            }
                                            if(data.getKey().equals("profit or loss")){
                                                ReportActivity.transaction.setProfitLoss(String.valueOf(data.getValue()));

                                            }

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
                }else if(parent.getPositionForView(view) == 4){
                    op = "converted values";

                    final String startDate = date1.getText().toString();
                    DatabaseReference optionRef = myRef.child("transactions").child(phNumber);
                    ReportActivity.transaction.setDate(startDate);
                    optionRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild("revenue")){
                                Query query = optionRef.child("revenue").child(startDate);


                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot data : snapshot.getChildren()) {
                                            if(data.getKey().equals("converted sales rev")){
                                                ReportActivity.convertedValues.setConvertedSalesRevenue(String.valueOf(data.getValue()));

                                            }

                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });


                            }
                            if(snapshot.hasChild("inventory")){

                                Query query1 = optionRef.child("inventory").child(startDate);


                                query1.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot data : snapshot.getChildren()) {
                                            if(data.getKey().equals("converted COGS")){
                                                ReportActivity.convertedValues.setConvertedCOGS(String.valueOf(data.getValue()));

                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }
                            if(snapshot.hasChild("expenses")){

                                Query query2 = optionRef.child("expenses").child(startDate);

                                query2.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot data : snapshot.getChildren()) {
                                            if(data.getKey().equals("converted expenses")){
                                                ReportActivity.convertedValues.setConvertedExpenses(String.valueOf(data.getValue()));


                                            }
                                            if(data.getKey().equals("converted gross profit")){
                                                ReportActivity.convertedValues.setConvertedGrossProfit(String.valueOf(data.getValue()));

                                            }
                                            if(data.getKey().equals("converted profit loss")){
                                                ReportActivity.convertedValues.setConvertedProfitLoss(String.valueOf(data.getValue()));

                                            }

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
                }
            }
        });


        btnGenerate.setOnClickListener(e ->{

            if(!date1.getText().toString().isEmpty()  && !option.getText().toString().isEmpty() ){
                if(op.equals("fields")){
                    DatabaseReference optionRef = myRef.child(op);

                    final String startDate = date1.getText().toString();

                    Query searchAllQuery = optionRef
                            .child(phNumber)
                            .orderByChild("date")
                            .equalTo(startDate);


                    searchAllQuery.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                for(DataSnapshot s: snapshot.getChildren()){
                                    data.put(String.valueOf(s.getKey()),String.valueOf(s.getValue()));
                                }
                            }

                            for(Map.Entry<String, String> m: data.entrySet()) {
                                if(m.getKey().equals("field name")){
                                    fields.setFieldName(m.getValue());
                                }
                                if(m.getKey().equals("field type")){
                                    fields.setFieldType(m.getValue());
                                }
                                if(m.getKey().equals("crop name")){
                                    fields.setCropName(m.getValue());
                                }
                                if(m.getKey().equals("number of plants")){
                                    fields.setNumOfPlants(m.getValue());
                                }
                                if(m.getKey().equals("target yield")){
                                    fields.setTargetYield((m.getValue()));
                                }
                                if(m.getKey().equals("actual yield")){
                                    fields.setActualYield((m.getValue()));
                                }
                                if(m.getKey().equals("yield variance")){
                                    fields.setYieldVariance((m.getValue()));
                                }

                            }

                            fields.setDate(startDate);

                            Intent intent = new Intent(ReportActivity.this,FieldReportActivity.class);
                            intent.putExtra("field",fields);
                            intent.putExtra("number",phNumber);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else if(op.equals("activities")){


                    final String startDate = date1.getText().toString();
                    DatabaseReference optionRef = myRef.child(op).child(phNumber).child(startDate).child("activity type");

                    optionRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild("Land Preparation")){
                                Query query = optionRef.child("Land Preparation");
                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.exists()){

                                            for (DataSnapshot s : snapshot.getChildren()) {
                                                data.put(String.valueOf(s.getKey()),String.valueOf(s.getValue()));

                                            }

                                        }


                                        for(Map.Entry<String, String> m: data.entrySet()) {
                                            if(m.getKey().equals("plough")){
                                                landPreparation.setPlough(m.getValue());
                                            }
                                            if(m.getKey().equals("disc harrow")){
                                                landPreparation.setDisc_harrow(m.getValue());
                                            }
                                            if(m.getKey().equals("ripper")){
                                                landPreparation.setRipper(m.getValue());
                                            }
                                        }

                                        landPreparation.setDate(startDate);

                                        Intent intent = new Intent(ReportActivity.this,LandPreparationReportActivity.class);
                                        intent.putExtra("land",landPreparation);
                                        intent.putExtra("number",phNumber);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }

                            if(snapshot.hasChild("Planting")){
                                Query query = optionRef.child("Planting");
                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.exists()){

                                            for (DataSnapshot s : snapshot.getChildren()) {

                                                data.put(String.valueOf(s.getKey()),String.valueOf(s.getValue()));

                                            }

                                            for(Map.Entry<String, String> m: data.entrySet()) {
                                                if(m.getKey().equals("mechanical planting")){
                                                    planting.setMechanicalPlanting(m.getValue());
                                                }
                                                if(m.getKey().equals("manual planting")){
                                                    planting.setManualPlanting(m.getValue());
                                                }
                                                if(m.getKey().equals("transplanting")){
                                                    planting.setTransPlanting(m.getValue());
                                                }
                                            }

                                            planting.setDate(startDate);

                                            Intent intent = new Intent(ReportActivity.this,PlantingReportActivity.class);
                                            intent.putExtra("planting",planting);
                                            intent.putExtra("number",phNumber);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);

                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }

                            if(snapshot.hasChild("Chemical Sprays")){
                                Query query = optionRef.child("Chemical Sprays");
                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.exists()){  

                                            for (DataSnapshot s : snapshot.getChildren()) {
                                                data.put(String.valueOf(s.getKey()),String.valueOf(s.getValue()));

                                            }

                                            for(Map.Entry<String, String> m: data.entrySet()) {
                                                if(m.getKey().equals("fungicides")){
                                                    chemicalSpray.setFungicides(m.getValue());
                                                }
                                                if(m.getKey().equals("herbicides")){
                                                    chemicalSpray.setHerbicides(m.getValue());
                                                }
                                                if(m.getKey().equals("inserticides")){
                                                    chemicalSpray.setInserticides(m.getValue());
                                                }
                                            }

                                            chemicalSpray.setDate(startDate);

                                            Intent intent = new Intent(ReportActivity.this,ChemicalSprayReportActivity.class);
                                            intent.putExtra("spray",chemicalSpray);
                                            intent.putExtra("number",phNumber);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);

                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }

                            if(snapshot.hasChild("Fertiliser Application")){
                                Query query = optionRef.child("Fertiliser Application");
                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.exists()){

                                            for (DataSnapshot s : snapshot.getChildren()) {
                                                data.put(String.valueOf(s.getKey()),String.valueOf(s.getValue()));
                                            }

                                            for(Map.Entry<String, String> m: data.entrySet()) {
                                                if(m.getKey().equals("pre planting")){
                                                    fertiliserApplication.setPreplantingApp(m.getValue());
                                                }
                                                if(m.getKey().equals("starter application")){
                                                    fertiliserApplication.setStarterApp(m.getValue());
                                                }
                                                if(m.getKey().equals("folia application")){
                                                    fertiliserApplication.setFolioApp(m.getValue());
                                                }
                                                if(m.getKey().equals("top dressing")){
                                                    fertiliserApplication.setTopDressing(m.getValue());
                                                }
                                                if(m.getKey().equals("doloping")){
                                                    fertiliserApplication.setDoloping(m.getValue());
                                                }
                                                if(m.getKey().equals("other")){
                                                    fertiliserApplication.setOther(m.getValue());

                                                }

                                            }

                                            fertiliserApplication.setDate(startDate);

                                            Intent intent = new Intent(ReportActivity.this,FertiliserReportActivity.class);
                                            intent.putExtra("fertiliser",fertiliserApplication);
                                            intent.putExtra("number",phNumber);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);


                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }

                            if(snapshot.hasChild("Weeding Program")){
                                Query query = optionRef.child("Weeding Program");
                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.exists()){

                                            for (DataSnapshot s : snapshot.getChildren()) {

                                                data.put(String.valueOf(s.getKey()),String.valueOf(s.getValue()));

                                            }

                                            for(Map.Entry<String, String> m: data.entrySet()) {
                                                if(m.getKey().equals("herbicides")){
                                                    weedingProgram.setHerbicides(m.getValue());
                                                }
                                                if(m.getKey().equals("manual removal")){
                                                    weedingProgram.setManualRemoval(m.getValue());
                                                }


                                            }

                                            weedingProgram.setDate(startDate);
                                            Intent intent = new Intent(ReportActivity.this,WeedingProgramReportActivity.class);
                                            intent.putExtra("weeding",weedingProgram);
                                            intent.putExtra("number",phNumber);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);


                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }

                            if(snapshot.hasChild("Irrigation Program")){
                                Query query = optionRef.child("Irrigation Program");
                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.exists()){

                                            for (DataSnapshot s : snapshot.getChildren()) {
                                                data.put(String.valueOf(s.getKey()),String.valueOf(s.getValue()));
                                            }

                                            for(Map.Entry<String, String> m: data.entrySet()) {
                                                if(m.getKey().equals("drip system")){
                                                    irrigationProgram.setDripSystem(m.getValue());
                                                }
                                                if(m.getKey().equals("overhead sprinkler")){
                                                    irrigationProgram.setOverheadSprinkler(m.getValue());
                                                }
                                                if(m.getKey().equals("natural rain")){
                                                    irrigationProgram.setNaturalRains(m.getValue());
                                                }
                                            }

                                            irrigationProgram.setDate(startDate);
                                            Intent intent = new Intent(ReportActivity.this,IrrigationProgramActivity.class);
                                            intent.putExtra("irrigation",irrigationProgram);
                                            intent.putExtra("number",phNumber);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }

                            if(snapshot.hasChild("Other")){
                                Query query = optionRef.child("Other");
                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.exists()){

                                            for (DataSnapshot s : snapshot.getChildren()) {

                                                data.put(String.valueOf(s.getKey()),String.valueOf(s.getValue()));

                                            }

                                            for(Map.Entry<String, String> m: data.entrySet()) {
                                                if(m.getKey().equals("value")){
                                                    otherActivity.setOther1(m.getValue());
                                                }
                                                /*if(m.getKey().equals("overhead sprinkler")){
                                                    irrigationProgram.setOverheadSprinkler(m.getValue());
                                                }
                                                if(m.getKey().equals("natural rain")){
                                                    irrigationProgram.setNaturalRains(m.getValue());
                                                }*/
                                            }

                                            otherActivity.setDate(startDate);
                                            Intent intent = new Intent(ReportActivity.this,OtherActivityReport.class);
                                            intent.putExtra("other",otherActivity);
                                            intent.putExtra("number",phNumber);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);


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
                   
                }else if(op.equals("inventory")){
                    DatabaseReference optionRef = myRef.child("transactions").child(phNumber);

                    final String startDate = date1.getText().toString();

                    Query searchAllQuery = optionRef
                            .child(op)
                            .orderByChild("date")
                            .equalTo(startDate);


                    searchAllQuery.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                for(DataSnapshot s: snapshot.getChildren()){
                                    data.put(String.valueOf(s.getKey()),String.valueOf(s.getValue()));
                                }
                            }

                            for(Map.Entry<String, String> m: data.entrySet()) {
                                if(m.getKey().equals("closing inventory")){
                                    inventory.setClosingInventory(m.getValue());
                                }


                            }

                            inventory.setDate(startDate);
                            Intent intent = new Intent(ReportActivity.this,InventoryReportActivity.class);
                            intent.putExtra("inventory",inventory);
                            intent.putExtra("number",phNumber);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }else if(op.equals("transaction")){

                    Intent intent = new Intent(ReportActivity.this,TransactionReportActivity.class);
                    intent.putExtra("number",phNumber);

                    if(!date1.getText().toString().isEmpty()){
                       this.transaction.setDate(date1.getText().toString());

                    }

                    if(transaction.getDate() != null){
                        intent.putExtra("date",transaction.getDate());

                    }

                    intent.putExtra("salesRevenue",transaction.getTotalSalesRevenue());
                    intent.putExtra("cogs",transaction.getCostsOfGoodsSold());
                    intent.putExtra("salesRevenue",transaction.getTotalSalesRevenue());
                    intent.putExtra("profitOrLoss",transaction.getProfitLoss());
                    intent.putExtra("grossP",transaction.getGrossProfit());
                    intent.putExtra("opex",transaction.getOperatingExpenses());
                    startActivity(intent);


                }else if(op.equals("converted values")){

                    Intent intent = new Intent(ReportActivity.this,ConvertedReportActivity.class);
                    intent.putExtra("number",phNumber);

                    if(!date1.getText().toString().isEmpty()){
                        this.convertedValues.setDate(date1.getText().toString());
                    }

                    if(convertedValues.getDate() != null){
                        intent.putExtra("dt",convertedValues.getDate());
                    }

                    intent.putExtra("salesRevenue",convertedValues.getConvertedSalesRevenue());
                    intent.putExtra("cogs",convertedValues.getConvertedCOGS());
                    intent.putExtra("profitOrLoss",convertedValues.getConvertedProfitLoss());
                    intent.putExtra("grossP",convertedValues.getConvertedGrossProfit());
                    intent.putExtra("opex",convertedValues.getConvertedExpenses());
                    startActivity(intent);
                }

            }


        });

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
        Intent intent = new Intent(ReportActivity.this,MainActivity.class);
        intent.putExtra("number",phNumber);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}