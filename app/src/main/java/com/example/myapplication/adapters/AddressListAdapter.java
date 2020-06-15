package com.example.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.CollectionDateActivity;
import com.example.myapplication.R;
import com.example.myapplication.models.Address;
import com.example.myapplication.models.CollectionDates;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 *  Adapter for recycler view for HomeFragment.
 */
public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.AddressViewHolder> implements View.OnClickListener {

    private final ArrayList<Address> list;
    private LayoutInflater inflater;

    /**
     *  constructor
     * @param context
     * @param list
     */
    public AddressListAdapter(Context context, ArrayList<Address> list) {
        inflater = LayoutInflater.from(context);
        this.list = list;
    }

    /**
     * creates a new addressViewHolder and inflates the layout
     * @param viewGroup
     * @param i
     * @return
     */
    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = inflater.inflate(R.layout.addresseslist, viewGroup,false);
        return new AddressViewHolder(itemView, this);
    }

    /**
     *  binds the data from the view holder to the model
     * @param holder
     * @param i
     */
    @Override
    public void onBindViewHolder(@NonNull final AddressViewHolder holder, int i) {
        Address current = list.get(i);
        holder.id.setText(current.getAddressid());
        holder.add1.setText(current.getAddressOne());
        holder.add2.setText(current.getAddressTwo());
        holder.street.setText(current.getStreet());
        holder.town.setText(current.getTown());
        holder.locality.setText(current.getLocality());

        // adds a colour pattern to recycler view items depending what they are odd or even.
        if(i %2 == 1)
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        else
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFAF8FD"));
        }

        // onclick to pass the addressid of the clicked item to the collectionDateActivity
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CollectionDateActivity.class);
                intent.putExtra("addressid", getAddressId(v));
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View v) {
        // not needed, refer to onClick on line 56 - 61 (subject to change)
    }

    /**
     * @return size of list.
     */
    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * used with onclick to get ID, to pass it to another activity.
     * @param v
     * @return id
     */
    public String getAddressId(View v) {
        TextView id = v.findViewById(R.id.id);
        return id.getText().toString();
    }

    /**
     *  inner class to set the view holders for recycler view.
     */
    class AddressViewHolder extends RecyclerView.ViewHolder {
        public final TextView id;
        public final TextView add1;
        public final TextView add2;
        public final TextView street;
        public final TextView town;
        public final TextView locality;

        final AddressListAdapter madapter;

        public AddressViewHolder(View itemView, AddressListAdapter adapter) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            add1 = itemView.findViewById(R.id.add1);
            add2 = itemView.findViewById(R.id.add2);
            street = itemView.findViewById(R.id.street);
            town = itemView.findViewById(R.id.town);
            locality = itemView.findViewById(R.id.locality);

            this.madapter = adapter;
        }

    }

}