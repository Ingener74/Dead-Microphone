package ru.venusgames.deadmicrophone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketConnectionHandler;
import de.tavendo.autobahn.WebSocketException;

public class MainActivity extends AppCompatActivity {

    private final WebSocketConnection webSocketConnection = new WebSocketConnection();
    private ArrayList<HashMap<String, String>> reqList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Context context = this;

        ((Button) findViewById(R.id.startLIstViewTest)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, ListViewTestActivity.class));
            }
        });

        ((Button) findViewById(R.id.startDataBaseListViewTest)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, DataBaseListViewTest.class));
            }
        });

        ((Button) findViewById(R.id.startTest3)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, DBCursorAdapterListViewTest.class));
            }
        });

        Button button = (Button) findViewById(R.id.webSocketButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(webSocketConnection.isConnected()){
                    JSONObject payload = new JSONObject();
                    try {
                        payload.put("jsonrpc", "2.0")
                                .put("method", "enter")
                                .put("id", 1)
                                .put("params", new JSONObject().
                                        put("phone", "+79090").
                                        put("device_id", "test-phone"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String s = payload.toString();

                    Log.d(getClass().getName(), "payload " + s);

                    webSocketConnection.sendTextMessage(s);
                }
            }
        });

        reqList = new ArrayList<>(1000);

        try {
//            String wsUri = "ws://invizer1.cloudapp.net:8888/ws";
            String wsUri = "ws://136.243.156.202:8888/ws";

            webSocketConnection.connect(wsUri, new WebSocketConnectionHandler(){
                @Override
                public void onOpen() {
                    Log.d(getClass().getName(), "onOpen");
                }

                @Override
                public void onTextMessage(String payload) {
                    Log.d(getClass().getName(), "onTextMessage " + payload);

//                    TextView text = (TextView)findViewById(R.id.responseTextView);
                    ListView listView = (ListView) findViewById(R.id.reqListView);

                    HashMap<String, String> stringStringHashMap = new HashMap<>();
                    stringStringHashMap.put("text", payload);
                    reqList.add(stringStringHashMap);

                    listView.setAdapter(new SimpleAdapter(context, reqList, android.R.layout.simple_list_item_1, new String []{"text"}, new int[]{android.R.id.text1}));

//                    try {
//                        JSONObject jsonObject = new JSONObject(payload);
//                        String jsonrpc = jsonObject.getString("jsonrpc");
//                        if(jsonObject.has("method")){
//                            String method = jsonObject.getString("method");
//
//                            listView.setAdapter(new SimpleAdapter(context, reqList));
//                        }
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
                }

                @Override
                public void onClose(int code, String reason) {
                    Log.d(getClass().getName(), "onClose " + code + ", " + reason);
                }
            });
        } catch (WebSocketException e) {
            e.printStackTrace();
        }
    }
}
