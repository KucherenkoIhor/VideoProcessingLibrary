package com.kucherenkoihor.videoprocessinglibrary;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.kucherenkoihor.vpl.VideoProcessing;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_TAKE_GALLERY_VIDEO = 1001;

    VideoProcessing videoProcessing = new VideoProcessing();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    coef = Integer.parseInt(((EditText) findViewById(R.id.input)).getText().toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    coef = 0;
                }
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Video"), REQUEST_TAKE_GALLERY_VIDEO);
            }
        });

    }

    int coef = 0;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_GALLERY_VIDEO) {
                Uri selectedImageUri = data.getData();
                final String selectedImagePath = getPath(selectedImageUri);



                //"/storage/emulated/0/DCIM/Video/V70815-205742.mp4"
                // "/storage/emulated/0/Recorder/gggg.mp3";
                if (selectedImagePath != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String output = getBaseName(selectedImagePath) + "1.mp4";
                            try {
                                videoProcessing.rotateDisplayMatrix(selectedImagePath, output, 270.0);
                                //videoProcessing.trim(selectedImagePath, output, 3.0, 9.3);
                            //try {
                               // videoProcessing.getDuration(selectedImagePath);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                                //videoProcessing.mergeAudioWithVideoWithoutTranscoding("/storage/emulated/0/DCIM/Video/V70815-205742.mp4", selectedImagePath, output);
                           // } catch (IOException e) {
                          //      e.printStackTrace();
                         //   }
                            //   trim(selectedImagePath, output, 1.0, 3.3);
                            //rotate(selectedImagePath, output, 360);
                            try {
                                //r(selectedImagePath, output);
                                //     speed(selectedImagePath, output, coef);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.setDataAndType(Uri.fromFile(new File(output).getParentFile()), "resource/folder");
                            startActivity(intent);
                        }
                    }).start();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static String getBaseName(String fileName) {
        int index = fileName.lastIndexOf('.');
        if (index == -1) {
            return fileName;
        } else {
            return fileName.substring(0, index);
        }
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Video.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }


}
