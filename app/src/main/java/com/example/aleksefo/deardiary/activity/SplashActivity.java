package com.example.aleksefo.deardiary.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import com.example.aleksefo.deardiary.R;

public class SplashActivity extends AppCompatActivity {

	private static final String TAG = SplashActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
			WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		ActionBar actionBar = getSupportActionBar();
		if (null != actionBar) {
			actionBar.hide();
		}
		ImageView tapImage = (ImageView) findViewById(R.id.tap_image);
		tapImage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent tapIntent = new Intent(SplashActivity.this, MainActivity.class);
				startActivity(tapIntent);
			}
		});
	}
}