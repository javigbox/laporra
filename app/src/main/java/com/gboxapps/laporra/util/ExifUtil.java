package com.gboxapps.laporra.util;

import android.app.Activity;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Cdorado on 29/06/2017.
 */
public class ExifUtil {
    public static Bitmap rotateBitmap(Activity act, Uri src, Bitmap bitmap) {
            /*int orientation = getExifOrientation(src);

            if (orientation == 1) {
                return bitmap;
            }

            Matrix matrix = new Matrix();
            switch (orientation) {
                case 2:
                    matrix.setScale(-1, 1);
                    break;
                case 3:
                    matrix.setRotate(180);
                    break;
                case 4:
                    matrix.setRotate(180);
                    matrix.postScale(-1, 1);
                    break;
                case 5:
                    matrix.setRotate(90);
                    matrix.postScale(-1, 1);
                    break;
                case 6:
                    matrix.setRotate(90);
                    break;
                case 7:
                    matrix.setRotate(-90);
                    matrix.postScale(-1, 1);
                    break;
                case 8:
                    matrix.setRotate(-90);
                    break;
                default:
                    return bitmap;
            }*/
            Matrix matrix = new Matrix();
            try {
                String[] orientationColumn = {MediaStore.Images.Media.ORIENTATION};
                Cursor cur = act.managedQuery(src, orientationColumn, null, null, null);
                int orientation = -1;
                if (cur != null && cur.moveToFirst()) {
                    orientation = cur.getInt(cur.getColumnIndex(orientationColumn[0]));
                }
                matrix.postRotate(orientation);
            }catch(CursorIndexOutOfBoundsException e){
                ExifInterface exifInterface = null;
                int orientation = -1;
                try {
                    exifInterface = new ExifInterface(src.toString());
                    String orientationStr = exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION);
                    orientation = orientationStr != null ? Integer.parseInt(orientationStr) :  ExifInterface.ORIENTATION_NORMAL;
                    switch(orientation) {

                        case ExifInterface.ORIENTATION_ROTATE_90:
                            matrix.postRotate(90);
                            break;

                        case ExifInterface.ORIENTATION_ROTATE_180:
                            matrix.postRotate(180);
                            break;

                        case ExifInterface.ORIENTATION_ROTATE_270:
                            matrix.postRotate(270);
                            break;

                        case ExifInterface.ORIENTATION_NORMAL:
                        default:
                            break;
                    }

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            try {
                Bitmap oriented = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                //bitmap.recycle();
                return oriented;
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
                return bitmap;
            }

    }

    /**
     * Este metodo se usa para cuando cogemos foto directamente desde la camara, ya que le tenemos que pasar la path del file que hacemos image.jpg
     * @param act
     * @param src
     * @param bitmap
     * @return
     */
    public static Bitmap rotateBitmap(Activity act, String src, Bitmap bitmap) {
        Matrix matrix = new Matrix();

            ExifInterface exifInterface = null;
            int orientation = -1;
            try {
                exifInterface = new ExifInterface(src.toString());
                String orientationStr = exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION);
                orientation = orientationStr != null ? Integer.parseInt(orientationStr) :  ExifInterface.ORIENTATION_NORMAL;
                switch(orientation) {

                    case ExifInterface.ORIENTATION_ROTATE_90:
                        matrix.postRotate(90);
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_180:
                        matrix.postRotate(180);
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_270:
                        matrix.postRotate(270);
                        break;

                    case ExifInterface.ORIENTATION_NORMAL:
                    default:
                        break;
                }

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        try {
            Bitmap oriented = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            //bitmap.recycle();
            return oriented;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return bitmap;
        }

    }

    private static int getExifOrientation(String src) throws IOException {
        int orientation = 1;

        try {
            /**
             * if your are targeting only api level >= 5
             * ExifInterface exif = new ExifInterface(src);
             * orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
             */
            if (Build.VERSION.SDK_INT >= 5) {
                Class<?> exifClass = Class.forName("android.media.ExifInterface");
                Constructor<?> exifConstructor = exifClass.getConstructor(new Class[] { String.class });
                Object exifInstance = exifConstructor.newInstance(new Object[] { src });
                Method getAttributeInt = exifClass.getMethod("getAttributeInt", new Class[] { String.class, int.class });
                Field tagOrientationField = exifClass.getField("TAG_ORIENTATION");
                String tagOrientation = (String) tagOrientationField.get(null);
                orientation = (Integer) getAttributeInt.invoke(exifInstance, new Object[] { tagOrientation, 1});
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return orientation;
    }
}
