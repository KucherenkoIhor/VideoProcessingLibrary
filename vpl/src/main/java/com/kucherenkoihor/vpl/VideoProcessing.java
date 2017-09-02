package com.kucherenkoihor.vpl;

/**
 * Created by ihor_kucherenko on 8/25/17.
 * https://github.com/KucherenkoIhor
 */

public class VideoProcessing {

    static {
        System.loadLibrary("vpl");
    }

    public native int getDuration(String input) throws Exception;

    public native int remux(String input, String output) throws Exception;

    public native int mergeAudioWithVideoWithoutTranscoding(String inputV, String inputA, String output) throws Exception;

    public native int trim(String input, String output, double start, double end) throws Exception;

    public native int rotateDisplayMatrix(String input, String output, double rotation) throws Exception;

    public native int speedOfVideo(String input, String output, int coefficient) throws Exception;
}
