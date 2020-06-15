package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.myapplication.models.CollectionDates;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

public class CollectionDateActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirestoreRecyclerAdapter<CollectionDates, CollectionDateActivity.colDateHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_date);

        RecyclerView recyclerView = findViewById(R.id.ColDatesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // gets the data passed from the previous activity (HomeFragment)
        Intent intent = getIntent();
        String addID = intent.getStringExtra("addressid");

        // gets firebase instance
        db = FirebaseFirestore.getInstance();

        // query to return the result based on the addressid
        Query query = db.collection("collections").whereEqualTo("addressid", addID).orderBy("date");

        //  sets up the query and binds it to the CollectionDates model.
        FirestoreRecyclerOptions<CollectionDates> options = new FirestoreRecyclerOptions.Builder<CollectionDates>()
                .setQuery(query, CollectionDates.class)
                .build();

        // creates a new firestore adapter and binds the recycler view to the viewholder items.
        adapter = new FirestoreRecyclerAdapter<CollectionDates, colDateHolder>(options) {
            @Override
            public void onBindViewHolder(colDateHolder holder, int position, CollectionDates model) {

                holder.setColId(model.getAddressid());
                holder.setType(model.getType());
                holder.setDate(model.getDate());

                // colours the recycler view items.
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
            public CollectionDateActivity.colDateHolder onCreateViewHolder(ViewGroup group, int i) {
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.coldatelist, group, false);

                return new CollectionDateActivity.colDateHolder(view);
            }

            @NonNull
            @Override
            public CollectionDates getItem(int position) {
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
     *  inner class to bind CollectionDate properties to recycler view
     */
    public class colDateHolder extends RecyclerView.ViewHolder {
        private View v;

        public colDateHolder(final View itemView) {
            super(itemView);
            v = itemView;

            // onclick to create a new alarmmanager instance (not implemented yet)
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Not implemented", Toast.LENGTH_SHORT).show();
                }
            });
        }

        /**
         *  sets the id in view holder
         * @param col_id
         */
        public void setColId(String col_id) {
            TextView id = v.findViewById(R.id.col_id);
            id.setText(col_id);
        }

        /**
         *  sets the type of bin in the view holder
         * @param type
         */
        public void setType(String type) {
            TextView bintype = v.findViewById(R.id.type);
            bintype.setText(type);
        }

        /**
         *  sets the date in the view holder
         * @param date
         */
        public void setDate(String date) {
            TextView coldate = v.findViewById(R.id.date);
            coldate.setText(date);
        }

    }

    // tells the adapter to make a connection and return the result set
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    // after the resultset is returned, stops the adapter from populating the recycler view.
    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }



}
