package com.example.worldcinemabrevnov.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.worldcinemabrevnov.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DiscussionsListAdapter extends BaseAdapter {

    private Context mContext;
    private List<DiscussionsListItem> mDiscussionsList;

    public DiscussionsListAdapter(Context context, List<DiscussionsListItem> data) {
        mContext = context;
        mDiscussionsList = data;
    }

    @Override
    public int getCount() {
        return mDiscussionsList.size();
    }

    @Override
    public Object getItem(int i) {
        return mDiscussionsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(mContext).inflate(R.layout.discussion_list_item, viewGroup, false);
        DiscussionsListItem discussion = mDiscussionsList.get(i);

        String[] titleWords = discussion.getDiscussionTitle().split(" ");
        TextView discussionCutTitle = view.findViewById(R.id.discussion_cut_title);
        if (titleWords.length > 1) {
            discussionCutTitle.setText(String.valueOf(titleWords[0].charAt(0)).concat(String.valueOf(titleWords[1].charAt(0))));
        } else if (titleWords.length == 1) {
            discussionCutTitle.setText(titleWords[0].toUpperCase());
        } else
            discussionCutTitle.setText("-");

        TextView discussionTitle = view.findViewById(R.id.discussion_title);
        discussionTitle.setText(discussion.getDiscussionTitle());

        TextView lastMessageText = view.findViewById(R.id.last_message);
        lastMessageText.setText(discussion.getLastMessageUser().concat(": ").concat(discussion.getLastMessage()));

        return view;
    }
}
