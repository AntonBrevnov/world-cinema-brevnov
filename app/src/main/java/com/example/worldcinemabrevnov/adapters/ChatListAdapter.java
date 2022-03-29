package com.example.worldcinemabrevnov.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.worldcinemabrevnov.R;
import com.example.worldcinemabrevnov.network.models.ChatResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ChatListAdapter extends BaseAdapter {

    private Context mContext;
    private List<ChatResponse> mMessages;
    private String mCurrentUserName;

    public ChatListAdapter(Context context, List<ChatResponse> data, String currentUserName) {
        mContext = context;
        mMessages = data;
        mCurrentUserName = currentUserName;
    }

    @Override
    public int getCount() {
        return mMessages.size();
    }

    @Override
    public Object getItem(int i) {
        return mMessages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ChatResponse message = mMessages.get(i);
        String userName = message.getFirstName().concat(" ").concat(message.getLastName());

        if (userName.equals(mCurrentUserName)) {
            view = LayoutInflater.from(mContext).inflate(R.layout.current_user_message, viewGroup, false);
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.other_user_message, viewGroup, false);
        }

        TextView messageText = view.findViewById(R.id.message_text);
        TextView messageUserName = view.findViewById(R.id.message_user_name);
        TextView messageTime = view.findViewById(R.id.message_time);
        ImageView messageUserAvatar = view.findViewById(R.id.message_user_avatar);

        messageText.setText(message.getText());
        messageUserName.setText(userName);
        messageTime.setText(message.getCreationDateTime());

        Picasso.with(mContext).load("http://cinema.areas.su/up/images/" + message.getAvatar()).into(messageUserAvatar);

        return view;
    }
}
