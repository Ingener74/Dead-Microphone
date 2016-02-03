package ru.venusgames.deadmicrophone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ContactsAdapter extends ArrayAdapter<Contact> {

    private final Context context;

    public ContactsAdapter(Context context, Contact[] objects) {
        super(context, R.layout.contact_item, objects);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.contact_item, parent, false);

        Contact contact = getItem(position);

        ((TextView)view.findViewById(R.id.contact_name)).setText(contact.name);
        ((TextView)view.findViewById(R.id.message)).setText(contact.message);

        return view;
    }
}
