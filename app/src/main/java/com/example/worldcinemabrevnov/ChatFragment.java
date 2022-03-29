package com.example.worldcinemabrevnov;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.worldcinemabrevnov.adapters.ChatListAdapter;
import com.example.worldcinemabrevnov.data.DataManager;
import com.example.worldcinemabrevnov.network.ApiHandler;
import com.example.worldcinemabrevnov.network.models.ChatResponse;
import com.example.worldcinemabrevnov.network.models.SendMessageBody;
import com.example.worldcinemabrevnov.network.services.ApiService;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatFragment extends Fragment {

    private String chatId;

    private ChatListAdapter adapter;
    private List<ChatResponse> messages;
    private ListView messagesListView;

    private EditText messageInput;
    private ImageButton sendButton;

    private ApiService service = ApiHandler.getInstance().getService();

    public ChatFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        Bundle args = getArguments();
        chatId = args.getString("chatId");

        InitUI(view);
        fetchMessages(chatId);

        return view;
    }

    private void InitUI(View view) {
        messagesListView = view.findViewById(R.id.chat_list_view);
        messages = new ArrayList<>();
        adapter = new ChatListAdapter(getContext(), messages, DataManager.getUserName());
        messagesListView.setAdapter(adapter);

        ImageButton backButton = view.findViewById(R.id.button_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout, new DiscussionsFragment()).commit();
            }
        });

        messageInput = view.findViewById(R.id.message_edit_text);
        sendButton = view.findViewById(R.id.send_message_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
    }

    private void fetchMessages(String charId) {
        List<ChatResponse> tempList = new ArrayList<>();
        AsyncTask.execute(() -> {
            service.fetchChat(charId).enqueue(new Callback<List<ChatResponse>>() {
                @Override
                public void onResponse(Call<List<ChatResponse>> call, Response<List<ChatResponse>> response) {
                    if (response.isSuccessful()) {
                        for (int i = 0; i < response.body().size(); i++) {
                            messages.add(response.body().get(i));
                        }
                        adapter.notifyDataSetChanged();
                    } else if (response.code() == 400) {
                        Toast.makeText(getContext(), "Не удалось получить список сообщений", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<ChatResponse>> call, Throwable t) {

                }
            });
        });
    }

    private void sendMessage() {
        String text = messageInput.getText().toString();
        SendMessageBody body = new SendMessageBody();
        body.setText(text);
        AsyncTask.execute(() -> {
            service.sendMessage(chatId, body).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    messages.clear();
                    fetchMessages(chatId);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        });

        messageInput.setText("");
        messagesListView.post(new Runnable() {
            @Override
            public void run() {
                messagesListView.setSelection(adapter.getCount() - 1);
            }
        });
    }
}
