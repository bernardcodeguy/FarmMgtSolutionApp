package com.lawrencemupaku.farmmgtsolutionapp.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lawrencemupaku.farmmgtsolutionapp.R;
import com.lawrencemupaku.farmmgtsolutionapp.models.FieldModel;
import com.lawrencemupaku.farmmgtsolutionapp.models.PdfGenerator;

public class FieldReportActivity extends AppCompatActivity {

    private FieldModel fields;
    private TextView fieldName,fieldType,cropName,numOfPlants,targetYield,actualYield,yieldVariance,reportDate;
    private Button btnPrint;
    private RelativeLayout contentLayout;
    private String phNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field_report);

        Intent intent = getIntent();
        fields = (FieldModel) intent.getSerializableExtra("field");
        phNumber = intent.getStringExtra("number").toString();
        fieldName = findViewById(R.id.fieldName);
        fieldType = findViewById(R.id.fieldType);
        cropName = findViewById(R.id.cropName);
        numOfPlants = findViewById(R.id.numOfPlants);
        targetYield = findViewById(R.id.targetYield);
        actualYield = findViewById(R.id.actualYield);
        yieldVariance = findViewById(R.id.yieldVariance);
        reportDate = findViewById(R.id.reportDate);
        btnPrint = findViewById(R.id.btnPrint);
        contentLayout = findViewById(R.id.contentLayout);

        Toolbar toolbar = findViewById(R.id.toolbarFields);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        requestPermission();

        if(fields.getFieldName() == null){
            fieldName.setText("N/A");
        }else {
            fieldName.setText(fields.getFieldName());
        }

        if(fields.getFieldType() == null){
            fieldType.setText("N/A");
        }else {
            fieldType.setText(fields.getFieldType());
        }

        if(fields.getCropName() == null){
            cropName.setText("N/A");
        }else {
            cropName.setText(fields.getCropName());
        }

        if(fields.getFieldType() == null){
            fieldType.setText("N/A");
        }else {
            fieldType.setText(fields.getFieldType());
        }

        if(fields.getNumOfPlants() == null){
            numOfPlants.setText("N/A");
        }else {
            numOfPlants.setText(fields.getNumOfPlants());
        }

        if(fields.getTargetYield() == null){
            targetYield.setText("N/A");
        }else {
            targetYield.setText(fields.getTargetYield());
        }

        if(fields.getActualYield() == null){
            actualYield.setText("N/A");
        }else {
            actualYield.setText(fields.getActualYield());
        }
        if(fields.getYieldVariance() == null){
            yieldVariance.setText("N/A");
        }else {
            yieldVariance.setText(fields.getYieldVariance());
        }
        if(fields.getDate() == null){
            reportDate.setText("N/A");
        }else {
            reportDate.setText(fields.getDate());
        }
        
        btnPrint.setOnClickListener(e ->{
            if(checkPermissionGranted()){
                convertToPdf();
            }
        });


    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(FieldReportActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
    }

    private void convertToPdf() {
        PdfGenerator pdfGenerator = new PdfGenerator(FieldReportActivity.this);
        // llLayoutToPdfMain is variable of that view which convert to PDF
        Bitmap bitmap = pdfGenerator.getViewScreenShot(contentLayout);
        pdfGenerator.saveImageToPDF(contentLayout, bitmap);
    }

    private boolean checkPermissionGranted() {
        if((ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            // Permission has already been granted
            return  true;
        } else {
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                convertToPdf();
            }else{
                requestPermission();
            }
        }
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
        Intent intent = new Intent(FieldReportActivity.this,ReportActivity.class);
        intent.putExtra("number",phNumber);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}