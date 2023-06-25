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
import com.lawrencemupaku.farmmgtsolutionapp.dialogs.ChemicalSpraysDialog;
import com.lawrencemupaku.farmmgtsolutionapp.dialogs.FertiliserAppDialog;
import com.lawrencemupaku.farmmgtsolutionapp.dialogs.IrrigationProgramDialog;
import com.lawrencemupaku.farmmgtsolutionapp.dialogs.LandPreparationDialog;
import com.lawrencemupaku.farmmgtsolutionapp.dialogs.OtherActivityDialog;
import com.lawrencemupaku.farmmgtsolutionapp.dialogs.PlantingDialog;
import com.lawrencemupaku.farmmgtsolutionapp.dialogs.UnitMeasurementDialog;
import com.lawrencemupaku.farmmgtsolutionapp.dialogs.WeedingProgramDialog;

public class ActActivity extends AppCompatActivity implements LandPreparationDialog.LandPreparationDialogListener, PlantingDialog.PlantingDialogListener,
        ChemicalSpraysDialog.ChemicalSpraysDialogListener, FertiliserAppDialog.FertiliserAppDialogListener, WeedingProgramDialog.WeedingProgramDialogListener,
        IrrigationProgramDialog.IrrigationProgramDialogListener, OtherActivityDialog.OtherActivityDialogListener {
    private EditText dateOfActivity;
    private EditText fieldName;
    private EditText cropName;
    private Button btnSend;
    private String phNumber;
    private DatabaseReference myRef1;
    private String date;
    private AutoCompleteTextView activityType;
    private ArrayAdapter<String> adapterItems;
    private String aType="";
    private String lPreparationValue="";
    private String ploughVal="",discHVal="",ripVal = "";
    private String mechanicalPlantingVal="",manualPlantingVal="",transplantingVal="";
    private String insecticideVal="",herbicideVal="",fungicideVal="";
    private String prePlantingVal="",starterAppVal="",foliaAppVal="",topDressingVal="",dolopingVal="",otherVal="";
    private String weedingHerbicideVal="",manualRemovalVal="";
    private String dripSystemVal="",overheadSprinklerVal="",naturalRainsVal="";

    private String otherActvityType="";
    private DatabaseReference myRef;

    String [] actType = {"Land Preparation","Planting","Chemical Sprays","Fertiliser Application","Weeding Program","Irrigation Program","Other (specify)"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act);

        Intent i = getIntent();
        phNumber = i.getStringExtra("number").toString();

        myRef1 = FirebaseDatabase.getInstance().getReferenceFromUrl("https://farm-solutions-faca6-default-rtdb.firebaseio.com/").child("fields").child(phNumber);
        myRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://farm-solutions-faca6-default-rtdb.firebaseio.com/").child("activities").child(phNumber);
        dateOfActivity = findViewById(R.id.dateOfActivity);
        fieldName = findViewById(R.id.fieldName);
        cropName = findViewById(R.id.cropName);
        btnSend = findViewById(R.id.btnSend);
        activityType = findViewById(R.id.activityType);


        Toolbar toolbar = findViewById(R.id.toolbarFields);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        adapterItems = new ArrayAdapter<String>(this,R.layout.list_item,actType);

        activityType.setAdapter(adapterItems);




        dateOfActivity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() >= 10){
                    date = dateOfActivity.getText().toString();


                    myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(date)){
                                if(snapshot.child(date).hasChild("field name")){
                                    final String fiel_name = snapshot.child(date).child("field name").getValue(String.class);
                                    fieldName.setText(fiel_name);
                                }
                                if(snapshot.child(date).hasChild("crop name")){
                                    final String crop_name = snapshot.child(date).child("crop name").getValue(String.class);
                                    cropName.setText(crop_name);
                                }

                            }else{
                                fieldName.setText("");
                                cropName.setText("");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else{
                    fieldName.setText("");
                    cropName.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnSend.setOnClickListener(e ->{
            final String dOfAct = dateOfActivity.getText().toString();
            final String fName = fieldName.getText().toString();
            final String cName = cropName.getText().toString();
            final String aType = activityType.getText().toString();

            if(aType.equals("Land Preparation") && !dOfAct.isEmpty()){
                    myRef.child(dOfAct).child("activity type").removeValue();
                    myRef.child(dOfAct).child("date").setValue(dOfAct);
                    if(!ploughVal.isEmpty()){
                        final double ploughCost = Double.parseDouble(ploughVal);
                        myRef.child(dOfAct).child("activity type").child(aType).child("plough").setValue(ploughCost);
                    }
                    if(!discHVal.isEmpty()){
                        final double discHarrowCost = Double.parseDouble(discHVal);
                        myRef.child(dOfAct).child("activity type").child(aType).child("disc harrow").setValue(discHarrowCost);
                    }
                    if(!ripVal.isEmpty()){
                        final double ripCost = Double.parseDouble(ripVal);
                        myRef.child(dOfAct).child("activity type").child(aType).child("ripper").setValue(ripCost);
                    }
                    if(!fName.isEmpty()){
                        myRef.child(dOfAct).child("field name").setValue(fName);
                    }
                    if(!cName.isEmpty()){
                        myRef.child(dOfAct).child("crop name").setValue(cName);
                    }

                    Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();

            }else if(aType.equals("Planting") && !dOfAct.isEmpty()){
                myRef.child(dOfAct).child("activity type").removeValue();
                /*if(dOfAct.isEmpty() || fName.isEmpty() || cName.isEmpty() || aType.isEmpty() || mechanicalPlantingVal.isEmpty() || manualPlantingVal.isEmpty() || transplantingVal.isEmpty()){
                    Toast.makeText(this, "Field(s) Empty", Toast.LENGTH_SHORT).show();
                }else{*/
                myRef.child(dOfAct).child("date").setValue(dOfAct);
                    if(!mechanicalPlantingVal.isEmpty()){
                        final double mec = Double.parseDouble(mechanicalPlantingVal);
                        myRef.child(dOfAct).child("activity type").child(aType).child("mechanical planting").setValue(mec);
                    }
                    if(!manualPlantingVal.isEmpty()){
                        final double man = Double.parseDouble(manualPlantingVal);
                        myRef.child(dOfAct).child("activity type").child(aType).child("manual planting").setValue(man);
                    }
                    if(!transplantingVal.isEmpty()){
                        final double tr = Double.parseDouble(transplantingVal);
                        myRef.child(dOfAct).child("activity type").child(aType).child("transplanting").setValue(tr);
                    }

                    if(!fName.isEmpty()){
                        myRef.child(dOfAct).child("field name").setValue(fName);
                    }
                    if(!cName.isEmpty()){
                        myRef.child(dOfAct).child("crop name").setValue(cName);
                    }

                    Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();



              /*  }*/
            }else if(aType.equals("Chemical Sprays") && !dOfAct.isEmpty()){
                /*if(dOfAct.isEmpty() || fName.isEmpty() || cName.isEmpty() || aType.isEmpty() || fungicideVal.isEmpty() || herbicideVal.isEmpty() || insecticideVal.isEmpty()){
                    Toast.makeText(this, "Field(s) Empty", Toast.LENGTH_SHORT).show();
                }else{*/
                    myRef.child(dOfAct).child("activity type").removeValue();
                    myRef.child(dOfAct).child("date").setValue(dOfAct);
                    if(!fungicideVal.isEmpty()){
                        final double fung = Double.parseDouble(fungicideVal);
                        myRef.child(dOfAct).child("activity type").child(aType).child("fungicides").setValue(fung);
                    }
                    if(!herbicideVal.isEmpty()){
                        final double h = Double.parseDouble(herbicideVal);
                        myRef.child(dOfAct).child("activity type").child(aType).child("herbicides").setValue(h);
                    }
                    if(!insecticideVal.isEmpty()){
                        final double insect = Double.parseDouble(insecticideVal);
                        myRef.child(dOfAct).child("activity type").child(aType).child("inserticides").setValue(insect);
                    }

                if(!fName.isEmpty()){
                    myRef.child(dOfAct).child("field name").setValue(fName);
                }
                if(!cName.isEmpty()){
                    myRef.child(dOfAct).child("crop name").setValue(cName);
                }

                Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();


               /* }*/
            }else if(aType.equals("Fertiliser Application") && !dOfAct.isEmpty()){
           /* if(!dOfAct.isEmpty() || fName.isEmpty() || cName.isEmpty() || aType.isEmpty() || prePlantingVal.isEmpty() || starterAppVal.isEmpty() || foliaAppVal.isEmpty()
            || topDressingVal.isEmpty() || dolopingVal.isEmpty()){
                Toast.makeText(this, "Field(s) Empty", Toast.LENGTH_SHORT).show();
            }else{*/
                myRef.child(dOfAct).child("activity type").removeValue();
                myRef.child(dOfAct).child("date").setValue(dOfAct);
                if(!prePlantingVal.isEmpty()){
                    final double prePlant = Double.parseDouble(prePlantingVal);
                    myRef.child(dOfAct).child("activity type").child(aType).child("pre planting").setValue(prePlant);
                }
                if(!starterAppVal.isEmpty()){
                    final double starterApp = Double.parseDouble(starterAppVal);
                    myRef.child(dOfAct).child("activity type").child(aType).child("starter application").setValue(starterApp);
                }
                if(!foliaAppVal.isEmpty()){
                    final double foliaAp = Double.parseDouble(foliaAppVal);
                    myRef.child(dOfAct).child("activity type").child(aType).child("folia application").setValue(foliaAp);
                }
                if(!topDressingVal.isEmpty()){
                    final double topDr = Double.parseDouble(topDressingVal);
                    myRef.child(dOfAct).child("activity type").child(aType).child("top dressing").setValue(topDr);
                }
                if(!dolopingVal.isEmpty()){
                    final double dolo = Double.parseDouble(dolopingVal);
                    myRef.child(dOfAct).child("activity type").child(aType).child("doloping").setValue(dolo);
                }


                if(!fName.isEmpty()){
                    myRef.child(dOfAct).child("field name").setValue(fName);
                }
                if(!cName.isEmpty()){
                    myRef.child(dOfAct).child("crop name").setValue(cName);
                }



                if(!otherVal.isEmpty()){
                    final double other = Double.parseDouble(otherVal);
                    myRef.child(dOfAct).child("activity type").child(aType).child("other").setValue(other);
                }



                Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();


           /* }*/
        }else if(aType.equals("Weeding Program") && !dOfAct.isEmpty()){
               /* if(!dOfAct.isEmpty() || fName.isEmpty() || cName.isEmpty() || aType.isEmpty() || weedingHerbicideVal.isEmpty() || manualRemovalVal.isEmpty()){
                    Toast.makeText(this, "Field(s) Empty", Toast.LENGTH_SHORT).show();
                }else{*/
                myRef.child(dOfAct).child("activity type").removeValue();
                myRef.child(dOfAct).child("date").setValue(dOfAct);
                    if(!weedingHerbicideVal.isEmpty()){
                        final double herbHerb = Double.parseDouble(weedingHerbicideVal);
                        myRef.child(dOfAct).child("activity type").child(aType).child("herbicides").setValue(herbHerb);
                    }
                if(!manualRemovalVal.isEmpty()){
                    final double manualR = Double.parseDouble(manualRemovalVal);
                    myRef.child(dOfAct).child("activity type").child(aType).child("manual removal").setValue(manualR);
                }

                if(!fName.isEmpty()){
                    myRef.child(dOfAct).child("field name").setValue(fName);
                }
                if(!cName.isEmpty()){
                    myRef.child(dOfAct).child("crop name").setValue(cName);
                }



                Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();


               /* }*/
            }else if(aType.equals("Irrigation Program") && !dOfAct.isEmpty()){
                /*if(!dOfAct.isEmpty() || fName.isEmpty() || cName.isEmpty() || aType.isEmpty() || dripSystemVal.isEmpty() || overheadSprinklerVal.isEmpty() || naturalRainsVal.isEmpty()){
                    Toast.makeText(this, "Field(s) Empty", Toast.LENGTH_SHORT).show();
                }else{*/
                myRef.child(dOfAct).child("activity type").removeValue();
                myRef.child(dOfAct).child("date").setValue(dOfAct);
                if(!dripSystemVal.isEmpty()){
                    final double drip = Double.parseDouble(dripSystemVal);
                    myRef.child(dOfAct).child("activity type").child(aType).child("drip system").setValue(drip);
                }
                if(!overheadSprinklerVal.isEmpty()){
                    final double over = Double.parseDouble(overheadSprinklerVal);
                    myRef.child(dOfAct).child("activity type").child(aType).child("overhead sprinkler").setValue(over);
                }
                if(!naturalRainsVal.isEmpty()){
                    final double nat = Double.parseDouble(naturalRainsVal);
                    myRef.child(dOfAct).child("activity type").child(aType).child("natural rain").setValue(nat);
                }

                if(!fName.isEmpty()){
                    myRef.child(dOfAct).child("field name").setValue(fName);
                }
                if(!cName.isEmpty()){
                    myRef.child(dOfAct).child("crop name").setValue(cName);
                }


                Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();


               /* }*/
            }else if(aType.equals("Other (specify)") && !dOfAct.isEmpty()){
               /* if(!dOfAct.isEmpty() || fName.isEmpty() || cName.isEmpty() || aType.isEmpty()){
                    Toast.makeText(this, "Field(s) Empty", Toast.LENGTH_SHORT).show();
                }else{*/
                myRef.child(dOfAct).child("activity type").removeValue();
                    myRef.child(dOfAct).child("date").setValue(dOfAct);
                    if(!fName.isEmpty()){
                        myRef.child(dOfAct).child("field name").setValue(fName);
                    }
                    if(!cName.isEmpty()){
                        myRef.child(dOfAct).child("crop name").setValue(cName);
                    }

                    if(!otherActvityType.isEmpty()){
                        final double other = Double.parseDouble(otherActvityType);
                        myRef.child(dOfAct).child("activity type").child("Other").child("value").setValue(other);
                    }

                    Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();

               /* }*/

            }else if(!dOfAct.isEmpty() || !fName.isEmpty()  || !cName.isEmpty()){

                if(!fName.isEmpty()){
                    myRef.child(dOfAct).child("field name").setValue(fName);
                }
                if(!cName.isEmpty()){
                    myRef.child(dOfAct).child("crop name").setValue(cName);
                }
                Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();
            }

        });

        activityType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                aType = activityType.getText().toString();
                if(parent.getPositionForView(view) == 0){
                    openDialogLandPre();
                }else if(parent.getPositionForView(view) == 1){
                    openDialogPlanting();
                }else if(parent.getPositionForView(view) == 2){
                    openDialogSpray();
                }else if(parent.getPositionForView(view) == 3){
                    openDialogFertApp();
                }else if(parent.getPositionForView(view) == 4){
                    openDialogWeedingPro();
                }else if(parent.getPositionForView(view) == 5){
                    openDialogIrrigationPro();
                }else{
                    openDialogOtherActivity();
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
        Intent intent = new Intent(ActActivity.this,MainActivity.class);
        intent.putExtra("number",phNumber);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void openDialogLandPre(){
        LandPreparationDialog landPreparationDialog = new LandPreparationDialog();
        landPreparationDialog.show(getSupportFragmentManager(),"land preparation dialog");
    }

    private void openDialogPlanting(){
        PlantingDialog plantingDialog = new PlantingDialog();
        plantingDialog.show(getSupportFragmentManager(),"planting dialog");
    }

    private void openDialogSpray(){
        ChemicalSpraysDialog chemicalSpraysDialog = new ChemicalSpraysDialog();
        chemicalSpraysDialog.show(getSupportFragmentManager(),"planting dialog");
    }

    private void openDialogFertApp(){
        FertiliserAppDialog fertiliserAppDialog = new FertiliserAppDialog();
        fertiliserAppDialog.show(getSupportFragmentManager(),"fertiliser app dialog");
    }

    private void openDialogWeedingPro(){
        WeedingProgramDialog weedingProgramDialog = new WeedingProgramDialog();
        weedingProgramDialog.show(getSupportFragmentManager(),"weeding program dialog");
    }

    private void openDialogIrrigationPro(){
        IrrigationProgramDialog irrigationProgramDialog = new IrrigationProgramDialog();
        irrigationProgramDialog.show(getSupportFragmentManager(),"irrigation program dialog");
    }

    private void openDialogOtherActivity(){
        OtherActivityDialog otherActivityDialog = new OtherActivityDialog();
        otherActivityDialog.show(getSupportFragmentManager(),"other program dialog");
    }

    @Override
    public void applyValue(String value) {
        lPreparationValue = value;
    }

    @Override
    public void applyValue(String plough, String discH, String rip) {
        ploughVal = plough;
        discHVal = discH;
        ripVal = rip;
    }



    @Override
    public void applyValue2(String fCides, String hCides, String iCides) {
        fungicideVal = fCides;
        herbicideVal = hCides;
        insecticideVal = iCides;

    }




    @Override
    public void applyValue6(String activityType) {
        otherActvityType = activityType;
    }

    @Override
    public void applyValue1(String mecPlanting, String manPlanting, String tPlanting) {
        mechanicalPlantingVal = mecPlanting;
        manualPlantingVal = manPlanting;
        transplantingVal = tPlanting;
    }

    @Override
    public void applyValue3(String preP, String starterA, String folia, String topD, String dolop, String ot) {
        prePlantingVal = preP;
        starterAppVal = starterA;
        foliaAppVal = folia;
        topDressingVal = topD;
        dolopingVal = dolop;
        otherVal = ot;
    }

    @Override
    public void applyValue4(String herb, String manual) {
        weedingHerbicideVal = herb;
        manualRemovalVal = manual;
    }

    @Override
    public void applyValue5(String dripS, String overH, String nRain) {
        dripSystemVal = dripS;
        overheadSprinklerVal = overH;
        naturalRainsVal = nRain;
    }
}