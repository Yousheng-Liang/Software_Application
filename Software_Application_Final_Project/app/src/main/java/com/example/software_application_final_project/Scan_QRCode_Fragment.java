package com.example.software_application_final_project;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeMap;

public class Scan_QRCode_Fragment extends Fragment {

    /**
     * 在Fragment中，以往在Activity內寫成 this 的部分，皆須使用 getActivity() 取代
     **/

    // 物件變數
    View view;
    SurfaceView surfaceView;
    TextView tvRead;

    // 用以儲存發票資料之相關變數
    String RECEIPT_DATA = "RECEIPT_DATA";
    Gson gson;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TreeMap<String, HashSet<String>> map; // 內層的map(日期)
    TreeMap<String, TreeMap<String, HashSet<String>>> map2; // 外層的map(年分)
    ArrayList<TreeMap<String, TreeMap<String, HashSet<String>>>> arrayList;

    // 宣告Google相機所需變數
    CameraSource cameraSource;
    BarcodeDetector barcodeDetector;


    @Override
    public void onStop() {
        super.onStop();
        // Destroy時，提交editor的改動
        try {
            arrayList.add(map2);
            String jsonData = gson.toJson(arrayList);
            editor.putString(RECEIPT_DATA, jsonData);
            editor.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /** Fragment中，須將這個view先抓出來才能使用 view.findViewById **/
        view = inflater.inflate(R.layout.fragment_scan__q_r_code_, container, false);

        surfaceView = view.findViewById(R.id.surfaceView);
        tvRead = view.findViewById(R.id.tvRead);

        myInit();

        // 建立相機物件之builder
        barcodeDetector = new BarcodeDetector.Builder(getActivity()).setBarcodeFormats(Barcode.QR_CODE).build();
        cameraSource = new CameraSource.Builder(getActivity(), barcodeDetector).setAutoFocusEnabled(true).build();

        // 將相機放入SurfaceView
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
                try {
                    // 檢查權限(cameraSource.start()方法需要，但其實我們已經檢查完了)
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    cameraSource.start(surfaceHolder); // 將相機抓到的畫面放入SurfaceView
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
                // 關閉相機
                cameraSource.stop();
            }
        });


        // 讀取QR Code
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                SparseArray<Barcode> QRCodes = detections.getDetectedItems();

                if (QRCodes.size() != 0) {
                    tvRead.post(new Runnable() {
                        @Override
                        public void run() {
                            // 取得抓到的完整字串
                            String strGet = QRCodes.valueAt(0).displayValue;
                            if (strGet.charAt(0) != '*' && strGet.length() > 2) {

                                // 分離出發票號碼(第0~9個字元)
                                String receipt_num = strGet.substring(0, 2) + "-" + strGet.substring(2, 10);
                                // 分離出發票年度(第10~12個字元)
                                String receipt_year = strGet.substring(10, 13);
                                // 分離出發票日期(第13~16個字元)
                                String receipt_date = strGet.substring(13, 15) + "/" + strGet.substring(15, 17);


                                tvRead.setText("發票號碼: " + receipt_num + "\n" + "發票日期: " + receipt_date);

                                // 依照年份取出內層map
                                map = map2.get(receipt_year);

                                if (map == null) {
                                    map = new TreeMap<>();
                                }

                                // 如果沒有儲存過這天的發票
                                if (!map.containsKey(receipt_date)) {
                                    // 直接新增一個HashSet並塞入map
                                    HashSet<String> tmp_set = new HashSet<>();
                                    tmp_set.add(receipt_num);
                                    map.put(receipt_date, tmp_set);
                                } else {  // 若已經儲存過
                                    // 則先撈出原本的資料放入HashSet
                                    HashSet<String> tmp_set = map.get(receipt_date);
                                    // 然後將新的發票加進去
                                    tmp_set.add(receipt_num);
                                    // 再塞回map中
                                    map.put(receipt_date, tmp_set);
                                }

                                // 將內層map的內容塞入外層map
                                map2.put(receipt_year, map);

                            }

                        }
                    });
                }
            }
        });

        return view;
    }

    private void myInit() {
        // 讓json資料能夠正常顯示:及=，而不是轉成unicode編碼
        gson = new GsonBuilder().disableHtmlEscaping().create();
        // 初始化SharedPreferences及HashMap等參數
        sharedPreferences = getActivity().getSharedPreferences(RECEIPT_DATA, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        map = new TreeMap<>();
        map2 = new TreeMap<>();
        arrayList = new ArrayList<>();

        // 讀取SharedPreferences中的Json格式資料並放入map2中(因為是外層map)
        String data = sharedPreferences.getString(RECEIPT_DATA, "[{}]");
        if (data.charAt(0) == '[')  // 拆掉最外層的中括號，以避免格式錯誤
            data = data.substring(1, data.length() - 1);

        map2 = new Gson().fromJson(data, new TypeToken<TreeMap<String, TreeMap<String, HashSet<String>>>>() {
        }.getType());

    }
}