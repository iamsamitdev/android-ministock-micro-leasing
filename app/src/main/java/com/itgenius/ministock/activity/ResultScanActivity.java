package com.itgenius.ministock.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.itgenius.ministock.R;

public class ResultScanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_scan);

        TextView result = findViewById(R.id.result);

        // รับค่าจาก ScanActivity
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            String barcode_data = bundle.getString("barcode");
            result.setText(barcode_data);
        }

    }
}
