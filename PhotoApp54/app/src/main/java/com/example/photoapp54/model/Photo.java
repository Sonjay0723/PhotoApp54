package com.example.photoapp54.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Comparator;

public class Photo implements Serializable {

    private static final long serialVersionUID = 6955723612371190680L;
    private ArrayList<Tag> tagList;
    private String photoName;
    //private SerializableBitmap bitmap;
    //private byte[] bytes;
    //String bytes;
    String path;

    public Photo(String path) {
        //this.photoName = photoName;
        /*ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        bytes = stream.toByteArray();*/
        //byte[] byteArray = stream.toByteArray();
        //bytes = Base64.encodeToString(byteArray, Base64.DEFAULT);
        //this.bitmap = new SerializableBitmap(bitmap);
        this.path = path;
        this.tagList = new ArrayList<>();
        if (path.contains("/"))
            this.photoName = path.substring(path.lastIndexOf('/') + 1);
        else
            this.photoName = path;
    }

    public String getPhotoName() {

        return photoName;
    }

    /*public Bitmap getImage() {
        try{
            byte[] encodeByte=Base64.decode(bytes,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
        //byte[] byteArray = bytes.getBytes();
        Bitmap res = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return res;
        //return bitmap.getBitmap();
    }*/

    /*public Bitmap getImage() {
        Bitmap bitmap = null;
        try {
            //File f= new File(new URI(path));
            File f = new File(new URI(path));
            bitmap = BitmapFactory.decodeStream(new FileInputStream(f));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }*/

    public String getPath() {
        return path;
    }

    public ArrayList<Tag> getTags() {
        return tagList;
    }

    public void addTag(Tag newTag) {
        for (int i = 0; i < tagList.size(); i++) {
            if (newTag.getName().equals(tagList.get(i).getName()) && !tagList.get(i).getMulti())
                return;

            if (newTag.equals(tagList.get(i)))
                return;
        }

        tagList.add(newTag);
    }

    public void removeTag(String name, String value) {
        Tag thisTag = new Tag(name,value,false);
        int position = 0;
        for(int i=0; i<tagList.size(); i++) {
            if(thisTag.equals(tagList.get(i))) {
                position = i;
                break;
            }
        }
        tagList.remove(position);
    }

    public boolean equals(Photo other) {

        return this.path.equals(other.getPath());
    }
}
