package no.hist.haavamoa.oving5;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;

/**
 * Created by haavamoa on 10/28/13.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    //My databases are not that special, only text and title are implemented.
    static final String DATABASE_CREATE1 = "create table author(name text unique not null);";
    static final String DATABASE_CREATE2 = "create table book(title text unique not null);";
    static final String DATABASE_CREATE3 = "create table author_book(" +
            "text author_name," +
            " text book_title," +
            "FOREIGN KEY(author_name) REFERENCES author(name)," +
            "FOREIGN KEY(book_title) REFERENCES book(title));";
    static final String DATABASE_NAME = "BooksDB";
    static final int DATABASE_VERSION = 1;

    DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
            try{
                db.execSQL(DATABASE_CREATE1);
                db.execSQL(DATABASE_CREATE2);
                db.execSQL(DATABASE_CREATE3);
            }catch(Exception e){
                e.printStackTrace();
            }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("TAG","Upgrading database from version " + oldVersion +
                "to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);

    }
}
