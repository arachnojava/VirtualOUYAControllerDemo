package com.michaeljhenson.virtualouyacontrollerdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        View drawView = new DrawView(this);

        setContentView(drawView);
    }
}
