package ca.ualberta.a1484506_countbook_20;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ListView ListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView = (ListView) findViewById(R.id.mainListView);
        setupNewRecButton();

    }

    private void setupNewRecButton(){
        Button newRecButton = (Button) findViewById(R.id.addNewRecButton);
        newRecButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CounterActivity.class));
            }
        });

    }

    @Override
    protected void onResume(){
        super.onResume();
        ListView.setAdapter(null);

        ArrayList<Counter> counters = Utilities.getAllSavedRecords(getApplicationContext());

        if(counters == null || counters.size() == 0){
            Toast.makeText(this, "You have no records.", Toast.LENGTH_SHORT).show();
            return;
        } else {
            RecordAdapter ra = new RecordAdapter(this, R.layout.item_counter, counters);
            ListView.setAdapter(ra);

            ListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String fileName = ((Counter)ListView.getItemAtPosition(position)).getDateTime()
                            + Utilities.FILE_EXTENSION;

                    Intent viewRecordIntent = new Intent(getApplicationContext(), CounterActivity.class);
                    viewRecordIntent.putExtra(Utilities.FILE_EXTENSION2, fileName);
                    startActivity(viewRecordIntent);
                }


            });

        }
    }
}
