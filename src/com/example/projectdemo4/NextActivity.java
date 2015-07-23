package com.example.projectdemo4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.example.projectdemo4.StringUtils;




import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

public class NextActivity extends Activity {
	
	private ImageView imageView;  //ͼƬ��ʾ
	private CheckBox characterbtn_01,
					 characterbtn_02,
					 characterbtn_03,
					 characterbtn_04,
					 characterbtn_05,
					 characterbtn_06;
	private List<CheckBox>checkboxs=new ArrayList<CheckBox>();
	private int select_count=0;
	private Button submitbtn;  //�ύ��ť
	
	
	private String[] items = new String[] { "ͼ��","����" };
	/* ͷ������ */
	private static final String IMAGE_FILE_NAME = "face.jpg";
	private String FILE_DIR="/sdcard/wolai_img/";      //��ͷ�ձ�����ļ���
	private File image_file=null;
	/* ������ */
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int RESULT_REQUEST_CODE = 2;
	private static final int SELECT_PIC_KITKAT = 3;

	private Button button1;
	private Bitmap bmp_compount;
	private int count=0;
    String checkboxs_content = ""; //���checkbox���ַ���
	int[] trivias = {
	        R.string.character01, R.string.character02, R.string.character03, R.string.character04, 
	        R.string.character05,R.string.character06,R.string.character07,R.string.character08,
	        R.string.character09,R.string.character10 };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_next);		
		showSettingFaceDialog();
		initViews();

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
	
	private void initViews() {
		imageView = (ImageView) findViewById(R.id.main_image);
		//�ӱ���ȡͼƬ(��sdcard�л�ȡ�ļ�)
		Bitmap bitmap1=null;
		image_file=getFileDir();
		//storeInSD(bitmap1);
		
		//Bitmap bitmap = getLoacalBitmap("/sdcard/mhc.jpg"); 
		Log.e("msgTag", new File("/sdcard/mhc.jpg").exists()+"==========");           //���ûʲô��
		imageView .setImageBitmap(bitmap1); //����Bitmap
		submitbtn = (Button) findViewById(R.id.submit);
		//6 checkbox
		characterbtn_01 = (CheckBox) findViewById(R.id.character_01);
		characterbtn_02 = (CheckBox) findViewById(R.id.character_02);
		characterbtn_03 = (CheckBox) findViewById(R.id.character_03);
		characterbtn_04 = (CheckBox) findViewById(R.id.character_04);
		characterbtn_05 = (CheckBox) findViewById(R.id.character_05);
		characterbtn_06 = (CheckBox) findViewById(R.id.character_06);
		
		//set checkboxs.setOnClickListener
		//register event  
		characterbtn_01.setOnCheckedChangeListener(listener);  
		characterbtn_02.setOnCheckedChangeListener(listener);  
		characterbtn_03.setOnCheckedChangeListener(listener);  
		characterbtn_04.setOnCheckedChangeListener(listener);
		characterbtn_05.setOnCheckedChangeListener(listener);
		characterbtn_06.setOnCheckedChangeListener(listener);
		//add to List
		checkboxs.add(characterbtn_01);
		checkboxs.add(characterbtn_02);
		checkboxs.add(characterbtn_03);
		checkboxs.add(characterbtn_04);
		checkboxs.add(characterbtn_05);
		checkboxs.add(characterbtn_06);
		//set checkbox values
		Random ran = new Random();
		final int n=ran.nextInt(5);
		characterbtn_01.setText(trivias[n]);
		characterbtn_02.setText(trivias[n+1]);
		characterbtn_03.setText(trivias[n+2]);
		characterbtn_04.setText(trivias[n+3]);
		characterbtn_05.setText(trivias[n+4]);
		characterbtn_06.setText(trivias[n+5]);
		//submit listener
		submitbtn.setOnClickListener(submitb_listener);
	}
	
	//��ȡ��ǰʱ��
	public static String getCharacterAndNumber() {
		String rel="";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss"); 
		Date curDate = new Date(System.currentTimeMillis());
		rel = formatter.format(curDate);   
		return rel;
	}
	
	//��������ļ���
	public static String setFileName() {
		// mu
		//String fileNameRandom = getCharacterAndNumber(8);
		String fileNameRandom = getCharacterAndNumber();
		return  fileNameRandom;
	}	

	//��ȡ�ļ�·��
	public File getFileDir(){
		File file = new File(FILE_DIR);
	    if (!file.exists()) {
	        file.mkdir();
	    }
	    File f = new File(file, setFileName() + ".jpg");
	    image_file=f;
		return image_file;
	}
	
	//listener checkbox
	CompoundButton.OnCheckedChangeListener  listener=new CompoundButton.OnCheckedChangeListener(){ 
		@Override
		public void onCheckedChanged(CompoundButton buttonView, 
		      boolean isChecked) { 
		// TODO Auto-generated method stub 
		CheckBox box = (CheckBox) buttonView;
		} 
	};




	
	//get checkbox's values when they were clicked
	public void getValues(View v){  
		
	     for (CheckBox cbx : checkboxs) {  
	         if (cbx.isChecked()) {  
	        	 checkboxs_content += cbx.getText();
	        	 select_count++;

	         }
	         
	         Log.i("select_count", String.valueOf(select_count));
	     }  
	     /*if ("".equals(checkboxs_content)) {  
	    	 checkboxs_content = "����û��ѡ��";  
	     }  
	     new AlertDialog.Builder(this).setMessage(checkboxs_content).setTitle("ѡ�е���������")  
	         .setPositiveButton("�ر�", null).show();*/
	    
	}

	//submitb_listener event
	public Button.OnClickListener submitb_listener=new Button.OnClickListener(){
		@SuppressWarnings("static-access")
		@Override
		public void onClick(View arg0){
			// TODO Auto-generated method stub
			StringUtils su=new StringUtils();
			
			getValues(arg0);
			Intent in = new Intent(NextActivity.this,YourPortraitActivity.class);
			if(isPass(select_count)){
				if((su.containsAny(checkboxs_content,"����"))&&(su.containsAny(checkboxs_content,"����"))){
					Bitmap bitmap_src2 = null;
					imageView.setDrawingCacheEnabled(true);
					bitmap_src2=Bitmap.createBitmap(imageView.getDrawingCache());
					//bitmap_src2=convertViewToBitmap(imageView);
					imageView.setDrawingCacheEnabled(false);									
					Bitmap bitmap_src1=BitmapFactory.decodeResource(getResources(), R.drawable.test_320_480);		
					bmp_compount=createBitmap(bitmap_src1,bitmap_src2);
					in.putExtra("bitmap", bmp_compount);
					startActivity(in);				
				}
				else if((su.containsAny(checkboxs_content,"����"))&&(su.containsAny(checkboxs_content,"���鱼��"))){
					Bitmap bitmap_src2 = null;

					imageView.setDrawingCacheEnabled(true);
					bitmap_src2=Bitmap.createBitmap(imageView.getDrawingCache());
					//bitmap_src2=convertViewToBitmap(imageView);
					imageView.setDrawingCacheEnabled(false);									
					Bitmap bitmap_src1=BitmapFactory.decodeResource(getResources(), R.drawable.test_320_480);
						
					bmp_compount=createBitmap(bitmap_src1,bitmap_src2);
					in.putExtra("bitmap", bmp_compount);
					startActivity(in);
				}
				else{
					Bitmap bitmap_src2 = null;

					imageView.setDrawingCacheEnabled(true);
					bitmap_src2=Bitmap.createBitmap(imageView.getDrawingCache());
					//bitmap_src2=convertViewToBitmap(imageView);
					imageView.setDrawingCacheEnabled(false);									
					Bitmap bitmap_src1=BitmapFactory.decodeResource(getResources(), R.drawable.test_320_480);
						
					bmp_compount=createBitmap(bitmap_src1,bitmap_src2);
					in.putExtra("bitmap", bmp_compount);
					startActivity(in);
				}

			}else{
				
				showAlertDialog();
			}
		}
	};
	


	 /**
	  * ͼƬ�ϳ�
	  * @param bitmap
	  * @return
	  */
	private Bitmap createBitmap( Bitmap src, Bitmap watermark ) {
	     if( src == null ) {
	         return null;
	     }
	     int w = src.getWidth();
	     int h = src.getHeight();
	     int ww = watermark.getWidth();
	     int wh = watermark.getHeight();
	     //create the new blank bitmap
	     Bitmap newb = Bitmap.createBitmap( w, h, Config.ARGB_8888 );//����һ���µĺ�SRC���ȿ��һ����λͼ
	     Canvas cv = new Canvas( newb );
	     //draw src into
	     cv.drawBitmap( src, 0, 0, null );//�� 0��0���꿪ʼ����src
	     //draw watermark into
	     cv.drawBitmap( watermark, w - ww + 5, h - wh + 5, null );//��src�����½ǻ���ˮӡ
	     //save all clip   
	     cv.save( Canvas.ALL_SAVE_FLAG );//����
	     //store
	     cv.restore();//�洢
	     return newb;
	}
	


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// ����벻����ȡ��ʱ��
		if (resultCode != RESULT_CANCELED) {
			switch (requestCode) {
			case IMAGE_REQUEST_CODE:
				startPhotoZoom(data.getData());
				break;
			case SELECT_PIC_KITKAT:
				startPhotoZoom(data.getData());
				break;
			case CAMERA_REQUEST_CODE:
				if (hasSdcard()) {
					/*File tempFile = new File(Environment.getExternalStorageDirectory(),IMAGE_FILE_NAME);
					startPhotoZoom(Uri.fromFile(tempFile));*/
					File tempFile = image_file;
					startPhotoZoom(Uri.fromFile(tempFile));
					
				} else {
					Toast.makeText(getApplicationContext(), "δ�ҵ��洢�����޷��洢��Ƭ��", Toast.LENGTH_SHORT).show();
				}

				break;
			case RESULT_REQUEST_CODE:
				if (data != null) {
					setImageToView(data,imageView);
				}
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}





	/**
	 * �ü�ͼƬ����ʵ��
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {
		if (uri == null) {
			Log.i("tag", "The uri is not exist.");
			return;
		}
		
		Intent intent = new Intent("com.android.camera.action.CROP");
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
			String url=getPath(NextActivity.this,uri);
			intent.setDataAndType(Uri.fromFile(new File(url)), "image/*");
		}else{
			intent.setDataAndType(uri, "image/*");
		}
		
		// ���òü�
		intent.putExtra("crop", "true");
		// aspectX aspectY �ǿ�ߵı���
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY �ǲü�ͼƬ���
		intent.putExtra("outputX", 200);
		intent.putExtra("outputY", 200);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, RESULT_REQUEST_CODE);
	}
	
	/**
	 * ����ü�֮���ͼƬ����
	 * 
	 * @param picdata
	 */
	private void setImageToView(Intent data,ImageView imageView) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			Bitmap roundBitmap=ImageUtil.toRoundBitmap(photo);
			imageView.setImageBitmap(roundBitmap);
			saveBitmap(photo);
		}
	}

	public void saveBitmap(Bitmap mBitmap) {
		//File f = new File(Environment.getExternalStorageDirectory(),IMAGE_FILE_NAME);
		Bitmap bitmapsrc=BitmapFactory.decodeResource(getResources(), R.drawable.panda_01);
        File f = image_file;
		try {
			f.createNewFile();
			FileOutputStream fOut = null;
			fOut = new FileOutputStream(f);
			mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
			
			fOut.flush();
			fOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//�����ǹؼ���ԭ��uri���ص���file:///...���ŵģ�android4.4���ص���content:///...
	@SuppressLint("NewApi")
	public static String getPath(final Context context, final Uri uri) {

	    final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

	    // DocumentProvider
	    if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
	        // ExternalStorageProvider
	        if (isExternalStorageDocument(uri)) {
	            final String docId = DocumentsContract.getDocumentId(uri);
	            final String[] split = docId.split(":");
	            final String type = split[0];

	            if ("primary".equalsIgnoreCase(type)) {
	                return Environment.getExternalStorageDirectory() + "/" + split[1];
	            }

	        }
	        // DownloadsProvider
	        else if (isDownloadsDocument(uri)) {
	            final String id = DocumentsContract.getDocumentId(uri);
	            final Uri contentUri = ContentUris.withAppendedId(
	                    Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

	            return getDataColumn(context, contentUri, null, null);
	        }
	        // MediaProvider
	        else if (isMediaDocument(uri)) {
	            final String docId = DocumentsContract.getDocumentId(uri);
	            final String[] split = docId.split(":");
	            final String type = split[0];

	            Uri contentUri = null;
	            if ("image".equals(type)) {
	                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
	            } else if ("video".equals(type)) {
	                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
	            } else if ("audio".equals(type)) {
	                contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
	            }

	            final String selection = "_id=?";
	            final String[] selectionArgs = new String[] {
	                    split[1]
	            };

	            return getDataColumn(context, contentUri, selection, selectionArgs);
	        }
	    }
	    // MediaStore (and general)
	    else if ("content".equalsIgnoreCase(uri.getScheme())) {
	        // Return the remote address
	        if (isGooglePhotosUri(uri))
	            return uri.getLastPathSegment();

	        return getDataColumn(context, uri, null, null);
	    }
	    // File
	    else if ("file".equalsIgnoreCase(uri.getScheme())) {
	        return uri.getPath();
	    }

	    return null;
	}

	/**
	 * Get the value of the data column for this Uri. This is useful for
	 * MediaStore Uris, and other file-based ContentProviders.
	 *
	 * @param context The context.
	 * @param uri The Uri to query.
	 * @param selection (Optional) Filter used in the query.
	 * @param selectionArgs (Optional) Selection arguments used in the query.
	 * @return The value of the _data column, which is typically a file path.
	 */
	public static String getDataColumn(Context context, Uri uri, String selection,
	        String[] selectionArgs) {

	    Cursor cursor = null;
	    final String column = "_data";
	    final String[] projection = {
	            column
	    };

	    try {
	        cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
	                null);
	        if (cursor != null && cursor.moveToFirst()) {
	            final int index = cursor.getColumnIndexOrThrow(column);
	            return cursor.getString(index);
	        }
	    } finally {
	        if (cursor != null)
	            cursor.close();
	    }
	    return null;
	}


	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	public static boolean isExternalStorageDocument(Uri uri) {
	    return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public static boolean isDownloadsDocument(Uri uri) {
	    return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	public static boolean isMediaDocument(Uri uri) {
	    return "com.android.providers.media.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is Google Photos.
	 */
	public static boolean isGooglePhotosUri(Uri uri) {
	    return "com.google.android.apps.photos.content".equals(uri.getAuthority());
	}

	//judge whether is n>=4
	public boolean isPass(int count){
		if(count>=4){
			return true;
		}else{
			return false;
		}
	}
	
	//at least choose 4 items
	private void showAlertDialog() {
		AlertDialog.Builder builder  = new Builder(NextActivity.this);
		builder.setTitle("ȷ��" ) ;
		builder.setMessage("������ѡ4��" ) ;
		builder.setPositiveButton("��" ,null );
		builder.show();
	}

}
