package com.example.myapplication.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.AddressLookup;
import com.example.myapplication.R;

public class SearchFragment extends Fragment {

    Button buttonSearchAddress;
    EditText editTextStreetName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_fragment, null);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        buttonSearchAddress = view.findViewById(R.id.buttonSearchAddress);
        editTextStreetName = view.findViewById(R.id.editText_streetname);


        // creates a onclick listener to pass the street name from the edittext field, goes to AddressLookup class
        buttonSearchAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String street = editTextStreetName.getText().toString();

                if(street.isEmpty() || street.equals(null)) {
                    Toast.makeText(getContext(), "The field: street name can't be empty", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getContext(), AddressLookup.class);
                    intent.putExtra("street", street.trim().toUpperCase());
                    startActivity(intent);
                }

            }
        });

    }
}
