package com.example.smsreader;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_READ_SMS = 1;
    private ListView lvSms;
    private List<SmsInfo> smsList = new ArrayList<>();
    private SmsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvSms = findViewById(R.id.lv_sms);
        adapter = new SmsAdapter(this, smsList);
        lvSms.setAdapter(adapter);

        // Android 6.0+ 需要动态申请危险权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_SMS}, REQUEST_READ_SMS);
        } else {
            loadSms();
        }
    }

    /**
     * 动态权限申请回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_READ_SMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadSms();
            } else {
                Toast.makeText(this, "权限被拒绝，无法读取短信", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 利用内容解析者（ContentResolver）读取短信数据库
     * 短信内容提供者 URI：content://sms/inbox（收件箱）
     * 字段说明：
     *   address - 发送号码
     *   date    - 发送时间（毫秒时间戳）
     *   body    - 短信内容
     */
    private void loadSms() {
        smsList.clear();

        Uri smsUri = Uri.parse("content://sms/inbox");
        String[] projection = {"address", "date", "body"};

        Cursor cursor = getContentResolver().query(
                smsUri,
                projection,
                null,
                null,
                "date DESC"   // 按时间降序排列，最新的在最前面
        );

        if (cursor != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

            while (cursor.moveToNext()) {
                String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                long dateMs    = cursor.getLong(cursor.getColumnIndexOrThrow("date"));
                String body    = cursor.getString(cursor.getColumnIndexOrThrow("body"));

                // 将时间戳格式化为可读时间
                String dateStr = sdf.format(new Date(dateMs));

                smsList.add(new SmsInfo(address, dateStr, body));
            }
            cursor.close();
        }

        if (smsList.isEmpty()) {
            Toast.makeText(this, "收件箱暂无短信，请用DDMS发送测试短信后重启应用", Toast.LENGTH_LONG).show();
        }

        adapter.notifyDataSetChanged();
    }
}
