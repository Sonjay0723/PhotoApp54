package com.example.photoapp54.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public class SerializableBitmap implements Serializable {
    private static final long serialVersionUID = 6554568434893643354L;
    private Bitmap bitmap;
    private byte[] bytes;
    private int size;

    public SerializableBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        bytes = stream.toByteArray();
        size = bytes.length;
    }

    public Bitmap getBitmap() {
        return bitmap; //BitmapFactory.decodeByteArray(bytes, 0, size);
    }

    public void setBitmap(Bitmap x) {
        bitmap = x;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        if (bitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            bytes = stream.toByteArray();
            out.writeInt(bytes.length);
            out.write(bytes);
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        if (in.readInt() > 0) {
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
    }
}
