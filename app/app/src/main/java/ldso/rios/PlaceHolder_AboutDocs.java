package ldso.rios;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link PlaceHolder_AboutDocs#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlaceHolder_AboutDocs extends Fragment {



    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    protected int number=-1;

    /**
     * Returns a new instance of this fragment for the given sectio.li
     * number.
     */
    public static PlaceHolder_AboutDocs newInstance(int sectionNumber) {
        PlaceHolder_AboutDocs fragment = new PlaceHolder_AboutDocs(sectionNumber);
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public PlaceHolder_AboutDocs() {
    }

    public PlaceHolder_AboutDocs(int number) {
        this.number=number;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("teste","number:"+number);
        View rootView=null;
        if(number==0)
            rootView = inflater.inflate(R.layout.fragment_info_about, container, false);
        else{
            rootView=inflater.inflate(R.layout.fragment_info_doc,container,false);
            TextView link = (TextView) rootView.findViewById(R.id.link_docs_irr_1);
            link.setMovementMethod(LinkMovementMethod.getInstance());
            link=(TextView) rootView.findViewById(R.id.link_docs_irr_2);
            link.setMovementMethod(LinkMovementMethod.getInstance());
            link=(TextView) rootView.findViewById(R.id.link_docs_irr_3);
            link.setMovementMethod(LinkMovementMethod.getInstance());
            link=(TextView) rootView.findViewById(R.id.link_docs_other_1);
            link.setMovementMethod(LinkMovementMethod.getInstance());
            link=(TextView) rootView.findViewById(R.id.link_docs_other_2);
            link.setMovementMethod(LinkMovementMethod.getInstance());
            link=(TextView) rootView.findViewById(R.id.link_docs_other_3);
            link.setMovementMethod(LinkMovementMethod.getInstance());
            link=(TextView) rootView.findViewById(R.id.link_docs_other_4);
            link.setMovementMethod(LinkMovementMethod.getInstance());
            link=(TextView) rootView.findViewById(R.id.link_docs_other_5);
            link.setMovementMethod(LinkMovementMethod.getInstance());
            link=(TextView) rootView.findViewById(R.id.link_docs_other_6);
            link.setMovementMethod(LinkMovementMethod.getInstance());
            link=(TextView) rootView.findViewById(R.id.link_docs_other_7);
            link.setMovementMethod(LinkMovementMethod.getInstance());
        }
        return rootView;
    }

}
