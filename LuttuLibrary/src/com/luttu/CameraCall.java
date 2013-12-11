package com.luttu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class CameraCall {
	Context context;
	CameraInterface cameraInterface;
	String Unedited_Img_Path, Unedited_Img_Name;
	File newfile, photopath;

	public CameraCall(Context context, CameraInterface cameraInterface) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.cameraInterface = cameraInterface;
	}

	public void Upload_Image() {
		// TODO Auto-generated method stub
		final String[] items = { "Camera", "Gallery" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
				android.R.layout.select_dialog_item, items);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("SELECT A ITEM");
		builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				if (item == 0) {
					Intent intent = new Intent(
							"android.media.action.IMAGE_CAPTURE");
					intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION,
							ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
					intent.putExtra(MediaStore.EXTRA_OUTPUT,
							Uri.fromFile(photopath));
					cameraInterface.cam(intent,1);
				}
				if (item == 1) {
					Intent intent = new Intent();
					intent.setType("image/*");
					intent.setAction(Intent.ACTION_GET_CONTENT);
					cameraInterface.cam(intent,2);
				}
			}
		});
		builder.create();
		builder.show();
	}
	public void Galler() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		cameraInterface.cam(intent,2);
	}
	private Bitmap decodeFile(File f) {
		try {
			// Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);
			final int REQUIRED_SIZE = 100;
			int scale = 1;
			while (o.outWidth / scale / 2 >= REQUIRED_SIZE
					&& o.outHeight / scale / 2 >= REQUIRED_SIZE)
				scale *= 2;
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {
		}
		return null;
	}

	public String setimage(ImageView im) {
		// TODO Auto-generated method stub
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 3;
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inSampleSize = 4;
		o.inDither = false;
		o.inPurgeable = true;
		im.setImageBitmap(BitmapFactory.decodeFile(Unedited_Img_Path, o));
		// ByteArrayOutputStream stream = new ByteArrayOutputStream();
		// bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
		// byte[] byteArray = stream.toByteArray();
		return Unedited_Img_Path;
	}

	public String setimagegallery(ImageView im, Intent data) {
		// TODO Auto-generated method stub
		Uri selectedImage = data.getData();
		String[] filePathColumn = { MediaStore.Images.Media.DATA };
		Cursor cursor = context.getContentResolver().query(selectedImage,
				filePathColumn, null, null, null);
		cursor.moveToFirst();
		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		String picturePath = cursor.getString(columnIndex);
		cursor.close();
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 3;
		im.setImageBitmap(BitmapFactory.decodeFile(picturePath, options));
		return picturePath;
	}

	public File createdir(String path) {
		// TODO Auto-generated method stub
		File dir = new File(Environment.getExternalStorageDirectory().getPath()
				+ "/" + path + "/");
		try {
			dir.mkdir();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Unedited_Img_Name = path + String.valueOf(System.currentTimeMillis())
				+ ".jpg";
		Unedited_Img_Path = Environment.getExternalStorageDirectory().getPath()
				+ "/" + path + "/" + Unedited_Img_Name;
		String filename = Environment.getExternalStorageDirectory().getPath()
				+ "/" + path + "/";
		newfile = new File(filename);
		return photopath = new File(Unedited_Img_Path);
	}

	private void setbitmap() {
		// TODO Auto-generated method stub
		File f = new File(Unedited_Img_Path);
		Bitmap b = decodeFile(f);
		File file = new File(newfile, Unedited_Img_Name);
		FileOutputStream fOut;
		try {
			fOut = new FileOutputStream(file);
			b.compress(Bitmap.CompressFormat.PNG, 100, fOut);
			fOut.flush();
			fOut.close();
			b.recycle();
		} catch (Exception e) {

		}
	}
}
