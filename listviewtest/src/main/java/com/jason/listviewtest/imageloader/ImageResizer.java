package com.jason.listviewtest.imageloader;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.FileDescriptor;

/**
 * Resize bitmap to fit imageView
 *
 * Created by Jason on 2016/6/20.
 */
public class ImageResizer {

    private final static String TAG = "ImageResizer";
    public ImageResizer(){}

    /**
     * @param res Resources (getResources())
     * @param resId ResourceID
     * @param reqWidth width required
     * @param reqHeight height required
     * @return Resized bitmap
     */
    public Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight){
        //First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res,resId,options);

        //Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        //Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res,resId,options);
    }


    /**
     *
     * @param fd FileDescriptor
     * @param reqWidth width required
     * @param reqHeight height required
     * @return Resized bitmap
     */
    public Bitmap decodeSampledBitmapFromFileDescriptor(FileDescriptor fd, int reqWidth, int reqHeight){
        //First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd,null,options);

        //Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options,reqWidth,reqHeight);

        //Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fd,null,options);
    }

    /**
     * Calculate inSampleSize
     * @param options BitmapFactory.Options
     * @param reqWidth width required
     * @param reqHeight height required
     * @return inSampleSize
     */
    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

        if(reqWidth == 0 || reqHeight == 0)
            return 1;

        final int height = options.outHeight;
        final int width = options.outWidth;

        int inSampleSize = 1;
        if(height > reqHeight || width > reqWidth){
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while((halfHeight / inSampleSize) >= reqHeight
                && (halfWidth / inSampleSize) >= reqWidth){
                inSampleSize *= 2;
            }
        }

        Log.d(TAG, "Rt SampleSize = " + inSampleSize);
        return inSampleSize;
    }
}
