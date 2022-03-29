package com.example.worldcinemabrevnov;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.worldcinemabrevnov.data.DataManager;
import com.example.worldcinemabrevnov.network.ApiHandler;
import com.example.worldcinemabrevnov.network.ErrorUtils;
import com.example.worldcinemabrevnov.network.models.UserResponse;
import com.example.worldcinemabrevnov.network.services.ApiService;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private UserResponse mUserData;

    private ImageView mAvatar;
    private TextView mUserName;
    private TextView mEmail;

    private Button mSignOutButton;
    private LinearLayout mDiscussionsLayoutButton;

    ApiService service = ApiHandler.getInstance().getService();

    SharedPreferences localStorage;

    public ProfileFragment() {

    }

    public static ProfileFragment newInstance(String param1, String param2) {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        localStorage = getActivity().getSharedPreferences("WCLS", Context.MODE_PRIVATE);

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        InitUI(view);
        fetchUserData();
        return view;
    }

    private void InitUI(View view) {
        mAvatar = view.findViewById(R.id.image_avatar);
        mUserName = view.findViewById(R.id.user_name);
        mEmail = view.findViewById(R.id.user_email);

        mSignOutButton = view.findViewById(R.id.sign_out_button);
        mSignOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

        mDiscussionsLayoutButton = view.findViewById(R.id.discussions_button);
        mDiscussionsLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout, new DiscussionsFragment()).commit();
            }
        });
    }

    private void signOut() {
        SharedPreferences.Editor editor = localStorage.edit();
        editor.remove("token");
        editor.commit();

        startActivity(new Intent(getContext(), SignInActivity.class));
        getActivity().finish();
    }

    private void fetchUserData() {
        AsyncTask.execute(() -> {
            service.fetchUserData().enqueue(new Callback<List<UserResponse>>() {
                @Override
                public void onResponse(Call<List<UserResponse>> call, Response<List<UserResponse>> response) {
                    if (response.isSuccessful()) {
                        Picasso.with(getContext())
                                .load("http://cinema.areas.su/up/images/" + response.body().get(0).getAvatar()).into(mAvatar);
                        mUserName.setText(response.body().get(0).getFirstName() + " " + response.body().get(0).getLastName());
                        DataManager.setUserName(response.body().get(0).getFirstName() + " " + response.body().get(0).getLastName());
                        mEmail.setText(response.body().get(0).getEmail());
                    } else if (response.code() == 400) {
                        String serverErrorMessage = ErrorUtils.parseError(response).message();
                        Toast.makeText(getContext(), serverErrorMessage, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Произошла неизвестная ошибка! Попробуйте позже", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<UserResponse>> call, Throwable t) {

                }
            });
        });
    }
}
