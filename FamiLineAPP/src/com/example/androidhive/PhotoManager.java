package com.example.androidhive;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PhotoManager
{
    private static SQLiteDatabase _photosDatabase = null; 
    private static int miCount = 0;
    
    private final String DATABASE_NAME          = "photosDatabase"; 
    private final static String TABLE_NAME      = "photosTable";    
    
    private final static String PHOTOS_NAME     = "photosname";
    private final static String PHOTOS_SCRIPT   = "photosscript";
    private final static String PHOTO_ALBUM     = "photosalbum";
    private final static String PHOTOS_TIME     = "photostime";
    private final static String USER_ID         = "userid";
    private final static String ALBUM_NUMBER    = "albumnumber";
    private final String CREATE_TABLE = "CREATE TABLE " 
                                        + TABLE_NAME + " ("
                                        + PHOTOS_NAME + " TEXT PRIMARY KEY," 
                                        + PHOTOS_SCRIPT + " TEXT," 
                                        + PHOTO_ALBUM + " TEXT,"
                                        + USER_ID + " TEXT)";
    
    
    public PhotoManager(Context context)
    {
        _photosDatabase = context.openOrCreateDatabase("/sdcard/"+DATABASE_NAME+".db", Context.MODE_PRIVATE, null);
        
        try
        {
            _photosDatabase.execSQL(CREATE_TABLE);
        }
        catch(Exception e)
        {
            printDatabase();
        }    
    }
    
    // upload the photo to DB
    public static void updatePHOTOS(String name, String text, String album, String text3)
    {
        ContentValues cv = new ContentValues();
        
        cv.put(PHOTOS_SCRIPT, text);
        cv.put(PHOTOS_NAME, name);
        cv.put(PHOTO_ALBUM, album);
        
        // modify the condition
        String whereClause = PHOTOS_NAME + " =?";
        
        // add parameter
        String[] whereArgs = {name};
        
        // upload to db
        _photosDatabase.update(TABLE_NAME, cv, whereClause, whereArgs);
    }
    
    
    public static void insertNote(String name, String text, String album, String userid)
    {
        ContentValues cv = new ContentValues();
        
        cv.put(PHOTOS_NAME, name);
        cv.put(PHOTOS_SCRIPT, text);
        cv.put(PHOTO_ALBUM, album);
        cv.put(USER_ID, userid);
        _photosDatabase.insert(TABLE_NAME, null, cv);
        
        miCount++;
    }
    
    // require the photodata
    public static PhotoData getPhotoData(String name)
    {
        Cursor c = _photosDatabase.query(TABLE_NAME, null, null, null, null, null, null);
        
        if(c.moveToFirst() == true)
        {
            while(c.moveToNext())
            {
                String photosname = c.getString(0);
                String photosscript = c.getString(1);
                String photosalbum = c.getString(2);
                String userid = c.getString(3);
                
                PhotoData photoData = new PhotoData();
                
                photoData.photosname     = photosname;
                photoData.photosscript     = photosscript;
                photoData.photosalbum    = photosalbum;
                photoData.userid         = userid;
            
                
               
                if(name.indexOf(photoData.photosname) != -1)
                {
                    return photoData;
                }
            }

            c.close();
        }

        return null;
    }
    
    
    public static void deletePhotoData(String name)
    {    
        
        String whereClause = PHOTOS_NAME + " =?";
        
        
        String[] whereArgs = {name};
        
        _photosDatabase.delete(TABLE_NAME, whereClause, whereArgs);
        
        System.out.println("delete a Note " + name);
    }
    
    
    public static ArrayList<PhotoData> getAllNotes()
    {
        //initial
        ArrayList<PhotoData> notes = new ArrayList<PhotoData>();
        
        Cursor c = _photosDatabase.query(TABLE_NAME, null, null, null, null, null, null);
        
        if(c.moveToFirst() == true)
        {
            while(c.moveToNext())
            {
                PhotoData photoData = new PhotoData();
                
                photoData.photosname    = c.getString(0);
                photoData.photosscript  = c.getString(1);
                photoData.photosalbum   = c.getString(2);
                photoData.userid        = c.getString(3);
                
                
                notes.add(photoData);
            }

            c.close();
        }

        return notes;
    }
    
    // test
    public static void printDatabase()
    {
        Cursor c = _photosDatabase.query(TABLE_NAME, null, null, null, null, null, null);
        
        if(c.moveToFirst() == true)
        {
            while(c.moveToNext())
            {
                String notename = c.getString(0);
                int notex         = c.getInt(1);
                int notey         = c.getInt(2);
                String notetext = c.getString(3);
                String notetype = c.getString(4);
                
                System.out.println(notename + " " + notex + " " + notey + " " + notetext + " " + notetype);
            }
            
            c.close();
        }
    }

}
