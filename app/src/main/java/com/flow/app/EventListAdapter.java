package com.flow.app;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import models.Event;

import java.util.ArrayList;

import static com.flow.app.R.*;

class EventListAdapter  extends ArrayAdapter<Event> {

    private ArrayList<Event> events;

    public EventListAdapter(Context context, int textViewResourceId, ArrayList<Event> events) {
        super(context, textViewResourceId, events);
        this.events = events;
    }

    public EventListAdapter(Context context, ArrayList<Event> values) {
        super(context, android.R.layout.simple_list_item_1,values); // change here
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View eventCell = convertView;

        if (eventCell == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            eventCell = inflater.inflate(layout.event_cell, null);
        }

        Event event = getItem(position);

        if (event != null) {
            //TextView eventdate = (TextView) eventCell.findViewById(id.event_date_textview);
            TextView evenTitle = (TextView) eventCell.findViewById(id.event_title_textview);
            TextView eventVenue = (TextView) eventCell.findViewById(id.event_venue_textview);
            TextView eventCity = (TextView) eventCell.findViewById(id.event_city_textview);
            ImageView eventImage = (ImageView) eventCell.findViewById(id.event_cover_imageview);

            //eventdate.setText(event.getDateStr());
            evenTitle.setText(event.getTitle());
            eventVenue.setText(event.getVenue());
            eventCity.setText(event.getCity());
        }

        return eventCell;
    }
}
