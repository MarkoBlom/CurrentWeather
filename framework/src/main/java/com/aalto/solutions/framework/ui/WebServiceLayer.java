package com.aalto.solutions.framework.ui;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.aalto.solutions.network.core.IWebServiceListener;
import com.aalto.solutions.network.core.WebServiceEngine;

/**
 * Created by marko on 10/7/2016.
 */

/**
 * Base class to manage web service requests and corresponding callbacks
 */
public class WebServiceLayer extends AppCompatActivity implements IWebServiceListener
{

    /**
     * Handle to web service engine APIs, rather simple for this app
     */
    protected WebServiceEngine mWebService;

    /*
	 * Progress animation
	 */
    protected ProgressDialog mProgressDialog = null;

    //================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // get handle to web services
        mWebService = WebServiceEngine.connect();
    }

    //==================================================================

    @Override
    public <T> void onRequestCompleted( final T aData )
    {
        // implement callback at UI level
    }

    @Override
    public void onIconDownLoaded(final Bitmap bmp)
    {
        // implement callback at UI level
    }

    //==================================================================
}
