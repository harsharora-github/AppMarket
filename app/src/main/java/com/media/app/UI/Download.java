package com.media.app.UI;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by harsh.arora on 4/11/2017.
 */

public class Download {
    String AppFolder = "Appdownload";
    String loc = AppFolder;
    private File dir = new File(AppFolder);
   static String fileName;


    public String Download(String ur, String loc) {

        try {
            URL url = new URL(ur);
             fileName = ur.substring(ur.lastIndexOf('/') + 1, ur.length());
            loc += "\\" + fileName;
            //  Log.d("harsh", "DownloadFile:"+fileName);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                File sdcard = Environment.getExternalStorageDirectory();
                File file = new File(sdcard, loc);

                if (!file.exists()) {

                    String[] path = loc.split("\\\\");
                    int l = path.length;
                    String p = "";
                    for (int i = 0; i < l - 1; i++) {
                        p += path[i];
                    }


                    File dr = new File(sdcard, p);
                    dir = dr;
                    if (dr.exists()) {
                        Log.d("harsh", "Download:dr exists");
                        File app = new File(sdcard + "/" + p, "/" + path[l - 1]);
                        FileOutputStream fileOutput = new FileOutputStream(app);
                        InputStream inputStream = conn.getInputStream();
                        int totalSize = conn.getContentLength();
                        int downloadedSize = 0;
                        byte[] buffer = new byte[1024];
                        int bufferLength = 0;
                        while ((bufferLength = inputStream.read(buffer)) > 0) {
                            //add the data in the buffer to the file in the file output stream (the file on the sd card
                            fileOutput.write(buffer, 0, bufferLength);
                            //add up the size so we know how much is downloaded
                            downloadedSize += bufferLength;
                            //this is where you would do something to report the prgress, like this maybe
                        }
                        fileOutput.close();
                        if (conn != null) {
                            conn.disconnect();
                        }
                        return app.getAbsolutePath();
                    } else if (dr.mkdirs()) {
                        Log.d("harsh", "Download:dr create");
                        File app = new File(sdcard + "/" + p, "/" + path[l - 1]);
                        FileOutputStream fileOutput = new FileOutputStream(app);
                        InputStream inputStream = conn.getInputStream();
                        int totalSize = conn.getContentLength();
                        int downloadedSize = 0;
                        byte[] buffer = new byte[1024];
                        int bufferLength = 0;
                        while ((bufferLength = inputStream.read(buffer)) > 0) {
                            //add the data in the buffer to the file in the file output stream (the file on the sd card
                            fileOutput.write(buffer, 0, bufferLength);
                            //add up the size so we know how much is downloaded
                            downloadedSize += bufferLength;
                            //this is where you would do something to report the prgress, like this maybe
                        }
                        fileOutput.close();
                        if (conn != null) {
                            conn.disconnect();
                        }
                        return app.getAbsolutePath();
                    } else {
                        Log.d("harsh", "Download: No write access");
                        // deleteDir();
                        return null;
                    }
                }
            } else {
                return null;
            }
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
            return null;
        } catch (IOException e1) {
            e1.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public String DownloadFiles(String ur) {
        return Download(ur, loc);
    }

    public boolean deleteDir() {
        return deleteDirectory(dir);
    }

    private boolean deleteDirectory(File dir) {

        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDirectory(children[i]);
                if (!success) {
                    return false;
                }
            }
        }

        // either file or an empty directory
        //System.out.println("removing file or directory : " + dir.getName());
        return dir.delete();
    }
}