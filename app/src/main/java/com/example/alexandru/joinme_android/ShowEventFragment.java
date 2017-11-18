package com.example.alexandru.joinme_android;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alexandru.joinme_android.Helpers.NetworkUtils;
import com.example.alexandru.joinme_android.Helpers.SharedPreferencesHelper;
import com.example.alexandru.joinme_android.domain.Event;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Alexandru on 11/17/2017.
 */

public class ShowEventFragment extends Fragment implements OnMapReadyCallback,View.OnClickListener{
    Button button2;


    MainActivity baseActivity;

    private MapView mMapView;
    private GoogleMap mGoogleMap;

    private TextView textView2;
    private TextView textView3;
    private TextView textView4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = container.inflate(getActivity(), R.layout.show_event_fragment, null);

        button2 = (Button) view.findViewById(R.id.button2);
        button2.setOnClickListener(this);

        mMapView = (MapView) view.findViewById(R.id.map_dashBoard);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(this);

        textView2 = (TextView) view.findViewById(R.id.textView2);
        textView3 = (TextView) view.findViewById(R.id.textView3);
        textView4 = (TextView) view.findViewById(R.id.textView4);

        this.setEventText("test1");
        this.setDescriptionText("test2");
        this.setDateText("test3");

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(-34, 151);
        mGoogleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public void setEventText(String eventText) {
        textView2.setText(eventText);
    }

    public void setDescriptionText(String descriptionText) {
        textView3.setText(descriptionText);
    }

    public void setDateText(String dateText) {
        textView4.setText(dateText);
    }

    @Override
    public void onClick(View v) {
        String email = SharedPreferencesHelper.getUserEmail(getActivity());
        String password = SharedPreferencesHelper.getUserPassword(getActivity());
        Event event = new Event(1, "Party Hard", "Bautura si manele la hackaton", "18.11.2017", "20:00", "46.790719, 23.607913", 1);
        Event event2 = new Event(3, "Alergat", "Fugit la caterinca prin parc", "20.11.2017", "10:00", "46.792719, 23.607913", 1);
        String eventid = Integer.toString(event.getId());
        /*
        Event{id=1, name='Party Hard', description='Bautura si manele la hackaton', date='18.11.2017', time='20:00', location='46.790719, 23.607913', users=null, admin=1}
6:15
Event{id=3, name='Alergat', description='Fugit la caterinca prin parc', date='20.11.2017', time='10:00', location='46.792719, 23.607913', users=null, admin=1}
         */
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest sr = new StringRequest(com.android.volley.Request.Method.GET, NetworkUtils.buildJoinEventUrl(email, password, eventid), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                button2.setEnabled(false);
                button2.setText("Joined");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(sr);

    }
}
