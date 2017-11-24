package org.artoolkit.ar.samples.ARSimple;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.opengl.GLES10;
import android.opengl.GLUtils;
import android.text.TextPaint;

import org.artoolkit.ar.base.rendering.RenderUtils;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

public class TextRender {
    private FloatBuffer mVertexBuffer;
    private FloatBuffer textureBuffer;
    private ByteBuffer mIndexBuffer;
    private int width;
    private int height;
    private boolean first = true;
    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint;

    public TextRender(float size, float x, float y, float z){
        setArrays(size, x, y, z);
    }

    private void setArrays(float size, float x, float y, float z) {
        float hs = size / 2.0f;

        width = Math.round(size);
        height = Math.round(size);

        float vertices[] = {
                x - hs, y - hs, z - hs, // 0
                x + hs, y - hs, z - hs, // 1
                x + hs, y + hs, z - hs, // 2
                x - hs, y + hs, z - hs  // 3
        };

        byte indices[] = {
                0,3,2,0,2,1
        };

        float texture[] = {
                x - hs, y - hs, // 0
                x + hs, y - hs, // 1
                x + hs, y + hs, // 2
                x - hs, y + hs  // 3
        };

        mVertexBuffer = RenderUtils.buildFloatBuffer(vertices);
        textureBuffer = RenderUtils.buildFloatBuffer(texture);
        mIndexBuffer = RenderUtils.buildByteBuffer(indices);
    }

    public void draw(GL10 gl) {
        GLES10.glEnable(GL10.GL_TEXTURE_2D);

        //Create a new bitmap to drawn
        if (first) {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(0);

            //Set a canvas to drawn on our bitmap
            canvas = new Canvas(bitmap);

            //Set paint styles
            paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setARGB(1000, 100, 0, 0);
            canvas.drawPaint(paint);

            //Set text styles.
            TextPaint txtPaint = new TextPaint();
            txtPaint.setTextSize(14f);
            txtPaint.setStrokeWidth(6f); // Text stroke size
            txtPaint.setARGB(1000, 0, 0, 100);

            //Write a text on bitmap
            canvas.drawText("Hello world", 10f, 30f, txtPaint);
            first = false;
        }

        int[] textures = new int[1];

        //Create a new texture
        GLES10.glGenTextures(1, textures,0);
        GLES10.glBindTexture(GLES10.GL_TEXTURE_2D, textures[0]);

        //Set up client state
        GLES10.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        //Set up texture parameters
        GLES10.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
        GLES10.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        GLES10.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
        GLES10.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);

        //Bind our bitmap on 3d object
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);

        //Drawn our  object
        GLES10.glVertexPointer(3, GLES10.GL_FLOAT, 0, mVertexBuffer);
        GLES10.glDrawElements(GLES10.GL_TRIANGLES, 6, GLES10.GL_UNSIGNED_BYTE, mIndexBuffer);
        GLES10.glActiveTexture(GLES10.GL_TEXTURE);

        //Disabling client state.
        GLES10.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        GLES10.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

        //Delete the texture.
        GLES10.glDeleteTextures(1, textures,0);
        GLES10.glDisable(GL10.GL_TEXTURE_2D);
    }
}
