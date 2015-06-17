package app.fragments.Base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import app.program.OtherActivity;
import app.program.R;

/**
 * Not for public use
 * Created by FAIZ on 30-04-2015.
 */
public class Help extends Fragment {
    public TextView tv,tv1,tv2;
    public EditText et,et1,et2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_help, container, false);
        ((OtherActivity) getActivity()).setActionBarTitle(R.string.toolbar_help);

        tv = (TextView)view.findViewById(R.id.Kisaancallcentre);
        tv1 = (TextView)view.findViewById(R.id.iiss);
        tv2 = (TextView)view.findViewById(R.id.ciae);
        et = (EditText)view.findViewById(R.id.editText);
        et1 = (EditText)view.findViewById(R.id.editText2);
        et2 = (EditText)view.findViewById(R.id.editText3);
        return view;
    }
}
