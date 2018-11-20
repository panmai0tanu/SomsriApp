package com.pethoalpar.androidtesstwoocr.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.googlecode.tesseract.android.TessBaseAPI;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.pethoalpar.androidtesstwoocr.R;
import com.pethoalpar.androidtesstwoocr.ToolbarActivity;
import com.scanlibrary.ScanActivity;
import com.scanlibrary.ScanConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


@SuppressLint("Registered")
public class TesseractActivity extends ToolbarActivity {

    private static final int REQUEST_CODE = 99;
    public static final String TESS_DATA = "/tessdata";
    private static final String TAG = TesseractActivity.class.getSimpleName();
    private TessBaseAPI tessBaseAPI;
    private String pathToDataFile;
    private Bitmap bitmap = null;
    private KProgressHUD loading = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loading = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(getString(R.string.loading))
                .setDimAmount(0.5f);

        int preference = 4;
        startScan(preference,null);
    }

    protected void startScan(int preference, String url) {
        if (url == null) {
            Intent intent = new Intent(this, ScanActivity.class);
            intent.putExtra(ScanConstants.OPEN_INTENT_PREFERENCE, preference);
            startActivityForResult(intent, REQUEST_CODE);
        } else {
            Intent intent = new Intent(this, ScanActivity.class);
            intent.putExtra(ScanConstants.OPEN_INTENT_PREFERENCE, preference);
            intent.putExtra("url", url);
            startActivityForResult(intent, 1);
            loading.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getExtras().getParcelable(ScanConstants.SCANNED_RESULT);

            AsyncTask.execute(() -> {
                OutputStream os;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    getContentResolver().delete(uri, null, null);

                    File photoFile = createImageFile();

                    os = new FileOutputStream(photoFile);
                    int imageWidth = bitmap.getWidth();
                    int imageHeight = bitmap.getHeight();
                    int newHeight = (imageHeight * 2000) / imageWidth;
                    bitmap = Bitmap.createScaledBitmap(bitmap, 2000, newHeight, true);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 10, os);

                    startScan(4, photoFile.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                    finish();
                }
            });
            loading.show();

        } else if (requestCode == 1 && resultCode == Activity.RESULT_OK){
            Uri uri = data.getExtras().getParcelable(ScanConstants.SCANNED_RESULT);
            AsyncTask.execute(() -> {

                OutputStream os;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    getContentResolver().delete(uri, null, null);

                    File photoFile = createImageFile();

                    os = new FileOutputStream(photoFile);
                    int imageWidth = bitmap.getWidth();
                    int imageHeight = bitmap.getHeight();
                    int newHeight = (imageHeight * 1000) / imageWidth;
                    bitmap = Bitmap.createScaledBitmap(bitmap, 1000, newHeight, true);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 10, os);

                    prepareTessData();
                    startOCR(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    finish();
                }
            });
            loading.show();
        } else {
            finish();
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        return image;
    }

    private void prepareTessData() {
        try {
            File dir = getExternalFilesDir(TESS_DATA);
            if (!dir.exists()) {
                if (!dir.mkdir()) {
                    Toast.makeText(getApplicationContext(), "The folder " + dir.getPath() + "was not created", Toast.LENGTH_SHORT).show();
                }
            }
            String fileList[] = getResources().getAssets().list("");
            for (String fileName : fileList) {
                pathToDataFile = dir + "/" + fileName;
                try {
                    if (!(new File(pathToDataFile)).exists()) {
                        InputStream in = getResources().getAssets().open(fileName);
                        OutputStream out = new FileOutputStream(pathToDataFile);
                        byte[] buff = new byte[1024];
                        int len;
                        while ((len = in.read(buff)) > 0) {
                            out.write(buff, 0, len);
                        }
                        in.close();
                        out.close();
                    }
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }

            }

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void startOCR(Bitmap bitmap) {
        try {

            String result = this.getText(bitmap);

            Intent intent = new Intent(getBaseContext(), ResultActivity.class);
            intent.putExtra("result", result);
            startActivity(intent);
            loading.dismiss();
            finish();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private String getText(Bitmap bitmap) {
        try {
            tessBaseAPI = new TessBaseAPI();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        String dataPath = getExternalFilesDir("/").getPath() + "/";
        tessBaseAPI.init(dataPath, "eng+tha");
        tessBaseAPI.setImage(bitmap);
        String retStr = "No result";
        try {
            retStr = tessBaseAPI.getUTF8Text();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        tessBaseAPI.end();
        return retStr;
    }

}
