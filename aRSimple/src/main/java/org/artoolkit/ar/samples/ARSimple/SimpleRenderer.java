package org.artoolkit.ar.samples.ARSimple;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.text.Html;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.artoolkit.ar.base.rendering.ARRenderer;
import org.artoolkit.ar.base.ARToolKit;
import org.artoolkit.ar.base.rendering.Cube;

import javax.microedition.khronos.opengles.GL10;


public class SimpleRenderer extends ARRenderer {

    private int markerID1 = -1;
    private int markerID2 = -1;
//    private int markerID3 = -1;
//    private int markerID4 = -1;
//    private int markerID5 = -1;
    private Cube cube = new Cube(40.0f, -80.0f, 0.0f, 20.0f);
    private TextRender text = new TextRender(120f, 60f, 0f, 50f);


    @Override
    public boolean configureARScene() {
        ARToolKit art = ARToolKit.getInstance();

        markerID1 = art.addMarker("single;Data/hiro.patt;80");
        markerID2 = art.addMarker("single;Data/flex.patt;80");
//        markerID3 = art.addMarker("single;Data/marker01.patt;80");
//        markerID4 = art.addMarker("single;Data/marker02.patt;80");
//        markerID5 = art.addMarker("single;Data/marker03.patt;80");

        if (markerID1 < 0)
            return false;
        if (markerID2 < 0)
            return false;

        return true;
    }

    /**
     * Override the draw function from ARRenderer.
     */
    public void draw(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        // Apply the ARToolKit projection matrix
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadMatrixf(ARToolKit.getInstance().getProjectionMatrix(), 0);

        gl.glEnable(GL10.GL_CULL_FACE);
        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glFrontFace(GL10.GL_CW);

        // If the marker is visible, apply its transformation, and draw a cube
        if (ARToolKit.getInstance().queryMarkerVisible(markerID1)) {
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            gl.glLoadMatrixf(ARToolKit.getInstance().queryMarkerTransformation(markerID1), 0);
            cube.draw(gl);
        }
        if (ARToolKit.getInstance().queryMarkerVisible(markerID2)) {
            changeText("<h3>Linha 1</h3>" +
                       "<b>OTB: </b> 90%<br>" +
                       "<b>Yield: </b> 95%<br>" +
                       "<b>Scrap: </b> 2%<br>", markerID2);
        }else{
            changeText("", 0);
        }
    }
    private void changeText(final String text, final int marker){
        Activity act = ARSimple.getActivity();

        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                ARSimple.textView.setText(Html.fromHtml(text));
                ARSimple.textView.setShadowLayer(100,40,40, Color.BLACK);
                ARSimple.textView.setTextColor(Color.LTGRAY);
                ARSimple.textView.setTextSize(30f);

                float[] matrixArray;

                if (marker != 0) {
                    int centreX = (-(ARSimple.textView.getWidth() / 2) + ARSimple.topLayout.getWidth()) /2;
                    int centreY = (-(ARSimple.textView.getHeight() / 2) + ARSimple.topLayout.getHeight()) /2;

                    matrixArray = ARToolKit.getInstance().queryMarkerTransformation(marker);

                    int aux1 = 1920/320;
                    int aux2 = 1080/240;

                    int posX = centreX + Math.round(matrixArray[12]) * aux1;
                    int posY = centreY + (-1 * Math.round(matrixArray[13])) * aux2;

                    if ((posX + ARSimple.textView.getWidth() / 2) >= ARSimple.topLayout.getWidth())
                        posX = posX - (posX - ARSimple.topLayout.getWidth() - ARSimple.textView.getWidth() / 2);

                    if (posY + ARSimple.textView.getHeight() / 2 >= ARSimple.topLayout.getHeight())
                        posY = posY - (posY - ARSimple.topLayout.getHeight() - ARSimple.textView.getHeight() / 2);

                    ARSimple.textView.setX(posX);
                    ARSimple.textView.setY(posY);
                }
            }
        });
    }
}