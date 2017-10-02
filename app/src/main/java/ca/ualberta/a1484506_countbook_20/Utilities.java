package ca.ualberta.a1484506_countbook_20;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by Stanislav Lebedev on 2017-09-30.
 */

public class Utilities {

    public static final String FILE_EXTENSION = ".bin";
    public static final String FILE_EXTENSION2 = "FILE_EXTENSION";

    public static boolean saveRecord(Context context, Counter counter){
        String fileName = String.valueOf(counter.getDateTime()) + FILE_EXTENSION;

        FileOutputStream aFile;
        ObjectOutputStream anObject;

        try {
            aFile = context.openFileOutput(fileName, context.MODE_PRIVATE);
            anObject = new ObjectOutputStream(aFile);
            anObject.writeObject(counter);
            anObject.close();
            aFile.close();

        } catch (IOException e) {
            e.printStackTrace();
            return false;

        }
        return true;

    }

    public static ArrayList<Counter> getAllSavedRecords(Context context){
        ArrayList<Counter> counters = new ArrayList<>();
        File filesDir = context.getFilesDir();
        ArrayList<String> recordFiles = new ArrayList<>();

        for (String file : filesDir.list()) {
            if (file.endsWith(FILE_EXTENSION)){
                 recordFiles.add(file);
            }
        }

        FileInputStream fis;
        ObjectInputStream ois;

        for (int i = 0; i < recordFiles.size(); i++) {
            try{
                fis = context.openFileInput(recordFiles.get(i));
                ois = new ObjectInputStream(fis);

                counters.add((Counter)ois.readObject());

                fis.close();
                ois.close();
            } catch(IOException | ClassNotFoundException e){
                e.printStackTrace();
                return null;
            }
        }
        return counters;
    }

    public static Counter getRecordByName(Context context, String fileName){
        File file = new File(context.getFilesDir(), fileName);
        Counter counter;

        if (file.exists()){
            FileInputStream fis;
            ObjectInputStream ois;

            try{
                fis = context.openFileInput(fileName);
                ois = new ObjectInputStream(fis);

                counter = (Counter) ois.readObject();
                fis.close();
                ois.close();

            } catch (IOException | ClassNotFoundException e){
                e.printStackTrace();
                return null;
            }
            return counter;
        }
        return null;
    }

    public static boolean delFile(Context context, String fileName) {
        File dir = context.getFilesDir();
        File file = new File(dir, fileName);

        if(file.exists() && !file.isDirectory()){
            return file.delete();
        }
        return false;
    }
}
