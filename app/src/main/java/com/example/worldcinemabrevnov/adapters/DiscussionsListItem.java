package com.example.worldcinemabrevnov.adapters;

public class DiscussionsListItem {
    private String mChatId;
    private String mDiscussionTitle;
    private String mLastMessageUser;
    private String mLastMessage;

    public DiscussionsListItem() {
        mChatId = "";
        mDiscussionTitle = "";
        mLastMessageUser = "";
        mLastMessage = "";
    }

    public DiscussionsListItem(String chatId, String discussionTitle, String lastMessageUser, String lastMessage) {
        mChatId = chatId;
        mDiscussionTitle = discussionTitle;
        mLastMessageUser = lastMessageUser;
        mLastMessage = lastMessage;
    }

    public String getChatId() {
        return mChatId;
    }

    public void setChatId(String mChatId) {
        this.mChatId = mChatId;
    }
    
    public String getDiscussionTitle() {
        return mDiscussionTitle;
    }

    public void setDiscussionTitle(String mDiscussionTitle) {
        this.mDiscussionTitle = mDiscussionTitle;
    }

    public String getLastMessageUser() {
        return mLastMessageUser;
    }

    public void setLastMessageUser(String mLastMessageUser) {
        this.mLastMessageUser = mLastMessageUser;
    }

    public String getLastMessage() {
        return mLastMessage;
    }

    public void setLastMessage(String mLastMessage) {
        this.mLastMessage = mLastMessage;
    }
}
