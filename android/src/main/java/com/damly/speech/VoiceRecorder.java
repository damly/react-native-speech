package com.damly.speech;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import com.damly.haian.AudioProJni;

/**
 * Created by timekettle on 2018/5/28.
 */

public class VoiceRecorder {
    public static final String TAG = "VoiceRecorder";

    private static int defaultSampleRateInHz = 16000;
    private static int defaultAudioSource = MediaRecorder.AudioSource.MIC;
    private static int defaultChannelConfig = AudioFormat.CHANNEL_IN_STEREO;
    private static int defaultAudioFormat = AudioFormat.ENCODING_PCM_16BIT;
    private boolean isWorking = false;
    private AudioRecord mAudioRecord = null;

    public VoiceRecorder(Context context) {

    }

    public void start() {

        if(isWorking) {
            return;
        }

        if(mAudioRecord != null) {
            mAudioRecord.stop();
            mAudioRecord = null;
        }

        int iRetCode = AudioProJni.getInstance().RTMSG_OK;
        if ((iRetCode = AudioProJni.getInstance().InitHianVad("1012:153", 20, 20, 6.0f, 6.0f)) != AudioProJni.getInstance().RTMSG_OK) {
            Log.e(TAG, "初始化海岸VAD 库错误!");
            iRetCode = AudioProJni.getInstance().RTMSG_KO;
            return;
        }

        Thread recordThread = new Thread(new Runnable() {
            @Override
            public void run() {

                int iDelayNum  = 30;
                int bFrameSize = 256*2*2;
                int nFrameSize = 256*2;
                int iLen = 0;
                int iIndex,jIndex;
                boolean bLFrist = true;
                boolean bRFrist = true;
                byte[]  bSrcData  = new byte[bFrameSize];    // 没帧读取的数据
                byte[]  bLChData  = new byte[nFrameSize];
                byte[]  bRChData  = new byte[nFrameSize];
                byte[]  bLChBuff  = new byte[nFrameSize * iDelayNum];    // 左声道算法缓冲区
                byte[]  bRChBuff  = new byte[nFrameSize * iDelayNum];    // 右声道算法缓冲区
                short[] nSrcData  = new short[nFrameSize];

                int bufferSize = AudioRecord.getMinBufferSize(defaultSampleRateInHz, defaultChannelConfig, defaultAudioFormat);
                Log.e(TAG, "AudioRecord bufferSize:"+bufferSize);

                mAudioRecord = new AudioRecord(defaultAudioSource, defaultSampleRateInHz, defaultChannelConfig, defaultAudioFormat, bufferSize);
                mAudioRecord.startRecording();
                byte[] buffer = new byte[bufferSize];

                while (isWorking) {
                    int bufferReadResult = mAudioRecord.read(buffer, 0, bufferSize);
                    if (bufferReadResult == AudioRecord.ERROR_INVALID_OPERATION || bufferReadResult == AudioRecord.ERROR_BAD_VALUE) {
                        continue;
                    }

                    if(bufferSize != bufferReadResult) {
                        Log.e(TAG, "bufferReadResult: "+bufferReadResult);
                    }
                    if (bufferReadResult != 0 && bufferReadResult != -1) {

                    }
                }

                mAudioRecord.release();
                mAudioRecord = null;
                Log.e(TAG, "录音已停止");
            }
        });

        recordThread.start();
        isWorking = true;
    }

    public void stop() {

        if(isWorking) {
            isWorking = false;
        }
    }

    public boolean isWorking() {
        return isWorking;
    }


 }
