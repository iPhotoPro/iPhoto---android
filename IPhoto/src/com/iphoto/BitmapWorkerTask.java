package com.iphoto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap>
{

	public BitmapWorkerTask(ImageView imageView) {
		this.imageView = imageView;
	}
	
	@Override
	protected void onPostExecute(Bitmap result) {
		if (imageView != null)
		{
			imageView.setImageBitmap(result);
		}
			
	}
	
	@Override
	protected Bitmap doInBackground(String... params) {
		// TODO Auto-generated method stub
		return getBitmap(params[0]);
	}
	
	private ImageView imageView;
	private Bitmap getBitmap(String path)
	{
		Bitmap myBitmap = null;
		File imgFile = new  File(path);
        BitmapFactory.Options option1 = new BitmapFactory.Options();
        option1.inJustDecodeBounds = true;
        try {
			
            if(imgFile.exists()){

            	BitmapFactory.decodeStream(new FileInputStream(imgFile),null,option1);
				int scale;
                if(option1.outWidth > option1.outHeight)
                {
                     scale = Math.round((float) option1.outHeight / (float) 185);
                }
                else
                {
                	scale = Math.round((float) option1.outWidth / (float) 185);
                }
            	
            	BitmapFactory.Options option2 = new BitmapFactory.Options();
            	option2.inSampleSize = scale;
            	option2.inScaled = false;
            	myBitmap =  BitmapFactory.decodeFile(imgFile.getAbsolutePath(),option2);
//                imageView.setImageBitmap(myBitmap);
            }
        } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return myBitmap;
	}
	
}