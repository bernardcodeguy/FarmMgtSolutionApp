package com.lawrencemupaku.farmmgtsolutionapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lawrencemupaku.farmmgtsolutionapp.R;

public class TransactionActivity extends AppCompatActivity {
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://farm-solutions-faca6-default-rtdb.firebaseio.com/").child("users");
    private ConstraintLayout constraintSalesRevenue,constraintInventory,constraintExpenses;
    private String phNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        Intent i = getIntent();
        phNumber = i.getStringExtra("number").toString();

        constraintSalesRevenue = findViewById(R.id.constraintSalesRevenue);
        constraintInventory = findViewById(R.id.constraintInventory);
        constraintExpenses = findViewById(R.id.constraintExpenses);

        Toolbar toolbar = findViewById(R.id.toolbarFields);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        constraintSalesRevenue.setOnClickListener(e ->{
            Intent intent = new Intent(TransactionActivity.this,SalesRevenueActivity.class);
            intent.putExtra("number",phNumber);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        });

        constraintInventory.setOnClickListener(e ->{
            Intent intent = new Intent(TransactionActivity.this,InventoryActivity.class);
            intent.putExtra("number",phNumber);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        constraintExpenses.setOnClickListener(e ->{

            Intent intent = new Intent(TransactionActivity.this,ExpensesActivity.class);
            intent.putExtra("number",phNumber);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
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
        Intent intent = new Intent(TransactionActivity.this,MainActivity.class);
        intent.putExtra("number",phNumber);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}