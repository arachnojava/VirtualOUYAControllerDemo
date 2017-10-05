package com.michaeljhenson.virtualouyacontrollerdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class DrawView extends View
{
    private Bitmap button;
    private float buttonX=50, buttonY=300;
    private Paint paint, bgColor;
    private float xt, yt;
    private float speed = 5.0f;

    private boolean isOnButton = false;

    public DrawView(Context context)
    {
        super(context);
        MHControllerUI.init(context.getAssets());
        paint = new Paint();
        bgColor = new Paint(android.graphics.Color.BLACK);
        button = BitmapFactory.decodeResource(getResources(), com.michaeljhenson.virtualouyacontrollerdemo.R.drawable.abc_btn_rating_star_on_mtrl_alpha);
    }


    private void update()
    {
        float speed = 5.0f;
        if (MHControllerUI.getInstance().isButtonPressed(MHControllerUI.DPAD_DOWN))
            buttonY+=speed;
        if (MHControllerUI.getInstance().isButtonPressed(MHControllerUI.DPAD_UP))
            buttonY-=speed;
        if (MHControllerUI.getInstance().isButtonPressed(MHControllerUI.DPAD_RIGHT))
            buttonX+=speed;
        if (MHControllerUI.getInstance().isButtonPressed(MHControllerUI.DPAD_LEFT))
            buttonX-=speed;


        if (buttonX < -button.getWidth())
            buttonX = getWidth();
        else if (buttonX > getWidth())
            buttonX = -button.getWidth();
        if (buttonY < -button.getHeight())
            buttonY = getHeight();
        else if (buttonY > getHeight())
            buttonY = -button.getHeight();

//        if (!isOnButton)
//        {
//            buttonX += speed;
//            if (buttonX > this.getWidth() - button.getWidth())
//            {
//                buttonX = getWidth() - button.getWidth();
//                speed = -speed;
//            }
//            else if (buttonX < 0)
//            {
//                buttonX = 0;
//                speed = -speed;
//            }
//        }
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        update();
        super.onDraw(canvas);

        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), bgColor);

        int buttonSize = button.getWidth();

        if (MHControllerUI.getInstance().isButtonPressed(MHControllerUI.O))
        {
            paint.setColor(Color.GREEN);
            canvas.drawRect(buttonX, buttonY, buttonX + buttonSize, buttonY + buttonSize, paint);
        }
        if (MHControllerUI.getInstance().isButtonPressed(MHControllerUI.U))
        {
            paint.setColor(Color.BLUE);
            canvas.drawRect(buttonX, buttonY, buttonX + buttonSize, buttonY + buttonSize, paint);
        }
        if (MHControllerUI.getInstance().isButtonPressed(MHControllerUI.Y))
        {
            paint.setColor(Color.YELLOW);
            canvas.drawRect(buttonX, buttonY, buttonX + buttonSize, buttonY + buttonSize, paint);
        }
        if (MHControllerUI.getInstance().isButtonPressed(MHControllerUI.A))
        {
            paint.setColor(Color.RED);
            canvas.drawRect(buttonX, buttonY, buttonX + buttonSize, buttonY + buttonSize, paint);
        }




        canvas.drawBitmap(button, buttonX, buttonY, paint);

        MHControllerUI.getInstance().render(canvas);

        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch (event.getActionMasked())
            {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN:
                case MotionEvent.ACTION_MOVE:
                    for (int me = 0; me < event.getPointerCount(); me++)
                    {
                        int eX = (int)event.getX(me);
                        int eY = (int)event.getY(me);
                        MHControllerUI.getInstance().touch(eX, eY, event.getPointerId(me));
                    }
                    return true;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:
                    for (int me = 0; me < event.getPointerCount(); me++)
                    {
                        int eX = (int)event.getX(me);
                        int eY = (int)event.getY(me);
                        MHControllerUI.getInstance().release(eX, eY, event.getPointerId(me));
                    }
                    return true;
                default:
                    for (int me = 0; me < event.getPointerCount(); me++)
                    {
                        int eX = (int)event.getX(me);
                        int eY = (int)event.getY(me);
                        MHControllerUI.getInstance().touch(eX, eY, event.getPointerId(me));
                    }
                    return true;
            }
    }



}
