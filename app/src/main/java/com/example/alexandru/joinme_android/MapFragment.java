package com.example.alexandru.joinme_android;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alexandru.joinme_android.domain.Event;
import com.example.alexandru.joinme_android.domain.response.EventResponse;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandru on 11/18/2017.
 */

public class MapFragment extends Fragment  {
    MapView mMapView;
    private GoogleMap googleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.map_fragment, container, false);

        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                //googleMap.setMyLocationEnabled(true);

                // For dropping a marker at a point on the Map

                googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        Toast.makeText(getActivity(), "MAiiii!", Toast.LENGTH_SHORT).show();
                    }
                });

                LatLng cluj = new LatLng(46.782719, 23.607913);
                googleMap.addMarker(new MarkerOptions()
                                    .position(cluj)
                                    .title("Marker Title")
                                    .snippet("Marker Description")
                                    //.icon(BitmapDescriptorFactory.fromResource(R.drawable.test))
                                    );
                drawEventMarkers();

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(cluj).zoom(16).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });



        return v;
    }

    private void drawEventMarkers() {

        String jsonArray=""; // to get
        /*
        Type eventResponseType = new TypeToken<EventResponse>(){}.getType();
        EventResponse eventResponse = new Gson().fromJson(jsonArray, eventResponseType);
        */


        LatLng ev1 = new LatLng(46.790719, 23.607913);
        LatLng ev2 = new LatLng(46.794719, 23.597913);
        LatLng ev3 = new LatLng(46.762719, 23.627913);
        LatLng ev4 = new LatLng(46.792719, 23.607913);
        LatLng ev5 = new LatLng(46.779719, 23.605913);
        List<LatLng> list=new ArrayList<>();
        list.add(ev1);
        list.add(ev2);
        list.add(ev3);
        list.add(ev4);
        list.add(ev5);
        for(LatLng ll:list){
            googleMap.addMarker(new MarkerOptions()
                    .position(ll)
                    .title("titlu")
                    .snippet("Marker Description")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.test)));
        }
        /*
        for(Event e:eventResponse.getEvents()){
            String[] coordStr = e.getLocation().split(",");
            LatLng coord=new LatLng(Float.parseFloat(coordStr[0]),Float.parseFloat(coordStr[1]));
            googleMap.addMarker(new MarkerOptions()
                    .position(coord)
                    .title(e.getName())
                    .snippet("Marker Description")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.test)));
        }*/
        //to do
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
