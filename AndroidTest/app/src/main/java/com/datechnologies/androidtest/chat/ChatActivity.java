package com.datechnologies.androidtest.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.datechnologies.androidtest.MainActivity;
import com.datechnologies.androidtest.R;
import com.datechnologies.androidtest.api.ChatLogMessageModel;
import com.datechnologies.androidtest.utils.API;
import com.datechnologies.androidtest.utils.APIClient;
import com.datechnologies.androidtest.utils.DataWrapper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Screen that displays a list of chats from a chat log.
 *
 * @see ChatAdapter
 */
public class ChatActivity extends AppCompatActivity {

    //==============================================================================================
    // Class Properties
    //==============================================================================================

    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    List<ChatLogMessageModel> tempList = new ArrayList<>();
    API api;
    //==============================================================================================
    // Static Class Methods
    //==============================================================================================

    public static void start(Context context)
    {
        Intent starter = new Intent(context, ChatActivity.class);
        context.startActivity(starter);
    }

    //==============================================================================================
    // Lifecycle Methods
    //==============================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        chatAdapter = new ChatAdapter();

        recyclerView.setAdapter(chatAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL,
                false));
        // TODO: Make the UI look like it does in the mock-up. Allow for horizontal screen rotation.
        /* DONE:
         * Made required UI changes in item_chat.xml and to handle horizontal screen rotation
         * added android:configChanges="orientation|screenSize" in AndroidManifest file under this
         * activity tag.
         */
        // TODO: Retrieve the chat data from http://dev.rapptrlabs.com/Tests/scripts/chat_log.php
        // TODO: Parse this chat data from JSON into ChatLogMessageModel and display it.
        /*
            Below we are creating an instance of API using APIClient and using its reference
            calling loadChat() method to request the data from server.
         */
        api = APIClient.getClient().create(API.class);
        Call<DataWrapper> dataCall = api.loadChat();
        dataCall.enqueue(new Callback<DataWrapper>() {
            @Override
            public void onResponse(Call<DataWrapper> call, Response<DataWrapper> response) {
                //Once the response is received we will add the data into list and set it to adapter.
                List<ChatLogMessageModel> chatList = response.body().getChats();
                tempList.addAll(chatList);
                chatAdapter.setChatLogMessageModelList(tempList);
            }
            @Override
            public void onFailure(Call<DataWrapper> call, Throwable t) {
                //If there is any error while fetching data, display a toast message
                Toast.makeText(ChatActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
