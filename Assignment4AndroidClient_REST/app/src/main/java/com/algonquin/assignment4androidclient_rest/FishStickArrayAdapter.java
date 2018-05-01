package com.algonquin.assignment4androidclient_rest;

import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class FishStickArrayAdapter extends ArrayAdapter<FishStick> {
    // class for reusing views as list items scroll off and onto the screen
    private static class ViewHolder {
        TextView idTextView;
        TextView recordNumTextView;
        TextView omegaTextView;
        TextView lambdaTextView;
        TextView uuidTextView;
    }
    // constructor to initialize superclass inherited members
    public FishStickArrayAdapter(Context context, List<FishStick> fishSticks) {
        super(context, -1, fishSticks);
    }
    // creates the custom views for the ListView's items
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        try {
            // get Stuff object for this specified ListView position
            FishStick fishStick = getItem(position);
            ViewHolder viewHolder; // object that reference's list item's views
            // check for reusable ViewHolder from a ListView item that scrolled
            // offscreen; otherwise, create a new ViewHolder
            if (convertView == null) { // no reusable ViewHolder, so create one
                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView =
                        inflater.inflate(R.layout.list_item, parent, false);
                viewHolder.idTextView =
                        convertView.findViewById(R.id.idTextView);
                viewHolder.recordNumTextView =
                        convertView.findViewById(R.id.recordNumTextView);
                viewHolder.omegaTextView =
                        convertView.findViewById(R.id.omegaTextView);
                viewHolder.lambdaTextView =
                        convertView.findViewById(R.id.lambdaTextView);
                viewHolder.uuidTextView =
                        convertView.findViewById(R.id.uuidTextView);
                convertView.setTag(viewHolder);
            } else { // reuse existing ViewHolder stored as the list item's tag
                viewHolder = (ViewHolder) convertView.getTag();
            }
            // get other data from Stuff object and place into views
            Context context = getContext(); // for loading String resources
            viewHolder.idTextView.setText(context.getString(
                    R.string.fishStick_id, fishStick.id));
            viewHolder.recordNumTextView.setText(context.getString(
                    R.string.fishStick_recordNum, fishStick.recordNum));
            viewHolder.omegaTextView.setText(context.getString(
                    R.string.fishStick_omega, fishStick.omega));
            viewHolder.lambdaTextView.setText(context.getString(
                    R.string.fishStick_lambda, fishStick.lambda));
            viewHolder.uuidTextView.setText(context.getString(
                    R.string.fishStick_uuid, fishStick.uuid));
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return convertView; // return completed list item to display
    }
}
