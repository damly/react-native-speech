package com.damly.speech;

import android.support.annotation.Nullable;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by timekettle on 2018/5/28.
 */

public class SpeechTranslatorModule  extends ReactContextBaseJavaModule {

    private static final String AutoMode = "AutoMode";
    private static final String ManualMode = "ManualMode";

    private ReactApplicationContext mContext;
    private VoiceRecorder   mVoiceRecorder;

    public SpeechTranslatorModule(ReactApplicationContext reactContext) {
        super(reactContext);

        mContext = reactContext;
    }

    @Override
    public String getName() {
        return "SpeechTranslatorModule";
    }

    @ReactMethod
    public void start(int mode) {
        mVoiceRecorder.start();
    }

    @ReactMethod
    public void stop() {
        mVoiceRecorder.stop();
    }

    @Nullable
    @Override
    public Map<String, Object> getConstants() {

        Map<String, Object> constants = new HashMap<>();
        constants.put(AutoMode, 0);
        constants.put(ManualMode, 1);
        return constants;
    }
}
