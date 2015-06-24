package com.flow.app.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.flow.app.Model.Event;

import java.util.ArrayList;

import static com.flow.app.R.id;
import static com.flow.app.R.layout;

class EventListAdapter  extends ArrayAdapter<Event> {

    public EventListAdapter(Context context, ArrayList<Event> values) {
        super(context, android.R.layout.simple_list_item_1,values); // change here
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View eventCell = convertView;

        if (eventCell == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            eventCell = inflater.inflate(layout.event_cell, null);
        }

        Event event = getItem(position);

        if (event != null) {
            TextView eventdate = (TextView) eventCell.findViewById(id.event_date_textView);
            TextView evenTitle = (TextView) eventCell.findViewById(id.event_title_textview);
            TextView eventVenueCity = (TextView) eventCell.findViewById(id.event_venue_city_textview);

            //ImageView eventImage = (ImageView) eventCell.findViewById(id.event_cover_imageview);

            eventdate.setText(event.getDateStr());
            evenTitle.setText(event.getTitle());
            eventVenueCity.setText(event.getVenue() + " - " + event.getCity());

        }

        return eventCell;
    }
}
