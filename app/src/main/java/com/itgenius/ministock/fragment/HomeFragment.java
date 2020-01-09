package com.itgenius.ministock.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.itgenius.ministock.R;
import com.itgenius.ministock.activity.ScanActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment  {

    ImageView img;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // ---------------------------------------------------------------
        // เรียกกล้องถ่ายรูป
        // ------------------------------------------------------------------
        img = rootView.findViewById(R.id.imgView);
        Button btnCapture = rootView.findViewById(R.id.btnCapture);
        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getContext(),"Hello", Toast.LENGTH_SHORT).show();
                //  คำสั่งในการเรียกใช้งานกล้องถ่ายรูป
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera,1);
            }
        });

        // ---------------------------------------------------------
        // เรียกการ scan barcode/qrcode
        // ----------------------------------------------------------
        Button btnScan = rootView.findViewById(R.id.btnScan);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ScanActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    // แสดงรูปภาพที่ถ่ายแล้ว
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == 1){
            Bitmap image = (Bitmap) data.getExtras().get("data");
            img.setImageBitmap(image);
        }

    }
}
