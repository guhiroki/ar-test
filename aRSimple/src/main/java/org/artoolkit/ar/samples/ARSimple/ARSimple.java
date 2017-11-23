package org.artoolkit.ar.samples.ARSimple;

import android.app.Activity;
import android.content.Context;
import android.graphics.Camera;
import android.os.Bundle;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import org.artoolkit.ar.base.ARActivity;
import org.artoolkit.ar.base.camera.CameraPreferencesActivity;
import org.artoolkit.ar.base.rendering.ARRenderer;

/**
 * A very simple example of extending ARActivity to create a new AR application.
 */
public class ARSimple extends ARActivity {
    private static FrameLayout frameLayout;
    private static Context context;
    private static Activity activity;
    private static SurfaceView surfaceView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        context = getApplicationContext();
        frameLayout = (FrameLayout) this.findViewById(R.id.mainLayout);
        activity = this;
        surfaceView = (SurfaceView) this.findViewById(R.id.surfaceView);
    }
    /**
     * Provide our own SimpleRenderer.
     */
    @Override
    protected ARRenderer supplyRenderer() {
        return new SimpleRenderer();
    }

    /**
     * Use the FrameLayout in this Activity's UI.
     */
    @Override
    protected FrameLayout supplyFrameLayout() {
        return frameLayout;
    }
    public static FrameLayout getFrameLayout(){
        return frameLayout;
    }
    public static Context getContext(){
        return context;
    }
    public static Activity getActivity(){
        return activity;
    }
    public static SurfaceView getSurfaceView(){
        return surfaceView;
    }
}