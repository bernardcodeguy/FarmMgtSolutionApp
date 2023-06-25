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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lawrencemupaku.farmmgtsolutionapp.R;
import com.lawrencemupaku.farmmgtsolutionapp.models.ChemicalSpray;
import com.lawrencemupaku.farmmgtsolutionapp.models.PdfGenerator;
import com.lawrencemupaku.farmmgtsolutionapp.models.Planting;

public class ChemicalSprayReportActivity extends AppCompatActivity {
    private TextView activityType,fungicides,herbicides,insecticides,reportDate;
    private Button btnPrint;
    private RelativeLayout contentLayout2;
    private String phNumber;
    private ChemicalSpray chemicalSpray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemical_spray_report);

        Intent intent = getIntent();
        chemicalSpray = (ChemicalSpray) intent.getSerializableExtra("spray");
        phNumber = intent.getStringExtra("number").toString();
        activityType = findViewById(R.id.activityType);
        fungicides = findViewById(R.id.fungicides);
        herbicides = findViewById(R.id.herbicides);
        insecticides = findViewById(R.id.insecticides);
        reportDate = findViewById(R.id.reportDate);
        btnPrint = findViewById(R.id.btnPrint);
        contentLayout2 = findViewById(R.id.contentLayout2);
        requestPermission();

        Toolbar toolbar = findViewById(R.id.toolbarFields);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(chemicalSpray.getFungicides() == null){
            fungicides.setText("N/A");
        }else {
            fungicides.setText(chemicalSpray.getFungicides());
        }

        if(chemicalSpray.getHerbicides() == null){
            herbicides.setText("N/A");
        }else {
            herbicides.setText(chemicalSpray.getHerbicides());
        }

        if(chemicalSpray.getInserticides() == null){
            insecticides.setText("N/A");
        }else {
            insecticides.setText(chemicalSpray.getInserticides());
        }

        if(chemicalSpray.getDate() == null){
            reportDate.setText("N/A");
        }else {
            reportDate.setText(chemicalSpray.getDate());
        }

        if(chemicalSpray != null){
            activityType.setText("Chemical Spray");
        }else{
            activityType.setText("N/A");
        }


        btnPrint.setOnClickListener(e ->{
            if(checkPermissionGranted()){
                convertToPdf();
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
        Intent intent = new Intent(ChemicalSprayReportActivity.this,ReportActivity.class);
        intent.putExtra("number",phNumber);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(ChemicalSprayReportActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
    }

    private void convertToPdf() {
        PdfGenerator pdfGenerator = new PdfGenerator(ChemicalSprayReportActivity.this);
        // llLayoutToPdfMain is variable of that view which convert to PDF
        Bitmap bitmap = pdfGenerator.getViewScreenShot(contentLayout2);
        pdfGenerator.saveImageToPDF(contentLayout2, bitmap);
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
}