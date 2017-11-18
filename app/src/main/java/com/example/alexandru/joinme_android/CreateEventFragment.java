package com.example.alexandru.joinme_android;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.alexandru.joinme_android.Helpers.SharedPreferencesHelper;
import com.example.alexandru.joinme_android.domain.Event;
import com.example.alexandru.joinme_android.domain.User;

import java.util.Date;

public class CreateEventFragment extends Fragment {
    Spinner eventSpinner;
    Date date;
    EditText name;
    EditText description;
    CheckBox open;
    Button add;
    TimePicker time;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.map_fragment, container, false);
        eventSpinner = (Spinner) v.findViewById(R.id.type);
        name = (EditText) v.findViewById(R.id.name);
        description = (EditText) v.findViewById(R.id.description);
        open = (CheckBox) v.findViewById(R.id.open);
        add = (Button) v.findViewById(R.id.add);
        String user = SharedPreferencesHelper.getUserEmail(getActivity());


        add.setOnClickListener(new View.OnClickListener() {
            Event event;
            @Override
            public void onClick(View view) {
                if (eventSpinner.isSelected() && name.toString() != "")

                    event = new Event(name.toString(), description.toString(), date.toString(),
                            time.toString(), "test", null, );


            }
        });
        return v;
    }
}
