package br.edu.utfpr.organizadordeviagem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AccommodationAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Accommodation> accommodations;

    public AccommodationAdapter(Context context, ArrayList<Accommodation> accommodations) {
        this.context = context;
        this.accommodations = accommodations;
    }

    @Override
    public int getCount() {
        return accommodations.size();
    }

    @Override
    public Object getItem(int position) {
        return accommodations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_accommodation, parent, false);
        }

        Accommodation accommodation = accommodations.get(position);

        TextView nameTextView = convertView.findViewById(R.id.nameTextView);
        TextView addressTextView = convertView.findViewById(R.id.addressTextView);
        TextView checkInDateTextView = convertView.findViewById(R.id.checkInDateTextView);
        TextView checkOutDateTextView = convertView.findViewById(R.id.checkOutDateTextView);

        nameTextView.setText(accommodation.getName());
        addressTextView.setText(accommodation.getAddress());
        checkInDateTextView.setText(accommodation.getCheckInDate());
        checkOutDateTextView.setText(accommodation.getCheckOutDate());

        return convertView;
    }
}