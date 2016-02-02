package ru.venusgames.deadmicrophone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.zip.Inflater;

public class ContactsAdapter extends ArrayAdapter<Contact> {
    private final LayoutInflater inflater;

    public ContactsAdapter(Context context, Contact[] objects) {
        super(context, R.layout.contact_item, objects);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if(view == null){
            view = inflater.inflate(R.layout.contact_item, parent);
        }

        Contact contact = getItem(position);

        ((TextView)view.findViewById(R.id.contact_name)).setText(contact.name);
        ((TextView)view.findViewById(R.id.message)).setText(contact.name);

        return view;
    }
}
