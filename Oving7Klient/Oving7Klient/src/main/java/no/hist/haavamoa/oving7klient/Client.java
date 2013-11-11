package no.hist.haavamoa.oving7klient;

/**
 * Created by haavamoa on 10/29/13.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class Client extends Thread {
    private final static String TAG = "Client";
    private final static String IP = "10.0.2.2";
    private final static int PORT = 12345;
    private int number1;
    private int number2;
    public String savedAnswer;
    private Context context;
    private ClientActivity client;
    private String res;

    public Client(int number1, int number2 ,ClientActivity client){
        this.number1 = number1;
        this.number2 = number2;
        this.client = client;
    }
    public void run() {
        Socket s 			= null;
        PrintWriter out		= null;
        BufferedReader in 	= null;
        try {
            res = "";
            s = new Socket(IP, PORT);
            Log.v(TAG, "C: Connected to server" + s.toString());
            out = new PrintWriter(s.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            res = in.readLine();
            out.println("calculate:"+number1+":"+number2); //Tell the server to calculate
            if(res!=null)
                Log.i(TAG,res);
            res = in.readLine(); //Get answer with calculation
            if(res!=null)
                if(res.contains("answer")){ saveAnswer();
                    Log.i(TAG,res);
            }
            out.println("PING to server from client");
        } catch (IOException e) {
            e.printStackTrace();
        }finally{//close socket!!
            try{
                out.close();
                in.close();
                s.close();
            }catch(IOException e){}
        }
    }

    /**
     * Method to save the answer from the server.
     * Splits on ":"
     *      tokens[0] : is used to recognize that it is a answer.'
     *      tokens[1] : is the answer
     */
    public void saveAnswer(){
        String[] tokens = res.split(":");
        savedAnswer = tokens[1];
        System.out.println("FÅTT SVAR:"+savedAnswer);
        client.runOnUiThread(new Thread(){
            @Override
            public void run() {
                client.answer.setText(savedAnswer);
            }
            });

        //Toast.makeText(context,savedAnswer,Toast.LENGTH_LONG).show(); //Show the answer, temporary
    }
}
