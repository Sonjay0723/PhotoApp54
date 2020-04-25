package com.example.photoapp54.model;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

public class Photo implements Serializable {

    //private static final long serialVersionUID = 6955723612371190680L;
    private ArrayList<Tag> tagList  = new ArrayList<Tag>();
    private String photoName;
    private SerializableBitmap bitmap;

    public Photo(Bitmap bitmap, String photoName) {
        this.photoName = photoName;
        this.bitmap = new SerializableBitmap(bitmap);
    }

    public String getPhotoName() {
        return photoName;
    }

    public Bitmap getBitmap() {
        return bitmap.getBitmap();
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
        Comparator<Tag> comparator = Comparator.comparing(Tag::toString);
        tagList.sort(comparator);
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
        return this.photoName.equals(other.photoName);
    }
}
