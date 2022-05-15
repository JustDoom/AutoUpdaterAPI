package com.imjustdoom.autoupdater;

import com.sun.scenario.Settings;
import javafx.application.Platform;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadProcess extends Thread {

    private String downloadUrl;
    private String path;
    private double currentProgress = 0.0;

    public DownloadProcess(String url, String path) {
        this.downloadUrl = url;
        this.path = path;
    }

    @Override
    public void start() {
        System.out.println("Download process started");

        try {

            URL url = new URL(downloadUrl);
            HttpURLConnection httpConnection = (HttpURLConnection) (url.openConnection());
            long completeFileSize = httpConnection.getContentLength();

            BufferedInputStream in = new BufferedInputStream(httpConnection.getInputStream());
            FileOutputStream fos = new FileOutputStream(path);
            BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);

            byte[] data = new byte[1024];
            int byteContent;
            int downloaded = 0;
            while ((byteContent = in.read(data, 0, 1024)) != -1) {
                downloaded += byteContent;

                currentProgress = ((((double) downloaded) / ((double) completeFileSize)));

                bout.write(data, 0, byteContent);
            }
            bout.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double getCurrentProgress() {
        return currentProgress;
    }
}
