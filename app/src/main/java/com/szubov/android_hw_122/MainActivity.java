package com.szubov.android_hw_122;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTextViewValues;
    private View mStandardCalc;
    private View mExtendedCalc;
    private ImageView mBackground;
    private static final String SETTINGS_FILE_NAME = "userSettings";
    private static final int BACKGROUND_CHOICE = 0;
    private static final String TAG = "My app";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mToolbar = findViewById(R.id.toolbarStandCalc);
        setSupportActionBar(mToolbar);

        initViews();

        loadUserSettings();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "MainActivity -> onCreateOptionsMenu");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "MainActivity -> onOptionItemSelected");
        if (item.getItemId() == R.id.menuSettings) {
            Intent settingsIntent = new Intent(MainActivity.this, Settings.class);
            startActivityForResult(settingsIntent, BACKGROUND_CHOICE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "MainActivity -> onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BACKGROUND_CHOICE) {
            if (resultCode == RESULT_OK) {
                try {
                    String string = data.getStringExtra(Settings.SETTINGS);
                    saveUserSettings(string);
                    loadUserSettings();
                } catch (Exception ex) {
                    ex.getStackTrace();
                    Log.e(TAG, "MainActivity -> onActivityResult", ex);
                }

            }
        }
    }

    public void saveUserSettings(String pathFile) {
        Log.d(TAG, "MainActivity -> saveUserSettings");
        if (isExternalStorageMounted()) {
            if (pathFile != null) {
                try {
                    File file = new File(this.getExternalFilesDir(null), SETTINGS_FILE_NAME);
                    BufferedWriter bw = new BufferedWriter(new FileWriter(file, false));
                    bw.append(pathFile);
                    bw.close();
                } catch (IOException ex) {
                    Log.e(TAG, "MainActivity -> saveUserSettings", ex);
                    ex.printStackTrace();
                }
            }
        } else {
            Log.e(TAG, "MainActivity -> loadUserSettings -> isExternalStorageMounted");
            Toast.makeText(this, R.string.external_storage_not_available, Toast.LENGTH_LONG).show();
        }
    }

    public void loadUserSettings() {
        Log.d(TAG, "MainActivity -> loadUserSettings");
        File file = new File(this.getExternalFilesDir(null), SETTINGS_FILE_NAME);
        if (isExternalStorageMounted()) {
            if (file.exists()) {
                try {
                    if (file.length() > 0) {
                        BufferedReader br = new BufferedReader(new FileReader(file));
                        String string = br.readLine();
                        if (!string.equals("default")) {
                            Bitmap image = BitmapFactory.decodeFile(string);
                            mBackground.setImageBitmap(image);
                            mBackground.setVisibility(View.VISIBLE);
                            br.close();
                        } else {
                            mBackground.setBackgroundResource(0);
                            mBackground.setVisibility(View.INVISIBLE);
                        }
                    }
                } catch (IOException ex) {
                    Log.e(TAG, "MainActivity -> loadUserSettings");
                    ex.printStackTrace();
                }
            }
        } else {
            Log.e(TAG, "MainActivity -> loadUserSettings -> isExternalStorageMounted");
            Toast.makeText(this, R.string.external_storage_not_available, Toast.LENGTH_LONG).show();
        }
    }


    private void initViews() {
        Log.d(TAG, "MainActivity -> initViews");
        mTextViewValues = findViewById(R.id.textViewValues);
        mStandardCalc = findViewById(R.id.standardCalc);
        mExtendedCalc = findViewById(R.id.extendedCalc);
        mBackground = findViewById(R.id.imageViewBackground);

        findViewById(R.id.btnActionSwitchMode).setOnClickListener(this);
        findViewById(R.id.btnActionSwitchModeExt).setOnClickListener(this);
        findViewById(R.id.btnActionAddition).setOnClickListener(this);
        findViewById(R.id.btnActionChangeOfSign).setOnClickListener(this);
        findViewById(R.id.btnActionDivision).setOnClickListener(this);
        findViewById(R.id.btnActionEqually).setOnClickListener(this);
        findViewById(R.id.btnActionMultiplication).setOnClickListener(this);
        findViewById(R.id.btnActionPercent).setOnClickListener(this);
        findViewById(R.id.btnActionSubtraction).setOnClickListener(this);
        findViewById(R.id.btnActionCleanAll).setOnClickListener(this);
        findViewById(R.id.btnPoint).setOnClickListener(this);
        findViewById(R.id.btnOne).setOnClickListener(this);
        findViewById(R.id.btnTwo).setOnClickListener(this);
        findViewById(R.id.btnThree).setOnClickListener(this);
        findViewById(R.id.btnFour).setOnClickListener(this);
        findViewById(R.id.btnFive).setOnClickListener(this);
        findViewById(R.id.btnSix).setOnClickListener(this);
        findViewById(R.id.btnSeven).setOnClickListener(this);
        findViewById(R.id.btnEight).setOnClickListener(this);
        findViewById(R.id.btnNine).setOnClickListener(this);
        findViewById(R.id.btnZero).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "MainActivity -> calcButtons -> onClick");
        switch (v.getId()) {
            case R.id.btnActionSwitchMode:
                mStandardCalc.setVisibility(View.INVISIBLE);
                mExtendedCalc.setVisibility(View.VISIBLE);
                break;
            case R.id.btnActionSwitchModeExt:
                mExtendedCalc.setVisibility(View.INVISIBLE);
                mStandardCalc.setVisibility(View.VISIBLE);
                break;
            case R.id.btnActionAddition:
                mTextViewValues.setText(R.string.btn_action_addition);
                break;
            case R.id.btnActionChangeOfSign:
                mTextViewValues.setText(R.string.btn_action_change_of_sign);
                break;
            case R.id.btnActionDivision:
                mTextViewValues.setText(R.string.btn_action_division);
                break;
            case R.id.btnActionEqually:
                mTextViewValues.setText(R.string.btn_action_equally);
                break;
            case R.id.btnActionMultiplication:
                mTextViewValues.setText(R.string.btn_action_multiplication);
                break;
            case R.id.btnActionPercent:
                mTextViewValues.setText(R.string.btn_action_percent);
                break;
            case R.id.btnActionSubtraction:
                mTextViewValues.setText(R.string.btn_action_subtraction);
                break;
            case R.id.btnActionCleanAll:
                mTextViewValues.setText(R.string.text_view_action_reset);
                break;
            case R.id.btnPoint:
                mTextViewValues.setText(R.string.btn_point);
                break;
            case R.id.btnOne:
                mTextViewValues.setText(R.string.btn_one);
                break;
            case R.id.btnTwo:
                mTextViewValues.setText(R.string.btn_two);
                break;
            case R.id.btnThree:
                mTextViewValues.setText(R.string.btn_three);
                break;
            case R.id.btnFour:
                mTextViewValues.setText(R.string.btn_four);
                break;
            case R.id.btnFive:
                mTextViewValues.setText(R.string.btn_five);
                break;
            case R.id.btnSix:
                mTextViewValues.setText(R.string.btn_six);
                break;
            case R.id.btnSeven:
                mTextViewValues.setText(R.string.btn_seven);
                break;
            case R.id.btnEight:
                mTextViewValues.setText(R.string.btn_eight);
                break;
            case R.id.btnNine:
                mTextViewValues.setText(R.string.btn_nine);
                break;
            case R.id.btnZero:
                mTextViewValues.setText(R.string.btn_zero);
                break;
        }
    }

    public boolean isExternalStorageMounted() {
        Log.d(TAG, "MainActivity -> checkFilePathEditText -> isExternalStorageReadable");
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState());
    }
}