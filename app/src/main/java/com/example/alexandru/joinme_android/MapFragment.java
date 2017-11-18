package com.example.alexandru.joinme_android;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alexandru.joinme_android.Helpers.NetworkUtils;
import com.example.alexandru.joinme_android.Helpers.SharedPreferencesHelper;
import com.example.alexandru.joinme_android.domain.Event;
import com.example.alexandru.joinme_android.domain.response.EventResponse;
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

import java.util.HashMap;

/**
 * Created by Alexandru on 11/18/2017.
 */

public class MapFragment extends Fragment {
    MapView mMapView;
    private GoogleMap googleMap;
    HashMap<Marker, Event> markerCollection = new HashMap<Marker, Event>();

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
                        Event e=markerCollection.get(marker);

                        final FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ShowEventFragment eventFragment=new ShowEventFragment();
                        ft.replace(R.id.frag_container_id,eventFragment, "NewFragmentTag");
                        ft.addToBackStack(null);
                        ft.commit();


                    }
                });

                LatLng cluj = new LatLng(46.782719, 23.607913);
                googleMap.addMarker(new MarkerOptions()
                                .position(cluj)
                                .title("Marker Title")
                                .snippet("Marker Description")
                        //.icon(BitmapDescriptorFactory.fromResource(R.drawable.test))
                );

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(cluj).zoom(16).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                RequestQueue queue = Volley.newRequestQueue(getActivity());
                String email = SharedPreferencesHelper.getUserEmail(getActivity());
                String passwd = SharedPreferencesHelper.getUserPassword(getActivity());
                String myUrl = NetworkUtils.buildGetEventsByInterestsUrl(email,passwd);
                final String[] jsonArray = {""};
                StringRequest sr = new StringRequest(com.android.volley.Request.Method.GET, myUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        drawEventMarkers(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                queue.add(sr);
            }
        });


        return v;
    }

    private void drawEventMarkers(String jsonArray) {
        // to get

        EventResponse eventResponse = new Gson().fromJson(jsonArray, EventResponse.class);


        for(Event e:eventResponse.getEvents()){
            String[] coordStr = e.getLocation().split(",");
            LatLng coord=new LatLng(Float.parseFloat(coordStr[0]),Float.parseFloat(coordStr[1]));
            int numberOfUsers=0;
            if(e.getUsers()!=null)
                numberOfUsers=e.getUsers().size();
            MarkerOptions markerOptions=new MarkerOptions()
                    .position(coord)
                    .title(e.getName())
                    .snippet(""+numberOfUsers+" participants.")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.test));
            Marker currentMarker = googleMap.addMarker(markerOptions);
            markerCollection.put(currentMarker,e);

        }
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
