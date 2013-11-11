package no.hist.haavamoa.oving5;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by haavamoa on 10/29/13.
 */
public class AppPreferenceActivity extends Activity {
    private EditText color;
    private Button btnChangeColor;
    final static  String PREF_FILE = "MyPreferencesFile"; //Preferences file
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apppreference_main);
        btnChangeColor = (Button) findViewById(R.id.btnChangeColor);
        color = (EditText) findViewById(R.id.color);
         SharedPreferences appPrefs = getSharedPreferences(PREF_FILE,0);
        final SharedPreferences.Editor editor = appPrefs.edit();
        color.setText(appPrefs.getString("color","#ff0052"));

        btnChangeColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    Color.parseColor(color.getText().toString());
                    editor.putString("color",color.getText().toString());
                    editor.commit();
                    finish();
                }catch(IllegalArgumentException e){
                    Toast.makeText(getBaseContext(), "The preference color value is an unknown color, please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
