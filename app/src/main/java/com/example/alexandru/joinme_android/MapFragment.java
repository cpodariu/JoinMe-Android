package com.example.alexandru.joinme_android;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
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
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.util.HashMap;

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
                        if(e!=null){
                            final FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ShowEventFragment eventFragment=new ShowEventFragment(e);
                            ft.replace(R.id.frag_container_id,eventFragment, "NewFragmentTag");
                            ft.addToBackStack(null);
                            ft.commit();
                        }
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

    public int getDrawableId(String name){
        try {
            Field fld = R.drawable.class.getField(name);
            return fld.getInt(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    private Drawable resize(Drawable image) {
        Bitmap b = ((BitmapDrawable)image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 80, 120, false);
        return new BitmapDrawable(getResources(), bitmapResized);
    }

    private BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private void drawEventMarkers(String jsonArray) {

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
                    .snippet(""+numberOfUsers+" participants.");
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.other_pin));
            String pinName=e.getCategory().toLowerCase()+"_"+e.getName().toLowerCase()+"_pin";
            int drawableId=getDrawableId(pinName);
            Drawable newSizeImage;
            if(drawableId!=-1) {
                newSizeImage = resize(ContextCompat.getDrawable(getActivity(), drawableId));
            }
            else{
                newSizeImage = resize(ContextCompat.getDrawable(getActivity(), R.drawable.other_pin));
            }
            BitmapDescriptor markerIcon = getMarkerIconFromDrawable(newSizeImage);
            markerOptions.icon(markerIcon);


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
