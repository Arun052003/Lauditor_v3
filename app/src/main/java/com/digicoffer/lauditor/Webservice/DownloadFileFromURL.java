package com.digicoffer.lauditor.Webservice;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.digicoffer.lauditor.common.Constants;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadFileFromURL  extends AsyncTask<String , String, HttpResultDo> {


    private ProgressDialog progressDialog;
    private String fileName;
    private String folder;
    private AsyncTaskCompleteListener callback;
    private Context activity = null;
    private boolean isDownloaded;
    private String requestType = null;

    public DownloadFileFromURL(AsyncTaskCompleteListener callback, Context activity, String requestType, String fileName){

        this.callback = callback;
        this.activity = activity;
        this.requestType = requestType;
        this.fileName = fileName;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.progressDialog = new ProgressDialog(activity);
        this.progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        this.progressDialog.setCancelable(false);
        this.progressDialog.setTitle(requestType.equals(Constants.DOWNLOAD_VIEWFILE_TAG) ? "Loading file....." : "Downloading....");
        this.progressDialog.show();
    }

    /**
     * Downloading file in background thread
     * */
    @Override
    protected HttpResultDo doInBackground(String... f_url) {
        int count;
        try {
            URL url = new URL(f_url[0]);
            URLConnection connection = url.openConnection();
            connection.connect();

            // this will be useful so that you can show a tipical 0-100%
            // progress bar
            int lenghtOfFile = connection.getContentLength();

            // download the file
            InputStream input = new BufferedInputStream(url.openStream(),
                    8192);

            // Output stream
            OutputStream output = new FileOutputStream(Environment
                    .getExternalStorageDirectory().toString()
                    + "/2011.kml");

            byte data[] = new byte[1024];

            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
                // publishing the progress....
                // After this onProgressUpdate will be called
                publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                // writing data to file
                output.write(data, 0, count);
            }

            // flushing output
            output.flush();

            // closing streams
            output.close();
            input.close();

        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }

        return null;
    }

    /**
     * Updating progress bar
     * */
    protected void onProgressUpdate(String... progress) {
        // setting progress percentage
        progressDialog.setProgress(Integer.parseInt(progress[0]));
    }

    /**
     * After completing background task Dismiss the progress dialog
     * **/
    @Override
    protected void onPostExecute(HttpResultDo httpResult) {
        // dismiss the dialog after the file was downloaded
        this.progressDialog.dismiss();

        // Display File path after downloading
        httpResult.setRequestType(requestType);
        callback.onAsyncTaskComplete(httpResult);

    }

}


