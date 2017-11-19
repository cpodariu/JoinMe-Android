package com.example.alexandru.joinme_android;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alexandru.joinme_android.Helpers.NetworkUtils;
import com.example.alexandru.joinme_android.Helpers.SharedPreferencesHelper;
import com.example.alexandru.joinme_android.domain.Event;
import com.example.alexandru.joinme_android.domain.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Alexandru on 11/17/2017.
 */

@SuppressLint("ValidFragment")
public class ShowEventFragment extends Fragment implements OnMapReadyCallback,View.OnClickListener{
    FloatingActionButton button2;


    MainActivity baseActivity;

    private MapView mMapView;
    private GoogleMap mGoogleMap;

    private TextView textView2;
    private TextView textView3;
    private TextView textView4;

    private Event event;

    private double latitude;
    private double longitude;

    private Boolean check;
    @SuppressLint("ValidFragment")
    public ShowEventFragment(Event event) {
        this.event = event;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = container.inflate(getActivity(), R.layout.show_event_fragment, null);

        check = false;

        String email = SharedPreferencesHelper.getUserEmail(getActivity());
        for (User user : event.getUsers()) {
            if (user.getEmail().equals(email)) {
                check = true;
                break;
            }
        }

        String[] coordinates = event.getLocation().split(", ");
        latitude = Double.parseDouble(coordinates[0]);
        longitude = Double.parseDouble(coordinates[1]);

        button2 = (FloatingActionButton) view.findViewById(R.id.button2);
        button2.setOnClickListener(this);

        if (check) {
            button2.setEnabled(false);
            button2.setImageResource(R.drawable.delete_icon);
        }

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

        this.setEventText(event.getName());
        this.setDescriptionText(event.getDescription());
        this.setDateText(event.getDate());


        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        LatLng location = new LatLng(latitude, longitude);

        mGoogleMap.addMarker(new MarkerOptions().position(location).title(""));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(location).zoom(16).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
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
        //Event event = new Event(1, "Party Hard", "Bautura si manele la hackaton", "18.11.2017", "20:00", "46.790719, 23.607913", 1);
        //Event event2 = new Event(3, "Alergat", "Fugit la caterinca prin parc", "20.11.2017", "10:00", "46.792719, 23.607913", 1);
        String eventid = Integer.toString(event.getId());


        if (!check) {
            RequestQueue queue = Volley.newRequestQueue(getActivity());
            StringRequest sr = new StringRequest(Request.Method.GET, NetworkUtils.buildJoinEventUrl(email, password, eventid), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    button2.setEnabled(false);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            queue.add(sr);
        }

    }
}
