package com.example.androidhive;
import info.androidhive.slidingmenu.MainActivity;
import info.androidhive.slidingmenu.GridFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.widget.Toast;


public class NetWorking {
	String internetip="http://203.64.183.224:8080/upload/photo/";
	GrideAdapter imageAdapter;
	final String SAVE_DIR = "MyPhoto/";
	String[] Albumnames=null;
	String[] Albumids=null;
	File file = new File(Environment.getExternalStorageDirectory().getPath() + File.separator+SAVE_DIR);
	    public NetWorking(GrideAdapter imagead, String[] albumids, String[] albumnames, String... urllist) {
			// TODO Auto-generated constructor stub
	    	imageAdapter = imagead;
	    	Albumnames = albumnames;
	    	Albumids = albumids;
	    	new DownloadImageTask().execute(urllist);
	    	
		}

private InputStream OpenHttpConnection(String urlString) 
	    throws IOException
	    {
	        InputStream in = null;
	        int response = -1;
	               
	        URL url = new URL(urlString); 
	        URLConnection conn = url.openConnection();
	                 
	        if (!(conn instanceof HttpURLConnection))                     
	            throw new IOException("Not an HTTP connection");        
	        try{
	            HttpURLConnection httpConn = (HttpURLConnection) conn;
	            httpConn.setAllowUserInteraction(false);
	            httpConn.setInstanceFollowRedirects(true);
	            httpConn.setRequestMethod("GET");
	            httpConn.connect();
	            response = httpConn.getResponseCode();                 
	            if (response == HttpURLConnection.HTTP_OK) {
	                in = httpConn.getInputStream();                                 
	            }                     
	        }
	        catch (Exception ex)
	        {
	        	Log.d("Networking", ex.getLocalizedMessage());
	            throw new IOException("Error connecting");
	        }
	        return in;     
	    }
	    
	    private Bitmap DownloadImage(String URL)
	    {        
	        Bitmap bitmap = null;
	        InputStream in = null;        
	        try {
	            in = OpenHttpConnection(URL);
	            bitmap = BitmapFactory.decodeStream(in);
	            in.close();
	        } catch (IOException e1) {
	            Log.d("NetworkingActivity", e1.getLocalizedMessage());            
	        }
	        return bitmap;                
	    }
	    private class DownloadImageTask extends AsyncTask 
	    <String, Bitmap, Long> {
	    	String name=null;
	        //---takes in a list of image URLs in String type---
	        protected Long doInBackground(String... urls) {
	            long imagesCount = 0;
	            for (int i = 0; i < urls.length; i++) {  
	                //---download the image---
	            	System.out.println(internetip+urls[i]);
	                Bitmap imageDownloaded = DownloadImage(internetip+urls[i]);
	                if (imageDownloaded != null)  {
	                    //---increment the image count---
	                    imagesCount++;
	                    /*
	                    try {
	                        //---insert a delay of 3 seconds---
	                        Thread.sleep(1000);
	                    } catch (InterruptedException e) {                         
	                        e.printStackTrace();
	                    }*/
	                    //---return the image downloaded---
	                    //System.out.println(Albumnames[i]+"@"+urls[i]);
	                    name=Albumids[i]+"@"+Albumnames[i]+"@"+urls[i];
	                    publishProgress(imageDownloaded);
	                    if(!file.exists()){
	                        file.mkdir();
			        		}
			    	    String AttachName = file.getPath() + File.separator + urls[i];
			        
			        	try {FileOutputStream out = new FileOutputStream(AttachName);
			        	  imageDownloaded.compress(CompressFormat.JPEG, 100, out);
		      	          out.flush();
		      	          out.close();
		      	          
	                }catch(Exception e){}
	            }
	            //---return the total images downloaded count---
	            
	            }
	            return imagesCount;
	        }

	        //---display the image downloaded---
	        protected void onProgressUpdate(Bitmap... bitmap) {            
	            //img.setImageBitmap(bitmap[0]); 
	        		HashMap<String, Bitmap> map = new HashMap<String,Bitmap>();
			        	map.put(name, bitmap[0]);
			        	MainActivity.list.add(map);
			        	
	      		      
	      	       
	              
	        	
	        }
	        //---when all the images have been downloaded---
	        protected void onPostExecute(Long imagesDownloaded) {   
	        	imageAdapter.notifyDataSetChanged();
	            
	        }
	    }
}