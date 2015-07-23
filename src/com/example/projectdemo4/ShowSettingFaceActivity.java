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
	
	private String[] items = new String[] { "ͼ��","����" };
	/* ͷ������ */
	private File image_file=null;
	/* ������ */
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int SELECT_PIC_KITKAT = 3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_setting_face);
		showSettingFaceDialog();
	}
	
	//where is your pic?ѡ��ͼƬ ͼ��/����
	private void showSettingFaceDialog() {
		new AlertDialog.Builder(this)
				.setTitle("ѡ���ͷ��")
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
							// �жϴ洢���Ƿ�����ã����ý��д洢
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
				.setNegativeButton("ȡ��",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						}).show();

	}
	
	//�ж��Ƿ��д洢��
	protected boolean hasSdcard(){
		return Environment.getExternalStorageState().equals( android.os.Environment.MEDIA_MOUNTED); //�Ƿ����SD��
	}
}
