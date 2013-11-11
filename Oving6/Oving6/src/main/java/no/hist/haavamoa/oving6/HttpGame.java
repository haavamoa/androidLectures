package no.hist.haavamoa.oving6;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by haavamoa on 10/29/13.
 */
public class HttpGame extends Activity {

    private String gameURL = "http://tomcat.stud.aitel.hist.no/studtomas/tallspill.jsp";
    private String name;
    private String cardNr;
    private TextView nameView;
    private TextView status;
    private EditText number;
    private Button btnSend;
    private Button btnRestart;
    private HttpClient client = new DefaultHttpClient(); //Session

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.httpgame_main);


        //Get the data from the main activity.
        name = getIntent().getStringExtra("name");
        cardNr = getIntent().getStringExtra("cardNr");

        //Initialise the widgets.
        nameView = (TextView) findViewById(R.id.nameView);
        nameView.setText("Navn: "+name);
        status = (TextView) findViewById(R.id.status);
        status.setText(checkNameAndCardNr(name, cardNr));
        number = (EditText) findViewById(R.id.number);
        btnSend = (Button) findViewById(R.id.btnSend);

        btnRestart = (Button) findViewById(R.id.btnRestart);

        //Button listeners
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                status.setText(playTheGame(Integer.parseInt(number.getText().toString())));
                }catch(NumberFormatException e){ //Log
                    Log.d("btnSend :: Onclick","Tallet du skal spille med kan ikke formateres. Kan være en string.");
                }
            }
        });

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number.setText("");
                finish();
            }
        });

    }

    /**
     * This method checks if the name and cardnr is set to be able to go to the next step in the game.
     * It also has the feature to add a name consisting space and norwegian letters (ÆÅØ).
     * @param name name of the player
     * @param cardNr cardnr of the player ( not encrypted)
     * @return the response from the http http-server.
     */
    private String checkNameAndCardNr(String name,String cardNr){

        //Method to encode and parse spaces and norwegian letters.
        List<BasicNameValuePair> parameterList = new ArrayList<BasicNameValuePair>();
        parameterList.add(new BasicNameValuePair("navn",name));
        parameterList.add(new BasicNameValuePair("kortnummer",cardNr));

        String url = gameURL+"?"+ URLEncodedUtils.format(parameterList, null);
        return httpReponse(url);
    }

    /**
     * A method to connect to the http server and return the response.
     * @param url the url with the request.
     * @return response from the http-server.
     */
    private String httpReponse(String url){
        HttpGet request = new HttpGet(url);
        ResponseHandler<String> responseHandler = new BasicResponseHandler();

        //Hack to avoid threads.
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String responseBody = "";
        try {
            responseBody = client.execute(request,responseHandler);
        } catch (IOException e) { //Log
            Log.d("httpResponse()", "Noe er feil med oppkobling mot tjener");
            e.printStackTrace();
        }
        return responseBody;
    }

    /**
     * This is the method to play the game when you are ready.
     * @param number the number you want to play with.
     * @return the response from the http-server.
     */
    private String playTheGame(int number){
        String url = gameURL+"?tall="+number;
        return httpReponse(url);
    }
}
