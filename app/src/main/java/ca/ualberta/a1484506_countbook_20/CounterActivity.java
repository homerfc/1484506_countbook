package ca.ualberta.a1484506_countbook_20;


import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
import android.icu.util.TimeZone;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Date;

public class CounterActivity extends AppCompatActivity{

    private int counterValue = 0;
    private TextView dpCurrentVal;
    private TextView dpDate;

    private EditText recordName;
    private EditText recordComments;
    private String recordFileName;
    private Counter loadedCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_counter);

        recordName = (EditText) findViewById(R.id.recordName);
        recordComments = (EditText) findViewById(R.id.recordComments);

        dpCurrentVal = (TextView)findViewById(R.id.dpCurrentVal);
        dpDate = (TextView)findViewById(R.id.recordDate);

        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String dateText = df.format(System.currentTimeMillis());

        dpDate.setText(dateText);

        recordFileName = getIntent().getStringExtra("Record_File");
        if (recordFileName != null && !recordFileName.isEmpty()){
            loadedCounter = Utilities.getRecordByName(this, recordFileName);

            if (loadedCounter != null){
                recordName.setText(loadedCounter.getName());
                recordComments.setText((loadedCounter.getComments()));

            }

        }

    }

    // increase current value by one
    public void plusButtonClicked(View view) {
        counterValue++;
        dpCurrentVal.setText(String.valueOf(counterValue));

    }
    //decrease current value by one
    public void minusButtonClicked(View view) {
        if (counterValue == 0){
            Toast.makeText(CounterActivity.this,"Value can't be negative.",Toast.LENGTH_SHORT).show();
        }
            else{
            counterValue--;
            dpCurrentVal.setText(String.valueOf(counterValue));
        }


    }
    //set up button for saving a record
    public void saveButtonClicked(View view) {
        saveRecord();
    }

    // take data from text fields and send it to Utilities class
    private void saveRecord() {
        Counter counter;

        if (recordName.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Please enter a name.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(loadedCounter == null) {
            counter = new Counter(System.currentTimeMillis(), recordName.getText().toString()
                    , recordComments.getText().toString(), counterValue);
        } else {
            counter = new Counter(System.currentTimeMillis(), recordName.getText().toString()
                    , recordComments.getText().toString(), counterValue);
        }

        if (Utilities.saveRecord(this, counter)) {
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Please make sure you have enough space.", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
    // looking for a record and delete it
    private void delRecord(){
        if (loadedCounter == null){
            finish();
        } else {
            AlertDialog.Builder warning = new AlertDialog.Builder(this)
                    .setTitle("Delete record")
                    .setMessage("Are you sure you want to delete" + recordName.getText().toString() + "?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Utilities.delRecord(getApplicationContext()
                                     , loadedCounter.getDateTime() + Utilities.FILE_EXTENSION);
                            Toast.makeText(getApplicationContext()
                                    , recordName.getText().toString() + "was deleted",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .setCancelable(false);

            warning.show();

        }
    }

}





