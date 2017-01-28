package com.sopa.mvvc.datamodel.network.asynchttp;

import android.os.Environment;
import android.webkit.URLUtil;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BinaryHttpResponseHandler;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import cz.msebera.android.httpclient.Header;

public class MapDownloader {

    public static AsyncHttpClient client = new AsyncHttpClient();

    private final static File dir_mcpe = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/games/com.mojang/minecraftWorlds/");
    private final static String _downloadDir = Environment.getDownloadCacheDirectory().getAbsolutePath() + "/MCPEmaps/";


    public void downloadMap(String url, final LoadingListener listener) throws IOException {


        boolean b = dir_mcpe.mkdirs();
        String urlFilename = URLUtil.guessFileName(url, null, "application/zip");

        ZipListener zl = (name, bytesProcessed, bytesTotal, isDirectory, isZip) -> {
            listener.onBeginUnZip();
            listener.onProgressUpdate(percentage(bytesProcessed, bytesTotal));

        };


        File zip = new File(_downloadDir + urlFilename);


        if (zip.exists()) {     //file alreadyExists  //add overwrite strategy?
            listener.onBeginUnZip();
            unpackZip(new FileInputStream(zip), dir_mcpe.getAbsolutePath(), zip.length(), zl);
        } else {
            listener.onBeginDownload();

            client.get(url, new BinaryHttpResponseHandler(allowedContentTypes) {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] bin) {

                    listener.onBeginUnZip();

                    // unpackZip(bin, zl, dir_mcpe.getAbsolutePath());
                    try {
                        unpackZip(true, bin, zl, dir_mcpe.getAbsolutePath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    listener.onFinishedUnzip();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] binaryData, Throwable error) {
                    error.printStackTrace();
                    listener.onFailure();
                }


                @Override
                public void onProgress(long bytesWritten, long totalSize) {
                    int progresspercent = percentage(bytesWritten, totalSize);
                    listener.onProgressUpdate(progresspercent);
                    super.onProgress(bytesWritten, totalSize);
                }

            });

        }
    }

    private static int percentage(double part, double total) {
        return (int) (part / total * 100);
    }

    private boolean unpackZip(boolean useByteBuffer, byte[] bytes, ZipListener listener, String outputDir) throws IOException {

        if(useByteBuffer)unpackZip(ByteBuffer.wrap(bytes),outputDir,bytes.length,listener);
        else unpackZip(new ByteArrayInputStream(bytes),dir_mcpe.getAbsolutePath(),bytes.length,listener);

        return true;

    }




    //-------------   v1  ----------------
    private boolean unpackZip(InputStream stream, String outputDir, long totalBytes, ZipListener listener) throws IOException {

        boolean isDir = false, isZip = false;

        _dirChecker(outputDir);
        ZipInputStream zis = new ZipInputStream(stream);

        ZipEntry ze;
        long bytesWritten = 0;

        while ((ze = zis.getNextEntry()) != null) {

            if (isDir = ze.isDirectory()) {
                _dirChecker(ze.getName());

            } else if (isZip = ze.getName().endsWith("zip")) {
                unpackZip(zis, outputDir + ze.getName() + File.separator, ze.getCompressedSize(), (name, _bytesProcessed, bytesTotal, isDirectory, isZip1) -> {
                    //callback fires every next file in inner zip
                });
                bytesWritten += ze.getCompressedSize();
            } else {

                FileOutputStream fout = new FileOutputStream(dir_mcpe + ze.getName());
                int count;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                while ((count = zis.read(buffer)) != -1) {
                    baos.write(buffer, 0, count);
                    byte[] bytes = baos.toByteArray();
                    fout.write(bytes);
                    baos.reset();
                    bytesWritten += buffer.length;

                }
                fout.close();
            }

            listener.onEntry(ze.getName(), bytesWritten, totalBytes, isDir, isZip);
            zis.closeEntry();
        }
        zis.close();


        return true;
    }


    //-------------   v2  ----------------


    private void unpackZip(ByteBuffer buffer,  String outputDir, long totalBytes, ZipListener zipListener) throws IOException {


        long bytesWritten = 0;
        boolean isDir = false, isZip = false;
        _dirChecker(outputDir);

        ZipInputStream zis = new ZipInputStream(null);
        zis.read(buffer.array());

        ZipEntry ze;


        while ((ze = zis.getNextEntry()) != null) {
            if (isDir = ze.isDirectory()) {
                _dirChecker(ze.getName());

            } else if (isZip = ze.getName().endsWith("zip")) {
                unpackZip(zis, outputDir + ze.getName() + File.separator, ze.getCompressedSize(), (name, _bytesProcessed, bytesTotal, isDirectory, isZip1) -> {});
                bytesWritten += ze.getCompressedSize();
            } else {
                FileOutputStream fout = new FileOutputStream(dir_mcpe + ze.getName());
                readBuffer(buffer, zis);
                writeBuffer(buffer, fout);
                bytesWritten += ze.getSize();
            }
            zipListener.onEntry(ze.getName(), bytesWritten, totalBytes, isDir, isZip);
            zis.closeEntry();
        }
        zis.close();
    }


    private void _dirChecker(String dir) {
        File f = new File(dir);
        if (!f.isDirectory()) {
            f.mkdirs();
        }
    }


    public static ByteBuffer fromArray(byte[] payload) {
        ByteBuffer buffer = ByteBuffer.wrap(payload);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        return buffer;
    }

    public static byte[] toArray(int value) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        return buffer.array();
    }

    private void writeBuffer(ByteBuffer buffer, OutputStream stream) throws IOException {
        WritableByteChannel channel = Channels.newChannel(stream);
        channel.write(buffer);
    }

    private void readBuffer(ByteBuffer buffer, InputStream inputStream) throws IOException {
        ReadableByteChannel channel = Channels.newChannel(inputStream);
        channel.read(buffer);

    }

    private final static String[] allowedContentTypes = new String[]{"application/octet-stream",
            "application/zip", "text/html; charset=ISO-8859-1", "image/png", "image/jpeg",
            "image/bmp", "application/pdf", "text/html; charset=UTF-8", "image/png;charset=UTF-8"};


}

