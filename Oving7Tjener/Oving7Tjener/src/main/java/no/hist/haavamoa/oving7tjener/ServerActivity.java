package no.hist.haavamoa.oving7tjener;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;


import android.app.Activity;
import android.os.Bundle;

public class ServerActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Server().start();//start server
    }
}
