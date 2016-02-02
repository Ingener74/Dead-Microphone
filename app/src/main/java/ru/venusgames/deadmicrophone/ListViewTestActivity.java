package ru.venusgames.deadmicrophone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListViewTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_test);
    }


    @Override
    protected void onResume() {
        super.onResume();

        ListView listView = (ListView) findViewById(R.id.testListView);
        listView.setAdapter(new ContactsAdapter(this, new Contact[]{
                new Contact("Pavel", "Y'v got my email"),
                new Contact("Sasha", "My brother is bitch")
        }));
    }

}
