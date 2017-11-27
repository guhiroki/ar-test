package org.artoolkit.ar.samples.ARSimple;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.artoolkit.ar.base.ARActivity;
import org.artoolkit.ar.base.rendering.ARRenderer;

public class ARSimple extends ARActivity {
    private static FrameLayout frameLayout;
    private static Context context;
    private static Activity activity;
    public static TextView textView;
    public static RelativeLayout topLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        context = getApplicationContext();
        frameLayout = (FrameLayout) this.findViewById(R.id.mainLayout);
        activity = this;
        textView = (TextView)(findViewById(R.id.textLayout));
        topLayout = (RelativeLayout) (findViewById(R.id.topLayout));
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
}