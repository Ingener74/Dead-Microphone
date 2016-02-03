package ru.venusgames.deadmicrophone;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class DataBaseListViewTest extends AppCompatActivity {

    private DB database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base_list_view_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Clear DataBase", Snackbar.LENGTH_LONG)
                        .setAction("Action", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                database.clearMessages();
                            }
                        }).show();
            }
        });

        database = new DB(this, 2);

        String[] fromColumns = new String[]{DB.COLUMN_NAME, DB.COLUMN_MESSAGE};
        int[] toIds = new int[]{R.id.contact_name, R.id.message};

        ListView listView = (ListView) findViewById(R.id.dataBaseListView);
        listView.setAdapter(new SimpleCursorAdapter(this, R.layout.contact_item, database.getAllData(), fromColumns, toIds));

        Button button = (Button) findViewById(R.id.addRec);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name = (EditText) findViewById(R.id.editTextName);
                EditText message = (EditText) findViewById(R.id.editTextMessage);
                database.addContact(new Contact(name.getText().toString(), message.getText().toString()));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }
}
