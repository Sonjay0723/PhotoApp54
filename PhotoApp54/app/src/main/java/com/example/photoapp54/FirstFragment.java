package com.example.photoapp54;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.photoapp54.model.Album;

import java.util.ArrayList;

public class FirstFragment extends Fragment {

    private ListView albumView;
    private GridView imageView;
    private ArrayList<Album> allAlbums;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        albumView = view.findViewById(R.id.albumList);
        imageView = view.findViewById(R.id.picList);

        /*Intent intent = getIntent();
        allAlbums = (ArrayList<Album>) intent.getSerializableExtra("allAlbums");
        if (allAlbums == null)
            return;
        /*view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });*/
    }
}
