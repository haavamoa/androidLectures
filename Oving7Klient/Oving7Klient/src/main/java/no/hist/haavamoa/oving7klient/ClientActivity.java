package no.hist.haavamoa.oving7klient;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ClientActivity extends Activity {
    public TextView answer;
    private Button btnBeregn;
    private EditText number1;
    private EditText number2;
    private ClientActivity clientActivity; //To use for the client class
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clientActivity = this;
        answer = (TextView) findViewById(R.id.answer);
        btnBeregn = (Button) findViewById(R.id.btnBeregn);
        number1 = (EditText) findViewById(R.id.number1);
        number2 = (EditText) findViewById(R.id.number2);

        btnBeregn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                int number1Int = Integer.parseInt(number1.getText().toString());
                int number2Int = Integer.parseInt(number2.getText().toString());
                Client client = new Client(number1Int,number2Int,clientActivity);
                client.start();
                }catch(Exception e){ //Feilhåndtering på front-end
                    e.printStackTrace();
                    Toast.makeText(getBaseContext(),"Ikke glem å ha med begge tallene!",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
