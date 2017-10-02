package ca.ualberta.a1484506_countbook_20;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kristian on 2017-10-01.
 */

public class RecordAdapter extends ArrayAdapter<Counter> {

    public RecordAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Counter> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //return super.getView(position, convertView, parent);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_counter, null);
        }

        Counter counter = getItem(position);
        if (counter != null) {
            TextView name = (TextView) convertView.findViewById(R.id.list_counter_name);
            TextView date = (TextView) convertView.findViewById(R.id.list_counter_date);
            TextView comments = (TextView) convertView.findViewById(R.id.list_counter_comments);

            name.setText(counter.getName());
            date.setText(counter.getDateToString(getContext()));
            if (counter.getComments().length() > 50){
                comments.setText(counter.getComments().substring(0,50));
            } else {
                comments.setText(counter.getComments());
            }

        }

        return convertView;
    }
}
