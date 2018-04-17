package com.charanajay.sqlitetester;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    DBHelper databaseHelper;
    Button showWords;
    TextView wordDisplay;
    EditText searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DBHelper(this);
        try {
            databaseHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        showWords = findViewById(R.id.showWords);
        wordDisplay = findViewById(R.id.wordDisplay);
        searchText = findViewById(R.id.searchBox);
        showWords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchableText = searchText.getText().toString();
                Log.d("searchText : ",searchableText);
                Cursor cursor = databaseHelper.getWordsStartingWith(searchableText);
                if(cursor.getCount()==0){
                    wordDisplay.setText("Empty");
                    return;
                }

                StringBuffer stringBuffer = new StringBuffer();
                while ( cursor.moveToNext()){
                    stringBuffer.append(cursor.getString(0)+"\n");
                }

                wordDisplay.setText(stringBuffer.toString());
            }
        });
    }
}
