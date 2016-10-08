package com.aalto.solutions.network.request;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.aalto.solutions.network.core.IWebServiceListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpGet
{
    // SAMPLE: http://api.openweathermap.org/data/2.5/weather?q=Dallas,us&APPID=8ba89e2d814a121c01ec599ff0b64c44
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static final String COUNTRY = ",us&";
    private static final String API_KEY = "APPID=8ba89e2d814a121c01ec599ff0b64c44";

    // SAMPLE: http://openweathermap.org/img/w/04n.png
    private static String IMG_URL = "http://openweathermap.org/img/w/";

    IWebServiceListener mListener;

    //==========================================================================

    public <T> void weatherInCity( final String aCity, final IWebServiceListener aListener, final Class<T> aClass )
    {
        // NOW WE USE JUST basic HttpUrlConnection in a hardcoded way,
        // we should apply one from httpclient package
        HttpURLConnection con = null ;
        InputStream is = null;
        String input = "";
        T data = null;

        mListener = aListener;

        try
        {
            con = (HttpURLConnection) ( new URL(BASE_URL + aCity + COUNTRY + API_KEY)).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();

            // response
            // NOTE: We don't handle error status here, but at UI level which is not good.
            // We should return a http status as well
            StringBuffer buffer = new StringBuffer();
            is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ( (line = br.readLine()) != null )
                buffer.append(line);

            is.close();
            con.disconnect();
            input = buffer.toString();

            // THIS GSON PARSER COULD BE WRAPPED IN PARSER package
            // Get response data:
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.serializeNulls(); //will serialize all properties, even if they're null
            Gson gson = gsonBuilder.create();
            data = gson.fromJson( input, aClass);
        }
        catch(Throwable t)
        {
            t.printStackTrace();
        }
        finally
        {
            try { is.close(); } catch(Throwable t) {}
            try { con.disconnect(); } catch(Throwable t) {}
        }

        if(mListener!=null)
            mListener.onRequestCompleted(data);
    }

    //================================================================================

    // NOTE: We SHOULD CACHE IMAGES SINCE THEY ARE THE SAME
    public void getImage(final String aIcon, final IWebServiceListener aListener )
    {
        HttpURLConnection con = null ;
        InputStream is = null;
        mListener = aListener;

        try
        {
            con = (HttpURLConnection) ( new URL(IMG_URL + aIcon + ".png")).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            //con.setDoOutput(true);
            con.connect();

            Bitmap bmp = BitmapFactory.decodeStream(con.getInputStream());

            if(mListener!=null)
                mListener.onIconDownLoaded(bmp/*imageBytes*/);
        }
        catch(Throwable t)
        {
            t.printStackTrace();
        }
        finally
        {
            try { is.close(); } catch(Throwable t) {}
            try { con.disconnect(); } catch(Throwable t) {}
        }
    }

    //=======================================================================================
}

