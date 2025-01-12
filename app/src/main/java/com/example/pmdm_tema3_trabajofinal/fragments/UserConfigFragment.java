package com.example.pmdm_tema3_trabajofinal.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pmdm_tema3_trabajofinal.R;

public class UserConfigFragment extends Fragment {

    public UserConfigFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inlar el layout
        return inflater.inflate(R.layout.user_config, container, false);
    }
}
