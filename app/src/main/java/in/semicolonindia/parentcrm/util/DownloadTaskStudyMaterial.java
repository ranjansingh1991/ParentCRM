package in.semicolonindia.parentcrm.util;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import in.semicolonindia.parentcrm.R;

import static android.content.Context.NOTIFICATION_SERVICE;
import static in.semicolonindia.parentcrm.rest.BaseUrl.sStudyMaterialDownloadURL;

/**
 * Created by Rupesh on 17-01-2018.
 */

public class DownloadTaskStudyMaterial extends AsyncTask<String, String, String> {

    private Activity activity;
    private String sFileName;
    private String sFileType;
    private ProgressDialog mProgressDialog;
    NotificationManager mNotifyManager;
    NotificationCompat.Builder mBuilder;
    int id = 100;

    public DownloadTaskStudyMaterial(Activity activity, String sFileName) {
        this.activity = activity;
        this.sFileName = sFileName;
        String[] temp = sFileName.split("\\.");
        this.sFileType = temp[temp.length - 1];
        mProgressDialog = new ProgressDialog(activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mNotifyManager = (NotificationManager) activity.getSystemService(NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(activity);
        mBuilder.setContentTitle(" file Download")
                .setContentText("Download in progress")
                .setSmallIcon(R.drawable.ic_launcher);
        mBuilder.setProgress(100, 0, false);
        mBuilder.setSubText("click download file");
        mNotifyManager.notify(id, mBuilder.build());
        Toast.makeText(activity, "Downloading the file........", Toast.LENGTH_LONG).show();

        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setMessage("DOWNLOADING...");
        mProgressDialog.getWindow().setGravity(Gravity.CENTER);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setTitle(sFileName);
        mProgressDialog.setIcon(activity.getResources().getDrawable(R.drawable.ic_launcher));
        mProgressDialog.show();
    }

    @Override
    protected String doInBackground(String... parms) {
        int count;
        String sStatus;
        try {
            URL url = new URL(sStudyMaterialDownloadURL + sFileName);
            URLConnection connection = url.openConnection();
            connection.connect();
            // getting file length
            int lenghtOfFile = connection.getContentLength();
            // input stream to read file - with 8k buffer
            InputStream input = new BufferedInputStream(url.openStream(), 8192);
            File direct = new File(Environment.getExternalStorageDirectory() + "/SemiCRM/Downloads/");
            if (!direct.exists()) {
                File mProfilePictureDirectory = new File("/sdcard/SemiCRM/Downloads/");
                mProfilePictureDirectory.mkdirs();
            }
            // Output stream to write file
            OutputStream output = new FileOutputStream("/sdcard/SemiCRM/Downloads/" + sFileName);
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
            sStatus = "Success";
        } catch (Exception e) {
            e.printStackTrace();
            sStatus = "Failure";
        }
        return sStatus;
    }

    @Override
    protected void onPostExecute(String file_url) {
        switch (file_url) {
            case "Success":
                Toast.makeText(activity, "File downloaded to /sdcard/SemiCRM/Downloads/"
                        + sFileName + sFileType, Toast.LENGTH_LONG).show();
                break;
            case "Failure":
               /* Toast.makeText(activity, "Error while downloading, please try again.",
                        Toast.LENGTH_LONG).show();*/
                Toast.makeText(activity, "File Does not Exists...",
                        Toast.LENGTH_LONG).show();
                break;
        }
        mBuilder.setContentText("Download complete");
        mNotifyManager.notify(100,mBuilder.build());

        mProgressDialog.dismiss();
    }

    /**
     * Updating progress bar
     */
    protected void onProgressUpdate(String... progress) {
        // setting progress percentage
        Log.d("ANDRO_ASYNC", progress[0]);
        mBuilder.setProgress(100,Integer.parseInt(progress[0]),false);

        mProgressDialog.setProgress(Integer.parseInt(progress[0]));
        mProgressDialog.setMessage("DOWNLOADING..." + Integer.parseInt(progress[0]) + "%");
    }
}