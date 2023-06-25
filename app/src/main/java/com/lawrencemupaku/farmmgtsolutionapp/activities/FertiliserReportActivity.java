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
import com.lawrencemupaku.farmmgtsolutionapp.models.FertiliserApplication;
import com.lawrencemupaku.farmmgtsolutionapp.models.PdfGenerator;

public class FertiliserReportActivity extends AppCompatActivity {
    private TextView activityType,prePlanting,starterApp,folioApp,topDressing,doloping,other,reportDate;
    private Button btnPrint;
    private RelativeLayout contentLayout2;
    private String phNumber;
    private FertiliserApplication fertiliserApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_planting_report);

        Intent intent = getIntent();
        fertiliserApplication = (FertiliserApplication) intent.getSerializableExtra("fertiliser");
        phNumber = intent.getStringExtra("number").toString();
        activityType = findViewById(R.id.activityType);
        prePlanting = findViewById(R.id.prePlanting);
        starterApp = findViewById(R.id.starterApp);
        folioApp = findViewById(R.id.folioApp);
        topDressing = findViewById(R.id.topDressing);
        doloping = findViewById(R.id.doloping);
        other = findViewById(R.id.other);
        reportDate = findViewById(R.id.reportDate);
        btnPrint = findViewById(R.id.btnPrint);
        contentLayout2 = findViewById(R.id.contentLayout2);
        //requestPermission();

        Toolbar toolbar = findViewById(R.id.toolbarFields);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        if(fertiliserApplication.getPreplantingApp() == null){
            prePlanting.setText("N/A");
        }else {
            prePlanting.setText(fertiliserApplication.getPreplantingApp());
        }
        if(fertiliserApplication.getStarterApp() == null){
            starterApp.setText("N/A");
        }else {
            starterApp.setText(fertiliserApplication.getStarterApp());
        }
        if(fertiliserApplication.getDoloping() == null){
            doloping.setText("N/A");
        }else {
            doloping.setText(fertiliserApplication.getDoloping());
        }

        if(fertiliserApplication.getOther() == null){
            other.setText("N/A");
        }else {
            other.setText(fertiliserApplication.getOther());
        }

        if(fertiliserApplication.getTopDressing() == null){
            topDressing.setText("N/A");
        }else {
            topDressing.setText(fertiliserApplication.getTopDressing());
        }

        if(fertiliserApplication.getFolioApp() == null){
            folioApp.setText("N/A");
        }else {
            folioApp.setText(fertiliserApplication.getFolioApp());
        }

        if(fertiliserApplication != null){
            activityType.setText("Chemical Spray");
        }else{
            activityType.setText("N/A");
        }

        reportDate.setText(fertiliserApplication.getDate());


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
        Intent intent = new Intent(FertiliserReportActivity.this,ReportActivity.class);
        intent.putExtra("number",phNumber);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(FertiliserReportActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
    }

    private void convertToPdf() {
        PdfGenerator pdfGenerator = new PdfGenerator(FertiliserReportActivity.this);
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