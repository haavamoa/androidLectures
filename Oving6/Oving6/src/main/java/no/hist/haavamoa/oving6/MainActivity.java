package no.hist.haavamoa.oving6;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

public class MainActivity extends Activity {
    private EditText name;
    private EditText cardNr;
    private Button send;
    private Button restart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initialise widgets.
        name = (EditText) findViewById(R.id.name);
        cardNr = (EditText) findViewById(R.id.cardNr);
        send = (Button) findViewById(R.id.send);
        restart = (Button) findViewById(R.id.restart);

        //Set on click listeners for the buttons.
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!name.equals("") || !cardNr.equals("")){
                Intent i = new Intent(getBaseContext(),HttpGame.class);
                i.putExtra("name",name.getText().toString());
                i.putExtra("cardNr",cardNr.getText().toString());
                startActivity(i);
                }else{ //dont play the game if anything is empty.
                    Toast.makeText(getBaseContext(),"Enten mangler du navn eller kortnummer",Toast.LENGTH_LONG).show();
                }
            }
        });
        //I expect this to reset the name and cardnumber widgets.
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             name.setText("");
                cardNr.setText("");
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Reset all the attributes when you go back to this activity
     */
    @Override
    protected void onResume() {
        super.onResume();
        name.setText("");
        cardNr.setText("");
    }
}
