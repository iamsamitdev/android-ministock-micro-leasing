package com.itgenius.ministock.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.Result;
import com.itgenius.ministock.R;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private  ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        // คำสั่งอนุญาติให้ user เปิดใช้งานกล้อง
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[] {Manifest.permission.CAMERA}, 1);
            }
        }

        mScannerView = findViewById(R.id.zxscan);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        //  แสดงข้อมูลที่ได้จากการ Scan
        Toast.makeText(getApplicationContext(),result.getText(), Toast.LENGTH_LONG).show();

        // การ Intent ไปยัง URL บนเว็บ
        /*
        String url = result.getText();
        Intent itent = new Intent(Intent.ACTION_VIEW);
        itent.setData(Uri.parse(url));
        startActivity(itent);
         */

        // การส่งข้อมูลไปแสดงที่หน้าอื่น
        Intent intent = new Intent(ScanActivity.this, ResultScanActivity.class);
        intent.putExtra("barcode", result.getText());
        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
