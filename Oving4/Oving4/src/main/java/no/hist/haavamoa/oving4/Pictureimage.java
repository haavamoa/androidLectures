package no.hist.haavamoa.oving4;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by haavamoa on 10/28/13.
 */
public class Pictureimage extends Fragment {
    private ImageView image; //Picture in the fragment
    private TextView text; //Description of the picture in the fragment
    private Button forward; //Forward button
    private Button back; //Back button
    private View v;
    private int[] images =  {R.drawable.picture1,R.drawable.picture2,R.drawable.picture3};
    private String[] descriptions = {"Dette er en søt hund","Denne er mye søtere?","Dette er den søteste"};
    private int imageTracker = -1; //If it is -1, you havent chosen any picture.

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.pictureimage,container,false);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        image = (ImageView)getActivity().findViewById(R.id.imageView);
        text = (TextView)getActivity().findViewById(R.id.textView);
        forward = (Button)getActivity().findViewById(R.id.forward);
        back = (Button)getActivity().findViewById(R.id.back);
        //Forward button onclicklistener
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageTracker < 2){
                    imageTracker++;
                    changeImage(imageTracker);
                }else{
                    System.out.println("jaij");
                    imageTracker=0;
                    changeImage(imageTracker);
                }
            }
        });
        //Back button onclicklistener
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageTracker > 0){
                    imageTracker--;
                    changeImage(imageTracker);
                }else{
                    imageTracker=2;
                    changeImage(imageTracker);
                }
            }
        });
        super.onActivityCreated(savedInstanceState);
    }

    /**
     *
     * @param position Position in the list from the listfragment.
     */
    public void changeImage(int position){
        image.setImageResource(images[position]);
        imageTracker = position;
        changeText(position);
    }

    /**
     * Method to change the desction text, this is private because it has nothing to do outside this class.
     * @param position The same number as the picture.
     */
    private void changeText(int position){
    text.setText(descriptions[position]);
    }
}
