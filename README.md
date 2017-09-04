Video Processing Library
===================================

![](https://preview.ibb.co/dxF3Gv/f.png)

This library uses FFmpeg to implement util functions for video processing.

Introduction
------------

The Android SDK does not contain a functionality to process audio/video makes out of the box.
The purpose of this library is to create tool for the video processing.
The library provides a simple API, so, you as a user can pay attention to the main logic of your app.

Screenshot of sample
-------------

<img src="https://preview.ibb.co/dSUzOa/device_2017_09_04_173619.png" height="400" alt="Screenshot"/> 

Getting Started
---------------
To use this library add this lines to your build.gradle file:

```Groovy 
repositories {
    jcenter()
    maven { url "https://jitpack.io" }
}

compile 'com.github.KucherenkoIhor:VideoProcessingLibrary:master-SNAPSHOT'
```

Usage
---------------

```Java
VideoProcessing mVideoProcessing = new VideoProcessing();

int duration = mVideoProcessing.getDuration(selectedPath);

mVideoProcessing.trim(selectedPath, output, mRangeBar.getLeftIndex(), mRangeBar.getRightIndex());

mVideoProcessing.remux(selectedPath, output);

mVideoProcessing.rotateDisplayMatrix(selectedPath, output, 90.0);

mVideoProcessing.mergeAudioWithVideoWithoutTranscoding(selectedPathVideo, selectedPathAudio, output);

mVideoProcessing.speedOfVideo(selectedPath, output, 2);

```

 ## License

MIT License

Copyright (c) 2017 Ihor Kucherenko

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.


