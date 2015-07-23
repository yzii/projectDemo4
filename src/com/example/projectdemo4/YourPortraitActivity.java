package com.example.projectdemo4;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.*;

@SuppressLint("ShowToast")
public class YourPortraitActivity extends Activity {
	private ImageView imgV;
	private String FILE_FOLDER="/wolai_img/";      //大头合成照保存的文件夹
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_your_portrait);
		imgV = (ImageView) findViewById(R.id.compount_image);
		Intent in = getIntent();
		if(in != null){
			
		/*byte[] bis = in.getByteArrayExtra("bitmap");
		System.out.println("-------CameraDemo bis length="+bis.length);
		Bitmap bitmap = BitmapFactory.decodeByteArray(bis, 0, bis.length);*/
		Bitmap bitmap = in.getParcelableExtra("bitmap");
		imgV .setImageBitmap(bitmap); //设置Bitmap
		//保存合成的图片
		storeInSD(bitmap);
		
		/*标题栏*/
		TitleActivity ta=new TitleActivity();
		setTitle("标题栏");
		ta.showBackwardView(R.string.text_back,true);
		ta.showForwardView(R.string.text_forward,true);
		}
	}
	
	//创建文件，存储并命名
	private void storeInSD(Bitmap bitmap1) {
		String FILE_DIR=Environment.getExternalStorageDirectory()+FILE_FOLDER;  //创建文件保存目录
		File file = new File(FILE_DIR);
		            if (!file.exists()) {
		                file.mkdir();
		            }
		            File imageFile = new File(file, NextActivity.setFileName() + ".jpg");
		            try {
		                imageFile.createNewFile();
		                FileOutputStream fos = new FileOutputStream(imageFile);
		                bitmap1.compress(CompressFormat.JPEG, 100, fos);
		                
		                Toast.makeText(YourPortraitActivity.this, "保存成功！", 1);
		                
		                fos.flush();
		                fos.close();
		            } catch (FileNotFoundException e) {
		                e.printStackTrace();
		                Toast.makeText(YourPortraitActivity.this, "保存失败！", 1);
		            } catch (IOException e) {
		                // TODO Auto-generated catch block
		                e.printStackTrace();
		                Toast.makeText(YourPortraitActivity.this, "保存失败！", 1);
		            }
		        }

}
