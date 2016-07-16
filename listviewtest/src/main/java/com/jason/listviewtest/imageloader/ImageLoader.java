package com.jason.listviewtest.imageloader;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StatFs;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jason.listviewtest.R;
import com.jason.listviewtest.Helpter.Utils;
import com.jason.listviewtest.libcore.io.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Provide memory cache and disk cache of bitmap.
 *
 * Created by Jason on 2016/6/20.
 */
public class ImageLoader {

    private static final int DISK_CACHE_SIZE = 50 * 1024 * 1024;//DiskCacheSize
    private static final int DISK_CACHE_INDEX = 0;
    private static final String TAG = "ImageLoader";
    private static final int IO_BUFFER_SIZE = 8 * 1024; //IO bufferedStream size
    private static final int TAG_KEY_URI = R.id.imageloader_url; //Tag for imageView
    private static final int MESSAGE_POST_RESULT = 1; //Tag for message for image
    private static final int MESSAGE_POST_RESULT_LAYOUT = 2; //Tag for message for image


    private Context mContext;
    private boolean mIsDiskCacheCreated = false;

    private LruCache<String, Bitmap> mMemoryCache;
    private DiskLruCache mDiskLruCache;
    private ImageResizer imageResizer = new ImageResizer();


    /**
     * Using Main Loop to create the handler.
     */
    private Handler mMainHandler = new Handler(Looper.getMainLooper()){
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MESSAGE_POST_RESULT:
                    LoaderResult loadResult = (LoaderResult) msg.obj;
                    ImageView imgview = loadResult.imageView;
                    String uri = (String) imgview.getTag(TAG_KEY_URI);
                    if(uri.equals(loadResult.Uri)) {
                        imgview.setImageBitmap(loadResult.bitmap);
                    }
                    else
                        Log.w(TAG,"ImageView uri has changed. Ignore the set operation");
                    break;
                case MESSAGE_POST_RESULT_LAYOUT:
                    LoaderResultForLayout loaderResultForLayout = (LoaderResultForLayout) msg.obj;
                    LinearLayout mlayout = loaderResultForLayout.mlayout;
                    mlayout.setBackground(new BitmapDrawable(loaderResultForLayout.bitmap));
                    break;
            }

        }
    };

    /**
     * Executor definition to load bitmap async
     */
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;

    private static final long KEEP_ALIVE = 10L;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            AtomicInteger mCount = new AtomicInteger(1);

            return new Thread(r,"ImageLoader#" + mCount.getAndIncrement());
        }
    };
    public static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
            CORE_POOL_SIZE, MAXIMUM_POOL_SIZE,KEEP_ALIVE, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(),sThreadFactory
    );

    /**
     * Build a new instance of Imageloader
     * @param context context
     * @return a new instance of ImageLoader
     */
    public static ImageLoader build(Context context) {
        return new ImageLoader(context);
    }

    private ImageLoader(Context context) {
        mContext = context.getApplicationContext();
        //Set memory cache size(maxMemory/8)
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };

        //init DiskCache
        File diskCacheDir = getDiskCacheDir(mContext, "bitmap");
        if(!diskCacheDir.exists())
            diskCacheDir.mkdirs();

        if(getUsableSpace(diskCacheDir) > DISK_CACHE_SIZE){
            try {
                mDiskLruCache = DiskLruCache.open(diskCacheDir,1,1,DISK_CACHE_SIZE);
                mIsDiskCacheCreated = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param path CachePath
     * @return Usable space left
     */
    private long getUsableSpace(File path) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD)
            return path.getUsableSpace();

        final StatFs stats = new StatFs(path.getPath());
        return stats.getBlockSizeLong() * stats.getAvailableBlocksLong();
    }

    /**
     * Get Cache directory
     * @param mContext Context
     * @param uniqueName LastPathName:identify different cache content
     * @return File
     */
    private File getDiskCacheDir(Context mContext, String uniqueName) {
        String cachePath;
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable())
            //Sd card exists and is not removable
            cachePath = mContext.getExternalCacheDir().getPath();
        else
            cachePath = mContext.getCacheDir().getPath();

        return new File(cachePath + File.separator + uniqueName);
    }


    /**
     * Add bitmap to MemoryCache
     * @param key key to bitmap
     * @param bitmap bitmap
     */
    private void addBitmapToMemoryCache(String key, Bitmap bitmap){
        if(getBitmapFromMemoryCache(key) == null)
            mMemoryCache.put(key,bitmap);
    }

    /**
     * Get bitmap from MemoryCache
     * @param key key to bitmap
     * @return bitmap associated with key
     */
    private Bitmap getBitmapFromMemoryCache(String key) {
        return mMemoryCache.get(key);
    }

    private Bitmap loadBitmapFromMemoryCache(String url){
        String key = hashKeyFormUrl(url);
        return getBitmapFromMemoryCache(key);
    }

    /**
     * Try to load bitmap from the Internet and cache it in Disk Cache.
     * @param url url
     * @param reqWidth width required
     * @param reqHeight height required
     * @return bitmap
     * @throws IOException
     */
    private Bitmap loadBitmapFromHttp(String url, int reqWidth, int reqHeight) throws IOException {
        if(Looper.getMainLooper() == Looper.myLooper())
            throw new RuntimeException("Cannot visit network from UI");

        if(mDiskLruCache == null)
            return null;

        String key = hashKeyFormUrl(url);
        DiskLruCache.Editor editor = mDiskLruCache.edit(key);
        if(editor != null){
            OutputStream outputStream = editor.newOutputStream(DISK_CACHE_INDEX);
            if(downloadUrlToStream(url, outputStream))
                editor.commit();
            else
                editor.abort();

            mDiskLruCache.flush();
        }

        return loadBitMapFromDiskCache(url, reqWidth, reqHeight);
    }

    /**
     * Get bitmap from DiskCache and resize it. If bitmap is not null, add the resized one to memory cache.
     * @param url bitmap's url
     * @param reqWidth width required for resize
     * @param reqHeight height required for resize
     * @return resized bitmap
     * @throws IOException
     */
    private Bitmap loadBitMapFromDiskCache(String url, int reqWidth, int reqHeight) throws IOException {
        if(Looper.myLooper() == Looper.getMainLooper())
            Log.v(TAG, "Load bitmap from UI thread. Not recommended.");

        if(mDiskLruCache == null)
            return null;

        String key = hashKeyFormUrl(url);
        Bitmap bitmap = null;
        DiskLruCache.Snapshot snapShot = mDiskLruCache.get(key);
        if(snapShot != null){
            FileInputStream fileInputStream = (FileInputStream) snapShot.getInputStream(DISK_CACHE_INDEX);
            FileDescriptor fd = fileInputStream.getFD();
            bitmap = imageResizer.decodeSampledBitmapFromFileDescriptor(fd,reqWidth,reqHeight);
            if(bitmap != null)
                //Add the resized bitmap to memory cache.
                addBitmapToMemoryCache(url,bitmap);
        }
        return bitmap;
    }

    /**
     * Download bitmap from urlString in given outputStream.
     * @param urlString url
     * @param outputStream outputStream to return.
     * @return isDownload success.
     */
    private boolean downloadUrlToStream(String urlString, OutputStream outputStream) {
        HttpURLConnection httpUrlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;

        try {
            final URL url = new URL(urlString);
            httpUrlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(httpUrlConnection.getInputStream(), IO_BUFFER_SIZE);
            out = new BufferedOutputStream(outputStream, IO_BUFFER_SIZE);

            int b;
            while((b = in.read()) != -1){
                out.write(b);
            }
            return true;
        } catch (MalformedURLException e) {
            Log.e(TAG,"MalformedURLException. Bitmap download failed.");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG,"IOException. Bitmap download failed.");
            e.printStackTrace();
        }finally {
            if(httpUrlConnection != null)
                httpUrlConnection.disconnect();
            try {
                if(in != null)
                    in.close();
                if(out != null)
                    out.close();
            } catch (IOException e){
                Log.v(TAG," Finally: IOException.");
            }

        }
        return false;

    }

    /**
     * Directly download bitmap from url and return. Won't cache.
     * @param urlString url
     * @return bitmap if success
     */
    private Bitmap downloadBitmapFromUrl(String urlString) {
        Bitmap bitmap = null;
        HttpURLConnection urlConnection = null;
        BufferedInputStream in = null;

        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(), IO_BUFFER_SIZE);

            bitmap = BitmapFactory.decodeStream(in);

        } catch (MalformedURLException e) {
            Log.e(TAG,"MalformedURLException. Bitmap download failed.");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG,"IOException. Bitmap download failed.");
            e.printStackTrace();
        }finally {
            if(urlConnection != null)
                urlConnection.disconnect();
            try {
                if(in != null)
                    in.close();
            } catch (IOException e){
                Log.v(TAG," Finally: IOException.");
            }
        }

        return bitmap;
    }

    /**
     * Get key of URL
     * @param url given url
     * @return hexString of MD5 version of url
     */
    private String hashKeyFormUrl(String url) {
        String cacheKey = null;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(url.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return cacheKey;
    }

    /**
     * Convert byte[] to HexString
     * @param bytes byte[]
     * @return Hex string
     */
    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0 ; i < bytes.length; i++){
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if(hex.length() == 1)
                sb.append('0');
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * Load bitmap from memory cache or disk cache or network.
     * @param url bitmap's url
     * @param reqWidth width required
     * @param reqHeight height required
     * @return bitmap, can be null
     */
    public Bitmap loadBitmap(String url, int reqWidth, int reqHeight){
        Bitmap bitmap = loadBitmapFromMemoryCache(url);
        if(bitmap != null)
            return bitmap;

        try {
            bitmap = loadBitMapFromDiskCache(url,reqWidth,reqHeight);
            if(bitmap != null)
                return bitmap;

            bitmap = loadBitmapFromHttp(url,reqWidth,reqHeight);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(bitmap == null && !mIsDiskCacheCreated) {
            Log.w(TAG, "Error encountered. DiskLruCache is not created.");
            bitmap = downloadBitmapFromUrl(url);
        }

        return bitmap;
    }

    /**
     * Load bitmap from memory cache or disk cache or network async, then bind bitmap to imageView. Bitmap can be shaped to round.
     * @param uri http url
     * @param imageView bitmap's bind object
     * @param isShapeToRound isShapeToRound(List use only)
     */
    public void bindBitmap(final String uri, final ImageView imageView, boolean isShapeToRound){
        bindBitmap(uri, imageView,0,0, isShapeToRound);
    }


    /**
     *  Load bitmap from memory cache or disk cache or network async, then bind bitmap to imageView.
     * NOTE THAT: should run in UI thread
     * @param uri http url
     * @param imageView bitmap's bind object
     */
    public void bindBitmap(final String uri, final ImageView imageView){
        bindBitmap(uri, imageView,0,0,false);
    }
    /**
     * @param uri http url
     * @param imageView bitmap's bind object
     * @param reqWidth width required
     * @param reqHeight height required
     * @param isShapeToRound  is bitmap need to be shaped to round(list only)
     */
    public void bindBitmap(final String uri, final ImageView imageView,
                           final int reqWidth, final int reqHeight, final boolean isShapeToRound){
        //Set Tag of ImageView in case of dislocation.
        imageView.setTag(TAG_KEY_URI, uri);
        Bitmap bitmap = loadBitmapFromMemoryCache(uri);
        if(bitmap != null){
            if(isShapeToRound)
                bitmap = Utils.toRoundBitmap(bitmap);

            imageView.setImageBitmap(bitmap);
            return;
        }

        //Get bitmap async.
        Runnable loadBitmapTast = new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = loadBitmap(uri,reqWidth,reqHeight);
                if(bitmap != null){
                    if(isShapeToRound)
                        bitmap = Utils.toRoundBitmap(bitmap);
                    LoaderResult result = new LoaderResult(imageView,uri,bitmap);
                    //Get a message from message pool and sets What, object. Then send the msg.
                    mMainHandler.obtainMessage(MESSAGE_POST_RESULT, result).sendToTarget();
                }
            }
        };

        THREAD_POOL_EXECUTOR.execute(loadBitmapTast);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void bindBitmap(final String uri, final LinearLayout mlayout,
                           final int reqWidth, final int reqHeight){
        //Set Tag of ImageView in case of dislocation.
        mlayout.setTag(TAG_KEY_URI, uri);
        Bitmap bitmap = loadBitmapFromMemoryCache(uri);
        if(bitmap != null){
            Drawable drawable = new BitmapDrawable(bitmap);
            mlayout.setBackground(drawable);
            return;
        }

        //Get bitmap async.
        Runnable loadBitmapTast = new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = loadBitmap(uri,reqWidth,reqHeight);
                if(bitmap != null){

                    LoaderResultForLayout result = new LoaderResultForLayout(mlayout,uri,bitmap);
                    //Get a message from message pool and sets What, object. Then send the msg.
                    mMainHandler.obtainMessage(MESSAGE_POST_RESULT_LAYOUT, result).sendToTarget();
                }
            }
        };

        THREAD_POOL_EXECUTOR.execute(loadBitmapTast);
    }

    private class LoaderResult {
        private ImageView imageView;
        private String Uri;
        private Bitmap bitmap;

        public LoaderResult(ImageView imageView, String uri, Bitmap bitmap) {
            this.imageView = imageView;
            Uri = uri;
            this.bitmap = bitmap;
        }
    }

    private class LoaderResultForLayout {
        private LinearLayout mlayout;
        private String Uri;
        private Bitmap bitmap;

        public LoaderResultForLayout(LinearLayout mlayout, String uri, Bitmap bitmap) {
            this.mlayout = mlayout;
            Uri = uri;
            this.bitmap = bitmap;
        }
    }
}
