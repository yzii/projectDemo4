package com.example.projectdemo4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

public class MainActivity extends Activity {
	private Button camerabtn;  //Ã·Ωª∞¥≈•

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		camerabtn = (Button) findViewById(R.id.camerabtn);		
		camerabtn.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, NextActivity.class);
				//intent.setClass(MainActivity.this, NextActivity.class);
				startActivity(intent);
			}			
		});
	}
}
