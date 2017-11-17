package org.artoolkit.ar.samples.ARSimple;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import org.artoolkit.ar.base.assets.AssetHelper;

import java.util.Set;

public class ARSimpleApplication extends Application {

    private static Application sInstance;

    // Anywhere in the application where an instance is required, this method
    // can be used to retrieve it.
    public static Application getInstance() {
        return sInstance;
    }

    private static Context context;
    private static FrameLayout fl;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        ARSimpleApplication.context = getApplicationContext();
        View v = new View(context);
        fl = (FrameLayout) v.findViewById(R.id.mainLayout);
        ((ARSimpleApplication) sInstance).initializeInstance();
    }

    // Here we do one-off initialisation which should apply to all activities
    // in the application.
    protected void initializeInstance() {

        // Unpack assets to cache directory so native library can read them.
        // N.B.: If contents of assets folder changes, be sure to increment the
        // versionCode integer in the AndroidManifest.xml file.
        AssetHelper assetHelper = new AssetHelper(getAssets());
        Set<String> a = assetHelper.getAssetFilenames("Data");
        assetHelper.cacheAssetFolder(getInstance(), "Data");
    }
    public static Context getAppContext() {
        return context;
    }
    public static FrameLayout getFL() {
        return fl;
    }
}
