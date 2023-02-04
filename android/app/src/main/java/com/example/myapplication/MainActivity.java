package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import tech.gusavila92.websocketclient.WebSocketClient;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private Socket socketio;
    private Config config = Config.getIntence();
    private WebSocketClient webSocketClient;
    private Emitter.Listener dataListener;
    private  ListAdapter nodeListAdapter;
    private ListAdapter djangoListAdapter;
    private final ArrayList<CryptoData>  forNodeList = new ArrayList<>(Arrays.asList(
            new CryptoData("BTC : ", "0"),
            new CryptoData("LIT : ", "0"),
            new CryptoData("ETH : ", "0")
    ));
    private final ArrayList<CryptoData> forDjangoList = new ArrayList<>(Arrays.asList(
            new CryptoData("DOT : ", "0"),
            new CryptoData("BNB : ", "0"),
            new CryptoData("XRP : ", "0")
    ));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupLayout();
        createSocketIO();
        createWebSocketClient();
    }
    private void setupLayout(){
        RecyclerView forNode = findViewById(R.id.for_node);
        forNode.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        RecyclerView froDjango = findViewById(R.id.for_django);
        froDjango.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        nodeListAdapter = new ListAdapter(forNodeList,getApplicationContext());
        forNode.setAdapter(nodeListAdapter);
         djangoListAdapter = new ListAdapter(forDjangoList,getApplicationContext());
        froDjango.setAdapter(djangoListAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socketio.disconnect();
        socketio.off("crypto_rate", dataListener);
        webSocketClient.close();
    }

    private void createSocketIO(){
        try {
            socketio = IO.socket(config.getNodeSocketIO());

        } catch (URISyntaxException e) {
            Log.d("asdjhsafgdjhgsafd","asdjnhbsafdhg");
        }
         dataListener =  new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Gson gson = new Gson();
                NodeCryptoPattern getData =gson.fromJson((String) args[0],NodeCryptoPattern.class);
                changeUi(new ArrayList<>(Arrays.asList(
                                new CryptoData("BTC : ", getData.BTC),
                                new CryptoData("LIT : ", getData.LIT),
                                new CryptoData("ETH : ", getData.ETH))),
                        true);
            }
        };
        socketio.on("crypto_rate", dataListener);

       socketio.connect();
    }
    private void changeUi(ArrayList<CryptoData>  list,Boolean node){
        ArrayList<CryptoData> array = list;
        if (node) {
            runOnUiThread(() -> {
                nodeListAdapter.dataInject(list);
            });
        }else{
            runOnUiThread(() -> {
                runOnUiThread(() -> {
                    djangoListAdapter.dataInject(list);
                });
            });
        }

    }
    private void createWebSocketClient() {

        URI uri;
        try {
            // Connect to local host
            uri = new URI(config.getDjangoSocket());
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        webSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen() {
                Log.i("WebSocket", "Session is starting");
                webSocketClient.send("Hello World!");
            }

            @Override
            public void onTextReceived(String s) {
                Gson gson = new Gson();
                DjangoCryptoPattern getData =gson.fromJson(s,DjangoCryptoPattern.class);
                changeUi(new ArrayList<>(Arrays.asList(
                                new CryptoData("DOT : ", getData.DOT),
                                new CryptoData("BNB : ", getData.BNB),
                                new CryptoData("XRP : ", getData.XRP))),
                        false);
            }

            @Override
            public void onBinaryReceived(byte[] data) {

            }

            @Override
            public void onPingReceived(byte[] data) {

            }

            @Override
            public void onPongReceived(byte[] data) {

            }

            @Override
            public void onException(Exception e) {
                System.out.println(e.getMessage());
            }

            @Override
            public void onCloseReceived() {
                Log.i("WebSocket", "Closed ");
            }
        };
        webSocketClient.connect();
    }
}