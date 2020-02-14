package com.example.demoapp2;

import androidx.appcompat.app.AppCompatActivity;

//import android.content.Context;
//import android.hardware.usb.UsbDevice;
//import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import java.util.HashMap;
//import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    //    UsbManager mManager = (UsbManager) getSystemService(Context.USB_SERVICE);
//    HashMap<String, UsbDevice> deviceList = mManager.getDeviceList();
//    Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
    Button btnClose;
    Button btnOpen;
    EditText txtContent;
    HidBridge hid;
    boolean isRuning;

    void addText(String text) {
        txtContent.setText((txtContent.getText().toString() + "\n" + text.toString().toString()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnClose = findViewById(R.id.btnClose);
        btnOpen = findViewById(R.id.btnOpen);
        txtContent = findViewById(R.id.txtContent);
        txtContent.setText("Console...");
//        hid = new HidBridge(getApplicationContext(), 30264, 8208);
        hid = new HidBridge(getApplicationContext(), 4096,59473 );

        isRuning = true;
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    isRuning = false;
                    Toast.makeText(getApplicationContext(), "Ngừng đọc", Toast.LENGTH_LONG).show();
                    hid.StopReadingThread();
                    Toast.makeText(getApplicationContext(), "Đóng thiết bị", Toast.LENGTH_LONG).show();
                    hid.CloseTheDevice();
                } catch (Exception e) {
                    e.printStackTrace();
                    addText(e.toString());
                }

            }
        });
        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isOpen = false;
                Toast.makeText(getApplicationContext(), "Bắt đầu kết nối", Toast.LENGTH_LONG).show();
                addText("Thử mở kết nối");
                if (hid.OpenDevice()) {
                    addText("Mở kết nối thành công!");
                    addText("Mở luồng đọc data...");
                    hid.StartReadingThread();
                } else {
                    addText("Mở kết nối thất bại");
                }

//                while (isRuning) {
                    try {
                        if (hid.IsThereAnyReceivedData()) {
                            addText(("data" + new String(hid.GetReceivedDataFromQueue())));
                        }
                        addText(hid.getMessageLog());
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                        addText(e.toString());
                    }
//                }
            }
        });
    }
}
