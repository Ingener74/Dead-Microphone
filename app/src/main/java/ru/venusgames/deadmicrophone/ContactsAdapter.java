package ru.venusgames.deadmicrophone;

import android.content.Context;
import android.widget.ArrayAdapter;

public class ContactsAdapter extends ArrayAdapter<Contact> {
    public ContactsAdapter(Context context, Contact[] objects) {
        super(context, R.layout.contact_item, objects);
    }
}
