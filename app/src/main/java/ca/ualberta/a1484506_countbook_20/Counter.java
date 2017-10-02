package ca.ualberta.a1484506_countbook_20;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.icu.util.TimeZone;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Stanislav Lebedev on 2017-09-30.
 */

public class Counter implements Serializable {
    private String Name;
    private long DateTime;
    private String Comments;
    private int Value;


    public Counter(long dateTime, String name,  String comments, int value) {
        Name = name;
        DateTime = dateTime;
        Comments = comments;
        Value = value;

    }

    public void setName(String name) {
        Name = name;
    }

    public void setDateTime(long dateTime) {
        DateTime = dateTime;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public long getDateTime() {
        return DateTime;
    }

    public String getName() {
        return Name;
    }

    public String getComments() {
        return Comments;
    }
    // make date to string in necessary format
    public String getDateToString(Context context){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"
                , context.getResources().getConfiguration().locale);
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(new Date(DateTime));

    }
}
