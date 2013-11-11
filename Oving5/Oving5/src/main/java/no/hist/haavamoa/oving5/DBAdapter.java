package no.hist.haavamoa.oving5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

/**
 * Created by haavamoa on 10/28/13.
 */
public class DBAdapter {
    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx){
        this.context = ctx;
        this.DBHelper = new DatabaseHelper(context);
    }

    public DBAdapter open() throws SQLException{
        this.db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        DBHelper.close();
    }

    public long insertAuthor(String name){
        ContentValues initialvalues = new ContentValues();
        initialvalues.put("name",name);
        return db.insertWithOnConflict("author",null,initialvalues,SQLiteDatabase.CONFLICT_IGNORE);
    }

    public long insertBookTitle(String title){
        ContentValues initialvalues = new ContentValues();
        initialvalues.put("title",title);
        return db.insertWithOnConflict("book",null,initialvalues,SQLiteDatabase.CONFLICT_IGNORE);
    }
    public Cursor getAuthor(String name){
        Cursor cursor = db.query(true,"author",new String[]{"name"},"name = '"+name+"'",null,null,null,null,null);
        if(cursor!=null)cursor.moveToFirst();
        return cursor;
    }

    public Cursor getBook(String author){
        Cursor cursor = db.query(true,"author_book",new String[]{"author_name"},"author_name='"+author+"'",null,null,null,null,null);
        if(cursor!=null) cursor.moveToFirst();
        return cursor;
    }

    public Cursor getAllAuthors(){
        return db.query("author",new String[]{"name"},null,null,null,null,null);
    }

    public Cursor getAllTitles(){
        return db.query("book",new String[]{"title"},null,null,null,null,null);
    }

}
