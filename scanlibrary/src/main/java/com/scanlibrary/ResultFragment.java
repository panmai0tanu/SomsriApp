package com.scanlibrary;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.kaopiz.kprogresshud.KProgressHUD;

import java.io.IOException;

/**
 * Created by jhansi on 29/03/15.
 */
public class ResultFragment extends Fragment {

    private View view;
    private ImageView scannedImageView;
    private Button doneButton;
    private Bitmap original;
    private Button originalButton;
    private Button MagicColorButton;
    private Button grayModeButton;
    private Button bwButton;
    private Bitmap transformed;
    private KProgressHUD loading = null;
    private String path;

    public ResultFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.result_layout, null);

        loading = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(getString(R.string.loading))
                .setDimAmount(0.5f);

        init();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d("PANMAI", "Hi");
        loading.show();
        Uri uri = getUri();
        try {
            original = Utils.getBitmap(getActivity(), uri);
            getActivity().getContentResolver().delete(uri, null, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Intent data = new Intent();
                    Bitmap bitmap = transformed;
                    if (bitmap == null) {
                        bitmap = original;
                    }
                    Uri uri = Utils.getUri(getActivity(), bitmap);
                    data.putExtra(ScanConstants.SCANNED_RESULT, uri);
                    getActivity().setResult(Activity.RESULT_OK, data);
                    original.recycle();
                    System.gc();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loading.dismiss();
                            getActivity().finish();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void init() {
//        scannedImageView = (ImageView) view.findViewById(R.id.scannedImage);
//        originalButton = (Button) view.findViewById(R.id.original);
//        originalButton.setOnClickListener(new OriginalButtonClickListener());
//        MagicColorButton = (Button) view.findViewById(R.id.magicColor);
//        MagicColorButton.setOnClickListener(new MagicColorButtonClickListener());
//        grayModeButton = (Button) view.findViewById(R.id.grayMode);
//        grayModeButton.setOnClickListener(new GrayButtonClickListener());
//        bwButton = (Button) view.findViewById(R.id.BWMode);
//        bwButton.setOnClickListener(new BWButtonClickListener());
//        Bitmap bitmap = getBitmap();
//        setScannedImage(bitmap);
//        doneButton = (Button) view.findViewById(R.id.doneButton);
//        doneButton.setOnClickListener(new DoneButtonClickListener());

    }

    private Bitmap getBitmap() {
        Uri uri = getUri();
        try {
            original = Utils.getBitmap(getActivity(), uri);
            getActivity().getContentResolver().delete(uri, null, null);
            return original;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Uri getUri() {
        Uri uri = getArguments().getParcelable(ScanConstants.SCANNED_RESULT);
        return uri;
    }

    public void setScannedImage(Bitmap scannedImage) {
        scannedImageView.setImageBitmap(scannedImage);
    }

    private class DoneButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            loading.show();
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Intent data = new Intent();
                        Bitmap bitmap = transformed;
                        if (bitmap == null) {
                            bitmap = original;
                        }
                        Uri uri = Utils.getUri(getActivity(), bitmap);
                        data.putExtra(ScanConstants.SCANNED_RESULT, uri);
                        getActivity().setResult(Activity.RESULT_OK, data);
                        original.recycle();
                        System.gc();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loading.dismiss();
                                getActivity().finish();
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private class BWButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(final View v) {
            loading.show();
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        transformed = ((ScanActivity) getActivity()).getBWBitmap(original);
                    } catch (final OutOfMemoryError e) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                transformed = original;
                                scannedImageView.setImageBitmap(original);
                                e.printStackTrace();
                                loading.dismiss();
                                onClick(v);
                            }
                        });
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            scannedImageView.setImageBitmap(transformed);
                            loading.dismiss();
                        }
                    });
                }
            });
        }
    }

    private class MagicColorButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(final View v) {
            loading.show();
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        transformed = ((ScanActivity) getActivity()).getMagicColorBitmap(original);
                    } catch (final OutOfMemoryError e) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                transformed = original;
                                scannedImageView.setImageBitmap(original);
                                e.printStackTrace();
                                loading.dismiss();
                                onClick(v);
                            }
                        });
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            scannedImageView.setImageBitmap(transformed);
                            loading.dismiss();
                        }
                    });
                }
            });
        }
    }

    private class OriginalButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            try {
                loading.show();
                transformed = original;
                scannedImageView.setImageBitmap(original);
                loading.dismiss();
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
                loading.dismiss();
            }
        }
    }

    private class GrayButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(final View v) {
            loading.show();
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        transformed = ((ScanActivity) getActivity()).getGrayBitmap(original);
                    } catch (final OutOfMemoryError e) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                transformed = original;
                                scannedImageView.setImageBitmap(original);
                                e.printStackTrace();
                                loading.dismiss();
                                onClick(v);
                            }
                        });
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            scannedImageView.setImageBitmap(transformed);
                            loading.dismiss();
                        }
                    });
                }
            });
        }
    }
}