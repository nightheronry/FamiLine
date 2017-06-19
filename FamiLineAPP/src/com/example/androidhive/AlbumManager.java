package com.example.androidhive;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AlbumManager
{
    private static SQLiteDatabase _albumDatabase = null; 
    private static int miCount = 0;
    
    private final String DATABASE_NAME          = "albumDatabase"; 
    private final static String TABLE_NAME      = "albumTable";    
                                                   
    private final static String ALBUM_NAME      = "albumname";
    private final static String ALBUM_COVER     = "albumcover";
    private final static String ALBUM_NUMBER    = "albumnumber";
    private final static String ALBUM_TOTAL     = "albumtotal";
    
    private final String CREATE_TABLE           = "CREATE TABLE "
                                                + TABLE_NAME  + " ("
                                                + ALBUM_NAME  + " TEXT PRIMARY KEY,"
                                                + ALBUM_COVER + " TEXT,"
                                                + ALBUM_NUMBER+ " TEXT,"
                                                + ALBUM_TOTAL + " TEXT)";
    
    
    public AlbumManager(Context context)
    {
        _albumDatabase = context.openOrCreateDatabase("/sdcard/"+DATABASE_NAME+".db", Context.MODE_PRIVATE, null);
        
        try
        {
            _albumDatabase.execSQL(CREATE_TABLE);
        }
        catch(Exception e)
        {
            printDatabase();
        }    
    }
    
    // upload the photo to DB
    public static void updateAlbum(String name, String text, String cover, String number)
    {
        ContentValues cv = new ContentValues(); 
        cv.put(ALBUM_NAME, name);
        cv.put(ALBUM_COVER, cover);
        cv.put(ALBUM_NUMBER, number);
        
        // modify the condition
        String whereClause = ALBUM_NAME + " =?";
        
        // add parameter
        String[] whereArgs = {name};
        
        // upload to db
        _albumDatabase.update(TABLE_NAME, cv, whereClause, whereArgs);
    }
    
    
    public static void insertAlbum(String name, String cover, String number, String total)
    {
        ContentValues cv = new ContentValues();
        
        cv.put(ALBUM_NAME, name);
        cv.put(ALBUM_COVER, cover);
        cv.put(ALBUM_NUMBER, number);
        cv.put(ALBUM_TOTAL, total);
        _albumDatabase.insert(TABLE_NAME, null, cv);
        
        miCount++;
    }
    
    // require the photodata
    public static AlbumData getAlbumData(String name)
    {
        Cursor c = _albumDatabase.query(TABLE_NAME, null, null, null, null, null, null);
        
        if(c.moveToFirst() == true)
        {
            while(c.moveToNext())
            {
                AlbumData albumData = new AlbumData();
                
                albumData.albumname  = c.getString(0);
                albumData.albumcover = c.getString(1);
                albumData.albumnumber= c.getString(2);
                albumData.albumtotal = c.getString(3);
                
               
                if(name.indexOf(albumData.albumname) != -1)
                {
                    return albumData;
                }
            }

            c.close();
        }

        return null;
    }
    
    
    public static void deleteAlbumData(String name)
    {    
        
        String whereClause = ALBUM_NAME + " =?";
        
        
        String[] whereArgs = {name};
        
        _albumDatabase.delete(TABLE_NAME, whereClause, whereArgs);
        
        System.out.println("delete a Note " + name);
    }
    
    
    public static ArrayList<AlbumData> getAllNotes()
    {
        //initial
        ArrayList<AlbumData> albums = new ArrayList<AlbumData>();
        
        Cursor c = _albumDatabase.query(TABLE_NAME, null, null, null, null, null, null);
        
        if(c.moveToFirst() == true)
        {
            while(c.moveToNext())
            {
                AlbumData albumData = new AlbumData();
                
                albumData.albumname  = c.getString(0);
                albumData.albumcover = c.getString(1);
                albumData.albumnumber= c.getString(2);
                albumData.albumtotal = c.getString(3);
                
                albums.add(albumData);
            }

            c.close();
        }

        return albums;
    }
    
    // test
    public static void printDatabase()
    {
        Cursor c = _albumDatabase.query(TABLE_NAME, null, null, null, null, null, null);
        
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
