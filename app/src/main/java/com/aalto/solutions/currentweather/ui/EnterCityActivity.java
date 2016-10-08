package com.aalto.solutions.currentweather.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.aalto.solutions.currentweather.R;
import com.aalto.solutions.currentweather.model.CityWeather;
import com.aalto.solutions.framework.ui.WebServiceLayer;
import com.aalto.solutions.framework.util.SharedPreferenceHelper;

public class EnterCityActivity extends WebServiceLayer
{
    TextView mFoundCity;
    EditText mCityEdit;
    TextView mWeatherDesc;
    ImageView mWeatherIcon;
    String mCity = "";

    //===============================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_enter_city);

        // NOTE: Could use ButterKnife for view binding
        mCityEdit = (EditText)findViewById( R.id.edit_city_entry );
        mFoundCity = (TextView)findViewById( R.id.label_city_found );
        mWeatherDesc = (TextView)findViewById( R.id.label_weather_description );
        mWeatherIcon = (ImageView)findViewById( R.id.weather_icon );

        // Get last stored city and request for fresh data:
        String lastRequestedCity = SharedPreferenceHelper.getLastRequestedCity(this);
        if(lastRequestedCity.length()>0)
            submitRequest(lastRequestedCity);

        // Send webservice request only when 'done' pressed:
        mCityEdit.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                if (actionId == EditorInfo.IME_ACTION_DONE)
                {
                    String city = mCityEdit.getText().toString();
                    if (city.length() > 0)
                    {
                        mCity = city;
                        submitRequest( city.trim() );
                        return true;
                    }
                }
                return false;
            }
        });

    }

    //===============================================================================

    private void submitRequest(final String aCity)
    {
        // start load animation:
        mProgressDialog = ProgressDialog.show( 	this,
                null,
                getString(R.string.Loading), // from network package strings
                false );

        mWebService.submit(aCity, this, CityWeather.class );
    }

    //================================================================================

    @Override
    public <T> void onRequestCompleted(final T aData)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                final CityWeather data = (CityWeather)aData;

                if(data==null || data.cod==401 ) // 401 is error code
                {
                    mProgressDialog.dismiss();
                    return;
                }

                // Download icon if exists:
                String icon = data.weather.get(0).icon;
                if(icon!=null && icon.length()>0)
                    downloadIcon(icon);

                mProgressDialog.dismiss();
                displayData(data);
            }
        });
    }



    //=============================================================================

    private void downloadIcon(final String aIcon)
    {
        mWebService.getImage(aIcon, this );
    }

    //===============================================================================

    @Override
    public void onIconDownLoaded( final Bitmap abmp )
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                Bitmap scaled = Bitmap.createScaledBitmap(abmp, 120, 120, true);
                mWeatherIcon.setImageBitmap(scaled);
            }
        });
    }

    //===================================================================================

    private void displayData( CityWeather aWeatherData )
    {
        Log.d("=MB=", "DATA back - CITY: " + aWeatherData.name + " ,WIND: " + aWeatherData.weather.get(0).main);
        mFoundCity.setText("City: " + aWeatherData.name);
        mWeatherDesc.setText(aWeatherData.weather.get(0).description);

        // save request (city) to prefs:
        SharedPreferenceHelper.saveLastRequestedCity(mCity, this);

        // NOTE: You can display here as much data as you want/need to...
        // All the data is contained in 'aWeatherData'


    }

    //===============================================================================
}
