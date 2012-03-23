package com.ngigroup;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BookmarkDatabase extends SQLiteOpenHelper {
	
	private static final String DB_NAME = "BrowserDatabase";
	private static final String TABLE_NAME = "BookmarkTable";
	public static final String ID = "id";
	public static final String BOOKMARK = "bookmark";
	public static final String URL = "url";
	public static final String[] COLUM_NAMES = {ID,BOOKMARK,URL};
	
	private static final String CREATE_SQL = "CREATE TABLE " + TABLE_NAME +
											 "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
											 BOOKMARK + " TEXT, " + 
											 URL + " TEXT );";
	
	private static final String DROP_SQL = "DROP TABLE IF EXISTS " + TABLE_NAME + ";"; 

	public BookmarkDatabase(Context context, int version) {
		super(context, DB_NAME, null, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_SQL);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL(DROP_SQL);
		onCreate(db);

	}
	
	public void insert(String bookmark, String url){
		ContentValues values = new ContentValues();
		values.put(BOOKMARK,bookmark);
		values.put(URL,url);
		getWritableDatabase().insert(TABLE_NAME, null, values);
	}
	
	public List<Entity>select(){
		String groupBy = null;
		String having = null;
		String orderBy = null;
		Cursor cursor = getReadableDatabase().query(TABLE_NAME, COLUM_NAMES, null, null, groupBy, having, orderBy);
		List<Entity> EntityList = new ArrayList<Entity>();
		if(!cursor.moveToFirst()){
			cursor.close();
			return EntityList;
		}
		do{
			EntityList.add(new Entity(cursor.getString(1),cursor.getString(2)));
			
		}while(cursor.moveToNext());
		cursor.close();
		return EntityList;
	}

}
