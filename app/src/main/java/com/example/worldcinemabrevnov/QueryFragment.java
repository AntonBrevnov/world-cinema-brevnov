package com.example.worldcinemabrevnov;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class QueryFragment extends Fragment {
    public QueryFragment() {

    }

    public static QueryFragment newInstance(String param1, String param2) {
        return new QueryFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_query, container, false);
    }
}
