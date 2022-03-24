package com.example.worldcinemabrevnov;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.worldcinemabrevnov.adapters.MoviesListAdapter;
import com.example.worldcinemabrevnov.network.ApiHandler;
import com.example.worldcinemabrevnov.network.ErrorUtils;
import com.example.worldcinemabrevnov.network.models.MovieResponse;
import com.example.worldcinemabrevnov.network.services.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private MoviesListAdapter moviesListAdapter;
    private List<MovieResponse> mMovies;
    private RecyclerView mMoviesContainer;

    private ApiService service = ApiHandler.getInstance().getService();

    public HomeFragment() {
    }

    public static HomeFragment newInstance(String param1, String param2) {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        fetchMovies();
        InitUI(view);
        return view;
    }

    private void InitUI(View view) {

        mMoviesContainer = view.findViewById(R.id.mainMoviesContainer);
    }

    private void fetchMovies() {
        AsyncTask.execute(() -> {
            service.fetchMovies("new").enqueue(new Callback<List<MovieResponse>>() {
                @Override
                public void onResponse(Call<List<MovieResponse>> call, Response<List<MovieResponse>> response) {
                    if (response.isSuccessful()) {
                        mMovies = response.body();
                        moviesListAdapter = new MoviesListAdapter(getContext(), mMovies);

                        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                        mMoviesContainer.setLayoutManager(manager);
                        mMoviesContainer.setAdapter(moviesListAdapter);
                    } else if (response.code() == 400) {
                        String serverErrorMessage = ErrorUtils.parseError(response).message();
                        Toast.makeText(getContext(), serverErrorMessage, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Не удалось получить информацию о фильме 1", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<MovieResponse>> call, Throwable t) {
                    Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}