package com.example.map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private final static int REQUEST_PERMISSIONS = 133;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSIONS:
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        finish();  // 使用者不給權限的話就關掉App
                    } else {
                        SupportMapFragment map = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                        map.getMapAsync(this);
                    }
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 檢查定位權限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //要求權限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_PERMISSIONS);
        } else {
            //連接MapFragment物件
            SupportMapFragment map = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            map.getMapAsync(this);
        }

    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        //檢查權限
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        //顯示目前位置與目前位置的按鈕
        map.setMyLocationEnabled(true);

        //建立MarkerOption物件
        MarkerOptions m1 = new MarkerOptions();
        m1.position(new LatLng(25.033611, 121.565));
        m1.title("台北101");
        m1.draggable(true);
        map.addMarker(m1);

        MarkerOptions m2 = new MarkerOptions();
        m2.position(new LatLng(25.047924, 121.517081));
        m2.title("台北車站");
        m2.draggable(true);
        map.addMarker(m2);

        //加入PolylineOptions到googleMap
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.add(new LatLng(25.033611, 121.565));
        polylineOptions.add(new LatLng(25.032728, 121.565137));
        polylineOptions.add(new LatLng(25.047924, 121.517081));

        polylineOptions.color(Color.BLUE);
        Polyline polyline = map.addPolyline(polylineOptions);
        polyline.setWidth(10);

        //移動鏡頭
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(25.034, 121.545), 13));
    }
}