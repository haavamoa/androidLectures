package no.hist.haavamoa.oving7tjener;

/**
 * Created by haavamoa on 10/29/13.
 */

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.io.PrintWriter;
        import java.net.ServerSocket;
        import java.net.Socket;

        import android.util.Log;

public class Server extends Thread{
    private final static String TAG = "ServerThread";
    private final static int PORT = 12345;
    private String number1;
    private String number2;

    public void run() {
        ServerSocket ss 	= null;
        Socket s 			= null;
        PrintWriter out 	= null;
        BufferedReader in 	= null;

        try{

            Log.i(TAG,"start server....");
            ss = new ServerSocket(PORT);
            Log.i(TAG,"serversocket created, wait for client....");

            while(true){
            s = ss.accept();
            Log.v(TAG, "client connected...");
            out = new PrintWriter(s.getOutputStream(), true);
            in = new BufferedReader(
                    new InputStreamReader(s.getInputStream()));

            out.println("Welcome client...");//send text to client

            String res = in.readLine();//receive calculation text from client
            Log.i(TAG,"Message from client: " + res);
            if(res.contains("calculate"))out.println(Calculate(res)); //Send the answer to the client
            Log.i(TAG,"Sent answer to client");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{//close sockets!!
            try{
                out.close();
                in.close();
                s.close();
                ss.close();
            }catch(Exception e){}
        }
    }

    /**
     * Calculate the "calculate"-string
     * Splits the string on :
     *        tokens[0] will contain the string "calculate", not much use other than to recognize the string for calculation.
     *        tokens[1] will be the first number to use in the calculation.
     *        tokens[2] will be the second number to use in the calculation.
     * @param numbers
     * @return
     */
    public String Calculate(String numbers){
        String[] tokens = numbers.split(":");
        number1 = tokens[1];
        number2 = tokens[2];
        int number1Int = Integer.parseInt(number1);
        int number2Int = Integer.parseInt(number2);
        int answerInt = number1Int+number2Int;
        return "answer:"+answerInt;
    }
}
