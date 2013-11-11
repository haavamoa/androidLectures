package no.hist.haavamoa.oving4;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by haavamoa on 10/28/13.
 */
public class Picturetitle extends ListFragment {
    private Pictureimage frag;
    private String[] titles = {"Picture1","Picture2","Picture3"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,titles));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        frag = (Pictureimage) getFragmentManager().findFragmentById(R.id.pictureimage);
        if(frag!=null && frag.isInLayout())
            frag.changeImage(position);

    }
}
