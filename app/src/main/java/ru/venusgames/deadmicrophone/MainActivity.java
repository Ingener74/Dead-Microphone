package ru.venusgames.deadmicrophone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import de.tavendo.autobahn.WebSocketConnection;

public class MainActivity extends AppCompatActivity {

    private final WebSocketConnection webSocketConnection = new WebSocketConnection();

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

//        try {
//            webSocketConnection.connect("ws://invizer1.cloudapp.net:8888/ws", new WebSocket.ConnectionHandler() {
//                @Override
//                public void onOpen() {
//                    Log.d(getClass().getName(), "onOpen");
//
//                    JSONObject payload = new JSONObject();
//                    try {
//                        payload.put("jsonrpc", "2.0")
//                                .put("method", "enter")
//                                .put("id", 1)
//                                .put("params", new JSONObject().
//                                        put("phone", "").
//                                        put("device_id", "test-phone"));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    String s = payload.toString();
//
//                    Log.d(getClass().getName(), "payload " + s);
//
//                    webSocketConnection.sendTextMessage(s);
//                }
//
//                @Override
//                public void onClose(int i, String s) {
//                    Log.d(getClass().getName(), "onClose " + i + ", " + s);
//                }
//
//                @Override
//                public void onTextMessage(String s) {
//                    Log.d(getClass().getName(), "onTextMessage " + s);
//
//                    TextView text = (TextView)findViewById(R.id.text);
//
//                    try {
//                        JSONObject jsonObject = new JSONObject(s);
//                        String jsonrpc = jsonObject.getString("jsonrpc");
//                        if(jsonObject.has("method")){
//                            String method = jsonObject.getString("method");
//                            text.setText(String.format("jsonrpc %s, method %s", jsonrpc, method));
//                        }
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onRawTextMessage(byte[] bytes) {
//                    Log.d(getClass().getName(), "onRawTextMessage " + bytes.length);
//                }
//
//                @Override
//                public void onBinaryMessage(byte[] bytes) {
//                    Log.d(getClass().getName(), "onBinaryMessage " + bytes.length);
//                }
//            });
//        } catch (WebSocketException e) {
//            e.printStackTrace();
//        }
    }
}
