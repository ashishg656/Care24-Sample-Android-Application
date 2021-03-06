package ashish.sample.care24.com.care24sample.application;

import java.io.File;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.support.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import ashish.sample.care24.com.care24sample.R;
import ashish.sample.care24.com.care24sample.serverApi.NutraBaseImageDecoder;

public class ZApplication extends Application {

    static ZApplication sInstance;
    private RequestQueue mRequestQueue;
    public static File cacheDir;
    public static final String IMAGE_DIRECTORY_NAME = "care24";

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        cacheDir = StorageUtils.getOwnCacheDirectory(getApplicationContext(),
                "care24");
        initImageLoader(getApplicationContext());
    }

    public synchronized static ZApplication getInstance() {
        if (sInstance == null)
            sInstance = new ZApplication();
        return sInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public static void initImageLoader(Context context) {
        Options decodingOptions = new Options();

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(true).cacheInMemory(true)
                .cacheOnDisk(true).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.drawable.symphony)
                .decodingOptions(decodingOptions)
                .bitmapConfig(Bitmap.Config.ARGB_8888).build();

        final int memClass = ((ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
        final int cacheSize = 1024 * 1024 * memClass / 8;

        System.out.println("Memory cache size" + cacheSize);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 2)
                .threadPoolSize(5).memoryCacheSize(cacheSize)
                .diskCacheSize(50 * 1024 * 1024).diskCacheFileCount(300)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .imageDecoder(new NutraBaseImageDecoder(true))
                .denyCacheImageMultipleSizesInMemory()
                .defaultDisplayImageOptions(options)
                .tasksProcessingOrder(QueueProcessingType.FIFO).build();

        ImageLoader.getInstance().init(config);
    }

    public <T> void addToRequestQueue(Request<T> req, @NonNull String tag) {
        req.setTag(tag);
        getRequestQueue().add(req);
    }
}
