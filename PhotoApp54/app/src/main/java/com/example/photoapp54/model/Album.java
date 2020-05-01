package com.example.photoapp54.model;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;

public class Album implements Serializable {
    /**
     * SerialID for Album Class
     * Title of album
     * Arraylist of pictures within album
     */
    private static final long serialVersionUID = -9155495546523356913L;
    public String title;
    public ArrayList<Photo> pictureList = new ArrayList<Photo>();

    /**
     * Initialize Album
     *
     * @param title of Album
     */
    public Album(String title) {
        this.title = title;
    }

    /**
     * get the album title
     *
     * @return Title of album
     */
    public String getTitle() {
        return title;
    }

    /**
     * set new title
     *
     * @param x new name of album
     */
    public void setTitle(String x) {
        this.title = x;
    }

    /**
     * Get the list of pictures in the album
     *
     * @return list of pictures in the album
     */
    public ArrayList<Photo> getPictureList(){
        return pictureList;
    }

    /**
     * Set the picture list of the album to the one provided
     *
     * @param newPictureList the picture list to set the album picture list to
     */
    public void setList(ArrayList<Photo> newPictureList) {
        this.pictureList = newPictureList;
    }

    /**
     * Add a picture to the album's picture list
     *
     * @param newPicture picture to add to the album's picture list(As long as it is not already there)
     */
    public void addPicture(Photo newPicture) {
        if (this.pictureList.size() != 0) {
            for(int i=0; i<this.pictureList.size(); i++) {
                if(this.getPicture(this.pictureList.get(i)).equals(newPicture.getBitmap()))
                    return;
            }
        }

        pictureList.add(newPicture);
    }

    /**
     * delete a picture from the album's picture list
     *
     * @param thisPicture Picture to delete
     */
    public void removePicture(Photo thisPicture) {
        int position = 0;
        for(int i=0; i<pictureList.size(); i++) {
            if(thisPicture.getBitmap().equals(pictureList.get(i).getBitmap())) {
                position = i;
                break;
            }
        }
        pictureList.remove(position);
    }

    /**
     * Get the picture
     *
     * @param thisPhoto the picture to find
     *
     * @return the Picture
     */
    public Bitmap getPicture(Photo thisPhoto) {
        Bitmap searchFor = null;
        for(int i=0; i<this.pictureList.size(); i++) {
            if(this.pictureList.get(i).getBitmap().equals(thisPhoto.getBitmap())) {
                searchFor = this.pictureList.get(i).getBitmap();
                break;
            }
        }
        return searchFor;
    }

    @Override
    public boolean equals(Object other) {
        if(other==null || !(other instanceof Album))
            return false;

        Album curr =(Album) other;
        return curr.getTitle().toLowerCase().equals(title.toLowerCase());
    }

    /**
     * Override hashCode for Album
     */
    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    /**
     * compareTo Method for album compares titles (in lower case)
     *
     * @param currAlbum Current album to compare
     *
     * @return 0 if equal, any number greater/less than 0 otherwise depending on order of album title's
     */
    public int compareTo(Album currAlbum) {
        return this.getTitle().toLowerCase().compareTo(currAlbum.getTitle().toLowerCase());
    }

    /**
     * toString method for album to that album display is legible
     * Has album title, date rage, and number of pictures in the album
     */
    public String toString() {
        String ret = this.getTitle();
        ret += "\n"+Integer.toString(this.pictureList.size())+" Photos";
        return ret;
    }
}
