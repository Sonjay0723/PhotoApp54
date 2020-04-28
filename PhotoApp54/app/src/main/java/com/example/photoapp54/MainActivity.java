package com.example.photoapp54;

import android.content.Intent;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.photoapp54.model.Album;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Album> allAlbums;
    private Album currAlbum;
    private int currAlbumPos;
    public String path;

    private ListView albumList;
    private GridView imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        path = this.getApplicationInfo().dataDir + "/data.dat";
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        albumList = findViewById(R.id.albumList);
        imageList = findViewById(R.id.imageView);

        Intent intent = getIntent();
        allAlbums = (ArrayList<Album>) intent.getSerializableExtra("allAlbums");
        if (allAlbums != null && !allAlbums.isEmpty()) {
            currAlbumPos = intent.getIntExtra("currAlbumPos", 0);
            currAlbum = allAlbums.get(currAlbumPos);

            ArrayList<String> titles = new ArrayList<>();
            for (int i = 0; i < allAlbums.size(); i++)
                titles.add(allAlbums.get(i).getTitle());

            ArrayAdapter<String> titleAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titles);
            titleAdapter.setNotifyOnChange(true);
            albumList.setAdapter(titleAdapter);
            albumList.setItemChecked(currAlbumPos, true);

            PhotoAdaptor adapter = new PhotoAdaptor(this, R.layout.adaptor_view, currAlbum.getPictureList());
            adapter.setNotifyOnChange(true);
            imageList = findViewById(R.id.photoList);
            imageList.setAdapter(adapter);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                /*NavHostFragment.findNavController(MainActivity.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);*/
                Intent intent = new Intent(getApplicationContext(), SearchPage.class);
                if (allAlbums != null && !allAlbums.isEmpty()) {
                    intent.putExtra("allAlbums", allAlbums);
                    intent.putExtra("currAlbum", currAlbum);
                    intent.putExtra("currAlbumPos", currAlbumPos);
                }
                else {
                    intent.putExtra("allAlbums", new ArrayList<Album>());
                    intent.putExtra("currAlbum", new Album("temp"));
                    intent.putExtra("currAlbumPos", 0);
                }

                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_open) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
