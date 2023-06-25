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
import com.lawrencemupaku.farmmgtsolutionapp.models.FieldModel;
import com.lawrencemupaku.farmmgtsolutionapp.models.LandPreparation;
import com.lawrencemupaku.farmmgtsolutionapp.models.PdfGenerator;

public class LandPreparationReportActivity extends AppCompatActivity {
    private TextView activityType,plough,discHarrow,ripper,reportDate;
    private Button btnPrint;
    private RelativeLayout contentLayout1;
    private String phNumber;
    private LandPreparation landPreparation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report2);


        Intent intent = getIntent();
        landPreparation = (LandPreparation) intent.getSerializableExtra("land");
        phNumber = intent.getStringExtra("number").toString();
        activityType = findViewById(R.id.activityType);
        plough = findViewById(R.id.plough);
        discHarrow = findViewById(R.id.discHarrow);
        ripper = findViewById(R.id.ripper);
        reportDate = findViewById(R.id.reportDate);
        btnPrint = findViewById(R.id.btnPrint);
        contentLayout1 = findViewById(R.id.contentLayout1);

        Toolbar toolbar = findViewById(R.id.toolbarFields);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        requestPermission();

        if(landPreparation.getPlough() == null){
            plough.setText("N/A");
        }else {
            plough.setText(landPreparation.getPlough());
        }

        if(landPreparation.getDisc_harrow() == null){
            discHarrow.setText("N/A");
        }else {
            discHarrow.setText(landPreparation.getDisc_harrow());
        }

        if(landPreparation.getRipper() == null){
            ripper.setText("N/A");
        }else {
            ripper.setText(landPreparation.getRipper());
        }

        if(landPreparation.getDate() == null){
            reportDate.setText("N/A");
        }else {
            reportDate.setText(landPreparation.getDate());
        }

        if(landPreparation != null){
            activityType.setText("Land Preparation");
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
        Intent intent = new Intent(LandPreparationReportActivity.this,ReportActivity.class);
        intent.putExtra("number",phNumber);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(LandPreparationReportActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
    }

    private void convertToPdf() {
        PdfGenerator pdfGenerator = new PdfGenerator(LandPreparationReportActivity.this);
        // llLayoutToPdfMain is variable of that view which convert to PDF
        Bitmap bitmap = pdfGenerator.getViewScreenShot(contentLayout1);
        pdfGenerator.saveImageToPDF(contentLayout1, bitmap);
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