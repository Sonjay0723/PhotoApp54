package com.example.photoapp54;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.text.Editable;
import android.view.autofill.AutofillValue;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioGroup;
import android.widget.Button;
import android.widget.RadioButton;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.photoapp54.model.*;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class SearchPage extends AppCompatActivity {
    private TextInputLayout personTag;
    private TextInputLayout locationTag;
    private RadioGroup andOr;
    private RadioButton radAnd;
    private RadioButton radOr;
    private GridView imageView;
    private Button search;
    private Button reset;
    private Button back;
    private ArrayList<Album> allAlbums;
    private Album currAlbum;
    private int currAlbumPos;
    //public String path;

    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.search, container, false);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        //path = this.getApplicationInfo().dataDir + "/data.dat";
        andOr = findViewById(R.id.grp2Tag);
        personTag = findViewById(R.id.txtPersonTag);
        locationTag = findViewById(R.id.txtLocationTag);
        radAnd = findViewById(R.id.btnAnd);
        radOr = findViewById(R.id.btnOr);
        imageView = findViewById(R.id.imageView);
        search = findViewById(R.id.btnSearch);
        reset = findViewById(R.id.btnReset);
        back = findViewById(R.id.btnBack);

        Intent intent = getIntent();
        allAlbums = (ArrayList<Album>) intent.getSerializableExtra("allAlbums");
        if (allAlbums == null || allAlbums.isEmpty())
            return;

        currAlbumPos = intent.getIntExtra("currAlbumPos", 0);
        currAlbum = allAlbums.get(currAlbumPos);
    }

    public void search(View view) {
        ArrayList<Photo> resList = new ArrayList<>();

        String strPerson;
        String strLocation;
        try {
            strPerson = personTag.getEditText().getText().toString().trim();
            strLocation = locationTag.getEditText().getText().toString().trim();
        }
        catch (NullPointerException e) {
            strPerson = "";
            strLocation = "";
        }


        if (radAnd.isSelected() || radOr.isSelected()) {
            if (strPerson.isEmpty()) {
                personTag.setError("Field can't be empty!");
                return;
            }
            if (strLocation.isEmpty()) {
                locationTag.setError("Field can't be empty!");
                return;
            }

            personTag.setError(null);
            locationTag.setError(null);
            imageView.removeAllViewsInLayout();
            resList = tagCheck(allAlbums, strPerson, strLocation);
        }
        else {
            if (strPerson.isEmpty() && strLocation.isEmpty()) {
                personTag.setError("Both fields can't be empty!");
                locationTag.setError("Both fields can't be empty!");
                return;
            }
            else {
                personTag.setError(null);
                locationTag.setError(null);
                imageView.removeAllViewsInLayout();
                resList = tagCheck(allAlbums, strPerson, strLocation);
            }
        }

        PhotoAdaptor photoAdaptor = new PhotoAdaptor(this, R.layout.adaptor_view , resList);
        imageView.setAdapter(photoAdaptor);
    }

    private ArrayList<Photo> tagCheck(ArrayList<Album> albums, String pTag, String lTag) {
        ArrayList<Photo> resList = new ArrayList<>();
        for (int i = 0; i < albums.size(); i++) {
            Album currAlbum = albums.get(i);

            if (radAnd.isSelected()) {
                boolean pFound = false;
                boolean lFound = false;
                Tag pTagTemp = new Tag("person", pTag, false);
                Tag lTagTemp = new Tag("location", lTag, false);

                for (int j = 0; j < currAlbum.getPictureList().size(); j++) {
                    Photo currPhoto = currAlbum.getPictureList().get(i);

                    for (int k = 0; k < currPhoto.getTags().size(); k++) {
                        if (currPhoto.getTags().get(i).equals(pTagTemp))
                            pFound = true;
                        if (currPhoto.getTags().get(i).equals(lTagTemp))
                            lFound = true;

                        if (pFound && lFound) {
                            resList.add(currPhoto);
                            break;
                        }
                    }
                }
            }
            else if (radOr.isSelected()) {
                Tag pTagTemp = new Tag("person", pTag, false);
                Tag lTagTemp = new Tag("location", lTag, false);

                for (int j = 0; j < currAlbum.getPictureList().size(); j++) {
                    Photo currPhoto = currAlbum.getPictureList().get(i);

                    for (int k = 0; k < currPhoto.getTags().size(); k++) {
                        if (currPhoto.getTags().get(i).equals(pTagTemp) || currPhoto.getTags().get(i).equals(lTagTemp)) {
                            resList.add(currPhoto);
                            break;
                        }
                    }
                }
            }
            else {
                Tag temp;
                if (pTag.isEmpty())
                    temp = new Tag("location", lTag, false);
                else
                    temp = new Tag("person", pTag, false);

                for (int j = 0; j < currAlbum.getPictureList().size(); j++) {
                    Photo currPhoto = currAlbum.getPictureList().get(i);

                    for (int k = 0; k < currPhoto.getTags().size(); k++) {
                        if (currPhoto.getTags().get(i).equals(temp)) {
                            resList.add(currPhoto);
                            break;
                        }
                    }
                }
            }
        }

        return resList;
    }

    public void reset(View view) {
        personTag.getEditText().setText("");
        locationTag.getEditText().setText("");
        andOr.clearCheck();
        imageView.removeAllViewsInLayout();
    }

    public void backHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra("allAlbums", allAlbums);
        intent.putExtra("currAlbum", currAlbum);
        intent.putExtra("currAlbumPos", currAlbumPos);
        startActivity(intent);
    }
}
