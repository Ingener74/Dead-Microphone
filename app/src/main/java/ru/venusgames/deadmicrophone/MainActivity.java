package ru.venusgames.deadmicrophone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringReader;

import de.tavendo.autobahn.WebSocket;
import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;

public class MainActivity extends AppCompatActivity {

    private final WebSocketConnection webSocketConnection = new WebSocketConnection();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(getClass().getName(), "onCreate");

        try {
            webSocketConnection.connect("ws://invizer1.cloudapp.net:8888/ws", new WebSocket.ConnectionHandler() {
                @Override
                public void onOpen() {
                    Log.d(getClass().getName(), "onOpen");

                    JSONObject payload = new JSONObject();
                    try {
                        payload.put("jsonrpc", "2.0")
                                .put("method", "enter")
                                .put("id", 1)
                                .put("params", new JSONObject().
                                        put("phone", "").
                                        put("device_id", "test-phone"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String s = payload.toString();

                    Log.d(getClass().getName(), "payload " + s);

                    webSocketConnection.sendTextMessage(s);
                }

                @Override
                public void onClose(int i, String s) {
                    Log.d(getClass().getName(), "onClose " + i + ", " + s);
                }

                @Override
                public void onTextMessage(String s) {
                    Log.d(getClass().getName(), "onTextMessage " + s);

                    TextView text = (TextView)findViewById(R.id.text);
//                    text.setText(s);

                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        String jsonrpc = jsonObject.getString("jsonrpc");
                        if(jsonObject.has("method")){
                            String method = jsonObject.getString("method");
                            text.setText(String.format("jsonrpc %s, method %s", jsonrpc, method));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

//                    JsonReader jsonReader = new JsonReader(new StringReader(s));
//
//                    try {
//
//                        String jsonrpc = null;
//                        String method = null;
//                        int id = 0;
//
//                        jsonReader.beginObject();
//                        while (jsonReader.hasNext()) {
//                            String name = jsonReader.nextName();
//                            if ("jsonrpc".equals(name)) {
//                                jsonrpc = jsonReader.nextString();
//                            } else if ("method".equals(name)) {
//                                method = jsonReader.nextString();
//                            } else if ("id".equals(name)) {
//                                id = jsonReader.nextInt();
//                            }
//                        }
//
//                        Log.d(getClass().getName(), jsonrpc);
//                        Log.d(getClass().getName(), method);
//                        Log.d(getClass().getName(), String.valueOf(id));
//
//                        TextView text = (TextView)findViewById(R.id.text);
//                        text.setText(method);
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                }

                @Override
                public void onRawTextMessage(byte[] bytes) {
                    Log.d(getClass().getName(), "onRawTextMessage " + bytes.length);
                }

                @Override
                public void onBinaryMessage(byte[] bytes) {
                    Log.d(getClass().getName(), "onBinaryMessage " + bytes.length);
                }
            });
        } catch (WebSocketException e) {
            e.printStackTrace();
        }
    }
}
