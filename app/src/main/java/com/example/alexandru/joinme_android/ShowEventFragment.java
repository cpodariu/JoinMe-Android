package com.example.alexandru.joinme_android;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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

public class ShowEventFragment extends Fragment implements OnMapReadyCallback {
    Button b;

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
}
