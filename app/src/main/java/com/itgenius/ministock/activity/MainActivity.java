package com.itgenius.ministock.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.itgenius.ministock.R;
import com.itgenius.ministock.fragment.AccountFragment;
import com.itgenius.ministock.fragment.HomeFragment;
import com.itgenius.ministock.fragment.NotificationFragment;
import com.itgenius.ministock.fragment.ProductFragment;
import com.itgenius.ministock.fragment.ReportFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // Toolbar
    Toolbar toolbar;
    BottomNavigationView bottomNavigationView; // เมนูแท๊บด้านล่าง

    // Drawer Layout
    DrawerLayout drawerLayout;

    // NavigationView
    NavigationView navigationView; // เมนูด้านข้าง

    // Actionbar DrawerToggle
    ActionBarDrawerToggle actionBarDrawerToggle;

    // SharedPreference
    SharedPreferences pref;

    // Fragment
    final Fragment homeFragment = new HomeFragment();
    final Fragment productFragment = new ProductFragment();
    final Fragment reportFragment = new ReportFragment();
    final Fragment notificationFrament = new NotificationFragment();
    final Fragment accountFragment = new AccountFragment();

    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); // ใช้ Toolbar แทน Actionbar
        getSupportActionBar().setTitle(R.string.nav_home);

        // เปิดแสดงผลปุ่ม Home
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // เรียก Drawer Menu
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this,
                drawerLayout, 0, 0);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        // คำสั่งแสดงแท๊บเเมนูด้านล่าง
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        // คำสั่ง Event click BottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // การเรียกใช้งาน Fragment
        fm.beginTransaction().add(R.id.content_main, homeFragment, "1").commit();
        fm.beginTransaction().add(R.id.content_main, productFragment, "2").hide(productFragment).commit();
        fm.beginTransaction().add(R.id.content_main, reportFragment, "3").hide(reportFragment).commit();
        fm.beginTransaction().add(R.id.content_main, notificationFrament, "4").hide(notificationFrament).commit();
        fm.beginTransaction().add(R.id.content_main, accountFragment, "5").hide(accountFragment).commit();


        // การเรียกใช้เมนูด้านข้าง
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();

    }

    // -------------------------------------------------------------------------------------------------------
    // ฟังก์ชันในการเปิดปุ่ม home และแสดงเมนูด้านข้าง
    // -------------------------------------------------------------------------------------------------------
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // -------------------------------------------------------------------------------------------------------
    // จบฟังก์ชันการเรียกเมนูด้านข้าง
    // -------------------------------------------------------------------------------------------------------


    // -------------------------------------------------------------------------------------------------------
    // ฟังก์ชันในการเเปลี่ยนหน้า Fragment
    // -------------------------------------------------------------------------------------------------------
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId()){
                case R.id.nav_home:
                    fm.beginTransaction().hide(active).show(homeFragment).commit();
                    active = homeFragment;
                    getSupportActionBar().setTitle(R.string.nav_home);
                    return true;
                case R.id.nav_product:
                    fm.beginTransaction().hide(active).show(productFragment).commit();
                    active = productFragment;
                    getSupportActionBar().setTitle(R.string.nav_product);
                    return true;
                case R.id.nav_report:
                    fm.beginTransaction().hide(active).show(reportFragment).commit();
                    active = reportFragment;
                    getSupportActionBar().setTitle(R.string.nav_report);
                    return true;
                case R.id.nav_notification:
                    fm.beginTransaction().hide(active).show(notificationFrament).commit();
                    active = notificationFrament;
                    getSupportActionBar().setTitle(R.string.nav_notification);
                    return true;
                case R.id.nav_account:
                    fm.beginTransaction().hide(active).show(accountFragment).commit();
                    active = accountFragment;
                    getSupportActionBar().setTitle(R.string.nav_account);
                    return true;
            }

            return false;
        }
    };


    // -------------------------------------------------------------------------------------------------------
    // ฟังก์ชัน Event Click เมนูด้านข้าง
    // -------------------------------------------------------------------------------------------------------
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        // About us
        if(id==R.id.side_about){

            // ปิดเมนูด้านข้าง
            drawerLayout.closeDrawer(GravityCompat.START);

            // เปิดหน้า About
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        }

        // Logout
        if(id == R.id.side_signout){

            // Logout ออกจากระบบ
            // Clear SharedPreference
            pref = getSharedPreferences("pref_login", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.remove("pref_userid");
            editor.commit();

            // กลับไปหน้า Login
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);

        }

        return true;
    }

}
