package ru.venusgames.deadmicrophone;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class DBCursorAdapterListViewTest extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int LOADER_ID = 0;
    private CursorAdapter cursorAdapter;
    private DB database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbcursor_adapter_list_view_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                database.clearMessages();
                                getSupportLoaderManager().getLoader(LOADER_ID).forceLoad();
                            }
                        }).show();
            }
        });

        Button addRecord = (Button) findViewById(R.id.add_record_button);
        addRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name = (EditText) findViewById(R.id.name_edit_text);
                EditText message = (EditText) findViewById(R.id.message_edit_text);
                database.addContact(new Contact(name.getText().toString(), message.getText().toString()));
                name.setText("");
                message.setText("");
                getSupportLoaderManager().getLoader(LOADER_ID).forceLoad();
            }
        });

        database = new DB(this, 2);

        ListView recordsListView = (ListView) findViewById(R.id.records_list_view);
        cursorAdapter = new CursorAdapter(this, null, false) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View view = layoutInflater.inflate(R.layout.contact_item, parent, false);
                return view;
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                ((TextView) view.findViewById(R.id.contact_name)).setText(cursor.getString(cursor.getColumnIndex(DB.COLUMN_NAME)));
                ((TextView) view.findViewById(R.id.message)).setText(cursor.getString(cursor.getColumnIndex(DB.COLUMN_MESSAGE)));
                ((ImageView) view.findViewById(R.id.contact_avatar)).setImageResource(R.drawable.contact);
                ((ImageView)view.findViewById(R.id.contact_status)).setImageResource(R.drawable.online_status);
            }
        };
        recordsListView.setAdapter(cursorAdapter);

        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == LOADER_ID) {
            return new MyDbCursorLoader(this, database);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        cursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    //
    static class MyDbCursorLoader extends CursorLoader {

        private DB db;

        public MyDbCursorLoader(Context context, DB db) {
            super(context);
            this.db = db;
        }

        @Override
        public Cursor loadInBackground() {
            return db.getAllData();
        }
    }
}
