package com.digicoffer.lauditor.Webservice;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.digicoffer.lauditor.common.AndroidUtils;

import java.net.HttpURLConnection;
import java.net.URL;

public class GetHeadersValues extends AsyncTask<String, String, HttpResultDo> {

    private ProgressDialog progressDialog;
    private AsyncTaskCompleteListener callback;
    private Context activity = null;
    private String URL = "";

    public GetHeadersValues(AsyncTaskCompleteListener callback, Context activity, String url)
    {
        this.callback = callback;
        this.activity = activity;
        this.URL = url;
    }
    @Override
    protected HttpResultDo doInBackground(String... strings) {
        String content_type = "";
        HttpURLConnection httpURLConnection = null;
        HttpResultDo httpResult = new HttpResultDo();
        try {
            httpURLConnection = (HttpURLConnection) new URL(URL).openConnection();
            content_type = httpURLConnection.getContentType();
        }
        catch (Exception e)
        {
            AndroidUtils.logMsg(e.getMessage());
        }
        httpResult.setResponseContent(content_type);
        return httpResult;
    }

    protected void onPostExecute(HttpResultDo httpResultDO) {
        super.onPostExecute(httpResultDO);
        callback.onAsyncTaskComplete(httpResultDO);
    }
}
