package com.mingle.myapplication.activity;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.ToggleButton;

import com.mingle.myapplication.R;
import com.mingle.myapplication.TriToggleButton;
import com.mingle.myapplication.model.SharedPreferenceUtil;
import com.mingle.myapplication.service.CallService;
import com.mingle.myapplication.severcall.Servercall;
import com.mingle.sweetpick.BlurEffect;
import com.mingle.sweetpick.CustomDelegate;
import com.mingle.sweetpick.SweetSheet;


public class ResionCinemaActivity extends AppCompatActivity {
        private SweetSheet mSweetSheet3;
        private RelativeLayout rl;

        Toolbar toolbar;
        Toolbar bottombar;
        Button homeButton;
        Button exhibitButton;
        Button libraryButton;
        ToggleButton bottomToggleButton;
        ImageView cinema_back;
        ImageView cinema_icon;
        ImageView cinema_edge;
        Bitmap bitmap;
        Bitmap bitmap2;
        Bitmap bitmap3;
        SeekBar seekBar;
        Switch callServiceSwitchBtn;
        Switch messgeSwitchBtn;
        Switch wifiSwitchBtn;
        AudioManager audioManager;
        ComponentName mCallService;
        int callFrag=0;
        Button saveBtn;
        WifiManager mWifiManager;

        String cinema;
        Servercall servercall;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_resion_cinema);

                servercall=new Servercall();
                cinema="cinema";
                servercall.postResioninfo(getApplicationContext(), cinema);

                final Animation animRotate = AnimationUtils.loadAnimation(this, R.anim.anim_rotate);
                audioManager = (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);

                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cinema);
                bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.cinema_edge);
                bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.cinema_icon);

                cinema_back = (ImageView) findViewById(R.id.cinema_back);
                cinema_edge = (ImageView) findViewById(R.id.cinema_edge);
                cinema_icon = (ImageView)findViewById(R.id.cinema_icon);

                cinema_back.setImageBitmap(bitmap);
                cinema_edge.setImageBitmap(bitmap2);
                cinema_edge.setAnimation(animRotate);
                cinema_icon.setImageBitmap(bitmap3);

                homeButton=(Button)findViewById(R.id.home_btn);
                homeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                Intent home = new Intent(getApplicationContext(), MainActivity.class);
                                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(home);
                                finish();
                        }
                });

                libraryButton=(Button)findViewById(R.id.library_btn);
                libraryButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                Intent library = new Intent(getApplicationContext(), RegionLibraryActivity.class);
                                library.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(library);
                                finish();
                        }
                });
                exhibitButton=(Button)findViewById(R.id.exhibition_btn);
                exhibitButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                Intent exhibition = new Intent(getApplicationContext(), ResionExhibitionActivity.class);
                                exhibition.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(exhibition);
                                finish();
                        }
                });


                rl = (RelativeLayout) findViewById(R.id.rl);
                setupCustomView();

                toolbar = (Toolbar) findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);
                //noinspection ConstantConditions
                getSupportActionBar().setDisplayShowCustomEnabled(true);
                getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                getSupportActionBar().setCustomView(getLayoutInflater().inflate(R.layout.actionbar_layout, null),
                        new ActionBar.LayoutParams(
                                ActionBar.LayoutParams.WRAP_CONTENT,
                                ActionBar.LayoutParams.MATCH_PARENT,
                                Gravity.CENTER
                        )
                );

                bottombar = (Toolbar) findViewById(R.id.bottombar);
                setSupportActionBar(bottombar);
                getSupportActionBar().setDisplayShowCustomEnabled(true);
                getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                getSupportActionBar().setCustomView(getLayoutInflater().inflate(R.layout.bottombar_layout, null),
                        new ActionBar.LayoutParams(
                                ActionBar.LayoutParams.WRAP_CONTENT,
                                ActionBar.LayoutParams.MATCH_PARENT,
                                Gravity.CENTER
                        )
                );
                bottomToggleButton = (ToggleButton) findViewById(R.id.bottomToggleButton);
                bottomToggleButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                if (bottomToggleButton.isChecked()) {
                                        mSweetSheet3.show();
                                } else {
                                        mSweetSheet3.dismiss();
                                }
                        }
                });

                Settings.System.putInt(getContentResolver(), "screen_brightness",
                        SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "CinemaBrightness"));
                audioManager.setRingerMode(
                        SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "CinemaRingerMode"));

                Log.d("SharedPreferenceUtil 1", "Resion Cinema: " + SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "CinemaBrightness"));
                Log.d("SharedPreferenceUtil 1", "Resion Cinema: " + SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "CinemaRingerMode"));


                SharedPreferenceUtil.putSharedPreference(getApplicationContext(), "CallServiceFrag", 1);
                mCallService = startService(new Intent(this, CallService.class));
                callFrag=1;


        }
        protected void onNewIntent(Intent intent){super.onNewIntent(intent); }

        @Override
        protected void onStart() {super.onStart();}

        @Override
        protected void onResume() {
                super.onResume();
        }

        @Override
        protected void onPause() {
                super.onPause();
        }

        @Override
        protected void onDestroy() {
                super.onDestroy();
                bitmap.recycle();
                bitmap2.recycle();
                bitmap3.recycle();
                callFrag=0;
        }

        private void setupCustomView() {
                mSweetSheet3 = new SweetSheet(rl);
                CustomDelegate customDelegate = new CustomDelegate(true,
                        CustomDelegate.AnimationType.AlphaAnimation);
                View view = LayoutInflater.from(this).inflate(R.layout.layout_custom_view, null, false);
                customDelegate.setCustomView(view);
                customDelegate.setSweetSheetColor(getResources().getColor(R.color.colorBottomtab));
                mSweetSheet3.setDelegate(customDelegate);
                mSweetSheet3.setBackgroundEffect(new BlurEffect(8));
                mSweetSheet3.setBackgroundClickEnable(false);
                view.findViewById(R.id.triToggleButton).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                switch (TriToggleButton.getState()) {
                                        case 0:
                                                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                                                SharedPreferenceUtil.putSharedPreference(getApplicationContext(), "CinemaRingerMode", AudioManager.RINGER_MODE_SILENT);
                                                break;
                                        case 1:
                                                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                                                SharedPreferenceUtil.putSharedPreference(getApplicationContext(), "CinemaRingerMode", AudioManager.RINGER_MODE_VIBRATE);
                                                break;
                                        case 2:
                                                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                                                SharedPreferenceUtil.putSharedPreference(getApplicationContext(), "CinemaRingerMode", AudioManager.RINGER_MODE_NORMAL);
                                                break;
                                        default:
                                                break;
                                }
                        }
                });

                seekBar = (SeekBar) view.findViewById(R.id.custom_seek);
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        float brightness = 0;

                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                if (progress < 10) {
                                        progress = 10;
                                        seekBar.setProgress(progress);
                                }

                                WindowManager.LayoutParams params = getWindow().getAttributes();
                                params.screenBrightness = (float) progress / 100;
                                brightness = params.screenBrightness;
                                getWindow().setAttributes(params);
                                Log.d("SharedPreferenceUtil 3", "Resion Cinema: " + SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "CinemaBrightness"));
                                Log.d("SharedPreferenceUtil 3", "Resion Cinema: " + SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "CinemaRingerMode"));

                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                                brightness = brightness * 255;
                                Settings.System.putInt(getContentResolver(), "screen_brightness", (int) brightness);
                                SharedPreferenceUtil.putSharedPreference(getApplicationContext(), "CinemaBrightness", (int) brightness);
                        }
                });
                seekBar.setProgress(
                        SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "CinemaBrightness") * 100 / 255);
                Log.d("SharedPreferenceUtil 2", "Resion Cinema: " + SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "CinemaBrightness"));
                Log.d("SharedPreferenceUtil 2", "Resion Cinema: " + SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "CinemaRingerMode"));
                callServiceSwitchBtn = (Switch)view.findViewById(R.id.switch1);
                messgeSwitchBtn = (Switch)view.findViewById(R.id.switch2);
                if(SharedPreferenceUtil.getSharedPreference(getApplicationContext(), "CinemaChecked")==1) callServiceSwitchBtn.setChecked(true);
                else callServiceSwitchBtn.setChecked(false);

                callServiceSwitchBtn.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                        if (callFrag == 0) {
                                                initiateService();
                                        }
                                        callFrag = 1;
                                        SharedPreferenceUtil.putSharedPreference(getApplicationContext(), "CinemaChecked", 1);
                                        messgeSwitchBtn.setEnabled(true);

                                } else {
                                        terminateService();
                                        callFrag = 0;
                                        SharedPreferenceUtil.putSharedPreference(getApplicationContext(), "CinemaChecked", 0);
                                        messgeSwitchBtn.setEnabled(false);
                                }

                        }
                });
                messgeSwitchBtn.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                        SharedPreferenceUtil.putSharedPreference(getApplicationContext(), "MessageChecked", 1);
                                } else {
                                        SharedPreferenceUtil.putSharedPreference(getApplicationContext(), "MessageChecked", 0);
                                }
                        }
                });
                mWifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
                wifiSwitchBtn = (Switch)view.findViewById(R.id.switch3);
                wifiSwitchBtn.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                        mWifiManager.setWifiEnabled(true);
                                }
                                else {
                                        mWifiManager.setWifiEnabled(false);
                                }
                        }
                });


        }

        public void initiateService() {
                SharedPreferenceUtil.putSharedPreference(getApplicationContext(), "CallServiceFrag", 1);
                mCallService = startService(new Intent(this, CallService.class));
        }

        public void terminateService() {
                if(mCallService==null) {
                        return;
                }
                Intent i = new Intent();
                i.setComponent(mCallService);
                stopService(i);

        }


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
                //getMenuInflater().inflate(R.menu.menu_main, menu);
                return true;
        }

        @Override
        public void onBackPressed() {

                if (mSweetSheet3.isShow()) {
                        mSweetSheet3.dismiss();
                } else {
                        super.onBackPressed();
                }
                bottomToggleButton.setChecked(false);

        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {

                return super.onOptionsItemSelected(item);
        }
}