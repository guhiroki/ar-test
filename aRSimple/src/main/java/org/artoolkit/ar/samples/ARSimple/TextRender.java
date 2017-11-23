package org.artoolkit.ar.samples.ARSimple;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
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

    public TextRender(float size, float x, float y, float z){
        setArrays(size, x, y, z);
    }

    private void setArrays(float size, float x, float y, float z) {
        size = 300;
        float hs = size / 2.0f;

        width = Math.round(hs);
        height = Math.round(hs);

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
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.eraseColor(0);

        //Set a canvas to drawn on our bitmap
        Canvas canvas = new Canvas(bitmap);
        canvas.translate(width, height);
        canvas.drawColor(Color.MAGENTA);

        //Set paint styles
        Paint paint = new Paint();
//        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(12); // Text Size
        paint.setARGB(100,10,20,30);

        //Drawn on bitmap
        canvas.drawText("Hello world", 0f, 0f, paint);

        int[] textures = new int[1];

        //Create a new texture
        GLES10.glGenTextures(1, textures,0);
        GLES10.glBindTexture(GLES10.GL_TEXTURE_2D, textures[0]);

        //Set up client state
        GLES10.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        GLES10.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

        GLES10.glTexCoordPointer(3, GLES10.GL_FLOAT, 0, textureBuffer);

        //Set up texture parameters
        GLES10.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
        GLES10.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
        GLES10.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        GLES10.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);

        //Bind our bitmap on 3d object
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);

        //Clean up the bitmap
        bitmap.recycle();

        //Drawn our  object
        GLES10.glVertexPointer(3, GLES10.GL_FLOAT, 0, mVertexBuffer);
        GLES10.glEnableClientState(GLES10.GL_VERTEX_ARRAY);
        GLES10.glDrawElements(GLES10.GL_TRIANGLES, 6, GLES10.GL_UNSIGNED_BYTE, mIndexBuffer);

        //Disabling client state.
        GLES10.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        GLES10.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

        //Delete the texture.
        GLES10.glDeleteTextures(1, textures,0);
        GLES10.glDisable(GL10.GL_TEXTURE_2D);
    }
}
