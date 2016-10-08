package com.aalto.solutions.network.core;


import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;

import com.aalto.solutions.network.request.HttpGet;

/**
 * Webservice Engine which manages webservice requests
 * @author Marko
 *
 */
public class WebServiceEngine extends Thread {

    static final String TAG = "=MB=";

    /**
     * Handle to Rest WebService -engine, this is a singleton class
     */
    static WebServiceEngine mRef = null;

    static private Handler mHandler=null;

    //=======================================================

    private WebServiceEngine() {}

    //=======================================================

    /**
     * Retrieve handle to engine which is a singleton -class
     *
     * @return handle to WebServiceEngine.
     *
     */
    public static WebServiceEngine connect() {

        if( mRef==null )
        {
            mRef = new WebServiceEngine();
            // Get Looper started :
            mRef.start();
        }

        return mRef;
    }

    //==============================================================================

    @Override
    public void run() {

        try {
            Looper.prepare();

            Log.d( TAG,"\n \n \n \n -------- WebServiceEngine:: Entering Loop ------\n \n \n \n " );

            mHandler = new Handler();

            Looper.loop();

            Log.d(TAG, "\n \n \n -------- WebServiceEngine:: Exiting gracefully ------\n \n \n \n" );
            mRef = null;
            mHandler = null;

        } catch( Throwable t) {
            // error:
            Log.d(TAG, t.toString() );
        }
    }

    //==============================================================================

    public static synchronized void requestStop()
    {
        if( mHandler==null )
            return;

        mHandler.post( new Runnable()
        {
            public void run()
            {
                Log.d(TAG, "\n \n-------- WebServiceEngine loop quitting by request -------\n \n" );
                Looper.myLooper().quit();
            }
        });
    }

    //===============================================================================

    public synchronized <T> void submit(final String aCity, final IWebServiceListener aListener, final Class<T> aClass   )
    {
        mHandler.post(new Runnable()
        {
            public void run()
            {
                try
                {
                    new HttpGet().weatherInCity( aCity, aListener, aClass );
                }
                catch( Exception e  )
                {
                    Log.d( TAG, e.toString() );
                    // TODO : do further error handling here
                }
            }
        });
    }

    //================================================================================

    public void getImage( final String aIcon, final IWebServiceListener aListener )
    {
        mHandler.post(new Runnable()
        {
            public void run()
            {
                try
                {
                    new HttpGet().getImage( aIcon, aListener );
                }
                catch( Exception e  )
                {
                    Log.d( TAG, e.toString() );
                    // TODO : do further error handling here
                }
            }
        });

    }

    //================================================================================
}
