package com.aalto.solutions.network.core;

import android.graphics.Bitmap;

/**
 * Callback to client
 */
public interface IWebServiceListener
{
    <T> void onRequestCompleted(final T aData);

    void onIconDownLoaded(final Bitmap bmp);
}

