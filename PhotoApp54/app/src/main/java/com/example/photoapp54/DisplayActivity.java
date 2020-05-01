package com.example.photoapp54;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.example.photoapp54.model.Album;
import com.example.photoapp54.model.Photo;
import com.example.photoapp54.model.Tag;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class DisplayActivity extends AppCompatActivity {

    private ImageView imageView;
    private ListView tagList;
    private RadioGroup typeGroup;
    private RadioButton personType;
    private RadioButton locationType;
    private TextView tagValue;

    private ArrayList<Album> allAlbums;
    private Album currAlbum;
    private int currAlbumPos;
    private int currPhotoPos;
    public String path;

    private Photo photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_photo);
        path = this.getApplicationInfo().dataDir + "/data.dat";
        Intent intent = getIntent();
        allAlbums = (ArrayList<Album>) intent.getSerializableExtra("allAlbums");
        currAlbum = (Album) intent.getSerializableExtra("currAlbum");
        currAlbumPos = intent.getIntExtra("currAlbumPos", 0);
        currPhotoPos = intent.getIntExtra("currPhotoPos", 0);
        photo = currAlbum.getPictureList().get(currPhotoPos);

        typeGroup = typeGroup.findViewById(R.id.typeGroup);
        personType = personType.findViewById(R.id.personType);
        locationType = locationType.findViewById(R.id.locationType);
        tagValue = tagValue.findViewById(R.id.tagValue);

        ArrayAdapter<Tag> adapter = new ArrayAdapter<>(this, R.layout.arrayadaptor_view, photo.getTags());
        adapter.setNotifyOnChange(true);
        tagList = findViewById(R.id.tagList);
        tagList.setAdapter(adapter);
        if(!photo.getTags().isEmpty()) {
            tagList.setItemChecked(0, true);
            Tag currTag = currAlbum.getPictureList().get(currPhotoPos).getTags().get(tagList.getCheckedItemPosition());
            if(currTag.getName().equals("person"))
                typeGroup.check(R.id.personType);
            else
                typeGroup.check(R.id.locationType);

            tagValue.setText(currTag.getValue());
        }
        else
            typeGroup.check(R.id.personType);

        tagList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tagList.setItemChecked(position,true);
                Tag currTag = currAlbum.getPictureList().get(currPhotoPos).getTags().get(tagList.getCheckedItemPosition());
                if(currTag.getName().equals("person"))
                    typeGroup.check(R.id.personType);
                else
                    typeGroup.check(R.id.locationType);

                tagValue.setText(currTag.getValue());
            }
        });

        imageView.findViewById(R.id.imageView);
        imageView.setImageBitmap(photo.getBitmap());
    }

    public void backBtn(View view) {
        Intent intent = new Intent(this, AlbumActivity.class);

        intent.putExtra("allAlbums", allAlbums);
        intent.putExtra("currAlbumPos", currAlbumPos);
        intent.putExtra("currAlbum", currAlbum);

        startActivity(intent);
    }

    public void nextImg(View view){
        if(currPhotoPos+1 >= currAlbum.getPictureList().size()){
            imageView.setImageBitmap(currAlbum.getPictureList().get(0).getBitmap());
            currPhotoPos=0;
        }
        else{
            imageView.setImageBitmap(currAlbum.getPictureList().get(currPhotoPos+1).getBitmap());
            currPhotoPos ++;
        }

        Photo currPhoto = currAlbum.getPictureList().get(currPhotoPos);

        ArrayAdapter<Tag> adapter = new ArrayAdapter<>(this, R.layout.arrayadaptor_view, currPhoto.getTags());
        adapter.setNotifyOnChange(true);
        tagList = findViewById(R.id.tagList);
        tagList.setAdapter(adapter);
        if(!currPhoto.getTags().isEmpty()) {
            tagList.setItemChecked(0, true);
            Tag currTag = currAlbum.getPictureList().get(currPhotoPos).getTags().get(tagList.getCheckedItemPosition());
            if(currTag.getName().equals("person"))
                typeGroup.check(R.id.personType);
            else
                typeGroup.check(R.id.locationType);

            tagValue.setText(currTag.getValue());
        }
        else
            typeGroup.check(R.id.personType);

        tagList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tagList.setItemChecked(position,true);
                Tag currTag = currAlbum.getPictureList().get(currPhotoPos).getTags().get(tagList.getCheckedItemPosition());
                if(currTag.getName().equals("person"))
                    typeGroup.check(R.id.personType);
                else
                    typeGroup.check(R.id.locationType);

                tagValue.setText(currTag.getValue());
            }
        });
    }

    public void previousImg(View view){
        if(currPhotoPos-1 <0){
            imageView.setImageBitmap(currAlbum.getPictureList().get(currAlbum.getPictureList().size()-1).getBitmap());
            currPhotoPos=currAlbum.getPictureList().size()-1;
        }
        else{
            imageView.setImageBitmap(currAlbum.getPictureList().get(currPhotoPos-1).getBitmap());
            currPhotoPos --;
        }

        Photo currPhoto = currAlbum.getPictureList().get(currPhotoPos);

        ArrayAdapter<Tag> adapter = new ArrayAdapter<>(this, R.layout.arrayadaptor_view, currPhoto.getTags());
        adapter.setNotifyOnChange(true);
        tagList = findViewById(R.id.tagList);
        tagList.setAdapter(adapter);
        if(!currPhoto.getTags().isEmpty()) {
            tagList.setItemChecked(0, true);
            Tag currTag = currAlbum.getPictureList().get(currPhotoPos).getTags().get(tagList.getCheckedItemPosition());
            if(currTag.getName().equals("person"))
                typeGroup.check(R.id.personType);
            else
                typeGroup.check(R.id.locationType);

            tagValue.setText(currTag.getValue());
        }
        else
            typeGroup.check(R.id.personType);

        tagList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tagList.setItemChecked(position,true);
                Tag currTag = currAlbum.getPictureList().get(currPhotoPos).getTags().get(tagList.getCheckedItemPosition());
                if(currTag.getName().equals("person"))
                    typeGroup.check(R.id.personType);
                else
                    typeGroup.check(R.id.locationType);

                tagValue.setText(currTag.getValue());
            }
        });
    }

    public void addTag(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        tagValue = findViewById(R.id.tagValue);
        if(tagValue.getText().toString().isEmpty()){
            builder.setTitle("Oops! There was an error...");
            builder.setMessage("There is no value entered to add!");
            builder.setPositiveButton("Close", null);
            builder.show();

            return;
        }

        String tagTypeSelected = "Person";
        boolean multipleValues = true;
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()){
            case R.id.personType:
                if(checked)
                    break;
            case R.id.locationType:
                if(checked) {
                    tagTypeSelected = "Location";
                    multipleValues = false;
                }
                break;
        }

        Tag newTag = new Tag(tagTypeSelected, tagValue.getText().toString(), multipleValues);
        for(int i=0; i< currAlbum.getPictureList().get(currPhotoPos).getTags().size(); i++){
            if(currAlbum.getPictureList().get(currPhotoPos).getTags().get(i).equals(newTag)){
                builder.setTitle("Oops! There was an error...");
                builder.setMessage("This tag already exists!");
                builder.setPositiveButton("Close", null);
                builder.show();

                return;
            }
            else if(currAlbum.getPictureList().get(currPhotoPos).getTags().get(i).getName().equals("location")){
                builder.setTitle("Oops! There was an error...");
                builder.setMessage("Location is only allowed one value, and "+currAlbum.getPictureList().get(currPhotoPos).getPhotoName()+" already has a location!");
                builder.setPositiveButton("Close", null);
                builder.show();

                return;
            }
        }

        builder.setTitle("Add Tag");
        builder.setMessage("Add tag"+ newTag.toString()+" from "+ currAlbum.getPictureList().get(currPhotoPos).getPhotoName()+"?");
        builder.setPositiveButton("Yes",
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    currAlbum.getPictureList().get(currPhotoPos).addTag(newTag);
                    tagList.refreshDrawableState();
                    saveData(allAlbums);
                    tagList.setItemChecked(currAlbum.getPictureList().get(currPhotoPos).getTags().size()-1, true);
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

    public void deleteTag(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if(currAlbum.getPictureList().get(currPhotoPos).getTags().isEmpty()){
            builder.setTitle("Oops! There was an error...");
            builder.setMessage("There are now tags selected to delete!");
            builder.setPositiveButton("Close", null);
            builder.show();

            return;
        }

        Tag currTag = currAlbum.getPictureList().get(currPhotoPos).getTags().get(tagList.getCheckedItemPosition());

        builder.setTitle("Delete Tag");
        builder.setMessage("Delete tag"+ currTag +" from "+ currAlbum.getPictureList().get(currPhotoPos).getPhotoName()+"?");
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        currAlbum.getPictureList().get(currPhotoPos).removeTag(currTag.getName(), currTag.getValue());
                        tagList.refreshDrawableState();
                        saveData(allAlbums);
                        if(!currAlbum.getPictureList().get(currPhotoPos).getTags().isEmpty()) {
                            tagList.setItemChecked(currAlbum.getPictureList().get(currPhotoPos).getTags().size() - 1, true);
                            Tag currTag = currAlbum.getPictureList().get(currPhotoPos).getTags().get(tagList.getCheckedItemPosition());
                            if(currTag.getName().equals("person"))
                                typeGroup.check(R.id.personType);
                            else
                                typeGroup.check(R.id.locationType);

                            tagValue.setText(currTag.getValue());
                        }
                        else
                            typeGroup.check(R.id.personType);
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
