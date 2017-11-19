package com.example.alexandru.joinme_android;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alexandru.joinme_android.Helpers.NetworkUtils;
import com.example.alexandru.joinme_android.Helpers.SharedPreferencesHelper;
import com.example.alexandru.joinme_android.domain.Event;
import com.example.alexandru.joinme_android.domain.User;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CreateEventFragment extends Fragment {
    Spinner eventSpinner;
    EditText date;
    EditText name;
    EditText description;
    CheckBox open;
    Button add;
    EditText time;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_event_layout, container, false);

    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        eventSpinner = (Spinner) v.findViewById(R.id.type);
        name = (EditText) v.findViewById(R.id.name);
        description = (EditText) v.findViewById(R.id.description);
        open = (CheckBox) v.findViewById(R.id.open);
        add = (Button) v.findViewById(R.id.add);
        date = v.findViewById(R.id.date);
        time = v.findViewById(R.id.time);

        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(getActivity(), R.array.event_type,
                        android.R.layout.simple_spinner_item);
        eventSpinner.setAdapter(staticAdapter);


        add.setOnClickListener(new View.OnClickListener() {
            Event event;
            @Override
            public void onClick(View view) {
                if (name.toString() != "") {

                    event = new Event(name.getText().toString(), description.getText().toString(), eventSpinner.getSelectedItem().toString(),
                            date.getText().toString(), time.getText().toString(), "43.023412,3.23", null, 0, open.hasFocus());
                    RequestQueue queue = Volley.newRequestQueue(getActivity());
                    final String email = SharedPreferencesHelper.getUserEmail(getActivity());
                    final String passwd = SharedPreferencesHelper.getUserPassword(getActivity());
                    final String jsonArray = "{\"events\":[" + new Gson().toJson(event) + "]}";
                    String url = "http://192.168.137.103:8080/rest/createEvent?email=" + email + "&password=" + passwd;
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // your response

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                        }
                    }) {
                        @Override
                        public byte[] getBody() throws AuthFailureError {
                            return jsonArray.getBytes();
                        }
                    };
                    queue.add(stringRequest);
                    queue.start();
                }
            }
            });
    }
}
