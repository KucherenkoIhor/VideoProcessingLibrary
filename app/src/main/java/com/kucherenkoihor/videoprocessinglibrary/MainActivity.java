package com.kucherenkoihor.videoprocessinglibrary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.appyvet.rangebar.RangeBar;
import com.kucherenkoihor.vpl.VideoProcessing;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_TAKE_GALLERY_VIDEO = 1001;

    VideoProcessing mVideoProcessing = new VideoProcessing();

    private VideoView mVideoView;
    private RangeBar mRangeBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mVideoView = (VideoView) findViewById(R.id.videoView);
        mRangeBar = (RangeBar) findViewById(R.id.rangebar);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Choose file..."), REQUEST_TAKE_GALLERY_VIDEO);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_GALLERY_VIDEO) {
                Uri selectedImageUri = data.getData();
                final String selectedPath = getPath(selectedImageUri);

                ((TextView) findViewById(R.id.tv)).setText(selectedPath);

                try {
                    int duration = mVideoProcessing.getDuration(selectedPath);
                    mRangeBar.setTickEnd(duration);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                mVideoView.setVideoPath(selectedPath);

                mVideoView.setMediaController(new MediaController(this));
                mVideoView.seekTo(100);

                findViewById(R.id.btnTrim).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final String output = new File(new File(selectedPath).getParent(), "trimmed.mp4").toString();
                        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                        progressDialog.show();
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                try {
                                    mVideoProcessing.trim(selectedPath, output, mRangeBar.getLeftIndex(), mRangeBar.getRightIndex());

                                    mVideoProcessing.speedOfVideo(selectedPath, output, 2);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressDialog.dismiss();
                                            mVideoView.setVideoPath(output);
                                            mVideoView.setMediaController(new MediaController(MainActivity.this));
                                            mVideoView.seekTo(100);
                                            mVideoView.requestFocus(0);
                                            mVideoView.start();
                                        }
                                    });


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();

                    }
                });


            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(uri, projection, null, null, null);
            if (cursor != null) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
                cursor.moveToFirst();
                return cursor.getString(column_index);
            } else
                return null;
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
    }


}
