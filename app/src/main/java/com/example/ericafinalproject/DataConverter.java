/*
    Convert Image to Byte [] and the opposite
 */
package com.example.ericafinalproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class DataConverter {
    public static byte[] convertImage2ByteArray(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, stream);
        return stream.toByteArray();
    }
    public static Bitmap converByteArray2Image(byte [] array){
        return BitmapFactory.decodeByteArray(array, 0, array.length);
    }

}
