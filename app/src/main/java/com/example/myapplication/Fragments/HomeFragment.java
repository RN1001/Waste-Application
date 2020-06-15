package com.example.myapplication.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.adapters.AddressListAdapter;
import com.example.myapplication.models.Address;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    RecyclerView myAddressRecyclerView;
    ArrayList<Address> myAddressList;
    AddressListAdapter mAdapter;

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return new View object.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // creates a new view, inflates with a fragment layout
        View myview = inflater.inflate(R.layout.home_fragment, null);

        // create new addresslist for recycler view.
        myAddressList = new ArrayList<>();

        myAddressRecyclerView = myview.findViewById(R.id.MyAddressRecyclerView);

        // reads data to put into recycler view before adapter is set
        ReadAddressData(getContext(), myAddressList);

        // binds readfile data into the adapter and sets the adapter
        mAdapter = new AddressListAdapter(getContext(), myAddressList);
        myAddressRecyclerView.setAdapter(mAdapter);

        // creates a new layout manager for recycler view items
        myAddressRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return myview;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

    /**
     * finds file called addresses.txt in internal storage, reads data, then adds into a list
     * reads all the data first from the txt file before displaying all the data in the recycler view.
     * @param context
     * @param list
     */
    public void ReadAddressData(Context context, ArrayList<Address> list) {
        File path = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "/wasteData/");
        File data = new File(path, "addresses.txt");

        try {
            String line;
            String [] addresses;

            BufferedReader br = new BufferedReader(new FileReader(data));

            while ((line = br.readLine()) != null) {

                addresses = line.trim().split(",");

                list.add(new Address(addresses[0], addresses[1], addresses[2], addresses[3], addresses[4], addresses[5]));
            }

        } catch (IOException e) {
            Log.e("Error", e.toString());
        }

    }

}
