package com.michaeljhenson.virtualouyacontrollerdemo;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import java.io.IOException;

/**
 * Created by D01052702 on 8/1/2015.
 */
public class MHControllerUI
{
    private static final int BUTTON_UP = 0;
    private static final int BUTTON_DOWN = 1;

    public static final int DPAD_UP = 0;
    public static final int DPAD_RIGHT = 1;
    public static final int DPAD_DOWN = 2;
    public static final int DPAD_LEFT = 3;
    public static final int O = 4;
    public static final int U = 5;
    public static final int Y = 6;
    public static final int A = 7;

    private static final int NUM_BUTTONS = 8;

    private static MHControllerUI instance;

    private AssetManager assets;
    private MHControllerButton[] buttons = new MHControllerButton[NUM_BUTTONS];
    private int[] id = new int[NUM_BUTTONS];

    private MHControllerUI(AssetManager assets)
    {
        this.assets = assets;

        // Load OUYA button images.
        buttons[DPAD_UP] = new MHControllerButton(load("OUYA_DPAD_UP_ARROW.png"));
        buttons[DPAD_RIGHT] = new MHControllerButton(load("OUYA_DPAD_RIGHT_ARROW.png"));
        buttons[DPAD_DOWN] = new MHControllerButton(load("OUYA_DPAD_DOWN_ARROW.png"));
        buttons[DPAD_LEFT] = new MHControllerButton(load("OUYA_DPAD_LEFT_ARROW.png"));
        buttons[O] = new MHControllerButton(load("OUYA_O.png"));
        buttons[U] = new MHControllerButton(load("OUYA_U.png"));
        buttons[Y] = new MHControllerButton(load("OUYA_Y.png"));
        buttons[A] = new MHControllerButton(load("OUYA_A.png"));

    }

    private Bitmap load(String filename)
    {
        try
        {
            return BitmapFactory.decodeStream(assets.open(filename));
        }
        catch (IOException ioe)
        {
            Log.e("ERROR", "Load failed for " + filename);
        }

        return null;
    }

    public static void init(AssetManager assets)
    {
        if (instance == null)
            instance = new MHControllerUI(assets);
    }

    public static MHControllerUI getInstance()
    {
        return instance;
    }


    public boolean isButtonPressed(int buttonID)
    {
        if (buttonID < 0 || buttonID >= buttons.length)
            return false;

        return buttons[buttonID].pressed;
    }

    public void render(Canvas g)
    {
        buttons[DPAD_UP].x = buttons[DPAD_UP].image.getWidth();
        buttons[DPAD_UP].y = g.getHeight() - buttons[DPAD_UP].image.getHeight()*3;
        buttons[DPAD_LEFT].x = 0;
        buttons[DPAD_LEFT].y = g.getHeight()-buttons[DPAD_LEFT].image.getHeight()*2;
        buttons[DPAD_RIGHT].x = buttons[DPAD_RIGHT].image.getWidth()*2;
        buttons[DPAD_RIGHT].y = buttons[DPAD_LEFT].y;
        buttons[DPAD_DOWN].x = buttons[DPAD_UP].x;
        buttons[DPAD_DOWN].y = g.getHeight() - buttons[DPAD_DOWN].image.getHeight();

        buttons[O].x = g.getWidth()-buttons[O].image.getWidth()*2;
        buttons[O].y = g.getHeight()-buttons[O].image.getHeight();
        buttons[U].x = g.getWidth()-buttons[U].image.getWidth()*3;
        buttons[U].y = g.getHeight()-buttons[O].image.getHeight()*2;
        buttons[Y].x = buttons[O].x;
        buttons[Y].y = g.getHeight()-buttons[Y].image.getHeight()*3;
        buttons[A].x = g.getWidth()-buttons[A].image.getWidth();
        buttons[A].y = buttons[U].y;

        for  (MHControllerButton b : buttons)
            b.render(g);

    }

    public void touch(int tx, int ty, int id)
    {
        for (MHControllerButton b : buttons)
            if (b.isOnButton(tx, ty))
            {
                b.pressed = true;
                b.id = id;
            }
    }

    public void release(int tx, int ty, int id)
    {
        for (MHControllerButton b : buttons)
            if (b.isOnButton(tx, ty))
            {
                b.pressed = false;
                b.id = id;
            }
    }

    private class MHControllerButton
    {
        int x, y, id;
        Bitmap image;
        boolean pressed;

        public MHControllerButton(Bitmap image)
        {
            x = y = 0;
            this.image = image;
            pressed = false;
        }

        public void render(Canvas g)
        {
            g.drawBitmap(image, x, y, null);
        }

        public boolean isOnButton(float tx, float ty)
        {
            int w = image.getWidth();
            int h = image.getHeight();
            return (x <= tx && tx <= x+w && y <= ty && ty <= y+h);
        }
    }
}
