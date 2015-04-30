package app.fragments.Calendar;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import app.adapters.CalendarSelectCropCardView;
import app.fragments.SCalendar.Location;
import app.fragments.SCalendar.SelectCrop;
import app.library.CustomJsonObjectRequest;
import app.library.DatabaseHandler;
import app.program.CalendarActivity;
import app.program.R;

/**
 * Created by admin on 10-04-2015.
 */
public class SoilParameter1 extends Fragment {

    private Button search;
    String selectedState, ph,ec,oc,n,p,k,z,c,i,m,b,s;

    EditText PH,EC,OC,N,P,K,ZN,CU,FE,MN,S;

    public static SoilParameter1 newInstance(String state){
        SoilParameter1 params = new SoilParameter1();
        Bundle bundle = new Bundle();
        bundle.putString("state", state);
        params.setArguments(bundle);
        return params;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            selectedState = getArguments().getString("state");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_soil_parameter1, container, false);
        ((CalendarActivity) getActivity()).setActionBarTitle("Enter Soil Report Details");

        search = (Button) view.findViewById(R.id.jumpbtn4);
        PH = (EditText) view.findViewById(R.id.ph);
        EC = (EditText) view.findViewById(R.id.ec);
        OC = (EditText) view.findViewById(R.id.oc);
        N = (EditText) view.findViewById(R.id.n);
        P = (EditText) view.findViewById(R.id.p);
        K = (EditText) view.findViewById(R.id.k);
        ZN = (EditText) view.findViewById(R.id.zn);
        CU = (EditText) view.findViewById(R.id.cu);
        FE = (EditText) view.findViewById(R.id.fe);
        MN = (EditText) view.findViewById(R.id.mn);
        S = (EditText) view.findViewById(R.id.s);
        search.setOnClickListener(new GoToSelectCrop());

        return view;
    }
    private class GoToSelectCrop implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            ph = PH.getText().toString();
            ec = EC.getText().toString();
            oc = OC.getText().toString();
            n = N.getText().toString();
            p = P.getText().toString();
            k = K.getText().toString();
            z = ZN.getText().toString();
            c = CU.getText().toString();
            i = FE.getText().toString();
            m = MN.getText().toString();
            s = S.getText().toString();

            SelectCrop crop = SelectCrop.newInstance(selectedState, ph,ec,oc,n,p,k,z,c,i,m,s);
            getFragmentManager().beginTransaction()
                    .replace(R.id.calendarFrame, crop)
                    .addToBackStack(null)
                     .commit();
        }
    }

}














