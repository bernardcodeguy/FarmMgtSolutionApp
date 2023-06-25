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
import com.lawrencemupaku.farmmgtsolutionapp.models.PdfGenerator;
import com.lawrencemupaku.farmmgtsolutionapp.models.Planting;
import com.lawrencemupaku.farmmgtsolutionapp.models.WeedingProgram;

public class WeedingProgramReportActivity extends AppCompatActivity {
    private TextView activityType,herbicides,manualRemoval,reportDate;
    private Button btnPrint;
    private RelativeLayout contentLayout2;
    private String phNumber;
    private WeedingProgram weedingProgram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weeding_program_report);

        Intent intent = getIntent();
        weedingProgram = (WeedingProgram) intent.getSerializableExtra("weeding");
        phNumber = intent.getStringExtra("number").toString();
        activityType = findViewById(R.id.activityType);
        herbicides = findViewById(R.id.herbicides);
        manualRemoval = findViewById(R.id.manualRemoval);

        reportDate = findViewById(R.id.reportDate);
        btnPrint = findViewById(R.id.btnPrint);
        contentLayout2 = findViewById(R.id.contentLayout2);

        Toolbar toolbar = findViewById(R.id.toolbarFields);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        requestPermission();

        if(weedingProgram.getHerbicides() == null){
            herbicides.setText("N/A");
        }else {
            herbicides.setText(weedingProgram.getHerbicides());
        }

        if(weedingProgram.getManualRemoval() == null){
            manualRemoval.setText("N/A");
        }else {
            manualRemoval.setText(weedingProgram.getManualRemoval());
        }

        if(weedingProgram.getDate() == null){
            reportDate.setText("N/A");
        }else {
            reportDate.setText(weedingProgram.getDate());
        }

        if(weedingProgram != null){
            activityType.setText("Weeding Program");
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
        Intent intent = new Intent(WeedingProgramReportActivity.this,ReportActivity.class);
        intent.putExtra("number",phNumber);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(WeedingProgramReportActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
    }

    private void convertToPdf() {
        PdfGenerator pdfGenerator = new PdfGenerator(WeedingProgramReportActivity.this);
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