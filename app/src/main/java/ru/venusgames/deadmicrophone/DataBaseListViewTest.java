package ru.venusgames.deadmicrophone;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
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

public class DataBaseListViewTest extends AppCompatActivity implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor> {

    public static final int LOADER_ID = 0;
    private DB database;
    private SimpleCursorAdapter simpleCursorAdapter;

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
                                getSupportLoaderManager().getLoader(LOADER_ID).forceLoad();
                            }
                        }).show();
            }
        });

        database = new DB(this, 2);

        String[] fromColumns = new String[]{DB.COLUMN_NAME, DB.COLUMN_MESSAGE};
        int[] toIds = new int[]{R.id.contact_name, R.id.message};

        simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.contact_item, null, fromColumns, toIds);

        ListView listView = (ListView) findViewById(R.id.dataBaseListView);
        listView.setAdapter(simpleCursorAdapter);

        Button button = (Button) findViewById(R.id.addRec);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name = (EditText) findViewById(R.id.editTextName);
                EditText message = (EditText) findViewById(R.id.editTextMessage);
                database.addContact(new Contact(name.getText().toString(), message.getText().toString()));
                getSupportLoaderManager().getLoader(LOADER_ID).forceLoad();
            }
        });

        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }

    // LocaderCallbacks<Cursor>
    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == LOADER_ID) {
            return new DbCursorLoader(this, database);
        }
        return null;
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        simpleCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {

    }

    //
    static class DbCursorLoader extends android.support.v4.content.CursorLoader {

        private DB db;

        public DbCursorLoader(Context context, DB database) {
            super(context);
            this.db = database;
        }

        @Override
        public Cursor loadInBackground() {
            return db.getAllData();
        }
    }
}
