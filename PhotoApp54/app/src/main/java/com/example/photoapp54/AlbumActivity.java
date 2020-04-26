package com.example.photoapp54;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.photoapp54.model.Album;
import com.example.photoapp54.PhotoAdaptor;
import com.example.photoapp54.model.Photo;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class AlbumActivity extends AppCompatActivity {

    private ArrayList<Album> allAlbums;
    private TextView albumName;
    private Album currAlbum;
    private GridView photoList;
    private int currAlbumPos;
    public String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_page);
        path = this.getApplicationInfo().dataDir + "/data.dat";
        Intent intent = getIntent();
        allAlbums = (ArrayList<Album>) intent.getSerializableExtra("allAlbums");
        currAlbumPos = intent.getIntExtra("currAlbumPos", 0);
        currAlbum = allAlbums.get( currAlbumPos);
        albumName = findViewById(R.id.albumName);
        albumName.setText(currAlbum.getTitle());

        PhotoAdaptor adapter = new PhotoAdaptor(this, R.layout.adaptor_view, currAlbum.getPictureList());
        adapter.setNotifyOnChange(true);
        photoList = findViewById(R.id.photoList);
        photoList.setAdapter(adapter);
        if(!currAlbum.getPictureList().isEmpty())
            photoList.setItemChecked(0, true);

        photoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                photoList.setItemChecked(position, true);
            }
        });
    }

    public void displayPhoto(View view) {
        if (photoList.getAdapter().getCount() == 0)
            return;

        Intent intent = new Intent(this, DisplayActivity.class);

        intent.putExtra("allAlbums", allAlbums);
        intent.putExtra("currAlbumPos", currAlbumPos);
        intent.putExtra("currPhotoPos", photoList.getCheckedItemPosition());
        startActivity(intent);
    }

    public void toHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra("allAlbums", allAlbums);
        intent.putExtra("currAlbumPos", currAlbumPos);
        startActivity(intent);
    }

    public void deletePhoto(View view){
        PhotoAdaptor adaptor = (PhotoAdaptor) photoList.getAdapter();
        int currPhoto = photoList.getCheckedItemPosition();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if(adaptor.getCount() == 0){
            builder.setTitle("Oops! There was an error...");
            builder.setMessage("There are no Images selected to delete!");
            builder.setPositiveButton("Close", null);
            builder.show();

            return;
        }

        builder.setTitle("Delete");
        builder.setMessage("Delete "+ adaptor.getItem(currPhoto).getPhotoName()+" from "+ currAlbum.getTitle()+"?");
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adaptor.remove(adaptor.getItem(currPhoto));
                        currAlbum.removePicture(adaptor.getItem(currPhoto));
                        saveData(allAlbums);
                        if(!currAlbum.getPictureList().isEmpty()){
                            if(currPhoto==0)
                                photoList.setItemChecked(0, true);
                            else
                                photoList.setItemChecked(currPhoto-1, true);
                        }
                    }
                });
        builder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.show();

        return;
    }

    public void addPhoto(View view) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");

        startActivityForResult(intent, 2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {

        super.onActivityResult(requestCode, resultCode, resultData);

        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {

            if (resultData != null) {
                Uri uri = resultData.getData();
                Bitmap bitmap = null;
                try {
                    ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(uri, "r");
                    FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                    bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                    parcelFileDescriptor.close();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

                if(bitmap!=null) {
                    String name = uri.getLastPathSegment();
                    Photo photo = new Photo(bitmap, name);
                    PhotoAdaptor adapter = (PhotoAdaptor) photoList.getAdapter();

                    for (int i = 0; i < adapter.getCount(); i++) {
                        if (photo.getBitmap().sameAs(adapter.getItem(i).getBitmap())) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setTitle("Oops! There was an error...");
                            builder.setMessage(name + " already exists in " + currAlbum.getTitle());
                            builder.setPositiveButton("OK", null);
                            builder.show();

                            return;
                        }
                    }

                    adapter.add(photo);
                    currAlbum.addPicture(photo);
                    saveData(allAlbums);
                }
            }
        }
    }

    public void copyBtn(View view){
        CharSequence[] albumsPossible = new CharSequence[allAlbums.size()-1];

        int value = 0;
        for(int i=0; i<allAlbums.size(); i++){
            if(!allAlbums.get(i).getTitle().equals(currAlbum.getTitle())) {
                albumsPossible[value] = allAlbums.get(i).getTitle();
                value++;
            }
        }

        PhotoAdaptor adaptor = (PhotoAdaptor) photoList.getAdapter();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final String[] moveToAlbum = {albumsPossible[0].toString()};

        builder.setSingleChoiceItems(albumsPossible, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                moveToAlbum[0] = albumsPossible[item].toString();
            }
        });

        builder.setPositiveButton("Copy",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        Photo photo = adaptor.getItem(photoList.getCheckedItemPosition());
                        for(int i=0; i< allAlbums.size(); i++){
                            if(allAlbums.get(i).getTitle().equals(moveToAlbum[0])){
                                for(int j=0; j<allAlbums.get(i).getPictureList().size(); j++){
                                    if(photo.getBitmap().sameAs(allAlbums.get(i).getPictureList().get(j).getBitmap())){
                                        builder.setTitle("Oops! There was an error...");
                                        builder.setMessage(photo.getPhotoName() + " already exists in " + moveToAlbum[0]);
                                        builder.setPositiveButton("OK", null);
                                        builder.show();

                                        return;
                                    }
                                }

                                allAlbums.get(i).addPicture(photo);
                                saveData(allAlbums);
                            }
                        }
                    }
                });
        builder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        builder.show();
        return;
    }

    public void moveBtn(View view){
        CharSequence[] albumsPossible = new CharSequence[allAlbums.size()-1];

        int value = 0;
        for(int i=0; i<allAlbums.size(); i++){
            if(!allAlbums.get(i).getTitle().equals(currAlbum.getTitle())) {
                albumsPossible[value] = allAlbums.get(i).getTitle();
                value++;
            }
        }

        PhotoAdaptor adaptor = (PhotoAdaptor) photoList.getAdapter();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final String[] moveToAlbum = {albumsPossible[0].toString()};

        builder.setSingleChoiceItems(albumsPossible, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                moveToAlbum[0] = albumsPossible[item].toString();
            }
        });

        builder.setPositiveButton("Move",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        Photo photo = adaptor.getItem(photoList.getCheckedItemPosition());
                        for(int i=0; i< allAlbums.size(); i++){
                            if(allAlbums.get(i).getTitle().equals(moveToAlbum[0])){
                                for(int j=0; j<allAlbums.get(i).getPictureList().size(); j++){
                                    if(photo.getBitmap().sameAs(allAlbums.get(i).getPictureList().get(j).getBitmap())){
                                        builder.setTitle("Oops! There was an error...");
                                        builder.setMessage(photo.getPhotoName() + " already exists in " + moveToAlbum[0]);
                                        builder.setPositiveButton("OK", null);
                                        builder.show();

                                        return;
                                    }
                                }

                                allAlbums.get(i).addPicture(photo);
                                currAlbum.removePicture(photo);
                                saveData(allAlbums);
                                adaptor.remove(photo);
                            }
                        }
                    }
                });
        builder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.show();
        return;
    }

    public void saveData(ArrayList<Album> albums) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(albums);

            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
