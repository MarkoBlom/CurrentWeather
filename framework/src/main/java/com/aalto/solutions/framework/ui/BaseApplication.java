package com.aalto.solutions.framework.ui;


import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import com.aalto.solutions.network.core.WebServiceEngine;

public class BaseApplication extends Application
{
    final String TAG = "=MB=";

    private Lifecycles mLifecyles;

    private ComponentCallback mComponentCallback;

    //=====================================================================

    @Override
    public void onCreate()
    {
        super.onCreate();

        // Register componentCallbacks to track when app goes to background
        mComponentCallback = new ComponentCallback();
        registerComponentCallbacks(mComponentCallback);

        // Register for Activity lifecycle method callbacks
        mLifecyles = new Lifecycles();
        registerActivityLifecycleCallbacks(mLifecyles);

    }

    //=====================================================================

    /**
     * Activity lifecycle callback
     */
    private class Lifecycles implements ActivityLifecycleCallbacks
    {
        private int runningActivityCount = 0;

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState)
        {
            if( runningActivityCount++ == 0 )
            {
                Log.d(TAG, "BaseApplication::onActivityCreated(): Creating activity. None were running");
                // Start web service engine:
                WebServiceEngine.connect();
            }
        }

        @Override
        public void onActivityStarted(Activity activity)
        {

        }

        @Override
        public void onActivityResumed(Activity activity)
        {

        }

        @Override
        public void onActivityPaused(Activity activity)
        {

        }

        @Override
        public void onActivityStopped(Activity activity)
        {
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState)
        {

        }

        @Override
        public void onActivityDestroyed(Activity activity)
        {
            if( --runningActivityCount == 0)
            {
                Log.d(TAG, "BaseApplication::onActivityDestroyed() - Destroying activity. None running");
                // stop web service engine
                WebServiceEngine.requestStop();
            }
        }
    }

    //==================================================================================

    private class ComponentCallback implements ComponentCallbacks2
    {
        /**
         * This method gets called when home button is pressed and app goes to background. We need to send adobe hits
         * when app goes to background.
         * When home button
         * is pressed system calls this method with ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN and that's when we create
         * Async task to make server call and push all queued tags to file.
         *
         * @param level
         */
        @Override
        public void onTrimMemory(int level)
        {
            if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN)
            {
                // We're in the Background
                Log.d(TAG, "BaseApplication::ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN : App went to background. ");
            }
        }

        @Override
        public void onLowMemory()
        {

        }

        @Override
        public void onConfigurationChanged(Configuration newConfig)
        {

        }
    }


    //=======================================================================



}
