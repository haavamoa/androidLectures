package no.hist.haavamoa.oving5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainActivity extends Activity {
    private ArrayList<String> bookTitle = new ArrayList<String>();
    private ArrayList<String> bookAuthor = new ArrayList<String>();
    private DBAdapter db;
    private ListView list;
    private Button showAuthors;
    private Button showTitles;
    private Button btnpreferences;
    private SharedPreferences preferences;
    private String[] values;
    private ArrayAdapter<String> myadapter;
    final static  String PREF_FILE = "MyPreferencesFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (ListView) findViewById(R.id.list);
        showAuthors = (Button) findViewById(R.id.btnAuthors);
        showTitles = (Button) findViewById(R.id.btnTitles);
        btnpreferences = (Button) findViewById(R.id.preferences);

        //Load preferences
        preferences = getSharedPreferences(PREF_FILE,0);

        //Database connection and read from file to database
        this.db = new DBAdapter(this);
        file2Db();

        //Views with authors and titles read from the database.
        values = new String[bookAuthor.size()];
        //Print out all the authors from the db
        Cursor c = db.getAllAuthors();
        setNewValues(c);
        myadapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,values);
        list.setAdapter(myadapter);

        //Button listeners.
        showAuthors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c = db.getAllAuthors();
                setNewValues(c);
                myadapter.notifyDataSetChanged();

            }
        });

        showTitles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c = db.getAllTitles();
                setNewValues(c);
                myadapter.notifyDataSetChanged();
            }
        });

        btnpreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),AppPreferenceActivity.class);
                startActivity(i);
            }
        });
    }

    /**
     * Method to set new values to the listview values.
     * @param ca
     */
    public void setNewValues(Cursor ca){
        for(int i=0;i<values.length;i++){
            ca.moveToNext();
            values[i] = ca.getString(0);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Reads a file and put it into the database.
     * Format of file:
     * Book Title - Author
     *            ^ split on -
     */
    public void file2Db(){
        try {
            InputStream fIn = getResources().openRawResource(R.raw.books);
            DataInputStream in = new DataInputStream(fIn);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = br.readLine()) != null) { //Reads every line from the file.
                String[] tokens = line.split("-");
                bookTitle.add(tokens[0]); // tokens[0] represents  book title.
                bookAuthor.add(tokens[1]); //tokens[1] represents book author.
            }
            } catch (IOException e) {
                e.printStackTrace();
            }

             try{
                 db.open();
                 for(String name:bookAuthor)
                     db.insertAuthor(name);
                 for(String title:bookTitle)
                     db.insertBookTitle(title);
             }catch(SQLException e){
                 e.printStackTrace();
             }
    }

    /**
     * I choose to close the database onDestory()
     */
    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }

    /**
     * This method will trigger the color changer, both when you start the app the first time, but also when the second activity finishes.
     */
    @Override
    protected void onResume() {
        super.onResume();
        int color = Color.parseColor(preferences.getString("color","#ff0052"));
        list.setBackgroundColor(color);
    }
}
