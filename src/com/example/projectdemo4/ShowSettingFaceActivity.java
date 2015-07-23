package com.example.projectdemo4;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

public class ShowSettingFaceActivity extends Activity {
	
	private String[] items = new String[] { "图库","拍照" };
	/* 头像名称 */
	private File image_file=null;
	/* 请求码 */
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int SELECT_PIC_KITKAT = 3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_setting_face);
		showSettingFaceDialog();
	}
	
	//where is your pic?选择图片 图库/拍照
	private void showSettingFaceDialog() {
		new AlertDialog.Builder(this)
				.setTitle("选择大头照")
				.setCancelable(true)
				.setItems(items, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:// Local Image
								Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
								intent.addCategory(Intent.CATEGORY_OPENABLE);
								intent.setType("image/*");
							if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
								startActivityForResult(intent,SELECT_PIC_KITKAT);
							} else {
								startActivityForResult(intent,IMAGE_REQUEST_CODE);
							}
							break;
						case 1:// Take Picture
							Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
							// 判断存储卡是否可以用，可用进行存储
							if (hasSdcard()) {
								intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(image_file));
										/*Uri.fromFile(new File(Environment
												.getExternalStorageDirectory(),
												IMAGE_FILE_NAME)));*/	
							}
							startActivityForResult(intentFromCapture,CAMERA_REQUEST_CODE);
							break;
						}
					}
				})
				.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						}).show();

	}
	
	//判断是否有存储卡
	protected boolean hasSdcard(){
		return Environment.getExternalStorageState().equals( android.os.Environment.MEDIA_MOUNTED); //是否存在SD卡
	}
}
