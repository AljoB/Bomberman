package edu.aljosa.Bomberman.data;

import edu.aljosa.Bomberman.android.Rezultat;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;



	public class DBAdapterRezultat implements BaseColumns{
		
		public static final  String TAG="DBAdapterStevec";

		public static final  String VALUE = "i_value";
		public static final  String NAME = "s_name";
		public static final  String TEZAVNOST = "s_tez";
		public static final  int POS__ID=0;
		public static final  int POS_NAME=1;
		public static final  int POS_VALUE=2;
		public static final  int POS_TEZAVNOST=3;
		
		public static final  String TABLE="stevec";
		private final Context context;
		private DatabaseHelper DBHelper;
		private SQLiteDatabase db;
	   
		
		
		public DBAdapterRezultat(Context ctx) 
		{
			this.context = ctx;
			DBHelper = new DatabaseHelper(context);
		}


		//---opens the database---
		public DBAdapterRezultat open() throws SQLException 
		{
			db = DBHelper.getWritableDatabase();
			return this;
		}

		//---closes the database---    
		public void close() 
		{
			DBHelper.close();
		}

		//---insert rezultat
		public long insertRezultat(Rezultat r) 
		{
			ContentValues initialValues = new ContentValues();
			initialValues.put(NAME, r.getName()); 
			initialValues.put(VALUE, r.getStevilo_poskusov()); 

			return db.insert(TABLE, null, initialValues);
		}

		//---deletes a particular title---
		public boolean deleteRezultat(long rowId) 
		{
			return db.delete(TABLE, _ID + "=" + rowId, null) > 0;
		}

		//---retrieves all the titles---
		public Cursor getAll() 
		{
			return db.query(TABLE, new String[] {
					_ID,       //POS__ID=0;
					NAME,      //POS_NAME=1
					VALUE		//POS_VALUE =2
					},    
					null, 
					null, 
					null, 
					null, 
					null);
		}

		//---retrieves a particular title---
		public Cursor getRezultat(long rowId) throws SQLException 
		{
			Cursor mCursor =
				db.query(true, TABLE, new String[] {
						_ID, 
						NAME,
						VALUE
						}, 
						_ID + "=" + rowId, 
						null,
						null, 
						null, 
						null, 
						null);
			if (mCursor != null) {
				mCursor.moveToFirst();
			}
			return mCursor;
		}

		//---update---
		public boolean updateRezultat(Rezultat tmp) 
		{
			ContentValues args = new ContentValues();
			args.put(NAME, tmp.getName());
			args.put(VALUE, tmp.getStevilo_poskusov());
			return db.update(TABLE, args, 
					_ID + "=" + tmp.getDbID(), null) > 0;
		}

	}

