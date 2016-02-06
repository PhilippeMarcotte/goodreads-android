package com.example.philippe.goodreadsapi;

import android.content.Context;
import android.widget.RelativeLayout;

import com.example.philippe.goodreadsapi.Book;
import com.example.philippe.goodreadsapi.Review;
import com.example.philippe.goodreadsapi.Reviews;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Philippe on 2016-02-04.
 */
public class ShelfLoader {
    private String shelf;
    private Context context = null;
    private File fl;
    public ShelfLoader(String shelf, Context context){
        this.shelf = shelf;
        this.context = context;
        this.fl = new File(context.getFilesDir(),shelf);
    }

    public ShelfLoader(File file){
        this.fl = file;
        this.shelf = file.getName().substring(0,file.getName().length() - "Shelf".length());
    }

    public ArrayList<Review> loadShelf(){
        String strJson;
        JSONArray jsonArray;
        ArrayList<Review> bookList = null;
        try {
            strJson = getStringFromFile();
            jsonArray = new JSONArray(strJson);
            bookList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                Review review = new Review(jsonArray.getJSONObject(i));
                bookList.add(review);
            }
        }catch(JSONException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }

        return bookList;

    }

    public String convertStreamToString(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    public String getStringFromFile () throws IOException {

        FileInputStream fin = new FileInputStream(fl);
        String ret = convertStreamToString(fin);
        //Make sure you close all streams.
        fin.close();
        return ret;
    }


}
