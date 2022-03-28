package com.example.worldcinemabrevnov;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class DiscussionsFragment extends Fragment {
    public DiscussionsFragment() {

    }

    public static DiscussionsFragment newInstance(String param1, String param2) {
        return new DiscussionsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discussions, container, false);
        return view;
    }
}
