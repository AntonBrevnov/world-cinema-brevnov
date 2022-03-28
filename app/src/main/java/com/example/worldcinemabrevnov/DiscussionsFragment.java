package com.example.worldcinemabrevnov;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.example.worldcinemabrevnov.adapters.DiscussionsListAdapter;
import com.example.worldcinemabrevnov.adapters.DiscussionsListItem;
import com.example.worldcinemabrevnov.network.ApiHandler;
import com.example.worldcinemabrevnov.network.ErrorUtils;
import com.example.worldcinemabrevnov.network.models.ChatResponse;
import com.example.worldcinemabrevnov.network.models.ChatsResponse;
import com.example.worldcinemabrevnov.network.services.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscussionsFragment extends Fragment {

    DiscussionsListItem discussion;
    private DiscussionsListAdapter adapter;
    private ListView mDiscussionsListView;

    private ImageButton mBackButton;

    private ApiService service = ApiHandler.getInstance().getService();

    public DiscussionsFragment() {

    }

    public static DiscussionsFragment newInstance(String param1, String param2) {
        return new DiscussionsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discussions, container, false);
        fetchDiscussionsList();
        InitUI(view);
        return view;
    }

    private void InitUI(View view) {
        mDiscussionsListView = view.findViewById(R.id.chats_container);
        mDiscussionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        mBackButton = view.findViewById(R.id.button_back);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout, new ProfileFragment()).commit();
            }
        });
    }

    private void fetchDiscussionsList() {
        AsyncTask.execute(() -> {
            service.fetchChats().enqueue(new Callback<List<ChatsResponse>>() {
                @Override
                public void onResponse(Call<List<ChatsResponse>> call, Response<List<ChatsResponse>> response) {
                    if (response.isSuccessful()) {
                        List<ChatsResponse> chats = response.body();
                        List<DiscussionsListItem> data = fetchChatInfoToPreview(chats);

                        adapter = new DiscussionsListAdapter(getContext(), data);
                        mDiscussionsListView.setAdapter(adapter);
                    } else if (response.code() == 400) {
                        String serverErrorMessage = ErrorUtils.parseError(response).message();
                        Toast.makeText(getContext(), serverErrorMessage, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Не удалось получить информацию об обсуждениях", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<ChatsResponse>> call, Throwable t) {

                }
            });
        });
    }

    private List<DiscussionsListItem> fetchChatInfoToPreview(List<ChatsResponse> chats) {
        List<DiscussionsListItem> discussions = new ArrayList<>();

        for (int i = 0; i < chats.size(); i++) {
            discussion = new DiscussionsListItem();
            discussion.setChatId(chats.get(i).getCharId());
            discussion.setDiscussionTitle(chats.get(i).getName());
            service.fetchChat(chats.get(i).getCharId()).enqueue(new Callback<List<ChatResponse>>() {
                @Override
                public void onResponse(Call<List<ChatResponse>> call, Response<List<ChatResponse>> response) {
                    if (response.isSuccessful()) {
                        ChatResponse last = response.body().get(response.body().size() - 1);
                        String userName = last.getFirstName().concat(" ").concat(last.getLastName());
                        discussion.setLastMessageUser(userName);
                        discussion.setLastMessage(last.getText());
                    } else if (response.code() == 400) {
                        String serverErrorMessage = ErrorUtils.parseError(response).message();
                        Toast.makeText(getContext(), serverErrorMessage, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Не удалось получить обсуждение", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<ChatResponse>> call, Throwable t) {

                }
            });
            discussions.add(discussion);
        }

        return discussions;
    }
}
