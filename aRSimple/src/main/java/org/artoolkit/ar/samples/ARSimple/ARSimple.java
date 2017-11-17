package org.artoolkit.ar.samples.ARSimple;

import android.os.Bundle;
import android.widget.FrameLayout;
import org.artoolkit.ar.base.ARActivity;
import org.artoolkit.ar.base.rendering.ARRenderer;

/**
 * A very simple example of extending ARActivity to create a new AR application.
 */
public class ARSimple extends ARActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
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
        return (FrameLayout) this.findViewById(R.id.mainLayout);
    }
}