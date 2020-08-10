package com.szubov.android_hw_122;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.io.File;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class Settings extends AppCompatActivity {

    private EditText mFilePathEdTx;
    public static final String SETTINGS = "ru.sergeyzubov.android-hw-122.settings";
    public static final int REQUEST_CODE_PERMISSION_READ_STORAGE = 11;
    public static final String TAG = "My app";
    public static final String BACKGROUND_DEFAULT = "default";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        mFilePathEdTx = findViewById(R.id.editTextFilePath);
        Button mBtnPathFileOk = findViewById(R.id.btnFilePathOk);

        mBtnPathFileOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Settings -> btnPathFileOk -> onClick");
                checkPermissionStatus();
                String filePath = checkFilePathEditText(mFilePathEdTx.getText().toString().trim());

                if (filePath != null) {
                    Intent answerIntent = new Intent().putExtra(SETTINGS, filePath);
                    setResult(RESULT_OK, answerIntent);
                    finish();
                }
            }
        });

        Button mBtnBackgroundDefault = findViewById(R.id.btnBackgroundDefault);

        mBtnBackgroundDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Settings -> btnBackgroundDefault -> onClick");
                Intent answerIntent = new Intent().putExtra(SETTINGS, BACKGROUND_DEFAULT);
                setResult(RESULT_OK, answerIntent);
                finish();
            }
        });

        Button mBtnPathFileCancel = findViewById(R.id.btnFilePathCancel);

        mBtnPathFileCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Settings -> btnPathFileCancel -> onClick");
                finish();
            }
        });
    }

    public String checkFilePathEditText(String picName) {
        Log.d(TAG, "Settings -> checkFilePathEditText");
        if (!isExternalStorageReadable()) {
            Toast.makeText(Settings.this, getString(R.string.external_storage_not_available),
                    Toast.LENGTH_LONG).show();
            return null;
        } else {
            if (picName.length() <= 0) {
                Toast.makeText(this, R.string.file_name_string_is_empty, Toast.LENGTH_LONG).show();
                return null;
            } else {
                File file = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS),picName);
                if (!file.exists()) {
                    Toast.makeText(this, R.string.file_not_available, Toast.LENGTH_LONG).show();
                    return null;
                }
                return file.toString();
            }
        }
    }

    public void checkPermissionStatus() {
        Log.d(TAG, "Settings -> checkPermissionStatus");
        int permissionStatus = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_PERMISSION_READ_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "Settings -> onRequestPermissionsResult");
        if (requestCode == REQUEST_CODE_PERMISSION_READ_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, R.string.toast_app_need_read_permission,
                        Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    public boolean isExternalStorageReadable() {
        Log.d(TAG, "Settings -> checkFilePathEditText -> isExternalStorageReadable");
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState());
    }
}
