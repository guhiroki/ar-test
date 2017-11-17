package org.artoolkit.ar.samples.ARSimple;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.opengl.GLES10;
import android.opengl.GLUtils;
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

        float hs = size / 2.0f;

        width = 40;
        height = 40;

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
        String txt;
        GLES10.glEnable(GL10.GL_TEXTURE_2D);

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        bitmap.eraseColor(0);

        Paint txtP = new Paint();
        txtP.setARGB(0xff, 0x00, 0x00, 0x00);
        canvas.drawPaint(txtP);

        txtP.setARGB(0xff, 0xff, 0xff, 0xff);
        txtP.setTextScaleX(0.5f);
        txtP.setTextSize(100);
        txtP.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("your_text", 0.f, 0.f + txtP.getTextSize(), txtP);

        int[] textures = new int[1];


        GLES10.glGenTextures(1, textures,0);
        GLES10.glBindTexture(GLES10.GL_TEXTURE_2D, textures[0]);

        GLES10.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        GLES10.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

        GLES10.glTexCoordPointer(2, GLES10.GL_FLOAT, 0, textureBuffer);

        GLES10.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        GLES10.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        GLES10.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
        GLES10.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);

        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);

        bitmap.recycle();

        GLES10.glVertexPointer(3, GLES10.GL_FLOAT, 0, mVertexBuffer);
        GLES10.glEnableClientState(GLES10.GL_VERTEX_ARRAY);
        GLES10.glDrawElements(GLES10.GL_TRIANGLES, 6, GLES10.GL_UNSIGNED_BYTE, mIndexBuffer);

        GLES10.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        GLES10.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        GLES10.glDisable(GL10.GL_TEXTURE_2D);
    }
}
