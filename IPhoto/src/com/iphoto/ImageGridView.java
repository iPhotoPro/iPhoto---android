package com.iphoto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageGridView extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imagegridview);
		
		getPathList();
		Log.e("Image Grid View", "");
		GridView gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new ImageAdapter(this));
 
        gridView.setOnItemClickListener(new OnItemClickListener() 
        {
            public void onItemClick(AdapterView<?> parent, 
            View v, int position, long id) 
            {                
                Toast.makeText(getBaseContext(), 
                        "pic" + (position + 1) + " selected", 
                        Toast.LENGTH_SHORT).show();
            }
        });   
	}
	private ArrayList<String> pathList = new ArrayList<String>();
	private void getPathList()
	{
	
		String[] projection = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DATA
        };

        // content:// style URI for the "primary" external storage volume
        Uri images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        // Make the query.
        Cursor cur = managedQuery(images,
                projection, // Which columns to return
                null,       // Which rows to return (all rows)
                null,       // Selection arguments (none)
                null        // Ordering
                );

        Log.e("ListingImages"," query count="+cur.getCount());

        if (cur.moveToFirst()) {
            String bucket;
            String path;
            int bucketColumn = cur.getColumnIndex(
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

            int dataColumn = cur.getColumnIndex(
                MediaStore.Images.Media.DATA);

            do {
                // Get the field values
                bucket = cur.getString(bucketColumn);
                path = cur.getString(dataColumn);

                // Do something with the values.
//                Log.e("ListingImages", " bucket=" + bucket 
//                       + "  date_taken=" + path);
                pathList.add(path);
            } while (cur.moveToNext());

        }
        Log.e("ListingImages", pathList.size() + "");
	}
	
	public class ImageAdapter extends BaseAdapter 
    {
        private Context context;
 
        public ImageAdapter(Context c) 
        {
            context = c;
        }
 
        //---returns the number of images---
        
        @Override
        public int getCount() {
//            return pathList.size();
        	return 100;
        }
 
        //---returns the ID of an item--- 
        @Override
        public Object getItem(int position) {
//            return position;
        	return pathList.get(mPosition);
        }
 
        @Override
        public long getItemId(int position) {
            return position;
        }
 
        //---returns an ImageView view---
        public View getView(int position, View convertView, ViewGroup parent) 
        {
        	
            ImageView imageView;
            mPosition++;
        	mPosition = position;
            if (convertView == null) {
            	
            	Log.e("Position", mPosition + "");
                imageView = new ImageView(context);
                
            } else {
                imageView = (ImageView) convertView;
            }
            imageView.setLayoutParams(new GridView.LayoutParams(185, 185));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(5, 5, 5, 5);
            new BitmapWorkerTask(imageView).execute(pathList.get(mPosition));
            
            return imageView;
        }
        private int mPosition = -1;
    }  
	
}
