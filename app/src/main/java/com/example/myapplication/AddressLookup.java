package com.example.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.models.Address;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AddressLookup extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirestoreRecyclerAdapter<Address, addressHolder> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_lookup);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // gets the data from the previous activity.
        Intent intent = getIntent();
        String street = intent.getStringExtra("street");

        // gets firebase instance
        db = FirebaseFirestore.getInstance();

        // returns a resultset from the query where the street name equals to the street name provided from the user.
        Query query = db.collection("userAddresses").whereEqualTo("street", street);

        // sets up query, binds to model
        FirestoreRecyclerOptions<Address> options = new FirestoreRecyclerOptions.Builder<Address>()
                .setQuery(query, Address.class)
                .build();

        // creates a new firestore adapter and binds the recycler view to the viewholder items.
        adapter = new FirestoreRecyclerAdapter<Address, addressHolder>(options) {
            @Override
            public void onBindViewHolder(addressHolder holder, int position, Address model) {

                holder.setId(model.getAddressid());
                holder.setAdd1(model.getAddressOne());
                holder.setAdd2(model.getAddressTwo());
                holder.setStreet(model.getStreet());
                holder.setTown(model.getTown());
                holder.setLocality(model.getLocality());

                // colours the items
                if(position %2 == 1)
                {
                    holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                }
                else
                {
                    holder.itemView.setBackgroundColor(Color.parseColor("#FFFAF8FD"));
                }

            }

            @Override
            public addressHolder onCreateViewHolder(ViewGroup group, int i) {
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.addresseslist, group, false);

                return new addressHolder(view);
            }

            @NonNull
            @Override
            public Address getItem(int position) {
                return super.getItem(position);
            }

            @Override
            public void onError(FirebaseFirestoreException e) {
                Log.e("error", e.getMessage());
            }
        };

        recyclerView.setAdapter(adapter);
    }

    /**
     *  inner class addressholder binds the model to the recycler view.
     */
    public class addressHolder extends RecyclerView.ViewHolder {
        private View v;

        public addressHolder(final View itemView) {
            super(itemView);
            v = itemView;

            // creates a new alertdialog and asks if the user would like to save the address.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getApplicationContext(),"onclick", Toast.LENGTH_SHORT).show();
                    final AlertDialog.Builder builder = new AlertDialog.Builder(AddressLookup.this);
                    builder.setMessage("Save this Address?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(),"Address Saved", Toast.LENGTH_SHORT).show();
                            writeAddressIdToFile(getApplicationContext(), getId(), getAdd1(), getAdd2(), getStreet(), getTown(), getLocality());
                            Intent intent = new Intent(AddressLookup.this, MainActivityFrag.class);
                            startActivity(intent);
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                }
            });
        }

        /**
         * creates a new directory in internal storage, if it exists, ignore it, creates a new file called addresses.txt, if it exists already, system ignores it
         *  writes the model data into a .txt for it to be read in the HomeFragment class.
         * @param context
         * @param id
         * @param add1
         * @param add2
         * @param street
         * @param town
         * @param locality
         */
        public void writeAddressIdToFile(Context context, String id, String add1, String add2, String street, String town, String locality) {

            try  {
                File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "/wasteData/");

                if(!file.exists()) {
                    file.mkdirs();
                }

                File data = new File(file, "addresses.txt");

                if (!data.exists()) {
                    data.createNewFile();
                }

                BufferedWriter writer = new BufferedWriter(new FileWriter(data,true));

                writer.write(id + "," + add1 + "," + add2 + "," + street + "," + town + "," + locality + "\n");

                writer.flush();
                writer.close();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "Could not save address!", Toast.LENGTH_SHORT).show();
                Log.e("Could not save address", e.toString());
            }
        }

        /*
            Binds all the setters data to the addresslist layout file.
            the getter methods are used to pass the data into the writeAddressIdtoFile.
        */

        public void setId(String addressid) {
            TextView id = v.findViewById(R.id.id);
            id.setText(addressid);
        }

        public String getId() {
            TextView id = v.findViewById(R.id.id);
            return id.getText().toString();
        }

        public void setAdd1(String address1) {
            TextView add1 = v.findViewById(R.id.add1);
            add1.setText(address1);
        }

        public String getAdd1() {
            TextView add1 = v.findViewById(R.id.add1);
            return add1.getText().toString();
        }

        public void setAdd2(String address2) {
            TextView add2 = v.findViewById(R.id.add2);
            add2.setText(address2);
        }

        public String getAdd2() {
            TextView add2 = v.findViewById(R.id.add2);
            return add2.getText().toString();
        }

        public void setStreet(String st) {
            TextView street = v.findViewById(R.id.street);
            street.setText(st);
        }

        public String getStreet() {
            TextView street = v.findViewById(R.id.street);
            return street.getText().toString();
        }

        public void setTown(String mtown) {
            TextView town = v.findViewById(R.id.town);
            town.setText(mtown);
        }

        public String getTown() {
            TextView town = v.findViewById(R.id.town);
            return town.getText().toString();
        }

        public void setLocality(String locality) {
            TextView loc = v.findViewById(R.id.locality);
            loc.setText(locality);
        }

        public String getLocality() {
            TextView loc = v.findViewById(R.id.locality);
            return loc.getText().toString();
        }

    }

    /*
     tells the adapter to populate the recycler view and stop when the it iterated to the last result.
     */

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }


}
