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
    private TextView dpIniValue;

    private EditText recordName;
    private EditText recordComments;
    private String recordFileName;
    private Counter loadedCounter = null;
    private boolean ViewOrUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_counter);

        recordName = (EditText) findViewById(R.id.recordName);
        recordComments = (EditText) findViewById(R.id.recordComments);


        dpCurrentVal = (TextView)findViewById(R.id.dpCurrentVal);
        dpIniValue = (TextView)findViewById(R.id.initialValue);
        dpDate = (TextView)findViewById(R.id.recordDate);

        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String dateText = df.format(System.currentTimeMillis());

        dpDate.setText(dateText);

        recordFileName = getIntent().getStringExtra(Utilities.FILE_EXTENSION2);
        if (recordFileName != null && !recordFileName.isEmpty() && recordFileName.endsWith(Utilities.FILE_EXTENSION)){
            loadedCounter = Utilities.getRecordByName(getApplicationContext(), recordFileName);

            if (loadedCounter != null){
                recordName.setText(loadedCounter.getName());
                recordComments.setText((loadedCounter.getComments()));
                dpIniValue.setText(loadedCounter.getValue());
                ViewOrUpdate = true;

            }

        } else {
            ViewOrUpdate = false;
        }

    }


    public void plusButtonClicked(View view) {
        counterValue++;
        dpCurrentVal.setText(String.valueOf(counterValue));

    }

    public void minusButtonClicked(View view) {
        if (counterValue == 0){
            Toast.makeText(CounterActivity.this,"Value can't be negative.",Toast.LENGTH_SHORT).show();
        }
            else{
            counterValue--;
            dpCurrentVal.setText(String.valueOf(counterValue));
        }


    }


    public void saveButtonClicked(View view) {
        saveRecord();
    }

    public void delButtonClicked(View view) {
        delRecord();
    }

    private void saveRecord() {
        Counter counter;

        if (recordName.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Please enter a name.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(loadedCounter == null) {
            counter = new Counter(System.currentTimeMillis(), recordName.getText().toString()
                    , recordComments.getText().toString(), String.valueOf(counterValue));
        } else {
            counter = new Counter(System.currentTimeMillis(), recordName.getText().toString()
                    , recordComments.getText().toString(), String.valueOf(counterValue));
        }

        if (Utilities.saveRecord(this, counter)) {
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Please make sure you have enough space.", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    private void delRecord(){

        AlertDialog.Builder warning = new AlertDialog.Builder(this)
                .setTitle("Delete record")
                .setMessage("Are you sure you want to delete" + recordName.getText().toString() + "?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        if (loadedCounter != null && Utilities.delFile(getApplicationContext(), recordFileName)) {
                            Toast.makeText(getApplicationContext()
                                    , loadedCounter.getName() + "was deleted", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CounterActivity.this, "Can't delete the record " + loadedCounter.getName() + " "
                                    , Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .setCancelable(false);

        warning.show();

        }
    }





