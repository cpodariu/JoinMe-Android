package com.example.alexandru.joinme_android;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Alexandru on 11/17/2017.
 */

public class TestFragment extends Fragment {
    Button b;

    MainActivity baseActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = container.inflate(getActivity(), R.layout.test_fragment, null);

        b = (Button) v.findViewById(R.id.test_button);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "MAiiii!", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }
}
